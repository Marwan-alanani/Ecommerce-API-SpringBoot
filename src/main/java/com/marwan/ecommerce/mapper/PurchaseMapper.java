package com.marwan.ecommerce.mapper;

import com.marwan.ecommerce.controller.purchase.request.CreatePurchaseRequest;
import com.marwan.ecommerce.dto.common.PageDto;
import com.marwan.ecommerce.dto.purchase.PurchaseDto;
import com.marwan.ecommerce.model.entity.Purchase;
import com.marwan.ecommerce.service.purchase.command.CreatePurchaseCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PurchaseMapper
{
    CreatePurchaseCommand createPurchaseRequestToCreatePurchaseCommand(
            CreatePurchaseRequest request);

//    @Mapping(target = "unitPrice", expression = "java(purchase.getUnitPrice().doubleValue())")
    PurchaseDto purchaseToPurchaseDto(Purchase purchase);

    List<PurchaseDto> purchaseListToPurchaseDtoList(List<Purchase> purchaseList);

    @Mapping(target = "pageNumber", expression = "java(page.getNumber() + 1)")
    PageDto<PurchaseDto> purchasePageToDto(Page<Purchase> page);
}
