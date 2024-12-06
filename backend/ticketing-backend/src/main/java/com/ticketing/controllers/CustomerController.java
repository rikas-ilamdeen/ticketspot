package com.ticketing.controllers;

import com.ticketing.entities.Customer;
import com.ticketing.services.CustomerService;
import com.ticketing.services.TicketPoolService;
import com.ticketing.threads.CustomerThread;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TicketPoolService ticketPoolService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation error: " + bindingResult.getFieldError().getDefaultMessage());
        }
        try {
            Customer savedCustomer = customerService.registerCustomer(customer);
            return ResponseEntity.ok().body("Customer registered successfully! ID: " + savedCustomer.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(@RequestBody Customer loginRequest) {
        try {
            String message = customerService.loginCustomer(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok().body(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable Long id) {
        try {
            Customer customer = customerService.getCustomerById(id);
            return ResponseEntity.ok().body(customer);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        try {
            Customer updatedCustomer = customerService.updateCustomer(id, customer);
            return ResponseEntity.ok().body("Customer updated successfully! ID: " + updatedCustomer.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.ok().body("Customer deleted successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint to purchase tickets by customer
    @PostMapping("/buy/{customerId}/{ticketPurchaseCount}")
    public ResponseEntity<?> purchaseTicket(@PathVariable Long customerId, @PathVariable int ticketPurchaseCount) {
        try {
            // Create a new CustomerThread with the received data
            CustomerThread customerThread = new CustomerThread(ticketPoolService, customerId, ticketPurchaseCount);

            // Start the thread
            new Thread(customerThread).start();

            return ResponseEntity.ok("Customer thread started to purchase tickets.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to start the customer thread: " + e.getMessage());
        }
    }
}
