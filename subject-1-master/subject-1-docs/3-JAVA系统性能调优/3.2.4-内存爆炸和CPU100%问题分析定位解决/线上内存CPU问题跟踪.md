[TOC]

# 1、资料
```
网易性能调优案例我这边的文档：
http://note.youdao.com/noteshare?id=4e896cdc93694894bccd58174e1bda86&sub=03BA0EFE576247FBB7850155102B2A8D

另外还有一篇也是fullGC相关的同事写的分享文档：
https://note.youdao.com/ynoteshare1/index.html?id=626a6d6f768e180c693911d26bca8318&type=note#/
```

# 2、工具
```
http://www.eclipse.org/mat/
https://www.cnblogs.com/trust-freedom/p/6744948.html
https://www.jianshu.com/p/82b25cf8cfde

监控工具cat参考资料 http://www.iigrowing.cn/cat_tong_yi_jian_kong_ping_tai_jian_dan_shi_yong.html
https://github.com/dianping/cat
```

# 3、 GC日志
```
GC：

表明进行了一次垃圾回收，前面没有Full修饰，表明这是一次Minor GC ,注意它不表示只GC新生代，并且现有的不管是新生代还是老年代都会STW。

Allocation Failure：

表明本次引起GC的原因是因为在年轻代中没有足够的空间能够存储新的数据了。

ParNew：

    表明本次GC发生在年轻代并且使用的是ParNew垃圾收集器。ParNew是一个Serial收集器的多线程版本，会使用多个CPU和线程完成垃圾收集工作（默认使用的线程数和CPU数相同，可以使用-XX：ParallelGCThreads参数限制）。该收集器采用复制算法回收内存，期间会停止其他工作线程，即Stop The World。

367523K->1293K(410432K)：单位是KB

三个参数分别为：GC前该内存区域(这里是年轻代)使用容量，GC后该内存区域使用容量，该内存区域总容量。

0.0023988 secs：

    该内存区域GC耗时，单位是秒

522739K->156516K(1322496K)：

三个参数分别为：堆区垃圾回收前的大小，堆区垃圾回收后的大小，堆区总大小。

0.0025301 secs：

该内存区域GC耗时，单位是秒

[Times: user=0.04 sys=0.00, real=0.01 secs]：

    分别表示用户态耗时，内核态耗时和总耗时

 

分析下可以得出结论：

    该次GC新生代减少了367523-1293=366239K

    Heap区总共减少了522739-156516=366223K

    366239 – 366223 =17K，说明该次共有17K内存从年轻代移到了老年代，可以看出来数量并不多，说明都是生命周期短的对象，只是这种对象有很多。

    我们需要的是尽量避免Full GC的发生，让对象尽可能的在年轻代就回收掉，所以这里可以稍微增加一点年轻代的大小，让那17K的数据也保存在年轻代中。
```

# 4、统一监控测试
```
java -Xmx512m -server -Xmx512m -server -verbose:gc -XX:+PrintGCDetails -cp om-demo-1.0.0.jar:netty-all-4.0.24.Final.jar:log4j-1.2.14.jar:cat-client-3.0.0.jar com.study.jvm.FullGCDemo1
```

# 5、jstack日志分析
```
线程dump信息说明：
elasticsearch[Native][merge][T#1] 是我们为线程起的名字
daemon 表示线程是否是守护线程
prio 表示我们为线程设置的优先级
os_prio 表示的对应的操作系统线程的优先级，由于并不是所有的操作系统都支持线程优先级，所以可能会出现都置为0的情况
tid 是java中为这个线程的id
nid 是这个线程对应的操作系统本地线程id，每一个java线程都有一个对应的操作系统线程
wait on condition表示当前线程处于等待状态，但是并没列出具体原因
java.lang.Thread.State: WAITING (parking) 也是表示的处于等待状态，括号中的内容说明了导致等待的原因，例如这里的parking说明是因为调用了 LockSupport.park方法导致等待
```

# 6、 堆外内存泄漏
```
https://gitbook.cn/books/5c47c4e291e0c40c7a462f05/
https://www.jianshu.com/p/cff037edb750

https://github.com/gperftools/gperftools
下载libunwind
wget http://ftp.twaren.net/Unix/NonGNU/libunwind/libunwind-1.1.tar.gz
tar -xvf libunwind-1.1.tar.gz
cd libunwind-1.1
./configure --prefix=/usr/local/tools/libunwind/ CFLAGS=-U_FORTRIFY_SOURCE
make
make install 

# 下载 https://github.com/gperftools/gperftools/releases
wget https://github.com/gperftools/gperftools/releases/download/gperftools-2.7/gperftools-2.7.tar.gz
tar -xvf gperftools-2.7.tar.gz
cd gperftools-2.7
./configure --prefix=/usr/local/tools/gperftools LDFLAGS=-L/usr/local/tools/libunwind/lib CPPFLAGS=-L/usr/local/tools/libunwind/include
make
make install 

# 使用
# 设定内存申请器
export LD_PRELOAD=/usr/local/tools/gperftools/lib/libtcmalloc.so
# 指定内存分析结果存放路径
mkdir /tmp/gperftool-heap
export HEAPPROFILE=/tmp/gperftool-heap/heap

# 结果分析
 /usr/local/tools/gperftools/bin/pprof --text $JAVA_HOME/bin/java /tmp/gperftool-heap/heap.0003.heap > report.txt


# 调用链
wget https://github.com/btraceio/btrace/releases/download/v1.3.11.3/btrace-bin-1.3.11.3.tgz
mkdir /usr/local/tools/btrace-bin-1.3.11.3
mv btrace-bin-1.3.11.3.tgz /usr/local/tools/btrace-bin-1.3.11.3/btrace-bin-1.3.11.3.tgz
cd  /usr/local/tools/btrace-bin-1.3.11.3
tar -xvf btrace-bin-1.3.11.3.tgz
rm -rf btrace-bin-1.3.11.3.tgz

./btrace -cp /usr/local/tools/btrace-bin-1.3.11.3/build/ 6358 /tmp/test/TraceMethodCaller.java 
或者 jvisualVM插件Btrace

```


#