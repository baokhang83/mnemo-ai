package io.github.baokhang83.mnemo.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Policy.Eviction;
import java.util.function.Function;

/**
 * A {@link CapacityBackend} backed by a Caffeine cache.
 *
 * <p>Caffeine owns eviction (W-TinyLFU); this backend only exposes its
 * runtime-adjustable maximum via {@link #setMaximum(long)} so the seasonality
 * controller can flex capacity along the curve.
 */
final class CaffeineBackend<K, V> implements CapacityBackend<K, V> {

    private final Cache<K, V> cache;

    CaffeineBackend(long initialMaximum) {
        this.cache = Caffeine.newBuilder()
                .maximumSize(initialMaximum)
                .recordStats()
                .build();
    }

    @Override
    public V getIfPresent(K key) {
        return cache.getIfPresent(key);
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public void invalidate(K key) {
        cache.invalidate(key);
    }

    @Override
    public V get(K key, Function<K, V> loader) {
        return cache.get(key, loader);
    }

    @Override
    public void setMaximum(long maximum) {
        cache.policy().eviction().ifPresent(e -> e.setMaximum(maximum));
    }

    @Override
    public long currentMaximum() {
        return cache.policy().eviction().map(Eviction::getMaximum).orElse(0L);
    }

    @Override
    public long estimatedSize() {
        return cache.estimatedSize();
    }

    @Override
    public long evictionCount() {
        return cache.stats().evictionCount();
    }
}
