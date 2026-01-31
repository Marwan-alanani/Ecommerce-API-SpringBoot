package com.marwan.ecommerce.service.common;

import com.marwan.ecommerce.dto.common.PagingOptions;
import com.marwan.ecommerce.dto.common.SortingOptions;
import com.marwan.ecommerce.model.enums.SortDirection;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public abstract class BaseService
{

    protected Pageable constructPageable(
            PagingOptions<? extends SortingOptions> pagingOptions
    )
    {
        Sort sort = Sort.by(pagingOptions.getSortBy().getPropertyName());
        if (pagingOptions.getSortDir() == SortDirection.ASC) {
            sort = sort.ascending();
        } else {
            sort = sort.descending();
        }
        return PageRequest.of(pagingOptions.getPageNo() - 1, pagingOptions.getPageSize(), sort);
    }
}
