package homework.NovaCine.service;

import homework.NovaCine.event.TicketBookedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Async
public class LoyaltyService {

    private final Map<String, Integer> loyaltyPoints = new HashMap<>();

    @EventListener
    public void countLoyaltyPoints(TicketBookedEvent event) {
        var name = event.getTicketBooking().getViewerName();
        var bookedSeats = event.getTicketBooking().getSeatCount();
        var updatedPoints = loyaltyPoints.getOrDefault(name, 0) + bookedSeats;
        loyaltyPoints.put(name, updatedPoints);
        log.info("[LoyaltyService]: current loyalty points for {} : {}", name, updatedPoints);
    }

}
