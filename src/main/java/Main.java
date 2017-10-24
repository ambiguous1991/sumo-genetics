import genetics.Population;
import log.Log;

public class Main {
    public static void main(String args[]){
        int dataSwitch = 1;
        Log.init("highPopSize",dataSwitch);
        new Thread(new Population(50, 1000, dataSwitch)).start();
    }
}