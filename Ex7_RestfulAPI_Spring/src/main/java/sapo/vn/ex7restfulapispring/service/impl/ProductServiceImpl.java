package sapo.vn.ex7restfulapispring.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sapo.vn.ex7restfulapispring.entity.Product;
import sapo.vn.ex7restfulapispring.entity.TypeCategory;
import sapo.vn.ex7restfulapispring.entity.Warehouse;
import sapo.vn.ex7restfulapispring.repository.ProductRepository;
import sapo.vn.ex7restfulapispring.service.ProductService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsByProductCode(String productCode) {
        return productRepository.existsByProductCode(productCode);
    }

    private Page toPage(List<Product> list, Pageable pageable) {
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
    public Page<Product> pageProduct(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 10);
        List<Product> products = productRepository.findAll();
        Page<Product> productPage = toPage(products, pageable);
        return productPage;
    }

    @Override
    public Page<Product> searchNameByProductPage(int pageNo, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, 10);
        List<Product> products = productRepository.searchNameByProduct(keyword);
        Page<Product> productPage = toPage(products, pageable);
        return productPage;
    }

    @Override
    public List<Product> filterNameProductAnhCategory(String namePro, String cate) {
        return productRepository.filterNameProductAnhCategory("%" + namePro, "%" + cate);
    }

    @Override
    public List<Product> getProductQuantitySell() {
        StoredProcedureQuery storedProcedure = entityManager.createNamedStoredProcedureQuery("getProductQuantitySell");
        storedProcedure.execute();
        List<Object[]> result = storedProcedure.getResultList();
        List<Product> products = new ArrayList<>();

        for (Object[] row : result) {
            Product product = new Product();
            product.setId((Integer) row[0]);
            product.setDateCreate((Date) row[1]);
            product.setDateFix((Date) row[2]);
            product.setName((String) row[3]);
            product.setDescription((String) row[4]);
            product.setPrice((Double) row[5]);
            product.setProductCode((String) row[6]);
            product.setQuantityProduct((Integer) row[7]);
            product.setQuantitySell((Integer) row[8]);
            product.setUrlImage((String) row[9]);
            Integer warehouseId = (Integer) row[10];
            Warehouse warehouse = entityManager.find(Warehouse.class, warehouseId);
            product.setWareHouse(warehouse);
            Integer typeCategoryId = (Integer) row[10];
            TypeCategory typeCategory = entityManager.find(TypeCategory.class, typeCategoryId);
            product.setTypeCategories(typeCategory);

            products.add(product);
        }
        return products;
    }

    @Override
    public List<Object[]> countProductsByTypeCategories() {
        return productRepository.countProductsByTypeCategories();
    }

    @Override
    public Product createProduct(Product product) {
        String productCode = product.getProductCode();
        if (existsByProductCode(productCode)) {
            throw new RuntimeException("Product with productCode " + productCode + " already exists.");
        }
        Product productCreate = new Product();
        productCreate.setProductCode(productCode);
        productCreate.setTypeCategories(product.getTypeCategories());
        productCreate.setWareHouse(product.getWareHouse());
        productCreate.setName(product.getName());
        productCreate.setPrice(product.getPrice());
        productCreate.setDescription(product.getDescription());
        productCreate.setUrlImage(product.getUrlImage());
        productCreate.setQuantityProduct(product.getQuantityProduct());
        productCreate.setQuantitySell(product.getQuantitySell());
        productCreate.setDateCreate(new Date());
        productCreate.setDateFix(new Date());
        return productRepository.save(productCreate);
    }

    @Override
    public Product updateProduct(int id, Product product) {
        Product productUpdate = productRepository.findById(id).orElse(null);
        if (productUpdate == null) {
            return null;
        }
        productUpdate.setProductCode(product.getProductCode());
        productUpdate.setTypeCategories(product.getTypeCategories());
        productUpdate.setWareHouse(product.getWareHouse());
        productUpdate.setName(product.getName());
        productUpdate.setPrice(product.getPrice());
        productUpdate.setDescription(product.getDescription());
        productUpdate.setUrlImage(product.getUrlImage());
        productUpdate.setQuantityProduct(product.getQuantityProduct());
        productUpdate.setQuantitySell(product.getQuantitySell());
        Date originalDateCreate = productUpdate.getDateCreate();
        productUpdate.setDateCreate(originalDateCreate);
        productUpdate.setDateFix(new Date());
        return productRepository.save(productUpdate);
    }

    @Override
    public void deleteProduct(int id) {
        Product productDelete = productRepository.findById(id).orElse(null);
        productRepository.delete(productDelete);
    }
}
