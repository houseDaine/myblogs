# 前端自动化nodejs/yeoman/bower/grunt

标签（空格分隔）：前端 

---

## 安装[nodejs](https://nodejs.org) :
```
//解压
tar -zxvf node-v5.1.1-linux-x86.tar.gz

//设为全局变量
sudo ln -s /home/acheron/software/node-v5.1.1-linux-x86/bin/node /usr/local/bin/node

sudo ln -s /home/acheron/software/node-v5.1.1-linux-x86/bin/npm /usr/local/bin/npm

//查看
node -v
```

## 安装[Yeoman](http://yeoman.io/)
```
npm install -g yo
```

## 安装[Bower](http://bower.io)
```
//安装
npm install -g bower

//配置环境变量
sudo vim /etc/profile

export PATH=/home/acheron/software/node-v5.1.1-linux-x86/lib/node_modules/bower/bin:$PATH

//设为全局变量
sudo ln -s /home/acheron/software/node-v5.1.1-linux-x86/lib/node_modules/bower/bin/bower /usr/local/bin/bower

//查看
bower -v
```


## 安装[grunt](http://www.gruntjs.net)
```
//安装
npm install -g grunt-cli

//设为全局变量
sudo ln -s /home/acheron/software/node-v5.1.1-linux-x86/lib/node_modules/grunt-cli/bin/grunt /usr/local/bin/grunt

```











