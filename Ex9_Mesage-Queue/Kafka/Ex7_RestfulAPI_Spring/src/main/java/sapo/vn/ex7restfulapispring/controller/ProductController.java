package sapo.vn.ex7restfulapispring.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import sapo.vn.ex7restfulapispring.entity.Product;
import sapo.vn.ex7restfulapispring.service.ProductService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            if (products.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(products);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server errors!");
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
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product,
                                           BindingResult bindingResult, Authentication authentication) {
        ResponseEntity<?> validationErrorsResponse = handleValidationErrors(bindingResult);
        if (validationErrorsResponse != null) {
            return validationErrorsResponse;
        }
        try {
            String productCode = product.getProductCode();

            if (productService.existsByProductCode(productCode)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Product with productCode " + productCode + " already exists!");
            }

            // Check if the authenticated user has the "ADMIN" role
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));

            if (!isAdmin) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied! Only ADMIN can update products.");
            }

            Product productCreate = productService.createProduct(product);
            if (productCreate != null) {
                return ResponseEntity.ok().body("Created successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Created failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Server error!");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") int id,
                                           @Valid @RequestBody Product product,
                                           BindingResult bindingResult, Authentication authentication) {
        ResponseEntity<?> validationErrorsResponse = handleValidationErrors(bindingResult);
        if (validationErrorsResponse != null) {
            return validationErrorsResponse;
        }

        try {
            Product existingProduct = productService.getProductById(id);
            if (existingProduct == null) {
                return ResponseEntity.notFound().build();
            }

            // Check if the authenticated user has the "ADMIN" role
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));

            if (!isAdmin) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied! Only ADMIN can update products.");
            }

            Product updatedProduct = productService.updateProduct(id, product);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error!");
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") int id, Authentication authentication) {
        try {
            // Check if the authenticated user has the "ADMIN" role
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));

            if (!isAdmin) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied! Only ADMIN can update products.");
            }

            productService.deleteProduct(id);
            return ResponseEntity.ok().body("Deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Server error!");
        }
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Product>> pageProduct(@RequestParam("pageNo") int page) {
        try {
            Page<Product> products = productService.pageProduct(page);
            if (products.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(products);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Product>> searchNameProduct(@RequestParam("page") int page,
                                                           @RequestParam("keyword") String keyword) {
        try {
            Page<Product> products = productService.searchNameByProductPage(page, keyword);
            if (products.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(products);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterNameProductAnhCategory(@RequestParam("name") String namePro,
                                                                      @RequestParam("category") String cate) {
        try {
            List<Product> products = productService.filterNameProductAnhCategory(namePro, cate);
            if (products.isEmpty()) {
                return ResponseEntity.badRequest().build();
            } else {
                return ResponseEntity.ok(products);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/get-storeprocedure")
    public ResponseEntity<List<Product>> getProductQuantitySell() {
        try {
            List<Product> products = productService.getProductQuantitySell();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/countByTypeCatgeory")
    public ResponseEntity<List<Object[]>> getCountByTypeCategory() {
        try {
            List<Object[]> products = productService.countProductsByTypeCategories();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}
