package com.avalony.test;

import com.avalony.config.Configuration;
import com.avalony.factory.SqlSession;
import com.avalony.factory.SqlSessionFactory;

public class AppMain {

    public static void main(String[] args){
        Configuration configuration = new Configuration();
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactory(configuration);
        SqlSession sqlSession = sqlSessionFactory.getSqlSession();
        Object obj = sqlSession.selectOne("com.avalony.dao.UserMapper.select","1");
        System.out.println(obj);
    }
}
