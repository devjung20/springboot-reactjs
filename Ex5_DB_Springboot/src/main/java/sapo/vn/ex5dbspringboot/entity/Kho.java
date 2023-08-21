package sapo.vn.ex5dbspringboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Kho")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Kho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "MaKho")
    private String maKho;

    @Column(name = "Ten")
    private String ten;

    @Column(name = "DiaDiem")
    private String diaDiem;

    @Column(name = "NgayTao")
    private Date ngayTao;

    @Column(name = "NgaySua")
    private Date ngaySua;

    @OneToMany(mappedBy = "kho", fetch = FetchType.LAZY)
    private Set<SanPham> sanPhams;
}