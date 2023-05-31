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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

public class QLSanPhamController implements Initializable {

//------------------------------------------SẢN PHẨM--------------------------------------

	@FXML
	private TableView<SanPham> table;

	@FXML
	private Tab quanLySPTab;
	@FXML
	private TableColumn<SanPham, String> maSPColumn;
	@FXML
	private TableColumn<SanPham, String> tenSPColumn;

	@FXML
	private TableColumn<SanPham, String> danhmucSPColumn;

	@FXML
	private TableColumn<SanPham, Double> soLuongColumn;

	@FXML
	private TableColumn<SanPham, Double> donViTinhColumn;

	@FXML
	private TableColumn<SanPham, String> ghiChuSPColumn;

	@FXML
	private TableColumn<SanPham, String> tinhTrangSPColumn;

	@FXML
	private TableColumn<SanPham, String> chooseColumn;

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
	private Button xoaSanPhamButton;

	@FXML
	AnchorPane root;

	private static String maSPDaChon;

	public void exitFocused(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			root.requestFocus();
		}
	}



//-----------------------------------------LOẠI SẢN PHẨM----------------------------------------------

	@FXML
	private TableView<LoaiSanPham> tableLoaiSP;

	@FXML
	private TableColumn<LoaiSanPham, String> maLoaiSPColumn;

	@FXML
	private TableColumn<LoaiSanPham, String> sttLoaiSPColumn;

	@FXML
	private TableColumn<LoaiSanPham, String> tenLoaiSPColumn;

	@FXML
	private TableColumn<LoaiSanPham, String> moTaLoaiSPColumn;

	@FXML
	private TableColumn<LoaiSanPham, Double> soLuongMatHangLoaiSPColumn;

	@FXML
	private TableColumn<LoaiSanPham, String> tinhTrangLoaiSPColumn;

	@FXML
	private TableColumn<LoaiSanPham, String> ghiChuLoaiSPColumn;

	private ObservableList<LoaiSanPham> listLoaiSP;

	@FXML
	private ComboBox<String> chonTinhTrangLSPComboBox;

	@FXML
	private TextField soLuongToiThieuTextField;

	@FXML
	private TextField soLuongToiDaTextField;

	@FXML
	Button apDungLoaiSPButton;
	
	@FXML
	Button timKiemLoaiSPButton;
	
	@FXML
	TextField timKiemLoaiSPTextField;
	
	@FXML
	Text daChonLSPText;
	
	@FXML
	Text tongSoLSPText;
	
	
	@FXML
	Text themMaLoaiSPText;
	
	@FXML
	TextField themTenLoaiSPTextField;
	
	@FXML
	TextField themMoTaLoaiSPTextField;
	
	@FXML
	TextField themGhiChuTextField;
	
	@FXML
	Button luuThemLoaiSPButton;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listSP = FXCollections.observableArrayList();
		updateList();
		maSPColumn.setCellValueFactory(new PropertyValueFactory<SanPham, String>("maSP"));
		tenSPColumn.setCellValueFactory(new PropertyValueFactory<SanPham, String>("tenSP"));
		danhmucSPColumn.setCellValueFactory(new PropertyValueFactory<SanPham, String>("danhMuc"));
		soLuongColumn.setCellValueFactory(new PropertyValueFactory<SanPham, Double>("soLuong"));
		tinhTrangSPColumn.setCellValueFactory(new PropertyValueFactory<SanPham, String>("tinhTrang"));
		donViTinhColumn.setCellValueFactory(new PropertyValueFactory<SanPham, Double>("donViTinh"));
		ghiChuSPColumn.setCellValueFactory(new PropertyValueFactory<SanPham, String>("ghiChu"));
		table.setItems(listSP);
		xemChiTietButton.setDisable(true);
		xoaSanPhamButton.setDisable(true);
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		table.getSelectionModel().getSelectedItems().addListener((ListChangeListener<SanPham>) change -> {
		    int selectedRowCount = change.getList().size();

		    if (selectedRowCount == 0) {
		        // No rows selected, disable both buttons
		        xemChiTietButton.setDisable(true);
		        xoaSanPhamButton.setDisable(true);
		    } else {
		        // Rows selected, enable the xoaSanPhamButton
		        xoaSanPhamButton.setDisable(false);

		        if (selectedRowCount >= 2) {
		            // More than 2 rows selected, disable xemChiTietButton
		            xemChiTietButton.setDisable(true);
		        } else {
		            // 2 or fewer rows selected, enable xemChiTietButton
		            xemChiTietButton.setDisable(false);
		        }
		    }
		});

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
				// window.setMaximized(true);
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
		        // Get the selected product from the table
		        SanPham selectedSanPham = table.getSelectionModel().getSelectedItem();
		        if (selectedSanPham != null) {
		            String selectedMaSP = selectedSanPham.getMaSP();
		            System.out.println(selectedMaSP);// Assuming you have a method to retrieve the ID of the product
		            ThongTinSanPhamController.maSP = selectedMaSP;
		            
		            FXMLLoader loader = new FXMLLoader(common.class.getResource("/view/ThongTinSanPham.fxml"));
		            AnchorPane root = loader.load();

		            // Access the controller and set the selected product's ID
		            System.out.print(ThongTinSanPhamController.maSP);

		            // Create a Scene object with the root node and set it to the primary stage
		            Scene scene = new Scene(root);
		            Stage window = new Stage();
		            window.setScene(scene);
		            // window.setMaximized(true);
		            window.setResizable(false);
		            window.show();
		            table.getSelectionModel().clearSelection();
		            
		            
		        }
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
					query += " AND ma_LSP = ";
					query = query + "'" + selectedMaLSP + "'";
				}

				if (selectedTinhtrang != null) {
					query += " AND TinhTrang = ";
					query = query + "'" + selectedTinhtrang + "'";
				}

				if (selectedDonViTinh != null) {
					query += " AND Dvt = ";
					query = query + "'" + selectedDonViTinh + "'";
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
			xoaSanPhamButton.setText("Xóa đã chọn: " + selectedCount);
		});
		
		listSP.addListener((ListChangeListener<SanPham>) c -> {
		    int rowCount = listSP.size();
		    tongSoText.setText("Tổng số: " + rowCount);
		});

		// Update the initial count
		tongSoText.setText("Tổng số: " + listSP.size());

		// Button click event for deleting selected rows
		xoaSanPhamButton.setOnAction(event -> {
            ObservableList<SanPham> selectedItems = table.getSelectionModel().getSelectedItems();
            if (!selectedItems.isEmpty()) {
                // Show confirmation dialog before deleting
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Xác nhận xóa");
                alert.setHeaderText("Xác nhận xóa");
                alert.setContentText("Bạn có chắc chắn muốn xóa các dòng đã chọn?");

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType == ButtonType.OK) {
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
            }
        });



//------------------------------------------------LOẠI SẢN PHẨM----------------------------------------------------

		listLoaiSP = FXCollections.observableArrayList();
		updateLoaiSPList();
		sttLoaiSPColumn.setCellValueFactory(new PropertyValueFactory<LoaiSanPham, String>("stt"));
		maLoaiSPColumn.setCellValueFactory(new PropertyValueFactory<LoaiSanPham, String>("maLoaiSP"));
		tenLoaiSPColumn.setCellValueFactory(new PropertyValueFactory<LoaiSanPham, String>("tenLoaiSP"));
		moTaLoaiSPColumn.setCellValueFactory(new PropertyValueFactory<LoaiSanPham, String>("moTaLoaiSP"));
		soLuongMatHangLoaiSPColumn.setCellValueFactory(new PropertyValueFactory<LoaiSanPham, Double>("soLuongMatHang"));
		tinhTrangLoaiSPColumn.setCellValueFactory(new PropertyValueFactory<LoaiSanPham, String>("tinhTrangLoaiSP"));
		ghiChuLoaiSPColumn.setCellValueFactory(new PropertyValueFactory<LoaiSanPham, String>("ghiChuLoaiSP"));
		tableLoaiSP.setItems(listLoaiSP);
		
		tableLoaiSP.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		daChonLSPText.setText("Đã chọn: 0");
		tableLoaiSP.getSelectionModel().getSelectedItems().addListener((ListChangeListener<LoaiSanPham>) c -> {
			int selectedCount = tableLoaiSP.getSelectionModel().getSelectedItems().size();
			daChonLSPText.setText("Đã chọn: " + selectedCount);
		});
		
		listLoaiSP.addListener((ListChangeListener<LoaiSanPham>) c -> {
		    int rowCount = listLoaiSP.size();
		    tongSoLSPText.setText("Tổng số: " + rowCount);
		});

		// Update the initial count
		tongSoLSPText.setText("Tổng số: " + listLoaiSP.size());
		

		timKiemLoaiSPButton.setOnMouseClicked((MouseEvent event) -> {
			listLoaiSP.clear();
			String timKiem = timKiemLoaiSPTextField.getText();
			if (timKiem != null && !timKiem.isEmpty()) {
				try {
					String query = "SELECT\r\n" 
							+ "    lsp.ma_lsp,\r\n" 
							+ "    lsp.ten_lsp,\r\n" 
							+ "    lsp.mo_ta,\r\n"
							+ "    COALESCE(SUM(sp.so_luong), 0) AS slmh,\r\n"
							+ "    CASE WHEN COALESCE(SUM(sp.so_luong), 0) = 0 THEN 'Het hang' ELSE 'Con hang' END AS TinhTrang,\r\n"
							+ "    lsp.ghi_chu\r\n" 
							+ "FROM\r\n" 
							+ "    loaisanpham lsp\r\n" 
							+ "LEFT JOIN\r\n"
							+ "    sanpham sp ON lsp.ma_lsp = sp.ma_lsp\r\n" 
							+ "GROUP BY\r\n" 
							+ "    lsp.ma_lsp,\r\n"
							+ "    lsp.ten_lsp,\r\n" 
							+ "    lsp.mo_ta,\r\n" 
							+ "    lsp.ghi_chu\r\n" 
							+ "HAVING\r\n" 
							+ "    TEN_LSP = ? ";
					PreparedStatement statement = database.connection.prepareStatement(query);
					statement.setString(1, timKiem);

					ResultSet resultSet = statement.executeQuery();
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

					// Process the ResultSet as needed

					statement.close();
				} catch (SQLException e) {
					System.out.println("Query failed: " + e.getMessage());
				}
			}
		});

		ObservableList<String> dataTinhTrangLSP = FXCollections.observableArrayList();

		dataTinhTrangLSP.add("Con hang");
		dataTinhTrangLSP.add("Het hang");

		// Set the ObservableList as the data source for the ComboBox
		chonTinhTrangLSPComboBox.setItems(dataTinhTrangLSP);

		apDungLoaiSPButton.setOnAction(event -> {
			String selectedTinhtrang = chonTinhTrangLSPComboBox.getValue();

			String query = "SELECT\r\n" 
					+ "    lsp.ma_lsp,\r\n" 
					+ "    lsp.ten_lsp,\r\n" 
					+ "    lsp.mo_ta,\r\n"
					+ "    COALESCE(SUM(sp.so_luong), 0) AS slmh,\r\n"
					+ "    CASE WHEN COALESCE(SUM(sp.so_luong), 0) = 0 THEN 'Het hang' ELSE 'Con hang' END AS TinhTrang,\r\n"
					+ "    lsp.ghi_chu\r\n" 
					+ "FROM\r\n" 
					+ "    loaisanpham lsp\r\n" 
					+ "LEFT JOIN\r\n"
					+ "    sanpham sp ON lsp.ma_lsp = sp.ma_lsp\r\n" 
					+ "GROUP BY\r\n" 
					+ "    lsp.ma_lsp,\r\n"
					+ "    lsp.ten_lsp,\r\n" 
					+ "    lsp.mo_ta,\r\n" 
					+ "    lsp.ghi_chu\r\n" 
					+ "HAVING\r\n" 
					+ "    1=1 ";
			if (soLuongToiThieuTextField.getText().equals("") && soLuongToiDaTextField.getText().equals("")) {
				if (selectedTinhtrang == null) {
					updateLoaiSPList();
				} else {
					query += "AND CASE WHEN COALESCE(SUM(sp.so_luong), 0) = 0 THEN 'Het hang' ELSE 'Con hang' END ";
					query = query + "= '" + selectedTinhtrang + "'";
				}
				listLoaiSP.clear();
				System.out.println(query);
				updateLoaiSPList(query);

			} else {
				if (soLuongToiThieuTextField.getText().equals("") || soLuongToiDaTextField.getText().equals("")) {
					System.out.println("Error");
				} else {
					if (selectedTinhtrang == null) {
						query = query + " AND COALESCE(SUM(sp.so_luong), 0) BETWEEN "
								+ soLuongToiThieuTextField.getText() + " AND " + soLuongToiDaTextField.getText();
					} else {
						query = query + " AND COALESCE(SUM(sp.so_luong), 0) BETWEEN "
								+ soLuongToiThieuTextField.getText() + " AND " + soLuongToiDaTextField.getText();
						query += "AND CASE WHEN COALESCE(SUM(sp.so_luong), 0) = 0 THEN 'Het hang' ELSE 'Con hang' END ";
						query = query + "= '" + selectedTinhtrang + "'";
					}
					listLoaiSP.clear();
					System.out.println(query);
					updateLoaiSPList(query);
				}
			}

		});
		
		setLoaiSPTextFieldStatus(false);
		String largestMaLSP = getLargestMaLSPFromDatabase();
		String nextMaLSP = generateNextMaLSP(largestMaLSP);
		themMaLoaiSPText.setText(nextMaLSP);

		tableLoaiSP.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			int selectedRowCount = tableLoaiSP.getSelectionModel().getSelectedItems().size();
		    if (newValue != null && selectedRowCount == 1) {
		        setLoaiSPTextFieldStatus(false);
		        String selectedMaLoaiSP = newValue.getMaLoaiSP();
		        themMaLoaiSPText.setText(selectedMaLoaiSP);
		    } else if (newValue == null && selectedRowCount == 0){
		        setLoaiSPTextFieldStatus(false);
		        themMaLoaiSPText.setText(nextMaLSP);
		    }
		    else {
		    	setLoaiSPTextFieldStatus(true);
		    }
		});

		luuThemLoaiSPButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	String largestMaLSP = getLargestMaLSPFromDatabase();
    			String nextMaLSP = generateNextMaLSP(largestMaLSP);
				if(themMaLoaiSPText.getText().equals(nextMaLSP)) {
					System.out.println("1");
					LoaiSanPham newLoaiSP = new LoaiSanPham();
		        	newLoaiSP.setMaLoaiSP(themMaLoaiSPText.getText());
		        	newLoaiSP.setTenLoaiSP(themTenLoaiSPTextField.getText());
		        	newLoaiSP.setMoTaLoaiSP(themMoTaLoaiSPTextField.getText());
		        	newLoaiSP.setGhiChuLoaiSP(themGhiChuTextField.getText());
		    	    
		    	    // Add the new SanPham to the database
		    	    try {
		    	        String query = "INSERT INTO LOAISANPHAM (ma_LSP, ten_LSP, Mo_ta, slmh, ghi_chu) " +
		    	                       "VALUES (?, ?, ?, ?, ?)";
		    	        PreparedStatement statement10 = database.connection.prepareStatement(query);
		    	        statement10.setString(1, newLoaiSP.getMaLoaiSP());
		    	        statement10.setString(2, newLoaiSP.getTenLoaiSP());
		    	        statement10.setString(3, newLoaiSP.getMoTaLoaiSP());
		    	        statement10.setInt(4, 0);
		    	        statement10.setString(5, newLoaiSP.getGhiChuLoaiSP());
		    	        statement10.executeUpdate();
		    	        largestMaLSP = getLargestMaLSPFromDatabase();
		    			nextMaLSP = generateNextMaLSP(largestMaLSP);
		    			themMaLoaiSPText.setText(nextMaLSP);
		    	        
		    	        System.out.println("New LoaiSanPham added to database: " + newLoaiSP);
		    	    } catch (SQLException e) {
		    	        System.out.println("Error adding LoaiSanPham to database: " + e.getMessage());
		    	    }
		    	    
		    	    // Add the new SanPham to the TableView
		    	    listLoaiSP.add(newLoaiSP);
					
				}
	        	
	        }
	    });



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
			String query = "SELECT\r\n" + "		    lsp.ma_lsp,\r\n" + "		    lsp.ten_lsp,\r\n"
					+ "		    lsp.mo_ta,\r\n" + "		    COALESCE(SUM(sp.so_luong), 0) AS slmh,\r\n"
					+ "		    CASE WHEN COALESCE(SUM(sp.so_luong), 0) = 0 THEN 'Het hang' ELSE 'Con hang' END AS TinhTrang,\r\n"
					+ "		    lsp.ghi_chu\r\n" + "		FROM\r\n" + "		    loaisanpham lsp\r\n"
					+ "		LEFT JOIN\r\n" + "		    sanpham sp ON lsp.ma_lsp = sp.ma_lsp\r\n"
					+ "		GROUP BY\r\n" + "		    lsp.ma_lsp,\r\n" + "		    lsp.ten_lsp,\r\n"
					+ "		    lsp.mo_ta,\r\n" + "		    lsp.ghi_chu" + "     ORDER BY MA_LSP ASC";

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

	public void updateLoaiSPList(String query) {
		try {

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
	
	public void setLoaiSPTextFieldStatus(Boolean bool) {
		themMaLoaiSPText.setDisable(bool);
		themTenLoaiSPTextField.setDisable(bool);
		themMoTaLoaiSPTextField.setDisable(bool);
		themGhiChuTextField.setDisable(bool);
		luuThemLoaiSPButton.setDisable(bool);

	}
	
	
	private String getLargestMaLSPFromDatabase() {
        String largestMaLSP = "";

        try {
            // Establish the database connection
            Statement statement = database.connection.createStatement();

            // Execute the query to retrieve the largest ma_LSP
            String query = "SELECT MAX(ma_LSP) FROM LoaisanPham";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                largestMaLSP = resultSet.getString(1);
            }

            // Close the resources
            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        return largestMaLSP;
    }

    private String generateNextMaLSP(String largestMaLSP) {
        String nextMaLSP = "";

        if (largestMaLSP != null && !largestMaLSP.isEmpty()) {
            String numericPart = largestMaLSP.substring(3); // Extract the numeric part, assuming the format is consistent
            int nextNumber = Integer.parseInt(numericPart) + 1;
            nextMaLSP = "LSP" + String.format("%03d", nextNumber); // Format the incremented value with leading zeros
        } else {
            nextMaLSP = "LSP001"; // If no existing ma_LSP found, start from LSP001
        }

        return nextMaLSP;
    }
//	private String maLoaiSP;
//	private String tenLoaiSP;
//	private String moTaLoaiSP;
//	private double soLuongMatHang;
//	private String tinhTrangLoaiSP;
//	private String ghiChuLoaiSP;
}
