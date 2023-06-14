package test;

import java.util.List;

import dao.SanPhamDAO;
import model.SanPham;

public class testSanPhamDAO {
	  
	  public static void main(String[] args) {
	    
	    SanPhamDAO sanPhamDAO = new SanPhamDAO();

	    // Test getAllKhachHang method
	    List<SanPham> listSanPham= sanPhamDAO.selectAll();
	    for (SanPham khachHang : listSanPham) {
	      System.out.println(khachHang);
	    }

	    // Test getKhachHangById method
//	    String id = "NV001";
//	    NhanVien nhanvien = nhanVienDAO.;
//	    System.out.println(nhanvien);

//	    // Test addKhachHang method
//	    KhachHang newKhachHang = new KhachHang("Nguyen Van A", "0912345678", "nguyenvana@gmail.com");
//	    khachHangDAO.addKhachHang(newKhachHang);
	//
//	    // Test updateKhachHang method
//	    KhachHang khachHangToUpdate = khachHangDAO.getKhachHangById(1);
//	    khachHangToUpdate.setTenKhachHang("Nguyen Van B");
//	    khachHangToUpdate.setSoDienThoai("0123456789");
//	    khachHangToUpdate.setEmail("nguyenvanb@gmail.com");
//	    khachHangDAO.updateKhachHang(khachHangToUpdate);
	//
//	    // Test deleteKhachHang method
//	    int khachHangIdToDelete = 2;
//	    khachHangDAO.deleteKhachHang(khachHangIdToDelete);
	    
	  }
	}

