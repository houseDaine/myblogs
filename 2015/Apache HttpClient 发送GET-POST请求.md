# Apache HttpClient 发送GET/POST请求

标签（空格分隔）： java

---
## 前言
Java发布Http请求的方式有多种,`HtmlUnit`,`HttpClient`,或者使用本身自带的`HttpURLConnection`,其中`HttpClient`的版本3和版本4使用方法略有不同,这里主要介绍用apache的`HttpClient`版本4来发送Http请求的用法.

## 步骤
使用`HttpClient`发送Http请求的步骤主要如下:

1. 使用`HttpClients`的`createDefault`方法创建一个`CloseableHttpClient`实例

2. 根据请求方式的不同创建`HttpGet`或`HttpPost`实例,并使用`addHeader`方法设置相关的头信息,比如`User-Agent`,`Accept-Encoding`等.

3. 如果是post请求,将所有的请求参数封装到`List<NameValuePair>`中,然后将其封装成一个`HttpEntiy`,然后调用`httpPost.setEntity`方法;

4. 执行请求,得到`CloseableHttpResponse`返回.

5. 从返回结果中取得返回状态码,返回数据等信息

6. 关闭资源

---

## 示例

### Maven
使用`Apache HttpClient`,需要加入相关jar包,如果用的是maven构建的,则如下引入:
```
<dependency>
  <groupId>org.apache.httpcomponents</groupId>
  <artifactId>httpclient</artifactId>
  <version>4.5.1</version>
</dependency>
```
### Jar包
如果用得不是maven,则可以在官网下载包,加入到项目的包目录下.
```
httpclient-4.5.jar
httpcore-4.5.jar
commons-logging-1.2.jar
commons-codec-1.9.jar
```

### GET请求
```
/** 
 * get请求方法
 * @param url 传入的api地址 
 * @return 返回String类型结果数据
 * @throws IOException
 */
private static String sendGet(String url) throws IOException {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpGet httpGet = new HttpGet(url);
    httpGet.addHeader( "User-Agent" , "Mozilla/5.0");
    CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
    HttpEntity httpEntity = httpResponse.getEntity();
    String response = EntityUtils.toString(httpEntity,StandardCharsets.UTF_8);
    httpClient.close();
    return response.toString();
}
```

### POST请求
```
/**
 * post请求方法
 * @param url 请求地址 
 * @param params 请求参数 为处理有多个参数,请求时将所有参数封装成Map
 * @return 返回String类型的结果数据
 * @throws IOException
 */
public static String sendPost(String url, Map<String,String> params) throws IOException {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpPost httpPost = new HttpPost(url);
    httpPost.addHeader("User-Agent","Mozilla/5.0");
    //urlParameters.add(new BasicNameValuePair("userName","www.iacheron.com"));
    List<NameValuePair> urlParameters = new ArrayList<NameValuePair>(params.size());
        for (String key : params.keySet()) {
            urlParameters.add(new BasicNameValuePair(key,params.get(key)));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(urlParameters, StandardCharsets.UTF_8));
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        String response = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
        httpClient.close();
        return response;
    }

```

## 源码
[HttpUtil.java](http://www.iacheron.com)