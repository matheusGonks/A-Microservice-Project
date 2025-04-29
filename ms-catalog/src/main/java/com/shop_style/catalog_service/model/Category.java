package com.shop_style.catalog_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private boolean active;

    @ManyToOne
    private Category parent;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Category> childrenCategories;

    public Category(String name, boolean active){
        this.name = name;
        this.active = active;
        this.childrenCategories = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Category category)) return false;
        return active == category.active && Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, active);
    }

    public boolean hasParent(){
        return parent != null;
    }
}
