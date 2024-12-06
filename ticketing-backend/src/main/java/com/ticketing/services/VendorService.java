package com.ticketing.services;

import com.ticketing.entities.Customer;
import com.ticketing.entities.Vendor;
import com.ticketing.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public Vendor registerVendor(Vendor vendor) {
        // Check if email already exists
        if (vendorRepository.findByEmail(vendor.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered.");
        }

        // Hash password before saving
        vendor.setPassword(bCryptPasswordEncoder.encode(vendor.getPassword()));

        // Save the new vendor
        return vendorRepository.save(vendor);
    }

    public String loginVendor(String email, String password) {
        // Retrieve vendor by email
        Vendor vendor = vendorRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        // Verify the hashed password
        if (!bCryptPasswordEncoder.matches(password, vendor.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        return "Login successful!";
    }

    public Vendor getVendorById(Long vendorId) {
        // Retrieve vendor by ID
        return vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found with ID: " + vendorId));
    }

    public Vendor updateVendor(Long id, Vendor vendorDetails) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found."));

        // Update fields as needed
        vendor.setEmail(vendorDetails.getEmail());
        vendor.setName(vendorDetails.getName());

        // Hash the password if it's updated
        if (!vendorDetails.getPassword().equals(vendor.getPassword())) {
            vendor.setPassword(bCryptPasswordEncoder.encode(vendorDetails.getPassword()));
        }

        return vendorRepository.save(vendor);
    }

    // Delete a vendor account by ID
    public void deleteVendor(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found."));
        vendorRepository.delete(vendor);
    }
}
