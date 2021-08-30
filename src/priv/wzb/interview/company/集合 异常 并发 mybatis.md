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
