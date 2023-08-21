package sapo.vn.ex7restfulapispring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sapo.vn.ex7restfulapispring.entity.TypeCategory;

import java.util.List;

@Repository
public interface TypeCategoryRepository extends JpaRepository<TypeCategory, Integer> {
    @Query("select c from TypeCategory c where c.name like %?1%")
    List<TypeCategory> searchByNameTyCategory(String keyword);

    boolean existsByCategoriesCode(String typeCategoryCode);

}
