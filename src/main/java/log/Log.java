package log;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Log {
    private static String PATH;
    private static File log;
    private static PrintWriter writer;
    private final static String INFO="INFO", DEBUG="DEBUG", WARNING="WARNING", ERROR="ERROR";

    private Log(){

    }

    public static void init(String logname, int dataSwitch){
        try{
            PATH="output\\"+logname+dataSwitch+".txt";
            log = new File(PATH);
            if(log.exists()){
                log.delete();
            }
            writer = new PrintWriter(new BufferedWriter(new FileWriter(log, true)));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void info(String message){
        log(INFO, message);
    }

    public static void warn(String message){
        log(WARNING, message);
    }

    public static void debug(String message){
        log(DEBUG, message);
    }

    public static void error(String message){
        log(ERROR, message);
    }

    public static void log(String tag, String message){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd;HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String time = sdf.format(timestamp);
        String text =tag + ";" + time + ";" + message;
        System.out.println(text);
        writer.println(text);
        writer.flush();
    }

    public static void close(){
        writer.close();
    }
}
