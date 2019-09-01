package com.study.mike.zookeeper;

import java.util.concurrent.CountDownLatch;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

public class MasterElectionDemo {

	static class Server {

		private String cluster, name, address;

		private final String path, value;

		private String master;

		public Server(String cluster, String name, String address) {
			super();
			this.cluster = cluster;
			this.name = name;
			this.address = address;
			path = "/" + this.cluster + "Master";
			value = "name:" + name + " address:" + address;

			ZkClient client = new ZkClient("localhost:2181");
			client.setZkSerializer(new MyZkSerializer());

			new Thread(new Runnable() {

				@Override
				public void run() {
					electionMaster(client);
				}

			}).start();
		}

		public void electionMaster(ZkClient client) {
			try {
				client.createEphemeral(path, value);
				master = client.readData(path);
				System.out.println(value + "创建节点成功，成为Master");
			} catch (ZkNodeExistsException e) {
				master = client.readData(path);
				System.out.println("Master为：" + master);
			}

			// 为阻塞自己等待而用
			CountDownLatch cdl = new CountDownLatch(1);

			// 注册watcher
			IZkDataListener listener = new IZkDataListener() {

				@Override
				public void handleDataDeleted(String dataPath) throws Exception {
					System.out.println("-----监听到节点被删除");
					cdl.countDown();
				}

				@Override
				public void handleDataChange(String dataPath, Object data) throws Exception {

				}
			};

			client.subscribeDataChanges(path, listener);

			// 让自己阻塞
			if (client.exists(path)) {
				try {
					cdl.await();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			// 醒来后，取消watcher
			client.unsubscribeDataChanges(path, listener);
			// 递归调自己（下一次选举）
			electionMaster(client);

		}

	}

	public static void main(String[] args) {
		// 测试时，依次开启多个Server实例java进程，然后停止获取的master的节点，看谁抢到Master
		// Server s = new Server("cluster1", "server1", "192.168.1.11:8991");
		// Server s = new Server("cluster1", "server2", "192.168.1.11:8992");
		Server s = new Server("cluster1", "server3", "192.168.1.11:8993");
		// Server s = new Server("cluster1", "server4", "192.168.1.11:8994");
	}

}
