# Java中的枚举类型(enum type)

---
## 枚举是什么
枚举是在JDK5.0加入的，主要用来表示一组相同类型的常量。枚举类本质上是一个Java类，经过编译之后产生的就是一个class文件，所有的枚举类型都继承`java.lang.Enum`类。

## int 枚举模式(int enum pattern)
在没有引入枚举之前，定义常量的方法一般是这样的：
```java
public static final int STATUS_START = 1;
public static final int STATUS_END = 2;
```
这种方法称作`int 枚举模式`，与枚举类型相比，这种方法有许多不足:

+ 无类型安全性,将上面`STATUS_START`的值传入需要`STATUS_END`的方法中，编译器不会出现警告。
+ 使用不便，如果有多个常量，需要每个类型成员声明一个常量。
+ int枚举是编译时常量，如果int值发生了变化，客户端就心須重新编译。
+ 遍历int枚举常量，没有可靠的办法。

与`int枚举模式`相同情况的，比如`String枚举模式`，也有相同的不足。

## 枚举的优点
- 编译时的类型安全
- 增加或重新排列枚举中的常量，无需重新编译客户端代码
- 紧凑有效的枚举数值定义
- 运行的高效率
- 允许添加任意的方法和域，并实现任意的接口


## 定义枚举
使用枚举定义常量的方法如下：
```java
public enum statusEnum { 
    START,END;
}
```
通过括号赋值,每个枚举常量后面括号中的数值就是传递给构造器的参数，而且必须带有一个参构造器和一个属性跟方法，否则编译出错，赋值必须都赋值或都不赋值，不能一部分赋值一部分不赋值。
```java
public enum SexEnum { 
    male("男"),female("女");
    private String sex; 
    
    SexEnum(String sex) {
        this.sex = sex; 
    }
    public String getSex() {
        return sex;
    }
}
```

实际上，这个声明定义的类型是一个类，它刚好有2个实例，因此比较两个字符串枚举类型的值时，不需要使用`equals`,而直接使用`==`就可以了：
```java
sexEnum.male.toString()=="男"
```

## 为什么要将方法和域添加到枚举类型中
因为很可能需要将数据和枚举常量关联起来，对于以后的扩展，可以利用适当的方法来增强枚举类型。枚举类型可以先作为枚举常量的一个简单集合，随着时间的推移再演变为全功能的抽象。


## 枚举中常用的方法
### toString()
说明：：返回枚举常量名，SexEnum.male.toString()将返回`male`

### valueOf(String) 
说明：toString的逆方法，返回某一个具体的枚举实例,将常量的名字转换为常量本身，如下面的语句将打印出`女`

```java
 System.out.println(SexEnum.valueOf("女").toString()); 
```

### values()
说明：返回一个包含全部枚举值的数组,如下面的语句将打印出`男 女`
```java
for(SexEnum e : SexEnum.values()){
    System.out.print(e); 
}
```
### ordinal()
说明：返回枚举常量的位置，从0开始计数，在jdk源码中，有写道：

>   Most programmers will have no use for this method.  It is designed for use by sophisticated enum-based data structures, such as {@link java.util.EnumSet} and {@link java.util.EnumMap}.

这个方法是设计成用于像`EnumSet`和`EnumMap`这种基于枚举的通用数据结构的，除非在编写的是这种数据结构，否则最好避免使用这个方法。***永远不要根据枚举的序数导出与它关联的值，而是要将它保存在一个实例域中。***

## 枚举的基本思想
枚举类型是真正的final，因为客户端既不能创建枚举类型的实例，也不能对它进行扩展，因此很可能没有实例，而只有声明过的常量，***枚举是单例的泛型化，它们就是通过公有的静态final域为每个枚举常量导出实例的类***。





