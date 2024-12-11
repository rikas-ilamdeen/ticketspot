package com.ticketing.threads;

import com.ticketing.entities.Ticket;
import com.ticketing.services.TicketPoolService;

import java.util.function.Consumer;

public class CustomerThread implements Runnable {
    private final TicketPoolService ticketPoolService;
    private final Long customerId;
    private final int ticketPurchaseCount;
    private final Consumer<String> callback;

    public CustomerThread(TicketPoolService ticketPoolService, Long customerId, int ticketPurchaseCount, Consumer<String> callback) {
        this.ticketPoolService = ticketPoolService;
        this.customerId = customerId;
        this.ticketPurchaseCount = ticketPurchaseCount;
        this.callback = callback;
    }

    @Override
    public void run() {
        try {
            // Simulate ticket purchase by customer
            String error = ticketPoolService.purchaseTicket(customerId, ticketPurchaseCount);
            callback.accept(error);
            Thread.sleep(2000); // Simulate delay between customer purchases
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
