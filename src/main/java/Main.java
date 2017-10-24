import genetics.Population;
import log.Log;

public class Main {
    public static void main(String args[]){
        int dataSwitch = 3;
        Log.init("EqualSplice",dataSwitch);
        new Thread(new Population(30000, 1000, dataSwitch)).start();
    }
}