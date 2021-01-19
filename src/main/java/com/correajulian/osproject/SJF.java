package com.correajulian.osproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Non-preemptive SJF class, subclass of Scheduler
 * @author Julian
 */
public class SJF extends Scheduler {
    
    /**
     * Constructor for SJF class
     * @param b Set true for detailed description of events, set false for only 
     * results 
     */
    public SJF(boolean b) {
        running_queue = new ArrayList<>();
    }
    
    /**
     * Alternative constructor for SJF class where only results will be given
     */
    public SJF() {
        running_queue = new ArrayList<>();
    }
    
    /**
     * Implementation of abstract method runScheduler from parent class Scheduler
     * @precondition Running queues have been set in the constructor
     */
    @Override
    public void runScheduler() {
        
        System.out.println("\n\nSJF Scheduler Simulation\n");
        
        setSchedulerParams();
        
        //while all queues that are not completed have processes the simulation is running
        while(!(ready_queue.isEmpty() && wait_queue.isEmpty() && 
                running_queue.isEmpty())) {
            
            if(running_queue.isEmpty() && !(ready_queue.isEmpty())) {
                Collections.sort(ready_queue);
                running_queue.add(ready_queue.get(0));
                ready_queue.get(0).setResponseTime(clock);
                ready_queue.remove(0);
            }
            else if (!running_queue.isEmpty()) {
                cpu++;
                if (running_queue.get(0).isCurrentBurstDone()) {
                    wait_queue.add(running_queue.get(0));
                    running_queue.remove(0);
                }  
            }
            
            //handles running queue process burst
            if (!running_queue.isEmpty()) {
                for (Iterator<Process> i = running_queue.iterator(); i.hasNext();) {
                    Process tmp = i.next();
                    if (tmp.isDone()) {
                        completed.add(tmp);
                        if (verbose == true) {
                            System.out.println(tmp.getName()+" has finished at " + 
                                clock);
                        }
                        tmp.setExitTime(clock);
                        i.remove();
                    }
                    else {
                        tmp.decBursts();
                        tmp.incRuntime();
                    }
                }
            }
            
            //handles ready queue waiting times
            if (!ready_queue.isEmpty()) {
                for (Iterator<Process> i = ready_queue.iterator(); i.hasNext();) {
                    Process tmp = i.next();
                    if (tmp.isDone()) {
                        completed.add(tmp);
                        if (verbose == true) {
                            System.out.println(tmp.getName()+" has finished at " + 
                                clock);
                        }
                        tmp.setExitTime(clock);
                        i.remove();
                    }
                    else {
                        tmp.incReadyTime();
                    }
                }
            }
            
            //handles IO bursts
            if (!wait_queue.isEmpty()) {
                for (Iterator<Process> i = wait_queue.iterator(); i.hasNext();) {
                    Process tmp = i.next();
                    if (tmp.isDone()) {
                        completed.add(tmp);
                        if (verbose == true) {
                            System.out.println(tmp.getName()+" has finished at " + 
                                clock);
                        }
                        tmp.setExitTime(clock);
                        i.remove();
                    }
                    else {
                        if(tmp.isCurrentBurstDone()) {
                            ready_queue.add(tmp);
                            i.remove();
                        }
                        else {
                            tmp.decBursts();
                            tmp.incWaitingTime();
                        }
                    }
                }
            }
            
            if (verbose == true) {
                //Status of simulation and each state
                System.out.print("Process in running at " + clock +": ");
                running_queue.forEach(tmp -> {
                    System.out.print(tmp.getName() + " ");
                });
                System.out.println("\n------------------");

                System.out.print("Processes in ready at " + clock +": ");
                ready_queue.forEach(tmp -> {
                    System.out.print(tmp.getName() + " ");
                });
                System.out.println("\n------------------");

                System.out.print("Processes in waiting at " + clock +": ");
                wait_queue.forEach(tmp -> {
                    System.out.print(tmp.getName() + " ");
                });
                System.out.println("\n------------------");
            }
            
            clock++;
        }
        printResults(completed);
    }

    private final ArrayList<Process> running_queue;
}
