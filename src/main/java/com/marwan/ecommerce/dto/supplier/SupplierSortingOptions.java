package com.marwan.ecommerce.dto.supplier;

import com.marwan.ecommerce.dto.common.SortingOptions;
import lombok.Getter;

public enum SupplierSortingOptions implements SortingOptions
{
    CREATED_AT("createdDateTime"),
    NAME("name"),
    EMAIL("email");
    @Getter
    private final String propertyName;

    SupplierSortingOptions(String propertyName)
    {
        this.propertyName = propertyName;
    }
}
