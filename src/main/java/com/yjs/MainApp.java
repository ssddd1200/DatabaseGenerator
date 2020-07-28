package com.yjs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("FX/Main.fxml"));

        Scene scene = new Scene(parent, 750, 450);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("FX/application.css").toExternalForm());

        // 设置代码
        primaryStage.setTitle("代码生成工具");
        // 设置图标
//        primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("/image/image.png")));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
