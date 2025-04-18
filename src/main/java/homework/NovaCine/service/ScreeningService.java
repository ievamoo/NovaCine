package homework.NovaCine.service;

import homework.NovaCine.model.Screening;
import homework.NovaCine.repository.CinemaRepository;
import homework.NovaCine.repository.ScreeningRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        log.info("[ScreeningService] Searching for screening with id {}...", id);
        var cachedScreening = cacheService.getFromCache(id);

        if (cachedScreening != null) {
            log.info("[ScreeningService] Cache hit for screening id {}", id);
            return cachedScreening;
        }

        log.info("[ScreeningService] Cache missed, retrieving screening from DB by id {}", id);
        var screeningFromDb =  fetchScreeningFromDb(id);
        cacheService.putInCache(id, screeningFromDb);

        return screeningFromDb;
    }

    public Screening fetchScreeningFromDb(Long id) {
        return screeningRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found by id: " + id));
    }
}
