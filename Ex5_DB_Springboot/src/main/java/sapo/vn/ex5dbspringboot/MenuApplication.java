// MenuApplication.java
package sapo.vn.ex5dbspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sapo.vn.ex5dbspringboot.entity.Kho;
import sapo.vn.ex5dbspringboot.entity.LoaiDanhMuc;
import sapo.vn.ex5dbspringboot.entity.SanPham;
import sapo.vn.ex5dbspringboot.service.jdbc.KhoService;
import sapo.vn.ex5dbspringboot.service.jdbc.LoaiDanhMucService;
import sapo.vn.ex5dbspringboot.service.jdbc.SanPhamService;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Component
public class MenuApplication implements CommandLineRunner {
    @Autowired
    private KhoService khoService;
    @Autowired
    private LoaiDanhMucService loaiDanhMucService;
    @Autowired
    private SanPhamService sanPhamService;

    @Override
    public void run(String... args) {
        Scanner sc = new Scanner(System.in);
        int luaChon;
        do {
            System.out.println("=============MENU===========");
            System.out.println("Nhập lựa chọn:");
            if (sc.hasNextInt()) {
                luaChon = sc.nextInt();
                sc.nextLine();
                System.out.println("1. Hiển thị danh sách kho");
                System.out.println("2. Hiển thị danh sách loại danh mục");
                System.out.println("3. Hiển thị danh sách sản phẩm");
                System.out.println("4. Thêm sản phẩm");
                System.out.println("5. Hiển thị danh sản phẩm(stored procedure)");
                System.out.println("6. Xóa sản phẩm");
                System.out.println("7. Cập nhật sản phẩm");
                System.out.println("0. Thoát khỏi chương trình!");

                switch (luaChon) {
                    case 1:
                        displayAllKho();
                        break;
                    case 2:
                        displayAllLoaiDanhMuc();
                        break;
                    case 3:
                        displayAllSanPham();
                        break;
                    case 4:
                        addSanPham();
                        break;
                    case 5:
                        disPlayAllSanPhamStoredProcedure();
                        break;
                    case 6:
                        deleteSanPham();
                        break;
                    case 7:
                        updateSanPham();
                        break;
                    default:
                        System.out.println("Lựa chọn này không tồn tại, xin vui lòng chọn lại!");
                        break;
                }
            } else {
                System.out.println("Lựa chọn không hợp lệ! Vui lòng nhập lại.");
                sc.nextLine();
                luaChon = -1; // Gán giá trị sai để tiếp tục vòng lặp
            }
        } while (luaChon != 0);
    }

    private void disPlayAllSanPhamStoredProcedure() {
        try {
            List<SanPham> sanPhamList = sanPhamService.getAllSanPhamStoredProcedure();
            System.out.println("===== DANH SÁCH SP =====");
            for (SanPham sp : sanPhamList) {
                System.out.println("ID: " + sp.getId());
                System.out.println("Mã sản phẩm: " + sp.getMaSanPham());
                if (sp.getDanhMuc() != null) {
                    System.out.println("Danh mục: " + sp.getDanhMuc().getId());
                }
                if (sp.getKho() != null) {
                    System.out.println("Kho: " + sp.getKho().getId());
                }
                System.out.println("Tên: " + sp.getTen());
                System.out.println("Giá: " + sp.getGia());
                System.out.println("Mô tả sản phẩm: " + sp.getMoTaSanPham());
                System.out.println("Đường dẫn ảnh: " + sp.getDuongDanAnh());
                System.out.println("Số lượng sản phẩm: " + sp.getSoLuongSanPham());
                System.out.println("Số lượng bán: " + sp.getSoLuongBan());
                System.out.println("Ngày tạo: " + sp.getNgayTao());
                System.out.println("Ngày sửa: " + sp.getNgaySua());
                System.out.println("------------------------------------------");
            }
        } catch (Exception e) {
            System.out.println("Đã xảy ra lỗi khi hiển thị danh sách sản phẩm: " + e.getMessage());
        }
    }

    private void displayAllKho() {
        try {
            List<Kho> khoList = khoService.getAllKho();
            System.out.println("===== DANH SÁCH KHO =====");
            for (Kho kho : khoList) {
                System.out.println("ID: " + kho.getId());
                System.out.println("Mã Kho: " + kho.getMaKho());
                System.out.println("Tên: " + kho.getTen());
                System.out.println("Địa điểm: " + kho.getDiaDiem());
                System.out.println("Ngày tạo: " + kho.getNgayTao());
                System.out.println("Ngày sửa: " + kho.getNgaySua());
                System.out.println("----------------------");
            }
        } catch (Exception e) {
            System.out.println("Đã xảy ra lỗi khi hiển thị danh sách kho: " + e.getMessage());
        }
    }

    private void displayAllLoaiDanhMuc() {
        try {
            List<LoaiDanhMuc> loaiDanhMucList = loaiDanhMucService.getAllLoaiDanhMuc();
            System.out.println("===== DANH SÁCH LOẠI DANH MỤC ====");
            for (LoaiDanhMuc ldm : loaiDanhMucList) {
                System.out.println("ID: " + ldm.getId());
                System.out.println("Mã loại danh mục: " + ldm.getMaLoaiDanhMuc());
                System.out.println("Tên: " + ldm.getTen());
                System.out.println("Mô tả: " + ldm.getMoTa());
                System.out.println("Ngày tạo: " + ldm.getNgayTao());
                System.out.println("Ngày sửa: " + ldm.getNgaySua());
                System.out.println("-----------------------------");
            }
        } catch (Exception e) {
            System.out.println("Đã xảy ra lỗi khi hiển thị danh sách loại danh mục: " + e.getMessage());
        }
    }

    private void displayAllSanPham() {
        try {
            List<SanPham> sanPhamList = sanPhamService.getAllSanPham();
            System.out.println("======= DANH SÁCH SẢN PHẨM ========");
            for (SanPham sp : sanPhamList) {
                System.out.println("ID: " + sp.getId());
                System.out.println("Mã sản phẩm: " + sp.getMaSanPham());
                if (sp.getDanhMuc() != null) {
                    System.out.println("Danh mục: " + sp.getDanhMuc().getId());
                }
                if (sp.getKho() != null) {
                    System.out.println("Kho: " + sp.getKho().getId());
                }
                System.out.println("Tên: " + sp.getTen());
                System.out.println("Giá: " + sp.getGia());
                System.out.println("Mô tả sản phẩm: " + sp.getMoTaSanPham());
                System.out.println("Đường dẫn ảnh: " + sp.getDuongDanAnh());
                System.out.println("Số lượng sản phẩm: " + sp.getSoLuongSanPham());
                System.out.println("Số lượng bán: " + sp.getSoLuongBan());
                System.out.println("Ngày tạo: " + sp.getNgayTao());
                System.out.println("Ngày sửa: " + sp.getNgaySua());
                System.out.println("------------------------------------------");
            }
        } catch (Exception e) {
            System.out.println("Đã xảy ra lỗi khi hiển thị danh sách sản phẩm: " + e.getMessage());
        }
    }

    private void addSanPham() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Nhập mã sản phẩm:");
            String maSanPham = sc.nextLine();
            System.out.println("Nhập ID danh mục:");
            int danhMucId = sc.nextInt();
            System.out.println("Nhập ID kho:");
            int khoId = sc.nextInt();
            sc.nextLine();
            System.out.println("Nhập tên sản phẩm:");
            String tenSanPham = sc.nextLine();
            System.out.println("Nhập giá:");
            double gia = sc.nextDouble();
            sc.nextLine();
            System.out.println("Nhập mô tả sản phẩm:");
            String moTaSanPham = sc.nextLine();
            System.out.println("Nhập đường dẫn ảnh:");
            String duongDanAnh = sc.nextLine();
            System.out.println("Nhập số lượng sản phẩm:");
            int soLuongSanPham = sc.nextInt();
            System.out.println("Nhập số lượng bán:");
            int soLuongBan = sc.nextInt();

            SanPham sanPham = new SanPham();
            sanPham.setMaSanPham(maSanPham);
            LoaiDanhMuc loaiDanhMuc = loaiDanhMucService.getLoaiDanhMucById(danhMucId);
            if (loaiDanhMuc == null) {
                throw new Exception("Không tìm thấy danh mục có ID là: " + loaiDanhMuc);
            }
            sanPham.setDanhMuc(loaiDanhMuc);
            Kho kho = khoService.getByIdKho(khoId);
            if (kho == null) {
                throw new Exception("Không tìm thấy danh mục có ID là: " + kho);
            }
            sanPham.setKho(kho);

            sanPham.setTen(tenSanPham);
            sanPham.setGia(gia);
            sanPham.setMoTaSanPham(moTaSanPham);
            sanPham.setDuongDanAnh(duongDanAnh);
            sanPham.setSoLuongSanPham(soLuongSanPham);
            sanPham.setSoLuongBan(soLuongBan);
            Date currentDate = new Date();
            sanPham.setNgayTao(currentDate);
            sanPham.setNgaySua(currentDate);

            SanPham createdSanPham = sanPhamService.createSanPham(sanPham);
            if (createdSanPham != null) {
                System.out.println("Thêm sản phẩm thành công!");
            } else {
                System.out.println("Thêm sản phẩm thất bại!");
            }
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }


    private void deleteSanPham() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Nhập ID sản phẩm cần xóa:");
            int sanPhamId = sc.nextInt();

            sanPhamService.deleteSanPham(sanPhamId);
            System.out.println("Xóa sản phẩm thành công!");
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    private void updateSanPham() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Nhập ID sản phẩm cần cập nhật:");
            int sanPhamId = sc.nextInt();
            sc.nextLine();
            System.out.println("Nhập mã sản phẩm mới:");
            String maSanPham = sc.nextLine();
            System.out.println("Nhập ID danh mục mới:");
            int danhMucId = sc.nextInt();
            System.out.println("Nhập ID kho mới:");
            int khoId = sc.nextInt();
            sc.nextLine();
            System.out.println("Nhập tên sản phẩm mới:");
            String tenSanPham = sc.nextLine();
            System.out.println("Nhập giá mới:");
            double gia = sc.nextDouble();
            sc.nextLine();
            System.out.println("Nhập mô tả sản phẩm mới:");
            String moTaSanPham = sc.nextLine();
            System.out.println("Nhập đường dẫn ảnh mới:");
            String duongDanAnh = sc.nextLine();
            System.out.println("Nhập số lượng sản phẩm mới:");
            int soLuongSanPham = sc.nextInt();
            System.out.println("Nhập số lượng bán mới:");
            int soLuongBan = sc.nextInt();

            SanPham sanPham = sanPhamService.getByIdSanPham(sanPhamId);
            if (sanPham != null) {
                sanPham.setMaSanPham(maSanPham);
                LoaiDanhMuc loaiDanhMuc = loaiDanhMucService.getLoaiDanhMucById(danhMucId);
                if (loaiDanhMuc == null) {
                    throw new Exception("Không tìm thấy danh mục có ID là: " + loaiDanhMuc);
                }
                sanPham.setDanhMuc(loaiDanhMuc);
                Kho kho = khoService.getByIdKho(khoId);
                if (kho == null) {
                    throw new Exception("Không tìm thấy danh mục có ID là: " + kho);
                }
                sanPham.setKho(kho);
                sanPham.setTen(tenSanPham);
                sanPham.setGia(gia);
                sanPham.setMoTaSanPham(moTaSanPham);
                sanPham.setDuongDanAnh(duongDanAnh);
                sanPham.setSoLuongSanPham(soLuongSanPham);
                sanPham.setSoLuongBan(soLuongBan);
                Date currentDate = new Date();
                sanPham.setNgaySua(currentDate);

                SanPham updatedSanPham = sanPhamService.updateSanPham(sanPham, sanPhamId);
                if (updatedSanPham != null) {
                    System.out.println("Cập nhật sản phẩm thành công!");
                } else {
                    System.out.println("Cập nhật sản phẩm thất bại!");
                }
            } else {
                System.out.println("Không tìm thấy sản phẩm với ID đã nhập!");
            }
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

}
