package sapo.vn.ex7restfulapispring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sapo.vn.ex7restfulapispring.entity.Warehouse;

import java.util.List;


@Repository
public interface WareHouseRepository extends JpaRepository<Warehouse, Integer> {
    @Query("select w from Warehouse w where w.name like %?1%")
    List<Warehouse> searchWarehouseList(String keyword);

    boolean existsByWarehouseCode(String warehouseCode);

}
