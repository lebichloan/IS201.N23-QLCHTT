DROP TABLE sanpham cascade constraints;
DROP TABLE loainguoidung cascade constraints;
DROP TABLE nhanvien cascade constraints;
DROP TABLE loaisanpham cascade constraints;
DROP TABLE sanpham cascade constraints;
DROP TABLE hoadon cascade constraints;
DROP TABLE loaikhachhang cascade constraints;
DROP TABLE khachhang cascade constraints;
DROP TABLE cthd cascade constraints;

alter session set nls_date_format = 'DD/MM/YYYY';

/
CREATE TABLE loainguoidung (
ma_lnd NVARCHAR2(10) PRIMARY KEY NOT NULL,
ten_lnd NVARCHAR2(50) NOT NULL
);
/

CREATE TABLE nhanvien (
ma_nv NVARCHAR2(10) PRIMARY KEY NOT NULL,
ho_ten NVARCHAR2(50) NOT NULL,
gioi_tinh NVARCHAR2(3) NOT NULL check(gioi_tinh in (n'Nam', n'Nữ')),
ngay_sinh DATE NOT NULL,
dia_chi NVARCHAR2(100) NOT NULL,
sdt NVARCHAR2(15) NOT NULL,
email NVARCHAR2(50) NOT NULL,
ghi_chu NVARCHAR2(200),
ngay_vl DATE NOT NULL,
username NVARCHAR2(20) NOT NULL,
password NVARCHAR2(20) NOT NULL,
ma_lnd NVARCHAR2(10) NOT NULL,
avatar blob,
FOREIGN KEY (ma_lnd) REFERENCES loainguoidung (ma_lnd)
);
/

CREATE TABLE loaikhachhang (
ma_lkh NVARCHAR2(10) PRIMARY KEY NOT NULL,
ten_lkh NVARCHAR2(50) NOT NULL,
mo_ta NVARCHAR2(50),
slkh NVARCHAR2(50) NOT NULL,
ghi_chu NVARCHAR2(200)
);
/

CREATE TABLE khachhang (
ma_kh NVARCHAR2(10) PRIMARY KEY NOT NULL,
ten_kh NVARCHAR2(50) NOT NULL,
gioi_tinh NVARCHAR2(3),
dia_chi NVARCHAR2(100),
sdt NVARCHAR2(15),
ghi_chu NVARCHAR2(200),
ma_lkh NVARCHAR2(10) NOT NULL,
FOREIGN KEY (ma_lkh) REFERENCES loaikhachhang (ma_lkh)
);
/

CREATE TABLE loaisanpham (
ma_lsp NVARCHAR2(10) PRIMARY KEY NOT NULL,
ten_lsp NVARCHAR2(50) NOT NULL,
mo_ta NVARCHAR2(200),
slmh INT NOT NULL,
ghi_chu NVARCHAR2(200)
);
/

CREATE TABLE sanpham (
ma_sp NVARCHAR2(10) PRIMARY KEY NOT NULL,
ten_sp NVARCHAR2(50) NOT NULL,
ngay_them DATE NOT NULL,
mo_ta NVARCHAR2(200),
thuong_hieu NVARCHAR2(20),
dvt NVARCHAR2(20) NOT NULL,
mau_sac NVARCHAR2(50),
kich_thuoc NVARCHAR2(20),
so_luong INT NOT NULL,
gia DECIMAL(10, 2) NOT NULL,
TinhTrang NVARCHAR2(20) NOT NULL,
ma_lsp NVARCHAR2(10) NOT NULL,
FOREIGN KEY (ma_lsp) REFERENCES loaisanpham (ma_lsp)
);
/

CREATE TABLE hoadon (
so_hd NVARCHAR2(20) PRIMARY KEY,
ngay_lap DATE NOT NULL,
slmh INT NOT NULL,
khuyen_mai DECIMAL(10, 2) NOT NULL,
tong_tien DECIMAL(10, 2) NOT NULL,
ma_nv NVARCHAR2(10) NOT NULL,
ma_kh NVARCHAR2(10),
trang_thai NVARCHAR2(50) NOT NULL,
FOREIGN KEY (ma_nv) REFERENCES nhanvien (ma_nv),
FOREIGN KEY (ma_kh) REFERENCES khachhang (ma_kh)
);
/

CREATE TABLE cthd (
so_hd NVARCHAR2(20),
ma_sp NVARCHAR2(10) NOT NULL,
so_luong INT NOT NULL,
gia DECIMAL(10, 2) NOT NULL,
khuyen_mai DECIMAL(10, 2) NOT NULL,
thanh_tien DECIMAL(10, 2) NOT NULL,
PRIMARY KEY (so_hd, ma_sp),
FOREIGN KEY (ma_sp) REFERENCES sanpham (ma_sp),
FOREIGN KEY (so_hd) REFERENCES hoadon (so_hd)
);
/

INSERT INTO loainguoidung (ma_lnd, ten_lnd)
VALUES ('LND001', 'Quản lý');
INSERT INTO loainguoidung (ma_lnd, ten_lnd)
VALUES ('LND002', 'Nhân viên bán hàng');

commit;

INSERT INTO nhanvien (ma_nv, ho_ten, gioi_tinh, ngay_sinh, dia_chi, sdt, email, ghi_chu, ngay_vl, username, password, ma_lnd)
VALUES ('NV001', n'Nguyễn Văn A', n'Nam', TO_DATE('1990-01-01', 'YYYY-MM-DD'), '123 ABC Street, City', '0123456789', 'aguyen123@example.com', 'Manager', TO_DATE('2010-01-01', 'YYYY-MM-DD'), 'admin', '123456', 'LND001');
INSERT INTO nhanvien (ma_nv, ho_ten, gioi_tinh, ngay_sinh, dia_chi, sdt, email, ghi_chu, ngay_vl, username, password, ma_lnd)
VALUES ('NV002', n'Trần Thị B', n'Nữ', TO_DATE('1995-05-10', 'YYYY-MM-DD'), '456 XYZ Street, City', '0876543210', 'btran321@example.com', 'Salesperson', TO_DATE('2015-06-01', 'YYYY-MM-DD'), 'nhanvien01', '123987', 'LND002');
INSERT INTO nhanvien (ma_nv, ho_ten, gioi_tinh, ngay_sinh, dia_chi, sdt, email, ghi_chu, ngay_vl, username, password, ma_lnd)
VALUES ('NV003', n'Phạm Văn C', n'Nam', TO_DATE('1992-11-15', 'YYYY-MM-DD'), '789 PQR Street, City', '0123456789', 'cpham115@example.com', 'Salesperson', TO_DATE('2018-03-15', 'YYYY-MM-DD'), 'snhanvien02', '123987', 'LND002');
insert into nhanvien values ('NV004', n'Phạm Trúc Hạnh', n'Nữ', '07/06/1988', n'33 Nguyễn Huệ', '0646580735', 'hanh889@gmail.com', null, '28/12/2023', 'hanh', '123', 'LND002',null);
insert into nhanvien values ('NV005', n'Dương Thúy Liên', n'Nữ', '18/10/1991', n'64 Trần Hưng Đạo', '0423927119', 'lien911@gmail.com', null, '25/07/2020', 'lien', 'lienthuy234', 'LND002',null);
insert into nhanvien values ('NV006', n'Lý Trúc Hương', n'Nữ', '26/05/1990', n'51 Lê Công Kiều', '0692930230', 'huong90@gmail.com', null, '08/03/2022', 'huong', '456', 'LND002',null);
insert into nhanvien values ('NV007', n'Dương Mỹ Anh', n'Nữ', '30/05/1990', n'40 Nam Kỳ Khởi Nghĩa', '0659109763', 'anh903@gmail.com', null, '10/08/2020', 'anh', '678', 'LND002',null);
insert into nhanvien values ('NV008', n'Đặng Trung Hưng', n'Nam', '03/11/1992', n'98 Cô Giang', '0149647040', 'hung923@gmail.com', null, '05/01/2023', 'hung', '000', 'LND002',null);
insert into nhanvien values ('NV009', n'Lê Tuấn Tài', n'Nam', '13/01/1988', n'46 Tôn Thất Tùng', '0164914071', 'tai889@gmail.com', null, '14/06/2022', 'Tai', '345791', 'LND002',null);
insert into nhanvien values ('NV010', n'Ngô Thành Thịnh', n'Nam', '06/05/1999', n'87 Nguyễn Du', '0744790670', 'thanh99@gmail.com', null, '18/06/2023', 'thinh', 'thinh', 'LND002',null);
insert into nhanvien values ('NV011', n'Bùi Mai Anh', n'Nữ', '07/05/1996', n'07 Trần Khánh Dư', '0906050699', 'anh969@gmail.com', null, '13/06/2023', '134anh', 'anh', 'LND002',null);
insert into nhanvien values ('NV015', n'Ngô Tấn Trường', n'Nam', '06/06/2004', n'32 Công Trường Lam Sơn', '0774188796', 'truong04@gmail.com', null, '25/07/2023', 'truong', 'letruong', 'LND002',null);
insert into nhanvien values ('NV016', n'Quãng Thảo Hằng', n'Nữ', '02/12/1997', n'01 Công Trường Lam Sơn', '0856613309', 'hong9779@gmail.com', null, '09/11/2020', 'hangcute', 'hang432', 'LND002',null);

commit;

-- Insert into loaisanpham table
INSERT INTO loaisanpham (ma_lsp, ten_lsp, mo_ta, slmh, ghi_chu)
VALUES ('LSP001', 'Áo thun', 'Loại sản phẩm áo', 0, 'Áo thun các loại');

INSERT INTO loaisanpham (ma_lsp, ten_lsp, mo_ta, slmh, ghi_chu)
VALUES ('LSP002', 'Áo khoác', 'Loại sản phẩm áo', 0, 'Áo khoác các loại');

INSERT INTO loaisanpham (ma_lsp, ten_lsp, mo_ta, slmh, ghi_chu)
VALUES ('LSP003', 'Quần', 'Loại sản phẩm quần', 0, 'Quần các loại');

INSERT INTO loaisanpham (ma_lsp, ten_lsp, mo_ta, slmh, ghi_chu)
VALUES ('LSP004', 'Váy', 'Loại sản phẩm quần', 0, 'Váy các loại');

INSERT INTO loaisanpham (ma_lsp, ten_lsp, mo_ta, slmh, ghi_chu)
VALUES ('LSP005', 'Vest', 'Loại sản phẩm vest', 0, 'Các sản phẩm vest sang trọng');

-- Insert into loaisanpham table
INSERT INTO loaisanpham (ma_lsp, ten_lsp, mo_ta, slmh, ghi_chu)
VALUES ('LSP006', 'Giày', 'Giày thời trang', 15, 'Loại sản phẩm giày');

INSERT INTO loaisanpham (ma_lsp, ten_lsp, mo_ta, slmh, ghi_chu)
VALUES ('LSP007', 'Phụ kiện', 'Phụ kiện thời trang', 10, 'Loại sản phẩm phụ kiện');

commit;

-- Thêm vào bảng sanpham
INSERT INTO sanpham (ma_sp, ten_sp, ngay_them, mo_ta, thuong_hieu, dvt, mau_sac, kich_thuoc, so_luong, gia, ma_lsp, TinhTrang)
VALUES ('SP001', 'Áo sơ mi nam', TO_DATE('2023-05-27', 'YYYY-MM-DD'), 'Áo sơ mi nam phong cách trẻ trung', 'Nhãn hiệu A', 'Cái', 'Trắng', 'L', 10, 250000, 'LSP001', 'Còn hàng');

INSERT INTO sanpham (ma_sp, ten_sp, ngay_them, mo_ta, thuong_hieu, dvt, mau_sac, kich_thuoc, so_luong, gia, ma_lsp, TinhTrang)
VALUES ('SP002', 'Váy hoa nữ', TO_DATE('2023-05-27', 'YYYY-MM-DD'), 'Váy hoa nữ dễ thương', 'Nhãn hiệu B', 'Cái', 'Đỏ', 'M', 5, 350000, 'LSP004', 'Còn hàng');

INSERT INTO sanpham (ma_sp, ten_sp, ngay_them, mo_ta, thuong_hieu, dvt, mau_sac, kich_thuoc, so_luong, gia, ma_lsp, TinhTrang)
VALUES ('SP003', 'Quần jeans nam', TO_DATE('2023-05-27', 'YYYY-MM-DD'), 'Quần jeans nam phong cách cá nhân', 'Nhãn hiệu C', 'Cái', 'Xanh', 'XL', 3, 450000, 'LSP003', 'Còn hàng');

INSERT INTO sanpham (ma_sp, ten_sp, ngay_them, mo_ta, thuong_hieu, dvt, mau_sac, kich_thuoc, so_luong, gia, ma_lsp, TinhTrang)
VALUES ('SP004', 'Áo khoác nữ', TO_DATE('2023-05-27', 'YYYY-MM-DD'), 'Áo khoác nữ dành cho mùa đông', 'Nhãn hiệu D', 'Cái', 'Đen', 'S', 0, 650000, 'LSP002', 'Hết hàng');

INSERT INTO sanpham (ma_sp, ten_sp, ngay_them, mo_ta, thuong_hieu, dvt, mau_sac, kich_thuoc, so_luong, gia, ma_lsp, TinhTrang)
VALUES ('SP005', 'Áo khoác nam', TO_DATE('2023-05-27', 'YYYY-MM-DD'), 'Áo khoác nam thời trang', 'Nhãn hiệu E', 'Cái', 'Đen', 'M', 8, 550000, 'LSP002', 'Còn hàng');

INSERT INTO sanpham (ma_sp, ten_sp, ngay_them, mo_ta, thuong_hieu, dvt, mau_sac, kich_thuoc, so_luong, gia, ma_lsp, TinhTrang)
VALUES ('SP006', 'Vest nữ', TO_DATE('2023-05-27', 'YYYY-MM-DD'), 'Vest nữ sang trọng', 'Nhãn hiệu F', 'Bộ', 'Đỏ', 'L', 2, 850000, 'LSP005', 'Còn hàng');

INSERT INTO sanpham (ma_sp, ten_sp, ngay_them, mo_ta, thuong_hieu, dvt, mau_sac, kich_thuoc, so_luong, gia, ma_lsp, TinhTrang)
VALUES ('SP007', 'Quần jogger nam', TO_DATE('2023-05-27', 'YYYY-MM-DD'), 'Quần jogger nam thoải mái', 'Nhãn hiệu G', 'Cái', 'Xanh', 'XL', 6, 380000, 'LSP003', 'Còn hàng');

INSERT INTO sanpham (ma_sp, ten_sp, ngay_them, mo_ta, thuong_hieu, dvt, mau_sac, kich_thuoc, so_luong, gia, ma_lsp, TinhTrang)
VALUES ('SP008', 'Áo len nữ', TO_DATE('2023-05-27', 'YYYY-MM-DD'), 'Áo len nữ ấm áp', 'Nhãn hiệu H', 'Cái', 'Trắng', 'S', 4, 280000, 'LSP001', 'Còn hàng');

INSERT INTO sanpham (ma_sp, ten_sp, ngay_them, mo_ta, thuong_hieu, dvt, mau_sac, kich_thuoc, so_luong, gia, ma_lsp, TinhTrang)
VALUES ('SP009', 'Áo khoác bomber nam', TO_DATE('2023-05-27', 'YYYY-MM-DD'), 'Áo khoác bomber nam phong cách', 'Nhãn hiệu I', 'Cái', 'Đen', 'L', 12, 680000, 'LSP002', 'Còn hàng');

INSERT INTO sanpham (ma_sp, ten_sp, ngay_them, mo_ta, thuong_hieu, dvt, mau_sac, kich_thuoc, so_luong, gia, ma_lsp, TinhTrang)
VALUES ('SP010', 'Váy maxi nữ', TO_DATE('2023-05-27', 'YYYY-MM-DD'), 'Váy maxi nữ tinh tế', 'Nhãn hiệu J', 'Cái', 'Xanh', 'M', 3, 450000, 'LSP004', 'Còn hàng');

INSERT INTO sanpham (ma_sp, ten_sp, ngay_them, mo_ta, thuong_hieu, dvt, mau_sac, kich_thuoc, so_luong, gia, ma_lsp, TinhTrang)
VALUES ('SP011', 'Quần kaki nam', TO_DATE('2023-05-27', 'YYYY-MM-DD'), 'Quần kaki nam phong cách', 'Nhãn hiệu K', 'Cái', 'Nâu', 'XL', 10, 420000, 'LSP003', 'Còn hàng');

INSERT INTO sanpham (ma_sp, ten_sp, ngay_them, mo_ta, thuong_hieu, dvt, mau_sac, kich_thuoc, so_luong, gia, ma_lsp, TinhTrang)
VALUES ('SP012', 'Áo phông crop top nữ', TO_DATE('2023-05-27', 'YYYY-MM-DD'), 'Áo phông crop top nữ trẻ trung', 'Nhãn hiệu L', 'Cái', 'Trắng', 'S', 6, 180000, 'LSP001', 'Còn hàng');

INSERT INTO sanpham (ma_sp, ten_sp, ngay_them, mo_ta, thuong_hieu, dvt, mau_sac, kich_thuoc, so_luong, gia, ma_lsp, TinhTrang)
VALUES ('SP013', 'Áo hoodie nam', TO_DATE('2023-05-27', 'YYYY-MM-DD'), 'Áo hoodie nam phong cách', 'Nhãn hiệu M', 'Cái', 'Xanh', 'L', 8, 380000, 'LSP002', 'Còn hàng');

INSERT INTO sanpham (ma_sp, ten_sp, ngay_them, mo_ta, thuong_hieu, dvt, mau_sac, kich_thuoc, so_luong, gia, ma_lsp, TinhTrang)
VALUES ('SP014', 'Đầm ren nữ', TO_DATE('2023-05-27', 'YYYY-MM-DD'), 'Đầm ren nữ tinh tế', 'Nhãn hiệu N', 'Chiếc', 'Trắng', 'M', 4, 550000, 'LSP004', 'Còn hàng');

INSERT INTO sanpham (ma_sp, ten_sp, ngay_them, mo_ta, thuong_hieu, dvt, mau_sac, kich_thuoc, so_luong, gia, ma_lsp, TinhTrang)
VALUES ('SP015', 'Quần shorts nam', TO_DATE('2023-05-27', 'YYYY-MM-DD'), 'Quần shorts nam thoải mái', 'Nhãn hiệu O', 'Cái', 'Đen', 'L', 6, 280000, 'LSP003', 'Còn hàng');

INSERT INTO sanpham (ma_sp, ten_sp, ngay_them, mo_ta, thuong_hieu, dvt, mau_sac, kich_thuoc, so_luong, gia, ma_lsp, TinhTrang)
VALUES ('SP016', 'Vest blazer nữ', TO_DATE('2023-05-27', 'YYYY-MM-DD'), 'Vest blazer nữ sang trọng', 'Nhãn hiệu P', 'Bộ', 'Đỏ', 'XL', 2, 980000, 'LSP005', 'Còn hàng');

INSERT INTO sanpham (ma_sp, ten_sp, ngay_them, mo_ta, thuong_hieu, dvt, mau_sac, kich_thuoc, so_luong, gia, ma_lsp, TinhTrang)
VALUES ('SP017', 'Áo khoác denim nam', TO_DATE('2023-05-27', 'YYYY-MM-DD'), 'Áo khoác denim nam phong cách', 'Nhãn hiệu Q', 'Cái', 'Xanh', 'L', 5, 550000, 'LSP002', 'Còn hàng');

INSERT INTO sanpham (ma_sp, ten_sp, ngay_them, mo_ta, thuong_hieu, dvt, mau_sac, kich_thuoc, so_luong, gia, ma_lsp, TinhTrang)
VALUES ('SP018', 'Áo blouse nữ', TO_DATE('2023-05-27', 'YYYY-MM-DD'), 'Áo blouse nữ thanh lịch', 'Nhãn hiệu R', 'Cái', 'Trắng', 'S', 7, 320000, 'LSP002', 'Còn hàng');

INSERT INTO sanpham (ma_sp, ten_sp, ngay_them, mo_ta, thuong_hieu, dvt, mau_sac, kich_thuoc, so_luong, gia, ma_lsp, TinhTrang)
VALUES ('SP019', 'Quần tây nam', TO_DATE('2023-05-27', 'YYYY-MM-DD'), 'Quần tây nam phong cách', 'Nhãn hiệu S', 'Cái', 'Nâu', 'M', 9, 380000, 'LSP003', 'Còn hàng');

INSERT INTO sanpham (ma_sp, ten_sp, ngay_them, mo_ta, thuong_hieu, dvt, mau_sac, kich_thuoc, so_luong, gia, ma_lsp, TinhTrang)
VALUES ('SP020', 'Váy xòe nữ', TO_DATE('2023-05-27', 'YYYY-MM-DD'), 'Váy xòe nữ năng động', 'Nhãn hiệu T', 'Cái', 'Đỏ', 'L', 3, 420000, 'LSP004', 'Còn hàng');

commit;

INSERT INTO loaikhachhang (ma_lkh, ten_lkh, mo_ta, slkh, ghi_chu)
VALUES ('LKH001', 'VIP', 'Khách hàng VIP', '3', 'Khách hàng đặc biệt');

INSERT INTO loaikhachhang (ma_lkh, ten_lkh, mo_ta, slkh, ghi_chu)
VALUES ('LKH002', 'Thành viên', 'Khách hàng thành viên', '8', 'Khách hàng đã đăng ký thành viên');

commit;

INSERT INTO khachhang (ma_kh, ten_kh, gioi_tinh, dia_chi, sdt, ghi_chu, ma_lkh)
VALUES ('KH001', 'Quãng Đại Thi', 'Nam', 'phường Linh Trung, Thủ Đức, TP. Hồ Chí Minh', '0123456789', 'Khách hàng VIP', 'LKH001');

INSERT INTO khachhang (ma_kh, ten_kh, gioi_tinh, dia_chi, sdt, ghi_chu, ma_lkh)
VALUES ('KH002', 'Nguyễn Thanh Hằng', 'Nữ', '69 đường Võ Văn Kiệt, Quận 1, TP. Hồ Chí Minh', '0123456789', 'Khách hàng VIP', 'LKH001');

-- Khách hàng VIP 2
INSERT INTO khachhang (ma_kh, ten_kh, gioi_tinh, dia_chi, sdt, ghi_chu, ma_lkh)
VALUES ('KH003', 'Trần Minh Quang', 'Nam', '45 đường Lê Lợi, Quận 1, TP. Hồ Chí Minh', '0987654321', 'Khách hàng VIP', 'LKH001');

-- Khách hàng thành viên 1
INSERT INTO khachhang (ma_kh, ten_kh, gioi_tinh, dia_chi, sdt, ghi_chu, ma_lkh)
VALUES ('KH004', 'Nguyễn Thị Mai', 'Nữ', '12 đường Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', '0123456789', 'Khách hàng thành viên', 'LKH002');

-- Khách hàng thành viên 2
INSERT INTO khachhang (ma_kh, ten_kh, gioi_tinh, dia_chi, sdt, ghi_chu, ma_lkh)
VALUES ('KH005', 'Lê Văn Tùng', 'Nam', '78 đường Lý Tự Trọng, Quận 1, TP. Hồ Chí Minh', '0987654321', 'Khách hàng thành viên', 'LKH002');

-- Khách hàng thành viên 3
INSERT INTO khachhang (ma_kh, ten_kh, gioi_tinh, dia_chi, sdt, ghi_chu, ma_lkh)
VALUES ('KH006', 'Nguyễn Thị Ngọc Anh', 'Nữ', '56 đường Nguyễn Thị Minh Khai, Quận 3, TP. Hồ Chí Minh', '0123456789', 'Khách hàng thành viên', 'LKH002');

-- Khách hàng thành viên 4
INSERT INTO khachhang (ma_kh, ten_kh, gioi_tinh, dia_chi, sdt, ghi_chu, ma_lkh)
VALUES ('KH007', 'Phạm Văn Trung', 'Nam', '23 đường Trần Hưng Đạo, Quận 1, TP. Hồ Chí Minh', '0987654321', 'Khách hàng thành viên', 'LKH002');

-- Khách hàng thành viên 5
INSERT INTO khachhang (ma_kh, ten_kh, gioi_tinh, dia_chi, sdt, ghi_chu, ma_lkh)
VALUES ('KH008', 'Trần Thị Hồng', 'Nữ', '34 đường Hồ Xuân Hương, Quận 3, TP. Hồ Chí Minh', '0123456789', 'Khách hàng thành viên', 'LKH002');

-- Khách hàng thành viên 6
INSERT INTO khachhang (ma_kh, ten_kh, gioi_tinh, dia_chi, sdt, ghi_chu, ma_lkh)
VALUES ('KH009', 'Nguyễn Đình Phúc', 'Nam', '89 đường Nam Kỳ Khởi Nghĩa, Quận 1, TP. Hồ Chí Minh', '0987654321', 'Khách hàng thành viên', 'LKH002');

-- Khách hàng thành viên 7
INSERT INTO khachhang (ma_kh, ten_kh, gioi_tinh, dia_chi, sdt, ghi_chu, ma_lkh)
VALUES ('KH010', 'Lê Thị Thu', 'Nữ', '47 đường Hai Bà Trưng, Quận 1, TP. Hồ Chí Minh', '0123456789', 'Khách hàng thành viên', 'LKH002');

-- Khách hàng thành viên 8
INSERT INTO khachhang (ma_kh, ten_kh, gioi_tinh, dia_chi, sdt, ghi_chu, ma_lkh)
VALUES ('KH011', 'Nguyễn Hoàng Anh', 'Nam', '51 đường Đồng Khởi, Quận 1, TP. Hồ Chí Minh', '0987654321', 'Khách hàng thành viên', 'LKH002');

commit;


-- Hóa đơn 1
INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, ma_nv, ma_kh, trang_thai)
VALUES ('HD001', TO_DATE('2023-06-01', 'YYYY-MM-DD'), 5, 10, 1395000, 'NV003', 'KH001', 'Đã thanh toán');

INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD001', 'SP001', 2, 250000, 10, 450000);

INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD001', 'SP002', 3, 350000, 10 , 945000);

INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, ma_nv, ma_kh, trang_thai)
VALUES ('HD002', TO_DATE('2022-02-15', 'YYYY-MM-DD'), 3, 10, 1810000, 'NV003', 'KH002', 'Đã thanh toán');
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD002', 'SP003', 1, 450000, 10, 450000);
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD002', 'SP009', 2, 680000, 10, 1360000);


INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, ma_nv, ma_kh, trang_thai)
VALUES ('HD003', TO_DATE('2022-03-10', 'YYYY-MM-DD'), 2, 0, 500000, 'NV002', null, 'Đã thanh toán');
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD003', 'SP001', 2, 250000, 0, 500000);



INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, ma_nv, ma_kh, trang_thai)
VALUES ('HD004', TO_DATE('2022-04-20', 'YYYY-MM-DD'), 1, 5, 550000, 'NV003', 'KH004', 'Đã thanh toán');
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD004', 'SP005', 1, 550000, 5, 550000);


INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, ma_nv, ma_kh, trang_thai)
VALUES ('HD005', TO_DATE('2022-05-05', 'YYYY-MM-DD'), 4, 5, 2300000, 'NV002', 'KH005', 'Đã thanh toán');
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD005', 'SP010', 1, 450000, 5, 450000);
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD005', 'SP017', 1, 550000, 5, 550000);
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD005', 'SP004', 2, 650000, 5, 1300000);


INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, ma_nv, ma_kh, trang_thai)
VALUES ('HD006', TO_DATE('2022-06-12', 'YYYY-MM-DD'), 2, 5, 630000, 'NV003', 'KH006', 'Đã thanh toán');
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD006', 'SP007', 1, 380000, 5, 380000);
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD006', 'SP001', 1, 250000, 5, 250000);


INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, ma_nv, ma_kh, trang_thai)
VALUES ('HD007', TO_DATE('2022-07-03', 'YYYY-MM-DD'), 3, 0, 1120000, 'NV002', null, 'Đã thanh toán');
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD007', 'SP018', 1, 250000, 0, 320000);
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD007', 'SP019', 1, 250000, 0, 380000);
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD007', 'SP020', 1, 250000, 0, 420000);

INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, ma_nv, ma_kh, trang_thai)
VALUES ('HD008', TO_DATE('2023-01-18', 'YYYY-MM-DD'), 2, 5, 1960000, 'NV003', 'KH008', 'Đã thanh toán');
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD008', 'SP016', 2, 980000, 5, 1960000);


INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, ma_nv, ma_kh, trang_thai)
VALUES ('HD009', TO_DATE('2023-02-25', 'YYYY-MM-DD'), 1, 5, 550000, 'NV002', 'KH009', 'Đã thanh toán');
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD009', 'SP014', 1, 550000, 5, 550000);


INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, ma_nv, ma_kh, trang_thai)
VALUES ('HD010', TO_DATE('2023-03-10', 'YYYY-MM-DD'), 3, 5, 1290000, 'NV003', 'KH010', 'Đã thanh toán');
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD010', 'SP011', 2, 420000, 5, 840000);
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD010', 'SP003', 1, 450000, 5, 450000);


INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, ma_nv, ma_kh, trang_thai)
VALUES ('HD011', TO_DATE('2022-06-20', 'YYYY-MM-DD'), 2, 5, 500000, 'NV002', 'KH005', 'Đã thanh toán');
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD011', 'SP018', 1, 320000, 5, 320000);
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD011', 'SP012', 1, 180000, 5, 180000);


INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, ma_nv, ma_kh, trang_thai)
VALUES ('HD012', TO_DATE('2022-04-10', 'YYYY-MM-DD'), 3, 10, 1140000, 'NV003', 'KH002', 'Đã thanh toán');
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD012', 'SP019', 2, 380000, 10, 760000);
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD012', 'SP013', 1, 380000, 10, 380000);



INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, ma_nv, ma_kh, trang_thai)
VALUES ('HD013', TO_DATE('2023-02-15', 'YYYY-MM-DD'), 1, 0, 380000, 'NV003', null, 'Đã thanh toán');
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD013', 'SP013', 1, 380000, 0, 380000);



INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, ma_nv, ma_kh, trang_thai)
VALUES ('HD014', TO_DATE('2023-01-01', 'YYYY-MM-DD'), 5, 10, 2140000, 'NV002', 'KH001', 'Đã thanh toán');
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD014', 'SP013', 1, 380000, 10, 380000);
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD014', 'SP014', 1, 550000, 10, 550000);
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD014', 'SP015', 1, 280000, 10, 280000);
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD014', 'SP017', 1, 550000, 10, 550000);
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD014', 'SP019', 1, 380000, 10, 380000);


INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, ma_nv, ma_kh, trang_thai)
VALUES ('HD015', TO_DATE('2022-08-05', 'YYYY-MM-DD'), 4, 10, 1600000, 'NV002', 'KH003', 'Đã thanh toán');
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD015', 'SP001', 2, 250000, 10, 500000);
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD015', 'SP005', 2, 550000, 10, 1100000);


INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, ma_nv, ma_kh, trang_thai)
VALUES ('HD016', TO_DATE('2022-09-12', 'YYYY-MM-DD'), 2, 5, 760000, 'NV003', 'KH006', 'Đã thanh toán');
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD016', 'SP007', 2, 380000, 5, 760000);


INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, ma_nv, ma_kh, trang_thai)
VALUES ('HD017', TO_DATE('2022-07-03', 'YYYY-MM-DD'), 3, 0, 2040000, 'NV002', null, 'Đã thanh toán');
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD017', 'SP009', 3, 680000, 0, 2040000);


INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, ma_nv, ma_kh, trang_thai)
VALUES ('HD018', TO_DATE('2023-01-18', 'YYYY-MM-DD'), 2, 5, 840000, 'NV003', 'KH008', 'Đã thanh toán');
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD018', 'SP011', 2, 420000, 5, 840000);



INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, ma_nv, ma_kh, trang_thai)
VALUES ('HD019', TO_DATE('2023-02-25', 'YYYY-MM-DD'), 1, 0, 180000, 'NV002', null, 'Đã thanh toán');
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD019', 'SP012', 1, 180000, 0, 180000);


INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, ma_nv, ma_kh, trang_thai)
VALUES ('HD020', TO_DATE('2023-03-10', 'YYYY-MM-DD'), 3, 5, 940000, 'NV003', 'KH010', 'Đã thanh toán');
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD020', 'SP013', 1, 380000, 5, 380000);
INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD020', 'SP015', 2, 280000, 5, 560000);



-- Hóa đơn 2
INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, ma_nv, ma_kh, trang_thai)
VALUES ('HD021', TO_DATE('2022-09-02', 'YYYY-MM-DD'), 3, 5, 969000, 'NV002', 'KH005', 'Đã thanh toán');

INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD021', 'SP015', 1, 280000, 5, 266000);

INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD021', 'SP018', 1, 320000, 5, 304000);

INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD021', 'SP020', 1, 420000, 5, 399000);

-- Hóa đơn 3
INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, ma_nv, ma_kh, trang_thai)
VALUES ('HD022', TO_DATE('2022-12-03', 'YYYY-MM-DD'), 5, 0, 2590000, 'NV002', 'KH009', 'Đã thanh toán');

INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD022', 'SP019', 2, 380000, 0, 760000);

INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD022', 'SP006', 1, 850000, 0, 850000);

INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD022', 'SP020', 1, 420000, 0, 420000);

INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD022', 'SP004', 1, 650000, 0, 650000);

-- Hóa đơn 4
INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, ma_nv, ma_kh, trang_thai)
VALUES ('HD023', TO_DATE('2023-04-04', 'YYYY-MM-DD'), 3, 0, 1950000, 'NV003', null, 'Đã thanh toán');

INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD023', 'SP005', 2, 550000, 0, 1100000);

INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD023', 'SP006', 1, 85000, 0, 850000);

INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD023', 'SP007', 1, 1, 0, 850000);

INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD023', 'SP008', 2, 2, 2, 0);



INSERT INTO hoadon (so_hd, ngay_lap, slmh, khuyen_mai, tong_tien, ma_nv, ma_kh, trang_thai)
VALUES ('HD024', TO_DATE('2023-05-04', 'YYYY-MM-DD'), 3, 0, 1950000, 'NV003', null, 'Đã thanh toán');

INSERT INTO cthd (so_hd, ma_sp, so_luong, gia, khuyen_mai, thanh_tien)
VALUES ('HD024', 'SP005', 2, 550000, 0, 1100000);

commit;

/

------------------------------------------TRIGGER-------------------------
CREATE OR REPLACE TRIGGER cthd_update_gia_khuyen_mai
BEFORE INSERT OR UPDATE OF ma_sp ON cthd
FOR EACH ROW
DECLARE
  v_khuyen_mai hoadon.khuyen_mai%TYPE;
BEGIN
  -- Update the "gia" column based on the corresponding value from the "sanpham" table
  SELECT gia INTO :NEW.gia FROM sanpham WHERE ma_sp = :NEW.ma_sp;

  -- Retrieve the "khuyen_mai" value from the corresponding "hoadon" row based on "so_hd"
  SELECT khuyen_mai INTO v_khuyen_mai FROM hoadon WHERE so_hd = :NEW.so_hd;

  -- Assign the retrieved "khuyen_mai" value to the ":NEW.khuyen_mai" column in "cthd"
  :NEW.khuyen_mai := v_khuyen_mai;

  -- Calculate the "thanh_tien" column using the provided formula
  :NEW.thanh_tien := (:NEW.gia * :NEW.so_luong) * ((100 - :NEW.khuyen_mai) / 100);
END;
/



CREATE OR REPLACE TRIGGER hoadon_set_khuyen_mai
BEFORE INSERT ON hoadon
FOR EACH ROW
DECLARE
  v_loai_kh khachhang.ma_lkh%TYPE;
BEGIN
  IF :NEW.ma_kh IS NOT NULL THEN
    SELECT ma_lkh INTO v_loai_kh FROM khachhang WHERE ma_kh = :NEW.ma_kh;
    
    IF v_loai_kh = 'KH001' THEN
      :NEW.khuyen_mai := 10; -- Set khuyen_mai to 10 if loai_kh is 'KH001'
    ELSIF v_loai_kh = 'KH002' THEN
      :NEW.khuyen_mai := 5; -- Set khuyen_mai to 5 if loai_kh is 'KH002'
    ELSE
      :NEW.khuyen_mai := 0; -- Set khuyen_mai to 0 if ma_kh is null or doesn't match any condition
    END IF;
  ELSE
    :NEW.khuyen_mai := 0; -- Set khuyen_mai to 0 if ma_kh is null
  END IF;
END;
/




CREATE OR REPLACE TRIGGER calculate_slmh
FOR INSERT OR UPDATE OR DELETE ON cthd
COMPOUND TRIGGER

  -- Collection to hold the affected "so_hd" values
  TYPE hd_list IS TABLE OF hoadon.so_hd%TYPE;
  v_hd_list hd_list := hd_list();

  -- Before Statement section
  BEFORE STATEMENT IS
  BEGIN
    -- Clear the collection before each statement
    v_hd_list.DELETE;
  END BEFORE STATEMENT;

  -- Before Each Row section
  BEFORE EACH ROW IS
  BEGIN
    -- Store the affected "so_hd" value in the collection
    IF INSERTING OR UPDATING THEN
      v_hd_list.EXTEND;
      v_hd_list(v_hd_list.LAST) := :NEW.so_hd;
    ELSE
      v_hd_list.EXTEND;
      v_hd_list(v_hd_list.LAST) := :OLD.so_hd;
    END IF;
  END BEFORE EACH ROW;

  -- After Statement section
  AFTER STATEMENT IS
  BEGIN
    -- Update "slmh" and calculate "sum(thanh_tien)" in "hoadon" table for the affected "so_hd" values
    FOR i IN 1..v_hd_list.COUNT LOOP
      UPDATE hoadon
      SET slmh = (
        SELECT NVL(SUM(so_luong), 0)
        FROM cthd
        WHERE so_hd = v_hd_list(i)
      ),
      tong_tien = (
        SELECT NVL(SUM(thanh_tien), 0)
        FROM cthd
        WHERE so_hd = v_hd_list(i)
      )
      WHERE so_hd = v_hd_list(i);
    END LOOP;
  END AFTER STATEMENT;

END calculate_slmh;
/

--Trigger và Function nhanvien
create or replace FUNCTION func_nhanvien_get_id  return nhanvien.ma_nv%type
as
        cur int;
        pre int := 0;
begin
        for c in (select * from nhanvien order by ma_nv asc) loop
                cur := substr(c.ma_nv,3);
                if cur - pre > 1 then
                        exit;
                end if;
                pre := cur;
        end loop;
        return ('NV' || to_char(pre+1,'fm000'));
end;
/
create or replace trigger trg_nhanvien_id
before insert on NHANVIEN
for each row
declare
        manv nhanvien.ma_nv%type := func_nhanvien_get_id;
begin
        :new.ma_nv := manv;
end;
/

commit;