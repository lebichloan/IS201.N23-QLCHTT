CREATE TABLE LOAISANPHAM (
MaLSP NVARCHAR2(10) PRIMARY KEY,
TenLSP NVARCHAR2(50) NOT NULL,
MoTa NVARCHAR2(200)
);

CREATE TABLE CUAHANGCON (
MaCH NVARCHAR2(10) PRIMARY KEY,
TenCH NVARCHAR2(50) NOT NULL,
DiaChi NVARCHAR2(100) NOT NULL
);

CREATE TABLE NHANVIEN (
MaNV NVARCHAR2(10) PRIMARY KEY,
HoTen NVARCHAR2(50) NOT NULL,
GioiTinh NVARCHAR2(10) NOT NULL,
NgaySinh DATE NOT NULL,
DiaChi NVARCHAR2(100) NOT NULL,
SDT NVARCHAR2(15) NOT NULL,
MaCH NVARCHAR2(10) NOT NULL,
MaQL NVARCHAR2(10),
FOREIGN KEY (MaCH) REFERENCES CUAHANGCON(MaCH)
);

CREATE TABLE SANPHAM (
MaSP NVARCHAR2(10) PRIMARY KEY,
TenSP NVARCHAR2(50) NOT NULL,
SoLuong NUMBER NOT NULL,
MauSac NVARCHAR2(20),
KichThuoc NVARCHAR2(20),
DonViTinh NVARCHAR2(20),
GhiChu NVARCHAR2(50),
TinhTrang NVARCHAR2(20),
MaLSP NVARCHAR2(10) NOT NULL,
FOREIGN KEY (MaLSP) REFERENCES LOAISANPHAM(MaLSP)
);

CREATE TABLE NHAPHANG (
MaCH NVARCHAR2(10) NOT NULL,
MaLSP NVARCHAR2(10) NOT NULL,
MaNV NVARCHAR2(10) NOT NULL,
NgayNhap DATE NOT NULL,
SoLuongNhap NUMBER NOT NULL,
DonGiaNhap NUMBER NOT NULL,
PRIMARY KEY (MaCH, MaLSP, MaNV, NgayNhap),
FOREIGN KEY (MaCH) REFERENCES CUAHANGCON(MaCH),
FOREIGN KEY (MaLSP) REFERENCES LOAISANPHAM(MaLSP),
FOREIGN KEY (MaNV) REFERENCES NHANVIEN(MaNV)
);

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


-- Insert into SANPHAM table (Products)
INSERT INTO SANPHAM (MaSP, TenSP, SoLuong, MauSac, KichThuoc, DonViTinh, GhiChu, TinhTrang, MaLSP) VALUES ('SP005', 'Quần jean nam', 50, 'Xanh', '32', 'chiếc', 'Quần jean dài nam', 'Còn hàng', 'LSP01');
INSERT INTO SANPHAM (MaSP, TenSP, SoLuong, MauSac, KichThuoc, DonViTinh, GhiChu, TinhTrang, MaLSP) VALUES ('SP006', 'Áo sơ mi nữ', 30, 'Trắng', 'M', 'chiếc', 'Áo sơ mi dài tay', 'Hết hàng', 'LSP02');
INSERT INTO SANPHAM (MaSP, TenSP, SoLuong, MauSac, KichThuoc, DonViTinh, GhiChu, TinhTrang, MaLSP) VALUES ('SP007', 'Dép nam', 70, 'Đen', '41', 'đôi', 'Dép đi biển', 'Còn hàng', 'LSP03');
INSERT INTO SANPHAM (MaSP, TenSP, SoLuong, MauSac, KichThuoc, DonViTinh, GhiChu, TinhTrang, MaLSP) VALUES ('SP008', 'Giày cao gót nữ', 40, 'Hồng', '37', 'đôi', 'Giày cao gót đi tiệc', 'Không rõ', 'LSP04');
-- Insert into SANPHAM table (Products)
INSERT INTO SANPHAM (MaSP, TenSP, SoLuong, MauSac, KichThuoc, DonViTinh, GhiChu, TinhTrang, MaLSP) VALUES ('SP009', 'Áo khoác nam', 20, 'Đen', 'L', 'chiếc', 'Áo khoác dù nam', 'Hết hàng', 'LSP01');
INSERT INTO SANPHAM (MaSP, TenSP, SoLuong, MauSac, KichThuoc, DonViTinh, GhiChu, TinhTrang, MaLSP) VALUES ('SP010', 'Váy đầm nữ', 40, 'Hồng', 'M', 'chiếc', 'Váy đầm công sở', 'Còn hàng', 'LSP02');
INSERT INTO SANPHAM (MaSP, TenSP, SoLuong, MauSac, KichThuoc, DonViTinh, GhiChu, TinhTrang, MaLSP) VALUES ('SP011', 'Giày thể thao nam', 60, 'Trắng', '43', 'đôi', 'Giày thể thao nam cao cấp', 'Còn hàng', 'LSP03');
INSERT INTO SANPHAM (MaSP, TenSP, SoLuong, MauSac, KichThuoc, DonViTinh, GhiChu, TinhTrang, MaLSP) VALUES ('SP012', 'Túi xách nữ', 30, 'Nâu', NULL, 'cái', 'Túi xách da thật', 'Còn hàng', 'LSP04');

-- Insert into SANPHAM table (Products)
INSERT INTO SANPHAM (MaSP, TenSP, SoLuong, MauSac, KichThuoc, DonViTinh, GhiChu, TinhTrang, MaLSP) VALUES ('SP001', 'Áo thun nam', 100, 'Trắng', 'M', 'chiếc', 'Áo thun dài tay', 'Còn hàng', 'LSP01');
INSERT INTO SANPHAM (MaSP, TenSP, SoLuong, MauSac, KichThuoc, DonViTinh, GhiChu, TinhTrang, MaLSP) VALUES ('SP002', 'Váy dài nữ', 50, 'Đen', 'S', 'chiếc', 'Váy dạ hội', 'Còn hàng', 'LSP02');
INSERT INTO SANPHAM (MaSP, TenSP, SoLuong, MauSac, KichThuoc, DonViTinh, GhiChu, TinhTrang, MaLSP) VALUES ('SP003', 'Giày thể thao nam', 80, 'Xanh', '42', 'đôi', 'Giày chạy bộ', 'Còn hàng', 'LSP03');
INSERT INTO SANPHAM (MaSP, TenSP, SoLuong, MauSac, KichThuoc, DonViTinh, GhiChu, TinhTrang, MaLSP) VALUES ('SP004', 'Giày cao gót nữ', 60, 'Đỏ', '38', 'đôi', 'Giày đi tiệc', 'Còn hàng', 'LSP04');
commit;
SELECT * FROM SANPHAM;
DROP TABLE SANPHAM cascade constraints;

-- Insert into LOAISANPHAM table (Product Categories)
INSERT INTO LOAISANPHAM (MaLSP, TenLSP, MoTa) VALUES ('LSP01', 'Quần áo nam', 'Các loại quần áo nam');
INSERT INTO LOAISANPHAM (MaLSP, TenLSP, MoTa) VALUES ('LSP02', 'Quần áo nữ', 'Các loại quần áo nữ');
INSERT INTO LOAISANPHAM (MaLSP, TenLSP, MoTa) VALUES ('LSP03', 'Giày nam', 'Các loại giày nam');
INSERT INTO LOAISANPHAM (MaLSP, TenLSP, MoTa) VALUES ('LSP04', 'Giày nữ', 'Các loại giày nữ');


