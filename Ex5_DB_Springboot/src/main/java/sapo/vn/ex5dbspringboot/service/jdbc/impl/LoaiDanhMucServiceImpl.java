package sapo.vn.ex5dbspringboot.service.jdbc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import sapo.vn.ex5dbspringboot.entity.LoaiDanhMuc;
import sapo.vn.ex5dbspringboot.service.jdbc.LoaiDanhMucService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoaiDanhMucServiceImpl implements LoaiDanhMucService {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<LoaiDanhMuc> getAllLoaiDanhMuc() {
        String sql = "SELECT * FROM loai_danh_muc";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(LoaiDanhMuc.class));
    }

    @Override
    public LoaiDanhMuc getLoaiDanhMucById(int id) {
        String sql = "SELECT * FROM loai_danh_muc WHERE Id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, new BeanPropertyRowMapper<>(LoaiDanhMuc.class));
    }

    @Override
    public LoaiDanhMuc createLoaiDanhMuc(LoaiDanhMuc loaiDanhMuc) {
        try {
            String sql = "INSERT INTO loai_danh_muc (MaLoaiDanhMuc,Ten,MoTa,NgayTao,NgaySua) " +
                    "VALUES (:maLoaiDanhMuc,:ten,:moTa,:ngayTao,:ngaySua)";
            Date currentDate = new Date();
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("maLoaiDanhMuc", loaiDanhMuc.getMaLoaiDanhMuc());
            paramMap.put("ten", loaiDanhMuc.getTen());
            paramMap.put("moTa", loaiDanhMuc.getMoTa());
            paramMap.put("ngayTao", currentDate);
            paramMap.put("ngaySua", currentDate);
            namedParameterJdbcTemplate.update(sql, paramMap);
            return loaiDanhMuc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public LoaiDanhMuc updateLoaiDanhMuc(LoaiDanhMuc loaiDanhMuc) {
        try {
            String sql = "UPDATE loai_danh_muc SET MaLoaiDanhMuc=:maLoaiDanhMuc,Ten=:ten,Mota=:moTa,NgaySua=:ngaySua WHERE Id=:id";
            Date currentDate = new Date();
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("maLoaiDanhMuc", loaiDanhMuc.getMaLoaiDanhMuc());
            paramMap.put("ten", loaiDanhMuc.getTen());
            paramMap.put("moTa", loaiDanhMuc.getMoTa());
            paramMap.put("ngaySua", currentDate);
            paramMap.put("id", loaiDanhMuc.getId());
            namedParameterJdbcTemplate.update(sql, paramMap);
            return loaiDanhMuc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteLoaiDanhMuc(int id) {
        try {
            String sql = "DELETE FROM loai_danh_muc WHERE Id=:id";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", id);
            namedParameterJdbcTemplate.update(sql, paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
