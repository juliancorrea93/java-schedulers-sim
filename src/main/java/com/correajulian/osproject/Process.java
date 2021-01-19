package com.correajulian.osproject;

import java.util.ArrayList;

/**
 * Process class for the simulations
 * @author Julian
 */
public class Process implements Comparable{
    /**
     * Constructor for Process class
     * @param proc: (String) name for the proc
     * @param process_bursts : (int[]) array of integers for bursts
     */
    public Process(String proc, int[] process_bursts) {
        name = proc;
        burst = new ArrayList();
        for(int b:process_bursts) {
            burst.add(b);
        }
        turnaround_time = 0;
        io_time = 0;
        time_in_ready = 0;
        time_in_running = 0;
        response_time = -1;
        exit = -1;
        completed = false;
        priority = 3;
    }
    /**
     * Decrements the burst by one to update amount of time left for burst
     * @precondition burst given is > 0
     */
    public void decBursts() {   
        this.burst.set(0, this.burst.get(0)-1);
    }
    /**
     * Increments the time spent in IO for the process
     */
    public void incWaitingTime() {
        this.io_time++;
    }
    /**
     * Increments the time spent in Ready for the process
     */
    public void incReadyTime() {
        this.time_in_ready++;
    }
    /**
     * Increments the time spent in Running for the process
     */
    public void incRuntime() {
        this.time_in_running++;
    }
    /**
     * Calculates the turnaround time or time from submission of process to
     * exit
     * @return Returns the calculated turnaround time
     */
    public int calcTurnarond() {
        this.turnaround_time = this.exit;
        //since all arrive at 0 no time needed
        return this.turnaround_time;
    }
    /**
     * Checks if there are bursts left to complete
     * @return returns true if there are no bursts left to complete or false if process still has bursts to complete
     */
    public boolean isDone() {
        if(burst.isEmpty()) {
            this.completed = true;
        }

        return burst.isEmpty();
    }
    /**
     * Checks if the current burst is done
     * @return returns true or false regarding status of current burst time being 0
     */
    public boolean isCurrentBurstDone() {
        if (this.isDone()) {
            return true;
        }
        else {
            if(burst.get(0) == 0) {
                burst.remove(0);
                return true;
            }
            else {
                return false;
            }
        }
    }
    /**
     * Implements comparator interface to sort a list of Processes
     * @param o Object to be compared, in this case Process instance
     * @return returns the difference of the current burst compared to another processes current burst
     */
    @Override
    public int compareTo(Object o) {
        int comparison = ((Process)o).getCurrentBurst();
        
        return this.getCurrentBurst()-comparison;
    }
    /**
     * Gets the current burst time
     * @return returns the current burst's time
     */
    public int getCurrentBurst() {
        return this.burst.get(0);
    }
    
    /**
     * Gets the name of the process
     * @return returns name of process
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Gets the response time
     * @return value for response time
     */
    public int getResponseTime() {
        return this.response_time;
    }
    /**
     * Gets the exit time
     * @return value for exit time
     */
    public int getExit() {
        return this.exit;
    }
    
    /**
     * Gets the time spent in IO
     * @return value of time spent in IO
     */
    public int getWaitTime() {
        return this.time_in_ready;
    }
    /**
     * Sets response time for Process, if already set does nothing
     * @param time current clock time
     */
    public void setResponseTime(int time) {
        if (response_time < 0) {
            this.response_time = time;
        }
    }
    /**
     * Sets exit time
     * @param time current clock time
     * @precondition encase this method within a conditional checking if isDone()
     * returns true
     */
    public void setExitTime(int time) {
        
        this.exit = time;
    }
    /**
     * Gets current priority of process
     * @return value of current priority
     */
    public int getPriority() {
        return this.priority;
    }
    /**
     * Sets the value of priority
     * @param val Integer value for new priority
     * @precondition 0 < val <= 3
     */
    public void setPriority(int val) {
        this.priority = val;
    }
    
    private final ArrayList<Integer> burst;
    private final String name;
    private int turnaround_time;
    private int io_time;
    private int time_in_running;
    private int time_in_ready;
    private int response_time;
    private int exit;
    private int priority;
    private boolean completed;
}
