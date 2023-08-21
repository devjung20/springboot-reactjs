package sapo.vn.ex5dbspringboot.service.jdbc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import sapo.vn.ex5dbspringboot.entity.Kho;
import sapo.vn.ex5dbspringboot.entity.LoaiDanhMuc;
import sapo.vn.ex5dbspringboot.entity.SanPham;
import sapo.vn.ex5dbspringboot.repository.SanPhamRepository;
import sapo.vn.ex5dbspringboot.service.jdbc.SanPhamService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Validated
public class SanPhamServiceImpl implements SanPhamService {
    @Autowired
    private SanPhamRepository sanPhamRepository;
    @Autowired
    private SimpleJdbcCall simpleJdbcCall;

    @Override
    public List<SanPham> getAllSanPham() {
        return sanPhamRepository.findAll();
    }

    @Override
    public SanPham getByIdSanPham(int id) {
        return sanPhamRepository.findById(id).orElse(null);
    }

    @Override
    public SanPham createSanPham(@Valid SanPham sanPham) {
        try {
            if (sanPham != null) {
                return sanPhamRepository.save(sanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SanPham updateSanPham(@Valid SanPham sanPham, int id) {
        try {
            SanPham sanPham1 = sanPhamRepository.findById(id).orElse(null);
            if (sanPham1 != null) {
                return sanPhamRepository.save(sanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteSanPham(int id) {
        try {
            SanPham sanPham = sanPhamRepository.findById(id).orElse(null);
            if (sanPham != null) {
                sanPhamRepository.delete(sanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SanPham> getAllSanPhamStoredProcedure() {
        try {
            Map<String, Object> result = simpleJdbcCall.withProcedureName("GetTopProducts").execute();

            List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");
            List<SanPham> productList = new ArrayList<>();

            for (Map<String, Object> row : resultSet) {
                SanPham sanPham = new SanPham();
                sanPham.setId((int) row.get("Id"));
                sanPham.setMaSanPham((String) row.get("ma_san_pham"));
                sanPham.setTen((String) row.get("Ten"));
                sanPham.setGia((double) row.get("Gia"));
                sanPham.setMoTaSanPham((String) row.get("mo_ta_san_pham"));
                sanPham.setDuongDanAnh((String) row.get("duong_dan_anh"));
                sanPham.setSoLuongSanPham((int) row.get("so_luong_san_pham"));
                sanPham.setSoLuongBan((int) row.get("so_luong_ban"));
                LocalDateTime ngayTao = (LocalDateTime) row.get("ngay_tao");
                LocalDateTime ngaySua = (LocalDateTime) row.get("ngay_sua");
                sanPham.setNgayTao(Date.from(ngayTao.atZone(ZoneId.systemDefault()).toInstant()));
                sanPham.setNgaySua(Date.from(ngaySua.atZone(ZoneId.systemDefault()).toInstant()));

                LoaiDanhMuc danhMuc = new LoaiDanhMuc();
                danhMuc.setId((int) row.get("danh_muc_id"));
                sanPham.setDanhMuc(danhMuc);

                Kho kho = new Kho();
                kho.setId((int) row.get("kho_id"));
                sanPham.setKho(kho);


                productList.add(sanPham);
            }

            return productList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}