package com.marwan.ecommerce.controller.common;

import com.marwan.ecommerce.dto.common.PageDto;
import org.springframework.data.domain.Page;

import java.util.List;

public class BaseController
{
    public static <T> PageDto<T> toPageDto(Page<?> page, List<T> itemsDto)
    {
        return new PageDto<>(
                page.getNumber() + 1,
                page.getNumberOfElements(),
                itemsDto,
                page.getTotalPages(),
                page.getTotalElements(),
                page.hasNext(),
                page.hasPrevious()
        );
    }
}
