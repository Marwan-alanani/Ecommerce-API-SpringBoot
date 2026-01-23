package com.marwan.ecommerce.controller.supplier;

import com.marwan.ecommerce.controller.supplier.request.CreateSupplierRequest;
import com.marwan.ecommerce.controller.supplier.request.UpdateSupplierRequest;
import com.marwan.ecommerce.dto.supplier.SupplierDto;
import com.marwan.ecommerce.exception.supplier.SupplierEmailExistsException;
import com.marwan.ecommerce.exception.supplier.SupplierIdNotFoundException;
import com.marwan.ecommerce.exception.supplier.SupplierNameExistsException;
import com.marwan.ecommerce.mapper.SupplierMapper;
import com.marwan.ecommerce.model.entity.Supplier;
import com.marwan.ecommerce.service.supplier.SupplierService;
import com.marwan.ecommerce.service.supplier.command.CreateSupplierCommand;
import com.marwan.ecommerce.service.supplier.command.UpdateSupplierCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
public class SupplierController
{
    private final SupplierService supplierService;
    private final SupplierMapper supplierMapper;

    @PostMapping("/create")
    public ResponseEntity<SupplierDto> create(@RequestBody CreateSupplierRequest request)
            throws SupplierEmailExistsException, SupplierNameExistsException
    {
        CreateSupplierCommand command =
                supplierMapper.createSupplierRequestToCreateSupplierCommand(request);
        Supplier supplier = supplierService.create(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                supplierMapper.supplierToSupplierDto(supplier)
        );
    }

    @GetMapping("/{supplierId}")
    public ResponseEntity<SupplierDto> get(@PathVariable UUID supplierId)
            throws SupplierIdNotFoundException
    {
        Supplier supplier = supplierService.get(supplierId);
        return ResponseEntity.ok(
                supplierMapper.supplierToSupplierDto(supplier)
        );
    }

    @GetMapping
    public ResponseEntity<List<SupplierDto>> getAll()
    {
        List<Supplier> supplierList = supplierService.getAll();
        List<SupplierDto> supplierDtoList =
                supplierMapper.supplierListToSupplierDtoList(supplierList);

        return ResponseEntity.ok(supplierDtoList);
    }

    @PostMapping("/update")
    public ResponseEntity<SupplierDto> update(@RequestBody UpdateSupplierRequest request)
            throws SupplierNameExistsException,
            SupplierEmailExistsException,
            SupplierIdNotFoundException
    {
        UpdateSupplierCommand command =
                supplierMapper.updateSupplierRequestToUpdateSupplierCommand(request);
        Supplier supplier = supplierService.update(command);
        return ResponseEntity.ok(supplierMapper.supplierToSupplierDto(supplier));
    }


    @PostMapping("/delete/{supplierId}")
    public ResponseEntity<?> deactivate(@PathVariable UUID supplierId)
            throws SupplierIdNotFoundException
    {
        supplierService.deactivate(supplierId);
        return ResponseEntity.ok().build();
    }
}
