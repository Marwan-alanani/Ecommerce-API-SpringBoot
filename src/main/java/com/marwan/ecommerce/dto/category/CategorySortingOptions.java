package com.marwan.ecommerce.dto.category;

import com.marwan.ecommerce.dto.common.SortingOptions;
import lombok.Getter;

public enum CategorySortingOptions implements SortingOptions
{
    CREATED_AT("createdDateTime"),
    NAME("name");

    @Getter
    private final String propertyName;

    CategorySortingOptions(String propertyName)
    {
        this.propertyName = propertyName;
    }

}
