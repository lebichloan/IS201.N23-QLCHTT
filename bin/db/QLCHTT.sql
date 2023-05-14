CREATE TABLE sanpham (
    maSP VARCHAR2(50) PRIMARY KEY,
    tenSP VARCHAR2(100) NOT NULL,
    mauSac VARCHAR2(50),
    kichThuoc VARCHAR2(50),
    tinhTrang VARCHAR2(50),
    maLSP VARCHAR2(50) NOT NULL
);


INSERT INTO sanpham (maSP, tenSP, mauSac, kichThuoc, tinhTrang, maLSP) VALUES
('SP001', 'Áo s? mi nam', 'Tr?ng', 'M', 'Còn hàng', 'LSP001');

INSERT INTO sanpham (maSP, tenSP, mauSac, kichThuoc, tinhTrang, maLSP) VALUES
('SP002', 'Áo thun nam', '?en', 'S', 'Còn hàng', 'LSP001');

INSERT INTO sanpham (maSP, tenSP, mauSac, kichThuoc, tinhTrang, maLSP) VALUES
('SP003', 'Qu?n jean nam', 'Xanh', '32', 'H?t hàng', 'LSP001');

INSERT INTO sanpham (maSP, tenSP, mauSac, kichThuoc, tinhTrang, maLSP) VALUES
('SP004', 'Áo s? mi n?', 'Xanh', 'M', 'Còn hàng', 'LSP002');

INSERT INTO sanpham (maSP, tenSP, mauSac, kichThuoc, tinhTrang, maLSP) VALUES
('SP005', 'Áo thun n?', 'H?ng', 'L', 'Còn hàng', 'LSP002');

INSERT INTO sanpham (maSP, tenSP, mauSac, kichThuoc, tinhTrang, maLSP) VALUES
('SP006', 'Váy n?', '??', 'M', 'H?t hàng', 'LSP002');

INSERT INTO sanpham (maSP, tenSP, mauSac, kichThuoc, tinhTrang, maLSP) VALUES
('SP007', 'Áo phông tr? em', 'Vàng', 'XL', 'Còn hàng', 'LSP003');

INSERT INTO sanpham (maSP, tenSP, mauSac, kichThuoc, tinhTrang, maLSP) VALUES
('SP008', 'Qu?n short tr? em', 'Xanh', '28', 'Còn hàng', 'LSP003');

INSERT INTO sanpham (maSP, tenSP, mauSac, kichThuoc, tinhTrang, maLSP) VALUES
('SP009', 'Váy cho bé gái', 'H?ng', '2', 'H?t hàng', 'LSP003');

INSERT INTO sanpham (maSP, tenSP, mauSac, kichThuoc, tinhTrang, maLSP) VALUES
('SP010', 'Áo khoác nam', '?en', 'M', 'Còn hàng', 'LSP001');

INSERT INTO sanpham (maSP, tenSP, mauSac, kichThuoc, tinhTrang, maLSP) VALUES
('SP011', 'Qu?n kaki nam', 'Beige', '30', 'Còn hàng', 'LSP001');

INSERT INTO sanpham (maSP, tenSP, mauSac, kichThuoc, tinhTrang, maLSP) VALUES
('SP012', 'Áo len nam', 'Xanh lá', 'L', 'H?t hàng', 'LSP001');

INSERT INTO sanpham (maSP, tenSP, mauSac, kichThuoc, tinhTrang, maLSP) VALUES
('SP013', 'Áo dài n?', 'H?ng', 'S', 'Còn hàng', 'LSP002');

INSERT INTO sanpham (maSP, tenSP, mauSac, kichThuoc, tinhTrang, maLSP) VALUES
('SP014', 'Qu?n legging n?', '?en', 'M', 'Còn hàng', 'LSP002');

INSERT INTO sanpham (maSP, tenSP, mauSac, kichThuoc, tinhTrang, maLSP) VALUES
('SP015', '??m n?', 'Xanh d??ng', 'L', 'H?t hàng', 'LSP002');

INSERT INTO sanpham (maSP, tenSP, mauSac, kichThuoc, tinhTrang, maLSP) VALUES
('SP016', 'Áo khoác tr? em', '??', 'XL', 'Còn hàng', 'LSP003');

INSERT INTO sanpham (maSP, tenSP, mauSac, kichThuoc, tinhTrang, maLSP) VALUES
('SP017', 'Qu?n jean tr? em', 'Xám', '24', 'Còn hàng', 'LSP003');

INSERT INTO sanpham (maSP, tenSP, mauSac, kichThuoc, tinhTrang, maLSP) VALUES
('SP018', 'Áo thun cho bé gái', 'H?ng', '6', 'H?t hàng', 'LSP003');

select * from sanpham;
