package sapo.vn.ex7restfulapispring.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "product")
@NamedStoredProcedureQuery(name = "getProductQuantitySell", procedureName = "getProductQuantitySell")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Product cannot be empty!")
    @Size(min = 3, message = "Product code must be 3 character or more!")
    private String productCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typeCategoriesId", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private TypeCategory typeCategories;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wareHouseId", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Warehouse wareHouse;
    @Size(min = 3, message = "Name must be 3 character or more!")
    private String name;
    private double price;
    private String description;
    private String urlImage;
    private int quantityProduct;
    private int quantitySell;
    private Date dateCreate;
    private Date dateFix;
}
