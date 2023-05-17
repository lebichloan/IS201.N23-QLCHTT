CREATE TABLE LOAIKHACHHANG (
MaLKH NVARCHAR2(10) PRIMARY KEY,
TenLKH NVARCHAR2(50) NOT NULL,
GhiChu NVARCHAR2(200)
);

CREATE TABLE KHACHHANG (
MaKH NVARCHAR2(10) PRIMARY KEY,
TenKH NVARCHAR2(50) NOT NULL,
DiaChi NVARCHAR2(100) NOT NULL,
SDT NVARCHAR2(15) NOT NULL,
MaLKH NVARCHAR2(10),
FOREIGN KEY (MaLKH) REFERENCES LOAIKHACHHANG(MaLKH)
);

CREATE TABLE HOADON (
SoHD NVARCHAR2(10) PRIMARY KEY,
NgayLap DATE NOT NULL,
TongTien NUMBER NOT NULL,
TinhTrang NVARCHAR2(20),
MaNV NVARCHAR2(10) NOT NULL,
MaKH NVARCHAR2(10),
FOREIGN KEY (MaNV) REFERENCES NHANVIEN(MaNV),
FOREIGN KEY (MaKH) REFERENCES KHACHHANG(MaKH)
);

CREATE TABLE CTHD (
SoHD NVARCHAR2(10) NOT NULL,
MaSP NVARCHAR2(10) NOT NULL,
SoLuong NUMBER NOT NULL,
DonGia NUMBER NOT NULL,
PRIMARY KEY (SoHD, MaSP),
FOREIGN KEY (SoHD) REFERENCES HOADON(SoHD),
FOREIGN KEY (MaSP) REFERENCES SANPHAM(MaSP)
);

-- Insert into LOAISANPHAM table (Product Categories)
INSERT INTO LOAISANPHAM (MaLSP, TenLSP, MoTa) VALUES ('LSP01', 'Quần áo nam', 'Các loại quần áo nam');
INSERT INTO LOAISANPHAM (MaLSP, TenLSP, MoTa) VALUES ('LSP02', 'Quần áo nữ', 'Các loại quần áo nữ');
INSERT INTO LOAISANPHAM (MaLSP, TenLSP, MoTa) VALUES ('LSP03', 'Giày nam', 'Các loại giày nam');
INSERT INTO LOAISANPHAM (MaLSP, TenLSP, MoTa) VALUES ('LSP04', 'Giày nữ', 'Các loại giày nữ');

-- Insert into SANPHAM table (Products)
INSERT INTO SANPHAM (MaSP, TenSP, SoLuong, MauSac, KichThuoc, DonViTinh, GhiChu, TinhTrang, MaLSP) VALUES ('SP001', 'Áo thun nam', 100, 'Trắng', 'M', 1, 'Áo thun dài tay', 'Còn hàng', 'LSP01');
INSERT INTO SANPHAM (MaSP, TenSP, SoLuong, MauSac, KichThuoc, DonViTinh, GhiChu, TinhTrang, MaLSP) VALUES ('SP002', 'Váy dài nữ', 50, 'Đen', 'S', 1, 'Váy dạ hội', 'Còn hàng', 'LSP02');
INSERT INTO SANPHAM (MaSP, TenSP, SoLuong, MauSac, KichThuoc, DonViTinh, GhiChu, TinhTrang, MaLSP) VALUES ('SP003', 'Giày thể thao nam', 80, 'Xanh', '42', 1, 'Giày chạy bộ', 'Còn hàng', 'LSP03');
INSERT INTO SANPHAM (MaSP, TenSP, SoLuong, MauSac, KichThuoc, DonViTinh, GhiChu, TinhTrang, MaLSP) VALUES ('SP004', 'Giày cao gót nữ', 60, 'Đỏ', '38', 1, 'Giày đi tiệc', 'Còn hàng', 'LSP04');
