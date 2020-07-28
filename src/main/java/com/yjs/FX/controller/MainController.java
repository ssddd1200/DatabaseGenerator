package com.yjs.FX.controller;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainController {

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab orclTab;

    @FXML
    private Tab mysqlTab;


    //动态加载fxml内容
    @FXML
    public void initialize(){
        tabPane.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> observable,
                                                                        Tab oldValue, Tab newValue) -> {
            if (newValue == orclTab) {
                System.out.println("Bar Tab page");
            } else if (newValue == mysqlTab) {
                System.out.println("Foo Tab page");
            }
        });
    }

}
