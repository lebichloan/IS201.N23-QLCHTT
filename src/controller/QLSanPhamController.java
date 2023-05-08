package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.SanPham;

public class QLSanPhamController implements Initializable{
	@FXML
	private TableView<SanPham> table;
	@FXML
	private TableColumn<SanPham,String> maSPColumn;
	@FXML
	private TableColumn<SanPham,String> tenSPColumn;
	@FXML
	private TableColumn<SanPham,String> mauSacColumn;
	@FXML
	private TableColumn<SanPham,String> kichThuocColumn;
	@FXML
	private TableColumn<SanPham,String> tinhTrangColumn;
	@FXML
	private TableColumn<SanPham,String> maLSPColumn;
	
	private ObservableList<SanPham> listSP;
	
	@FXML
	private TextField maSPTextField;
	@FXML
	private TextField tenSPTextField;
	@FXML
	private TextField mauSacTextField;
	@FXML
	private TextField kichThuocTextField;
	@FXML
	private TextField tinhTrangTextField;
	@FXML
	private TextField maLSPTextField;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listSP = FXCollections.observableArrayList(
		 new SanPham("SP001", "Áo sơ mi nam", "Trắng", "M", "Còn hàng", "LSP001"),
		 new SanPham("SP002", "Áo thun nam", "Đen", "S", "Còn hàng", "LSP001"),
		 new SanPham("SP003", "Quần jean nam", "Xanh", "32", "Hết hàng", "LSP001"),
		 new SanPham("SP004", "Áo sơ mi nữ", "Xanh", "M", "Còn hàng", "LSP002"),
		 new SanPham("SP005", "Áo thun nữ", "Hồng", "L", "Còn hàng", "LSP002"),
		 new SanPham("SP006", "Váy nữ", "Đỏ", "M", "Hết hàng", "LSP002"),
		 new SanPham("SP007", "Áo phông trẻ em", "Vàng", "XL", "Còn hàng", "LSP003"),
		 new SanPham("SP008", "Quần short trẻ em", "Xanh", "28", "Còn hàng", "LSP003"),
		 new SanPham("SP009", "Váy cho bé gái", "Hồng", "2", "Hết hàng", "LSP003")
		);
		maSPColumn.setCellValueFactory(new PropertyValueFactory<SanPham,String>("maSP"));
		tenSPColumn.setCellValueFactory(new PropertyValueFactory<SanPham,String>("tenSP"));
		mauSacColumn.setCellValueFactory(new PropertyValueFactory<SanPham,String>("mauSac"));
		kichThuocColumn.setCellValueFactory(new PropertyValueFactory<SanPham,String>("kichThuoc"));
		tinhTrangColumn.setCellValueFactory(new PropertyValueFactory<SanPham,String>("tinhTrang"));
		maLSPColumn.setCellValueFactory(new PropertyValueFactory<SanPham,String>("maLSP"));
		table.setItems(listSP);
	}
	public void add(ActionEvent e) {
		SanPham newSP = new SanPham();
		newSP.setMaSP(maSPTextField.getText());
		newSP.setTenSP(tenSPTextField.getText());
		newSP.setMauSac(mauSacTextField.getText());
		newSP.setKichThuoc(kichThuocTextField.getText());
		newSP.setTinhTrang(tinhTrangTextField.getText());
		newSP.setMaLSP(maLSPTextField.getText());
	    listSP.add(newSP);
	}
	public void delete(ActionEvent e) {
		SanPham selected =table.getSelectionModel().getSelectedItem();
		listSP.remove(selected);
	}
}
