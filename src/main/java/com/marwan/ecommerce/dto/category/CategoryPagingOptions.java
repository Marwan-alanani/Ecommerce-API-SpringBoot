package com.marwan.ecommerce.dto.category;

import com.marwan.ecommerce.dto.common.PagingOptions;
import com.marwan.ecommerce.model.enums.SortDirection;

public class CategoryPagingOptions extends PagingOptions<CategorySortingOptions>
{
    public CategoryPagingOptions(Integer pageSize, Integer pageNo, CategorySortingOptions sortBy,
            SortDirection sortDir)
    {
        if (sortBy == null)
            sortBy = CategorySortingOptions.CREATED_AT;
        super(pageSize, pageNo, sortBy, sortDir);
    }
}
