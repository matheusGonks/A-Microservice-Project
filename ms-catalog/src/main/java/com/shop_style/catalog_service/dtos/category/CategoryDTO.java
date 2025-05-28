package com.shop_style.catalog_service.dtos.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "Category name can not be blank or null.")
    private String name;

    private Boolean active = true;

    @Min(value = 1, message = "Category parent Id must be at least 1.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long parentId ;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    List<CategoryDTO> childrenCategories;

    public CategoryDTO(long id, String name, boolean active) {
        this(id, name, active, null);
    }

    public CategoryDTO(long id, String name, boolean active, Long parentId) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.parentId = parentId;
        this.childrenCategories = new ArrayList<>();
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CategoryDTO that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, active, parentId);
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", parentId=" + parentId +
                ", childrenCategories=" + childrenCategories +
                '}';
    }
}
