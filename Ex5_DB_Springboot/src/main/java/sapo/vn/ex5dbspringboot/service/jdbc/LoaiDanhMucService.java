package sapo.vn.ex5dbspringboot.service.jdbc;

import sapo.vn.ex5dbspringboot.entity.LoaiDanhMuc;

import java.util.List;

public interface LoaiDanhMucService {
    List<LoaiDanhMuc> getAllLoaiDanhMuc();

    LoaiDanhMuc getLoaiDanhMucById(int id);

    LoaiDanhMuc createLoaiDanhMuc(LoaiDanhMuc loaiDanhMuc);

    LoaiDanhMuc updateLoaiDanhMuc(LoaiDanhMuc loaiDanhMuc);

    void deleteLoaiDanhMuc(int id);
}
