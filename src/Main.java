public class Main {
    public static void main(String[] args) {
    AlarmClock clock=new AlarmClock(0,0,50,0,1);
    clock.setAlarmOn();
        while(true){
            try{
                System.out.println(clock);
                Thread.sleep(1000);
            }catch(InterruptedException e ){
                throw new RuntimeException(e);
            }
        }

    }
}