package com.gksvp.web.company.service;



import com.gksvp.web.company.entity.Supplier;
import com.gksvp.web.company.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(Long supplierId) {
        return supplierRepository.findById(supplierId).orElse(null);
    }

    public Supplier createSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(Long supplierId, Supplier updatedSupplier) {
        if (supplierRepository.existsById(supplierId)) {
            updatedSupplier.setName(updatedSupplier.getName());
            return supplierRepository.save(updatedSupplier);
        }
        return null; // Handle non-existing supplier
    }

    public void deleteSupplier(Long supplierId) {
        supplierRepository.deleteById(supplierId);
    }

    // Add other business logic methods as needed
}
