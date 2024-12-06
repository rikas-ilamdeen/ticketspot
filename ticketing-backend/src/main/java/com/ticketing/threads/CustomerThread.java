package com.ticketing.threads;

import com.ticketing.entities.Ticket;
import com.ticketing.services.TicketPoolService;

public class CustomerThread implements Runnable {
    private final TicketPoolService ticketPoolService;
    private final Long customerId;
    private final int ticketPurchaseCount;

    public CustomerThread(TicketPoolService ticketPoolService, Long customerId, int ticketPurchaseCount) {
        this.ticketPoolService = ticketPoolService;
        this.customerId = customerId;
        this.ticketPurchaseCount = ticketPurchaseCount;
    }

    @Override
    public void run() {
        try {
            Ticket ticket = ticketPoolService.purchaseTicket(customerId, ticketPurchaseCount);
            if (ticket != null) {
                System.out.println("Customer " + customerId + " purchased " + ticketPurchaseCount + " tickets.");
            } else {
                System.out.println("Customer " + customerId + " failed to purchase tickets.");
            }
            Thread.sleep(2000); // Simulating delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
