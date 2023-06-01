package ro.bb.tranzactii.model;

import lombok.Getter;

@Getter
public class TestResultHolder {
    private final String serviceLabel;
    private long minDuration = Integer.MAX_VALUE;
    private long maxDuration = -1;
    private long totalDuration = 0;
    private long numberOfRuns = 0;


    public TestResultHolder(String serviceLabel) {
        this.serviceLabel = serviceLabel;
    }


    public void updateWithNewRun(long duration) {
        numberOfRuns++;
        totalDuration += duration;
        if (duration < minDuration) minDuration = duration;
        if (duration > maxDuration) maxDuration = duration;
    }

    public long averageDuration() {
        if (numberOfRuns == 0) return 0;
        return totalDuration / numberOfRuns;
    }

}
