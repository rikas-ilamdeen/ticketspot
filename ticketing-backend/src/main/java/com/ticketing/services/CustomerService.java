package com.ticketing.services;

import com.ticketing.entities.Customer;
import com.ticketing.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public Customer registerCustomer(Customer customer) {
        // Check if email already exists
        if (!customerRepository.findAll()
                .stream()
                .noneMatch(c -> c.getEmail().equals(customer.getEmail()))) {
            throw new IllegalArgumentException("Email already registered.");
        }

        customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));

        // Save the new customer
        return customerRepository.save(customer);
    }

    public Customer loginCustomer(String email, String password) {
        // Retrieve customer by email
        Customer customer = customerRepository.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        if (!bCryptPasswordEncoder.matches(password, customer.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        return customer;
    }

    // Retrieve all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll(); // Fetch all customers from the repository
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

        if (customerDetails.getPassword() != null && !customerDetails.getPassword().isBlank()) {
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
