package model;

import java.sql.Date;

public class NhanVien {
    private String maNv;
    private String hoTen;
    private String gioiTinh;
    private Date ngaySinh;
    private String diaChi;
    private String sdt;
    private String email;
    private String ghiChu;
    private String username;
    private String password;
    private String maLnd;
	public NhanVien(String maNv, String hoTen, String gioiTinh, Date ngaySinh, String diaChi, String sdt, String email,
			String ghiChu, String username, String password, String maLnd) {
		this.maNv = maNv;
		this.hoTen = hoTen;
		this.gioiTinh = gioiTinh;
		this.ngaySinh = ngaySinh;
		this.diaChi = diaChi;
		this.sdt = sdt;
		this.email = email;
		this.ghiChu = ghiChu;
		this.username = username;
		this.password = password;
		this.maLnd = maLnd;
	}
	public NhanVien() {
	}
	public String getMaNv() {
		return maNv;
	}
	public void setMaNv(String maNv) {
		this.maNv = maNv;
	}
	public String getHoTen() {
		return hoTen;
	}
	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}
	public String getGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public Date getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	public String getSdt() {
		return sdt;
	}
	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGhiChu() {
		return ghiChu;
	}
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMaLnd() {
		return maLnd;
	}
	public void setMaLnd(String maLnd) {
		this.maLnd = maLnd;
	}
    

}