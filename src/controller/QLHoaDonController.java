package controller;

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
import db.database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.HoaDon;

public class QLHoaDonController implements Initializable {
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dsHoaDon = FXCollections.observableArrayList();

		database connectNow = new database();
		Connection connnectDB = connectNow.getConnection();
		String hoaDonViewQuery = "SELECT ROW_NUMBER() OVER (ORDER BY hd.SO_HD) AS STT, hd.SO_HD, hd.NGAY_LAP, hd.SLMH, hd.khuyen_mai, hd.tong_tien, hd.tinh_trang, nv.Ma_NV, kh.Ma_KH "
				+ "FROM Hoadon hd INNER JOIN khachhang kh ON hd.ma_kh = kh.ma_kh INNER JOIN NHANVIEN nv ON nv.ma_nv = hd.ma_nv";
		
		try {
			Statement statement = connnectDB.createStatement();
			ResultSet queryOutput = statement.executeQuery(hoaDonViewQuery);

			while (queryOutput.next()) {
			    String querysohoadon = queryOutput.getString("STT");
			    Date queryNgayLap = queryOutput.getDate("NGAY_LAP"); 
			    int queryslmh = queryOutput.getInt("SLMH"); 
			    int querykhuyenmai = queryOutput.getInt("khuyen_mai"); 
			    int querythanhtien = queryOutput.getInt("tong_tien"); 
			    String querytinhtrang = queryOutput.getString("tinh_trang"); 
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
	
	
}
