package com.cyp.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cyp.pojo.User;

public class MyBatisService {
	// 根据Id查询用户信息，得到一条记录结果
	String resource = "SqlMapConfig.xml";
	InputStream inputStream = null;
	SqlSession sqlSession = null;

	private Logger logger = Logger.getLogger(MyBatisService.class);

	@Before
	public void init() {
		try {
			logger.info("初始化开始");
			inputStream = Resources.getResourceAsStream(resource);
			logger.info("1.创建会话工场,传入mybatis的配置文件信息");
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
					.build(inputStream);
			logger.info("2.通过工厂得到SqlSession");
			sqlSession = sqlSessionFactory.openSession();
			logger.info("初始化完成");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	@After()
	public void release() {
		if (sqlSession != null) {
			sqlSession.close();
			logger.info("关闭Session");
		}
		if (inputStream != null) {
			try {
				inputStream.close();
				logger.info("inputStream");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void findUserByIdTest() {
		User user = sqlSession.selectOne("test.findUserById", 1);
		logger.info(user.toString());
	}

	// 根据姓名模糊查询用户信息，得到一条或多条记录结果
	@Test
	public void findUserByNameTest() {

		List<User> list = sqlSession.selectList("test.findUserByName", "小");
		System.out.println(list);
		logger.info(list);

	}

	// 添加用户
	@Test
	public void insertUserTest() {
		User user = new User();
		user.setUserName("小红");
		user.setBirthday(new Date());
		user.setSex("1");
		user.setAddress("上海");
		sqlSession.insert("test.insertUser", user);
		// 执行提交事务
		logger.info("//执行提交事务");
		sqlSession.commit();

		logger.info("//项目中经常需要 获取新增的用户的主键");
		logger.info(user.getId());
	}

	// 根据Id删除用户
	@Test
	public void deleteUserTest() {
		sqlSession.delete("test.deleteUser", 7);
		logger.info("提交删除");
		sqlSession.commit();
	}

	// 根据Id更新用户信息
	@Test
	public void updateUserTest() {

		User user = new User();
		user.setId(2);
		user.setUserName("小黑");
		user.setBirthday(new Date());
		user.setSex("2");
		user.setAddress("上海");

		sqlSession.update("test.updateUser", user);
		logger.info("//执行提交事务");
		sqlSession.commit();

	}
}