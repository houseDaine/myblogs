#网页布局：CSS实现一列二列三列和混合布局

## 常见网站布局
网站常见的布局方式主要有一列布局，二列布局，三列布局和混合布局。一列布局比如[Google](www.google.com),[Baidu](www.baidu.com),

## 一列布局
### 效果图：
![一列布局](images/csslayout_1.png)
### 代码：
- CSS:
```
    body{margin:0;padding: 0;}
    .top{height:100px;background: blue;}
    .main{width:800px;height:300px;background: #ccc;margin:0 auto;}
    .foot{width: 800px;height:100px;background: red;margin: 0 auto;} 
```
- HTML:
```
    <div class="top"></div>
    <div class="main"></div>
    <div class="foot"></div>
```
- 说明：
页面中有三块`div`，头底和中间内容部分，三部分都设置了固定的高度，顶部的宽度不设置，在实际开发中`main`部分一般不设置固定高度，而是可随着内容的多少而多高，比如[新浪网](http://www.sina.com.cn/)

## 二列布局
### 效果图：
![二列布局]()
### 代码：
CSS：

HTML:

说明：

## 三列布局
### 效果图：
![三列布局]()
### 代码：
CSS：

HTML:

说明：

## 混合布局
### 效果图：
![混合布局]()
### 代码：
CSS：

HTML:

说明：

