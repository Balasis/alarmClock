import java.util.Scanner;
public class Main {
    private static Scanner myScanObj;

    public static void main(String[] args) {
    AlarmClock clock=new AlarmClock(0,0,50,0,1);
    Thread thread=new Thread(()->{
        viewClock(clock);
    });
    thread.start();

    clock.setAlarmOn();

     }

    private static void viewClock(AlarmClock clock){
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


