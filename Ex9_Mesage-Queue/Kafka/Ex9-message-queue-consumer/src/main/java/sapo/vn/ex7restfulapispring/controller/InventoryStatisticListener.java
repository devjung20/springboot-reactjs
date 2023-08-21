package sapo.vn.ex7restfulapispring.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sapo.vn.ex7restfulapispring.dto.InventoryDTO;
import sapo.vn.ex7restfulapispring.entity.Inventory;
import sapo.vn.ex7restfulapispring.repository.InventoryRepository;

import java.util.Date;

@Component
@Slf4j
public class InventoryStatisticListener {
    private final InventoryRepository inventoryRepository;

    public InventoryStatisticListener(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @KafkaListener(topics = "inventory-topic", groupId = "stock-consumer-group")
    public void consume(ConsumerRecord<String, InventoryDTO> record) {
        log.info("Received inventory message: key={}, value={}, offset={}",
                record.key(), record.value(), record.offset());

        // Xử lý message và lưu vào database
        InventoryDTO inventoryDTO = record.value();

        if (!inventoryRepository.existsByProductNameAndWarehouseCode(inventoryDTO.getProductName(), inventoryDTO.getWarehouseCode())) {
            // Tạo một đối tượng Inventory từ InventoryDTO để lưu vào database
            Inventory inventory = new Inventory();
            inventory.setProductName(inventoryDTO.getProductName());
            inventory.setWarehouseCode(inventoryDTO.getWarehouseCode());
            inventory.setTotalQuantity(inventoryDTO.getTotalQuantity());
            inventory.setStatisticsDate(new Date()); // Set ngày thống kê tùy vào business logic của bạn

            // Lưu đối tượng Inventory vào database thông qua InventoryRepository
            inventoryRepository.save(inventory);
        }
        log.info("Inventory data saved to database.");
    }
}
