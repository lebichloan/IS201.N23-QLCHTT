package controller;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import db.database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ThemSanPhamController implements Initializable{
	
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
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		luuButton.setDisable(true);
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
        populateDanhMucMenuButton();
        maSPText.setText(generateNextMaSP());
        
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
        	    QLSanPhamController.updateList();
        	    QLSanPhamController.updateLoaiSPList();
        });
        
        
        
        luuButton.setOnMouseClicked((MouseEvent event) -> {
        	// Create a confirmation dialog
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation");
            confirmationDialog.setHeaderText(null);
            confirmationDialog.setContentText("Xác nhận thêm sản phẩm?");

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
                Double gia = Double.parseDouble(donGiaTextField.getText());
                String maLSP = chonDanhMucMenuButton.getText();
                


                String updateQuery = "INSERT INTO SanPham (MA_SP, TEN_SP, MO_TA, THUONG_HIEU, DVT, KICH_THUOC, MAU_SAC, SO_LUONG, GIA, MA_LSP,  Ngay_Them) "
                        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE) ";

                try (PreparedStatement statement = database.connection.prepareStatement(updateQuery)) {
                	statement.setString(1, maSP);
                    statement.setString(2, tenSP);
                    statement.setString(3, moTa);
                    statement.setString(4, thuongHieu);
                    statement.setString(5, donViTinh);
                    statement.setString(6, kichThuoc);
                    statement.setString(7, mauSac);
                    statement.setInt(8, soLuong);
                    statement.setDouble(9, gia);
                    statement.setString(10, maLSP);

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
            QLSanPhamController.updateList();
            QLSanPhamController.updateLoaiSPList();
            
        });
		
	}

	private void updateLuuButtonDisable() {
        boolean isNotNullAndChanged = !tenSanPhamTextField.getText().isEmpty() &&
                !moTaTextField.getText().isEmpty() &&
                !thuongHieuTextField.getText().isEmpty() &&
                !donViTinhTextField.getText().isEmpty() &&
                !kichThuocTextField.getText().isEmpty() &&
                !mauSacTextField.getText().isEmpty() &&
                !soLuongTextField.getText().isEmpty() &&
                !donGiaTextField.getText().isEmpty();
        
        luuButton.setDisable(!isNotNullAndChanged);
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
    
    private String generateNextMaSP() {
        String nextMaSP = "";
        try {
            // Establish the database connection
            Statement statement = database.connection.createStatement();

            // Execute the PL/SQL block to generate the next MaSP
            String query = "DECLARE\r\n"
                    + "   v_MaSP NVARCHAR2(10);\r\n"
                    + "   v_TotalSP NVARCHAR2(10);\r\n"
                    + "BEGIN\r\n"
                    + "   SELECT MAX(MA_SP) INTO v_MaSP FROM SANPHAM;\r\n"
                    + "   SELECT COUNT(MA_SP) INTO v_TotalSP FROM SANPHAM;\r\n"
                    + "   \r\n"
                    + "   IF v_MaSP IS NULL THEN\r\n"
                    + "      v_MaSP := 'SP001';\r\n"
                    + "   ELSIF v_MaSP = v_TotalSP THEN\r\n"
                    + "      -- Increment the numerical part of the string\r\n"
                    + "      v_MaSP := 'SP' || TO_CHAR(TO_NUMBER(SUBSTR(v_MaSP, 3)) + 1, 'FM000');\r\n"
                    + "   ELSE\r\n"
                    + "      -- Find the smallest available string by incrementing the numerical part\r\n"
                    + "      SELECT 'SP' || TO_CHAR(TO_NUMBER(MIN(SUBSTR(MA_SP, 3))) + 1, 'FM000') INTO v_MaSP\r\n"
                    + "      FROM SANPHAM\r\n"
                    + "      WHERE ('SP' || TO_CHAR(TO_NUMBER(SUBSTR(MA_SP, 3)) + 1, 'FM000')) NOT IN (SELECT MA_SP FROM SANPHAM);\r\n"
                    + "   END IF;\r\n"
                    + "\r\n"
                    + "   ? := v_MaSP; -- Assign the generated value to the output parameter\r\n"
                    + "END;";
            
            CallableStatement callableStatement = database.connection.prepareCall(query);
            callableStatement.registerOutParameter(1, Types.NVARCHAR); // Register the output parameter
            callableStatement.execute();
            
            nextMaSP = callableStatement.getString(1); // Retrieve the generated value
            System.out.println(nextMaSP);
            
            // Close the resources
            callableStatement.close();
            statement.close();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        return nextMaSP;
    }

	

}
