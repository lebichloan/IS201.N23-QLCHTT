package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;

public class NhanVien {
	private String maNV;
	private String hoten;
	private String gioiTinh;
	private LocalDate NgaySinh;
	private String diaChi;
	private String sdt;
	private String email;
	private String ghiChu;
	private LocalDate ngayVL;
	private String username;
	private String password;
	private CheckBox checkBox;
	private String maLND;
	private File avatarFile;
	
	public NhanVien() {
		checkBox = new CheckBox();
		checkBox.setSelected(false);
		avatarFile = null;
	}

	public NhanVien(String maNV, String hoten, String gioiTinh, LocalDate ngaySinh, String diaChi, String sdt,
			String email, String ghiChu, LocalDate ngayVL, String username, String password, String ma_lnd, File avatar) {
		super();
		this.maNV = maNV;
		this.hoten = hoten;
		this.gioiTinh = gioiTinh;
		this.NgaySinh = ngaySinh;
		this.diaChi = diaChi;
		this.sdt = sdt;
		this.email = email;
		this.ghiChu = ghiChu;
		this.ngayVL = ngayVL;
		this.username = username;
		this.password = password;
		this.maLND = ma_lnd;
		checkBox = new CheckBox();
		checkBox.setSelected(false);
		this.setAvatarFile(avatar);
	}
	
	public NhanVien(String maNV, String hoten, String gioiTinh, LocalDate ngaySinh, String diaChi, String sdt,
			String email, String ghiChu, LocalDate ngayVL, String username, String password, String ma_lnd) {
		super();
		this.maNV = maNV;
		this.hoten = hoten;
		this.gioiTinh = gioiTinh;
		this.NgaySinh = ngaySinh;
		this.diaChi = diaChi;
		this.sdt = sdt;
		this.email = email;
		this.ghiChu = ghiChu;
		this.ngayVL = ngayVL;
		this.username = username;
		this.password = password;
		this.maLND = ma_lnd;
	}

	public String getMaLnd() {
		return maLND;
	}

	public void setMaLnd(String maLnd) {
		this.maLND = maLnd;
	}

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public String getHoten() {
		return hoten;
	}

	public void setHoten(String hoten) {
		this.hoten = hoten;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public LocalDate getNgaySinh() {
		return NgaySinh;
	}

	public void setNgaySinh(LocalDate ngaySinh) {
		NgaySinh = ngaySinh;
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

	public LocalDate getNgayVL() {
		return ngayVL;
	}

	public void setNgayVL(LocalDate ngayVL) {
		this.ngayVL = ngayVL;
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
	
	public CheckBox getCheckBox() {
		return checkBox;
	}
	
	public File getAvatarFile() {
		return this.avatarFile;
	}
	
	public void setAvatarFile(File file) {
		if(file == null) {
			this.avatarFile = null;
			return;
		}
		String fileName = this.getMaNV() + "Avatar.png";
		try {
			Files.copy(Paths.get(file.toURI()), Paths.get(
					System.getProperty("user.dir")+"/src/asset/"+fileName), 
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.avatarFile = new File(System.getProperty("user.dir")+"/src/asset/"+fileName);
	}
	
	public void clone(NhanVien nv) {
		this.hoten = nv.getHoten();
		this.gioiTinh = nv.getGioiTinh();
		this.NgaySinh = nv.getNgaySinh();
		this.diaChi = nv.getDiaChi();
		this.sdt = nv.getSdt();
		this.email = nv.getEmail();
		this.ghiChu = nv.getGhiChu();
		this.ngayVL = nv.getNgayVL();
		this.username = nv.getUsername();
		this.password = nv.getPassword();
		this.maLND = nv.getMaLnd();
		this.avatarFile = nv.getAvatarFile();
	}
}
