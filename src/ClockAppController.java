import java.util.Scanner;

public class ClockAppController {
    private static final Scanner myScanObj=new Scanner(System.in);

    public ClockAppController() {

        AlarmClock clock=new AlarmClock(0,0,55,0,1);
        Thread thread=new Thread( ()->{
            viewClock(clock);
        });
        thread.start();
        clock.setAlarmOn();
        myScanObj.nextLine();
        clock.setAlarmOff();
    }

    private void viewClock(AlarmClock clock){
        while(true){
            System.out.println(clock);
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e ){
                throw new RuntimeException(e);
            }
        }
    }


}
