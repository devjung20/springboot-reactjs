package sapo.vn.ex7restfulapispring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import sapo.vn.ex7restfulapispring.entity.TypeCategory;
import sapo.vn.ex7restfulapispring.service.TypeCategoryService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class TypeCategoryController {
    private final TypeCategoryService typeCategoryService;

    @Autowired
    public TypeCategoryController(TypeCategoryService typeCategoryService) {
        this.typeCategoryService = typeCategoryService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllTypeCategory() {
        try {
            List<TypeCategory> typeCategories = typeCategoryService.getAllTypeCategories();
            if (typeCategories.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No category type!");
            } else {
                return ResponseEntity.ok(typeCategories);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error!");
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<?> getTypeCategoryById(@RequestParam("id") int id) {
        try {
            TypeCategory typeCategory = typeCategoryService.getTypeCategoriesById(id);
            if (typeCategory != null) {
                return ResponseEntity.ok(typeCategory);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Type category not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error!");
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
    public ResponseEntity<?> createTypeCategory(@Valid @RequestBody TypeCategory typeCategory,
                                                BindingResult bindingResult) {
        ResponseEntity<?> validationErrorsResponse = handleValidationErrors(bindingResult);
        if (validationErrorsResponse != null) {
            return validationErrorsResponse;
        }
        try {
            String typeCategoryCode = typeCategory.getCategoriesCode();
            if (typeCategoryService.existsByCategoriesCode(typeCategoryCode)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Type category code " + typeCategoryCode + " already exists!");
            }
            typeCategoryService.createTypeCategories(typeCategory);
            return ResponseEntity.status(HttpStatus.CREATED).body("Create Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error!");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTypeCategory(@PathVariable("id") int id,
                                                @Valid @RequestBody TypeCategory typeCategory,
                                                BindingResult bindingResult) {
        ResponseEntity<?> validationErrorsResponse = handleValidationErrors(bindingResult);
        if (validationErrorsResponse != null) {
            return validationErrorsResponse;
        }
        try {
            TypeCategory existingCategory = typeCategoryService.getTypeCategoriesById(id);
            if (existingCategory == null) {
                return ResponseEntity.notFound().build();
            }
            TypeCategory typeCategoryUpdate = typeCategoryService.updateTypeCategories(id, typeCategory);
            return ResponseEntity.ok(typeCategoryUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error!");
        }
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTypeCategory(@PathVariable("id") int id) {
        try {
            typeCategoryService.deleteTypeCategories(id);
            return ResponseEntity.ok().body("Deleted Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error!");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TypeCategory>> searchTypeCategory(@RequestParam("page") int page,
                                                                 @RequestParam("keyword") String keyword) {
        Page<TypeCategory> typeCategoryPage = typeCategoryService.searchNameByCategory(page, keyword);
        if (typeCategoryPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(typeCategoryPage);
        }
    }

    @GetMapping("/page")
    public ResponseEntity<Page<TypeCategory>> pageTypeCategory(@RequestParam("pageNo") int page) {
        Page<TypeCategory> typeCategoryPage = typeCategoryService.pageTypeCategory(page);
        if (typeCategoryPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(typeCategoryPage);
        }
    }
}
