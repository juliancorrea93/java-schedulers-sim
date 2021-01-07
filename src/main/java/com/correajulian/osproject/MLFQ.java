package com.correajulian.osproject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * MLFQ Simulation program
 * @author Julian
 */
public class MLFQ {
     
    public static float calculateAverageWait(ArrayList<Process> procs) {
        float list_sum = 0;
        int list_size = procs.size();
        float avg;
        
        for(int i  = 0; i < list_size; i++) {
            list_sum += procs.get(i).getWaitTime();
        }
        
        avg = list_sum / list_size;
        
        return avg;
    }
    
    public static float calculateAverageResponse(ArrayList<Process> procs) {
        float list_sum = 0;
        int list_size = procs.size();
        float avg;
        
        for(int i  = 0; i < list_size; i++) {
            list_sum += procs.get(i).getResponseTime();
        }
        
        avg = list_sum / list_size;
        
        return avg;
    }
    
    public static float calculateAverageTurnaround(ArrayList<Process> procs) {
        float list_sum = 0;
        int list_size = procs.size();
        float avg;
        
        for(int i  = 0; i < list_size; i++) {
            list_sum += procs.get(i).calcTurnarond();
        }
        
        avg = list_sum / list_size;
        
        return avg;
    }
    
    public static void main(String args[]) {
        //Initialize processes
        Process p1 = new Process("p1", new int[] {5, 27, 3, 31, 5, 43, 4, 18, 6, 22, 4, 26, 3, 24, 4});
        Process p2 = new Process("p2", new int[] {4, 48, 5, 44, 7, 42, 12, 37, 9, 76, 4, 41, 9, 31, 7, 43, 8});
        Process p3 = new Process("p3", new int[] {8, 33, 12, 41, 18, 65, 14, 21, 4, 61, 15, 18, 14, 26, 5, 31, 6}); 
        Process p4 = new Process("p4", new int[] {3, 35, 4, 41, 5, 45, 3, 51, 4, 61, 5, 54, 6, 82, 5, 77, 3});
        Process p5 = new Process("p5", new int[] {16, 24, 17, 21, 5, 36, 16, 26, 7, 31, 13, 28, 11, 21, 6, 13, 3, 11, 4});
        Process p6 = new Process("p6", new int[] {11, 22, 4, 8, 5, 10, 6, 12, 7, 14, 9, 18, 12, 24, 15, 30, 8});
        Process p7 = new Process("p7", new int[] {14, 46, 17, 41, 11, 42, 15, 21, 4, 32, 7, 19, 16, 33, 10});
        Process p8 = new Process("p8", new int[] {4, 14, 5, 33, 6, 51, 14, 73, 16, 87, 6});
        Process focus = new Process("tmp", new int[] {0,0});
        
        //auxiliary variable initialization
        float average_turnaround;
        float average_wait;
        float average_response;
        float cpu = 0;
        float cpu_util;
        
        //setup queues for simulation
        ArrayList<Process> wait_queue = new ArrayList();
        ArrayList<Process> ready_queue = new ArrayList();
        ArrayList<Process> fcfs = new ArrayList(1);
        ArrayList<Process> rr1 = new ArrayList(1);
        ArrayList<Process> rr2 = new ArrayList(1);
        ArrayList<Process> completed = new ArrayList();
        
        //timer variables
        int clock = 0;
        int current_proc_runtime = 0;
        
        //add all processes to ready queue at clock = 0
        ready_queue.add(p1);
        ready_queue.add(p2);
        ready_queue.add(p3);
        ready_queue.add(p4);
        ready_queue.add(p5);
        ready_queue.add(p6);
        ready_queue.add(p7);
        ready_queue.add(p8);
        
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
                        System.out.println(tmp.getName()+" has finished at " + 
                                clock);
                        tmp.setExitTime(clock);
                        i.remove();
                    }
                    //time quantum remove after 5
                    else if (current_proc_runtime >= 5) {
                        tmp.setPriority(2);
                        System.out.println("Process " + tmp.getName() 
                                + " has been moved to lower priority");
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
                        System.out.println(tmp.getName()+" has finished at " + 
                                clock);
                        tmp.setExitTime(clock);
                        i.remove();
                    }
                    //time quantum remove after 10
                    else if (current_proc_runtime >= 10) {
                        System.out.println("Process " + tmp.getName() 
                                + " has been moved to lower priority");
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
                        System.out.println(tmp.getName()+" has finished at " + 
                                clock);
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
                        System.out.println(tmp.getName()+" has finished at " + 
                                clock);
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
                        System.out.println(tmp.getName()+" has finished at " + 
                                clock);
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
            
            
            clock++;
        }
        //cpu utilization calculation
        cpu_util = (cpu / clock) * 100;
        
        System.out.println("\n---------------------------\nAll done at " + 
                clock + " with utilization being " + cpu_util + "%\n");
        
        average_turnaround = calculateAverageTurnaround(completed);
        average_wait = calculateAverageWait(completed);
        average_response = calculateAverageResponse(completed);
        
        System.out.println("Turnaround Times: ");
        
        System.out.println(p1.getName() + ": " + p1.calcTurnarond());
        System.out.println(p2.getName() + ": " + p2.calcTurnarond());
        System.out.println(p3.getName() + ": " + p3.calcTurnarond());
        System.out.println(p4.getName() + ": " + p4.calcTurnarond());
        System.out.println(p5.getName() + ": " + p5.calcTurnarond());
        System.out.println(p6.getName() + ": " + p6.calcTurnarond());
        System.out.println(p7.getName() + ": " + p7.calcTurnarond());
        System.out.println(p8.getName() + ": " + p8.calcTurnarond());
        System.out.println("Average turnaround: " + average_turnaround);
        
        System.out.println("Waiting Times: ");
        
        System.out.println(p1.getName() + ": " + p1.getWaitTime());
        System.out.println(p2.getName() + ": " + p2.getWaitTime());
        System.out.println(p3.getName() + ": " + p3.getWaitTime());
        System.out.println(p4.getName() + ": " + p4.getWaitTime());
        System.out.println(p5.getName() + ": " + p5.getWaitTime());
        System.out.println(p6.getName() + ": " + p6.getWaitTime());
        System.out.println(p7.getName() + ": " + p7.getWaitTime());
        System.out.println(p8.getName() + ": " + p8.getWaitTime());
        System.out.println("Average wait: " + average_wait);
        
        System.out.println("Response Times: ");

        System.out.println(p1.getName() + ": " + p1.getResponseTime());
        System.out.println(p2.getName() + ": " + p2.getResponseTime());
        System.out.println(p3.getName() + ": " + p3.getResponseTime());
        System.out.println(p4.getName() + ": " + p4.getResponseTime());
        System.out.println(p5.getName() + ": " + p5.getResponseTime());
        System.out.println(p6.getName() + ": " + p6.getResponseTime());
        System.out.println(p7.getName() + ": " + p7.getResponseTime());
        System.out.println(p8.getName() + ": " + p8.getResponseTime());
        System.out.println("Average response: " + average_response);
        
    }
}