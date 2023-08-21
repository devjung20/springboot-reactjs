package sapo.vn.ex7restfulapispring.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sapo.vn.ex7restfulapispring.entity.Product;
import sapo.vn.ex7restfulapispring.entity.Warehouse;
import sapo.vn.ex7restfulapispring.repository.ProductRepository;
import sapo.vn.ex7restfulapispring.repository.WareHouseRepository;
import sapo.vn.ex7restfulapispring.service.WareHouseService;

import java.util.Date;
import java.util.List;

@Service
public class WareHouseServiceImpl implements WareHouseService {
    private final WareHouseRepository wareHouseRpository;
    private final ProductRepository productRepository;

    public WareHouseServiceImpl(WareHouseRepository wareHouseRpository,
                                ProductRepository productRepository) {
        this.wareHouseRpository = wareHouseRpository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Warehouse> getAllWareHouses() {
        return wareHouseRpository.findAll();
    }

    @Override
    public Warehouse getWareHouseById(int id) {
        return wareHouseRpository.findById(id).orElse(null);
    }

    @Override
    public Warehouse createWareHouse(Warehouse wareHouse) {
        String warehouseCode = wareHouse.getWarehouseCode();
        if (existsByWarehouseCode(warehouseCode)) {
            throw new RuntimeException("Warehouse with warehouse code " + warehouseCode + " already exists.");
        }
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseCode(wareHouse.getWarehouseCode());
        warehouse.setName(wareHouse.getName());
        warehouse.setLocation(wareHouse.getLocation());
        Date currentDate = new Date();
        warehouse.setDateCreate(currentDate);
        warehouse.setDateFix(currentDate);
        return wareHouseRpository.save(warehouse);
    }

    @Override
    public Warehouse updateWareHouse(int id, Warehouse wareHouse) {
        Warehouse warehouse = wareHouseRpository.findById(id).orElse(null);
        warehouse.setWarehouseCode(wareHouse.getWarehouseCode());
        warehouse.setName(wareHouse.getName());
        warehouse.setLocation(wareHouse.getLocation());
        Date currentDate = new Date();
        warehouse.setDateFix(currentDate);
        return wareHouseRpository.save(warehouse);
    }

    @Transactional
    @Override
    public void deleteWareHouse(int id) {
        Warehouse warehouse = wareHouseRpository.findById(id).orElse(null);
        List<Product> products = productRepository.findByWareHouse(warehouse);
        wareHouseRpository.delete(warehouse);
        productRepository.deleteAll(products);
    }

    private Page toPage(List<Warehouse> warehouseList, Pageable pageable) {
        if (pageable.getOffset() >= warehouseList.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize() > warehouseList.size()))
                ? warehouseList.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = warehouseList.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, warehouseList.size());
    }

    @Override
    public Page<Warehouse> searchWarehouses(int page, String keyword) {
        Pageable pageable = PageRequest.of(page, 10);
        List<Warehouse> warehouses = wareHouseRpository.searchWarehouseList(keyword);
        Page<Warehouse> warehousePage = toPage(warehouses, pageable);
        return warehousePage;
    }

    @Override
    public Page<Warehouse> pageWarehouse(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 10);
        List<Warehouse> warehouses = wareHouseRpository.findAll();
        Page<Warehouse> warehousePage = toPage(warehouses, pageable);
        return warehousePage;
    }

    @Override
    public boolean existsByWarehouseCode(String warehouseCode) {
        return wareHouseRpository.existsByWarehouseCode(warehouseCode);
    }
}
