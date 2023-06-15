package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dao.CTHDDAO;
import dao.KhachHangDAO;
import dao.NhanVienDAO;
import dao.SanPhamDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.CTHD;
import model.HoaDon;
import model.KhachHang;
import model.NhanVien;

public class ThongTinHoaDonController implements Initializable{

	@FXML
	private MenuButton khachHangMenu,nhanVienMenu;
	@FXML
	private TextField tenKhachHangTextField,sdtTextField,diaChiTextField,tenNhanVienTextField;
	@FXML
	private Text soHoaDonTuDongTao,ngayTaoText;
	@FXML
	private CheckBox daThanhToanCheckBox,chuaThanhToanCheckBox,daHuyCheckBox;
	@FXML
	private TableView<CTHD> cthdTable;
	@FXML
	private TableColumn<CTHD, String> maSanPhamColumn;
	@FXML
	private TableColumn<CTHD, String> tenSanPhamColumn;
	@FXML
	private TableColumn<CTHD, Integer> soLuongColumn;
	@FXML
	private TableColumn<CTHD, String> donViTinhColumn;
	@FXML
	private TableColumn<CTHD, Integer> donGiaColumn;
	@FXML
	private TableColumn<CTHD, Integer> khuyenMaiColumn;
	@FXML
	private TableColumn<CTHD, Double> thanhTienColumn;
	@FXML
	private TextField tongSoLuongMatHangTextFiled,TongTienHangTextField,tongKhuyenMaiTextField,tongTienThanhToanTextField;
	@FXML
	private Button quayLaiButton;
	private KhachHangDAO khachHangDAO;
	private NhanVienDAO nhanvienDAO;
	private SanPhamDAO sanphamDAO;
	private CTHDDAO cthdDAO;
	
	@FXML
	private ObservableList<CTHD> invoiceDetails;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		invoiceDetails = FXCollections.observableArrayList();
		
	}

	//Hàm Hiển thị thông tin Hoá Đơn
	public void setHoaDon(HoaDon hoaDon) {
		nhanvienDAO = new NhanVienDAO();
		khachHangDAO = new KhachHangDAO();
        nhanVienMenu.setText(hoaDon.getMaNv());
        NhanVien nv = nhanvienDAO.selectedById(hoaDon.getMaNv());
//        //Lấy Tên Nhân Viên
        tenNhanVienTextField.setText(nv.getHoTen());
       loadThongTinKhachHang(hoaDon);
       loadThongTinChungHoaDon(hoaDon);
       khoitaoCTHDTable(hoaDon.getSoHd());
    }
	public void loadThongTinChungHoaDon(HoaDon hoaDon) {
		
        // Hiển thị thông tin chi tiết của hoá đơn
        soHoaDonTuDongTao.setText(hoaDon.getSoHd());
        ngayTaoText.setText(hoaDon.getNgayLap().toString());
        double TongTienHang = hoaDon.getKhuyenMai()+hoaDon.getTongTien();
		TongTienHangTextField.setText(String.valueOf(TongTienHang));
		tongKhuyenMaiTextField.setText(String.valueOf(hoaDon.getKhuyenMai()));
		tongTienThanhToanTextField.setText(String.valueOf(hoaDon.getTongTien()));
	}
	public void loadThongTinKhachHang(HoaDon hoaDon) {
		 khachHangMenu.setText(hoaDon.getMaKh());
		 KhachHang kh = khachHangDAO.selectedById(hoaDon.getMaKh());
	        tenKhachHangTextField.setText(kh.getTenKh());
			diaChiTextField.setText(kh.getDiaChi());
			sdtTextField.setText(kh.getSdt());
			tongSoLuongMatHangTextFiled.setText(String.valueOf(hoaDon.getSlmh()));
	}
	private void khoitaoCTHDTable(String soHD){
		maSanPhamColumn.setCellValueFactory(new PropertyValueFactory<>("maSp"));
		tenSanPhamColumn.setCellValueFactory(cellData -> {
		    String maSanPham = cellData.getValue().getMaSp();
		    String tenSanPham = SanPhamDAO.getInstance().selectedById(maSanPham).getTenSP();
		    return new SimpleStringProperty(tenSanPham);
		});
        soLuongColumn.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        donViTinhColumn.setCellValueFactory(cellData -> {
		    String maSanPham = cellData.getValue().getMaSp();
		    String donViTinh = SanPhamDAO.getInstance().selectedById(maSanPham).getDonViTinh();
		    return new SimpleStringProperty(donViTinh);
		});
        donGiaColumn.setCellValueFactory(new PropertyValueFactory<>("Gia"));
        khuyenMaiColumn.setCellValueFactory(new PropertyValueFactory<>("khuyenMai"));
        thanhTienColumn.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));
        cthdDAO = new CTHDDAO();
        invoiceDetails = FXCollections.observableArrayList(cthdDAO.selectBySoHd(soHD));
        cthdTable.setItems(invoiceDetails);
	}
	@FXML
	private void handleQuayLai() {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/QLHoaDon.fxml"));
	        Parent root = loader.load();
	        Stage stage = (Stage) quayLaiButton.getScene().getWindow();
	        stage.setScene(new Scene(root));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	
}
