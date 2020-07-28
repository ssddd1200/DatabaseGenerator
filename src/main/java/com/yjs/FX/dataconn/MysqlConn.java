package com.yjs.FX.dataconn;

import com.yjs.entity.Column;
import com.yjs.entity.Tables;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlConn {

    private Connection conn;

    public MysqlConn(){

    }

    public void createConn(String dburl, String dbname, String username, String password) throws SQLException  {

        String url = String.format("jdbc:mysql://%s/%s?useUnicode=true&characterEncoding=utf-8&&serverTimezone=UTC&allowMultiQueries=true&useSSL=false", dburl, dbname);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void discoryConn(){
        try {
            this.conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Tables> getTables(String dbname) throws SQLException {
        Statement statement = this.conn.createStatement();
        String sql = "SELECT TABLE_NAME name, TABLE_COMMENT remark FROM information_schema.TABLES WHERE " +
                "TABLE_SCHEMA = '"+dbname+"' and TABLE_NAME not like 'seq_%'";
        ResultSet rs = statement.executeQuery(sql);
        List<Tables> list = new ArrayList<>();
        while (rs.next()){
            list.add(new Tables(rs.getString("name"), rs.getString("remark")));
        }
        rs.close();
        statement.close();
        return list;
    }

    public List<Column> getColumns(String dbname, String tableName) throws SQLException {
        Statement statement = this.conn.createStatement();
        String sql = "SELECT COLUMN_NAME name, " +
                "CASE COLUMN_key WHEN 'PRI' THEN 1 ELSE 0 END isKey, " +
                "DATA_TYPE type, COLUMN_COMMENT remark FROM information_schema.COLUMNS" +
                " WHERE TABLE_SCHEMA = '"+dbname+"' AND TABLE_NAME = '"+tableName+"' ORDER BY ORDINAL_POSITION ";
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
}
