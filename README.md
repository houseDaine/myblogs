http://me.herohuang.com

# 序列化与反序列化

## 概念
    - 序列化：将一个对象转换成字节序列的过程
    - 反序列化：将一个字节序列重新构造成对象的过程

## 序列化的作用
    - 把对象的字节序列永久保存到硬盘上
    - 在网络上转送对象的字节序列

## 序列化反序列化的步骤
    - 序列化：
    1. 创建一个对象输出流`ObjectOutputStream`
    2. 调用对象输出流的`writeObject()`方法写对象,将对象写入到输入流中
    3. 关闭流
    - 反序列化： 
    1. 创建一个对象输入流`ObjectInputStream`
    2. 通过对象输入流的`readObject()`方法读取对象。
    3. 关闭流
    
## Serializable接口
序列化接口Serializable接口没有方法或变量，仅用于标识可序列化的语义,Java类通过实现`Serializable`接口不启用序列化功能，如果对一个对象序列化时，该对象没有实现此接口，则会报`NotSerializableException`错误。

```
public class User implements Serializable {
    private String name;
    private Integer age;
    private String sex;
    
    ...
}
```

## 序列化和反序列化案例

```
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("D://test.txt"));
        User user = new User("herohuang.com",20,"man");
        out.writeObject(user);

        ObjectInputStream in = new ObjectInputStream(new FileInputStream("D://test.txt"));
        User user1 = (User)in.readObject();
        System.out.println(user1.toString());
    }
```

## transient关键字


对象序列化保存的是对象的"状态"，即它的成员变量。由此可知，对象序列化不会关注类中的静态变量。
http://www.blogjava.net/jiangshachina/archive/2012/02/13/369898.html

http://www.importnew.com/17964.html

http://www.importnew.com/18024.html

https://www.ibm.com/developerworks/cn/java/j-lo-serial/
