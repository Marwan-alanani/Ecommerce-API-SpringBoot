package com.marwan.ecommerce.service.supplier;

import com.marwan.ecommerce.exception.supplier.SupplierEmailExistsException;
import com.marwan.ecommerce.exception.supplier.SupplierIdNotFoundException;
import com.marwan.ecommerce.exception.supplier.SupplierNameExistsException;
import com.marwan.ecommerce.mapper.SupplierMapper;
import com.marwan.ecommerce.model.entity.Supplier;
import com.marwan.ecommerce.repository.SupplierRepository;
import com.marwan.ecommerce.service.supplier.command.CreateSupplierCommand;
import com.marwan.ecommerce.service.supplier.command.UpdateSupplierCommand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SupplierService
{
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    public Supplier create(CreateSupplierCommand command)
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
    public Supplier get(UUID supplierId)
            throws SupplierIdNotFoundException
    {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(supplierId);
        if (optionalSupplier.isEmpty()) {
            throw new SupplierIdNotFoundException(supplierId);
        }
        return optionalSupplier.get();
    }

    public boolean supplierExists(UUID supplierId)
    {
        if (supplierRepository.existsById(supplierId)) {
            return true;
        }
        return false;
    }

    public List<Supplier> getAll()
    {
        return supplierRepository.findAll();
    }

    public Supplier update(UpdateSupplierCommand command)
            throws SupplierIdNotFoundException, SupplierNameExistsException,
            SupplierEmailExistsException
    {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(command.supplierId());
        if (optionalSupplier.isEmpty()) {
            throw new SupplierIdNotFoundException(command.supplierId());
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

    public void delete(UUID supplierId)
            throws SupplierIdNotFoundException
    {
        if (!supplierRepository.existsById(supplierId)) {
            throw new SupplierIdNotFoundException(supplierId);
        }
        supplierRepository.deleteById(supplierId);

    }
}
