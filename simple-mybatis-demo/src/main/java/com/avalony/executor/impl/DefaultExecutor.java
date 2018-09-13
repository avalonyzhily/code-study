package com.avalony.executor.impl;

import com.avalony.config.Configuration;
import com.avalony.config.MappedStatement;
import com.avalony.executor.Executor;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultExecutor implements Executor {

    private Configuration conf;

    public DefaultExecutor(Configuration conf) {
        this.conf = conf;
    }

    public List<Map<String, Object>> execute(MappedStatement ms, Object args){
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(conf.getDbUrl(),conf.getUserName(),conf.getPassword());
            String sqlStr = ms.getSqlText();
            String sqlstr = sqlStr.replace("#{id}","?");
            psmt = conn.prepareStatement(sqlstr);
            //填充参数
            formatParam(psmt,args);
            rs = psmt.executeQuery();
            return handlerResultToEntity(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                rs.close();
                psmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private  List<Map<String, Object>> handlerResultToEntity(ResultSet rs) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            ResultSetMetaData md = rs.getMetaData(); //获得结果集结构信息,元数据
            int columnCount = md.getColumnCount();   //获得列数
            while (rs.next()) {
                Map<String,Object> rowData = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void formatParam(PreparedStatement psmt,Object args) {
        try {
            psmt.setString(1,(String)args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
