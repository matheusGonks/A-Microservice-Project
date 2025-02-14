package com.shop_style.customers_service.domain.enums;

public enum Gender {

    MALE,
    FEMALE,
    NON_BINARY,
    OTHER;

    public String getFormattedName(){
        return this.name().substring(0 ,1).toUpperCase() +
                this.name().substring(1).toLowerCase().replace("_", "-");
    }
}
