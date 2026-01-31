package com.marwan.ecommerce.service.supplier;

import com.marwan.ecommerce.dto.supplier.SupplierPagingOptions;
import com.marwan.ecommerce.exception.supplier.SupplierEmailExistsException;
import com.marwan.ecommerce.exception.supplier.SupplierNotFoundException;
import com.marwan.ecommerce.exception.supplier.SupplierNameExistsException;
import com.marwan.ecommerce.mapper.SupplierMapper;
import com.marwan.ecommerce.model.entity.Supplier;
import com.marwan.ecommerce.repository.SupplierRepository;
import com.marwan.ecommerce.service.common.BaseService;
import com.marwan.ecommerce.service.supplier.command.CreateSupplierCommand;
import com.marwan.ecommerce.service.supplier.command.UpdateSupplierCommand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupplierService extends BaseService
{
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    @Transactional
    public Supplier createSupplier(CreateSupplierCommand command)
            throws SupplierEmailExistsException, SupplierNameExistsException
    {
        if (supplierRepository.existsByEmail(command.email())) {
            throw new SupplierEmailExistsException(command.email());
        }

        if (supplierRepository.existsByName(command.name())) {
            throw new SupplierNameExistsException(command.name());
        }
        Supplier supplier = Supplier.create(command.name(), command.email());
        supplierRepository.save(supplier);
        return supplier;
    }

    public Supplier get(UUID supplierId, boolean isEnabled)
            throws SupplierNotFoundException
    {
        Optional<Supplier> optionalSupplier = supplierRepository
                .findBySupplierIdAndIsEnabled(supplierId, isEnabled);

        if (optionalSupplier.isEmpty()) {
            throw new SupplierNotFoundException(supplierId);
        }
        return optionalSupplier.get();
    }

    public boolean supplierExists(UUID supplierId, boolean isEnabled)
    {
        return supplierRepository.existsBySupplierIdAndIsEnabled(supplierId, isEnabled);
    }

    public Page<Supplier> getAll(SupplierPagingOptions pagingOptions)
    {
        var pageable = constructPageable(pagingOptions);
        return supplierRepository.findAll(pageable);
    }

    @Transactional
    public Supplier update(UpdateSupplierCommand command, boolean isEnabled)
            throws SupplierNotFoundException, SupplierNameExistsException,
            SupplierEmailExistsException
    {
        Optional<Supplier> optionalSupplier = supplierRepository
                .findBySupplierIdAndIsEnabled(command.supplierId(), isEnabled);

        if (optionalSupplier.isEmpty()) {
            throw new SupplierNotFoundException(command.supplierId());
        }
        Supplier supplier = optionalSupplier.get();

        int countMail = supplierRepository.countByEmail(command.email());
        int countName = supplierRepository.countByName(command.name());

        if (countMail > 0 && !supplier.getEmail().equals(command.email())) {
            throw new SupplierEmailExistsException(command.email());
        }
        if (countName > 0 && !supplier.getName().equals(command.name())) {
            throw new SupplierNameExistsException(command.name());
        }

        supplierMapper.updateFromCommand(supplier, command);
        supplierRepository.save(supplier);
        return supplier;
    }

    @Transactional
    public void deactivate(UUID supplierId, boolean isEnabled)
            throws SupplierNotFoundException
    {
        Optional<Supplier> optionalSupplier = supplierRepository
                .findBySupplierIdAndIsEnabled(supplierId, isEnabled);

        if (optionalSupplier.isEmpty()) {
            throw new SupplierNotFoundException(supplierId);
        }
        Supplier supplier = optionalSupplier.get();
        supplier.setEnabled(false);
        supplierRepository.save(supplier);
    }
}
