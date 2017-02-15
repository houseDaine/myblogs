# Java核心技术卷I（第9版）阅读笔记

标签（空格分隔）： java

---
###概念
JDK:Java development kit 编写java程序的程序员使用的软件
JRE:Java runtime environment 运行java程序的用户使用的软件
SE:standard edition 用于桌面或简单的服务器应用的java平台
ME:micro edition 用于手机或其它小型设备的java平台
EE：enterprsie edition 用于复杂的服务器应用的java平台
实例域：对象中的数据
方法：操作数据的过程
反射：在程序运行期间发现更多的类及其属性的能力

###大数值
如果基本的整数和浮点数精度不能满足要求，则可以使用BigInteger和BigDecimal，这两个类可以处理任意长度的数值。使用`valueof`方法可以将普通的数值转换为大数值，如果是相加运算时，不能直接相加，只能用add方法：
```
BigInteger a = BigInteger.valueOf(100);
BigInteger b = BigInteger.valueOf(100);
BigInteger c = b.add(a);// c = a+b
```

###数组长度为0
在java中数组长度可以为0，在一个返回数组的方法中，如果返回为空，则可以返回一个长度为0的数组：
```
public String[] test() {
    return new String[0];
}
```

##3静态导入
使用static可以实现静态导入：
```
import static java.lang.System.*;
public class Test {
    public static void main(String args[]) {
        out.print("hello java");
    }
}
```

#4自动装箱和自动拆箱：
自动装箱：
```
list.add(3) --> list.add(Integer.valueOf(3))
```
自动拆箱：
```
int n = list.get(i) --> int n = list.get(i).intValue()
```






