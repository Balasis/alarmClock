public class AlarmClock extends Clock{
    //default alarm ,like regular alarm clocks there is always a default one...
    private int alarmHour=0;
    private int alarmMinutes=0;

    private boolean isAlarmOn;
    private boolean isThereARingSoundPlaying;
    private int alarmVolume;// by %

    private final int secBeforeReplayingMelody=20;
    private int secBeforeReplayingMelodyCounter =20;

    private int secBeforeAlarmAutoAbortCounter=60;

    private Thread alarmThread;
    private boolean isThreadRunning;

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
        isThreadRunning=false;
    }

    private void initiateAlarmThread(){
        isThreadRunning=true;
        alarmThread=new Thread( ()->{
            //a Ring Sound is playing in intervals, during the playing there is an increase
            //in volume till the ring sound its being reset
            while(isThreadRunning){
                if (isItTimeToRing() && !isThereARingSoundPlaying){
                       playRingSound();
                }
                timesTicking();//Thread sleep 1s| protected method,exists in Clock class
                if (isThereARingSoundPlaying){
                    increaseRingVolume();
                    updateSecBeforeReplayingMelodyCounter();
                }
                if (isItTimeToReplayTheSound()){
                    stopCurrentAlarmSound();
                    resetRingSoundProperties();
                }
                updateAutoAbortAlarmTimer();
                if (secBeforeAlarmAutoAbortCounter <0){
                    setAlarmOff();
                }
            }
        });
        alarmThread.start();
    }

    private boolean isItTimeToRing(){
        return alarmHour==super.getHours() && alarmMinutes==super.getMinutes();
    }

    private void playRingSound(){
        isThereARingSoundPlaying =true;
    }
    private void increaseRingVolume(){
        alarmVolume=((secBeforeReplayingMelody - secBeforeReplayingMelodyCounter) * 100) / secBeforeReplayingMelody;
    }

    private void updateSecBeforeReplayingMelodyCounter(){
        secBeforeReplayingMelodyCounter--;

    }

    private boolean isItTimeToReplayTheSound(){
        return secBeforeReplayingMelodyCounter < 0;
    }

    private void stopCurrentAlarmSound(){
        isThereARingSoundPlaying=false;
    }

    private void resetRingSoundProperties(){
        secBeforeReplayingMelodyCounter =secBeforeReplayingMelody;
        alarmVolume=0;
    }

    private void updateAutoAbortAlarmTimer(){
        secBeforeAlarmAutoAbortCounter--;
    }

    @Override
    public String toString() {
        String wakeUp=isThereARingSoundPlaying ? " Ring Vol(" +alarmVolume+")" : "";
        return super.toString() + wakeUp;
    }
}
