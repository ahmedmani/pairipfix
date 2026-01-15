package com.pairip.licensecheck;

/* loaded from: /home/ark/Downloads/Kuku TV_5.6.3_antisplit/smali_classes2/com/pairip/licensecheck/RepeatedCheckMetadata.smali */
public class RepeatedCheckMetadata {
    private final long durationToRetryMillis;
    private final long timeToRetryMillis;

    public RepeatedCheckMetadata(long durationToRetryMillis, long timeToRetryMillis) {
        if (durationToRetryMillis <= 0) {
            throw new IllegalArgumentException("Duration to retry must be positive.");
        }
        if (timeToRetryMillis <= 0) {
            throw new IllegalArgumentException("Time to retry must be positive.");
        }
        this.durationToRetryMillis = durationToRetryMillis;
        this.timeToRetryMillis = timeToRetryMillis;
    }

    public long getDurationToRetryMillis() {
        return this.durationToRetryMillis;
    }

    public long getTimeToRetryMillis() {
        return this.timeToRetryMillis;
    }
}
