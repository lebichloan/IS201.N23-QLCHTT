package controller;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import dao.CTHDDAO;
import dao.HoaDonDAO;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.CTHD;
import model.HoaDon;
import model.KhachHang;
import model.NhanVien;
import model.SanPham;

public class ThayDoiHoaDonController implements Initializable{

	@FXML
	private MenuButton khachHangMenu;
	@FXML
	private TextField tenKhachHangTextField,sdtTextField,diaChiTextField;
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
	
	//Thông Tin sản phẩm trong cthd:
	@FXML 
	private MenuButton sanPhamMenu;
	@FXML 
	private TextField tenSanPhamTextField,kichThuocTextField,mauSacTextField,khuyenMaiTextField,soLuongTextField;
	@FXML 
	private TextField conLaiTextField,donGiaTextField,donViTinhTextField,thanhTienTextField;
	@FXML
	private Button luuCTHDButton,luuHoaDonButton;
	
	public String maNhanVien;
	
	@FXML
	private ObservableList<CTHD> invoiceDetails;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		invoiceDetails = FXCollections.observableArrayList();
		handleLayThongTinCTHD();
		capNhatThanhTien();
		handleChonTinhTrangDonHang();
	}

	private void handleLayThongTinCTHD() {
		cthdTable.setOnMouseClicked(event -> {
		    if (event.getClickCount() == 2) { // Kiểm tra sự kiện nhấp đúp chuột
		        CTHD selectedCTHD = cthdTable.getSelectionModel().getSelectedItem(); // Lấy CTHD được chọn

		        SanPham sp = sanphamDAO.getInstance().selectedById(selectedCTHD.getMaSp());
		        // Đổ dữ liệu từ CTHD đã chọn vào các trường tương ứng
		        sanPhamMenu.setText(sp.getMaSP());
		        tenSanPhamTextField.setText(sp.getTenSP());
		        kichThuocTextField.setText(sp.getKichThuoc());
		        mauSacTextField.setText(sp.getMauSac());
		        khuyenMaiTextField.setText(String.valueOf(selectedCTHD.getKhuyenMai()));
		        soLuongTextField.setText(String.valueOf(selectedCTHD.getSoLuong()));
		        conLaiTextField.setText(String.valueOf(sp.getSoLuong()));
		        donGiaTextField.setText(String.valueOf(sp.getGia()));
		        donViTinhTextField.setText(sp.getDonViTinh());
		        thanhTienTextField.setText(String.valueOf(selectedCTHD.getThanhTien()));
		    }
		});

		
	}

	//Hàm Hiển thị thông tin Hoá Đơn
	public void setHoaDon(HoaDon hoaDon) {
		khachHangDAO = new KhachHangDAO();
       loadThongTinKhachHang(hoaDon);
       loadThongTinChungHoaDon(hoaDon);
       khoitaoCTHDTable(hoaDon.getSoHd());
       updateCheckBoxes(hoaDon);
       maNhanVien = hoaDon.getMaNv();
    }
	public void loadThongTinChungHoaDon(HoaDon hoaDon) {
		
        // Hiển thị thông tin chi tiết của hoá đơn
        soHoaDonTuDongTao.setText(hoaDon.getSoHd());
        ngayTaoText.setText(hoaDon.getNgayLap().toString());
        double TongTienHang =  hoaDon.getTongTien() / ((100 - hoaDon.getKhuyenMai())/100);
		TongTienHangTextField.setText(String.valueOf(TongTienHang));
		tongKhuyenMaiTextField.setText(String.valueOf(hoaDon.getKhuyenMai()));
		tongTienThanhToanTextField.setText(String.valueOf(hoaDon.getTongTien()));
	}
	private void tinhLaiThongTinHoaDon() {
	    double tongTienHang = 0;
	    double tongKhuyenMai = 0;
	    double tongTienThanhToan = 0;

	    for (CTHD cthd : invoiceDetails) {
	        tongTienHang += cthd.getGia() * cthd.getSoLuong();
	        tongKhuyenMai += cthd.getGia() * cthd.getKhuyenMai() * cthd.getSoLuong() / 100;
	        tongTienThanhToan += cthd.getGia() * cthd.getSoLuong() - cthd.getGia() * cthd.getKhuyenMai() * cthd.getSoLuong() / 100;
	    }

	    tongSoLuongMatHangTextFiled.setText(Integer.toString(invoiceDetails.size()));
	    DecimalFormat decimalFormat = new DecimalFormat("#.##");
	    TongTienHangTextField.setText(decimalFormat.format(tongTienHang));
	    tongKhuyenMaiTextField.setText(decimalFormat.format(tongKhuyenMai));
	    tongTienThanhToanTextField.setText(decimalFormat.format(tongTienThanhToan));
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
	    Stage stage = (Stage) quayLaiButton.getScene().getWindow();
	    System.out.println("1");
	    stage.close();
	   
	}
	
	public void capNhatThanhTien() {
	    // Create a listener for the quantity text field
	    soLuongTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	        
	        // Calculate the raw total value first
	        if (newValue.matches("\\d+")) {
	            int quantity = Integer.parseInt(newValue);
	            double price = Double.parseDouble(donGiaTextField.getText());
	            int remaining = Integer.parseInt(conLaiTextField.getText());
	            if (quantity > remaining) {
	                Alert alert = new Alert(AlertType.ERROR, "Số lượng không được vượt quá " + remaining);
	                alert.showAndWait();
	                soLuongTextField.setText(oldValue);
	            } else {
	                double rawTotal = quantity * price;

	                // Calculate the discount percentage based on the value entered in the "khuyenMaiTextField"
	                double discountPercentage = 0;
	                if (!khuyenMaiTextField.getText().isEmpty()) {
	                    try {
	                        discountPercentage = Double.parseDouble(khuyenMaiTextField.getText());
	                    } catch (NumberFormatException e) {
	                        // Show an error message if the discount value cannot be parsed to a number
	                        Alert alert = new Alert(AlertType.ERROR, "Giá trị khuyến mãi không hợp lệ");
	                        alert.showAndWait();
	                        return;
	                    }
	                }
	                // Subtract the discount from the raw total to get the final total
	                double discountAmount = rawTotal * discountPercentage / 100;
	                double finalTotal = rawTotal - discountAmount;

	                thanhTienTextField.setText(String.format("%.2f", finalTotal));
	            }
	        } else {
	            thanhTienTextField.clear();
	        }
	    });
	}
	
	@FXML
	private void handleLuuCTHDButton() {
	    // Kiểm tra xem một CTHD đã được chọn trong bảng hay chưa
	    CTHD selectedCTHD = cthdTable.getSelectionModel().getSelectedItem();
	    if (selectedCTHD == null) {
	        // Nếu không có CTHD nào được chọn, hiển thị thông báo cho người dùng
	        Alert alert = new Alert(Alert.AlertType.WARNING, "Vui lòng chọn một CTHD trong bảng");
	        alert.showAndWait();
	        return;
	    }

	    // Lấy thông tin từ các trường nhập liệu
	    String maSanPham = sanPhamMenu.getText();
	    int soLuong = Integer.parseInt(soLuongTextField.getText());
	    double donGia = Double.parseDouble(donGiaTextField.getText());
	    int khuyenMai = Integer.parseInt(khuyenMaiTextField.getText());
	    double thanhTien = Double.parseDouble(thanhTienTextField.getText());
	    // Lấy các thông tin khác từ các trường nhập liệu khác


	    // Cập nhật thông tin cho chi tiết hoá đơn
	    selectedCTHD.setMaSp(maSanPham);
	    selectedCTHD.setSoLuong(soLuong);
	    selectedCTHD.setGia(donGia);
	    selectedCTHD.setKhuyenMai(khuyenMai);
	    selectedCTHD.setThanhTien(thanhTien);
	   
	    // Cập nhật CTHD trong CSDL (hoặc danh sách CTHD)
	    cthdDAO.update(selectedCTHD);
	    tinhLaiThongTinHoaDon();
	    // Hiển thị thông tin đã cập nhật lên giao diện
	    cthdTable.refresh();; // Hàm này cập nhật lại dữ liệu trong bảng CTHD

	    // Hiển thị thông báo cho người dùng về việc cập nhật thành công
	    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cập nhật thành công");
	    alert.showAndWait();
	}
	private String getTinhTrang() {
	    if (daThanhToanCheckBox.isSelected()) {
	        return "Đã thanh toán";
	    } else if (chuaThanhToanCheckBox.isSelected()) {
	        return "Chưa thanh toán";
	    } else if (daHuyCheckBox.isSelected()) {
	        return "Đã hủy";
	    } else {
	        return ""; // Hoặc giá trị mặc định tùy thuộc vào yêu cầu của bạn
	    }
	}
	
	@FXML
	private void handleCapNhatHoaDonButton() {
	    String soHd = soHoaDonTuDongTao.getText();
	    java.util.Date utilDate = new java.util.Date();
	    java.sql.Date ngayLap = new java.sql.Date(utilDate.getTime());
	    int slmh = Integer.parseInt(tongSoLuongMatHangTextFiled.getText());
	    double khuyenMai = Double.parseDouble(tongKhuyenMaiTextField.getText());
	    double tongTien = Double.parseDouble(tongTienThanhToanTextField.getText());
	    String tinhTrang = getTinhTrang();
	    String maNv = maNhanVien;
	    String maKh = khachHangMenu.getText();

	    HoaDon hoaDon = new HoaDon(soHd, ngayLap, slmh, khuyenMai, tongTien, tinhTrang, maNv, maKh);
	    HoaDonDAO.getInstance().update(hoaDon);

	    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cập nhật Hoá Đơn thành công");
	    alert.showAndWait();
	}
	
	private void updateCheckBoxes(HoaDon hoaDon) {
	    String tinhTrang = hoaDon.getTinhTrang();

	    // Kiểm tra và tick CheckBox tương ứng với tình trạng của Hoá đơn
	    if (tinhTrang.equals("Đã thanh toán")) {
	        daThanhToanCheckBox.setSelected(true);
	        chuaThanhToanCheckBox.setSelected(false);
	        daHuyCheckBox.setSelected(false);
	    } else if (tinhTrang.equals("Chưa thanh toán")) {
	        daThanhToanCheckBox.setSelected(false);
	        chuaThanhToanCheckBox.setSelected(true);
	        daHuyCheckBox.setSelected(false);
	    } else if (tinhTrang.equals("Đã hủy")) {
	        daThanhToanCheckBox.setSelected(false);
	        chuaThanhToanCheckBox.setSelected(false);
	        daHuyCheckBox.setSelected(true);
	    }
	}
	private void handleChonTinhTrangDonHang() {
		// TODO Auto-generated method stub
		daThanhToanCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
		    if (newValue) {
		        chuaThanhToanCheckBox.setSelected(false);
		        daHuyCheckBox.setSelected(false);
		    }
		});

		chuaThanhToanCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
		    if (newValue) {
		        daThanhToanCheckBox.setSelected(false);
		        daHuyCheckBox.setSelected(false);
		    }
		});

		daHuyCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
		    if (newValue) {
		        daThanhToanCheckBox.setSelected(false);
		        chuaThanhToanCheckBox.setSelected(false);
		    }
		});
	}
}
