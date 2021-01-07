/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.correajulian.osproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Julian
 */
public class ProcessTest {
    
    public ProcessTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of decBursts method, of class Process.
     */
    @Test
    public void testDecBursts() {
        System.out.println("decBursts");
        int index = 0;
        Process instance = new Process("p",new int[] {5, 27, 3, 31, 5, 43, 4, 18, 6, 22, 4, 26, 3, 24, 4});
        instance.decBursts();
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of incWaitingTime method, of class Process.
     */
    @Test
    public void testIncWaitingTime() {
        System.out.println("incWaitingTime");
        Process instance = new Process("p",new int[] {5, 27, 3, 31, 5, 43, 4, 18, 6, 22, 4, 26, 3, 24, 4});
        instance.incWaitingTime();
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of incReadyTime method, of class Process.
     */
    @Test
    public void testIncReadyTime() {
        System.out.println("incReadyTime");
        Process instance = new Process("p",new int[] {5, 27, 3, 31, 5, 43, 4, 18, 6, 22, 4, 26, 3, 24, 4});
        instance.incReadyTime();
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of incRuntime method, of class Process.
     */
    @Test
    public void testIncRuntime() {
        System.out.println("incRuntime");
        Process instance = new Process("p",new int[] {5, 27, 3, 31, 5, 43, 4, 18, 6, 22, 4, 26, 3, 24, 4});
        instance.incRuntime();
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of calcTurnarond method, of class Process.
     */
    @Test
    public void testCalcTurnarond() {
        System.out.println("calcTurnarond");
        Process instance = new Process("p",new int[] {5, 27, 3, 31, 5, 43, 4, 18, 6, 22, 4, 26, 3, 24, 4});
        int expResult = 0;
        int result = instance.calcTurnarond();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of isDone method, of class Process.
     */
    @org.junit.Test
    public void testIsDone() {
        System.out.println("isDone");
        Process instance = new Process("p",new int[] {5, 27, 3, 31, 5, 43, 4, 18, 6, 22, 4, 26, 3, 24, 4});
        boolean expResult = false;
        boolean result = instance.isDone();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       
    }

    /**
     * Test of isCurrentBurstDone method, of class Process.
     */
    @org.junit.Test
    public void testIsCurrentBurstDone() {
        System.out.println("isCurrentBurstDone");
        Process instance = new Process("p",new int[] {5, 27, 3, 31, 5, 43, 4, 18, 6, 22, 4, 26, 3, 24, 4});
        boolean expResult = false;
        boolean result = instance.isCurrentBurstDone();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getName method, of class Process.
     */
    @org.junit.Test
    public void testGetName() {
        System.out.println("getName");
        Process instance = new Process("p",new int[] {5, 27, 3, 31, 5, 43, 4, 18, 6, 22, 4, 26, 3, 24, 4});
        String expResult = "p";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getWaitTime method, of class Process.
     */
    @org.junit.Test
    public void testGetWaitTime() {
        System.out.println("getWaitTime");
        Process instance = new Process("p",new int[] {5, 27, 3, 31, 5, 43, 4, 18, 6, 22, 4, 26, 3, 24, 4});
        int expResult = 0;
        int result = instance.getWaitTime();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.

    }
    
}
