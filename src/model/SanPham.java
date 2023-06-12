package model;

import java.sql.Date;

import javafx.scene.control.DatePicker;

public class SanPham {
    private String maSP;
    private String tenSP;
	private String donViTinh;
    private int soLuong;
    private String mauSac;
    private String kichThuoc;
    private String tinhTrang;
    private String ghiChu;
    private Date ngayThem;
    private int gia;
    private String thuongHieu;
	private String maLSP;

	public Date getNgayThem() {
		return ngayThem;
	}
	public void setNgayThem(Date ngayThem) {
		this.ngayThem = ngayThem;
	}
	public int getGia() {
		return gia;
	}
	public void setGia(int gia) {
		this.gia = gia;
	}
	public String getThuongHieu() {
		return thuongHieu;
	}
	public void setThuongHieu(String thuongHieu) {
		this.thuongHieu = thuongHieu;
	}
	public String getDonViTinh() {
		return donViTinh;
	}
	public void setDonViTinh(String donViTinh) {
		this.donViTinh = donViTinh;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public String getGhiChu() {
		return ghiChu;
	}
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	public String getMaSP() {
		return maSP;
	}
	public void setMaSP(String maSP) {
		this.maSP = maSP;
	}
	public String getTenSP() {
		return tenSP;
	}
	public void setTenSP(String tenSP) {
		this.tenSP = tenSP;
	}
	public String getMauSac() {
		return mauSac;
	}
	public void setMauSac(String mauSac) {
		this.mauSac = mauSac;
	}
	public String getKichThuoc() {
		return kichThuoc;
	}
	public void setKichThuoc(String kichThuoc) {
		this.kichThuoc = kichThuoc;
	}
	public String getTinhTrang() {
		return tinhTrang;
	}
	public void setTinhTrang(String tinhTrang) {
		this.tinhTrang = tinhTrang;
	}
	public String getMaLSP() {
		return maLSP;
	}
	public void setMaLSP(String maLSP) {
		this.maLSP = maLSP;
	}
	
	
	public SanPham(String maSP, String tenSP, String donViTinh, int soLuong, String mauSac,
			String kichThuoc, String tinhTrang, String ghiChu, Date ngayThem, int gia, String thuongHieu,
			String maLSP) {
		super();
		this.maSP = maSP;
		this.tenSP = tenSP;
		this.donViTinh = donViTinh;
		this.soLuong = soLuong;
		this.mauSac = mauSac;
		this.kichThuoc = kichThuoc;
		this.tinhTrang = tinhTrang;
		this.ghiChu = ghiChu;
		this.ngayThem = ngayThem;
		this.gia = gia;
		this.thuongHieu = thuongHieu;
		this.maLSP = maLSP;
	}
	

	public SanPham() {
		this.maSP="0";
	}


}
