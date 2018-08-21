package com.avalony.config;

import java.io.IOException;
import java.util.Properties;

/**
 * simpleMybatis 配置类
 */
public class Configuration {

    private String driverClass;
    private String dbUrl;
    private String userName;
    private String password;

    private static final String CONFIG_PATH = "application.properties";

    public Configuration() {
        Properties prop = new Properties();
        try {
            prop.load(this.getClass().getClassLoader().getResourceAsStream(CONFIG_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.driverClass = prop.getProperty("db.driver");
        this.dbUrl = prop.getProperty("db.url");
        this.userName = prop.getProperty("db.userName");
        this.password = prop.getProperty("db.password");
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
