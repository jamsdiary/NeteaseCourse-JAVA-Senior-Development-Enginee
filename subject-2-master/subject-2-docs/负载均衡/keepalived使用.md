[TOC]

# 前提-配置java环境变量

# tomcat
```
# 下载
curl "http://mirror.bit.edu.cn/apache/tomcat/tomcat-8/v8.5.31/bin/apache-tomcat-8.5.31.tar.gz" -o apache-tomcat-8.5.31.tar.gz

# 解压
tar -xvf apache-tomcat-8.5.31.tar.gz 

# 启动
./apache-tomcat-8.5.31/bin/startup.sh
```

# nginx
## 下载及解压 相关的模块到/u01目录
```
curl "http://nginx.org/download/nginx-1.14.0.tar.gz" -o nginx-1.14.0.tar.gz
tar -xvf nginx-1.14.0.tar.gz

curl "http://www.zlib.net/zlib-1.2.11.tar.gz" -o zlib-1.2.11.tar.gz
tar -xvf zlib-1.2.11.tar.gz

curl "https://jaist.dl.sourceforge.net/project/pcre/pcre/8.41/pcre-8.41.tar.gz" -o pcre-8.41.tar.gz
tar -xvf pcre-8.41.tar.gz

curl "https://www.openssl.org/source/openssl-1.0.2o.tar.gz" -o openssl-1.0.2o.tar.gz
tar -xvf openssl-1.0.2o.tar.gz

curl "http://labs.frickle.com/files/ngx_cache_purge-2.3.tar.gz" -o ngx_cache_purge-2.3.tar.gz
tar -xvf ngx_cache_purge-2.3.tar.gz
```

## 编译安装nginx
```
cd nginx-1.14.0

./configure --add-module=../ngx_cache_purge-2.3 --prefix=/usr/local/nginx --with-http_ssl_module --with-pcre=../pcre-8.41 --with-zlib=../zlib-1.2.11 --with-openssl=../openssl-1.0.2o

make 

make install
```

> 如果有异常：./configure: error: C compiler cc is not found，则安装gcc等软件

``` yum -y install gcc gcc-c++ autoconf automake make ```

# keepalived
## 下载安装相关相关的组件
```
yum -y install openssl-devel 
yum -y install libnl libnl-devel
yum install -y libnfnetlink-devel
```

## 下载安装keepalived
```
# 下载
curl "http://www.keepalived.org/software/keepalived-1.4.4.tar.gz" -o keepalived-1.4.4.tar.gz
tar -xvf keepalived-1.4.4.tar.gz

cd keepalived-1.4.4  

# 安装到/usr/local/keepalived目录
./configure --prefix=/usr/local/keepalived --sysconf=/etc  
make && make install
```

## 配置文件存放地址 
> 配置放在/etc/keepalived/，三份配置文件（一个nginx_monitor监控脚本，主备各一份keepalived配置）
### nginx监控shell脚本 nginx_monitor.sh 文件
```
# 创建nginx monitor 脚本，并赋予可执行权限
chmod +x /etc/keepalived/nginx_monitor.sh
# 测试一下脚本能不能执行
执行命令：/etc/keepalived/nginx_monitor.sh 
没报错即表示为成功
```

### keepalived配置
```
# - master主机
keepalived-nginx-master.conf
# - backup备机
keepalived-nginx-backup.conf

```

## 启动keepalived
```
# 启动master主机
/usr/local/keepalived/sbin/keepalived -f /etc/keepalived/keepalived-nginx-master.conf
# 启动backup备机
/usr/local/keepalived/sbin/keepalived -f /etc/keepalived/keepalived-nginx-backup.conf
```

## 停止
```
ps -ef | grep keepalived
kill -9 关闭相关的进程
```

## 测试
```
1. 关掉备机，功能完全不受影响。
2. 关掉主机，虚拟IP漂移到备机，备机开始工作。
3. 关掉主机nginx，主机监控到无nginx后，自动切换
```