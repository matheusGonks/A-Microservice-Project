package com.shop_style.catalog_service.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    @DisplayName("Category - test equality.")
    public void testEquality(){
        Category category1 = new Category("Masculine", true);
        Category category2 = new Category("Masculine", true);
        Category category3 = new Category("Shorts", true);

        assertEquals(category1, category2);
        assertEquals(category2, category3);
    }
}