package sapo.vn.ex7restfulapispring.service;

import org.springframework.data.domain.Page;
import sapo.vn.ex7restfulapispring.entity.Warehouse;

import java.util.List;

public interface WareHouseService {
    List<Warehouse> getAllWareHouses();

    Warehouse getWareHouseById(int id);

    Warehouse createWareHouse(Warehouse warehouse);

    Warehouse updateWareHouse(int id, Warehouse warehouse);

    void deleteWareHouse(int id);

    Page<Warehouse> searchWarehouses(int page, String keyword);

    Page<Warehouse> pageWarehouse(int pageNo);

    boolean existsByWarehouseCode(String warehouseCode);
}
