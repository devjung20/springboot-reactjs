CREATE TABLE Kho (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    MaKho VARCHAR(50) NOT NULL,
    Ten VARCHAR(100) NOT NULL,
    DiaDiem VARCHAR(100) NOT NULL,
    NgayTao DATETIME NOT NULL,
    NgaySua DATETIME NOT NULL
);

CREATE TABLE LoaiDanhMuc (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    MaLoaiDanhMuc VARCHAR(50) NOT NULL,
    Ten VARCHAR(100) NOT NULL,
    MoTa TEXT,
    NgayTao DATETIME NOT NULL,
    NgaySua DATETIME NOT NULL
);

CREATE TABLE SanPham (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    MaSanPham VARCHAR(50) NOT NULL,
    DanhMuc INT,
    Kho INT,
    Ten VARCHAR(100) NOT NULL,
    MoTaSanPham TEXT,
    DuongDanAnh VARCHAR(200),
    SoLuongSanPham INT,
    SoLuongBan INT,
    NgayTao DATETIME NOT NULL,
    NgaySua DATETIME NOT NULL,
    FOREIGN KEY (DanhMuc) REFERENCES LoaiDanhMuc(Id),
    FOREIGN KEY (Kho) REFERENCES Kho(Id)
);

INSERT INTO Kho (Id, MaKho, Ten, DiaDiem, NgayTao, NgaySua) 
VALUES (1,"K001","Kho 1","Hà Nam",NOW(),NOW());
INSERT INTO Kho (Id, MaKho, Ten, DiaDiem, NgayTao, NgaySua) 
VALUES (2,"K002","Kho 3","Hà Nam",NOW(),NOW());

INSERT INTO LoaiDanhMuc (Id, MaLoaiDanhMuc, Ten, MoTa, NgayTao, NgaySua) 
VALUES (1,"DM001","Danh mục 1","Mô tả 1",NOW(),NOW());
INSERT INTO LoaiDanhMuc (Id, MaLoaiDanhMuc, Ten, MoTa, NgayTao, NgaySua) 
VALUES (2,"DM002","Danh mục 2","Mô tả 2",NOW(),NOW());


INSERT INTO SanPham (Id, MaSanPham, DanhMuc, Kho, Ten, MoTaSanPham, DuongDanAnh, SoLuongSanPham, SoLuongBan, NgayTao, NgaySua)
VALUES (1, 'SP001', 1, 1, 'Sản phẩm 1', 'Mô tả sản phẩm 1', 'duongdananh1.jpg', 10, 5, NOW(), NOW());
INSERT INTO SanPham (Id, MaSanPham, DanhMuc, Kho, Ten, MoTaSanPham, DuongDanAnh, SoLuongSanPham, SoLuongBan, NgayTao, NgaySua)
VALUES (2, 'SP002', 2, 2, 'Sản phẩm 2', 'Mô tả sản phẩm 2', 'duongdananh2.jpg', 20, 10, NOW(), NOW());

UPDATE Kho SET Ten = "Kho 2", NgaySua = NOW() WHERE Id = 1;
UPDATE LoaiDanhMuc SET Ten = "Danh mục 2", NgaySua = NOW() WHERE Id = 1;
UPDATE SanPham SET Ten = "Sản phẩm 2", NgaySua = NOW() WHERE Id = 1;

DELETE FROM Kho WHERE Id = 1;
DELETE FROM LoaiDanhMuc WHERE Id = 1;
DELETE FROM SanPham WHERE Id = 1;

SELECT * FROM SanPham WHERE Ten LIKE "%Điện thoại%" AND DanhMuc IN (
	SELECT Id FROM LoaiDanhMuc WHERE Ten = "Apple"
);

SELECT LoaiDanhMuc.Ten, COUNT(SanPham.Id) AS SoLuongSanPham FROM LoaiDanhMuc
LEFT JOIN SanPham ON LoaiDanhMuc.Id = SanPham.DanhMuc
GROUP BY LoaiDanhMuc.Id, LoaiDanhMuc.Ten
ORDER BY SoLuongSanPham DESC;

START TRANSACTION;
DELETE FROM SanPham WHERE DanhMuc IN (
	SELECT Id FROM LoaiDanhMuc WHERE Ten = "Danh mục 1"
);
DELETE FROM LoaiDanhMuc WHERE Ten = "Danh mục 1";
COMMIT;

DELIMITER //
CREATE PROCEDURE GetTopProducts ()
BEGIN
SELECT * FROM SanPham ORDER BY SoLuongBan DESC 
	LIMIT 10;
END//
DELIMITER;

CALL GetTopProducts();
