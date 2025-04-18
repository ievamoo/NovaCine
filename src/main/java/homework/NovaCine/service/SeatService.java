package homework.NovaCine.service;

import homework.NovaCine.event.TicketBookedEvent;
import homework.NovaCine.exception.InsufficientSeatsException;
import homework.NovaCine.model.Screening;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatService {

    private final ScreeningService screeningService;
    private final TicketBookingService ticketBookingService;
    private final CacheService cacheService;

    @EventListener
    @Order(1)
    public void validateAndAllocateSeats(TicketBookedEvent event) {
        var booking = event.getTicketBooking();
        var screening = screeningService.fetchScreeningFromDb(booking.getScreening().getId());
        updateAvailableSeats(screening, booking.getSeatCount());
        booking.setScreening(screening).setBookingTime(LocalDateTime.now());
        ticketBookingService.saveTicket(booking);
        cacheService.evictFromCache(screening.getId());
    }

    private void updateAvailableSeats(Screening screening, Integer bookedSeats) {
        var availableSeats = screening.getAvailableSeats();
        if (availableSeats < bookedSeats) {
            throw new InsufficientSeatsException(bookedSeats, availableSeats);
        }
        screening.setAvailableSeats(availableSeats-bookedSeats);
        log.info("[SeatService]: Available seats reduced from {} to {} ", availableSeats, screening.getAvailableSeats());
    }
}
