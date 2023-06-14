package test;

import java.util.List;
import dao.KhachHangDAO;
import model.KhachHang;

public class TestKhachHangDAO {
  
  public static void main(String[] args) {
    
    KhachHangDAO khachHangDAO = new KhachHangDAO();

    // Test getAllKhachHang method
    List<KhachHang> listKhachHang = khachHangDAO.selectAll();
    for (KhachHang khachHang : listKhachHang) {
      System.out.println(khachHang);
    }

    // Test getKhachHangById method
    String id = "KH001";
    KhachHang khachHang = khachHangDAO.getKhachHangInfo(id);
    System.out.println(khachHang);

//    // Test addKhachHang method
//    KhachHang newKhachHang = new KhachHang("Nguyen Van A", "0912345678", "nguyenvana@gmail.com");
//    khachHangDAO.addKhachHang(newKhachHang);
//
//    // Test updateKhachHang method
//    KhachHang khachHangToUpdate = khachHangDAO.getKhachHangById(1);
//    khachHangToUpdate.setTenKhachHang("Nguyen Van B");
//    khachHangToUpdate.setSoDienThoai("0123456789");
//    khachHangToUpdate.setEmail("nguyenvanb@gmail.com");
//    khachHangDAO.updateKhachHang(khachHangToUpdate);
//
//    // Test deleteKhachHang method
//    int khachHangIdToDelete = 2;
//    khachHangDAO.deleteKhachHang(khachHangIdToDelete);
    
  }
}
