# xxl-job

## xxl调度中心admin怎么实现的，底层是什么

XXL-JOB最终选择自研调度组件（早期调度组件基于Quartz）；一方面是为了精简系统降低冗余依赖，另一方面是为了提供系统的可控度与稳定性；

XXL-JOB中“调度模块”和“任务模块”完全解耦，调度模块进行任务调度时，将会解析不同的任务参数发起远程调用，调用各自的远程执行器服务。这种调用模型类似RPC调用，调度中心提供调用代理的功能，而执行器提供远程服务的功能。

调度采用线程池方式实现，避免单线程因阻塞而引起任务调度延迟。

XXL-JOB调度模块默认采用并行机制，在多线程调度的情况下，调度模块被阻塞的几率很低，大大提高了调度系统的承载量。

- 全异步化设计：XXL-JOB系统中业务逻辑在远程执行器执行，触发流程全异步化设计。相比直接在调度中心内部执行业务逻辑，极大的降低了调度线程占用时间；
  - 异步调度：调度中心每次任务触发时仅发送一次调度请求，该调度请求首先推送“异步调度队列”，然后异步推送给远程执行器
  - 异步执行：执行器会将请求存入“异步执行队列”并且立即响应调度中心，异步运行。
- 轻量级设计：XXL-JOB调度中心中每个JOB逻辑非常 “轻”，在全异步化的基础上，单个JOB一次运行平均耗时基本在 “10ms” 之内（基本为一次请求的网络开销）；因此，可以保证使用有限的线程支撑大量的JOB并发运行；

得益于上述两点优化，理论上默认配置下的调度中心，单机能够支撑 5000 任务并发运行稳定运行；

实际场景中，由于调度中心与执行器网络ping延迟不同、DB读写耗时不同、任务调度密集程度不同，会导致任务量上限会上下波动。

如若需要支撑更多的任务量，可以通过 “调大调度线程数” 、”降低调度中心与执行器ping延迟” 和 “提升机器配置” 几种方式优化。

**一次完整的任务调度通讯流程**

```
- 1、“调度中心”向“执行器”发送http调度请求: “执行器”中接收请求的服务，实际上是一台内嵌Server，默认端口9999;
- 2、“执行器”执行任务逻辑；
- 3、“执行器”http回调“调度中心”调度结果: “调度中心”中接收回调的服务，是针对执行器开放一套API服务;
```

## 多系统的注册如何实现

指挥平台暴露任务调度中心账号出去

其他系统的注册在参数中添加

## 多系统公用的任务调度中心的实现

写一个/多个调度器，多系统必须系统之间通过各种协议请求可以进行交互，起码咱们这边能调用其他系统的接口

调度器对应

![image-20201027173714243](D:\Learning\公司学习\xxl-job\xxl-job.assets\image-20201027173714243.png)

后端接收到请求后会接收param参数，

尝试验证是否是前端传递过来的最后一个自己编写的

可以考虑自己设定一套json规则

返回必须封装为ReturnT原因是要交给XXL去进行任务调度监控

尝试获取参数

其他参数是xxl-admin去使用的





## 故障转移

一次完整任务流程包括”调度（调度中心） + 执行（执行器）”两个阶段。

- “故障转移”发生在调度阶段，在执行器集群部署时，如果某一台执行器发生故障，该策略支持自动进行Failover切换到一台正常的执行器机器并且完成调度请求流程。
- “失败重试”发生在”调度 + 执行”两个阶段，支持通过自定义任务失败重试次数，当任务失败时将会按照预设的失败重试次数主动进行重试；

任务调度

com.xxl.job.core.biz.ExecutorBiz.run接口，具体实现方法：

```java
    @Override
    public ReturnT<String> run(TriggerParam triggerParam) {
        // load old：jobHandler + jobThread
        JobThread jobThread = XxlJobExecutor.loadJobThread(triggerParam.getJobId());
        IJobHandler jobHandler = jobThread!=null?jobThread.getHandler():null;
        String removeOldReason = null;

        // valid：jobHandler + jobThread
        GlueTypeEnum glueTypeEnum = GlueTypeEnum.match(triggerParam.getGlueType());
        if (GlueTypeEnum.BEAN == glueTypeEnum) {

            // new jobhandler
            IJobHandler newJobHandler = XxlJobExecutor.loadJobHandler(triggerParam.getExecutorHandler());

            // valid old jobThread
            if (jobThread!=null && jobHandler != newJobHandler) {
                // change handler, need kill old thread
                removeOldReason = "change jobhandler or glue type, and terminate the old job thread.";

                jobThread = null;
                jobHandler = null;
            }

            // valid handler
            if (jobHandler == null) {
                jobHandler = newJobHandler;
                if (jobHandler == null) {
                    return new ReturnT<String>(ReturnT.FAIL_CODE, "job handler [" + triggerParam.getExecutorHandler() + "] not found.");
                }
            }

        } else if (GlueTypeEnum.GLUE_GROOVY == glueTypeEnum) {

            // valid old jobThread
            if (jobThread != null &&
                    !(jobThread.getHandler() instanceof GlueJobHandler
                        && ((GlueJobHandler) jobThread.getHandler()).getGlueUpdatetime()==triggerParam.getGlueUpdatetime() )) {
                // change handler or gluesource updated, need kill old thread
                removeOldReason = "change job source or glue type, and terminate the old job thread.";

                jobThread = null;
                jobHandler = null;
            }

            // valid handler
            if (jobHandler == null) {
                try {
                    IJobHandler originJobHandler = GlueFactory.getInstance().loadNewInstance(triggerParam.getGlueSource());
                    jobHandler = new GlueJobHandler(originJobHandler, triggerParam.getGlueUpdatetime());
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    return new ReturnT<String>(ReturnT.FAIL_CODE, e.getMessage());
                }
            }
        } else if (glueTypeEnum!=null && glueTypeEnum.isScript()) {

            // valid old jobThread
            if (jobThread != null &&
                    !(jobThread.getHandler() instanceof ScriptJobHandler
                            && ((ScriptJobHandler) jobThread.getHandler()).getGlueUpdatetime()==triggerParam.getGlueUpdatetime() )) {
                // change script or gluesource updated, need kill old thread
                removeOldReason = "change job source or glue type, and terminate the old job thread.";

                jobThread = null;
                jobHandler = null;
            }

            // valid handler
            if (jobHandler == null) {
                jobHandler = new ScriptJobHandler(triggerParam.getJobId(), triggerParam.getGlueUpdatetime(), triggerParam.getGlueSource(), GlueTypeEnum.match(triggerParam.getGlueType()));
            }
        } else {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "glueType[" + triggerParam.getGlueType() + "] is not valid.");
        }

        // executor block strategy
        if (jobThread != null) {
            ExecutorBlockStrategyEnum blockStrategy = ExecutorBlockStrategyEnum.match(triggerParam.getExecutorBlockStrategy(), null);
            if (ExecutorBlockStrategyEnum.DISCARD_LATER == blockStrategy) {
                // discard when running
                if (jobThread.isRunningOrHasQueue()) {
                    return new ReturnT<String>(ReturnT.FAIL_CODE, "block strategy effect："+ExecutorBlockStrategyEnum.DISCARD_LATER.getTitle());
                }
            } else if (ExecutorBlockStrategyEnum.COVER_EARLY == blockStrategy) {
                // kill running jobThread
                if (jobThread.isRunningOrHasQueue()) {
                    removeOldReason = "block strategy effect：" + ExecutorBlockStrategyEnum.COVER_EARLY.getTitle();

                    jobThread = null;
                }
            } else {
                // just queue trigger
            }
        }

        // replace thread (new or exists invalid)
        if (jobThread == null) {
            jobThread = XxlJobExecutor.registJobThread(triggerParam.getJobId(), jobHandler, removeOldReason);
        }

        // push data to queue
        ReturnT<String> pushResult = jobThread.pushTriggerQueue(triggerParam);
        return pushResult;
    }
```



## 源码分析

目标：找出执行的故障转移及其他策略如何实现的（居然属于路由策略，去查看路由策略相关的源码

由官方文档[执行器 RESTful API](https://www.xuxueli.com/xxl-job/#6.2 执行器 RESTful API)引发的源码阅读

现在进度：找到了任务运行的源码，主要是密集调度时的阻塞处理策略分配方式、执行器ID及handler的优先级，具体执行方法handler发生变更时的变化，内部的任务存储容器，任务超时时间的设定、运行模式的匹配对运行产生的影响

任务超时时间的设定与生效主要体现在内部使用线程的运行代表某个任务的执行，在执行时如果有超时时间的设定采用FutureTask去运行具体逻辑，根据返回值查看是否运行超时，再执行超时出错逻辑





看到了阻塞策略的执行源码

任务阻塞策略

在执行过程中出现了策略匹配

阻塞处理策略：调度过于密集执行器来不及处理时的处理策略，策略包括：单机串行（默认）、丢弃后续调度、覆盖之前调度；



**自定义Executor**

触发任务传入参数

API服务位置：com.xxl.job.core.biz.ExecutorBiz
API服务请求参考代码：com.xxl.job.executorbiz.ExecutorBizTest

**触发任务**

```json
说明：触发任务执行
------
地址格式：{执行器内嵌服务跟地址}/run
Header：
    XXL-JOB-ACCESS-TOKEN : {请求令牌}
请求数据格式如下，放置在 RequestBody 中，JSON格式：
    {
        "jobId":1,                                  // 任务ID
        "executorHandler":"demoJobHandler",         // 任务标识
        "executorParams":"demoJobHandler",          // 任务参数
        "executorBlockStrategy":"COVER_EARLY",      // 任务阻塞策略，可选值参考 com.xxl.job.core.enums.ExecutorBlockStrategyEnum
        "executorTimeout":0,                        // 任务超时时间，单位秒，大于零时生效
        "logId":1,                                  // 本次调度日志ID
        "logDateTime":1586629003729,                // 本次调度日志时间
        "glueType":"BEAN",                          // 任务模式，可选值参考 com.xxl.job.core.glue.GlueTypeEnum
        "glueSource":"xxx",                         // GLUE脚本代码
        "glueUpdatetime":1586629003727,             // GLUE脚本更新时间，用于判定脚本是否变更以及是否需要刷新
        "broadcastIndex":0,                         // 分片参数：当前分片
        "broadcastTotal":0                          // 分片参数：总分片
    }
    响应数据格式：
    {
      "code": 200,      // 200 表示正常、其他失败
      "msg": null       // 错误提示消息
    }
```



java.util.concurrent.LinkedBlockingQueue

线程安全的链式阻塞队列，阻塞队列，队列容量不足自动阻塞，队列容量为 0 自动阻塞。

Executor中使用ConcurrentHashMap<Integer,JobThread> 一个线程安全的hashmap容器来存储jobThread（任务执行线程

jobThreadRepository



```java
private static ConcurrentMap<Integer, JobThread> jobThreadRepository = new ConcurrentHashMap<Integer, JobThread>();
```

id：thread的KV键值对，通过传递过来的ID获取要执行的线程Thread

使用阻塞队列保存参数

线程停止

```java
   /**
    * kill job thread
    *
    * @param stopReason
    */
public void toStop(String stopReason) {
   /**
    * Thread.interrupt只支持终止线程的阻塞状态(wait、join、sleep)，
    * 在阻塞出抛出InterruptedException异常,但是并不会终止运行的线程本身；
    * 所以需要注意，此处彻底销毁本线程，需要通过共享变量方式；
    * 所以加上volatile让多线程可见么
    */
   this.toStop = true;
   this.stopReason = stopReason;
}
```

toStop加上volatile修饰保证线程可见，线程停止后通知其他线程，可能避免影响任务与子任务之间的调用关系

Futuretask

运行超时，get就出错

可以看出，RunnableFuture继承了Runnable接口和Future接口，而FutureTask实现了RunnableFuture接口。所以它既可以作为Runnable被线程执行，又可以作为Future得到Callable的返回值。

**异步 可运行 带返回值**

任务执行就调用IJobHandler的具体实现类去执行，这边就可以定义我们自己的任务执行逻辑

MethodJobHandler

反射，method调用，传参



### 主要的run方法解析

运行主要步骤：

1. 根据传递过来的参数去任务容器中查看是否存在运行过的处理方法
2. 根据不同的Glue（运行模式）执行不同逻辑，在BEAN的运行模式下去根据XxlJob注册的value获取处理方法
3. 两次获取的处理方法对比，如果不同说明在页面上重新配置了处理方法并使用新的处理方法，传参时体现在executorHandler优于jobId，前者更为准确
4. 调度过于密集时根据配置选择不同的处理策略
5. 运行任务，并更新容器
6. 记录结果并返回

```java
    @Override
    public ReturnT<String> run(TriggerParam triggerParam) {
        // load old：jobHandler + jobThread
        // 从executor中获取具体要执行的线程
        // TriggerParam封装触发请求参数，这个参数由控制中心点击触发任务时传递过来
        // 根据jobid判断要执行的定时任务
        // 在这里获取的jobThread是旧的是根据界面ID来的，ID是同一个但具体handler可能发生变动
        JobThread jobThread = XxlJobExecutor.loadJobThread(triggerParam.getJobId());
        // 拿到要执行的handler否则置空
        IJobHandler jobHandler = jobThread!=null?jobThread.getHandler():null;
        String removeOldReason = null;

        // valid：jobHandler + jobThread
        // 检测Glue类型是否在限定之内
        GlueTypeEnum glueTypeEnum = GlueTypeEnum.match(triggerParam.getGlueType());
        // 根据不同任务运行模式GLUE分逻辑执行
        if (GlueTypeEnum.BEAN == glueTypeEnum) {

            // new jobhandler
            // 根据传递过来的具体任务名寻找具体处理逻辑
            // loadJobHandler方法进行了重载
            // 根据id就是跟着界面传过来的id这个id标识界面上的某个执行任务
            // 根据String的方式就是根据配置的具体任务标识，String类型的标识，与@XxlJob(value="")中的value相对应
            IJobHandler newJobHandler = XxlJobExecutor.loadJobHandler(triggerParam.getExecutorHandler());

            // 两次获取的jobHandler不一致，说明之前有注册过但是更改了配置（比如执行器/任务的具体执行方法的变更
            // valid old jobThread
            if (jobThread!=null && jobHandler != newJobHandler) {
                // change handler, need kill old thread
                // 变更执行方法停止旧任务
                removeOldReason = "change jobhandler or glue type, and terminate the old job thread.";

                // 一旦具体handler变动就清空之前的任务
                jobThread = null;
                jobHandler = null;
            }

            // valid handler
            // 旧的handler变为新的（变更过的）
            if (jobHandler == null) {
                jobHandler = newJobHandler;
                if (jobHandler == null) {
                    return new ReturnT<String>(ReturnT.FAIL_CODE, "job handler [" + triggerParam.getExecutorHandler() + "] not found.");
                }
            }

        } else if (GlueTypeEnum.GLUE_GROOVY == glueTypeEnum) {

            // valid old jobThread
            if (jobThread != null &&
                    !(jobThread.getHandler() instanceof GlueJobHandler
                        && ((GlueJobHandler) jobThread.getHandler()).getGlueUpdatetime()==triggerParam.getGlueUpdatetime() )) {
                // change handler or gluesource updated, need kill old thread
                removeOldReason = "change job source or glue type, and terminate the old job thread.";

                jobThread = null;
                jobHandler = null;
            }

            // valid handler
            if (jobHandler == null) {
                try {
                    IJobHandler originJobHandler = GlueFactory.getInstance().loadNewInstance(triggerParam.getGlueSource());
                    jobHandler = new GlueJobHandler(originJobHandler, triggerParam.getGlueUpdatetime());
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    return new ReturnT<String>(ReturnT.FAIL_CODE, e.getMessage());
                }
            }
        } else if (glueTypeEnum!=null && glueTypeEnum.isScript()) {

            // valid old jobThread
            if (jobThread != null &&
                    !(jobThread.getHandler() instanceof ScriptJobHandler
                            && ((ScriptJobHandler) jobThread.getHandler()).getGlueUpdatetime()==triggerParam.getGlueUpdatetime() )) {
                // change script or gluesource updated, need kill old thread
                removeOldReason = "change job source or glue type, and terminate the old job thread.";

                jobThread = null;
                jobHandler = null;
            }

            // valid handler
            if (jobHandler == null) {
                jobHandler = new ScriptJobHandler(triggerParam.getJobId(), triggerParam.getGlueUpdatetime(), triggerParam.getGlueSource(), GlueTypeEnum.match(triggerParam.getGlueType()));
            }
        } else {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "glueType[" + triggerParam.getGlueType() + "] is not valid.");
        }

        // executor block strategy
        // 策略执行块，通过不同的运行模式找到固定handler后去执行
        // 配置调度过于密集执行器来不及处理时的处理策略
        if (jobThread != null) {
            // 匹配执行策略
            // 可在这里进行策略扩展改动源码实现自己的阻塞处理策略
            // 策略匹配
            // 任务阻塞策略
            ExecutorBlockStrategyEnum blockStrategy = ExecutorBlockStrategyEnum.match(triggerParam.getExecutorBlockStrategy(), null);
            // 丢弃后续调度
            if (ExecutorBlockStrategyEnum.DISCARD_LATER == blockStrategy) {
                // discard when running
                if (jobThread.isRunningOrHasQueue()) {
                    return new ReturnT<String>(ReturnT.FAIL_CODE, "block strategy effect："+ExecutorBlockStrategyEnum.DISCARD_LATER.getTitle());
                }
                // 覆盖之前调度
            } else if (ExecutorBlockStrategyEnum.COVER_EARLY == blockStrategy) {
                // kill running jobThread
                if (jobThread.isRunningOrHasQueue()) {
                    removeOldReason = "block strategy effect：" + ExecutorBlockStrategyEnum.COVER_EARLY.getTitle();

                    jobThread = null;
                }
            } else {
                // just queue trigger
            }
        }

        // replace thread (new or exists invalid)
        if (jobThread == null) {
            // 执行新的任务，如果任务有更新就中断旧任务
            jobThread = XxlJobExecutor.registJobThread(triggerParam.getJobId(), jobHandler, removeOldReason);
        }

        // push data to queue
        // 将运行结果放入容器
        ReturnT<String> pushResult = jobThread.pushTriggerQueue(triggerParam);
        return pushResult;
    }
```

**主要涉及的JobThread及XxlJobExecutor方法：**

**XxlJobExecutor**

```java
    /**
     *
     * @param jobId 调度中心传递过来的要执行任务的唯一标识ID
     * @return
     */
    public static JobThread loadJobThread(int jobId){
        // 自定义的JobThread
        JobThread jobThread = jobThreadRepository.get(jobId);
        return jobThread;
    }
    public static JobThread registJobThread(int jobId, IJobHandler handler, String removeOldReason){
        JobThread newJobThread = new JobThread(jobId, handler);
        newJobThread.start();
        logger.info(">>>>>>>>>>> xxl-job regist JobThread success, jobId:{}, handler:{}", new Object[]{jobId, handler});

        // 运行新任务，更新任务容器jobThreadRepository，更新后居然会返回旧的value
        // 对旧任务进行处理和中断处理，代表任务更新
        JobThread oldJobThread = jobThreadRepository.put(jobId, newJobThread);	// putIfAbsent | oh my god, map's put method return the old value!!!
        if (oldJobThread != null) {
            oldJobThread.toStop(removeOldReason);
            oldJobThread.interrupt();
        }

        return newJobThread;
    }
    
```

**JobThread**

并没有使用阻塞队列的特性只是单纯的当做队列使用，只是利用了阻塞队列线程安全的特性

```java
    /**
     * new trigger to queue
     *
     * @param triggerParam
     * @return
     */
	public ReturnT<String> pushTriggerQueue(TriggerParam triggerParam) {
		// avoid repeat
		if (triggerLogIdSet.contains(triggerParam.getLogId())) {
			logger.info(">>>>>>>>>>> repeate trigger job, logId:{}", triggerParam.getLogId());
			return new ReturnT<String>(ReturnT.FAIL_CODE, "repeate trigger job, logId:" + triggerParam.getLogId());
		}

		triggerLogIdSet.add(triggerParam.getLogId());
		// 在LinkedBlockQueue中使用add添加元素在队列满会抛出IllegalStateException
		// 建议在双端队列添加元素时采用offer，队列满也不会抛出异常，会被阻塞
		triggerQueue.add(triggerParam);
        return ReturnT.SUCCESS;
	}
    @Override
	public void run() {

    	// init
		// 处理器初始化
    	try {
			handler.init();
		} catch (Throwable e) {
    		logger.error(e.getMessage(), e);
		}

		// execute
		while(!toStop){
			// 置为false只有查到运行参数才开启
			running = false;
			// 尝试运行次数，计数自增，非线程安全
			idleTimes++;

			// 清空参数
            TriggerParam triggerParam = null;
            ReturnT<String> executeResult = null;
            try {
            	// 要检查停止信号，我们需要循环，所以我们不能使用queue.take（）代替poll（timeout）
				// take不仅查看还会去除首部元素
				// to check toStop signal, we need cycle, so wo cannot use queue.take(), instand of poll(timeout)
				triggerParam = triggerQueue.poll(3L, TimeUnit.SECONDS);
				if (triggerParam!=null) {
					// 标识当前任务（线程）已经开启
					running = true;
					// 重置尝试运行次数
					idleTimes = 0;
					// 参数调用过了，移除当前参数
					triggerLogIdSet.remove(triggerParam.getLogId());

					// 记录日志
					// log filename, like "logPath/yyyy-MM-dd/9999.log"
					String logFileName = XxlJobFileAppender.makeLogFileName(new Date(triggerParam.getLogDateTime()), triggerParam.getLogId());
					XxlJobContext.setXxlJobContext(new XxlJobContext(
							triggerParam.getLogId(),
							logFileName,
							triggerParam.getBroadcastIndex(),
							triggerParam.getBroadcastTotal()));

					// execute
					XxlJobLogger.log("<br>----------- xxl-job job execute start -----------<br>----------- Param:" + triggerParam.getExecutorParams());

					// 如果运行定义了超时参数
					// 使用futureTask执行，方便执行并传回执行结果
					if (triggerParam.getExecutorTimeout() > 0) {
						// limit timeout
						Thread futureThread = null;
						try {
							final TriggerParam triggerParamTmp = triggerParam;
							FutureTask<ReturnT<String>> futureTask = new FutureTask<ReturnT<String>>(new Callable<ReturnT<String>>() {
								@Override
								public ReturnT<String> call() throws Exception {
									return handler.execute(triggerParamTmp.getExecutorParams());
								}
							});
							futureThread = new Thread(futureTask);
							futureThread.start();

							executeResult = futureTask.get(triggerParam.getExecutorTimeout(), TimeUnit.SECONDS);
						} catch (TimeoutException e) {

							XxlJobLogger.log("<br>----------- xxl-job job execute timeout");
							XxlJobLogger.log(e);

							executeResult = new ReturnT<String>(IJobHandler.FAIL_TIMEOUT.getCode(), "job execute timeout ");
						} finally {
							futureThread.interrupt();
						}
					} else {
						// just execute
						executeResult = handler.execute(triggerParam.getExecutorParams());
					}

					if (executeResult == null) {
						executeResult = IJobHandler.FAIL;
					} else {
						executeResult.setMsg(
								(executeResult!=null&&executeResult.getMsg()!=null&&executeResult.getMsg().length()>50000)
										?executeResult.getMsg().substring(0, 50000).concat("...")
										:executeResult.getMsg());
						executeResult.setContent(null);	// limit obj size
					}
					XxlJobLogger.log("<br>----------- xxl-job job execute end(finish) -----------<br>----------- ReturnT:" + executeResult);

				} else {
					// 重试超过三十次移除任务
					if (idleTimes > 30) {
						if(triggerQueue.size() == 0) {	// avoid concurrent trigger causes jobId-lost
							XxlJobExecutor.removeJobThread(jobId, "excutor idel times over limit.");
						}
					}
				}
			} catch (Throwable e) {
				if (toStop) {
					XxlJobLogger.log("<br>----------- JobThread toStop, stopReason:" + stopReason);
				}

				StringWriter stringWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(stringWriter));
				String errorMsg = stringWriter.toString();
				executeResult = new ReturnT<String>(ReturnT.FAIL_CODE, errorMsg);

				XxlJobLogger.log("<br>----------- JobThread Exception:" + errorMsg + "<br>----------- xxl-job job execute end(error) -----------");
			} finally {
                if(triggerParam != null) {
                    // callback handler info
                    if (!toStop) {
                        // commonm
                        TriggerCallbackThread.pushCallBack(new HandleCallbackParam(triggerParam.getLogId(), triggerParam.getLogDateTime(), executeResult));
                    } else {
                        // is killed
                        ReturnT<String> stopResult = new ReturnT<String>(ReturnT.FAIL_CODE, stopReason + " [job running, killed]");
                        TriggerCallbackThread.pushCallBack(new HandleCallbackParam(triggerParam.getLogId(), triggerParam.getLogDateTime(), stopResult));
                    }
                }
            }
        }

		// callback trigger request in queue
		while(triggerQueue !=null && triggerQueue.size()>0){
			TriggerParam triggerParam = triggerQueue.poll();
			if (triggerParam!=null) {
				// is killed
				ReturnT<String> stopResult = new ReturnT<String>(ReturnT.FAIL_CODE, stopReason + " [job not executed, in the job queue, killed.]");
				TriggerCallbackThread.pushCallBack(new HandleCallbackParam(triggerParam.getLogId(), triggerParam.getLogDateTime(), stopResult));
			}
		}

		// destroy
		try {
			handler.destroy();
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}

		logger.info(">>>>>>>>>>> xxl-job JobThread stoped, hashCode:{}", Thread.currentThread());
	}

```



## JobThread

使用LinkedBlockQueue存储参数，任务执行包含失败重试（默认重试上线：30次）

调用toStop方法使用volatile修饰参数保证当前线程停止后所有线程可见



运行分为带参数和不带参数

带参数直接执行

不带参数说明有问题，重复重试请求，超过三十次任务取消，自旋锁

有参数后运行分为超时和不超时



```java
package com.xxl.job.core.thread;

import com.xxl.job.core.biz.model.HandleCallbackParam;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.biz.model.TriggerParam;
import com.xxl.job.core.context.XxlJobContext;
import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.log.XxlJobFileAppender;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;


/**
 * handler thread
 * @author xuxueli 2016-1-16 19:52:47
 */
public class JobThread extends Thread{
	private static Logger logger = LoggerFactory.getLogger(JobThread.class);

	private int jobId;
	/**
	 * 真正的任务处理器
	 */
	private IJobHandler handler;
	/**
	 * 使用线程安全的链式阻塞队列存储参数
	 */
	private LinkedBlockingQueue<TriggerParam> triggerQueue;
	private Set<Long> triggerLogIdSet;		// avoid repeat trigger for the same TRIGGER_LOG_ID

	/**
	 * volatile修饰，禁止指令重排，保证可见性，不完全保证线程安全
	 * 使用场景：
	 * 一写多读场景，某个线程改变变量，其他线程并不一定马上可见，因为线程内部会维护自己的内存区域
	 * 乐观锁场景，使用自旋锁实现乐观锁的时候加入volatile关键字可避免无意义的自选浪费大量资源（时间/CPU或者其他）
	 */
	private volatile boolean toStop = false;
	private String stopReason;

    private boolean running = false;    // if running job
	private int idleTimes = 0;			// idel times


	public JobThread(int jobId, IJobHandler handler) {
		this.jobId = jobId;
		this.handler = handler;
		this.triggerQueue = new LinkedBlockingQueue<TriggerParam>();
		this.triggerLogIdSet = Collections.synchronizedSet(new HashSet<Long>());
	}
	public IJobHandler getHandler() {
		return handler;
	}

    /**
     * new trigger to queue
     *
     * @param triggerParam
     * @return
     */
	public ReturnT<String> pushTriggerQueue(TriggerParam triggerParam) {
		// avoid repeat
		if (triggerLogIdSet.contains(triggerParam.getLogId())) {
			logger.info(">>>>>>>>>>> repeate trigger job, logId:{}", triggerParam.getLogId());
			return new ReturnT<String>(ReturnT.FAIL_CODE, "repeate trigger job, logId:" + triggerParam.getLogId());
		}

		triggerLogIdSet.add(triggerParam.getLogId());
		// 在LinkedBlockQueue中使用add添加元素在队列满会抛出IllegalStateException
		// 建议在双端队列添加元素时采用offer，队列满也不会抛出异常，会被阻塞
		triggerQueue.add(triggerParam);
        return ReturnT.SUCCESS;
	}

    /**
     * kill job thread
     *
     * @param stopReason
     */
	public void toStop(String stopReason) {
		/**
		 * Thread.interrupt只支持终止线程的阻塞状态(wait、join、sleep)，
		 * 在阻塞出抛出InterruptedException异常,但是并不会终止运行的线程本身；
		 * 所以需要注意，此处彻底销毁本线程，需要通过共享变量方式；
		 * 所以加上volatile让多线程可见么
		 */
		this.toStop = true;
		this.stopReason = stopReason;
	}

    /**
     * is running job
     * @return
     */
    public boolean isRunningOrHasQueue() {
        return running || triggerQueue.size()>0;
    }

    @Override
	public void run() {

    	// init
		// 处理器初始化
    	try {
			handler.init();
		} catch (Throwable e) {
    		logger.error(e.getMessage(), e);
		}

		// execute
		while(!toStop){
			// 置为false只有查到运行参数才开启
			running = false;
			// 尝试运行次数，计数自增，非线程安全
			idleTimes++;

			// 清空参数
            TriggerParam triggerParam = null;
            ReturnT<String> executeResult = null;
            try {
            	// 要检查停止信号，我们需要循环，所以我们不能使用queue.take（）代替poll（timeout）
				// take不仅查看还会去除首部元素
				// to check toStop signal, we need cycle, so wo cannot use queue.take(), instand of poll(timeout)
				triggerParam = triggerQueue.poll(3L, TimeUnit.SECONDS);
				if (triggerParam!=null) {
					// 标识当前任务（线程）已经开启
					running = true;
					// 重置尝试运行次数
					idleTimes = 0;
					// 参数调用过了，移除当前参数
					triggerLogIdSet.remove(triggerParam.getLogId());

					// 记录日志
					// log filename, like "logPath/yyyy-MM-dd/9999.log"
					String logFileName = XxlJobFileAppender.makeLogFileName(new Date(triggerParam.getLogDateTime()), triggerParam.getLogId());
					XxlJobContext.setXxlJobContext(new XxlJobContext(
							triggerParam.getLogId(),
							logFileName,
							triggerParam.getBroadcastIndex(),
							triggerParam.getBroadcastTotal()));

					// execute
					XxlJobLogger.log("<br>----------- xxl-job job execute start -----------<br>----------- Param:" + triggerParam.getExecutorParams());

					// 如果运行定义了超时参数
					// 使用futureTask执行，方便执行并传回执行结果
					if (triggerParam.getExecutorTimeout() > 0) {
						// limit timeout
						Thread futureThread = null;
						try {
							final TriggerParam triggerParamTmp = triggerParam;
							FutureTask<ReturnT<String>> futureTask = new FutureTask<ReturnT<String>>(new Callable<ReturnT<String>>() {
								@Override
								public ReturnT<String> call() throws Exception {
									return handler.execute(triggerParamTmp.getExecutorParams());
								}
							});
							futureThread = new Thread(futureTask);
							futureThread.start();

							executeResult = futureTask.get(triggerParam.getExecutorTimeout(), TimeUnit.SECONDS);
						} catch (TimeoutException e) {

							XxlJobLogger.log("<br>----------- xxl-job job execute timeout");
							XxlJobLogger.log(e);

							executeResult = new ReturnT<String>(IJobHandler.FAIL_TIMEOUT.getCode(), "job execute timeout ");
						} finally {
							futureThread.interrupt();
						}
					} else {
						// just execute
						executeResult = handler.execute(triggerParam.getExecutorParams());
					}

					if (executeResult == null) {
						executeResult = IJobHandler.FAIL;
					} else {
						executeResult.setMsg(
								(executeResult!=null&&executeResult.getMsg()!=null&&executeResult.getMsg().length()>50000)
										?executeResult.getMsg().substring(0, 50000).concat("...")
										:executeResult.getMsg());
						executeResult.setContent(null);	// limit obj size
					}
					XxlJobLogger.log("<br>----------- xxl-job job execute end(finish) -----------<br>----------- ReturnT:" + executeResult);

				} else {
					// 重试超过三十次移除任务
					if (idleTimes > 30) {
						if(triggerQueue.size() == 0) {	// avoid concurrent trigger causes jobId-lost
							XxlJobExecutor.removeJobThread(jobId, "excutor idel times over limit.");
						}
					}
				}
			} catch (Throwable e) {
				if (toStop) {
					XxlJobLogger.log("<br>----------- JobThread toStop, stopReason:" + stopReason);
				}

				StringWriter stringWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(stringWriter));
				String errorMsg = stringWriter.toString();
				executeResult = new ReturnT<String>(ReturnT.FAIL_CODE, errorMsg);

				XxlJobLogger.log("<br>----------- JobThread Exception:" + errorMsg + "<br>----------- xxl-job job execute end(error) -----------");
			} finally {
                if(triggerParam != null) {
                    // callback handler info
                    if (!toStop) {
                        // commonm
                        TriggerCallbackThread.pushCallBack(new HandleCallbackParam(triggerParam.getLogId(), triggerParam.getLogDateTime(), executeResult));
                    } else {
                        // is killed
                        ReturnT<String> stopResult = new ReturnT<String>(ReturnT.FAIL_CODE, stopReason + " [job running, killed]");
                        TriggerCallbackThread.pushCallBack(new HandleCallbackParam(triggerParam.getLogId(), triggerParam.getLogDateTime(), stopResult));
                    }
                }
            }
        }

		// callback trigger request in queue
		while(triggerQueue !=null && triggerQueue.size()>0){
			TriggerParam triggerParam = triggerQueue.poll();
			if (triggerParam!=null) {
				// is killed
				ReturnT<String> stopResult = new ReturnT<String>(ReturnT.FAIL_CODE, stopReason + " [job not executed, in the job queue, killed.]");
				TriggerCallbackThread.pushCallBack(new HandleCallbackParam(triggerParam.getLogId(), triggerParam.getLogDateTime(), stopResult));
			}
		}

		// destroy
		try {
			handler.destroy();
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}

		logger.info(">>>>>>>>>>> xxl-job JobThread stoped, hashCode:{}", Thread.currentThread());
	}
}

```

## 心跳检测

API服务位置：com.xxl.job.core.biz.ExecutorBiz
API服务请求参考代码：com.xxl.job.executorbiz.ExecutorBizTest

#### a、心跳检测

```json
说明：调度中心检测执行器是否在线时使用
------
地址格式：{执行器内嵌服务跟地址}/beat
Header：    XXL-JOB-ACCESS-TOKEN : {请求令牌}
请求数据格式如下，放置在 RequestBody 中，JSON格式：
响应数据格式：    
{       "code": 200,      // 200 表示正常、其他失败      
 		"msg": null       // 错误提示消息    
}
```

## 路由策略

com.xxl.job.admin.core.route.ExecutorRouter

轮询

根据jobid轮询获取值

每次+1

使用ConcurrentMap<Integer, AtomicInteger> routeCountEachJob存储每个任务的调用次数

根据调用次数每次自增式求模实现轮询（内带缓存清理机制）

```java
package com.xxl.job.admin.core.route.strategy;

import com.xxl.job.admin.core.route.ExecutorRouter;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.biz.model.TriggerParam;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xuxueli on 17/3/10.
 */
public class ExecutorRouteRound extends ExecutorRouter {

    private static ConcurrentMap<Integer, AtomicInteger> routeCountEachJob = new ConcurrentHashMap<>();
    private static long CACHE_VALID_TIME = 0;

    private static int count(int jobId) {
        // cache clear
        // 每天清理一次缓存，每次调用判断一次是否要清理缓存
        if (System.currentTimeMillis() > CACHE_VALID_TIME) {
            // 缓存清理
            routeCountEachJob.clear();
            // 更新缓存有效时间
            CACHE_VALID_TIME = System.currentTimeMillis() + 1000*60*60*24;
        }

        // 获取当前任务调用次数
        AtomicInteger count = routeCountEachJob.get(jobId);
        if (count == null || count.get() > 1000000) {
            // 初始化时主动Random一次，缓解首次压力
            count = new AtomicInteger(new Random().nextInt(100));
        } else {
            // count++
            // 原子自增，自旋锁
            count.addAndGet(1);
        }
        routeCountEachJob.put(jobId, count);
        return count.get();
    }

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList) {
        // 每次自增式求模实现轮询
        String address = addressList.get(count(triggerParam.getJobId())%addressList.size());
        return new ReturnT<String>(address);
    }

}
```



### 故障转移

ExecutorRouteFailover

遍历，寻找心跳判定成功（寻找存活的）任务去返回任务信息以便后续调用

如果要二次开发可在找到心跳成功后添加算法

不行，就得把轮询寻找改为其他方式的寻找，并且每次发现不存货的就要从缓存中剔除

更改故障转移还是更改所有策略



com.xxl.job.admin.controller.JobInfoController

将路由策略导入前端

i18n/message_zh_CN.properties 在这个配置中著名国际化显示配置(中英文显示置换)

使用I18nUtil，com.xxl.job.admin.core.util，读取配置文件英文映射的中文信息，并用于前台展示

```html
<div class="col-sm-4">
    <select class="form-control" name="executorRouteStrategy">
        <option value="FIRST">第一个</option>
        <option value="LAST">最后一个</option>
        <option value="ROUND">轮询</option>
        <option value="RANDOM">随机</option>
        <option value="CONSISTENT_HASH">一致性HASH</option>
        <option value="LEAST_FREQUENTLY_USED">最不经常使用</option>
        <option value="LEAST_RECENTLY_USED">最近最久未使用</option>
        <option value="FAILOVER">故障转移</option>
        <option value="BUSYOVER">忙碌转移</option>
        <option value="SHARDING_BROADCAST">分片广播</option>
    </select>
</div>
```

具体实现：

 抽取公共方法，查询存活服务来故障转移？原实现就是轮询寻找心跳有效的地址

路由策略的配置

前台显示在ExecutorRouteStrategyEnum中添加一种类型即可

具体调用的路由选择呢?

路由在哪执行

com.xxl.job.admin.core.trigger.XxlJobTrigger

processTrigger方法

根据执行器启动进程运行实例

看看触发器怎么被调用的

触发器一直向外寻找，找到触发器接口

com.xxl.job.admin.controller.JobInfoController



加载注解使用反射设置方法可访问，将可执行的方法放入容器jobHandlerRepository

在正式运行过程中就通过http请求调用方法进行执行，

是如何发起请求的

使用触发器trigger

trigger对外暴露接口

trigger就是对外暴露的按钮接收



#### 如何添加新的路由策略

1. 前端读取，properties 文件中添加新参数
2. 后端在com.xxl.job.admin.core.route包下添加新的路由策略并写入ExecutorRouteStrategyEnum
3. 在任务启动时会根据任务配置自动进行路由策略匹配，然后调用具体的路由策略执行http地址的接口选择
4. 后续定时任务执行逻辑与XXL保持匹配
5. 要改动的只有1、2两步



### 触发器

整体流程

![](https://img2020.cnblogs.com/i-beta/1301624/202003/1301624-20200320135904253-1168265916.png)



![image-20201026145447068](D:\Learning\公司学习\xxl-job\xxl-job.assets\image-20201026145447068.png)

就是这个启动

<a href="javascript:void(0);" class="job_operate" _type="job_resume">启动</a>

去看下前端代码

![image-20201026161430488](D:\Learning\公司学习\xxl-job\xxl-job.assets\image-20201026161430488.png)

job_trigger

jobinfo.index.ftl 中的table：job_list

使用JavaScript添加点击按钮`执行一次`时动态触发ajax调用base_url + "/jobinfo/trigger"

跳到后端

使用线程池执行任务

```java
JobTriggerPoolHelper.trigger(id, TriggerTypeEnum.MANUAL, -1, null, executorParam, addressList);
// 调用触发器执行远程任务调用
XxlJobTrigger.trigger(jobId, triggerType, failRetryCount, executorShardingParam, executorParam, addressList);
// 根据路由策略获取路由地址
routeAddressResult = executorRouteStrategyEnum.getRouter().route(triggerParam, group.getRegistryList());
// 4、trigger remote executor
// 远程调用执行任务
ReturnT<String> triggerResult = null;
if (address != null) {
    triggerResult = runExecutor(triggerParam, address);
} else {
    triggerResult = new ReturnT<String>(ReturnT.FAIL_CODE, null);
}

```

在执行时调用com.xxl.job.admin.core.trigger.XxlJobTrigger类中的processTrigger方法时通过ExecutorRouteStrategyEnum.match获取具体的路由策略

**根据路由策略调用route方法获取路由地址**

因此可以自己在com.xxl.job.admin.core.route.strategy包下添加新的路由策略，自定义的路由策略进行二次扩展，然后将其添入com.xxl.job.admin.core.route.ExecutorRouteStrategyEnum及前端即可



执行过程：

trigger调用的是通过客户端去请求执行

内部使用各种JUC包下的容器进行内部缓存数据（ConcurrentHashMap）

```java
/**
 * run executor
 * @param triggerParam
 * @param address
 * @return
 */
public static ReturnT<String> runExecutor(TriggerParam triggerParam, String address){
    ReturnT<String> runResult = null;
    try {
        // 通过调度器执行任务
        ExecutorBiz executorBiz = XxlJobScheduler.getExecutorBiz(address);
        // 远程调用执行任务，一个post请求，这里的executorBiz实例是client而不是impl，一个远程调用一个逻辑运行
        // 使用Java.net原生实现调用
        runResult = executorBiz.run(triggerParam);
    } catch (Exception e) {
        logger.error(">>>>>>>>>>> xxl-job trigger error, please check if the executor[{}] is running.", address, e);
        runResult = new ReturnT<String>(ReturnT.FAIL_CODE, ThrowableUtil.toString(e));
    }

    StringBuffer runResultSB = new StringBuffer(I18nUtil.getString("jobconf_trigger_run") + "：");
    runResultSB.append("<br>address：").append(address);
    runResultSB.append("<br>code：").append(runResult.getCode());
    runResultSB.append("<br>msg：").append(runResult.getMsg());

    runResult.setMsg(runResultSB.toString());
    return runResult;
}
```

#### 小结

com.xxl.job.admin.controller.JobInfoController，trigger接口

com/xxl/job/admin/core/thread/JobTriggerPoolHelper.java addTrigger方法 调用触发器执行远程任务调用

com/xxl/job/admin/core/trigger/XxlJobTrigger.java processTrigger方法，路由策略匹配，获取远程调用地址，

com/xxl/job/admin/core/trigger/XxlJobTrigger.java runExecutor方法  执行远程调用

新建ExecutorBizClient 使用POST请求交互



1. 触发器整体流程：前端点击执行一次按钮，js通过AJAX调用后端接口
2. 后端通过线程池执行远程调用任务
3. 根据传递过来的ExecutorBlockStrategy字段与后端ExecutorBlockStrategyEnum中的title进行匹配获取路由选择策略，并根据策略选择远程调用地址
4. 使用Java原生的java.net包执行http调用并封装返回结果

HTTP请求addressUrl + "run"连接调度可看到是请求了run接口

执行ExecutorBizImpl的run方法，选取执行器handler进而确定唯一工作线程jobThread，将定时任务推入阻塞队列，等待读取运行

具体运行是JobThread的run方法调用，handler 去具体执行，

用到了反射+注解将方法注入容器以便在运行时发现

通过配置注入容器，xxl官网也描述是开启了一个服务器接收请求，RPC框架的请求与调用



### 启动任务

<a href="javascript:void(0);" class="job_operate" _type="job_resume">启动</a>

调用start接口

![image-20201026151829337](D:\Learning\公司学习\xxl-job\xxl-job.assets\image-20201026151829337.png)

```java
@RequestMapping("/start")
@ResponseBody
public ReturnT<String> start(int id) {
   return xxlJobService.start(id);
}
```

start方法解析cron表达式并更新数据库中的任务状态

```java
@Override
public ReturnT<String> start(int id) {
   XxlJobInfo xxlJobInfo = xxlJobInfoDao.loadById(id);

   // next trigger time (5s后生效，避开预读周期)
   long nextTriggerTime = 0;
   try {
      // quartz 的cron表达式解析方案，这里加了5S避开预读周期
      // 解析cron表达式，计算出下次启动的时间
      Date nextValidTime = new CronExpression(xxlJobInfo.getJobCron()).getNextValidTimeAfter(new Date(System.currentTimeMillis() + JobScheduleHelper.PRE_READ_MS));
      if (nextValidTime == null) {
         return new ReturnT<String>(ReturnT.FAIL_CODE, I18nUtil.getString("jobinfo_field_cron_never_fire"));
      }
      // timestamp * 1000
      nextTriggerTime = nextValidTime.getTime();
   } catch (ParseException e) {
      logger.error(e.getMessage(), e);
      return new ReturnT<String>(ReturnT.FAIL_CODE, I18nUtil.getString("jobinfo_field_cron_unvalid")+" | "+ e.getMessage());
   }

   // 设置启动运行
   xxlJobInfo.setTriggerStatus(1);
   xxlJobInfo.setTriggerLastTime(0);
   xxlJobInfo.setTriggerNextTime(nextTriggerTime);

   xxlJobInfo.setUpdateTime(new Date());
   xxlJobInfoDao.update(xxlJobInfo);
   return ReturnT.SUCCESS;
}
```

时间到了怎么触发的？

com.xxl.job.admin.core.scheduler.XxlJobScheduler初始化开启JobScheduleHelper扫描方法

通过thread+while(true)+sleep来开启子线程不断监控要执行的任务

通过com.xxl.job.admin.core.thread.JobScheduleHelper类定期读取数据库触发定时任务

![](https://img2020.cnblogs.com/i-beta/1301624/202003/1301624-20200317142704552-1647789828.png)

虽然放入了ring中但是好像没触发

```java
public void start(){

    // schedule thread
    scheduleThread = new Thread(new Runnable() {
        @Override
        public void run() {

            try {
                // 每5秒执行一次
                TimeUnit.MILLISECONDS.sleep(5000 - System.currentTimeMillis()%1000 );
            } catch (InterruptedException e) {
                if (!scheduleThreadToStop) {
                    logger.error(e.getMessage(), e);
                }
            }
            logger.info(">>>>>>>>> init xxl-job admin scheduler success.");

            // pre-read count: treadpool-size * trigger-qps (each trigger cost 50ms, qps = 1000/50 = 20)
            int preReadCount = (XxlJobAdminConfig.getAdminConfig().getTriggerPoolFastMax() + XxlJobAdminConfig.getAdminConfig().getTriggerPoolSlowMax()) * 20;

            while (!scheduleThreadToStop) {

                // Scan Job
                // 任务开始时间
                long start = System.currentTimeMillis();

                Connection conn = null;
                Boolean connAutoCommit = null;
                PreparedStatement preparedStatement = null;

                // 是否有任务触发
                boolean preReadSuc = true;
                try {

                    conn = XxlJobAdminConfig.getAdminConfig().getDataSource().getConnection();
                    connAutoCommit = conn.getAutoCommit();
                    conn.setAutoCommit(false);

                    // 添加排他锁
                    // 锁定schedule_lock，不让其他线程访问
                    preparedStatement = conn.prepareStatement(  "select * from xxl_job_lock where lock_name = 'schedule_lock' for update" );
                    preparedStatement.execute();

                    // tx start

                    // 1、pre read
                    long nowTime = System.currentTimeMillis();
                    // 查询5S内要启动的定时任务
                    List<XxlJobInfo> scheduleList = XxlJobAdminConfig.getAdminConfig().getXxlJobInfoDao().scheduleJobQuery(nowTime + PRE_READ_MS, preReadCount);
                    if (scheduleList!=null && scheduleList.size()>0) {
                        // 遍历进行任务过时/触发/延迟操作
                        // 2、push time-ring
                        for (XxlJobInfo jobInfo: scheduleList) {

                            // time-ring jump
                            if (nowTime > jobInfo.getTriggerNextTime() + PRE_READ_MS) {
                                // 2.1、trigger-expire > 5s：pass && make next-trigger-time
                                // 任务执行超时则跳过，更行下次执行时间
                                logger.warn(">>>>>>>>>>> xxl-job, schedule misfire, jobId = " + jobInfo.getId());

                                // fresh next
                                refreshNextValidTime(jobInfo, new Date());

                            } else if (nowTime > jobInfo.getTriggerNextTime()) {
                                // 2.2、trigger-expire < 5s：direct-trigger && make next-trigger-time
                                // 触发过期时间在5S内，直接触发

                                // 1、trigger
                                JobTriggerPoolHelper.trigger(jobInfo.getId(), TriggerTypeEnum.CRON, -1, null, null, null);
                                logger.debug(">>>>>>>>>>> xxl-job, schedule push trigger : jobId = " + jobInfo.getId() );

                                // 2、fresh next
                                refreshNextValidTime(jobInfo, new Date());

                                // next-trigger-time in 5s, pre-read again
                                // 定时任务在五秒内又要触发放入TimeRing
                                if (jobInfo.getTriggerStatus()==1 && nowTime + PRE_READ_MS > jobInfo.getTriggerNextTime()) {

                                    // 1、make ring second
                                    int ringSecond = (int)((jobInfo.getTriggerNextTime()/1000)%60);

                                    // 2、push time ring
                                    pushTimeRing(ringSecond, jobInfo.getId());

                                    // 3、fresh next
                                    refreshNextValidTime(jobInfo, new Date(jobInfo.getTriggerNextTime()));

                                }

                            } else {
                                // 2.3、trigger-pre-read：time-ring trigger && make next-trigger-time
                                // 未到启动时间，设定ringSecond放入容器

                                // 1、make ring second
                                int ringSecond = (int)((jobInfo.getTriggerNextTime()/1000)%60);

                                // 2、push time ring
                                pushTimeRing(ringSecond, jobInfo.getId());

                                // 3、fresh next
                                refreshNextValidTime(jobInfo, new Date(jobInfo.getTriggerNextTime()));

                            }

                        }

                        // 3、update trigger info
                        // 触发完一轮后更新数据库（主要更新下次触发时间
                        for (XxlJobInfo jobInfo: scheduleList) {
                            XxlJobAdminConfig.getAdminConfig().getXxlJobInfoDao().scheduleUpdate(jobInfo);
                        }

                    } else {
                        preReadSuc = false;
                    }

                    // tx stop


                } catch (Exception e) {
                    if (!scheduleThreadToStop) {
                        logger.error(">>>>>>>>>>> xxl-job, JobScheduleHelper#scheduleThread error:{}", e);
                    }
                } finally {

                    // commit
                    if (conn != null) {
                        try {
                            conn.commit();
                        } catch (SQLException e) {
                            if (!scheduleThreadToStop) {
                                logger.error(e.getMessage(), e);
                            }
                        }
                        try {
                            conn.setAutoCommit(connAutoCommit);
                        } catch (SQLException e) {
                            if (!scheduleThreadToStop) {
                                logger.error(e.getMessage(), e);
                            }
                        }
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            if (!scheduleThreadToStop) {
                                logger.error(e.getMessage(), e);
                            }
                        }
                    }

                    // close PreparedStatement
                    if (null != preparedStatement) {
                        try {
                            preparedStatement.close();
                        } catch (SQLException e) {
                            if (!scheduleThreadToStop) {
                                logger.error(e.getMessage(), e);
                            }
                        }
                    }
                }
                // 计算任务触发花费时间
                long cost = System.currentTimeMillis()-start;


                // Wait seconds, align second
                // 触发完成太快就休息一段时间
                if (cost < 1000) {  // scan-overtime, not wait
                    try {
                        // 花费时间在5S内每秒触发一次查询是否有任务要执行
                        // 感觉存在误差，导致任务无法执行的情况
                        // pre-read period: success > scan each second; fail > skip this period;
                        TimeUnit.MILLISECONDS.sleep((preReadSuc?1000:PRE_READ_MS) - System.currentTimeMillis()%1000);
                    } catch (InterruptedException e) {
                        if (!scheduleThreadToStop) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }

            }

            logger.info(">>>>>>>>>>> xxl-job, JobScheduleHelper#scheduleThread stop");
        }
    });
    scheduleThread.setDaemon(true);
    scheduleThread.setName("xxl-job, admin JobScheduleHelper#scheduleThread");
    scheduleThread.start();


    // ring thread
    ringThread = new Thread(new Runnable() {
        @Override
        public void run() {

            // align second
            try {
                TimeUnit.MILLISECONDS.sleep(1000 - System.currentTimeMillis()%1000 );
            } catch (InterruptedException e) {
                if (!ringThreadToStop) {
                    logger.error(e.getMessage(), e);
                }
            }

            while (!ringThreadToStop) {

                try {
                    // second data
                    List<Integer> ringItemData = new ArrayList<>();
                    int nowSecond = Calendar.getInstance().get(Calendar.SECOND);   // 避免处理耗时太长，跨过刻度，向前校验一个刻度；
                    for (int i = 0; i < 2; i++) {
                        List<Integer> tmpData = ringData.remove( (nowSecond+60-i)%60 );
                        if (tmpData != null) {
                            ringItemData.addAll(tmpData);
                        }
                    }

                    // ring trigger
                    logger.debug(">>>>>>>>>>> xxl-job, time-ring beat : " + nowSecond + " = " + Arrays.asList(ringItemData) );
                    if (ringItemData.size() > 0) {
                        // do trigger
                        for (int jobId: ringItemData) {
                            // do trigger
                            JobTriggerPoolHelper.trigger(jobId, TriggerTypeEnum.CRON, -1, null, null, null);
                        }
                        // clear
                        ringItemData.clear();
                    }
                } catch (Exception e) {
                    if (!ringThreadToStop) {
                        logger.error(">>>>>>>>>>> xxl-job, JobScheduleHelper#ringThread error:{}", e);
                    }
                }

                // next second, align second
                try {
                    TimeUnit.MILLISECONDS.sleep(1000 - System.currentTimeMillis()%1000);
                } catch (InterruptedException e) {
                    if (!ringThreadToStop) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
            logger.info(">>>>>>>>>>> xxl-job, JobScheduleHelper#ringThread stop");
        }
    });
    ringThread.setDaemon(true);
    ringThread.setName("xxl-job, admin JobScheduleHelper#ringThread");
    ringThread.start();
}
```

看到了触发，其中添加排他锁，分布式锁通过数据库实现

如何解除

#### 小结

任务启动

com/xxl/job/admin/core/thread/JobScheduleHelper.java

com/xxl/job/admin/core/scheduler/XxlJobScheduler.java

com/xxl/job/admin/core/conf/XxlJobAdminConfig.java

XxlJobAdminConfig作为`Component`被Spring管理，启动，读取配置类，触发定时任务初始化方法，开启子线程循环触发定时任务

实现了两个接口

```java
public class XxlJobAdminConfig implements InitializingBean, DisposableBean
```

这两个接口是一组的，功能类似，因此放在一起：前者顾名思义在Bean属性都设置完毕后调用afterPropertiesSet()方法做一些初始化的工作，后者在Bean生命周期结束前调用destory()方法做一些收尾工作。

1. XxlJobAdminConfig作为`Component`被Spring管理，启动，读取配置类，触发定时任务初始化方法，开启子线程循环触发定时任务
2. 在初始化XxlJobScheduler过程中调用开启定时任务监控的子线程JobScheduleHelper.getInstance().start();
3. 每五秒从数据库读取一次数据，寻找是否有要触发的定时任务，触发完的定时任务会重写TriggerNextTime并写回数据库更新执行时间
4. 在寻找触发定时任务时分为三种情况分别操作：

   任务过时：当前时间超过任务执行时间，刷新数据跳过本次执行

   任务触发：任务将在5秒内执行则直接触发并更新下次触发的时间

   延迟操作：未到启动时间设定ringSecond放入容器

ring的分析

选取时间，将要执行的任务放入ringData容器Map<Integer, List<Integer>> ringData

选取list将jobId推入容器，队列+链表

执行过程中开了一个ringThread，读取ring缓存并触发执行





## Introduction

## Features

- 1、简单：支持通过Web页面对任务进行CRUD操作，操作简单，一分钟上手；
- 2、动态：支持动态修改任务状态、启动/停止任务，以及终止运行中任务，即时生效；
- 3、调度中心HA（中心式）：调度采用中心式设计，“调度中心”自研调度组件并支持集群部署，可保证调度中心HA；
- 4、执行器HA（分布式）：任务分布式执行，任务"执行器"支持集群部署，可保证任务执行HA；
- 5、注册中心: 执行器会周期性自动注册任务, 调度中心将会自动发现注册的任务并触发执行。同时，也支持手动录入执行器地址；
- 6、弹性扩容缩容：一旦有新执行器机器上线或者下线，下次调度时将会重新分配任务；
- 7、路由策略：执行器集群部署时提供丰富的路由策略，包括：第一个、最后一个、轮询、随机、一致性HASH、最不经常使用、最近最久未使用、故障转移、忙碌转移等；
- 8、故障转移：任务路由策略选择"故障转移"情况下，如果执行器集群中某一台机器故障，将会自动Failover切换到一台正常的执行器发送调度请求。
- 9、阻塞处理策略：调度过于密集执行器来不及处理时的处理策略，策略包括：单机串行（默认）、丢弃后续调度、覆盖之前调度；
- 10、任务超时控制：支持自定义任务超时时间，任务运行超时将会主动中断任务；
- 11、任务失败重试：支持自定义任务失败重试次数，当任务失败时将会按照预设的失败重试次数主动进行重试；其中分片任务支持分片粒度的失败重试；
- 12、任务失败告警；默认提供邮件方式失败告警，同时预留扩展接口，可方便的扩展短信、钉钉等告警方式；
- 13、分片广播任务：执行器集群部署时，任务路由策略选择"分片广播"情况下，一次任务调度将会广播触发集群中所有执行器执行一次任务，可根据分片参数开发分片任务；
- 14、动态分片：分片广播任务以执行器为维度进行分片，支持动态扩容执行器集群从而动态增加分片数量，协同进行业务处理；在进行大数据量业务操作时可显著提升任务处理能力和速度。
- 15、事件触发：除了"Cron方式"和"任务依赖方式"触发任务执行之外，支持基于事件的触发任务方式。调度中心提供触发任务单次执行的API服务，可根据业务事件灵活触发。
- 16、任务进度监控：支持实时监控任务进度；
- 17、Rolling实时日志：支持在线查看调度结果，并且支持以Rolling方式实时查看执行器输出的完整的执行日志；
- 18、GLUE：提供Web IDE，支持在线开发任务逻辑代码，动态发布，实时编译生效，省略部署上线的过程。支持30个版本的历史版本回溯。
- 19、脚本任务：支持以GLUE模式开发和运行脚本任务，包括Shell、Python、NodeJS、PHP、PowerShell等类型脚本;
- 20、命令行任务：原生提供通用命令行任务Handler（Bean任务，"CommandJobHandler"）；业务方只需要提供命令行即可；
- 21、任务依赖：支持配置子任务依赖，当父任务执行结束且执行成功后将会主动触发一次子任务的执行, 多个子任务用逗号分隔；
- 22、一致性：“调度中心”通过DB锁保证集群分布式调度的一致性, 一次任务调度只会触发一次执行；
- 23、自定义任务参数：支持在线配置调度任务入参，即时生效；
- 24、调度线程池：调度系统多线程触发调度运行，确保调度精确执行，不被堵塞；
- 25、数据加密：调度中心和执行器之间的通讯进行数据加密，提升调度信息安全性；
- 26、邮件报警：任务失败时支持邮件报警，支持配置多邮件地址群发报警邮件；
- 27、推送maven中央仓库: 将会把最新稳定版推送到maven中央仓库, 方便用户接入和使用;
- 28、运行报表：支持实时查看运行数据，如任务数量、调度次数、执行器数量等；以及调度报表，如调度日期分布图，调度成功分布图等；
- 29、全异步：任务调度流程全异步化设计实现，如异步调度、异步运行、异步回调等，有效对密集调度进行流量削峰，理论上支持任意时长任务的运行；
- 30、跨语言：调度中心与执行器提供语言无关的 RESTful API 服务，第三方任意语言可据此对接调度中心或者实现执行器。除此之外，还提供了 “多任务模式”和“httpJobHandler”等其他跨语言方案；
- 31、国际化：调度中心支持国际化设置，提供中文、英文两种可选语言，默认为中文；
- 32、容器化：提供官方docker镜像，并实时更新推送dockerhub，进一步实现产品开箱即用；
- 33、线程池隔离：调度线程池进行隔离拆分，慢任务自动降级进入"Slow"线程池，避免耗尽调度线程，提高系统稳定性；
- 34、用户管理：支持在线管理系统用户，存在管理员、普通用户两种角色；
- 35、权限控制：执行器维度进行权限控制，管理员拥有全量权限，普通用户需要分配执行器权限后才允许相关操作；

## 定时任务

- while(true) + thread.sleep

  轮询+线程休眠

- java.util.Timer+java.util.TimerTask

- ScheduleExecutorService

  并发工具类，调度

- Quartz

  定时任务框架

- Spring Task

  轻量级框架

- Spring Boot注解@EnableScheduling+@Scheduled

  底层仍是spring task

主要使用是后三种



## 分布式定时任务

逐步演化过来

分布式集群模式下，采用集中式任务调度方式，会带来一些问题

1. 多台机器集群部署的定时任务如何保证不被重复执行
2. 如何动态地调整定时任务的执行时间
3. 部署定时任务的及其发生故障如何故障转移，故障转义+算法更换如何实现
4. 如何对定时任务监控
5. 业务较大，单机瓶颈问题，如何扩展，高可用

### 分布式任务调度解决方案

集中式的定时任务调度需要解决的一系列问题，解决方案：

1. 数据库的唯一约束
2. 使用配置文件、redis、MySQL作为调度开关
3. 使用分布式锁实现调度控制
4. 使用分布式任务调度平台，TBSchedule 淘宝、Elastric-Job 当当、Saturn 唯品会、XXL-JOB 美团、Google Cron系统
5. 自研

XXL-JOB 国内线上生产环境案例较多，受欢迎

架构

![](https://www.xuxueli.com/doc/static/xxl-job/images/img_Qohm.png)

## XXL-JOB分布式任务调度平台实践

### 调度中心

从GitHub下载源码

SQL脚本初始化数据库

脚本执行数据库创建

![image-20201023151529352](D:\Learning\公司学习\xxl-job\xxl-job.assets\image-20201023151529352.png)

maven编译打包，成为调度中心

admin调度中心是个spring boot项目

改下配置

主要改下数据库连接配置

在配置中可更改email配置

可设置token，（此token必须admin与子模块一致否则无法通过验证注册到调度中心

```properties
### xxl-job, access token
xxl.job.accessToken=
```

启动项目即可

默认username/password admin/123456

执行器（新增定时任务的IP+端口

用户管理，管理员/普通用户可进行权限区分

可基于此项目二次开发扩展

### 执行器（任务）

1. 创建执行器，可支持集群
2. 配置文件中需要填写xxl-job注册中心地址
3. 每个具体执行job服务器需要创建一个netty连接端口号
4. 需要执行job的任务类，集成IJobHandler抽象类注册到job容器中
5. Execute方法中编写具体job任务
6. 写一个执行器（业务逻辑

其他平台的定时任务，可通过http请求去访问执行，可否让他们进入admin自己去配置

任务开启后需配置任务

执行器管理

自动注册/手动注册

自动注册的任务ip+端口在配置中指定，手动情况需自己输入

先执行器再任务管理

**任务路由策略**？

运行模式

- BEAN
- GLUE（Java）
- GLUE（Shell）
- GLUE（python）
- GLUE（PHP）
- GLUE（nodejs）
- GLUE（power shell）

GLUE代表在线写代码

JOBHandler与注解内容相等

任务参数传递给定时任务

server.port是tomcat的端口

xxl.job.executor.port是定时任务的端口

执行器IP+端口可手动配置多个形成集群（逗号分隔

调度中心的高可用

起两个不同调度中心的端口，server.port

在任务子模块通过负载均衡的方式去连接

![image-20201023161918415](D:\Learning\公司学习\xxl-job\xxl-job.assets\image-20201023161918415.png)

好蠢啊

无法多配置自动分发

nginx配置

![image-20201023162039287](D:\Learning\公司学习\xxl-job\xxl-job.assets\image-20201023162039287.png)

location转发

![image-20201023162102584](D:\Learning\公司学习\xxl-job\xxl-job.assets\image-20201023162102584.png)

锁，分布式锁

正式调用只有一个调度中心去工作

自动故障转移

如果轮询是否自带故障转移

## spring boot整合XXL-JOB

maven依赖

配置文件信息

配置XXL JOB CONFIG

创建handler接口

## 设计思想

将调度行为抽象成“调度中心”公共平台，而平台自身并不承担业务逻辑，“调度中心”负责发起调度请求

将任务抽象成分散的JobHandler，交由“执行器”统一管理，“执行器”负责接收调度请求并执行对应的JobHandler中业务逻辑。

因此，“调度”和“任务”两部分可以互相解耦，提高系统整体稳定性和可扩展性；

调度模块（调度中心）：负责管理调度信息，按照调度配置发出调度请求，自身不承但业务代码。调度系统与任务解耦，提高了系统可用性和稳定性，同时调度系统性能不再受限于任务模块；支持可视化、简单且动态的管理调度信息，包括任务新建、更新、删除、CLUE开发和任务报警等，所有上述操作都会实时生效，同时支持监控调度结果及执行日志，支持执行器Failover

GLUE开发模式好蠢



执行模块（执行器）：负责接收调度请求并执行任务逻辑。任务模块专注于任务的执行等操作，开发和维护更加简单和高效；接收“调度中心“的执行请求、终止请求和日志请求等。



基于netty开发类似dubbo的RPC框架内部调度开发



Java自导，spring boot注解cron表达式

XXL分布式任务调度框架



```java
/**
 * 1、简单任务示例（Bean模式）
 */
@XxlJob("commonHttpJobHandler")
public ReturnT<String> commonHttpJobHandler(String param) throws Exception {
    System.out.println("CommonJob.commonHttpJobHandler");
    System.out.println("param = " + param);
    XxlJobLogger.log("XXL-JOB, Hello World.");
    JSONObject json = JSON.parseObject(param);
    // 反射转switch匹配为了让所有对象创建都交给spring管理从而使用spring的种种特性
    JobModel job = new JobModel() {
        @Override
        public void doJob(JSONObject params) {
            throw new RuntimeException("未匹配到合适的系统，params:" + params);
        }
    };
    // 使用switch 方便后续平台增加后的扩展
    switch (json.getInteger(CODE)){
        case SystemCode.COMMAND_CODE:
            job = consoleJob;
            break;
        default:
            break;
    }
    job.doJob(json);
    return ReturnT.SUCCESS;
}
```