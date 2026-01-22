package com.marwan.ecommerce.mapper;

import com.marwan.ecommerce.controller.purchase.request.CreatePurchaseRequest;
import com.marwan.ecommerce.dto.purchase.PurchaseDto;
import com.marwan.ecommerce.model.entity.Purchase;
import com.marwan.ecommerce.service.purchase.command.CreatePurchaseCommand;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PurchaseMapper
{
    CreatePurchaseCommand createPurchaseRequestToCreatePurchaseCommand(
            CreatePurchaseRequest request);

    PurchaseDto purchaseToPurchaseDto(Purchase purchase);

    List<PurchaseDto> purchaseListToPurchaseDtoList(List<Purchase> purchaseList);
}
