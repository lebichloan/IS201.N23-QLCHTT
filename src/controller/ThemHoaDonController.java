package controller;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.sql.Date;
import dao.HoaDonDAO;
import dao.KhachHangDAO;
import dao.NhanVienDAO;
import dao.SanPhamDAO;
import dao.CTHDDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import model.KhachHang;
import model.SanPham;
import model.HoaDon;
import model.NhanVien;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import model.CTHD;
import java.text.DecimalFormat;
import java.text.ParseException;

import model.TuTaoKey;

public class ThemHoaDonController implements Initializable {

	private KhachHangDAO khachHangDAO;
	private NhanVienDAO nhanvienDAO;
	private SanPhamDAO sanphamDAO;
//nhập thông tin chung và thong tin san pham
	@FXML
	private MenuButton khachHangMenu;
	@FXML
	private MenuButton nhanVienMenu;
	@FXML
	private MenuButton sanPhamMenu;
	@FXML
	private TextField diaChiTextField;
	@FXML
	private TextField tenKhachHangTextField;
	@FXML
	private TextField sdtTextField;
	@FXML
	private TextField tenSanPhamTextField;
	@FXML
	private TextField mauSacTextField;
	@FXML
	private TextField soLuongTextField;
	@FXML
	private TextField donGiaTextField;
	@FXML
	private TextField thanhTienTextField;
	@FXML
	private TextField kichThuocTextField;
	@FXML
	private TextField conLaiTextField;
	@FXML
	private TextField donViTinhTextField;
	@FXML
	private TextField khuyenMaiTextField;
	@FXML
	private Button themHoaDonButton;
	
//bảng chi tiết hoá đơn
	@FXML
	private Text soHoaDonTextField;
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
	private ObservableList<CTHD> invoiceDetails;
	@FXML
	private TableView<CTHD> cthdTable;
	@FXML
	private TextField tongSoLuongMatHangTextFiled;
	@FXML
	private TextField TongTienHangTextField;
	@FXML
	private TextField tongKhuyenMaiTextField;
	@FXML
	private TextField tongTienThanhToanTextField;

	private boolean maSanPhamDaChon = false;
	
	@FXML
	private CheckBox daThanhToanCheckBox;
	@FXML
	private CheckBox chuaThanhToanCheckBox;
	@FXML
	private CheckBox daHuyCheckBox;
	@FXML
    private Text ngayTaoText;
	@FXML
	private Text soHoaDonTuDongTao;
	@FXML
	private Button quayLaiButton;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		chuaThanhToanCheckBox.setSelected(true);
		loadDanhSachKhachHang();
		loadDanhSachNhanVien();
		loadDanhSachSanPham();
		capNhatThanhTien();
		invoiceDetails = FXCollections.observableArrayList();
		khoitaoCTHDTable();
		handleChonTinhTrangDonHang();
		handleNgayTao();
		soHoaDonTuDongTao.setText(TuTaoKey.createKey("HD"));
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


	private void handleNgayTao() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

	    // Lấy thông tin ngày hiện tại
	    LocalDateTime currentDateTime = LocalDateTime.now();

	    //định dạng ngày hiện tại sang kiểu string
	    String formattedDateTime = currentDateTime.format(formatter);

	    // Set the formatted date and time as the default value of the ngayTaoText TextField
	    ngayTaoText.setText(formattedDateTime);
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
				tenKhachHangTextField.setText(kh.getTenKh());
				diaChiTextField.setText(kh.getDiaChi());
				sdtTextField.setText(kh.getSdt());
			});
			khachHangMenu.getItems().add(item);
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

	private void loadDanhSachSanPham() {
		// Khởi tạo đối tượng DAO cho KhachHang và HoaDon
		sanphamDAO = new SanPhamDAO();
		
		// Load danh sách khách hàng lên menu
		ObservableList<SanPham> listSanPham = FXCollections.observableArrayList(sanphamDAO.selectAll());
		sanPhamMenu.getItems().clear();
		for (SanPham sp : listSanPham) {
			MenuItem item = new MenuItem(sp.getMaSanPham());
			item.setOnAction(e -> {
				maSanPhamDaChon = true;
				sanPhamMenu.setText(sp.getMaSanPham());
				tenSanPhamTextField.setText(sp.getTenSanPham());
				mauSacTextField.setText(sp.getMauSac());
				donGiaTextField.setText(String.valueOf(sp.getDonGia()));
				kichThuocTextField.setText(sp.getKichThuoc());
				conLaiTextField.setText(String.valueOf(sp.getSoLuong()));
				donViTinhTextField.setText(sp.getDonViTinh());
			});
			sanPhamMenu.getItems().add(item);
		}
	}
	
	private void khoitaoCTHDTable(){
		tenSanPhamColumn.setCellValueFactory(new PropertyValueFactory<>("maSp"));
        soLuongColumn.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        donViTinhColumn.setCellValueFactory(new PropertyValueFactory<>("donViTinh"));
        donGiaColumn.setCellValueFactory(new PropertyValueFactory<>("Gia"));
        khuyenMaiColumn.setCellValueFactory(new PropertyValueFactory<>("khuyenMai"));
        thanhTienColumn.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));

        cthdTable.setItems(invoiceDetails);
	}
	
	
	private boolean kiemTraMaSanPhamDaChon() {
	    if (!maSanPhamDaChon) {
	        Alert alert = new Alert(AlertType.ERROR, "Vui lòng chọn mã sản phẩm trước khi nhập số lượng và khuyến mãi");
	        alert.showAndWait();
	    }
	    return maSanPhamDaChon;
	}
	
	public void capNhatThanhTien() {
	    // Create a listener for the quantity text field
	    soLuongTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	        if (!kiemTraMaSanPhamDaChon()) {
	            // If the product has not been selected yet, do not proceed with calculating the total value
	            soLuongTextField.setText(oldValue);
	            return;
	        }
	       
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
	
	//Hàm xử lí nút thêm hoá đơn
	@FXML
	private void themHoaDonButtonClicked()  {
		if (kiemTraTruongChuaChon()) {
	        return; // Không thực hiện thêm hóa đơn nếu có trường chưa chọn/nhập
	    }
	    String soHd = soHoaDonTuDongTao.getText();
	    String maSp = sanPhamMenu.getText();
	    int soLuong = Integer.parseInt(soLuongTextField.getText());
	    double donGia = Double.parseDouble(donGiaTextField.getText());
	    int khuyenMai = Integer.parseInt(khuyenMaiTextField.getText());
	    double thanhTien = Double.parseDouble(thanhTienTextField.getText());

        CTHD cthd = new CTHD(soHd, maSp, soLuong, donGia, khuyenMai, thanhTien);
        //thêm các thông tin về sản phẩm vào trong cthd
	    invoiceDetails.add(cthd);
	    thongTinHoaDon();
	    
	}
	//khi nhấn vào nút lưu hoá đơn
	@FXML
	private void luuHoaDonButtonClicked() {
	    try {
	        HoaDon hoaDon = themThongTinChungVaoHoaDon();
	        CTHDDAO cthdDAO = new CTHDDAO();
	        for (CTHD cthd : invoiceDetails) {
	            cthdDAO.insert(cthd); // Lưu chi tiết hóa đơn vào CSDL
	        }
	        HoaDonDAO hoaDonDAO = new HoaDonDAO();
	        hoaDon.setSlmh(Integer.parseInt(tongSoLuongMatHangTextFiled.getText()));
	        hoaDon.setTongTien(Integer.parseInt(TongTienHangTextField.getText()));
	        // Cập nhật hóa đơn vào CSDL
	        hoaDonDAO.insert(hoaDon);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	}
	private HoaDon themThongTinChungVaoHoaDon() throws ParseException {
	    String soHd = soHoaDonTuDongTao.getText();
	    java.util.Date utilDate = new java.util.Date();
	    java.sql.Date ngayLap = new java.sql.Date(utilDate.getTime()); 
	    int slmh = Integer.parseInt(tongSoLuongMatHangTextFiled.getText());
	    double khuyenMai = Double.parseDouble(tongKhuyenMaiTextField.getText());
	    double tongTien = Double.parseDouble(tongTienThanhToanTextField.getText());
	    String tinhTrang = getTinhTrang() ;
	    String maNv = nhanVienMenu.getText();
	    String maKh = khachHangMenu.getText();
	    
	    HoaDon hd = new HoaDon(soHd, ngayLap, slmh, khuyenMai, tongTien, tinhTrang, maNv, maKh);
	    HoaDonDAO.getInstance().insert(hd);
	    return hd;
	}

	private void thongTinHoaDon() {
		double tongTienHang = 0;
		double tongKhuyenMai =0;
		double tongTienThanhToan =0;
		for(CTHD cthd : invoiceDetails) {
			tongTienHang += cthd.getGia()*cthd.getSoLuong();
			tongKhuyenMai += cthd.getGia()*cthd.getKhuyenMai()*cthd.getSoLuong()/100;
			tongTienThanhToan += cthd.getGia()*cthd.getSoLuong() -cthd.getGia()*cthd.getKhuyenMai()*cthd.getSoLuong()/100;
		}
		tongSoLuongMatHangTextFiled.setText(Integer.toString(invoiceDetails.size()));
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		TongTienHangTextField.setText(decimalFormat.format(tongTienHang));
		tongKhuyenMaiTextField.setText(decimalFormat.format(tongKhuyenMai));
		tongTienThanhToanTextField.setText(decimalFormat.format(tongTienThanhToan));
	}
	private boolean kiemTraTruongChuaChon() {
		String khachHangMenuDefault = "Chọn mã khách hàng";
	    String nhanVienMenuDefault = "Chọn Mã Nhân Viên";
	    String sanPhamMenuDefault = "Chọn mã sản phẩm";
	    boolean khachHangChuaChon = khachHangMenu.getText().equals(khachHangMenuDefault);
	    boolean nhanVienChuaChon = nhanVienMenu.getText().equals(nhanVienMenuDefault);
	    boolean sanPhamChuaChon = sanPhamMenu.getText().equals(sanPhamMenuDefault);
	    boolean soLuongChuaNhap = soLuongTextField.getText().isEmpty();

	    StringBuilder errorMessage = new StringBuilder("Vui lòng chọn/nhập đầy đủ thông tin:");

	    if (khachHangChuaChon) {
	        errorMessage.append("\n- Chưa chọn khách hàng");
	    }
	    if (nhanVienChuaChon) {
	        errorMessage.append("\n- Chưa chọn nhân viên");
	    }
	    if (sanPhamChuaChon) {
	        errorMessage.append("\n- Chưa chọn sản phẩm");
	    }
	    if (soLuongChuaNhap) {
	        errorMessage.append("\n- Chưa Nhập số lượng sản phẩm");
	    }

	    if (khachHangChuaChon || nhanVienChuaChon || sanPhamChuaChon || soLuongChuaNhap) {
	        Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage.toString());
	        alert.showAndWait();
	        return true; // Trả về true nếu có trường chưa chọn/nhập
	    }
	    return false; // Trả về false nếu tất cả các trường đã chọn/nhập
	}

	}
