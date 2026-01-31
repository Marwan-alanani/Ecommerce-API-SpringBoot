package com.marwan.ecommerce.dto.user;

import com.marwan.ecommerce.dto.common.PagingOptions;
import com.marwan.ecommerce.model.enums.SortDirection;

public class UserPagingOptions extends PagingOptions<UserSortingOptions>
{
    public UserPagingOptions(Integer pageSize, Integer pageNo, UserSortingOptions sortBy,
            SortDirection sortDir)
    {
        if (sortBy == null)
            sortBy = UserSortingOptions.CREATED_AT;
        super(pageSize, pageNo, sortBy, sortDir);
    }
}
