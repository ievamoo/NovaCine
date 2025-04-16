package homework.NovaCine.controller;

import homework.NovaCine.model.TicketBooking;
import homework.NovaCine.service.TicketBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final TicketBookingService ticketBookingService;

    @PostMapping
    public ResponseEntity<TicketBooking> bookTicket(@RequestBody TicketBooking tickedBooking) {
        log.info("POST /tickets | Received request to book tickets");
        return ResponseEntity.ok(ticketBookingService.bookTicket(tickedBooking));
    }
}
