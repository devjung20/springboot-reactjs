package sapo.vn.ex7restfulapispring.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sapo.vn.ex7restfulapispring.entity.Product;
import sapo.vn.ex7restfulapispring.entity.TypeCategory;
import sapo.vn.ex7restfulapispring.repository.ProductRepository;
import sapo.vn.ex7restfulapispring.repository.TypeCategoryRepository;
import sapo.vn.ex7restfulapispring.service.TypeCategoryService;

import java.util.Date;
import java.util.List;

@Service
public class TypeCategoryServiceImpl implements TypeCategoryService {
    private final TypeCategoryRepository typeCategoryRepository;
    private final ProductRepository productRepository;

    public TypeCategoryServiceImpl(TypeCategoryRepository typeCategoryRepository,
                                   ProductRepository productRepository) {
        this.typeCategoryRepository = typeCategoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<TypeCategory> getAllTypeCategories() {
        return typeCategoryRepository.findAll();
    }

    @Override
    public TypeCategory getTypeCategoriesById(int id) {
        return typeCategoryRepository.findById(id).orElse(null);
    }

    @Override
    public TypeCategory createTypeCategories(TypeCategory typeCate) {
        String typeCategoryCode = typeCate.getCategoriesCode();
        if (existsByCategoriesCode(typeCategoryCode)) {
            throw new RuntimeException("Type Category with type category code " + typeCategoryCode + " already exists.");
        }
        TypeCategory typeCategory = new TypeCategory();
        typeCategory.setCategoriesCode(typeCate.getCategoriesCode());
        typeCategory.setName(typeCate.getName());
        typeCategory.setDescription(typeCate.getDescription());
        typeCategory.setDateCreate(new Date());
        typeCategory.setDateFix(new Date());
        return typeCategoryRepository.save(typeCategory);
    }

    @Override
    public TypeCategory updateTypeCategories(int id, TypeCategory typeCate) {
        TypeCategory typeCategory = typeCategoryRepository.findById(id).orElse(null);
        typeCategory.setCategoriesCode(typeCate.getCategoriesCode());
        typeCategory.setName(typeCate.getName());
        typeCategory.setDescription(typeCate.getDescription());
        typeCategory.setDateFix(new Date());
        return typeCategoryRepository.save(typeCategory);
    }

    @Transactional
    @Override
    public void deleteTypeCategories(int id) {
        TypeCategory typeCategory = typeCategoryRepository.findById(id).orElse(null);
        List<Product> products = productRepository.findByTypeCategories(typeCategory);
        typeCategoryRepository.delete(typeCategory);
        productRepository.deleteAll(products);
    }

    private Page toPage(List<TypeCategory> list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = (pageable.getOffset() + pageable.getPageSize() > list.size())
                ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    @Override
    public Page<TypeCategory> pageTypeCategory(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        List<TypeCategory> typeCategories = typeCategoryRepository.findAll();
        Page<TypeCategory> typeCategoryPage = toPage(typeCategories, pageable);
        return typeCategoryPage;
    }

    @Override
    public Page<TypeCategory> searchNameByCategory(int page, String keyword) {
        Pageable pageable = PageRequest.of(page, 10);
        List<TypeCategory> typeCategories = typeCategoryRepository.searchByNameTyCategory(keyword);
        Page<TypeCategory> typeCategoryPage = toPage(typeCategories, pageable);
        return typeCategoryPage;
    }

    @Override
    public boolean existsByCategoriesCode(String typeCategoryCode) {
        return typeCategoryRepository.existsByCategoriesCode(typeCategoryCode);
    }
}
