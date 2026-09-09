package com.ticketing.services;

import com.ticketing.entities.Vendor;
import com.ticketing.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public Vendor registerVendor(Vendor vendor) {
        // Check if email already exists
        if (!vendorRepository.findAll()
                .stream()
                .noneMatch(c -> c.getEmail().equals(vendor.getEmail()))) {
            throw new IllegalArgumentException("Email already registered.");
        }

        vendor.setPassword(bCryptPasswordEncoder.encode(vendor.getPassword()));

        // Save the new vendor
        return vendorRepository.save(vendor);
    }

    public Vendor loginVendor(String email, String password) {
        // Retrieve vendor by email
        Vendor vendor = vendorRepository.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        if (!bCryptPasswordEncoder.matches(password, vendor.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        return vendor;
    }

    // Retrieve all customers
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll(); // Fetch all vendors from the repository
    }

    public Vendor getVendorById(Long vendorId) {
        // Retrieve vendor by ID
        return vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found with ID: " + vendorId));
    }

    public Vendor updateVendor(Long vendorId, Vendor vendorDetails) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found."));

        // Update fields as needed
        vendor.setEmail(vendorDetails.getEmail());
        vendor.setName(vendorDetails.getName());

        if (vendorDetails.getPassword() != null && !vendorDetails.getPassword().isBlank()) {
            vendor.setPassword(bCryptPasswordEncoder.encode(vendorDetails.getPassword()));
        }

        return vendorRepository.save(vendor);
    }

    // Delete a vendor account by ID
    public void deleteVendor(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found."));
        vendorRepository.delete(vendor);
    }
}
