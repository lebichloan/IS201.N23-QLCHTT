package controller;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.System.Logger;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;

import dao.HoaDonDAO;
import dao.KhachHangDAO;
import dao.NhanVienDAO;
import dao.SanPhamDAO;
import db.database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import model.HoaDon;
import model.KhachHang;
import model.NhanVien;
import dao.KhachHangDAO;

public class QLHoaDonController implements Initializable {
	private KhachHangDAO khachHangDAO;
	private NhanVienDAO nhanvienDAO;
	private SanPhamDAO sanphamDAO;
	@FXML
	private TableView<HoaDon> table;
	@FXML
	private TableColumn<HoaDon, String> sohoadonColumn;
	@FXML
	private TableColumn<HoaDon, String> maKhColumn;
	@FXML
	private TableColumn<HoaDon, Date> ngaylapColumn;
	@FXML
	private TableColumn<HoaDon, Integer> thanhtienColumn;
	@FXML
	private TableColumn<HoaDon, String> tinhtrangColumn;
	@FXML
	private TableColumn<HoaDon, String> nhanvienbanhangColumn;
	@FXML 
	private Button ThemMoiButton;
	@FXML 
	private Button XemChiTietButton;
	private ObservableList<HoaDon> dsHoaDon;

//thành phần cho chức năng lọc:
	@FXML
	private MenuButton khachHangMenu,TinhTrangMenu,nhanVienMenu;
	@FXML
	private Button apdungButton,huyApDungButton,timKiemButton;
	
	@FXML
	private TextField timKiemTextField;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dsHoaDon = FXCollections.observableArrayList();
		handleHienThiDanhSachHoaDon();
		handleThemMoiHoaDon();
		handleClickHoaDon();
		loadDanhSachKhachHang();
		loadDanhSachNhanVien();
		loadTinhTrang();
	}

	private void loadTinhTrang() {
	    ObservableList<String> listtinhTrang = FXCollections.observableArrayList(
	        "Đã Thanh Toán", "Chưa thanh toán", "Đã Huỷ"
	    );
	    TinhTrangMenu.getItems().clear();
	    for(String tt :listtinhTrang) {
	    	MenuItem item = new MenuItem(tt);
	    	item.setOnAction(e -> {
	    		TinhTrangMenu.setText(tt);
			});
	    	TinhTrangMenu.getItems().add(item);
	    }
	}
	

	public void loadDanhSachNhanVien() {
		nhanvienDAO = new NhanVienDAO();
		ObservableList<NhanVien> listNhanVien = FXCollections.observableArrayList(nhanvienDAO.selectAll());
		nhanVienMenu.getItems().clear();
		for (NhanVien nv : listNhanVien) {
			MenuItem item = new MenuItem(nv.getMaNv());
			item.setOnAction(e -> {
				nhanVienMenu.setText(nv.getMaNv());
			});
			nhanVienMenu.getItems().add(item);
		}
	}
	
	public void loadDanhSachKhachHang() {
		// Khởi tạo đối tượng DAO cho KhachHang và HoaDon: Kết nối csdl với khach hang
		khachHangDAO = new KhachHangDAO();

		// Load danh sách khách hàng lên menu: hỏi tạo một list khách hàng lưu trữ danh sách khách hàng trong cơ sở dữ liệu
		ObservableList<KhachHang> listKhachHang = FXCollections.observableArrayList(khachHangDAO.selectAll());
		//xoá hết các lựa chọn trước của menu
		khachHangMenu.getItems().clear();
		
		for (KhachHang kh : listKhachHang) {
			//tạo một menu Item
			MenuItem item = new MenuItem(kh.getMaKh());
			
			item.setOnAction(e -> {
				khachHangMenu.setText(kh.getMaKh());
			});
			khachHangMenu.getItems().add(item);
		}
	}

	private void handleClickHoaDon() {
	    ContextMenu contextMenu = new ContextMenu();
	    MenuItem xemChiTiet = new MenuItem("Xem chi tiết");
	    MenuItem xoa = new MenuItem("Xoá");
	    MenuItem sua = new MenuItem("Sửa thông tin");
	    contextMenu.getItems().addAll(xemChiTiet, xoa, sua);

	    table.setContextMenu(contextMenu);

	    table.setOnMouseClicked(event -> {
	        if (event.getButton() == MouseButton.SECONDARY) { // Xử lý khi nhấp chuột phải
	            HoaDon selectedHoaDon = table.getSelectionModel().getSelectedItem();
	            if (selectedHoaDon != null) {
	                xemChiTiet.setOnAction(e -> showChiTietHoaDon(selectedHoaDon));
	                xoa.setOnAction(e -> xoaHoaDon(selectedHoaDon));
	                sua.setOnAction(e -> suaThongTinHoaDon(selectedHoaDon));
	            }
	        }
	    });
	}

	private void suaThongTinHoaDon(HoaDon selectedHoaDon) {
		try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ThayDoiHoaDon.fxml"));
	        Parent root = loader.load();
	        ThayDoiHoaDonController controller = loader.getController();
	        controller.setHoaDon(selectedHoaDon);
	        Scene scene = new Scene(root);
			Stage window = new Stage();
			window.setScene(scene);
			window.show();
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	}

	private void xoaHoaDon(HoaDon selectedHoaDon) {
		dsHoaDon.remove(selectedHoaDon);
		HoaDonDAO.getInstance().delete(selectedHoaDon);
	}

	private void showChiTietHoaDon(HoaDon selectedHoaDon) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ThongTinHoaDon.fxml"));
	        Parent root = loader.load();
	        ThongTinHoaDonController controller = loader.getController();
	        controller.setHoaDon(selectedHoaDon);
	        Scene scene = new Scene(root);
			Stage window = new Stage();
			window.setScene(scene);
			window.show();
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	}


	private void handleHienThiDanhSachHoaDon() {
		database connectNow = new database();
		Connection connnectDB = connectNow.getConnection();
		String hoaDonViewQuery = "SELECT  * FROM HOADON";
		try {
			Statement statement = connnectDB.createStatement();
			ResultSet queryOutput = statement.executeQuery(hoaDonViewQuery);

			while (queryOutput.next()) {
				String querysohoadon = queryOutput.getString("SO_HD");
			    Date queryNgayLap = queryOutput.getDate("NGAY_LAP"); 
			    int queryslmh = queryOutput.getInt("SLMH"); 
			    int querykhuyenmai = queryOutput.getInt("khuyen_mai"); 
			    int querythanhtien = queryOutput.getInt("tong_tien"); 
			    String querytinhtrang = queryOutput.getString("trang_thai"); 
			    String queryNhanVien = queryOutput.getString("Ma_NV"); 
			    String queryKhachHang = queryOutput.getString("Ma_KH");
			    

				HoaDon hoaDon = new HoaDon(querysohoadon, queryNgayLap, queryslmh, querykhuyenmai, querythanhtien,
						querytinhtrang, queryNhanVien, queryKhachHang);
				dsHoaDon.add(hoaDon);
			}

			sohoadonColumn.setCellValueFactory(new PropertyValueFactory<>("soHd"));
			maKhColumn.setCellValueFactory(new PropertyValueFactory<>("maKh"));
			ngaylapColumn.setCellValueFactory(new PropertyValueFactory<>("ngayLap"));
			thanhtienColumn.setCellValueFactory(new PropertyValueFactory<>("tongTien"));
			tinhtrangColumn.setCellValueFactory(new PropertyValueFactory<>("tinhTrang"));
			nhanvienbanhangColumn.setCellValueFactory(new PropertyValueFactory<>("maNv"));

			table.setItems(dsHoaDon);

		} catch (SQLException e) {
			System.out.println("Query failed: " + e.getMessage());
			e.printStackTrace();
		}
		
	}

	private void handleThemMoiHoaDon() {
		ThemMoiButton.setOnAction(event -> {
	        try {
	        	FXMLLoader loader = new FXMLLoader(common.class.getResource("/view/ThemHoaDon.fxml"));
				AnchorPane root = loader.load();

				// Create a Scene object with the root node and set it to the primary stage
				Scene scene = new Scene(root);
				Stage window = new Stage();
				window.setScene(scene);
				window.setResizable(false);
				window.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    });
	}
	@FXML
	private void handleApDung() {
	    String maKhachHang = khachHangMenu.getText();
	    String tinhTrang = TinhTrangMenu.getText();
	    String maNhanVien = nhanVienMenu.getText();

	    // Lọc danh sách các hoá đơn
	    ObservableList<HoaDon> filteredList = dsHoaDon.filtered(hoaDon ->
	        hoaDon.getMaKh().equals(maKhachHang) &&
	        hoaDon.getTinhTrang().equals(tinhTrang) &&
	        hoaDon.getMaNv().equals(maNhanVien)
	    );

	    // Hiển thị danh sách các hoá đơn đã lọc trên TableView
	    table.setItems(filteredList);
	}
	@FXML
	private void handleHuyApDung() {
	    // Hiển thị danh sách ban đầu trên TableView
	    table.setItems(dsHoaDon);
	 // Xóa các lựa chọn trong các MenuButton
	    khachHangMenu.setText("Lọc Khách Hàng");
	    TinhTrangMenu.setText("Chọn Tình Trạng");
	    nhanVienMenu.setText("Chọn Nhân Viên");
	}
	@FXML
	private void handleTimKiem() {
	    String maHoaDon = timKiemTextField.getText();

	    // Tìm kiếm hoá đơn theo mã hoá đơn
	    ObservableList<HoaDon> filteredList = dsHoaDon.filtered(hoaDon ->
	        hoaDon.getSoHd().equals(maHoaDon)
	    );

	    // Hiển thị kết quả tìm kiếm trên TableView
	    table.setItems(filteredList);
	}

}
