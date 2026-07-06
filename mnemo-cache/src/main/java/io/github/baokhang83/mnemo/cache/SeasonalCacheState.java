package io.github.baokhang83.mnemo.cache;

/**
 * An immutable snapshot of a {@link SeasonalCache}'s current state, for observability.
 *
 * @param currentFraction  the curve's fraction at the moment of the snapshot, {@code [0.0, 1.0]}
 * @param currentMaxEntries the backend's active maximum entry count
 * @param size             the backend's estimated current entry count
 * @param evictionCount    total entries evicted since creation
 */
public record SeasonalCacheState(
        double currentFraction,
        long currentMaxEntries,
        long size,
        long evictionCount) {}
