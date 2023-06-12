package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.database;
import model.CTHD;

public class CTHDDAO implements DAOInterface<CTHD> {

	@Override
    public int insert(CTHD cthd) {
		Connection conn = database.getConnection();
        String query = "INSERT INTO CTHD (So_HD, Ma_SP, So_Luong, Gia, Khuyen_Mai, Thanh_Tien) VALUES (?, ?, ?, ?, ?, ?)";
        try {
        	PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, cthd.getSoHd());
            st.setString(2, cthd.getMaSp());
            st.setInt(3, cthd.getSoLuong());
            st.setDouble(4, cthd.getGia());
            st.setInt(5, cthd.getKhuyenMai());
            st.setDouble(6, cthd.getThanhTien());

            return st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

	@Override
	public int update(CTHD cthd) {
		Connection conn = database.getConnection();
        String query = "UPDATE cthd SET CTHD SO_LUONG =? , Gia =? , KhuyenMai= ?, ThanhTien =? WHERE so_hd = ? AND ma_sp = ?";
        try {
        	PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, cthd.getSoHd());
            st.setString(2, cthd.getMaSp());
            st.setInt(3, cthd.getSoLuong());
            st.setDouble(4, cthd.getGia());
            st.setInt(5, cthd.getKhuyenMai());
            st.setDouble(6, cthd.getThanhTien());

            return st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
	}

	@Override
	public int delete(CTHD t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<CTHD> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CTHD selectedById(String t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<CTHD> selectByCondition(String condition) {
		// TODO Auto-generated method stub
		return null;
	}
	public ArrayList<CTHD> selectBySoHd(String soHd) {
	    ArrayList<CTHD> cthdList = new ArrayList<>();
	    Connection conn = database.getConnection();
	    String query = "SELECT * FROM CTHD WHERE So_HD = ?";
	    try {
	        PreparedStatement st = conn.prepareStatement(query);
	        st.setString(1, soHd);
	        ResultSet rs = st.executeQuery();
	        while (rs.next()) {
	            CTHD cthd = new CTHD(
	                rs.getString("So_HD"),
	                rs.getString("Ma_SP"),
	                rs.getInt("So_Luong"),
	                rs.getDouble("Gia"),
	                rs.getInt("Khuyen_Mai"),
	                rs.getDouble("Thanh_Tien")
	            );
	            cthdList.add(cthd);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return cthdList;
	}
}
