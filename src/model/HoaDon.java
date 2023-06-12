package model;

import java.sql.Date;

public class HoaDon {
    private String soHd;
    private Date ngayLap;
    private int slmh;
    private double khuyenMai;
    private double tongTien;
    private String tinhTrang;
    private String maNv;
    private String maKh;

    public HoaDon(String soHd, Date ngayLap, int slmh, double khuyenMai, double tongTien, String tinhTrang, String maNv, String maKh) {
        this.soHd = soHd;
        this.ngayLap = ngayLap;
        this.slmh = slmh;
        this.khuyenMai = khuyenMai;
        this.tongTien = tongTien;
        this.tinhTrang = tinhTrang;
        this.maNv = maNv;
        this.maKh = maKh;
    }

    // Getters and Setters

    public HoaDon(String querysohoadon, Date queryNgayLap, double querythanhtien, String querytinhtrang,
			String queryNhanVien, String queryKhachHang) {
		// TODO Auto-generated constructor stub
	}

	public String getSoHd() {
        return soHd;
    }

    public void setSoHd(String soHd) {
        this.soHd = soHd;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    public int getSlmh() {
        return slmh;
    }

    public void setSlmh(int slmh) {
        this.slmh = slmh;
    }

    public double getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(double khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getMaNv() {
        return maNv;
    }

    public void setMaNv(String maNv) {
        this.maNv = maNv;
    }

    public String getMaKh() {
        return maKh;
    }

    public void setMaKh(String maKh) {
        this.maKh = maKh;
    }
}
