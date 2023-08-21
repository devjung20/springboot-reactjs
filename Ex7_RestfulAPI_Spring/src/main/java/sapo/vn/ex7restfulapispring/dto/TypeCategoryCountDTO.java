package sapo.vn.ex7restfulapispring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TypeCategoryCountDTO {
    private String typeCategoryName;
    private Long productCount;
}
