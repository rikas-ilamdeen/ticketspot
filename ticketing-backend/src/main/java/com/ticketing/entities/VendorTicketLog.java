package com.ticketing.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorTicketLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendorLogId;
    private Long vendorId;
    private Long ticketId;
    private int addedTicketCount;
    private LocalDateTime addedDate;
}
