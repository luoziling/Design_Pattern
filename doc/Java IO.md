# Java IO

## 传统BIO

- 单个请求对应单个线程，线程资源消耗严重
- 可通过线程池管理，但仍存在资源浪费情况，建立连接后并不是时刻都忙录
- Input/OutputStream
  - 所有输入输出流的抽象，提供读写功能
  - 代表数据方向
  - 通过ByteArray的具体实现可以看到底层是一个字节数组，然后向着不同的方向流动，input就是从不同地方流入内存，output就是从内存流出
  - 因此借助ByteArray可进行输入/输出流的转换，但是需要注意内存占用
- 



## NIO

- 多路复用器，处理多个连接，通过轮询+事件执行相关操作
- 组件
  - buffer
    - 与channel交互读写数据的缓冲区
    - DriectorByteBuffer 零拷贝，与内核态共享内存 减少不必要的拷贝 大型持久
    - 数据量少考虑受JVM管理的heapBuffer
  - channel
    - 双向的打开连接 一定要借助buffer完成数据读写
    - transferFrom 零拷贝
  - Selector
    - 处理多个channel
- 

## TCP的拆包粘包

- 由于网络协议在TCP层为了防止一次性注入网络的数据量过多，对包进行拆解，确保每个数据包有各自编号不会超出端点的缓冲区大小，另一端接收到之后要粘包数据合并再向上层（应用层）发送处理
- 消息定长：FixedLengthFrameDecoder类 java处理

## Netty

> Netty是 一个异步事件驱动的网络应用程序框架，用于快速开发可维护的高性能协议服务器和客户端。Netty是基于nio的，它封装了jdk的nio，让我们使用起来更加方法灵活。

- NIO的封装，解决传统NIO难用、可靠性低、epoll bug等问题

### Reactor线程模型

- 事件驱动
- 线程池支持
- IO多路复用

#### 单Ractor 单线程

![](https://pic3.zhimg.com/80/v2-5598826468eb205e6a7ba3354367c372_720w.jpg)

- 一个reactor做请求分发
- 建立连接的请求交给acceptor
- 事务处理的请求交给handler
- 缺陷
  - 无法利用多核CPU优势
  - 单线程容易挂

#### 单Reactor多线程

![](https://pic1.zhimg.com/80/v2-d60a5c2c930e3ec611855d387d2429ec_720w.jpg)

- reactor分发请求
- 连接交给acceptor
- 读写交给线程池处理，线程池来管理和维护具体worker thread充分利用多核
- 缺陷：reactor单点并且承担了所有请求 成为性能瓶颈

#### 多Reactor 多线程模型

![](https://pic2.zhimg.com/80/v2-ca0ee6f64ec8654ba143c30548874095_720w.jpg)

- reactor分离，分别处理连接和IO事件
- 

### protobuf

Protobuf，将数据结构以.proto文件进行描述，通过代码生成工具可以生成对应数据结构的POJO对象和Protobuf相关的方法和属性。优点：序列化后码流小，性能高、结构化数据存储格式（XML JSON等）、通过标识字段的顺序，可以实现协议的前向兼容、结构化的文档更容易管理和维护。缺点：需要依赖于工具生成代码、支持的语言相对较少，官方只支持Java 、C++ 、python。适用场景：对性能要求高的RPC调用、具有良好的跨防火墙的访问属性、适合应用层对象的持久化

### Netty 和 Tomcat 的区别

tomcat比较局限 基于http协议的web服务器

netty简化网络编程可以兼容各种协议，也可以作为web服务器