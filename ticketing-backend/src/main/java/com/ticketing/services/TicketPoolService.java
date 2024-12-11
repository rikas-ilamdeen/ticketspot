package com.ticketing.services;

import com.ticketing.entities.Ticket;
import com.ticketing.entities.CustomerTicketLog;
import com.ticketing.entities.VendorTicketLog;
import com.ticketing.repositories.TicketRepository;
import com.ticketing.repositories.CustomerLogRepository;
import com.ticketing.repositories.VendorLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.List;

@Service
public class TicketPoolService {
    private final List<Ticket> ticketList = Collections.synchronizedList(new ArrayList<>());

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CustomerLogRepository customerLogRepository;
    @Autowired
    private VendorLogRepository vendorLogRepository;
    private final ConcurrentLinkedQueue<Ticket> ticketQueue = new ConcurrentLinkedQueue<>();

    private final ReentrantLock lock = new ReentrantLock();
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    // Method to add tickets by vendors
    public String addTickets(Long vendorId, int numberOfTickets) {
        lock.lock();
        try {
            List<Ticket> ticketList = ticketRepository.findAll();
            Ticket ticket = ticketList.isEmpty() ? null : ticketList.get(0);

            String error;
            if(numberOfTickets > ticket.getTicketReleaseRate()){
                error = "You can't add more than "+ticket.getTicketReleaseRate()+" tickets.";
                System.out.println(error);
            }
            else if (ticket != null && (ticket.getTotalTickets() + numberOfTickets) <= ticket.getMaxTicketCapacity()) {
                ticket.setTotalTickets(ticket.getTotalTickets() + numberOfTickets);
                ticketRepository.save(ticket);
                sendEventUpdate(ticket);
                VendorTicketLog vendorTicketLog = new VendorTicketLog();
                vendorTicketLog.setTicketId(ticket.getTicketId());
                vendorTicketLog.setAddedTicketCount(numberOfTickets);
                vendorTicketLog.setVendorId(vendorId);
                vendorTicketLog.setAddedDate(LocalDateTime.now());
                vendorLogRepository.save(vendorTicketLog);

                error = "Added " + numberOfTickets + " tickets.";
                System.out.println(error);
            }
            else {
                error = "Cannot add tickets. Max capacity reached.";
                System.out.println(error);
            }
            return error;
        } finally {
            lock.unlock();
        }
    }
    public void sendEventUpdate(Ticket event) {
        // Send updated event data to the WebSocket topic
        messagingTemplate.convertAndSend("/topic/event", event);
    }
    // Method to purchase a ticket by customers
    public String purchaseTicket(Long customerId, int ticketPurchaseCount) {
        lock.lock();
        try {
            List<Ticket> ticketList = ticketRepository.findAll();
            Ticket ticket = ticketList.isEmpty() ? null : ticketList.get(0);

            String error;
            if(ticketPurchaseCount > ticket.getCustomerRetrievalRate()){
                error = "You can't get more than "+ticket.getCustomerRetrievalRate()+" tickets.";
                System.out.println(error);
            }
            else if (ticket != null && (ticket.getTotalTickets() > ticketPurchaseCount)) {
                ticket.setTotalTickets(ticket.getTotalTickets()-ticketPurchaseCount);
                //ticket.setAvailable(false);
                ticketRepository.save(ticket);
                sendEventUpdate(ticket);
                System.out.println("Ticket purchased for event: " + ticket.getEventName());
                CustomerTicketLog customerTicketLog = new CustomerTicketLog();
                customerTicketLog.setBoughtTicketCount(ticketPurchaseCount);
                customerTicketLog.setTicketId(ticket.getTicketId());
                customerTicketLog.setTotalAmount(ticket.getPrice() * ticketPurchaseCount);
                customerTicketLog.setBookingDate(LocalDateTime.now());
                customerTicketLog.setCustomerId(customerId);
                customerLogRepository.save(customerTicketLog);

                error = "Booked " + ticketPurchaseCount + " tickets.";
                System.out.println(error);

            } else if (ticket.getTotalTickets() > 0) {
                error = "Only "+ ticket.getTotalTickets() +" ticket/tickets available for the event: ";
                System.out.println(error);
            } else {
                error = "No tickets available for event: " + ticket.getEventName();
                System.out.println(error);
            }
            return error;
        } finally {
            lock.unlock();
        }
    }
}
