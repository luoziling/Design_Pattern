# MySQL总结

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/30a5f0863dc6417b980875918e30e55e~tplv-k3u1fbpfcp-zoom-1.image?imageslim)

## 存储引擎

### InnoDB

事务性存储引擎

MVCC支持高并发

默认隔离级别可重复读 REPEATABLE READ，在可重复读级别下通过MVCC + Next-Key Locking（Record Lock + GAP）防止幻读

主索引->聚簇索引，主键索引和非主键索引，在索引中保存数据

聚簇索引并非单独的索引类型而是一种数据存储方式。InnoDB的聚簇索引实际上是在同一个结构中保存了B-Tree索引和数据行

覆盖索引模拟多聚簇索引

一个表只能有一个聚簇索引，因为无法同时把数据行存放在两个不同的地方

聚簇索引优缺点:

- 相关数据保存在一起提高访问效率（减少磁盘IO）
- 数据访问更快。
- 直接使用主键值

缺点:

- 提高IO密集型应用的性能，对数据存放在内存中的情况就显得无效
- 插入速度依赖插入顺序
- 更新聚簇索引列代价高
- 页分裂问题（page split），占用更多空间
- 全表扫描慢，行稀疏，页分裂 的时候扫描慢
- 二级索引(非聚簇索引)占用空间大，包含主键列
- 二级索引两次查找而不是一次，一次主键一次内容？

可预测性读，自适应哈希索引，加速插入的缓冲区

在线热备份

### MyISAM

设计简单，存储紧密。用处:只读数据、表小、可容忍修复操作

大量特性：压缩表、空间数据索引

不支持事务

不支持行级锁，只有表锁，读取所有表加共享锁，写入对表加排他锁

读取时也可插入（并发插入 concurrent insert）

### 对比

- 事务

  Innodb支持事务，使用commit和Rollback

- 并发

  MyISAM表锁，InnoDB行锁

- 外键

  InnoDB支持

- 备份

  InnoDB支持在线热备份

- 崩溃恢复

  MyISAM 崩溃后数据易损坏，修复慢

- 其他特性

  MyISAM支持压缩表和空间数据索引（GIS 三维空间数据）

## 索引

### B+Tree原理

### 数据结构

B-Tree BalanceTree 平衡树，一颗查找树，所有叶子节点位于同一层

B+Tree是B-Tree的变形，基于B树和叶子节点顺序访问指针实现，通常用于数据库和操作系统的文件系统中

所有数据存储在叶子节点，中间节点不保存数据，叶子节点有指针指向下一个叶子节点更方便范围性查找

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/45187333c7174435a91d44d6c1e6c985~tplv-k3u1fbpfcp-zoom-1.image)

### 操作

**查找**

典型的二分查找，自顶向下

**插入**

- 执行搜索以确定新计入存放在哪个存储区(bucket)

- 存储区未满存入元素

- 否则在插入新记录之前，执行存储区分裂（split the bucket）

  分裂：原始节点有「(L+1)/2」个数据（L代表Length

  新节点有「(L+1)/2」个数据

  移动「(L+1)/2」个key到父节点，在父节点插入新节点

  重复直到父节点不需要分裂

- 如果根节点分裂，对其处理就像它有一个空的父级，并按上述操作

B-trees grow as the root and not at the leaves.

**删除**

和插入类似只不过自下而上

### 树的常见特性

**AVL树**

平衡二叉树，用平衡因子插值决定并通过旋转实现，左右子树树高不超过1，红黑树是严格的平衡二叉树，条件严格（树高差只有1），只要插入/删除不满足条件就要旋转/变色保持平衡，耗时严重。**AVL树使用插入/删除少 查找多的场景**这一特性也被其具体实现（红黑树，B/B+树）所继承

**红黑树**

颜色约束，根节点必须是黑色，黑色节点下必须是两个红色子节点，每条路径到子节点长度相同。适合查找少，插入/删除次数多的场景（部分场景用调表（skiplist）代替红黑树）

跳表类似多重索引的概念，最下层是真实数据，逐次向上，层次的提高代表数据跨度的增加，下面的间隔小，约到上面所代表的数据间隔越大

**B/B+树**

多路查找树，出度高，磁盘IO低，用于数据库

### red-black B+Tree的比较

红黑树等AVL实现也可索引文件系统/数据库系统用B+Tree的原因：

- 磁盘IO次数

  B+Tree一个节点存储多个元素，树更加矮胖代表查询效率高，磁盘IO少

- 磁盘预读性

  减少IO每次都预读。预读过程中 磁盘顺序读取，顺序读取不需要进行磁盘寻道。每次读取页的整倍数。

  一次IO读取一页，在内存中完整载入一个节点

### B+Tree B Tree的比较

- B+Tree的磁盘IO更低
- B+Tree查询效率更稳定
- B+Tree元素遍历效率高 

## MySQL索引

索引是在存储引擎层实现的而不是在服务器层，不存存储引擎有不同的索引类型和实现

### B+Tree索引

大多数MySQL存储引擎的默认索引

- 不需要全表扫描
- 有序
- 指定多个索引列，组合索引
- 使用全键值、键值范围和键前缀查找。

InnoDB的B+Tree 主索引和辅助索引

主索引记录完整数据，聚簇索引因为无法把数据行存放在两个不同的地方，所以一个表只能有一个聚簇索引。

辅助索引的叶子节点的 data 域记录着主键的值，因此在使用辅助索引进行查找时，需要先查找到主键值，然后再到主索引中进行查找，这个过程也被称作回表。

### 哈希索引

哈希索引能以 O(1) 时间进行查找，但是失去了有序性：

- 无法用于排序与分组；
- 只支持精确查找，无法用于部分查找和范围查找。

InnoDB 存储引擎有一个特殊的功能叫“自适应哈希索引”，当某个索引值被使用的非常频繁时，会在 B+Tree 索引之上再创建一个哈希索引，这样就让 B+Tree 索引具有哈希索引的一些优点，比如快速的哈希查找。

### 全文索引

MyISAM 存储引擎支持全文索引，用于查找文本中的关键词，而不是直接比较是否相等。查找条件使用 MATCH AGAINST，而不是普通的 WHERE。

全文索引使用倒排索引实现，它记录着关键词到其所在文档的映射。

### 空间数据索引

MyISAM 存储引擎支持空间数据索引（R-Tree），可以用于地理数据存储。空间数据索引会从所有维度来索引数据，可以有效地使用任意维度来进行组合查询。

必须使用 GIS 相关的函数来维护数据。

## 索引优化

### 独立的列

索引列不能是表达式的一部分，否则无法用到索引

### 多列索引

多列查询用多列作为索引效果更好

### 索引列的顺序

区分度最高的放在最左边

区分度查询 当前不重复记录在所有记录中的占比

```sql
SELECT COUNT(DISTINCT staff_id)/COUNT(*) AS staff_id_selectivity,
COUNT(DISTINCT customer_id)/COUNT(*) AS customer_id_selectivity,
COUNT(*)
FROM payment;

```

### 前缀索引

对于 BLOB、TEXT 和 VARCHAR 类型的列，必须使用前缀索引，只索引开始的部分字符。

### 覆盖索引

索引包含所有需要查询的字段的值。

select后的字段正好与索引重复，避免回表

具有以下优点：

- 索引通常远小于数据行的大小，只读取索引能大大减少数据访问量。
- 一些存储引擎（例如 MyISAM）在内存中只缓存索引，而数据依赖于操作系统来缓存。因此，只访问索引可以不使用系统调用（通常比较费时）。
- 对于 InnoDB 引擎，若辅助索引能够覆盖查询，则无需访问主索引。



## 索引的优点

- 减少扫描行
- 避免排序和分组，避免临时表（索引不正确也会产生临时表/filesort
- 随机IO变为顺序IO

## 索引的使用条件

- 小表用全表扫描
- 中大型表，索引有效
- 特大型表维护成本增加

## 查询性能优化

### explain分析select语句

### select_type

常用的有 SIMPLE 简单查询，UNION 联合查询，SUBQUERY 子查询等。

简单、联合、子查询

### table

要查询的表

### possible_keys

可能用到的索引

### key

实际用到的索引

### rows

扫描的行数

### type索引查询类型

const一条记录 ref主键查询 range主键单个字段的辅助索引，多字段辅助索引

**const：使用主键或者唯一索引进行查询的时候只有一行匹配 ref：使用非唯一索引 range：使用主键、单个字段的辅助索引、多个字段的辅助索引的最后一个字段进行范围查询 index：和all的区别是扫描的是索引树 all：扫描全表：**

#### system

表只有一行

#### const

使用主键/唯一索引只有一行匹配

```sql
SELECT * FROM tbl_name WHERE primary_key=1;

SELECT * FROM tbl_name
  WHERE primary_key_part1=1 AND primary_key_part2=2;

```

#### eq_ref

连接查询使用主键/唯一索引只匹配到一行

```sql
SELECT * FROM ref_table,other_table
  WHERE ref_table.key_column=other_table.column;

SELECT * FROM ref_table,other_table
  WHERE ref_table.key_column_part1=other_table.column
  AND ref_table.key_column_part2=1;

```

#### ref

使用非唯一索引

```sql
SELECT * FROM ref_table WHERE key_column=expr;

SELECT * FROM ref_table,other_table
  WHERE ref_table.key_column=other_table.column;

SELECT * FROM ref_table,other_table
  WHERE ref_table.key_column_part1=other_table.column
  AND ref_table.key_column_part2=1;

```

#### range

用主键、单个字段的辅助索引，多个字段的辅助索引在最后一个字段进行范围匹配（索引字段全用到

```sql
SELECT * FROM tbl_name
  WHERE key_column = 10;

SELECT * FROM tbl_name
  WHERE key_column BETWEEN 10 and 20;

SELECT * FROM tbl_name
  WHERE key_column IN (10,20,30);

SELECT * FROM tbl_name
  WHERE key_part1 = 10 AND key_part2 IN (10,20,30);

```

#### index

只扫描索引树

1）查询的字段是索引的一部分，覆盖索引。 2）使用主键进行排序

#### all

触发条件：全表扫描，不走索引

## 优化数据访问

### 减少请求的数据量

- 返回必要列，不用select * 
- 返回必要行，用limit（可用where后加clue配合limit来使用索引过滤大部分无效行
- 缓存重复查询的数据

### 减少服务端扫描的行

使用索引来覆盖查询

## 重构查询方式

### 切分大查询

一个大查询如果一次性执行的话，可能一次锁住很多数据、占满整个事务日志、耗尽系统资源、阻塞很多小的但重要的查询。

### 分解大连接查询

连接查询造成笛卡尔积，查询数据量指数级上涨， 最好join表不超过三张

否则在服务端进行逻辑拆分查询

将一个大连接查询分解成对每一个表进行一次单表查询，然后在应用程序中进行关联，这样做的好处有：

- 让缓存更高效。对于连接查询，如果其中一个表发生变化，那么整个查询缓存就无法使用。而分解后的多个查询，即使其中一个表发生变化，对其它表的查询缓存依然可以使用。
- 分解成多个单表查询，这些单表查询的缓存结果更可能被其它查询使用到，从而减少冗余记录的查询。
- 减少锁竞争；
- 在应用层进行连接，可以更容易对数据库进行拆分，从而更容易做到高性能和可伸缩。
- 查询本身效率也可能会有所提升。例如下面的例子中，使用 IN() 代替连接查询，可以让 MySQL 按照 ID 顺序进行查询，这可能比随机的连接要更高效。

## 事务

满足ACID特性的一组操作，通过commit提交一个事务，使用Rollback进行回滚

### ACID

- Atomicity

  事务被视为不可拆分的最小执行单元要么全部执行成功，要么全部失败

  undo log

- Consistency

  所有事务对一个数据读取结果都相同

- Isolation

  一个事务在所做修改提交前对其他事务不可见

  mvcc + lock

- Durability

  事务提交所作修改永远保存在数据库中，及时崩溃，事务执行结果也不能丢

  redo log

### ACID的关系

C是最基本的通过AI来保证C

事务的 ACID 特性概念很简单，但不好理解，主要是因为这几个特性不是一种平级关系：

- 只有满足一致性，事务的结果才是正确的。
- 在无并发的情况下，事务串行执行，隔离性一定能够满足。此时只要能满足原子性，就一定能满足一致性。在并发的情况下，多个事务并行执行，事务不仅要满足原子性，还需要满足隔离性，才能满足一致性。
- 事务满足持久化是为了能应对数据库崩溃的情况。

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/e8ab3258ed8d49de99b02cfeeda0d3a3~tplv-k3u1fbpfcp-zoom-1.image)

### 隔离级别

- 未提交读（Read umcommitted)

  事务中的修改即使未提交，对其他事务也可见

- 提交读（READ COMMITTED）

  只能读取已经提交的事务所作的修改。未提交前的修改对其他事务不可见

- 可重复读（REPEATABLE READ）

  一个事务多次读取结果一直

- 串行化（SERLIZABLE）

  事务串行执行，读取数量也一致

强制事务串行执行。

需要加锁实现，而其它隔离级别通常不需要。

| 隔离级别 | 脏读 | 不可重复读 | 幻影读 |
| -------- | ---- | ---------- | ------ |
| 未提交读 | √    | √          | √      |
| 提交读   | ×    | √          | √      |
| 可重复读 | ×    | ×          | √      |
| 可串行化 | ×    | ×          | ×      |

## 锁

用于管理对共享资源的并发访问

### 类型

- 共享锁

  允许事务读取一行数据

  对读开放对写关闭

- 排他锁

  允许事务删除/更新一行数据

  读写都关闭

- 意向共享锁

  事务想要获得一张表中几行的共享锁

- 意向排他锁

  事务想要获得一张表中几行的排他锁

### MVCC

多版本并发控制MVCC（Multi-Version Concurrency Control），是 MySQL 的 InnoDB 存储引擎实现隔离级别的一种具体方式，用于实现提交读和可重复读两种隔离级别，而未提交读隔离级别总是读取最新的数据行，无需使用 MVCC。可串行化隔离级别需要对所有读取的行都**加锁**，单纯使用 MVCC 无法实现。

#### 基础概念

**版本号**

- 系统版本号

  递增的数字，每开启一个事务版本号自动递增

- 事务版本号

  事务开始时的事务版本号

**隐藏的列**

MVCC在每行记录后增加两个列用于存储两个版本号

- 创建版本号

  指示创建一个数据行快照时的系统版本号

- 删除版本号

  快照的删除版本号大于当前事务说明行快照有效（事务创建的时候数据未被删除)否则快照已被删除

**Undo log**

MVCC 使用到的快照存储在 Undo 日志中，该日志通过回滚指针把一个数据行（Record）的所有快照连接起来。

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/de91fc2eaabb45beaea338d0cc3ceb7b~tplv-k3u1fbpfcp-zoom-1.image)

#### 实现过程

以下实现过程针对可重复读隔离级别。

当开始一个事务时，该事务的版本号肯定大于当前所有数据行快照的创建版本号，理解这一点很关键。数据行快照的创建版本号是创建数据行快照时的系统版本号，系统版本号随着创建事务而递增，因此新创建一个事务时，这个事务的系统版本号比之前的系统版本号都大，也就是比所有数据行快照的创建版本号都大。

- SELECT

  读取事务T数据创建版本号小于T的版本号（否则数据未创建）

  删除版本号大于或未定义（否则数据已被删除不可用）

- INSERT

  将当前系统版本号作为数据行快照的创建版本号。

- DELETE

  将当前系统版本号作为数据行快照的删除版本号。

- UPDATE

  将当前系统版本号作为更新前的数据行快照的删除版本号，并将当前系统版本号作为更新后的数据行快照的创建版本号。可以理解为先执行 DELETE 后执行 INSERT。

**快照读和当前读**

在可重复读级别中，通过MVCC机制，虽然让数据变得可重复读，但我们读到的数据可能是历史数据，是不及时的数据，不是数据库当前的数据！这在一些对于数据的时效特别敏感的业务中，就很可能出问题。

对于这种读取历史数据的方式，我们叫它快照读 (snapshot read)，而读取数据库当前版本数据的方式，叫当前读 (current read)。很显然，在MVCC中：

**历史读和当前读**

**快照读**

MVCC 的 SELECT 操作是快照中的数据，不需要进行加锁操作。

```sql
select * from table ….;
```

**当前读**

MVCC 其它会对数据库进行修改的操作（INSERT、UPDATE、DELETE）需要进行加锁操作，从而读取最新的数据。可以看到 MVCC 并不是完全不用加锁，而只是避免了 SELECT 的加锁操作。

```sql
INSERT;
UPDATE;
DELETE;
```

在进行 SELECT 操作时，可以强制指定进行加锁操作。以下第一个语句需要加 S 锁，第二个需要加 X 锁。

```sql
select * from table where ? lock in share mode;
select * from table where ? for update;
```

## 锁算法

#### Record Lock

锁定一个记录上的索引，而不是记录本身。

如果表没有设置索引，InnoDB 会自动在主键上创建隐藏的聚簇索引，因此 Record Locks 依然可以使用。

#### Gap Lock

锁定索引之间的间隙，但是不包含索引本身。例如当一个事务执行以下语句，其它事务就不能在 t.c 中插入 15。

```sql
SELECT c FROM t WHERE c BETWEEN 10 and 20 FOR UPDATE;
```

#### Next-Key Lock

它是 Record Locks 和 Gap Locks 的结合，不仅锁定一个记录上的索引，也锁定索引之间的间隙。例如一个索引包含以下值：10, 11, 13, and 20，那么就需要锁定以下区间：

```sql
(-∞, 10]
(10, 11]
(11, 13]
(13, 20]
(20, +∞)
```

> **在 InnoDB 存储引擎中，SELECT 操作的不可重复读问题通过 MVCC 得到了解决，而 UPDATE、DELETE 的不可重复读问题通过 Record Lock 解决，INSERT 的不可重复读问题是通过 Next-Key Lock（Record Lock + Gap Lock）解决的。**



## 锁问题

### 脏读

脏读指的是不同事务下，当前事务可以读取到另外事务未提交的数据。

例如：

T1 修改一个数据，T2 随后读取这个数据。如果 T1 撤销了这次修改，那么 T2 读取的数据是脏数据。

**一次读，读到未提交数据**

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/d1079ab7329d4b1ca5a276f355440a5f~tplv-k3u1fbpfcp-zoom-1.image)

### 不可重复读

不可重复读指的是同一事务内多次读取同一数据集合，读取到的数据是不一样的情况。

例如：

T2 读取一个数据，T1 对该数据做了修改。如果 T2 再次读取这个数据，此时读取的结果和第一次读取的结果不同。

**两次读，中间数据被修改**

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/d5771057ee6a44a2839429b580802eea~tplv-k3u1fbpfcp-zoom-1.image)

在 InnoDB 存储引擎中，SELECT 操作的不可重复读问题通过 MVCC 得到了解决，而 UPDATE、DELETE 的不可重复读问题是通过 Record Lock 解决的，INSERT 的不可重复读问题是通过 Next-Key Lock（Record Lock + Gap Lock）解决的。



### Phantom Proble（幻影读）

> The so-called phantom problem occurs within a transaction when the same query produces different sets of rows at different times. For example, if a SELECT is executed twice, but returns a row the second time that was not returned the first time, the row is a “phantom” row.

Phantom Proble 是指在同一事务下，连续执行两次同样的 sql 语句可能返回不同的结果，第二次的 sql 语句可能会返回之前不存在的行。

幻影读是一种特殊的不可重复读问题。

两次读，中间事务有增/删

#### 丢失更新

一个事务的更新操作会被另一个事务的更新操作所覆盖。

例如：

T1 和 T2 两个事务都对一个数据进行修改，T1 先修改，T2 随后修改，T2 的修改覆盖了 T1 的修改。



![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/f8c004ec55fc437ebe9fed66a99ed120~tplv-k3u1fbpfcp-zoom-1.image)

## 分库分表数据切分

### 水平切分

同样的数据表，多张相同表存储部分数据减少单表压力，分布式节点存储

水平切分又称为 Sharding，它是将同一个表中的记录拆分到多个结构相同的表中。

当一个表的数据不断增多时，Sharding 是必然的选择，它可以将数据分布到集群的不同节点上，从而缓存单个数据库的压力。

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/7dcbec66819e4f048d664673ee1c55dd~tplv-k3u1fbpfcp-zoom-1.image)

### 垂直切分

根据逻辑/密集度进行表拆分

垂直切分是将一张表按列分成多个表，通常是按照列的关系密集程度进行切分，也可以利用垂直气氛将经常被使用的列喝不经常被使用的列切分到不同的表中。

在数据库的层面使用垂直切分将按数据库中表的密集程度部署到不通的库中，例如将原来电商数据部署库垂直切分称商品数据库、用户数据库等。


![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/e578e11489f743e8982dbb0626598aa3~tplv-k3u1fbpfcp-zoom-1.image)

### Sharding 策略

- 哈希取模：hash(key)%N
- 范围：可以是 ID 范围也可以是时间范围
- 映射表：使用单独的一个数据库来存储映射关系

### Sharding 存在的问题

**事务问题**

使用分布式事务来解决，比如 XA 接口

**连接**

可以将原来的连接分解成多个单表查询，然后在用户程序中进行连接。

**唯一性**

- 使用全局唯一 ID （GUID）
- 为每个分片指定一个 ID 范围
- 分布式 ID 生成器（如 Twitter 的 Snowflake 算法）

## 复制

### 主从复制

三个线程bin\IO\SQL

bin写入bin log

IO读取bin LOG放入 relay log

SQL读取relay log并执行

主要涉及三个线程：binlog 线程、I/O 线程和 SQL 线程。

- binlog 线程 ：负责将主服务器上的数据更改写入二进制日志（Binary log）中。
- I/O 线程 ：负责从主服务器上读取- 二进制日志，并写入从服务器的中继日志（Relay log）。
- SQL 线程 ：负责读取中继日志，解析出主服务器已经执行的数据更改并在从服务器中重放（Replay）。



![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/f0239cdc98e44a848b01e7d08b6c7dee~tplv-k3u1fbpfcp-zoom-1.image)

### 读写分离

主服务器处理写操作以及实时性要求比较高的读操作，而从服务器处理读操作。

读写分离能提高性能的原因在于：

- 主从服务器负责各自的读和写，极大程度缓解了锁的争用；
- 从服务器可以使用 MyISAM，提升查询性能以及节约系统开销；
- 增加冗余，提高可用性。

读写分离常用代理方式来实现，代理服务器接收应用层传来的读写请求，然后决定转发到哪个服务器。

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/3c3841dbdc1e42a5a04fa39627525066~tplv-k3u1fbpfcp-zoom-1.image)

## 范式

- 1NF

  每个属性不可再分

- 2NF

  消除部分依赖

- 3NF

  消除传递依赖