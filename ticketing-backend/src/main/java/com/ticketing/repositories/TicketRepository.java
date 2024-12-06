package com.ticketing.repositories;

import com.ticketing.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findFirstByOrderByIdAsc();
}
