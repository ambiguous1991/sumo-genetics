import genetics.Population;
import log.Log;

public class Main {
    public static void main(String args[]){
        int dataSwitch = 3;
        Log.init("Shuffle",dataSwitch);
        new Thread(new Population(50, 1000, dataSwitch)).start();
    }
}