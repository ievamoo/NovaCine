package homework.NovaCine.controller;

import homework.NovaCine.dto.ScreeningAnalyticsDto;
import homework.NovaCine.service.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final ScreeningService screeningService;

    @GetMapping("/screening/{id}")
    public ResponseEntity<ScreeningAnalyticsDto> getScreeningAnalytics(@PathVariable Long id) {
        var screening = screeningService.getScreeningById(id);
        return ResponseEntity.ok(
                ScreeningAnalyticsDto.builder()
                        .totalBookings(screening.getTicketBookings().size())
                        .remainingSeats(screening.getAvailableSeats())
                        .build()
        );
    }
}
