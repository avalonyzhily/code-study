package com.avalony.config;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.omg.CORBA.Object;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * simpleMybatis 配置类
 */
public class Configuration {

    private String driverClass;
    private String dbUrl;
    private String userName;
    private String password;

    private static final String CONFIG_PATH = "application.properties";
    private static final String XML_PATH = "mapper/UserMapper.xml";

    private Map<String,MappedStatement> mapperMap = new ConcurrentHashMap<String, MappedStatement>();

    public Configuration() {
        initProperties();
        initMapper();
    }

    private void initMapper() {
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(this.getClass().getClassLoader().getResourceAsStream(XML_PATH));
            Element rootElement = document.getRootElement();
            Attribute namespaceAttr = rootElement.attribute("namespace");
            String namespace = "";
            if(null != namespaceAttr){
                namespace += namespaceAttr.getValue();
            }
            List<Element> elements = rootElement.elements();
            for(Element el : elements){
                MappedStatement mappedStatement = new MappedStatement();
                String key = namespace+".";
                Attribute id = el.attribute("id");
                if(null != id){
                    String idStr = id.getValue();
                    key += idStr;
                    mappedStatement.setSqlId(key);
                }else {
                    System.out.println("not id attribute");
                    continue;
                }
                String sqlType = el.getName();
                mappedStatement.setSqlType(sqlType);
                Attribute  parameterType = el.attribute("parameterType");
                if(null != parameterType){
                    String parameterTypeStr = parameterType.getValue();
                    mappedStatement.setParameterType(parameterTypeStr);
                }
                Attribute  resultType = el.attribute("resultType");
                if(null != resultType){
                    String resultTypeStr = resultType.getValue();
                    mappedStatement.setResultType(resultTypeStr);
                }
                String sql = el.getTextTrim();
                if(null != sql && !Objects.equals("",sql)){
                    mappedStatement.setSqlText(sql);
                }
                mapperMap.put(key,mappedStatement);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void initProperties() {
        Properties prop = new Properties();
        try {
            prop.load(this.getClass().getClassLoader().getResourceAsStream(CONFIG_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.driverClass = prop.getProperty("db.driver");
        this.dbUrl = prop.getProperty("db.url");
        this.userName = prop.getProperty("db.username");
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

    public Map<String, MappedStatement> getMapperMap() {
        return mapperMap;
    }

    public void setMapperMap(Map<String, MappedStatement> mapperMap) {
        this.mapperMap = mapperMap;
    }
}
