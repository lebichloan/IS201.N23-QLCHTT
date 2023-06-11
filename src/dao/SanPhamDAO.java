package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import db.database;
import model.KhachHang;
import model.SanPham;

public class SanPhamDAO implements DAOInterface<SanPham>{

	public static SanPhamDAO getInstance() {
		return new SanPhamDAO();
	}
	@Override
	public int insert(SanPham t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(SanPham t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(SanPham t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<SanPham> selectAll() {
		ArrayList<SanPham> listSanPham = new ArrayList<>();

	    try { 
	    	Connection conn = database.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT * FROM SANPHAM");

	        while (rs.next()) {
	            String masp = rs.getString("MA_SP");
	            String tensp = rs.getString("TEN_SP");
	            Date ngayThem = rs.getDate("NGAY_THEM");
	            String mota = rs.getString("MO_TA");
	            String thuonghieu = rs.getString("THUONG_HIEU");
	            String dvt = rs.getString("DVT");
	            String mausac = rs.getString("MAU_SAC");
	            String kichthuoc =rs.getString("SO_LUONG");
	            int soluong = rs.getInt("SO_LUONG");
	            int dongia = rs.getInt("GIA");
	            String malsp = rs.getString("MA_LSP");

	            SanPham sanpham = new SanPham(masp, tensp, ngayThem, mota, thuonghieu, dvt, mausac, kichthuoc, soluong, dongia,malsp);
	            listSanPham.add(sanpham);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return listSanPham;
	}

	@Override
	public SanPham selectedById(String t) {
		ArrayList<SanPham> danhSachSanPham = this.selectAll();
	    for (SanPham sanpham : danhSachSanPham) {
	        if (sanpham.getMaSanPham().equals(t)) {
	            return sanpham;
	        }
	    }
	    return null;
	}


	@Override
	public ArrayList<SanPham> selectByCondition(String condition) {
		// TODO Auto-generated method stub
		return null;
	}

}
