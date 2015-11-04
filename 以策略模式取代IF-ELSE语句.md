# 以策略模式取代IF-ELSE语句

---

> 我个人认为编程技术质的飞越有几个关键的阶段，其中脱离低级程序员很重要的一个标志就是设计模式的运用，类似于各种修仙小说中主角的度劫飞天，过了这一关，一个崭新的更大的世界便向你打开。对于设计模式的运用，难点不是怎么使用，而是怎样才能明确地知道什么时候使用它，而这需要大量的编程经验的积累。

在重构老项目的过程中，最烦的就是遇到一大堆if-else语句，我曾经见过一个方法近千行，全是if-else，直接能把人绕晕，遇到这种情况，使用多态和策略模式，就能有效地解决。这里写个简单的例子。

有一个类Test，在这个类中有一个方法operate，根据参数type的不同，进行相关操作，如果type有多种，那么这里的if-else就将继续写下去，怎样改善这段代码。
```
class Test{
   int operate(String type) {
        if (type.equals("aaa")) {
            //do something...
        } else if (type.equals("bbb")){
            //do something...
        } else if(type.equals("ccc")){
            //do something...
        }
    }
}
```
* 第一步
新建一个接口，接口中定义一个方法operate。
```
interface ITest{
    int operate(String type);
}
```

* 第二步:
根据type的不同，新建相应的接口实现类，在实现类中作各自的不同的操作。
```
class A implements ITest{
   int operate(String type) {
       //do something
   }
}
```
```
class B implements ITest{
   int operate(String type) {
       //do something
   }
}
```
```
class C implements ITest{
   int operate(String type) {
       //do something
   }
}
```
* 第三步：
新一个Map，在map中放置各个实现类。
```
Map<String,ITest> map = new HashMap<String,ITest>();
map.put('aaa',new A());
map.put('bbb',new B());
map.put('ccc',new C());
```
* 第四步：
去除Test类的operate方法中的if-else判断,根据type从map中获得对应的实现类，调用相关的operate方法。
```
class Test{
   int operate(String type) {
        map.get(type).operate(type);
    }
}
```