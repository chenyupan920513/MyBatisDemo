package com.cyp.service;


import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.cyp.pojo.User;

public class UserTest {
    // 根据Id查询用户信息，得到一条记录结果
    @Test
    public void findUserByIdTest() {
        // mybatis的配置文件
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = null;
        SqlSession sqlSession = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            // 1.创建会话工场,传入mybatis的配置文件信息
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
                    .build(inputStream);

            // 2.通过工厂得到SqlSession
            sqlSession = sqlSessionFactory.openSession();

            // 3.通过sqlSession操作数据库
            // 第一个参数：映射文件中的statement的Id,等于namespace + "." + statement的id;
            // 第二个参数:指定和映射文件中所匹配的parameterType类型的参数;
            // sqlSession.selectOne结果是与映射文件所匹配的resultType类型的对象;
            // selectOne：查询一条结果
            User user = sqlSession.selectOne("test.findUserById", 1);
            System.out.println(user.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 根据姓名模糊查询用户信息，得到一条或多条记录结果
    @Test
    public void findUserByNameTest() {
        // mybatis的配置文件
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = null;
        SqlSession sqlSession = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            // 1.创建会话工场,传入mybatis的配置文件信息
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
                    .build(inputStream);

            // 2.通过工厂得到SqlSession
            sqlSession = sqlSessionFactory.openSession();

            // 3.通过sqlSession操作数据库
            // 第一个参数：映射文件中的statement的Id,等于namespace + "." + statement的id;
            // 第二个参数:指定和映射文件中所匹配的parameterType类型的参数;
            // sqlSession.selectOne结果是与映射文件所匹配的resultType类型的对象;
            // list中的user和resultType类型一致
            List<User> list = sqlSession.selectList("test.findUserByName", "小");
            System.out.println(list);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 添加用户
    @Test
    public void insertUserTest() {
        // mybatis的配置文件
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = null;
        SqlSession sqlSession = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            // 1.创建会话工场,传入mybatis的配置文件信息
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
                    .build(inputStream);
            // 2.通过工厂得到SqlSession
            sqlSession = sqlSessionFactory.openSession();
            //插入用户的对象
            User user = new User();
            user.setUserName("小红");
            user.setBirthday(new Date());
            user.setSex("1");
            user.setAddress("上海");
            // 3.通过sqlSession操作数据库
            // 第一个参数：映射文件中的statement的Id,等于namespace + "." + statement的id;
            // 第二个参数:指定和映射文件中所匹配的parameterType类型的参数;
            // sqlSession.selectOne结果是与映射文件所匹配的resultType类型的对象;
            sqlSession.insert("test.insertUser", user);
            //执行提交事务
            sqlSession.commit();
            
            //项目中经常需要 获取新增的用户的主键
            System.out.println(user.getId());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    // 根据Id删除用户
        @Test
        public void deleteUserTest() {
            // mybatis的配置文件
            String resource = "SqlMapConfig.xml";
            InputStream inputStream = null;
            SqlSession sqlSession = null;
            try {
                inputStream = Resources.getResourceAsStream(resource);
                // 1.创建会话工场,传入mybatis的配置文件信息
                SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
                        .build(inputStream);
                // 2.通过工厂得到SqlSession
                sqlSession = sqlSessionFactory.openSession();
                // 3.通过sqlSession操作数据库
                // 第一个参数：映射文件中的statement的Id,等于namespace + "." + statement的id;
                // 第二个参数:指定和映射文件中所匹配的parameterType类型的参数;
                // sqlSession.selectOne结果是与映射文件所匹配的resultType类型的对象;
                //传入Id，删除用户
                sqlSession.delete("test.deleteUser", 7);
                //执行提交事务
                sqlSession.commit();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (sqlSession != null) {
                    sqlSession.close();
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        
        // 根据Id更新用户信息
        @Test
        public void updateUserTest() {
            // mybatis的配置文件
            String resource = "SqlMapConfig.xml";
            InputStream inputStream = null;
            SqlSession sqlSession = null;
            try {
                inputStream = Resources.getResourceAsStream(resource);
                // 1.创建会话工场,传入mybatis的配置文件信息
                SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
                        .build(inputStream);
                // 2.通过工厂得到SqlSession
                sqlSession = sqlSessionFactory.openSession();
                //更新用户的信息
                User user = new User();
                user.setId(2);
                user.setUserName("小黑");
                user.setBirthday(new Date());
                user.setSex("2");
                user.setAddress("上海");
                // 3.通过sqlSession操作数据库
                // 第一个参数：映射文件中的statement的Id,等于namespace + "." + statement的id;
                // 第二个参数:指定和映射文件中所匹配的parameterType类型的参数;
                // sqlSession.selectOne结果是与映射文件所匹配的resultType类型的对象;
                //更具Id更新用户
                sqlSession.update("test.updateUser", user);
                //执行提交事务
                sqlSession.commit();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (sqlSession != null) {
                    sqlSession.close();
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
}