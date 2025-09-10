package com.echoItSolution.booking_service.repository;

import com.echoItSolution.booking_service.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRespository extends JpaRepository<Booking, Integer> {
}
