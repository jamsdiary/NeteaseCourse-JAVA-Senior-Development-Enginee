package com.study.kafka.jmx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: allen
 * @Date: 2019/2/20 17:06
 */
public class KafkaJmxConnection {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private MBeanServerConnection conn;
    /**
     * 默认连接的ip和端口号
     */
    private String ipAndPort = "192.168.100.249:9999";

    public KafkaJmxConnection(String ipAndPort){
        this.ipAndPort = ipAndPort;
    }

    public boolean init() throws Exception {
        String jmxURL = "service:jmx:rmi:///jndi/rmi://" +ipAndPort+ "/jmxrmi";
        log.info("init jmx, jmxUrl: {}, and begin to connect it",jmxURL);
        try {
            // 初始化连接jmx
            JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);
            JMXConnector connector = JMXConnectorFactory.connect(serviceURL, null);
            conn = connector.getMBeanServerConnection();
            if(conn == null){
                log.error("getValue connection return null!");
                return  false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getName(String metric, String topicName){
        if (topicName==null){
            return metric;
        }
        // 一些metric与topic有关, 如果要指定具体的topic的话
        return metric + ",topic=" + topicName;
    }

    public Map<String, Object> getValue(String topicName, String metric, Collection<String> attrs){
        ObjectName objectName;
        try {
            objectName = new ObjectName(this.getName(metric, topicName));
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
            return null;
        }

        Map<String, Object> result = new HashMap<>();
        // 遍历所有属性, 获取每个属性的结果, 并将属性和属性对应的结果保存到map中
        for(String attr:attrs){
            result.put(attr, getAttribute(objectName, attr));
        }
        return result;
    }

    public Map<String, Object> getValue(String metric, Collection<String> attrs){
        return getValue(null, metric, attrs);
    }

    private Object getAttribute(ObjectName objName, String objAttr){
        if(conn== null){
            log.error("jmx connection is null");
            return null;
        }

        try {
            return conn.getAttribute(objName,objAttr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}