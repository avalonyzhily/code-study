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

    public <T> T selectOne(String sqlId,Object obj) {
        MappedStatement ms = mappedStatementMap.get(sqlId);
        executor.execute(ms,obj);
        return null;
    }

    public <T> List<T> selectList(String sqlId,Object obj) {
        return null;
    }

    public <T> int insertOne(String sqlId,T t) {
        return 0;
    }
}
