package com.marwan.ecommerce.dto.order;

import com.marwan.ecommerce.dto.common.PagingOptions;
import com.marwan.ecommerce.model.enums.SortDirection;

public class OrderPagingOptions extends PagingOptions<OrderSortingOptions>
{

    public OrderPagingOptions(Integer pageSize, Integer pageNo, OrderSortingOptions sortBy,
            SortDirection sortDir)
    {
        if (sortBy == null)
            sortBy = OrderSortingOptions.CREATED_AT;

        super(pageSize, pageNo, sortBy, sortDir);
    }
}
