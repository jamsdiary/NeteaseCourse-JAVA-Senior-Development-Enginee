三个业务数据库：
ag_admin_v1
ag_auth_v1
course_classroom
先创建这三个数据库（utf8）,然后在相应的数据库中执行sql脚本。

nacos_config.sql为 nacos的数据库表文件，其中包含了各微服的配置数据，nacos的版本为1.0版本
通过导入此sql可以直接导入各微服务的配置项

