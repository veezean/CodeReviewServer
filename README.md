# CodeReviewServer

> 新版本代码已经正式合入到主干分支，欢迎使用。
> 整体采用 `SpringBoot + Vue` 技术架构实现。


## 一种更简单高效的代码review体验

在我们的项目开发过程中，代码`review`是不可或缺的一个环节。虽然市面上已有一些成熟的代码`review`系统，或者是基于`git`提交记录进行的在线review操作，也许其功能更强大，但是使用上总是不够方便：

- 代码不同于小说审稿，纯文本类型的阅读式review模式，很难发现逻辑层面的问题
- 代码review完成之后，针对评审意见的逐个确认、跟踪闭环也比较麻烦
- 平时项目开发的时候没法同步记录发现的问题
- ...

对于程序员来说，`IDEA`中查看代码才是最佳模式，在IDEA中可以跳转、搜索、分析调用，然后才能检视出深层的代码逻辑问题。此外，平时开发过程中，如果写代码的时候发现一些问题点，如果可以直接在IDEA中记录下来，然后交由对应责任人去修改，这样的代码review体验岂不是更方便、更高效。

基于此想法，利用业余时间开发了IDEA配套的代码review插件，上到应用时长之后，也收获了相对比较高的评分，也收到很多同学的私信赞扬，说明程序员“**苦code review久矣**”！

![](https://pics.codingcoder.cn/pics/202307222357867.png)

当然，随着使用的同学数量增加，也收到越来越多的同学反馈希望加一个团队协作能力，这样可以方便团队内评审活动的开展。

于是，在原有的本地review基础上，增加了插件配套的服务端，实现了团队内成员间代码review意见的管理、统计以及彼此的协同。

![](https://pics.codingcoder.cn/pics/202307230012353.png)

## 服务端安装部署

好了，如果你正在为团队寻找一个简单、高效的代码检视协同工具，也已经准备好体验一番，下面就可以开始动手部署服务端应用啦。

### 依赖条件

- JDK8+
- MySQL
- MongoDB

### 部署说明

您可以从本仓库的`release version`中下载最新版本的二进制包，然后直接部署即可使用：

1. 准备好MySQL、MongoDB
2. 执行`initial_db.sql`对MySQL进行初始化操作
3. 修改`config/application-PROD.properties`文件中的配置，填写正确的数据库连接信息
4. 根据部署系统的不同，执行`start.bat`或者`start.sh`，启动服务
5. 访问`http://localhost:23560`可以查看到登录界面，使用预置账号`codereview/123456`可以登录进入系统。


如果您是直接clone本代码仓库到本地，您可以按照如下方式即可轻松部署运行：

1. 准备好MySQL、MongoDB
2. 执行`initial_db.sql`对MySQL进行初始化操作
3. 修改`application-PROD.properties`文件中的配置，填写正确的数据库连接信息
4. 启动服务
5. 访问`http://localhost:23560`可以查看到登录界面，使用预置账号`codereview/123456`可以登录进入系统。


## 服务端使用教程

### 详细教程

点击查看 [服务端使用教程](https://blog.codingcoder.cn/post/codereviewserverdeploydoc.html)

### 界面示意图

![](https://pics.codingcoder.cn/pics/202307230022440.png)

![](https://pics.codingcoder.cn/pics/202307230022600.png)

![](https://pics.codingcoder.cn/pics/202307230023029.png)

![](https://pics.codingcoder.cn/pics/202307230023684.png)


### 源码获取

配套界面采用vue开发，如果需要定制界面功能，可以从如下途径获取前端源码：

- [Github源码仓库](https://github.com/veezean/CodeReviewServer_Portal)

- [Gitee镜像仓库](https://gitee.com/veezean/CodeReviewServer_Portal)


## 配套IDEA插件

### 通过IDEA plugin marketplace获取并安装

这是最简单的一种方式，在IDEA插件市场搜索安装即可：

![](https://pics.codingcoder.cn/pics/202307230017131.png)

### 源码编译定制开发

您也可以直接获取IDEA配套源码，进行二次开发。点击获取IDEA源码：

- [Github源码仓库](https://github.com/veezean/IntellijIDEA-CodeReview-Plugin)

- [Gitee镜像仓库](https://gitee.com/veezean/IntellijIDEA-CodeReview-Plugin)

## 问题&建议

新版本上线，如果发现有bug或者有功能建议，欢迎提issue单，或者通过公众号`@架构悟道`联系到作者。

当然，如果觉得本软件帮助到了您的工作，也欢迎支持我继续更新维护下去~

![](https://pics.codingcoder.cn/pics/202307231540263.png)