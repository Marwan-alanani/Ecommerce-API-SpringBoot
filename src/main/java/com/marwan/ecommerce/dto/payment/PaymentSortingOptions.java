package com.marwan.ecommerce.dto.payment;

import com.marwan.ecommerce.dto.common.SortingOptions;
import lombok.Getter;

public enum PaymentSortingOptions implements SortingOptions
{
    AMOUNT("amount"),
    CREATED_AT("createdDateTime"),
    LAST_MODIFIED("updatedDateTime"),
    ;
    @Getter
    private final String propertyName;

    PaymentSortingOptions(String propertyName)
    {
        this.propertyName = propertyName;
    }
}
