package homework.NovaCine.service;

import homework.NovaCine.event.TicketBookedEvent;
import homework.NovaCine.repository.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatService {

    private final ScreeningRepository screeningRepository;
    private final CacheManager cacheManager;

    @EventListener
    @Order(1)
    public void validateAndAllocateSeats(TicketBookedEvent event) {
        var screening = event.getTicketBooking().getScreening();
        var availableSeats = event.getTicketBooking().getScreening().getAvailableSeats();
        var bookedSeats = event.getTicketBooking().getSeatCount();
        screening.setAvailableSeats(availableSeats-bookedSeats);
        screeningRepository.save(screening);
        log.info("[SeatService]: available seats reduced from {} to {} ", availableSeats, screening.getAvailableSeats());
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
