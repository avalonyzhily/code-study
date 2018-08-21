package com.avalony.factory;

import com.avalony.config.Configuration;
import com.avalony.config.MappedStatement;
import com.avalony.executor.Executor;
import com.avalony.executor.impl.DefaultExecutor;
import com.avalony.factory.impl.DefaultSqlSession;

import java.util.Map;

public class SqlSessionFactory {

    private Configuration conf;
    private Map<String,MappedStatement> mappedStatementMap;

    public SqlSessionFactory(Configuration conf) {
        this.conf = conf;
        initMapped();
    }

    private void initMapped() {
    }

    public SqlSession getSqlSession(){
        Executor executor = new DefaultExecutor(conf);
        return new DefaultSqlSession(executor,mappedStatementMap);
    }
}
