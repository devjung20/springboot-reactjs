package sapo.vn.ex5dbspringboot.service.jdbc;

import sapo.vn.ex5dbspringboot.entity.Kho;

import java.util.List;

public interface KhoService {
    List<Kho> getAllKho();

    Kho createKho(Kho kho);

    Kho updateKho(Kho kho);

    void deleteKho(int id);

    Kho getByIdKho(int id);
}
