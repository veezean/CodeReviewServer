# CodeReviewServer

> 
> 该Server端构建的时候，为尽快构建完成，简化界面部分开发，采用了Erupt前后端一体化框架进行界面生成。使用了一段时间发现较多定制化想法难以实施，所以目前正在使用SpringBoot + Vue 构建一个更完善的后端管理界面，计划4月初会更新，如有需要，请关注。
> 


本仓库为IDEA代码检视插件配套使用的服务端。支持从IDEA插件中将评审意见上传到服务端，或者从服务端拉取别人提交的评审意见下来，以及维护评审人员、项目信息、历史评审意见、评审意见确认信息等等功能。

本服务端需要配合IDEA插件一起使用，插件可以从下面信息中获取。


**配套IDEA插件**

本仓库的服务端代码，配套IDEA代码检视插件进行使用，插件可从如下地址获取：

- [github仓库地址](https://github.com/veezean/IntellijIDEA-CodeReview-Plugin)

- [gitee仓库地址](https://gitee.com/veezean/IntellijIDEA-CodeReview-Plugin)

**版本说明**

本服务端属于前后端一体化部署，提供了统一服务端，用于团队协作场景，可以对用户、部门、项目以及评审记录进行一个统一的管理。服务端还支持定制统一的评审字段信息，然后下发给所有的客户端进行配置更新，保证团队内按照统一的要求进行代码检视动作。

![](https://pics.codingcoder.cn/pics/202303122311867.png)

![](https://pics.codingcoder.cn/pics/202303122311188.png)

**部署方式**

1. 新建MySQL数据库，然后将sql目录下的数据库初始化脚本刷入
2. 修改`application-PROD.properties`配置文件中的数据库连接信息
3. 启动SpringBoot服务
4. 打开浏览器，访问 `http://127.0.0.1:23560` 即可访问。初始登录信息：
    >     用户名：    codereview
    >     密码：      123456
5. 如需访问服务端接口文档，访问 `http://127.0.0.1:23560/swagger-ui/index.html`

**问题&建议**

新版本上线，如果发现有bug或者有功能建议，欢迎提issue单，或者通过公众号`@架构悟道`联系到作者。
