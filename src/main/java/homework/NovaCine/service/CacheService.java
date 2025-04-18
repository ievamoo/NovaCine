package homework.NovaCine.service;

import homework.NovaCine.model.Screening;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CacheService {

    private final CacheManager cacheManager;

    @Value("${screening.cache.name}")
    private String cacheName;

    public void evictFromCache(Long id) {
        var cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(id);
            log.info("[CacheService] evicted screening id {} from cache", id);
        }
    }

    public Screening getFromCache(Long id) {
        var cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            log.info("[CacheService] Cache not found");
            return null;
        }
        var screening = cache.get(id, Screening.class);
        if (screening == null) {
            log.info("[CacheService] Cache miss for screening id {}", id);
            return null;
        }
        log.info("[CacheService] Cache hit for screening id {}", id);
        return cache.get(id, Screening.class);
    }

    public void putInCache(Long id, Screening screening) {
        var cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.put(id, screening);
            log.info("[CacheService] Screening id {} cached", id);
        }
    }

}
