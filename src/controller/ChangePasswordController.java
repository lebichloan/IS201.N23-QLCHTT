package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ChangePasswordController implements Initializable{
	
	@FXML
	TextField usernameTextField;
	
	@FXML
	TextField oldPasswordTextField;
	
	@FXML
	TextField newPasswordTextField;
	
	@FXML
	Button saveButton;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
