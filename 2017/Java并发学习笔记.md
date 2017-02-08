## Java内存模型

Java 语言的内存模型由一些规则组成，这些规则确定线程对内存的访问如何排序以及何时可以确保它们对线程是可见的。比如 Java 内存模型的重排序，内存可见性和 happens-before 关系。



### 重排序

内存模型描述了程序的可能行为。具体的编译器实现可以产生任意它喜欢的代码 – 只要所有执行这些代码产生的结果，能够和内存模型预测的结果保持一致。这为编译器实现者提供了很大的自由，包括操作的重排序。

重排序类型包括：

- 编译器生成指令的次序，可以不同于源代码所暗示的“显然”版本。
- 处理器可以乱序或者并行的执行指令。
- 缓存会改变写入提交到主内存的变量的次序。



### 内存可见性

在现代可共享内存的多处理器体系结构中每个处理器都有自己的缓存，并周期性的与主内存协调一致。假设线程 A 写入一个变量值 V，随后另一个线程 B 读取变量 V 的值，在下列情况下，线程 B 读取的值可能不是线程 A 写入的最新值：

- 执行线程 A 的处理器把变量 V 缓存到寄存器中。
- 执行线程 A 的处理器把变量 V 缓存到自己的缓存中，但还没有同步刷新到主内存中去。
- 执行线程 B 的处理器的缓存中有变量 V 的旧值。



### Happens-before 关系

happens-before 关系保证：如果线程 A 与线程 B 满足 happens-before 关系，则线程 A 执行动作的结果对于线程 B 是可见的。如果两个操作未按 happens-before 排序，JVM 将可以对他们任意重排序。

happens-before关系法则：

- 程序次序法则：如果在程序中，所有动作 A 出现在动作 B 之前，则线程中的每动作 A 都 happens-before 于该线程中的每一个动作 B。
- 监视器锁法则：对一个监视器的解锁 happens-before 于每个后续对同一监视器的加锁。
- Volatile 变量法则：对 Volatile 域的写入操作 happens-before 于每个后续对同一 Volatile 的读操作。
- 传递性：如果 A happens-before 于 B，且 B happens-before C，则 A happens-before C。



## 非阻塞算法

一个线程的失败和挂起不会引起其他些线程的失败和挂起，这样的算法称为非阻塞算法。非阻塞算法通过使用底层机器级别的原子指令来取代锁，从而保证数据在并发访问下的一致性。与锁相比，非阻塞算法在更细粒度（机器级别的原子指令）的层面协调多线程间的竞争。它使得多个线程在竞争相同资源时不会发生阻塞，它的并发性与锁相比有了质的提高；同时也大大减少了线程调度的开销。同时，由于几乎所有的同步原语都只能对单个变量进行操作，这个限制导致非阻塞算法的设计和实现非常复杂。 见`ConcurrentLinkedQueue`。



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





