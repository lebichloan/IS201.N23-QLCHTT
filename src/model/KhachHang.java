package model;

public class KhachHang {
    private String maKh;
    private String tenKh;
    private String gioiTinh;
    private String diaChi;
    private String sdt;
    private String ghiChu;
    private String maLkh;

    public KhachHang(String maKh, String tenKh, String gioiTinh, String diaChi, String sdt, String ghiChu, String maLkh) {
        this.maKh = maKh;
        this.tenKh = tenKh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.ghiChu = ghiChu;
        this.maLkh = maLkh;
    }

    public String getMaKh() {
        return maKh;
    }

    public void setMaKh(String maKh) {
        this.maKh = maKh;
    }

    public String getTenKh() {
        return tenKh;
    }

    public void setTenKh(String tenKh) {
        this.tenKh = tenKh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaLkh() {
        return maLkh;
    }

    public void setMaLkh(String maLkh) {
        this.maLkh = maLkh;
    }
}

