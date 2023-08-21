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
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TypeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Category code cannot be empty!")
    @Size(min = 3, message = "Category code must be 3 character or more!")
    private String categoriesCode;
    @NotBlank(message = "Name code cannot be empty!")
    @Size(min = 3, message = "Name code must be 3 character or more!")
    private String name;
    private String description;
    private Date dateCreate;
    private Date dateFix;
}
