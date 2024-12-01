package com.ticketing.services;

import com.ticketing.entities.Customer;
import com.ticketing.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public Customer registerCustomer(Customer customer) {
        // Check if email already exists
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered.");
        }

        // Hash password before saving
        customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));

        // Save the new customer
        return customerRepository.save(customer);
    }

    public String loginCustomer(String email, String password) {
        // Retrieve customer by email
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        // Verify the hashed password
        if (!bCryptPasswordEncoder.matches(password, customer.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        return "Login successful!";
    }

    // Get a customer by their ID
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found."));
    }

    // Update customer information by ID
    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found."));

        // Update fields as needed
        customer.setEmail(customerDetails.getEmail());
        customer.setName(customerDetails.getName());

        // Hash the password if it's updated
        if (!customerDetails.getPassword().equals(customer.getPassword())) {
            customer.setPassword(bCryptPasswordEncoder.encode(customerDetails.getPassword()));
        }

        return customerRepository.save(customer);
    }


    // Delete a customer account by ID
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found."));
        customerRepository.delete(customer);
    }
}
