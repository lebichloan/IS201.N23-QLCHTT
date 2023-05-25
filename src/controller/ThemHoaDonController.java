package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.KhachHangDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.KhachHang;
import model.SanPham;
import model.HoaDon;

public class ThemHoaDonController implements Initializable {

    @FXML
    private MenuButton maKhachHangMenuButton;

    @FXML
    private MenuButton loaiKhachHangMenuButton;

    @FXML
    private MenuButton maSanPhamMenuButton;

    @FXML
    private MenuButton tenSanPhamMenuButton;

    @FXML
    private MenuButton mauSacMenuButton;

    @FXML
    private MenuButton kichThuocMenuButton;

    @FXML
    private MenuButton donViTinhMenuButton;

    @FXML
    private TextField soLuongTextField;

    @FXML
    private TextField donGiaTextField;

    @FXML
    private TextField thanhTienTextField;

    @FXML
    private CheckBox tinhTrangCheckBox;

    @FXML
    private DatePicker ngayTaoHoaDonDatePicker;
    
    @FXML
    private TextField tenKhachHangTextField;
    
    @FXML
    private TextField sdtTextField;
    
    @FXML
    private TextField diaChiTextField;

    @FXML
    private TableView<HoaDon> chiTietHoaDonTableView;

    @FXML
    private TableColumn<HoaDon, String> maSanPhamColumn;

    @FXML
    private TableColumn<HoaDon, String> tenSanPhamColumn;

    @FXML
    private TableColumn<HoaDon, String> mauSacColumn;

    @FXML
    private TableColumn<HoaDon, String> kichThuocColumn;

    @FXML
    private TableColumn<HoaDon, String> donViTinhColumn;

    @FXML
    private TableColumn<HoaDon, Integer> soLuongColumn;

    @FXML
    private TableColumn<HoaDon, Double> donGiaColumn;
    
    @FXML
    private TableColumn<HoaDon, Integer> khuyenMaiColumn;

    @FXML
    private TableColumn<HoaDon, Double> thanhTienColumn;

    private ObservableList<HoaDon> chiTietHoaDonList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Khởi tạo danh sách chi tiết hoá đơn
        chiTietHoaDonList = FXCollections.observableArrayList();

        // Thiết lập cột cho TableView
        maSanPhamColumn.setCellValueFactory(new PropertyValueFactory<>("maSanPham"));
        tenSanPhamColumn.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));
        mauSacColumn.setCellValueFactory(new PropertyValueFactory<>("mauSac"));
        kichThuocColumn.setCellValueFactory(new PropertyValueFactory<>("kichThuoc"));
        donViTinhColumn.setCellValueFactory(new PropertyValueFactory<>("donViTinh"));
        soLuongColumn.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        donGiaColumn.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        khuyenMaiColumn.setCellValueFactory(new PropertyValueFactory<>("khuyenMai"));
        thanhTienColumn.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));

        // Thiết lập TableView sử dụng danh sách chi tiết hoá đơn
        chiTietHoaDonTableView.setItems(chiTietHoaDonList);

//        //chọn mã khách hàng
//        maKhachHangMenuButton.setOnAction(e -> {
//            // Xử lý khi chọn mã khách hàng từ menu button
//            String maKhachHang = maKhachHangMenuButton.getText();
//            // TODO: Thực hiện các thao tác khi chọn mã khách hàng
//            
//            // Example: Update the text fields with the selected customer's information
//            // Assuming you have a method to retrieve the customer's information based on the selected maKhachHang
//            KhachHang khachHang = KhachHangDAO.getKhachHangInfo(maKhachHang);
//            
//            if (khachHang != null) {
//                tenKhachHangTextField.setText(khachHang.getTenKh());
//                sdtTextField.setText(khachHang.getSdt());
//                diaChiTextField.setText(khachHang.getDiaChi());
//            }
//        });
//        
//        // Thiết lập sự kiện cho menu button
//        maSanPhamMenuButton.setOnAction(e -> {
//            // Xử lý khi chọn mã sản phẩm từ menu button
//            String maSanPham = maSanPhamMenuButton.getText();
//            // TODO: Thực hiện các thao tác khi chọn mã sản phẩm
//        });
//
//        tenSanPhamMenuButton.setOnAction(e -> {
//            // Xử lý khi chọn tên sản phẩm từ menu button
//            String tenSanPham = tenSanPhamMenuButton.getText();
//            // TODO: Thực hiện các thao tác khi chọn tên sản phẩm
//        });
//
//        // TODO: Xử lý các sự kiện và thao tác khác
//
//    }

    // TODO: Thêm các phương thức xử lý sự kiện và thêm sản phẩm vào hoá đơn

}
}