package io.github.baokhang83.mnemo.cache;

import java.util.function.Function;

/**
 * The pluggable cache backend behind a {@link SeasonalCache}. It must support a
 * runtime-adjustable maximum ({@link #setMaximum(long)}), which is the seam the
 * seasonality controller drives. {@code CaffeineBackend} is the first implementation.
 */
interface CapacityBackend<K, V> {

    V getIfPresent(K key);

    void put(K key, V value);

    void invalidate(K key);

    V get(K key, Function<K, V> loader);

    void setMaximum(long maximum);

    long currentMaximum();

    long estimatedSize();

    long evictionCount();
}
