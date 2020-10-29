# 《Java开发企业级权限管理系统》原生实现的权限管理系统

## Spring Security Demo 项目
https://gitee.com/jiminzheng/springsecurity_demo

## Apache Shiro Demo 项目
https://gitee.com/jiminzheng/shiro_demo

## 课程地址
https://coding.imooc.com/class/149.html

## 讲师其他课程推荐：Java并发编程与高并发解决方案
https://coding.imooc.com/class/195.html

## 手记推荐
### 课程问题汇总， 持续更新...
http://www.imooc.com/article/21449

温馨提示：同时看一下留言里贴的问题（目前修改时总提示有敏感词语，无法更新，就暂且在留言区更新了）

### 课程知识点索引
http://www.imooc.com/article/21443

### 改造电商交易后台权限管理过程
https://www.imooc.com/article/20741

### 数据权限通用设计方案
https://www.imooc.com/article/21376


## 项目启动注意事项

### 数据库配置
/resources/settings.properties
### redis配置
/resources/redis.properties
### 项目登录页
/signin.jsp
### 登录使用用户名和密码：
username: admin@qq.com

password: 12345678

同时，项目名建议设置为/

## 其他
### 如果暂时不想使用redis，如何移除
1）applicationContext.xml里移除 <import resource="redis.xml" />

2）修改RedisPool.java 类取消被spring管理

3）修改SysCacheService.java 类移除RedisPool.java的使用


### 如果想在正式环境使用，需要注意哪些

1）如果是集群部署，需要配置session共享，保证登录一次就可以，体验好

可以参考一下：http://blog.csdn.net/pingnanlee/article/details/68065535

2）确认一下项目里超级管理员的权限的规则

代码位置：SysCoreService.java类里的isSuperAdmin()

3）新增管理员的密码处理

SysUserService.java里的save() 方法里需要移除 password = "12345678";

同时，MailUtil里的发信参数要补全，并在SysUserService.java里的save()里 sysUserMapper.insertSelective(user) 之前调用

这是默认给的逻辑，可以根据项目实际情况调整
