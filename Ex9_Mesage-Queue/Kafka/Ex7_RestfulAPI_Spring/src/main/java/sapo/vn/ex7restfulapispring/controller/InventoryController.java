package sapo.vn.ex7restfulapispring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import sapo.vn.ex7restfulapispring.dto.InventoryDTO;
import sapo.vn.ex7restfulapispring.service.InventoryService;

import java.util.List;

@RestController
@RequestMapping("/admin/inventory")
@Slf4j
public class InventoryController {
    private final InventoryService inventoryService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public InventoryController(InventoryService inventoryService, KafkaTemplate<String, Object> kafkaTemplate) {
        this.inventoryService = inventoryService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<InventoryDTO>> getInventoryStatistics() {
        try {
            List<InventoryDTO> inventoryDTOS = inventoryService.getProductQuantitiesByWarehouse();
            for (InventoryDTO inventoryDTO : inventoryDTOS) {
                kafkaTemplate.send("inventory-topic", inventoryDTO.getProductName(), inventoryDTO);
            }

            log.info("Message sent to inventory statistic -> {}", inventoryDTOS);
            return ResponseEntity.ok(inventoryDTOS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

