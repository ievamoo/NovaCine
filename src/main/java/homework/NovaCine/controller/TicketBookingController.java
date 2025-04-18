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

import java.util.UUID;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
@Slf4j
public class TicketBookingController {

    private final EventPublisher publisher;
    private final TicketBookingService ticketBookingService;

    @PostMapping
    public ResponseEntity<TicketBooking> bookTicket(@RequestBody TicketBooking ticketBooking) {
        log.info("POST /tickets | Received request to book tickets");
        ticketBooking.setUuid(UUID.randomUUID());
        log.info("UUID generated for the ticket {}", ticketBooking.getUuid());
        publisher.publishTicketBookingEvent(new TicketBookedEvent(this, ticketBooking));
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketBookingService.getTicketByUuid(ticketBooking.getUuid()));
    }
}
