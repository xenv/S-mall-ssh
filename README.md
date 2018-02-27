# 小小商城系统  - SSH版

练手JavaWEB项目，本版本为SSH版（实现了SSM版95%的功能），实现了BaseService对Service层的大部分方法的抽象，通过拦截器实现了方法级粒度的鉴权

---------------------------

**演示**(SSM版)：[http://small.ડ.com/][1]  
可自行注册账号，或使用后台查看权限账号 demo 密码 demo （后台入口登陆后显示）  
  
兄弟项目：  
[SSM版（完整版）][3]  
[Servlet版（实现了SSM版85%功能）][2]  

----------------------------

本项目的亮点：

 * 功能齐全，页面丰富，实现了小商城的大部分功能
 * 前端仿天猫2017页面，基于原生CSS（前台）、Bootstrap（后台）、Jquery、Bootstrap Js构建
 * 本项目为Maven项目，后端使用 Spring 4 + Struts 2 + Hibernate 4
 * 实现了一个 BaseService 类，涵盖了90% 的 Service 方法，各个 Service 只需写少量代码即可
 * 通过拦截器和自定义注解实现了方法级粒度的用户鉴权
  

功能： 

 - [x] 首页、分类页、搜索页、产品页
 - [x] 购物车页面、下单页、支付页及支付成功页
 - [x] 我的订单页、确认收货及成功页、评价页
 - [x] 登陆页、注册页
 - [x] 全部数据库的后台可视化管理
 - [x] 网站SEO设置、图片路径设置

------------------
 
 安装使用：
 
  1. 若使用IDE打开，需按 Maven 文件安装依赖
  2. 若在Tomcat中部署，Maven文件中已经配置好直接在线部署，使用 maven tomcat7:deploy 可直接在线部署 （需先配置好Tomcat）
  3. 导入数据库small.sql，在 \src\main\resources\applicationContext.xml 中配置数据库
  4. 默认后台地址 /admin ，账户密码为 admin 123456 ，新建用户在前台注册，需要后台权限需要在数据库的User表的group_列中将该用户的用户组设置为 superAdmin
  5. JDK >= 1.8、数据库 Mysql


  [1]: http://small.ડ.com
  [2]: https://github.com/xenv/S-mall-servlet
  [3]: https://github.com/xenv/S-mall-ssm