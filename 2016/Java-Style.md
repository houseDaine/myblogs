# Java 开发基础规范（初稿）

<!-- more -->




## 命名、规范


### Java 相关命名、规范

- 命名：
    - Java 类名命名（用名词性单词组合）
        - **普通类名**：首字母大写，需要两个、多个单词表达的，使用大驼峰命名法进行命名，`eg：CategoryService`
        - **抽象类名**：在普通类名的基础上对其命名后加上 Abstract，`eg：CategoryAbstract`
        - **自定义异常类名**：在普通类名的基础上对其命名后加上 Exception，`eg：CategoryException`
        - **队列类名**：在普通类名的基础上对其命名后加上 Queue，`eg：CategoryQueue`
        - **后台任务类名**：在普通类名的基础上对其命名后加上 Task 或 Job，`eg：CategoryTask、CategoryJob`
        - **Servlet 类名**：在普通类名的基础上对其命名后加上 Servlet，`eg：CategoryServlet`
        - **Filter 类名**：在普通类名的基础上对其命名后加上 Filter，`eg：CategoryFilter`
        - **工厂类名**：在普通类名的基础上对其命名后加上 Factory，`eg：CategoryFactory`
        - **工具类名**：在普通类名的基础上对其命名后加上 Util，`eg：CategoryUtil`
        - **测试类名**：在普通类名的基础上对其命名后加上 Test，`eg：CategoryServiceTest`
        - **数据库访问层接口类名**：在普通类名的基础上对其命名后加上 Dao，`eg：CategoryDao`
        - **数据库访问层实现类名**：在普通类名的基础上对其命名后加上 DaoImpl，`eg：CategoryDaoImpl`
        - **业务层接口类名**：在普通类名的基础上对其命名后加上 Service，`eg：CategoryService`
        - **业务层实现类名**：在普通类名的基础上对其命名后加上 ServiceImpl，`eg：CategoryServiceImpl`
        - **控制层类名**：在普通类名的基础上对其命名后加上 Controller，`eg：CategoryController、CategoryAction、CategoryActivity`
        - **对象扩展类名**：在普通类名的基础上对其命名后加上 VO，`eg：CategoryVO(Value Object)、CategoryDTO(Data Transfer Object)、CategoryPOJO(plain ordinary java object)`
    - **常量名**：全部字母大写，有多个单词用下划线分隔，`eg：MY_AGE`
    - **常规变量名**：首字母小写，需要两个、多个单词表达的，使用小驼峰命名法进行命名，`eg：categoryName`
    - **复数变量名**：首字母小写，需要两个、多个单词表达的，使用小驼峰命名法进行命名，eg：
        - `List：categoryList`
        - `Map：categoryMap`
        - `Set：categorySet`
    - **package 名**：所有单词全部小写，即使有多个单词组成，且不能使用下划线连接，或是其他任意字符连接，`eg：googlebook`
    - **方法参数名**：首字母小写，需要两个、多个单词表达的，使用小驼峰命名法进行命名，`eg：categoryName`
    - 方法命名（用动词性单词开头）：
        - **数据库访问层方法名**
            - `saveCategory()`
            - `deleteCategoryByObject()`
            - `deleteCategoryById()`
            - `updateCategoryByObject()`
            - `updateCategoryById()`
            - `findCategoryList()`
            - `findCategory()`
        - **业务层方法名**
            - `saveCategory()`
            - `deleteCategoryByObject()`
            - `deleteCategoryById()`
            - `updateCategoryByObject()`
            - `updateCategoryById()`
            - `findCategoryList()`
            - `findCategory()`
            - `initCategory()`
            - `openConnection()`
            - `closeConnection()`
            - `writeFile()`
            - `readFile()`
    - 视图层（JSP、FreeMarker 等）：
        - `categoryList`
        - `categoryAdd`
        - `categoryUpdate`
        - `categoryEdit`
        - `categoryDetail`
        - `categoryTree`
- 规范：
    - 当一个类有多个构造函数，或是多个同名方法，这些函数 / 方法应该按顺序出现在一起，中间不要放进其它函数 / 方法
    - 导入包的时候，import 后面不要使用通配符 * 来代替有些包的导入
    - 大括号与 if, else, for, do, while 语句一起使用，即使只有一条语句(或是空)，也应该把大括号写上
    - 不要使用组合声明，比如 int a, b;
    - 需要时才声明，并尽快进行初始化
    - 注解紧跟在文档块后面，应用于类、方法和构造函数，一个注解独占一行
- 注释：
- 块注释

``` bash
/*
logger.info("---------开始---------");
SubmitOrderInfo submitOrderInfo = getSubmitOrderInfo(orderId);
*/
```

- 行注释，只用来注释

``` bash
//ResultInfo resultInfo = orderService.orderStateUpdate(voucherNo);
```

- 行注释，用来解释

``` bash
private int categoryId = 1; // 1 是顶级分类的 ID
```


### Mysql 相关命名、规范


- **表名**：全部小写，需要两个、多个单词表达的使用下划线隔开，`eg：prd_category`
- **字段名**：全部小写，需要两个、多个单词表达的使用下划线隔开，`eg：category_name`
- 注释：
- 行注释
    - `# 下面内容需要先执行`
    - `-- 下面内容需要先执行`

- 块注释

``` bash
/*
下面内容需要先执行
需要注意的是：分类的 ID 需要先检查
*/
```

- 程序 SQL 补丁文件命名，eg：
    - `20160306-update-更新所有会员密码`
    - `20160312-delete-删除指定会员密码`
    - `20160313-insert-新增会员数据`
    - `20160315-alter-更新会员邮箱字段长度`


## 编码

- 数据源连接：`jdbc:mysql://localhost:3306/youshop?characterEncoding=utf-8`
- Java 文件编码：`UTF-8`
- XML 文件编码：`UTF-8`
- Properties 文件编码：`UTF-8`
- Mysql 字符集：`UTF-8`



## 其他

- Tab 缩进为 4 个空格，使用 IntelliJ IDEA 标准格式化即可
- TODO 标记必须使用个人自定义 TODO，不能使用公共的
- SVN、Git 提交必须有 Commit Message


## 资料

- <https://github.com/Ac-heron/cscs>
- <https://github.com/google/styleguide>
- <http://www.cnblogs.com/lanxuezaipiao/p/3534447.html>

















