package com.correajulian.osproject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * MLFQ class, subclass of Scheduler
 * @author Julian
 */
public class MLFQ extends Scheduler {
    
    /**
     * Constructor for MLFQ class
     * @param b Set true for detailed description of events, set false for only 
     * results 
     */
    public MLFQ(boolean b) {
        focus = new Process("tmp", new int[] {0,0});
        current_proc_runtime = 0;
        rr1 = new ArrayList<>(1);
        rr2 = new ArrayList<>(1);
        fcfs = new ArrayList<>(1);
        verbose = b;
    }
    
    /**
     * Alternative constructor for MLFQ class where only results will be given
     */
    public MLFQ() {
        focus = new Process("tmp", new int[] {0,0});
        current_proc_runtime = 0;
        rr1 = new ArrayList<>(1);
        rr2 = new ArrayList<>(1);
        fcfs = new ArrayList<>(1);
    }
    
    /**
     * Implementation of abstract method runScheduler from parent class Scheduler
     * @precondition Running queues have been set in the constructor
     */
    @Override
    public void runScheduler() {
        
        System.out.println("\n\nMLFQ Scheduler Simulation\n");
        
        setSchedulerParams();
        
        //while all queues that are not completed queue have elements simulation is active
        while(!(ready_queue.isEmpty() && wait_queue.isEmpty() && 
                fcfs.isEmpty() && rr1.isEmpty() && rr2.isEmpty())) {
            
            //check if any running queues are empty and fill them if there is a process with a matching priority
            if((rr1.isEmpty() || rr2.isEmpty() || fcfs.isEmpty()) && !(ready_queue.isEmpty())) {
                if (rr1.isEmpty() && ready_queue.get(0).getPriority() == 3) {
                    ready_queue.get(0).setResponseTime(clock);
                    rr1.add(ready_queue.get(0));
                    ready_queue.remove(0);
                }
                else if (rr2.isEmpty() && ready_queue.get(0).getPriority() == 2) {
                    ready_queue.get(0).setResponseTime(clock);
                    rr2.add(ready_queue.get(0));
                    ready_queue.remove(0);
                }
                else if (fcfs.isEmpty() && ready_queue.get(0).getPriority() == 1) {
                    ready_queue.get(0).setResponseTime(clock);
                    fcfs.add(ready_queue.get(0));
                    ready_queue.remove(0);
                }
            }
            
            //if a process is in any of the running queues increment cpu utilization counter
            if (!fcfs.isEmpty() || !rr1.isEmpty() || !rr2.isEmpty()) {
                cpu++;
            }
            
            //Conditionals for running queues preempting is detected with the focus instance
            //first checks if focus has shifted, then checks if process's current burst is done
            //To track round robin time quantums I use current_proc_time
            if (!rr1.isEmpty()) {
                if (focus != rr1.get(0)) {
                    current_proc_runtime = 0;
                    focus = rr1.get(0);
                }
                if (rr1.get(0).isCurrentBurstDone() && !rr1.get(0).isDone()) {
                    wait_queue.add(rr1.get(0));
                    rr1.remove(0);
                }
                for (Iterator<Process> i = rr1.iterator(); i.hasNext();) {
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
                    //time quantum remove after 5
                    else if (current_proc_runtime >= 5) {
                        tmp.setPriority(2);
                        if (verbose == true) {
                            System.out.println("Process " + tmp.getName() 
                                    + " has been moved to lower priority");
                        }
                        current_proc_runtime = 0;
                        ready_queue.add(tmp);
                        i.remove();
                    }
                    else {
                        tmp.decBursts();
                        tmp.incRuntime();
                        current_proc_runtime++;
                        rr2.forEach(p -> {
                            p.incReadyTime();
                        });
                        fcfs.forEach(p -> {
                            p.incReadyTime();
                        });
                    }
                }
            }
            else if (!rr2.isEmpty()) {
                
                if (focus != rr2.get(0)) {
                    current_proc_runtime = 0;
                    focus = rr2.get(0);
                }
                if (rr2.get(0).isCurrentBurstDone() && !rr2.get(0).isDone()) {
                    wait_queue.add(rr2.get(0));
                    rr2.remove(0);
                }
                for (Iterator<Process> i = rr2.iterator(); i.hasNext();) {
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
                    //time quantum remove after 10
                    else if (current_proc_runtime >= 10) {
                        if (verbose == true) {
                            System.out.println("Process " + tmp.getName() 
                                    + " has been moved to lower priority");
                        }
                        tmp.setPriority(1);
                        current_proc_runtime = 0;
                        ready_queue.add(tmp);
                        i.remove();
                    }
                    else {
                        tmp.decBursts();
                        tmp.incRuntime();
                        current_proc_runtime++;
                        fcfs.forEach(p -> {
                            p.incReadyTime();
                        });
                    }
                }
            }
            else if (!fcfs.isEmpty()) {
                if (focus != fcfs.get(0)) {
                    current_proc_runtime = 0;
                    focus = fcfs.get(0);
                }
                if (fcfs.get(0).isCurrentBurstDone() && !fcfs.get(0).isDone()) {
                    wait_queue.add(fcfs.get(0));
                    fcfs.remove(0);
                }
                
                for (Iterator<Process> i = fcfs.iterator(); i.hasNext();) {
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
            
            //increments ready times
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
                //Status of simulation for each tick
                System.out.print("Process in running at " + clock +": ");
                System.out.print("rr1: ");
                rr1.forEach(tmp -> {
                    System.out.print(tmp.getName() + " ");
                });
                System.out.print("rr2: ");
                rr2.forEach(tmp -> {
                    System.out.print(tmp.getName() + " ");
                });
                System.out.print("fcfs: ");
                fcfs.forEach(tmp -> {
                    System.out.print(tmp.getName() + " ");
                });
                System.out.println("\n------------------");

                System.out.print("Processes in ready at " + clock +": ");
                ready_queue.forEach(tmp -> {
                    System.out.print(tmp.getName() + " ");
                });
                System.out.println("\n------------------");

                System.out.print("Processes in IO at " + clock +": ");
                wait_queue.forEach(tmp -> {
                    System.out.print(tmp.getName() + " ");
                });
                System.out.println("\n------------------");
            }
            
            clock++;
        }
        printResults(completed);
    }
    
    private final ArrayList<Process> rr1;
    private final ArrayList<Process> rr2;
    private final ArrayList<Process> fcfs;
    private Process focus;
    private int current_proc_runtime;
}