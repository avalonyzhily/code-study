package com.avalony.factory.impl;

import com.avalony.config.MappedStatement;
import com.avalony.executor.Executor;
import com.avalony.factory.SqlSession;

import java.util.List;
import java.util.Map;

public class DefaultSqlSession implements SqlSession {
    private Executor executor;
    private Map<String,MappedStatement> mappedStatementMap;

    public DefaultSqlSession(Executor executor,Map<String,MappedStatement> mappedStatementMap) {
        this.executor = executor;
        this.mappedStatementMap = mappedStatementMap;
    }

    public List<Map<String, Object>> selectOne(String sqlId,Object obj) {
        MappedStatement ms = mappedStatementMap.get(sqlId);
        return executor.execute(ms,obj);
    }

    public <T> List<T> selectList(String sqlId,Object obj) {
        return null;
    }

    public <T> int insertOne(String sqlId,T t) {
        return 0;
    }
}
