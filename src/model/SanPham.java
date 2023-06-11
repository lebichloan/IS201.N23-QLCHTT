package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.database;

public class SanPham {
    private String maSanPham;
    private String tenSanPham;
    private Date ngayThem;
    private String moTa;
    private String thuongHieu;
    private String donViTinh;
    private String mauSac;
    private String kichThuoc;
    private int soLuong;
    private int donGia;
    private String maLoaiSanPham;
	public SanPham(String maSanPham, String tenSanPham, Date ngayThem, String moTa, String thuongHieu, String donViTinh,
			String mauSac, String kichThuoc, int soLuong, int donGia, String maLoaiSanPham) {
		this.maSanPham = maSanPham;
		this.tenSanPham = tenSanPham;
		this.ngayThem = ngayThem;
		this.moTa = moTa;
		this.thuongHieu = thuongHieu;
		this.donViTinh = donViTinh;
		this.mauSac = mauSac;
		this.kichThuoc = kichThuoc;
		this.soLuong = soLuong;
		this.donGia = donGia;
		this.maLoaiSanPham = maLoaiSanPham;
	}
	public String getMaSanPham() {
		return maSanPham;
	}
	public void setMaSanPham(String maSanPham) {
		this.maSanPham = maSanPham;
	}
	public String getTenSanPham() {
		return tenSanPham;
	}
	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}
	public Date getNgayThem() {
		return ngayThem;
	}
	public void setNgayThem(Date ngayThem) {
		this.ngayThem = ngayThem;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
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
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public int getDonGia() {
		return donGia;
	}
	public void setDonGia(int donGia) {
		this.donGia = donGia;
	}
	public String getMaLoaiSanPham() {
		return maLoaiSanPham;
	}
	public void setMaLoaiSanPham(String maLoaiSanPham) {
		this.maLoaiSanPham = maLoaiSanPham;
	}
	public SanPham() {
	}

}
