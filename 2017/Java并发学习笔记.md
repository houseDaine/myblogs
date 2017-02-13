## Java内存模型

Java 语言的内存模型由一些规则组成，这些规则确定线程对内存的访问如何排序以及何时可以确保它们对线程是可见的。比如 Java 内存模型的重排序，内存可见性和 happens-before 关系。

Java线程之间的通信由Java内存模型（本文简称为JMM）控制，JMM决定一个线程对共享变量的写入何时对另一个线程可见。从抽象的角度来看，JMM定义了线程和主内存之间的抽象关系：线程之间的共享变量存储在主内存（main memory）中，每个线程都有一个私有的本地内存（local memory），本地内存中存储了该线程以读/写共享变量的副本。本地内存是JMM的一个抽象概念，并不真实存在。它涵盖了缓存，写缓冲区，寄存器以及其他的硬件和编译器优化。Java内存模型的抽象示意图如下：

![Java内存模型(JMM)](images/jmm.png)

从上图来看，线程A与线程B之间如要通信的话，必须要经历下面2个步骤：

1. 首先，线程A把本地内存A中更新过的共享变量刷新到主内存中去。
2. 然后，线程B到主内存中去读取线程A之前已更新过的共享变量。





### 重排序

内存模型描述了程序的可能行为。具体的编译器实现可以产生任意它喜欢的代码 – 只要所有执行这些代码产生的结果，能够和内存模型预测的结果保持一致。这为编译器实现者提供了很大的自由，包括操作的重排序。

重排序类型包括：

- 编译器优化的重排序。编译器在不改变单线程程序语义的前提下，可以重新安排语句的执行顺序。
- 指令级并行的重排序。现代处理器采用了指令级并行技术（Instruction-Level Parallelism， ILP）来将多条指令重叠执行。如果不存在数据依赖性，处理器可以改变语句对应机器指令的执行顺序。
- 内存系统的重排序。由于处理器使用缓存和读/写缓冲区，这使得加载和存储操作看上去可能是在乱序执行。




从java源代码到最终实际执行的指令序列，会分别经历下面三种重排序：

源代码->1.编译器优化的重排序--> 2.指令级并行的重排序-->3.内存系统的重排序-->最终执行的指令序列




### 内存可见性

在现代可共享内存的多处理器体系结构中每个处理器都有自己的缓存，并周期性的与主内存协调一致。假设线程 A 写入一个变量值 V，随后另一个线程 B 读取变量 V 的值，在下列情况下，线程 B 读取的值可能不是线程 A 写入的最新值：

- 执行线程 A 的处理器把变量 V 缓存到寄存器中。
- 执行线程 A 的处理器把变量 V 缓存到自己的缓存中，但还没有同步刷新到主内存中去。
- 执行线程 B 的处理器的缓存中有变量 V 的旧值。


### Happens-before 关系

从JDK5开始，java使用新的JSR -133内存模型（本文除非特别说明，针对的都是JSR- 133内存模型）。JSR-133提出了happens-before的概念，通过这个概念来阐述操作之间的内存可见性。如果一个操作执行的结果需要对另一个操作可见，那么这两个操作之间必须存在happens-before关系。

happens-before 关系保证：如果线程 A 与线程 B 满足 happens-before 关系，则线程 A 执行动作的结果对于线程 B 是可见的。如果两个操作未按 happens-before 排序，JVM 将可以对他们任意重排序。

happens-before关系法则：

- 程序次序法则：如果在程序中，所有动作 A 出现在动作 B 之前，则线程中的每动作 A 都 happens-before 于该线程中的每一个动作 B。
- 监视器锁法则：对一个监视器的解锁 happens-before 于每个后续对同一监视器的加锁。
- Volatile 变量法则：对 Volatile 域的写入操作 happens-before 于每个后续对同一 Volatile 的读操作。
- 传递性：如果 A happens-before 于 B，且 B happens-before C，则 A happens-before C。



###  非阻塞算法

一个线程的失败和挂起不会引起其他些线程的失败和挂起，这样的算法称为非阻塞算法。非阻塞算法通过使用底层机器级别的原子指令来取代锁，从而保证数据在并发访问下的一致性。与锁相比，非阻塞算法在更细粒度（机器级别的原子指令）的层面协调多线程间的竞争。它使得多个线程在竞争相同资源时不会发生阻塞，它的并发性与锁相比有了质的提高；同时也大大减少了线程调度的开销。同时，由于几乎所有的同步原语都只能对单个变量进行操作，这个限制导致非阻塞算法的设计和实现非常复杂。 见`ConcurrentLinkedQueue`。



### 并发编程模型的分类

在并发编程中，我们需要处理两个关键问题：线程之间如何通信及线程之间如何同步。

通信是指线程之间以何种机制来交换信息，同步是指程序用于控制不同线程之间操作发生相对顺序的机制。

线程之间的通信机制有两种：共享内存（Java采用的是共享内存模型）和消息传递。

在共享内存的并发模型里，线程之间共享程序的公共状态，线程之间通过写-读内存中的公共状态来隐式进行通信。在消息传递的并发模型里，线程之间没有公共状态，线程之间必须通过明确的发送消息来显式进行通信。





## LinkedBlockingQueue、PriorityBlockingQueue、ConcurrentLinkedQueue三者区别

- LinkedBlockingQueue和PriorityBlockingQueue是阻塞的，而ConcurrentLinkedQueue是非阻塞的，

- LinkedBlockingQueue和PriorityBlockingQueue通过加锁实现线程安全，而ConcurrentLinkedQueue使用CAS实现无锁模式

- PriorityBlockingQueue支持优先级

  ​

  上三者的使用场景也不同：


- LinkedBlockingQueue适合需要阻塞的队列场景，如果能不阻塞或者可以通过代码自行实现阻塞，那么建议使用ConcurrentLinkedQueue代替
- ConcurrentLinkedQueue适合对性能要求较高，同时无需阻塞的场景使用
- PriorityBlockingQueue适合需要根据任务的不同优先级进行调整队列的顺序的场景




---

## 二、Java.util.concurrent包源码阅读

### 源码包的结构

大致五部分：

- Aomic数据类型：这部分都被放在java.util.concurrent.atomic这个包里面，实现了原子化操作的数据类型，包括 Boolean,Integer, Long, 和Referrence这四种类型以及这四种类型的数组类型。
- 锁：这部分都被放在java.util.concurrent.lock这个包里面，实现了并发操作中的几种类型的锁
- java集合框架中的一些数据结构的并发实现：这部分实现的数据结构主要有List, Queue和Map。
- 多线程任务执行，这部分大体上涉及到三个概念：
  - Callable ：被执行的任务
  - Executor ：执行任务
  - Future：异步提交任务的返回数据
- 线程管理类：这部分主要是对线程集合的管理的实现，有CyclicBarrier, CountDownLatch,Exchanger等一些类



### java.util.concurrent.atomic包

Aomic数据类型有四种类型：AomicBoolean, AomicInteger, AomicLong, 和AomicReferrence(针对Object的)以及它们的数组类型，还有一个特殊的AomicStampedReferrence,它不是AomicReferrence的子类，而是利用AomicReferrence实现的一个储存引用和Integer组的扩展类。

所有原子操作都是依赖于sun.misc.Unsafe这个类，这个类底层是由C++实现的，利用指针来实现数据操作。



**关于数组**：没有Boolean的Array，可以用Integer代替，底层实现完全一致，毕竟AtomicBoolean底层就是用Integer实现



### CAS：

Compare and Swap（比较并交换）： 一种无锁机制，比较并交换, 操作包含三个操作数 —— 内存位置（V）、预期原值（A）和新值(B)，CAS有3个操作数，内存值V，旧的预期值A，要修改的新值B。当且仅当预期值A和内存值V相同时，将内存值V修改为B，否则什么都不做。

好处：操作系统级别的支持，效率更高，无锁机制，降低线程的等待，实际上是把这个任务丢给了操作系统来做。

 这个理论是整个java.util.concurrent包的基础。

http://blog.csdn.net/hsuxu/article/details/9467651



>  Lock包：

### Condition接口

Condition的作用类似Objet类的监视器方法(wait、notify、nofityAll)。Object类的wait、notify 和 notifyAll三个方法必须在同步代码块(synchronized)中调用，但是在java并发包中，使用了ReentrantLock替代了synchronized关键字，因此无法直接调用wait、notify 和 notifyAll。因此，为了实现这个功能，必须要有另外一种替代的机制，这就是Condition的作用。

Condition接口的方法与Object的监视器主要方法对比：

| **Condition**                   | **Object**         | **作用**                              |
| ------------------------------- | ------------------ | ----------------------------------- |
| await()                         | wait()             | 造成当前线程在接到信号或被中断之前一直处于等待状态。          |
| await(long time, TimeUnit unit) | wait(long timeout) | 造成当前线程在接到信号、被中断或到达指定等待时间之前一直处于等待状态。 |
| signal()                        | notify()           | 唤醒一个等待线程。                           |
| signalAll()                     | notifyAll()        | 唤醒所有等待线程。                           |

类似于wait、notify 和 notifyAll必须在同步代码块中使用，Condition对象的方法也必须要写在Lock.lock与Lock.unLock()代码之间(Object中的wait、notify、notifyAll方法是和 同步锁（synchronized关键字）捆绑使用的；而Condition是需要与["互斥锁"/"共享锁"]捆绑使用的。)：


```java
ReentrantLock lock=new ReentrantLock();
Condition condition = lock.newCondition();
lock.lock();
  //获取到锁之后才能调用以下方法
  condition.await();
  condition.signal();
  condition.signalAll();
lock.unlock();
```



官网API的示例`BoundedBuffer`：

```java
public class BoundedBuffer {
    final Lock lock = new ReentrantLock();//锁对象
    final Condition notFull = lock.newCondition();//写线程条件
    final Condition notEmpty = lock.newCondition();//读线程条件

    final Object[] items = new Object[100];//缓存队列
    int putptr,/*写索引*/ takeptr,/*读索引*/ count;/*队列中存在的数据个数*/

    public void put(Object x) throws InterruptedException {
        //获取锁
        lock.lock();
        try {
            // 如果“缓冲已满”，则等待；直到“缓冲”不是满的，才将x添加到缓冲中。
            while (count == items.length)
                notFull.await();
            // 将x添加到缓冲中
            items[putptr] = x;
            // 将“put统计数putptr+1”；如果“缓冲已满”，则设putptr为0。
            if (++putptr == items.length) putptr = 0;
            // 将“缓冲”数量+1
            ++count;
            // 唤醒take线程，因为take线程通过notEmpty.await()等待
            notEmpty.signal();

            // 打印写入的数据
            System.out.println(Thread.currentThread().getName() + " put  " + (Integer) x);
        } finally {
            lock.unlock();    // 释放锁
        }
    }

    public Object take() throws InterruptedException {
        //获取锁
        lock.lock();   
        try {
            // 如果“缓冲为空”，则等待；直到“缓冲”不为空，才将x从缓冲中取出。
            while (count == 0)
                notEmpty.await();
            // 将x从缓冲中取出
            Object x = items[takeptr];
            // 将“take统计数takeptr+1”；如果“缓冲为空”，则设takeptr为0。
            if (++takeptr == items.length) takeptr = 0;
            // 将“缓冲”数量-1
            --count;
            // 唤醒put线程，因为put线程通过notFull.await()等待
            notFull.signal();
            // 打印取出的数据
            System.out.println(Thread.currentThread().getName() + " take " + (Integer) x);
            return x;
        } finally {
            lock.unlock();    // 释放锁
        }
    }
}
```

示例说明：

这是一个处于多线程工作环境下的缓存区，缓存区提供了两个方法，put和take，put是存数据，take是取数据，内部有个缓存队列。

这个缓存区类实现的功能：有多个线程往里面存数据和从里面取数据，其缓存队列(先进先出后进后出)能缓存的最大数值是100，多个线程间是互斥的，当缓存队列中存储的值达到100时，将写线程阻塞，并唤醒读线程，当缓存队列中存储的值为0时，将读线程阻塞，并唤醒写线程，这也是ArrayBlockingQueue的内部实现。

下面分析一下代码的执行过程：

1. 一个写线程执行，调用put方法；
2. 判断count是否为100，显然没有100
3. 继续执行，存入值；      
4. 判断当前写入的索引位置++后，是否和100相等，相等将写入索引值变为0，并将count+1；        
5. 仅唤醒**读线程阻塞队列**中的一个；       
6. 一个读线程执行，调用take方法；      
7. ……       
8. 仅唤醒**写线程阻塞队列**中的一个



Condition强大的地方在于：能够更加精细的控制多线程的休眠与唤醒。对于同一个锁，我们可以创建多个Condition，在不同的情况下使用不同的Condition。

这就是多个Condition的强大之处，假设缓存队列中已经存满，那么阻塞的肯定是写线程，唤醒的肯定是读线程，相反，阻塞的肯定是读线程，唤醒的肯定是写线程，那么假设只有一个Condition会有什么效果呢，缓存队列中已经存满，这个Lock不知道唤醒的是读线程还是写线程了，如果唤醒的是读线程，皆大欢喜，如果唤醒的是写线程，那么线程刚被唤醒，又被阻塞了，这时又去唤醒，这样就浪费了很多时间。如果采用Object类中的wait(), notify(), notifyAll()实现该缓冲区，当向缓冲区写入数据之后需要唤醒"读线程"时，不可能通过notify()或notifyAll()明确的指定唤醒"读线程"，而只能通过notifyAll唤醒所有线程(但是notifyAll无法区分唤醒的线程是读线程，还是写线程)。  但是，通过Condition，就能明确的指定唤醒读线程。



### LockSupport

工具类，操作对象是线程，基于Unsafe类实现。基本操作park和unpark:

park会把使得当前线程失效,暂时挂起，直到出现以下几种情况中的一种：

- 其他线程调用unpark方法操作该线程   
- 该线程被中断    
- park方法立刻返回





### **AbstractQueuedSynchronizer**（重点）

站在使用者的角度，AQS的功能可以分为两类：独占功能和共享功能，它的所有子类中，要么实现并使用了它独占功能的API，要么使用了共享锁的功能，而不会同时使用两套API，即便是它最有名的子类ReentrantReadWriteLock，也是通过两个内部类：读锁和写锁，分别实现的两套API来实现的。



http://www.infoq.com/cn/articles/jdk1.8-abstractqueuedsynchronizer