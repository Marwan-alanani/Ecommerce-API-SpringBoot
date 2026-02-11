package com.marwan.ecommerce.controller.supplier;

import com.marwan.ecommerce.controller.supplier.request.CreateSupplierRequest;
import com.marwan.ecommerce.controller.supplier.request.UpdateSupplierRequest;
import com.marwan.ecommerce.dto.common.PageDto;
import com.marwan.ecommerce.dto.supplier.SupplierDto;
import com.marwan.ecommerce.exception.supplier.SupplierEmailExistsException;
import com.marwan.ecommerce.exception.supplier.SupplierNotFoundException;
import com.marwan.ecommerce.exception.supplier.SupplierNameExistsException;
import com.marwan.ecommerce.mapper.SupplierMapper;
import com.marwan.ecommerce.model.entity.Supplier;
import com.marwan.ecommerce.service.supplier.SupplierService;
import com.marwan.ecommerce.service.supplier.command.CreateSupplierCommand;
import com.marwan.ecommerce.service.supplier.command.UpdateSupplierCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.marwan.ecommerce.controller.common.BaseController.toPageDto;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
public class SupplierController
{
    private final SupplierService supplierService;
    private final SupplierMapper supplierMapper;

    @PostMapping
    public ResponseEntity<SupplierDto> create(@Valid @RequestBody CreateSupplierRequest request)
            throws SupplierEmailExistsException, SupplierNameExistsException
    {
        CreateSupplierCommand command =
                supplierMapper.createSupplierRequestToCreateSupplierCommand(request);
        Supplier supplier = supplierService.createSupplier(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                supplierMapper.supplierToSupplierDto(supplier)
        );
    }

    @GetMapping("/{supplierId}")
    public ResponseEntity<SupplierDto> get(@PathVariable UUID supplierId)
            throws SupplierNotFoundException
    {
        Supplier supplier = supplierService.get(supplierId);
        return ResponseEntity.ok(
                supplierMapper.supplierToSupplierDto(supplier)
        );
    }

    @GetMapping
    public ResponseEntity<PageDto<SupplierDto>> getAll(
            @ParameterObject @PageableDefault(sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) Boolean isEnabled
    )
    {
        Page<Supplier> supplierPage = supplierService.getAll(pageable, isEnabled);
        List<SupplierDto> supplierDtoList =
                supplierMapper.supplierListToSupplierDtoList(supplierPage.getContent());

        return ResponseEntity.ok(toPageDto(supplierPage, supplierDtoList));
    }

    @PatchMapping
    public ResponseEntity<SupplierDto> update(@Valid @RequestBody UpdateSupplierRequest request)
            throws SupplierNameExistsException,
            SupplierEmailExistsException,
            SupplierNotFoundException
    {
        UpdateSupplierCommand command =
                supplierMapper.updateSupplierRequestToUpdateSupplierCommand(request);
        Supplier supplier = supplierService.update(command);
        return ResponseEntity.ok(supplierMapper.supplierToSupplierDto(supplier));
    }


    @DeleteMapping("/{supplierId}")
    public ResponseEntity<?> deactivate(@PathVariable UUID supplierId)
            throws SupplierNotFoundException
    {
        supplierService.deactivate(supplierId);
        return ResponseEntity.ok().build();
    }
}
