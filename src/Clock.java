public class Clock {
    private int hours;
    private int minutes;

    public Clock(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;

        Thread clocksThread = new Thread(() -> {
            while (true) {
                synchronized (this) {
                    incrementMinutes();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        clocksThread.start();
    }

    private synchronized void incrementMinutes() {
        minutes++;
        if (minutes == 60) {
            minutes = 0;
            System.out.println(hours+" : "+minutes);
            incrementHours();
        }
    }

    private synchronized void incrementHours() {
        hours++;
        if (hours == 24) {
            hours = 0; // Reset hours at midnight
        }
    }

}
