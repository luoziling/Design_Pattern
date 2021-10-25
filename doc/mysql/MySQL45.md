# MySQL45 上

## 开篇

- 高频
- 事务
- 索引
- 锁

![](https://static001.geekbang.org/resource/image/b7/c2/b736f37014d28199c2457a67ed669bc2.jpg)

## 01 | 基础架构：一条SQL查询语句是如何执行的？

>看一个事儿千万不要直接陷入细节里，你应该先鸟瞰其全貌，这样能够帮助你从高维度理解问题

```mysql
mysql> select * from T where ID=10；
```

**基础架构**

![](https://static001.geekbang.org/resource/image/0d/d9/0d2070e8f84c4801adbfa03bda1f98d9.png)

- server
  - 连接器
    - 与客户端建立通信
  - 查询缓存
    - 查询缓存，命中直接返回
  - 分析器
    - 词法、语法 分析
  - 优化器
    - 生成执行计划，索引选择
  - 执行器
    - 操作引擎，返回结果
- 存储引擎
  - 存储数据，插拔式，接口

### 连接器

- 功能

  - 建立连接
  - 获取权限
  - 维持和管理连接

- 登录

  - ```mysql
    mysql -h$ip -P$port -u$user -p
    ```

  - mysql代表客户端工具

- 建立连接时获取一次权限，之后的权限都依赖此次获取的

  - 权限修改后需要重新登录，权限缓存，类似shiro的处理

- 查看连接状态

  - `show processlist`
    - ![](https://static001.geekbang.org/resource/image/f2/ed/f2da4aa3a672d48ec05df97b9f992fed.png)
  - 太长没动静 连接自动端开 wait_timeout

- 长连接和短连接

  - 长连接 不用重新建立连接 但是内存占用无法释放，内存占用过多可能导致OOM 浪费内存资源
  - 短连接 经常重新建立连接浪费CPU资源
  - 建议
    - 长连接 定期断开重连
    - 通过执行 mysql_reset_connection 来重新初始化连接资源。
  - 

- 

### 查询缓存

- 建立连接后查询缓存
- KV形式，自适应hash索引？ 跟自适应hash索引步匹配，类似mybatis缓存，但仍然存在较大缺陷
- 缓存命中直接返回
- 不命中则继续执行，查到后存储KV
- 缓存危害
  - 只适用读多写少的场景
  - update操作清空查询缓存
- 按需使用 query
  - 将参数 query_cache_type 设置成 DEMAND
  - `select SQL_CACHE * from T where ID=10；`
- 8.0后不支持查询缓存

### 分析器

- 词法分析

  - 解析SQL语句内容，识别出字符串分别代表什么

- 语法分析

  - 判断语法是否符合规范

  - 出错`You have an error in your SQL syntax`比如下面这个语句 select 少打了开头的字母“s”。

  - MySQL 从你输入的"select"这个关键字识别出来，这是一个查询语句。它也要把字符串“T”识别成“表名 T”，把字符串“ID”识别成“列 ID”。

  - ```mysql
    
    mysql> elect * from t where ID=1;
    
    ERROR 1064 (42000): You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'elect * from t where ID=1' at line 1
    ```

  - 

- 一般语法错误会提示第一个出现错误的位置，所以你要关注的是紧接“use near”的内容。

### 优化器

- 优化处理

  - 决定索引选择

  - 决定join顺序

  - 例子

    - ```mysql
      mysql> select * from t1 join t2 using(ID)  where t1.c=10 and t2.d=20;
      ```

    - 执行顺序不同，结果相同，效率不同

- 

### 执行器

- 执行优化后的执行计划
- 流程
  - 判断是否有权限
  - 判断是否有索引 分别进行全表扫描/索引扫描
  - 搜索找到合适的数据加入结果集
  - 结果返回客户端
- 你会在数据库的慢查询日志中看到一个 rows_examined 的字段，表示这个语句执行过程中扫描了多少行。这个值就是在执行器每次调用引擎获取数据行的时候累加的。
- 在有些场景下，执行器调用一次，在引擎内部则扫描了多行，因此引擎扫描行数跟 rows_examined 并不是完全相同的。我们后面会专门有一篇文章来讲存储引擎的内部机制，里面会有详细的说明。



### 小结

- 在分析器检测语法是否有问题

## 02 | 日志系统：一条SQL更新语句是如何执行的？

- 查询和更新一样 都会经过 连接器 分析器 优化器 执行器 最后到达存储引擎

- MySQL 可以恢复到半个月内任意一秒的状态 怎么做到的

- 例子

  - ```mysql
    mysql> create table T(ID int primary key, c int);
    mysql> update T set c=c+1 where ID=2;
    ```

  - 更新操作使查询缓存失效

  - 词法/语法分析直到这是一条更新语句，优化器选择合适索引

  - 需要额外了解日志redo log/undo log

  - redo log

    - 崩溃恢复

  - binlog

    - 数据备份 主从复制

  - undo log

    - 事务回滚

- 

### 重要的日志模块：redo log

- 粉板和账本
  - 而粉板和账本配合的整个过程，其实就是 MySQL 里经常说到的 WAL 技术，WAL 的全称是 Write-Ahead Logging，它的关键点就是先写日志，再写磁盘，也就是先写粉板，等不忙的时候再写账本。
- 冷热数据分离
- redo log执行流程
  - 更新记录先放到redo log,在不忙的时候刷回binlog
  - 若redo log记录满了，将一部分记录刷回磁盘上的binlog再记录redo log
- 优势
  - 避免每次都刷回binlog造成额外性能浪费，减少IO
- redo log 底层
  - ![](https://static001.geekbang.org/resource/image/16/a7/16a7950217b3f0f4ed02db5db59562a7.png)
  - 环形缓存 由多块环形缓存构成 两个定位 **checkpoint** 和 **writepos** 分别代表**擦除位置**和当**前记录位置**，
- 作用
  - 防止异常重启的数据丢失 => **crash-safe**
  - 需借助double write buffer保证页的安全
- 

### 重要的日志模块：binlog

- 两种log的区别

  - binlog是server层
  - redolog是存储引擎层，属于innodb特有以实现crash-safe,而MYISAM不支持

- 更新语句执行流程

  - 连接、分析、优化准备好执行
  - 根据索引找到数据在哪页，数据不再内存中从磁盘读取放到内存
  - 执行器拿到数据 执行更新，调用存储引擎写入新数据
  - 更新到内存时同时更新到redo log 并且置为prepare,可随时提交
  - 生成binlog，写入磁盘
  - 提交事务，把redo log改为commit 更新完成
  - 流程图 图中浅色框表示是在 InnoDB 内部执行的，深色框表示是在执行器中执行的

  ![](https://static001.geekbang.org/resource/image/2e/be/2e5bff4910ec189fe1ee6e2ecc7b4bbe.png)

- 

### 两阶段提交

- 两阶段提交为了报纸日志逻辑一致
- 通过binlog 让MySQL数据库可随时恢复
- 备份系统定期备份 一天/一周 一次备份
- 恢复先找到对应binlog写入临时库再写回线上库
- redo log 和 binlog相互独立两个写非原子，可能导致一个写完数据库crash 另一个日志错误
  - redo log写完crash 回复时少了数据（从binlog恢复
  - binlog写完crash 事务未提交，无效事务导致多了数据
- 

### 小结

- 逻辑日志binlog和物理日志redo log
  - 记录修改逻辑
  - 记录具体数据
- redo log 用于保证 crash-safe 能力。innodb_flush_log_at_trx_commit 这个参数设置成 1 的时候，表示每次事务的 redo log 都直接持久化到磁盘。这个参数我建议你设置成 1，这样可以保证 MySQL 异常重启之后数据不丢失。
  - 事务提交马上同步日志
- **两阶段提交是跨系统维持数据逻辑一致性时常用的一个方案**
  - ack机制
- 那么在什么场景下，一天一备会比一周一备更有优势呢？或者说，它影响了这个数据库系统的哪个指标？
  - 影响redo log大小
  - 看数据库修改的记录量而定
- 

## 03 | 事务隔离：为什么你改了我还看不见？

- 事务ACID，一串操作要么都成功要么都失败
- 事务在存储引擎层实现，Innodb支持myisam不支持
- innodb支持redo log更安全，myisam不支持

### 隔离性与隔离级别

- 解决的问题
  - 多个事务同时执行带来的
    - 脏读
      - 读到另一个事务未提交的数据
    - 不可重复读
      - 一个事务内两次读取不一致，由于修改导致
    - 幻读
      - 一个事务内两次读取不一致，由增删导致

![](https://static001.geekbang.org/resource/image/7d/f8/7dea45932a6b722eb069d2264d0066f8.png)

- 我们来看看在不同的隔离级别下，事务 A 会有哪些不同的返回结果，也就是图里面 V1、V2、V3 的返回值分别是什么。
- 若隔离级别是“读未提交”， 则 V1 的值就是 2。这时候事务 B 虽然还没有提交，但是结果已经被 A 看到了。因此，V2、V3 也都是 2。
- 若隔离级别是“读提交”，则 V1 是 1，V2 的值是 2。事务 B 的更新在提交后才能被 A 看到。所以， V3 的值也是 2。
- 若隔离级别是“可重复读”，则 V1、V2 是 1，V3 是 2。之所以 V2 还是 1，遵循的就是这个要求：事务在执行期间看到的数据前后必须是一致的。
- 若隔离级别是“串行化”，则在事务 B 执行“将 1 改成 2”的时候，会被锁住。直到事务 A 提交后，事务 B 才可以继续执行。所以从 A 的角度看， V1、V2 值是 1，V3 的值是 2。





- Oracle默认读提交
- MySQL默认repeatable read
- 配置
  - 配置的方式是，将启动参数 transaction-isolation 的值设置成 READ-COMMITTED。你可以用 show variables 来查看当前的值。
  - 
- 可重复读场景
  - 一段时间内对数据做统计，避免统计时数据修改导致的变动

### 事务隔离的实现

- 通过redo log  实现可重复读，事务回滚 以将数据恢复到合适的状态
- 长事务
  - 导致redo log过长占用空间过多
  - 占用锁资源
- 事务启动
  - 总是使用set autocommit=1
- 你可以在 information_schema 库的 innodb_trx 这个表中查询长事务，比如下面这个语句，用于查找持续时间超过 60s 的事务。



### 小结

- 长事务避免
  - 减小事务范围
  - 监控innodb_trx
- 

## 04 | 深入浅出索引（上）

- 为了提高数据查询效率，类似书的目录

### 索引的常见模型

- 常见搜索效率的数据结构
  - hash
    - O（1）
    - hash冲突 链地址法
    - 不适用范围查询
  - 有序数组
    - 支持范围查询
    - 无重复 递增 身份证号
    - 二分搜索
    - 更新麻烦 挪动
    - 静态存储引擎
  - 搜索树
    - 二叉搜索树
    - 平衡二叉树
    - 索引在内存/磁盘
    - 层次过高
    - 经常读磁盘
    - 减少磁盘IO
    - n叉树 1200  树高 4 存储1200^3 17亿
    - 
- 

### InnoDB 的索引模型

- 聚簇索引 B+树 主键聚簇
- 主键索引=聚簇索引=叶子=值
- 非主键索引需要回调

### 索引维护

- 数据维护
  - 增加可能造成页分裂 数据移动 影响性能
  - 删除=》页合并
- 自增主键
  - 性能，不会触发页分裂
  - 空间，主键长度小非叶子节点占用空间小
- 特殊场景
  - KV
  - 只有一个索引 且唯一
- 

### 小结

主键索引变更

alter table T engine=InnoDB

## 05 | 深入浅出索引（下）

![](https://static001.geekbang.org/resource/image/dc/8d/dcda101051f28502bd5c4402b292e38d.png)

- 索引执行流程
  - 查k=3 徽标 R3
  - 查k=5 回表 R4
  - 查询k=6 不满足跳出
- 三条记录两次回表

### 覆盖索引

- select后字段与索引一致
  - 不需要回表
- 由于覆盖索引可以减少树的搜索次数，显著提升查询性能，所以使用覆盖索引是一个常用的性能优化手段。
- 引擎找3次但server只认为2
- 身份中号+名字
  - 覆盖索引
- 

### 最左前缀原则

- 在建立联合索引的时候，如何安排索引内的字段顺序。
- 第一原则是，如果通过调整顺序，可以少维护一个索引，那么这个顺序往往就是需要优先考虑采用的。
- 区分度高的放在前面
- age单个索引

### 索引下推

- 最左前缀可以用于在索引中定位记录。这时，你可能要问，那些不符合最左前缀的部分，会怎么样呢
  - ![](https://static001.geekbang.org/resource/image/b3/ac/b32aa8b1f75611e0759e52f5915539ac.jpg)
- 只有满足条件才回表查找

### 小结

在满足语句需求的情况下， 尽量少地访问资源是数据库设计的重要原则之一

- order by后的索引顺序
- 若order by后满足主键排序则不需要索引例如去掉的ca

## 06 | 全局锁和表锁 ：给表加个字段怎么有这么多阻碍？

- [锁](https://zhuanlan.zhihu.com/p/29150809)=》处理并发访问问题
- 锁存在于server+存储引擎
- 行锁只存在于存储引擎层
  - Myisam只有表锁，并且读写串行从而并发度更低，可通过降低写操作优先级缓解饿死现象，一次申请所有资源，破坏请求和保持从而避免死锁
  - 事务中LOCK IN SHARE MODE 给select加共享锁
  - FOR UPDATE加排他锁
- INNODB的锁实现
  - 给索引项加锁，意味着不走索引就用不了行锁
  - 普通索引多个索引项一致会产生索引冲突
  - 间隙锁 next-key lock对于范围查询的加锁对于不存在的记录也会锁定就是间隙锁
  - MySQL 的恢复机制要求：在一个事务未提交前，其他并发事务不能插入满足其锁定条件的任何记录，也就是不允许出现幻读。
- 根据加锁的范围，MySQL 里面的锁大致可以分成全局锁、表级锁和行锁三类。
- 全局锁
  - Flush tables with read lock (FTWRL)
  - 全局锁的典型使用场景是，做全库逻辑备份。也就是把整库每个表都 select 出来存成文本。
  - 业务停摆/主从延迟
  - 粒度太大
  - 无锁
    - 导致数据不一致
  - 一致性视图
    - 可重复读开启事务
  - 官方自带的逻辑备份工具是 mysqldump。当 mysqldump 使用参数–single-transaction 的时候，导数据之前就会启动一个事务，来确保拿到一致性视图。而由于 MVCC 的支持，这个过程中数据是可以正常更新的。
  - mysqldump 通过开启事务防止数据异常 所以放在RR
    - 适用于事务的表
  - set global readonly=true FTWRL 自动释放
- 表锁
- 行锁

### 表级锁

- 表锁的语法是 lock tables … read/write

- lock tables 语法除了会限制别的线程的读写外，也限定了本线程接下来的操作对象。

- 另一类表级的锁是 MDL（metadata lock)

  - MDL 不需要显式使用，在访问一个表的时候会被自动加上。MDL 的作用是，保证读写的正确性。你可以想象一下，如果一个查询正在遍历一个表中的数据，而执行期间另一个线程对这个表结构做变更，删了一列，那么查询线程拿到的结果跟表结构对不上，肯定是不行的。

- 给一个小表加个字段，导致整个库挂了。

  - 所有增删改查都需要MDL读锁
  - alter操作导致全局读写阻塞

- 如何安全地给小表加字段？

  - 解决长事务

    - 暂停DDL/kill长事务

  - 热点表

    - 不能kill
    - alter table设置等待事件，防止锁阻塞

  - ```mysql
    ALTER TABLE tbl_name NOWAIT add column ...
    ALTER TABLE tbl_name WAIT N add column ... 
    ```

  - 

- 

### 小结

要么是你的系统现在还在用 MyISAM 这类不支持事务的引擎，那要安排升级换引擎；要么是你的引擎升级了，但是代码还没升级。我见过这样的情况，最后业务开发就是把 lock tables 和 unlock tables 改成 begin 和 commit，问题就解决了。

- MDL 会直到事务提交才释放，在做表结构变更的时候，你一定要小心不要导致锁住线上查询和更新。
- 主库加一列 备库如何处理
  - 确保RR
  - 得到一致性视图
  - 设置保存点
  - 如果在 Q4 语句执行之前到达，现象：没有影响，备份拿到的是 DDL 后的表结构。
  - 如果在“时刻 2”到达，则表结构被改过，Q5 执行的时候，报 Table definition has changed, please retry transaction，现象：mysqldump 终止；
  - 如果在“时刻 2”和“时刻 3”之间到达，mysqldump 占着 t1 的 MDL 读锁，binlog 被阻塞，现象：主从延迟，直到 Q6 执行完成。
  - 从“时刻 4”开始，mysqldump 释放了 MDL 读锁，现象：没有影响，备份拿到的是 DDL 前的表结构。

## 07 | 行锁功过：怎么减少行锁对性能的影响？

- 存储引擎实现 myisam不支持行锁
- 两阶段锁
- **在 InnoDB 事务中，行锁是在需要的时候才加上的，但并不是不需要了就立刻释放，而是要等到事务结束时才释放。这个就是两阶段锁协议**。
- 通过逻辑+两阶段锁 影院账户两次update+插入 ,先insert+update可提高并发度
- 

### 死锁和死锁检测

![](https://static001.geekbang.org/resource/image/4d/52/4d0eeec7b136371b79248a0aed005a52.jpg)

- 两个事务持有各自行锁 需求对方行锁
- 处理
  - 一种策略是，直接进入等待，直到超时。这个超时时间可以通过参数 innodb_lock_wait_timeout 来设置。
    - 破坏请求和保持
  - 另一种策略是，发起死锁检测，发现死锁后，主动回滚死锁链条中的某一个事务，让其他事务得以继续执行。将参数 innodb_deadlock_detect 设置为 on，表示开启这个逻辑。
    - 破坏不可剥夺
- 在 InnoDB 中，innodb_lock_wait_timeout 的默认值是 50s
- 根据上面的分析，我们来讨论一下，怎么解决由这种热点行更新导致的性能问题呢？问题的症结在于，死锁检测要耗费大量的 CPU 资源。
  - 一种头痛医头的方法，就是如果你能确保这个业务一定不会出现死锁，可以临时把死锁检测关掉
  - 另一个思路是控制并发度。根据上面的分析，你会发现如果并发能够控制住，比如同一行同时最多只有 10 个线程在更新，那么死锁检测的成本很低，就不会出现这个问题。
  - 因此，这个并发控制要做在数据库服务端。如果你有中间件，可以考虑在中间件实现；如果你的团队有能修改 MySQL 源码的人，也可以做在 MySQL 里面。基本思路就是，对于相同行的更新，在进入引擎之前排队。
- 如果团队里暂时没有数据库方面的专家，不能实现这样的方案，能不能从设计上优化这个问题呢？
  - 你可以考虑通过将一行改成逻辑上的多行来减少锁冲突。还是以影院账户为例，可以考虑放在多条记录上，比如 10 个记录，影院的账户总额等于这 10 个记录的值的总和。这样每次要给影院账户加金额的时候，随机选其中一条记录来加。这样每次冲突概率变成原来的 1/10，可以减少锁等待个数，也就减少了死锁检测的 CPU 消耗。
- 

### 小结

其中，我以两阶段协议为起点，和你一起讨论了在开发的时候如何安排正确的事务语句。这里的原则 / 我给你的建议是：如果你的事务中需要锁多个行，要把最可能造成锁冲突、最可能影响并发度的锁的申请时机尽量往后放。

最后，我给你留下一个问题吧。如果你要删除一个表里面的前 10000 行数据，有以下三种方法可以做到：

- 第一种，直接执行 delete from T limit 10000;
  - 长事务
- 第二种，在一个连接中循环执行 20 次 delete from T limit 500;
- 第三种，在 20 个连接中同时执行 delete from T limit 500。
  - 人为锁冲突概率提高

## 08 | 事务到底是隔离的还是不隔离的？

- 也就是说，一个在可重复读隔离级别下执行的事务，好像与世无争，不受外界影响。
- 那么等到这个事务自己获取到行锁要更新数据的时候，它读到的值又是什么呢？
  - 行锁与MVCC/RR的冲突
- **begin/start transaction 命令并不是一个事务的起点，在执行到它们之后的第一个操作 InnoDB 表的语句，事务才真正启动**。
- 在 MySQL 里，有两个“视图”的概念：
  - 一个是 view。它是一个用查询语句定义的虚拟表，在调用的时候执行查询语句并生成结果。创建视图的语法是 create view … ，而它的查询方法与表一样。
  - 另一个是 InnoDB 在实现 MVCC 时用到的一致性读视图，即 consistent read view，用于支持 RC（Read Committed，读提交）和 RR（Repeatable Read，可重复读）隔离级别的实现。
  - 它没有物理结构，作用是事务执行期间用来定义“我能看到什么数据”。
- 

### “快照”在 MVCC 里是怎么工作的？

- 在可重复读隔离级别下，事务在启动的时候就“拍了个快照”。注意，这个快照是基于整库的。
- InnoDB 里面每个事务有一个唯一的事务 ID，叫作 transaction id。它是在事务开始的时候向 InnoDB 的事务系统申请的，是按申请顺序严格递增的。
- 也就是说，数据表中的一行记录，其实可能有多个版本 (row)，每个版本有自己的 row trx_id。
- ![](https://static001.geekbang.org/resource/image/68/ed/68d08d277a6f7926a41cc5541d3dfced.png)
- 你可能会问，前面的文章不是说，语句更新会生成 undo log（回滚日志）吗？那么，undo log 在哪呢
  - 实际上，图 2 中的三个虚线箭头，就是 undo log；而 V1、V2、V3 并不是物理上真实存在的，而是每次需要的时候根据当前版本和 undo log 计算出来的。比如，需要 V2 的时候，就是通过 V4 依次执行 U3、U2 算出来。
- 因此，一个事务只需要在启动的时候声明说，“以我启动的时刻为准，如果一个数据版本是在我启动之前生成的，就认；如果是我启动以后才生成的，我就不认，我必须要找到它的上一个版本”。
- 数组里面事务 ID 的最小值记为低水位，当前系统里面已经创建过的事务 ID 的最大值加 1 记为高水位。
- 这个视图数组和高水位，就组成了当前事务的一致性视图（read-view）。
- 而数据版本的可见性规则，就是基于数据的 row trx_id 和这个一致性视图的对比结果得到的。
- ![](https://static001.geekbang.org/resource/image/88/5e/882114aaf55861832b4270d44507695e.png)
- 几种情况
  - 如果落在绿色部分，表示这个版本是已提交的事务或者是当前事务自己生成的，这个数据是可见的；
  - 如果落在红色部分，表示这个版本是由将来启动的事务生成的，是肯定不可见的；
  - 如果落在黄色部分，那就包括两种情况
    - a. 若 row trx_id 在数组中，表示这个版本是由还没提交的事务生成的，不可见；
    - b. 若 row trx_id 不在数组中，表示这个版本是已经提交了的事务生成的，可见。
  - 比如，对于图 2 中的数据来说，如果有一个事务，它的低水位是 18，那么当它访问这一行数据时，就会从 V4 通过 U3 计算出 V3，所以在它看来，这一行的值是 11。
  - 事务可以看到小于当前版本号的事务提交记录
- 所以你现在知道了，InnoDB 利用了“所有数据都有多个版本”的这个特性，实现了“秒级创建快照”的能力。
- 接下来，我们继续看一下图 1 中的三个事务，分析下事务 A 的语句返回的结果，为什么是 k=1。
  - 这里，我们不妨做如下假设：
  - 事务 A 开始前，系统里面只有一个活跃事务 ID 是 99；
  - 事务 A、B、C 的版本号分别是 100、101、102，且当前系统里只有这四个事务；
  - 三个事务开始前，(1,1）这一行数据的 row trx_id 是 90。
  - 这样，事务 A 的视图数组就是[99,100], 事务 B 的视图数组是[99,100,101], 事务 C 的视图数组是[99,100,101,102]。
  - ![](https://static001.geekbang.org/resource/image/94/49/9416c310e406519b7460437cb0c5c149.png)
  - 所以，我来给你翻译一下。一个数据版本，对于一个事务视图来说，除了自己的更新总是可见以外，有三种情况：
    - 版本未提交，不可见；
    - 版本已提交，但是是在视图创建后提交的，不可见；
    - 版本已提交，而且是在视图创建前提交的，可见。
  - 
- 

### 更新逻辑

你看图 5 中，事务 B 的视图数组是先生成的，之后事务 C 才提交，不是应该看不见 (1,2) 吗，怎么能算出 (1,3) 来？

![](https://static001.geekbang.org/resource/image/86/9f/86ad7e8abe7bf16505b97718d8ac149f.png)

- 是的，如果事务 B 在更新之前查询一次数据，这个查询返回的 k 的值确实是 1。
- 但是，当它要去更新数据的时候，就不能再在历史版本上更新了，否则事务 C 的更新就丢失了。因此，事务 B 此时的 set k=k+1 是在（1,2）的基础上进行的操作。
- 更新数据都是先读后写的，而这个读，只能读当前的值，称为“当前读”（current read）。
- 这里我们提到了一个概念，叫作当前读。其实，除了 update 语句外，select 语句如果加锁，也是当前读。
  - 锁=》可见性
- ![](https://static001.geekbang.org/resource/image/cd/6e/cda2a0d7decb61e59dddc83ac51efb6e.png)
- 两阶段锁协议
- 这时候，我们在上一篇文章中提到的“两阶段锁协议”就要上场了。事务 C’没提交，也就是说 (1,2) 这个版本上的写锁还没释放。而事务 B 是当前读，必须要读最新版本，而且必须加锁，因此就被锁住了，必须等到事务 C’释放这个锁，才能继续它的当前读。

### 事务的可重复读的能力是怎么实现的

- 可重复读的核心就是一致性读（consistent read）；而事务更新数据的时候，只能用当前读。如果当前的记录的行锁被其他事务占用的话，就需要进入锁等待。
- 而读提交的逻辑和可重复读的逻辑类似，它们最主要的区别是：
  - 在可重复读隔离级别下，只需要在事务开始的时候创建一致性视图，之后事务里的其他查询都共用这个一致性视图；
  - 在读提交隔离级别下，每一个语句执行前都会重新算出一个新的视图。
- 

### 小结

- InnoDB 的行数据有多个版本，每个数据版本有自己的 row trx_id，每个事务或者语句有自己的一致性视图。普通查询语句是一致性读，一致性读会根据 row trx_id 和一致性视图确定数据版本的可见性。
  - 对于可重复读，查询只承认在事务启动前就已经提交完成的数据；
  - 对于读提交，查询只承认在语句启动前就已经提交完成的数据；
- 你也可以想一下，为什么表结构不支持“可重复读”？这是因为表结构没有对应的行数据，也没有 row trx_id，因此只能遵循当前读的逻辑。
- 当然，MySQL 8.0 已经可以把表结构放在 InnoDB 字典里了，也许以后会支持表结构的可重复读。

**问题**

![](https://static001.geekbang.org/resource/image/9b/0b/9b8fe7cf88c9ba40dc12e93e36c3060b.png)

上期的问题是：如何构造一个“数据无法修改”的场景。评论区里已经有不少同学给出了正确答案，这里我再描述一下。

## 09 | 普通索引和唯一索引，应该怎么选择？

- 唯一索引和普通索引

  - 唯一索引 主键，聚簇，区分度高，主键不能null
  - 普通索引回表

- 例子

  - ```mysql
    select name from CUser where id_card = 'xxxxxxxyyyyyyzzzzz';
    ```

    - 市民身份证号查询用户基础数据
    - 普通/唯一 索引选择

  - 普通/唯一 索引的区别，普通索引找到命中的后会继续查询下一个命中的，而唯一索引则停止

    - 但是这两个在这个基础上的性能消耗没啥区别
    - 普通索引按页读取，若在同一个页内多一次指针寻找+判断
    - 不同页则复杂一些，但是一个索引页可存放近千个key，因此这种情况出现概率低

  - 

- 

### 更新过程

- insert buffer?后来升级为change buffer
- 加快数据变更的效率，若变更数据在内存则直接更新，不在则计入change buffer，下次读入更新保证一致性
  - change buffer增加执行效率
  - 可持久化，定期merge 提高可靠性
  - 正常关闭 shutdown 执行merge
- **使用change buffer的场景**
  - 唯一索引使用需要先判断更新值是否符合唯一约束，不符合则失败，因此只有普通索引能使用
- change buffer 用的是 buffer pool 里的内存，因此不能无限增大。change buffer 的大小，可以通过参数 innodb_change_buffer_max_size 来动态设置。这个参数设置为 50 的时候，表示 change buffer 的大小最多只能占用 buffer pool 的 50%。
- **如果要在这张表中插入一个新记录 (4,400) 的话，InnoDB 的处理流程是怎样的。**
  - 数据页在内存中
    - 唯一索引比普通索引多了一步约束校验
  - 数据页不再内存中
    - 唯一索引需读取磁盘数据涉及随机IO（数据库内陈本最高的操作）
    - 普通索引只需记录change buffer即可
- **change buffer 的使用场景**
  - 日志、账单系统 =》 写多读少
    - 最大限度利用change buffer 读操作少减少磁盘IO
  - 写完后马上要读
    - 读取会触发merge，与无change buffer差不多反而要付出维护change buffer的代价
- 索引选择和实践
  - 其实，这两类索引在查询能力上是没差别的，主要考虑的是对更新性能的影响。所以，我建议你尽量选择普通索引。
  - change buffer 在写后马上读应该关闭
  - 在实际使用中，你会发现，普通索引和 change buffer 的配合使用，对于数据量大的表的更新优化还是很明显的。
  - 历史数据归档+机械硬盘 =》change buffer
- 

### change buffer 和 redo log

- change buffer redo log和wal
- 执行插入`mysql> insert into t(id,k) values(id1,k1),(id2,k2);`
  - ![](https://static001.geekbang.org/resource/image/98/a3/980a2b786f0ea7adabef2e64fb4c4ca3.png)
  - 内存布局，change buffer在 buffer pool 而redo log单独 独立
  - 若k1的数据页在磁盘，k2不再
  - 更新k1内存，k2记录change buffer,两个操作持久化到redo log(redo log 会定期刷回磁盘)
- 那在这之后的读请求，要怎么处理呢？ 执行`select * from t where k in (k1, k2)`
- ![](https://static001.geekbang.org/resource/image/6d/8e/6dc743577af1dbcbb8550bddbfc5f98e.png)
- 读page1 直接返回，读page2 ,读取对应数据页到磁盘，执行merge，返回正确值
- redo log和 change buffer的区别
  - redo log 提升随机写转为顺序写，减少写磁盘的IO消耗
  - change buffer节省读磁盘的IO消耗（读取后更新与直接更新）
- 

### 小结

- 今天，我从普通索引和唯一索引的选择开始，和你分享了数据的查询和更新过程，然后说明了 change buffer 的机制以及应用场景，最后讲到了索引选择的实践。
- 由于唯一索引用不上 change buffer 的优化机制，因此如果业务可以接受，从性能角度出发我建议你优先考虑非唯一索引。
- change buffer 一开始是写内存的，那么如果这个时候机器掉电重启，会不会导致 change buffer 丢失呢
  - 不会丢失，虽然更新内存但是事务提交会存入redo log持久化
  - merge在内存中不会触发磁盘
- 

## 10 | MySQL为什么有时候会选错索引？

- MySQL支持多个索引

- MySQL可能会选错索引

  - ```mysql
    
    CREATE TABLE `t` (
      `id` int(11) NOT NULL AUTO_INCREMENT,
      `a` int(11) DEFAULT NULL,
      `b` int(11) DEFAULT NULL,
      PRIMARY KEY (`id`),
      KEY `a` (`a`),
      KEY `b` (`b`)
    ) ENGINE=InnoDB;
    # 插入初始记录
    
    delimiter ;;
    create procedure idata()
    begin
      declare i int;
      set i=1;
      while(i<=100000)do
        insert into t values(i, i, i);
        set i=i+1;
      end while;
    end;;
    delimiter ;
    call idata();
    # 分析语句
    # 正确使用索引
    mysql> select * from t where a between 10000 and 20000;
    # 特殊情况
    
    set long_query_time=0;
    select * from t where a between 10000 and 20000; /*Q1*/
    select * from t force index(a) where a between 10000 and 20000;/*Q2*/
    
    ```

  - 特殊情况

    ![](https://static001.geekbang.org/resource/image/1e/1e/1e5ba1c2934d3b2c0d96b210a27e1a1e.png?wh=936*344)

  - 新的测试

  - ![](https://static001.geekbang.org/resource/image/7c/f6/7c58b9c71853b8bba1a8ad5e926de1f6.png?wh=1221*325)

  - 可以看到第一条本应该用索引的却全表扫描了



### 优化器的逻辑

- 扫描行数是影响执行代价的因素之一。扫描的行数越少，意味着访问磁盘数据的次数越少，消耗的 CPU 资源越少。

- 优化器还会结合是否使用临时表、是否排序等因素进行综合判断。

- **扫描行数的判断**

  - MySQL在执行之前并不能知道精确的行数，而是通过采样统计的方式了解大概的索引**基数**（区分度，索引中不同值的数量）
  - 随机抽取N个数据页取平均 * 数据页数量 = 基数
  - 在 MySQL 中，有两种存储索引统计的方式，可以通过设置参数 innodb_stats_persistent 的值来选择：
    - 设置为 on 的时候，表示统计信息会持久化存储。这时，默认的 N 是 20，M 是 10。
    - 设置为 off 的时候，表示统计信息只存储在内存中。这时，默认的 N 是 8，M 是 16。
  - 

- explain

  ![](https://static001.geekbang.org/resource/image/e2/89/e2bc5f120858391d4accff05573e1289.png?wh=1606*382)

- 优化器选择了扫描100000行的，因为直接借助主键索引，不需要回表，优化器会把回表带来的性能消耗也算进去

- 重新统计索引信息`analyze table t`

- 另一条语句

  - ```MYSQL
    
    mysql> select * from t where (a between 1 and 1000)  and (b between 50000 and 100000) order by b limit 1;
    ```

  - ![](https://static001.geekbang.org/resource/image/48/b8/483bcb1ef3bb902844e80d9cbdd73ab8.png?wh=1891*163)

  - 分析可知 若借助索引A则扫描的数据更少但是选择了b

  - 扫描行数的估计值依然不准确；这个例子里 MySQL 又选错了索引。

- 

### 索引选择异常和处理

- 一种方法是，像我们第一个例子一样，采用 force index 强行选择一个索引。
  - 矫正作用，若候选索引包含force index直接使用
  - 不优美，移植性差，索引变更需要更改代码
  - 后期加入
- 第二种方法就是，我们可以考虑修改语句，引导 MySQL 使用我们期望的索引。
  - ![](https://static001.geekbang.org/resource/image/14/94/14cd598e52a2b72dd334a42603e5b894.png?wh=1623*146)
  - 使用索引b在之前可能认为不需要排序现在都需要排序了就选A
  - 修改语句需要符合业务逻辑，也并不是一定能实施的方法
- 第三种方法是，在有些场景下，我们可以新建一个更合适的索引，来提供给优化器做选择，或删掉误用的索引。
  - 协商 删除不必要索引
- 

### 小结

- 索引统计的更新机制
  - 分页采样统计
- 优化器选错索引
  - 考虑到回表、排序等额外情况
- 而对于其他优化器误判的情况，你可以在应用端用 force index 来强行指定索引，也可以通过修改语句来引导优化器，还可以通过增加或者删除索引来绕过这个问题。
- 问题：加了一个session 原本rows10000+变为37000+
  - 事务开启，MVCC 删除并不立即生效，标识位数据删除 逻辑删除，导致扫描行数不对
  - 但是主键索引不同，直接通过show status查看表基础信息了解其行数

## 11 | 怎么给字符串字段加索引？

现在，几乎所有的系统都支持邮箱登录，如何在邮箱这样的字段上建立合理的索引，是我们今天要讨论的问题。

数据库SQL

```mysql

mysql> create table SUser(
ID bigint unsigned primary key,
email varchar(64), 
... 
)engine=innodb; 
mysql> select f1, f2 from SUser where email='xxx';
```

**前缀索引**

```mysql

mysql> alter table SUser add index index1(email);
或
mysql> alter table SUser add index index2(email(6));
```

![](https://static001.geekbang.org/resource/image/d3/b7/d31da662bee595991862c439a5567eb7.jpg)

![](https://static001.geekbang.org/resource/image/13/42/134583875561de914991fc2e192cf842.jpg)

从图中你可以看到，**由于 email(6) 这个索引结构中每个邮箱字段都只取前 6 个字节（即：zhangs），所以占用的空间会更小**，这就是使用前缀索引的优势。

```mysql
select id,name,email from SUser where email='zhangssxyz@xxx.com';
```



两种索引名的查询过程

- 普通索引

  - 普通索引扫描一行获取ID
  - ID回表扫描获取值 加入结果集
  - 索引扫描下一行 发现失败 循环结束

- 前缀索引

  - index2索引搜索'zhangs'记录找到主键ID
  - 主键ID回表寻找具体记录匹配发现步匹配丢弃
  - 取下一条记录匹配，正确则加入结果集
  - 继续匹配'zhangs'的记录直到索引不匹配

- 可以看到通过前缀索引扫描的次数反而多了 也就是说使用前缀索引，定义好长度，就可以做到既节省空间，又不用额外增加太多的查询成本。

- 于是，你就有个问题：当要给字符串创建前缀索引时，有什么方法能够确定我应该使用多长的前缀呢？

  - 区分度

    ```mysql
    mysql> select count(distinct email) as L from SUser;
    
    mysql> select 
      count(distinct left(email,4)）as L4,
      count(distinct left(email,5)）as L5,
      count(distinct left(email,6)）as L6,
      count(distinct left(email,7)）as L7,
    from SUser;
    ```

    

  - 

- 

### 前缀索引对覆盖索引的影响

- 前缀索引影响扫描行数

- 额外影响

  ```mysql
  select id,email from SUser where email='zhangssxyz@xxx.com';
  ```

  - 所以，如果使用 index1（即 email 整个字符串的索引结构）的话，可以利用覆盖索引，从 index1 查到结果后直接就返回了，不需要回到 ID 索引再去查一次。而如果使用 index2（即 email(6) 索引结构）的话，就不得不回到 ID 索引再去判断 email 字段的值。

- 也就是说，使用前缀索引就用不上覆盖索引对查询性能的优化了，这也是你在选择是否使用前缀索引时需要考虑的一个因素。

### 其他方式

- 存储身份证，身份证前缀大部分相同

  - 索引选取的越长，占用的磁盘空间就越大，相同的数据页能放下的索引值就越少，搜索的效率也就会越低。

- 解决方案

  - 倒序存储

    ```mysql
    
    mysql> select field_list from t where id_card = reverse('input_id_card_string');
    ```

    

  - 第二种方式是使用 hash 字段。你可以在表上再创建一个整数字段，来保存身份证的校验码，同时在这个字段上创建索引。

    ```mysql
    
    mysql> alter table t add id_card_crc int unsigned, add index(id_card_crc);
    ```

  - 然后每次插入新记录的时候，都同时用 crc32() 这个函数得到校验码填到这个新字段。由于校验码可能存在冲突，也就是说两个不同的身份证号通过 crc32() 函数得到的结果可能是相同的，所以你的查询语句 where 部分要判断 id_card 的值是否精确相同。

  - 注意hash冲突

    ```mysql
    
    mysql> select field_list from t where id_card_crc=crc32('input_id_card_string') and id_card='input_id_card_string'
    ```

    

  - 

- 不支持范围查询

  - hash消耗额外空间，倒序字段长
  - 倒序reverse，hash=》crc32 ，reverse消耗更小
  - hash更稳定

### 小结

字符串创建索引方式

- 完整索引
- 前缀索引，无法用到覆盖索引
- 倒序存储+前缀索引，注意区分度
- hash索引，注意hash冲突

**问题**

- 如果你在维护一个学校的学生信息数据库，学生登录名的统一格式是”学号 @gmail.com", 而学号的规则是：十五位的数字，其中前三位是所在城市编号、第四到第六位是学校编号、第七位到第十位是入学年份、最后五位是顺序编号。
- hash索引

## 12 | 为什么我的MySQL会“抖”一下？

> 平时的工作中，不知道你有没有遇到过这样的场景，一条 SQL 语句，正常执行的时候特别快，但是有时也不知道怎么回事，它就会变得特别慢，并且这样的场景很难复现，它不只随机，而且持续时间还很短。

### 你的 SQL 语句为什么变“慢”了

- 数据更新时采用write ahead logging 机制，更新内存 记录redo log ,空闲写回binlog 同时释放redo log 资源，双写机制保障数据安全
- 当内存数据页跟磁盘数据页内容不一致的时候，我们称这个内存页为“脏页”。内存数据写入到磁盘后，内存和磁盘上的数据页的内容就一致了，称为“干净页”。

![](https://static001.geekbang.org/resource/image/34/da/349cfab9e4f5d2a75e07b2132a301fda.jpeg)

- redo log 刷回磁盘的场景

  - redo log满了，MySQL停下工作数据一部分刷回磁盘 redo log check pos推进

    ![](https://static001.geekbang.org/resource/image/a2/e5/a25bdbbfc2cfc5d5e20690547fe7f2e5.jpg)

  - 场景2 掌柜记不住过量数据，内存不足，淘汰数据页，淘汰的是脏页就需要先刷回磁盘

    - 你一定会说，这时候难道不能直接把内存淘汰掉，下次需要请求的时候，从磁盘读入数据页，然后拿 redo log 出来应用不就行了？这里其实是从性能考虑的。如果刷脏页一定会写盘，就保证了每个数据页有两种状态：
      - 脏页直接淘汰会导致下次读入内存还需引用redo log将内存数据补完
      - 刷回保证内存数据页一定是最新的/内存没数据磁盘上是最新的 读取直接返回

  - 场景3 不忙的时候，MySQL认为当前不忙就把脏页刷回磁盘

  - 场景4 MySQL正常关闭 把内存中所有脏页刷回磁盘

- 场景对性能影响，空闲和关闭不考虑

  - redo log满了，阻塞写操作，等待redo log刷回空出空间再继续，此时MySQL写性能变为0 后续恢复
  - 内存不足，**InnoDB 用缓冲池（buffer pool）管理内存，缓冲池中的内存页有三种状态**：
    - 未使用
    - 使用但干净（已经被刷回
    - 使用后变为脏页
  - 内存不足淘汰脏页需要刷回磁盘，此时阻塞，淘汰脏页太多/日志写满导致响应变长
  - 

### InnoDB 刷脏页的控制策略

- 你要正确地告诉 InnoDB 所在主机的 IO 能力，这样 InnoDB 才能知道需要全力刷脏页的时候，可以刷多快。

- 这就要用到 innodb_io_capacity 这个参数了，它会告诉 InnoDB 你的磁盘能力。这个值我建议你设置成磁盘的 IOPS。

  - 1000/max 10000

  - fio测试磁盘随机读写能力

  - ```shell
    
     fio -filename=$filename -direct=1 -iodepth 1 -thread -rw=randrw -ioengine=psync -bs=16k -size=500M -numjobs=10 -runtime=10 -group_reporting -name=mytest 
    ```

  - 

- 其实，因为没能正确地设置 innodb_io_capacity 参数，而导致的性能问题也比比皆是。之前，就曾有其他公司的开发负责人找我看一个库的性能问题，说 MySQL 的写入速度很慢，TPS 很低，但是数据库主机的 IO 压力并不大。经过一番排查，发现罪魁祸首就是这个参数的设置出了问题。

  - TPS低，IO压力不大，磁盘刷回能力判断失误导致
  - 例如参数设置错误（偏小i ）导致MySQL认为服务器硬件性能不行，导致脏页刷回变慢从而影响

- 如果你来设计策略控制刷脏页的速度，会参考哪些因素呢？

  - 刷太慢=》内存脏页太多/redo log写满

  - InnoDB 的刷盘速度就是要参考这两个因素：一个是脏页比例，一个是 redo log 写盘速度。

  - 根据上述算得的 F1(M) 和 F2(N) 两个值，取其中较大的值记为 R，之后引擎就可以按照 innodb_io_capacity 定义的能力乘以 R% 来控制刷脏页的速度。F1脏页比 F2 redolog写回速度

    ![](https://static001.geekbang.org/resource/image/cc/74/cc44c1d080141aa50df6a91067475374.png)

- 要尽量避免这种情况，你就要合理地设置 innodb_io_capacity 的值，并且平时要多关注脏页比例，不要让它经常接近 75%。

- 其中，脏页比例是通过 Innodb_buffer_pool_pages_dirty/Innodb_buffer_pool_pages_total 得到的，具体的命令参考下面的代码：

- ```mysql
  
  mysql> select VARIABLE_VALUE into @a from global_status where VARIABLE_NAME = 'Innodb_buffer_pool_pages_dirty';
  select VARIABLE_VALUE into @b from global_status where VARIABLE_NAME = 'Innodb_buffer_pool_pages_total';
  select @a/@b;
  ```

- 一旦一个查询请求需要在执行过程中先 flush 掉一个脏页时，这个查询就可能要比平时慢了。而 MySQL 中的一个机制，可能让你的查询会更慢：在准备刷一个脏页的时候，如果这个数据页旁边的数据页刚好是脏页，就会把这个“邻居”也带着一起刷掉；而且这个把“邻居”拖下水的逻辑还可以继续蔓延，也就是对于每个邻居数据页，如果跟它相邻的数据页也还是脏页的话，也会被放到一起刷。

  - 脏页连坐机制
  - 在 InnoDB 中，innodb_flush_neighbors 参数就是用来控制这个行为的，值为 1 的时候会有上述的“连坐”机制，值为 0 时表示不找邻居，自己刷自己的。
  - innodb_flush_neighbors 机械硬盘开启，SSD关闭，SSD的IO速度快，只刷自己更好
  - 在 MySQL 8.0 中，innodb_flush_neighbors 参数的默认值已经是 0 了。小结

- 

### 小结

- WAL减少IO提高性能，随机写改为顺序写提升性能
  - 带来刷脏页带来的额外性能消耗问题
- 

### 思考题

> 一个内存配置为 128GB、innodb_io_capacity 设置为 20000 的大规格实例，正常会建议你将 redo log 设置成 4 个 1GB 的文件。
>
> 但如果你在配置的时候不慎将 redo log 设置成了 1 个 100M 的文件，会发生什么情况呢？又为什么会出现这样的情况呢？

- 导致redo log很快被占满，脏页频繁刷回 进而性能下降
- write pos追着checkpos
- 磁盘压力很小，但是数据库出现间歇性的性能下跌。

## 13 | 为什么表数据删掉一半，表文件大小不变？

> 经常会有同学来问我，我的数据库占用空间太大，我把一个最大的表删掉了一半的数据，怎么表文件的大小还是没变？
>
> 表结构定义和数据。在 MySQL 8.0 版本以前，表结构是存在以.frm 为后缀的文件里。而 MySQL 8.0 版本，则已经允许把表结构定义放在系统数据表中了。因为表结构定义占用的空间很小，所以我们今天主要讨论的是表数据。
>
> 接下来，我会先和你说明为什么简单地删除表数据达不到表空间回收的效果，然后再和你介绍正确回收空间的方法。

### 参数 innodb_file_per_table

- 控制表数据放在单独文件/共享表空间
  - on 每个表存储在一个.ibd文件中
  - off代表数据存放在系统共享表空间，和数据字典在一起
- 建议开启 drop table直接删除文件
- **将 innodb_file_per_table 设置为 ON，是推荐做法，我们接下来的讨论都是基于这个设置展开的。**
- 

### 数据删除流程

INNODB用B+树组织结构

![](https://static001.geekbang.org/resource/image/f0/c8/f0b1e4ac610bcb5c5922d0b18563f3c8.png)

- 假设删除R4 标记R4 空间可以被复用，当录入ID300-600时可复用，但删除不会让表空间变小
- 如果我们删掉了一个数据页上的所有记录，会怎么样？
  - 整个数据页就可以被复用了。
- **你现在知道了，delete 命令其实只是把记录的位置，或者数据页标记为了“可复用”**，但磁盘文件的大小是不会变的。也就是说，通过 delete 命令是不能回收表空间的。这些可以复用，而没有被使用的空间，看起来就像是“空洞”。
- 实际上，不止是删除数据会造成空洞，插入数据也会。
- 增删改都会造成空洞，增加可能导致页分裂，页分裂导致数据空洞，修改是先删除后插入也一样
- 重建表可达到修复空洞

### 重建表

- 表A，空间收缩 去掉空洞 处理方案
  - 新建表B，将A记录插入B，主键有序修复空洞
  - 插入完成后用B替代A完成空洞修复
- 你可以使用 alter table A engine=InnoDB 命令来重建表。
  - 5.5与上述操作差不多
  - ![](https://static001.geekbang.org/resource/image/02/cd/02e083adaec6e1191f54992f7bc13dcd.png)
  - 5.6增加了Online DDL
- 5.5版本的问题
  - 写tmp耗时且为了保证数据一致性不能对A进行修改操作
- 5.6的Online DDL
  - ![](https://static001.geekbang.org/resource/image/2d/f0/2d1cfbbeb013b851a56390d38b5321f0.png)
  - 建立A记录的B+树存入临时文件
  - 生成临时文件时的修改存入row log
  - 将row log应用于临时文件
  - tmp-file与表A交换
  - 在最耗时的复制部分需要MDL 读锁，但是不阻塞增删改操作
  - 这个读锁只防止出现其他DDL操作
- 需要补充说明的是，上述的这些重建方法都会扫描原表数据和构建临时文件。对于很大的表来说，这个操作是很消耗 IO 和 CPU 资源的。因此，如果是线上服务，你要很小心地控制操作时间。如果想要比较安全的操作的话，我推荐你使用 GitHub 开源的 gh-ost 来做。

### Online 和 inplace

- online和inplace的区别在于 online是innodb层而inplace是在server层创建临时表
  - inplace = `alter table t engine=innodb,ALGORITHM=inplace;`
- online在引擎层所以类似原地 = `alter table t engine=innodb,ALGORITHM=copy;`
- 但是并不是就不占用空间了5.6的操作还是需要空间的
- 

- optimize table
  - optimize table t 等于 recreate+analyze。
- analyze table 
  - analyze table t 其实不是重建表，只是对表的索引信息做重新统计，没有修改数据，这个过程中加了 MDL 读锁；
- alter table
  - 从 MySQL 5.6 版本开始，alter table t engine = InnoDB（也就是 recreate）默认的就是上面图 4 的流程了；

### 小结

今天这篇文章，我和你讨论了数据库中收缩表空间的方法。

现在你已经知道了，如果要收缩一个表，只是 delete 掉表里面不用的数据的话，表文件的大小是不会变的，你还要通过 alter table 命令重建表，才能达到表文件变小的目的。我跟你介绍了重建表的两种实现方式，Online DDL 的方式是可以考虑在业务低峰期使用的，而 MySQL 5.5 及之前的版本，这个命令是会阻塞 DML 的，这个你需要特别小心。

- 5.5阻塞DML
- alter table engine = innodb来缩减漏洞

### 问题

> 假设现在有人碰到了一个“想要收缩表空间，结果适得其反”的情况，看上去是这样的：一个表 t 文件大小为 1TB；对这个表执行 alter table t engine=InnoDB；发现执行完成后，空间不仅没变小，还稍微大了一点儿，比如变成了 1.01TB。你觉得可能是什么原因呢 ？

- 表没有空洞，重建过程中录入数据会导致增大的现象
- 

## 14 | count(*)这么慢，我该怎么办？

> select count(*) from t 会随着数据量增大而变慢

- 了解其原理
- 有统计且频繁更新的需求如何处理

### count(*) 的实现方式

- 不同存储引擎的实现不同
  - myisam不支持事务，直接记录总行数
    - 若加上where也不会很快
  - innodb需要一行一行读取统计
    - innodb在事务支持、并发能力、数据安全等方面更优
-  innodb由于MVCC所以无法确定总行数，count(*)可能存在某个事务中，因此结果需要基于当前事务版本
  - ![](https://static001.geekbang.org/resource/image/5e/97/5e716ba1d464c8224c1c1f36135d0e97.png)
- **在保证逻辑正确的前提下，尽量减少扫描的数据量，是数据库系统设计的通用法则之一。**
  - 一张表可能有一个主键索引和多个普通索引，普通索引占用空间小，因此可以选择合适的普通索引扫描以计数
- **show table status** 这个命令会返回一个table rows 但是基于采样 误差大
- 小结
  - MyISAM 表虽然 count(*) 很快，但是不支持事务；
  - show table status 命令虽然返回很快，但是不准确；
  - InnoDB 表直接 count(*) 会遍历全表，虽然结果准确，但会导致性能问题。
- 用某个空间存储并更新总记录数解决 有统计且频繁更新的需求



### 用缓存系统保存计数

- 用redis存储计数，新增+1 ，删除-1
- redis意外宕机，数据丢失，此时可通过重新count(*)来确保一致
- 但实际上，**将计数保存在缓存系统中的方式，还不只是丢失更新的问题。即使 Redis 正常工作，这个值还是逻辑上不精确的。**
- 多线程环境下的问题
  - ![](https://static001.geekbang.org/resource/image/39/33/39898af053695dad37227d71ae288e33.png)
    - 此时计数与最近100条记录不符，读取的记录比计数多
  - ![](https://static001.geekbang.org/resource/image/5c/db/5c2f786beae1d8917cdc5033b7bf0bdb.png)
    - 此时读取的数据比记录少
- 在并发系统里面，我们是无法精确控制不同线程的执行时刻的，因为存在图中的这种操作序列，所以，我们说即使 Redis 正常工作，这个计数值还是逻辑上不精确的。

### 在数据库保存计数

- 解决数据持久化问题
- 依赖于MVCC的事务机制解决多线程并发问题
  - ![](https://static001.geekbang.org/resource/image/9e/e3/9e4170e2dfca3524eb5e92adb8647de3.png)

### 不同的 count 用法

> 在前面文章的评论区，有同学留言问到：在 select count(?) from t 这样的查询语句里面，count(*)、count(主键 id)、count(字段) 和 count(1) 等不同用法的性能，有哪些差别。今天谈到了 count(*) 的性能问题，我就借此机会和你详细说明一下这几种用法的性能差别。

- count基于innodb
- count统计非null值
- count(*)、count(主键 id) 和 count(1) 都表示返回满足条件的结果集的总行数；而 count(字段），则表示返回满足条件的数据行里面，参数“字段”不为 NULL 的总个数。
- 所以结论是：按照效率排序的话，count(字段) <count(主键 id)<count(1)≈count(\*)所以我建议你，尽量使用 count(*)。

### 小结

- 不同存储引擎count(*)不同，不同存储组件的分布式事务导致数据不一致问题
- 其实，把计数放在 Redis 里面，不能够保证计数和 MySQL 表里的数据精确一致的原因，是这两个不同的存储构成的系统，不支持分布式事务，无法拿到精确一致的视图。而把计数值也放在 MySQL 中，就解决了一致性视图的问题。
- innodb支持事务，原子+隔离简化开发逻辑

### 问题

在刚刚讨论的方案中，我们用了事务来确保计数准确。由于事务可以保证中间结果不被别的事务读到，因此修改计数值和插入新记录的顺序是不影响逻辑结果的。但是，从并发系统性能的角度考虑，你觉得在这个事务序列里，应该先插入操作记录，还是应该先更新计数表呢？

- 根据行锁的两阶段锁机制 行锁用到才会加，并且需要考虑并发影响，行锁事务结束才会释放
- 根据特性insert语句不如update影响大，update的对象是计数，计数经常被查询，加上行锁存在事务对查询产生阻塞，因此将update值后																																																																																																																																																																																																																																																																																																																																																																																																																																																																																	

## 15 | 答疑文章（一）：日志和索引相关问题

### 日志相关问题

- redo log两阶段提交期间产生的问题
- ![](https://static001.geekbang.org/resource/image/ee/2a/ee9af616e05e4b853eba27048351f62a.jpg)
- MySQL两阶段就是在事务commit时才确认完成否则回滚
- redo log prepare/commit两阶段，prepare到commit就代表binlog写入
- 崩溃恢复规则
  - 如果 redo log 里面的事务是完整的，也就是已经有了 commit 标识，则直接提交；
  - 如果 redo log 里面的事务只有完整的 prepare，则判断对应的事务 binlog 是否存在并完整：
    - a. 如果是，则提交事务；
    - b. 否则，回滚事务。
- 因此A回滚B提交



- 

**追问 1：MySQL 怎么知道 binlog 是完整的?**

- statement模式的语句 后面有commit
- row模式的有XID 与redo log中对应进而验证两段锁
- binlog-checksum验证binlog一致性

**追问 2：redo log 和 binlog 是怎么关联起来的?**

- XID关联
- 崩溃恢复顺序扫描redo log
  - 若binlog存在对应XID说明记录了binlog可能是commit前出错，直接提交
  - 或者redolog包含prepare/commit两端提交则直接提交

**追问 3：处于 prepare 阶段的 redo log 加上完整 binlog，重启就能恢复，MySQL 为什么要这么设计?**

- 为了保证数据一致性，redo log写完回写binlog成功可能已经被从库读取

**追问 4：如果这样的话，为什么还要两阶段提交呢？干脆先 redo log 写完，再写 binlog。崩溃恢复的时候，必须得两个日志都完整才可以。是不是一样的逻辑？**

- 分布式系统问题，两者不协调
- redolog 提交不能回滚，若binlog写入导致回滚导致数据和binlog不一致



**追问 5：不引入两个日志，也就没有两阶段提交的必要了。只用 binlog 来支持崩溃恢复，又能支持归档，不就可以了？**

- 历史原因：binlog不支持崩溃恢复
- 实现原因：binlog没有能力实现数据页恢复，由于write ahead log机制若MySQL写完日志（binlog）发生崩溃此时无法恢复数据页，若binlog额外记录数据页恢复就又是一个redo log



**追问 6：那能不能反过来，只用 redo log，不要 binlog？**

- redo log有其限制，内存限制、无法用于数据主从

**追问 7：redo log 一般设置多大？**

- 几个TB就4个redo log文件每个1G

**追问 8：正常运行中的实例，数据写入后的最终落盘，是从 redo log 更新过来的还是从 buffer pool 更新过来的呢？**

- redo log并非完整记录 所以从buffer pool
- 数据页可能是脏页刷回磁盘，若崩溃恢复则先从redo log让页变为脏页再刷回

**追问 9：redo log buffer 是什么？是先修改内存，还是先写 redo log 文件？**

- 提交前在buffer commit后写入redo log文件

### 业务设计问题

> 业务上有这样的需求，A、B 两个用户，如果互相关注，则成为好友。设计上是有两张表，一个是 like 表，一个是 friend 表，like 表有 user_id、liker_id 两个字段，我设置为复合唯一索引即 uk_user_id_liker_id。
>
> 语句执行逻辑是这样的：以 A 关注 B 为例：
>
> 第一步，先查询对方有没有关注自己（B 有没有关注 A）select * from like where user_id = B and liker_id = A;
>
> 如果有，则成为好友insert into friend;
>
> 没有，则只是单向关注关系insert into like;
>
> 但是如果 A、B 同时关注对方，会出现不会成为好友的情况。
>
> 因为上面第 1 步，双方都没关注对方。第 1 步即使使用了排他锁也不行，因为记录不存在，行锁无法生效。请问这种情况，在 MySQL 锁层面有没有办法处理？

```mysql

CREATE TABLE `like` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `liker_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id_liker_id` (`user_id`,`liker_id`)
) ENGINE=InnoDB;

CREATE TABLE `friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `friend_1_id` int(11) NOT NULL,
  `friend_2_id` int(11) NOT NULL,
  UNIQUE KEY `uk_friend` (`friend_1_id`,`friend_2_id`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;
```



- 由于设计上user_id和liker_id 虽然加了UK但是两者AB存储和BA存储 是存在两条记录无法用到行锁，导致事务不生效

- 首先，要给“like”表增加一个字段，比如叫作 relation_ship，并设为整型，取值 1、2、3。值是 1 的时候，表示 user_id 关注 liker_id;值是 2 的时候，表示 liker_id 关注 user_id;值是 3 的时候，表示互相关注。

- 应用代码里面，比较 A 和 B 的大小，

  ```mysql
  #如果 A<B，就执行下面的逻辑
  mysql> begin; /*启动事务*/
  insert into `like`(user_id, liker_id, relation_ship) values(A, B, 1) on duplicate key update relation_ship=relation_ship | 1;
  select relation_ship from `like` where user_id=A and liker_id=B;
  /*代码中判断返回的 relation_ship，
    如果是1，事务结束，执行 commit
    如果是3，则执行下面这两个语句：
    */
  insert ignore into friend(friend_1_id, friend_2_id) values(A,B);
  commit;
  #如果 A>B，则执行下面的逻辑
  
  mysql> begin; /*启动事务*/
  insert into `like`(user_id, liker_id, relation_ship) values(B, A, 2) on duplicate key update relation_ship=relation_ship | 2;
  select relation_ship from `like` where user_id=B and liker_id=A;
  /*代码中判断返回的 relation_ship，
    如果是2，事务结束，执行 commit
    如果是3，则执行下面这两个语句：
  */
  insert ignore into friend(friend_1_id, friend_2_id) values(B,A);
  commit;
  ```

  

- 

- 通过额外字段记录AB相互关注顺序，事务不生效的实质在于uk不触发，加上一个字段让AB相互关注uk一样，修改同一条记录，使用行锁来确保事务

### 问题

- 建表+数据更新然而数据库affect rows为0的原因

  - ```mysql
    mysql> CREATE TABLE `t` (`id` int(11) NOT NULL primary key auto_increment,`a` int(11) DEFAULT NULL) ENGINE=InnoDB;insert into t values(1,2);
    mysql> update t set a=2 where id=1;
    ```

  - ![](https://static001.geekbang.org/resource/image/36/70/367b3f299b94353f32f75ea825391170.png)

- 锁验证

  - ![](https://static001.geekbang.org/resource/image/6d/90/6d9d8837560d01b57d252c470157ea90.png)
  - B阻塞因为没获取锁，所以不会比较相同就放弃更新
  - 单个事务内快照更新和查询

- 我们上期思考题的答案应该是选项 3，即：InnoDB 认真执行了“把这个值修改成 (1,2)"这个操作，该加锁的加锁，该更新的更新。

## 16 | “order by”是怎么工作的？

> 在你开发应用的时候，一定会经常碰到需要根据指定的字段排序来显示结果的需求。还是以我们前面举例用过的市民表为例，假设你要查询城市是“杭州”的所有人名字，并且按照姓名排序返回前 1000 个人的姓名、年龄。

```mysql

CREATE TABLE `t` (
  `id` int(11) NOT NULL,
  `city` varchar(16) NOT NULL,
  `name` varchar(16) NOT NULL,
  `age` int(11) NOT NULL,
  `addr` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `city` (`city`)
) ENGINE=InnoDB;


select city,name,age from t where city='杭州' order by name limit 1000  ;

```

这个语句看上去逻辑很清晰，但是你了解它的执行流程吗？今天，我就和你聊聊这个语句是怎么执行的，以及有什么参数会影响执行的行为。

### 全字段排序



执行顺序

-  Extra 这个字段中的“Using filesort”表示的就是需要排序，MySQL 会给每个线程分配一块内存用于排序，称为 sort_buffer。 构建sort buffer

- 根据索引查询符合条件的city放入sort buffer

- sort buffer排序

- 取1000返回

- ![](https://static001.geekbang.org/resource/image/6c/72/6c821828cddf46670f9d56e126e3e772.jpg)

- 图中“按 name 排序”这个动作，可能在内存中完成，也可能需要使用外部排序，这取决于排序所需的内存和参数 sort_buffer_size。

- 根据分配的buffer大小判断是否需要额外的文件排序

- ```mysql
  
  /* 打开optimizer_trace，只对本线程有效 */
  SET optimizer_trace='enabled=on'; 
  
  /* @a保存Innodb_rows_read的初始值 */
  select VARIABLE_VALUE into @a from  performance_schema.session_status where variable_name = 'Innodb_rows_read';
  
  /* 执行语句 */
  select city, name,age from t where city='杭州' order by name limit 1000; 
  
  /* 查看 OPTIMIZER_TRACE 输出 */
  SELECT * FROM `information_schema`.`OPTIMIZER_TRACE`\G
  
  /* @b保存Innodb_rows_read的当前值 */
  select VARIABLE_VALUE into @b from performance_schema.session_status where variable_name = 'Innodb_rows_read';
  
  /* 计算Innodb_rows_read差值 */
  select @b-@a;
  
  {
    "steps": [
      {
        "join_preparation": {
          "select#": 1,
          "steps": [
            {
              "expanded_query": "/* select#1 */ select `t`.`city` AS `city`,`t`.`name` AS `name`,`t`.`age` AS `age` from `t` where (`t`.`city` = '杭州') order by `t`.`name` limit 1000"
            }
          ]
        }
      },
      {
        "join_optimization": {
          "select#": 1,
          "steps": [
            {
              "condition_processing": {
                "condition": "WHERE",
                "original_condition": "(`t`.`city` = '杭州')",
                "steps": [
                  {
                    "transformation": "equality_propagation",
                    "resulting_condition": "multiple equal('杭州', `t`.`city`)"
                  },
                  {
                    "transformation": "constant_propagation",
                    "resulting_condition": "multiple equal('杭州', `t`.`city`)"
                  },
                  {
                    "transformation": "trivial_condition_removal",
                    "resulting_condition": "multiple equal('杭州', `t`.`city`)"
                  }
                ]
              }
            },
            {
              "substitute_generated_columns": {
              }
            },
            {
              "table_dependencies": [
                {
                  "table": "`t`",
                  "row_may_be_null": false,
                  "map_bit": 0,
                  "depends_on_map_bits": [
                  ]
                }
              ]
            },
            {
              "ref_optimizer_key_uses": [
                {
                  "table": "`t`",
                  "field": "city",
                  "equals": "'杭州'",
                  "null_rejecting": false
                }
              ]
            },
            {
              "rows_estimation": [
                {
                  "table": "`t`",
                  "range_analysis": {
                    "table_scan": {
                      "rows": 1,
                      "cost": 2.45
                    },
                    "potential_range_indexes": [
                      {
                        "index": "PRIMARY",
                        "usable": false,
                        "cause": "not_applicable"
                      },
                      {
                        "index": "city",
                        "usable": true,
                        "key_parts": [
                          "city",
                          "id"
                        ]
                      }
                    ],
                    "setup_range_conditions": [
                    ],
                    "group_index_range": {
                      "chosen": false,
                      "cause": "not_group_by_or_distinct"
                    },
                    "skip_scan_range": {
                      "potential_skip_scan_indexes": [
                        {
                          "index": "city",
                          "usable": false,
                          "cause": "query_references_nonkey_column"
                        }
                      ]
                    },
                    "analyzing_range_alternatives": {
                      "range_scan_alternatives": [
                        {
                          "index": "city",
                          "ranges": [
                            "杭州 <= city <= 杭州"
                          ],
                          "index_dives_for_eq_ranges": true,
                          "rowid_ordered": true,
                          "using_mrr": false,
                          "index_only": false,
                          "rows": 1,
                          "cost": 0.61,
                          "chosen": true
                        }
                      ],
                      "analyzing_roworder_intersect": {
                        "usable": false,
                        "cause": "too_few_roworder_scans"
                      }
                    },
                    "chosen_range_access_summary": {
                      "range_access_plan": {
                        "type": "range_scan",
                        "index": "city",
                        "rows": 1,
                        "ranges": [
                          "杭州 <= city <= 杭州"
                        ]
                      },
                      "rows_for_plan": 1,
                      "cost_for_plan": 0.61,
                      "chosen": true
                    }
                  }
                }
              ]
            },
            {
              "considered_execution_plans": [
                {
                  "plan_prefix": [
                  ],
                  "table": "`t`",
                  "best_access_path": {
                    "considered_access_paths": [
                      {
                        "access_type": "ref",
                        "index": "city",
                        "rows": 1,
                        "cost": 0.35,
                        "chosen": true
                      },
                      {
                        "access_type": "range",
                        "range_details": {
                          "used_index": "city"
                        },
                        "chosen": false,
                        "cause": "heuristic_index_cheaper"
                      }
                    ]
                  },
                  "condition_filtering_pct": 100,
                  "rows_for_plan": 1,
                  "cost_for_plan": 0.35,
                  "chosen": true
                }
              ]
            },
            {
              "attaching_conditions_to_tables": {
                "original_condition": "(`t`.`city` = '杭州')",
                "attached_conditions_computation": [
                ],
                "attached_conditions_summary": [
                  {
                    "table": "`t`",
                    "attached": "(`t`.`city` = '杭州')"
                  }
                ]
              }
            },
            {
              "optimizing_distinct_group_by_order_by": {
                "simplifying_order_by": {
                  "original_clause": "`t`.`name`",
                  "items": [
                    {
                      "item": "`t`.`name`"
                    }
                  ],
                  "resulting_clause_is_simple": true,
                  "resulting_clause": "`t`.`name`"
                }
              }
            },
            {
              "finalizing_table_conditions": [
                {
                  "table": "`t`",
                  "original_table_condition": "(`t`.`city` = '杭州')",
                  "final_table_condition   ": null
                }
              ]
            },
            {
              "refine_plan": [
                {
                  "table": "`t`"
                }
              ]
            },
            {
              "considering_tmp_tables": [
                {
                  "adding_sort_to_table": "t"
                }
              ]
            }
          ]
        }
      },
      {
        "join_execution": {
          "select#": 1,
          "steps": [
            {
              "sorting_table": "t",
              "filesort_information": [
                {
                  "direction": "asc",
                  "expression": "`t`.`name`"
                }
              ],
              "filesort_priority_queue_optimization": {
                "limit": 1000
              },
              "filesort_execution": [
              ],
              "filesort_summary": {
                "memory_available": 262144,
                "key_size": 264,
                "row_size": 406,
                "max_rows_per_buffer": 633,
                "num_rows_estimate": 1057,
                "num_rows_found": 0,
                "num_initial_chunks_spilled_to_disk": 0,
                "peak_memory_used": 0,
                "sort_algorithm": "none",
                "sort_mode": "<varlen_sort_key, packed_additional_fields>"
              }
            }
          ]
        }
      }
    ]
  }
  ```

- 这个方法是通过查看 OPTIMIZER_TRACE 的结果来确认的，你可以从 number_of_tmp_files 中看到是否使用了临时文件。

- ![](https://static001.geekbang.org/resource/image/89/95/89baf99cdeefe90a22370e1d6f5e6495.png)

- 

### rowID排序

- 如果 MySQL 认为排序的单行长度太大会怎么做呢？

- ```mysql
  #设置最大长度16 上面三个查询总和为36
  SET max_length_for_sort_data = 16;
  ```

- 执行流程变更

- rowID排序

  - ![](https://static001.geekbang.org/resource/image/dc/6d/dc92b67721171206a302eb679c83e86d.jpg)

- ![](https://static001.geekbang.org/resource/image/27/9b/27f164804d1a4689718291be5d10f89b.png)

- 首先，图中的 examined_rows 的值还是 4000，表示用于排序的数据是 4000 行。但是 select @b-@a 这个语句的值变成 5000 了。

- 如果 MySQL 实在是担心排序内存太小，会影响排序效率，才会采用 rowid 排序算法，这样排序过程中一次可以排序更多行，但是需要再回到原表去取数据。

- 如果 MySQL 认为内存足够大，会优先选择全字段排序，把需要的字段都放到 sort_buffer 中，这样排序后就会直接从内存里面返回查询结果了，不用再回到原表去取数据。

- 若超出内存则name+主键排序还要回表取值

- **这也就体现了 MySQL 的一个设计思想：如果内存够，就要多利用内存，尽量减少磁盘访问。**

- MySQL 之所以需要生成临时表，并且在临时表上做排序操作，**其原因是原来的数据都是无序的**。

- 所以，我们可以在这个市民表上创建一个 city 和 name 的联合索引，对应的 SQL 语句是：alter table t add index city_user(city, name);

- **ROWID排序 排序字段更少但是要回表**

- 这里我们可以再稍微复习一下。覆盖索引是指，索引上的信息足够满足查询请求，不需要再回到主键索引上去取数据。

- 针对这个查询，我们可以创建一个 city、name 和 age 的联合索引，对应的 SQL 语句就是：alter table t add index city_user_age(city, name, age);

- 可以看到，Extra 字段里面多了“Using index”，表示的就是使用了覆盖索引，性能上会快很多。

### 小结

今天这篇文章，我和你介绍了 MySQL 里面 order by 语句的几种算法流程。在开发系统的时候，你总是不可避免地会使用到 order by 语句。你心里要清楚每个语句的排序逻辑是怎么实现的，还要能够分析出在最坏情况下，每个语句的执行对系统资源的消耗，这样才能做到下笔如有神，不犯低级错误。

### 问题

假设你的表里面已经有了 city_name(city, name) 这个联合索引，然后你要查杭州和苏州两个城市中所有的市民的姓名，并且按名字排序，显示前 100 条记录。如果 SQL 查询语句是这么写的 ：

`mysql> select * from t where city in ('杭州',"苏州") order by name limit 100;`

- in 范围 用不到后面的索引
- 单独建立name索引（没提到
- 做两次搜索然后数据聚合

这里，我们要用到 (city,name) 联合索引的特性，把这一条语句拆成两条语句，执行流程如下：执行 select * from t where city=“杭州” order by name limit 100; 这个语句是不需要排序的，客户端用一个长度为 100 的内存数组 A 保存结果。执行 select * from t where city=“苏州” order by name limit 100; 用相同的方法，假设结果被存进了内存数组 B。现在 A 和 B 是两个有序数组，然后你可以用归并排序的思想，得到 name 最小的前 100 值，就是我们需要的结果了。

## 17 | 如何正确地显示随机消息？

> 我在上一篇文章，为你讲解完 order by 语句的几种执行模式后，就想到了之前一个做英语学习 App 的朋友碰到过的一个性能问题。今天这篇文章，我就从这个性能问题说起，和你说说 MySQL 中的另外一种排序需求，希望能够加深你对 MySQL 排序逻辑的理解。这个英语学习 App 首页有一个随机显示单词的功能，也就是根据每个用户的级别有一个单词表，然后这个用户每次访问首页的时候，都会随机滚动显示三个单词。他们发现随着单词表变大，选单词这个逻辑变得越来越慢，甚至影响到了首页的打开速度。

```mysql

mysql> CREATE TABLE `words` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `word` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

delimiter ;;
create procedure idata()
begin
  declare i int;
  set i=0;
  while i<10000 do
    insert into words(word) values(concat(char(97+(i div 1000)), char(97+(i % 1000 div 100)), char(97+(i % 100 div 10)), char(97+(i % 10))));
    set i=i+1;
  end while;
end;;
delimiter ;

call idata();
```

### 内存临时表

实现：

```mysql
mysql> select word from words order by rand() limit 3;
```

- innodb优先全字段排序避免IO
- 内存表没这个顾虑直接 rowid 排序
- 这里插一句题外话，在平时学习概念的过程中，你可以经常这样做，先通过原理分析算出扫描行数，然后再通过查看慢查询日志，来验证自己的结论。我自己就是经常这么做，这个过程很有趣，分析对了开心，分析错了但是弄清楚了也很开心。
- 其实不是的。如果你创建的表没有主键，或者把一个表的主键删掉了，那么 InnoDB 会自己生成一个长度为 6 字节的 rowid 来作为主键。
- 这也就是排序模式里面，rowid 名字的来历。实际上它表示的是：每个引擎用来唯一标识数据行的信息。
- 对于有主键的 InnoDB 表来说，这个 rowid 就是主键 ID；对于没有主键的 InnoDB 表来说，这个 rowid 就是由系统生成的；MEMORY 引擎不是索引组织表。在这个例子里面，你可以认为它就是一个数组。因此，这个 rowid 其实就是数组的下标。
- SQL执行顺序
  - 构建临时表
  - 表数据*rand后入表 扫描10000
  - 按临时表double字段排序
  - sort buffer 取字段存入 扫描10000
  - sort buffer 排序
    - 问题就在这里方法2 不用排序 虽然扫描的多
  - 取前三返回
- 到这里，我来稍微小结一下：order by rand() 使用了内存临时表，内存临时表排序的时候使用了 rowid 排序方法。

### 磁盘临时表

- 超出内存16MB就用磁盘

- 为了复现这个过程，我把 tmp_table_size 设置成 1024，把 sort_buffer_size 设置成 32768, 把 max_length_for_sort_data 设置成 16。

- ```mysql
  
  set tmp_table_size=1024;
  set sort_buffer_size=32768;
  set max_length_for_sort_data=16;
  /* 打开 optimizer_trace，只对本线程有效 */
  SET optimizer_trace='enabled=on'; 
  
  /* 执行语句 */
  select word from words order by rand() limit 3;
  
  /* 查看 OPTIMIZER_TRACE 输出 */
  SELECT * FROM `information_schema`.`OPTIMIZER_TRACE`\G
  ```

- ![](https://static001.geekbang.org/resource/image/78/ab/78d2db9a4fdba81feadccf6e878b4aab.png)

- 排序 算法  归并/堆排序，若limit小则堆排序更合适 若limit大则构建的堆太大不合适

- 

**怎么正确地随机排序呢？**

### 随机排序方法

我们先把问题简化一下，如果只随机选择 1 个 word 值，可以怎么做呢？思路上是这样的：

- 取得这个表的主键 id 的最大值 M 和最小值 N;
- 用随机函数生成一个最大值到最小值之间的数 X = (M-N)*rand() + N;
- 取不小于 X 的第一个 ID 的行。
- 最大最小随机取数，取第一个不小于x的 但是分布不均匀

**严格随机**

- 取得整个表的行数，并记为 C。
- 取得 Y = floor(C * rand())。 floor 函数在这里的作用，就是取整数部分。
- 再用 limit Y,1 取得一行。
- 总行数随机取一个再limit 1
- 扫描多 C10000 + Y10000+1
- 但是不需要排序比order by rand()效率高

## 18 | 为什么这些SQL语句逻辑相同，性能却差异巨大？

> 在 MySQL 中，有很多看上去逻辑相同，但性能却差异巨大的 SQL 语句。对这些语句使用不当的话，就会不经意间导致整个数据库的压力变大。

### 案例一：条件字段函数操作

假设你现在维护了一个交易系统，其中交易记录表 tradelog 包含交易流水号（tradeid）、交易员 id（operator）、交易时间（t_modified）等字段。为了便于描述，我们先忽略其他字段。这个表的建表语句如下：

```mysql

mysql> CREATE TABLE `tradelog` (
  `id` int(11) NOT NULL,
  `tradeid` varchar(32) DEFAULT NULL,
  `operator` int(11) DEFAULT NULL,
  `t_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tradeid` (`tradeid`),
  KEY `t_modified` (`t_modified`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# 查询每年七月份的交易总和
mysql> select count(*) from tradelog where month(t_modified)=7;

```

- 虽然t_modified 上存在索引，但是无法使用
- 函数无法使用普通索引

索引示意图

![](https://static001.geekbang.org/resource/image/3e/86/3e30d9a5e67f711f5af2e2599e800286.png)

- 实际上，B+ 树提供的这个快速定位能力，来源于同一层兄弟节点的有序性。

- 传入7则无法利用同层有序特性

- 对索引字段做函数操作，可能会破坏索引值的有序性，因此优化器就决定放弃走树搜索功能。

- 优化器并不是直接放弃这个索引

- 优于使用索引扫描更少最后还是会走索引

  - ![](https://static001.geekbang.org/resource/image/27/55/27c2f5ff3549b18ba37a28f4919f3655.png)

  

- 可以修改SQL达到可以使用索引的目的

  - ```mysql
    
    mysql> select count(*) from tradelog where
        -> (t_modified >= '2016-7-1' and t_modified<'2016-8-1') or
        -> (t_modified >= '2017-7-1' and t_modified<'2017-8-1') or 
        -> (t_modified >= '2018-7-1' and t_modified<'2018-8-1');
    ```

  - 

- 到这里我给你说明了，由于加了 month() 函数操作，MySQL 无法再使用索引快速定位功能，而只能使用全索引扫描。

- 

### 案例二：隐式类型转换

```mysql

mysql> select * from tradelog where tradeid=110717;
```

交易编号 tradeid 这个字段上，本来就有索引，但是 explain 的结果却显示，这条语句需要走全表扫描。你可能也发现了，tradeid 的字段类型是 varchar(32)，而输入的参数却是整型，所以需要做类型转换。

那么，现在这里就有两个问题：

- 数据类型转换的规则是什么？

  - 这里有一个简单的方法，看 select “10” > 9 的结果：
    - 如果1代表字符串转数字
    - 如果0代表数字转字符串
    - ![](https://static001.geekbang.org/resource/image/2b/14/2b67fc38f1651e2622fe21d49950b214.png)

- 为什么有数据类型转换，就需要走全索引扫描？

  - 此时语句变为

  - ```mysql
    
    mysql> select * from tradelog where  CAST(tradid AS signed int) = 110717;
    ```

  - 可以看到字段用了函数导致索引失效

  - 现在，我留给你一个小问题，id 的类型是 int，如果执行下面这个语句，是否会导致全表扫描呢？select * from tradelog where id="83126";

    - 不会全表扫描因为函数加在value上

### 案例三：隐式字符编码转换

> 假设系统里还有另外一个表 trade_detail，用于记录交易的操作细节。为了便于量化分析和复现，我往交易日志表 tradelog 和交易详情表 trade_detail 这两个表里插入一些数据。

```mysql

mysql> CREATE TABLE `trade_detail` (
  `id` int(11) NOT NULL,
  `tradeid` varchar(32) DEFAULT NULL,
  `trade_step` int(11) DEFAULT NULL, /*操作步骤*/
  `step_info` varchar(32) DEFAULT NULL, /*步骤信息*/
  PRIMARY KEY (`id`),
  KEY `tradeid` (`tradeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into tradelog values(1, 'aaaaaaaa', 1000, now());
insert into tradelog values(2, 'aaaaaaab', 1000, now());
insert into tradelog values(3, 'aaaaaaac', 1000, now());

insert into trade_detail values(1, 'aaaaaaaa', 1, 'add');
insert into trade_detail values(2, 'aaaaaaaa', 2, 'update');
insert into trade_detail values(3, 'aaaaaaaa', 3, 'commit');
insert into trade_detail values(4, 'aaaaaaab', 1, 'add');
insert into trade_detail values(5, 'aaaaaaab', 2, 'update');
insert into trade_detail values(6, 'aaaaaaab', 3, 'update again');
insert into trade_detail values(7, 'aaaaaaab', 4, 'commit');
insert into trade_detail values(8, 'aaaaaaac', 1, 'add');
insert into trade_detail values(9, 'aaaaaaac', 2, 'update');
insert into trade_detail values(10, 'aaaaaaac', 3, 'update again');
insert into trade_detail values(11, 'aaaaaaac', 4, 'commit');


mysql> select d.* from tradelog l, trade_detail d where d.tradeid=l.tradeid and l.id=2; /*语句Q1*/
```

我们一起来看下这个结果：

![](https://static001.geekbang.org/resource/image/ad/22/adfe464af1d15f3261b710a806c0fa22.png)

- 两表字符集不同 utf8mb4是utf8的超集所以转换时会将utf8 转为utf8mb4
- tradeid关联，先扫描了tradelog
- trade detail全表扫描

![](https://static001.geekbang.org/resource/image/82/a9/8289c184c8529acea0269a7460dc62a9.png)

图中：第 1 步，是根据 id 在 tradelog 表里找到 L2 这一行；

第 2 步，是从 L2 中取出 tradeid 字段的值；

第 3 步，是根据 tradeid 值到 trade_detail 表中查找条件匹配的行。explain 的结果里面第二行的 key=NULL 表示的就是，这个过程是通过遍历主键索引的方式，一个一个地判断 tradeid 的值是否匹配。

如果你去问 DBA 同学，他们可能会告诉你，**因为这两个表的字符集不同，一个是 utf8，一个是 utf8mb4，所以做表连接查询的时候用不上关联字段的索引。这个回答，也是通常你搜索这个问题时会得到的答案。**

关联没有用索引

也就是说，实际上这个语句等同于下面这个写法：

```mysql

select * from trade_detail  where CONVERT(traideid USING utf8mb4)=$L2.tradeid.value; 

```

- mysql中不管是隐式的类型转换或者字符集转换都会触发函数操作

- 到这里，你终于明确了，字符集不同只是条件之一，连接过程中要求在被驱动表的索引字段上加函数操作，是直接导致对被驱动表做全表扫描的原因。

- ```mysql
  
  mysql>select l.operator from tradelog l , trade_detail d where d.tradeid=l.tradeid and d.id=4;
  
  
  select operator from tradelog  where traideid =$R4.tradeid.value; 
  
  select operator from tradelog  where traideid =CONVERT($R4.tradeid.value USING utf8mb4); 
  
  ```

- 由于右侧函数所以不影响索引使用

- 优化

  - 比较常见的优化方法是，把 trade_detail 表上的 tradeid 字段的字符集也改成 utf8mb4，这样就没有字符集转换的问题了。

    - ```mysql
      
      alter table trade_detail modify tradeid varchar(32) CHARACTER SET utf8mb4 default null;
      ```

    - 

  - 如果能够修改字段的字符集的话，是最好不过了。但如果数据量比较大， 或者业务上暂时不能做这个 DDL 的话，那就只能采用修改 SQL 语句的方法了。

    - ```mysql
      
      mysql> select d.* from tradelog l , trade_detail d where d.tradeid=CONVERT(l.tradeid USING utf8) and l.id=2; 
      ```

    - 强转utf8mb4变为utf8从而实现强制转换成功

- 

### 小结

今天我给你举了三个例子，其实是在说同一件事儿，即：**对索引字段做函数操作，可能会破坏索引值的有序性，因此优化器就决定放弃走树搜索功能。**

## 19 | 为什么我只查一行的语句，也执行这么慢？

> 一般情况下，如果我跟你说查询性能优化，你首先会想到一些复杂的语句，想到查询需要返回大量的数据。但有些情况下，“查一行”，也会执行得特别慢。今天，我就跟你聊聊这个有趣的话题，看看什么情况下，会出现这个现象。

```mysql

mysql> CREATE TABLE `t` (
  `id` int(11) NOT NULL,
  `c` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

delimiter ;;
create procedure idata()
begin
  declare i int;
  set i=1;
  while(i<=100000) do
    insert into t values(i,i);
    set i=i+1;
  end while;
end;;
delimiter ;

call idata();
```

### 第一类：查询长时间不返回

```mysql

mysql> select * from t where id=1;

```

查询结果长时间不返回。

![](https://static001.geekbang.org/resource/image/87/2a/8707b79d5ed906950749f5266014f22a.png)

- 推论：MDL锁/表锁/行锁 导致无法继续执行

一般碰到这种情况的话，大概率是表 t 被锁住了。接下来分析原因的时候，一般都是首先执行一下 **show processlist** 命令，看看当前语句处于什么状态。

#### 等 MDL 锁

如图 2 所示，就是使用 show processlist 命令查看 Waiting for table metadata lock 的示意图。

![](https://static001.geekbang.org/resource/image/50/28/5008d7e9e22be88a9c80916df4f4b328.png)

出现这个状态表示的是，现在有一个线程正在表 t 上请求或者持有 MDL 写锁，把 select 语句堵住了。

不过，在 MySQL 5.7 版本下复现这个场景，也很容易。如图 3 所示，我给出了简单的复现步骤。

![](https://static001.geekbang.org/resource/image/74/ca/742249a31b83f4858c51bfe106a5daca.png)

- sessionA加锁MDL 锁定，sessionB被阻塞

#### 等 flush

```mysql

mysql> select * from information_schema.processlist where id=1;

flush tables t with read lock;

flush tables with read lock;
```

![](https://static001.geekbang.org/resource/image/2d/24/2d8250398bc7f8f7dce8b6b1923c3724.png)

flush table命令等待锁会产生这个问题

![](https://static001.geekbang.org/resource/image/2b/9c/2bbc77cfdb118b0d9ef3fdd679d0a69c.png)

#### 等行锁

```mysql

mysql> select * from t where id=1 lock in share mode; 
```

由于访问 id=1 这个记录时要加读锁，如果这时候已经有一个事务在这行记录上持有一个写锁，我们的 select 语句就会被堵住。

- 加锁查询
- 此前若行被互斥锁锁定就上面语句就被锁定

![](https://static001.geekbang.org/resource/image/3c/8f/3c266e23fc307283aa94923ecbbc738f.png)

查询具体的罪魁祸首

这个问题并不难分析，但问题是怎么查出是谁占着这个写锁。如果你用的是 MySQL 5.7 版本，可以通过 sys.innodb_lock_waits 表查到。

```mysql

mysql> select * from t sys.innodb_lock_waits where locked_table='`test`.`t`'\G
```

![](https://static001.geekbang.org/resource/image/d8/18/d8603aeb4eaad3326699c13c46379118.png)

- 可以看到4号线程出问题 直接KILL 4
- 

### 第二类：查询慢

经过了重重封“锁”，我们再来看看一些查询慢的例子。

```mysql

mysql> select * from t where c=50000 limit 1;
```

由于字段 c 上没有索引，这个语句只能走 id 主键顺序扫描，因此需要扫描 5 万行。

如果不知道哪条语句出了问题可以通过慢查询日志打印

作为确认，你可以看一下慢查询日志。注意，这里为了把所有语句记录到 slow log 里，我在连接后先执行了 **set long_query_time=0**，将慢查询日志的时间阈值设置为 0。

![](https://static001.geekbang.org/resource/image/d8/3c/d8b2b5f97c60ae4fc4a03c616847503c.png)

Rows_examined 显示扫描了 50000 行。你可能会说，不是很慢呀，11.5 毫秒就返回了，我们线上一般都配置超过 1 秒才算慢查询。但你要记住：**坏查询不一定是慢查询**。我们这个例子里面只有 10 万行记录，数据量大起来的话，执行时间就线性涨上去了。

**另一种情况**

```mysql

mysql> select * from t where id=1；
```

![](https://static001.geekbang.org/resource/image/66/46/66f26bb885401e8e460451ff6b0c0746.png)

单记录查询却花了800ms

- 可能因为锁
- 

![](https://static001.geekbang.org/resource/image/bd/d2/bde83e269d9fa185b27900c8aa8137d2.png)

in share mode 只花了2毫秒更快了

![](https://static001.geekbang.org/resource/image/1f/1c/1fbb84bb392b6bfa93786fe032690b1c.png)

- 可以看到两个查询结果不同
- in share mode有当前查的意思
- 若存在undo log+mvcc回滚查事务开始数据可能就是导致查询慢的原因

![](https://static001.geekbang.org/resource/image/84/ff/84667a3449dc846e393142600ee7a2ff.png)

​	带 lock in share mode 的 SQL 语句，是当前读，因此会直接读到 1000001 这个结果，所以速度很快；而 select * from t where id=1 这个语句，是一致性读，因此需要从 1000001 开始，依次执行 undo log，执行了 100 万次以后，才将 1 这个结果返回。

### 小结

- 锁
- 未加索引
- mvcc过程中更新了太多

### 问题

```mysql

begin;
select * from t where c=5 for update;
commit;
```

- 判断怎么加锁

## 20 | 幻读是什么，幻读有什么问题？

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

begin;
select * from t where d=5 for update;
commit;
```

- SQL语句给表 d=5的行加写锁
- 二阶段锁协议只有事务结束才释放
- d没有索引走全表扫描，全表扫描是扫描主键，主键存在索引所以锁加载主键索引上

### 幻读是什么？

![](https://static001.geekbang.org/resource/image/5b/8b/5bc506e5884d21844126d26bbe6fa68b.png)

- 都是 select * from t where d=5 for update。这个语句的意思你应该很清楚了，查所有 d=5 的行，而且使用的是当前读，并且加上写锁。
- 当前都导致快照读的失效导致读到了最新数据并且update没有更新被锁住的那一行导致写锁失效
- 第二次insert则出现幻读
  - 在可重复读隔离级别下，普通的查询是快照读，是不会看到别的事务插入的数据的。因此，幻读在“当前读”下才会出现。
  - 上面 session B 的修改结果，被 session A 之后的 select 语句用“当前读”看到，不能称为幻读。幻读仅专指“新插入的行”。

### 幻读有什么问题？

![](https://static001.geekbang.org/resource/image/7a/07/7a9ffa90ac3cc78db6a51ff9b9075607.png)

- sessionB sessionC都破坏了写锁的加锁申明，在d=5的行上做了数据修改
- 语义被破坏

**数据一致性**

为了说明这个问题，我给 session A 在 T1 时刻再加一个更新语句，即：update t set d=100 where d=5。

![](https://static001.geekbang.org/resource/image/dc/92/dcea7845ff0bdbee2622bf3c67d31d92.png)

- 若只给d=5这一行加锁

- 则T2的sessionB在T1的sessionA值后就执行

- ```mysql
  
  update t set d=5 where id=0; /*(0,0,5)*/
  update t set c=5 where id=0; /*(0,5,5)*/
  
  insert into t values(1,1,5); /*(1,1,5)*/
  update t set c=5 where id=1; /*(1,5,5)*/
  
  update t set d=100 where d=5;/*所有d=5的行，d改成100*/
  ```

- 意味着sessionB的T2更新被覆盖了

- 我们分析一下可以知道，这是我们假设“select * from t where d=5 for update 这条语句只给 d=5 这一行，也就是 id=5 的这一行加锁”导致的。

- 实际上没有索引走主键索引，扫描的行都会加锁就意味着之前的都枷锁了

![](https://static001.geekbang.org/resource/image/34/47/34ad6478281709da833856084a1e3447.png)

实际的binlog

```mysql

insert into t values(1,1,5); /*(1,1,5)*/
update t set c=5 where id=1; /*(1,5,5)*/

update t set d=100 where d=5;/*所有d=5的行，d改成100*/

update t set d=5 where id=0; /*(0,0,5)*/
update t set c=5 where id=0; /*(0,5,5)*/
```

- sessionB的update被阻塞
- 也就是说，即使把所有的记录都加上锁，还是阻止不了新插入的记录，这也是为什么“幻读”会被单独拿出来解决的原因。
- **加锁锁不了新加入的记录**

### 如何解决幻读？

现在你知道了，产生幻读的原因是，行锁只能锁住行，但是新插入记录这个动作，要更新的是记录之间的“间隙”。因此，为了解决幻读问题，InnoDB 只好引入新的锁，也就是间隙锁 (Gap Lock)。

- 通过间隙锁解决幻读
- 间隙锁意味着间隙被锁住，因此只有等锁释放才能继续执行update

行锁的冲突兼容

![](https://static001.geekbang.org/resource/image/c4/51/c435c765556c0f3735a6eda0779ff151.png)

- 间隙锁是可以被多次获取的
- 但是间隙锁不一样，**跟间隙锁存在冲突关系的，是“往这个间隙中插入一个记录”这个操作。**间隙锁之间都不存在冲突关系。
- 间隙锁和行锁合称 next-key lock，每个 next-key lock 是前开后闭区间。也就是说，我们的表 t 初始化以后，如果用 select * from t for update 要把整个表所有记录锁起来，就形成了 7 个 next-key lock，分别是 (-∞,0]、(0,5]、(5,10]、(10,15]、(15,20]、(20, 25]、(25, +supremum]。
- 间隙锁和 next-key lock 的引入，帮我们解决了幻读的问题，但同时也带来了一些“困扰”。
- **RR级别提供间隙锁保证update 的数据一致性 解决幻读带来的update导致不可重复读的问题 幻读依旧只能通过串行解决**

在前面的文章中，就有同学提到了这个问题。我把他的问题转述一下，对应到我们这个例子的表来说，业务逻辑这样的：任意锁住一行，如果这一行不存在的话就插入，如果存在这一行就更新它的数据，代码如下：

```mysql

begin;
select * from t where id=N for update;

/*如果行不存在*/
insert into t values(N,N,N);
/*如果行存在*/
update t set d=N set id=N;

commit;
```

![](https://static001.geekbang.org/resource/image/df/be/df37bf0bb9f85ea59f0540e24eb6bcbe.png)

- **上述并发场景带来了问题，insert被间隙锁阻塞，间隙锁可以被获取多次 共享资源被多处获取 产生死锁**
- 前面文章的评论区有同学留言说，**他们公司就使用的是读提交隔离级别加 binlog_format=row 的组合。**他曾问他们公司的 DBA 说，你为什么要这么配置。DBA 直接答复说，因为大家都这么用呀。
  - 这个组合row级别可解决幻读带来的数据不一致的问题 read commit又处理了间隙锁带来的并发问题
  - 但其实我想说的是，配置是否合理，跟业务场景有关，需要具体问题具体分析。
  - 
- 

### 小结

- 加锁依赖索引，完全没有索引就无法顺利加锁，若在没有索引的行上加锁，就会走全表扫描，全表扫描会给所有扫描的记录加锁，类似表锁，效率低，同时存在间隙的问题，因此insert导致的幻读会让数据不一致
- 通过引入间隙锁解决幻读问题，那还有必要串行这种隔离级别么？间隙锁可以被多个获取 存在并发下的死锁问题
  - 幻读解决的是历史读的幻读问题，间隙锁解决当前读的幻读问题
  - 所以真正完整的幻读解决在串行级别
- 可以将数据库置为read committed + row级别的binlog解决数据不一致+死锁问题

## 21 | 为什么我只改一行的语句，锁这么多？

> 从间隙锁的加锁规则开始
>
> 1. MySQL 后面的版本可能会改变加锁策略，所以这个规则只限于截止到现在的最新版本，即 5.x 系列 <=5.7.24，8.0 系列 <=8.0.13。
> 2. 如果大家在验证中有发现 bad case 的话，请提出来，我会再补充进这篇文章，使得一起学习本专栏的所有同学都能受益。
>
> 因为间隙锁在可重复读隔离级别下才有效，所以本篇文章接下来的描述，若没有特殊说明，默认是可重复读隔离级别。

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

**我总结的加锁规则里面，包含了两个“原则”、两个“优化”和一个“bug”。**

- 原则 1：加锁的基本单位是 next-key lock。希望你还记得，next-key lock 是前开后闭区间。
- 原则 2：查找过程中访问到的对象才会加锁。
  - 查找访问对象是索引
- 优化 1：索引上的等值查询，给唯一索引加锁的时候，next-key lock 退化为行锁。
- 优化 2：索引上的等值查询，向右遍历时且最后一个值不满足等值条件的时候，next-key lock 退化为间隙锁。
- 一个 bug：唯一索引上的范围查询会访问到不满足条件的第一个值为止。



### 案例一：等值查询间隙锁

第一个例子是关于等值条件操作间隙：

![](https://static001.geekbang.org/resource/image/58/6c/585dfa8d0dd71171a6fa16bed4ba816c.png)

- 根据原则1 sessionA的加锁范围(5,10]
- 根据优化2 向后遍历最后一个值不满足条件，锁退化(5,10)

### 案例二：非唯一索引等值锁

第二个例子是关于覆盖索引上的锁：

![](https://static001.geekbang.org/resource/image/46/65/465990fe8f6b418ca3f9992bd1bb5465.png)

- c是普通索引根据原则1 锁定(0,5] 加next-key lock
- 普通索引并不马上停止继续访问到10 所以给(5,10]加锁
- 根据优化二 退化为(5,10)
- 根据原则二 （这里访问到的对象指索引） 锁定的是普通索引c
- 因此完整的锁定范围是 c = (0,10) 因此sessionB生效sessionC阻塞
- lock in share mode 只锁覆盖索引，但是如果是 for update 就不一样了。 执行 for update 时，系统会认为你接下来要更新数据，因此会顺便给主键索引上满足条件的行加上行锁。

这个例子说明，锁是加在索引上的；同时，它给我们的指导是，如果你要用 lock in share mode 来给行加读锁避免数据被更新的话，就必须得绕过覆盖索引的优化，在查询字段中加入索引中不存在的字段。比如，将 session A 的查询语句改成 select d from t where c=5 lock in share mode。你可以自己验证一下效果。

- 可以多查询一个字段破坏覆盖索引

### 案例三：主键索引范围锁

第三个例子是关于范围查询的。

举例之前，你可以先思考一下这个问题：对于我们这个表 t，下面这两条查询语句，**加锁范围相同吗**？

```mysql

mysql> select * from t where id=10 for update;
mysql> select * from t where id>=10 and id<11 for update;
```

不同

- 第一条根据原则1和优化1锁定主键id=10
- 第二条范围查询要继续向后 锁定[10,15)

![](https://static001.geekbang.org/resource/image/30/80/30b839bf941f109b04f1a36c302aea80.png)

- 开始查找next-key lock(5,10]
- 继续向后找到id = 15才停下 所以锁定(10,15]
- 这里你需要注意一点，首次 session A 定位查找 id=10 的行的时候，是当做等值查询来判断的，而向右扫描到 id=15 的时候，用的是范围查询判断。

### 案例四：非唯一索引范围锁

接下来，我们再看两个范围查询加锁的例子，你可以对照着案例三来看。需要注意的是，与案例三不同的是，案例四中查询语句的 where 部分用的是字段 c。

![](https://static001.geekbang.org/resource/image/73/7a/7381475e9e951628c9fc907f5a57697a.png)

- c非主键索引，没有用到覆盖索引，还是会回表遍历主键索引
- c>=10 锁定(5,10] 不会命中优化1
- c<10 锁定(10,15] 命中优化2向右遍历 不满足条件next-key lock 退化为间隙锁 锁定(10,15) **分析有问题**
- c<10不会命中优化2**因为不是等值查询的向后遍历**所以锁定(10,15]
- 总和锁定(5,15]
- 这里需要扫描到 c=15 才停止扫描，是合理的，因为 InnoDB 要扫到 c=15，才知道不需要继续往后找了。

### 案例五：唯一索引范围锁 bug

前面的四个案例，我们已经用到了加锁规则中的**两个原则和两个优化**，接下来再看一个关于加锁规则中 bug 的案例。

- RR级别默认next-key lock（前开后闭）
- 访问到的对象（索引）才被锁定（注意覆盖索引
- 等值 唯一索引查询 命中后 退化为行锁
- 等值 非唯一索引 范围查询 最后一个不命中就退化为间隙锁

![](https://static001.geekbang.org/resource/image/b1/6d/b105f8c4633e8d3a84e6422b1b1a316d.png)

- id>10 锁定(10,15] 非等值 无法命中优化
  - session A 是一个范围查询，按照原则 1 的话，应该是索引 id 上只加 (10,15]这个 next-key lock，并且因为 id 是唯一键，所以循环判断到 id=15 这一行就应该停止了。
  - 但是实现上，InnoDB 会往前扫描到第一个不满足条件的行为止，也就是 id=20。而且由于这是个范围扫描，因此索引 id 上的 (15,20]这个 next-key lock 也会被锁上。
  -  8.0.21 版本的，实验的时候这个 bug 貌似是被解决了的样子。session B 和 session C 的插入，都没有问题
- id<=15 等值查询继续向后锁定(15,20] 等值命中优化2 锁定(15,20)
- 总和锁定 (10,20)

### 案例六：非唯一索引上存在"等值"的例子

```mysql

mysql> insert into t values(30,10,30);
```

![](https://static001.geekbang.org/resource/image/c1/59/c1fda36c1502606eb5be3908011ba159.png)

这次我们用 delete 语句来验证。注意，delete 语句加锁的逻辑，其实跟 select ... for update 是类似的，也就是我在文章开始总结的两个“原则”、两个“优化”和一个“bug”。

![](https://static001.geekbang.org/resource/image/b5/78/b55fb0a1cac3500b60e1cf9779d2da78.png)

- c非唯一索引 等值遍历 锁定(5,10]
  - 这时，session A 在遍历的时候，先访问第一个 c=10 的记录。同样地，根据原则 1，这里加的是 (c=5,id=5) 到 (c=10,id=10) 这个 next-key lock。
- 等值继续遍历 锁定(10,15]命中优化2 锁定(10,15)
  - 然后，session A 向右查找，直到碰到 (c=15,id=15) 这一行，循环才结束。根据优化 2，这是一个等值查询，向右查找到了不满足条件的行，所以会退化成 (c=10,id=10) 到 (c=15,id=15) 的间隙锁。
- 总和锁定(5,15)
  - ![](https://static001.geekbang.org/resource/image/bb/24/bb0ad92483d71f0dcaeeef278f89cb24.png)

这个蓝色区域左右两边都是虚线，表示开区间，即 (c=5,id=5) 和 (c=15,id=15) 这两行上都没有锁。

### 案例七：limit 语句加锁

![](https://static001.geekbang.org/resource/image/af/2e/afc3a08ae7a254b3251e41b2a6dae02e.png)

- 这个例子里，session A 的 delete 语句加了 limit 2。你知道表 t 里 c=10 的记录其实只有两条，因此加不加 limit 2，删除的效果都是一样的，但是加锁的效果却不同。可以看到，session B 的 insert 语句执行通过了，跟案例六的结果不同。
- limit特殊限定遍历到符合值后就停止向后
  - 这是因为，案例七里的 delete 语句明确加了 limit 2 的限制，因此在遍历到 (c=10, id=30) 这一行之后，满足条件的语句已经有两条，循环就结束了。
- ![](https://static001.geekbang.org/resource/image/e5/d5/e5408ed94b3d44985073255db63bd0d5.png)
- 这个例子对我们实践的指导意义就是，在删除数据的时候尽量加 limit。这样不仅可以控制删除数据的条数，让操作更安全，还可以减小加锁的范围。

### 案例八：一个死锁的例子

next-key lock=行锁+间隙锁

![](https://static001.geekbang.org/resource/image/7b/06/7b911a4c995706e8aa2dd96ff0f36506.png)

- sessionA的select锁定(5,15)
- session 加next-key lock (5,15) 此时锁定并阻塞
  - 先加(5, 10)的间隙锁，然后加10的行锁，锁住，还没有来得及加（10，15]的next-key lock呢，就被10的行锁给锁住了，所以这个时候session A 如果插入（12,12,12）是不会被session B的间隙锁给锁住。
- 然后 session A 要再插入 (8,8,8) 这一行，被 session B 的间隙锁锁住。由于出现了死锁，InnoDB 让 session B 回滚。
- 其实是这样的，session B 的“加 next-key lock(5,10] ”操作，实际上分成了两步，先是加 (5,10) 的间隙锁，加锁成功；然后加 c=10 的行锁，这时候才被锁住的。
- 间隙锁和间隙锁不冲突，间隙锁和insert冲突因此插入数据和sessionB冲突
- 就算分成了两步，为什么session B加(5,10)就能成功呢？session A不是加了(5, 10]的锁吗？ 好吧，自问自答一下，前面应该也是提到过的，间隙锁和间隙锁之间并不冲突，间隙锁和insert到这个间隙的语句才会冲突，因此session B加间隙锁(5, 10)是可以成功的，但是如果往(5, 10)里面插入的话会被阻塞。 但是如果直接加next-key lock(5, 10]，那么肯定是会被阻塞的，因此这个例子确实说明，加锁的步骤是分两步的，先是间隙锁，后是行锁。而且只要理解了间隙锁和行锁之间冲突的原则是不一样的，也就很容易理解这两个锁并不是一起加的了。
- session A 启动事务后执行查询语句加 lock in share mode，在索引 c 上加了 next-key lock(5,10] 和间隙锁 (10,15)；
- session B 的 update 语句也要在索引 c 上加 next-key lock(5,10] ，进入锁等待；
- 然后 session A 要再插入 (8,8,8) 这一行，被 session B 的间隙锁锁住。由于出现了死锁，InnoDB 让 session B 回滚。

你可能会问，session B 的 next-key lock 不是还没申请成功吗？

因为sessionB的更新语句被blocked了，因为要等待c=10的行锁释放，所以行锁这时候是申请不到的(所以按理来说next-key lock也是还未申请成功的，所以SessionA的insert不会被blocked)。但是因为insert是成功的，想想是因为加间隙锁之间是没有冲突的，所以这里间隙锁是加上了的。从而得出了先加间隙锁再加写锁这两步的分开操作。

### 小结

- RR级别默认next-key lock（前开后闭）
- 访问到的对象（索引）才被锁定（注意覆盖索引
- 等值 唯一索引查询 命中后 退化为行锁
- 等值 非唯一索引 范围查询 最后一个不命中就退化为间隙锁

这里我再次说明一下，我们上面的所有案例都是在可重复读隔离级别 (repeatable-read) 下验证的。**同时，可重复读隔离级别遵守两阶段锁协议，所有加锁的资源，都是在事务提交或者回滚的时候才释放的。**

在最后的案例中，你可以清楚地知道 next-key lock 实际上是由间隙锁加行锁实现的。如果切换到读提交隔离级别 (read-committed) 的话，就好理解了，过程中去掉间隙锁的部分，也就是只剩下行锁的部分。

另外，在读提交隔离级别下还有一个优化，即：语句执行过程中加上的行锁，在语句执行完成后，就要把“不满足条件的行”上的行锁直接释放了，不需要等到事务提交。

### 问题

![](https://static001.geekbang.org/resource/image/3a/1e/3a7578e104612a188a2d574eaa3bd81e.png)

- 由于是 order by c desc，第一个要定位的是索引 c 上“最右边的”c=20 的行，所以会加上间隙锁 (20,25) 和 next-key lock (15,20]。
- 在索引 c 上向左遍历，要扫描到 c=10 才停下来，所以 next-key lock 会加到 (5,10]，这正是阻塞 session B 的 insert 语句的原因。
  - 由于desc所以原本的从左到右变成了从右到左
  - 由于左开右闭 属于(5,10]
- 在扫描过程中，c=20、c=15、c=10 这三行都存在值，由于是 select *，所以会在主键 id 上加三个行锁。
  - 索引 c 上 (5, 25)；主键索引上 id=15、20 两个行锁。

## 22 | MySQL有哪些“饮鸩止渴”提高性能的方法？

> 不知道你在实际运维过程中有没有碰到这样的情景：业务高峰期，生产环境的 MySQL 压力太大，没法正常响应，需要短期内、临时性地提升一些性能。
>
> 我以前做业务护航的时候，就偶尔会碰上这种场景。用户的开发负责人说，不管你用什么方案，让业务先跑起来再说。但，如果是无损方案的话，肯定不需要等到这个时候才上场。今天我们就来聊聊这些临时方案，并着重说一说它们可能存在的风险。

### 短连接风暴

**正常的短连接模式就是连接到数据库后，执行很少的 SQL 语句就断开，下次需要的时候再重连。**如果使用的是短连接，在业务高峰期的时候，就可能出现连接数突然暴涨的情况。

- 连接通过连接器建立，还需要权限验证+维护/管理连接
- 短连接的风险
  - 数据库处理的慢一些导致链接数暴涨超出max_connections参数 系统会拒绝连接报错“Too many connections”
- 解决方案
  - 提高max_connections,但是太大导致系统负载过大，大量资源消耗在权限验证等逻辑上
- 可用方案
  - 先处理掉站着连接但是不工作的线程
    - maxconnections不看是否忙碌只看是否存在连接
    - 不需要连接的kill connection主动踢掉
    - wait_timeout:空闲多久MySQL直接断开连接
  - ![](https://static001.geekbang.org/resource/image/90/2a/9091ff280592c8c68665771b1516c62a.png)
    - 如上，sessionA存在未提交事务所以断开连接优先级低
  - 具体处理方案
    - show processlist，看到的结果是这样的。
      - ![](https://static001.geekbang.org/resource/image/ae/25/ae6a9ceecf8517e47f9ebfc565f0f925.png)
    - 图中 id=4 和 id=5 的两个会话都是 Sleep 状态。而要看事务具体状态的话，你可以查 information_schema 库的 innodb_trx 表。
      - ![](https://static001.geekbang.org/resource/image/ca/e8/ca4b455c8eacbf32b98d1fe9ed9876e8.png)
  - 如果是连接数过多，你可以优先断开事务外空闲太久的连接；如果这样还不够，再考虑断开事务内空闲太久的连接。
  - 服务端主动断开 客户端再次使用连接会报错（“ERROR 2013 (HY000): Lost connection to MySQL server during query”。）需要重连否则一直出错
- 第二种方法：减少连接过程的消耗。
  - 有的业务代码会在短时间内先大量申请数据库连接做备用，如果现在数据库确认是被连接行为打挂了，那么一种可能的做法，是让数据库跳过权限验证阶段。
  - 暴露在外网 不推荐
  - 在 MySQL 8.0 版本里，如果你启用–skip-grant-tables 参数，MySQL 会默认把 --skip-networking 参数打开
- 第三种：业务代码申请连接备用 逐步建立连接

### 慢查询性能问题

在 MySQL 中，会引发性能问题的慢查询，大体有以下三种可能：

- 索引没设计好

  - 解决方案:重新构建索引
    - 假设主备模式主A备B
    - 在B执行`set sql_log_bin=off`不写binlog,然后执行alter table语句加上合适索引
    - 主备切换
    - 主库B备库A在A执行`set sql_log_bin=off`不写binlog,然后执行alter table语句加上合适索引
    - 这是一个“古老”的 DDL 方案。平时在做变更的时候，你应该考虑类似 gh-ost 这样的方案，更加稳妥。但是在需要紧急处理时，上面这个方案的效率是最高的。

- SQL语句没写好

  - 第18章

  - 条件字段函数操作/隐式类型转换/字符集转换

  - 比如，语句被错误地写成了 select * from t where id + 1 = 10000，你可以通过下面的方式，增加一个语句改写规则。

    - ```mysql
      
      mysql> insert into query_rewrite.rewrite_rules(pattern, replacement, pattern_database) values ("select * from t where id + 1 = ?", "select * from t where id = ? - 1", "db1");
      
      call query_rewrite.flush_rewrite_rules();
      ```

    - 

  - 

- MySQL选错索引

  - 第10章 这时候，应急方案就是给这个语句加上 force index。

**慢查询解决方案**

- 上线前，在测试环境，把慢查询日志（slow log）打开，并且把 long_query_time 设置成 0，确保每个语句都会被记录入慢查询日志；

- 在测试表里插入模拟线上的数据，做一遍回归测试；在测试表里插入模拟线上的数据，做一遍回归测试；

- 观察慢查询日志里每类语句的输出，特别留意 Rows_examined 字段是否与预期一致。（我们在前面文章中已经多次用到过 Rows_examined 方法了，相信你已经动手尝试过了。如果还有不明白的，欢迎给我留言，我们一起讨论）。

- 如果新增的 SQL 语句不多，手动跑一下就可以。而如果是新项目的话，或者是修改了原有项目的 表结构设计，全量回归测试都是必要的。这时候，你需要工具帮你检查所有的 SQL 语句的返回结果。比如，你可以使用开源工具 pt-query-digest(https://www.percona.com/doc/percona-toolkit/3.0/pt-query-digest.html)。

- 

  

### QPS 突增问题

> 有时候由于业务突然出现高峰，或者应用程序 bug，导致某个语句的 QPS 突然暴涨，也可能导致 MySQL 压力过大，影响服务。

- 一种是由全新业务的 bug 导致的。假设你的 DB 运维是比较规范的，也就是说白名单是一个个加的。这种情况下，如果你能够确定业务方会下掉这个功能，只是时间上没那么快，那么就可以从数据库端直接把白名单去掉。
  - 阻止连接
- 如果这个新功能使用的是单独的数据库用户，可以用管理员账号把这个用户删掉，然后断开现有连接。这样，这个新功能的连接不成功，由它引发的 QPS 就会变成 0。
  - 删除用户（阻止连接
- 如果这个新增的功能跟主体功能是部署在一起的，那么我们只能通过处理语句来限制。这时，我们可以使用上面提到的查询重写功能，把压力最大的 SQL 语句直接重写成"select 1"返回。
  - 可能造成其他啊问题
  - 如果别的功能里面也用到了这个 SQL 语句模板，会有误伤；
  - 很多业务并不是靠这一个语句就能完成逻辑的，所以如果单独把这一个语句以 select 1 的结果返回的话，可能会导致后面的业务逻辑一起失败。
  - 同时你会发现，其实方案 1 和 2 都要依赖于规范的运维体系：虚拟化、白名单机制、业务账号分离。由此可见，更多的准备，往往意味着更稳定的系统。

### 小结

- 短连接
  - 杀掉长时间等待的线程断开连接
  - 客户端提前构建合适连接 自动重连
- 慢查询
  - 使用合适索引
  - 编写合适SQL
  - 应急force index
- QPS暴增
  - 断开连接
  - 删除用户
  - 查询pattern 强制默认返回



