package ro.bb.tranzactii.util;

public class Time {

    public static void waitMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {/* tant pis */}
    }
}
