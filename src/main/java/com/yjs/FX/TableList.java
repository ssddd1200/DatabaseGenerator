package com.yjs.FX;

import com.yjs.FX.UI.TabelChoseUI;
import com.yjs.FX.controller.MySQLController;
import com.yjs.FX.controller.OracleController;
import com.yjs.FX.dataconn.MysqlConn;
import com.yjs.FX.dataconn.OracleConn;
import com.yjs.entity.Tables;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableList {

    public void display(String dbURL, String username, String password, OracleController controller){
        String[] tableNames = {""};
        List<Tables> tablesList = getTableList(dbURL, username, password);
        if (tablesList.size() == 0){
            return;
        }
        Stage windows = new Stage();
        TableView tableView = new TableView();

        Button button = new Button("确认");
        button.setOnAction(e -> {
            ObservableList<TabelChoseUI> list = tableView.getItems();
            for(TabelChoseUI o:list){
                if(o.checkBoxUI.isSelected()){
                    tableNames[0] += o.tabname +",";
                }
            }
            if (tableNames[0] != ""){
                controller.setData(tableNames[0]);
            }
            windows.close();
        });


        ObservableList<TabelChoseUI> list = FXCollections.observableArrayList();
        for (Tables tables: tablesList){
            list.add(new TabelChoseUI(tables.getName()));
        }
        tableView.setItems(list);
        TableColumn<TabelChoseUI, CheckBox> column1 = new TableColumn<>();
        column1.setCellValueFactory(cellData -> cellData.getValue().checkBoxUI.getCheckBox());
        column1.setPrefWidth(50.0);
        TableColumn<TabelChoseUI, String> column2 = new TableColumn<>("表名");
        column2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().tabname));
        column2.setPrefWidth(140.0);
        tableView.getColumns().addAll(column1, column2);

        VBox box = new VBox(15);
        box.getChildren().addAll(tableView, button);
        box.setAlignment(Pos.CENTER);

        Scene scene = new Scene(box);
        windows.setWidth(200.0);
        windows.setResizable(false);
        windows.setScene(scene);
        windows.showAndWait();
    }

    public void display(String dbURL, String dbname, String username, String password, MySQLController controller){
        String[] tableNames = {""};
        List<Tables> tablesList = getTableList(dbURL, dbname, username, password);
        if (tablesList.size() == 0){
            return;
        }
        Stage windows = new Stage();
        TableView tableView = new TableView();

        Button button = new Button("确认");
        button.setOnAction(e -> {
            ObservableList<TabelChoseUI> list = tableView.getItems();
            for(TabelChoseUI o:list){
                if(o.checkBoxUI.isSelected()){
                    tableNames[0] += o.tabname +",";
                }
            }
            if (tableNames[0] != ""){
                controller.setData(tableNames[0]);
            }
            windows.close();
        });


        ObservableList<TabelChoseUI> list = FXCollections.observableArrayList();
        for (Tables tables: tablesList){
            list.add(new TabelChoseUI(tables.getName()));
        }
        tableView.setItems(list);
        TableColumn<TabelChoseUI, CheckBox> column1 = new TableColumn<>();
        column1.setCellValueFactory(cellData -> cellData.getValue().checkBoxUI.getCheckBox());
        column1.setPrefWidth(50.0);
        TableColumn<TabelChoseUI, String> column2 = new TableColumn<>("表名");
        column2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().tabname));
        column2.setPrefWidth(140.0);
        tableView.getColumns().addAll(column1, column2);

        VBox box = new VBox(15);
        box.getChildren().addAll(tableView, button);
        box.setAlignment(Pos.CENTER);

        Scene scene = new Scene(box);
        windows.setWidth(200.0);
        windows.setResizable(false);
        windows.setScene(scene);
        windows.showAndWait();
    }

    private static List<Tables> getTableList(String dbURL, String username, String password){
        List<Tables> list = new ArrayList<>();
        if (dbURL.isEmpty() || username.isEmpty() || password.isEmpty()) {
            new AlertBox().display("消息", "数据库基本信息未填写");
            return list;
        } else {
            OracleConn oracle = new OracleConn();
            try {
                oracle.createConn(dbURL, username, password);
                list = oracle.getTables();
            } catch (SQLException e) {
                new AlertBox().display("消息", "数据库连接异常");
            }finally {
                oracle.discoryConn();
            }
        }
        return list;
    }

    private static List<Tables> getTableList(String dbURL, String dbname, String username, String password) {
        List<Tables> list = new ArrayList<>();
        if (dbURL.isEmpty() || dbname.isEmpty() || username.isEmpty() || password.isEmpty()) {
            new AlertBox().display("消息", "数据库基本信息未填写");
            return list;
        } else {
            MysqlConn mysql = new MysqlConn();
            try {
                mysql.createConn(dbURL, dbname, username, password);
                list = mysql.getTables(dbname);


            } catch (SQLException e) {
                new AlertBox().display("消息", "数据库连接异常");
            }
            mysql.discoryConn();
        }
        return list;
    }
}
