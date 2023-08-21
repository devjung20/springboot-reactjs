package sapo.vn.ex7restfulapispring.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "warehouse")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Warehouse code cannot be empty!")
    @Size(min = 3, message = "Warehouse code must be 3 characters or more!")
    private String warehouseCode;
    @NotBlank(message = "Name cannot be empty!")
    @Size(min = 3, message = "Name must be 3 characters or more!")
    private String name;
    @Size(min = 5, max = 30, message = "Location must be between 5 to 30 characters!")
    private String location;
    private Date dateCreate;
    private Date dateFix;
}