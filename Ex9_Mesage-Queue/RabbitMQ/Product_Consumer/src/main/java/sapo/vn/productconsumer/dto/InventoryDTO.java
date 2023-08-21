package sapo.vn.productconsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    private String productName;
    private String warehouseCode;
    private int totalQuantity;
}
