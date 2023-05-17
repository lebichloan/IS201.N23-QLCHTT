package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController implements Initializable {

	@FXML
	TextField usernameTextField;
	
	@FXML
	TextField passwordTextField;
	
	@FXML
	Text forgotPasswordText;
	
	@FXML
	Button loginButton;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		forgotPasswordText.setOnMouseClicked((MouseEvent event) -> {
            common.sceneTransition("/view/ChangePassword.fxml",event);
        });
		// TODO Auto-generated method stub
		DropShadow dropShadow = new DropShadow();
		forgotPasswordText.setEffect(dropShadow);
		dropShadow.setRadius(0.0);
		
		DropShadow dropShadow1 = new DropShadow();
		loginButton.setEffect(dropShadow1);
		dropShadow1.setRadius(0.0);
		

		forgotPasswordText.setOnMouseEntered((MouseEvent event) -> {
            dropShadow.setRadius(10.0);
            forgotPasswordText.setCursor(Cursor.HAND);
        });

		forgotPasswordText.setOnMouseExited((MouseEvent event) -> {
            dropShadow.setRadius(0.0);
            forgotPasswordText.setCursor(Cursor.DEFAULT);
        });
		
		loginButton.setOnMouseEntered((MouseEvent event) -> {
            dropShadow1.setRadius(10.0);
            loginButton.setCursor(Cursor.HAND);
        });
		
		loginButton.setOnMouseExited((MouseEvent event) -> {
            dropShadow1.setRadius(0.0);
            loginButton.setCursor(Cursor.DEFAULT);
        });
		
		loginButton.setOnMouseClicked((MouseEvent event) -> {
			try {
			    FXMLLoader loader = new FXMLLoader(common.class.getResource("/view/TrangChu.fxml"));
			    AnchorPane root = loader.load();

			   

			    // Create a Scene object with the root node and set it to the primary stage
			    Scene scene = new Scene(root);

			    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			    window.setScene(scene);
			    window.setMaximized(true);
			    window.show();

			    // add a listener to the stage to handle window resize events
			    scene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
			    	double scaleFactor = newSceneWidth.doubleValue() / oldSceneWidth.doubleValue();
		            root.setPrefWidth(root.getPrefWidth() * scaleFactor);
			    });
			    scene.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> {
			    	double scaleFactor = newSceneHeight.doubleValue() / oldSceneHeight.doubleValue();
		            root.setPrefWidth(root.getPrefWidth() * scaleFactor);
			    });
			} catch (IOException e) {
			    e.printStackTrace();
			}
		});
	}
}
