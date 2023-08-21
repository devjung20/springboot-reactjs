package sapo.vn.ex7restfulapispring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sapo.vn.ex7restfulapispring.dto.InventoryDTO;
import sapo.vn.ex7restfulapispring.repository.InventoryRepository;
import sapo.vn.ex7restfulapispring.service.InventoryService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public List<InventoryDTO> getProductQuantitiesByWarehouse() {
        List<Object[]> inventoryStatistics = inventoryRepository.getProductQuantityByWarehouse();
        List<InventoryDTO> inventoryDTOS = new ArrayList<>();

        for (Object[] data : inventoryStatistics) {
            InventoryDTO inventoryDTO = new InventoryDTO();
            inventoryDTO.setProductName((String) data[0]);
            inventoryDTO.setWarehouseCode((String) data[1]);
            inventoryDTO.setTotalQuantity(((BigDecimal) data[2]).intValue());

            inventoryDTOS.add(inventoryDTO);
        }
        return inventoryDTOS;
    }
}

