package org.headstrait.texteditor.util;

import org.apache.commons.lang3.time.StopWatch;

import static java.lang.Thread.sleep;

public class PerformanceUtil {
    public static StopWatch stopWatch = new StopWatch();

    public static void startTimer(){
        stopWatchReset();
        stopWatch.start();
    }

    public static void printTimeTaken(){
        stopWatch.stop();
        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Total Time Taken : " +stopWatch.getTime()+" ms");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
    }

    public static void delay(long delayMilliSeconds)  {
        try{
            sleep(delayMilliSeconds);
        }catch (Exception e){
            System.out.println("Exception is :" + e.getMessage());
        }

    }

    public static void stopWatchReset(){
        stopWatch.reset();
    }
}
