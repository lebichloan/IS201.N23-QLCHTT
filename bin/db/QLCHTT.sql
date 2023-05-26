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
Email NVARCHAR2(50) NOT NULL,
NgayVaoLam DATE NOT NULL,
MaCH NVARCHAR2(10),
MaQL NVARCHAR2(10),
GhiChu NVARCHAR2(500),
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

-- Insert into NHANVIEN
insert into NHANVIEN values ('NV1', 'Nguyễn Văn A', 'Nam', '01/01/2000', '123 Đường ABC', '0123456789', 'nva@gmail.com', '01/01/2020', null, null, null);
insert into NHANVIEN values ('NV2', 'Trần Thị B', 'Nữ', '02/02/2001', '456 Đường XYZ', '0123456789', 'ttb@gmail.com', '02/02/2020', null, null, null);
insert into NHANVIEN values ('NV3', 'Lê Văn C', 'Nam', '03/03/2002', '789 Đường DEF', '0123456789', 'lvc@gmail.com', '03/03/2020', null, null, null);
insert into NHANVIEN values ('NV4', 'Phạm Thị D', 'Nữ', '04/04/2003', '101 Đường GHI', '0123456789', 'ptd@gmail.com', '04/04/2020', null, null, null);
insert into NHANVIEN values ('NV5', 'Nguyễn Văn E', 'Nam', '05/05/2004', '111 Đường JKL', '0123456789', 'nve@gmail.com', '05/05/2020', null, null, null);
insert into NHANVIEN values ('NV6', 'Trần Thị F', 'Nữ', '06/06/2005', '222 Đường MNO', '0123456789', 'ttf@gmail.com','06/06/2020' ,null ,null ,null );
insert into NHANVIEN values ('NV7','Lê Văn G','Nam','07/07/2006','333 Đường PQR','0123456789','lvg@gmail.com','07/07/2020' ,null ,null ,null );
insert into NHANVIEN values ('NV8','Phạm Thị H','Nữ','08/08/2007','444 Đường STU','0123456789','pth@gmail.com','08/08/2020' ,null ,null ,null );
insert into NHANVIEN values ('NV9','Nguyễn Văn I','Nam','09/09/2008','555 Đường VWX','0123456789','nvi@gmail.com','09/09/2020' ,null ,null ,null );
insert into NHANVIEN values ('NV10','Trần Thị K','Nữ','10/10/2009','666 Đường YCX','1234567890','ktranthi@gmail.com','10/12/2020',null,null,null);
insert into NHANVIEN values ('NV11','Lê Thị L','Nữ','11/11/2010','777 Đường ABC','0123456789','ltl@gmail.com','11/11/2020' ,null ,null ,null );
insert into NHANVIEN values ('NV12','Phạm Văn M','Nam','12/12/2011','888 Đường XYZ','0123456789','pvm@gmail.com','12/12/2020' ,null ,null ,null );
insert into NHANVIEN values ('NV13','Nguyễn Thị N','Nữ','13/01/2002','999 Đường DEF','0123456789','ntn@gmail.com','13/01/2020' ,null ,null ,null );
insert into NHANVIEN values ('NV14','Trần Văn O','Nam','14/02/2003','101 Đường GHI KLM','0123456789','tvo@gmail.com', '14/02/2020', null, null, null);
insert into NHANVIEN values ('NV15', 'Lê Thị P', 'Nữ', '15/03/2004', '111 Đường JKL', '0123456789', 'ltp@gmail.com', '15/03/2020', null, null, null);
insert into NHANVIEN values ('NV16', 'Phạm Văn Q', 'Nam', '16/04/2005', '222 Đường MNO', '0123456789', 'pvq@gmail.com', '16/04/2020', null, null, null);
insert into NHANVIEN values ('NV17', 'Nguyễn Thị R', 'Nữ', '17/05/2006', '333 Đường PQR', '0123456789', 'ntr@gmail.com', '17/05/2020', null, null, null);
insert into NHANVIEN values ('NV18', 'Trần Văn S', 'Nam', '18/06/2007', '444 Đường STU', '0123456789', 'tvs@gmail.com', '18/06/2020', null, null, null);
insert into NHANVIEN values ('NV19', 'Lê Thị T', 'Nữ', '19/07/2008', '555 Đường VWX', '0123456789', 'ltt@gmail.com', '19/07/2020' ,null ,null ,null );
insert into NHANVIEN values ('NV20','Phạm Văn U','Nam','20/08/2009','666 Đường YZ ','0123456789','pvu@gmail.com' ,'20/08/2020' ,null ,null ,null );
insert into NHANVIEN values ('NV21' ,'Nguyễn Thị V' ,'Nữ' ,'21/09/2010' ,'777 Đường ABC' ,'0123456789' ,'ntv@gmail.com' ,'21/09/2020' ,null ,null ,null );
insert into NHANVIEN values ('NV22' ,'Trần Văn X' ,'Nam' ,'22/10/2011' ,'888 Đường XYZ' ,'0123456789' ,'tvx@gmail.com' ,'22/10/2020' ,null ,null ,null );
insert into NHANVIEN values ('NV23' ,'Lê Thị Y' ,'Nữ' ,'23/11/2012' ,'999 Đường DEF' ,'0123456789' ,'lty@gmail.com' ,'23/11/2020' ,null ,null ,null );
insert into NHANVIEN values ('NV24' ,'Phạm Văn Z1' ,'Nam' ,'24/12/2013' ,'101 Đường GHI KLMN1 ','0123456789' ,'pvz1@gmail.com ','24/03/2022',null,null,null);


