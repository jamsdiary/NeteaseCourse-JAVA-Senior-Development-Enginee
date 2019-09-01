FROM docker.elastic.co/logstash/logstash:6.4.0
#安装input插件
RUN /usr/share/logstash/bin/logstash-plugin install logstash-input-jdbc
#安装filter插件
RUN /usr/share/logstash/bin/logstash-plugin install logstash-filter-aggregate
RUN /usr/share/logstash/bin/logstash-plugin install logstash-filter-jdbc_streaming
RUN /usr/share/logstash/bin/logstash-plugin install logstash-filter-mutate
#安装output插件
RUN logstash-plugin install logstash-output-elasticsearch
COPY ./mysql-connector-java-5.1.46.jar /usr/share/logstash/mysql-connector-java.jar