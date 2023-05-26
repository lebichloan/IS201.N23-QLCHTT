package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import db.database;
import model.HoaDon;
import model.KhachHang;

public class KhachHangDAO implements DAOInterface<KhachHang>{

	public static KhachHangDAO getInstance() {
		return new KhachHangDAO();
	}
	
	@Override
	public int insert(KhachHang t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(KhachHang t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(KhachHang t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<KhachHang> selectAll() {
	    ArrayList<KhachHang> listKhachHang = new ArrayList<>();

	    try { 
	    	Connection conn = database.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT * FROM khachhang");

	        while (rs.next()) {
	            String maKH = rs.getString("ma_kh");
	            String tenKH = rs.getString("ten_kh");
	            String gioiTinh = rs.getString("gioi_tinh");
	            String diaChi = rs.getString("dia_chi");
	            String sdt = rs.getString("sdt");
	            String ghiChu = rs.getString("ghi_chu");
	            String maLKH = rs.getString("ma_lkh");

	            KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinh, diaChi, sdt, ghiChu, maLKH);
	            listKhachHang.add(khachHang);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return listKhachHang;
	}


	@Override
	public KhachHang selectedById(KhachHang t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<KhachHang> selectByCondition(String condition) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public  KhachHang getKhachHangInfo(String maKhachHang) {
        KhachHang khachHang = null;
        String query = "SELECT * FROM khachhang WHERE ma_kh = ?";

        try {
            Connection connectDB = database.getConnection();
            PreparedStatement preparedStatement = connectDB.prepareStatement(query);
            preparedStatement.setString(1, maKhachHang);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String tenKhachHang = resultSet.getString("ten_kh");
                String gioiTinh = resultSet.getString("gioi_tinh");
                String diaChi = resultSet.getString("dia_chi");
                String sdt = resultSet.getString("sdt");
                String ghiChu = resultSet.getString("ghi_chu");
                String maLoaiKhachHang = resultSet.getString("ma_lkh");

                // Create the KhachHang object with retrieved information
                khachHang = new KhachHang(maKhachHang, tenKhachHang, gioiTinh, diaChi, sdt, ghiChu, maLoaiKhachHang);
            }
            resultSet.close();
            preparedStatement.close();
            connectDB.close();
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }

        return khachHang;
    }

}
