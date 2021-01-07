package com.correajulian.osproject;

import java.util.ArrayList;

/**
 * Process class for the simulations
 * @author Julian
 */
public class Process implements Comparable{
    public Process(String proc, int[] process_bursts) {
        /**
         * Constructor for Process class
         * @param proc: (String) name for the proc
         * @param process_bursts: (int[]) array of integers for bursts
         */
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
    public void decBursts() {
        /**
         * Decrements the burst by one to update amount of time left for burst
         * @precondition burst given is > 0
         */
        
        this.burst.set(0, this.burst.get(0)-1);
    }
    public void incWaitingTime() {
        /**
         * Increments the time spent in IO for the process
         */
        this.io_time++;
    }
    public void incReadyTime() {
        /**
         * Increments the time spent in Ready for the process
         */
        this.time_in_ready++;
    }
    public void incRuntime() {
        /**
         * Increments the time spent in Running for the process
         */
        this.time_in_running++;
    }
    public int calcTurnarond() {
        /**
         * Calculates the turnaround time or time from submission of process to
         * exit
         * @return Returns the calculated turnaround time
         */
        this.turnaround_time = this.exit;
        //since all arrive at 0 no time needed
        return this.turnaround_time;
    }
    public boolean isDone() {
        /**
         * Checks if there are bursts left to complete
         * @return returns true if there are no bursts left to complete or false if process still has bursts to complete
         */
        if(burst.isEmpty()) {
            this.completed = true;
        }

        return burst.isEmpty();
    }
    public boolean isCurrentBurstDone() {
        /**
         * Checks if the current burst is done
         * @return returns true or false regarding status of current burst time being 0
         */
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
    @Override
    public int compareTo(Object o) {
        /**
         * Implements comparator interface to sort a list of Processes
         * @return returns the difference of the current burst compared to another processes current burst
         */
        int comparison = ((Process)o).getCurrentBurst();
        
        return this.getCurrentBurst()-comparison;
    }
    public int getCurrentBurst() {
        /**
         * Gets the current burst time
         * @return returns the current burst's time
         */
        return this.burst.get(0);
    }
    
    public String getName() {
        /**
         * Gets the name of the process
         * @return returns name of process
         */
        return this.name;
    }
    
    public int getResponseTime() {
        /**
         * Gets the response time
         * @return value for response time
         */
        return this.response_time;
    }
    public int getExit() {
        /**
         * Gets the exit time
         * @return value for exit time
         */
        return this.exit;
    }
    
    public int getWaitTime() {
        /**
         * Gets the time spent in IO
         * @return value of time spent in IO
         */
        return this.time_in_ready;
    }
    public void setResponseTime(int time) {
        /**
         * Sets response time for Process, if already set does nothing
         */
        if (response_time < 0) {
            this.response_time = time;
        }
    }
    
    public void setExitTime(int time) {
        /**
         * Sets exit time
         * @precondition encase this method within a conditional checking if isDone()
         * returns true
         */
        this.exit = time;
    }
    public int getPriority() {
        /**
         * Gets current priority of process
         * @return value of current priority
         */
        return this.priority;
    }
    public void setPriority(int val) {
        /**
         * Sets the value of priority
         * @param Integer value for priority
         * @precondition 0 < val <= 3
         */
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
