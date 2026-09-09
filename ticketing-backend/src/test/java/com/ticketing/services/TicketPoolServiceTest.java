package com.ticketing.services;

import com.ticketing.entities.Ticket;
import com.ticketing.repositories.CustomerLogRepository;
import com.ticketing.repositories.TicketRepository;
import com.ticketing.repositories.VendorLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;

@ExtendWith(MockitoExtension.class)
class TicketPoolServiceTest {

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private CustomerLogRepository customerLogRepository;
    @Mock
    private VendorLogRepository vendorLogRepository;
    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private TicketPoolService ticketPoolService;

    @Test
    void addTicketsReportsMissingConfiguration() {
        when(ticketRepository.findAll()).thenReturn(List.of());

        assertEquals("Ticket configuration is not available.", ticketPoolService.addTickets(1L, 1));
        verify(ticketRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void operationsRejectZeroAndNegativeQuantities() {
        assertEquals("Ticket quantity must be greater than zero.", ticketPoolService.addTickets(1L, 0));
        assertEquals("Ticket quantity must be greater than zero.", ticketPoolService.purchaseTicket(1L, -1));
        verify(ticketRepository, never()).findAll();
    }

    @Test
    void purchaseAllowsExactlyAllRemainingTickets() {
        Ticket ticket = ticket(2, 5, 10);
        when(ticketRepository.findAll()).thenReturn(List.of(ticket));

        assertEquals("Purchased 2 tickets.", ticketPoolService.purchaseTicket(1L, 2));
        assertEquals(0, ticket.getTotalTickets());
        verify(ticketRepository).save(ticket);
    }

    @Test
    void purchaseReportsWhenRequestedQuantityExceedsAvailability() {
        Ticket ticket = ticket(2, 5, 10);
        when(ticketRepository.findAll()).thenReturn(List.of(ticket));

        assertEquals("Only 2 tickets available for the event: Test Event", ticketPoolService.purchaseTicket(1L, 3));
        verify(ticketRepository, never()).save(ticket);
    }

    private Ticket ticket(int totalTickets, int retrievalRate, int releaseRate) {
        Ticket ticket = new Ticket();
        ticket.setTicketId(1L);
        ticket.setEventName("Test Event");
        ticket.setTotalTickets(totalTickets);
        ticket.setCustomerRetrievalRate(retrievalRate);
        ticket.setTicketReleaseRate(releaseRate);
        ticket.setMaxTicketCapacity(20);
        ticket.setPrice(10.0);
        return ticket;
    }
}