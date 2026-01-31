package com.marwan.ecommerce.dto.product;

import com.marwan.ecommerce.dto.common.SortingOptions;
import lombok.Getter;

public enum ProductSortingOptions implements SortingOptions
{
    CREATED_AT("createdDateTime"),
    NAME("name"),
    PRICE("sellingPrice"),
    BALANCE("balance");

    @Getter
    private final String propertyName;

    ProductSortingOptions(String propertyName)
    {
        this.propertyName = propertyName;
    }


}