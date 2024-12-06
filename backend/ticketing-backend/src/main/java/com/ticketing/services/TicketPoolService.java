package com.ticketing.services;

import com.ticketing.entities.Ticket;
import com.ticketing.entities.CustomerTicketLog;
import com.ticketing.entities.VendorTicketLog;
import com.ticketing.repositories.TicketRepository;
import com.ticketing.repositories.CustomerTicketLogRepository;
import com.ticketing.repositories.VendorTicketLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;
import java.util.List;

@Service
public class TicketPoolService {
    private final List<Ticket> ticketList = Collections.synchronizedList(new ArrayList<>());
    private final ReentrantLock lock = new ReentrantLock();

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CustomerTicketLogRepository customerTicketLogRepository;

    @Autowired
    private VendorTicketLogRepository vendorTicketLogRepository;

    // Method to add tickets by vendors
    public void addTickets(Long vendorId, int numberOfTickets) {
        lock.lock();
        try {
            Ticket ticket = ticketRepository.findFirstByOrderByIdAsc(); // Get the first ticket
            if (ticket == null) {
                System.out.println("No ticket pool available to add tickets.");
                return;
            }

            if (numberOfTickets > ticket.getTicketReleaseRate()) {
                System.out.println("You can't add more than " + ticket.getTicketReleaseRate() + " tickets.");
            } else if ((ticket.getTotalTickets() + numberOfTickets) <= ticket.getMaxTicketCapacity()) {
                ticket.setTotalTickets(ticket.getTotalTickets() + numberOfTickets);
                ticketRepository.save(ticket);

                VendorTicketLog vendorLog = new VendorTicketLog(vendorId, numberOfTickets, LocalDateTime.now());
                vendorTicketLogRepository.save(vendorLog);

                System.out.println("Added " + numberOfTickets + " tickets. Total: " + ticket.getTotalTickets());
            } else {
                System.out.println("Cannot add tickets. Max capacity reached.");
            }
        } finally {
            lock.unlock();
        }
    }

    // Method to purchase tickets by customers
    public Ticket purchaseTicket(Long customerId, int ticketPurchaseCount) {
        lock.lock();
        try {
            Ticket ticket = ticketRepository.findFirstByOrderByIdAsc(); // Get the first ticket
            if (ticket == null) {
                System.out.println("No tickets available for purchase.");
                return null;
            }

            if (ticketPurchaseCount > ticket.getCustomerRetrievalRate()) {
                System.out.println("You can't buy more than " + ticket.getCustomerRetrievalRate() + " tickets.");
                return null;
            } else if (ticket.getTotalTickets() >= ticketPurchaseCount) {
                ticket.setTotalTickets(ticket.getTotalTickets() - ticketPurchaseCount);
                ticketRepository.save(ticket);

                CustomerTicketLog customerLog = new CustomerTicketLog(customerId, ticketPurchaseCount, LocalDateTime.now());
                customerTicketLogRepository.save(customerLog);

                System.out.println("Ticket purchased successfully by Customer " + customerId + ".");
                return ticket;
            } else {
                System.out.println("Not enough tickets available. Only " + ticket.getTotalTickets() + " left.");
                return null;
            }
        } finally {
            lock.unlock();
        }
    }
}
