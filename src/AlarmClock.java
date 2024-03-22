public class AlarmClock extends Clock{
    //default alarm ,like regular alarm clocks there is always a default one...
    private int alarmHour=0;
    private int alarmMinutes=0;

    private boolean isAlarmOn;
    private boolean isItRinging;

    private int ringingToneFrequency=4;
    private int ringingToneFrequencyCountdown;

    private int ringingDuration=12;
    private int ringingDurationCounter=12;

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

                if (isItTimeToRing()){
                    startRinging();
                }

                if (isItRinging && ringingToneFrequencyCountdown==0){
                    ring();
                    resetRingingToneFrequencyCountdown();//resets to frequency value e.g : 4
                }

                timesTicking();

                if (isItRinging){
                    decreaseRingingToneFrequencyCountdown();
                    decreaseRingingDurationCounter();
                }

                if (ringingDurationCounter<0){
                    setAlarmOff();
                }

            }
        });

        alarmThread.start();
    }

    private boolean isItTimeToRing(){
        return alarmHour==super.getHours() && alarmMinutes==super.getMinutes();
    }

    private void startRinging(){
        isItRinging=true;
    }

    private void ring(){
        System.out.println("WAKE UP!!!");
    }

    private void resetRingingToneFrequencyCountdown(){
        ringingToneFrequencyCountdown =ringingToneFrequency;
    }

    private void decreaseRingingToneFrequencyCountdown(){
        ringingToneFrequencyCountdown--;
    }

    private void timesTicking(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void decreaseRingingDurationCounter(){
        ringingDurationCounter--;
    }

}
