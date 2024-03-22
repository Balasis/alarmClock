public class Main {
    public static void main(String[] args) {
    Clock clock=new Clock(23,59,55);

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