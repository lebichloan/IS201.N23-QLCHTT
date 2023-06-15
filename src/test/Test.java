package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controller.common;
import db.database;


public class Test extends Application {
	public static common com;
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        try {
        	database.connect();
        	Parent root = FXMLLoader.load(this.getClass().getResource("/view/QLHoaDon.fxml"));
            //Parent root = FXMLLoader.load(this.getClass().getResource("/view/TrangChu.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.setResizable(false);
            primaryStage.show();
        }catch (Exception e){
        	e.printStackTrace();
        }
    }
}

