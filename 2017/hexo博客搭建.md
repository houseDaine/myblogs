# 搭建Hexo博客


## Hexo
Hexo官网：http://hexo.io/zh-cn/

## 开始部署

- 安装hexo：cnpm install -g hexo-cli
- 创建 Hexo 项目:mkdir hexo
- 进入该目录：cd hexo
- 初始化：hexo init
- 执行：cnpm install
- 安装完成后，启动服务：hexo server
- 访问：http://localhost:4000/

## 换主题
- 官方提供:hexo-theme：https://hexo.io/themes/
- 我这里选用：https://github.com/mickeyouyou/yinwang
- cd themes
- git clone https://github.com/mickeyouyou/yinwang
- 修改hexo目录下的项目配置文件：`_config.yml`，把对应的主题目录名改下
- 重新生成：hexo generate
- 重启服务：hexo server

## 创建 Github pages 并 SSH 授权

- 生成密钥：ssh-keygen -t rsa -C "test@gmail.com"
- 打开.ssh/id_rsa.pub，复制文件中的所有内容。
- 访问：https://github.com/settings/ssh
Title：随便
Key：把刚刚复制的都粘贴进来

### 把本地的博客内容同步到 Github 上
- 先安装两个跟部署相关的 hexo 插件：
```
cnpm install hexo -server --save
cnpm install hexo-deployer-git --save
```
- 配置hexo配置文件：`_config.yml`
Docs: https://hexo.io/docs/configuration.html

## 设置博客，注意：每个冒号后面都是有一个空格，然后再书写自己的内容的
title: Acheron
subtitle: test

## URL要设置绑定域名的，这里写域名信息
url: http://blog.herohuang.com
root: /
## Deployment
这里是重点，这里是修改发布地址，因为我们前面已经加了 SSH 密钥信息在 Github 设置里面了，所以只要我们电脑里面持有那两个密钥文件就可以无需密码地跟 Github 做同步。
需要注意的是这里的 repo 采用的是 ssh 的地址，而不是 https 的。分支我们默认采用 master 分支.
Docs: https://hexo.io/docs/deployment.html
deploy:
  type: git
  repo: git@github.com:Ac-heron/acheron.github.io.git
  branch: master

- 编辑全局配置后我们需要重新部署：
- 先清除掉已经生成的旧文件：hexo clean
- 再生成一次静态文件：hexo generate
- 在本地预览下：hexo server
- 使用部署命令部署到 Github 上：hexo deploy，有弹出下面提示框，请输入：yes
确认提交
- 访问服务器地址进行检查：http://acheron.github.io

但是，也不排除你 deploy 不了到 Github，报这个错误：Host key verification failed，那解决办法如下：
还是在 Git Bash 界面中，输入如下命令：ssh -T git@github.com，根据界面提示，输入：yes 回车。之后你可以再试一下是否可以 deploy。
通过上面几次流程我们也就可以总结：以后，每次发表新文章要部署都按这样的流程来：
hexo clean
hexo generate
hexo deploy
也因为这几个命令太频繁了，所以又有了精简版的命令：
hexo clean == hexo clean
hexo g == hexo generate
hexo s == hexo server
hexo d == hexo deploy

## 绑定域名
首先我们要一个 CNAME 文件（文件名叫 CNAME，没有文件后缀的），把该文件放在 \hexo\source 目录下，CNAME 上的内容需要写你具体要绑定的域名信息，比如我是 blog.herohuang.com，：
设置 CNAME 文件
接着我们需要到 DNSPOD 上设置域名解析：https://www.dnspod.cn/
设置域名解析
设置域名解析
设置好之后，等过几分钟域名解析好之后
主机记录：code
记录类型：CNAME
记录值：ac-heron.github.io.（后面的这个点别忘记了）
还有，要记得把 CNAME 那个文件再 deploy 到 Github 哦，不然还是访问不了的。
