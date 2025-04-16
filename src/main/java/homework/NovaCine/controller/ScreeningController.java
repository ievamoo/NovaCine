package homework.NovaCine.controller;

import homework.NovaCine.model.Screening;
import homework.NovaCine.service.ScreeningService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/screenings")
@RequiredArgsConstructor
@Slf4j
public class ScreeningController {

    private final ScreeningService screeningService;

    @PostMapping
    public ResponseEntity<Screening> addScreening(@RequestBody Screening screening) {
        log.info("POST /screenings | Received request to add screening");
        return ResponseEntity.ok(screeningService.createScreening(screening));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Screening> getScreeningById(@PathVariable Long id) {
        log.info("GET /screenings | Received request to retrieve screening by id");
        return ResponseEntity.ok(screeningService.getScreeningById(id));
    }

}
