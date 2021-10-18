# @Async探索以及异步系统通信

## @Async

- 标注方法异步执行
- 不适用于@Configuration类下的方法
- 方法签名任意值，但是返回必须是void/Future 因为是异步执行
- 借助代理模式实现异步
- value指定异步执行器

### [源码分析](https://juejin.cn/post/6858854987280809997)

- 可借助@EnableAsync向下不断查找，最终找到构造intercetor具体调用

- 借助AOP实现方法的异步增强，利用特定/默认executor去执行异步方法`AsyncExecutionInterceptor#invoke`

- 默认线程池是唯一TaskExecutor，最好自定义TaskExecutor

  - ```java
        @Bean("messageExecutor")
        public ThreadPoolTaskExecutor messageExecutor() {
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(4);
            executor.setMaxPoolSize(10);
            executor.setQueueCapacity(200);
            executor.setKeepAliveSeconds(60);
            executor.setThreadNamePrefix("messageExecutor-");
            executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            executor.setWaitForTasksToCompleteOnShutdown(true);
            executor.setAwaitTerminationSeconds(60);
            return executor;
        }
    ```

  - 默认线程池定义的队列容量过大极其容易出错

- 出错处理可实现`AsyncConfigurer`接口

- 返回值只支持

  - void
  - Future
  - ListenableFuture
  - CompletableFuture

- 



`AnnotationAsyncExecutionInterceptor#`

## 异步系统通信

- Http
- 消息队列