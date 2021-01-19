package com.correajulian.osproject;

import java.util.ArrayList;

/**
 * Scheduler abstract class
 * @precondition running queues are expected to be set by the subclass
 * @author Julian
 */
abstract class Scheduler {
    /**
     * Calculates the average time waiting for CPU
     * @param procs ArrayList of processes
     * @return 
     */
    private float calculateAverageWait(ArrayList<Process> procs) {
        
        float list_sum = 0;
        int list_size = procs.size();
        float avg;
        
        for(int i  = 0; i < list_size; i++) {
            list_sum += procs.get(i).getWaitTime();
        }
        
        avg = list_sum / list_size;
        
        return avg;
    }
    
    /**
     * Calculates the average first response time for the processes
     * @param procs ArrayList of processes
     * @return 
     */
    private float calculateAverageResponse(ArrayList<Process> procs) {
        float list_sum = 0;
        int list_size = procs.size();
        float avg;
        
        for(int i  = 0; i < list_size; i++) {
            list_sum += procs.get(i).getResponseTime();
        }
        
        avg = list_sum / list_size;
        
        return avg;
    }
    
    /**
     * Calculates the average turnaround time for the processes
     * @param procs ArrayList of processes
     * @return 
     */
    private float calculateAverageTurnaround(ArrayList<Process> procs) {
        float list_sum = 0;
        int list_size = procs.size();
        float avg;
        
        for(int i  = 0; i < list_size; i++) {
            list_sum += procs.get(i).calcTurnarond();
        }
        
        avg = list_sum / list_size;
        
        return avg;
    }
    
    /**
     * Sets the scheduler parameters and initial state
     * @precondition should be called initially within runScheduler
     */
    protected void setSchedulerParams() {
        Process p1 = new Process("p1", new int[] {5, 27, 3, 31, 5, 43, 4, 18, 6, 22, 4, 26, 3, 24, 4});
        Process p2 = new Process("p2", new int[] {4, 48, 5, 44, 7, 42, 12, 37, 9, 76, 4, 41, 9, 31, 7, 43, 8});
        Process p3 = new Process("p3", new int[] {8, 33, 12, 41, 18, 65, 14, 21, 4, 61, 15, 18, 14, 26, 5, 31, 6}); 
        Process p4 = new Process("p4", new int[] {3, 35, 4, 41, 5, 45, 3, 51, 4, 61, 5, 54, 6, 82, 5, 77, 3});
        Process p5 = new Process("p5", new int[] {16, 24, 17, 21, 5, 36, 16, 26, 7, 31, 13, 28, 11, 21, 6, 13, 3, 11, 4});
        Process p6 = new Process("p6", new int[] {11, 22, 4, 8, 5, 10, 6, 12, 7, 14, 9, 18, 12, 24, 15, 30, 8});
        Process p7 = new Process("p7", new int[] {14, 46, 17, 41, 11, 42, 15, 21, 4, 32, 7, 19, 16, 33, 10});
        Process p8 = new Process("p8", new int[] {4, 14, 5, 33, 6, 51, 14, 73, 16, 87, 6});
        
        ready_queue.add(p1);
        ready_queue.add(p2);
        ready_queue.add(p3);
        ready_queue.add(p4);
        ready_queue.add(p5);
        ready_queue.add(p6);
        ready_queue.add(p7);
        ready_queue.add(p8);
    }
    
    /**
     * Prints the results of the Scheduler simulation
     * @precondition all processes are complete
     * @param procs 
     */
    protected void printResults(ArrayList<Process> procs) {
        cpu_util = (cpu / clock) * 100;
        
        System.out.println("\n---------------------------\nAll done at " + 
                clock + " with utilization being " + cpu_util + "%\n");
        
        average_turnaround = calculateAverageTurnaround(completed);
        average_wait = calculateAverageWait(completed);
        average_response = calculateAverageResponse(completed);
        
        System.out.println("Turnaround Times: ");
        
        procs.forEach(p -> {
            System.out.println(p.getName() + ": " + p.calcTurnarond());
        });
        System.out.println("Average turnaround: " + average_turnaround);
        
        System.out.println("Waiting Times: ");
        
        procs.forEach(p -> {
            System.out.println(p.getName() + ": " + p.getWaitTime());
        });
        
        System.out.println("Average wait: " + average_wait);
        
        System.out.println("Response Times: ");
        
        procs.forEach(p -> {
            System.out.println(p.getName() + ": " + p.getResponseTime());
        });

        System.out.println("Average response: " + average_response);
        System.out.println("---------------------------\n");
    }
    /**
     * Method to be implemented by the subclass to run the desired 
     * scheduler algorithm
     * 
     * @precondition Running queues have been set in the constructor
     */
    public abstract void runScheduler();
    
    ArrayList<Process> wait_queue = new ArrayList<>();
    ArrayList<Process> ready_queue = new ArrayList<>();
    ArrayList<Process> completed = new ArrayList<>();
    protected float average_turnaround;
    protected float average_wait;
    protected float average_response;
    protected float cpu = 0;
    protected float cpu_util;
    protected int clock = 0;
    protected boolean verbose = false;
}
