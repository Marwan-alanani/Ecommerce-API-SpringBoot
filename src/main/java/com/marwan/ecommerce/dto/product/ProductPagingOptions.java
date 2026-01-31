package com.marwan.ecommerce.dto.product;

import com.marwan.ecommerce.dto.common.PagingOptions;
import com.marwan.ecommerce.model.enums.SortDirection;

public class ProductPagingOptions extends PagingOptions<ProductSortingOptions>
{
    public ProductPagingOptions(
            Integer pageSize,
            Integer pageNo,
            ProductSortingOptions sortBy,
            SortDirection sortDir)
    {
        if(sortBy == null) sortBy = ProductSortingOptions.CREATED_AT;
        super(pageSize, pageNo, sortBy, sortDir);
    }
}
