# SpringMvc返回Json时的日期格式处理

标签（空格分隔）： java

---

## 问题
SpringMvc使用`@ResponseBody`时返回json的日期是一个`long `值的时间戳，怎么样才能返回自定义的`yyyy-MM-dd HH :mm:ss` 的格式。

## 原因
`@ResponseBody`返回Json字符串的核心类是`MappingJacksonHttpMessageConverter `，它使用了`Jackson`这个开源的第三方类库,`jackson`如何转换，spring不作多干涉。

## 解决 方法一
`MappingJacksonHttpMessageConverter`是通过ObjectMapper来实现返回json数据的。只要继承该类，注册一个JsonSerializer<T>。然后在配置文件中注入自定义的ObjectMapper即可。

### 第一步：新建一个`CustomerObjectMapper`类继承`ObjectMapper`类
```java
/**
 * 解决返回json格式时日期格式为自定义格式
 */
public class CustomerObjectMapper extends ObjectMapper {
    public CustomerObjectMapper() {
        CustomSerializerFactory factory = new CustomSerializerFactory();
        factory.addGenericMapping(Date.class, new JsonSerializer<Date>() {
            @Override
            public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                jsonGenerator.writeString(sdf.format(date));
            }
        });
        this.setSerializerFactory(factory);
    }
}
```

### 第二步：在springmvc的配置文件中如下注入
```
 <mvc:annotation-driven>
      <mvc:message-converters>
          <bean class="org.springframework.http.converter.json.MappingJacksonHttpMessageConverter">
              <property name="objectMapper" ref="customerObjectMapper"></property>
          </bean>
      </mvc:message-converters>       
    </mvc:annotation-driven>
    <bean id="customerObjectMapper" class="com.wmchoice.customer.module.hspd.util.CustomerObjectMapper"></bean>
```

## 解决 方法二
抛弃jackson方式,改用`fastjson`,fastjson是阿里巴巴的一个Json处理工具包,号称是最快的json解析工具,使用方法如下

### Maven引入
```
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.7</version>
</dependency>
```
### springmvc配置文件中更换原jackson方式
```xml
 <mvc:annotation-driven>
    <mvc:message-converters register-defaults="true">
        <bean class="com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter">
            <property name="supportedMediaTypes" value="text/html;charset=UTF-8"/>
            <property name="features">
                <array>
                    <!--为空的不显示-->
                    <!--<value>WriteMapNullValue</value>-->
                    <value>WriteNullStringAsEmpty</value>
                    <!--格式化日期格式-->
                    <value>WriteDateUseDateFormat</value>
                </array>
            </property>
        </bean>
    </mvc:message-converters>
    </mvc:annotation-driven>
```
如果不对全局的日期格式都格式化,那上面的配置中 `<value>WriteDateUseDataFormat</value>`不添加,而可以在相应的javabean中给某个属性加上如下注解:
```
@JSONField(format = "yyyy-MM-dd HH:mm:ss")
private Date addDate;
```

## 其它问题
- jackson去除返回的null值
原jackson中对于返回的json取出null的字段,可以在对应的javabean中的类上加如下注解
``` java
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
```
- fastjson返回json时的属性排序
```
public static class VO {
    @JSONField(ordinal = 3)
    private int f0;

    @JSONField(ordinal = 2)
    private int f1;

    @JSONField(ordinal = 1)
    private int f2;
}
```