package com.ticketing.controllers;

import com.ticketing.entities.Vendor;
import com.ticketing.services.TicketPoolService;
import com.ticketing.services.VendorService;
import com.ticketing.threads.VendorThread;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private TicketPoolService ticketPoolService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerVendor(@Valid @RequestBody Vendor vendor) {
        try {
            Vendor registeredVendor = vendorService.registerVendor(vendor);
            return ResponseEntity.ok(registeredVendor);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginVendor(@RequestBody Vendor loginRequest) {
        try {
            String message = vendorService.loginVendor(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok().body(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVendor(@PathVariable Long id) {
        try {
            Vendor vendor = vendorService.getVendorById(id);
            return ResponseEntity.ok(vendor);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVendor(@PathVariable Long id, @RequestBody Vendor vendorDetails) {
        try {
            Vendor updatedVendor = vendorService.updateVendor(id, vendorDetails);
            return ResponseEntity.ok(updatedVendor);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVendor(@PathVariable Long id) {
        try {
            vendorService.deleteVendor(id);
            return ResponseEntity.ok().body("Vendor deleted successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint to add tickets by vendor
    @PostMapping("/addTickets/{vendorId}/{numberOfTickets}")
    public ResponseEntity<?> addTickets(@PathVariable Long vendorId, @PathVariable int numberOfTickets) {
        try {
            // Create a new VendorThread with the received data
            VendorThread vendorThread = new VendorThread(ticketPoolService, vendorId, numberOfTickets);

            // Start the thread
            new Thread(vendorThread).start();

            return ResponseEntity.ok("Vendor thread started to add tickets.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to start the vendor thread: " + e.getMessage());
        }
    }
}
