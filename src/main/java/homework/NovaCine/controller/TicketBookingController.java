package homework.NovaCine.controller;

import homework.NovaCine.event.EventPublisher;
import homework.NovaCine.event.TicketBookedEvent;
import homework.NovaCine.model.TicketBooking;
import homework.NovaCine.service.TicketBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
@Slf4j
public class TicketBookingController {

    private final EventPublisher publisher;

    @PostMapping
    public ResponseEntity<String> bookTicket(@RequestBody TicketBooking ticketBooking) {
        log.info("POST /tickets | Received request to book tickets");
        publisher.publishTicketBookingEvent(new TicketBookedEvent(this, ticketBooking));
        return ResponseEntity.status(HttpStatus.CREATED).body("Your ticket was successfully booked.");

    }
}
