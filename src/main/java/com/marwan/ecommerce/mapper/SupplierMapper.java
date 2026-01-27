package com.marwan.ecommerce.mapper;

import com.marwan.ecommerce.controller.supplier.request.CreateSupplierRequest;
import com.marwan.ecommerce.controller.supplier.request.UpdateSupplierRequest;
import com.marwan.ecommerce.dto.supplier.SupplierDto;
import com.marwan.ecommerce.model.entity.Supplier;
import com.marwan.ecommerce.service.supplier.command.CreateSupplierCommand;
import com.marwan.ecommerce.service.supplier.command.UpdateSupplierCommand;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SupplierMapper
{
    SupplierDto supplierToSupplierDto(Supplier supplier);

    List<SupplierDto> supplierListToSupplierDtoList(List<Supplier> supplierList);

    CreateSupplierCommand createSupplierRequestToCreateSupplierCommand(
            CreateSupplierRequest request);

    UpdateSupplierCommand updateSupplierRequestToUpdateSupplierCommand(
            UpdateSupplierRequest request);

    @Mapping(target = "updatedDateTime", expression = "java(java.time.Instant.now())")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromCommand(@MappingTarget Supplier supplier, UpdateSupplierCommand command);
}