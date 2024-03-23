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
            while(isThreadRunning){
                if (isItTimeToRing() && !isThereARingSoundPlaying){
                       playRingSound();
                }
                timesTicking();//Thread sleep 1s| protected method,exists in Clock class
                updateRingSoundProperties();
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
        alarmVolume= (secBeforeReplayingMelody-secBeforeReplayingMelodyCounter)/;
        System.out.println("Wake UP!!! sound volume : "+ alarmVolume);//melody obj here...
    }
    private void updateRingSoundProperties(){
        secBeforeReplayingMelodyCounter--;
        if (secBeforeReplayingMelodyCounter <0){
            isThereARingSoundPlaying=false;
            secBeforeReplayingMelodyCounter =secBeforeReplayingMelody;
            alarmVolume=0;
        }
    }

    private void updateAutoAbortAlarmTimer(){
        secBeforeAlarmAutoAbortCounter--;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
