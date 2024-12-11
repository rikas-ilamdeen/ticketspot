package com.ticketing.controllers;

import com.ticketing.entities.AddTicketData;
import com.ticketing.entities.Customer;
import com.ticketing.entities.Vendor;
import com.ticketing.services.TicketPoolService;
import com.ticketing.services.VendorService;
import com.ticketing.threads.VendorThread;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private TicketPoolService ticketPoolService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerVendor(@Valid @RequestBody Vendor vendor) {
        try {
            Vendor registeredVendor = vendorService.registerVendor(vendor);
            return new ResponseEntity<>(registeredVendor, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginVendor(@RequestBody Vendor loginRequest) {
        try {
            HashMap<String,String> test = new HashMap<>();
            Vendor message = vendorService.loginVendor(loginRequest.getEmail(), loginRequest.getPassword());
            if(message!=null){
                return ResponseEntity.ok(message);
            } else{
                test.put("message","invalid email or password");
                return ResponseEntity.ok().body(test);
            }
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

    @GetMapping("/")
    public ResponseEntity<?> getAllVendors() {
        try {
            // Fetch all vendors using the vendor service
            List<Vendor> vendors = vendorService.getAllVendors();
            return ResponseEntity.ok(vendors); // Return the list of vendors
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving vendors: " + e.getMessage());
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
    @PostMapping("/addTickets")
    public ResponseEntity<?> addTickets(@RequestBody AddTicketData addTicketData) {
        BlockingQueue<String> result = new ArrayBlockingQueue<>(1);
        HashMap<String,String> test = new HashMap<>();

        // Create a new VendorThread with the received data
        AddTicketData data = addTicketData;
        VendorThread vendorThread = new VendorThread(ticketPoolService, data.getVendorId(), data.getNumberOfTickets(), result::offer);

        // Start the thread
        new Thread(vendorThread).start();

        try{
            String mes = result.take();
            test.put("message", mes);
            return ResponseEntity.ok(test);
        }catch (Exception e){
            return ResponseEntity.ok(test.put("message", "error"));
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of());
        }
    }
}
