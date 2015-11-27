# SpringMvc返回Json时的日期格式处理

标签（空格分隔）： java

---

## 问题
SpringMvc使用`@ResponseBody`时返回json的日期是一个`long `值的时间戳，怎么样才能返回自定义的`yyyy-MM-dd HH :mm:ss` 的格式。

## 原因
`@ResponseBody`返回Json字符串的核心类是`MappingJacksonHttpMessageConverter `，它使用了`Jackson`这个开源的第三方类库,`jackson`如何转换，spring不作多干涉。

## 解决
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





