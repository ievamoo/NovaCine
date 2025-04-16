package homework.NovaCine.service;

import homework.NovaCine.event.TicketBookedEvent;
import homework.NovaCine.exception.InsufficientSeatsException;
import homework.NovaCine.model.TicketBooking;
import homework.NovaCine.repository.ScreeningRepository;
import homework.NovaCine.repository.TicketBookingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatService {

    private final ScreeningRepository screeningRepository;
    private final CacheManager cacheManager;
    private final TicketBookingRepository ticketBookingRepository;

    @EventListener
    @Order(1)
    public void validateAndAllocateSeats(TicketBookedEvent event) {
        var screening = screeningRepository.findById(event.getTicketBooking().getScreening().getId())
                .orElseThrow(() -> new EntityNotFoundException("Screening does not exist by id:" + event.getTicketBooking().getScreening().getId()));
        var availableSeats = screening.getAvailableSeats();
        var bookedSeats = event.getTicketBooking().getSeatCount();
        if (availableSeats < bookedSeats) {
            throw new InsufficientSeatsException(bookedSeats, availableSeats);
        }
        screening.setAvailableSeats(availableSeats-bookedSeats);
        screeningRepository.save(screening);
        log.info("[SeatService]: available seats reduced from {} to {} ", availableSeats, screening.getAvailableSeats());
        var booking = event.getTicketBooking();
        booking.setBookingTime(LocalDateTime.now()).setScreening(screening);
        ticketBookingRepository.save(booking);
        evictFromCache(screening.getId());
    }

    private void evictFromCache(Long id) {
        var cache = cacheManager.getCache("screenings");
        if (cache != null) {
            cache.evict(id);
            log.info("[SeatService] evicted screening id {} from cache", id);
        }
    }
}
