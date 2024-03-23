public class AlarmClock extends Clock{
    private int alarmHour=0;
    private int alarmMinutes=0;
    private int alarmVolumeIncreamental =50;
    private int alarmVolume=50;
    private int secBeforeAlarmAutoAbortCounter=70;
    private int secBeforeAlarmAutoAbort=70;
    private int secBeforeReplayingMelodyCounter =20;
    private int secBeforeReplayingMelody=20;
    private boolean isAlarmOn;
    private boolean isAlarmProcessAlreadyDone;
    private boolean isRingingProcessAllowedToBegin;
    private boolean isThereARingSoundPlaying;

    private SoundPlayer soundPlayer;

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
        System.out.println("got");
        isAlarmOn=false;
        if (soundPlayer!=null){
            abortAlarm();
        }
    }

    private void initiateAlarmThread(){
        Thread alarmThread = new Thread(() -> {
            while (isAlarmOn) {
                System.out.println("everyTime");
                timesTicking(1000);//Thread sleep 1s| protected method,exists in Clock class
                if (isItTimeToRing() && !isAlarmProcessAlreadyDone) {
                    isAlarmProcessAlreadyDone =true;
                    isRingingProcessAllowedToBegin=true;
                }
                if (!isRingingProcessAllowedToBegin){
                    continue;
                }
                if(secBeforeAlarmAutoAbortCounter<0){
                    abortAlarm();
                    continue;
                }
                ring();
                increaseRingVolume();
                updateTimerTillReplayAlarmMelody();
                updateTimerTillAlarmAutoabort();
                replayAlarmMelodyIfItsTime();
            }
        });
        alarmThread.start();

    }

    private boolean isItTimeToRing(){
        return alarmHour==super.getHours() && alarmMinutes==super.getMinutes();
    }

    private void ring(){
        //Prevent from recreation of soundPlayer if it already exist OR user manually set the AlarmOff
        if (soundPlayer!=null || !isAlarmOn){
            return;
        }
        isThereARingSoundPlaying =true;
        soundPlayer=new SoundPlayer("alarmSound.wav");
        soundPlayer.play();
    }

    private void increaseRingVolume(){
        Thread increaseRingVolThread=new Thread(()->{
            for (int i = 0; i <5 ; i++) {
                timesTicking(70) ;
                if (alarmVolumeIncreamental +1 >100 || soundPlayer==null){
                    return;
                }
                alarmVolumeIncreamental++;
                soundPlayer.setVolumePercentage(alarmVolumeIncreamental);
            }
        });
            increaseRingVolThread.start();
    }

    private void updateTimerTillReplayAlarmMelody(){
        secBeforeReplayingMelodyCounter--;
    }

    private void replayAlarmMelodyIfItsTime(){
        if (secBeforeReplayingMelodyCounter>0){
            return;
        }
        resetSoundRelatedValues();
    }

    private void updateTimerTillAlarmAutoabort(){
        secBeforeAlarmAutoAbortCounter--;
    }

    private void abortAlarm(){
        isAlarmOn=false;
        resetSoundRelatedValues();
        secBeforeAlarmAutoAbortCounter = secBeforeAlarmAutoAbort;
    }

    private void resetSoundRelatedValues(){
        soundPlayer.stop();
        soundPlayer=null;
        isThereARingSoundPlaying=false;
        alarmVolumeIncreamental =alarmVolume;
        secBeforeReplayingMelodyCounter=secBeforeReplayingMelody;
    }

    @Override
    public String toString() {
        String wakeUp=isThereARingSoundPlaying && soundPlayer!=null ? " Ring Vol(" + alarmVolumeIncreamental +")" : "";
        return super.toString() + wakeUp;
    }
}
