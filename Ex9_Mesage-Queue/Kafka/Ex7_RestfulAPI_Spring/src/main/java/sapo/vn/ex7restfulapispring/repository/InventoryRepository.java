package sapo.vn.ex7restfulapispring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sapo.vn.ex7restfulapispring.entity.Inventory;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    @Query(value = "SELECT p.name, w.warehouse_code, SUM(p.quantity_product) as total_quantity_product " +
            "FROM warehouse AS w " +
            "JOIN product AS p ON w.id = p.ware_house_id " +
            "GROUP BY p.name, w.warehouse_code", nativeQuery = true)
    List<Object[]> getProductQuantityByWarehouse();
}