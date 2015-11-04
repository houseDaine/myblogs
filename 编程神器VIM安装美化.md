# 编程神器VIM安装美化

---
> 天下武功，唯快不破。编程器对于程序员，犹如剑对于剑客。什么样的剑配什么样的剑客，什么样的编程器配什么样的程序员。VIM、Emacs、Sublime Text、textmate、notepad等，这里面我最喜欢的是VIM和SublimeText,sublime Text近几年才流行起来，而VIM历史悠久，学习成本高，很多人望而生畏，但一旦上手熟悉后，能极大提高编程效率，VIM可以说是第一编程神器。

##效果图
![趋势图](http://www.iacheron.com/wp-content/uploads/2015/01/vim-full.png)

## 安装：
在windows系统下，VIM称为GVIM，在官网[下载](http://www.vim.org/download.php)。安装后，在安装目录下，有三部分：

- `vim74`：安装目录，我这里安装的7.4版本。
- `vimfiles`：用户自己的一个配置文件夹，vim74中的plugin和vimfiles中的plugin作用是一样的，插件放到这2个文件夹都会起作用。
- `_vimrc`：VIM的个性化配置文件，快捷键、主题、字体等都在这里配置。

##安装[Pathogen](https://github.com/tpope/vim-pathogen)（管理插件的插件）
VIM的一个强大的地方在于它有无数方便的插件，在Pathogen或Vundle出现之前，VIM的插件管理非常混乱，安装好VIM后的第一步事就应该是安装Pathogen或Vundle,管理插件的插件，以[Pathogen](https://github.com/tpope/vim-pathogen)为例,首先在`vimfiles`目录下手工新建两个文件夹，名字分别为`autoload`和`bundle`，然后下载[Pathogen](https://github.com/tpope/vim-pathogen)为一个ZIP文件，解压后将其中`pathogen.vim`文件拷贝到`\Vim\vimfiles\autoload`目录下。之后在配置文件`_vimrc`中加上如下代码，安装成功。以后通过pathogen安装的插件都会放在`bundle`目录下，不需要时删除即可。

```
" pathogen插件管理
execute pathogen#infect()
```

## 安装配色主题
VIM原始界面朴素得就像TXT记事本，VIM内置了10多种配色方案可供选择，可以通过菜单（Edit -> ColorScheme）试用不同方案。在github上有许多漂亮的主题方案，推荐两款：[solarized](https://github.com/altercation/vim-colors-solarized) &  [molokai](https://github.com/tomasr/molokai)。以[solarized](https://github.com/altercation/vim-colors-solarized)为例，用两种方法来安装。
### 原始方法：
下载之后解压，将其中的solarized.vim文件拷贝至Vim\vimfiles\colors,然后在`_vimrc`中设定选用其作为默认主题，如下。其中，不同主题都有暗/亮色系之分，dark或light。

```
" 配色主题
set background=dark
colorscheme solarized
```
### 用pathogen来安装（推荐）
一个简单的方法是下载后，将解压后的文件夹拷贝到'vimfiles\bundle'目录，或者用git客户断，切到`bundle`目录，运行`git clone`命令即可。
```
$ cd ~/.vimfiles/bundle
$ git clone https://github.com/altercation/vim-colors-solarized
```
之后在`_vimrc`的配置同上面方法一样。

### 去菜单工具条等
为了专注编程，不受干扰，不应该有工具条、菜单、滚动条浪费空间的元素，在`_vimrc`中配置如下即可去掉。

```
" 禁止光标闪烁
set gcr=a:block-blinkon0
```

```
" 禁止显示滚动条
set guioptions-=l
set guioptions-=L
set guioptions-=r
set guioptions-=R
```

```
" 禁止显示菜单和工具条,并绑定到快捷键F2
set guioptions-=m
set guioptions-=T
map <silent> <F2> :if &guioptions =~# 'T' <Bar>
        \set guioptions-=T <Bar>
        \set guioptions-=m <bar>
    \else <Bar>
        \set guioptions+=T <Bar>
        \set guioptions+=m <Bar>
    \endif<CR>
```


### 全屏设置
设置全屏需要插件，在这里[下载](http://www.vim.org/scripts/script.php?script_id=2596)。下载得到的是一个zip压缩包，解压将gvimfullscreen.dll文件复制到安装目录下gvim.exe所在的文件夹，和gvim.exe同目录，打开Vim配置文件_vimrc，将其绑定到`F11`快捷键，配置如下：


```
" F11 为Vim全屏切换快捷键
map <F11> <Esc>:call libcallnr("gvimfullscreen.dll", "ToggleFullScreen", 0)<CR>
```

```
" 在插入模式下也设置F11全屏
imap <F11> <Esc>:call libcallnr("gvimfullscreen.dll", "ToggleFullScreen", 0)<CR>
```

```
" 启动 vim 时自动全屏
au GUIEnter * simalt ~x
```

五、字体编码等
默认字体不好看，挑个自己喜欢的，前提是你的电脑得先安装好该字体。我用的是Mono

```
" 指定语言，由于字体名存在空格，需要用“\”进行转义，最后的12指字体大小
set guifont=DejaVu\ Sans\ Mono:h12
```

``` 
" 设置编码
set encoding=utf-8
```

```
" 解决consle输出乱码
language messages zh_CN.utf-8
```

```
" 设置文件编码检测类型及支持格式
set fencs=utf-8,gbk,ucs-bom,gb18030,gb2312,cp936
```

```
" 指定菜单语言
set langmenu=zh_CN.utf-8
source $VIMRUNTIME/delmenu.vim
source $VIMRUNTIME/menu.vim
```

### 添加辅助信息

```
" 总是显示状态栏
set laststatus=2
```

```
" 显示光标当前位置
set ruler
```

```
" 开启行号显示
set number
```

```
" 高亮显示当前行/列
set cursorline
set cursorcolumn
```

```
" 高亮显示搜索结果
set hlsearch
```

```
" 禁止折行
set nowrap
```

```
" 开启语法高亮功能
syntax enable
```

```
" 允许用指定语法高亮配色方案替换默认方案
syntax on
```
