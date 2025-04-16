package homework.NovaCine.service;

import homework.NovaCine.model.Cinema;
import homework.NovaCine.repository.CinemaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CinemaService {

private final CinemaRepository cinemaRepository;

    @Transactional
    public Cinema createCinema(Cinema cinema) {
        var savedCinema = cinemaRepository.save(cinema);
        log.info("Cinema saved to DB: {}", cinema);
        return savedCinema;
    }
}
