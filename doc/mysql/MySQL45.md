# MySQL45

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

