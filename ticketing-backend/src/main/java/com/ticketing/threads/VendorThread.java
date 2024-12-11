package com.ticketing.threads;

import com.ticketing.services.TicketPoolService;

import java.util.function.Consumer;

public class VendorThread implements Runnable {
    private final TicketPoolService ticketPoolService;
    private final Long vendorId;
    private final int numberOfTickets;
    private final Consumer<String> callback;

    public VendorThread(TicketPoolService ticketPoolService, Long vendorId, int numberOfTickets, Consumer<String> callback) {
        this.ticketPoolService = ticketPoolService;
        this.vendorId = vendorId;
        this.numberOfTickets = numberOfTickets;
        this.callback = callback;
    }

    @Override
    public void run() {
        try {
            // Simulate ticket addition by vendor
            String error = ticketPoolService.addTickets(vendorId, numberOfTickets);
            callback.accept(error);
            Thread.sleep(5000); // Simulate delay between adding tickets
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
