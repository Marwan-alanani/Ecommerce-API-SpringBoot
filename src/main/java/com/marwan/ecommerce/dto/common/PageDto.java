package com.marwan.ecommerce.dto.common;


import java.util.List;

public record PageDto<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        long totalPages,
        long totalElements,
        boolean hasNext,
        boolean hasPrevious
)
{
}
