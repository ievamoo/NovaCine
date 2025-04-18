package homework.NovaCine.service;

import homework.NovaCine.dto.LoyaltyResponseDto;
import homework.NovaCine.event.TicketBookedEvent;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
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

    public LoyaltyResponseDto getLoyaltyPoints (String viewerName) {
        if (!loyaltyPoints.containsKey(viewerName)) {
            throw new EntityNotFoundException("No viewer found with name " + viewerName);
        }
        return LoyaltyResponseDto.builder()
                .viewerName(viewerName)
                .totalPoints(loyaltyPoints.get(viewerName))
                .build();
    }
}
