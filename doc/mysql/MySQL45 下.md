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
  - commit_id不同导致无法按组
- WRITESET，表示的是对于事务涉及更新的每一行，计算出这一行的 hash 值，组成集合 writeset。如果两个事务没有操作相同的行，也就是说它们的 writeset 没有交集，就可以并行。
- WRITESET_SESSION，是在 WRITESET 的基础上多了一个约束，即在主库上同一个线程先后执行的两个事务，在备库执行的时候，要保证相同的先后顺序。**单线程退化问题**

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

### 问题

如果主库都是单线程压力模式，在从库追主库的过程中，binlog-transaction-dependency-tracking 应该选用什么参数？

- 这个问题的答案是，应该将这个参数设置为 WRITESET。
- 由于主库是单线程压力模式，所以每个事务的 commit_id 都不同，那么设置为 COMMIT_ORDER 模式的话，从库也只能单线程执行。
- 同样地，由于 WRITESET_SESSION 模式要求在备库应用日志的时候，同一个线程的日志必须与主库上执行的先后顺序相同，也会导致主库单线程压力模式下退化成单线程复制。



## 27 | 主库出问题了，从库怎么办？

- 切换主备

由于互联网大多数场景都是一主多从 说说这种结构下的主备切换

![](https://static001.geekbang.org/resource/image/aa/79/aadb3b956d1ffc13ac46515a7d619e79.png)

A和A'互为主备

若A宕机则BCD切换主库为A'

### 基于位点的主备切换

```mysql

CHANGE MASTER TO 
MASTER_HOST=$host_name 
MASTER_PORT=$port 
MASTER_USER=$user_name 
MASTER_PASSWORD=$password 
MASTER_LOG_FILE=$master_log_name 
MASTER_LOG_POS=$master_log_pos  
```

难点在于如何同步新的主库的日志位置 也就是`$master_log_name `和`$master_log_pos `

最后两个参数 MASTER_LOG_FILE 和 MASTER_LOG_POS 表示，要从主库的 master_log_name 文件的 master_log_pos 这个位置的日志继续同步。而这个位置就是我们所说的同步位点，也就是主库对应的文件名和日志偏移量。

- 若考虑到数据完整性可以考虑找一个较前的位点，防止数据丢失

一种完整性优先的方案

- 等到A‘把中转日志同步完成
- 在 A’上执行 show master status 命令，得到当前 A’上最新的 File 和 Position；
- 取原主库 A 故障的时刻 T；
- 用 mysqlbinlog 工具解析 A’的 File，得到 T 时刻的位点。
  - 通过日志一致+原库T找到故障的同步位置从而保证完整性

```mysql

mysqlbinlog File --stop-datetime=T --start-datetime=T
```

![](https://static001.geekbang.org/resource/image/34/dd/3471dfe4aebcccfaec0523a08cdd0ddd.png)

问题

- A’和其他只读库可能都从A接到过这个位点的记录并且已经执行

- 这时候，从库 B 的同步线程就会报告 Duplicate entry ‘id_of_R’ for key ‘PRIMARY’ 错误，提示出现了主键冲突，然后停止同步。

- 所以，通常情况下，我们在切换任务的时候，要先主动跳过这些错误，有两种常用的方法。

  - 跳过一个事务

    - ```mysql
      
      set global sql_slave_skip_counter=1;
      start slave;s
      ```

    - 

  - 跳过不影响数据的错误

    - ```mysql
      通过设置 slave_skip_errors 参数，直接设置跳过指定的错误。
      1062 错误是插入数据时唯一键冲突；1032 错误是删除数据时找不到行。
      ```

    - 

- 

### GTID

> 通过 sql_slave_skip_counter 跳过事务和通过 slave_skip_errors 忽略错误的方法，虽然都最终可以建立从库 B 和新主库 A’的主备关系，但这两种操作都很复杂，而且容易出错。所以，MySQL 5.6 版本引入了 GTID，彻底解决了这个困难。

- 通过GTID解决处理复杂可能出错的问题

- GTID 的全称是 Global Transaction Identifier，也就是全局事务 ID，是一个事务在提交的时候生成的，是这个事务的唯一标识。

- `GTID=server_uuid:gno`

  - server_uuid 是一个实例第一次启动时自动生成的，是一个全局唯一的值；
  - gno 是一个整数，初始值是 1，每次提交事务的时候分配给这个事务，并加 1。
    - 这个是提交才增加，普通MySQL事务ID是开启事务就自增，不管是否回滚

- GTID 模式的启动也很简单，我们只需要在启动一个 MySQL 实例的时候，加上参数 gtid_mode=on 和 enforce_gtid_consistency=on 就可以了。

- 如果 gtid_next=automatic，代表使用默认值。这时，MySQL 就会把 server_uuid:gno 分配给这个事务。a. 记录 binlog 的时候，先记录一行 SET @@SESSION.GTID_NEXT=‘server_uuid:gno’;b. 把这个 GTID 加入本实例的 GTID 集合。

  - binlog记录GTID

- 如果 gtid_next 是一个指定的 GTID 的值，比如通过 set gtid_next='current_gtid’指定为 current_gtid，那么就有两种可能：

  - 如果 current_gtid 已经存在于实例的 GTID 集合中，接下来执行的这个事务会直接被系统忽略；
  - 如果 current_gtid 没有存在于实例的 GTID 集合中，就将这个 current_gtid 分配给接下来要执行的事务，也就是说系统不需要给这个事务生成新的 GTID，因此 gno 也不用加 1。

- 这样，每个 MySQL 实例都维护了一个 GTID 集合，用来对应“这个实例执行过的所有事务”。

- 在备库想跳过被GTID标注的事务可通过空事务去处理

  - ```mysql
    
    set gtid_next='aaaaaaaa-cccc-dddd-eeee-ffffffffffff:10';
    begin;
    commit;
    set gtid_next=automatic;
    start slave;
    ```

  - 

- 

### 基于 GTID 的主备切换

```mysql

CHANGE MASTER TO 
MASTER_HOST=$host_name 
MASTER_PORT=$port 
MASTER_USER=$user_name 
MASTER_PASSWORD=$password 
master_auto_position=1 
```

我们把现在这个时刻，实例 A’的 GTID 集合记为 set_a，实例 B 的 GTID 集合记为 set_b。接下来，我们就看看现在的主备切换逻辑。

- 我们在实例 B 上执行 start slave 命令，取 binlog 的逻辑是这样的：
  - 建立连接发送set_b
  - A'找到没执行过的事务发给B去执行

其实，这个逻辑里面包含了一个设计思想：在基于 GTID 的主备关系里，系统认为只要建立主备关系，就必须保证主库发给备库的日志是完整的。因此，如果实例 B 需要的日志已经不存在，A’就拒绝把日志发给 B。

### GTID 和在线 DDL

之前在第 22 篇文章《MySQL 有哪些“饮鸩止渴”提高性能的方法？》中，我和你提到业务高峰期的慢查询性能问题时，分析到如果是由于索引缺失引起的性能问题，我们可以通过在线加索引来解决。但是，考虑到要避免新增索引对主库性能造成的影响，我们可以先在备库加索引，然后再切换。

- 通过备库加索引，主备切换 防止线上DDL出问题

- 评论区有位同学提出了一个问题：这样操作的话，数据库里面是加了索引，但是 binlog 并没有记录下这一个更新，是不是会导致数据和日志不一致？

- 假设，这两个互为主备关系的库还是实例 X 和实例 Y，且当前主库是 X，并且都打开了 GTID 模式。这时的主备切换流程可以变成下面这样：

  - 在实例 X 上执行 stop slave。

  - 在实例 Y 上执行 DDL 语句。注意，这里并不需要关闭 binlog。

  - 执行完成后，查出这个 DDL 语句对应的 GTID，并记为 server_uuid_of_Y:gno。

  - 到实例 X 上执行以下语句序列：

  - ```mysql
    到实例 X 上执行以下语句序列：
    
    set GTID_NEXT="server_uuid_of_Y:gno";
    begin;
    commit;
    set gtid_next=automatic;
    start slave;
    ```

  - 通过GTID的空事务解决不想处理的事务语句不执行

### 小结

- 如何主从切换
  - binlog file+pos
    - 困难，事具不准确
  - GTID基础
    - 通过提交事务ID确定位置
  - GTID使用
    - 开启GTID后通过GTID的交集确定同步位置解决同步不准确问题
  - 在线DDL
    - 通过空事务跳过GTID不想执行的语句



### 问题

如果一个新的从库接上主库，但是需要的 binlog 已经没了，要怎么做？

- 若允许不一致则从丢失后的开始同步
- 不允许不一致 重新建库
- 看看其他从库有没有完整数据
- 看看能不能找到binlog备份



## 28 | 读写分离有哪些坑？

- 主要会造成主从苏剧不同步的问题
- 即使MySQL提供主从并行减少不同步但实际上还是会存在

两种读写分离结构

![](https://static001.geekbang.org/resource/image/13/aa/1334b9c08b8fd837832fdb2d82e6b0aa.png)

客户端去了解读/写SQL的对应数据库

![](https://static001.geekbang.org/resource/image/1b/45/1b1ea74a48e1a16409e9b4d02172b945.jpg)

中间proxy架构 解耦，数据库的迁移友好

> 但是，不论使用哪种架构，你都会碰到我们今天要讨论的问题：由于主从可能存在延迟，客户端执行完一个更新事务后马上发起查询，如果查询选择的是从库的话，就有可能读到刚刚的事务更新之前的状态。

**这种“在从库上会读到系统的一个过期状态”的现象，在这篇文章里，我们暂且称之为“过期读”。**

这里，我先把文章中涉及到的处理过期读的方案汇总在这里，以帮助你更好地理解和掌握全文的知识脉络。这些方案包括：

- 强制走主库
- sleep
- 判断主备无延迟
- 配合semi-sync
- 等主库位点
- 等GTID

### 强制走主库方案

查询请求分类

- 必须拿到最新结果 例如商家发布商品
  - 打到主库
- 可以接受延迟 例如买家查询商品
  - 打给从库



### sleep方案

- 每次执行查询强制select sleep(1)
- 赌大多数情况主从延迟不会超过1S
- 不准确

### 判断主备无延迟方案

- 通过show slave status命令查看备库与主库的同步延迟参数`seconds_hehind_master`
- 若为0则代表无延迟就可以查询
- 但是单位是秒，需要更精确则要使用对比位点/GTID

![](https://static001.geekbang.org/resource/image/00/c1/00110923007513e865d7f43a124887c1.png)

通过上面截图可以看到读取master日志文件的位点

- Master_Log_File 和 Read_Master_Log_Pos，表示的是读到的主库的最新位点；
- Relay_Master_Log_File 和 Exec_Master_Log_Pos，表示的是备库执行的最新位点。
- 如果 Master_Log_File 和 Relay_Master_Log_File、Read_Master_Log_Pos 和 Exec_Master_Log_Pos 这两组值完全相同，就表示接收到的日志已经同步完成。

GTID方案

- Auto_Position=1 ，表示这对主备关系使用了 GTID 协议。
- Retrieved_Gtid_Set，是备库收到的所有日志的 GTID 集合；
- Executed_Gtid_Set，是备库所有已经执行完成的 GTID 集合。

**问题**

我们上面判断主备无延迟的逻辑，是“备库收到的日志都执行完成了”。但是，从 binlog 在主备之间状态的分析中，不难看出还有一部分日志，处于客户端已经收到提交确认，而备库还没收到日志的状态。

同步也有延迟

### 配合 semi-sync

semi-sync 做了这样的设计：

- 事务提交的时候，主库把 binlog 发给从库；
- 从库收到 binlog 以后，发回给主库一个 ack，表示收到了；
- 主库收到这个 ack 以后，才能给客户端返回“事务完成”的确认。
- 通过事务完成前先发给从库并用ack机制确认确保没数据延迟

主备同步不需要完全同步才能读取，只要读取时的GTID已经被备库执行即可

**问题**

- 一主多备环境下产生问题，一个备库返回ack就ack了
- 大量并发备库持续延迟，但是可通过GTID判断处理

### 等主库位点方案

主备延迟一个事务

![](https://static001.geekbang.org/resource/image/9c/09/9cf54f3e91dc8f7b8947d7d8e384aa09.png)

具体逻辑

- trx1 事务更新完成后，马上执行 show master status 得到当前主库执行到的 File 和 Position；

- 选定一个从库执行查询语句；

- 在从库上执行 select master_pos_wait(File, Position, 1)；

  - ```mysql
    
    select master_pos_wait(file, pos[, timeout]);
    它是在从库执行的；
    参数 file 和 pos 指的是主库上的文件名和位置；
    timeout 可选，设置为正整数 N 表示这个函数最多等待 N 秒。
    这个命令正常返回的结果是一个正整数 M，表示从命令开始执行，到应用完 file 和 pos 表示的 binlog 位置，执行了多少事务。
    ```

  - 

- 如果返回值是 >=0 的正整数，则在这个从库执行查询语句；

  - 从库执行完这个事务了？

- 否则，到主库执行查询语句。

- 

### GTID 方案

```mysql

 select wait_for_executed_gtid_set(gtid_set, 1);
```

- 等待，直到这个库执行的事务中包含传入的 gtid_set，返回 0；
- 超时返回 1。

在前面等位点的方案中，我们执行完事务后，还要主动去主库执行 show master status。而 MySQL 5.7.6 版本开始，允许在执行完更新类事务后，把这个事务的 GTID 返回给客户端，这样等 GTID 的方案就可以减少一次查询。

- 通过事务ID判断从库是否同步这个事务，同步执行完成则可以查询
- trx1 事务更新完成后，从返回包直接获取这个事务的 GTID，记为 gtid1；
- 选定一个从库执行查询语句；
- 在从库上执行 select wait_for_executed_gtid_set(gtid1, 1)；
- 如果返回值是 0，则在这个从库执行查询语句；这里会阻塞等待事务执行完成
- 否则，到主库执行查询语句。

### 小结

在今天这篇文章中，我跟你介绍了一主多从做读写分离时，可能碰到过期读的原因，以及几种应对的方案。

- 强制主库
  - 好的思路，区分延迟/实时
- sleep
  - 觉得1s大多数完成同步
- 主备延迟
  - 只判断备库是否执行完同步的事务 存在事务传输等问题
- semi-sync
  - ack机制确保事务给到备库，多备库问题/大量事务不一致问题
- 主库信息方案
  - 根据主库位点/GTID+备库信息确保事务执行完

方案混用

比如，先在客户端对请求做分类，区分哪些请求可以接受过期读，而哪些请求完全不能接受过期读；然后，对于不能接受过期读的语句，再使用等 GTID 或等位点的方案。

### 问题

如果使用 GTID 等位点的方案做读写分离，在对大表做 DDL 的时候会怎么样。

- 主库不断延时，导致备库后面的读都阻塞
- 假设，这条语句在主库上要执行 10 分钟，提交后传到备库就要 10 分钟（典型的大事务）。那么，在主库 DDL 之后再提交的事务的 GTID，去备库查的时候，就会等 10 分钟才出现。这样，这个读写分离机制在这 10 分钟之内都会超时，然后走主库。
- 这种预期内的操作，应该在业务低峰期的时候，确保主库能够支持所有业务查询，然后把读请求都切到主库，再在主库上做 DDL。等备库延迟追上以后，再把读请求切回备库。
- 使用 gh-ost 方案来解决这个问题也是不错的选择。
- 先在备库做也行 然后切位主库





## 29 | 如何判断一个数据库是不是出问题了？

> 我在第25和27篇文章中，和你介绍了主备切换流程。通过这些内容的讲解，你应该已经很清楚了：在一主一备的双 M 架构里，主备切换只需要把客户端流量切到备库；而在一主多从架构里，主备切换除了要把客户端流量切到备库外，还需要把从库接到新主库上。

- 切换的新主库必须数据同步完成保证数据一致性
- 多备库需要考虑各自数据同步进度和新主库的差距，然后在指定位置继续同步 GTID/POS 位点
- HA系统怎么判断主库出错？select 1

### select 1 判断

```mysql

set global innodb_thread_concurrency=3;

CREATE TABLE `t` (
  `id` int(11) NOT NULL,
  `c` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

 insert into t values(1,1)
```

- 设置`innodb_thread_concurrency`限制 模拟大并发场景下正在执行查询的线程数过多导致的整个库的阻塞和不可用

![](https://static001.geekbang.org/resource/image/35/55/35076dd3d0a0d44d22b76d2a29885255.png)

- 但是在这个场景下select 1却可以返回，与预期的库由于并发太高出现拒绝服务现象不符
-  这个是线程并发上限而不是链接上限，链接代表内存占用真正执行才是占用线程，通常情况下，我们建议把 innodb_thread_concurrency 设置为 64~128 之间的值
- **在线程进入锁等待以后，并发线程的计数会减一**
- 若线程A执行update并加行锁，其他线程都update这一行，若技术不断增加则整个系统不可用，即使A操作完成释放的动作也会被阻塞

![](https://static001.geekbang.org/resource/image/32/1d/3206ea18b8a24b546515b1b95dc4a11d.png)

### 查表判断

在系统库创建health_check表判断数据库是否健康

```mysql

mysql> select * from mysql.health_check; 
```

- 缺陷：只能判断读，若写场景出问题

### 更新判断

判断改为更新用timestamp

```mysql

mysql> update mysql.health_check set t_modified=now();

```

- 问题：主库同步可能导致数据不一致
- 解决：多记录更新
- 为了让主备之间的更新不产生冲突，我们可以在 mysql.health_check 表上存入多行数据，并用 A、B 的 server_id 做主键。



```mysql

mysql> CREATE TABLE `health_check` (
  `id` int(11) NOT NULL,
  `t_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

/* 检测命令 */
insert into mysql.health_check(id, t_modified) values (@@server_id, now()) on duplicate key update t_modified=now();
```

- 问题：发现问题满，数据库100%的IO并不代表挂了，还是 可能执行的update语句可能执行成功并在规定时间返回从而导致判断出错
- 上面所有方法都是从数据库外部发起检测导致各种问题

### 内部统计

其实，MySQL 5.6 版本以后提供的 performance_schema 库，就在 file_summary_by_event_name 表里统计了每次 IO 请求的时间。

file_summary_by_event_name 表里有很多行数据，我们先来看看 event_name='wait/io/file/innodb/innodb_log_file’这一行。

![](https://static001.geekbang.org/resource/image/75/dd/752ccfe43b4eab155be17401838c62dd.png)

- 查看innodb_log_file中执行语句的redolog IO等待时间
- 第一组五列，是所有 IO 类型的统计。其中，COUNT_STAR 是所有 IO 的总次数，接下来四列是具体的统计项， 单位是皮秒；前缀 SUM、MIN、AVG、MAX，顾名思义指的就是总和、最小值、平均值和最大值。
- 第二组六列，是读操作的统计。最后一列 SUM_NUMBER_OF_BYTES_READ 统计的是，总共从 redo log 里读了多少个字节。
- 第三组六列，统计的是写操作。
- 最后的第四组数据，是对其他类型数据的统计。在 redo log 里，你可以认为它们就是对 fsync 的统计。

因为我们每一次操作数据库，performance_schema 都需要额外地统计这些信息，所以我们打开这个统计功能是有性能损耗的。

- 开启内部方案损耗性能，全部开启损耗10%,只开启特定属性监控

如果要打开 redo log 的时间监控，你可以执行这个语句：

```mysql

mysql> update setup_instruments set ENABLED='YES', Timed='YES' where name like '%wait/io/file/innodb/innodb_log_file%';
```

**检测出错**

很简单，你可以通过 MAX_TIMER 的值来判断数据库是否出问题了。比如，你可以设定阈值，单次 IO 请求时间超过 200 毫秒属于异常，然后使用类似下面这条语句作为检测逻辑。

```mysql

mysql> select event_name,MAX_TIMER_WAIT  FROM performance_schema.file_summary_by_event_name where event_name in ('wait/io/file/innodb/innodb_log_file','wait/io/file/sql/binlog') and MAX_TIMER_WAIT>200*1000000000;
```

发现异常后，取到你需要的信息，再通过下面这条语句：

```mysql

mysql> truncate table performance_schema.file_summary_by_event_name;

```

把之前的统计信息清空。这样如果后面的监控中，再次出现这个异常，就可以加入监控累积值了。

### 小结

- MySQL可用性判断（是否IO以及链接处理是否正常）分为以下方案
  - 外部
    - select 1
    - 系统建表update
    - 多记录+server id update避免冲突
  - 内部
    - performance schema的特定行
- 从上往下性能消耗不断增加，从下往上则准确性不高需要权衡
- 推荐系统表update+内部判断 可靠性最高



### 问题

- 关于服务状态和服务质量的监控。其中，服务状态的监控，一般都可以用外部系统来实现；而服务的质量的监控，就要通过接口的响应时间来统计。
- 服务中使用了 healthCheck 来检测，其实跟我们文中提到的 select 1 的模式类似。
- 按照监控的对象，将监控分成了基础监控、服务监控和业务监控，并分享了每种监控需要关注的对象。

## 30 | 答疑文章（二）：用动态的观点看加锁

加锁原则、优化、bug

- 原则
  - 基本单位是next-key lock
  - 前开后闭
- 优化
  - 唯一索引退化为行锁
  - 等值索引最后一个取不到退化为间隙锁，也就是取不到的不加锁
- bug
  - 唯一索引会向右多扩展一些直到第一个不满足的

```mysql

CREATE TABLE `t` (
  `id` int(11) NOT NULL,
  `c` int(11) DEFAULT NULL,
  `d` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `c` (`c`)
) ENGINE=InnoDB;

insert into t values(0,0,0),(5,5,5),
(10,10,10),(15,15,15),(20,20,20),(25,25,25);
```

### 不等号条件里的等值查询

不等号和等值查询

```mysql

begin;
select * from t where id>9 and id<12 order by id desc for update;
```

- 由于order by id 从后向前
- 先找id<12 锁定(0,5] (5,10] (10,15),第三个锁定由于优化2 等值索引不满足退化为间隙锁
- 非等值查询但遍历的找通过等值判断

![](https://static001.geekbang.org/resource/image/ac/bb/ac1aa07860c565b907b32c5f75c4f2bb.png)

也就是说，在执行过程中，通过树搜索的方式定位记录的时候，用的是“等值查询”的方法。

### 等值查询的过程

```mysql

begin;
select id from t where c in(5,20,10) lock in share mode;
```

- 加锁范围

  - (0,5](5,10)  =>5
  - (5,10](10,15) 10
  - (15,20] (20,25) 20

- 加锁是一个一个加

- ```mysql
  
  select id from t where c in(5,20,10) order by c desc for update;
  ```

- desc 反向 加锁是20 10 5

- 死锁

- 范围查询是找到第一个合适的然后向另一边扩展？

### 怎么看死锁？

[具体详情](https://time.geekbang.org/column/article/78427)

执行 show engine innodb status 命令得到的部分输出

![](https://static001.geekbang.org/resource/image/a7/f6/a7dccb91bc17d12746703eb194775cf6.png)

- 这个结果分成三部分：
  - TRANSACTION，是第一个事务的信息；
  - TRANSACTION，是第二个事务的信息；
  - WE ROLL BACK TRANSACTION (1)，是最终的处理结果，表示回滚了第一个事务。
- 第一个事务的信息中：
  - WAITING FOR THIS LOCK TO BE GRANTED，表示的是这个事务在等待的锁信息；
  - index c of table `test`.`t`，说明在等的是表 t 的索引 c 上面的锁；
  - lock mode S waiting 表示这个语句要自己加一个读锁，当前的状态是等待中；
  - Record lock 说明这是一个记录锁；
  - n_fields 2 表示这个记录是两列，也就是字段 c 和主键字段 id；
    - 0: len 4; hex 0000000a; asc ;; 是第一个字段，也就是 c。值是十六进制 a，也就是 10；
    - 1: len 4; hex 0000000a; asc ;; 是第二个字段，也就是主键 id，值也是 10；
- 第二个事务显示的信息要多一些：
  - HOLDS THE LOCK(S)”用来显示这个事务持有哪些锁；
  - hex 0000000a 和 hex 00000014 表示这个事务持有 c=10 和 c=20 这两个记录锁
  - WAITING FOR THIS LOCK TO BE GRANTED，表示在等 (c=5,id=5) 这个记录锁。
- “lock in share mode”的这条语句，持有 c=5 的记录锁，在等 c=10 的锁；
- “for update”这个语句，持有 c=20 和 c=10 的记录锁，在等 c=5 的记录锁。
- 因此导致了死锁。这里，我们可以得到两个结论：

#### 结论

- 由于锁是一个个加的，要避免死锁，对同一组资源，要按照尽量相同的顺序访问；
  - 破坏循环等待
- 在发生死锁的时刻，for update 这条语句占有的资源更多，回滚成本更大，所以 InnoDB 选择了回滚成本更小的 lock in share mode 语句，来回滚。
  - 按照代价去掉死锁

### 怎么看锁等待？

![](https://static001.geekbang.org/resource/image/af/75/af3602b81aeb49e33577ba372d220a75.png)

现在我们一起看一下此时 show engine innodb status 的结果，看看能不能给我们一些提示。锁信息是在这个命令输出结果的 TRANSACTIONS 这一节。你可以在文稿中看到这张图片

![](https://static001.geekbang.org/resource/image/c3/a6/c3744fb7b61df2a5b45b8eb1f2a853a6.png)

- lock_mode X locks gap before rec insert intention waiting 这里有几个信息：
  - insert intention 表示当前线程准备插入一个记录，这是一个插入意向锁。为了便于理解，你可以认为它就是这个插入动作本身。
  - gap before rec 表示这是一个间隙锁，而不是记录锁。
- n_fields 5 也表示了，这一个记录有 5 列：
  - 0: len 4; hex 0000000f; asc ;; 第一列是主键 id 字段，十六进制 f 就是 id=15。所以，这时我们就知道了，这个间隙就是 id=15 之前的，因为 id=10 已经不存在了，它表示的就是 (5,15)。
  - 1: len 6; hex 000000000513; asc ;; 第二列是长度为 6 字节的事务 id，表示最后修改这一行的是 trx id 为 1299 的事务。
  - 2: len 7; hex b0000001250134; asc % 4;; 第三列长度为 7 字节的回滚段信息。可以看到，这里的 acs 后面有显示内容 (% 和 4)，这是因为刚好这个字节是可打印字符。
  - 后面两列是 c 和 d 的值，都是 15。
- 因此，我们就知道了，由于 delete 操作把 id=10 这一行删掉了，原来的两个间隙 (5,10)、(10,15）变成了一个 (5,15)。
  - session A 执行完 select 语句后，什么都没做，但它加锁的范围突然“变大”了；
  - 第 21 篇文章的课后思考题，当我们执行 select * from t where c>=15 and c<=20 order by c desc lock in share mode; 向左扫描到 c=10 的时候，要把 (5, 10]锁起来。
  - 
- **也就是说，所谓“间隙”，其实根本就是由“这个间隙右边的那个记录”定义的。**

### update 的例子

![](https://static001.geekbang.org/resource/image/61/a7/61c1ceea7b59201649c2514c9db864a7.png)

- sessionA的间隙被sessionB的第一个update破坏了 扩大到了c=5 由于间隙锁由右侧决定（锁定 (1,5]）
- 之后 session B 的第一个 update 语句，要把 c=5 改成 c=1，你可以理解为两步：
  - 插入 (c=1, id=5) 这个记录；
  - 删除 (c=5, id=5) 这个记录。
- 在delete的时候被锁住无法执行

### 小结

> 在我看来，每个想认真了解 MySQL 原理的同学，应该都要能够做到：通过 explain 的结果，就能够脑补出一个 SQL 语句的执行流程。达到这样的程度，才算是对索引组织表、索引、锁的概念有了比较清晰的认识。你同样也可以用这个方法，来验证自己对这些知识点的掌握程度。

- 说是不等号，底层还是在b+树上搜索，具体遍历到的范围应该根据生成的b+树的结构来确定
- 等值查询扩展到第一个无法达到的地方
- 死锁四个条件，两个线程持有各自的资源去申请对方持有的，可通过破坏循环等待减少死锁
- 锁等待从show engine innodb status看
- 也就是说，所谓“间隙”，其实根本就是由“这个间隙右边的那个记录”定义的。
- update=insert+delete

那么，一个空表有间隙吗？这个间隙是由谁定义的？你怎么验证这个结论呢？

- 有
- 先加锁
- 再insert

### 问题

**空表的间隙的定义**

一个空表就只有一个间隙。比如，在空表上执行：

- 但是间隙锁不一样，**跟间隙锁存在冲突关系的，是“往这个间隙中插入一个记录”这个操作。**间隙锁之间都不存在冲突关系。
- 通过间隙锁解决幻读问题？
- RR级别幻读问题，快照读解决了，当前读没解决

```mysql

begin;
select * from t where id>1 for update;
```

- 这个查询语句加锁的范围就是 next-key lock (-∞, supremum]。
- 



## 31 | 误删数据后除了跑路，还能怎么办？

- 由于binlog同步导致数据删除的操作也同步到其他备库进而导致整个集群的数据删除
- 为了找到解决误删数据的更高效的方法，我们需要先对和 MySQL 相关的误删数据，做下分类：
  - delete 删行
  - truncate/drop table 删表
  - drop database 删库
  - rm 命令删除整个MySQL实例
- 

### 误删行

- 利用flashback工具回滚即可
- 需要确保 binlog_format=row 和 binlog_row_image=FULL。
- 具体处理
  - 对于 insert 语句，对应的 binlog event 类型是 Write_rows event，把它改成 Delete_rows event 即可；
  - 同理，对于 delete 语句，也是将 Delete_rows event 改为 Write_rows event；
  - 而如果是 Update_rows 的话，binlog 里面记录了数据行修改前和修改后的值，对调这两行的位置即可。
- 如果误删数据涉及到了多个事务的话，需要将事务的顺序调过来再执行。
- 恢复数据比较安全的做法，是恢复出一个备份，或者找一个从库作为临时库，在这个临时库上执行这些操作，然后再将确认过的临时库的数据，恢复回主库。
- 当然，我们不止要说误删数据的事后处理办法，更重要是要做到事前预防。我有以下两个建议：
  - 把 sql_safe_updates 参数设置为 on。这样一来，如果我们忘记在 delete 或者 update 语句中写 where 条件，或者 where 条件里面没有包含索引字段的话，这条语句的执行就会报错。
  - 代码上线前，必须经过 SQL 审计。
- 使用 delete 命令删除的数据，你还可以用 Flashback 来恢复。而使用 truncate /drop table 和 drop database 命令删除的数据，就没办法通过 Flashback 来恢复了。
- 这是因为，即使我们配置了 binlog_format=row，执行这三个命令时，记录的 binlog 还是 statement 格式。binlog 里面就只有一个 truncate/drop 语句，这些信息是恢复不出数据的。

### 误删库 / 表

这种情况下，要想恢复数据，就需要使用全量备份，加增量日志的方式了。这个方案要求线上有定期的全量备份，并且实时备份 binlog。

![](https://static001.geekbang.org/resource/image/2f/db/2fafd0b75286e0163f432f85428ff8db.png)

- 恢复到最近的一次全量备份
- 增量恢复，除了错误的删除语句可通过修改binlog
- 重点
  - 为了加速数据恢复，如果这个临时库上有多个数据库，你可以在使用 mysqlbinlog 命令时，加上一个–database 参数，用来指定误删表所在的库。这样，就避免了在恢复数据时还要应用其他库日志的情况。
  - 没有GTID通过stop/start position跳过错误语句
  - GTID环境下`set gtid_next=gtid1;begin;commit;`跳过错误事务
- 加速方案
  - 全量恢复后，增加可作为从库去恢复
  - ﻿change replication filter replicate_do_table = (tbl_name) 命令，就可以让临时库只同步误操作的表；
- 不论是把 mysqlbinlog 工具解析出的 binlog 文件应用到临时库，还是把临时库接到备库上，这两个方案的共同点是：误删库或者表后，恢复数据的思路主要就是通过备份，再加上应用 binlog 的方式。

### 延迟复制备库

如果有非常核心的业务，不允许太长的恢复时间，我们可以考虑搭建延迟复制的备库。这个功能是 MySQL 5.6 版本引入的。

延迟复制的备库是一种特殊的备库，通过 CHANGE MASTER TO MASTER_DELAY = N 命令，可以指定这个备库持续保持跟主库有 N 秒的延迟。

- 比如你把 N 设置为 3600，这就代表了如果主库上有数据被误删了，并且在 1 小时内发现了这个误操作命令，这个命令就还没有在这个延迟复制的备库执行。这时候到这个备库上执行 stop slave，再通过之前介绍的方法，跳过误操作命令，就可以恢复出需要的数据。
- 通过主备一定延迟确保一定时间内的数据是对的解决主库突然的错误



### 预防误删库 / 表的方法

- 第一条建议是，账号分离。这样做的目的是，避免写错命令
- 制定操作规范。这样做的目的，是避免写错要删除的表名。
  - 删表前简单改变表名加个to_be_delete后缀观察效果
  - 运行一段时间没问题再处理

### rm 删除数据

其实，对于一个有高可用机制的 MySQL 集群来说，最不怕的就是 rm 删除数据了。只要不是恶意地把整个集群删除，而只是删掉了其中某一个节点的数据的话，HA 系统就会开始工作，选出一个新的主库，从而保证整个集群的正常工作。

- SA的自动化操作导致一整个机房下线
- 应对这种情况，我的建议只能是说尽量把你的备份跨机房，或者最好是跨城市保存。

### 小结

- 误删数据处理方案
  - delete 则flashback
  - truncate/drop table/drop table 并且超过一定时间只能通过binlog备份恢复
    - 全量恢复+增量同步
  - 可设定一个延迟复制备库在一定时间解决恢复慢问题 且保证更高的可靠性
  - 预防更重要，做好数据库权限控制，删表操作先逻辑删除再物理（加表后缀假装被删）
- 可以用 show grants 命令查看账户的权限，如果权限过大，可以建议 DBA 同学给你分配权限低一些的账号
- 你也可以评估业务的重要性，和 DBA 商量备份的周期、是否有必要创建延迟复制的备库等等。

### 误删数据的处理经验

- 运维的同学直接拷贝文本去执行，SQL 语句截断，导致数据库执行出错。
  - 不规范，提供脚本地址+md5校验
- @linhui0705 同学提到的“四个脚本”的方法，我非常推崇。这四个脚本分别是：备份脚本、执行脚本、验证脚本和回滚脚本。如果能够坚持做到，即使出现问题，也是可以很快恢复的，一定能降低出现故障的概率。
- 为了数据安全和服务稳定，多做点预防方案的设计讨论，总好过故障处理和事后复盘。方案设计讨论会和故障复盘会，这两种会议的会议室气氛完全不一样。经历过的同学一定懂的。

## 32 | 为什么还有kill不掉的语句？

主要因为kill的执行是一个过程，不是马上停止，并且线程锁等待必须被唤醒到可以执行kill的地方，一个线程多个埋点触发kill

> 在 MySQL 中有两个 kill 命令：一个是 kill query + 线程 id，表示终止这个线程中正在执行的语句；一个是 kill connection + 线程 id，这里 connection 可缺省，表示断开这个线程的连接，当然如果这个线程有语句正在执行，也是要先停止正在执行的语句的。

执行场景

- 执行查询
- 锁等待

### 收到 kill 以后，线程做什么？

![](https://static001.geekbang.org/resource/image/17/d0/17f88dc70c3fbe06a7738a0ac01db4d0.png)

例如释放MDL读锁

实现上，当用户执行 kill query thread_id_B 时，MySQL 里处理 kill 命令的线程做了两件事：

- 更改运行状态 把 session B 的运行状态改成 THD::KILL_QUERY(将变量 killed 赋值为 THD::KILL_QUERY)；
- 发送信号

具体含义

- 一个语句执行过程中有多处“埋点”，在这些“埋点”的地方判断线程状态，如果发现线程状态是 THD::KILL_QUERY，才开始进入语句终止逻辑；
- 如果处于等待状态，必须是一个可以被唤醒的等待，否则根本不会执行到“埋点”处；
- 语句从开始进入终止逻辑，到终止逻辑完全完成，是有一个过程的。

**kill 不掉的例子**

首先，执行 set global innodb_thread_concurrency=2，将 InnoDB 的并发线程上限数设置为 2；然后，执行下面的序列：

![](https://static001.geekbang.org/resource/image/32/6e/32e4341409fabfe271db3dd4c4df696e.png)

- 由于超出线程上下所以D被停止



session E 执行 kill connection 命令时，是这么做的

- 把 12 号线程状态设置为 KILL_CONNECTION；
- 关掉 12 号线程的网络连接。因为有这个操作，所以你会看到，这时候 session C 收到了断开连接的提示。



那为什么执行 show processlist 的时候，会看到 Command 列显示为 killed 呢？其实，这就是因为在执行 show processlist 的时候，有一个特别的逻辑：

**如果一个线程的状态是KILL_CONNECTION，就把Command列显示成Killed。**

两种执行kill延迟的情况

- 这个例子是 kill 无效的第一类情况，即：线程没有执行到判断线程状态的逻辑。
- 另一类情况是，终止逻辑耗时较长。
  - 大事务执行期间
  - 大查询回滚
  - DDL回滚

### 另外两个关于客户端的误解

- 第一个误解是：如果库里面的表特别多，连接就会很慢。
  - ![](https://static001.geekbang.org/resource/image/7e/83/7e4666bfd580505180c77447d1f44c83.png)
  - 连接等待
  - 但实际上，正如图中的文字提示所说的，当使用默认参数连接的时候，MySQL 客户端会提供一个本地库名和表名补全的功能。为了实现这个功能，客户端在连接成功后，需要多做一些操作：
    - 执行 show databases；
    - 切到 db1 库，执行 show tables；
    - 把这两个命令的结果用于构建一个本地的哈希表。
      - 在这些操作中，最花时间的就是第三步在本地构建哈希表的操作。所以，当一个库中的表个数非常多的时候，这一步就会花比较长的时间。
    - ，我们感知到的连接过程慢，其实并不是连接慢，也不是服务端慢，而是客户端慢。
- 其实提示里面没有说，除了加 -A 以外，加–quick(或者简写为 -q) 参数，也可以跳过这个阶段。但是，这个–quick 是一个更容易引起误会的参数，也是关于客户端常见的一个误解。
  - quick让客户端更快 跳过自动补全+本地开启缓存
  - 跳过自动补全
  - 不会消耗过多本地内存
  - 不会记录本地文件

### 小结

- kill之后还需做额外处理
  - 这些“kill 不掉”的情况，其实是因为发送 kill 命令的客户端，并没有强行停止目标线程的执行，而只是设置了个状态，并唤醒对应的线程。而被 kill 的线程，需要执行到判断状态的“埋点”，才会开始进入终止逻辑阶段。并且，终止逻辑本身也是需要耗费时间的。
- 由于线程超出限制/kill操作逻辑多 都会造成延迟现象看上去像kill不掉
- 客户端建立连接做补全hash记录导致慢，quick还关掉本地缓存导致慢

### 问题

一个事务被 kill 之后，持续处于回滚状态，从恢复速度的角度看，你是应该重启等它执行结束，还是应该强行重启整个 MySQL 进程。

- 为了数据完整性考虑应该等它执行结束
- 为了恢复速度可以重启，因为重启后由于WAL+redo log + double write  buffer 可以完整的恢复，但是这个恢复时间就拉长了，可能还是等待执行完成更好

## 33 | 我查这么多数据，会不会把数据库内存打爆？

> 我经常会被问到这样一个问题：我的主机内存只有 100G，现在要对一个 200G 的大表做全表扫描，会不会把数据库主机的内存用光了？

### 全表扫描对 server 层的影响

- 与JavaIO一样借助缓冲区进行数据读写，避免全量数据载入内存把内存打爆

- ```mysql
  
  mysql -h$host -P$port -u$user -p$pwd -e "select * from db1.t" > $target_file
  ```

- 实际上，服务端并不需要保存一个完整的结果集。取数据和发数据的流程是这样的：

  - 获取一行，写到 net_buffer 中。这块内存的大小是由参数 net_buffer_length 定义的，默认是 16k。
    - 读取数据写入缓存
  - 重复获取行，直到 net_buffer 写满，调用网络接口发出去。
    - 缓存写满请求发送
  - 如果发送成功，就清空 net_buffer，然后继续取下一行，并写入 net_buffer。
    - 成功就循环
  - 如果发送函数返回 EAGAIN 或 WSAEWOULDBLOCK，就表示本地网络栈（socket send buffer）写满了，进入等待。直到网络栈重新可写，再继续发送。
    - 网络栈写满等待空闲再继续

  

- 也就是说，MySQL 是“边读边发的”，这个概念很重要。这就意味着，如果客户端接收得慢，会导致 MySQL 服务端由于结果发不出去，这个事务的执行时间变长。

![](https://static001.geekbang.org/resource/image/a0/bd/a027c300d7dde8cea4fad8f34b670ebd.jpg)

**观察数据**

show processlist

![](https://static001.geekbang.org/resource/image/18/c3/183a704d4495bebbc13c524695b5b6c3.png)

如果你看到 State 的值一直处于“Sending to client”，就表示服务器端的网络栈写满了。

我在上一篇文章中曾提到，如果客户端使用–quick 参数，会使用 mysql_use_result 方法。这个方法是读一行处理一行。你可以想象一下，假设有一个业务的逻辑比较复杂，每读一行数据以后要处理的逻辑如果很慢，就会导致客户端要过很久才会去取下一行数据，可能就会出现如图 2 所示的这种情况。

- -quick可在客户端处理慢的清空下加速发送
- 因此，对于正常的线上业务来说，如果一个查询的返回结果不会很多的话，我都建议你使用 mysql_store_result 这个接口，直接把查询结果保存到本地内存。
- 也就是说，“Sending data”并不一定是指“正在发送数据”，而可能是处于执行器过程中的任意阶段。比如，你可以构造一个锁等待的场景，就能看到 Sending data 状态。
  - sending to client 数据读取慢
  - sending data 任意时刻慢，比如等待锁
- 

### 全表扫描对 InnoDB 的影响

- 由于WAL机制+insert buffer 机制导致内存中的数据就是最新的，查询命中数据页在内存中就直接使用，log确保刷回的正确性 也考虑到了异常情况
- 全表扫描导致内存热点页被大量替换 造成缓存命中率降低
- 查看缓存命中率
  - show engine innodb status
  - ![](https://static001.geekbang.org/resource/image/c7/2e/c70a95ee99826812c292c46de508982e.png)
- InnoDB 内存管理用的是最近最少使用 (Least Recently Used, LRU) 算法，这个算法的核心就是淘汰最久未使用的数据。

**LRU的优化**

- 原先LRU模型在大量读取的时候会把内存中的热点页全部刷出若全表扫描后又要正常业务逻辑处理导致缓存命中率急剧下降
- 优化：
- ![](https://static001.geekbang.org/resource/image/21/28/21f64a6799645b1410ed40d016139828.png)
- 5：3划分缓冲区为young old
- 改进LRU执行流程
  - 在young区的被访问与正常LRU算法一样访问到的放到头部
  - 访问不存在的依旧是淘汰到tail，但是新进来的存入lru_old的head而不是young的head
  - 访问在old区域的数据做如下判断
    - 如果新的数据在LRU old中超过1S就代表没在全表刷新，放到young 头部
    - 小于1s则放在原地，可能访问到正在全表扫描中的数据
  - 
- 



### 小结

- 全表扫描由于server借助缓冲区查询所以不会吧内存打爆，只会占用缓冲区大小的内存 ，Java解决buffer做数据的转换也是这个原因
- innodb通过优化过的LRU算法保证在全表扫描时避免大量数据刷新队缓存命中率的降低

由于 MySQL 采用的是边算边发的逻辑，因此对于数据量很大的查询结果来说，不会在 server 端保存完整的结果集。所以，如果客户端读结果不及时，会堵住 MySQL 的查询过程，但是不会把内存打爆。

而对于 InnoDB 引擎内部，由于有淘汰策略，大查询也不会导致内存暴涨。并且，由于 InnoDB 对 LRU 算法做了改进，冷数据的全表扫描，对 Buffer Pool 的影响也能做到可控。

### 问题

如果客户端由于压力过大，迟迟不能接收数据，会对服务端造成什么严重的影响。

这个问题的核心是，造成了“长事务”。

- 客户端读取慢也代表处理慢那么持有的锁就不会释放产生长事务导致锁不被释放

至于长事务的影响，就要结合我们前面文章中提到的锁、MVCC 的知识点了。

- 如果前面的语句有更新，意味着它们在占用着行锁，会导致别的语句更新被锁住；
- 当然读的事务也有问题，就是会导致 undo log 不能被回收，导致回滚段空间膨胀。



## 34 | 到底可不可以使用join？

两个问题

- 使用join有什么问题
- 两张不同大小的表join 哪张表是驱动表

```mysql

CREATE TABLE `t2` (
  `id` int(11) NOT NULL,
  `a` int(11) DEFAULT NULL,
  `b` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `a` (`a`)
) ENGINE=InnoDB;

drop procedure idata;
delimiter ;;
create procedure idata()
begin
  declare i int;
  set i=1;
  while(i<=1000)do
    insert into t2 values(i, i, i);
    set i=i+1;
  end while;
end;;
delimiter ;
call idata();

create table t1 like t2;
insert into t1 (select * from t2 where id<=100)
```

### Index Nested-Loop Join

```mysql

select * from t1 straight_join t2 on (t1.a=t2.a);

```

![](https://static001.geekbang.org/resource/image/4b/90/4b9cb0e0b83618e01c9bfde44a0ea990.png)

- straight_join 固定连接，t1是驱动表t2是被驱动表
- explain 看到被驱动表的扫描用到了索引
- 具体流程
  - 从t1读取R
  - R行中取出字段a去t2查找
  - t2找到合r组成一行作为结果集
  - 重复
- 上述流程类似双重循环，并且用得到索引
- ![](https://static001.geekbang.org/resource/image/d8/f6/d83ad1cbd6118603be795b26d38f8df6.jpg)
- 流程分析
  - A表全表扫描100行（次）
  - B索引扫描100次 由于B索引特殊11对应所以扫描100行
  - 一共两百次（行）
- **若不用join的多次单独读取增加了与数据库的交互时间效率更低**
- 怎么选择驱动表，驱动表全表扫描被驱动表走树搜索
  - 被驱动表搜索两次 普通索引和主键索引查具体数据每次搜索一棵树近似复杂度是以 2 为底的 M 的对数，记为 log2M 所以在被驱动表上查一行的时间复杂度是 2*log2M。
  - 因此整个执行过程，近似复杂度是 N + N*2*log2M。
- 结论
  - join语句比单个查询更好
  - join语句小表作为驱动表
    - 这个结论的前提是“可以使用被驱动表的索引”。
- 

### Simple Nested-Loop Join

```mysql

select * from t1 straight_join t2 on (t1.a=t2.b);
```

- 上述语句用不到t2索引 那么扫描总共是100*1000
- 着就是Simple Nested-Loop Join 笨重

### Block Nested-Loop Join

- 与simple对比多了t1数据入内存的步骤加速扫描
  - 把表 t1 的数据读入线程内存 join_buffer 中，由于我们这个语句中写的是 select *，因此是把整个表 t1 放入了内存；
  - 扫描表 t2，把表 t2 中的每一行取出来，跟 join_buffer 中的数据做对比，满足 join 条件的，作为结果集的一部分返回。
- ![](https://static001.geekbang.org/resource/image/15/73/15ae4f17c46bf71e8349a8f2ef70d573.jpg)
- ![](https://static001.geekbang.org/resource/image/67/e1/676921fa0883e9463dd34fb2bc5e87e1.png)
- 小表/大表效率相同但空间复杂度不同
- 若表太大使用join_buffer ， join_buffer 的大小是由参数 join_buffer_size 设定的，默认值是 256k。如果放不下表 t1 的所有数据话，策略很简单，就是分段放。我把 join_buffer_size 改成 1200
- 变更
  - 载入t1一定大小数据入内存
  - t2匹配 入结果集
  - 清空内存 取下一段继续
- ![](https://static001.geekbang.org/resource/image/69/c4/695adf810fcdb07e393467bcfd2f6ac4.jpg)
- 可以看到，这时候由于表 t1 被分成了两次放入 join_buffer 中，导致表 t2 会被扫描两次。虽然分成两次放入 join_buffer，但是判断等值条件的次数还是不变的，依然是 (88+12)*1000=10 万次。
- 所以，在这个算法的执行过程中：
  - 扫描行数是 N+λ*N*M；
  - 内存判断 N*M 次。

### 小结

能不能用join

- 如果可以使用 Index Nested-Loop Join 算法，也就是说可以用上被驱动表上的索引，其实是没问题的；
- 如果使用 Block Nested-Loop Join 算法，扫描行数就会过多。尤其是在大表上的 join 操作，这样可能要扫描被驱动表很多次，会占用大量的系统资源。所以这种 join 尽量不要用。

第二个问题是：如果要使用 join，应该选择大表做驱动表还是选择小表做驱动表？

- 如果是 Index Nested-Loop Join 算法，应该选择小表做驱动表；
- 如果是 Block Nested-Loop Join 算法：
  - 在 join_buffer_size 足够大的时候，是一样的；
  - 在 join_buffer_size 不够大的时候（这种情况更常见），应该选择小表做驱动表。
  - 所以，这个问题的结论就是，总是应该使用小表做驱动表。

**小表定义**

```mysql

select * from t1 straight_join t2 on (t1.b=t2.b) where t2.id<=50;
select * from t2 straight_join t1 on (t1.b=t2.b) where t2.id<=50;
```

- b关联用不到索引
- t2只有前50行 t2是小标

```mysql

select t1.b,t2.* from  t1  straight_join t2 on (t1.b=t2.b) where t2.id<=100;
select t1.b,t2.* from  t2  straight_join t1 on (t1.b=t2.b) where t2.id<=100;
```

- t1在查询次数相同情况下查询字段更少
- 所以，更准确地说，在决定哪个表做驱动表的时候，应该是两个表按照各自的条件过滤，过滤完成之后，计算参与 join 的各个字段的总数据量，数据量小的那个表，就是“小表”，应该作为驱动表。



默认join buffer size 256K 数据库为1M



## 35 | join语句怎么优化？

join语句的算法

- Index Nested-Loop Join(NLJ)
  - 走索引内部类似双重循环
- Block Nested-Loop Join(BNL)
  - 不走索引，分组载入内存

但是，BNL 算法在大表 join 的时候性能就差多了，比较次数等于两个表参与 join 的行数的乘积，很消耗 CPU 资源。

- 特殊情况：NLJ索引字段大量垃圾数据导致NLJ慢于BNL



```mysql

create table t1(id int primary key, a int, b int, index(a));
create table t2 like t1;
drop procedure idata;
delimiter ;;
create procedure idata()
begin
  declare i int;
  set i=1;
  while(i<=1000)do
    insert into t1 values(i, 1001-i, i);
    set i=i+1;
  end while;
  
  set i=1;
  while(i<=1000000)do
    insert into t2 values(i, i, i);
    set i=i+1;
  end while;

end;;
delimiter ;
call idata();
```

为了便于后面量化说明，我在表 t1 里，插入了 1000 行数据，每一行的 a=1001-id 的值。也就是说，表 t1 中字段 a 是逆序的。同时，我在表 t2 中插入了 100 万行数据

### Multi-Range Read 优化

- Multi-Range Read 优化 (MRR)。这个优化的主要目的是尽量使用顺序读盘。
- 回表：非聚簇索引查询后需要根据id回表查询具体数据入结果集
- ![](https://static001.geekbang.org/resource/image/97/05/97ae269061192f6d7a632df56fa03605.png)
- 顺序按行查询 效率低 因为a对应的主键id乱序
  - 因为大多数的数据都是按照主键递增顺序插入得到的，所以我们可以认为，如果按照主键的递增顺序查询的话，对磁盘的读比较接近顺序读，能够提升读性能。
  - 主键顺序查询接近顺序读尽可能避免磁盘随机读 提高效率
- MRR优化思路
  - 根据索引 a，定位到满足条件的记录，将 id 值放入 read_rnd_buffer 中 ;
  - 将 read_rnd_buffer 中的 id 进行递增排序；
  - 排序后的 id 数组，依次到主键 id 索引中查记录，并作为结果返回。
- 这里，read_rnd_buffer 的大小是由 read_rnd_buffer_size 参数控制的。如果步骤 1 中，read_rnd_buffer 放满了，就会先执行完步骤 2 和 3，然后清空 read_rnd_buffer。之后继续找索引 a 的下个记录，并继续循环。
- 另外需要说明的是，如果你想要稳定地使用 MRR 优化的话，需要设置set optimizer_switch="mrr_cost_based=off"。（官方文档的说法，是现在的优化器策略，判断消耗的时候，会更倾向于不使用 MRR，把 mrr_cost_based 设置为 off，就是固定使用 MRR 了。）
- MRR：通过缓冲+排序 优化回表过程
- MRR流程
  - ![](https://static001.geekbang.org/resource/image/d5/c7/d502fbaea7cac6f815c626b078da86c7.jpg)
- ![](https://static001.geekbang.org/resource/image/a5/32/a513d07ebaf1ae044d44391c89bc6432.png)
- **MRR 能够提升性能的核心在于，这条查询语句在索引 a 上做的是一个范围查询（也就是说，这是一个多值查询），可以得到足够多的主键 id。这样通过排序以后，再去主键索引查数据，才能体现出“顺序性”的优势。**



### Batched Key Access

![NLJ](https://static001.geekbang.org/resource/image/10/3d/10e14e8b9691ac6337d457172b641a3d.jpg)

- NLJ 算法执行的逻辑是：从驱动表 t1，一行行地取出 a 的值，再到被驱动表 t2 去做 join。也就是说，对于表 t2 来说，每次都是匹配一个值。这时，MRR 的优势就用不上了。

- 借助MRR思路优化join=》BKA 驱动表查询放入缓存排序后批量去被驱动表查询加快速度 避免磁盘随机访问

- 通过上一篇文章，我们知道 join_buffer 在 BNL 算法里的作用，是暂存驱动表的数据。但是在 NLJ 算法里并没有用。那么，我们刚好就可以复用 join_buffer 到 BKA 算法中。

- 优化后：

- ![](https://static001.geekbang.org/resource/image/68/88/682370c5640244fa3474d26cc3bc0388.png)

- 使用BKA 你需要在执行 SQL 语句之前，先设置：

  - ```mysql
    
    set optimizer_switch='mrr=on,mrr_cost_based=off,batched_key_access=on';
    ```

  - 

- 其中，前两个参数的作用是要启用 MRR。这么做的原因是，BKA 算法的优化要依赖于 MRR。

### BNL 算法的性能问题

- ​	BNL下对冷数据大表做join的问题
  - IO问题多次join
  - 内存buffer 污染问题，多次对同一张大表 若大表数据量超过buffer的3/8 也就是超过old区域则进入young区
    - 导致正常业务无法进入young
    - 由于优化机制的存在，一个正常访问的数据页，要进入 young 区域，需要隔 1 秒后再次被访问到。但是，由于我们的 join 语句在循环读磁盘和淘汰内存页，进入 old 区域的数据页，很可能在 1 秒之内就被淘汰了。这样，就会导致这个 MySQL 实例的 Buffer Pool 在这段时间内，young 区域的数据页没有被合理地淘汰。
      - old区的正常业务数据无法留存
      - young区的冷数据没有被淘汰
- 大表 join 操作虽然对 IO 有影响，但是在语句执行结束后，对 IO 的影响也就结束了。但是，对 Buffer Pool 的影响就是持续性的，需要依靠后续的查询请求慢慢恢复内存命中率。
- 为了减少这种影响，你可以考虑增大 join_buffer_size 的值，减少对被驱动表的扫描次数
- BNL算法的影响
  - 扫描被驱动表IO
  - 对比CPU资源
  - buffer poll 热数据被淘汰
- 我们执行语句之前，需要通过理论分析和查看 explain 结果的方式，确认是否要使用 BNL 算法。如果确认优化器会使用 BNL 算法，就需要做优化。优化的常见做法是，给被驱动表的 join 字段加上索引，把 BNL 算法转成 BKA 算法。

### BNL 转 BKA

- 一些情况下，我们可以直接在被驱动表上建索引，这时就可以直接转成 BKA 算法了。

- 但是，有时候你确实会碰到一些不适合在被驱动表上建索引的情况。比如下面这个语句：

  - ```mysql
    
    select * from t1 join t2 on (t1.b=t2.b) where t2.b>=1 and t2.b<=2000;
    ```

  - 我们在文章开始的时候，在表 t2 中插入了 100 万行数据，但是经过 where 条件过滤后，需要参与 join 的只有 2000 行数据。如果这条语句同时是一个低频的 SQL 语句，那么再为这个语句在表 t2 的字段 b 上创建一个索引就很浪费了。

    - 低频 小范围的查询 不划算

  - 

- 通过where预估结果行数，遵循以下规则：

  - 如果where里没有相应表的筛选条件，无论on里是否有相关条件，默认为全表
  - 如果where里有筛选条件，但是不能使用索引来筛选，那么默认为全表
  - 如果where里有筛选条件，而且可以使用索引，那么会根据索引来预估返回的记录行数

- 不走索引 的BNL算法

  - 获取t1 存入join_buffer
  - 扫描t2对比 合适就条件下推 判断where后的 符合进入结果集，不符合跳过
  - 总计遍历1000*100W = 10 亿

- 优化套路：

  - 使用索引

  - 使用临时表，就是先查t2放入临时表，再给临时表加索引 再join关联查询

    - ```mysql
      
      create temporary table temp_t(id int primary key, a int, b int, index(b))engine=innodb;
      insert into temp_t select * from t2 where b>=1 and b<=2000;
      select * from t1 join temp_t on (t1.b=temp_t.b);
      ```

    - 

- 总体来看，不论是在**原表上加索引**，还是用有**索引的临时表**，我们的思路都是让 join 语句能够用上被驱动表上的索引，来触发 BKA 算法，提升查询性能。

### 扩展 -hash join

这，也正是 MySQL 的优化器和执行器一直被诟病的一个原因：不支持哈希 join。并且，MySQL 官方的 roadmap，也是迟迟没有把这个优化排上议程。

- 100可以改为hash的话更快
- 也可以全部到业务端，在业务端借助hash匹配

### 小结

MySQL的join查询根据走索引/不走索引分为两种

- Nested-Loop Join
  - 走索引
- Block Nested Loop Join
  - 不走索引， 利用join buffer 加快访问

join优化

- 走索引的NLJ 
  - 使用batch key access 用上join buffer +排序 批量排序后去内存对比 尽量规避随机访问加快速度
- 不走索引的BNL
  - 最快最有效就是BNL加上索引变为NLJ
  - 数据量不大可考虑在客户端查出自己实现匹配达到优化

今天，我和你分享了 Index Nested-Loop Join（NLJ）和 Block Nested-Loop Join（BNL）的优化方法。

- BKA 优化是 MySQL 已经内置支持的，建议你默认使用；
- BNL 算法效率低，建议你都尽量转成 BKA 算法。优化的方向就是给被驱动表的关联字段加上索引；
- 基于临时表的改进方案，对于能够提前过滤出小数据的 join 语句来说，效果还是很好的；
- MySQL 目前的版本还不支持 hash join，但你可以配合应用端自己模拟出来，理论上效果要好于临时表的方案。



## 40 | insert语句的锁为什么这么多？

- MySQL 对自增主键锁做了优化，尽量在申请到自增 id 以后，就释放自增锁。
- 普通insert语句轻量，不会占用过多资源
- 但是有些insert依赖select导致资源占用过多，因为select使用不当会加锁

### insert … select 语句

```mysql

CREATE TABLE `t` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `c` int(11) DEFAULT NULL,
  `d` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `c` (`c`)
) ENGINE=InnoDB;

insert into t values(null, 1,1);
insert into t values(null, 2,2);
insert into t values(null, 3,3);
insert into t values(null, 4,4);

create table t2 like t

insert into t2(c,d) select c,d from t;
```

- 上述insert为什么需要对表t行和间隙加锁？

数据一致性

![](https://static001.geekbang.org/resource/image/33/86/33e513ee55d5700dc67f32bcdafb9386.png)

- 若B在A之后执行完成则B的语义被扩大了，所以在B执行的时候需要加锁
- 锁定索引，前开后闭

### insert 循环写入

- insert select锁定需要锁定的资源

```mysql

insert into t2(c,d)  (select c+1, d from t force index(c) order by c desc limit 1);

```

- 这个语句的加锁范围，就是表 t 索引 c 上的 (3,4]和 (4,supremum]这两个 next-key lock，以及主键索引上 id=4 这一行。
  - 强制索引c limit1 找到4 锁定(4,supermum]
  - 继续向前扩展一次，由于limit1 停止 因此锁定(3,4]

**自查询+写入**

```mysql

insert into t(c,d)  (select c+1, d from t force index(c) order by c desc limit 1);

```

- 观察效果

- 慢查询日志

  - ![](https://static001.geekbang.org/resource/image/6f/18/6f90b04c09188bff11dae6e788abb918.png)

- explain

  - ![](https://static001.geekbang.org/resource/image/d7/2a/d7270781ee3f216325b73bd53999b82a.png)

- Innodb_rows_read

  - ![](https://static001.geekbang.org/resource/image/48/d7/489281d8029e8f60979cb7c4494010d7.png)

- explain会受到limit 1的影响导致不准确

- 自查询用到了临时表，并且全表扫描放入了临时表，这是为了避免数据不一致，避免在insert的时候其他语句对表做修改

- 内存表优化

  - ```mysql
    
    create temporary table temp_t(c int,d int) engine=memory;
    insert into temp_t  (select c+1, d from t force index(c) order by c desc limit 1);
    insert into t select * from temp_t;
    drop table temp_t;
    ```

  - 

- 

### insert 唯一键冲突

- 唯一键冲突加读锁 且 主键/唯一键冲突都是加next-key lock+读锁

**死锁场景**

![](https://static001.geekbang.org/resource/image/63/2d/63658eb26e7a03b49f123fceed94cd2d.png)

- session A持有 c=5的锁
- 在T3释放 BC冲突加了读锁，需要申请c=5的锁，BC互相持有读锁，无法释放导致死锁

![](https://static001.geekbang.org/resource/image/3e/b8/3e0bf1a1241931c14360e73fd10032b8.jpg)

### insert into … on duplicate key update

- insert into … on duplicate key update 这个语义的逻辑是，插入一行数据，如果碰到唯一键约束，就执行后面的更新语句。
  - 注意，如果有多个列违反了唯一性约束，就会按照索引的顺序，修改跟第一个索引冲突的行。
  - 语义是遇到唯一冲突就更新，更新只更新一行，优先主键索引
- 需要注意的是，执行这条语句的 affected rows 返回的是 2，很容易造成误解。实际上，真正更新的只有一行，只是在代码实现上，insert 和 update 都认为自己成功了，update 计数加了 1， insert 计数也加了 1。

![](https://static001.geekbang.org/resource/image/5f/02/5f384d6671c87a60e1ec7e490447d702.png)

### 小结

- insert..select 不同表复制，加next-key lock 
- insert循环写入 需要引入临时表来加快否则全表扫描加锁
- insert唯一冲突会加共享的next-key lock 死锁检测后及时回滚事务

