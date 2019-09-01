package com.study.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

/**
 * 快速插入大量数据
 * 
 * @author allen
 *
 */
public class InsertTest2 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		final String url = "jdbc:mysql://allen.aliyun.com:3306/sharding_db?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
		final String name = "com.mysql.jdbc.Driver";
		final String user = "root";
		final String password = "root@123456";
		Connection conn = null;
		Class.forName(name);// 指定连接类型
		conn = DriverManager.getConnection(url, user, password);// 获取连接
		if (conn != null) {
			System.out.println("获取连接成功");
			insert(conn);
		} else {
			System.out.println("获取连接失败");
		}
	}

	/**
	 * 多线程批量插入
	 * @param conn
	 */
	public static void insert(Connection conn) {
		// 开始时间
		Long begin = new Date().getTime();
		// sql前缀
		String prefix = "INSERT INTO tb_goods_info (goods_id, goods_title, goods_desc, goods_comment, goods_ext_info, goods_price, version) VALUES ";
		try {
			// 保存sql后缀
			StringBuffer suffix = new StringBuffer();
			// 设置事务为非自动提交
			conn.setAutoCommit(false);
			// 比起st，pst会更好些
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement("");// 准备执行语句
			// 外层循环，总提交事务次数
			for (int i = 1; i <= 100; i++) {
				suffix = new StringBuffer();
				// 第j次提交步长
				for (int j = 1; j <= 1000; j++) {
					// 构建SQL后缀
					suffix.append("('id-"+i+j+"', '标题-"+i+j+"', '穿了它回头率100%', NULL, '"+j+".0', 0),");
				}
				// 构建完整SQL
				String sql = prefix + suffix.substring(0, suffix.length() - 1);
				// 添加执行SQL
				pst.addBatch(sql);
				// 执行操作
				pst.executeBatch();
				// 提交事务
				conn.commit();
				// 清空上一次添加的数据
				suffix = new StringBuffer();
			}
			// 头等连接
			pst.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 结束时间
		Long end = new Date().getTime();
		// 耗时
		System.out.println("1000万条数据插入花费时间 : " + (end - begin) / 1000 + " s");
		System.out.println("插入完成");
	}
}

/**
 * <pre>
DROP TABLE IF EXISTS `t_teacher`;
CREATE TABLE IF NOT EXISTS `t_teacher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `t_name` varchar(32) DEFAULT NULL,
  `t_password` varchar(32) DEFAULT NULL,
  `sex` varchar(2) DEFAULT NULL,
  `description` varchar(32) DEFAULT NULL,
  `pic_url` varchar(32) DEFAULT NULL,
  `school_name` varchar(32) DEFAULT NULL,
  `regist_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `remark` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000004 DEFAULT CHARSET=utf8;
 * </pre>
 */