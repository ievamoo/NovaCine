package homework.NovaCine.service;

import homework.NovaCine.model.Screening;
import homework.NovaCine.repository.CinemaRepository;
import homework.NovaCine.repository.ScreeningRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final CinemaRepository cinemaRepository;
    private final CacheManager cacheManager;


    @Transactional
    public Screening createScreening(Screening screening) {
        var cinema = cinemaRepository.findById(screening.getCinema().getId())
                .orElseThrow(() -> new EntityNotFoundException("Cinema does not exist under id " + screening.getCinema().getId()));
        screening.setCinema(cinema);
        var savedScreening = screeningRepository.save(screening);
        log.info("[ScreeningService]: screening saved to DB under id {} ", savedScreening);
        return savedScreening;
    }

    public Screening getScreeningById(Long id) {
        log.info("[ScreeningService] Searching for screening with id {}...", id);
        var cachedScreening = getFromCache(id);

        if (cachedScreening != null) {
            log.info("[ScreeningService] Cache hit for screening id {}", id);
            return cachedScreening;
        }

        log.info("[ScreeningService] Cache missed, retrieving screening from DB by id {}", id);
        var screeningFromDb =  fetchFromDb(id);
        putInCache(id, screeningFromDb);

        return screeningFromDb;
    }

    private Screening getFromCache(Long id) {
        var cache = cacheManager.getCache("screenings");
        return cache != null ? cache.get(id, Screening.class) : null;
    }

    private void putInCache(Long id, Screening screening) {
        var cache = cacheManager.getCache("screenings");
        if (cache != null) {
            cache.put(id, screening);
            log.info("[ScreeningService] Screening id {} cached", id);
        }
    }

    private Screening fetchFromDb(Long id) {
        return screeningRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found by id: " + id));
    }
}
