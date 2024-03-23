public class Clock {
    private int seconds;
    private int minutes;
    private int hours;
    // You cant change variables inside lambda BUT you can call methods to change them for ya...
    // although the disadvantage remain if methods require different parameters ; ? ...perhaps
    // in the case of just having an interval you could call another methods that inside there you call methods
    //with updated parameters.
    public Clock(int hours, int minutes , int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        Thread clocksThread = new Thread(() -> {
            while (true) {
                incrementSeconds();
                timesTicking();
            }
        });
        clocksThread.start();
    }

    void timesTicking(){
        try {
            Thread.sleep(1000);
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
