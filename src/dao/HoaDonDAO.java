package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.database;
import model.HoaDon;

public class HoaDonDAO implements DAOInterface<HoaDon> {

	public HoaDonDAO() {
		// Private constructor to enforce singleton pattern
	}

	public static HoaDonDAO getInstance() {
		return new HoaDonDAO();
	}

	@Override
	public int insert(HoaDon t) {
		int result = 0;

		try {
			Connection conn = database.getConnection();

			String sql = "INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, tinh_trang, ma_nv, ma_kh) VALUES (?,?,?,?,?,?,?,?)";
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, t.getSoHd());
			st.setDate(2, t.getNgayLap());
			st.setInt(3, t.getSlmh());
			st.setInt(4, t.getKhuyenMai());
			st.setInt(5, t.getTongTien());
			st.setString(6, t.getTinhTrang());
			st.setString(7, t.getMaNv());
			st.setString(8, t.getMaKh());

			result = st.executeUpdate();

			conn.close();

			System.out.println("Ban da thuc thi cau lenh: " + sql);
			System.out.println("So dong du lieu them vao: " + result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public int update(HoaDon t) {
		int result = 0;

		try {
			Connection conn = database.getConnection();

			String sql = "UPDATE hoadon SET ngay_lap=?, slmh=?, khuyen_mai=?, tong_tien=?, tinh_trang=?, ma_nv=?, ma_kh=? WHERE so_hd=?";
			PreparedStatement st = conn.prepareStatement(sql);

			st.setDate(1, t.getNgayLap());
			st.setInt(2, t.getSlmh());
			st.setInt(3, t.getKhuyenMai());
			st.setInt(4, t.getTongTien());
			st.setString(5, t.getTinhTrang());
			st.setString(6, t.getMaNv());
			st.setString(7, t.getMaKh());
			st.setString(8, t.getSoHd());

			result = st.executeUpdate();

			conn.close();

			System.out.println("Ban da thuc thi cau lenh: " + sql);
			System.out.println("So dong du lieu duoc cap nhat: " + result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int delete(HoaDon t) {
		int result = 0;

		try {
			Connection conn = database.getConnection();

			String sql = "DELETE FROM hoadon WHERE so_hd=?";
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, t.getSoHd());

			result = st.executeUpdate();

			conn.close();

			System.out.println("Ban da thuc thi cau lenh: " + sql);
			System.out.println("So dong du lieu duoc xoa: " + result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ArrayList<HoaDon> selectAll() {
		ArrayList<HoaDon> hoaDons = new ArrayList<>();

		try {
			Connection conn = database.getConnection();

			String sql = "SELECT * FROM hoadon";
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				String soHd = rs.getString("so_hd");
				java.sql.Date ngayLap = rs.getDate("ngay_lap");
				int slmh = rs.getInt("slmh");
				int khuyenMai = rs.getInt("khuyen_mai");
				int tongTien = rs.getInt("tong_tien");
				String tinhTrang = rs.getString("tinh_trang");
				String maNv = rs.getString("ma_nv");
				String maKh = rs.getString("ma_kh");

				HoaDon hoaDon = new HoaDon(soHd, ngayLap, slmh, khuyenMai, tongTien, tinhTrang, maNv, maKh);
				hoaDons.add(hoaDon);
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hoaDons;
	}

	@Override
	public HoaDon selectedById(HoaDon t) {
		HoaDon hoaDon = null;

		try {
			Connection conn = database.getConnection();

			String sql = "SELECT * FROM hoadon WHERE so_hd=?";
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, t.getSoHd());

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				String soHd = rs.getString("so_hd");
				java.sql.Date ngayLap = rs.getDate("ngay_lap");
				int slmh = rs.getInt("slmh");
				int khuyenMai = rs.getInt("khuyen_mai");
				int tongTien = rs.getInt("tong_tien");
				String tinhTrang = rs.getString("tinh_trang");
				String maNv = rs.getString("ma_nv");
				String maKh = rs.getString("ma_kh");

				hoaDon = new HoaDon(soHd, ngayLap, slmh, khuyenMai, tongTien, tinhTrang, maNv, maKh);
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hoaDon;
	}

	@Override
	public ArrayList<HoaDon> selectByCondition(String condition) {
		ArrayList<HoaDon> hoaDons = new ArrayList<>();

		try {
			Connection conn = database.getConnection();

			String sql = "SELECT * FROM hoadon WHERE " + condition;
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				String soHd = rs.getString("so_hd");
				java.sql.Date ngayLap = rs.getDate("ngay_lap");
				int slmh = rs.getInt("slmh");
				int khuyenMai = rs.getInt("khuyen_mai");
				int tongTien = rs.getInt("tong_tien");
				String tinhTrang = rs.getString("tinh_trang");
				String maNv = rs.getString("ma_nv");
				String maKh = rs.getString("ma_kh");

				HoaDon hoaDon = new HoaDon(soHd, ngayLap, slmh, khuyenMai, tongTien, tinhTrang, maNv, maKh);
				hoaDons.add(hoaDon);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hoaDons;
	}
}
