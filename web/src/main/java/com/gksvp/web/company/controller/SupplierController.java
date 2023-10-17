package com.gksvp.web.company.controller;

import com.gksvp.web.company.entity.Supplier;
import com.gksvp.web.company.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public List<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{supplierId}")
    public Supplier getSupplierById(@PathVariable Long supplierId) {
        return supplierService.getSupplierById(supplierId);
    }

    @PostMapping
    public Supplier createSupplier(@RequestBody Supplier supplier) {
        return supplierService.createSupplier(supplier);
    }

    @PutMapping("/{supplierId}")
    public Supplier updateSupplier(@PathVariable Long supplierId, @RequestBody Supplier updatedSupplier) {
        return supplierService.updateSupplier(supplierId, updatedSupplier);
    }

    @DeleteMapping("/{supplierId}")
    public void deleteSupplier(@PathVariable Long supplierId) {
        supplierService.deleteSupplier(supplierId);
    }

    // Add other endpoints as needed
}
