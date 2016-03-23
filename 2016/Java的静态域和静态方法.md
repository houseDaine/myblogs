# Java的静态域和静态方法

标签（空格分隔）： java

---
## static的定义
`static`是Java的静态修饰符,何为静态,一段程序中的变量或方法,是在编译时由系统自动分配内存来存储的，而所谓静态就是指在编译后所分配的内存会一直存在，直到程序退出才会释放这个空间.在Java程序里，万物皆对象，而对象的抽象就是类，一个Java类主要由数据和程序组成,数据也就是所谓的`域(field)`,程序就是`方法(method)`,当域或方法被`static`修饰时,则分别称它们为静态域和静态方法,也叫`类变量`和`类方法`;
```java
public Class User {
    public int id;//实例域
    public static final String name = "Acheron";//类变量
    public static String getName(){ return name; }//类方法
}
```
## static的使用
对于一个类而言，如果要使用其中的成员，普通情况下必须先实例化对象后，通过对象的引用才能够访问这些成员，但是当该成员是用`static`声明的,则可以不用实例化对象就可以引用成员:
```
User.getName();
```

## 静态方法
静态方法是不能操作对象的方法,所以不能在静态方法中访问实例域,但可以访问静态域:
```
public static String getName(){ 
    return name; 
}
```
## 内存分配
根据是否有`static`修饰,则可以将一个类中的变量和方法分为四种情况:
1. 实例域(instance field) : 没有static修饰的field,随着每个instance各有一块内存
2. 实例方法(instance method) : 没有static修饰的method,***共享一块内存***
3. 类变量(class field) : 有static修饰的field,共享一块内存
4. 类方法(class method) : 有static修饰的method,共享一块内存

### 静态域的内存分配 
当一个field是static修饰时,则这个field只占用一块内存,而且此内存空间是在此class被加载之后就立刻配置的,这个field和class本身有关,而不是与这个class的实例(instance)相关,此类的所有实例将会共享这个field.如果field没有static修饰,则此类的每一个实例对这个filed都有一个自己的拷贝.

对于上面的`User`类,每个User都有一个自己的`id`域,但这个类的所有实例将共享一个`name`域,如果`new`100个`User`类的对象,则有100个实例域`id`,但只有一个静态域`name`,就算没有一个`User`对象,静态域`name`也存在,它属于类,不属于对象.

### 实例方法的内存分配
实例方法为什么和静态方法一样共享一块内存,因为实例方法占用的内存是域的数百倍,如果和实例域一样随着每个instance各占一块内存,则太浪费空间了.

## 隐藏的this参数
既然一个类的所有实例共享相同的实例方法,那么下面两个实例调用相同method时,如何区分是instance1和instance2:
```
instance1.method();
instance2.method();
```
任何实例方法都有一个隐藏的参数,此参数的变量名是`this`,这个参数是由java编译器加上去的.当调用某个实例的方法时,必须在前面加上该实例的名称,当该实例和该方法所有在实例指的是同一个实例时,则该实例的名称就是`this`,这种情况,也可以省略`this`.

当方法中的参数或变量和实例域完全相同时,如果不在前面加上`this`,则表示指的是参数或局部变量,如果在前面加上`this`,那么指的才是实例域.
```
public void setName(String name) {
    this.name = name;
}
```

