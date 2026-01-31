package com.marwan.ecommerce.dto.order;

import com.marwan.ecommerce.dto.common.SortingOptions;
import lombok.Getter;

public enum OrderSortingOptions implements SortingOptions
{
    CREATED_AT("createdDateTime"),
    TOTAL("totalPrice");

    @Getter
    private final String propertyName;

    OrderSortingOptions(String propertyName)
    {
        this.propertyName = propertyName;
    }
}
