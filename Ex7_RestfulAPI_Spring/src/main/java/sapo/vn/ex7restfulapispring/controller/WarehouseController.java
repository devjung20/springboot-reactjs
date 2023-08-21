package sapo.vn.ex7restfulapispring.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import sapo.vn.ex7restfulapispring.entity.Warehouse;
import sapo.vn.ex7restfulapispring.service.WareHouseService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    private final WareHouseService wareHouseService;

    public WarehouseController(WareHouseService wareHouseService) {
        this.wareHouseService = wareHouseService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllWarehouses() {
        try {
            List<Warehouse> warehouses = wareHouseService.getAllWareHouses();
            if (warehouses.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No warehouse!");
            } else {
                return ResponseEntity.ok(warehouses);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error!");
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<?> getWarehouseById(@RequestParam("id") int id) {
        try {
            Warehouse warehouse = wareHouseService.getWareHouseById(id);
            if (warehouse != null) {
                return ResponseEntity.ok(warehouse);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Warehouse not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error!");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Warehouse>> searchWarehouses(@RequestParam("page") int page,
                                                            @RequestParam("keyword") String keyword) {
        Page<Warehouse> warehousePage = wareHouseService.searchWarehouses(page, keyword);
        if (warehousePage.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(warehousePage);
        }
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Warehouse>> pageWarehouse(@RequestParam("pageNo") int pageNo) {
        Page<Warehouse> warehousePage = wareHouseService.pageWarehouse(pageNo);
        if (warehousePage.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(warehousePage);
        }
    }


    private ResponseEntity<?> handleValidationErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        return null;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createWarehouse(@Valid @RequestBody Warehouse warehouse,
                                             BindingResult bindingResult) {
        ResponseEntity<?> validationErrorsResponse = handleValidationErrors(bindingResult);
        if (validationErrorsResponse != null) {
            return validationErrorsResponse;
        }

        try {
            String warehouseCode = warehouse.getWarehouseCode();
            if (wareHouseService.existsByWarehouseCode(warehouseCode)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Warehouse code " + warehouseCode + " already exists!");
            }
            wareHouseService.createWareHouse(warehouse);
            return ResponseEntity.status(HttpStatus.CREATED).body("Create Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Create failed!");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateWarehouse(@PathVariable("id") int id,
                                             @Valid @RequestBody Warehouse warehouse,
                                             BindingResult bindingResult) {
        ResponseEntity<?> validationErrorsResponse = handleValidationErrors(bindingResult);
        if (validationErrorsResponse != null) {
            return validationErrorsResponse;
        }

        try {
            Warehouse updatedWarehouse = wareHouseService.updateWareHouse(id, warehouse);
            if (updatedWarehouse != null) {
                return ResponseEntity.ok(updatedWarehouse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update failed!");
        }
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteWarehouse(@PathVariable("id") int id) {
        try {
            wareHouseService.deleteWareHouse(id);
            return ResponseEntity.ok().body("Deleted Successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error!");
        }
    }
}
