package homework.NovaCine.service;

import homework.NovaCine.event.TicketBookedEvent;
import homework.NovaCine.exception.BookingNotPossibleException;
import homework.NovaCine.exception.InsufficientSeatsException;
import homework.NovaCine.model.Screening;
import homework.NovaCine.model.TicketBooking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatService {

    private final ScreeningService screeningService;
    private final TicketBookingService ticketBookingService;
    private final CacheService cacheService;

    @EventListener
    @Order(1)
    public void handleTicketBooking(TicketBookedEvent event) {
        var booking = event.getTicketBooking();
        var screening = screeningService.getScreeningById(booking.getScreening().getId());

        validateBookingTime(screening.getStartTime());
        validateSeatAvailability(screening, booking.getSeatCount());

        updateSeats(screening, booking.getSeatCount());
        finalizeBooking(booking, screening);

        cacheService.evictFromCache(screening.getId());
    }

    private void validateBookingTime(LocalTime startTime) {
        var now = LocalTime.now();
        var duration = Duration.between(now, startTime);
        if (startTime.isBefore(now) || duration.toMinutes() < 120) {
            throw new BookingNotPossibleException();
        }
    }

    private void validateSeatAvailability(Screening screening, int bookedSeats) {
        int availableSeats = screening.getAvailableSeats();
        if (availableSeats < bookedSeats) {
            throw new InsufficientSeatsException(bookedSeats, availableSeats);
        }
    }

    private void updateSeats(Screening screening, Integer bookedSeats) {
        int availableSeats = screening.getAvailableSeats();
        screening.setAvailableSeats(availableSeats - bookedSeats);
        log.info("[SeatService] Reduced available seats from {} to {}", availableSeats, screening.getAvailableSeats());
    }

    private void finalizeBooking(TicketBooking booking, Screening screening) {
        booking.setScreening(screening)
                .setBookingTime(LocalDateTime.now());
        ticketBookingService.saveTicket(booking);
    }
}
