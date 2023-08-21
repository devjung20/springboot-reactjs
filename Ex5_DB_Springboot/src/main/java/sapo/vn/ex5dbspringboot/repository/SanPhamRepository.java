package sapo.vn.ex5dbspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sapo.vn.ex5dbspringboot.entity.SanPham;


@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
}
