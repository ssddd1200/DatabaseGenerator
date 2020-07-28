package com.yjs.FX;

import com.yjs.entity.Column;
import com.yjs.entity.Tables;
import com.yjs.helper.GeneratorHepler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 *
 * mybatis-generator V1.0
 */
public class GeneratorDisplay {

    private Map<String, String> baseConfig;
    private Map<String, String> dbConfig;
    private Connection conn;

    public GeneratorDisplay() {
        this.baseConfig = new HashMap<>();
        this.dbConfig = new HashMap<>();
        this.getGeneratorConfig();
        this.createConn();
    }

    private void getGeneratorConfig() {
        System.out.println("正在读取配置.....");
        Properties prop = new Properties();
        String filepath = GeneratorDisplay.class.getResource("/generatorConfig.properties").toString();
        InputStream is = null;
        if (filepath.startsWith("jar:file")){
            try {
                is = new FileInputStream("./conf/config.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            is = GeneratorDisplay.class.getResourceAsStream("/generatorConfig.properties");
        }
        Map<String, String> baseMap = new HashMap<>();
        Map<String, String> dbMap = new HashMap<>();
        try {
            prop.load(is);
            Enumeration en = prop.propertyNames();
            while (en.hasMoreElements()){
                String key = (String) en.nextElement();
                if (key.equals("isTrim") || key.equals("trimValue") || key.equals("url") || key.equals("username")
                        || key.equals("password") || key.equals("dbname") || key.equals("tabname")){
                    dbMap.put(key, prop.getProperty(key));
                }else{
                    baseMap.put(key, prop.getProperty(key));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("读取配置失败.....");
        }
        this.baseConfig.putAll(baseMap);
        this.dbConfig.putAll(dbMap);
        System.out.println("读取配置完成.....");
    }

    private void createConn(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection(this.dbConfig.get("url"),this.dbConfig.get("username"),this.dbConfig.get("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Tables> getTables() throws SQLException {
        Statement statement = this.conn.createStatement();
        String sql = "SELECT TABLE_NAME name, TABLE_COMMENT remark FROM information_schema.TABLES WHERE " +
                     "TABLE_SCHEMA = '"+this.dbConfig.get("dbname")+"' and TABLE_NAME not like 'seq_%'";
        ResultSet rs = statement.executeQuery(sql);
        List<Tables> list = new ArrayList<>();
        while (rs.next()){
            list.add(new Tables(rs.getString("name"), rs.getString("remark")));
        }
        rs.close();
        statement.close();
        return list;
    }

    private List<Column> getColumns(String tableName) throws SQLException {
        Statement statement = this.conn.createStatement();
        String sql = "SELECT COLUMN_NAME name, " +
                "CASE COLUMN_key WHEN 'PRI' THEN 1 ELSE 0 END isKey, " +
                "DATA_TYPE type, COLUMN_COMMENT remark FROM information_schema.COLUMNS" +
                " WHERE TABLE_SCHEMA = '"+this.dbConfig.get("dbname")+"' AND TABLE_NAME = '"+tableName+"' ORDER BY ORDINAL_POSITION ";
        ResultSet rs = statement.executeQuery(sql);
        List<Column> list = new ArrayList<>();
        while (rs.next()){
            Column column = new Column();
            column.setName(rs.getString("name"));
            column.setIsKey(rs.getString("isKey"));
            column.setType(rs.getString("type"));
            column.setRemark(rs.getString("remark"));
            column.setField(rs.getString("name"));
            list.add(column);
        }
        rs.close();
        statement.close();
        return list;
    }

    private void closeConn() throws SQLException {
        this.conn.close();
    }

    private static String setFistUpper(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    private void buildJavaFile(List<Column> columnList, Map<String, String> config) throws Exception {
        GeneratorHepler hepler = new GeneratorHepler();
        hepler.generateCommonFile(config);
        hepler.generateEntityFile(columnList, config);
        hepler.generateControllerFile(columnList, config);
        hepler.generateMapperFile(columnList, config);
        hepler.generateMapperXmlFile(columnList, config);
        hepler.generateServiceFile(columnList, config);
        hepler.generateServiceImplFile(columnList, config);
    }

    public static void main(String[] args) throws Exception {
        GeneratorDisplay display = new GeneratorDisplay();

        if (display.dbConfig.get("tabname").equals("*")){
            List<Tables> tabList = display.getTables();
            for (Tables t : tabList) {
                display.baseConfig.put("tableName", t.getName());
                List<Column> columnList = display.getColumns(t.getName());
                if (Boolean.valueOf(display.dbConfig.get("isTrim"))){
                    display.baseConfig.put("className", setFistUpper(t.getName().replaceAll(display.dbConfig.get("trimValue"),"")));
                }else {
                    display.baseConfig.put("className", setFistUpper(t.getName()));
                }
                display.baseConfig.put("comType","1");
                display.buildJavaFile(columnList, display.baseConfig);
            }
        }else {
            String[] tableNames = display.dbConfig.get("tabname").split(",");
            for (String name: tableNames) {
                System.out.println("正在生成"+name+"表信息.....");
                display.baseConfig.put("tableName", name);
                List<Column> columnList = display.getColumns(name);
                if (Boolean.valueOf(display.dbConfig.get("isTrim"))){
                    display.baseConfig.put("className", setFistUpper(name.replaceAll(display.dbConfig.get("trimValue"),"")));
                }else {
                    display.baseConfig.put("className", setFistUpper(name));
                }
                display.baseConfig.put("comType","1");
                display.buildJavaFile(columnList, display.baseConfig);
            }
        }

        display.closeConn();
    }
}
