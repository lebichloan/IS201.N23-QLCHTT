package test;

import java.sql.Date;
import dao.HoaDonDAO;
import model.HoaDon;

public class testHoaDonDAO {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String soHd = "HHH";
        Date ngayLap = new Date(System.currentTimeMillis()); // Use the current date
        int slmh = 5;
        int khuyenMai = 10;
        int tongTien = 500000;
        String tinhTrang = "In progress";
        String maNv = "NV001";
        String maKh = "KH001";

        HoaDon hd1 = new HoaDon(soHd, ngayLap, slmh, khuyenMai, tongTien, tinhTrang, maNv, maKh);
        
        HoaDonDAO.getInstance().insert(hd1);
	}
}
