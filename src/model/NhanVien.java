package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NhanVien {
	Integer stt;
	String MaNV;
	String HoTen;
	LocalDate NgaySinh;
	String GioiTinh;
	String SDT;
	String email;
	String DiaChi;
	LocalDate NgayVaoLam;
	String GhiChu;
	String MaCH;
	String MaQL;
	
	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	final String NO_DATA = "no data";
	
	public NhanVien(Integer stt, String maNV, String hoten, LocalDate ngaySinh, String gioiTinh, String sDT, String email, String diaChi,
			LocalDate ngayVaoLam, String ghiChu, String maCH, String maQL) {
		this.stt = stt;
		MaNV = maNV;
		HoTen = hoten;
		NgaySinh = ngaySinh;
		GioiTinh = gioiTinh;
		SDT = sDT;
		this.email = email;
		DiaChi = diaChi;
		NgayVaoLam = ngayVaoLam;
		GhiChu = ghiChu;
		MaCH = maCH;
		MaQL = maQL;
	}
	
	public String getStt() {
		return stt.toString();
	}
	
	public String getMaNV() {
		if(MaNV == null) return NO_DATA;
		return MaNV;
	}

	public void setMaNV(String maNV) {
		MaNV = maNV;
	}

	public String getHoTen() {
		if(HoTen == null) return NO_DATA;
		return HoTen;
	}

	public void setHoTen(String hoten) {
		HoTen = hoten;
	}

	public String getNgaySinh() {
		if(NgaySinh == null) return NO_DATA;
		return NgaySinh.format(dateFormatter);
	}

	public void setNgaySinh(LocalDate ngaySinh) {
		NgaySinh = ngaySinh;
	}

	public String getGioiTinh() {
		if(GioiTinh == null) return NO_DATA;
		return GioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		GioiTinh = gioiTinh;
	}

	public String getSDT() {
		if(SDT == null) return NO_DATA;
		return SDT;
	}

	public void setSDT(String sDT) {
		SDT = sDT;
	}

	public String getEmail() {
		if(email == null) return NO_DATA;
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDiaChi() {
		if(DiaChi == null) return NO_DATA;
		return DiaChi;
	}

	public void setDiaChi(String diaChi) {
		DiaChi = diaChi;
	}

	public String getNgayVaoLam() {
		if(NgayVaoLam == null) return NO_DATA;
		return NgayVaoLam.format(dateFormatter);
	}

	public void setNgayVaoLam(LocalDate ngayVaoLam) {
		NgayVaoLam = ngayVaoLam;
	}

	public String getGhiChu() {
		if(GhiChu == null) return NO_DATA;
		return GhiChu;
	}

	public void setGhiChu(String ghiChu) {
		GhiChu = ghiChu;
	}

	public String getMaCH() {
		if(MaCH == null) return NO_DATA;
		return MaCH;
	}

	public void setMaCH(String maCH) {
		MaCH = maCH;
	}

	public String getMaQL() {
		if(MaQL == null) return NO_DATA;
		return MaQL;
	}

	public void setMaQL(String maQL) {
		MaQL = maQL;
	}
}
