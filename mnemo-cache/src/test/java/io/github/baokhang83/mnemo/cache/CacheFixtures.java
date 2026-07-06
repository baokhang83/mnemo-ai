package io.github.baokhang83.mnemo.cache;

import static io.github.baokhang83.mnemo.cache.schedule.SeasonalCapacity.percent;

import io.github.baokhang83.mnemo.cache.schedule.ScheduleSpec;
import io.github.baokhang83.mnemo.cache.schedule.SeasonalCapacity;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

/** Shared fixtures for the runtime tests. */
final class CacheFixtures {

    private CacheFixtures() {}

    /** The canonical reclaim curve: 10% overnight, ramp to an 85% daytime plateau, ramp down. */
    static ScheduleSpec fullDay100k() {
        return SeasonalCapacity.named("hot").max(100_000).min(500).zone("UTC")
                .startAt("00:00", percent(10)).holdUntil("06:00")
                .rampUntil("11:00", percent(85)).holdUntil("20:00")
                .rampUntil("23:00", percent(10)).build();
    }

    /** A fixed clock at {@code time} on a stable UTC date, so curve sampling is deterministic. */
    static Clock clockAt(LocalTime time) {
        Instant instant = LocalDate.of(2026, 1, 15).atTime(time).atZone(ZoneOffset.UTC).toInstant();
        return Clock.fixed(instant, ZoneOffset.UTC);
    }
}
