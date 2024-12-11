package com.ticketing.repositories;

import com.ticketing.entities.VendorTicketLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorLogRepository extends JpaRepository<VendorTicketLog, Long> {
}
