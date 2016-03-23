# Git常用命令总结

---
## 一、开始
```
// 1.设置用户信息（初次安装时）
git config --global user.name "Acheron"
git config --global user.email acheron20122012@gmail.com

// 2.检查配置信息
git config --list 

// 3.获得帮助
git help
```

## 二、使用
```
// 1.初始化仓库
git init

// 2.查看文件的状态
git status

// 3.文件加入版本控制
git add <file>...

// 4.提交
git commit -m "say something"

// 5.查看远程仓库
git remote

//6.从远程仓库中抓取更新
git fetch [remote-name]

// 7.推送到远程仓库
git push origin master

// 8.查看提交历史
git log -p

// 9.移除文件
git rm <file>...

// 10.移动文件
git mv file_from file_to

//11.查看尚未暂存的文件更新了哪些部分
git diff
```
