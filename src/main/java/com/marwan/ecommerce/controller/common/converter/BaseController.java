package com.marwan.ecommerce.controller.common.converter;

import com.marwan.ecommerce.dto.common.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

public class BaseController
{
    public static <T> PageDto<T> toPageDto(Page<?> page, List<T> itemsDto)
    {
        return new PageDto<>(
                itemsDto,
                page.getNumber() + 1,
                page.getNumberOfElements(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.hasNext(),
                page.hasPrevious()
        );
    }
}
