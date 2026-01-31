package com.marwan.ecommerce.dto.common;


import com.marwan.ecommerce.model.enums.SortDirection;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import java.util.Objects;

@Getter
public class PagingOptions<T extends Enum<T> & SortingOptions>
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
        this.sortDir = Objects.requireNonNullElse(sortDir, SortDirection.DESC);
        this.sortBy = sortBy;
    }
}

