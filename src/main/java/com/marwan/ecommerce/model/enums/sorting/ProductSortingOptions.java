package com.marwan.ecommerce.model.enums.sorting;

import lombok.Getter;

public enum ProductSortingOptions implements BaseSortingOptions
{
    createdAt("createdDateTime"),
    name("name"),
    price("sellingPrice"),
    balance("balance");

    @Getter
    private final String propertyName;

    ProductSortingOptions(String propertyName)
    {
        this.propertyName = propertyName;
    }


}