package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.NhanVien;
import db.database;

public class loginController implements Initializable { 	
	Connection conn = null;
	Statement stm = null;
	PreparedStatement prepareStm = null;
	ResultSet rs = null;
	String sql = null;
	
	@FXML
	private AnchorPane loginRoot;
	double x = 0, y = 0;
 
//	login form--------------------------------------------
    @FXML
    private AnchorPane loginForm;
    
    @FXML
    private JFXButton loginSignUpButton;
    
    @FXML
    private TextField loginUserName;

    @FXML
    private TextField loginShowPassWord;
    
    @FXML
    private PasswordField loginHidePassWord;
    
    @FXML
    private HBox passWordHBox;
    
    @FXML 
    private ImageView passWordImageView;
    boolean isShowPassWord = false;
    
    @FXML
    private Label loginErr;
    
    @FXML
    private JFXButton loginEnterButton;
    
    @FXML
    private Hyperlink loginForgotPassWord;
    
//  forgot password form------------------------------------------
    final String patternErr = "Must not have \'\\\', \'/\', space and not be empty";
    final String confirmErr = "The password confirmation does not match";
    
    @FXML
    private AnchorPane forgotPassWordForm;
    
    @FXML
    private JFXButton forgotPassWordBackButton;
    
    @FXML
    private TextField forgotPassWordUserName;
    
    @FXML
    private PasswordField forgotPassWordNew;
    
    @FXML
    private PasswordField forgotPassWordConfirm;
    
    @FXML
    private Label forgotPassWordErr;
    
    @FXML
    private JFXButton forgotPassWordEnterButton;
    
    final String userNotExistErr = "Username does not exist";
    
//  window button
    @FXML private ImageView closeBtn;
    private Image closeIcon = new Image(getClass().getResource("/asset/loginCloseIcon.png").toExternalForm());
    private Image closeIconActive = new Image(getClass().getResource("/asset/loginCloseIconActive.png").toExternalForm());
    @FXML private ImageView minimizeBtn;
    private Image minimizeIcon = new Image(getClass().getResource("/asset/loginMinimizeIcon.png").toExternalForm());
    private Image minimizeIconActive = new Image(getClass().getResource("/asset/loginMinimizeIconActive.png").toExternalForm());
    
//-------------------------------------------------------------
    @FXML
    private AnchorPane loginPane;
    
    EventHandler<KeyEvent> enterHandlerTextField = (event)->{
    	if(event.getCode() == KeyCode.ENTER)
    		loginRoot.requestFocus();
    };
    
    public void exitFocused(MouseEvent event) {
    	if(event.getButton() == MouseButton.PRIMARY)
    		loginRoot.requestFocus();
    }
        
    private ChangeListener<Boolean> focusPassWordHBox = (observableVal,oldVal,newVal)->{
    	if(loginErr.isVisible()) {
    		loginErr.setVisible(false);
    		loginUserName.getStyleClass().remove("login-err");
    	}
    	if(newVal) {
			passWordHBox.setStyle(
					" -fx-text-fill: #fffaf4;\r\n"
					+ "	-fx-border-color: linear-gradient(to right, #fdfc47, #24fe41);\r\n"
					+ "	-fx-border-width: 0 0 1.5 0;\r\n"
					+ "	-fx-border-radius: 3 3 0 0;\r\n"
					+ "	-fx-background-radius: 3 3 0 0;\r\n"
					+ "	-fx-prompt-text-fill: #827f7a;\r\n"
			);
		}else {
			passWordHBox.setStyle("	-fx-background-color: transparent;\r\n"
					+ "	-fx-border-width: 0 0 1 0;\r\n"
					+ "	-fx-border-color: linear-gradient(to right, #34e89e, #0f3443);\r\n"
					+ "	-fx-font-size: 15px;\r\n"
					+ "	-fx-font-family: \"Arial\";\r\n"
					+ "	-fx-font-weight: bold;\r\n"
					+ "	-fx-cursor: text;\r\n"
					+ "	-fx-prompt-text-fill: #827f7a;\r\n"
					+ "	-fx-text-fill: #fffaf4;\r\n"
					+ "	-fx-border-radius: 3 3 0 0;\r\n"
					+ "	-fx-background-radius: 3 3 0 0;");
			if(loginShowPassWord.isHover() || loginHidePassWord.isHover()) {
				passWordHBox.setStyle("	-fx-background-color: rgba(0, 0, 0, 0.35);\r\n"
						+ "	-fx-prompt-text-fill: #827f7a;\r\n"
						+ "	-fx-text-fill: #fffaf4;\r\n"
						+ "	-fx-border-color: linear-gradient(to right, #fdfc47, #24fe41);\r\n"
						+ "	-fx-border-width: 0 0 1.5 0;\r\n"
						+ "	-fx-border-radius: 3 3 0 0;\r\n"
						+ "	-fx-background-radius: 3 3 0 0;");
			}
			if(isShowPassWord)
				passWordImageView.setImage(
						new Image(getClass().getResource("/asset/showPassIcon.png").toExternalForm()));
			else 
				passWordImageView.setImage(
						new Image(getClass().getResource("/asset/hidePassIcon.png").toExternalForm()));
			if(!loginHidePassWord.isFocused() && !loginShowPassWord.isFocused()) {
				if(!loginEnterButton.isDisabled())
					loginEnterButton.requestFocus();
			}
		}
    };
    
    private ChangeListener<Boolean> hoverPassWordHBox = (observableVal,oldVal,newVal) -> {
    	if(loginShowPassWord.isFocused() || loginHidePassWord.isFocused()) return;
    	if(loginErr.isVisible()) {
    		loginUserName.getStyleClass().remove("login-err");
    		loginErr.setVisible(false);
    	}
    	if(newVal) {
    		passWordHBox.setStyle("	-fx-background-color: rgba(0, 0, 0, 0.35);\r\n"
    				+ "	-fx-prompt-text-fill: #827f7a;\r\n"
    				+ "	-fx-text-fill: #fffaf4;\r\n"
    				+ "	-fx-border-color: linear-gradient(to right, #fdfc47, #24fe41);\r\n"
    				+ "	-fx-border-width: 0 0 1.5 0;\r\n"
    				+ "	-fx-border-radius: 3 3 0 0;\r\n"
    				+ "	-fx-background-radius: 3 3 0 0;");
    	}else {
			passWordHBox.setStyle("	-fx-background-color: transparent;\r\n"
					+ "	-fx-border-width: 0 0 1 0;\r\n"
					+ "	-fx-border-color: linear-gradient(to right, #34e89e, #0f3443);\r\n"
					+ "	-fx-font-size: 15px;\r\n"
					+ "	-fx-font-family: \"Arial\";\r\n"
					+ "	-fx-font-weight: bold;\r\n"
					+ "	-fx-cursor: text;\r\n"
					+ "	-fx-prompt-text-fill: #827f7a;\r\n"
					+ "	-fx-text-fill: #fffaf4;\r\n"
					+ "	-fx-border-radius: 3 3 0 0;\r\n"
					+ "	-fx-background-radius: 3 3 0 0;");
    	}
		if(isShowPassWord)
			passWordImageView.setImage(
					new Image(getClass().getResource("/asset/showPassIcon.png").toExternalForm()));
		else 
			passWordImageView.setImage(
					new Image(getClass().getResource("/asset/hidePassIcon.png").toExternalForm()));
    };
    
    public void clearLoginForm() {
		loginUserName.clear();
		loginHidePassWord.clear();
		loginHidePassWord.setVisible(true);
		loginShowPassWord.setVisible(false);
		isShowPassWord = false;
		passWordImageView.setImage(
				new Image(getClass().getResource("/asset/hidePassIcon.png").toExternalForm()));
		if(loginErr.isVisible()) {
			loginErr.setVisible(false);
			passWordHBox.setStyle("-fx-background-color: transparent;");
			loginUserName.getStyleClass().remove("login-err");
		}
    }
    
	
    public void clearForgotPassWordForm() {
    	forgotPassWordUserName.clear();
		forgotPassWordNew.clear();
		forgotPassWordConfirm.clear();
		forgotPassWordUserName.getStyleClass().remove("login-err");
		forgotPassWordNew.getStyleClass().remove("login-err");
		forgotPassWordConfirm.getStyleClass().remove("login-err");
		forgotPassWordErr.setText("");
		forgotPassWordNew.setDisable(true);
    }
    
    public void switchForm(ActionEvent event) {
    	Node node = (Node)event.getSource();
    	if(node == loginSignUpButton) {
    		clearLoginForm();
    		loginForm.setVisible(false);
    	}else if(node == loginForgotPassWord) {
    		forgotPassWordUserName.setText(loginUserName.getText());
    		clearLoginForm();
    		loginForm.setVisible(false);
    		forgotPassWordForm.setVisible(true);
    		forgotPassWordUserName.requestFocus();
    		forgotPassWordUserName.selectEnd();
    	}else if(node == forgotPassWordBackButton) {
    		clearForgotPassWordForm();
    		forgotPassWordForm.setVisible(false);
    		loginForm.setVisible(true);
    		loginRoot.requestFocus();
    	}
    }
    
    public void loginAction(ActionEvent event) throws IOException {
    	String username = loginUserName.getText();
    	String password = loginShowPassWord.getText();
    	conn = database.getConnection();
    	sql = "select * from nhanvien where username = ? and password = ?";
    	try {
			prepareStm = conn.prepareStatement(sql);
			prepareStm.setString(1, username);
			prepareStm.setString(2, password);
			rs = prepareStm.executeQuery();
			if(rs.next()) { //login success
				common.NV = new NhanVien(rs.getString("ma_nv"), rs.getString("ho_ten"), 
					rs.getString("gioi_tinh"),rs.getDate("ngay_sinh").toLocalDate(),
					rs.getString("dia_chi"),rs.getString("sdt"),
					rs.getString("email"),rs.getString("ghi_chu"),
					rs.getDate("ngay_vl").toLocalDate(),rs.getString("username"),
					rs.getString("password"),rs.getString("ma_lnd"));
				((Stage)(loginEnterButton.getScene().getWindow())).hide();
				Parent root = FXMLLoader.load(getClass().getResource("/view/TrangChu.fxml"));
				Scene scene = new Scene(root);
				Stage stg = new Stage();
				stg.setScene(scene);
				stg.show();
			}else {
	    		loginErr.setVisible(true);
	    		loginShowPassWord.clear();
	    		loginShowPassWord.setVisible(false);
	    		loginHidePassWord.setVisible(true);
	    		isShowPassWord = false;
	    		loginUserName.getStyleClass().add("login-err");
	    		passWordHBox.setStyle("	-fx-border-width: 0 0 1.5 0;\r\n"
	    				+ "	-fx-border-radius: 3 3 0 0;\r\n"
	    				+ "	-fx-border-color: #ff1a5f;");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void forgotPassWordAction(ActionEvent event) { //update lai password trong csdl
    	String username = forgotPassWordUserName.getText();
    	String newPassword = forgotPassWordConfirm.getText();
    	
    	//update password in nhanvien
    	sql = "update nhanvien set password = ? where username = ?";
    	try {
			prepareStm = conn.prepareStatement(sql);
			prepareStm.setString(1, newPassword);
			prepareStm.setString(2, username);
			prepareStm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Change password");
    	alert.setHeaderText(null);
    	alert.setContentText("Change password sucess");
    	alert.getDialogPane().getStylesheets().add(getClass().getResource("/css/alertDialog.css").toExternalForm());
    	Stage alertStage = (Stage)alert.getDialogPane().getScene().getWindow();
    	alertStage.initStyle(StageStyle.TRANSPARENT);
    	alertStage.getScene().setFill(Color.TRANSPARENT);
    	ImageView alertIcon = new ImageView(new Image(getClass().getResource("/asset/loginInformIcon.png").toExternalForm()));
    	alert.getDialogPane().setGraphic(alertIcon);
    	alert.showAndWait();
    
		clearForgotPassWordForm();
		forgotPassWordForm.setVisible(false);
		loginForm.setVisible(true);
		loginRoot.requestFocus();
    }
    
    public boolean isValidPattern(String str) {
    	if(str.contains(" ") || str.contains("\\") || str.contains("/") || str.equals(""))
    		return false;
    	return true;
    }
    
    public void requestError(Node node) {
    	Pane parent = (Pane)node.getParent();
    	ObservableList<Node> childrenList = parent.getChildren();
    	for(Node i : childrenList) {
    		if(i.getStyleClass().remove("login-err"))
    			break;
    	}
    	node.getStyleClass().add("login-err");
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		conn = database.getConnection();
		try {
			stm = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		loginRoot.requestFocus();
		loginRoot.setOnMousePressed(event -> {
			x = event.getSceneX();
			y = event.getSceneY();
		});
		loginRoot.setOnMouseDragged((event) -> {
			Stage stg = (Stage)(((Node)event.getSource()).getScene()).getWindow();
			stg.setX(event.getScreenX() - x);
			stg.setY(event.getScreenY() - y);
		});
		
//		login form------------------------------------------------------------------------------------
		loginEnterButton.getStyleClass().add("enter-button");
		loginUserName.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		loginHidePassWord.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		loginShowPassWord.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);		
		loginUserName.textProperty().addListener((observableVal,oldVal,newVal)->{
			if(newVal.length() > 0 && loginHidePassWord.getText().length() > 0)
				loginEnterButton.setDisable(false);
			else 
				loginEnterButton.setDisable(true);
		});
		loginUserName.hoverProperty().addListener((observableVal,oldVal,newVal)->{
			if(loginErr.isVisible()) {
				passWordHBox.setStyle("-fx-background-color: transparent");
				loginUserName.getStyleClass().remove("login-err");
				loginErr.setVisible(false);
			}
		});
		loginUserName.focusedProperty().addListener((observableVal,oldVal,newVal)->{
			if(loginErr.isVisible()) {
				passWordHBox.setStyle("-fx-background-color: transparent");
				loginUserName.getStyleClass().remove("login-err");
				loginErr.setVisible(false);
			}
			if(!newVal) {
				if(!loginEnterButton.isDisabled()) {
					loginEnterButton.requestFocus();
				}else if(isShowPassWord)
					loginShowPassWord.requestFocus();
				else
					loginHidePassWord.requestFocus();
			}
		});
		
		loginShowPassWord.focusedProperty().addListener(focusPassWordHBox);
		loginShowPassWord.hoverProperty().addListener(hoverPassWordHBox);
		loginHidePassWord.focusedProperty().addListener(focusPassWordHBox);
		loginHidePassWord.hoverProperty().addListener(hoverPassWordHBox);
		loginHidePassWord.textProperty().addListener((observableVal,oldVal,newVal)->{
			loginShowPassWord.setText(newVal);
			if(newVal.length() > 0 && loginUserName.getText().length() > 0)
				loginEnterButton.setDisable(false);
			else 
				loginEnterButton.setDisable(true);
				
		});
		loginShowPassWord.textProperty().addListener((observableVal,oldVal,newVal)->{
			loginHidePassWord.setText(newVal);
		});
		loginShowPassWord.setVisible(false);
		loginHidePassWord.setVisible(true);
		
		passWordImageView.setImage(
				new Image(getClass().getResource("/asset/hidePassIcon.png").toExternalForm()));
		passWordImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			if(event.getButton() != MouseButton.PRIMARY)
				return;
			isShowPassWord = !isShowPassWord;
			loginShowPassWord.setVisible(true);
			loginHidePassWord.setVisible(true);
			if(isShowPassWord) {
				if(loginHidePassWord.isFocused()) {
					passWordImageView.setImage(
							new Image(getClass().getResource("/asset/showPassIcon.png").toExternalForm()));
					loginShowPassWord.requestFocus();
					loginShowPassWord.selectEnd();
				}else {
					passWordImageView.setImage(
							new Image(getClass().getResource("/asset/showPassIcon.png").toExternalForm()));
				}
			}else {
				if(loginShowPassWord.isFocused()) {
					passWordImageView.setImage(
							new Image(getClass().getResource("/asset/hidePassIcon.png").toExternalForm()));
					loginHidePassWord.requestFocus();
					loginHidePassWord.selectEnd();
				}else {
					passWordImageView.setImage(
							new Image(getClass().getResource("/asset/hidePassIcon.png").toExternalForm()));
				}
			}
			loginShowPassWord.setVisible(isShowPassWord);
			loginHidePassWord.setVisible(!isShowPassWord);
			event.consume();
		});
		
//		forgot password form--------------------------------------------------------------------------
		forgotPassWordEnterButton.getStyleClass().add("enter-button");
		forgotPassWordUserName.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		forgotPassWordNew.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		forgotPassWordConfirm.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		
		forgotPassWordNew.setDisable(true); forgotPassWordConfirm.setDisable(true); 
		forgotPassWordEnterButton.setDisable(true); forgotPassWordErr.setText("");
		
		forgotPassWordNew.disabledProperty().addListener((obervableVal,oldVal,newVal)->{
			if(newVal) {
				forgotPassWordConfirm.setDisable(true);
			}else {
				String password = forgotPassWordNew.getText();
				if(password.equals("")) 
					forgotPassWordNew.requestFocus();
				else if(!isValidPattern(password)) {
					forgotPassWordErr.setText(patternErr);
					requestError(forgotPassWordNew);
					forgotPassWordConfirm.setDisable(true);
				}else {
					forgotPassWordConfirm.setDisable(false);
				}
			}
		});
		forgotPassWordConfirm.disabledProperty().addListener((observableVal,oldVal,newVal)->{
			if(newVal) {
				forgotPassWordEnterButton.setDisable(true);
			}else {
				String conFirmPassWord = forgotPassWordConfirm.getText();
				if(conFirmPassWord.equals("")) 
					forgotPassWordConfirm.requestFocus();
				else if(!conFirmPassWord.equals(forgotPassWordNew.getText())) {
					forgotPassWordErr.setText(confirmErr);
					requestError(forgotPassWordConfirm);
					forgotPassWordEnterButton.setDisable(true);
				}else {
					forgotPassWordEnterButton.setDisable(false);
					forgotPassWordEnterButton.requestFocus();
				}
			}
		});
		forgotPassWordUserName.focusedProperty().addListener((observableVal,oldVal,newVal)->{
			if(!newVal) {
				String username = forgotPassWordUserName.getText();
				
				if(!isValidPattern(username)) {
					forgotPassWordErr.setText(patternErr);
					requestError(forgotPassWordUserName);
					forgotPassWordNew.setDisable(true);
					return;
				}
				
				sql = "select * from nhanvien where username = ?";
				try {
					prepareStm = conn.prepareStatement(sql);
					prepareStm.setString(1, username);
					rs = prepareStm.executeQuery();
					if(!rs.next()) {
						forgotPassWordErr.setText(userNotExistErr);
						requestError(forgotPassWordUserName);
						forgotPassWordNew.setDisable(true);
						return;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//username hop le
				forgotPassWordUserName.getStyleClass().remove("login-err");
				forgotPassWordErr.setText("");
				forgotPassWordNew.setDisable(false);
			}
		});
				
		forgotPassWordNew.focusedProperty().addListener((observableVal,oldVal,newVal)->{
			if(!newVal) {
				String password = forgotPassWordNew.getText();
				if(!isValidPattern(password)) {
					forgotPassWordErr.setText(patternErr);
					requestError(forgotPassWordNew);
					forgotPassWordConfirm.setDisable(true);
				}else {
					//password hop le
					if(forgotPassWordNew.getStyleClass().remove("login-err")) {
						forgotPassWordErr.setText("");
					}
					if(forgotPassWordConfirm.isDisabled()) {
						forgotPassWordConfirm.setDisable(false);
					}else {
						String confirmPassWord = forgotPassWordConfirm.getText();
						if(!confirmPassWord.equals(password)) {
							forgotPassWordErr.setText(confirmErr);
							requestError(forgotPassWordConfirm);
							forgotPassWordEnterButton.setDisable(true);
						}else {
							forgotPassWordErr.setText("");
							forgotPassWordConfirm.getStyleClass().remove("login-err");
							forgotPassWordEnterButton.setDisable(false);
							forgotPassWordEnterButton.requestFocus();
						}
					}
				}
			}
		});
		forgotPassWordConfirm.focusedProperty().addListener((observableVal,oldVal,newVal)->{
			if(!newVal) {
				String confirmPassWord = forgotPassWordConfirm.getText();
				if(!confirmPassWord.equals(forgotPassWordNew.getText())) {
					forgotPassWordErr.setText(confirmErr);
					requestError(forgotPassWordConfirm);
					forgotPassWordEnterButton.setDisable(true);
				}else {
					forgotPassWordErr.setText("");
					forgotPassWordConfirm.getStyleClass().remove("login-err");
					forgotPassWordEnterButton.setDisable(false);
					forgotPassWordEnterButton.requestFocus();
				}
			}
		});
//		window button
		closeBtn.setImage(closeIcon);
		minimizeBtn.setImage(minimizeIcon);
		closeBtn.hoverProperty().addListener((observableVal,oldVal,newVal)->{
			if(newVal) {
				closeBtn.setImage(closeIconActive);
			}else {
				closeBtn.setImage(closeIcon);
			}
		});
		minimizeBtn.hoverProperty().addListener((observableVal,oldVal,newVal)->{
			if(newVal) {
				minimizeBtn.setImage(minimizeIconActive);
			}else {
				minimizeBtn.setImage(minimizeIcon);
			}
		});
		closeBtn.setOnMouseClicked(event->{
			if(event.getButton().equals(MouseButton.PRIMARY)) {
				System.exit(0);
			}
		});
		minimizeBtn.setOnMouseClicked(event->{
			if(event.getButton().equals(MouseButton.PRIMARY)) {
				((Stage)loginRoot.getScene().getWindow()).setIconified(true);
			}
		});
		
//		loginPane fade in effect
		loginPane.setOpacity(0);
		FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.65),loginPane);
		fadeIn.setFromValue(0); fadeIn.setToValue(1);
		fadeIn.play();
	}
}

