package com.yjs.FX.controller;

import com.yjs.FX.AlertBox;
import com.yjs.FX.GeneratorDisplay;
import com.yjs.FX.TableList;
import com.yjs.FX.dataconn.MysqlConn;
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

public class MySQLController {
    @FXML
    private TextField dburl;

    @FXML
    private TextField dbname;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField basePackage;

    @FXML
    private TextField entityPackage;

    @FXML
    private TextField mapperPackage;

    @FXML
    private TextField servicePackage;

    @FXML
    private TextField controllerPackage;

    @FXML
    private TextField tabname;

    @FXML
    private CheckBox isTrim;

    @FXML
    private TextField trimValue;

    @FXML
    private CheckBox myMapper;

    @FXML
    private CheckBox controller;

    @FXML
    private CheckBox service;

    @FXML
    private CheckBox mapper;

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
                if (myMapper.isSelected()){
                    hepler.generateCommonFile(config);
                }
                if (entity.isSelected()){
                    hepler.generateEntityFile(cols, config);
                }
                if (controller.isSelected()){
                    hepler.generateControllerFile(cols, config);
                }
                if (mapper.isSelected()){
                    hepler.generateMapperFile(cols, config);
                    hepler.generateMapperXmlFile(cols, config);
                }
                if (service.isSelected()){
                    hepler.generateServiceFile(cols, config);
                    hepler.generateServiceImplFile(cols, config);
                }

            } catch (Exception e){
                e.printStackTrace();
            }
        }

        new AlertBox().display("消息","成功");
    }


    @FXML
    public void choseBtnClick(ActionEvent event){
        new TableList().display(dburl.getText(),dbname.getText(),username.getText(),password.getText(), this);
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
                if (key.equals("dburl")){
                    dburl.setText(prop.getProperty(key));
                }else if (key.equals("username")){
                    username.setText(prop.getProperty(key));
                }else if (key.equals("password")){
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
        MysqlConn mysql = new MysqlConn();
        List<Column> list = new ArrayList<>();
        try {
            mysql.createConn(dburl.getText(), dbname.getText(), username.getText(), password.getText());
            list = mysql.getColumns(dbname.getText(), tabname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Map<String, String> getCommConfig(){
        Map<String, String> config = new HashMap<>();
        config.put("basePackage", basePackage.getText());
        config.put("entityPackage", entityPackage.getText());
        config.put("mapperPackage", mapperPackage.getText());
        config.put("servicePackage", servicePackage.getText());
        config.put("controllerPackage", controllerPackage.getText());
        return config;
    }

    private String setFistUpper(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
