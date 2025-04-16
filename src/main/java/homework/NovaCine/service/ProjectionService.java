package homework.NovaCine.service;

import homework.NovaCine.event.TicketBookedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@Async
public class ProjectionService {

    @EventListener
    public void notifyProjection(TicketBookedEvent event) {
        log.info("[ProjectionService]: projection notified for {} at {} ", event.getTicketBooking().getScreening().getMovieTitle(), LocalDateTime.now());
    }
}
