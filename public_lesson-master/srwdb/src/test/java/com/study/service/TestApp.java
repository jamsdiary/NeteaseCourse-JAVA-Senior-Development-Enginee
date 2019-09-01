package com.study.service;

import java.util.ArrayList;
import java.util.List;

import com.study.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.study.entity.Person;
import com.study.mapper.UserMapper;

// -----------
// 如果加入以下代码，所有继承该类的测试类都会遵循该配置，也可以不加，在测试类的方法上
// 这个非常关键，如果不加入这个注解配置，事务控制就会完全失效！
// @Transactional
// 这里的事务关联到配置文件中的事务控制器（transactionManager = "transactionManager"），同时
// 指定自动回滚（defaultRollback = true）。这样做操作的数据才不会污染数据库！
// @TransactionConfiguration(transactionManager = "transactionManager",
// defaultRollback = true)
// ------------
@RunWith(SpringJUnit4ClassRunner.class) // 使用junit4进行测试
@ContextConfiguration(locations = { "classpath:datasource.xml" }) // 加载配置文件
public class TestApp {

	@Autowired
	private com.study.service.UserService userService;
	
	@Autowired
	private UserMapper userMapper;

	@Test
	// @Transactional //标明此方法需使用事务
	// @Rollback(false) //标明使用完此方法后事务不回滚,true时为回滚
	public void test() {

//		List<User> lists = userService.selectUsersBySql(1, 3);
//		for (User user : lists) {
//			System.out.println(user.getId() + "\t" + user.getUserName() + "\t" + user.getUserAddress());
//		}
		
		User user = new User();
		user.setUserName("allen");
		user.setUserAge("18");
		user.setUserAddress("hunan-changsha");
//		int countIns = userService.insertUser(user);
//		System.out.println("插入" + countIns);

		int countDel = userService.deleteUser(user);
		System.out.println("删除" + countDel);
	}
	
	public static void main(String[] args) {
		final String REGEX = ".*insert$|.*delete$|.*update$";
		String sql1 = "user_insert";
		String sql2 = "user_delete";
		String sql3 = "user_update";
		System.out.println(sql1.matches(REGEX));
		System.out.println(sql2.matches(REGEX));
		System.out.println(sql3.matches(REGEX));
	}
	
	@Test
	public void test1() {
		Person p = new Person();
        List<String> list = new ArrayList<String>();
        list.add("足球");
        list.add("排球");
        list.add("音乐");
        list.add("读书");
        p.setInterest(list);
        userMapper.insertPerson(p);
	}
	
	@Test
	public void test2() {
		List<Person> people = userMapper.getPerson();
        for (Person person : people) {
            System.out.println(person);
        }
	}

}
