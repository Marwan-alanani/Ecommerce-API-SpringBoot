package com.marwan.ecommerce.dto.payment;

import com.marwan.ecommerce.dto.common.PagingOptions;
import com.marwan.ecommerce.model.enums.SortDirection;

public class PaymentPagingOptions extends PagingOptions<PaymentSortingOptions>
{
    public PaymentPagingOptions(Integer pageSize, Integer pageNo, PaymentSortingOptions sortBy,
            SortDirection sortDir)
    {
        if (sortBy == null)
            sortBy = PaymentSortingOptions.CREATED_AT;
        super(pageSize, pageNo, sortBy, sortDir);
    }
}
