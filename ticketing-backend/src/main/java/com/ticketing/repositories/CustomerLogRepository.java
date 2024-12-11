package com.ticketing.repositories;

import com.ticketing.entities.CustomerTicketLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerLogRepository extends JpaRepository<CustomerTicketLog, Long> {
}