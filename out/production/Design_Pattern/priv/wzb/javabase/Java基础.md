# Java基础

java是面向对象的编程语言不是动态语言

但是拥有一些动态特性通过反射等机制可以在运行中动态的加载类

## 数据结构：

primitive type ：int ,short ,long ,float,double,boolean,byte,char

## java容器

collection:父类定义了一些基础的容器方法继承了迭代器

set：继承自collection，实现有HashSet 、TreeSet都是借助map进行的实现都是map中的key value会由一个Object担任 

list：继承自collection

ArrayList:由数组实现长度扩展一般扩展为原来长度+原来长度*0.5的空间由于由数组实现所以查找较快但是插入删除较慢

LinkedList：由链表实现的线性结构，插入删除较快查找较慢

map：图 数据结构 由key和value组成

HashMap：借助哈希表（散列表）实现的图存储结合了数组与链表，由位运算（较快）和取模运算来计算map中节点应该存放的位置，线程不安全，线程安全可以使用hashtable ，但是有了JUC包之后线程安全的容器一般采用Concurrent开头的容器

TreeMap：借助红黑树的数据结构实现因此相对于HashMap来说TreeMap总是有序的

## IO

File根据一定的路径获取文件是java对文件系统中文件的抽象可以通过该类对文件系统进行操作

java不会直接与操作系统进行交互操作，java对文件系统的操作是通过jvm完成的

java的IO操作根据读取内容的不同可以分为字符流和字节流两种

字节流:InputStream OutputStream都继承了Closeable接口 用途广泛，可以读取图片，视频，音频，二进制数据，也可以读取字符

常用的有:

FileInputStream 从主机环境的文件中获取的输入字节流

FileOutputStream 一个文件输出流可以将字节数据写出到文件中

ByteArrayInputStream 内部包含缓冲的字节数组输入流

ByteArrayOutputStream内部包含缓冲的字节数组输出流

SequenceInputStream 相当于一个容器把一定的字节流或者字符流放入容器中挨个读取

BufferedInputStream 继承自FileInputStream使用装饰器设计模式 内部包含了一个缓冲区有助于加快读取速度提高性能可以直接嵌套在FileInputStream外层进行使用读取

BufferedOutputStream 与上面类似

字符流：Reader Writer 用于读取一定编码格式之下的字符



文件复制的本质就是输入流与输出流连成一条通道

```java
public static void copy(String srcPath,String destPath){
        File dest = new File(destPath);

        File src = new File(srcPath);

//        src = new File(path);
        System.out.println(src.getPath());

        InputStream is = null;
        OutputStream os = null;

        try{
            is = new FileInputStream(src);
            os = new FileOutputStream(dest,true);

            byte[] flush = new byte[1024];
            int len=-1;
            while ((len=is.read(flush))!=-1){
                os.write(flush,0,len);
            }

            os.flush();
        }catch (Exception e){

        }finally {
            try {
                if(os!=null){
                    os.close();
                }
                if(is!=null){
                    is.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }
```

IO操作可以借助apache下的commons io包进行简要的操作

装饰器设计模式的简要实现：

```java
package priv.wzb.javabase.io.decorateio;

/**
 * @author Satsuki
 * @time 2019/9/5 16:19
 * @description:
 * 实现放大器对声音的放大
 */
public class DecorateTest01 {
    public static void main(String[] args) {
        Person p = new Person();
        p.say();

        Amplifier am = new Amplifier(p);
        am.say();
    }
}
interface Say{
    void say();
}

class Person implements Say{
    private int voice = 10;
    @Override
    public void say() {
        System.out.println("人的声音为：" + this.getVoice());
    }

    public int getVoice() {
        return voice;
    }

    public void setVoice(int voice) {
        this.voice = voice;
    }
}

class Amplifier implements Say{
    private Person p;

    public Amplifier(Person p) {
        this.p = p;
    }

    @Override
    public void say() {
        System.out.println("人的声音为：" + p.getVoice()*100);
        System.out.println("噪音。。");
    }
}
```

## 多线程

1.多线程的实现（包括可重入锁

通过实现Runnable接口（implements Runnable）或者继承Thread类（extends Thread）

两者都有需要实现run()方法

java8中的lambda表达式可以快速的启动一个线程示例代码如下：

```java
package priv.wzb.multi_threaded_high_concurrent.study;

/**
 * @author Satsuki
 * @time 2019/9/6 15:13
 * @description:
 */
public class LambdaThread {
    public static void main(String[] args) {
        new Thread(()->{
            System.out.println("hello");
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hell2");
            }
        }).start();
    }
}
```

线程的声明周期：

新建（new一个线程类） 

就绪（调用.start()方法） 

运行（这个无法自己管理或者设定当CPU的资源分配到对应的线程才可以运行）虽然线程可以通过 .setPriority()来提高线程的优先级但是这也只是提高了线程被执行的几率而无法保证该线程就一定会优先执行或者说马上执行

java中线程的优先级设定如下：

```java
/**
  * The minimum priority that a thread can have.
  */
 public final static int MIN_PRIORITY = 1;

/**
  * The default priority that is assigned to a thread.
  */
 public final static int NORM_PRIORITY = 5;

 /**
  * The maximum priority that a thread can have.
  */
 public final static int MAX_PRIORITY = 10;
```

阻塞 分为释放锁和不释放锁的阻塞

不释放锁：Thread.sleep会阻塞线程让线程等待但是不会释放锁 其他线程仍然无法执行

释放锁： wait()则会阻塞线程并且释放锁一定要其他线程调用notify()或者notifyAll()方法才可以使线程再次进入就绪状态

线程让步：Thread.yield()会产生线程让步线程让步不是阻塞线程 只是将运行中的线程进入就绪状态等待CPU的调度所以线程让步之后可能该线程还是会获得CPU的时间片又重新运行

停止；自然运行结束或者出现异常

volatile关键字 无法保证线程安全只是使得变量变为线程透明每个线程读取的数据与内存内保存的数据保持一直

Synchronized关键字：

synchronized关键字主要用于保证线程安全，防治对个线程对同一个数据空间进行操作时产生的错误结果

Synchronized关键字可以加在类，方法，或者一个单独的synchronized代码块上可以对他们枷锁类或者方法上锁定的就是当前对象但是锁的粒度太大会大大影响线程的执行速度所以synchronized关键字最好用在一定的代码块上但是要注意使用是否合适若粒度太细可能会无法达到预期的线程安全效果

模拟抢票保证线程安全:

```java
package priv.wzb.multi_threaded_high_concurrent.study;

import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2019/9/6 14:52
 * @description:
 */
public class Web12306 implements Runnable {
    private int tickedNums = 99;
    @Override
    public void run() {
        while (true){
            if(tickedNums<0){
                break;
            }

            //模拟网络延迟
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(tickedNums>=0){
                synchronized (this){
                    while (tickedNums>=0){
                        System.out.println(Thread.currentThread().getName()+":" + tickedNums--);
                        break;
                    }


                }
            }
        }
    }

    public static void main(String[] args) {
        //一份资源
        Web12306 web = new Web12306();

        //多个代理
        new Thread(web,"th1").start();
        new Thread(web,"th2").start();
        new Thread(web,"th3").start();
    }
}
```

如果线程在主线程中开启可能会因为主线程运行结束而使得其他线程未完全运行此时可以通过调用线程的.join()方法使得线程都完整运行该方法本质就是把线程排进一个容器等待都结束完成后再结束

示例代码：

```java
package priv.wzb.multi_threaded_high_concurrent.study02;

/**
 * @author Satsuki
 * @time 2019/9/6 16:06
 * @description:
 * join合并线程，插队线程
 */
public class BlockedJoin01 {
    public static void main(String[] args) {
        Thread t=  new Thread(()->{
            for (int i = 0; i < 100; i++) {
                System.out.println("lambda..." + i);
            }
        });
        t.start();

        for (int i = 0; i < 100; i++) {
            if(i==20){
                try {
                    t.join();//插队 阻塞主线程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("main..." + i);
        }
    }
}
```

ThreadLocal:保证每个线程都有自己的一片独立的空间不会被其他线程锁影响

示例如下：

```java
package priv.wzb.multi_threaded_high_concurrent.thread_synchronization;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2019/5/29 16:56
 * @description:
 */
public class ThreadLocal2 {
    static ThreadLocal<Person> tl = new ThreadLocal<>();

    public static void main(String[] args) {
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(tl.get());
        }).start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            tl.set(new Person());
        }).start();

    }

    static class Person{
        String name="zhangsan";
}
}
```

ReentrantLock：可以完成synchronized的所有功能并且代替synchronized

2.线程安全的容器

java.util.concurrent包下的一下Concurrent开头的容器都是线程安全的并且效率高锁细化例如ConcurrentHashMap,ConcurrentSkipListMap(有序)

CopyOnWriteList每个线程都拥有自己内部的一个list来保证线程安全

线程安全的容器也可以使用Collections.synchronizedXXX(要安全化的容器)来保证容器的线程安全



3.线程池

通过Excutors来创建线程池也可以自己实现Excutor接口来实现自己的线程池

单个线程的线程池：Executors.newSingleThreadExecutor();

Callable与Runnable的不同：

Callable可以带参数传递并且可以返回参数

固定大小的线程池：Executors.newFixedThreadPool(4); 4就是线程数一般与cpu的核数相同

普通的线程池会持续存在可能浪费资源

Executors.newCachedThreadPool();可以提高效率当长时间闲置时不会消耗任何资源

可以延迟执行的线程池：Executors.newScheduledThreadPool

当线程池中的一个service执行完自己的任务后可以去帮助别的service执行任务：Executors.newWorkStealingPool();

## 网络编程

udp

TCP

详情见net复习