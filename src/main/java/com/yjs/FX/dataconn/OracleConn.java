package com.yjs.FX.dataconn;

import com.yjs.entity.Column;
import com.yjs.entity.Tables;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OracleConn {

    static final String driverClass = "oracle.jdbc.driver.OracleDriver";

    private Connection conn;

    public OracleConn(){

    }

    public void createConn(String url, String username, String password){
        String dbUrl = String.format("jdbc:oracle:thin:@%s", url);
        try {
            Class.forName(driverClass);
            this.conn = DriverManager.getConnection(dbUrl, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
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

    public List<Tables> getTables() throws SQLException {
        String sql = "SELECT TABLE_NAME AS name, COMMENTS AS remark FROM user_tab_comments";
        PreparedStatement pstm = this.conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        List<Tables> list = new ArrayList<>();
        while (rs.next()){
            list.add(new Tables(rs.getString("name"), rs.getString("remark")));
        }
        rs.close();
        pstm.close();
        return list;
    }

    public List<Column> getColumns(String tableName) throws SQLException {
        String sql = "SELECT A.COLUMN_NAME name," +
                "'0' isKey, A.DATA_TYPE type," +
                "(SELECT B.COMMENTS FROM USER_COL_COMMENTS B" +
                " WHERE A.TABLE_NAME = B.TABLE_NAME AND A.COLUMN_NAME = B.COLUMN_NAME) remark" +
                " FROM USER_TAB_COLUMNS A WHERE A.TABLE_NAME = '" + tableName+"'";
        PreparedStatement pstm = this.conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
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
        pstm.close();
        return list;
    }
}
