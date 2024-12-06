package com.ticketing.threads;

import com.ticketing.services.TicketPoolService;

public class VendorThread implements Runnable {
    private final TicketPoolService ticketPoolService;
    private final Long vendorId;
    private final int numberOfTickets;

    public VendorThread(TicketPoolService ticketPoolService, Long vendorId, int numberOfTickets) {
        this.ticketPoolService = ticketPoolService;
        this.vendorId = vendorId;
        this.numberOfTickets = numberOfTickets;
    }

    @Override
    public void run() {
        try {
            ticketPoolService.addTickets(vendorId, numberOfTickets);
            Thread.sleep(5000); // Simulating delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
