package homework.NovaCine.controller;

import homework.NovaCine.model.Cinema;
import homework.NovaCine.service.CinemaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cinemas")
@RequiredArgsConstructor
@Slf4j
public class CinemaController {

    private final CinemaService cinemaService;

    @PostMapping
    public ResponseEntity<Cinema> addCinema(@RequestBody Cinema cinema) {
        log.info("POST /cinemas | Received request to add cinema");
        return ResponseEntity.ok(cinemaService.createCinema(cinema));
    }
}
