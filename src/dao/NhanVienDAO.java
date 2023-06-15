package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import db.database;
import model.KhachHang;
import model.NhanVien;

public class NhanVienDAO implements DAOInterface<NhanVien>{

	@Override
	public int insert(NhanVien t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(NhanVien t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(NhanVien t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<NhanVien> selectAll() {
		ArrayList<NhanVien> listNhanVien = new ArrayList<>();

	    try { 
	    	Connection conn = database.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT * FROM NhanVien");

	        while (rs.next()) {
	            String manv = rs.getString("MA_NV");
	            String hoTen = rs.getString("HO_TEN");
	            String gioiTinh = rs.getString("GIOI_TINH");
	            LocalDate ngaySinh = rs.getDate("NGAY_SINH").toLocalDate();
	            String diaChi = rs.getString("DIA_CHI");
	            String sdt = rs.getString("SDT");
	            String email = rs.getString("EMAIL");
	            String ghiChu = rs.getString("GHI_CHU");
	            String username = rs.getString("USERNAME");
	            String password = rs.getString("PASSWORD");
	            String maLnd = rs.getString("MA_LND");
	            LocalDate NgayVaoLam = rs.getDate("NGAY_VL").toLocalDate();
	            NhanVien nhanvien = new NhanVien(manv, hoTen, gioiTinh, ngaySinh, diaChi, sdt, email, ghiChu, NgayVaoLam, username, password, maLnd);
	            
	            listNhanVien.add(nhanvien);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return listNhanVien;
	}

	@Override
	public NhanVien selectedById(String id) {
		ArrayList<NhanVien> danhSachNhanVien = this.selectAll();
	    for (NhanVien nhanVien : danhSachNhanVien) {
	        if (nhanVien.getMaNV().equals(id)) {
	            return nhanVien;
	        }
	    }
	    return null;
	}
	
	@Override
	public ArrayList<NhanVien> selectByCondition(String condition) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
