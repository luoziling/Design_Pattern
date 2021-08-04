# Netty

## 1.简介

> Netty is an asynchronous event-driven network application framework for rapid development of maintainable high performance protocol servers & clients.
>
> Netty是一种**异步事件驱动**的网络应用程序框架，可快速开发可维护的高性能协议服务器和客户端。

事件驱动:基于事件驱动,根据点击事件调用方法

客户端行为进行事件驱动,连接 断开 读 写

- Netty
- NIO
- JDK IO
- TCP/IP





- TCP协议
- NIO框架



### 1.1场景

1. RPC框架
2. 游戏数据交互
3. 大数据领域 数据聚合传递



## 2.Java BIO编程

IO模型:用什么通道进行数据的发送和接收,决定通信性能

Java的三种IO模型

- BIO
- NIO
- AIO

### 2.1 JavaBIO

- 同步阻塞
- 一个请求一个线程处理
- 线程自身会占用资源,过多线程导致OOM
- 线程切换消耗大导致效率低
- socket连接维护线程导致性能浪费

![image-20210730181726270](Netty.assets/image-20210730181726270.png)

### 2.1 NIO

![image-20210730182214054](Netty.assets/image-20210730182214054.png)

- 单线程维护多个请求从而提高利用率
- 理论基础:单个连接建立连接后不一定一直进行数据交互,空闲时让线程去处理其他连接的请求从而提高效率



### 2.3 AIO

- 异步非阻塞
- 异步通道
- 先经过操作系统判定,有效请求校验
- 链接数多,连接时间长



### 2.4场景

- BIO 连接数量少 固定架构
- NIO 连接数量多,连接操作短,轻架构 聊天服务器 弹幕服务器 服务器之间的通讯
- AIO 连接数多 连接时间长 重操作,相册服务器 充分调用OS进行并发JDK7之后支持
