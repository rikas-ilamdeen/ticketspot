package com.ticketing.services;

import com.ticketing.entities.CustomerTicketLog;
import com.ticketing.repositories.CustomerTicketLogRepository;
import com.ticketing.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerTicketLogService {

    private final CustomerTicketLogRepository logRepository;
    private final CustomerRepository customerRepository; // For manual validation of customer IDs

    @Autowired
    public CustomerTicketLogService(CustomerTicketLogRepository logRepository, CustomerRepository customerRepository) {
        this.logRepository = logRepository;
        this.customerRepository = customerRepository;
    }

    public void logPurchase(Long customerId, int ticketsPurchased) {
        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " does not exist");
        }

        CustomerTicketLog log = new CustomerTicketLog(customerId, ticketsPurchased, LocalDateTime.now());
        logRepository.save(log);
    }
}
