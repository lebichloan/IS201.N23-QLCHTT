package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import db.database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.SanPham;

public class QLSanPhamController implements Initializable{
	@FXML
	private TableView<SanPham> table;
	@FXML
	private TableColumn<SanPham,String> maSPColumn;
	@FXML
	private TableColumn<SanPham,String> tenSPColumn;
	@FXML
	private TableColumn<SanPham,String> mauSacColumn;
	@FXML
	private TableColumn<SanPham,String> kichThuocColumn;
	@FXML
	private TableColumn<SanPham,String> tinhTrangColumn;
	@FXML
	private TableColumn<SanPham,String> maLSPColumn;
	
	private ObservableList<SanPham> listSP;
	
	@FXML
	private TextField maSPTextField;
	@FXML
	private TextField tenSPTextField;
	@FXML
	private TextField mauSacTextField;
	@FXML
	private TextField kichThuocTextField;
	@FXML
	private TextField tinhTrangTextField;
	@FXML
	private TextField maLSPTextField;
	
	@FXML
	private Button add;
	
	@FXML
	private Button update;
	
	@FXML
	private Button delete;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			listSP = FXCollections.observableArrayList();
			updateList();
			maSPColumn.setCellValueFactory(new PropertyValueFactory<SanPham,String>("maSP"));
			tenSPColumn.setCellValueFactory(new PropertyValueFactory<SanPham,String>("tenSP"));
			mauSacColumn.setCellValueFactory(new PropertyValueFactory<SanPham,String>("mauSac"));
			kichThuocColumn.setCellValueFactory(new PropertyValueFactory<SanPham,String>("kichThuoc"));
			tinhTrangColumn.setCellValueFactory(new PropertyValueFactory<SanPham,String>("tinhTrang"));
			maLSPColumn.setCellValueFactory(new PropertyValueFactory<SanPham,String>("maLSP"));
			table.setItems(listSP);
			
			table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		        if (newSelection != null) {
		        	maSPTextField.setText(newSelection.getMaSP());
		            maSPTextField.setDisable(true); // Disable the maSPTextField
		            tenSPTextField.setText(newSelection.getTenSP());
		            mauSacTextField.setText(newSelection.getMauSac());
		            kichThuocTextField.setText(newSelection.getKichThuoc());
		            tinhTrangTextField.setText(newSelection.getTinhTrang());
		            maLSPTextField.setText(newSelection.getMaLSP());
		        } else {
		            maSPTextField.setDisable(false); // Enable the maSPTextField
		            tenSPTextField.setText("");
		            mauSacTextField.setText("");
		            kichThuocTextField.setText("");
		            tinhTrangTextField.setText("");
		            maLSPTextField.setText("");
		        }
		    });
			
//			// Clear the selection when the table loses focus
//		    table.focusedProperty().addListener((observable, oldValue, newValue) -> {
//		        if (!newValue) {
//		            table.getSelectionModel().clearSelection();
//		        }
//		    });
//
//		    // Clear the selection when the user clicks on something else on the screen
//		    table.getParent().addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
//		        if (!table.getBoundsInParent().contains(event.getX(), event.getY())) {
//		            table.getSelectionModel().clearSelection();
//		        }
//		    });

			add.setOnAction(new EventHandler<ActionEvent>() {
		        @Override
		        public void handle(ActionEvent event) {
		        	SanPham newSP = new SanPham();
		    	    newSP.setMaSP(maSPTextField.getText());
		    	    newSP.setTenSP(tenSPTextField.getText());
		    	    newSP.setMauSac(mauSacTextField.getText());
		    	    newSP.setKichThuoc(kichThuocTextField.getText());
		    	    newSP.setTinhTrang(tinhTrangTextField.getText());
		    	    newSP.setMaLSP(maLSPTextField.getText());
		    	    
		    	    // Add the new SanPham to the database
		    	    try {
		    	        String query = "INSERT INTO SANPHAM (maSP, tenSP, mauSac, kichThuoc, tinhTrang, maLSP) " +
		    	                       "VALUES (?, ?, ?, ?, ?, ?)";
		    	        PreparedStatement statement = database.connection.prepareStatement(query);
		    	        statement.setString(1, newSP.getMaSP());
		    	        statement.setString(2, newSP.getTenSP());
		    	        statement.setString(3, newSP.getMauSac());
		    	        statement.setString(4, newSP.getKichThuoc());
		    	        statement.setString(5, newSP.getTinhTrang());
		    	        statement.setString(6, newSP.getMaLSP());
		    	        statement.executeUpdate();
		    	        String insertStatement = "INSERT INTO SANPHAM (maSP, tenSP, mauSac, kichThuoc, tinhTrang, maLSP) " +
		    	                "VALUES ('" + newSP.getMaSP() + "', '" + newSP.getTenSP() + "', '" + newSP.getMauSac() + "', '" +
		    	                newSP.getKichThuoc() + "', '" + newSP.getTinhTrang() + "', '" + newSP.getMaLSP() + "')";
		    	        FileWriter fileWriter = new FileWriter("QLCHTT.sql", true);
		    	        fileWriter.write(insertStatement + ";\n");
		    	        fileWriter.close();
		    	        System.out.println("New SanPham added to database: " + newSP);
		    	    } catch (SQLException | IOException ex) {
		    	        System.out.println("Error adding SanPham to database: " + ex.getMessage());
		    	    }
		    	    
		    	    // Add the new SanPham to the TableView
		    	    listSP.add(newSP);
		        }
		    });
			
			
			update.setOnAction(new EventHandler<ActionEvent>() {
		        @Override
		        public void handle(ActionEvent event) {
		        	
		        		try {
				    	    
		        			SanPham updateSP = new SanPham();
		        			updateSP.setMaSP(maSPTextField.getText());
				    	    String query = "UPDATE SANPHAM SET tensp=?, mausac=?, kichthuoc=?, tinhtrang=?, malsp=? WHERE masp=?";
				            PreparedStatement statement = database.connection.prepareStatement(query);
				            statement.setString(1, tenSPTextField.getText());
				            statement.setString(2, mauSacTextField.getText());
				            statement.setString(3, kichThuocTextField.getText());
				            statement.setString(4, tinhTrangTextField.getText());
				            statement.setString(5, maLSPTextField.getText());
				            statement.setString(6, updateSP.getMaSP());
				            statement.executeUpdate();
				            updateSP.setTenSP(tenSPTextField.getText());
				            updateSP.setMauSac(mauSacTextField.getText());
				            updateSP.setKichThuoc(kichThuocTextField.getText());
				            updateSP.setTinhTrang(tinhTrangTextField.getText());
				            updateSP.setMaLSP(maLSPTextField.getText());
				            int selectedIndex = table.getSelectionModel().getSelectedIndex();
				            listSP.set(selectedIndex, updateSP);
				            table.setItems(listSP);
				            
		        		}catch (SQLException e) {
		    	            System.out.println("Query failed: " + e.getMessage());
		    	        }
		        	}
		        
			});
			
			//ergeg
			delete.setOnAction(new EventHandler<ActionEvent>() {
		        @Override
		        public void handle(ActionEvent event) {
		        	SanPham selected = table.getSelectionModel().getSelectedItem();
		    	    if (selected != null) {
		    	        try {
		    	            String deleteQuery = "DELETE FROM SANPHAM WHERE maSP = ?";
		    	            PreparedStatement statement = database.connection.prepareStatement(deleteQuery);
		    	            statement.setString(1, selected.getMaSP());
		    	            int rowsDeleted = statement.executeUpdate();
		    	            if (rowsDeleted > 0) {
		    	                listSP.remove(selected);
		    	                System.out.println("Deleted " + rowsDeleted + " row(s) from the database");
		    	            } else {
		    	                System.out.println("No rows deleted from the database");
		    	            }
		    	        } catch (SQLException ex) {
		    	            System.out.println("Query failed: " + ex.getMessage());
		    	        }
		    	    }
		        }
			});
	}
	
	public void updateList() {
		try {
			String query = "SELECT * FROM SANPHAM";
			Statement statement = database.connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				SanPham newSP = new SanPham();
				newSP.setMaSP(resultSet.getString("maSP"));
				newSP.setTenSP(resultSet.getString("tenSP"));
				newSP.setMauSac(resultSet.getString("mauSac"));
				newSP.setKichThuoc(resultSet.getString("kichThuoc"));
				newSP.setTinhTrang(resultSet.getString("tinhTrang"));
				newSP.setMaLSP(resultSet.getString("maLSP"));
			    listSP.add(newSP);
			}
		} catch (SQLException e) {
	    System.out.println("Query failed: " + e.getMessage());
		}
	}
	

}
