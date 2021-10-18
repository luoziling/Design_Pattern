# 从输入url到页面展示到底发生了什么

## 1.输入地址

浏览器自带缓存/书签，寻找合适url

## 2.浏览器查找域名的IP

- 请求发起，域名解析，本地hosts文件查看是否有域名对应的规则，有就直接用对应配置的IP
- DNS请求本地的DNS服务器，本地服务器一版是ISP（网络服务提供商）提供，比如中国电信/移动
- DNS服务器内置缓存，命中直接返回，否则继续查找，找不到递归发送给其他的DNS服务器查找 例如DNS根服务器
- **根DNS服务器**找不到则重新向**域DNS服务器**发起请求
  - 域DNS服务器返回解析服务器的地址而不是直接返回IP
- 本地DNS服务器向解析DNS服务器发起请求，获取域名/IP的对应关系，本地DNS服务器存储（**缓存**）关系并返回给用户
- ![](https://pic3.zhimg.com/80/v2-367da995706289a83af5c0372d55f43e_720w.jpg)



### 什么是DNS

> DNS（Domain Name System，域名系统），因特网上作为域名和IP地址相互映射的一个分布式数据库，能够使用户更方便的访问互联网，而不用去记住能够被机器直接读取的IP数串。通过主机名，最终得到该主机名对应的IP地址的过程叫做域名解析（或主机名解析）。　　 通俗的讲，我们更习惯于记住一个网站的名字，比如www.baidu.com,而不是记住它的ip地址，比如：167.23.10.2。而计算机更擅长记住网站的ip地址，而不是像[http://www.baidu.com](https://link.zhihu.com/?target=http%3A//www.baidu.com)等链接。因为，DNS就相当于一个电话本，比如你要找[http://www.baidu.com](https://link.zhihu.com/?target=http%3A//www.baidu.com)这个域名，那我翻一翻我的电话本，我就知道，哦，它的电话（ip）是167.23.10.2。

- 用户擅长记录域名，计算机擅长记录IP
- DNS就是一个域名到IP的字典，域名和IP多对多
- 

### DNS查询的两种方式：递归查询和迭代查

- 递归解析
  - 深度遍历 本机向本地DNS服务器发起DNS解析请求
  - 本地DNS服务器不断向下递归查找
  - 分别由本地、根、顶级域、解析域递归查找
  - ![](https://pic3.zhimg.com/80/v2-4415eab38ab3774f85663197b3559942_720w.jpg)
- 迭代解析
  - 迭代与递归的差别在于解析的行为由本地DNS与服务器发起还是由用户发起
  - 广度遍历，本地DNS服务器返回可查找DNS的相关服务器，客户端再向这些服务器发起请求并查询
  - ![](https://pic2.zhimg.com/80/v2-1d2ec667390081157834a69f5a17445d_720w.jpg)
  - 

### DNS域名称空间的组织方式

DNS构成

![](https://pic4.zhimg.com/80/v2-a3aee8aa119440d8b9407fec4b60ab0b_720w.jpg)

![](https://upload-images.jianshu.io/upload_images/16547068-d38044d468d0e657.png?imageMogr2/auto-orient/strip|imageView2/2/w/674/format/webp)

### DNS负载均衡

- 一个域名可以对应多个IP，以此做负载均衡，NGINX可以一个域名对应多个IP+端口确定唯一服务实现负载均衡