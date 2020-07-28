package com.yjs.FX.controller;

import com.yjs.FX.AlertBox;
import com.yjs.FX.GeneratorDisplay;
import com.yjs.FX.TableList;
import com.yjs.FX.dataconn.MysqlConn;
import com.yjs.FX.dataconn.OracleConn;
import com.yjs.entity.Column;
import com.yjs.helper.GeneratorHepler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;

public class OracleController {
    @FXML
    private TextField dburl;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField javaPackage;

    @FXML
    private TextField tabname;

    @FXML
    private CheckBox isTrim;

    @FXML
    private TextField trimValue;

    @FXML
    private CheckBox html;

    @FXML
    private CheckBox sql;

    @FXML
    private CheckBox entity;

    @FXML
    public void buildBtnClick(ActionEvent event) {

        Map<String, String> config = getCommConfig();
        GeneratorHepler hepler = new GeneratorHepler();
        String[] tabNames = tabname.getText().split(",");
        for (String name: tabNames){
            config.put("tableName", name);
            List<Column> cols = getColums(name);
            if (isTrim.isSelected()){
                config.put("className", setFistUpper(name.replaceAll(trimValue.getText(),"")));
            }else {
                config.put("className", setFistUpper(name));
            }
            config.put("comType","1");
            try {
                if (entity.isSelected()){
                    hepler.generateOracleFile(cols, config);
                }
                if (html.isSelected()){
                    hepler.generateHtmlFile(cols, config);
                    hepler.generateJSFile(cols, config);
                }
                if (sql.isSelected()){
                    hepler.generateOraleDataFile(cols, config);
                }

            } catch (Exception e){
                e.printStackTrace();
            }
        }

        new AlertBox().display("消息","成功");
    }


    @FXML
    public void choseBtnClick(ActionEvent event){
        new TableList().display(dburl.getText(),username.getText(),password.getText(), this);
    }

    @FXML
    public void isTrimChange(ActionEvent event){
        if(!isTrim.isSelected()){
            trimValue.setDisable(true);
            trimValue.setText("");
        }else{
            trimValue.setDisable(false);
        }
    }

    @FXML
    public void initialize(){
        Properties prop = new Properties();
        String filepath = GeneratorDisplay.class.getResource("/config.properties").toString();
        InputStream is = null;
        if (filepath.startsWith("jar:file")){
            try {
                is = new FileInputStream("./conf/config.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            is = GeneratorDisplay.class.getResourceAsStream("/config.properties");
        }
        try {
            prop.load(is);
            Enumeration en = prop.propertyNames();
            while (en.hasMoreElements()){
                String key = (String) en.nextElement();
                if (key.equals("odburl")){
                    dburl.setText(prop.getProperty(key));
                }else if (key.equals("ousername")){
                    username.setText(prop.getProperty(key));
                }else if (key.equals("opassword")){
                    password.setText(prop.getProperty(key));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            new AlertBox().display("消息","读取配置失败.....");
        }
    }

    public void setData(String tableNames){
        tabname.setText(tableNames.substring(0, tableNames.length() -1));
    }

    private List<Column> getColums(String tabname){
        OracleConn oracle = new OracleConn();
        List<Column> list = new ArrayList<>();
        try {
            oracle.createConn(dburl.getText(), username.getText(), password.getText());
            list = oracle.getColumns(tabname);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oracle.discoryConn();
        }
        return list;
    }

    private Map<String, String> getCommConfig(){
        Map<String, String> config = new HashMap<>();
        config.put("basePackage", javaPackage.getText());
        return config;
    }

    private String setFistUpper(String s) {
        String new_S = s.toLowerCase();
        return new_S.substring(0, 1).toUpperCase() + new_S.substring(1);
    }
}
