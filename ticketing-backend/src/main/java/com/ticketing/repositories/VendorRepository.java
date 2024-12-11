package com.ticketing.repositories;

import com.ticketing.entities.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    // JpaRepository provides the findAll() method by default
}
