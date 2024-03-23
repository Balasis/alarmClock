public class AlarmClock extends Clock{
    //default alarm ,like regular alarm clocks there is always a default one...
    private int alarmHour=0;
    private int alarmMinutes=0;
    private int alarmVolume;// by %

    private int secBeforeAlarmAutoAbortCounter=60;
    private final int secBeforeAlarmAutoAbort=60;

    private int secBeforeReplayingMelodyCounter =20;
    private final int secBeforeReplayingMelody=20;

    private boolean isAlarmOn;
    private boolean isThereARingSoundPlaying;

    public AlarmClock(int hours, int minutes, int seconds) {
        super(hours, minutes, seconds);
    }

    public AlarmClock(int hours, int minutes, int seconds, int alarmHour, int alarmMinutes) {
        super(hours, minutes, seconds);
        setAlarmTime(alarmHour,alarmMinutes);
    }

    public void setAlarmTime(int alarmHour, int alarmMinutes){
        this.alarmHour = alarmHour;
        this.alarmMinutes = alarmMinutes;
    }

    public void setAlarmOn(){
        if (isAlarmOn){
            return;
        }
        isAlarmOn=true;
        initiateAlarmThread();
    }

    public void setAlarmOff(){
        if (!isAlarmOn){
            return;
        }
        isAlarmOn=false;
    }

    private void initiateAlarmThread(){
        Thread alarmThread = new Thread(() -> {
            while (isAlarmOn) {
                timesTicking();//Thread sleep 1s| protected method,exists in Clock class
                if (isItTimeToRing()) {
                    ring();
                    increaseRingVolume();
                    updateTimerTillReplayAlarmMelody();
                    updateTimerTillAlarmAutoabort();
                    replayAlarmMelodyIfItsTime();
                    autoAbortIfItsTime();
                }

            }
        });
        alarmThread.start();

    }

    private boolean isItTimeToRing(){
        return alarmHour==super.getHours() && alarmMinutes==super.getMinutes();
    }

    private void ring(){
            isThereARingSoundPlaying =true;
    }

    private void increaseRingVolume(){
        alarmVolume=(( (secBeforeReplayingMelody - secBeforeReplayingMelodyCounter) * 100 ) / secBeforeReplayingMelody)+5;
    }

    private void updateTimerTillReplayAlarmMelody(){
        secBeforeReplayingMelodyCounter--;
    }

    private void replayAlarmMelodyIfItsTime(){
        if (secBeforeReplayingMelodyCounter<=0){
            secBeforeReplayingMelodyCounter=secBeforeReplayingMelody;
        }
    }

    private void updateTimerTillAlarmAutoabort(){
        secBeforeAlarmAutoAbortCounter--;
    }

    private void autoAbortIfItsTime(){
        if (secBeforeAlarmAutoAbortCounter <= 0) {
            setAlarmOff();
            secBeforeAlarmAutoAbortCounter = secBeforeAlarmAutoAbort;
        }
    }

    @Override
    public String toString() {
        String wakeUp=isThereARingSoundPlaying ? " Ring Vol(" +alarmVolume+")" : "";
        return super.toString() + wakeUp;
    }
}


//    private boolean isItTimeToReplayTheSound(){
//        return secBeforeReplayingMelodyCounter < 0;
//    }
//
//    private void stopCurrentAlarmSound(){
//        isThereARingSoundPlaying=false;
//    }
//
//    private void resetRingSoundProperties(){
//        secBeforeReplayingMelodyCounter =secBeforeReplayingMelody;
//        alarmVolume=0;
//    }