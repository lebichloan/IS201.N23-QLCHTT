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
	
	public static void newScene (String path, ActionEvent event){
    	try {
    		FXMLLoader loader = new FXMLLoader(common.class.getResource(path));
    		AnchorPane root = loader.load();

            // Create a Scene object with the root node and set it to the primary stage
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the Resizable property of the primary stage to true
            window.setResizable(true);

            // Set the scene to the primary stage and show it
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static void newScene (String path, MouseEvent event){
    	try {
    		FXMLLoader loader = new FXMLLoader(common.class.getResource(path));
    		AnchorPane root = loader.load();

            // Create a Scene object with the root node and set it to the primary stage
            Scene scene = new Scene(root);
            Stage window = new Stage ();
            
            window.setHeight(768);
            window.setWidth(1366);

            // Set the Resizable property of the primary stage to true
            window.setResizable(true);

            // Set the scene to the primary stage and show it
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
