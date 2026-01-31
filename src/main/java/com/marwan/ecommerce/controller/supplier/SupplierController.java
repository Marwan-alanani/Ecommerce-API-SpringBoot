package com.marwan.ecommerce.controller.supplier;

import com.marwan.ecommerce.controller.common.converter.BaseController;
import com.marwan.ecommerce.controller.supplier.request.CreateSupplierRequest;
import com.marwan.ecommerce.controller.supplier.request.UpdateSupplierRequest;
import com.marwan.ecommerce.dto.common.PageDto;
import com.marwan.ecommerce.dto.supplier.SupplierDto;
import com.marwan.ecommerce.dto.supplier.SupplierPagingOptions;
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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
public class SupplierController extends BaseController
{
    private final SupplierService supplierService;
    private final SupplierMapper supplierMapper;

    @PostMapping("/create")
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
        Supplier supplier = supplierService.get(supplierId, true);
        return ResponseEntity.ok(
                supplierMapper.supplierToSupplierDto(supplier)
        );
    }

    @GetMapping
    public ResponseEntity<PageDto<SupplierDto>> getAll(
            @Valid SupplierPagingOptions pagingOptions
    )
    {
        Page<Supplier> supplierPage = supplierService.getAll(pagingOptions);
        List<SupplierDto> supplierDtoList =
                supplierMapper.supplierListToSupplierDtoList(supplierPage.getContent());

        return ResponseEntity.ok(toPageDto(supplierPage, supplierDtoList));
    }

    @PutMapping("/update")
    public ResponseEntity<SupplierDto> update(@Valid @RequestBody UpdateSupplierRequest request)
            throws SupplierNameExistsException,
            SupplierEmailExistsException,
            SupplierNotFoundException
    {
        UpdateSupplierCommand command =
                supplierMapper.updateSupplierRequestToUpdateSupplierCommand(request);
        Supplier supplier = supplierService.update(command, true);
        return ResponseEntity.ok(supplierMapper.supplierToSupplierDto(supplier));
    }


    @DeleteMapping("/delete/{supplierId}")
    public ResponseEntity<?> deactivate(@PathVariable UUID supplierId)
            throws SupplierNotFoundException
    {
        supplierService.deactivate(supplierId, true);
        return ResponseEntity.ok().build();
    }
}
