package com.marwan.ecommerce.dto.supplier;

import com.marwan.ecommerce.dto.common.PagingOptions;
import com.marwan.ecommerce.model.enums.SortDirection;

public class SupplierPagingOptions extends PagingOptions<SupplierSortingOptions>
{
    public SupplierPagingOptions(Integer pageSize, Integer pageNo, SupplierSortingOptions sortBy,
            SortDirection sortDir)
    {
        if (sortBy == null)
            sortBy = SupplierSortingOptions.CREATED_AT;
        super(pageSize, pageNo, sortBy, sortDir);
    }
}
