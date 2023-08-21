package sapo.vn.ex5dbspringboot.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;


@Entity
@Table(name = "SanPham")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "MaSanPham")
    @NotBlank(message = "Mã sản phẩm không được để trống!")
    @Size(min = 3,max = 10,message = "Mã sản phẩm ít nhất là 3 đến 10 ký tự")
    private String maSanPham;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DanhMucId")
    private LoaiDanhMuc danhMuc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KhoId")
    private Kho kho;

    @Column(name = "Ten")
    @NotBlank(message = "Tên sản phẩm không được để trống!")
    @Size(min = 3,max = 10,message = "Tên sản phẩm ít nhất là 3 đến 10 ký tự")
    private String ten;

    @Column(name = "Gia")
    private double gia;

    @Column(name = "MoTaSanPham")
    private String moTaSanPham;

    @Column(name = "DuongDanAnh")
    private String duongDanAnh;

    @Column(name = "SoLuongSanPham")
    private int soLuongSanPham;

    @Column(name = "SoLuongBan")
    private int soLuongBan;

    @Column(name = "NgayTao")
    private Date ngayTao;

    @Column(name = "NgaySua")
    private Date ngaySua;
}
