package com.correajulian.osproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Simple non-preemptive SJF program
 * @author Julian
 */
public class SJF {
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
        //Process initialization and auxiliary variable initialization
        Process p1 = new Process("p1", new int[] {5, 27, 3, 31, 5, 43, 4, 18, 6, 22, 4, 26, 3, 24, 4});
        Process p2 = new Process("p2", new int[] {4, 48, 5, 44, 7, 42, 12, 37, 9, 76, 4, 41, 9, 31, 7, 43, 8});
        Process p3 = new Process("p3", new int[] {8, 33, 12, 41, 18, 65, 14, 21, 4, 61, 15, 18, 14, 26, 5, 31, 6}); 
        Process p4 = new Process("p4", new int[] {3, 35, 4, 41, 5, 45, 3, 51, 4, 61, 5, 54, 6, 82, 5, 77, 3});
        Process p5 = new Process("p5", new int[] {16, 24, 17, 21, 5, 36, 16, 26, 7, 31, 13, 28, 11, 21, 6, 13, 3, 11, 4});
        Process p6 = new Process("p6", new int[] {11, 22, 4, 8, 5, 10, 6, 12, 7, 14, 9, 18, 12, 24, 15, 30, 8});
        Process p7 = new Process("p7", new int[] {14, 46, 17, 41, 11, 42, 15, 21, 4, 32, 7, 19, 16, 33, 10});
        Process p8 = new Process("p8", new int[] {4, 14, 5, 33, 6, 51, 14, 73, 16, 87, 6});
        
        float average_turnaround;
        float average_wait;
        float average_response;
        float cpu = 0;
        float cpu_util;
        
        ArrayList<Process> wait_queue = new ArrayList();
        ArrayList<Process> ready_queue = new ArrayList();
        ArrayList<Process> running_queue = new ArrayList(1);
        ArrayList<Process> completed = new ArrayList();
        
        int clock = 0;
        
        //Loads ready queue with processes at clock 0
        ready_queue.add(p1);
        ready_queue.add(p2);
        ready_queue.add(p3);
        ready_queue.add(p4);
        ready_queue.add(p5);
        ready_queue.add(p6);
        ready_queue.add(p7);
        ready_queue.add(p8);
        
        //Initialization for Processes, auxiliary variables, and cpu clock
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
            
            //handles ready queue waiting times
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
            
            clock++;
        }
        //Calculation and Simulation results display
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
