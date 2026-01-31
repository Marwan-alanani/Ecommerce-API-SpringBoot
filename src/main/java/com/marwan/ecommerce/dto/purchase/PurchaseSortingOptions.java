package com.marwan.ecommerce.dto.purchase;

import com.marwan.ecommerce.dto.common.SortingOptions;
import lombok.Getter;

public enum PurchaseSortingOptions implements SortingOptions
{
    UNIT_PRICE("unitPrice"),
    QUANTITY("quantity"),
    CREATED_AT("createdDateTime");


    @Getter
    private final String propertyName;

    PurchaseSortingOptions(String propertyName)
    {
        this.propertyName = propertyName;
    }
}
