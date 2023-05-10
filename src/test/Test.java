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

import db.database;


public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    
//hghyjfjh
    public static Connection connection=null;
   
    @Override
    public void start(Stage primaryStage) {
        try {
        	database.connecting();
        	//database.SelectFromSanPham();
            Parent root = FXMLLoader.load(this.getClass().getResource("/view/QLSanPhamView.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (Exception e){
        	e.printStackTrace();
        }
    }
}

