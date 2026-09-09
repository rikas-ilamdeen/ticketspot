package com.ticketing.services;

import com.ticketing.entities.Customer;
import com.ticketing.entities.Vendor;
import com.ticketing.repositories.CustomerRepository;
import com.ticketing.repositories.VendorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private VendorRepository vendorRepository;
    @InjectMocks
    private CustomerService customerService;
    @InjectMocks
    private VendorService vendorService;

    @Test
    void customerSignupHashesPasswordAndLoginChecksIt() {
        Customer customer = new Customer(null, "Customer", "customer@example.com", "secret123", "0712345678");
        when(customerRepository.findAll()).thenReturn(List.of());
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Customer saved = customerService.registerCustomer(customer);

        assertTrue(saved.getPassword().startsWith("$2"));
        verify(customerRepository).save(customer);
        when(customerRepository.findAll()).thenReturn(List.of(saved));
        assertDoesNotThrow(() -> customerService.loginCustomer(saved.getEmail(), "secret123"));
        assertThrows(IllegalArgumentException.class, () -> customerService.loginCustomer(saved.getEmail(), "wrong"));
    }

    @Test
    void vendorSignupHashesPasswordAndLoginChecksIt() {
        Vendor vendor = new Vendor(null, "vendor@example.com", "Vendor", "secret123", "0712345678");
        when(vendorRepository.findAll()).thenReturn(List.of());
        when(vendorRepository.save(any(Vendor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Vendor saved = vendorService.registerVendor(vendor);

        assertTrue(saved.getPassword().startsWith("$2"));
        verify(vendorRepository).save(vendor);
        when(vendorRepository.findAll()).thenReturn(List.of(saved));
        assertDoesNotThrow(() -> vendorService.loginVendor(saved.getEmail(), "secret123"));
        assertThrows(IllegalArgumentException.class, () -> vendorService.loginVendor(saved.getEmail(), "wrong"));
    }
}