package com.ticketing.repositories;

import com.ticketing.entities.CustomerTicketLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerTicketLogRepository extends JpaRepository<CustomerTicketLog, Long> {
}