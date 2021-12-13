# Demo

app name及token的添加导致无法访问

未出现状况

## 任务调度中心架构设计

### 需求分析

要建立一个整体性的多平台的任务调度中心，

其他平台用户只需要登录调度中心进行简单配置即可运行其任务

系统的交互必须事前相互交互确定

底层的交互确定后（接口调用验证，HTTP请求，定时任务需对外暴露接口），指挥平台需要定时去调用其他平台的接口/触发自身需求的任务

底层能交互

其他平台只需注册定时相关信息，按指挥平台提供 

测试调度成功

尝试分环境挑选配置文件并配置各任务执行

```yaml
command:
    url: http://127.0.0.1:8082/task/
    consumer:
        getCustomerAddress: consumer/getCustomerAddress
        getCustomer: consumer/getCustomer
        listCustomer: consumer/listCustomer
        getHwLog: consumer/getHwLog
    employee:
        getExternalEmployee: employee/getExternalEmployee
    position:
        outMember: position/outMember
    sync:
        employeeClock: sync/employeeClock
        replenishData: sync/replenishData
        deviceData: sync/deviceData
        deviceWorkOrder: sync/deviceWorkOrder
        location: sync/location
        organization: sync/organization
        position: sync/position
        employee: sync/employee
        employeeAll: sync/employeeAll
```

非注入到spring容器导致无法获取配置文件内容

{"uri":"sync/employeeClock","code":1}

使用setter注入static变量的方式

https://blog.csdn.net/ZYC88888/article/details/87863038

定时任务配置，与console的启动时间及接口保持一致



添加验证码验证功能

xxl-job自带的登录验证与记住我：

获取前端参数封装post请求给后端接口

后端接到后先校验用户名密码格式规范时候准确，然后根据用户名查密码

如果查到且匹配判断是否需要记住我

有rememberme就把用户名密码序列化为json传然后转为16禁止存储进cookie

要加验证码就在进入登录界面时后端自动生成验证码，传递给前端并渲染验证

通过UUID生成唯一key

存入redis

将key与验证码图片返回给前端

传到后端多加一个验证码参数

前端就加一个ajax获取验证码图片的js代码，login自动序列化参数给后端

后端新增返回验证码图片的方法，与redis建立连接，存储验证码参数

```
var verKey;
// 获取验证码
$.get(base_url + '/captcha', function(res) {
    verKey = res.key;
    $('#verImg').attr('src', res.image);
},'json');
```