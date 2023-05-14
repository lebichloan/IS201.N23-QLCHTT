package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class common {
	
	
	public static void sceneTransition (String path, ActionEvent event){
    	try {
            FXMLLoader loader = new FXMLLoader(common.class.getResource(path));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static void sceneTransition (String path, MouseEvent event){
    	try {
            FXMLLoader loader = new FXMLLoader(common.class.getResource(path));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
