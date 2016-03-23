# ECharts使用总结

## 本文目录
[TOC]


## 我对Echarts的理解

我对技术的理解是，一门技术，先会使用，再去理解，使用才是真正目的，理解只是更上一层楼，但不是强求的，技术只是工具而已，它不是知识。所以用Echarts实现图表的展现，其内部原理，实现机制，程序员根本不需要了解，我们要做的就是把数据扔给它，如何给它数据，一是直接在页面上写死（-_-......），二是访问图表页面的时候，程序从后台返回数据，前台页面取得数据后，在需要的地方填入即可，三是用Ajax的方式来动态加载数据，Ajax取得数据后，在返回后的succss方法里通过js脚本替换Echarts的option里的相关数据，当然还有其它方法，目的只有一个，把数据给它。

## Echarts的简单使用

### 一、准备。
新建一个echarts_demo.html页面，在body中准备一个div，用于放置图表。
```
<body>
   <div id="main" style="width:800px;height:500px"></div>
</body>
```

### 二、引入echarts.js
在echarts_demo.html中，引入echarts,主要有两种方式，一种是模块化包引入：
```
<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
```
一种是单文件引入，在官网下载文件后，放在本地项目下，如下引入：
```
<script src="/js/echarts.js"></script>
```

### 三、配置echarts路径
```
<script type="text/javascript">
    // 路径配置
    require.config({
        paths: {
            echarts: 'http://echarts.baidu.com/build/dist'
        }
    });
</script>
```
### 四、初始化图表
ECharts图表大部分要关心的是option的实现，这里以我的今年9月前的阅读数为例生成柱状图，代码如下。
```
<script type="text/javascript">
    var option = {
        tooltip: {
            show: true
        },
        legend: {
            data:['2015年1-9月读书数'],
            backgroundColor:'blue'
        },
        xAxis : [
            {
                type : 'category',
                data : ["1月","2月","...省略","8月","9月"]
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                "name":"读书数",
                "type":"bar",
                "data":[3, 4, 1,1,1,3,1,2,4],
                itemStyle: {
                    normal: {
                        label : {
                            show: true,
                            position: 'top'
                        }
                    }
                }
            }
        ]
    };
    // 为echarts对象加载数据 
    myChart.setOption(option); 
    }
);
</script>
```
### 五、效果图
![2015read](http://www.herohuang.com/wp-content/uploads/2015/09/2015.09.24_11h01m16s_002_.jpg)


## 图表拖拽

## 多图联动

```
option = {
    title : {
        text: '金华企业类型',
        subtext: '企业分页',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient : 'vertical',
        x : 'left',
        data:['食品','药品','化妆品','保健品','器械']
    },
    calculable : true,
    series : [
        {
            name:'访问来源',
            type:'pie',
            radius : '55%',
            center: ['50%', 225],
            data:[
                {value:335, name:'食品'},
                {value:310, name:'药品'},
                {value:234, name:'化妆品'},
                {value:135, name:'保健品'},
                {value:1548, name:'器械'}
            ]
        }
    ]
};

option2 = {
    tooltip : {
        trigger: 'axis',
        axisPointer : {
            type: 'shadow'
        }
    },
    legend: {
        data:['食品','药品','化妆品','保健品','器械']
    },
    toolbox: {
        show : true,
        orient : 'vertical',
        y : 'center',
        feature : {
            mark : {show: true},
            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    xAxis : [
        {
            type : 'category',
            data : ['2011','2012','2013','2014','2015']
        }
    ],
    yAxis : [
        {
            type : 'value',
            splitArea : {show : true}
        }
    ],
    grid: {
        x2:40
    },
    series : [
        {
            name:'食品',
            type:'bar',
            stack: '总量',
            data:[320, 332, 301, 334, 390, 330, 320]
        },
        {
            name:'药品',
            type:'bar',
            stack: '总量',
            data:[120, 132, 101, 134, 90, 230, 210]
        },
        {
            name:'化妆品',
            type:'bar',
            stack: '总量',
            data:[220, 182, 191, 234, 290, 330, 310]
        },
        {
            name:'保健品',
            type:'bar',
            stack: '总量',
            data:[150, 232, 201, 154, 190, 330, 410]
        },
        {
            name:'器械',
            type:'bar',
            stack: '总量',
            data:[820, 932, 901, 934, 1290, 1330, 1320]
        }
    ]
};

myChart2 = echarts.init(document.getElementById('main2'));
myChart2.setOption(option2);

myChart.connect(myChart2);
myChart2.connect(myChart);

setTimeout(function (){
    window.onresize = function () {
        myChart.resize();
        myChart2.resize();
    }
},200)
                    
```

## 时间轴


## 地图区域

## 结合百度地图


## Ajax动态加载数据 `[重点]`
用Ajax动态加载图表数据，后台以Json方式传过来一个map，需要的是把option里用到的静态数据替换为返回的Json数据，要换的数据主要是
> `option.legend.data`
`option.xAxis.data`
`option.series.data`

后台传过来的数据一般如下格式：
```
   {
    counts={
        食品=[3, 5, 35, 15], 
        药品=[5, 3, 2, 8]
    }, 
    years=[2011, 2012, 2013, 2014, 2015],        
    types=[
        com.demo.bean.CorpType@16d63462,
        com.demo.bean.CorpType@74728371,
        com.demo.bean.CorpType@a13ab71
        ]
    }
```

Ajax调用的代码如下，目的就是动态给相关属性设置值：
```
 $.ajax({
        url:"/echarts/url", //访问后台取得Json数据路径
        type:"POST",
        dataType:"json",
        async:false,
        success:function(result){
            var types=[];           
            for(var i=0;i<result.types.length;i++){
                types.push(result.types[i].name);
            }
            option.legend.data=types;
            option.xAxis[0].data=result.years;
            option.series=[];
            for(var n in result.counts){
                var new_series={
                    type:'bar',
                    itemStyle: {
                    normal: {
                        label : {
                            show: true,
                            position: 'top'
                        }
                    }
                }};
                new_series.name=n;
                new_series.data=result.counts[n];  
                option.series.push(new_series);
            }
            myChart.hideLoading();
            myChart.setOption(option);
         }
    });
    
```
[完]




