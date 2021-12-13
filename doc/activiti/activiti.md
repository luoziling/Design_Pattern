# activiti

Java 工作流引擎

例子:

请假：员工申请——部门经理——总经理——人事存档

自动化流程控制

作用：流程自动化控制

## 发展思路

- 先入门，搞个入门demo（已完成
- 入门总结以及深入学习API，可接触底层源码，了解实现思想及其概念
- activiti7看似强依赖spring security，尝试剔除这种强依赖，尝试与指挥平台用的shiro框架整合，如果不成功，由于是独立项目是否能去学习security自己开发一套基于security的用户登录认证与授权管理
- 做出分享



好像不需要整合mybatis之类的，我们只调用activiti提供的方法，由它去调用数据库连接，无需关心activiti与数据库的交互，有其他需求也可以引入

## 工作流

工作流(Workflow)，就是通过计算机对业务流程自动化执行管理。它主要解决的是“使在多个参与者 之间按照某种预定义的规则自动进行传递文档、信息或任务的过程，从而实现某个预期的业务目标， 或者促使此目标的实现”。



没有工作流引擎之前，之前为了实现流程控制，通常的做法就是采用状态字段的值来跟踪流程变化情况。这样不同角色的用户通过状态字段的取值来决定记录是否显示和处理。

这是一种原始的方式，业务稍有改动，相关代码变动较大导致大量重复开发，版本迭代累计后还容易产生各种逻辑错误，使用工作流对流程式业务进行管理控制后，可减少代码改变，流程的变更依赖可自由配置的bpmn流程图文件。

使用acitiviti7带来的优秀特性：

- 抽取复杂的业务流程使用专门的流程建模语言（BPMN2.0）进行定义
- 业务系统按照预先定义的流程执行
- 业务流程交给activiti进行管理，减少业务系统由于流程变动进行系统升级改造的工作量
- 提高系统健壮性
- 减少系统开发维护成本
- 执行工程中涉及的数据由activiti自带的数据库表记录自动执行创建于存储，使用该框架使用者与数据库解耦，只需调用activiti提供的API可间接操控数据
- 项目开源免费
- 可实现业务自动化
- 帮助企业解决分布式，高度可扩展且经济高效的基础架构中的自动化挑战。





工作流系统：具有工作流的系统，系统具有流程的自动化管理功能

工作流系统的实现：

工作流，状态机？

可通过编码控制状态取值来判断流程执行步骤

缺陷：业务变更后程序整体变动太大

区分变与不变

将不变的流程控制过程拆分出

Activiti->业务流程变化后，程序代码不变

SaaS 人力资源管理

行政审批(调薪)

更新配置文件/数据库中（业务流程图） 减少代码变动

解耦，节点信息存入数据库，流程的变动不影响代码，代码只要控制开始、中间处理、结束

**activiti核心工作机制**

1. 流程图画好
2. 流程图中的节点放入表中
3. 读取第一条记录，处理并推进

## 使用过程

1. 规范化业务流程图
2. 本质上是一个xml文件
3. 读取业务流程图过程就是解析XML文件的过程
4. 读取一业务流程图中的节点相当于解析一个xml结构，进一步插入mysql表中，形成一条记录
5. 将所有节点读取都读取并存入mysql
6. 后面只要读取mysql记录即可，读一条记录先当与读一个节点
7. 业务流程的推进转换为读表的数据并处理
8. 结束时只有一行，这一行数据就可删除

## 什么是Activiti7

BPM（Business Process Management）业务流程管理

监控流程，优化流程，提高办事效率

BPM软件

BPMN（Business Process Model And Notation） 业务流程模型与符号

idea自带集成

使用BPMN画图，使用activiti解析，自定义逻辑编码和处理

成本预算

公司开发软件的规模会去决定是否使用activiti

25张表

1. activiti整合到项目
2. 实现业务流程建模，使用BPMN实现业务流程
3. 部署业务流程到activiti
4. 启动流程实例
5. 用户查询待办任务
6. 处理待办任务
7. 结束流程

## 入门小结

工作流：业务流程的自动化管理

BPMN：业务流程建模与标注（Business Process Model and Notation，BPMN) ，描述流程的基本符号，包括这些图元如何组合成一个业务流程图（Business Process Diagram）

流程定义：请假流程：填写请假单——部门经理——财务经理——总经理

安装流程设计器actiBPM

为什么使用activiti可实现业务代码不变更实现流程更新？

流程定义图做更新——先读取节点信息到数据库表中——后续就针对数据库表进行操作

多张数据表的创建支持activiti的工作

Java代码

ProcessEngineConfiguration以及ProcessEngine

创建ProcessEngineConfiguration对象脱机方式StandaloneProcessEngineConfiguration

databaseSehemaUpdate = true自动创建表

## 表名命规则

![image-20201014141623751](D:\Learning\公司学习\activiti\activiti.assets\image-20201014141623751.png)

## 架构图

![image-20201014141639402](D:\Learning\公司学习\activiti\activiti.assets\image-20201014141639402.png)

通过API对工作流25张表自动化操作无需手动变更

与spring boot整合后由于其自动配置的特性，ProcessEngine根据yaml中的各种配置自动生成并注入IOC容器，后续主要调用架构图中提到的7种服务执行流程自动化管理即可

虽然自动生成了数据库中的表但无需直接对表进行操作，通过activiti框架提供的各service调用其接口就可隐式操作数据库

**在7版本中IdentityService FormService两个service已经删除**

## Service

总览

| Service           | describe                 |
| ----------------- | ------------------------ |
| RepositoryService | activiti的资源管理类     |
| RuntimeService    | activiti的流程运行管理类 |
| TaskService       | activiti的任务管理类     |
| HistoryService    | activiti的历史管理类     |
| ManagerService    | activiti的引擎管理类     |

- RepositoryService

  activiti的资源管理类，提供了管理和控制流程发布包和流程定义的操作。使用工作流建模工具涉及的业务流程图需要使用此service将流程定义文件的内容部署到计算机。

  除了部署流程定义以外还可以： 

  查询引擎中的发布包和流程定义。 

  暂停或激活发布包，对应全部和特定流程定义。 暂停意味着它们不能再执行任何操作了，激活 是对应的反向操作。 

  获得多种资源，像是包含在发布包里的文件， 或引擎自动生成的流程图。 

  获得流程定义的 pojo 版本， 可以用来通过 java 解析流程，而不必通过 xml。

  **将bpmn文件中绘制的流程图部署到activiti自带的数据库中**

- RuntimeService

  它是 activiti 的流程运行管理类。可以从这个服务类中获取很多关于流程执行相关的信息

  **根据工作流开启工作流实例（类似Java中类和对象**

  部署的工作流是类，可创建工作流实例对象

- TaskService

  是 activiti 的任务管理类。可以从这个类中获取任务的信息。

  **获取当前已开启的工作流信息**

- HistoryService

  是 activiti 的历史管理类，可以查询历史信息，执行流程时，引擎会保存很多数据（根据配置），比 如流程实例启动时间，任务的参与者， 完成任务的时间，每个流程实例的执行路径，等等。 这个 服务主要通过查询功能来获得这些数据。

- ManagerService

  是 activiti 的引擎管理类，提供了对 Activiti 流程引擎的管理和维护功能，这些功能不在工作流驱动 的应用程序中使用，主要用于 Activiti 系统的日常维护





bpmn转图片

### 流程定义

使用actiBPM插件绘制工作流图像文件

可转为jpg

注意插件的bug

如果xmlns=“”解决了还有问题就清空数据库把，数据库存在残留数据（生产环境肯定不能这么做）

指定流程的key

![image-20201020190934431](D:\Learning\公司学习\activiti\activiti.assets\image-20201020190934431.png)

指定负责人、多个负责人、负责的用户组（security中的概念）

![image-20201020191039153](D:\Learning\公司学习\activiti\activiti.assets\image-20201020191039153.png)

在idea插件中有bug看不到修改，用notepad++打开就能看到是否成功保存了修改

```java
/**
 * 测试自动生成数据库的表
 */
@Test
public void testGenTable(){
   // 条件：processEngineConfiguration的bean
   // 看来经过yaml配置springboot的IOC容器中已经存在该bean
   System.out.println(processEngine);
}
```

### 流程部署

**部署流程定义就是要将上边绘制的图形即流程定义（.bpmn）部署在工作流程引擎 activiti 中**

- 获取repositoryService
- 部署对象
- 添加bpmn文件/图片文件/其他部署信息（name，key...）
- 执行部署
- 输出部署信息

```java
/**
 * 流程定义部署
 * repositoryService
 * 影响的activiti表
 * act_re_deployment 新增部署信息
 * act_ge_bytearray    保存静态文件信息bpmn/png
 * act_re_procdef 流程定义的信息（其中的KEY_是流程id
 */
@Test
public void deployment(){
   // 原流程：创建ProcessEngine对象-> 获得RepositoryService
   // 直接注入获得
   // 进行部署
   // 简化开发，减少大量数据库操作的Java代码
   Deployment deployment = repositoryService
         .createDeployment()
         // 添加bpmn资源
         .addClasspathResource("processes/holiday.bpmn")
         // 添加png资源
         .addClasspathResource("processes/holiday.png")
         // 添加工作流名称
         .name("请假申请单流程")
         // act_re_deployment中的key
         .key("holiday")
         .deploy();
   // 输出部署信息
   System.out.println("deployment.getId() = " + deployment.getId());
   System.out.println("deployment.getKey() = " + deployment.getKey());
   System.out.println("deployment.getName() = " + deployment.getName());
}
```

<bpmndi:BPMNDiagram

也会出现xmlns参数的问题

可能是数据库问题

可能是png图片问题

部署成功

deployment.getId() = 5b3104be-12bd-11eb-88aa-0433c2acfca1
deployment.getKey() = null
deployment.getName() = 请假申请单流程

数据库问题

存在残留数据

添加key

deployment.getId() = df6be098-12bd-11eb-886d-0433c2acfca1
deployment.getKey() = holiday
deployment.getName() = 请假申请单流程

使用KEY_来标识唯一部署工作流

在deployment的时候需添加key



### 流程实例

一个流程定义可对应多个流程实例

![image-20201020182957207](D:\Learning\公司学习\activiti\activiti.assets\image-20201020182957207.png)

开启一个工作流获取流程实例信息

**流程定义部署在 activiti 后就可以通过工作流管理业务流程了，也就是说上边部署的请假申请流 程可以使用了。**

针对该流程，启动一个流程表示发起一个新的请假申请单，这就相当于 java 类与 java 对象的关 系，类定义好后需要 new 创建一个对象使用，当然可以 new 多个对象。对于请假申请流程，张三发 起一个请假申请单需要启动一个流程实例，请假申请单发起一个请假单也需要启动一个流程实例。

- 获取runtimeService
- 根据流程定义key启动流程

```java
/**
 * 前提完成流程部署
 * 启动一个流程实例
 * runtimeService
 * 涉及的表
 * act_hi_actinst 工作流创建/完成时间 已完成的活动
 * act_hi_identitylink 工作流与执行人 参与者
 * act_hi_procinst 现存工作流信息 流程实例
 * act_hi_taskinst 任务执行者信息 任务实例
 * act_ru_execution 执行
 * act_ru_identitylink 当前参与者
 * act_ru_task 工作流当前任务，当前任务执行者， 开始/执行时间
 *
 */
@Test
public void startProcessInstance(){
   // 获取RuntimeService
   // 根据流程定义key启动流程，创建流程实例
   // bpmn中的id->key
   ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holiday");
   // 输出实例信息
   System.out.println("processInstance = " + processInstance);
   System.out.println("processInstance.getDeploymentId() = " + processInstance.getDeploymentId());
   // 唯一标识流程的id
   System.out.println("processInstance.getId() = " + processInstance.getId());
   // 活动中的具体节点
   System.out.println("processInstance.getActivityId() = " + processInstance.getActivityId());
   System.out.println("processInstance.getProcessDefinitionId() = " + processInstance.getProcessDefinitionId());
}
```

processInstance = ProcessInstance[0ff83edd-12c0-11eb-be22-0433c2acfca1]
processInstance.getDeploymentId() = null
processInstance.getId() = 0ff83edd-12c0-11eb-be22-0433c2acfca1
processInstance.getActivityId() = null

生成唯一id

在task中可看到

his的procinst中也可以看到

processInstance = ProcessInstance[c1f71bce-12c0-11eb-9802-0433c2acfca1]
processInstance.getDeploymentId() = null
processInstance.getId() = c1f71bce-12c0-11eb-9802-0433c2acfca1
processInstance.getActivityId() = null
processInstance.getProcessDefinitionId() = holiday:3:df892c9b-12bd-11eb-886d-0433c2acfca1

有重复key的情况下寻找最近部署的工作流



### 任务查询

**流程启动后，各各任务的负责人就可以查询自己当前需要处理的任务，查询出来的任务都是该用户 的待办任务。**

- 设定任务负责人（其实是当前登录者
- 获取taskService
- 根据负责人/（负责人+流程实例key）
- 查询当前登录用户负责的流程信息

```java
/**
 * 查询负责人，当前登录用户点击流程的时候要查询是否有要做的任务
 * taskService
 */
@Test
public void TaskQuery(){
   // 获取taskService
   // 根据流程定义key及负责人assignee来实现当前用户的任务列表查询
   List<Task> taskList = taskService.createTaskQuery()
         // 流程key
         .processDefinitionKey("holiday")
         // 执行者（当前登录人员信息
         .taskAssignee("zhangsan")
         // 转为任务列表
         .list();

   // 任务列表展示
   for (Task task : taskList) {
      // 流程实例id
      // act_ru_execution
      System.out.println("task.getProcessInstanceId() = " + task.getProcessInstanceId());
      // 任务id
      // act_ru_task
      System.out.println("task.getId() = " + task.getId());
      // 任务负责人
      System.out.println("task.getAssignee() = " + task.getAssignee());
      // 任务名称
      System.out.println("task.getName() = " + task.getName());
   }
}
```

task.getProcessInstanceId() = 0ff83edd-12c0-11eb-be22-0433c2acfca1
task.getId() = 0fffb8f1-12c0-11eb-be22-0433c2acfca1
task.getAssignee() = zhangsan
task.getName() = 填写请假申请单
task.getProcessInstanceId() = c1f71bce-12c0-11eb-9802-0433c2acfca1
task.getId() = c1fdd292-12c0-11eb-9802-0433c2acfca1
task.getAssignee() = zhangsan
task.getName() = 填写请假申请单

可以看到启动多工作流后登陆人员可以看到多个流程信息（自己要执行的处理流程

### 完成任务

可以看到工作流实例产生的任务在当前任务执行完后向前推进了

act_ru_task

![image-20201021095031614](D:\Learning\公司学习\activiti\activiti.assets\image-20201021095031614.png)

act_hi_actinst

![image-20201021095345345](D:\Learning\公司学习\activiti\activiti.assets\image-20201021095345345.png)

根据唯一标识的流程实例执行ID可看到流程的推进与每项节点的完成时间

这张表中的EXECUTION_ID_字段代表一整个完整的工作流实例（用于标识，识别

act_hi_identitylink

![image-20201021095927417](D:\Learning\公司学习\activiti\activiti.assets\image-20201021095927417.png)

标识出下一个任务节点处理者信息

act_hi_taskinst

该表变动与actinst代表的差不多

![image-20201021100121304](D:\Learning\公司学习\activiti\activiti.assets\image-20201021100121304.png)

act_ru_execution

增加记录

act_ru_identitylink

流程变动（处理人员变动

![image-20201021100442231](D:\Learning\公司学习\activiti\activiti.assets\image-20201021100442231.png)

多次运行完成某条工作流实例

上面完成张三的某条任务的处理

接下去查询lisi要处理的任务并完成

查询结果：

task.getProcessInstanceId() = 0ff83edd-12c0-11eb-be22-0433c2acfca1
task.getId() = bfa39c8c-133f-11eb-bf2e-0433c2acfca1
task.getAssignee() = lisi
task.getName() = 部门经理审批

可以看到流程在不断推进

![image-20201021101038036](D:\Learning\公司学习\activiti\activiti.assets\image-20201021101038036.png)





**OA那边申请流程要填写的一些具体信息可以借鉴一下**



查询并完成任务

taskList = [Task[id=7bfc176b-1342-11eb-af48-0433c2acfca1, name=总经理审批]]

任务执行完成后可以看到ru中task中只剩一条记录，之前任务的执行流程保存在hi中

act_ru_task

![](D:\Learning\公司学习\activiti\activiti.assets\image-20201021102104336.png)

act_hi_actinst

![image-20201021102137189](D:\Learning\公司学习\activiti\activiti.assets\image-20201021102137189.png)

runtime相关表记录都清除，hi表中有完整流程

### 部署压缩包

结果

deploy.getName() = zipDeployment
deploy.getId() = 8002fd7d-1346-11eb-8d59-0433c2acfca1
deploy.getKey() = zip



act_re_deployment

在deployment中可看到key跟着设定的走了

![image-20201021104106963](D:\Learning\公司学习\activiti\activiti.assets\image-20201021104106963.png)

act_re_procdef

这张表中展现了新的部署资源直接是名字没有路径

![image-20201021104225052](D:\Learning\公司学习\activiti\activiti.assets\image-20201021104225052.png)

act_ge_bytearray

![image-20201021104308689](D:\Learning\公司学习\activiti\activiti.assets\image-20201021104308689.png)

activiti内部自动解压缩了

zip更适合上传

多文件上传部署

测试多bpmn压缩部署

结果：

deploy.getName() = zipDeployment
deploy.getId() = 7ab112c5-1348-11eb-81cd-0433c2acfca1
deploy.getKey() = zip

支持多bpmn文件压缩后的多部署

![image-20201021105431585](D:\Learning\公司学习\activiti\activiti.assets\image-20201021105431585.png)

act_re_deployment 某次部署后的部署记录

act_re_procdef 流程记录

act_ge_bytearray 部署文件记录



## 使用小结

**架构说明**

- ProcessEngineConfiguration

  加载activiti.cfg.xml配置文件，与spring boot整合后自动加载配置注入到IOC容器

- ProcessEngine

  快速获得各接口，生成activiti环境及25张表

- Service接口

  RepositoryService

  RuntimeService

  TaskService

  HistoryService



**BPMN的activitiDesigner插件**

**画出流程定义图**

**部署定义**

单个文件

repositoryService.createDeployment().addclasspathResource/addzipxxx

多文件压缩

**启动流程实例**

runtimeService.startProcessInstanceByKey("【某个部署过的工作流key】")

**查看任务**

任务=工作流实例

可根据任务key+用户 查询可执行的任务列表，主要作用根据当前登录用户查询可完成的工作流程

taskService.createTaskQuery

**完成任务**

taskService.complete("【query中查出的某任务ID】")

也就是act_hi_actinst中的EXECUTION_ID_字段用于标识任务

**通过各种service及其接口解耦用户直接操作25张表的复杂过程，转为API操作流程**

## 流程定义

流程定义是线下按照 **bpmn2.0** 标准去描述 业务流程，通常使用 **activiti-explorer**（web 控制台） 或 activiti-eclipse-designer 插件对业务流程进行建模，这两种方式都遵循 bpmn2.0 标准。本教程使用 activiti-eclipse-designer 插件完成流程建模。使用 designer 设计器绘制流程，会生成两个文件：.bpmn 和.png

### BPMN文件

BPMN 2.0 根节点是 definitions 节点。 这个元素中，可以定义多个流程定义（不过我们建议每 个文件只包含一个流程定义， 可以简化开发过程中的维护难度）。 注意，definitions 元素 最少也要 包含 xmlns 和 targetNamespace 的声明。 targetNamespace 可以是任意值，它用来对流程实例进行分 类。

流程定义部分：定义了流程每个结点的描述及结点之间的流程流转。

 流程布局定义：定义流程每个结点在流程图上的位置坐标等信息。



### png 图片文件

通过idea转为xml文件并使用自带的Dagram即可完成自动转换

### 流程定义部署

可将bpmn（+png）两个文件部署到activiti（的数据库）中

```java
/**
 * 流程定义部署
 * repositoryService
 * 影响的activiti表
 * act_re_deployment 新增部署信息
 * act_ge_bytearray    保存静态文件信息bpmn/png
 * act_re_procdef 流程定义的信息（其中的KEY_是流程id
 */
@Test
public void deployment(){
   // 原流程：创建ProcessEngine对象-> 获得RepositoryService
   // 直接注入获得
   // 进行部署
   // 简化开发，减少大量数据库操作的Java代码
   Deployment deployment = repositoryService
         .createDeployment()
         // 添加bpmn资源
         .addClasspathResource("processes/holiday.bpmn")
         // 添加png资源
         .addClasspathResource("processes/holiday.png")
         // 添加工作流名称
         .name("请假申请单流程")
         // act_re_deployment中的key
         .key("holiday")
         .deploy();
   // 输出部署信息
   System.out.println("deployment.getId() = " + deployment.getId());
   System.out.println("deployment.getKey() = " + deployment.getKey());
   System.out.println("deployment.getName() = " + deployment.getName());
}
```

### 部署压缩包

可将多个文件添加到zip格式的压缩包中一起部署

在spring boot整合后每次启动会自动部署processes包下的bpmn文件

```java
    /**
    * 部署压缩zip文件（bpmn+jpg）
    * act_re_deployment
    * act_re_procdef
    * act_ge_bytearray
    */
   @Test
   public void deployZipTest() throws FileNotFoundException {
      // 定义zip输入流
      InputStream inputStream = this
            .getClass()
            .getClassLoader()
            .getResourceAsStream("processes/holidayBPMN.zip");
//    ZipInputStream zipInputStream = new ZipInputStream(inputStream);
      // 这里目录需要从项目根目录开始
//    ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream("src/main/resources/processes/holidayBPMN.zip"));
      ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream("src/main/resources/processes/processes.zip"));

      // 获取repositoryService
      // 流程部署
      Deployment deploy = repositoryService
                        .createDeployment()
                        .addZipInputStream(zipInputStream)
                        .name("zipDeployment")
                        .key("zip")
                        .deploy();
      System.out.println("deploy.getName() = " + deploy.getName());
      System.out.println("deploy.getId() = " + deploy.getId());
      System.out.println("deploy.getKey() = " + deploy.getKey());
   }
```



部署相关数据库操作

流程定义部署后操作 activiti 数据表如下： 

SELECT * FROM act_re_deployment #流程定义部署表，记录流程部署信息 

SELECT * FROM act_re_procdef #流程定义表，记录流程定义信息 

SELECT * FROM act_ge_bytearray #资源表



act_re_deployment 和 act_re_procdef 一对多关系，一次部署在流程部署表生成一条记录，但一次部署 可以部署多个流程定义，每个流程定义在流程定义表生成一条记录。每一个流程定义在 act_ge_bytearray 会存在两个资源记录，bpmn 和 png。



建议：一次部署一个流程，这样部署表和流程定义表是一对一有关系，方便读取流程部署及流程定义信息。



### 流程定义查询

流程定义将来肯定在前端展示，现在只用于流程定义的查询

processDefinitionQuery查询流程定义信息

```java
/**
 * 流程定义查询
 */
@Test
public void queryProcessDefinitionTest(){
   // 流程定义key

   // 获取repositoryService
   // 查询流程定义
   // 获取ProcessDefinitionQuery对象，一个查询器
   ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
   // 设置查询条件：流程定义的key
   // KEY在不同表中代表不同含义
   // act_re_deployment中是部署时.key设置的
   // act_re_procdef是BPMN文件中id标识的
   // orderByProcessDefinitionAppVersion设置排序方式，根据流程定义的版本号排序
   List<ProcessDefinition> holiday = processDefinitionQuery
                              .processDefinitionKey("holiday")
                              .orderByProcessDefinitionAppVersion()
                              .desc()
                              .list();
   for (ProcessDefinition processDefinition : holiday) {
      // 遍历查询结果
      System.out.println("流程定义id：" + processDefinition.getId());
      System.out.println("流程定义名称：" + processDefinition.getName());
      System.out.println("流程定义key：" + processDefinition.getKey());
      System.out.println("流程定义版本：" + processDefinition.getVersion());
   }
   // 根据输出信息可知，流程信息是act_re_procdef表中的数据


}
```

运行结果

流程定义id：holiday:1:5925f27d-12bd-11eb-88aa-0433c2acfca1
流程定义名称：请假流程
流程定义key：holiday
流程定义版本：1
流程部署id：58fa9cb9-12bd-11eb-88aa-0433c2acfca1
流程定义id：holiday:2:5b38a5e1-12bd-11eb-88aa-0433c2acfca1
流程定义名称：请假流程
流程定义key：holiday
流程定义版本：2
流程部署id：5b3104be-12bd-11eb-88aa-0433c2acfca1
流程定义id：holiday:3:df892c9b-12bd-11eb-886d-0433c2acfca1
流程定义名称：请假流程
流程定义key：holiday
流程定义版本：3
流程部署id：df6be098-12bd-11eb-886d-0433c2acfca1
流程定义id：holiday:4:801fad40-1346-11eb-8d59-0433c2acfca1
流程定义名称：请假流程
流程定义key：holiday
流程定义版本：4
流程部署id：8002fd7d-1346-11eb-8d59-0433c2acfca1
流程定义id：holiday:5:7ad64e08-1348-11eb-81cd-0433c2acfca1
流程定义名称：请假流程
流程定义key：holiday
流程定义版本：5
流程部署id：7ab112c5-1348-11eb-81cd-0433c2acfca1





流程可删除



### 流程定义删除

根据流程部署id删除指定流程

如果有正在执行的流程实例那么会删除失败

```java
  /**
   * Deletes the given deployment.
   * 
   * @param deploymentId
   *          id of the deployment, cannot be null.
   * @throwns RuntimeException if there are still runtime or history process instances or jobs.
   */
  void deleteDeployment(String deploymentId);
```

```java
/**
 * 删除已有的流程定义信息
 * RuntimeException if there are still runtime or history process instances or jobs.
 * 当我们正在执行的者一套流程没完全审批结束的时候，次数如果要删除流程定义信息就会失败
 * 如果公司层面要强制删除，可用repositoryService.deleteDeployment("id",true);
 * 参数true代表级联删除，此时先删除每完成的流程任务节点，最后就可以删除流程定义信息、
 * 默认false
 */
@Test
public void deleteProcessDefinitionTest(){
   // 获取repositoryService
   // 执行删除流程定义
   // 参数代表deploymentId流程部署id
   repositoryService.deleteDeployment("58fa9cb9-12bd-11eb-88aa-0433c2acfca1");

}
```

查看影响的表

act_re_procdef 删除了对应的部署信息（根据key+version判断

act_re_deployment 删除了一条部署信息

act_ge_bytearray 删除了信息

对hi的表不做影响

**说明**

- 使用repositoryService删除流程定义
- 如果该流程定义下没有正在运行的流程实例则可用普通删除
- 如果该流程顶一下存在已经运行的流程实例，使用普通删除报错，可用级联删除方法将流程及相关记录全部删除。项目开发中使用级联删除的清空较多，删除操作一般只开放给超级管理员使用
- 能否逻辑删除呢

### 流程定义资源查询

通过流程定义对象获取流程定义资源，获取bpmn和png

pom文件alt+ins实现自动导包generate

```java
/**
 * Gives access to a deployment resource through a stream of bytes.
 * 
 * @param deploymentId
 *          id of the deployment, cannot be null.
 * @param resourceName
 *          name of the resource, cannot be null.
 * @throws ActivitiObjectNotFoundException
 *           when the resource doesn't exist in the given deployment or when no deployment exists for the given deploymentId.
 */
InputStream getResourceAsStream(String deploymentId, String resourceName);
```

```java
/**
 * 从activiti的act_ge_bytearray中读取两个资源文件
 * 将两个资源文件保存到硬盘D:\Learning\公司学习\activiti\bpmn
 * 技术方案：
 *     使用activiti的API
 *     原理层面：使用jdbc的对blob类型，clob类型的数据读取并保存
 *     IO流转换：commons-io.jar包可轻松解决
 * 真实应用场景：用户想查看请假流程具体有哪些步骤要走？
 */
@Test
public void queryBpmnFileTest() throws IOException {
   // 得到repositoryService
   // 得到查询器（流程定义信息)processDefinitionQuery对象
   ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
   // 设置查询条件
   // 参数是流程定义的key
   processDefinitionQuery.processDefinitionKey("holiday");
   // 执行查询操作,查询出想要的流程定义
   List<ProcessDefinition> list = processDefinitionQuery.list();
   System.out.println("list = " + list);
   // 通过流程定义信息得到部署id
   String deploymentId = list.get(0).getDeploymentId();
   // 获取png图片资源的名称
   String pngName = list.get(0).getDiagramResourceName();
   // 获取bpmn资源名
   String resourceName = list.get(0).getResourceName();
   // 通过repositoryService的方法，实现读取图片信息及bpmn文件信息（inputStream）
   // 部署id，资源名
   InputStream pngIs = repositoryService.getResourceAsStream(deploymentId, pngName);
   InputStream bpmnIs = repositoryService.getResourceAsStream(deploymentId, resourceName);
   // 构造OutputStream流
   OutputStream pngOs = new FileOutputStream("D:\\Learning\\公司学习\\activiti\\bpmn\\" + pngName);
   OutputStream bpmnOs = new FileOutputStream("D:\\Learning\\公司学习\\activiti\\bpmn\\" + resourceName);
   // 输入流与输出流的转换
   // commons-io-xx的方法
   IOUtils.copy(pngIs,pngOs);
   IOUtils.copy(bpmnIs,bpmnOs);
   // 关闭流
   pngOs.close();
   bpmnOs.close();
   pngIs.close();
   bpmnIs.close();
}
```

- 获取资源
- 流式转换
- 输入输出流拷贝
- 流关闭

说明： 

1) deploymentId 为流程部署 ID 

2) resource_name 为 act_ge_bytearray 表中 NAME_列的值 

3) 使用 repositoryService 的 getDeploymentResourceNames方法可以获取指定部署下得所有文件的名 称

4) 使用 repositoryService 的 getResourceAsStream 方法传入部署 ID和资源图片名称可以获取部署下 指定名称文件的输入流 

5) 最后的将输入流中的图片资源进行输出。

### 流程历史信息查看

即使流程定义已经删除了，流程执行的历史信息通过前面的分析，依然保存在 activiti 的 act_hi_*相 关的表中。所以我们还是可以查询流程执行的历史信息，可以通过 HistoryService 来查看相关的历史 记录。

```java
/**
 * 历史流程查看
 */
@Test
public void historyQueryTest(){
   // 获取historyService
   // 得到HistoricActivityInstanceQuery对象
   HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
   // 流程实例ID
   historicActivityInstanceQuery.processInstanceId("0ff83edd-12c0-11eb-be22-0433c2acfca1");
   // 排序,强制要写升序、降序 asc/desc
   historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime().asc();
   // 执行查询
   List<HistoricActivityInstance> list = historicActivityInstanceQuery.list();
   // 遍历查询结果
   for (HistoricActivityInstance instance : list) {
      System.out.println("instance.getActivityId() = " + instance.getActivityId());
      System.out.println("instance.getActivityName() = " + instance.getActivityName());
      System.out.println("instance.getProcessDefinitionId() = " + instance.getProcessDefinitionId());
      System.out.println("instance.getProcessInstanceId() = " + instance.getProcessInstanceId());
      System.out.println("=======================");
   }
}
```

运行结果

要顺序一定要按startTime来排序且排序时必须指定asc/desc

```java
instance.getActivityId() = _2
instance.getActivityName() = StartEvent
instance.getProcessDefinitionId() = holiday:3:df892c9b-12bd-11eb-886d-0433c2acfca1
instance.getProcessInstanceId() = 0ff83edd-12c0-11eb-be22-0433c2acfca1
=======================
instance.getActivityId() = _3
instance.getActivityName() = 填写请假申请单
instance.getProcessDefinitionId() = holiday:3:df892c9b-12bd-11eb-886d-0433c2acfca1
instance.getProcessInstanceId() = 0ff83edd-12c0-11eb-be22-0433c2acfca1
=======================
instance.getActivityId() = _4
instance.getActivityName() = 部门经理审批
instance.getProcessDefinitionId() = holiday:3:df892c9b-12bd-11eb-886d-0433c2acfca1
instance.getProcessInstanceId() = 0ff83edd-12c0-11eb-be22-0433c2acfca1
=======================
instance.getActivityId() = _5
instance.getActivityName() = 总经理审批
instance.getProcessDefinitionId() = holiday:3:df892c9b-12bd-11eb-886d-0433c2acfca1
instance.getProcessInstanceId() = 0ff83edd-12c0-11eb-be22-0433c2acfca1
=======================
instance.getActivityId() = _6
instance.getActivityName() = EndEvent
instance.getProcessDefinitionId() = holiday:3:df892c9b-12bd-11eb-886d-0433c2acfca1
instance.getProcessInstanceId() = 0ff83edd-12c0-11eb-be22-0433c2acfca1
=======================

```

# 进阶部分

## 流程实例

参与者（可以是用户也可以是程序）按照流程定义内容发起一个流程，这就是一个流程实例。是动态的。

![image-20201021175622432](D:\Learning\公司学习\activiti\activiti.assets\image-20201021175622432.png)

### 启动流程实例

流程定义部署在 activiti 后，就可以在系统中通过 activiti 去管理该流程的执行，执行流程表示流 程的一次执行。比如部署系统请假流程后，如果某用户要申请请假这时就需要执行这个流程，如果 另外一个用户也要申请请假则也需要执行该流程，每个执行互不影响，每个执行是单独的流程实例。

执行流程首先需要启动流程实例。



流程开启由申请人填写信息

activiti 流程

业务流程化自动控制，主要是控制25张表

只是保证activiti本身运行所需的

如果有额外的流程产生的特定信息记录需要存入自己创建的库中

业务系统要和activiti进行关联

关联方式？

businessKey

业务主键



![image-20201021141400102](D:\Learning\公司学习\activiti\activiti.assets\image-20201021141400102.png)



act_ru_execution中的BUSINESS_KEY_

业务系统中保存这业务申请信息

### 关联businessKey

启动流程实例时，指定的businesskey，就会在act_ru_execution #流程实例的执行表中存储businesskey。

Businesskey：业务标识，通常为业务表的主键，业务标识和流程实例一一对应。业务标识来源于业 务系统。存储业务标识就是根据业务标识来关联查询业务系统的数据。

比如：请假流程启动一个流程实例，就可以将请假单的 id 作为业务标识存储到 activiti 中，将来查询 activiti 的流程实例信息就可以获取请假单的 id 从而关联查询业务系统数据库得到请假单信息。



在 activiti 实际应用时，查询流程实例列表时可能要显示出业务系统的一些相关信息，比如：查询当 前运行的请假流程列表需要将请假单名称、请假天数等信息显示出来，请假天数等信息在业务系统 中存在，而并没有在 activiti 数据库中存在，所以是无法通过 activiti 的 api 查询到请假天数等信息。

在查询流程实例时，通过 businessKey（业务标识 ）关联查询业务系统的请假单表，查询出请假天 数等信息。

通过下面的代码就可以获取 activiti 中所对应实例保存的业务 Key。而这个业务 Key 一般都会保存相 关联的业务操作表的主键，再通过主键 ID 去查询业务信息，比如通过请假单的 ID，去查询更多的 请假信息（请假人，请假时间，请假天数，请假事由等） String businessKey = processInstance.getBusinessKey();



```java
/**
 * 添加businessKey
 * 在审批过程中可以根据businessKey获取业务系统中对应的请假单信息
 */
@Test
public void addBusinessKeyTest(){
   // 获取runtimeService

   // 启动流程实例 同事指定业务标识，businessKey，业务系统中请假单的ID
   // 第一个参数代表流程定义的key
   // 第二个参数代表业务标识 businessKey
   ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holiday", "1001");
   // 输出processInstance相关属性
   System.out.println("processInstance.getBusinessKey() = " + processInstance.getBusinessKey());
   System.out.println("processInstance.getProcessDefinitionKey() = " + processInstance.getProcessDefinitionKey());
}
```

在 activiti 的 act_ru_execution 表，字段 BUSINESS_KEY 就是存放业务 KEY 的。



act_ge_bytearray 流程是否部署进来

act_re_procdef  流程key

act_ru_execution 关联businessKey

存储及其运行结果

processInstance.getBusinessKey() = 1001
processInstance.getProcessDefinitionKey() = holiday

### 流程挂起

**制度发生变化**

原本没有走完的流程

xx员工个人每批完的流程怎么办

30个人如何处理

processDefinition代表某个流程

```java
/**
 * 流程挂起/激活
 * 场景：流程发生变化
 * 这里是挂起/激活某条流程下的所有实例
 * processDefinition代表流程实例
 */
@Test
public void suspendOrActivateProcessDefinitionTest(){
   // 获取repositoryService
   // 获取流程定义
   ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
   // 根据标识流程的key去查询
   List<ProcessDefinition> list = processDefinitionQuery.processDefinitionKey("holiday").list();
   System.out.println("list = " + list);
   // 获取某个流程实例对象
   ProcessDefinition processDefinition = list.get(0);
   // 是否暂停
   boolean suspended = processDefinition.isSuspended();
   // 如果暂停则激活，激活所有信息
   if (suspended){
      // 执行激活
      // 根据特定流程实例的id来激活 id,是否激活，时间
      repositoryService.activateProcessDefinitionById(processDefinition.getId(),true,null);
      System.out.println("流程定义：" + processDefinition + "激活");
   }else {
      // 如果激活则挂起/暂停
      repositoryService.suspendProcessDefinitionById(processDefinition.getId(),true,null);
      System.out.println("流程定义：" + processDefinition + "挂起");
   }
}
```

### 单个流程实例的挂起

操作流程实例对象，针对单个流程执行挂起操作，某个流程实例挂起则此流程不再继续执行，完成 该流程实例的当前任务将报异常。

```java
/**
 * 单个流程实例的挂起/激活
 */
@Test
public void suspendOrActiveProcessInstanceTest(){
   ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();

   // 可通过business key来获取实例（与业务挂钩，业务系统中的流程申请发起信息
   ProcessInstance processInstance = processInstanceQuery.processInstanceBusinessKey("1001").singleResult();

   String processInstanceId = processInstance.getId();

   boolean suspended = processInstance.isSuspended();
   if (suspended){
      runtimeService.activateProcessInstanceById(processInstanceId);
      System.out.println("流程：" + processInstance + "激活");
   }else {
      runtimeService.suspendProcessInstanceById(processInstanceId);
      System.out.println("流程：" + processInstance + "挂起");
   }
}
```

processInstance代表单个流程实例

在act_ru_execution中可以看到流程实例被挂起

![image-20201021145932393](D:\Learning\公司学习\activiti\activiti.assets\image-20201021145932393.png)

某些情况可能由于流程变更需要将当前运行的流程暂停而不是直接删除，流程暂停后将不会继续执 行。



##  个人任务

### 分配任务责任人

早期在bpmn中写死assignee

很多时候assignee不是固定的

UEL（Unified Expression Language,统一表达式语言）表达式

${assignee}

程序代码指定，由登录人员来决定assignee

非常类似EL表达式

可用${user.assignee}的方式去调用



部署

2020-10-21 18:38:18.194  INFO 20716 --- [           main] o.a.e.impl.bpmn.deployer.BpmnDeployer    : Process deployed: {id: holiday1:2:883a2010-1389-11eb-8eef-0433c2acfca1, key: holiday1, name: 请假流程 }
deployment = DeploymentEntity[id=8834c8de-1389-11eb-8eef-0433c2acfca1, name=请假申请单1]



```java
    /**
    * 动态设置assignee的取值
    * 用户可以在界面上设置流程的执行人
    */
   @Test
   public void assigneeUELTest(){
      Map<String,Object> map = new HashMap<>();
      map.put("assignee0","satsuki");
      map.put("assignee1","yuzuki");
      map.put("assignee2","chiaki");

      // 部署
//    Deployment deployment = repositoryService.createDeployment()
//          .addClasspathResource("processes/holiday1.bpmn")
//          .name("请假申请单1")
//          .key("holiday1")
//          .deploy();
//    System.out.println("deployment = " + deployment);

//    // 启动流程实例，同时还要设置流程定义的assignee的值
//    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holiday1","1002", map);
//    // 输出
//    System.out.println("processInstance.getName() = " + processInstance.getName());

      // 执行
      Task holiday1 = taskService.createTaskQuery().processDefinitionKey("holiday1").singleResult();
      System.out.println("holiday1 = " + holiday1);
      System.out.println("holiday1.getId() = " + holiday1.getId());
      taskService.complete(holiday1.getId());

   }
```

部署、创建实例、执行

### 监听器分配

- Create

  任务创建后

- Assignment

  任务分配后

- Delete

  任务完成后

- All

  所有事件都发生

绑定listener

实现接口

绑定assignee

```xml
   <process id="holiday3" isClosed="false" isExecutable="true"
            processType="None">
      <startEvent id="_2" name="StartEvent"/>
      <endEvent id="_3" name="EndEvent"/>
      <userTask activiti:exclusive="true" id="_4" name="UserTask">
         <extensionElements>
            <activiti:taskListener class="" event="create"/>
         </extensionElements>
      </userTask>
      <sequenceFlow id="_5" sourceRef="_2" targetRef="_4"/>
      <sequenceFlow id="_6" sourceRef="_4" targetRef="_3"/>
   </process>
```



## 查询任务

在 activiti 实际应用时，查询待办任务可能要显示出业务系统的一些相关信息，比如：查询待审批请 假单任务列表需要将请假单的日期、请假天数等信息显示出来，请假天数等信息在业务系统中存在， 而并没有在 activiti 数据库中存在，所以是无法通过 activiti 的 api 查询到请假天数等信息。

在查询待办任务时，通过 businessKey（业务标识 ）关联查询业务系统的请假单表，查询出请假天 数等信息。



根据登录用户查询task

根据task查询instance

根据instance查询businesskey

根据businesskey查询具体请假单信息

查询任务负责人的待办任务

```java
/**
 * 关联businessKey
 * 是act_ru_execution表中的
 */
@Test
public void getBusinessKey(){
   Task yuzukiTask = taskService.createTaskQuery().taskAssignee("yuzuki").singleResult();
   System.out.println("yuzukiTask = " + yuzukiTask);
   String processInstanceId = yuzukiTask.getProcessInstanceId();
   ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
   System.out.println("processInstance = " + processInstance);
   System.out.println("processInstance.getBusinessKey() = " + processInstance.getBusinessKey());

}
```

## 流程变量

### 是什么

流程运行过程中加入流程变量

activiti设置流程变量是为了流程执行需要而创建

流程变量在 activiti 中是一个非常重要的角色，流程运转有时需要靠流程变量，业务系统和 activiti 结合时少不了流程变量，流程变量就是 activiti 在管理工作流时根据管理需要而设置的变量。

比如在请假流程流转时如果请假天数大于 3 天则由总经理审核，否则由人事直接审核，请假天 数就可以设置为流程变量，在流程流转时使用。



**注意：虽然流程变量中可以存储业务数据可以通过 activiti 的 api 查询流程变量从而实现 查询业务 数据，但是不建议这样使用，因为业务数据查询由业务系统负责，activiti 设置流程变量是为了流程执行需要而创建。**

### 类型

基本数据类型+对象（serializable）序列化的对象

作用域

![image-20201021191901885](D:\Learning\公司学习\activiti\activiti.assets\image-20201021191901885.png)

### 作用域

流程变量的作用域默认是一个流程实例(processInstance)，也可以是一个任务(task)或一个执行实例 (execution)，这三个作用域**流程实例**的范围最大，可以称为 **global 变量**，**任务和执行实例**仅仅是针对 一个任务和一个执行实例范围，范围没有流程实例大，称为 **local 变量**。

作用域的规定：

全局global变量->流程实例（processInstance）

小范围local变量->任务（task）

global 变量中变量名不允许重复，设置相同名称的变量，后设置的值会覆盖前设置的变量值。

Local 变量由于在不同的任务或不同的执行实例中，作用域互不影响，变量名可以相同没有影响。 Local 变量名也可以和 global 变量名相同，没有影响。



一般来说定义后作用域是整个流程实例

global 整个流程实例

local 流程实例中的单个节点

### 使用

通过UEL表达式设定assignee或分流条件

- 设置流程变量

- 通过 UEL 表达式使用流程变量

  可以在 assignee 处设置 UEL 表达式，表达式的值为任务的负责人

  ```
  比如：${assignee}，assignee 就是一个流程变量名称
  ```

  可以在连线上设置 UEL 表达式，决定流程走向

  ```
  比如：${price>=10000}和${price<10000}: price 就是一个流程变量名称，uel 表达式结果类型为布尔类型
  ```

  



绘图，在指定连线加入条件

![image-20201022095443869](D:\Learning\公司学习\activiti\activiti.assets\image-20201022095443869.png)

一定要修改整体的id

流程部署

```java
	/**
	 * 流程变量的测试
	 */
	@Test
	public void variableTest() {
		// 新的请假流程定义的部署
		// 得到repositoryService
//		Deployment deploy = repositoryService.createDeployment()
//												.addClasspathResource("processes/holiday2.bpmn")
//												.key("holiday2")
//												.name("请假流程2")
//												.deploy();
//		System.out.println("deploy.getId() = " + deploy.getId());
//		System.out.println("deploy.getName() = " + deploy.getName());
//		System.out.println("deploy.getKey() = " + deploy.getKey());


		// 启动流程实例，设置流程变量的值
		Map<String, Object> map = new HashMap<>();
		Holiday holiday = new Holiday();
		holiday.setNum(4F);
		map.put("holiday", holiday);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holiday2", "10005", map);
		// 输出实例信息
		System.out.println("processInstance.getName() = " + processInstance.getName());
		System.out.println("processInstance.getProcessDefinitionId() = " + processInstance.getProcessDefinitionId());
		System.out.println("processInstance.getBusinessKey() = " + processInstance.getBusinessKey());

		System.out.println("processInstance.getId() = " + processInstance.getId());


		// 任务执行
		// 查询
		Task task = taskService
				.createTaskQuery()
				.processInstanceBusinessKey("10005")
				.singleResult();
		// 执行
		if (task != null) {
			for (int i = 0; i < 4; i++) {
				taskService.complete(task.getId());
				System.out.println("任务执行完毕");
				System.out.println("task.getAssignee() = " + task.getAssignee());
				System.out.println("task = " + task);
				// 重新查询，每次执行后task的id都会变更代表流程向下执行
				task = taskService
						.createTaskQuery()
						.processInstanceBusinessKey("10005")
						.singleResult();
			}
		}


	}
```

启动流程实例

processInstance.getName() = null
processInstance.getProcessDefinitionId() = holiday2:2:65538500-140b-11eb-b36a-0433c2acfca1
processInstance.getBusinessKey() = 10003

act_ru_variable表中存储序列化的对象

![image-20201022101627367](D:\Learning\公司学习\activiti\activiti.assets\image-20201022101627367.png)

任务执行

任务执行完毕
task.getAssignee() = yuzuki
task = Task[id=5352af45-140d-11eb-8303-0433c2acfca1, name=部门经理审批]

第二次执行后就到了分歧点

根据之前的条件condition进行判断

![image-20201022102359173](D:\Learning\公司学习\activiti\activiti.assets\image-20201022102359173.png)

num<=3所以直接到了hr

任务执行完毕
task.getAssignee() = hr
task = Task[id=7f9f2b4f-140d-11eb-8d1e-0433c2acfca1, name=人事经理存档]

流程：

```java
instance.getActivityId() = _2
instance.getActivityName() = StartEvent
instance.getProcessDefinitionId() = holiday2:2:65538500-140b-11eb-b36a-0433c2acfca1
instance.getProcessInstanceId() = 3e420869-140c-11eb-935e-0433c2acfca1
=======================
instance.getActivityId() = _4
instance.getActivityName() = 填写请假申请
instance.getProcessDefinitionId() = holiday2:2:65538500-140b-11eb-b36a-0433c2acfca1
instance.getProcessInstanceId() = 3e420869-140c-11eb-935e-0433c2acfca1
=======================
instance.getActivityId() = _5
instance.getActivityName() = 部门经理审批
instance.getProcessDefinitionId() = holiday2:2:65538500-140b-11eb-b36a-0433c2acfca1
instance.getProcessInstanceId() = 3e420869-140c-11eb-935e-0433c2acfca1
=======================
instance.getActivityId() = _7
instance.getActivityName() = 人事经理存档
instance.getProcessDefinitionId() = holiday2:2:65538500-140b-11eb-b36a-0433c2acfca1
instance.getProcessInstanceId() = 3e420869-140c-11eb-935e-0433c2acfca1
=======================
instance.getActivityId() = _3
instance.getActivityName() = EndEvent
instance.getProcessDefinitionId() = holiday2:2:65538500-140b-11eb-b36a-0433c2acfca1
instance.getProcessInstanceId() = 3e420869-140c-11eb-935e-0433c2acfca1
=======================
```

一起的执行流程

```java
processInstance.getName() = null
processInstance.getProcessDefinitionId() = holiday2:2:65538500-140b-11eb-b36a-0433c2acfca1
processInstance.getBusinessKey() = 10005
processInstance.getId() = 216fc522-140f-11eb-9657-0433c2acfca1
任务执行完毕
task.getAssignee() = satsuki
task = Task[id=21859718-140f-11eb-9657-0433c2acfca1, name=填写请假申请]
任务执行完毕
task.getAssignee() = yuzuki
task = Task[id=21a776fe-140f-11eb-9657-0433c2acfca1, name=部门经理审批]
任务执行完毕
task.getAssignee() = chiaki
task = Task[id=21b83fe4-140f-11eb-9657-0433c2acfca1, name=总经理审批]
任务执行完毕
task.getAssignee() = hr
task = Task[id=21c49bfa-140f-11eb-9657-0433c2acfca1, name=人事经理存档]
```

查看历史

```java
instance.getActivityId() = _2
instance.getActivityName() = StartEvent
instance.getProcessDefinitionId() = holiday2:2:65538500-140b-11eb-b36a-0433c2acfca1
instance.getProcessInstanceId() = 216fc522-140f-11eb-9657-0433c2acfca1
=======================
instance.getActivityId() = _4
instance.getActivityName() = 填写请假申请
instance.getProcessDefinitionId() = holiday2:2:65538500-140b-11eb-b36a-0433c2acfca1
instance.getProcessInstanceId() = 216fc522-140f-11eb-9657-0433c2acfca1
=======================
instance.getActivityId() = _5
instance.getActivityName() = 部门经理审批
instance.getProcessDefinitionId() = holiday2:2:65538500-140b-11eb-b36a-0433c2acfca1
instance.getProcessInstanceId() = 216fc522-140f-11eb-9657-0433c2acfca1
=======================
instance.getActivityId() = _6
instance.getActivityName() = 总经理审批
instance.getProcessDefinitionId() = holiday2:2:65538500-140b-11eb-b36a-0433c2acfca1
instance.getProcessInstanceId() = 216fc522-140f-11eb-9657-0433c2acfca1
=======================
instance.getActivityId() = _7
instance.getActivityName() = 人事经理存档
instance.getProcessDefinitionId() = holiday2:2:65538500-140b-11eb-b36a-0433c2acfca1
instance.getProcessInstanceId() = 216fc522-140f-11eb-9657-0433c2acfca1
=======================
instance.getActivityId() = _3
instance.getActivityName() = EndEvent
instance.getProcessDefinitionId() = holiday2:2:65538500-140b-11eb-b36a-0433c2acfca1
instance.getProcessInstanceId() = 216fc522-140f-11eb-9657-0433c2acfca1
=======================
```

![image-20201022103705263](D:\Learning\公司学习\activiti\activiti.assets\image-20201022103705263.png)

**任务办理流程变量的设置**

taskService.complete("id",map)

调用此接口设值

**通过当前流程实例设置**

runtimeService.setVariable（instanceID,key,value）

runtimeService.setVariables（instanceID,map）

**通过当前任务**

taskService.setVariable(taskId,kley,value)

taskService.setVariables（taskId,map）



流程实例-》单个任务

全局变量

不设置按flow序号小的那条线



act_ru_variable 流程变量

act_hi_varinst 

act_ge_bytearray 也会存储一份序列化的pojo





任务分配

固定、EL表达式

监听器 implements taskListener

查询任务

办理任务

流程变量

类型、作用域、怎么用

执行人变量 condition变量

### 局部变量

taskService.setVariablesLocal(taskid,variables)

### 注意

1、 如果 UEL表达式中流程变量名不存在则报错。 

2、 如果 UEL表达式中流程变量值为空 NULL，流程不按 UEL 表达式去执行，而流程结束 

3、 如果 UEL表达式都不符合条件，流程结束 

4、 如果连线不设置条件，会走 flow 序号小的那条线

## 组任务

在流程定义中在任务结点的 assignee 固定设置任务负责人，在流程定义时将参与者固定设置 在.bpmn 文件中，如果临时任务负责人变更则需要修改流程定义，系统可扩展性差。 针对这种情况可以给任务设置多个候选人，可以从候选人中选择参与者来完成任务。

组任务代表某task可由多个人完成/由某组完成，分配更加灵活

组任务办理流程

1. 查询组任务

   指定候选人，查询该候选人当前的待办任务。 候选人不能办理任务。

2. 拾取组任务

   该组任务的所有候选人都能拾取。 将候选人的组任务，变成个人任务。原来候选人就变成了该任务的负责人。

   可将已经拾取的个人任务归还到组里边，将个人任务变成了组任务。

   转交还是听过claim方法执行，两步，先置空再重新分配不要暴力直接设置assignee

3. 查询个人任务

   查询方式同个人任务部分，根据 assignee 查询用户负责的个人任务。

4. 办理个人任务

   

### Candidate-Users候选人

逗号分隔





### 组任务

查询组任务

拾取（claim）任务（可退回

查询个人任务

办理个人任务



需要一个后台管理activiti

```java
/**
 * 组任务
 * 拾取任务，让assignee有值
 */
@Test
public void groupTest() {
   /**
    // 部署
    Deployment deploy = repositoryService
    .createDeployment()
    .addClasspathResource("processes/holiday5.bpmn")
    .name("候选人请假流程单")
    .key("holiday5")
    .deploy();
    System.out.println("deploy.getId() = " + deploy.getId());
    // 启动流程实例
    ProcessInstance processInstance = runtimeService
    .startProcessInstanceByKey("holiday5","2001");
    System.out.println("processInstance.getId() = " + processInstance.getId());
    System.out.println("processInstance.getProcessDefinitionId() = " + processInstance.getProcessDefinitionId());
    System.out.println("processInstance.getBusinessKey() = " + processInstance.getBusinessKey());

    // 先完成一次任务
    Task task = taskService.createTaskQuery().processInstanceBusinessKey("2001").singleResult();
    taskService.complete(task.getId());
    System.out.println("task = " + task);
    System.out.println("task.getAssignee() = " + task.getAssignee());
    */
   // 候选人claim拾取任务
   // 查询候选用户的组任务
   String key = "holiday5";
   String candidateUser = "zhangsan";
   // 执行查询
   List<Task> list = taskService.createTaskQuery().processDefinitionKey(key).taskCandidateUser(candidateUser).list();
   // 输出
   // 这里没有具体assignee，设置了组任务，没被拾取
   for (Task task1 : list) {
      System.out.println("task1 = " + task1);
      System.out.println("task1.getProcessInstanceId() = " + task1.getProcessInstanceId());
      System.out.println("task1.getId() = " + task1.getId());
      System.out.println("task1.getName() = " + task1.getName());
      System.out.println("task1.getAssignee() = " + task1.getAssignee());
   }
   // 用户拾取组任务
   // 查询当前用户是否有组任务
   // 正常流程：查询用户可拾取的列表，拾取某条任务
   // 这里假设有一个任务
   Task task1 = taskService.createTaskQuery().processDefinitionKey(key).taskCandidateUser(candidateUser).singleResult();
   // 不为空就拾取组任务
   if (null != task1) {
      taskService.claim(task1.getId(), candidateUser);
      System.out.println("任务拾取完毕");
   }
   // 作为执行人查询任务
   // 查询多个用list
   Task task2 = taskService.createTaskQuery().taskAssignee(candidateUser).processDefinitionKey(key).singleResult();
   System.out.println("task2 = " + task2);
   System.out.println("task2.getAssignee() = " + task2.getAssignee());
   System.out.println("task2.getId() = " + task2.getId());
   System.out.println("task2.getName() = " + task2.getName());

   // 完成任务
   if (task2 != null) {
      taskService.complete(task2.getId());
      System.out.println("任务完成");
   }

}
```

### **任务拾取**

拾取后

act_hi_actinst

act_ru_task

zhangsan正式成为assignee

只是拾取并未完成任务

结果

```java
deploy.getId() = cb1d87ef-1419-11eb-8e68-0433c2acfca1
processInstance.getId() = cb25c552-1419-11eb-8e68-0433c2acfca1
processInstance.getProcessDefinitionId() = holiday5:2:cb21f4c1-1419-11eb-8e68-0433c2acfca1
processInstance.getBusinessKey() = 2001
task = Task[id=cb4eaa16-1419-11eb-8e68-0433c2acfca1, name=填写请假申请单]
task.getAssignee() = xiaozhang
```

出现用户未找到异常，是security报的，在配置中添加该用户即可

剩余执行：

```java
task1 = Task[id=cb584709-1419-11eb-8e68-0433c2acfca1, name=部门经理审核]
task1.getProcessInstanceId() = cb25c552-1419-11eb-8e68-0433c2acfca1
task1.getId() = cb584709-1419-11eb-8e68-0433c2acfca1
task1.getName() = 部门经理审核
task1.getAssignee() = null
任务拾取完毕
task2 = Task[id=cb584709-1419-11eb-8e68-0433c2acfca1, name=部门经理审核]
task2.getAssignee() = zhangsan
task2.getId() = cb584709-1419-11eb-8e68-0433c2acfca1
task2.getName() = 部门经理审核
任务完成
```

在log中可看到调用taskServer.claim（声称、拾取、认领）后进行了工作流的实例的某个任务节点与assignee（代理人）的绑定完成后就进入了下一个节点

### 归还任务

```java
    /**
    * 组任务归还
    */
   @Test
   public void returnTaskTest() {
//    Task task = taskService.createTaskQuery().processInstanceBusinessKey("2002").singleResult();
//    System.out.println("task = " + task);
//    System.out.println("task.getId() = " + task.getId());
//    System.out.println("task.getAssignee() = " + task.getAssignee());
//    System.out.println("task.getName() = " + task.getName());
//    // 分配
      String assignee = "zhangsan";
//    tas kService.claim(task.getId(),assignee);
      // 查看分配后的任务信息
      Task task1 = taskService.createTaskQuery().taskAssignee(assignee).processInstanceBusinessKey("2002").singleResult();
      System.out.println("task1 = " + task1);
      System.out.println("task1.getAssignee() = " + task1.getAssignee());
      // 任务归还，只要把任务的assignee设置为空即可
      // 归还前最好再多一步判断，判断代理人为理想中的人
      if (task1.getAssignee().equalsIgnoreCase("zhangsan")) {
         taskService.claim(task1.getId(), null);
      }
      // 再次查询任务状态
      Task task2 = taskService.createTaskQuery().processInstanceBusinessKey("2002").singleResult();
      System.out.println("task = " + task2);
      System.out.println("task.getId() = " + task2.getId());
      System.out.println("task.getAssignee() = " + task2.getAssignee());
      System.out.println("task.getName() = " + task2.getName());
      // 也可以通过claim设置assignee为其他人完成任务委托

   }
```

结果

```
task = Task[id=682b4f67-142b-11eb-bea0-0433c2acfca1, name=部门经理审核]
task.getId() = 682b4f67-142b-11eb-bea0-0433c2acfca1
task.getAssignee() = null
task.getName() = 部门经理审核
task1 = Task[id=682b4f67-142b-11eb-bea0-0433c2acfca1, name=部门经理审核]
task1.getAssignee() = zhangsan
task = Task[id=682b4f67-142b-11eb-bea0-0433c2acfca1, name=部门经理审核]
task.getId() = 682b4f67-142b-11eb-bea0-0433c2acfca1
task.getAssignee() = null
task.getName() = 部门经理审核

```

### 交接

```java
    /**
    * 任务交接
    * 前提保证当前用户是任务负责人，才有权限去将任务交接给其他候选人
    */
   @Test
   public void handoverTask() {
      // 分配任务
      String assignee = "zhangsan";
      Task task = taskService.createTaskQuery().processInstanceBusinessKey("2002").singleResult();
      // 交接用户是否合法交给activiti及security判定
//    taskService.claim(task.getId(), assignee);
      // 任务交接
      Task task1 = taskService.createTaskQuery().processInstanceBusinessKey("2002").taskAssignee(assignee).singleResult();
      System.out.println("task1 = " + task1);
      System.out.println("task1.getAssignee() = " + task1.getAssignee());
      if (task1 != null) {
         // 如果交接人不再组内应该会报错，多测测
         // 这里yuzuki不再组内
         // claim只能用于分配任务无法用于交接
//       taskService.claim(task1.getId(),"yuzuki");
         // 交接使用setAssignee
         // setAssignee没判断委托人是否在组内，也没经过security
         // 判断是否可转交需要手动执行，是否在组内，是否可用
         taskService.setAssignee(task1.getId(),"yuzuki");
      }
      Task task2 = taskService.createTaskQuery().processInstanceBusinessKey("2002").singleResult();
      System.out.println("task2 = " + task2);
      System.out.println("task2.getAssignee() = " + task2.getAssignee());
   }
```

**这种交接方式比较暴力，不考虑交接人是否在组内，根据具体业务场景进行区分**

**交接遵守规则的话使用claim方法先置空再重新分配**

setAssignee

结果

task1 = Task[id=682b4f67-142b-11eb-bea0-0433c2acfca1, name=部门经理审核]
task1.getAssignee() = zhangsan
task2 = Task[id=682b4f67-142b-11eb-bea0-0433c2acfca1, name=部门经理审核]
task2.getAssignee() = yuzuki



act_ru_task 实际影响的表

act_ru_identitylink 查看用户是否为候选人 participant 实际任务执行人 参加者

## 网关

流程变量

保证流程更为合理的执行

### 排他网关

排他网关（也叫异或（XOR）网关，或叫基于数据的排他网关），用来在流程中实现决策。 当流程 执行到这个网关，所有分支都会判断条件是否为 true，如果为 true 则执行该分支， 注意，排他网关只会选择一个为 true 的分支执行。(即使有两个分支条件都为 true，排他网关也会只 选择一条分支去执行)

**解决问题：**

多分支条件都符合怎么办？

根据定义顺序执行

无排他网关导致任务分裂

添加排他网关

#### 定义

![image-20201022164028767](D:\Learning\公司学习\activiti\activiti.assets\image-20201022164028767.png)



#### 测试

```java
/**
 * 网关
 * 排他网关
 * 前提:没网关时的情况，两个分支条件可同时满足
 * 流程乱了，task流程分裂成两条
 */
@Test
public void exclusiveGatewayTest(){
   // 流程部署
   Deployment deploy = repositoryService
         .createDeployment()
         .key("holiday6")
         .name("请假流程6")
         .addClasspathResource("processes/holiday6.bpmn")
         .deploy();
   System.out.println("deploy.getName() = " + deploy.getName());
   System.out.println("deploy = " + deploy);
   System.out.println("deploy.getId() = " + deploy.getId());
   // 流程启动
   Map<String,Object> map = new HashMap<>();
   Holiday holiday = new Holiday();
   holiday.setNum(5F);
   map.put("holiday",holiday);
   ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holiday6", "2003", map);
   System.out.println("processInstance = " + processInstance);
   // 完成任务
   Task task = taskService.createTaskQuery().processInstanceBusinessKey("2003").singleResult();
   System.out.println("task.getAssignee() = " + task.getAssignee());
   System.out.println("task = " + task);
   taskService.complete(task.getId());
   task = taskService.createTaskQuery().processInstanceBusinessKey("2003").singleResult();
   System.out.println("task.getAssignee() = " + task.getAssignee());
   System.out.println("task = " + task);
   taskService.complete(task.getId());
   // 查看结果，只有一条记录了，两根线默认为true走id取值更小的分支


}
```



```java
deploy.getName() = 请假流程6
deploy = DeploymentEntity[id=8b261b99-1433-11eb-a1ff-0433c2acfca1, name=请假流程6]
deploy.getId() = 8b261b99-1433-11eb-a1ff-0433c2acfca1
processInstance = ProcessInstance[8b2f1c4c-1433-11eb-a1ff-0433c2acfca1]
task.getAssignee() = satsuki
task = Task[id=8b60b3a2-1433-11eb-a1ff-0433c2acfca1, name=填写请假申请单]
task.getAssignee() = yuzuki
task = Task[id=8b7b8ea8-1433-11eb-a1ff-0433c2acfca1, name=部门经理审核]
```

如果都不成立也抛出异常，无分支可走



### 并行网关

并行网关允许将流程分成多条分支，也可以把多条分支汇聚到一起，并行网关的功能是基于进 入和外出顺序流的：



允许将流程分成多条分支也可以把多分支汇聚到一起

fork分支

join分支

**并行网关不会解析条件，有条件也被忽略**

#### 定义

![image-20201022164147629](D:\Learning\公司学习\activiti\activiti.assets\image-20201022164147629.png)

#### 测试

```java
    /**
    * 并行网关
    * 丰富请假流程
    * 流程分裂和汇流
    * 排他+并行
    */
   @Test
   public void parallelTest(){
      // 流程部署
//    Deployment deploy = repositoryService
//          .createDeployment()
//          .key("holiday7")
//          .name("请假流程7")
//          .addClasspathResource("processes/holiday7.bpmn")
//          .deploy();
//    System.out.println("deploy.getName() = " + deploy.getName());
//    System.out.println("deploy = " + deploy);
//    System.out.println("deploy.getId() = " + deploy.getId());
//    // 启动实例
//    Map<String,Object> map = new HashMap<>();
//    Holiday holiday = new Holiday();
//    holiday.setNum(5F);
//    map.put("holiday",holiday);
//    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holiday7", "2004", map);
//    System.out.println("processInstance = " + processInstance);
//    // 不断执行任务
//    for (int i = 0; i < 3; i++) {
//       Task task = taskService.createTaskQuery().processInstanceBusinessKey("2004").singleResult();
//       System.out.println("task = " + task);
//       System.out.println("task.getAssignee() = " + task.getAssignee());
//       taskService.complete(task.getId());
//    }
      // 完成新出的两个任务
      List<Task> list = taskService.createTaskQuery().processInstanceBusinessKey("2004").list();
      for (Task task : list) {
         System.out.println("task = " + task);
         System.out.println("task.getAssignee() = " + task.getAssignee());
         taskService.complete(task.getId());
      }


   }
```

经过并行网关拆分出两个任务

![image-20201022151252455](D:\Learning\公司学习\activiti\activiti.assets\image-20201022151252455.png)

两个都执行

task = Task[id=f3089453-1435-11eb-a303-0433c2acfca1, name=财务会计]
task.getAssignee() = abc
task = Task[id=f3089455-1435-11eb-a303-0433c2acfca1, name=行政考勤]
task.getAssignee() = def

就执行完成

可进行下一步

act_ru_task 执行中的任务记录

act_hi_actinst 执行记录



### 包含网关

包含网关可以看做是排他网关和并行网关的结合体。 和排他网关一样，你可以在外出顺序流上 定义条件，包含网关会解析它们。 但是主要的区别是包含网关可以选择多于一条顺序流，这和并行 网关一样。



事件网关



排他+并行

选择多个分支执行

**带有条件判定的并行网关**

分支

汇聚

等待选中的数据流执行完毕才去进入下一状态

体检案例

inclusive

#### 定义

![image-20201022164253018](D:\Learning\公司学习\activiti\activiti.assets\image-20201022164253018.png)

#### 测试

```java
    /**
    * 包含网关
    * 待条件判断的并行网关
    * fork 分支
    * 聚集
    */
   @Test
   public void inclusiveGatewayTest(){
      /**
      // 部署
      Deployment deploy = repositoryService.createDeployment()
                     .addClasspathResource("processes/examination.bpmn")
                     .key("examination")
                     .name("体检")
                     .deploy();
      System.out.println("deploy = " + deploy);
      System.out.println("deploy.getName() = " + deploy.getName());
      System.out.println("deploy.getKey() = " + deploy.getKey());
      // 启动流程实例
      Integer userType = 2;
      Map<String,Object> map = new HashMap<>();
      map.put("userType",userType);
      ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("examination", "2005", map);
      System.out.println("processInstance = " + processInstance);
      System.out.println("processInstance.getProcessDefinitionId() = " + processInstance.getProcessDefinitionId());
      System.out.println("processInstance.getBusinessKey() = " + processInstance.getBusinessKey());
      System.out.println("processInstance.getProcessVariables() = " + processInstance.getProcessVariables());

       */
      // 任务执行
//    Task task = taskService.createTaskQuery().processInstanceBusinessKey("2005").singleResult();
//    System.out.println("task.getAssignee() = " + task.getAssignee());
//    System.out.println("task = " + task);
//    taskService.complete(task.getId());
      List<Task> list = taskService.createTaskQuery().processInstanceBusinessKey("2005").list();

      for (int i = 0; i < list.size(); i++) {
         Task task = list.get(i);
         System.out.println("task = " + task);
         System.out.println("task.getAssignee() = " + task.getAssignee());
         System.out.println("task.getName() = " + task.getName());
         // 完成任务
         taskService.complete(task.getId());
      }
      Task task = taskService.createTaskQuery().processInstanceBusinessKey("2005").singleResult();
      System.out.println("task = " + task);
      System.out.println("task.getAssignee() = " + task.getAssignee());
      taskService.complete(task.getId());

   }
```

结合运行

```java
2020-10-22 16:01:25.564  INFO 12044 --- [           main] o.a.e.impl.bpmn.deployer.BpmnDeployer    : Process deployed: {id: examination:2:c846abb7-143c-11eb-9b66-0433c2acfca1, key: examination, name: null }
deploy = DeploymentEntity[id=c8432945-143c-11eb-9b66-0433c2acfca1, name=体检]
deploy.getName() = 体检
deploy.getKey() = examination
processInstance = ProcessInstance[c84c29f8-143c-11eb-9b66-0433c2acfca1]
processInstance.getProcessDefinitionId() = examination:2:c846abb7-143c-11eb-9b66-0433c2acfca1
processInstance.getBusinessKey() = 2005
processInstance.getProcessVariables() = {}
task.getAssignee() = zhangsan
task = Task[id=c85b934e-143c-11eb-9b66-0433c2acfca1, name=申请体检单]
    
```

可以看到拆成了三条任务

![image-20201022160425457](D:\Learning\公司学习\activiti\activiti.assets\image-20201022160425457.png)

由于测试的userType为2所以出现三条任务都要走，都完成

继续执行

流程不断推进

![image-20201022161013257](D:\Learning\公司学习\activiti\activiti.assets\image-20201022161013257.png)

全部执行完成后 task表清空

```
task = Task[id=ea14ff97-143c-11eb-9b66-0433c2acfca1, name=常规体检项]
task.getAssignee() = b
task.getName() = 常规体检项
task = Task[id=ea14ff99-143c-11eb-9b66-0433c2acfca1, name=抽血化验]
task.getAssignee() = c
task.getName() = 抽血化验
task = Task[id=ea14ff9b-143c-11eb-9b66-0433c2acfca1, name=附加体检项目]
task.getAssignee() = d
task.getName() = 附加体检项目
2020-10-22 16:10:19.583  WARN 12588 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Thread starvation or clock leap detected (housekeeper delta=56s141ms79µs700ns).
task = Task[id=f2aebb8a-143d-11eb-a320-0433c2acfca1, name=早餐]
task.getAssignee() = e

```

任务撤销

## 总结

### 工作流

通过计算机对业务流程进行自动化管理，实现与多个参与者按照预定义的流程去自动执行业务流程。

### activiti

Activiti 是一个工作流的引擎，开源的架构，基本 bpmn2.0 标准进行流程定义，它的是前身是 jbpm。 Activiti 通过是要嵌入到业务系统开发使用。

### 开发

- 部署activiti环境

- 使用activiti提供的流程设计器工具设计BPMN

- 将流程定义文件部署到activiti的数据库

  SELECT * FROM act_re_deployment #流程定义部署表

  一次部署插入一条记录，记录流程定义的部署信息

  SELECT * FROM act_re_procdef #流程定义表

  一次部署流程定义信息，如果一次部署两个流程定义，插入两条记录

  建议：一次部署只部署一个流程定义，这样 act_re_deployment 和 act_re_procdef 一对一关系

  常用两个方法：单个文件部署和 zip 文件部署。

  建议单个文件部署。

  与spring boot整合后会自动部署

- 启动流程实例

  业务系统就可以按照流程定义去执行业务流程，执行前需要启动一个流程实例 根据流程定义来启动一个流程实例。

  指定一个流程定义的 key 启动一个流程实例，activiti 根据 key 找最新版本的流程定义。

  指定一个流程定义的 id 启动一个流程实例。

  启动一个实例需要指定 businesskey（业务标识），businessKey 是 activiti 和业务系统整合时桥梁。 比如：请假流程，businessKey 就是请假单 id。

  启动一个实例还可以指定流程变量，流程变量是全局变量（生命期是整个流程实例，流程实例结束， 变量就消失）

  **key最好BPMN文件的id与启动时的key一致**

  **在正式开发的时候使用businessKey来标识流程**

- 查询代办任务

  查询个人任务：使用 taskService，根据 assignee 查询该用户当前的待办任务。

  查询组任务：使用 taskService，根据 candidateuser 查询候选用户当前的待办组任务。

- 办理任务

  办理个人任务：调用 taskService 的 complete 方法完成任务。

  如果是组任务，需要先拾取任务，调用 taskService 的 claim 方法拾取任务，拾取任务之后组任务就 变成了个人任务（该任务就有负责人）。



### 网关

排他网关：任务执行之后的分支，经过排他网关分支只有一条有效。 

并行网关：任务执行后，可以多条分支，多条分支总会汇聚，汇聚完成，并行网关结束。 

包含网关：是排他网关和并行网关结合体。待流程分支判断的并行网关

## activiti7 新特性



## 与springboot整合

assignee 直接设置任务执行人

candidate-user设置候选用户格式:x,y,z 依然指定具体用户信息

candidate-groups 设置用户组，不需要知道具体用户信息

情况很差

idea自带的antiBPM插件会自动添加xmlns=""标签导致用于展示工作流的bpmn插件不断报错

activiti7.0高版本强引用springSecurity

新的引擎flowable基于activiti

192.168.198.120 node-1
192.168.198.121 node-2
192.168.198.122 node-3

cvc-complex-type.2.4.a: Invalid content was found starting with element 'process'. One of '{"http://www.omg.org/spec/BPMN/20100524/MODEL":import, "http://www.omg.org/spec/BPMN/20100524/MODEL":extension, "http://www.omg.org/spec/BPMN/20100524/MODEL":rootElement, "http://www.omg.org/spec/BPMN/20100524/DI":BPMNDiagram, "http://www.omg.org/spec/BPMN/20100524/MODEL":relationship}' is expected.

一个流程多次部署部署如果生成多条记录会产生问题

org.activiti.engine.ActivitiException: Query return 25 results instead of max 1

解决办法情况数据库

org.activiti.api.runtime.shared.UnprocessableEntityException: Process definition with the given id:'myProcess_1:1:713312ec-129d-11eb-bded-0433c2acfca1' belongs to a different application version.





查询、拾取、完成任务

完成任务后工作流自动向后移动开启下一个任务

```java
task1 = TaskImpl{id='9e4fc4e0-129f-11eb-b2d6-0433c2acfca1', owner='null', assignee='null', name='TeamLeader审核', description='null', createdDate=Tue Oct 20 14:43:52 CST 2020, claimedDate=null, dueDate=null, priority=50, processDefinitionId='myProcess_1:1:713312ec-129d-11eb-bded-0433c2acfca1', processInstanceId='9e47887c-129f-11eb-b2d6-0433c2acfca1', parentTaskId='null', formKey='null', status=CREATED, processDefinitionVersion=null, businessKey=null, taskDefinitionKey=_3}

task2 = TaskImpl{id='6c66c0a2-12a1-11eb-a5eb-0433c2acfca1', owner='null', assignee='null', name='HR审核', description='null', createdDate=Tue Oct 20 14:56:48 CST 2020, claimedDate=null, dueDate=null, priority=50, processDefinitionId='myProcess_1:1:713312ec-129d-11eb-bded-0433c2acfca1', processInstanceId='9e47887c-129f-11eb-b2d6-0433c2acfca1', parentTaskId='null', formKey='null', status=CREATED, processDefinitionVersion=null, businessKey=null, taskDefinitionKey=_4}

```

使用TaskRuntime接口的tasks()方法实现任务的查询

使用TaskRuntime接口的claim()方法实现任务的拾取

使用TaskRuntime接口的complete()方法实现任务的完成

全部执行完后无下一个任务就只输出一个任务的log

```java
task1 = TaskImpl{id='6c66c0a2-12a1-11eb-a5eb-0433c2acfca1', owner='null', assignee='null', name='HR审核', description='null', createdDate=Tue Oct 20 14:56:48 CST 2020, claimedDate=null, dueDate=null, priority=50, processDefinitionId='myProcess_1:1:713312ec-129d-11eb-bded-0433c2acfca1', processInstanceId='9e47887c-129f-11eb-b2d6-0433c2acfca1', parentTaskId='null', formKey='null', status=CREATED, processDefinitionVersion=null, businessKey=null, taskDefinitionKey=_4}

```

ru_task表也被清空



### 导入依赖

```xml
<dependencies>
    <!-- web用于与mvc整合测试 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- activiti与spring-boot整合的依赖 -->
    <dependency>
        <groupId>org.activiti</groupId>
        <artifactId>activiti-spring-boot-starter</artifactId>
        <version>7.1.0.M6</version>
    </dependency>
    <!-- 整合MySQL 注意版本5.x和8.x驱动不同 -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>

    <!-- 整合jdbc，activiti会自动对数据库进行操作，后续可用mybatis+druid-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
        <exclusions>
            <exclusion>
                <groupId>org.junit.vintage</groupId>
                <artifactId>junit-vintage-engine</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    <!-- 默认支持security，后期考虑可否转shiro-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

### 配置

主要是数据库连接以及activiti的配置

```yaml
server:
  port: 8085
spring:
  application:
    name: spring-activiti
  datasource:
    url: jdbc:mysql://localhost:3306/activiti?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT&nullCatalogMeansCurrent=true
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver
  activiti:
    #1.flase： 默认值。activiti在启动时，会对比数据库表中保存的版本，如果没有表或者版本不匹配，将抛出异常
    #2.true： activiti会对数据库中所有表进行更新操作。如果表不存在，则自动创建
    #3.create_drop： 在activiti启动时创建表，在关闭时删除表（必须手动关闭引擎，才能删除表）
    #4.drop-create： 在activiti启动时删除原来的旧表，然后在创建新表（不需要手动关闭引擎）
    database-schema-update: true
    #检测历史表是否存在
    db-history-used: true
    #记录历史等级 可配置的历史级别有none, activity, audit, full
    history-level: full
    #校验流程文件，默认校验resources下的processes文件夹里的流程文件
    #check-process-definitions: false
    # 自动建表
    database-schema: ACTIVITI
```

### 添加SecurityUtil类

快速实现SpringSecurity安全框架的配置，方便后续调用logInAs方法模拟不同用户登录

```java
package com.example.activiti;

/**
 * @program: activiti-spring
 * @author: yuzuki
 * @create: 2020-10-20 13:44
 * @description:
 **/


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;

@Component
public class SecurityUtil {

   @Resource
   private UserDetailsService userDetailsService;

   public void logInAs(String username) {

      UserDetails user = userDetailsService.loadUserByUsername(username);
      if (user == null) {
         throw new IllegalStateException("User " + username + " doesn't exist, please provide a valid user");
      }

      SecurityContextHolder.setContext(new SecurityContextImpl(new Authentication() {
         @Override
         public Collection<? extends GrantedAuthority> getAuthorities() {
            return user.getAuthorities();
         }

         @Override
         public Object getCredentials() {
            return user.getPassword();
         }

         @Override
         public Object getDetails() {
            return user;
         }

         @Override
         public Object getPrincipal() {
            return user;
         }

         @Override
         public boolean isAuthenticated() {
            return true;
         }

         @Override
         public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
         }

         @Override
         public String getName() {
            return user.getUsername();
         }
      }));
      org.activiti.engine.impl.identity.Authentication.setAuthenticatedUserId(username);
   }
}
```

### 添加DemoApplicationConfig类

在activiti7官方example中找到的该类用于实现SpringSecurity框架的用户权限设置，可在系统中使用用户权限信息，本次在文件中自定义了，后续可放到数据库中

用户、用户组

```java
package com.example.activiti;

/**
 * @program: activiti-spring
 * @author: yuzuki
 * @create: 2020-10-20 13:42
 * @description:
 **/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class DemoApplicationConfiguration extends WebSecurityConfigurerAdapter {

   private Logger logger = LoggerFactory.getLogger(DemoApplicationConfiguration.class);

   @Override
   @Autowired
   public void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(myUserDetailsService());
   }

   @Bean
   public UserDetailsService myUserDetailsService() {

      InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

      String[][] usersGroupsAndRoles = {
            {"user", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
            {"team-leader", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
            {"hr", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
            {"other", "password", "ROLE_ACTIVITI_USER", "GROUP_otherTeam"},
            {"admin", "password", "ROLE_ACTIVITI_ADMIN"},
            {"salaboy", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
            {"ryandawsonuk", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
            {"erdemedeiros", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},

      };

      for (String[] user : usersGroupsAndRoles) {
         List<String> authoritiesStrings = Arrays.asList(Arrays.copyOfRange(user, 2, user.length));
         logger.info("> Registering new user: " + user[0] + " with the following Authorities[" + authoritiesStrings + "]");
         inMemoryUserDetailsManager.createUser(new User(user[0], passwordEncoder().encode(user[1]),
               authoritiesStrings.stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList())));
      }
      return inMemoryUserDetailsManager;
   }


   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http
            .csrf().disable()
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic();
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }
}
```

### 使用Junit进行测试

```java
package com.example.activiti;

import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: activiti-spring
 * @author: yuzuki
 * @create: 2020-10-20 13:47
 * @description:
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiTest {
	@Resource
	private RepositoryService repositoryService;
	@Resource
	private TaskService taskService;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private HistoryService historyService;
	@Resource
	private ProcessRuntime processRuntime;
	@Autowired
	private TaskRuntime taskRuntime;
	@Resource
	private SecurityUtil securityUtil;

	/**
	 * 流程定义信息的查看
	 * 流程部署工作在activiti7与springboot整合后，会自动实现部署
	 * resource/processes/*.bpmn
	 */
	@Test
	public void testDefinition() {
		// springSecurity的认证工作
		securityUtil.logInAs("user");
		// 分页查询出流程定义信息
		Page processDefinitionPage = processRuntime
				.processDefinitions(Pageable.of(0, 10));
		// 查看已部署的流程
		System.out.println(processDefinitionPage.getTotalItems());
		// getContent 得到当前部署的每个流程定义信息
		for (Object pd : processDefinitionPage.getContent()) {
			System.out.println(pd);
		}
	}

	/**
	 * 启动流程实例
	 * 直接使用processRuntime启动实例失败，通过runtimeService启动
	 * 其实processRuntime底层也是调用的runtimeService
	 */
	@Test
	public void testStartProcess() {
		// security认证
		securityUtil.logInAs("user");
		// 启动实例
		ProcessInstance pi = processRuntime
				.start(ProcessPayloadBuilder
						.start()
						.withProcessDefinitionKey("myProcess_1")
						.build());
		System.out.println("流程实例ID：" + pi.getId());
	}

	/**
	 * 查询任务并完成任务
	 */
	@Test
	public void testTask() {
		// 同组不同人登录
		securityUtil.logInAs("ryandawsonuk");
		// 查询任务
		Page<org.activiti.api.task.model.Task> taskPage = taskRuntime.tasks(Pageable.of(0, 10));
		if (taskPage.getTotalItems() > 0) {
			// 遍历任务列表取出任务信息
			for (org.activiti.api.task.model.Task task : taskPage.getContent()) {
				// 拾取任务
				taskRuntime
						.claim(TaskPayloadBuilder
								.claim()
								.withTaskId(task.getId())
								.build());
				System.out.println("task1 = " + task);
				// 执行/完成任务
				taskRuntime
						.complete(TaskPayloadBuilder
								.complete()
								.withTaskId(task.getId())
								.build());
			}
		}
		// 再次查询新任务
		taskPage = taskRuntime.tasks(Pageable.of(0, 10));
		if (taskPage.getTotalItems() > 0) {
			// 遍历任务列表取出任务信息
			for (org.activiti.api.task.model.Task task : taskPage.getContent()) {
				System.out.println("task2 = " + task);
			}
		}
	}

	/**
	 * 根据bpmn文件，创建一个新的流程
	 */
	@Test
	public void create() {
		Deployment deployment = repositoryService.createDeployment()
				.addClasspathResource("processes/myProcess_leave.bpmn")//添加bpmn资源
//                .addClasspathResource("diagram/holiday.png")
				.name("请假申请单流程")
				.key("myProcess_leave")
				.deploy();

		System.out.println("流程部署id:" + deployment.getId());
		System.out.println("流程部署名称:" + deployment.getName());
		System.out.println("deployment.getKey() = " + deployment.getKey());
	}

	/**
	 * 查询已有的流程
	 */
	@Test
	public void read() {
		List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().list();
		for (ProcessDefinition p : processDefinitions) {
			System.out.println(p.getDeploymentId());
			System.out.println("p.getKey() = " + p.getKey());
			System.out.println("p.getName() = " + p.getName());
			System.out.println(p);
		}
	}

	/**
	 * 删除已有的流程
	 */
	@Test
	public void delete() {
		//这个id是DeploymentId
		repositoryService.deleteDeployment("7243c6b0-129a-11eb-a911-0433c2acfca1");

		List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().list();
		for (ProcessDefinition p : processDefinitions) {
			System.out.println("DeploymentId:" + p.getDeploymentId());
			System.out.println(p);
		}
	}

	/**
	 * 启动一个实例
	 */
	@Test
	public void startProcessInstance() {
		System.out.println("Number of process instances: " + runtimeService.createProcessInstanceQuery().count());
//		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess_1");
//		runtimeService.startProcessInstanceById("7243c6b0-129a-11eb-a911-0433c2acfca1");
		runtimeService.startProcessInstanceByKey("myProcess_1");
		System.out.println("Number of process instances: " + runtimeService.createProcessInstanceQuery().count());

	}

	/**
	 * 传递参数、读取参数
	 * 查询用户的任务列表
	 */
	@Test
	public void taskQuery() {
		//根据流程定义的key,负责人assignee来实现当前用户的任务列表查询
		List<Task> list = taskService.createTaskQuery()
				.processDefinitionKey("myProcess_leave")
				.taskCandidateOrAssigned("teamleader")
				.list();

		if (list != null && list.size() > 0) {

			for (Task task : list) {
				Map<String, Object> variables = new HashMap<String, Object>();
				variables.put("employeeName", "Kermit");
				variables.put("numberOfDays", new Integer(4));
				variables.put("vacationMotivation", "I'm really tired!");

				taskService.setVariable(task.getId(), "参数", variables);
			}

			for (Task task : list) {
				System.out.println("任务ID:" + task.getId());
				System.out.println("任务名称:" + task.getName());
				System.out.println("任务的创建时间:" + task.getCreateTime());
				System.out.println("任务的办理人:" + task.getAssignee());
				System.out.println("流程实例ID：" + task.getProcessInstanceId());
				System.out.println("执行对象ID:" + task.getExecutionId());
				System.out.println("流程定义ID:" + task.getProcessDefinitionId());
				System.out.println("getOwner:" + task.getOwner());
				System.out.println("getCategory:" + task.getCategory());
				System.out.println("getDescription:" + task.getDescription());
				System.out.println("getFormKey:" + task.getFormKey());

				Map<String, Object> map1 = (Map<String, Object>) taskService.getVariable(task.getId(), "参数");
				for (Map.Entry<String, Object> m : map1.entrySet()) {
					System.out.println("key:" + m.getKey() + " value:" + m.getValue());
				}
				Map<String, Object> map = task.getProcessVariables();
				for (Map.Entry<String, Object> m : map.entrySet()) {
					System.out.println("key:" + m.getKey() + " value:" + m.getValue());
				}
				for (Map.Entry<String, Object> m : task.getTaskLocalVariables().entrySet()) {
					System.out.println("key:" + m.getKey() + " value:" + m.getValue());
				}

			}
		}
	}

	/**
	 * 完成任务
	 */
	@Test
	public void completeTask() {
		securityUtil.logInAs("teamleader");        //指定组内任务人
		Page<org.activiti.api.task.model.Task> tasks = taskRuntime.tasks(Pageable.of(0, 10));
		if (tasks.getTotalItems() > 0) {
			for (org.activiti.api.task.model.Task task : tasks.getContent()) {
				System.out.println("任务名称：" + task.getName());
				//拾取任务
				taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
				//执行任务
				taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build());
			}
		}
	}

	/**
	 * 历史活动实例查询,参数也能查到
	 */
	@Test
	public void queryHistoryTask() {
		List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery() // 创建历史活动实例查询
//                .processInstanceId("9671cdea-3367-11ea-a057-30b49ec7161f") // 执行流程实例id
				.includeProcessVariables()
				.orderByTaskCreateTime()
				.asc()
				.list();
		for (HistoricTaskInstance hai : list) {
			System.out.println("活动ID:" + hai.getId());
			System.out.println("流程实例ID:" + hai.getProcessInstanceId());
			System.out.println("活动名称：" + hai.getName());
			System.out.println("办理人：" + hai.getAssignee());
			System.out.println("开始时间：" + hai.getStartTime());
			System.out.println("结束时间：" + hai.getEndTime());
		}
	}
}

```

通过processRuntime查看已部署的流程定义信息

runtimeService.startProcessInstanceByKey启动流程任务

使用 TaskRuntime 接口的 tasks()方法实现任务的查询。 

使用 TaskRuntime 接口的 claim()方法实现任务拾取。 

使用 TaskRuntime 接口的 complete()方法实现任务的完成

### 整合springmvc

```java
package com.example.activiti.controller;

import com.example.activiti.SecurityUtil;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: activiti-spring
 * @author: yuzuki
 * @create: 2020-10-20 13:45
 * @description:
 * 控制器 任务执行
 **/
@RestController
public class ActivitiController {
   @Resource
   private ProcessRuntime processRuntime;
   @Resource
   private TaskRuntime taskRuntime;
   @Resource
   private SecurityUtil securityUtil;
   @Autowired
   private ProcessEngine processEngine;

   /**
    * 查询任务，有任务就执行
    */
   @RequestMapping("/sel")
   public void testSel(){
      // 同组不同人登录
      securityUtil.logInAs("ryandawsonuk");
      // 查询任务
      Page<org.activiti.api.task.model.Task> taskPage = taskRuntime.tasks(Pageable.of(0, 10));
      if (taskPage.getTotalItems() > 0) {
         // 遍历任务列表取出任务信息
         for (org.activiti.api.task.model.Task task : taskPage.getContent()) {
            // 拾取任务
            taskRuntime
                  .claim(TaskPayloadBuilder
                        .claim()
                        .withTaskId(task.getId())
                        .build());
            System.out.println("task1 = " + task);
            // 执行/完成任务
            taskRuntime
                  .complete(TaskPayloadBuilder
                        .complete()
                        .withTaskId(task.getId())
                        .build());
         }
      }
      // 再次查询新任务
      taskPage = taskRuntime.tasks(Pageable.of(0, 10));
      if (taskPage.getTotalItems() > 0) {
         // 遍历任务列表取出任务信息
         for (org.activiti.api.task.model.Task task : taskPage.getContent()) {
            System.out.println("task2 = " + task);
         }
      }
   }

   /**
    * 根据bpmn文件部署一个流程
    *
    * @return
    */
   @RequestMapping("/createDeploy")
   public Deployment createDeploy() {
      RepositoryService repositoryService = processEngine.getRepositoryService();

      Deployment deployment = repositoryService.createDeployment()
            .addClasspathResource("processes/leave-approve.bpmn")//添加bpmn资源
//                .addClasspathResource("diagram/holiday.png")
            .name("请假申请单流程")
            .deploy();

      System.out.println("流程部署id:" + deployment.getName());
      System.out.println("流程部署名称:" + deployment.getId());
      return deployment;
   }

   /**
    * 查询已经部署的流程
    */
   @RequestMapping("/getProcess")
   public void getProcess() {
      //查询所有流程定义信息
      Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));
      System.out.println("当前流程定义的数量：" + processDefinitionPage.getTotalItems());
      //获取流程信息
      for (ProcessDefinition processDefinition : processDefinitionPage.getContent()) {
         System.out.println("流程定义信息" + processDefinition);
      }
   }


   /**
    * 启动流程示例
    */
   @RequestMapping("/startInstance")
   public void startInstance() {
      ProcessInstance instance = processRuntime.start(ProcessPayloadBuilder.start().withProcessDefinitionKey("myProcess_leave_approve_1").build());
      System.out.println(instance.getId());
   }

   /**
    * 获取任务，拾取任务，并且执行（认领、完成）
    */
   @RequestMapping("/getTask")
   public void getTask() {
      securityUtil.logInAs("team-leader");        //指定组内任务人
      Page<Task> tasks = taskRuntime.tasks(Pageable.of(0, 10));
      if (tasks.getTotalItems() > 0) {
         for (Task task : tasks.getContent()) {
            System.out.println("任务名称：" + task.getName());
            //拾取任务
            taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
            //执行任务
            taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build());
         }
      }
   }
}
```

其实流程是一样的

流程图可导出jpg，具体开发可与前端商量

流程确定后变更应该不大



## 踩坑

### 流程图插件

idea2019的版本就找不到activiti的流程图绘制插件actiBPM

![image-20201020152031949](D:\Learning\公司学习\activiti\activiti.assets\image-20201020152031949.png)

可以看到该插件2014年后就停更所以与新版本IDEA集成后会出现许多bug

需要去idea插件下载的官网寻找下载jar包

然后从磁盘上导入插件

![image-20201020151950845](D:\Learning\公司学习\activiti\activiti.assets\image-20201020151950845.png)

**插件bug**

自动修改bpmn文件（本质是一个xml文件）

对流程图做任意修改都会报错：

![image-20201020152336681](D:\Learning\公司学习\activiti\activiti.assets\image-20201020152336681.png)

原因是任何改动都会修改bpmn文件在process参数中添加不能识别的参数

![image-20201020152431025](D:\Learning\公司学习\activiti\activiti.assets\image-20201020152431025.png)

只需要把这个参数删掉就可以了，但是对于开发来说就比较麻烦了（暂时没找到可以完整替代的其他插件



**中文乱码问题**

不仅要修改idea的bin目录下的两个vmoptions文件添加启动参数

-Dfile.encoding=UTF-8

还需要去C盘搜索vmoptions文件在所有搜到的启动配置文件中都添加该参数



### 发现的问题

时间不同得设置+8时区

考虑能否使用activiti-explorer创建管理着页面平台