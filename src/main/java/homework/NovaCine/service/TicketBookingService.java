package homework.NovaCine.service;

import homework.NovaCine.event.EventPublisher;
import homework.NovaCine.event.TicketBookedEvent;
import homework.NovaCine.exception.InsufficientSeatsException;
import homework.NovaCine.model.TicketBooking;
import homework.NovaCine.repository.ScreeningRepository;
import homework.NovaCine.repository.TicketBookingRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketBookingService {

    private final TicketBookingRepository ticketBookingRepository;
    private final ScreeningRepository screeningRepository;
    private final EventPublisher publisher;

    @Transactional
    public TicketBooking bookTicket(TicketBooking ticketBooking) {
//        var screening = screeningRepository.findById(ticketBooking.getScreening().getId())
//                .orElseThrow(() -> new EntityNotFoundException("Screening does not exist by id:" + ticketBooking.getScreening().getId()));
//        validateSeatAvailability(screening.getAvailableSeats(), ticketBooking.getSeatCount());
//        ticketBooking.setScreening(screening).setBookingTime(LocalDateTime.now());
//        var savedBooking = ticketBookingRepository.save(ticketBooking);
        publisher.publishTicketBookingEvent(new TicketBookedEvent(this, ticketBooking));
        return ticketBooking;
    }

//    private void validateSeatAvailability(Integer availableSeats, Integer bookedSeats) {
//        if (availableSeats < bookedSeats) {
//            throw new InsufficientSeatsException(bookedSeats, availableSeats);
//        }
//    }
}
