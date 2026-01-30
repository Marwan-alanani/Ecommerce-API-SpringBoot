package com.marwan.ecommerce.controller.product.request;

import com.marwan.ecommerce.controller.common.request.PagingOptions;
import com.marwan.ecommerce.model.enums.SortDirection;
import com.marwan.ecommerce.model.enums.sorting.ProductSortingOptions;

public class ProductPagingOptions extends PagingOptions<ProductSortingOptions>
{
    public ProductPagingOptions(Integer pageSize, Integer pageNo, ProductSortingOptions sortBy,
            SortDirection sortDir)
    {
        super(pageSize, pageNo, sortBy, sortDir);
    }
}
