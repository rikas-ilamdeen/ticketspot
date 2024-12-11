package com.ticketing.controllers;

import com.ticketing.entities.BuyTicketData;
import com.ticketing.entities.Customer;
import com.ticketing.services.CustomerService;
import com.ticketing.services.TicketPoolService;
import com.ticketing.threads.CustomerThread;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@RestController
@RequestMapping("/api/customer")
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
            return new ResponseEntity<>(customer, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(@RequestBody Customer loginRequest) {
        try {
            HashMap<String,String> test = new HashMap<>();
            Customer message = customerService.loginCustomer(loginRequest.getEmail(), loginRequest.getPassword());
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
    public ResponseEntity<?> getCustomer(@PathVariable Long customerId) {
        try {
            Customer customer = customerService.getCustomerById(customerId);
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

    @GetMapping("/")
    public ResponseEntity<?> getAllCustomers() {
        try {
            // Fetch all customers using the customer service
            List<Customer> customers = customerService.getAllCustomers();
            return ResponseEntity.ok(customers); // Return the list of customers
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving customers: " + e.getMessage());
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
    @PostMapping("/buy")
    public ResponseEntity<?> purchaseTicket(@RequestBody BuyTicketData buyTicketData) {
        BlockingQueue<String> result = new ArrayBlockingQueue<>(1);
        HashMap<String,String> test = new HashMap<>();
        // Create a new CustomerThread with the received data
        BuyTicketData data = buyTicketData;
        CustomerThread customerThread = new CustomerThread(ticketPoolService, data.getCustomerId(), data.getTicketPurchaseCount(), result::offer);

        // Start the thread
        new Thread(customerThread).start();

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
