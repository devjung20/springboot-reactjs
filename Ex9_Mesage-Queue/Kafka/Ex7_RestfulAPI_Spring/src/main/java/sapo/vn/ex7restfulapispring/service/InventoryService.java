package sapo.vn.ex7restfulapispring.service;

import sapo.vn.ex7restfulapispring.dto.InventoryDTO;

import java.util.List;

public interface InventoryService {
    List<InventoryDTO> getProductQuantitiesByWarehouse();
}

