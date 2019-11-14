# Java面试-Java基础

## 请你说说Java和PHP的区别？

Java核心运行类库是Java编写的，java运行的时候需要先把.java文件编译成.class文件然后在jvm上解释运行

## 请你谈谈Java中是如何支持正则表达式操作的？

Java的String类提供了支持正则表达式的操作。包括：matches()、replaceAll()、replaceFirst()、split()。此外，Java中可以用Pattern类表示正则表达式对象，它提供了丰富的API进行各种正则表达式操作

```java
import java.util.regex.Matcher;
import java.util.regex.Pattern;
class RegExpTest {
    public static void main(String[] args) {
        String str = "成都市(成华区)(武侯区)(高新区)";
        Pattern p = Pattern.compile(".*?(?=\\()");
        Matcher m = p.matcher(str);
        if(m.find()) {
            System.out.println(m.group());
        }
    }
}
```

##  请你简单描述一下正则表达式及其用途。

用于处理复杂规则的字符串。正则表达式用于描述哪些复杂的规则，正则表达式就是文本规则的代码。计算机处理最多的不是数字而是字符，正则表达式为字符匹配处理提供了强大的支持

##  请你比较一下Java和JavaSciprt？

java和javascript是两种不同的语言，java是Sun公司（现已被oracle收购并且维持java的更新）开发的面向对象的程序设计语言，特别适合互联网应用程序开发，JavaScript是Netscape公司的产品，为扩展Netscape浏览器的功能而开发的一种嵌入web网页中运行的基于对象和事件驱动的解释性语言。

- 基于面向对象

java是面向对象语言，即使简单的开发也必须设计对象，JavaScript是脚本语言用来制作与网络无关，与用户交互的复杂软件，是一种基于对象（Object-Based）和事件驱动（Event-Driven）的编程语言，因而它本身提供了丰富的内部对象供开发者使用。

- 解释和编译

  java原代码在执行之前必须经过编译。JavaScript是一种解释性编程语言，原代码不需要经过编译，由浏览器解释执行。（目前浏览器基本都使用了JIT：Just-In-Time Compiler（即时编译）技术来提高JavaScript运行效率）

- 强类型变量和类型弱变量

  Java采用强类型变量检查，即所有变量在使用前必须声明，JavaScript中变量是弱类型的，甚至在使用变量前可以不作声明，JavaScript的解释器在运行时检查推断其数据类型。

- 代码格式不同

## 请你讲讲&和&&的区别？

&是按位与&&是逻辑与，&&具有短路措施，如果&&前面的出错了，就不会检测其后面的

&运算符具有两种用法：1.按位与，2.逻辑与，&&运算符是短路与运算。逻辑与跟短路与的差别是非常巨大的，虽然两者都要求运算符的左右两侧bool值均为true整个表达式才是true。&&之所以被成为短路与是因为如果&&左侧表达式为false右边表达式会被直接短路掉，不会进行运算。很多使用我们都采用&&而不是&例如在验证用户登陆判定用户名不是null且不是空字符串，应当写为：username != null &&!username.equals("")，二者的顺序不能交换，更不能用&运算符，因为第一个条件如果不成立，根本不能进行字符串的equals比较，否则会产生NullPointerException异常。

##  int和Integer有什么区别？

int是基本数据类型是primitive

Integer是包装数据类型Wrap
java会自动的装箱和拆箱例如：

```java
//自动装箱
int a = 10;

Integer i = new Integer(a);

//自动拆箱
System.out.println(i==10);

//比较时候的不同
Integer z = new Integer(2);
Integer x = new Integer(2);
//因其对象的hashcode不同，z和x都是保持了对真实对象的引用，存放在堆中，对象存放在方法区
System.out.println(z==x);//false
System.out.println(z.equals(x));//true

```

Java是一个近乎春节的面向对象编程语言，但是为了编程的方便还是引入了基本数据类型，但是为了能够将这些基本数据类型当成对象操作，Java为每个基本数据类型准备了包装类（wrapper class）int的包装类是Integer从java5开始引入了自动装箱/拆箱机制，使得两者可以相互转换

Java为每个原始类型提供了包装类型：

原始类型（primitive）：short int long float double boolean char byte

包装类型（wrapper type）Short Integer Long Float Double Boolean Character Byte

## 请你说明String 和StringBuffer的区别

String的内部存储数据的容器是final类型的也就是说长度不可更改，如果"a"+"b"实质上是由两个String类型并且创建了一个新的String类型来存储其结果，而StringBuffer提供了append方法内部容器可以变长，StringBuffer是线程安全的所以运行效率慢，另外一种StringBuilder是线程不安全的边长String，但是如果是固定长度的字符串还是用String比较合适否则效率慢

## Java内存详解

**未特殊说明则默认为hotspot虚拟机**

### 常见面试题

- **介绍下Java内存区域（运行时数据区）**

  简单的来分可以把运行时数据区分为堆和栈，在JDK1.8之后把方法区从堆中移除放到了直接内存中以更方便的获得一定大小避免大小限制

  栈是线程私有的内部分为PC、Java虚拟机栈、本地方法栈。他们的生命周期都是随着线程创建而常见随着线程死亡而死亡。PC是用来保存当前线程运行到哪一步的，用于完成选择，循环，异常抛出等操作，在多线程的情况下当线程切换切换PC可以保存当前线程运行的信息，当切换回来时线程根据PC的信息可以得知程序运行到哪一步，Java虚拟机栈用于存放Java的基础数据类型和对象的引用。会产生两种错误StackOverflowError：当Java虚拟机栈大小固定不可扩展不断压栈直至超出栈深度时会抛出该异常，OutOfMemoryError当栈大小可以扩展但是不停申请大小超出所能申请的大小后会抛出该异常，本地方法栈和Java虚拟机栈类似，但是本地方法栈保存的是Java虚拟机与本地方法交互的数据

  堆和方法区都是线程共享的，堆中存放对象实例、数组，分为四个区域Eden S0 S1 tentired（老年代）是垃圾回收的主要场所，经过不停的垃圾回收对象从Eden慢慢的向老年代转移

  方法区存放的是对象信息（X 应该是类信息），方法，常量，静态方法等信息

- **Java 对象的创建过程（五步，建议能默写出来并且要知道每一步虚拟机做了什么）**

  类加载检测，去常量池定位并查找该类是否被加载，解析，初始化，若没有则必须执行对象创建过程

  分配内存空间，在类加载检测之后，为类分配一块大小等于将堆中空间划分一块给对象，主要的分配算法有碰撞检测用于Serial虚拟机将用过的空间和空闲空间分为两部分，将指针指向这两部分的中心为止，指针从中心开始向空间空间划分直至找到足控的空间（有碎片，采用标记--清楚算法）和空闲列表：常用于CMS虚拟机，虚拟机会维护一个空间空间的列表分配空间直接在列表中查找，直到找到一块合适大小的空间为止（内存无碎片，采用标记-整理，或者复制算法）。内存分配并发问题的解决有两种CAS+失败重试:CAS是乐观锁的一种实现，每次假定都有空间可用申请，如果没有则失败重试和TLAB

  设置零值：在分配内存空间之后执行，在分配的空间内初始化为零值，这也代表了Java程序的对象可以不初始化而直接调用，调用时获取的是对象所对应的零值

  设置对象头：对象头的设置包括对象的hash值，GC分代信息，对象所对应的类信息等

  执行init方法：上述方法执行完后在JVM的角度来看对象创建完毕，然而在Java程序的角度来看对象才刚刚开始创建，到这一步Java程序就可以依照程序员的意愿执行init方法初始化

- **对象的访问定位的两种方式（句柄和直接指针两种方式**）

扩展问题

- **String类和常量池**
- **8中基本类型的包装类和常量池**

### 

### 概述

java与C++在内存回收上的对比，java因为有JVM的关系所以补想C/C++那样需要自己释放内存资源不易出现内存溢出或内存泄漏问题。但也正因为如此，如果出现内存泄漏或溢出问题，如果不了解虚拟机如何使用内存，排查将是一个十分苦难的工作

### 运行时数据区域

jdk1.8之前

![](https://camo.githubusercontent.com/a66819fd82c6adfa69b368edf3c52b1fa9cdc89d/68747470733a2f2f6d792d626c6f672d746f2d7573652e6f73732d636e2d6265696a696e672e616c6979756e63732e636f6d2f323031392d332f4a564de8bf90e8a18ce697b6e695b0e68daee58cbae59f9f2e706e67)

jdk1.8

![](https://camo.githubusercontent.com/0bcc6c01a919b175827f0d5540aeec115df6c001/68747470733a2f2f6d792d626c6f672d746f2d7573652e6f73732d636e2d6265696a696e672e616c6979756e63732e636f6d2f323031392d334a617661e8bf90e8a18ce697b6e695b0e68daee58cbae59f9f4a444b312e382e706e67)

**线程私有**

- 虚拟机栈
- 本地方法栈
- PC程序计数器

**线程共享**

- 堆
- 方法区
- 直接内存（非运行时数据区的一部分）

##### 程序计数器

用于保存当前线程所执行的字节码的行号指示器，

有两种用途

用于帮助执行跳转，循环，异常抛出等操作用于定位程序运行顺序问题

在多线程情况下用于保存当前程序的运行状态，如果线程切换又恢复可以根据PC了解当前线程执行到了哪一步

是线程私有的

**注意：PC是唯一一个不会出现OutOfMemoryError的区域，它的生命随着线程创建而创建，随着线程结束而死亡**

##### Java虚拟机栈

和PC一样都是线程私有的

Java内存如果简单划分内存可以分为堆内存（Heap）和栈内存（Stack）两部分，其中的栈内存就是指Java虚拟机栈，或者说是Java虚拟机栈中包含局部变量表该表存放基本数据类型（primitive）：short int long float double char byte bool 以及对对象的引用，句柄或者说是指向对象起始位置的指针

Java虚拟机栈会出现两种错误：

StackOverflowError：如果Java虚拟机栈大小不允许动态扩展，当线程不断请求，栈深度超过Java虚拟机栈的最大深度会抛出StackOverflow异常

OutOfMemoryError：如果Java虚拟机栈大小运行动态扩展，当线程不断请求栈时，内存用完了，Java虚拟机栈不断扩展直至超出能申请的内存大小时抛出OutOfMemory异常。

生命周期和PC一样随着线程创建而创建，随着线程死亡而死亡。

**扩展：那么方法/函数如何调用？**

Java 栈可用类比数据结构中栈，Java 栈中保存的主要内容是栈帧，每一次函数调用都会有一个对应的栈帧被压入 Java 栈，每一个函数调用结束后，都会有一个栈帧被弹出。

Java 方法有两种返回方式：

1. return 语句。
2. 抛出异常。

不管哪种返回方式都会导致栈帧被弹出。

##### 本地方法栈

与Java虚拟机栈类似在hotspot虚拟机中两者合二为一，Java虚拟机栈主要保存的是Java执行的字节码信息，而本地方法栈则是为native方法服务，本地方法被执行时也会在本地方法栈创建一个栈帧，用于存放本地方法的局部变量表，操作数栈等信息。

方法执行完毕后栈帧也会弹出释放资源，同时也会抛出StackOverflowError和OutOfMemory两种异常。

##### 堆

Java虚拟机所管理的内存中最大的一块，是线程共享的，主要用于存放对象实例，数组。几乎所有的对象和数组都是在这里分配内存。

Java堆是垃圾回收器管理的主要区域所以也被成为GC堆（Garbage Collected Heap）堆中对象存放在四个不同区域中分别是Eden、from survivor（s0）、to survivor（s1）、tentired（老年代），当一次垃圾回收执行后如果对象未被回收则年龄加一，如果年龄到15则被移动到老年代，对象晋升老年代的阈值可以通过可以通过参数 `-XX:MaxTenuringThreshold` 来设置。如下图所示：

![](https://camo.githubusercontent.com/4012482f49926b35d8557b63952ee605fd259f62/68747470733a2f2f6d792d626c6f672d746f2d7573652e6f73732d636e2d6265696a696e672e616c6979756e63732e636f6d2f323031392d33e5a086e7bb93e69e842e706e67)

##### 方法区

方法区也是线程共享的用于存放类信息，常量，静态变量，即时编译器编译后的代码等数据，很多人把它作为堆的一个区域但是它又另一个名字（non-heap)目的是和堆进行区分

**方法区也被成为老年代**

 **方法区和永久代的关系很像 Java 中接口和类的关系，类实现了接口，而永久代就是 HotSpot 虚拟机对虚拟机规范中方法区的一种实现方式。**

 **常用参数**

JDK 1.8 之前永久代还没被彻底移除的时候通常通过下面这些参数来调节方法区大小

```
-XX:PermSize=N //方法区 (永久代) 初始大小
-XX:MaxPermSize=N //方法区 (永久代) 最大大小,超过这个值将会抛出 OutOfMemoryError 异常:java.lang.OutOfMemoryError: PermGen
```

相对而言，垃圾收集行为在这个区域是比较少出现的，但并非数据进入方法区后就“永久存在”了。

JDK 1.8 的时候，方法区（HotSpot 的永久代）被彻底移除了（JDK1.7 就已经开始了），取而代之是元空间，元空间使用的是直接内存。

下面是一些常用参数：

```
-XX:MetaspaceSize=N //设置 Metaspace 的初始（和最小大小）
-XX:MaxMetaspaceSize=N //设置 Metaspace 的最大大小
```

与永久代很大的不同就是，如果不指定大小的话，随着更多类的创建，虚拟机会耗尽所有可用的系统内存。

**为什么要将永久代 (PermGen) 替换为元空间 (MetaSpace) 呢?**

整个永久代有一个 JVM 本身设置固定大小上限，无法进行调整，而元空间使用的是直接内存，受本机可用内存的限制，并且永远不会得到 java.lang.OutOfMemoryError。你可以使用 `-XX：MaxMetaspaceSize` 标志设置最大元空间大小，默认值为 unlimited，这意味着它只受系统内存的限制。`-XX：MetaspaceSize` 调整标志定义元空间的初始大小如果未指定此标志，则 Metaspace 将根据运行时的应用程序需求动态地重新调整大小。

当然这只是其中一个原因，还有很多底层的原因，这里就不提了。

##### 运行时常量池

运行时常量池是方法区的一部分，受到内存限制，用于何保存类版本、字段、方法、接口楼等描述信息外还有常量池信息当常量池无法申请到内存时会抛出OutOfMemoryError

jdk1.7之后的版本将运行时常量池从方法区中移除在堆中开辟了一片空间来保存

##### 直接内存

**直接内存并不是虚拟机运行时数据区的一部分，也不是虚拟机规范中定义的内存区域，但是这部分内存也被频繁地使用。而且也可能导致 OutOfMemoryError 异常出现。**

JDK1.4 中新加入的 **NIO(New Input/Output) 类**，引入了一种基于**通道（Channel）** 与**缓存区（Buffer）** 的 I/O 方式，它可以直接使用 Native 函数库直接分配堆外内存，然后通过一个存储在 Java 堆中的 DirectByteBuffer 对象作为这块内存的引用进行操作。这样就能在一些场景中显著提高性能，因为**避免了在 Java 堆和 Native 堆之间来回复制数据**。

本机直接内存的分配不会受到 Java 堆的限制，但是，既然是内存就会受到本机总内存大小以及处理器寻址空间的限制。

##### HopSpot虚拟机对象探秘

###### 对象的创建

![](https://camo.githubusercontent.com/8adbddf019488872c5da890c3bee263db22150fe/68747470733a2f2f6d792d626c6f672d746f2d7573652e6f73732d636e2d6265696a696e672e616c6979756e63732e636f6d2f323031392d362f4a6176612545352538382539422545352542422542412545352541462542392545382542312541312545372539412538342545382542462538372545372541382538422e706e67)

**Step1:类加载检查**

当虚拟机遇到一条new指定，先去常量池中定位并查看这个类的符号引用是否被加载解析初始化过，如果没有则必须指定类加载过程

**Step2:分配内存**

在**类加载通过**后进行内存分配，对象所需内存大小在类加载检查时确定，为类分配大小等同于把一跨内存从堆中划分出来，分配方式有指针碰撞和空闲列表

**指针碰撞**：把用过的内存和没用过的列出来，指针指向两者中间位置从没用过的开始滑动直到获取足够位置GC算法：Serial，ParNew,内存不规整，有碎片（标记-清楚算法）

**空闲列表**：专门维护一个列表记录哪些内存块可用，在分配时找一块足够大的内存块，GC算法:CMS，内存规整（标记-整理）或者复制算法

**内存分配并发问题**

- **CAS+失败重试：**CAS是乐观锁的一种实现方式，乐观锁:每次不加锁假设没有冲突而去完成某项工作，如果有冲突则重试直到成功为止。**虚拟机采用CAS配上失败重试的方法保证更新操作的原子性**
- **TLAB：**为每个线程在Eden区分配一块内存，当线程创建时优先使用这块内存，当使用完毕再去使用CAS+失败重试进行内存分配

**Step3:初始化零值**

在内存分配完毕后，把内存空间都初始化为零值（除对象头），这一步保证了Java代码中不赋初值就可直接使用，使用时程序访问的是这些字段的对应零值。

**Step4:设置对象头**

初始化零值完成后，**虚拟机对对象进行必要的设置**，这个对象是哪个类的实例、如何找到元数据、对象哈希码、对象的GC分带年龄，这些信息存放在对象头中。 另外，根据虚拟机当前运行状态的不同，如是否启用偏向锁等，对象头会有不同的设置方式。

**Step5:执行Init方法**

上述工作完成，在虚拟机的角度看来对象创建完毕，然而在Java程序视角看来，才更改开始创建对象，接下去根据程序员的意愿进行初始化对哪些初始为零的对象赋值，至此一个真正的对象才被创建出来

### 3.2 对象的内存布局

在 Hotspot 虚拟机中，对象在内存中的布局可以分为 3 块区域：**对象头**、**实例数据**和**对齐填充**。

**Hotspot 虚拟机的对象头包括两部分信息**，**第一部分用于存储对象自身的自身运行时数据**（哈希码、GC 分代年龄、锁状态标志等等），**另一部分是类型指针**，即对象指向它的类元数据的指针，虚拟机通过这个指针来确定这个对象是那个类的实例。

**实例数据部分是对象真正存储的有效信息**，也是在程序中所定义的各种类型的字段内容。

**对齐填充部分不是必然存在的，也没有什么特别的含义，仅仅起占位作用。** 因为 Hotspot 虚拟机的自动内存管理系统要求对象起始地址必须是 8 字节的整数倍，换句话说就是对象的大小必须是 8 字节的整数倍。而对象头部分正好是 8 字节的倍数（1 倍或 2 倍），因此，当对象实例数据部分没有对齐时，就需要通过对齐填充来补全。

### 3.3 对象的访问定位

建立对象就是为了使用对象，我们的 Java 程序通过栈上的 reference 数据来操作堆上的具体对象。对象的访问方式由虚拟机实现而定，目前主流的访问方式有**①使用句柄**和**②直接指针**两种：

1. **句柄：** 如果使用句柄的话，那么 Java 堆中将会划分出一块内存来作为句柄池，reference 中存储的就是对象的句柄地址，而句柄中包含了对象实例数据与类型数据各自的具体地址信息；

[![对象的访问定位-使用句柄](https://camo.githubusercontent.com/04c82b46121149c8cc9c3b81e18967a5ce06353f/68747470733a2f2f6d792d626c6f672d746f2d7573652e6f73732d636e2d6265696a696e672e616c6979756e63732e636f6d2f323031392d362f2545352541462542392545382542312541312545372539412538342545382541452542462545392539372541452545352541452539412545342542442538442d2545342542442542462545372539342541382545352538462541352545362539462538342e706e67)](https://camo.githubusercontent.com/04c82b46121149c8cc9c3b81e18967a5ce06353f/68747470733a2f2f6d792d626c6f672d746f2d7573652e6f73732d636e2d6265696a696e672e616c6979756e63732e636f6d2f323031392d362f2545352541462542392545382542312541312545372539412538342545382541452542462545392539372541452545352541452539412545342542442538442d2545342542442542462545372539342541382545352538462541352545362539462538342e706e67)

1. **直接指针：** 如果使用直接指针访问，那么 Java 堆对象的布局中就必须考虑如何放置访问类型数据的相关信息，而 reference 中存储的直接就是对象的地址。

[![对象的访问定位-直接指针](https://camo.githubusercontent.com/0ae309b058b45ee14004cd001e334355231b2246/68747470733a2f2f6d792d626c6f672d746f2d7573652e6f73732d636e2d6265696a696e672e616c6979756e63732e636f6d2f323031392d362f2545352541462542392545382542312541312545372539412538342545382541452542462545392539372541452545352541452539412545342542442538442d2545372539422542342545362538452541352545362538432538372545392539322538382e706e67)](https://camo.githubusercontent.com/0ae309b058b45ee14004cd001e334355231b2246/68747470733a2f2f6d792d626c6f672d746f2d7573652e6f73732d636e2d6265696a696e672e616c6979756e63732e636f6d2f323031392d362f2545352541462542392545382542312541312545372539412538342545382541452542462545392539372541452545352541452539412545342542442538442d2545372539422542342545362538452541352545362538432538372545392539322538382e706e67)

**这两种对象访问方式各有优势。使用句柄来访问的最大好处是 reference 中存储的是稳定的句柄地址，在对象被移动时只会改变句柄中的实例数据指针，而 reference 本身不需要修改。使用直接指针访问方式最大的好处就是速度快，它节省了一次指针定位的时间开销。**

## 四 重点补充内容

### 4.1 String 类和常量池

**String 对象的两种创建方式：**

```
String str1 = "abcd";//先检查字符串常量池中有没有"abcd"，如果字符串常量池中没有，则创建一个，然后 str1 指向字符串常量池中的对象，如果有，则直接将 str1 指向"abcd""；
String str2 = new String("abcd");//堆中创建一个新的对象
String str3 = new String("abcd");//堆中创建一个新的对象
System.out.println(str1==str2);//false
System.out.println(str2==str3);//false
```

这两种不同的创建方法是有差别的。

- 第一种方式是在常量池中拿对象；
- 第二种方式是直接在堆内存空间创建一个新的对象。

记住一点：**只要使用 new 方法，便需要创建新的对象。**

再给大家一个图应该更容易理解，图片来源：<https://www.journaldev.com/797/what-is-java-string-pool>：

[![String-Pool-Java](https://camo.githubusercontent.com/48189454746b5979fd465b32b996d222619ac1dc/68747470733a2f2f6d792d626c6f672d746f2d7573652e6f73732d636e2d6265696a696e672e616c6979756e63732e636f6d2f323031392d33537472696e672d506f6f6c2d4a617661312d343530783234392e706e67)](https://camo.githubusercontent.com/48189454746b5979fd465b32b996d222619ac1dc/68747470733a2f2f6d792d626c6f672d746f2d7573652e6f73732d636e2d6265696a696e672e616c6979756e63732e636f6d2f323031392d33537472696e672d506f6f6c2d4a617661312d343530783234392e706e67)

**String 类型的常量池比较特殊。它的主要使用方法有两种：**

- 直接使用双引号声明出来的 String 对象会直接存储在常量池中。
- 如果不是用双引号声明的 String 对象，可以使用 String 提供的 intern 方法。String.intern() 是一个 Native 方法，它的作用是：如果运行时常量池中已经包含一个等于此 String 对象内容的字符串，则返回常量池中该字符串的引用；如果没有，JDK1.7之前（不包含1.7）的处理方式是在常量池中创建与此 String 内容相同的字符串，并返回常量池中创建的字符串的引用，JDK1.7以及之后的处理方式是在常量池中记录此字符串的引用，并返回该引用。

```
	      String s1 = new String("计算机");
	      String s2 = s1.intern();
	      String s3 = "计算机";
	      System.out.println(s2);//计算机
	      System.out.println(s1 == s2);//false，因为一个是堆内存中的 String 对象一个是常量池中的 String 对象，
	      System.out.println(s3 == s2);//true，因为两个都是常量池中的 String 对象
```

**字符串拼接:**

```
		  String str1 = "str";
		  String str2 = "ing";
		 
		  String str3 = "str" + "ing";//常量池中的对象
		  String str4 = str1 + str2; //在堆上创建的新的对象	  
		  String str5 = "string";//常量池中的对象
		  System.out.println(str3 == str4);//false
		  System.out.println(str3 == str5);//true
		  System.out.println(str4 == str5);//false
```

[![img](https://camo.githubusercontent.com/6008db677de95edd009ce8dc9337f5bdf7de8d91/68747470733a2f2f6d792d626c6f672d746f2d7573652e6f73732d636e2d6265696a696e672e616c6979756e63732e636f6d2f323031392d362f2545352541442539372545372541432541362545342542382542322545362538422542432545362538452541352e706e67)](https://camo.githubusercontent.com/6008db677de95edd009ce8dc9337f5bdf7de8d91/68747470733a2f2f6d792d626c6f672d746f2d7573652e6f73732d636e2d6265696a696e672e616c6979756e63732e636f6d2f323031392d362f2545352541442539372545372541432541362545342542382542322545362538422542432545362538452541352e706e67)

尽量避免多个字符串拼接，因为这样会重新创建对象。如果需要改变字符串的话，可以使用 StringBuilder 或者 StringBuffer。

### 4.2 String s1 = new String("abc");这句话创建了几个字符串对象？

**将创建 1 或 2 个字符串。如果池中已存在字符串常量“abc”，则只会在堆空间创建一个字符串常量“abc”。如果池中没有字符串常量“abc”，那么它将首先在池中创建，然后在堆空间中创建，因此将创建总共 2 个字符串对象。**

**验证：**

```
		String s1 = new String("abc");// 堆内存的地址值
		String s2 = "abc";
		System.out.println(s1 == s2);// 输出 false,因为一个是堆内存，一个是常量池的内存，故两者是不同的。
		System.out.println(s1.equals(s2));// 输出 true
```

**结果：**

```
false
true
```

### 4.3 8 种基本类型的包装类和常量池

- **Java 基本类型的包装类的大部分都实现了常量池技术，即 Byte,Short,Integer,Long,Character,Boolean；这 5 种包装类默认创建了数值[-128，127] 的相应类型的缓存数据，但是超出此范围仍然会去创建新的对象。** 为啥把缓存设置为[-128，127]区间？（[参见issue/461](https://github.com/Snailclimb/JavaGuide/issues/461)）性能和资源之间的权衡。
- **两种浮点数类型的包装类 Float,Double 并没有实现常量池技术。**

```
		Integer i1 = 33;
		Integer i2 = 33;
		System.out.println(i1 == i2);// 输出 true
		Integer i11 = 333;
		Integer i22 = 333;
		System.out.println(i11 == i22);// 输出 false
		Double i3 = 1.2;
		Double i4 = 1.2;
		System.out.println(i3 == i4);// 输出 false
```

**Integer 缓存源代码：**

```
/**
*此方法将始终缓存-128 到 127（包括端点）范围内的值，并可以缓存此范围之外的其他值。
*/
    public static Integer valueOf(int i) {
        if (i >= IntegerCache.low && i <= IntegerCache.high)
            return IntegerCache.cache[i + (-IntegerCache.low)];
        return new Integer(i);
    }
```

**应用场景：**

1. Integer i1=40；Java 在编译的时候会直接将代码封装成 Integer i1=Integer.valueOf(40);，从而使用常量池中的对象。
2. Integer i1 = new Integer(40);这种情况下会创建新的对象。

```
  Integer i1 = 40;
  Integer i2 = new Integer(40);
  System.out.println(i1==i2);//输出 false
```

**Integer 比较更丰富的一个例子:**

```
  Integer i1 = 40;
  Integer i2 = 40;
  Integer i3 = 0;
  Integer i4 = new Integer(40);
  Integer i5 = new Integer(40);
  Integer i6 = new Integer(0);
  
  System.out.println("i1=i2   " + (i1 == i2));
  System.out.println("i1=i2+i3   " + (i1 == i2 + i3));
  System.out.println("i1=i4   " + (i1 == i4));
  System.out.println("i4=i5   " + (i4 == i5));
  System.out.println("i4=i5+i6   " + (i4 == i5 + i6));   
  System.out.println("40=i5+i6   " + (40 == i5 + i6));     
```

结果：

```
i1=i2   true
i1=i2+i3   true
i1=i4   false
i4=i5   false
i4=i5+i6   true
40=i5+i6   true
```

解释：

语句 i4 == i5 + i6，因为+这个操作符不适用于 Integer 对象，首先 i5 和 i6 进行自动拆箱操作，进行数值相加，即 i4 == 40。然后 Integer 对象无法与数值进行直接比较，所以 i4 自动拆箱转为 int 值 40，最终这条语句转为 40 == 40 进行数值比较。

#### 参考

- 《深入理解 Java 虚拟机：JVM 高级特性与最佳实践（第二版》
- 《实战 java 虚拟机》
- <https://docs.oracle.com/javase/specs/index.html>
- <http://www.pointsoftware.ch/en/under-the-hood-runtime-data-areas-javas-memory-model/>
- [https://dzone.com/articles/jvm-permgen-%E2%80%93-where-art-thou](https://dzone.com/articles/jvm-permgen-–-where-art-thou)
- <https://stackoverflow.com/questions/9095748/method-area-and-permgen>
- 深入解析String#intern<https://tech.meituan.com/2014/03/06/in-depth-understanding-string-intern.html>

## 请你谈谈大O符号(big-O notation)并给出不同数据结构的例子

大O符号用于描述一段程序执行的最坏程度，所需花费的最多时间是算法复杂度的渐进上界，描述了当数据结构中元素增加时，算法规模或性能在最坏情况下有多么好，一般使用大O符号来描述时间，内存，性能

如果函数f(n),g(n)之间存在一个常数c使得f(n)<=c*(g(n))则记为f(n)=O(g(n))

## 请你讲讲数组(Array)和列表(ArrayList)的区别？什么时候应该使用Array而不是ArrayList？

Array用于保存基本数据类型和对象大小固定而ArrayList用于保存对象大小变化，拥有更加丰富的API，当要存储的是一串固定大小的基本数据类型所构成的数组时应该用Array

## 请你解释什么是值传递和引用传递？

引用传递等于是传递了一个句柄

Java是值传递

值传递是对于基本类型而言的，所传递的是一个基本数据类型的副本，改变副本不改变原变量

引用传递一般是对于对象变量类型而言的，传递的是该对象地址的一个副本，而不是源对象本身，所以引用传递的对象改变会改变源对象。

参考:

<https://juejin.im/post/5bce68226fb9a05ce46a0476>

如果传递的是primitive基本数据类型，那么传过去的就是一个副本内容，原地址内容并没有发生改变，传过去由实参变成了形参，形参的变化并不会影响到原来实参所指向的地址的内容。

如果传递的是对象，那么其实就把一个句柄传过去了，这个句柄指向一个地址，这个地址代表了对象在堆中存储的位置，如果此时不作任何改动就对该句柄进行操作，那么也就修改了堆中对象的内容，如果将该句柄指向了一个新对象的地址，那么之后所有的操作都是对这个新对象所作的，并不会影响堆中原来对象。

至此，可以说java中的传递都是值传递。传递的都是一个句柄一个指针，指向一个地址，该地址可以保存一个基本数据类型，也可以保存另一个地址指向堆中真实的对象

![](https://user-gold-cdn.xitu.io/2018/10/23/1669e46be89ea9fe?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)

## 请你讲讲Java支持的数据类型有哪些？什么是自动拆装箱？

由基本数据类型和对象组成

基本数据类型primitive：short int long float double char bool byte

自动装箱就是把基本数据类型转为它们的包装类（wrapper 类）自动拆箱就是从包装类转为基本数据类型代码如下：

ArrayList**<**Integer**>** intList **=** new ArrayList**<**Integer**>();**
intList**.**add**(**1**);** *//autoboxing - primitive to object*
intList**.**add**(**2**);** *//autoboxing*

ThreadLocal**<**Integer**>** intLocal **=** new ThreadLocal**<**Integer**>();**
intLocal**.**set**(**4**);** *//autoboxing*

int number **=** intList**.**get**(**0**);** *// unboxing*
int local **=** intLocal**.**get**();** *// unboxing in Java*

## 请你讲讲一个十进制的数在内存中是怎么存的？

补码形式存储

## 请你说说Lamda表达式的优缺点。

优点：1. 简洁。2. 非常容易并行计算。3. 可能代表未来的编程趋势。

缺点：1. 若不用并行计算，很多时候计算速度没有比传统的 for 循环快。（并行计算有时需要预热才显示出效率优势）2. 不容易调试。3. 若其他程序员没有学过 lambda 表达式，代码不容易让其他语言的程序员看懂。

## 你知道java8的新特性吗，请简单介绍一下

lambda表达式用作实现一个接口减少冗余代码（）->{}

Stream API −新添加的Stream API（java.util.stream） 把真正的函数式编程风格引入到Java中。

Date Time API − 加强对日期与时间的处理。

Optional 类 − Optional 类已经成为 Java 8 类库的一部分，用来解决空指针异常。

Nashorn, JavaScript 引擎 − Java 8提供了一个新的Nashorn javascript引擎，它允许我们在JVM上运行特定的javascript应用。

## 请你说明符号“==”比较的是什么？

==比较的应该是栈中句柄指向的地址内容，如果是基本数据类型primitive那么就会直接比较存放在栈中的基本数据类型的值，如果是对象，那么==比较的就是句柄指向存放在栈中的一个堆中对象的地址，比较的是地址，地址相同说明句柄指向同一个对象则相同否则就不同。

##  请你解释Object若不重写hashCode()的话，hashCode()如何计算出来的？

使用本地native方法

Object 的 hashcode 方法是本地方法，也就是用 c 语言或 c++ 实现的，该方法直接返回对象的 内存地址。

##  请你解释为什么重写equals还要重写hashcode？

参考:

<https://blog.csdn.net/neosmith/article/details/17068365>

在hashset中添加值时

首先创建两个对象



重写hashCode()的目的在于，在A.equals(B)返回true的情况下，A,B的hashcode()也要返回相同的值

如果hashcode()重写不够健壮（比如查询hashcode就返回一个相同的值）那么哈希表也就退化成了链表，查找hashcode等于在遍历链表那么哈希表就失去了其本身的作用

```java
package priv.wzb.effective_java.HEOverride;

import java.util.Objects;

/**
 * @author Satsuki
 * @time 2019/10/11 22:07
 * @description:
 * 一个对象重写hashcode与equals方法
 */
public class Coder {
    private String name;
    private int age;

    public Coder() {
    }

    public Coder(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coder coder = (Coder) o;
        return age == coder.age &&
                name.equals(coder.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}

import java.util.HashSet;
import java.util.Set;

/**
 * @author Satsuki
 * @time 2019/10/11 22:08
 * @description:
 */
public class Test {
    @org.junit.Test
    public void test1(){
        // 创建两个对象
        Coder c1 = new Coder("satsuki",16);
        Coder c2 = new Coder("momuji",17);
        Coder c3 = new Coder("momuji",17);

        // 构造一个HashSet将c1对象存放到其中
        Set<Coder> set = new HashSet<>();
        set.add(c1);
        set.add(c2);

        // true
        System.out.println(set.contains(c2));

        // true
        // 若不重写hashcode 则为false
        // 若重写一个hashcode返回同一个值则把哈希表降级成了链表,降低了查询速度
        System.out.println(set.contains(c3));
    }
}

```

## 请你介绍一下map的分类和常见的情况

hashmap treemap linkedhashmap hashtable

前三者线程不安全后者线程安全但是如果多线程高并发环境下并不会使用hashtable会使用concurrenthashmap之类的或者Collections.synchronized将线程不安全的map转换为线程安全的map

treemap底层使用红黑树实现，所以其内部保存的entry总是有序的，linkedhashmap是hashmap的子类，保存了记录的插入顺序，用iterator遍历时先插入的会先得到。

## 请你讲讲Java里面的final关键字是怎么用的？

- final修饰类

  被final修饰的类不能被继承，final类中的成员变量可以根据需要设置为final，final类中的方法被隐式的指定为final

- final修饰方法

  使用final方法的原因由两个，一是把方法锁定以访任何继承类修改它的意义不想让它改变，二是加快效率

- final修饰成员变量

  final修饰成员变量，如果是基本数据类型，那么值不会改变，如果是引用那么该引用无法指向别的对象，其实就是锁定了栈中句柄指向的地址。

##  请你谈谈关于Synchronized和lock 

都是用于保持线程同步，synchronized是Java关键字，可以用来修饰方法或者代码块，能保证在同一时间只有一个线程执行该段代码JDK1.5以后引入了自旋锁、锁粗化、轻量级锁，偏向锁来有优化关键字的性能。

- 偏向锁

  运行过程中对象的锁偏向某个线程，已获得锁再次想获得该锁不需要再获得锁

- 轻量级锁

  在没有多线程竞争的情况下减少性能消耗，若是多线程同时竞争锁则会膨胀为重量级锁

- 重量级锁

  当其他线程占有锁会进入阻塞状态

- 自旋锁

  若锁被线程B获取，线程A也想获取锁，此时线程不进入等待状态而是原地空转等待B释放锁。



Lock是一个接口，synchronized使用内置的语言实现，synchronized发生异常时会自动释放线程占有的锁，因此不会导致死锁的情况发生，而Lock在发生异常时没主动通过unlock就不会主动释放锁会造成死锁现象，所以需要在finally中添加lock.unlock；lock可以让等待锁的线程响应中断，synchronized不行，lock可以尝试锁定，可以直到有没有获得锁，synchronized不行

ReentrantLock是Lock接口的实现类，可以完成所有synchronized的功能可以代替synchronized，ReentrantLock比synchronized更加灵活，可以使用tryLock尝试锁定可以根据返回值判断是否锁定成功所有的unlock操作必须放在finally代码块中否则会出现上述的死锁现象，可以通过interrupt来打断等待中的线程,ReentrantLock可以实现公平锁在初始化时传入true参数即可实现公平锁

##  请你介绍一下volatile？

volatile用于保证有序性和可见性，Java代码的执行顺序可能跟编写顺序不同，编译器会重新排序，CPU也会重新排序，为了提高运行效率。有序性实现的是通过插入内存屏障来保证的。可见性，Java内存模型分为，主内存，工作内存，如果线程A从主存读取变量到自己的线程中，并且做了+1操作，但是此时没有将刷新的值放入主存，线程B读取的还是旧值。为了让值每次都去主存中存储或者获取保证可见性。

##  请你介绍一下Syncronized锁，如果用这个关键字修饰一个静态方法，锁住了什么？如果修饰成员方法，锁住了什么？

synchronized修饰静态方法以及同步代码块，用法锁的是类，线程想执行响应的同步代码需要获得类锁

synchronized修饰成员方法，线程获取的是当前调用该方法的对象实例的对象锁

## 请解释hashCode()和equals()方法有什么联系？

相等（相同）的对象必须拥有相等的hashCode

而拥有相等hashCode的对象不一定相同

## 请解释Java中的概念，什么是构造函数？什么是构造函数重载？什么是复制构造函数？

当对象被创建，构造函数就会被调用。每个类都有构造函数。在程序员没有提供构造函数的情况下Java编译器会为类创建一个默认的构造函数

构造函数的重载与类方法的重载相似。一个类可以有多个构造函数，重载的构造函数，参数个数或顺序必须不同。

Java不支持复制构造函数。复制构造函数是C++编程语言中的一种特别的构造函数，习惯上用来创建一个全新的对象，这个全新的对象相当于已存在对象的副本。这个构造函数只有一个参数：就是用来复制对象的引用。构造函数也可以有更多的参数，但除了最左第一个参数是该类的引用类型外，其它参数必须有默认值。

##  请说明Java中的方法覆盖(Overriding)和方法重载(Overloading)是什么意思？

overriding是用于重写接口中的方法实现或者继承的父类中的方法的重写

overloading是重载，是方法的重载

##  请说明Query接口的list方法和iterate方法有什么区别？

list()方法无法利用一级缓存和二级缓存，iterate充分利用缓存，效率更高。

list方法不会引起N+1问题，iterate方法会引起N+1问题

##  请你谈一下面向对象的"六原则一法则"。

- 单一职责原则（Single Responsibility Pinciple,SRP）

  一个类只负责一个功能领域中的相应职责

- 开闭原则(Open-Closed Principle,OCP)

  软件实体应堆扩展开放对修改关闭

- 里氏代换原则(Liskov Substitution Principle,LSP)

  所有引用基类对象的地方都能透明地使用其父类

- 依赖倒转原则(Dependence Inversion Principle,DIP)

  抽象不应该依赖于细节，细节应该依赖于抽象，针对接口编程而不是实现编程

- 接口隔离原则(Interface Segregation Principle,ISP)

  使用多个专门的接口，而不是用单一的总接口

- 合成复用原则(Composite Reuse Principle,CRP)

  尽量使用对象的组合而不是继承达到复用目的

- 迪米特法则(Law of Demeter,LoD)

  一个软件实体应当尽可能少的与其他实体发生相互作用

##  请说明如何通过反射获取和设置对象私有字段的值？

- 通过Class.forName("类引用路径")获取类。
- 使用.getDeclaredField("要操作的私有字段名")获取Field
- 调用Field.setAccessible(true)使得私有字段可以访问
- 调用Field.set(对象句柄,要设置私有字段的值)

```java
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Satsuki
 * @time 2019/9/12 15:37
 * @description:
 * 应用反射API动态的操作
 * 类名，属性，方法，构造器等
 */
public class Demo02 {
    public static void main(String[] args) {
        String path = "priv.wzb.javabase.reflection.RUser";
        try {
            Class<RUser> clazz = (Class<RUser>) Class.forName(path);

            //通过反射API调用构造方法，构造对象
            RUser u =  clazz.newInstance();//其实是调用了user的无参构造方法

            //记得重写类的无参构造器因为反射的应用会调用类的无参构造器生成对象
            Constructor<RUser> declaredConstructor = clazz.getDeclaredConstructor(int.class, int.class, String.class);
            RUser u2 = declaredConstructor.newInstance(1001, 17, "satsuki");
            System.out.println(u2.toString());

            //通过反射API调用普通方法
            RUser u3 = clazz.newInstance();
            System.out.println(u.hashCode()==u3.hashCode());
            Method setUname = clazz.getDeclaredMethod("setUname", String.class);
            setUname.invoke(u3,"stk3");
            System.out.println(u3.getUname());

            //通过反射API操作属性
            RUser u4 = clazz.newInstance();
            Field f = clazz.getDeclaredField("uname");
            f.setAccessible(true);//这个属性不用做安全检查直接访问
            f.set(u4,"stk4");
            System.out.println(u4.getUname());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## 请判断，两个对象值相同(x.equals(y) == true)，但却可有不同的hash code，该说法是否正确，为什么？

不对，如果x,y满足(x.equals(y) == true)，他们的hashcode应当相同，(1)如果两个对象相同（equals方法返回true），那么它们的hashCode值一定要相同；(2)如果两个对象的hashCode相同，它们并不一定相同。当然，你未必要按照要求去做，但是如果你违背了上述原则就会发现在使用容器时，相同的对象可以出现在Set集合中，同时增加新元素的效率会大大下降（对于使用哈希存储的系统，如果哈希码频繁的冲突将会造成存取性能急剧下降）。

##  请说明内部类可以引用他包含类的成员吗，如果可以，有没有什么限制吗？

一个内部类对象可以访问创建它的外部对象的内容，内部类如果不是static的，那么它可以访问创建它的外部对象的所有属性。如果是static的则只能访问外部对象的所有static参数和方法。一般普通类只有public或package的访问修饰，而内部类可以实现static，protected，private等访问修饰。当从外部类继承的时候，内部类是不会被覆盖的，它们是完全独立的实体，每个都在自己的命名空间内，如果从内部类中明确地继承，就可以覆盖原来内部类的方法。

## 请说明JAVA语言如何进行异常处理，关键字：throws,throw,try,catch,finally分别代表什么意义？在try块中可以抛出异常吗？

Java通过面向对象的方法进行异常处理，把各种不同的异常进行分类，并提供了良好的接口。在Java中，每个异常都是一个对象，它是Throwable类或它子类的实例。当一个方法出现异常后便抛出异常对象，异常对象包含异常信息，这时可以通过它的类型来捕捉（catch）它，或最后（finally)由缺省处理器来处理，用try指定一块预防所i有异常的程序。紧跟在try后面应包含一块catch子句来指定捕获想捕获的异常类型。throw语句用于明确抛出各种异常。finally为不管什么情况都会执行的代码。可以在一个成员函数调用的外面写一个try语句，在这个成员函数内部写另一个try语句保护其他代码。每当遇到一个try语句，”异常“的框架就放到堆栈上面，直到所有的try语句都完成。如果下一级的try语句没有对某种”异常”进行处理，堆栈就会展开，直到遇到有处理这种”异常”的try语句。

## 请说明Java的接口和C++的虚类的相同和不同处。

由于Java不支持多继承，而有可能某个类或对象要使用分别在几个类或对象中的方法或者属性，现有的单继承无法满足，与集成相比，接口具有更高的灵活性，所有的方法都没有具体实现，而一个类实现某个接口后必须重写接口内的所有方法接口内的成员变量都是public static的而成员方法都是public的

## 请判断当一个对象被当作参数传递给一个方法后，此方法可改变这个对象的属性，并可返回变化后的结果，那么这里到底是值传递还是引用传递?

Java中只有值传递，Java传递的是一个句柄，一个指针指向栈中的某个地址。该地址内存放的可能是基本数据类型也可能是存放于堆中对象的地址。

当一个对象实例作为一个参数被传递到方法中，参数的值就是对该对象的引用。对象的内容可以在调用的方法中改变，但是对象的引用是不会发生变化的

## 请你说说Static Nested Class 和 Inner Class的不同

Static Nested Class是被声明为静态（static）的内部类，它可以不依赖于外部类实例被实例化。而通常的内部类需要在外部类实例化后才能实例化。Static-Nested Class 的成员, 既可以定义为静态的(static), 也可以定义为动态的(instance).Nested Class的静态成员(Method)只能对Outer Class的静态成员(static memebr)进行操作(ACCESS), 而不能Access Outer Class的动态成员(instance member).而 Nested Class的动态成员(instance method) 却可以 Access Outer Class的所有成员, 这个概念很重要, 许多人对这个概念模糊. 有一个普通的原则, 因为静态方法(static method) 总是跟 CLASS 相关联(bind CLASS), 而动态方法( (instance method) 总是跟 instance object 相关联, 所以,静态方法(static method)永远不可以Access跟 object 相关的动态成员(instance member),反过来就可以, 一个CLASS的 instance object 可以 Access 这个 Class 的任何成员, 包括静态成员(static member).

## 请你讲讲abstract class和interface有什么区别?

抽象类不支持多继承，任何类只能继承（extends）一个抽象类，interface支持多继承一个类可以实现多个接口（implements），只要有一个抽象方法的类就是抽象类，在方法上加上abstract关键字的方法就是抽象方法，抽象类像普通类一个可以有自己的成员变量和成员方法，接口中的方法一般不允许有实现，但是高版本Java允许空实现，接口中的变量和方法默认是public static的

声明方法存在而不去实现它的类叫做抽象类。不能创建abstract类的实例然而可以创建一个变量，其类型是抽象类，并让它指向子类的一个实例。不能有抽象构造函数或抽象静态方法。abstract类的子类为他们父类中的所有抽象方法提供实现，否则他们也是抽象类。

接口是抽象类的变体。在接口中所有方法抽象。多继承性可通过实现这样的接口而获得。接口中的所有方法都是抽象的，没有程序体。接口只可以定义static final成员变量。接口可以作为引用变量的类型。使用instanceof运算符可以确定某对象是否实现了某接口。

##  请说明Overload和Override的区别，Overloaded的方法是否可以改变返回值的类型?

方法重写Overridding和重载Overloading是Java多态性的不同表现。重写Overriding是父类与子类之间多态性的一种表现。重载Overloadding是一个类中多态性的一种表现。如果在子类中定义某方法与其父类有相同的名称和参数，我们说该方法呗重写。子类的对象使用这个方法时，将调用子类中的定义，父类中的定义仿佛呗屏蔽了。如果一个类中定义了多个同名方法，他们有不同参数个数或不同参数类型，成为重载（overloaded）重载的方法可以改变返回值类型。

## 请说明一下final, finally, finalize的区别。

final是关键字

final修饰类表明该类不可被继承，修饰方法表明方法不可覆盖，修饰属性表明属性不可更改。

finally用于修饰代码块是异常处理语句结构的一部分，表示总是执行。

finalize是Object类的一个方法，在垃圾收集器执行的时候会调用被回收对象的此方法，可以覆盖此方法提供垃圾收集时的其他资源回收，例如关闭文件等。

## 请说明面向对象的特征有哪些方面

- 抽象

  抽象就是忽略一个主题中与当前目标无关的那些方面，以便更充分地注意与当前目标有关的方面。抽象并不打算了解全部问题，只是选择其中一部分。过程抽象和数据抽象。

- 封装

  封装是把过程和数据包围起来，对数据的访问只能通过以定义的界面。现实世界可以被描绘成一系列完全自治、封装的对象，这些对象通过一个受保护的接口访问其他对象

- 继承

  描述了类的称此结构对象的一个新类可以从现有类中派生。这个过程称为类继承，新类继承了原始类的特性，新类称为原始类的派生类（子类）	原始类称为新类的基类。派生类可以使用基类的成员变量和方法也可以做出一些修改或增加。

- 多态

  允许不同类的对象对同一消息作出相应。参数化多态和包含多态性。很好的解决了应用程序函数同名问题。

## 请说明Comparable和Comparator接口的作用以及它们的区别。

两种方法各有优势，用Comparable简单，只要实现Comparable接口的对象直接就称为一个可比较的对象，注意，equals()方法与compareTo()返回0值应该保持一致，否则使用set等无法保存相同对象的容器容易产生错误，但是Comparable需要修改原代码违反了开闭原则，此时就有了基于策略模式的Comparator，使用Comparator的好处就是不需要修改原代码，而是另外实现一个比较器，当某个自定义对象需要作比较的时候，把比较器和对象一起传递过去就可以比较大小，例如Collections.sort(list,new MyComparator);

Java提供一个只包含compareTo()方法的Comparable接口。这个方法可以给两个对象排序，如果返回值是负数，0，正数，表明输入对象小于，等于，大于已存在的对象。

Java提供包含compare()和euqals()两个方法的Comparator接口。compare()方法用来给两个输入参数排序，返回负数，0，正数表明第一个参数是小于，等于，大于第二个参数。equals()方法需要一个对象作为参数，它用来决定输入参数是否和comparator相等。

## 接口和抽象类的区别是什么？

接口中所有方法都是隐含抽象的，而抽象类则可以同时包含抽象和非抽象方法。

接口中所有的方法隐含的都是抽象的。而抽象类则可以同时包含抽象和非抽象的方法。
类可以实现很多个接口，但是只能继承一个抽象类
类可以不实现抽象类和接口声明的所有方法，当然，在这种情况下，类也必须得声明成是抽象的。
抽象类可以在不提供接口方法实现的情况下实现接口。
Java接口中声明的变量默认都是final的。抽象类可以包含非final的变量。
Java接口中的成员函数默认是public的。抽象类的成员函数可以是private，protected或者是public。
接口是绝对抽象的，不可以被实例化。抽象类也不可以被实例化，但是，如果它包含main方法的话是可以被调用的。

##  请说明Java是否支持多继承？

Java中类不支持多继承，只支持单继承（即一个类只有一个父类）。 但是java中的接口支持多继承，，即一个子接口可以有多个父接口。（接口的作用是用来扩展对象的功能，一个子接口继承多个父接口，说明子接口扩展了多个功能，当类实现接口时，类就扩展了相应的功能）。

一个类只能由一个父类，但可以实现多个接口。

## 请你谈谈如何通过反射创建对象？

- 通过类对象调用newInstance()方法，例如：String.class.newInstance()
- 通过类对象的getConstructor()或getDeclaredConstructor()方法获得构造器(Constructor)对象并调用其newInstance()方法创建对象，例如String.class.getConstructor(String.class).newInstance("Hello");

## 请你说明是否可以在static环境中访问非static变量？

不行，因为static变量在Java中是属于类的，它在所有实例中的值是一样的。当Java虚拟机载入类的时候会对static变量初始化，而在static环境中访问未初始化的变量非static变量会访问那些未被创建出来的变量会出错。

## 请解释一下extends 和super 泛型限定符

extends代表了泛型上界例如

```
class Fruit {}
class Apple extends Fruit {}
class Jonathan extends Apple {}
class Orange extends Fruit {}
List<? extends Fruit> flistTop = new ArrayList<Apple>();
Fruit fruit = flistTop.get(0);
```

就只能使用get方法无法使用add方法因为所有类都是Furlt子类无法确定具体添加哪个类

但是却可以通过Fruit接收所有类

super代表了泛型下界

```
//下界
        List<? super Apple> flistBottem = new ArrayList<Apple>();
        flistBottem.add(new Apple());
        flistBottem.add(new Jonathan());
//        flistBottem.add(new Fruit());
        //get Apple对象会报错
        //Apple apple = flistBottem.get(0);
        Object o = flistBottem.get(0);
```

只能add（）Apple或它的子类

- extends上限通配符，用来限制类型的上限，只能传入本类和子类，add方法受阻，可以从一个数据类型里获取数据；
- super下限通配符，用来限制类型的下限，只能传入本类和父类，get方法受阻，可以把对象写入一个数据结构里；
- 限定通配符总是包括自己
  上界类型通配符：add方法受限
  下界类型通配符：get方法受限
  如果你想从一个数据类型里获取数据，使用 ? extends 通配符
  如果你想把对象写入一个数据结构里，使用 ? super 通配符
  如果你既想存，又想取，那就别用通配符
  不能同时声明泛型通配符上界和下界
  ————————————————
  版权声明：本文为CSDN博主「I18N_R」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
  原文链接：https://blog.csdn.net/qq_40395278/article/details/88603655

## 请你讲讲什么是泛型？

泛型即参数化类型

将类型由原来的具体的类型参数化。例如List<E>中的E就是一种泛型

##  请说明静态变量存在什么位置?

方法区，静态变量，常数，类信息都保存在方法区

栈值得是Java虚拟机栈存放基本数据类型primitive或者对对象的引用（句柄）

堆区存放对象实例和数组

## 请你解释一下类加载机制，双亲委派模型，好处是什么？

某个特定类加载器在接到加载类的请求时，首先将加载任务委托给父类加载器，依次递归，如果父类加载器可完成加载任务则返回，只有父类无法完成加载任务时才自己去加载。

使用双亲委派模型的好处在于Java类锁着它的类加载器一起具备了一种带有优先级的层次关系。例如类java.lang.Object，它存在在rt.jar中，无论哪一个类加载器要加载这个类，最终都是委派给处于模型最顶端的Bootstrap ClassLoader进行加载，因此Object类在程序的各种类加载器环境中都是同一个类。相反，如果没有双亲委派模型而是由各个类加载器自行加载的话，如果用户编写了一个java.lang.Object的同名类并放在ClassPath中，那系统中将会出现多个不同的Object类，程序将混乱。因此，如果开发者尝试编写一个与rt.jar类库中重名的Java类，可以正常编译，但是永远无法被加载运行

## 请你谈谈StringBuffer和StringBuilder有什么区别，底层实现上呢？

StringBuffer线程安全，StringBuilder线程不安全，底层实现上的话，StringBuffer其实就是比StringBuilder多了Synchronized修饰符。

## 请说明String是否能能继承？

不能，String类用final修饰无法被继承

```
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence 
```

## 请说明”static”关键字是什么意思？Java中是否可以覆盖(override)一个private或者是static的方法？

static关键字表明一个成员变量或成员方法可以在没有所属类的实例变量情况下被访问。

Java中static不能被覆盖，因为方法覆盖是基于运行时动态绑定的。而static方法是静态绑定的。

## 请说明重载和重写的区别，相同参数不同返回值能重载吗？

重载是类中多态性的一种体现，方法名必须相同，但是参数的类型，顺序，个数和返回值都可以不同。

重写是类之间多态性的体现，子类（派生类）重写父类（基类）的方法必须拥有相同方法名，参数，返回值。子类函数的访问权限不能少于父类的

## 请列举你所知道的Object类的方法并简要说明。

clone()创建并返回一个对象的副本，equals(Object obj)只是某个其他对象是否与该对象相等。finalize()当垃圾回收器确定不存在对象的引用时，由垃圾回收器调用此方法，getClass()获取对象的运行时类。toString()把当前对象以字符串描述，hashCode()返回该对象的hash值，notify（）唤醒在此对象监视器上等待的单个线程，notifyAll()唤醒在此对象监视器上等待的所有线程，wait（）导致当前线程暂停直到被其他线程调用此对象的notify或者notifyAll()wait(long timeout)导致当前的线程等待，直到其他线程调用此对象的 notify() 方法或 notifyAll() 方法，或者超过指定的时间量。wait(long timeout, int nanos) 导致当前的线程等待，直到其他线程调用此对象的 notify() 方法或 notifyAll() 方法，或者其他某个线程中断当前线程，或者已超过某个实际时间量。

## 请说明类和对象的区别

对象是类的实例化，类是一些描述，但不是具体的实现，是抽象的。

对象是函数、变量的集合体；而类是一组函数和变量的集合体，即类是一组具有相同属性的对象集合体。

##  请解释一下String为什么不可变？

因为String类的char[]数组容器是final类型的。

##  请讲讲Java有哪些特性，并举一个和多态有关的例子。

封装、继承、多态。多态：对不同数据的不同处理的整合。

## 请你讲讲wait方法的底层原理

ObjectSynchronizer::wait方法通过object的对象中找到ObjectMonitor对象调用方法 void ObjectMonitor::wait(jlong millis, bool interruptible, TRAPS)

通过ObjectMonitor::AddWaiter调用把新建立的ObjectWaiter对象放入到 _WaitSet 的队列的末尾中然后在ObjectMonitor::exit释放锁，接着 thread_ParkEvent->park 也就是wait。

链接：

https://www.nowcoder.com/questionTerminal/34f9ba9283f54975939bc631eead1e64

来源：牛客网

wait方法会将当前线程放入wait set，等待被唤醒，并放弃lock对象上的所有同步声明。 

  1、将当前线程封装成ObjectWaiter对象node； 

  2、通过ObjectMonitor::AddWaiter方法将node添加到_WaitSet列表中； 

  3、通过ObjectMonitor::exit方法释放当前的ObjectMonitor对象，这样其它竞争线程就可以获取该ObjectMonitor对象。 

  4、最终底层的park方法会挂起线程； 

  （最后与之对应的notify方法）会随机唤醒_WaitSet中随机一个线程

##  请说明List、Map、Set三个接口存取元素时，各有什么特点？

List以特定索引来存取元素，可以有重复元素。Set不能存放重复元素（用对象的equals()方法来区别重复，重写对象的equals方法同时要重写hashcode方法，若两个对象相同，其hashcode必定相同，反之则不一定）。Map保存键值对（key-value pair)映射，映射关系可以是一对一或多对一。Set和Map重启都有基于哈希存储和排序树两种实现版本HashMap，HashSet或者TreeMap、TreeSet，基于哈希存储的版本理论存取时间复杂度为O（1），基于排序树版本的实现在插入或删除元素时会按照元素或元素的键（key）构成排序树从而达到去重目的

```java
private transient HashMap<E,Object> map;
// Dummy value to associate with an Object in the backing Map
private static final Object PRESENT = new Object();
```

set的底层实现其实也是依靠对应的map来实现的，将map的值设为一个固定的Object

## 阐述ArrayList、Vector、LinkedList的存储性能和特性

ArrarList get方法快set慢索引数据块插入数据慢LinkedList正好相反

ArrayList和Vector都是采用数组方式存储数据，插入元素涉及到数组元素移动等内存操作

LinkedList采用双向链表数据结构，将内存中零散的数据单元关联起来	形成按序号索引的线性数据结构与数组连续存储方式相比，内存利用率更高 搜索数据按序号索引数据向前或向后遍历，插入快，索引慢

Vector属于遗留容器（Java早期版本中提供的容器，除此之外，Hashtable、Dictionary，BitSet，Stack，Properties都是遗留容器）母舰不推荐使用。

ArrayList和LinkedList都不是线程安全的，遇到多线程环境需要用Collections.synchronizedList方法转换成线程安全容器后再使用这是对装潢模式的应用，将已有对象传入另一个类的构造器中，创建新的对象来增强实现。

map容器可以用java.util.concurrent包下的容器实现线程安全

## 请判断List、Set、Map是否继承自Collection接口？

List和Set继承自Collection，Map不是

Map是键值对映射容器，与List和Set有明显的区别，而Set存储的零散的元素且不允许有重复元素（数学中的集合也是如此），List是线性结构的容器，适用于按数值索引访问元素的情形。

## 请讲讲你所知道的常用集合类以及主要方法？



Collection（List、Set）

ArrayList、Vector（数组）LinkedList(双向链表) size（）add（）clear、clone、remove、replace、get

Map(hashmap、TreeMap(底层键使用红黑树实现有序排列))键值对的存储数据结构

contains get put replace size

## 请说明Collection 和 Collections的区别。

Collection是集合类的上级接口，继承他的接口主要有Set和List

Collections是针对集合类的帮助类，提供了一系列静态方法实现对各种集合的搜索、排序、线程安全化等操作

## 请说明ArrayList和LinkedList的区别？

ArrayList和LinkedList都实现了List接口，他们有以下的不同点：
ArrayList是基于索引的数据接口，它的底层是数组。它可以以O(1)时间复杂度对元素进行随机访问。与此对应，LinkedList是以元素列表的形式存储它的数据，每一个元素都和它的前一个和后一个元素链接在一起，在这种情况下，查找某个元素的时间复杂度是O(n)。
相对于ArrayList，LinkedList的插入，添加，删除操作速度更快，因为当元素被添加到集合任意位置的时候，不需要像数组那样重新计算大小或者是更新索引。
LinkedList比ArrayList更占内存，因为LinkedList为每一个节点存储了两个引用，一个指向前一个元素，一个指向下一个元素。

## 请你说明HashMap和Hashtable的区别？ 

都使用了哈希表来存储key

hashtable是线程安全的但是一般使用concurrent包下的map作为线程安全容器或者Collections.synchronized方法来把hashmap转为线程安全的容器

都实现了map接口

hashmap允许键和值是null而hashtable不允许

HashMap提供了可供应用迭代的键的集合，因此，HashMap是快速失败的。另一方面，Hashtable提供了对键的列举(Enumeration)。

## 请说说快速失败(fail-fast)和安全失败(fail-safe)的区别？

iterator的安全失败是基于对底层集合做拷贝，因此不受源集合上修改的影响。java.util包下的所有集合类都是快速失败的java.util.concurrent包下的所有集合类都是安全失败的快速失败的迭代器会抛出ConcurrentModificationException异常，而安全失败的迭代器永远不会抛出这样的异常。

快速失败和安全失败是对迭代器而言的。 快速失败：当在迭代一个集合的时候，如果有另外一个线程在修改这个集合，就会抛出ConcurrentModification异常，java.util下都是快速失败。 安全失败：在迭代时候会在集合二层做一个拷贝，所以在修改集合上层元素不会影响下层。在java.util.concurrent下都是安全失败
————————————————
版权声明：本文为CSDN博主「龙骨」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/qq_31780525/article/details/77431970

## 请你说说Iterator和ListIterator的区别？

![1571290396556](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1571290396556.png)

Iterator可以遍历Set和List但是ListIterator只能遍历List

Iterator对集合只能 是向前遍历，ListIterator可以向前或向后遍历

ListIterator实现了Iterator接口，并包含其他的功能，比如：增加元素，替换元素，获取前一个和后一个元素的索引，等等。

## 请简单说明一下什么是迭代器？

Iterator提供了统一遍历操作集合元素的统一接口，Collection接口实现Iterable接口

每个集合都通过实现Iterable接口中iterator（）方法返回iterator接口的实例，然后对集合元素进行迭代操作

在使用Iterator遍历元素时不能对元素进行修改否则会抛出ConcurrentModificationException异常但是可以通过iterator.remove方法进行删除

## 请解释为什么集合类没有实现Cloneable和Serializable接口？

这个问题说的不清楚，集合类框架中的接口没有实现Cloneable和Serializable接口，但是具体的实现类是实现了这些接口的，比如Arraylist。

接口，比如collection list iterable

克隆（cloning）或者序列化（serialization）的予以和含义是根具体的实现相关的。因此，应该由集合类的具体实现来决定如何被克隆或者是序列化。

实现Serializable序列化的作用：将对象状态保存在存储媒体中以便可以在以后重写创建出完全相同的副本；按置将对象从一个应用程序域发送到另一个应用程序域。

实现Serializable接口的作用就是可以把对象存到字节流，然后可以恢复。所以你想如果你的对象没有序列化，怎么才能进行网络传输呢？要网络传输就得转为字节流，所以在分布式应用中，你就得实现序列化。如果不需要分布式应用就没必要实现序列化。

java如果用json传输数据javabean时许需要实现Serializable

**个人观点：我写的应用中不需要实现Serializable因为我使用了json数据格式。**

**结论**：java如果使用json+restfulAPI进行数据交互那么javabean不需要implements Serializable接口因为对象数据并没有真正的进行网络传输或者持久化。而是转换为了json字符串。进行网络传输的是对象的具体信息而不是对象本身。所以在这种场景下不需要implements Serializable。

如果使用dubbo这种RPC（Remote Procedure Call）框架那么使用面向接口编程的时候，接口中的参数如果是类那么该类就必须implements Serializable才可以让对象在远程调用的时候在网络上传输。

**javabean为什么不implements Serializable也可以保存到数据库中**

不是说数据持久化就必须implements Serializable接口么

原因：

Java的基本数据类型（primitive：short int long float double byte char boolean）在数据库中都有对应的字段。一些Java的集合具体实现类也都实现了Serializable接口。那么JavaBean此时不实现该接口也可以存储到数据库中

**序列化绝对不止实现Serializable接口一种方式。还存在其他序列化方式可能效率更高。但是稳定性肯定是Serializable最好。因为该接口从Java1.1开始就存在并且一直保持使用**

<https://github.com/eishay/jvm-serializers/wiki>

序列化存储到本地，若从存储介质中读取数据字节流并反序列化的时候并不会调用类的构造函数

具体序列化参考：

<https://juejin.im/post/5ce3cdc8e51d45777b1a3cdf#heading-11>



## 请说明Java集合类框架的基本接口有哪些？

Collection：代表一组对象。

Set：不包含重复元素的Collection

List：有序Collection可以包含重复元素

Map ：key不可重复value可重复。多对一或一对一

## 请你说明一下ConcurrentHashMap的原理？

HashTable

参考：

**HashMap? ConcurrentHashMap? 相信看完这篇没人能难住你！**

<https://crossoverjie.top/2018/07/23/java-senior/ConcurrentHashMap/>

**探索 ConcurrentHashMap 高并发性的实现机制**

负载因子

```JAVA
static final float DEFAULT_LOAD_FACTOR = 0.75f;
```

给定的默认容量为 16，负载因子为 0.75。Map 在使用过程中不断的往里面存放数据，当数量达到了 `16 * 0.75 = 12` 就需要将当前 16 的容量进行扩容，而扩容这个过程涉及到 rehash、复制数据等操作，所以非常消耗性能。



```java
public class ConcurrentHashMap<K,V> extends AbstractMap<K,V>
    implements ConcurrentMap<K,V>, Serializable {
    private static final long serialVersionUID = 7249069246763182397L;
```

```java
	

/**
     * Returns the hash code value for this {@link Map}, i.e.,
     * the sum of, for each key-value pair in the map,
     * {@code key.hashCode() ^ value.hashCode()}.
     *
     * @return the hash code value for this map
     */
    public int hashCode() {
        int h = 0;
        Node<K,V>[] t;
        if ((t = table) != null) {
            Traverser<K,V> it = new Traverser<K,V>(t, t.length, 0, t.length);
            for (Node<K,V> p; (p = it.advance()) != null; )
                h += p.key.hashCode() ^ p.val.hashCode();
        }
        return h;
    }


/**
 * Stripped-down version of helper class used in previous version,
 * declared for the sake of serialization compatibility
 */
static class Segment<K,V> extends ReentrantLock implements Serializable {
    private static final long serialVersionUID = 2249069246763182397L;
    final float loadFactor;
    Segment(float lf) { this.loadFactor = lf; }
}
```

JDK1.8以下版本的ConcurrentHashMap采用分离锁（分段锁）机制内置了一个内部类Segment包含了Segments数组用于存储

多个segment（table）每个table都是一个带链式存储结构的数组.数组中存放了带N个节点的链式数据结构。

这些节点当中才存储了真正的map数据

具体结构示意图如下

![ConcurrentHashMap 的结构示意图](https://www.ibm.com/developerworks/cn/java/java-lo-concurrenthashmap/image005.jpg)

**put方法**

- 计算要存储的map数据的键的散列码

- 根据散列码找到对应的Segment

- 执行某个特定的Segment的put方法

- 加锁，锁定某个特定的Segment

  这里的枷锁操作是针对键的hash值对应的某个特定的Segment锁定的是该Segment而不是整个ConcurrentHashMap,此时如果在插入过程中再次执行put操作并不会因为当前线程对这个Segment的枷锁而阻塞。同时，ConcurrentHashMap中所有的get（读）线程也几乎不会一位内本线程的枷锁而阻塞

- 判断再加一个数据是否超出再散列的阈值，超出则再散列，不超出则继续执行

- 通过散列码找到table中具体的HashBucket

- 在Bucket中查找有没有与要插入的元素的键相等的元素

- 如果有说明map已存在，更新value值

- 如果没有，说明map不存在创建新节点。并保存到链表（bucket）头部

get方法并没有实现线程同步

ConcurrentHashMap仅对对ConcurrentHashMap的结构产生影响的操作进行线程同步，读取并不会影响到ConcurrentHashMap的结构所以不做线程同步

ConcurrentHashMap是并发散列映射表的实现。它允许完全并发的读取并且支持给定数量的并发更新。相比于 `HashTable 和`用同步包装器包装的 HashMap（Collections.synchronizedMap(new HashMap())），ConcurrentHashMap 拥有更高的并发性。在 HashTable 和由同步包装器包装的 HashMap 中，使用一个全局的锁来同步不同线程间的并发访问。同一时间点，只能有一个线程持有锁，也就是说在同一时间点，只能有一个线程能访问容器。这虽然保证多线程间的安全并发访问，但同时也导致对容器的访问变成``*串行化*``的了。

在使用锁来协调多线程并发访问的模式下。减小对锁的竞争可以提高并发性，有两种方式可以减小对锁的竞争

减小请求同一锁的频率

减少持有锁的时间

ConcurrentHashMap 的高并发性主要来自于三个方面

- 用分离锁实现多线程间更深层次的共享访问

- 用HashEntry对象的不变性来降低执行读操作的线程在遍历链表期间对枷锁的需求

  final HashEntry<K,V> next;因为是final所以不会变，在put操作时会用新的链表代替旧的链表

- 通过对同一个volatile变量的读/写访问来协调不同线程间读/写的内存可见性

使用分离锁减少了请求同一个锁的概率

使用HashEntry的不变性减少了对锁的竞争，减少了锁的占有时间和占有频率

通过减小请求同一个锁的频率和尽量减少持有锁的时间使得ConcurrentHashMap的的并发性相对于HashTble和用同步包装器包装的HashMap有了质的提高。

Concurrent有两个静态内部类HashEntry和Segment。HashEntry用于封装保存映射表键值对，Segment来充当锁的角色，每个Segment内部都有多个桶每个桶由若干个HashEntry拼接起来的链表组成。一个ConcurrentHashMap包含多个Segment。在HashEntry中将next设为final使得该容器拥有了不变性，这样就使得调用get方法时不需要保持线程同步。减少了锁的竞争提高了效率。

在ConcurrentHashMap 中，在散列时如果产生“碰撞”，将采用“分离链接法”来处理“碰撞”：把“碰撞”的 HashEntry 对象链接成一个链表。由于 HashEntry 的 next 域为 final 型，所以新节点只能在链表的表头处插入。 下图是在一个空桶中依次插入 A，B，C 三个 HashEntry 对象后的结构图：

图1. 插入三个节点后桶的结构示意图：



![img](https://uploadfiles.nowcoder.com/images/20180925/308572_1537878284540_B6C31D01D41C9E1714958F9C56D01D8F)

注意：由于只能在表头插入，所以链表中节点的顺序和插入的顺序相反。

Segment 类继承于 ReentrantLock 类，从而使得 Segment 对象能充当锁的角色。每个 Segment 对象用来守护其（成员对象 table 中）包含的若干个桶。

## 请解释一下TreeMap?

一个有序的Map容器，底层使用红黑树来实现排序。排序方式根据键的自然顺序进行排序或者根据创建映射提供的Comparator进行排序取决于使用的构造函数

TreeMap的特性：

每个根节点都是黑色的

所有节点都是红色/黑色的

每个叶子节点都是黑色的空节点

任何节点到叶子节点所经过的路径包含相同个数的黑色节点

每个红色节点的两个子节点都是黑色的（每个叶子节点到根的所有路径上不能出现两个红色节点）

## 请说明ArrayList是否会越界？

ArrayList是基于动态数组的数据结构

并发add()可能出现数组下标越界异常

多线程环境下一个ArrayList在add之间会对下标是否越界进行判断然后添加值若判断完成值添加之前又有一个线程进行了判断那么此时ArrayList返回还有空余位置可以保存。这样就保存了两条记录就可能产生越界情况

## 请你说明concurrenthashmap有什么优势以及1.7和1.8区别？

**concurrenthashmap**锁的粒度更小，采用分离锁机制减少了对相同锁的请求。采用存储元素结构（HashEntry）的不变性来使得get方法不需要进行同步操作，concurrenthashmap只需要对map结构会产生改变的操作添加同步即可这样减小了对锁的竞争频率。

1.7采用Segment+HashEntry

1.8采用Node+CAS+Synchronized来保证并发安全进行。1.8中使用一个volatile类型的变量baseCount记录元素的个数，当插入新数据或则删除数据时，会通过addCount()方法更新baseCount，通过累加baseCount和CounterCell数组中的数量，即可得到元素的总个数；

## 请你解释HashMap的容量为什么是2的n次幂？

负载因子默认是0.75，2^n是为了让散列更加均匀，若出现机端情况所有散列值都指向一个下标那么hash会由O（1）退化为O（n）

## 请你简单介绍一下ArrayList和LinkedList的区别，并说明如果一直在list的尾部添加元素，用哪种方式的效率高？

ArrayList底层实现是数组，查询快

LinkedList底层实现是双向链表，增删快，所以一直在list的尾部添加元素使用LinkedList更好

## 如果hashMap的key是一个自定义的类，怎么办？

必须重写hashcode和equals方法来帮助hashmap进行键是否存在的判断

## 请你解释一下hashMap具体如何实现的？

Hashmap基于数组实现的，通过对key的hashcode & 数组的长度得到在数组中位置，如当前数组有元素，则数组当前元素next指向要插入的元素，这样来解决hash冲突的，形成了拉链式的结构。put时在多线程情况下，会形成环从而导致死循环。数组长度一般是2n，从0开始编号，所以hashcode & （2n-1），（2n-1）每一位都是1，这样会让散列均匀。需要注意的是，HashMap在JDK1.8的版本中引入了红黑树结构做优化，当链表元素个数大于等于8时，链表转换成树结构；若桶中链表元素个数小于等于6时，树结构还原成链表。因为红黑树的平均查找长度是log(n)，长度为8的时候，平均查找长度为3，如果继续使用链表，平均查找长度为8/2=4，这才有转换为树的必要。链表长度如果是小于等于6，6/2=3，虽然速度也很快的，但是转化为树结构和生成树的时间并不会太短。还有选择6和8，中间有个差值7可以有效防止链表和树频繁转换。假设一下，如果设计成链表个数超过8则链表转换成树结构，链表个数小于8则树结构转换成链表，如果一个HashMap不停的插入、删除元素，链表个数在8左右徘徊，就会频繁的发生树转链表、链表转树，效率会很低。

## 请你说明一下HashMap和ConcurrentHashMap的区别？

HashMap是线程不安全的，put时在多线程情况下会形成环而导致死循环.ConcurrentHashMap是线程安全的采用分段锁机制减少锁的粒度，只锁定ConcurrentHashMap内部类Segment中的一些代码

## 如何保证线程安全？

同一时间只有一个线程能够访问共享资源。解决共享资源的存取冲突问题。在Java中使用synchronized关键字锁定想要保证线程安全访问的程序段（一般不用synchronized关键字锁定方法或者类，因为那样锁的粒度太大了，会影响程序运行效率。）或者使用lock创建一些锁，在代码段执行之前将该代码段锁定以防在运行时由其他线程访问允许需要保证同步的代码段。

## 请你简要说明一下线程的基本状态以及状态之间的关系？

![](https://upload-images.jianshu.io/upload_images/2115013-847fa3c2e24178fe?imageMogr2/auto-orient/strip|imageView2/2/w/757/format/webp)

初始，就绪，运行，阻塞，结束

![1571570936842](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1571570936842.png)

- 线程被创建出来后处于等待状态（new Thread
- 调用线程的.start()方法后进入就绪状态（也就是可以运行的状态
- 线程有等待状态转为运行态是通过CPU来决定的通常没办法让一个线程强制马上运行（即使Java中有设置运行优先级的方法，就算把优先级设置很高，也只是增加了让线程先运行的概率并不能保证一定先运行
- 在运行态的线程可以通过调用yield()方法来使得从运行态返回就绪态等待CPU再次调度（这种操作也称为线程让步
- 在运行态的线程可以通过调用sleep/wait/join方法让线程进入阻塞状态
- 如果是通过sleep方法进入阻塞状态的那么等设定的睡眠时间结束后线程就会自动变为就绪（Runnable）状态
- 如果是通过wait方法进入的阻塞状态那么就需要等待在其他线程中调用notify/notifyAll方法使线程返回就绪状态
- 如果是使用join方法进入的阻塞状态，这种方法一般是为了防止主线程（父线程）优先于子线程运行结束导致子线程没运行完就结束了当join的线程运行完毕后父线程会继续运行
- 有运行状态转为结束状态/死亡状态（Dead）正常情况是线程完整运行结束，如果在运行中出错也会进入死亡状态

## 请你解释一下什么是线程池（thread pool）？

线程池的概念类似人才资源中心，原本每次要运行一个线程（公司找一个员工）都必须去创建（培养）一个员工才行，有了线程池之后就在线程池中准备了多个创建好的线程（培训好的员工）等待被调用

在面向对象编程中，创建和销毁对象都是很费时间的，因为创建一个对象要获取内存资源或其他更多资源。在Java中更加如此，虚拟机将视图跟踪每一个对象，以便能够在对象销毁后进行垃圾回收。所以提高服务程序效率的一个手段就是尽可能减少创建和销毁对象的次数，特别是一些很耗资源的对象的创建和销毁，这就是“池化资源”技术产生的原因。线程池顾名思义就是事先创建若干个可执行的线程放入一个池（容器）中，需要的时候从池中获取线程不用自行创建，使用完毕不需要销毁线程，而是返回池中从而减少创建和销毁对象的开销

Java 5+中的Executor接口定义了一个执行线程的工具。它的子类型即线程池接口ExecutorService。要配置一个线程池是比较复杂的，尤其是在对线程池原理不是很清楚的情况下，因此在工具类Executors里面提供了一些静态工厂方法，胜场一些常用的线程池：

**newSingleThreadExecutor**:创建一个单线程的线程池，这个线程池只有一个线程在工作也就是相当于单线程串行执行所有任务。如果这个微医的线程因为异常而结束，那么会有一个新的线程来代替它。此线程池保证所有任务的的执行顺序按照任务的提交顺序执行。

**示例代码**

```java
/**
 * @author Satsuki
 * @time 2019/6/8 17:21
 * @description:
 */
public class SingleThreadPool {
    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            final int j=i;
            service.execute(()->{
                System.out.println(j+" "+ Thread.currentThread().getName());
                // 即使出错之后也不会停止运行，会创建一个新的线程来继续执行
//                int x = 1/0;
            });
        }
    }
}
```

正常执行

![1571572718589](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1571572718589.png)



若线程池执行任务出现异常可以看到程序并没有停止执行而是创建了新的线程继续执行

![1571572826371](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1571572826371.png)

**newFixedThreadPool**:创建固定大小的线程池。每次提交一个任务就创建一个线程，知道线程达到线程池的最大大小。线程池的大小一旦达到最大值就会保持不变，如果某个线程因为执行一场而结束那么线程池会补充一个新线程。

**示例代码**：

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Satsuki
 * @time 2019/6/8 16:26
 * @description:
 */
public class T05_ThreadPool {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 6; i++) {
            service.execute(()->{
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });

        }
        System.out.println(service);
        System.out.println(service.isTerminated());
        System.out.println(service.isShutdown());
        System.out.println(service);

        TimeUnit.SECONDS.sleep(50);

        System.out.println(service.isTerminated());
        System.out.println(service.isShutdown());
        System.out.println(service);
    }
}
```

正常执行结果如下：

![1571573103761](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1571573103761.png)

可以看到如果任务数量如果超出线程池大小那么不会创建新的线程执行而是随机调用线程池中存在的线程执行任务

**newCachedThreadPool**:创建一个可缓存的线程池。如果线程池大小超过了处理任务所需的线程，那么就会回收部分空闲(60s不执行任务)的线程，当任务书增加时，此线程池又可以智能的添加新线程来处理任务。此线程池不会对线程池大小做限制，线程池大小完全依赖于操作系统（或者说JVM）能够创建的最大线程大小。

**示例代码**：

```java
public class T08_CachedPool {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        System.out.println(service);

        for (int i = 0; i < 2; i++) {
            service.execute(()->{
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }
        System.out.println(service);
        TimeUnit.SECONDS.sleep(8);
        System.out.println(service);
    }
}
```

运行结果

![1571573471055](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1571573471055.png)

**newScheduledThreadPool**:创建一个大小无线的线程池。此线程池支持定时及周期性执行任务的要求

**示例代码**：

```java
public class T10_SchedulePool {
    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
        service.scheduleAtFixedRate(()->{
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        },0,500, TimeUnit.MILLISECONDS);
    }
}
```

运行结果自行观察。

以上部分资源来自牛客网（[https://www.nowcoder.com](https://www.nowcoder.com/)）

## 举例说明同步和异步

同步是阻塞式编程，异步是非阻塞式编程。如果系统中存在临界资源（资源数量少于竟争资源的线程数量的资源），例如正在写的数据以后可能被另一个线程读到。或者正在读的线程已经被另一个线程读过了。同步可以理解为如果在多线程环境下让线程按照一定的顺序执行。异步就是指如果按正常的顺序执行有些操作会花费很长时间。如Android中强制规定了耗时操作不得在UI线程中进行需要使用异步线程来执行耗时操作然后执行完成后可以通知UI线程更新UI。

## 请介绍一下线程同步和线程调度的相关方法。

- wait()：使一个线程处于等待（阻塞）状态，并且释放所持有的对象的锁；
- sleep()：使一个正在运行的线程处于睡眠状态，是一个静态方法，调用此方法要处理InterruptedException异常；
- notify()：唤醒一个处于等待状态的线程，当然在调用此方法的时候，并不能确切的唤醒某一个等待状态的线程，而是由JVM确定唤醒哪个线程，而且与优先级无关；
- notityAll()：唤醒所有处于等待状态的线程，该方法并不是将对象的锁给所有线程，而是让它们竞争，只有获得锁的线程才能进入就绪状态；

通过Lock接口提供了显式的锁机制（explicit lock），增强了灵活性以及对线程的协调。Lock接口中定义了加锁（lock()）和解锁（unlock()）的方法，同时还提供了newCondition()方法来产生用于线程之间通信的Condition对象；此外，Java 5还提供了信号量机制（semaphore），信号量可以用来限制对某个共享资源进行访问的线程的数量。在对资源进行访问之前，线程必须得到信号量的许可（调用Semaphore对象的acquire()方法）；在完成对资源的访问后，线程必须向信号量归还许可（调用Semaphore对象的release()方法）。

## 请问当一个线程进入一个对象的synchronized方法A之后，其它线程是否可进入此对象的synchronized方法B？

不行

测试代码

```java
/**
 * @author Satsuki
 * @time 2019/10/20 20:28
 * @description:
 */
public class TestSyn {
    public static void main(String[] args) {
        TestSyn testSyn = new TestSyn();
        testSyn.A();
        testSyn.B();
    }

    public synchronized void A(){
        try {
            System.out.println("Thread A running...");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread A over");
    }
    public synchronized void B(){
        System.out.println("Thread B running...");
    }
}
```

结果

Thread A running...
Thread A over
Thread B running...

Process finished with exit code 0



通过结果可以看出必须在A方法执行完后才可以执行B方法

原因：synchronized关键字如果加在方法上那么表示锁定的是当前对象。所以在调用方法B时发现对象以及被锁定无法获取锁所以无法执行。

不能。其它线程只能访问该对象的非同步方法，同步方法则不能进入。因为非静态方法上的synchronized修饰符要求执行方法时要获得对象的锁，如果已经进入A方法说明对象锁已经被取走，那么试图进入B方法的线程就只能在等锁池（注意不是等待池哦）中等待对象的锁。

##  请简述一下线程的sleep()方法和yield()方法有什么区别？

sleep方法不会释放锁并且阻塞当前线程直至sleep时间结束

yield释放锁将线程从运行态转为就绪态

①sleep()方法给其他线程运行机会时不考虑线程的优先级，因此会给低优先级的线程以运行的机会；yield()方法只会给相同优先级或更高优先级的线程以运行的机会；

② 线程执行sleep()方法后转入阻塞（blocked）状态，而执行yield()方法后转入就绪（ready）状态；

③ sleep()方法声明抛出InterruptedException，而yield()方法没有声明任何异常；

④ sleep()方法比yield()方法（跟操作系统CPU调度相关）具有更好的可移植性。①sleep()方法给其他线程运行机会时不考虑线程的优先级，因此会给低优先级的线程以运行的机会；yield()方法只会给相同优先级或更高优先级的线程以运行的机会；

② 线程执行sleep()方法后转入阻塞（blocked）状态，而执行yield()方法后转入就绪（ready）状态；

③ sleep()方法声明抛出InterruptedException，而yield()方法没有声明任何异常；

④ sleep()方法比yield()方法（跟操作系统CPU调度相关）具有更好的可移植性。

## 请回答以下几个问题： 第一个 问题：Java中有几种方法可以实现一个线程？ 第二个问题：用什么关键字修饰同步方法?  第三个问题：stop()和suspend()方法为何不推荐使用，请说明原因？

实现Runnable接口或者继承Thread类但是这两种方法都需要重写run方法

synchronized

反对使用stop是因为它不安全。它会解除由线程获取的所有锁定，而且如果对象处于一种不连贯状态，那么其他线程能在那种状态下检测和修改他们。结果很难检查出真正的问题所在。suspend方法容易发生死锁。调用suspend目标线程会暂停下来但是仍然持有锁此时，其他任何线程都不能访问锁定的资源，除非被”挂起”的线程恢复运行。对任何线程来说，如果它们想恢复目标线程，同时又试图使用任何一个锁定的资源，就会造成死锁。所以不应该使用suspend()，而应在自己的Thread类中置入一个标志，指出线程应该活动还是挂起。若标志指出线程应该挂起，便用 wait()命其进入等待状态。若标志指出线程应当恢复，则用一个notify()重新启动线程。

## 同步有几种实现方法

分别是synchronized,wait与notify。lock

CountDownLatch

## 请说出你所知道的线程同步的方法

wait():使一个线程处于等待状态，并且释放所持有的对象的lock。
sleep():使一个正在运行的线程处于睡眠状态，是一个静态方法，调用此方法要捕捉InterruptedException异常。
notify():唤醒一个处于等待状态的线程，注意的是在调用此方法的时候，并不能确切的唤醒某一个等待状态的线程，而是由JVM确定唤醒哪个线程，而且不是按优先级。
notityAll():唤醒所有处入等待状态的线程，注意并不是给所有唤醒线程一个对象的锁，而是让它们竞争。

## 启动一个线程是用run()还是start()?

用start如果用run只是调用run方法而非启动线程

启动一个线程是调用start()方法，使线程所代表的虚拟处理机处于可运行状态，这意味着它可以由JVM调度并执行。这并不意味着线程就会立即运行。run()方法可以产生必须退出的标志来停止一个线程。

## 请使用内部类实现线程设计4个线程，其中两个线程每次对j增加1，另外两个线程对j每次减少1。

```java
public class SynUse {
    private static int j = 0;

    public static void main(String[] args) {
        SynUse synUse = new SynUse();
        Thread thread;
        AddClass addClass = synUse.new AddClass();
        LessClass lessClass = synUse.new LessClass();
        for (int i = 0; i < 2; i++) {
            new Thread(addClass).start();
            new Thread(lessClass).start();
        }

    }

    private synchronized void plus(){
        j++;
        System.out.println(Thread.currentThread().getName()+ " plus" + j);
    }

    private synchronized void less(){
        j--;
        System.out.println(Thread.currentThread().getName()+ " less" + j);
    }

    class AddClass implements Runnable{
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                plus();
            }

        }
    }

    class LessClass implements Runnable{
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                less();
            }
        }
    }
}
```

## 请说明一下线程中的同步和异步有何异同？并且请举例说明在什么情况下会使用到同步和异步？

如果数据将在线程间共享。例如正在写的数据以后可能被另一个线程读到，或者正在读的数据可能已经被另一个线程写过了，那么这些数据就是共享数据，必须进行同步存取。
当应用程序在对象上调用了一个需要花费很长时间来执行的方法，并且不希望让程序等待方法的返回时，就应该使用异步编程，在很多情况下采用异步途径往往更有效率。

## 请说明一下sleep() 和 wait() 有什么区别？

sleep是线程类（Thread）的方法，导致此线程暂停执行指定时间，把执行机会给其他线程，但是监控状态依然保持，到时后会自动恢复。调用sleep不会释放对象锁。
wait是Object类的方法，对此对象调用wait方法导致本线程放弃对象锁，进入等待此对象的等待锁定池，只有针对此对象发出notify方法（或notifyAll）后本线程才进入对象锁定池准备获得对象锁进入运行状态。

## 请你说明一下在监视器(Monitor)内部，是如何做到线程同步的？在程序又应该做哪种级别的同步呢？ 

监视器和锁在Java虚拟机中是一块使用的。监视器监视一块同步代码块，确保一次只有一个线程执行同步代码块。每一个监视器都和一个对象引用相关联。线程在获取锁之前不允许执行同步代码。

## 请分析一下同步方法和同步代码块的区别是什么？

锁的粒度不同，锁代码块效率更爱锁方法效率低

锁的对象不同

同步方法锁定的是当前对象使用this或者当前类class对象作为锁

锁代码块可以自定义锁的对象

## 请详细描述一下线程从创建到死亡的几种状态都有哪些？

1. 新建( new )：新创建了一个线程对象。
2. 可运行( runnable )：线程对象创建后，其他线程(比如 main 线程）调用了该对象 的 start ()方法。该状态的线程位于可运行线程池中，等待被线程调度选中，获 取 cpu 的使用权 。
3. 运行( running )：可运行状态( runnable )的线程获得了 cpu 时间片（ timeslice ） ，执行程序代码。
4. 阻塞( block )：阻塞状态是指线程因为某种原因放弃了 cpu 使用权，也即让出了 cpu timeslice ，暂时停止运行。直到线程进入可运行( runnable )状态，才有 机会再次获得 cpu timeslice 转到运行( running )状态。阻塞的情况分三种：
(一). 等待阻塞：运行( running )的线程执行 o . wait ()方法， JVM 会把该线程放 入等待队列( waitting queue )中。
(二). 同步阻塞：运行( running )的线程在获取对象的同步锁时，若该同步锁 被别的线程占用，则 JVM 会把该线程放入锁池( lock pool )中。
(三). 其他阻塞: 运行( running )的线程执行 Thread . sleep ( long ms )或 t . join ()方法，或者发出了 I / O 请求时， JVM 会把该线程置为阻塞状态。 当 sleep ()状态超时、 join ()等待线程终止或者超时、或者 I / O 处理完毕时，线程重新转入可运行( runnable )状态。
5. 死亡( dead )：线程 run ()、 main () 方法执行结束，或者因异常退出了 run ()方法，则该线程结束生命周期。死亡的线程不可再次复生。

##  创建线程有几种不同的方式？你喜欢哪一种？为什么？

有两种通过实现Runnable接口或者继承Thread类第三种是使用Executors来创建线程池

我喜欢实现Runnable接口的方式

因为extends关键字只能单继承，如果使用extends关键字继承Thread类来创建线程此时就没办法继承其他类



实现Runnable接口这种方式更受欢迎，因为这不需要继承Thread类。在应用设计中已经继承了别的对象的情况下，这需要多继承（而Java不支持多继承），只能实现接口。同时，线程池也是非常高效的，很容易实现和使用。

## 请解释一下Java多线程回调是什么意思？

所谓回调，就是客户程序C调用服务程序S中的某个方法A，然后S又在某个时候反过来调用C中的某个方法B，对于C来说，这个B便叫做回调方法。

线程互相调用其他线程

## 请列举一下启动线程有哪几种方式，之后再说明一下线程池的种类都有哪些？

如果是继承Thread的子类并重写run方法那么就直接创建该子类然后调用.start方法即可

如果是实现了Runnable接口那么需要Thread a = new Thread(实现Runnable接口的方法)

然后a.start()来开启线程

Callable和Future创建线程

implements Callable接口并实现call()方法call方法是可以带返回值的

使用FutureTask封装callable的实现类进行返回值封装获取

测试：

```java
/**
 * @author Satsuki
 * @time 2019/6/8 16:26
 * @description:
 */
public class T03_Callable implements Callable<String>{
//    Callable
//    FutureTask

    public static void main(String[] args) {
        T03_Callable callable = new T03_Callable();
        FutureTask<String> futureTask = new FutureTask<>(callable);
        Thread thread = new Thread(futureTask);
        thread.start();

        try {
            System.out.println("线程启动，等待两秒");
            TimeUnit.SECONDS.sleep(2);
            System.out.println(futureTask.get());
            System.out.println(thread);
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public String call() throws Exception {
        TimeUnit.SECONDS.sleep(1);
        return "call  return";
    }
}
```

结果：

![1571650211087](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1571650211087.png)

线程池的种类有：

Java通过Executors提供四种线程池，分别为：
newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。

## 请简要说明一下JAVA中cyclicbarrier和countdownlatch的区别分别是什么？

**CountDownLatch**

```java
/**
* CountDownLatch
* A synchronization aid that allows one or more threads to wait until
* a set of operations being performed in other threads completes.
* 支持一个或多个线程等待直到其他线程中的一系列操作完成为止的一个同步解决方案
**/
```



解释：其实就是一个计数器可以保证线程之间的顺序执行把线程从并发状态调整为串行状态保证了线程的执行顺序

具体实例：

```java
//    CountDownLatch
//    CyclicBarrier
public class CountAndCyclic {
    CountDownLatch countDownLatch = new CountDownLatch(1);
    public static void main(String[] args) {
        CountAndCyclic countAndCyclic = new CountAndCyclic();
        System.out.println("主线程初始化完成开始执行...");
        new Thread(()->{
            System.out.println("subClassA start working...");
            try {
                TimeUnit.SECONDS.sleep(2);
                // 此线程执行完毕计数-1
                countAndCyclic.countDownLatch.countDown();
                System.out.println("subClassA end working...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            // 阻塞主线程等待子线程执行完毕
            countAndCyclic.countDownLatch.await();
            System.out.println("主线程继续执行.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

//    class subClassA implements Runnable{
//        @Override
//        public void run() {
//
//        }
//    }
}
```

运行结果：

![1571651159683](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1571651159683.png)

**CyclicBarrier**

```java
/**
 * A synchronization aid that allows a set of threads to all wait for
 * each other to reach a common barrier point.  CyclicBarriers are
 * useful in programs involving a fixed sized party of threads that
 * must occasionally wait for each other. The barrier is called
 * <em>cyclic</em> because it can be re-used after the waiting threads
 * are released.
 * 支持一系列线程全部等待直至彼此都到达一个公共的屏障的一个同步辅助工具。
 */
```

示例代码

```java
/**
 * @author Satsuki
 * @time 2019/10/21 17:54
 * @description:
 */
public class CyclicTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CyclicBarrier barrier = new CyclicBarrier(4, new Runnable() {
            @Override
            public void run() {
                System.out.println("好了，大家可以去吃饭了……"  );
            }
        });

        // 尝试替换成CountDownLatch 可以替换为CountDownLatch
        CountDownLatch countDownLatch = new CountDownLatch(4);
        System.out.println("要吃饭，必须所有人都到终点，oK?");
        System.out.println("不放弃不抛弃！");
        for (int i = 0; i < 4; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + ":Go");
                    try {
                        TimeUnit.MILLISECONDS.sleep((long)(2000*Math.random()));
//                        countDownLatch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+ ":我到终点了");
                    try {
//                        countDownLatch.await();
                        barrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName()+ ":终于可以吃饭啦！");

                }
            });
        }
    }


}
```

执行结果：

![1571652967330](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1571652967330.png)

**cyclicbarrier和countdownlatch的区别**

- cyclicbarrier支持重用
- countdownlatch不支持重用
- countdownlatch用于保持多个线程之间的执行顺序
- cyclicbarrier用于一组线程互相等待对方执行一些操作后再一起继续执行
- 用的侧重点不同，但是countdownlatch在某些时候是可以和cyclicbarrier互换的
- 如果要用countdownlatch代替cyclicbarrier那么必须在每个线程的操作完成之后调用countDown方法



- CountDownLatch一般用于某个线程A等待若干个其他线程执行完任务之后，它才执行；

- 而CyclicBarrier一般用于一组线程互相等待至某个状态，然后这一组线程再同时执行；

  上述来自[https://www.nowcoder.com](https://www.nowcoder.com/)牛客网

- **countdownlatch是计数器，线程完成一个就记一个，就像报数，只不过是递减的**

- **而CyclicBarrier更像一个水闸，线程执行就想水流动，但是会在水闸处停止，直至水满（线程都运行完毕）才开始泄流**
  上述两句话的原文链接：https://blog.csdn.net/paul342/article/details/49661991 部分代码也参照了此链接

## 请说明一下线程池有什么优势？

降低资源消耗。通过重复利用已创建的线程降低线程创建和销毁造成的消耗

提高响应速度。当任务到达时，任务可以不需要等待线程创建就能执行

提高线程的可管理性，线程是稀缺资源，如果无限创建，会消耗系统资源，降低系统稳定性。使用线程池统一分配，调优和调度。

## 请回答一下Java中有几种线程池？并且详细描述一下线程池的实现过程

1、newFixedThreadPool创建一个指定工作线程数量的线程池。每当提交一个任务就创建一个工作线程，如果工作线程数量达到线程池初始的最大数，则将提交的任务存入到池队列中。
2、newCachedThreadPool创建一个可缓存的线程池。这种类型的线程池特点是：
1).工作线程的创建数量几乎没有限制(其实也有限制的,数目为Interger. MAX_VALUE), 这样可灵活的往线程池中添加线程。
2).如果长时间没有往线程池中提交任务，即如果工作线程空闲了指定的时间(默认为1分钟)，则该工作线程将自动终止。终止后，如果你又提交了新的任务，则线程池重新创建一个工作线程。
3、newSingleThreadExecutor创建一个单线程化的Executor，即只创建唯一的工作者线程来执行任务，如果这个线程异常结束，会有另一个取代它，保证顺序执行(我觉得这点是它的特色)。单工作线程最大的特点是可保证顺序地执行各个任务，并且在任意给定的时间不会有多个线程是活动的 。
4、newScheduleThreadPool创建一个定长的线程池，而且支持定时的以及周期性的任务执行，类似于Timer。(这种线程池原理暂还没完全了解透彻)

## 请简短说明一下你对AQS的理解。

AQS 全称是 AbstractQueuedSynchronizer，顾名思义，是一个用来构建锁和同步器的框架，它底层用了 CAS 技术来保证操作的原子性，同时利用 FIFO 队列实现线程间的锁竞争，将基础的同步相关抽象细节放在 AQS，这也是 ReentrantLock、CountDownLatch 等同步工具实现同步的底层实现机制。它能够成为实现大部分同步需求的基础，也是 J.U.C 并发包同步的核心基础组件。

AQS是一个可以实现锁的框架

内部实现的关键：FIFO的队列、state状态

定义了内部类ConditionObject

拥有两种线程模式独占模式和共享模式

在LOCK包中的相关锁(常用的有ReentrantLock、 ReadWriteLock)都是基于AQS来构建，一般我们叫AQS为同步器

##  请简述一下线程池的运行流程，使用参数以及方法策略等

线程池主要指定线程池核心线程大小，最大线程数，存储队列，拒绝策略，空闲线程存活时间。当请求任务数大于核心线程数把任务存储到队列中，队列满了就增加线程池创建的线程数，当线程数达到最大开始执行拒绝策略，比如说记录日志，直接丢弃，或者丢弃最老的任务。

## 线程，进程，然后线程创建有很大开销，怎么优化？

使用线程池

## 请介绍一下什么是生产者消费者模式？

生产者和消费者在同一时间共用同一存储空间，生产者向空间生产数据，消费者从空间取走数据

## 请简述一下实现多线程同步的方法？

synchronized lock volatile ThreadLocal

## 如何在线程安全的情况下实现一个计数器？

可以使用加锁，比如synchronized或者lock。也可以使用Concurrent包下的原子类。

## 多线程中的i++线程安全吗？请简述一下原因？

不安全。

i++不是原子性操作。i++分为读取i值，对i值加一，再赋值给i++，执行期中任何一步都是有可能被其他线程抢占的。

##  请你简述一下synchronized与java.util.concurrent.locks.Lock的相同之处和不同之处？

都可以实现线程同步。

lock可以完成synchronized的所有功能

lock有比synchronized更精确的线程语义和更好的性能。synchronized会自动释放锁，而Lock一定要求程序员手动释放，并且必须在finally从句中释放

##  JAVA中如何确保N个线程可以访问N个资源，但同时又不导致死锁？ 

使用多线程的时候，一种非常简单的避免死锁的方式：指定获取锁的顺序，并强制现场按照指定的顺序获取锁。因此，所有线程按照同样的顺序加锁和释放就不会出现死锁。



**产生死锁的四个条件**

- 互斥条件

  一个资源每次只能被一个进程使用，互斥条件是非共享设备所必须的，不仅不能改变还需要加以保证。

- 请求和保持

  一个进程因请求资源而阻塞且对已获得的资源保持不放

- 不可剥夺（不可抢占

  线程已获得的资源在在未使用完之前，不能强行剥夺

- 循环等待

  若干个线程之间形成一种头尾相接的循环等待资源关系

 

**产生死锁的原因**

- 竞争不可抢占性资源
- 竞争可消耗资源
- 线程推进顺序不当

**预防死锁，预先破坏产生死锁的四个条件。互斥不可能破坏，所以有以下三种方法。**

- 破坏请求和保持条件

  解决方案有两种：

  在线程开始之前一次性申请所有资源（必须等待所有请求的资源空闲时才能申请资源）

  在线程开始时只申请运行初期所需的资源便开始运行运行过程中逐步释放自己已分配的且使用完毕的资源例如，一个线程要获取磁盘资源再通过打印机打印，那么初期获得磁盘资源即可，等待从磁盘中取出数据后便释放磁盘资源再申请打印机资源

- 破坏不可抢占条件

  当一个已经保持了某些不可抢占资源的线程提出新的资源请求但不能满足时释放所有保持的资源。但是该方法实现起来比较复杂花费代价太大。

- 破坏循环等待条件

  对系统中所有资源类型进行线性排列并赋予不同序号。每个线程必须按序号增序请求资源。这种方法对资源的利用率比前两种都高，但是前期要为设备指定序号，新设备加入会有一个问题，其次对用户编程也有限制。



线程必须等所有请求的资源都空闲时才能申请资源，

## 请问什么是死锁(deadlock)?

竞争不可抢占资源形成死锁

如果有两个线程T1和T2，要打开两个文件F1和F2，如果等线程T1打开F1和F2并释放后T2打开F1和F2不会出现任何问题。但是如果T1打开了F1此时需要去申请打开F2，此时T2打开了F2要去申请F1这样两个线程就阻塞了他们希望对方关闭自己所需的文件，但谁也无法运行，只能无限等待

竞争可消耗资源形成死锁

以生产者消费者为举例，有一个公共的空间S一个生产者线程PT一个消费者线程CT，如果CT线程先运行，并没有对有没有产品判断不断地像公共空间S申请自己所需要的资源，那么生产者线程就永远无法运行产生死锁

进程推进顺序不当造成死锁

于竞争不可抢占资源类似，但是进程推进顺序不当有可能可以成功运行

## 请说明一下锁和同步的区别。

用法上的不同：
synchronized既可以加在方法上，也可以加载特定代码块上，而lock需要显示地指定起始位置和终止位置。
synchronized是托管给JVM执行的，lock的锁定是通过代码实现的，它有比synchronized更精确的线程语义。
性能上的不同：
lock接口的实现类ReentrantLock，不仅具有和synchronized相同的并发性和内存语义，还多了超时的获取锁、定时锁、等候和中断锁等。
在竞争不是很激烈的情况下，synchronized的性能优于ReentrantLock，竞争激烈的情况下synchronized的性能会下降的非常快，而ReentrantLock则基本不变。
锁机制不同：
synchronized获取锁和释放锁的方式都是在块结构中，当获取多个锁时，必须以相反的顺序释放，并且是自动解锁。而Lock则需要开发人员手动释放，并且必须在finally中释放，否则会引起死锁。

## 请说明一下synchronized的可重入怎么实现。

添加一个计时器记录某个线程对同一资源申请的次数，没申请一次+1而不是判断该资源有没有被申请过。当一个线程退出synchronized代码块时锁的计数器-1直至到0说明该线程完整运行完了可以释放锁。

## 请讲一下非公平锁和公平锁在reetrantlock里的实现过程是怎样的。

如果一个锁是公平的，那么锁的获取顺序就应该符合请求的绝对时间顺序，FIFO。对于非公平锁，只要CAS设置同步状态成功，则表示当前线程获取了锁，而公平锁还需要判断当前节点是否有前驱节点，如果有，则表示有线程比当前线程更早请求获取锁，因此需要等待前驱线程获取并释放锁之后才能继续获取锁。

把对锁申请的线程按照申请时间的现后排成一个队列，按照FIFO的策略进行锁获取，一个线程在获取锁时必须判断前面是不是有其他线程优先于自己申请锁，没有则申请锁有则等待前面的线程申请锁运行并且释放锁。

## 请问JDK和JRE的区别是什么？

JDK Java Development Kit Java开发工具包

JDK包括了JRE，编译器和其他工具可以让Java开发者开发，编译，执行Java程序

 JRE Java Runtime Environment Java运行环境

JDK和JRE都包括了Java虚拟机JVM

## Java中的LongAdder和AtomicLong有什么区别？

JDK1.8引入了LongAdder类。CAS机制就是，在一个死循环内，不断尝试修改目标值，直到修改成功。如果竞争不激烈，那么修改成功的概率就很高，否则，修改失败的的概率就很高，在大量修改失败时，这些原子操作就会进行多次循环尝试，因此性能就会受到影响。 结合ConcurrentHashMap的实现思想，应该可以想到对一种传统AtomicInteger等原子类的改进思路。虽然CAS操作没有锁，但是像减少粒度这种分离热点的思想依然可以使用。将AtomicInteger的内部核心数据value分离成一个数组，每个线程访问时，通过哈希等算法映射到其中一个数字进行计数，而最终的计数结果，则为这个数组的求和累加。热点数据value被分离成多个单元cell，每个cell独自维护内部的值，当前对象的实际值由所有的cell累计合成，这样热点就进行了有效的分离，提高了并行度。

## 请说明一下JAVA中反射的实现过程和作用分别是什么？

Java语言编译后形成.class文件，反射就是通过字节码文件找到一个类，类中的方法及属性等。反射的实现主要通过四个类

Class 类的对象

Constructor 类的构造方法

Field 类的成员函数

Method 类的成员方法

反射机制能够在运行时获取类对类的一些信息进行修改从而使Java拥有了动态特性。

由于反射的特性从而导致了其实private修饰的成员函数并非不可访问。可以通过反射机制获取到并且跳过安全检测并访问

样例代码：

```java
/**
 * @author Satsuki
 * @time 2019/9/12 15:18
 * @description:
 */
public class RUser {
    private int id;
    private int age;
    private String uname;

    public RUser() {
    }

    public RUser(int id, int age, String uname) {
        this.id = id;
        this.age = age;
        this.uname = uname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    @Override
    public String toString() {
        return "RUser{" +
                "id=" + id +
                ", age=" + age +
                ", uname='" + uname + '\'' +
                '}';
    }
}

/**
 * @author Satsuki
 * @time 2019/9/12 15:37
 * @description:
 * 应用反射API动态的操作
 * 类名，属性，方法，构造器等
 */
public class Demo02 {
    public static void main(String[] args) {
        String path = "priv.wzb.javabase.reflection.RUser";
        try {
            Class<RUser> clazz = (Class<RUser>) Class.forName(path);

            //通过反射API调用构造方法，构造对象
            RUser u =  clazz.newInstance();//其实是调用了user的无参构造方法

            //记得重写类的无参构造器因为反射的应用会调用类的无参构造器生成对象
            Constructor<RUser> declaredConstructor = clazz.getDeclaredConstructor(int.class, int.class, String.class);
            RUser u2 = declaredConstructor.newInstance(1001, 17, "satsuki");
            System.out.println(u2.toString());

            //通过反射API调用普通方法
            RUser u3 = clazz.newInstance();
            System.out.println(u.hashCode()==u3.hashCode());
            Method setUname = clazz.getDeclaredMethod("setUname", String.class);
            setUname.invoke(u3,"stk3");
            System.out.println(u3.getUname());

            //通过反射API操作属性
            RUser u4 = clazz.newInstance();
            Field f = clazz.getDeclaredField("uname");
            f.setAccessible(true);//这个属性不用做安全检查直接访问
            f.set(u4,"stk4");
            System.out.println(u4.getUname());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

```

运行结果：

![1571748176663](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\1571748176663.png)

可以看到通过调用setAccessible(true)使得可以直接访问设定private成员函数

# [JVM内存结构 VS Java内存模型 VS Java对象模型](https://www.hollischuang.com/archives/2509)

## JVM内存结构（JVM内存分区

图解

![](https://www.hollischuang.com/wp-content/uploads/2018/06/QQ20180624-150918.png)

Java代码运行在JVM虚拟机上，虚拟机在执行Java程序的过程中把所管理的内存划分为上述的不同数据区域，每个区域拥有自己的功能。

1、以上是Java虚拟机规范，不同的虚拟机实现会各有不同，但是一般会遵守规范。

2、规范中定义的方法区，只是一种概念上的区域，并说明了其应该具有什么功能。但是并没有规定这个区域到底应该处于何处。所以，对于不同的虚拟机实现来说，是由一定的自由度的。

3、不同版本的方法区所处位置不同，上图中划分的是逻辑区域，并不是绝对意义上的物理区域。因为某些版本的JDK中方法区其实是在堆中实现的。

4、运行时常量池用于存放编译期生成的各种字面量和符号应用。但是，Java语言并不要求常量只有在编译期才能产生。比如在运行期，String.intern也会把新的常量放入池中。

5、除了以上介绍的JVM运行时内存外，还有一块内存区域可供使用，那就是直接内存。Java虚拟机规范并没有定义这块内存区域，所以他并不由JVM管理，是利用本地方法库直接在堆外申请的内存区域。

6、堆和栈的数据划分也不是绝对的，如HotSpot的JIT会针对对象分配做相应的优化。

如上，做个总结，JVM内存结构，由Java虚拟机规范定义。描述的是Java程序执行过程中，由JVM管理的不同数据区域。各个区域有其特定的功能。

## Java内存模型（Java Memory Model(JMM))

Java内存模型和JVM内存结构很像，但是JVM内存结构虽然可能具体实现有所不同，但是确实是真实存在的。

而Java内存模型则是为了方便程序员编程和理解抽象而出的概念，讲述了多线程的运行情况如何处理共享资源线程运行模型或者说这是一个规范，定义了一个线程对共享变量的写入时对另一个线程是可见的

图解

![](https://www.hollischuang.com/wp-content/uploads/2018/06/11.png)

## Java对象模型

Java是面向对象的语言，Java对象模型是一个Java对象如何存储在jvm虚拟机中的描述

图解

![](https://www.hollischuang.com/wp-content/uploads/2018/06/20170615230126453.jpeg)

如上图所示

primitive基本类型和引用（指针、句柄)存储在栈中

基本数据类型的存储到此为止，但是对象的具体数据则是存储在堆中的，堆中存储的是对象实例。在对象实例中包含一个元数据指针，该指针指向了方法区。在方法区保存了类信息

参考:<https://www.hollischuang.com/archives/2509> 欢迎大家阅读原文

## 请简单描述一下JVM加载class文件的原理是什么?

JVM中类装载是有ClassLoader和它的子类来实现的，它负责把class文件从磁盘中读取到内存中。

隐式装载：new等方式生成对象时，隐式调用类装载器加载对应的类到JVM中

显式装载：使用class.forName()等方法，显示装载所需的类。两者本质是一样的

Java类加载并不会一次性的把类的所有信息都从磁盘中读取到内存，而是刚开始只读取保证程序运行的基础类完全加载到JVM中，其他类什么时候用到什么时候再加载

## 什么是Java虚拟机？为什么Java被称作是“平台无关的编程语言”？

用于屏蔽操作系统的不同将本地方法进行封装，Java的一次编译到处运行的特性就是借由JVM来实现的，Java程序员只需要编写Java代码即可不需要关心操作系统是什么，Java代码被编译后在JVM虚拟机上解释运行所以只要配置了Java运行环境Java应用程序就可以在任何平台上运行

Java虚拟机是一个可以执行Java字节码的虚拟机进程。Java源文件被编译成能被Java虚拟机执行的字节码文件。
Java被设计成允许应用程序可以运行在任意的平台，而不需要程序员为每一个平台单独重写或者是重新编译。Java虚拟机让这个变为可能，因为它知道底层硬件平台的指令长度和其他特性。

##  jvm最大内存限制多少？

堆内存分配

-Xms 配置最小堆内存默认是物理内存的1/64 -Xmx配置最大堆内存默认是物理内存的1/4，空余堆小于40%时开始扩大堆内存直至最大内存，空闲空间大于70%时JVM会减少堆内存直到最小堆内存因此服务器一般设置-Xms、 -Xmx相等以避免在每次GC后调整堆的大小。

非堆内存分配

-XX:PermSize设置非堆内存的初始值，若不指定默认是堆内存的1/4

JVM最大内存

首先JVM内存限制于实际的最大物理内存，假设物理内存无限大的话，JVM内存的最大值跟操作系统有很大的关系。简单的说就32位处理器虽 然可控内存空间有4GB,但是具体的操作系统会给一个限制，这个限制一般是2GB-3GB（一般来说Windows系统下为1.5G-2G，Linux系 统下为2G-3G），而64bit以上的处理器就不会有限制了。

![](https://uploadfiles.nowcoder.com/images/20180926/308572_1537966762062_914FBB519FF1ACBAF602B2DCBD5184D6)

## jvm是如何实现线程的？

线程是比进程更轻量级的调度执行单位。线程可以把一个进程的资源分配和执行调度分开。一个进程可以启动多个线程，线程共享进程的资源（内存地址，文件IO）。线程是CPU调度的基本单位。

Thread类的关键方法都是native（最起码sleep与start都是native，这些native方法无法或没有使用平台无关的手段实现，也可能是为了执行效率。

实现线程的方式

- 使用内核线程

  内核线程（Kernel-Level Thread,KLT)就是直接由操作系统内核支持的线程

  内核完成线程切换 调度 并且将任务映射到各个CPU上

  用户一般不直接使用内核线程，而是使用内核线程的高级接口--轻量级线程，

  各种线程操作比如创建、析构、同步需要在用户态和内核态之间切换代价很高

- 使用用户线程实现

  一个线程只要不是内核线程就是用户线程，严格意义上轻量级进程也是用户线程但轻量级进程很多操作建立在内核之上效率会受限。狭义的用户线程是完全建立在用户空间线程库上系统内核不能感知线程库的实现。线程的建立，操作（同步，阻塞，唤醒，睡眠，等待等），销毁，调度完全在用户态中完成。但是使用用户线程实现的程序十分复杂（已放弃

- 使用用户现场加轻量级进程混合实现

  既存在轻量级进程又存在用户线程，线程的建立，操作，销毁等还是在用户态，轻量级进程作为用户线程和内核线程的桥梁

## 请列举一下，在JAVA虚拟机中，哪些对象可作为ROOT对象？

栈中的引用对象

方法区中静态属性引用对象

方法区中常量引用对象

本地方法栈中JNI引用对象

## GC中如何判断对象是否需要被回收？

判断对象存在几个引用（可不可达）。即使被分析为不可达也不会马上回收而是标记，在两次或多次回收都发现不可达才进行回收

## 请说明一下JAVA虚拟机的作用是什么?

屏蔽平台底层实现抽象出固定的实现方案，是Java跨平台特性的保证。jvm将Java字节码解释为具体平台的具体指令执行。

## 请说明一下eden区和survival区的含义以及工作原理？

这两个区域都在堆区是堆区的更详细划分eden代表了线程刚刚创建出来所在的区域经过垃圾回收(GC)之后若对象仍然存在引用（线程可达)那么对象就不会回收掉，到达了一定回收次数后会进入survival区域survival区域分为from和to。堆空间的比例: 8:1:1.

## 请简单描述一下JVM分区都有哪些？

栈（pc 本地方法栈 虚拟机栈（通常Java栈指虚拟机栈））这里面三个都是分散的

堆（存放对象实例 Eden survivor)

方法区（存放类信息）

## 请简单描述一下类的加载过程

![](https://uploadfiles.nowcoder.com/images/20180926/308572_1537962641528_95106A90F455887E4A4B298735A4641B)

- 加载

  在内存中生成一个代表这个类的java.lang.Class对象，作为这个类的方法和各种数据的入口

- 验证

  确保Class文件的字节流包含的信息符合虚拟机需求

- 准备

  设置初值

- 解析

  将常量池中的符号引用替换为直接引用

- 初始化

  执行类构造器client方法

- 使用

- 卸载

## 请简单说明一下JVM的回收算法以及它的回收器是什么？还有CMS采用哪种回收算法？使用CMS怎样解决内存碎片的问题呢？

- 标记清除

  标记未被引用的垃圾对象，再清除所有对象但是会带来大量内存碎片

- 标记复制

  将内存空间分为两块，每次只使用其中一块，在垃圾回收时将正在使用的内存中的存活对象复制到未被使用的内存块，然后将原先内存块的对象全部清楚交换两个内存的对象，完成垃圾回收

- 标记整理

  标记复制是建立在垃圾对象多存活对象少的前提下。使用成本较高。标记整理会将要清楚的对象全部标记，并且在清理后进行内存空间整理减少碎片

- 增量算法

  如果一次性将所有的垃圾进行处理，需要造成系统长时间的停顿，那么就可以让垃圾收集线程和应用程序线程交替执行

CMS收集器

CMS(Concurrent Mark Swep)收集器是一个比较重要的回收器，现在应用非常广泛，我们重点来看一下，CMS一种获取最短回收停顿时间为目标的收集器，这使得它很适合用于和用户交互的业务。从名字(Mark Swep)就可以看出，CMS收集器是基于标记清除算法实现的。它的收集过程分为四个步骤：

初始标记(initial mark)

并发标记(concurrent mark)

重新标记(remark)

并发清除(concurrent sweep)

注意初始标记和重新标记还是会stop the world，但是在耗费时间更长的并发标记和并发清除两个阶段都可以和用户进程同时工作。

## 请简单描述一下垃圾回收器的基本原理是什么？还有垃圾回收器可以马上回收内存吗？并且有什么办法可以主动通知虚拟机进行垃圾回收呢？

程序员创建对象GC开始监控对象的地址，大小，使用情况，当对象不再被引用（不可达）在垃圾回收时进行内存回收，并不会马上回收内存，由System.gc()方法通知虚拟机进行垃圾回收，但是这也只是提醒虚拟机不一定马上执行回收

## 请问，在java中会存在内存泄漏吗？请简单描述一下。

存在

- 不再需要的对象被引用

  一个长久存在的对象不被使用

- 未释放系统资源

## 请说明一下垃圾回收的优点以及原理。

程序员不需要关注系统的内存分配情况交给JVM即可可以简化编程更专注于业务开发。回收算法有标记清除、标记复制、标记整理等。

## 请问GC是什么? 还有为什么要有GC?

GC是垃圾收集的意思（Gabage Collection）,内存处理是编程人员容易出现问题的地方，忘记或者错误的内存回收会导致程序或系统的不稳定甚至崩溃，Java提供的GC功能可以自动监测对象是否超过作用域从而达到自动回收内存的目的，Java语言没有提供释放已分配内存的显示操作方法。

## 请简述一下GC算法

①GC（GarbageCollection 垃圾收集），GC的对象是堆空间和永久区

②GC算法包含：引用计数法，标记清除，标记压缩，复制算法。

③引用计数器的实现很简单，对于一个对象A，只要有任何一个对象引用了A，则A的引用计数器就加1，当引用失效时，引用计数器就减1。只要对象A的引用计数器的值为0，则对象A就不可能再被使用。

④标记-清除算法是现代垃圾回收算法的思想基础。标记-清除算法将垃圾回收分为两个阶段：标记阶段和清除阶段。一种可行的实现是，在标记阶段，首先通过根节点，标记所有从根节点开始的可达对象。因此，未被标记的对象就是未被引用的垃圾对象。然后，在清除阶段，清除所有未被标记的对象。与标记-清除算法相比，复制算法是一种相对高效的回收方法不适用于存活对象较多的场合如老年代将原有的内存空间分为两块，每次只使用其中一块，在垃圾回收时，将正在使用的内存中的存活对象复制到未使用的内存块中，之后，清除正在使用的内存块中的所有对象，交换两个内存的角色，完成垃圾回收。



## 什么原因会导致minor gc运行频繁？同样的，什么原因又会导致minor gc运行很慢？请简要说明一下

堆内存太小

## 请问java中内存泄漏是什么意思？什么场景下会出现内存泄漏的情况？

Java中的内存泄露，广义并通俗的说，就是：不再会被使用的对象的内存不能被回收，就是内存泄露。如果长生命周期的对象持有短生命周期的引用，就很可能会出现内存泄露。

## 请问运行时异常与受检异常有什么区别？

java中Throwable是所有异常与错误的超类（super class）,直接继承这个类的子类有Exception和Error。

Error是合理的引用程序不应该捕获的严重问题。

运行时异常和受检异常都属于Exception

运行时异常是指在运行期间会出现的异常一般交给java自动处理也可以抛出这些异常

它是uncheckedExcepiton。

Java.lang.ArithmeticException

Java.lang.ArrayStoreExcetpion

Java.lang.ClassCastException

Java.lang.EnumConstantNotPresentException

Java.lang.IllegalArgumentException

Java.lang.IllegalThreadStateException

Java.lang.NumberFormatException

Java.lang.IllegalMonitorStateException

Java.lang.IllegalStateException

Java.lang.IndexOutOfBoundsException

Java.lang.ArrayIndexOutOfBoundsException

Java.lang.StringIndexOutOfBoundsException

Java.lang.NegativeArraySizeException’

Java.lang.NullPointerException

Java.lang.SecurityException

Java.lang.TypeNotPresentException

Java.lang.UnsupprotedOperationException
————————————————
版权声明：本文为CSDN博主「nju.拈花」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/nlznlz/article/details/53271045

受检异常是指一些不得不处理（捕获，声明抛出）的可能出现的异常如果不处理那么程序将无法运行

Java.lang.ClassNotFoundException

Java.lang.CloneNotSupportedException

Java.lang.IllegalAccessException

Java.lang.InterruptedException

Java.lang.NoSuchFieldException

Java.lang.NoSuchMetodException
————————————————
版权声明：本文为CSDN博主「nju.拈花」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/nlznlz/article/details/53271045

异常表示程序运行过程中可能出现的非正常状态，运行时异常表示虚拟机的通常操作中可能出现的异常，是一种常见的运行错误，只要程序设计的没有问题通常就不会发生。受检异常根程序运行的上下文环境有关，即使程序设计无误，仍然可能因使用的问题而引发。Java编译器要求方法必须声明抛出可能发生的受检异常，但是并不要求必须声明抛出未捕获的运行时异常。

异常和继承一样，是面向对象程序设计中经常被滥用的东西，在Effective Java中对异常的使用给出了以下指导原则：
- 不要将异常处理用于正常的控制流（设计良好的API不应该强迫它的调用者为了正常的控制流而使用异常）
- 对可以恢复的情况使用受检异常，对编程错误使用运行时异常
- 避免不必要的使用受检异常（可以通过一些状态检测手段来避免异常的发生）
- 优先使用标准的异常
- 每个方法抛出的异常都要有文档
- 保持异常的原子性
- 不要在catch中忽略掉捕获到的异常

##  请问什么是java序列化？以及如何实现java序列化？

Java序列化是指将现有的对象存储到硬盘或其他介质中，序列化的对象可以用于网络传输，并且在传输过去之后可以进行反序列化得到同一个对象。所以一般要进行持久化或网络传输的对象都需要进行序列化，序列化的常用方法就是实现Serializable接口。该接口并没有需要实现的方法，实现这个接口只是标注该类可以被序列化。

序列化是一种用来处理对象流的机制，所谓对象流也就是将对象内容进行流化。可以对流化后的对象进行读写操作。可将硫化后的对象传输于网络之间。

## 请问java中有几种类型的流？JDK为每种类型的流提供了一些抽象类以供继承，请说出他们分别是哪些类？

文件流，二进制流，对象流。（字节流

按照大类来分可以分为字节流和字符流。字节流继承于InputSteam OutPutStream 字符流继承于reader和writer

java.io包中还有许多其他的流，主要是为了提高性能和使用方便。

## 请说明一下Java中的异常处理机制的原理以及如何应用。

当Java程序违反了Java的语义规则，Java虚拟机就会将发生的错误表示为一个异常。违反语义规则有两种情况：

- Java类库内置的语义检查

  数组下标越界引发IndexOutOfBoundsException,访问null的类型抛出NullPointerException.

- Java运行程序员扩展语义检查

  程序员扩展于一检查创建自己的异常使用throw关键字抛出异常。

## 请问你平时最常见到的runtime exception是什么？

ArithmeticException,

ArrayStoreException,

BufferOverflowException,

BufferUnderflowException,

CannotRedoException,
CannotUndoException,

ClassCastException,

CMMException,

ConcurrentModificationException,

DOMException,
EmptyStackException,

IllegalArgumentException,

IllegalMonitorStateException,

IllegalPathStateException,
IllegalStateException,

ImagingOpException,

IndexOutOfBoundsException,

MissingResourceException,
NegativeArraySizeException,

NoSuchElementException,

NullPointerException,

ProfileDataException,

ProviderException,
RasterFormatException, SecurityException, SystemException, UndeclaredThrowableException, UnmodifiableSetException,
UnsupportedOperationException

## 请问error和exception有什么区别?

exception可能不用处理

error不处理程序就无法运行

error 表示恢复不是不可能但很困难的情况下的一种严重问题。比如说内存溢出。不可能指望程序能处理这样的情况。
exception 表示一种设计或实现问题。也就是说，它表示如果程序运行正常，从不会发生的情况。

## 请问运行时的异常与一般情况下出现的异常有什么相同点和不同点？

异常表示程序运行过程中可能出现的非正常状态，运行时异常表示虚拟机的通常操作中可能遇到的异常，是一种常见运行错误。java编译器要求方法必须声明抛出可能发生的非运行时异常，但是并不要求必须声明抛出未被捕获的运行时异常。

##  请问如何打印日志？

cat /var/log/*.log

如果日志在更新，如何实时查看tail -f /var/log/messages

还可以使用watch -d -n 1 cat /var/log/messages

-d表示高亮不同的地方，-n表示多少秒刷新一次。

