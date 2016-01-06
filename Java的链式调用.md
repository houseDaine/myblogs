# Java的链式调用 

当实例化一个Java对象时,常常会看到如下结构的代码:
```
User user = new User();
user.setName("Acheron");
user.setAge(25);
user.setSex("男");
user.setAddress("some where");
```
怎样去除上面代码中Java API风格中令人反感的冗余,用链式调用法,让所有的属性设置方法返回this而不是void,User类就该是这样的:
```
public class User {
    private String name;
    private Integer age;
    private String sex;
    private String address;

    public static User instance(){
        return new User();
    }
    public User name(String name) {
        this.name = name;
        return this;
    }
    ......
}
```
现在用链式调用法就可以用如下方式:
```
User user = User.instance().name("Acheron").age(20).sex("男").address("some where");
```
    





