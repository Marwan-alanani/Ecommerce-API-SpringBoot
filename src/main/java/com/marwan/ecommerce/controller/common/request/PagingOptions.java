package com.marwan.ecommerce.controller.common.request;


import com.marwan.ecommerce.model.enums.SortDirection;
import com.marwan.ecommerce.model.enums.sorting.BaseSortingOptions;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.util.Objects;

@Getter
@NoArgsConstructor
@Setter
public class PagingOptions<T extends Enum<T> & BaseSortingOptions>
{

    @Range(min = 1, max = 30, message = "Page size must be between 1 and 30")
    private Integer pageSize;
    @Min(1)
    private Integer pageNo;
    private T sortBy;
    private SortDirection sortDir;

    public PagingOptions(
            Integer pageSize,
            Integer pageNo,
            T sortBy,
            SortDirection sortDir
    )
    {
        this.pageSize = Objects.requireNonNullElse(pageSize, 10);
        this.pageNo = Objects.requireNonNullElse(pageNo, 1);
        this.sortDir = sortDir;
        this.sortBy = sortBy;
    }
}

