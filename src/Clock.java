public class Clock {
    private int seconds;
    private int minutes;
    private int hours;

    public Clock(int hours, int minutes , int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        startClock();
    }

    private void startClock(){
        Thread clocksThread = new Thread(() -> {
            while (true) {
                timesTicking(1000);
                incrementSeconds();
            }
        });
        clocksThread.start();
    }

    void timesTicking(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void incrementSeconds(){
        seconds++;
        if(seconds>=60){
            seconds=0;
            incrementMinutes();
        }
    }

    private void incrementMinutes() {
        minutes++;
        if (minutes >= 60) {
            minutes = 0;
            incrementHours();
        }
    }

    private void incrementHours() {
        hours++;
        if (hours >= 24) {
            hours = 0;
        }
    }

    public int getSeconds() {
        return seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getHours() {
        return hours;
    }

    @Override
    public String toString() {
        String hoursPrefix=hours<10?"0":"";
        String minutesPrefix=minutes<10?"0":"";
        String secondsPrefix=seconds<10?"0":"";
        return  "Clock |" + hoursPrefix + hours +
                " : " + minutesPrefix +  minutes +
                " : " + secondsPrefix + seconds;
    }

}
