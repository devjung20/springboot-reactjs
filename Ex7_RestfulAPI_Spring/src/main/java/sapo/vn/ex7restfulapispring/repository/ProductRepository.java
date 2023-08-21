package sapo.vn.ex7restfulapispring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import sapo.vn.ex7restfulapispring.entity.Product;
import sapo.vn.ex7restfulapispring.entity.TypeCategory;
import sapo.vn.ex7restfulapispring.entity.Warehouse;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByWareHouse(Warehouse warehouse);

    List<Product> findByTypeCategories(TypeCategory typeCategory);

    boolean existsByProductCode(String productCode);

    @Query(value = "select p from Product p where p.name like %?1%")
    List<Product> searchNameByProduct(String keyword);

    @Query(value = "select p from Product p join p.typeCategories c " +
            "where p.name like %?1% and c.name like ?2")
    List<Product> filterNameProductAnhCategory(String namePro, String cate);

    @Procedure(name = "getProductQuantitySell")
    List<Product> getProductQuantitySell();

    @Query(value = "select c.name as categoryName, count(p.quantity_product) as productCount " +
            "from Product p " +
            "join categories c on p.id = c.id " +
            "group by c.name order by productCount desc ", nativeQuery = true)
    List<Object[]> countProductsByTypeCategories();
}
