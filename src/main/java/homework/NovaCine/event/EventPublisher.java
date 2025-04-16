package homework.NovaCine.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventPublisher {

    private final ApplicationEventPublisher publisher;

    public void publishTicketBookingEvent(TicketBookedEvent event) {
        log.info("[EventPublisher]: TicketBookedEvent fired for screeningId {}", event.getTicketBooking().getScreening().getId());
        publisher.publishEvent(event);
    }
}
