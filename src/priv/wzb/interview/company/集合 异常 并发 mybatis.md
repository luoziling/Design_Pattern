### 集合

- ConcurrentHashMap采用1.7采用分段锁机制减小锁粒度，提高性能
- 1.8采用数组+连表+CAS+synchronized ，改为只锁头节点，进一步细画锁，除非hash冲突过多，否则性能较好
- hashMap的put
  - 判断是否初始化，为空则初始化
  - 映射到指定node，node为空则直接初始化作为头节点
  - node存在则判断是否树化，树化交给红黑树处理
  - 遍历连表尝试找到key，找到了则更新
  - 找不到 头插法，
  - 判断是否需要树化
- concurrentHashMap的put
  - 判断是否初始化，为空则初始化
  - 映射到指定node，node为空则直接初始化作为头节点，使用CAS保证线程安全
  - 判断是否需要重置大小，判断是否树化
  - 锁定头节点，开始遍历，寻找是否有相同key
  - 找到并且onlyIfAbsent为false则更新，onlyIfAbsent用于判断对象是否存在，可用于对象去重
  - 找不到则尾插法
  - 判断是否需要树化
- sleep wait park
  - sleep 会被打断，不释放锁，不需要持有线程锁
  - wait释放锁，获取对象锁才能调用对象.wait
  - park/unpark和wait/notify的区别，都是先休眠再唤醒，wait一定要在notify之前，unpark可在park之前
- 集合是存放数据的容器
- 快速存取 提供一系列有效API编译管理
- fail-fast是指使用迭代器遍历，若集合元素变更抛出ConcurrentModificationException 
- ArrayList的序列化和反序列化由内部提供的write/readObject方法实现，由于ArrayList的扩容机制，导致其内部存在类似占位符的节点，这些节点不需要被序列化使用transient标识就代表使用重定义方法进行序列化从而节省时间和空间
- blocking Queue用于生产者/消费者模型中的数据队列，通过特定API实现读写的安全，当操作无法执行可以进行额外操作避免错误操作，抛出异常/返回true/false / 阻塞



### 异常

- Throwable是基础接口，异常区分
    - error
        - JVM oom stackoverflow
    - exception
        - 编译时
            - ClassCastException
            - IOException
        - 运行时
            - RuntimeException
                - NPE
                - IndexOutOfBounds
                - ConcurrentModificationException
                -
- 最佳实践
    - finally清理资源
    - 优先处理明确异常
    - 文档说明
    - 描述性信息抛出异常
    - 不要捕获throwable类因为可能是error，本质上不支持处理（因为处理不了
    - 不要忽略异常，当前不处理，之后也不会处理
    - 可通知预检而不是try-catch来处理异常
    - 异常不要用作流程
    - 异常范围限定，不要大段
    - 捕获是为了处理，统一异常处理+转化
    - 可以就用try-with-resource
    - 不要在finallyreturn，造成逻辑问题，finally在return之前执行
    - RPC可用throwable来捕获 NoSuchMethodError
    -

### 并发

- 多核优势

- 提高并发

- 复杂，问题多，死锁上下问题切换内存泄漏

- 三要素

    - 原子
        - atomic
        - synchronized
        - lock
    - 可见 线程改动可见性
        - synchronized
        - volatile
        - lock
    - 有序 防止指令重排序
        - Happens-Before 规则可以解决有序性问题

- 线程和进程

    - 线程是程序运行的最小单位，进程是资源分配的最小单位，一个进程多个线程，多线程可数据共享
    - 进程切换开销大，线程开销小
    - 进程影响隔离，线程可能相互影响

- 上下文切换

    - 多个线程并发执行，线程见存在切换，需要保存线程和恢复现场

- linux

    - top获取进程
    - jstack进行分析获取指定文件

- 死锁

    - 双方都持有一部分又去请求另一部分
        - 请求和保持（一次申请所有)
            - 持有一个去请求另一个
        - 循环等待（申请顺序）
            - 死循环阻塞
        - 不可剥夺(申请不到主动释放自身资源)
            - 只能等待锁释放
        - 互斥（无法破坏)
            - 锁对象排他，一个资源只能给一个锁
            -

- 为什么我们调用 start() 方法时会执行 run() 方法，为什么我们不能直接调用 run() 方法？

    - start是让线程进入就绪状态等待CPU分配时间片运行
    - run方法不会开启子线程，同步，在主线程的方法调用

- callable产生结果 future获取结果

- FutureTask 可放到线程池中被执行，集成callable

- ![](https://imgconvert.csdnimg.cn/aHR0cHM6Ly91c2VyLWdvbGQtY2RuLnhpdHUuaW8vMjAxNy8xMi8xNS8xNjA1OWNjOTFlZThlZmIz?x-oss-process=image/format,png)

- 调度机制

    - 分时
    - 抢占 饿死

-  wait(), notify()和 notifyAll()必须在同步方法或者同步块中被调用

- 锁竞争，wait就是锁竞争，必须等待notify的锁释放

- sleep不考虑优先级 分时 yield 考虑 抢占

- interrupted 和 isInterrupted

    - interrupted  设置中断标识位
    - 获取中断标识位

- notifyall释放所有到锁池

- 共享变量，注意多线程的并发问题

- 锁方法锁当前对象，锁越小越好

- synchronized原理

    - 添加monitorenter/monitorexit两个指令
    - 两个monitorexit是为了避免线程异常不释放锁
    -

- AQS(abstractQueuedSynchronizer 抽象同步器队列)

    - 构建锁和同步器的框架，例如reentrantLock CountdownLatch ReentrantReadWriteLock

    - 原理：多线程请求资源，资源空闲则可被获取，其他未获取资源的线程进入一个虚拟队列等待资源变为可获取

        - 分类：独占/共享

        - 独占 reentrantlock

        - 共享 countdownlatch

        - 底层模板方法模式

            - ```java
        isHeldExclusively()//该线程是否正在独占资源。只有用到condition才需要去实现它。
        tryAcquire(int)//独占方式。尝试获取资源，成功则返回true，失败则返回false。
        tryRelease(int)//独占方式。尝试释放资源，成功则返回true，失败则返回false。
        tryAcquireShared(int)//共享方式。尝试获取资源。负数表示失败；0表示成功，但没有剩余可用资源；正数表示成功，且有剩余资源。
        tryReleaseShared(int)//共享方式。尝试释放资源，成功则返回true，失败则返回false。
        ```
        
            -
        
        - 自定义同步器只需重写获取/释放资源的方法，其他入队出队等交给AQS

### 抽象类和接口

- 接口定义了行为，描述了某个类必须具有什么行为
- 而抽象类更偏向代码复用，公共代码放到抽象类，抽象类无法实现的可变行为可以由子类实现，并且抽象类的方法种可以调用其他抽象方法，这些抽象方法是没有实现的，所以抽象类无法实例化，这些抽象方法被子类实现后就实现了模板方法模式



### mybatis

- 基础是由sqlSessionFactory生产sqlSession，由SqlSession去处理增删改查逻辑
- sqlSession调用executor去执行实际查询代码
- 基础的查询被封装为mappedStatement
- cache：一样的SQL+缓存
- 源码执行流程
    - 通过sqlsessionFactory创建sqlSession
        - 创建过程中创建具体executor，若开启二级缓存则返回cachingExecutor装饰对象
    - 根据Statement不同类型进入sqlSession不同方法，如果是`Select`语句的话，最后会执行到`SqlSession`的`selectList`
    -  `sqlSession`把查询委托给executor,如果只开启一级缓存则调用`BaseExecutor`的query方法
    - 根据`MappedStatement`的Id、SQL的offset、SQL的limit、SQL本身 构成cachekey
    - 只要`Statement Id + Offset + Limmit + Sql + Params`相等就可以认为是同一条SQL即 查询的语句是调用的哪个方法，查询的SQL，SQL的参数、分页参数
    - `BaseExecutor`先从缓存查询，查不到就去数据库查询
        - 数据库查询后会写入localCache
        - 若语句级别则清空localCache
        - update会清空localCache 使缓存失效
        -



#### 一级缓存

![](https://awps-assets.meituan.net/mit-x/blog-images-bundle-2018a/6e38df6a.jpg)

- MyBatis一级缓存的生命周期和SqlSession一致。

- MyBatis一级缓存内部设计简单，只是一个没有容量限定的HashMap，在缓存的功能性上有所欠缺。

- MyBatis的一级缓存最大范围是SqlSession内部，有多个SqlSession或者分布式的环境下，数据库写操作会引起脏数据，建议设定缓存级别为Statement。

    - 多个sqlSession一个修改对另一个不可见，导致缓存的是旧数据，置为STATEMENT(无效化一级缓存)

- 一级缓存在SqlSession下的executor中

- 配置 默认SESSION 开启

    - ```properties
    mybatis.configuration.local-cache-scope=SESSION/STATEMENT
    ```
    
    -



#### 二级缓存

![](https://awps-assets.meituan.net/mit-x/blog-images-bundle-2018a/28399eba.png)

- 多个sqlSession共享

- 都开启 二级=》一级=》数据库

- 配置 默认true

- ```properties
  mybatis.configuration.cache-enabled
  ```

- 配置

-
要正确的使用二级缓存，需完成如下配置的。

    1. 在MyBatis的配置文件中开启二级缓存。

```xml
<setting name="cacheEnabled" value="true"/>
```

    1. 在MyBatis的映射XML中配置cache或者 cache-ref 。

cache标签用于声明这个namespace使用二级缓存，并且可以自定义配置。

```xml
<cache/>   
```

    - `type`：cache使用的类型，默认是`PerpetualCache`，这在一级缓存中提到过。
    - `eviction`： 定义回收的策略，常见的有FIFO，LRU。
    - `flushInterval`： 配置一定时间自动刷新缓存，单位是毫秒。
    - `size`： 最多缓存对象的个数。
    - `readOnly`： 是否只读，若配置可读写，则需要对应的实体类能够序列化。
    - `blocking`： 若缓存中找不到对应的key，是否会一直blocking，直到有对应的数据进入缓存。

`cache-ref`代表引用别的命名空间的Cache配置，两个命名空间的操作使用的是同一个Cache。

```xml
<cache-ref namespace="mapper.StudentMapper"/>
```

- 二级缓存 只有sqlSession commit后才生效

- 同样的update操作会清理缓存

- 二级缓存同样基于namespace 多个xml无法共享

    - 为了解决实验4的问题呢，可以使用Cache ref，让`ClassMapper`引用`StudenMapper`命名空间，这样两个映射文件对应的SQL操作都使用的是同一块缓存了。

- 源码

    - 创建sqlSession
        - 附带创建executor，由于开启二级缓存返回cachingExecutor
        -
    - 执行查询操作 sqlSession委托给executor执行
        - 一级缓存通过`BaseExecutor`内置的localCache实现
        - 二级缓存则通过`TransactionalCacheManager`实现
    - 首先查询缓存是否命中
        - `TransactionalCache`实现了Cache接口，`CachingExecutor`会默认使用他包装初始生成的Cache，作用是如果事务提交，对缓存的操作才会生效，如果事务回滚或者不提交事务，则不对缓存产生影响。
        - 传递到PerpetualCache 没查到放入miss集合统计命中率
    - 未命中查询数据库并放入缓存
        - 先放入待commit的map中
        - 最终在flushCacheIfRequired方法内将待提交map数据放入transactionManager的缓存中
    - update操作刷新缓存 清理特定的缓存key
    -

- 小结

    - MyBatis的二级缓存相对于一级缓存来说，实现了`SqlSession`之间缓存数据的共享，同时粒度更加的细，能够到`namespace`级别，通过Cache接口实现类不同的组合，对Cache的可控性也更强。

    - MyBatis在多表查询时，极大可能会出现脏数据，有设计上的缺陷，安全使用二级缓存的条件比较苛刻。

    - 在分布式环境下，由于默认的MyBatis Cache实现都是基于本地的，分布式环境下必然会出现读取到脏数据，需要使用集中式缓存将MyBatis的Cache接口实现，有一定的开发成本，直接使用Redis、Memcached等分布式缓存可能成本更低，安全性也更高。

    - mybatis 扫描配配置文件解析为mappedStatement,的map id为接口全限定名+方法名，因此方法无法重载

        - 调用时实际上是调用代理生成的对象，通过id找到对应的语句执行

    - id是否可以重复

        - 不同namespace下可以重复 namespace+方法名不能重复



- xml映射，语句paramter会聚合到parameterMap result聚合到resultMap 查询则聚合到mappedStatement

- 结果映射 忽略大小写

    - resultMap
    - as

- include解析

    - A引用B
    - 若发现B未加载，则加载B再完成加载A所以可以任意放置

- 动态sql

    - 自动SQL拼接 代码复用
    - foreach if trim where  使用OGNL表达式逻辑判断动态SQL拼接

- #{}和${}

    - #可以放置sql注入 预编译 占位符
    - $直接拼接





### MySQL

- 组合索引就是一个多字段多条件排序规则的索引，以最左侧为最优先排序项所以有了最左前缀原则，一定要从最左侧开始，并且中间匹配不能断，遇到范围会停止后续匹配
- explain
  - id 相同从上到下，不同从大到小
  - type
    - system 系统只有一行
    - const
      - 唯一匹配 主键/唯一索引 +常量
    - eq_ref
      - 唯一索引 主键/唯一索引
    - ref
      - 非唯一索引
    - range
      - 范围查询 用到索引 但是遇到范围查询
    - index
      - 覆盖索引
      - 排序分组
  - key 实际用到的索引
  - key_len前缀索引
  - rows 扫描行数
  - extra 额外信息
    - using index 覆盖索引
    - using temporary 临时表 排序 分组
      - order by 和 group by列不同
    - using filesort 
      - 文件排序 order by后没用到索引
    - 
  - 
- B+tree
  - 所有数据存储在叶子节点
  - 叶子节点相连构成连表方便连续范围查询
  - 非叶子节点存储索引方便遍历
- 索引设计原则
  - 区分度高
  - 查询频率高，修改频率低
  - 不能建太多
  - 前缀索引
    - 外键索引							
  - 最左前缀匹配
  - 使用
    - where
    - join on
    - order by
  - 创建索引的列最好不要存在null isnull is not null不走索引
- 创建方式
  - 创建表时指定 所有字段后的key primary key 
  - alter table xxx add index idx_x()
  - create index idx_x on table_x()
  - 删除
    - alter table drop index 
- 使用索引查询一定能提高查询的性能吗
  - 不一定，若索引创建不合适，不一定会命中
  - 索引增加增删改的性能消耗
- 百万级别或以上的数据如何删除
  - 数据量大的时候索引越多删除越慢
  - 先删除索引，再删除数据
  - 再重新创建索引
- 前缀索引
  - 计算字符串类型的区分度，并不一定要为整个长度的字符串都建立索引
  - 通过计算前缀区分度公式来判断前缀长度
- hash与B+树
  - hash等值更快，不支持范围
  - 不支持排序
  - 回表
  - hash碰撞 不稳定
- B+树而不是B树
  - B+树底层支持顺序
  - B+树空间利用率高 非叶子节点都是索引 查询更快 减少IO次数，单次IO读取数据有限
  - B+树稳定
- 聚簇索引和覆盖索引
  - innodb下 只有主键索引是举措索引，索引的key和整条记录存储在叶子节点中，其他索引是非聚簇索引，只存储特定列的key+列的具体数据
  - 覆盖索引是指非聚簇索引查询时select后的数据和索引列相同
- 

#### 事务

- 数据库事务
  - 保证操作的ACID特性
  - A atomic
    - 原子性，要么都成功，要么都失败 undolog
  - C consistency
    - 数据一致性，由AID来保证C，数据状态一致
  - I isolation
    - 隔离性 事务隔离级别，mvcc+锁
  - D duration
    - 持久性 redolog + double write buffer,改动永久出故障也不影响
- 脏读？幻读？不可重复读
  - 脏读
    - 读取到另一个事务未提交的数据，锁释放在事务提交后放置脏读
  - 幻读
    - 事务多次读取读取到的数据行数或内容不同，另一个事务提交了增删 串行化
  - 不可重复读
    - 多个事务同时执行，一个事务内的两次读取读取到的数据不一致，另一个事务提交了修改，通过mvcc解决 隐藏列，ABA问题 时间戳/版本号
- 隔离级别
  - read umcommitted
    - 读取未提交
  - read committed
    - 读取已提交
      - 防止脏读
  - repeatable read
    - 可重复读
      - 防止不可重复读
  - serializable
    - 串行
      - 防止幻读
  - 默认可重复读

#### 锁

多事务并发执行，导致数据不一致，加锁保证数据一致性。

- 隔离级别与锁

  - read uncommitted无锁，事务执行数据会不一致
  - read committed  读锁，对修改关闭，修改过程中其他事务可以读取，事务执行完毕后释放锁
  - repeatable read mvcc 乐观锁机制，通过版本号来解决ABA问题 undo log实现数据多版本 同一个事务多次读取数据相同
  - serializable 写锁，锁定整个范围数据直到操作完成才放开

- 行锁和表锁

  - 锁定行记录，开销大，冲突低，会出现死锁 类似记录锁，锁定某条node连表

    - 行锁基于索引

      - ```mysql
        select * from tab_with_index where id = 1 for update;
        ```

      - 

  - 表锁 开销小容易冲突，类似分段锁，段就是表

- 共享和排他

  - 读锁 支持读，不能写 
  - 写锁 不支持读写

- 锁算法

  - record lock 记录锁
  - gap lock 间隙锁 范围锁
  - next-key lock 记录锁+间隙锁

- 死锁

  - 持久部分资源去申请另一部分资源
  - 请求和保持
    - 一次锁定所有资源 提升锁粒度
  - 互斥
  - 顺序访问
    - 以相同顺序访问
  - 不可剥夺

- 乐观和悲观

  - 乐观 CAS/MVCC 读多写少 共享锁
  - 悲观锁 独占锁 写多

- 

#### SQL

- SQL语句主要分为哪几类

  - DDL 数据库定义 create drop alter
  - DML 操作 update delete insert
  - DQL select
  - DCL 控制 COMMIT ROLLBACK

- 超键、候选键、主键、外键分别是什么？

  - 包含候选键和主键 唯一标识元组属性
  - 唯一的key，最小超键
  - 通过主键可以找到其他任何字段，候选键可能是主键 不能未null
  - 外键 放在其他表中的主键

- SQL 约束有哪几种？

  - not null
  - UNIQUE
  - PRIMARY
  - FOREIGN
  - CHECK

- 六种关联查询

  - ![](https://images2017.cnblogs.com/blog/1035967/201709/1035967-20170907174926054-907920122.jpg)
  - inner join 
  - left join
  - right join
  - union

- 子查询

  - 嵌套查询，一条SQL依赖另一条SQL
  - 

- 子查询的三种情况

  - where后
  - inner join后
  - select 后

- mysql中 in 和 exists 区别

  - in类似双重循环，in后面的数据量不宜过多
  - exist则类似调用exist函数，这个函数去B表查询，可以用到索引
  - 两个集合大小差不多则效率差不多
  - 子查询表大勇exist否则用in
  - not in无法用到索引，此时用exist

- varchar与char的区别

  - varchar变长 char定长，char最多存储256 则varchar 接近2^16
  - 数据量少且固定用char更好，若变长且经常操作则用varchar

- varchar(50)中50的涵义

  - 限定字符串最大字符数，由于变长，因此存储长度不会马上分配50个字符，但排序会因为定义的越大而造成影响

- int(20)中20的涵义

  - 宽度20 只有zerofill时才会前面加0存储

- FLOAT和DOUBLE的区别是什么

  - 单精度和双精度 4字节和8字节

- drop、delete与truncate的区别

  - drop 删除表 速度快 不可恢复
  - truncate 清空表 速度快从0开始记录id 表结构还在数据清空
  - delete DML 删除记录 速度慢 可回滚

- union和union all

  - Union：对两个结果集进行并集操作，不包括重复行，同时进行默认规则的排序；

    Union All：对两个结果集进行并集操作，包括重复行，不进行排序；

- 

#### SQL优化

- 优化

  - 慢查询日志
  - explain 指定SQL
    - explain提供了SQL语句的具体执行过程分析，包括用到了什么索引，扫描了多少数据，是否使用文件排序和临时表等信息
  - 分析
    - 最左前缀原则
    - 覆盖索引
    - 范围查询后无法用到
    - 少用is not null
    - != <>无法用到索引
    - 索引列不能函数计算
    - or用不到
    - Like 左百分号用不到
  - 构建 索引
  - 测试

- SQL的生命周期

  - 建立连接
  - 拿到sql
  - 执行计划
  - 处理
  - 返回
  - 关闭连接

- 大表数据查询，怎么优化

  - 优化schema,SQL语句+索引
  - 二级缓存 redis
  - 主从复制 读写分离
  - 垂直拆分 系统模块拆分
  - 水平拆分 sql中尽量带sharding key

- 超大分页怎么处理

  - 通过子查询进行ID过滤后再查询
  - 需求层面，不做跳过几百万条数据的查询

- mysql 分页

  - limit a,b 查询a+b 返回b行
  - limit a 查询限制a行
  - limit a offset b 越过b行查询a

- 慢查询日志

  - slow_query_log

    > 可以使用`show variables like ‘slov_query_log’`查看是否开启，如果状态值为`OFF`，可以使用`set GLOBAL slow_query_log = on`来开启，它会在`datadir`下产生一个`xxx-slow.log`的文件。

  - 设置临界时间`long_query_time`

- 为什么要尽量设定一个主键 主键使用自增ID还是UUID

  - 加快数据检索，数据有序 唯一
  - 推荐自增ID 主键索引在innodb中是聚簇索引，底层存储数据，若遇到范围连续的查询则有序更好

- 字段为什么要求定义为not null

  - 占用更多字节，程序运行null出错

- 如果要存储用户的密码散列，应该使用什么字段进行存储

  - 定长散列，salt，身份证定长用char

#### 数据库优化

- 为什么要优化

  - 数据多处理速度慢，导致响应慢影响用户体验，加大处理资源投入

- 数据库优化

  - 表结构设计优化
    - 大表拆小表
    - 创建关联表
    - SQL反模式
    - 字段选择优化
    - 增加冗余字段
  - 

- MySQL数据库cpu飙升到500%的话他怎么处理

  -  show processlist 查看在运行的SQL 看索引缺失还是数据量过大
  - 调整链接数

- 大表查询

  - 限定范围
  - 读写分离
  - 缓存
  - 垂直分区
    - 利用主键拆分 适用于一列常用一些不常用
    - 优势:单表数据量减少
    - 劣势：出现冗余需要join
  - 水平分区
    - 数据量不变但是分表/分库 分片数据
    - 优势：数据量减少
    - 劣势：增加复杂度，分片事务，操作跨表等问题
    - 适用于表数据独立
    - 操作
      - 客户端代理 sharding-JDBC
      - 中间件代理 mycat
    - 
  - 分库分表的问题
    - 事务
      - 编程上实现逻辑事务
    - 跨表join
      - 分库分表产品
    - 跨界点聚合
      - 与join类似节点查询聚合
    - 数据迁移，容量规定
      - 2的倍数取余，向前兼容，类似map扩容
    - ID
      - 分布式递增ID生成
  - 

- MySQL的复制原理以及流程

  - 通过固定策略（每秒 每次事务提交） 写入binlog
    - 从库拉取binlog进行重放
  - **主从复制的作用**
    - 健壮
    - 读写分离
    - 高可用和故障切换
  - 原理
    - ![](https://imgconvert.csdnimg.cn/aHR0cHM6Ly91c2VyLWdvbGQtY2RuLnhpdHUuaW8vMjAxOC85LzIxLzE2NWZiNjgzMjIyMDViMmU?x-oss-process=image/format,png)
    - 主库生成binlog 
    - 从库IO线程拉取远程拉取 存入中继日志，
    - 从库SQL线程重放中级日志
  - 读写分离有哪些解决方案
    - 基于主从复制，主库写，从库读，每个从库只能依赖一个主库
    - mysql-proxy
    - AbstractRoutingDataSource+aop+annotation
    - AbstractRoutingDataSource+aop+annotation在service层决定数据源，可以支持事务. AOP拦截，注意AOP失效问题
  - 

- 备份计划，mysqldump以及xtranbackup的实现原理

  - 视库的大小来定，一般来说 100G 内的库，可以考虑使用 mysqldump 来做，因为 mysqldump更加轻巧灵活，备份时间选在业务低峰期，可以每天进行都进行全量备份(mysqldump 备份出来的文件比较小，压缩之后更小)。
  - 100G 以上的库，可以考虑用 xtranbackup 来做，备份速度明显要比 mysqldump 要快。一般是选择一周一个全备，其余每天进行增量备份，备份时间为业务低峰期。
  - 

- **备份恢复时间**

  - 20G的2分钟（mysqldump）

    80G的30分钟(mysqldump)

    111G的30分钟（mysqldump)

    288G的3小时（xtra)

    3T的4小时（xtra)

- **备份恢复失败如何处理**

  - 首先在恢复之前就应该做足准备工作，避免恢复的时候出错。比如说备份之后的有效性检查、权限检查、空间检查等。如果万一报错，再根据报错的提示来进行相应的调整。

- 原理

  - mysqldump属于逻辑备份。加入–single-transaction 选项可以进行一致性备份 通过MVCC逻辑备份
  - Xtrabackup:
    - 物理备份占用空间，文件拷贝 不拷贝binlog redolog+现有数据

- 数据表损坏的修复方式有哪些

  - 修复前将mysql服务停止
  - 打开命令行方式，然后进入到mysql的/bin目录
  - 执行myisamchk –recover 数据库所在路径/*.MYI

- 



### 系统

#### command

- 本体
  - 活动管理
  - 攻击队项目管理
    - 文件管理
  - 云安全事件管理
  - 区域和接口人配置
  - shiro框架优化
- 定时任务
  - 数据同步 平台上下游关系
  - 基于redis stream特性的简要消息队列开发
- 大屏
  - 数据查询
  - 经纬度偏移，省市区基础数据，数据字典



#### permission

- 自身
  - 多系统校验机制
  - 多系统基础RBAC功能支持
  - 
- 对外
  - 菜单及功能权限
  - 对外角色绑定功能



