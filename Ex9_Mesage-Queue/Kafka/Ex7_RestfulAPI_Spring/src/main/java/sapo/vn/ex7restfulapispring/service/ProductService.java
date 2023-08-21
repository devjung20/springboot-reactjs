package sapo.vn.ex7restfulapispring.service;

import org.springframework.data.domain.Page;
import sapo.vn.ex7restfulapispring.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(int id);

    Product createProduct(Product product);

    Product updateProduct(int id, Product product);

    void deleteProduct(int id);

    boolean existsByProductCode(String productCode);

    Page<Product> pageProduct(int pageNo);

    Page<Product> searchNameByProductPage(int pageNo, String keyword);

    List<Product> filterNameProductAnhCategory(String namePro, String cate);

    List<Product> getProductQuantitySell();

    List<Object[]> countProductsByTypeCategories();
}
