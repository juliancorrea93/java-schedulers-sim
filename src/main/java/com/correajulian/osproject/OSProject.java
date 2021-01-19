package com.correajulian.osproject;

/**
 * Holds the main function to run the three implemented schedulers
 * @author Julian
 */
public class OSProject {
    public static void main(String[] args) {
        FCFS f = new FCFS();
        SJF s = new SJF();
        MLFQ m = new MLFQ();
        
        f.runScheduler();
        s.runScheduler();
        m.runScheduler();
    }
}
