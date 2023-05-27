package controller;

import java.io.FileWriter;
import javafx.beans.binding.Bindings;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import db.database;
import javafx.collections.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.SanPham;
import model.LoaiSanPham;


public class QLSanPhamController implements Initializable{
	
	
//------------------------------------------SẢN PHẨM--------------------------------------
	
	@FXML
	private TableView<SanPham> table;
	
	@FXML
	private Tab quanLySPTab;
	@FXML
	private TableColumn<SanPham,String> maSPColumn;
	@FXML
	private TableColumn<SanPham,String> tenSPColumn;
	
	@FXML
	private TableColumn<SanPham,String> danhmucSPColumn;
	
	@FXML
	private TableColumn<SanPham,Double> soLuongColumn;
	
	@FXML
	private TableColumn<SanPham,Double> donViTinhColumn;
	
	@FXML
	private TableColumn<SanPham,String> ghiChuSPColumn;

	@FXML
	private TableColumn<SanPham,String> tinhTrangSPColumn;

	@FXML
	private TableColumn<SanPham,String> chooseColumn;
	
	@FXML
    private ComboBox<String> chonDanhMucComboBox;
	
	@FXML
    private ComboBox<String> chonTinhTrangComboBox;
	
	@FXML
    private ComboBox<String> chonDonViTinhComboBox;
	
	@FXML
	private Text daChonText;
	
	private ObservableList<SanPham> listSP;
	
	@FXML
	private Text tongSoText;
	
//	@FXML
//	private TextField maSPTextField;
//	@FXML
//	private TextField tenSPTextField;
//	@FXML
//	private TextField mauSacTextField;
//	@FXML
//	private TextField kichThuocTextField;
//	@FXML
//	private TextField tinhTrangTextField;
//	@FXML
//	private TextField maLSPTextField;
	
	@FXML
	private Button themSanPhamButton;
	
	@FXML
	private Button xemChiTietButton;
	
	@FXML
	private Button apDungButton;
//	
	@FXML
	private TextField timSanPhamTextField;
	
	@FXML
	private Button timKiemButton;
//	@FXML
//	private Button update;
//	
	@FXML
	private Button xoaSanPhambutton;
	
	@FXML
	AnchorPane root;
	

	
	private static String maSPDaChon;

	
	public void exitFocused(MouseEvent event) {
		if(event.getButton() == MouseButton.PRIMARY) {
			root.requestFocus();
		}
	}
	
	private void createCenteredCellFactory(TableColumn<SanPham, String> column) {
	    column.setCellFactory(col -> new TableCell<SanPham, String>() {
	        @Override
	        protected void updateItem(String item, boolean empty) {
	            super.updateItem(item, empty);

	            if (item == null || empty) {
	                setText(null);
	            } else {
	                setText(item);
	                setAlignment(Pos.CENTER); // Center-align the text
	            }
	        }
	    });
	}
	
	private void createCenteredCellFactoryDouble(TableColumn<SanPham, Double> column) {
	    column.setCellFactory(col -> new TableCell<SanPham, Double>() {
	        @Override
	        protected void updateItem(Double item, boolean empty) {
	            super.updateItem(item, empty);

	            if (item == null || empty) {
	                setText(null);
	            } else {
	                setText(String.valueOf(item));
	                setAlignment(Pos.CENTER); // Center-align the text
	            }
	        }
	    });
	}
	
	
	
//-----------------------------------------LOẠI SẢN PHẨM----------------------------------------------
	
	@FXML
	private TableView<LoaiSanPham> tableLoaiSP;
	
	@FXML
	private TableColumn<LoaiSanPham,String> maLoaiSPColumn;
	
	@FXML
	private TableColumn<LoaiSanPham,String> sttLoaiSPColumn;
	
	
	@FXML
	private TableColumn<LoaiSanPham,String> tenLoaiSPColumn;
	
	@FXML
	private TableColumn<LoaiSanPham,String> moTaLoaiSPColumn;
	
	@FXML
	private TableColumn<LoaiSanPham,Double> soLuongMatHangLoaiSPColumn;
	
	@FXML
	private TableColumn<LoaiSanPham,String> tinhTrangLoaiSPColumn;
	
	@FXML
	private TableColumn<LoaiSanPham,String> ghiChuLoaiSPColumn;
	
	private ObservableList<LoaiSanPham> listLoaiSP;
	
	


	private void createCenteredCellFactoryLSP(TableColumn<LoaiSanPham, String> column) {
	    column.setCellFactory(col -> new TableCell<LoaiSanPham, String>() {
	        @Override
	        protected void updateItem(String item, boolean empty) {
	            super.updateItem(item, empty);

	            if (item == null || empty) {
	                setText(null);
	            } else {
	                setText(item);
	                setAlignment(Pos.CENTER); // Center-align the text
	            }
	        }
	    });
	}
	
	private void createCenteredCellFactoryDoubleLSP(TableColumn<LoaiSanPham, Double> column) {
	    column.setCellFactory(col -> new TableCell<LoaiSanPham, Double>() {
	        @Override
	        protected void updateItem(Double item, boolean empty) {
	            super.updateItem(item, empty);

	            if (item == null || empty) {
	                setText(null);
	            } else {
	                setText(String.valueOf(item));
	                setAlignment(Pos.CENTER); // Center-align the text
	            }
	        }
	    });
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			listSP = FXCollections.observableArrayList();
			updateList();
			maSPColumn.setCellValueFactory(new PropertyValueFactory<SanPham,String>("maSP"));
			tenSPColumn.setCellValueFactory(new PropertyValueFactory<SanPham,String>("tenSP"));
			danhmucSPColumn.setCellValueFactory(new PropertyValueFactory<SanPham,String>("danhMuc"));
			soLuongColumn.setCellValueFactory(new PropertyValueFactory<SanPham,Double>("soLuong"));
			tinhTrangSPColumn.setCellValueFactory(new PropertyValueFactory<SanPham,String>("tinhTrang"));
			donViTinhColumn.setCellValueFactory(new PropertyValueFactory<SanPham,Double>("donViTinh"));
			ghiChuSPColumn.setCellValueFactory(new PropertyValueFactory<SanPham,String>("ghiChu"));
			table.setItems(listSP);
			
			
//			createCenteredCellFactory(maSPColumn);
//			createCenteredCellFactory(tenSPColumn);
//			createCenteredCellFactory(danhmucSPColumn);
//			createCenteredCellFactoryDouble(soLuongColumn);
//			createCenteredCellFactory(tinhTrangSPColumn);
//			createCenteredCellFactoryDouble(donViTinhColumn);
//			createCenteredCellFactory(ghiChuSPColumn);
	        // Set the selection mode to MULTIPLE
	        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			
//			table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//		        if (newSelection != null) {
//		        	maSPTextField.setText(newSelection.getMaSP());
//		            maSPTextField.setDisable(true); // Disable the maSPTextField
//		            tenSPTextField.setText(newSelection.getTenSP());
//		            mauSacTextField.setText(newSelection.getMauSac());
//		            kichThuocTextField.setText(newSelection.getKichThuoc());
//		            tinhTrangTextField.setText(newSelection.getTinhTrang());
//		            maLSPTextField.setText(newSelection.getMaLSP());
//		        } else {
//		            maSPTextField.setDisable(false); // Enable the maSPTextField
//		            tenSPTextField.setText("");
//		            mauSacTextField.setText("");
//		            kichThuocTextField.setText("");
//		            tinhTrangTextField.setText("");
//		            maLSPTextField.setText("");
//		        }
//		    });
			
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

//			add.setOnAction(new EventHandler<ActionEvent>() {
//		        @Override
//		        public void handle(ActionEvent event) {
//		        	SanPham newSP = new SanPham();
//		    	    newSP.setMaSP(maSPTextField.getText());
//		    	    newSP.setTenSP(tenSPTextField.getText());
//		    	    newSP.setMauSac(mauSacTextField.getText());
//		    	    newSP.setKichThuoc(kichThuocTextField.getText());
//		    	    newSP.setTinhTrang(tinhTrangTextField.getText());
//		    	    newSP.setMaLSP(maLSPTextField.getText());
//		    	    
//		    	    // Add the new SanPham to the database
//		    	    try {
//		    	        String query = "INSERT INTO SANPHAM (maSP, tenSP, mauSac, kichThuoc, tinhTrang, maLSP) " +
//		    	                       "VALUES (?, ?, ?, ?, ?, ?)";
//		    	        PreparedStatement statement = database.connection.prepareStatement(query);
//		    	        statement.setString(1, newSP.getMaSP());
//		    	        statement.setString(2, newSP.getTenSP());
//		    	        statement.setString(3, newSP.getMauSac());
//		    	        statement.setString(4, newSP.getKichThuoc());
//		    	        statement.setString(5, newSP.getTinhTrang());
//		    	        statement.setString(6, newSP.getMaLSP());
//		    	        statement.executeUpdate();
//		    	        String insertStatement = "INSERT INTO SANPHAM (maSP, tenSP, mauSac, kichThuoc, tinhTrang, maLSP) " +
//		    	                "VALUES ('" + newSP.getMaSP() + "', '" + newSP.getTenSP() + "', '" + newSP.getMauSac() + "', '" +
//		    	                newSP.getKichThuoc() + "', '" + newSP.getTinhTrang() + "', '" + newSP.getMaLSP() + "')";
//		    	        FileWriter fileWriter = new FileWriter("QLCHTT.sql", true);
//		    	        fileWriter.write(insertStatement + ";\n");
//		    	        fileWriter.close();
//		    	        System.out.println("New SanPham added to database: " + newSP);
//		    	    } catch (SQLException | IOException ex) {
//		    	        System.out.println("Error adding SanPham to database: " + ex.getMessage());
//		    	    }
//		    	    
//		    	    // Add the new SanPham to the TableView
//		    	    listSP.add(newSP);
//		        }
//		    });
//			
//			
//			update.setOnAction(new EventHandler<ActionEvent>() {
//		        @Override
//		        public void handle(ActionEvent event) {
//		        	
//		        		try {
//				    	    
//		        			SanPham updateSP = new SanPham();
//		        			updateSP.setMaSP(maSPTextField.getText());
//				    	    String query = "UPDATE SANPHAM SET tensp=?, mausac=?, kichthuoc=?, tinhtrang=?, malsp=? WHERE masp=?";
//				            PreparedStatement statement = database.connection.prepareStatement(query);
//				            statement.setString(1, tenSPTextField.getText());
//				            statement.setString(2, mauSacTextField.getText());
//				            statement.setString(3, kichThuocTextField.getText());
//				            statement.setString(4, tinhTrangTextField.getText());
//				            statement.setString(5, maLSPTextField.getText());
//				            statement.setString(6, updateSP.getMaSP());
//				            statement.executeUpdate();
//				            updateSP.setTenSP(tenSPTextField.getText());
//				            updateSP.setMauSac(mauSacTextField.getText());
//				            updateSP.setKichThuoc(kichThuocTextField.getText());
//				            updateSP.setTinhTrang(tinhTrangTextField.getText());
//				            updateSP.setMaLSP(maLSPTextField.getText());
//				            int selectedIndex = table.getSelectionModel().getSelectedIndex();
//				            listSP.set(selectedIndex, updateSP);
//				            table.setItems(listSP);
//				            
//		        		}catch (SQLException e) {
//		    	            System.out.println("Query failed: " + e.getMessage());
//		    	        }
//		        	}
//		        
//			});
//			
//			//ergeg
//			delete.setOnAction(new EventHandler<ActionEvent>() {
//		        @Override
//		        public void handle(ActionEvent event) {
//		        	SanPham selected = table.getSelectionModel().getSelectedItem();
//		    	    if (selected != null) {
//		    	        try {
//		    	            String deleteQuery = "DELETE FROM SANPHAM WHERE maSP = ?";
//		    	            PreparedStatement statement = database.connection.prepareStatement(deleteQuery);
//		    	            statement.setString(1, selected.getMaSP());
//		    	            int rowsDeleted = statement.executeUpdate();
//		    	            if (rowsDeleted > 0) {
//		    	                listSP.remove(selected);
//		    	                System.out.println("Deleted " + rowsDeleted + " row(s) from the database");
//		    	            } else {
//		    	                System.out.println("No rows deleted from the database");
//		    	            }
//		    	        } catch (SQLException ex) {
//		    	            System.out.println("Query failed: " + ex.getMessage());
//		    	        }
//		    	    }
//		        }
//			});
	        
	        themSanPhamButton.setOnMouseClicked((MouseEvent event) -> {
				try {
				    FXMLLoader loader = new FXMLLoader(common.class.getResource("/view/ThemSanPham.fxml"));
				    AnchorPane root = loader.load();

				   

				    // Create a Scene object with the root node and set it to the primary stage
				    Scene scene = new Scene(root);
				    Stage window = new Stage();
				    window.setScene(scene);
				    //window.setMaximized(true);
				    window.setResizable(false);
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
	        
	        timKiemButton.setOnMouseClicked((MouseEvent event) -> {
	        	listSP.clear();
	            String timKiem = timSanPhamTextField.getText();
	            if (timKiem != null && !timKiem.isEmpty()) {
	                try {
	                    String query = "SELECT * FROM SANPHAM WHERE TEN_SP = ?";
	                    PreparedStatement statement = database.connection.prepareStatement(query);
	                    statement.setString(1, timKiem);
	                 
	                    
	                    ResultSet resultSet = statement.executeQuery();
	                    while (resultSet.next()) {
	        				SanPham newSP = new SanPham();
	        				newSP.setMaSP(resultSet.getString("MA_SP"));
	        				newSP.setTenSP(resultSet.getString("TEN_SP"));
	        				newSP.setSoLuong(resultSet.getInt("SO_LUONG"));
	        				newSP.setDanhMuc(resultSet.getString("MA_LSP"));
	        				newSP.setTinhTrang(resultSet.getString("tinhTrang"));
	        				newSP.setDonViTinh(resultSet.getString("Dvt"));
	        				newSP.setGhiChu(resultSet.getString("Mo_ta"));
	        			    listSP.add(newSP);
	        			}
	                    
	                    // Process the ResultSet as needed
	                    

	                    statement.close();
	                } catch (SQLException e) {
	                    System.out.println("Query failed: " + e.getMessage());
	                }
	            }
	        });
	        
	        xemChiTietButton.setOnMouseClicked((MouseEvent event) -> {
				try {
				    FXMLLoader loader = new FXMLLoader(common.class.getResource("/view/ThongTinSanPham.fxml"));
				    AnchorPane root = loader.load();

				   

				    // Create a Scene object with the root node and set it to the primary stage
				    Scene scene = new Scene(root);
				    Stage window = new Stage();
				    window.setScene(scene);
				    //window.setMaximized(true);
				    window.setResizable(false);
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
	        
	        try {

	        	ObservableList<String> dataMaLSP = FXCollections.observableArrayList();
	            ObservableList<String> dataTinhTrang = FXCollections.observableArrayList();
	            ObservableList<String> dataDonViTinh = FXCollections.observableArrayList();
	            
	            Statement statement = database.connection.createStatement();
	            ResultSet resultSet = statement.executeQuery("SELECT Distinct Ma_LSP FROM LOAISanPham");
	            while (resultSet.next()) {
	                String name1 = resultSet.getString("Ma_LSP");
	                dataMaLSP.add(name1);
	            }
	            resultSet = statement.executeQuery("SELECT Distinct tinhtrang FROM SanPham");
	            while (resultSet.next()) {
	                String name2 = resultSet.getString("TINHTRANG");
	                System.out.println(name2);
	                dataTinhTrang.add(name2);
	            }
	            resultSet = statement.executeQuery("SELECT Distinct dvt FROM SanPham");
	            while (resultSet.next()) {
	                String name3 = resultSet.getString("dvt");
	                dataDonViTinh.add(name3);
	            }


	            // Set the ObservableList as the data source for the ComboBox
	            chonDanhMucComboBox.setItems(dataMaLSP);
	            chonTinhTrangComboBox.setItems(dataTinhTrang);
	            chonDonViTinhComboBox.setItems(dataDonViTinh);

	            // Close the connection and resources
	            resultSet.close();
	            statement.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	        apDungButton.setOnAction(event -> {
	            String selectedMaLSP = chonDanhMucComboBox.getValue();
	            String selectedTinhtrang = chonTinhTrangComboBox.getValue();
	            String selectedDonViTinh = chonDonViTinhComboBox.getValue();

	            
	            if (!(selectedMaLSP == null && selectedTinhtrang == null && selectedDonViTinh == null)) {
	                    
		            String query = "SELECT * FROM SANPHAM WHERE 1=1 ";
	                    if (selectedMaLSP != null) {
	                        query += " AND ma_LSP = " ;
	                        query = query +  "'" + selectedMaLSP + "'";
	                    }
	                    
	                    if (selectedTinhtrang != null) {
	                        query += " AND TinhTrang = ";
	                        query = query +  "'" + selectedTinhtrang + "'";
	                    }
	                    
	                    if (selectedDonViTinh != null) {
	                        query += " AND Dvt = ";
	                        query = query +  "'" + selectedDonViTinh + "'";
	                    }
	                    
	                    listSP.clear();
	                    System.out.println(query);
	                    updateList(query);
	            }
	        });
	        
	     // Listener for updating the selected count
	        table.getSelectionModel().getSelectedItems().addListener((ListChangeListener<SanPham>) c -> {
	            int selectedCount = table.getSelectionModel().getSelectedItems().size();
	            daChonText.setText("Đã chọn: " + selectedCount);
	        });

	        // Button click event for deleting selected rows
	        xoaSanPhambutton.setOnAction(event -> {
	            ObservableList<SanPham> selectedItems = table.getSelectionModel().getSelectedItems();
	            if (!selectedItems.isEmpty()) {
	                try {
	                	String deleteQuery = "DELETE FROM SANPHAM WHERE MA_SP = ?";
	                    PreparedStatement deleteStatement = database.connection.prepareStatement(deleteQuery);

	                    // Delete each selected row
	                    for (SanPham selectedItem : selectedItems) {
	                    	String id = selectedItem.getMaSP();
	                        System.out.println(id);// Assuming you have an ID property in the SanPham class
	                        deleteStatement.setString(1, id);
	                        deleteStatement.executeUpdate();
	                    }
	                    // After deleting, remove the selected rows from the ObservableList
	                    listSP.removeAll(selectedItems);
	                    table.getSelectionModel().clearSelection();
	                } catch (SQLException ex) {
	                    System.out.println("Delete failed: " + ex.getMessage());
	                }
	            }
	        });
	        
	        int countSanPham = 0;
	        try {
				String query = "SELECT * FROM SANPHAM";
				Statement statement = database.connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);
				while (resultSet.next()) {
					countSanPham++;
				}
				tongSoText.setText("Tổng số: " + countSanPham);
				
			} catch (SQLException e) {
		    System.out.println("Query failed: " + e.getMessage());
			}
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
//------------------------------------------------LOẠI SẢN PHẨM----------------------------------------------------

	        listLoaiSP = FXCollections.observableArrayList();
			updateLoaiSPList();
			sttLoaiSPColumn.setCellValueFactory(new PropertyValueFactory<LoaiSanPham,String>("stt"));
			maLoaiSPColumn.setCellValueFactory(new PropertyValueFactory<LoaiSanPham,String>("maLoaiSP"));
			tenLoaiSPColumn.setCellValueFactory(new PropertyValueFactory<LoaiSanPham,String>("tenLoaiSP"));
			moTaLoaiSPColumn.setCellValueFactory(new PropertyValueFactory<LoaiSanPham,String>("moTaLoaiSP"));
			soLuongMatHangLoaiSPColumn.setCellValueFactory(new PropertyValueFactory<LoaiSanPham,Double>("soLuongMatHang"));
			tinhTrangLoaiSPColumn.setCellValueFactory(new PropertyValueFactory<LoaiSanPham,String>("tinhTrangLoaiSP"));
			ghiChuLoaiSPColumn.setCellValueFactory(new PropertyValueFactory<LoaiSanPham,String>("ghiChuLoaiSP"));
			tableLoaiSP.setItems(listLoaiSP);
			
			

	}
	
	public static String getMaSPDaChon() {
		return maSPDaChon;
	}
	
	public void updateList() {
		try {
			String query = "SELECT * FROM SANPHAM ORDER BY MA_SP ASC";
			Statement statement = database.connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				SanPham newSP = new SanPham();
				newSP.setMaSP(resultSet.getString("MA_SP"));
				newSP.setTenSP(resultSet.getString("TEN_SP"));
				newSP.setSoLuong(resultSet.getInt("SO_LUONG"));
				newSP.setDanhMuc(resultSet.getString("MA_LSP"));
				newSP.setTinhTrang(resultSet.getString("tinhTrang"));
				newSP.setDonViTinh(resultSet.getString("Dvt"));
				newSP.setGhiChu(resultSet.getString("Mo_ta"));
			    listSP.add(newSP);
			}
		} catch (SQLException e) {
	    System.out.println("Query failed: " + e.getMessage());
		}
	}
	
	public void updateList(String query) {
		try {
			Statement statement = database.connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				SanPham newSP = new SanPham();
				newSP.setMaSP(resultSet.getString("MA_SP"));
				newSP.setTenSP(resultSet.getString("TEN_SP"));
				newSP.setSoLuong(resultSet.getInt("SO_LUONG"));
				newSP.setDanhMuc(resultSet.getString("MA_LSP"));
				newSP.setTinhTrang(resultSet.getString("tinhTrang"));
				newSP.setDonViTinh(resultSet.getString("Dvt"));
				newSP.setGhiChu(resultSet.getString("Mo_ta"));
			    listSP.add(newSP);
			}
		} catch (SQLException e) {
	    System.out.println("Query failed: " + e.getMessage());
		}
	}
	
	
	public void updateLoaiSPList() {
		try {
			String query = "SELECT\r\n"
					+ "		    lsp.ma_lsp,\r\n"
					+ "		    lsp.ten_lsp,\r\n"
					+ "		    lsp.mo_ta,\r\n"
					+ "		    COALESCE(SUM(sp.so_luong), 0) AS slmh,\r\n"
					+ "		    CASE WHEN COALESCE(SUM(sp.so_luong), 0) = 0 THEN 'Het hang' ELSE 'Con hang' END AS TinhTrang,\r\n"
					+ "		    lsp.ghi_chu\r\n"
					+ "		FROM\r\n"
					+ "		    loaisanpham lsp\r\n"
					+ "		LEFT JOIN\r\n"
					+ "		    sanpham sp ON lsp.ma_lsp = sp.ma_lsp\r\n"
					+ "		GROUP BY\r\n"
					+ "		    lsp.ma_lsp,\r\n"
					+ "		    lsp.ten_lsp,\r\n"
					+ "		    lsp.mo_ta,\r\n"
					+ "		    lsp.ghi_chu"
					+ "     ORDER BY MA_LSP ASC";

			Statement statement = database.connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			int stt = 1;
			while (resultSet.next()) {
				LoaiSanPham newLoaiSP = new LoaiSanPham();
				newLoaiSP.setStt(stt);
				newLoaiSP.setMaLoaiSP(resultSet.getString("MA_LSP"));
				newLoaiSP.setTenLoaiSP(resultSet.getString("TEN_LSP"));
				newLoaiSP.setSoLuongMatHang(resultSet.getInt("SLMH"));
				newLoaiSP.setMoTaLoaiSP(resultSet.getString("MO_TA"));
				newLoaiSP.setTinhTrangLoaiSP(resultSet.getString("TINHTRANG"));
				newLoaiSP.setGhiChuLoaiSP(resultSet.getString("GHI_CHU"));
			    listLoaiSP.add(newLoaiSP);
			    stt++;
			}
		} catch (SQLException e) {
	    System.out.println("Query failed: " + e.getMessage());
		}
	}
	
//	private String maLoaiSP;
//	private String tenLoaiSP;
//	private String moTaLoaiSP;
//	private double soLuongMatHang;
//	private String tinhTrangLoaiSP;
//	private String ghiChuLoaiSP;
}
