package sapo.vn.ex5dbspringboot.service.jdbc;

import sapo.vn.ex5dbspringboot.entity.SanPham;

import javax.validation.Valid;
import java.util.List;

public interface SanPhamService {
    List<SanPham> getAllSanPham();

    SanPham getByIdSanPham(int id);

    SanPham createSanPham(@Valid SanPham sanPham);

    SanPham updateSanPham(@Valid SanPham sanPham, int id);

    void deleteSanPham(int id);

    List<SanPham> getAllSanPhamStoredProcedure();
}
