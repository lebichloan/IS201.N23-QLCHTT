package model;

public class CTHD {
    private String soHd;
    private String maSp;
    private int soLuong;
    private double Gia;
    private int khuyenMai;
    private double thanhTien;

    public CTHD(String soHd, String maSp, int soLuong, double donGia, int khuyenMai, double thanhTien) {
        this.soHd = soHd;
        this.maSp = maSp;
        this.soLuong = soLuong;
        this.Gia = donGia;
        this.khuyenMai = khuyenMai;
        this.thanhTien = thanhTien;
    }

    // Getters and Setters

    public String getSoHd() {
        return soHd;
    }

    public void setSoHd(String soHd) {
        this.soHd = soHd;
    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGia() {
        return Gia;
    }

    public void setGia(double donGia) {
        this.Gia = donGia;
    }

    public int getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(int khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }
}
