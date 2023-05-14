package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

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
	}

}
