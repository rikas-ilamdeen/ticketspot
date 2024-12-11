package com.ticketing.config;

import com.ticketing.entities.Ticket;
import com.ticketing.services.TicketService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.Optional;

@Component
public class TicketCLI implements CommandLineRunner {

    private final TicketService ticketService;

    public TicketCLI(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Load existing ticket configuration or prompt for new configuration
        Optional<Ticket> existingConfig = ticketService.getConfiguration();

        if (existingConfig.isPresent()) {
            Ticket config = existingConfig.get();
            System.out.println("Current Ticket Configuration:");
            System.out.println("Total Tickets: " + config.getTotalTickets());
            System.out.println("Ticket Release Rate: " + config.getTicketReleaseRate());
            System.out.println("Customer Retrieval Rate: " + config.getCustomerRetrievalRate());
            System.out.println("Max Ticket Capacity: " + config.getMaxTicketCapacity());
            System.out.print("Do you want to keep this configuration? (Y/N): ");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("Y")) {
                System.out.println("Using existing configuration.");
                return;
            }
        }

        // Prompt for new configuration if existing configuration is not used
        System.out.println("Enter New Ticket Configuration:");

        System.out.print("Total Tickets: ");
        int totalTickets = scanner.nextInt();

        System.out.print("Ticket Release Rate: ");
        int ticketReleaseRate = scanner.nextInt();

        System.out.print("Customer Retrieval Rate: ");
        int customerRetrievalRate = scanner.nextInt();

        System.out.print("Max Ticket Capacity: ");
        int maxTicketCapacity = scanner.nextInt();

        // Save the new ticket configuration
        Ticket newConfig = new Ticket();
        newConfig.setEventName("Movie");
        newConfig.setTotalTickets(totalTickets);
        newConfig.setTicketReleaseRate(ticketReleaseRate);
        newConfig.setCustomerRetrievalRate(customerRetrievalRate);
        newConfig.setMaxTicketCapacity(maxTicketCapacity);
        newConfig.setEventDate("2024-11-28");
        newConfig.setEventTime("8:30 PM");
        newConfig.setPrice(1500);
        ticketService.saveConfiguration(newConfig);

        System.out.println("Ticket configuration saved successfully.");
    }
}