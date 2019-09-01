# 0、 启动客户端和服务端
```
# 测试环境： centos7 jdk8 4核6G

# 服务端启动
java -Xmx4096m -Xms4096m -Dnetease.debug=true -cp netty-all-4.1.32.Final.jar:netty-push-1.0.0.jar com.study.netty.push.server.WebSocketServer

# 客户端
java -Xmx4096m -Xms4096m -Dnetease.debug=false -Dnetease.pushserver.host=192.168.100.241 -cp netty-all-4.1.32.Final.jar:netty-push-1.0.0.jar com.study.netty.push.client.WebSocketClient
```

# 1、 too many openFiles  服务端和测试机都改一下
```
# 进程最大文件打开添加参数最大限制
vi /etc/security/limits.conf  
* soft nofile 1000000
* hard nofile 1000000

# 全局限制 cat /proc/sys/fs/file-nr
echo 1200000 > /proc/sys/fs/file-max

vi /etc/sysctl.conf
fs.file-max = 1000000
```

# 2、 客户端问题汇总
```
# 客户机开不了这么多连接 ，可能的问题原因端口开放数
linux对外随机分配的端口是有限制,理论上单机对外端口数可达65535,但实际对外可建立的连接默认最大只有28232个
查看： cat /proc/sys/net/ipv4/ip_local_port_range
echo "net.ipv4.ip_local_port_range= 1024 65535">> /etc/sysctl.conf
sysctl -p

# 如果你的机器差，出现了奇怪的问题~
sysctl -w net.ipv4.tcp_tw_recycle=1 #快速回收time_wait的连接
sysctl -w net.ipv4.tcp_tw_reuse=1 
sysctl -w net.ipv4.tcp_timestamps=1
```

# 3、 可能的问题
```
# 如果发现自己的用例跑不上去，就看看linux日志
tail -f /var/log/messages
# linux 日志
1、 nf_conntrack: table full, dropping packet 表示防火墙的表满了，加大 nf_conntrack_max 参数
echo "net.nf_conntrack_max = 1000000">> /etc/sysctl.conf

# 2、 TCP: too many orphaned sockets 表示内存不太够，拒绝分配，一般就是TCP缓冲区内存不够用，调大一点
# cat /proc/sys/net/ipv4/tcp_mem 
echo "net.ipv4.tcp_mem = 786432 2097152 16777216">> /etc/sysctl.conf
echo "net.ipv4.tcp_rmem = 4096 4096 16777216">> /etc/sysctl.conf
echo "net.ipv4.tcp_wmem = 4096 4096 16777216">> /etc/sysctl.conf
sysctl -p
```

# 4、  常规监控
```
# 查看某个端口的连接情况
netstat -nat|grep -i "9001"|wc -l
netstat -n | awk '/^tcp/ {++S[$NF]} END {for(a in S) print a, S[a]}'

# 网络接口的带宽使用情况 
#tcpdump https://www.cnblogs.com/maifengqiang/p/3863168.html

# glances工具
yum install -y glances
glances 控制台查看
glances -s 服务器模式查看
```

>如果是自己虚拟机，要记得关闭防火墙
> systemctl stop firewalld.service
