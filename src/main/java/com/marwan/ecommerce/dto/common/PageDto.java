package com.marwan.ecommerce.dto.common;


import java.util.List;

public record PageDto<T>(
        int pageNumber,
        int pageSize,
        List<T> content,
        long totalPages,
        long totalElements,
        boolean hasNext,
        boolean hasPrevious
)
{
}
