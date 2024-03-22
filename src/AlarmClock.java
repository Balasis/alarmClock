public class AlarmClock extends Clock{
    private int alarmHour;
    private int alarmMinutes;
    private int alarmSeconds;

    public AlarmClock(int hours, int minutes, int seconds) {
        super(hours, minutes, seconds);
    }

    public AlarmClock(int hours, int minutes, int seconds, int alarmHour, int alarmMinutes, int alarmSeconds) {
        super(hours, minutes, seconds);
        setAlarmTime(alarmHour,alarmMinutes,alarmSeconds);
    }

    public void setAlarmTime(int alarmHour, int alarmMinutes, int alarmSeconds){
        this.alarmHour = alarmHour;
        this.alarmMinutes = alarmMinutes;
        this.alarmSeconds = alarmSeconds;
    }

    @Override
    public String toString() {

        return  "alarmHour=" + alarmHour +
                ", alarmMinutes=" + alarmMinutes +
                ", alarmSeconds=" + alarmSeconds +
                '}';
    }
}
