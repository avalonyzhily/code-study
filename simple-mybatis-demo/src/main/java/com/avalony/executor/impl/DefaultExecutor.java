package com.avalony.executor.impl;

import com.avalony.config.Configuration;
import com.avalony.config.MappedStatement;
import com.avalony.executor.Executor;

import java.sql.*;
import java.util.List;

public class DefaultExecutor implements Executor {

    private Configuration conf;

    public DefaultExecutor(Configuration conf) {
        this.conf = conf;
    }

    public <T> List<T> execute(MappedStatement ms, Object args){
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(conf.getDbUrl());
            psmt = conn.prepareStatement(ms.getSqlText());
            //填充参数
            formatParam(psmt,args);
            rs = psmt.executeQuery();
            return handlerResultToEntity(rs,ms.getResultType());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> List<T> handlerResultToEntity(ResultSet rs, String resultType) {
        return null;
    }

    private void formatParam(PreparedStatement psmt, Object args) {
    }
}
