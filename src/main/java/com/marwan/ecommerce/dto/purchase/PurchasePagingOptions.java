package com.marwan.ecommerce.dto.purchase;

import com.marwan.ecommerce.dto.common.PagingOptions;
import com.marwan.ecommerce.model.enums.SortDirection;

public class PurchasePagingOptions extends PagingOptions<PurchaseSortingOptions>
{
    public PurchasePagingOptions(Integer pageSize, Integer pageNo, PurchaseSortingOptions sortBy,
            SortDirection sortDir)
    {
        if (sortBy == null)
            sortBy = PurchaseSortingOptions.CREATED_AT;

        super(pageSize, pageNo, sortBy, sortDir);
    }
}
