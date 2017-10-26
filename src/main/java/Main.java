import genetics.Population;
import log.Log;

public class Main {
    public static void main(String args[]){
        int dataSwitch =1;//= Integer.parseInt(args[0]);
        String logname ="Random";//= args[1];
        Log.init(logname,dataSwitch);
        new Thread(new Population(500, 1000, dataSwitch)).start();
    }
}