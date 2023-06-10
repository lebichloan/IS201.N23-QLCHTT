package controller;

import model.NhanVien;
import db.database;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class QLNhanVienController implements Initializable {
	@FXML
	private TableColumn<NhanVien, String> sttCol;
	
//	@FXML
//	private TableColumn<NhanVien, String> diaChiCol;

//	@FXML
//	private TableColumn<NhanVien, String> emailCol;

	@FXML
	private TableColumn<NhanVien, String> ghiChuCol;

	@FXML
	private TableColumn<NhanVien, String> tenNVCol;

//	@FXML
//	private TableColumn<NhanVien, String> maCHCol;

//	@FXML
//	private TableColumn<NhanVien, String> maNQLCol;

	@FXML
	private TableColumn<NhanVien, String> maNVCol;

	@FXML
	private TableColumn<NhanVien, String> ngaySinhCol;

	@FXML
	private TableColumn<NhanVien, String> ngayVLCol;

//	@FXML
//	private TableColumn<NhanVien, String> sdtCol;
	
	@FXML
	private TableColumn<NhanVien, String> gioiTinhCol;

	@FXML
	private TableView<NhanVien> nvTable;
	
	ObservableList<NhanVien> nvList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initTable();
		getData();
		
	}
	
	void initTable() {
		sttCol.setCellValueFactory(new PropertyValueFactory<>("stt"));
		tenNVCol.setCellValueFactory(new PropertyValueFactory<>("HoTen"));
//		sdtCol.setCellValueFactory(new PropertyValueFactory<>("SDT"));
//		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
//		diaChiCol.setCellValueFactory(new PropertyValueFactory<>("DiaChi"));
//		maCHCol.setCellValueFactory(new PropertyValueFactory<>("MaCH"));
//		maNQLCol.setCellValueFactory(new PropertyValueFactory<>("MaQL"));
		maNVCol.setCellValueFactory(new PropertyValueFactory<>("MaNV"));
		ngaySinhCol.setCellValueFactory(new PropertyValueFactory<>("NgaySinh"));
		gioiTinhCol.setCellValueFactory(new PropertyValueFactory<>("GioiTinh"));
		ngayVLCol.setCellValueFactory(new PropertyValueFactory<>("NgayVaoLam"));
		ghiChuCol.setCellValueFactory(new PropertyValueFactory<>("GhiChu"));
		nvTable.setItems(nvList);
	}
	void getData() {
		nvList.clear();
		database.connect();
		Connection conn = database.connection;
		String sql = "select * from NHANVIEN";
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			int stt = 1;
			while(resultSet.next()) {
				String MaNV = resultSet.getString("MaNV");
				String Hoten = resultSet.getString("HoTen");
				LocalDate NgaySinh = resultSet.getDate("NgaySinh").toLocalDate();
				String GioiTinh = resultSet.getString("GioiTinh");
				String SDT = resultSet.getString("SDT");
				String email = resultSet.getString("email");
				String DiaChi = resultSet.getString("DiaChi");
				LocalDate NgayVaoLam = resultSet.getDate("NgayVaoLam").toLocalDate(); 
				String GhiChu = resultSet.getString("GhiChu");
				String MaCH = resultSet.getString("MaCH");
				String MaQL = resultSet.getString("MaQL");
				nvList.add(new NhanVien((stt++), MaNV, Hoten, NgaySinh, GioiTinh, SDT, email, 
						DiaChi, NgayVaoLam, GhiChu, MaCH, MaQL));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			resultSet.close();
			statement.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}