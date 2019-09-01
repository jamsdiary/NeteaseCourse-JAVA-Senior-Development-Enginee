# openjdk1.8作为基础镜像
FROM openjdk:8-jdk-alpine

# 作者
MAINTAINER study.163.com <422539792@qq.com>

# 添加了一个VOLUME指向”/tmp“的内容，因为这是Spring Boot应用程序默认为Tomcat创建工作目录的地方。
# 效果是在"/var/lib/docker"下的主机上创建一个临时文件，并将其链接到"/tmp"下的容器。
# 对于我们在此处编写的简单应用程序，此步骤是可选的，但如果需要在文件系统中实际编写，则对于其他Spring Boot应用程序可能是必需的。
VOLUME /tmp

# 设置参数
ARG JAR_FILE=target/*.jar

# 建议使用copy而非工具强大的add
COPY ${JAR_FILE} app.jar

# 如果有权限问题
# RUN chmod -R 777 /app.jar

# 该命令用于声明在运行时容器提供服务的端口。
# 注意：这只是一个声明，运行时并不会因为该声明就打开相应的端口。
# 该指令的作用是帮助镜像使用者理解该镜像服务的守护端口；其次是当运行时使用随机映射时（即run命令的-P参数），会自动映射EXPORT的端口。
EXPOSE 9777

# 程序切入点，为了减少Tomcat的启动时间，我们添加了一个指向“/dev/urandom”的系统属性
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

##################################
# 另一种方式，如果jar太大可以选择使用挂载外置的jar方式
##################################
#ADD cloud-auth.jar /root/docker/cloud-platform/build/app.jar
# 外挂盘
#VOLUME /root/docker/cloud-platform/build/
# 工作目录
#WORKDIR /root/docker/cloud-platform/