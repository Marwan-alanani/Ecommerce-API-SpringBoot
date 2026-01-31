package com.marwan.ecommerce.dto.user;

import com.marwan.ecommerce.dto.common.SortingOptions;
import lombok.Getter;

public enum UserSortingOptions implements SortingOptions
{
    first_name("firstName"),
    last_name("lastName"),
    email("email"),
    CREATED_AT("createdDateTime"),
    ;
    @Getter
    private final String propertyName;

    UserSortingOptions(String propertyName)
    {
        this.propertyName = propertyName;
    }
}
