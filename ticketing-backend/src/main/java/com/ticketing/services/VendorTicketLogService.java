package com.ticketing.services;

import com.ticketing.entities.VendorTicketLog;
import com.ticketing.repositories.VendorTicketLogRepository;
import com.ticketing.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VendorTicketLogService {

    private final VendorTicketLogRepository logRepository;
    private final VendorRepository vendorRepository; // For manual validation of vendor IDs

    @Autowired
    public VendorTicketLogService(VendorTicketLogRepository logRepository, VendorRepository vendorRepository) {
        this.logRepository = logRepository;
        this.vendorRepository = vendorRepository;
    }

    public void logAddition(Long vendorId, int ticketsAdded) {
        if (!vendorRepository.existsById(vendorId)) {
            throw new IllegalArgumentException("Vendor with ID " + vendorId + " does not exist");
        }

        VendorTicketLog log = new VendorTicketLog(vendorId, ticketsAdded, LocalDateTime.now());
        logRepository.save(log);
    }
}
