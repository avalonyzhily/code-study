package com.avalony.factory;

import com.avalony.config.Configuration;
import com.avalony.config.MappedStatement;
import com.avalony.executor.Executor;
import com.avalony.executor.impl.DefaultExecutor;
import com.avalony.factory.impl.DefaultSqlSession;

import java.util.Map;

public class SqlSessionFactory {

    private Configuration conf;

    public SqlSessionFactory(Configuration conf) {
        this.conf = conf;
    }
    public SqlSession getSqlSession(){
        Executor executor = new DefaultExecutor(conf);
        return new DefaultSqlSession(executor,conf.getMapperMap());
    }
}
