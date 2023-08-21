package sapo.vn.ex5dbspringboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "LoaiDanhMuc")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoaiDanhMuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "MaLoaiDanhMuc")
    private String maLoaiDanhMuc;

    @Column(name = "Ten")
    private String ten;

    @Column(name = "MoTa")
    private String moTa;

    @Column(name = "NgayTao")
    private Date ngayTao;

    @Column(name = "NgaySua")
    private Date ngaySua;

    @OneToMany(mappedBy = "danhMuc", fetch = FetchType.LAZY)
    private Set<SanPham> sanPhams;
}
