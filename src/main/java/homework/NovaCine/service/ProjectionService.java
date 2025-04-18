package homework.NovaCine.service;

import homework.NovaCine.event.TicketBookedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProjectionService {

    @EventListener
    public void notifyProjection(TicketBookedEvent event) {
        log.info("[ProjectionService]: projection notified for {} at {} ",
                event.getTicketBooking().getScreening().getMovieTitle(),
                event.getTicketBooking().getScreening().getStartTime());
    }
}
