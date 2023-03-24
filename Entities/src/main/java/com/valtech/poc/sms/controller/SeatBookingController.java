package com.valtech.poc.sms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.valtech.poc.sms.service.SeatBookingService;

@RestController
@RequestMapping("/seats")
public class SeatBookingController {

    @Autowired
    private SeatBookingService seatService;

    @GetMapping("/all")
    public ResponseEntity<List<Integer>> getAllSeats() {
        List<Integer> allSeats = seatService.getAllSeats();
        return ResponseEntity.ok().body(allSeats);
    }
}
