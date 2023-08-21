package sapo.vn.ex7restfulapispring.service;

import org.springframework.data.domain.Page;
import sapo.vn.ex7restfulapispring.entity.TypeCategory;

import java.util.List;

public interface TypeCategoryService {
    List<TypeCategory> getAllTypeCategories();

    TypeCategory getTypeCategoriesById(int id);

    TypeCategory createTypeCategories(TypeCategory typeCate);

    TypeCategory updateTypeCategories(int id, TypeCategory typeCate);

    void deleteTypeCategories(int id);

    Page<TypeCategory> pageTypeCategory(int page);

    Page<TypeCategory> searchNameByCategory(int page, String keyword);

    boolean existsByCategoriesCode(String typeCategoryCode);
}
