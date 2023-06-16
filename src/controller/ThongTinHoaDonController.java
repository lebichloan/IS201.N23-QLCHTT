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
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.GridPane;
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
	private Button quayLaiButton, inHoaDonButton;
	private KhachHangDAO khachHangDAO;
	private NhanVienDAO nhanvienDAO;
	private SanPhamDAO sanphamDAO;
	private CTHDDAO cthdDAO;
	
	@FXML
	private ObservableList<CTHD> invoiceDetails;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		invoiceDetails = FXCollections.observableArrayList();
		inHoaDonButton.setOnAction(e -> printContent());
	}

	private void printContent() {
	    // Tạo một PrinterJob
	    PrinterJob printerJob = PrinterJob.createPrinterJob();

	    if (printerJob != null && printerJob.showPrintDialog(null)) {
	        // Lấy nội dung in
	        Node content = createPrintingContent();

	        // Cấu hình in
	        printerJob.getJobSettings().setPageLayout(printerJob.getPrinter().getDefaultPageLayout());

	        // Thực hiện in
	        boolean success = printerJob.printPage(content);
	        if (success) {
	            System.out.println("In hoá đơn thành công.");
	        } else {
	            System.out.println("In hoá đơn thất bại.");
	        }

	        printerJob.endJob();
	    }
	}



	private Node createPrintingContent() {
	    // Tạo một GridPane để chứa nội dung in
	    GridPane gridPane = new GridPane();
	    gridPane.setHgap(10);
	    gridPane.setVgap(10);

	    // Thêm các nodes vào GridPane
	    gridPane.add(new Label("Hóa Đơn"), 0, 0);
	    gridPane.add(new Label("Mã hóa đơn: " + soHoaDonTuDongTao.getText()), 0, 1);
	    gridPane.add(new Label("Ngày tạo: " + ngayTaoText.getText()), 0, 2);

	    // Thêm các thông tin khách hàng vào GridPane
	    gridPane.add(new Label("Thông tin khách hàng"), 0, 4);
	    gridPane.add(new Label("Tên khách hàng: " + tenKhachHangTextField.getText()), 0, 5);
	    gridPane.add(new Label("Số điện thoại: " + sdtTextField.getText()), 0, 6);
	    gridPane.add(new Label("Địa chỉ: " + diaChiTextField.getText()), 0, 7);

	    // Thêm các thông tin nhân viên vào GridPane
	    gridPane.add(new Label("Thông tin nhân viên"), 1, 4);
	    gridPane.add(new Label("Tên nhân viên: " + tenNhanVienTextField.getText()), 1, 5);

	    // Tạo một GridPane mới để chứa thông tin chi tiết hóa đơn
	    GridPane cthdGridPane = new GridPane();
	    cthdGridPane.setHgap(10);
	    cthdGridPane.setVgap(10);

	    // Thêm các cột vào GridPane thông tin chi tiết hóa đơn
	    cthdGridPane.add(new Label("Tên mặt hàng"), 0, 0);
	    cthdGridPane.add(new Label("Số lượng"), 1, 0);
	    cthdGridPane.add(new Label("Đơn giá"), 2, 0);
	    cthdGridPane.add(new Label("Thành tiền"), 3, 0);

	    // Lấy dữ liệu từ TableView và thêm vào GridPane thông tin chi tiết hóa đơn
	    int rowIndex = 1;
	    for (CTHD cthd : cthdTable.getItems()) {
	        String tenMatHang = SanPhamDAO.getInstance().selectedById(cthd.getMaSp()).getTenMatHang();
	        String soLuong = String.valueOf(cthd.getSoLuong());
	        String donGia = String.valueOf(cthd.getDonGia());
	        String thanhTien = String.valueOf(cthd.getThanhTien());

	        cthdGridPane.add(new Label(tenMatHang), 0, rowIndex);
	        cthdGridPane.add(new Label(soLuong), 1, rowIndex);
	        cthdGridPane.add(new Label(donGia), 2, rowIndex);
	        cthdGridPane.add(new Label(thanhTien), 3, rowIndex);

	        rowIndex++;
	    }

	    // Thêm GridPane thông tin chi tiết hóa đơn vào GridPane chính
	    gridPane.add(new Label("Thông tin chi tiết hóa đơn"), 0, 9, 2, 1);
	    gridPane.add(cthdGridPane, 0, 10, 2, 1);

	    // Thêm các thông tin tổng hợp hóa đơn vào GridPane
	    gridPane.add(new Label("Tổng số lượng mặt hàng: " + tongSoLuongMatHangTextFiled.getText()), 0, 12);
	    gridPane.add(new Label("Tổng tiền hàng: " + TongTienHangTextField.getText()), 0, 13);
	    gridPane.add(new Label("Tổng khuyến mãi: " + tongKhuyenMaiTextField.getText()), 0, 14);
	    gridPane.add(new Label("Tổng tiền thanh toán: " + tongTienThanhToanTextField.getText()), 0, 15);

	    return gridPane;
	}


	//Hàm Hiển thị thông tin Hoá Đơn
	public void setHoaDon(HoaDon hoaDon) {
		nhanvienDAO = new NhanVienDAO();
		khachHangDAO = new KhachHangDAO();
        nhanVienMenu.setText(hoaDon.getMaNv());
        NhanVien nv = nhanvienDAO.selectedById(hoaDon.getMaNv());
//        //Lấy Tên Nhân Viên
        tenNhanVienTextField.setText(nv.getHoten());
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
	        Stage stage = (Stage) quayLaiButton.getScene().getWindow();
	        stage.close();
	}

	  
	
}
