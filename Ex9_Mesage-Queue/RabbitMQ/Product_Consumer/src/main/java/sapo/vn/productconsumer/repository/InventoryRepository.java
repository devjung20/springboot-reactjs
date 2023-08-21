package sapo.vn.productconsumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sapo.vn.productconsumer.entity.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    boolean existsByProductNameAndWarehouseCode(String productName, String warehouseCode);
}
