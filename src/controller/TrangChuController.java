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
import javafx.scene.control.SplitPane;
import javafx.scene.control.SplitPane.Divider;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class TrangChuController implements Initializable {
	
	@FXML
	Button trangChuButton;
	
	@FXML
	Button hoaDonButton;
	
	@FXML
	Button sanPhamButton;
	
	@FXML
	Button khachhangButton;
	
	@FXML
	Button nhanVienButton;
	
	@FXML
	Button thongKeButton;
	
	@FXML
	Button dangXuatButton;
	
	@FXML
	AnchorPane thaoTacAnchorPane;
	
	@FXML
	AnchorPane root;
	Button selectedButton;
	
	@FXML SplitPane splitPaneNavigatorAndContent;
	
	public void exitFocused(MouseEvent event) {
		if(event.getButton() == MouseButton.PRIMARY) {
			root.requestFocus();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		DropShadow dropShadow = new DropShadow();
		trangChuButton.setEffect(dropShadow);
		hoaDonButton.setEffect(dropShadow);
		nhanVienButton.setEffect(dropShadow);
		sanPhamButton.setEffect(dropShadow);
		khachhangButton.setEffect(dropShadow);
		dangXuatButton.setEffect(dropShadow);
		dropShadow.setRadius(0.0);
		trangChuButton.setOnMouseEntered((MouseEvent event) -> {
            dropShadow.setRadius(10.0);
            trangChuButton.setCursor(Cursor.HAND);
        });
		
		hoaDonButton.setOnMouseEntered((MouseEvent event) -> {
            dropShadow.setRadius(10.0);
            hoaDonButton.setCursor(Cursor.HAND);
        });
		
		sanPhamButton.setOnMouseEntered((MouseEvent event) -> {
            dropShadow.setRadius(10.0);
            sanPhamButton.setCursor(Cursor.HAND);
        });
		
		khachhangButton.setOnMouseEntered((MouseEvent event) -> {
            dropShadow.setRadius(10.0);
            khachhangButton.setCursor(Cursor.HAND);
        });
		
		nhanVienButton.setOnMouseEntered((MouseEvent event) -> {
            dropShadow.setRadius(10.0);
            nhanVienButton.setCursor(Cursor.HAND);
        });
		
		dangXuatButton.setOnMouseEntered((MouseEvent event) -> {
            dropShadow.setRadius(10.0);
            dangXuatButton.setCursor(Cursor.HAND);
        });
		
		hoaDonButton.setOnMouseExited((MouseEvent event) -> {
            dropShadow.setRadius(0.0);
            hoaDonButton.setCursor(Cursor.DEFAULT);
        });
		
		trangChuButton.setOnMouseExited((MouseEvent event) -> {
            dropShadow.setRadius(0.0);
            trangChuButton.setCursor(Cursor.DEFAULT);
        });
		
		sanPhamButton.setOnMouseExited((MouseEvent event) -> {
            dropShadow.setRadius(0.0);
            sanPhamButton.setCursor(Cursor.DEFAULT);
        });
		
		khachhangButton.setOnMouseExited((MouseEvent event) -> {
            dropShadow.setRadius(0.0);
            khachhangButton.setCursor(Cursor.DEFAULT);
        });
		
		nhanVienButton.setOnMouseExited((MouseEvent event) -> {
            dropShadow.setRadius(0.0);
            nhanVienButton.setCursor(Cursor.DEFAULT);
        });
		
		dangXuatButton.setOnMouseExited((MouseEvent event) -> {
            dropShadow.setRadius(0.0);
            dangXuatButton.setCursor(Cursor.DEFAULT);
        });
		
		trangChuButton.setOnMouseClicked((MouseEvent event) -> {
            dropShadow.setRadius(10.0);
            trangChuButton.setCursor(Cursor.HAND);
        });
		
		
		
		
		
		//trangChuButton.setOnAction(event -> handleButtonClick(trangChuButton));
	    hoaDonButton.setOnAction(event -> handleButtonClick(hoaDonButton,"/view/QLHoaDon.fxml"));
	    sanPhamButton.setOnAction(event -> handleButtonClick(sanPhamButton, "/view/QLSanPham.fxml"));
	    khachhangButton.setOnAction(event -> handleButtonClick(khachhangButton, "/view/QLKhachHang.fxml"));
	    nhanVienButton.setOnAction(event -> handleButtonClick(nhanVienButton, "/view/QLNhanVienView.fxml"));
	    thongKeButton.setOnAction(event -> handleButtonClick(thongKeButton, "/view/ThongKe.fxml"));
	    dangXuatButton.setOnMouseClicked((MouseEvent event) -> {
            ((Stage)((dangXuatButton.getScene()).getWindow())).hide();
    		Stage stg = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
    		stg.initStyle(StageStyle.TRANSPARENT);
    		Scene scene;
			try {
				scene = new Scene(loader.load());
				scene.setFill(Color.TRANSPARENT);
	    		stg.setScene(scene);
	    		stg.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
	    
	    Divider divider = splitPaneNavigatorAndContent.getDividers().get(0);
	    divider.positionProperty().addListener((observableVal,oldVal,newVal)->{
	    	if(newVal.doubleValue() > 0.1386) 
	    		divider.setPosition(0.1386);
	    });
	}
	
	private void handleButtonClick(Button clickedButton, String path) {
	    if (selectedButton != null) {
	        selectedButton.setStyle("-fx-background-color: #44707A; -fx-font-size: 13; -fx-font-weight: BOLD;"); // Set the style of the previously selected button to its normal state
	    }
	    clickedButton.setStyle("-fx-background-color: #1EB2A6; -fx-font-size: 13; -fx-font-weight: BOLD;"); // Set the style of the clicked button to the desired color
	    selectedButton = clickedButton; // Update the selected button to the clicked button
	    
	    try {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
	        Parent fxmlRoot = loader.load();

	        thaoTacAnchorPane.getChildren().setAll(fxmlRoot);
	        AnchorPane.setTopAnchor(fxmlRoot, 0.0);
	        AnchorPane.setBottomAnchor(fxmlRoot, 0.0);
	        AnchorPane.setLeftAnchor(fxmlRoot, 0.0);
	        AnchorPane.setRightAnchor(fxmlRoot, 0.0);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
