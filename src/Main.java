public class Main {
    public static void main(String[] args) {
    AlarmClock clock=new AlarmClock(0,0,50,0,1);
    clock.setAlarmOn();


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