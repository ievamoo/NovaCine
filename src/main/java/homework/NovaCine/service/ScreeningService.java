package homework.NovaCine.service;

import homework.NovaCine.model.Screening;
import homework.NovaCine.repository.ScreeningRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final CinemaService cinemaService;
    private final CacheService cacheService;


    @Transactional
    public Screening createScreening(Screening screening) {
        var cinema = cinemaService.getCinemaById(screening.getCinema().getId());
        screening.setCinema(cinema);
        var savedScreening = screeningRepository.save(screening);
        log.info("[ScreeningService]: screening saved to DB under id {} ", savedScreening.getId());
        return savedScreening;
    }

    public Screening getScreeningById(Long id) {
        var cachedScreening = cacheService.getFromCache(id);
        return cachedScreening != null ? cachedScreening : fetchScreeningFromDb(id);
    }

    private Screening fetchScreeningFromDb(Long id) {
        var screening =screeningRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found by id: " + id));
        log.info("[ScreeningService]: fetched screening from DB" );
        cacheService.putInCache(id, screening);
        return screening;
    }


}
