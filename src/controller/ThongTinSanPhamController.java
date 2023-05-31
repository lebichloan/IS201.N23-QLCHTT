package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.ButtonBar;
import db.database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ThongTinSanPhamController implements Initializable {
    @FXML
    private Button quayLaiButton;
    @FXML
    private Button luuButton;
    @FXML
    private Text maSPText;
    @FXML
    private TextField tenSanPhamTextField;
    @FXML
    private TextField moTaTextField;
    @FXML
    private TextField thuongHieuTextField;
    @FXML
    private TextField donViTinhTextField;
    @FXML
    private TextField kichThuocTextField;
    @FXML
    private TextField mauSacTextField;
    @FXML
    private TextField soLuongTextField;
    @FXML
    private TextField donGiaTextField;
    @FXML
    private MenuButton chonDanhMucMenuButton;
    @FXML
    private DatePicker ngayThemDatePicker;

    public static String maSP;

    public String getMaSP() {
        return maSP;
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	luuButton.setDisable(true);
        loadData();
        tenSanPhamTextField.textProperty().addListener((observable, oldValue, newValue) -> updateLuuButtonDisable());
        moTaTextField.textProperty().addListener((observable, oldValue, newValue) -> updateLuuButtonDisable());
        thuongHieuTextField.textProperty().addListener((observable, oldValue, newValue) -> updateLuuButtonDisable());
        donViTinhTextField.textProperty().addListener((observable, oldValue, newValue) -> updateLuuButtonDisable());
        kichThuocTextField.textProperty().addListener((observable, oldValue, newValue) -> updateLuuButtonDisable());
        mauSacTextField.textProperty().addListener((observable, oldValue, newValue) -> updateLuuButtonDisable());
        soLuongTextField.textProperty().addListener((observable, oldValue, newValue) -> updateLuuButtonDisable());
        donGiaTextField.textProperty().addListener((observable, oldValue, newValue) -> updateLuuButtonDisable());

        // Add a listener to the date picker
        ngayThemDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> updateLuuButtonDisable());

        // Add a listener to the menu button
        chonDanhMucMenuButton.setOnAction(event -> updateLuuButtonDisable());
        
        quayLaiButton.setOnMouseClicked((MouseEvent event) -> {
        	 Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        	    confirmationDialog.setTitle("Confirmation");
        	    confirmationDialog.setHeaderText(null);
        	    confirmationDialog.setContentText("Thoát ra sẽ không lưu giá trị thay đổi, bạn có muốn tiếp tục?");

        	    // Add buttons to the confirmation dialog
        	    ButtonType yesButton = new ButtonType("Yes");
        	    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        	    confirmationDialog.getButtonTypes().setAll(yesButton, noButton);

        	    // Show the confirmation dialog and wait for user input
        	    Optional<ButtonType> result = confirmationDialog.showAndWait();

        	    // Handle user's choice
        	    if (result.isPresent() && result.get() == yesButton) {
        	        // User chose to continue, navigate back to "/view/QLSanPham.fxml"
        	    	Stage dialogStage = (Stage) quayLaiButton.getScene().getWindow();
        	        dialogStage.close();
        	    } else {
        	        // User chose not to continue, close the dialog
        	    	confirmationDialog.close();
        	    }
        });
        
        
        
        luuButton.setOnMouseClicked((MouseEvent event) -> {
        	// Create a confirmation dialog
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation");
            confirmationDialog.setHeaderText(null);
            confirmationDialog.setContentText("Lưu giá trị thay đổi?");

            // Add buttons to the confirmation dialog
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmationDialog.getButtonTypes().setAll(yesButton, noButton);

            // Show the confirmation dialog and wait for user input
            Optional<ButtonType> result = confirmationDialog.showAndWait();

            // Handle user's choice
            if (result.isPresent() && result.get() == yesButton) {
                // User chose to save, perform the update operation

                // Get the values from the text fields, date picker, menu button, etc.
                String maSP = maSPText.getText();
                String tenSP = tenSanPhamTextField.getText();
                String moTa = moTaTextField.getText();
                String thuongHieu = thuongHieuTextField.getText();
                String donViTinh = donViTinhTextField.getText();
                String kichThuoc = kichThuocTextField.getText();
                String mauSac = mauSacTextField.getText();
                int soLuong = Integer.parseInt(soLuongTextField.getText());
                String maLSP = chonDanhMucMenuButton.getText();
                // ... Get values from other fields as needed
                


                String updateQuery = "UPDATE SanPham SET "
                        + "ten_sp = ?, mo_ta = ?, thuong_hieu = ?, dvt = ?, kich_thuoc = ?, mau_sac = ?, "
                        + "so_luong = ?, ma_lsp = ? "
                        + "WHERE ma_sp = ?";

                try (PreparedStatement statement = database.connection.prepareStatement(updateQuery)) {
                    statement.setString(1, tenSP);
                    statement.setString(2, moTa);
                    statement.setString(3, thuongHieu);
                    statement.setString(4, donViTinh);
                    statement.setString(5, kichThuoc);
                    statement.setString(6, mauSac);
                    statement.setInt(7, soLuong);
                    statement.setString(8, maLSP);
                    statement.setString(9, maSP);

                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        // Update successful
                        System.out.println("Update successful");
                    } else {
                        // Update failed
                        System.out.println("Update failed");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // Close the confirmation dialog
                Stage dialogStage = (Stage) quayLaiButton.getScene().getWindow();
    	        dialogStage.close();
    	        
            } else if (result.isPresent() && result.get() == noButton) {
            	Stage dialogStage = (Stage) quayLaiButton.getScene().getWindow();
    	        dialogStage.close();
            } else {
                // User closed the dialog or clicked cancel, close the confirmation dialog
                confirmationDialog.close();
            }
        });
    
    }
    
    private void updateLuuButtonDisable() {
        // Enable the luuButton
        luuButton.setDisable(false);
    }

    private void loadData() {
        // Retrieve data from the database based on the maSP value
        String query = "SELECT * FROM SanPham WHERE ma_SP = ?";
        try {
        	PreparedStatement statement = database.connection.prepareStatement(query);
            statement.setString(1, maSP);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Retrieve the data from the result set
                String tenSP = resultSet.getString("ten_SP");
                String moTa = resultSet.getString("mo_ta");
                String thuongHieu = resultSet.getString("thuong_hieu");
                String donViTinh = resultSet.getString("dvt");
                String kichThuoc = resultSet.getString("kich_thuoc");
                String mauSac = resultSet.getString("mau_sac");
                int soLuong = resultSet.getInt("so_luong");
                LocalDate ngayThem = resultSet.getDate("ngay_them").toLocalDate();
                String gia = resultSet.getString("gia");
                String maLSP = resultSet.getString("ma_lsp");

                // Set the retrieved data to the respective UI controls
                maSPText.setText(maSP);
                tenSanPhamTextField.setText(tenSP);
                moTaTextField.setText(moTa);
                thuongHieuTextField.setText(thuongHieu);
                donViTinhTextField.setText(donViTinh);
                kichThuocTextField.setText(kichThuoc);
                mauSacTextField.setText(mauSac);
                soLuongTextField.setText(String.valueOf(soLuong));
                ngayThemDatePicker.setValue(ngayThem);
                donGiaTextField.setText(gia);
                populateDanhMucMenuButton();
                chonDanhMucMenuButton.setText(maLSP);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add event handlers for the buttons or any other controls as needed
    private void populateDanhMucMenuButton() {
        String query = "SELECT ma_lsp FROM LoaiSanPham";
        try {
            PreparedStatement statement = database.connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String maLSP = resultSet.getString("ma_lsp");
                MenuItem menuItem = new MenuItem(maLSP);
                menuItem.setOnAction(event -> chonDanhMucMenuButton.setText(maLSP));
                chonDanhMucMenuButton.getItems().add(menuItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleQuayLaiButtonAction() {
        // Handle the action for the "quayLaiButton"
    }

    @FXML
    private void handleLuuButtonAction() {
        // Handle the action for the "luuButton"
    }
}