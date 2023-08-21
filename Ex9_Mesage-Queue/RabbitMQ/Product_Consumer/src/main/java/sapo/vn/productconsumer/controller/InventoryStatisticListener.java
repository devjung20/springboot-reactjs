package sapo.vn.productconsumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sapo.vn.productconsumer.dto.InventoryDTO;
import sapo.vn.productconsumer.entity.Inventory;
import sapo.vn.productconsumer.repository.InventoryRepository;

import java.util.Date;
import java.util.List;


@Slf4j
@Component
public class InventoryStatisticListener {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryStatisticListener(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @RabbitListener(queues = "queue.InventoryStatistic")
    public void receiveInventoryStatistic(List<InventoryDTO> inventoryDTOs) {
        log.info("Received inventory statistic message: {}", inventoryDTOs);

        // Thực hiện thống kê số lượng sản phẩm trong kho và lưu vào cơ sở dữ liệu
        Date statisticDate = new Date();
        for (InventoryDTO inventoryDTO : inventoryDTOs) {
            if (!inventoryRepository.existsByProductNameAndWarehouseCode(inventoryDTO.getProductName(), inventoryDTO.getWarehouseCode())) {
                Inventory inventory = new Inventory();
                inventory.setProductName(inventoryDTO.getProductName());
                inventory.setWarehouseCode(inventoryDTO.getWarehouseCode());
                inventory.setTotalQuantity(inventoryDTO.getTotalQuantity());
                inventory.setStatisticsDate(statisticDate);
                inventoryRepository.save(inventory);
            }
        }

        log.info("Inventory statistic data saved to database.");
    }
}

