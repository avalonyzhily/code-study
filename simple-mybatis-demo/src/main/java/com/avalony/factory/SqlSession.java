package com.avalony.factory;

import java.util.List;

public interface SqlSession {
    <T extends Object> T selectOne(String sqlId,Object obj);

    <T extends Object> List<T> selectList(String sqlId,Object obj);

    <T> int insertOne(String sqlId,T t);
}
