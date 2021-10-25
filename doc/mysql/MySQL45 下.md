# MySQL45 下

## 23 | MySQL是怎么保证数据不丢的？

> 今天这篇文章，我会继续和你介绍在业务高峰期临时提升性能的方法。从文章标题“MySQL 是怎么保证数据不丢的？”，你就可以看出来，今天我和你介绍的方法，跟数据的可靠性有关。

在专栏前面文章和答疑篇中，我都着重介绍了 WAL 机制（你可以再回顾下第 2 篇、第 9 篇、第 12 篇和第 15 篇文章中的相关内容），得到的结论是：只要 redo log 和 binlog 保证持久化到磁盘，就能确保 MySQL 异常重启后，数据可以恢复。

- redo log 两阶段提交协议 prepare和commit都有就完整，没有commit需要判断binlog 是否完整 完整则认为数据完整否则redo log回滚
  - WAL通过log记录+内存更新尽量避免磁盘IO从而提升性能

**我们就再一起看看 MySQL 写入 binlog 和 redo log 的流程。**

### binlog 的写入机制

- binlog 借助binlog cache写回磁盘 事务执行过程事务记录到binlog cache 事务提交把binlog cache 写入binlog文件
- 不论事务多大也要确保一次性写入，`binlog_cache_size`控制单个线程内的binlog cache所占内存大小。超出规格暂存磁盘
- 事务提交清空cache 并写入binlog

![](https://static001.geekbang.org/resource/image/9e/3e/9ed86644d5f39efb0efec595abb92e3e.png)

可以看到，**每个线程有自己 binlog cache，但是共用同一份 binlog 文件。**

- 图中的 write，指的就是指把日志写入到文件系统的 page cache，并没有把数据持久化到磁盘，所以速度比较快。
- 图中的 fsync，才是将数据持久化到磁盘的操作。一般情况下，我们认为 fsync 才占磁盘的 IOPS。
  - 两阶段write+fsync,第一阶段文件缓存第二阶段写回磁盘，第二阶段耗时

write 和 fsync 的时机，是由参数 sync_binlog 控制的：

- sync_binlog=0 的时候，表示每次提交事务都只 write，不 fsync；
- sync_binlog=1 的时候，表示每次提交事务都会执行 fsync；
- sync_binlog=N(N>1) 的时候，表示每次提交事务都 write，但累积 N 个事务后才 fsync。
  - IO瓶颈可以提高sync_binlog的值，通常100-1000,但是崩溃丢失数据，如果主机发生异常重启，会丢失最近 N 个事务的 binlog 日志。

### redo log 的写入机制

事务在执行过程中，生成的 redo log 是要先写到 redo log buffer 的。

如果事务执行期间 MySQL 发生异常重启，那这部分日志就丢了。由于事务并没有提交，所以这时日志丢了也不会有损失。

- 事务没提交完成这部分redo log buffer写回了也是浪费

那么，另外一个问题是，事务还没提交的时候，redo log buffer 中的部分日志有没有可能被持久化到磁盘呢？

- 有可能因为是多线程公用，其他线程可能带着当前的一起写回
- redo log三状态:

![](https://static001.geekbang.org/resource/image/9d/d4/9d057f61d3962407f413deebc80526d4.png)

- 红色 存在redo log buffer中 不在buffer pool（包含脏页和change buffer）中
- 黄色 刷到file cache写到磁盘（write）但是没有持久化（fsync）
- 持久化到磁盘

日志写到 redo log buffer 是很快的，wirte 到 page cache 也差不多，但是持久化到磁盘的速度就慢多了。

为了控制 redo log 的写入策略，InnoDB 提供了 `innodb_flush_log_at_trx_commit` 参数，它有三种可能取值：

- 0 每次事务提交留在redo log buffer中
- 1 每次事务提交 直接持久化到磁盘
- 2 每次事务提交 写到page cache



**redo log什么时候持久化到磁盘**

- 后台线程每秒一次的轮询，将已提交的redo log buffer持久化到磁盘 会包含其他线程未提交的事务
- redo log buffer 达到 innodb_log_buffer_size 一半的时候，后台线程会主动写盘。
- 由于`innodb_flush_log_at_trx_commit`参数配置为1 因此其他线程提交事务同时写回当前线程的redo log.**并行的事务提交的时候，顺带将这个事务的 redo log buffer 持久化到磁盘。**

我们介绍两阶段提交的时候说过，时序上 redo log 先 prepare， 再写 binlog，最后再把 redo log commit。

- 如果把 innodb_flush_log_at_trx_commit 设置成 1，那么 redo log 在 prepare 阶段就要持久化一次，因为有一个崩溃恢复逻辑是要依赖于 prepare 的 redo log，再加上 binlog 来恢复的。
- crash-safe通过redo log的两阶段提交+binlog是否完整判断事务是否执行完成从而保证数据一致性
- **每秒一次后台轮询刷盘，再加上崩溃恢复这个逻辑，InnoDB 就认为 redo log 在 commit 的时候就不需要 fsync 了，只会 write 到文件系统的 page cache 中就够了。**
- 若binlog+redolog设置为双1 则一个事务提交两次刷盘
- TPS 20000 实际40000？ 其实实际就20000左右，由于组提交

> 这里，我需要先和你介绍日志逻辑序列号（log sequence number，LSN）的概念。LSN 是单调递增的，用来对应 redo log 的一个个写入点。每次写入长度为 length 的 redo log， LSN 的值就会加上 length。

- LSN redolog写入长度记录点
- 如下组提交

![](https://static001.geekbang.org/resource/image/93/cc/933fdc052c6339de2aa3bf3f65b188cc.png)

- tx1第一个到 就是组leader
- 写盘看事务记录 LSN代表最后一条就是160 包含三个事务
- tx1返回 同时将tx2 tx3也写入磁盘
- 所以，一次组提交里面，组员越多，节约磁盘 IOPS 的效果越好。但如果只有单线程压测，那就只能老老实实地一个事务对应一次持久化操作了。
- 在并发更新场景下，第一个事务写完 redo log buffer 以后，接下来这个 fsync 越晚调用，组员可能越多，节约 IOPS 的效果就越好。
- binlog分为两步
  - write到file fache
  - fsync到磁盘
  - 通过调整redolog prepare fsync放到较后位置提高性能
- ![](https://static001.geekbang.org/resource/image/5a/28/5ae7d074c34bc5bd55c82781de670c28.png)
- binlog也可以组提交
- 如果你想提升 binlog 组提交的效果，可以通过设置 binlog_group_commit_sync_delay 和 binlog_group_commit_sync_no_delay_count 来实现。
  - binlog_group_commit_sync_delay 参数，表示延迟多少微秒后才调用 fsync;binlog_group_commit_sync_no_delay_count 参数，表示累积多少次以后才调用 fsync。
- binlog/redo log都有组提交机制 redo log 自带 binlog可设置
- WAL 机制主要得益于两个方面：
  - redo log 和 binlog 都是顺序写，磁盘的顺序写比随机写速度要快；
  - 组提交机制，可以大幅度降低磁盘的 IOPS 消耗。
    - 组提交减少IO
- 

**如果你的 MySQL 现在出现了性能瓶颈，而且瓶颈在 IO 上，可以通过哪些方法来提升性能呢？**

- 设置binlog和redolog的刷盘机制
  - 将 sync_binlog 设置为大于 1 的值（比较常见是 100~1000）。这样做的风险是，主机掉电时会丢 binlog 日志。
  - 将 innodb_flush_log_at_trx_commit 设置为 2。这样做的风险是，主机掉电的时候会丢数据。 write cache
- 开启组提交
  - 设置 binlog_group_commit_sync_delay 和 binlog_group_commit_sync_no_delay_count 参数，减少 binlog 的写盘次数。这个方法是基于“额外的故意等待”来实现的，因此可能会增加语句的响应时间，但没有丢失数据的风险。

> 我不建议你把 innodb_flush_log_at_trx_commit 设置成 0。因为把这个参数设置成 0，表示 redo log 只保存在内存中，这样的话 MySQL 本身异常重启也会丢数据，风险太大。而 redo log 写到文件系统的 page cache 的速度也是很快的，所以将这个参数设置成 2 跟设置成 0 其实性能差不多，但这样做 MySQL 异常重启时就不会丢数据了，相比之下风险会更小。

binlog/redo log 都不建议设置为只在内存中 断电丢失数据

### 小结

如果 redo log 和 binlog 是完整的，MySQL 是如何保证 crash-safe 的。今天这篇文章，我着重和你介绍的是 MySQL 是“怎么保证 redo log 和 binlog 是完整的”。

- 通过redo log+binlog的内容确保crash-safe
  - redo log prepare+commit两阶段，两阶段都有binlog一定刷回
  - 没有commit判断binlog是否完整，完整就认为成功，否则binlog可能刷到从库数据不一致
- 通过配置每秒刷回/超出限制刷回/多线程提交刷回确保redo log 完整
- binlog可配置不同机制刷回
  - 不刷回
  - 事务提交刷回
  - 多个事务提交刷回

### 问题

你在什么时候会把线上生产库设置成“非双 1”。我目前知道的场景，有以下这些：

双1：

- binlog每个事务提交刷回磁盘
- redo log  事务提交直接持久化

- 不设置的场景 主要是数据量突然过大

  - 业务高峰

  - 备库恢复要赶上主库

  - 批量导入数据

  - ```mysql
    一般情况下，把生产库改成“非双 1”配置，是设置 innodb_flush_logs_at_trx_commit=2、sync_binlog=1000。
    ```

  - 

- 





## 24 | MySQL是怎么保证主备一致的？

> 在前面的文章中，我不止一次地和你提到了 binlog，大家知道 binlog 可以用来归档，也可以用来做主备同步，但它的内容是什么样的呢？为什么备库执行了 binlog 就可以跟主库保持一致了呢？今天我就正式地和你介绍一下它。
>
> 今天这篇文章我主要为你介绍主备的基本原理。理解了背后的设计原理，你也可以从业务开发的角度，来借鉴这些设计思想。

- binlog内容
- binlog主备同步 原理

### MySQL 主备的基本原理

**切换流程**

![](https://static001.geekbang.org/resource/image/fd/10/fd75a2b37ae6ca709b7f16fe060c2c10.png)

- 主备切换就是状态1到状态2 主备身份互换，客户端连接主库
- 问题：备库没有被直接访问为什么设置为只读
  - 防止运营数据查询误操作
  - 防止切换逻辑bug 双写导致数据不一致
  - 通过readonly来判断节点的角色
- 只读的备库如何与主库保持同步更新
  - readonly对超级权限无效，同步线程就是超级权限用户
- 节点 A 到 B 这条线的内部流程是什么样的
  - ![](https://static001.geekbang.org/resource/image/a6/a3/a66c154c1bc51e071dd2cc8c1d6ca6a3.png)
- 整体流程
  - 被踢change master设置主库ip 端口 用户名 密码 从哪个位置开始请求binlog(文件名+偏移)
  - 备库Bstart slave 启动IOthread+SQL thread
  - io线程与主库A建立连接，主库A校验身份后按照指定位置传送binlog数据给备库B
  - 备库拿到后写入relay log
  - sql thread读取relay log解析执行
- 

### binlog 的三种格式对比

- statement
  - 数据量少，函数出错 例如now
- row
  - 安全，数据量大
- mixed
  - 通常statement遇到无法准备描述就用row

```mysql

mysql> CREATE TABLE `t` (
  `id` int(11) NOT NULL,
  `a` int(11) DEFAULT NULL,
  `t_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `a` (`a`),
  KEY `t_modified`(`t_modified`)
) ENGINE=InnoDB;

insert into t values(1,1,'2018-11-13');
insert into t values(2,2,'2018-11-12');
insert into t values(3,3,'2018-11-11');
insert into t values(4,4,'2018-11-10');
insert into t values(5,5,'2018-11-09');
# 例如删除某条数据
mysql> delete from t /*comment*/  where a>=4 and t_modified<='2018-11-10' limit 1;
```

- 上述删除数据由于加了limit 1 因此多条符合的情况下只会删除一条

- 查看当前系统配置`show variables like '%binlog_format%'`结果是ROW

- 当 binlog_format=statement 时，binlog 里面记录的就是 SQL 语句的原文。你可以用`mysql> show binlog events in 'master.000001';`

- ![](https://static001.geekbang.org/resource/image/b9/31/b9818f73cd7d38a96ddcb75350b52931.png)

- 第三行就是真实执行的语句了。可以看到，在真实执行的 delete 命令之前，还有一个“use ‘test’”命令。这条命令不是我们主动执行的，而是 MySQL 根据当前要操作的表所在的数据库，自行添加的。这样做可以保证日志传到备库去执行的时候，不论当前的工作线程在哪个库里，都能够正确地更新到 test 库的表 t。use 'test’命令之后的 delete 语句，就是我们输入的 SQL 原文了。可以看到，binlog“忠实”地记录了 SQL 命令，甚至连注释也一并记录了。

- commit记录了xid

  - xid是验证redo log和binlog两边数据是否一致 主要为了crash-safe

  - 如果redo log两阶段在prepare值后发生崩溃则需通过XID检测binlog记录是否完整，完整（XID到达commit）则认为事务已提交

  - 数据刷回还是脏页刷回，redolog是让页 变成 脏页 同样脏页刷回磁盘保证数据一致性

  - statement的警告

    ![](https://static001.geekbang.org/resource/image/96/2b/96c2be9c0fcbff66883118526b26652b.png)

  - **statement可能导致主备数据不一致**

  - 如果这里索引选错，就可能导致删除另一条数据，或者函数调用也会出现这个问题

- row格式

  ![](https://static001.geekbang.org/resource/image/d6/26/d67a38db154afff610ae3bb64e266826.png)

  ![](https://static001.geekbang.org/resource/image/c3/c2/c342cf480d23b05d30a294b114cebfc2.png)

- row格式完整记录每条语句的变动，最后也有xid

### 为什么会有 mixed 格式的 binlog？

- 因为有些 statement 格式的 binlog 可能会导致主备不一致，所以要使用 row 格式。

- 但 row 格式的缺点是，很占空间。比如你用一个 delete 语句删掉 10 万行数据，用 statement 的话就是一个 SQL 语句被记录到 binlog 中，占用几十个字节的空间。但如果用 row 格式的 binlog，就要把这 10 万条记录都写到 binlog 中。这样做，不仅会占用更大的空间，同时写 binlog 也要耗费 IO 资源，影响执行速度。

- 所以，MySQL 就取了个折中方案，也就是有了 mixed 格式的 binlog。mixed 格式的意思是，MySQL 自己会判断这条 SQL 语句是否可能引起主备不一致，如果有可能，就用 row 格式，否则就用 statement 格式。

- 当然我要说的是，现在越来越多的场景要求把 MySQL 的 binlog 格式设置成 row。这么做的理由有很多，我来给你举一个可以直接看出来的好处：恢复数据。

  - 通过图 6 你可以看出来，即使我执行的是 delete 语句，row 格式的 binlog 也会把被删掉的行的整行信息保存起来。所以，如果你在执行完一条 delete 语句以后，发现删错数据了，可以直接把 binlog 中记录的 delete 语句转成 insert，把被错删的数据插入回去就可以恢复了。
  - 如果你是执行错了 insert 语句呢？那就更直接了。row 格式下，insert 语句的 binlog 里会记录所有的字段信息，这些信息可以用来精确定位刚刚被插入的那一行。这时，你直接把 insert 语句转成 delete 语句，删除掉这被误插入的一行数据就可以了。
  - 如果执行的是 update 语句的话，binlog 里面会记录修改前整行的数据和修改后的整行数据。所以，如果你误执行了 update 语句的话，只需要把这个 event 前后的两行信息对调一下，再去数据库里面执行，就能恢复这个更新操作了。
  - **insert/delete/update在row级别可以有反悔的余地**

- mixed的问题

  ```mysql
  
  mysql> insert into t values(10,10, now());
  ```

  

- 如果我们把 binlog 格式设置为 mixed，你觉得 MySQL 会把它记录为 row 格式还是 statement 格式呢？

  - 感觉像row因为now函数会获取当前导致不一致
  - ![](https://static001.geekbang.org/resource/image/01/ef/0150301698979255a6f27711c35e9eef.png)

- 接下来，我们再用 mysqlbinlog 工具来看看：

  - ![](https://static001.geekbang.org/resource/image/1a/41/1ad3a4c4b9a71955edba5195757dd041.png)

- 从图中的结果可以看到，原来 binlog 在记录 event 的时候，多记了一条命令：SET TIMESTAMP=1546103491。它用 SET TIMESTAMP 命令约定了接下来的 now() 函数的返回时间。

  - 前置函数来保证数据一致

- 用binlog恢复数据的正确做法：用mysqlbinlog工具解析，整个结果发给MySQL执行

  - ```mysql
    
    mysqlbinlog master.000001  --start-position=2738 --stop-position=2973 | mysql -h127.0.0.1 -P13000 -u$user -p$pwd;
    ```

  - 

- 

### 循环复制问题

通过上面对 MySQL 中 binlog 基本内容的理解，你现在可以知道，binlog 的特性确保了在备库执行相同的 binlog，可以得到与主库相同的状态。

![](https://static001.geekbang.org/resource/image/20/56/20ad4e163115198dc6cf372d5116c956.png)

- MySQL线上可能是两台机器互为主备，此时有一种场景：A insert 语句1 ，同步到B，Binsert 语句1 ，写入binlog又会同步回A，如果A执行则出现循环问题
- 从上面的图 6 中可以看到，MySQL 在 binlog 中记录了这个命令第一次执行时所在实例的 server id。因此，我们可以用下面的逻辑，来解决两个节点间的循环复制的问题：
  - 通过server id判断是否是自身来源，是自身来源就不执行从而解决循环复制问题
  - 按照这个逻辑，如果我们设置了双 M 结构，日志的执行流就会变成这样：
    - 从节点 A 更新的事务，binlog 里面记的都是 A 的 server id；
    - 传到节点 B 执行一次以后，节点 B 生成的 binlog 的 server id 也是 A 的 server id；
    - 再传回给节点 A，A 判断到这个 server id 与自己的相同，就不会再处理这个日志。所以，死循环在这里就断掉了。

### 小结

- 通过binlog实现主从数据同步，主机A数据更新同步记录binlog 主机B开启两个线程IO+SQL，IO去主机A拉取binlog并存入relay log，SQL 重放relay log达到数据一致
- binlog三种模式 
  - statement
    - 语句记录 节省空间
  - row
    - 行记录 浪费空间，但数据准确
  - mixed
    - 能语句记录就语句记录否则行记录
    - now()通过局部遍历+语句记录保证数据一致，索引选择不同的需要行记录
- 循环复制问题
  - AB互为主从，任何一个库的insert都会导致循环赋值，通过server id解决循环赋值，每个insert语句加上最开始库的server id

### 问题

什么情况下双 M 结构会出现循环复制。

- 主动修改server id
- 三M结构



## 25 | MySQL是怎么保证高可用的？

> 正常情况下，只要主库执行更新生成的所有 binlog，都可以传到备库并被正确地执行，备库就能达到跟主库一致的状态，这就是最终一致性。但是，MySQL 要提供高可用能力，只有最终一致性是不够的。为什么这么说呢？今天我就着重和你分析一下。

![](https://static001.geekbang.org/resource/image/89/cc/89290bbcf454ff9a3dc5de42a85a69cc.png)

### 主备延迟

主备延迟过高导致虽然最终一致但是体验很不好

主备切换可能是一个主动运维动作，比如软件升级、主库所在机器按计划下线等，也可能是被动操作，比如主库所在机器掉电。

**数据同步延迟**

- 主库 A 执行完成一个事务，写入 binlog，我们把这个时刻记为 T1;
- 之后传给备库 B，我们把备库 B 接收完这个 binlog 的时刻记为 T2;
- 备库 B 执行完成这个事务，我们把这个时刻记为 T3。

所谓主备延迟，就是同一个事务，在备库执行完成的时间和主库执行完成的时间之间的差值，也就是 T3-T1。

你可以在备库上执行 `show slave status` 命令，它的返回结果里面会显示 seconds_behind_master，用于表示当前备库延迟了多少秒。

**seconds_behind_master计算方法**

- 每个事务的 binlog 里面都有一个时间字段，用于记录主库上写入的时间；
- 备库取出当前正在执行的事务的时间字段的值，计算它与当前系统时间的差值，得到 seconds_behind_master。
- T3-T1 = seconds_behind_master 精度秒
- 机器时间不一致由从库获取与主库机器的延迟差值解决
- 网络正常情况下，主备延迟的主要来源是备库接收完 binlog 和执行完这个事务之间的时间差。 主要来源：T3-T2
- 

### 主备延迟的来源

- 当备库主机上的多个备库都在争抢资源的时候，就可能会导致主备延迟了。 性能问题
- 备库的压力大，备库执行的命令过量/命令自身问题导致性能问题
  - 由于主库直接影响业务，大家使用起来会比较克制，反而忽视了备库的压力控制。结果就是，备库上的查询耗费了大量的 CPU 资源，影响了同步速度，造成主备延迟。
  - 解决方案
    - 一主多从。除了备库外，可以多接几个从库，让这些从库来分担读的压力。
    - 通过 binlog 输出到外部系统，比如 Hadoop 这类系统，让外部系统提供统计类查询的能力。
- 大事务
  - 因为主库上必须等事务执行完成才会写入 binlog，再传给备库。所以，如果一个主库上的语句执行 10 分钟，那这个事务很可能就会导致从库延迟 10 分钟。
  - 大事务执行越久延迟越高
  - 不要一次性地用 delete 语句删除太多数据
- 大表DDL跟大事务类似 执行过久

由于主备延迟的存在，所以在主备切换的时候，就相应的有不同的策略。

### 可靠性优先策略

双M结构下的切换策略

- 备库是否压力不大
  - 判断备库 B 现在的 seconds_behind_master，如果小于某个值（比如 5 秒）继续下一步，否则持续重试这一步；
- 主库置为readonly
- 备库停止数据同步（两边数据完全一致保证可靠性
  - 判断备库 B 的 seconds_behind_master 的值，直到这个值变成 0 为止；
- 备库置为可读写 readonly=false
- 业务请求导向B

![](https://static001.geekbang.org/resource/image/54/4a/54f4c7c31e6f0f807c2ab77f78c8844a.png)

- 在这个不可用状态中，比较耗费时间的是步骤 3，可能需要耗费好几秒的时间。这也是为什么需要在步骤 1 先做判断，确保 seconds_behind_master 的值足够小。
  - 试想如果一开始主备延迟就长达 30 分钟，而不先做判断直接切换的话，系统的不可用时间就会长达 30 分钟，这种情况一般业务都是不可接受的。
- 

### 可用性优先策略

如果我强行把步骤 4、5 调整到最开始执行，也就是说不等主备数据同步，直接把连接切到备库 B，并且让备库 B 可以读写，那么系统几乎就没有不可用时间了。

我们把这个切换流程，暂时称作可用性优先流程。这个切换流程的代价，就是可能出现数据不一致的情况。

例子

```mysql

mysql> CREATE TABLE `t` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `c` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

insert into t(c) values(1),(2),(3);

insert into t(c) values(4);
insert into t(c) values(5);

```

假设，现在主库上其他的数据表有大量的更新，导致主备延迟达到 5 秒。在插入一条 c=4 的语句后，发起了主备切换。

下图  是可用性优先策略，且 binlog_format=mixed 时的切换流程和数据结果。

![](https://static001.geekbang.org/resource/image/37/3a/3786bd6ad37faa34aca25bf1a1d8af3a.png)

- 问题：两边都是statement 由于id自增导致记录不一致
- 具体步骤
  - 步骤 2 中，主库 A 执行完 insert 语句，插入了一行数据（4,4），之后开始进行主备切换。
  - 步骤 3 中，由于主备之间有 5 秒的延迟，所以备库 B 还没来得及应用“插入 c=4”这个中转日志，就开始接收客户端“插入 c=5”的命令。
  - 步骤 4 中，备库 B 插入了一行数据（4,5），并且把这个 binlog 发给主库 A。
  - 步骤 5 中，备库 B 执行“插入 c=4”这个中转日志，插入了一行数据（5,4）。而直接在备库 B 执行的“插入 c=5”这个语句，传到主库 A，就插入了一行新数据（5,5）。
- 可用性优先策略，但设置 binlog_format=row
  - 因为 row 格式在记录 binlog 的时候，会记录新插入的行的所有字段值，所以最后只会有一行不一致。而且，两边的主备同步的应用线程会报错 duplicate key error 并停止。也就是说，这种情况下，备库 B 的 (5,4) 和主库 A 的 (5,5) 这两行数据，都不会被对方执行。
- row级别更容易找到数据问题

有没有哪种情况数据的可用性优先级更高呢

- 可靠性不高可用性极高
- **但是常规要保证可靠性**
- 聊到这里你就知道了，在满足数据可靠性的前提下，MySQL 高可用系统的可用性，是依赖于主备延迟的。延迟的时间越小，在主库故障的时候，服务恢复需要的时间就越短，可用性就越高。

### 小结

- 主备同步存在延迟
- 延迟来源主要是语句执行可能是机器性能问题，语句问题，大事务，DDL
- 切换策略有可靠性优先和可用性优先，优先考虑可靠性

### 问题

备库的主备延迟会表现为一个 45 度的线段

备库的同步在这段时间完全被堵住了

- 大事务
- 备库长事务

## 26 | 备库为什么会延迟好几个小时？

> 在上一篇文章中，我和你介绍了几种可能导致备库延迟的原因。你会发现，这些场景里，不论是偶发性的查询压力，还是备份，对备库延迟的影响一般是分钟级的，而且在备库恢复正常以后都能够追上来。
>
> 但是，如果备库执行日志的速度持续低于主库生成日志的速度，那这个延迟就有可能成了小时级别。而且对于一个压力持续比较高的主库来说，备库很可能永远都追不上主库的节奏。这就涉及到今天我要给你介绍的话题：备库并行复制能力。

- 主备由于查询/事务 导致的延迟 几分钟级别若备库处理快可以追上
- 由于sql-thread的速度慢于 主库的事务写入导致备库延迟不断扩大

![](https://static001.geekbang.org/resource/image/1a/ef/1a85a3bac30a32438bfd8862e5a34eef.png)

在官方的 5.6 版本之前，MySQL 只支持单线程复制，由此在主库并发高、TPS 高时就会出现严重的主备延迟问题。

通过新的多线程模型解决主备延迟问题

![](https://static001.geekbang.org/resource/image/bc/45/bcf75aa3b0f496699fd7885426bc6245.png)

- coordinator是原来的sql thread只负责读
- worker是真正的更新线程
- 多线程问题
  - 多事务执行顺序
  - 事务中多update更新问题
    - 跨线程原子性问题
- 多线程要求
  - 更新同一行的事务在同一个worker
  - 同一个事务原子性保证都在同一个worker
- 通过并行增加sql-thread的备份速度但是带来多线程问题

### MySQL 5.5 版本的并行复制策略

> 官方 MySQL 5.5 版本是不支持并行复制的。但是，在 2012 年的时候，我自己服务的业务出现了严重的主备延迟，原因就是备库只有单线程复制。然后，我就先后写了两个版本的并行策略。
>
> 这里，我给你介绍一下这两个版本的并行策略，即按表分发策略和按行分发策略，以帮助你理解 MySQL 官方版本并行复制策略的迭代。

#### 按表分发策略

- 理论基础:**两个事务更新不同表 就可以并行**
- 当然，如果有跨表的事务，还是要把两张表放在一起考虑的。

![](https://static001.geekbang.org/resource/image/8b/76/8b6976fedd6e644022d4026581fb8d76.png)

每个worker维护hash表key="库名. 表名" value="有多少个事务在这个张表执行"

在有事务分配给 worker 时，事务里面涉及的表会被加到对应的 hash 表中。worker 执行完成后，这个表会被从 hash 表中去掉。

假设在图中的情况下，coordinator 从中转日志中读入一个新事务 T，这个事务修改的行涉及到表 t1 和 t3。

- T与w1冲突因为t1在被w1更新
- T与w2冲突
- 多余一个冲突 进入等待(要使用的表被其他多个线程使用 出现多线程更新问题)
- w2处理完成就之和w1冲突
- 事务给w1
- 读取下一个再继续

也就是说，每个事务在分发的时候，跟所有 worker 的冲突关系包括以下三种情况：

- 都不冲突 分配给最空闲的
- 和一个冲突 分配给冲突的worker
- 多余一个，等待只有一个worker冲突

#### 按行分发策略

要解决热点表的并行复制问题，就需要一个按行并行复制的方案。按行复制的核心思路是：如果两个事务没有更新相同的行，它们在备库上可以并行执行。显然，这个模式要求 binlog 格式必须是 row。

这时候的 key，就必须是“库名 + 表名 + 唯一键的值”。

因此，基于行的策略，事务 hash 表中还需要考虑唯一键，即 key 应该是“库名 + 表名 + 索引 a 的名字 +a 的值”。

- binlog 里面记录了整行的数据修改前各个字段的值，和修改后各个字段的值。
- 

因此，coordinator 在解析这个语句的 binlog 的时候，这个事务的 hash 表就有三个项:

- key=hash_func(db1+t1+“PRIMARY”+2), value=2; 这里 value=2 是因为修改前后的行 id 值不变，出现了两次。
- key=hash_func(db1+t1+“a”+2), value=1，表示会影响到这个表 a=2 的行。
- key=hash_func(db1+t1+“a”+1), value=1，表示会影响到这个表 a=1 的行。

可见，相比于按表并行分发策略，按行并行策略在决定线程分发的时候，需要消耗更多的计算资源。你可能也发现了，这两个方案其实都有一些约束条件：

- 必须是row，要解析主键 唯一索引 表名
- 表必须有主键
- 不能有外键，外键更新不记录 外键增加事务更新呢复杂性，业务层保证，无法记录log 无法使用此策略



**劣势**

- 耗内存 大事务
- 耗CPU 存在计算

**超出阈值变为串行**

### MySQL 5.6 版本的并行复制策略

- 按库并行
- hash表记录以库为单位
- 若业务逻辑拆分到不同的库中 且分布均匀这个策略很好
  - 只要库名 hash表占用空间小
  - 不要求binlog格式
- 缺陷
  - 所有表同一个库无法使用
  - 库之间热度不同无法使用
- 用的不多

### MariaDB 的并行复制策略

- 利用组提交思路
  - 同一组不会修改同一行
  - 主库可并行 备库也可并行
- 实现方案
  - 组提交事务同一个commit_id下一个id+1
  - commit_id写入binlog
  - 相同id分发到多个worker
  - 一组执行完再下一组
- 缺陷
  - 由于主库在写入binlog时还在执行其他事务
  - 导致备库原型的预期达不到，会因为大事务导致延迟
- 

### MySQL 5.7 的并行复制策略

由参数 slave-parallel-type 来控制并行复制策略：

- DATABASE 就是5.6
- 配置为 LOGICAL_CLOCK，表示的就是类似 MariaDB 的策略。不过，MySQL 5.7 这个策略，针对并行度做了优化。这个优化的思路也很有趣儿。

同时处理的事务可以并行么？

- 不行，里面有些加锁了
- 因此只要通过锁检测的就可以并行
- Maria 的策略：commit的事务可以并行

两阶段提交

![](https://static001.geekbang.org/resource/image/5a/28/5ae7d074c34bc5bd55c82781de670c28.png)

- 只要redo log prepare+binlog完整就可以

因此，MySQL 5.7 并行复制策略的思想是：

- 同时处于 prepare 状态的事务，在备库执行时是可以并行的；
- 处于 prepare 状态的事务，与处于 commit 状态的事务之间，在备库执行时也是可以并行的。

binlog 的组提交

- binlog_group_commit_sync_delay 参数，表示延迟多少微秒后才调用 fsync;
- binlog_group_commit_sync_no_delay_count 参数，表示累积多少次以后才调用 fsync。



- 通过组提交提高并行度，通过事务两阶段提交（事务提交）处理并行冲突
- 主库拉长延迟时间提高写入能力
- 备库拉长binlog延迟，提高数据同步能力
- 也就是说，这两个参数，既可以“故意”让主库提交得慢些，又可以让备库执行得快些。在 MySQL 5.7 处理备库延迟的时候，可以考虑调整这两个参数值，来达到提升备库复制并发度的目的。

### MySQL 5.7.22 的并行复制策略

相应地，新增了一个参数 binlog-transaction-dependency-tracking，用来控制是否启用这个新策略。这个参数的可选值有以下三种。

- COMMIT_ORDER，表示的就是前面介绍的，根据同时进入 prepare 和 commit 来判断是否可以并行的策略。
- WRITESET，表示的是对于事务涉及更新的每一行，计算出这一行的 hash 值，组成集合 writeset。如果两个事务没有操作相同的行，也就是说它们的 writeset 没有交集，就可以并行。
- WRITESET_SESSION，是在 WRITESET 的基础上多了一个约束，即在主库上同一个线程先后执行的两个事务，在备库执行的时候，要保证相同的先后顺序。

当然为了唯一标识，这个 hash 值是通过“库名 + 表名 + 索引名 + 值”计算出来的。如果一个表上除了有主键索引外，还有其他唯一索引，那么对于每个唯一索引，insert 语句对应的 writeset 就要多增加一个 hash 值。

优势

- writeset 是在主库生成后直接写入到 binlog 里面的，这样在备库执行的时候，不需要解析 binlog 内容（event 里的行数据），节省了很多计算量；
- 不需要把整个事务的 binlog 都扫一遍才能决定分发到哪个 worker，更省内存；
- 由于备库的分发策略不依赖于 binlog 内容，所以 binlog 是 statement 格式也是可以的。

缺陷

- 当然，对于“表上没主键”和“外键约束”的场景，WRITESET 策略也是没法并行的，也会暂时退化为单线程模型。

### 小结

- 由于备库单线程，主库多线程 导致速度不一致
- 从现象上看就是，备库上 seconds_behind_master 的值越来越大。

| 版本   | 策略             |
| ------ | ---------------- |
| 5.5    | 作者自身的 表/行 |
| 5.6    | 按库             |
| miara  | 按组             |
| 5.7    | 按组优化 prepare |
| 5.7.22 | 按组/按行        |

