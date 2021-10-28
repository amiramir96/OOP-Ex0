package tests;

import ex0.CallForElevator;
import ex0.algo.*;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class DataCallsTest {
    CallForElevetorTest c1 = new CallForElevetorTest(1, 1, 10);
    CallForElevetorTest c2 = new CallForElevetorTest(1, 2, 11);
    CallForElevetorTest c3 = new CallForElevetorTest(1, 33, 44);
    CallForElevetorTest c4 = new CallForElevetorTest(-1, 22, 11);
    CallForElevetorTest c5 = new CallForElevetorTest(-1, 10, 4);
    CallForElevetorTest c6 = new CallForElevetorTest(-1, 1, -7);
    ElevetorForTest[] allElev = new ElevetorForTest[4];

    //check getter first (nextStop)
    @org.junit.jupiter.api.Test
    void nextStop() {
        for (int i=0; i < allElev.length; i++){
            allElev[i] = new ElevetorForTest(i+1, 2,3,i);
        }
        BuildingForTest b = new BuildingForTest("kanGarimBekef", -10, 100, allElev);
        DataCalls d1 = new DataCalls(b);
        DataCalls d2 = new DataCalls(b);
        DataCalls d3 = new DataCalls(b);
        DataCalls d4 = new DataCalls(b);

        d1.getElev(0).addFirst(1);
        d2.getElev(1).addFirst(5);
        d3.getElev(2).addFirst(6);
        d4.getElev(3).addFirst(11);
        assertEquals(1, d1.nextStop(0));
        assertEquals(5, d2.nextStop(1));
        assertEquals(6, d3.nextStop(2));
        assertEquals(11, d4.nextStop(3));



    }

    //check remove
    @org.junit.jupiter.api.Test
    void removeNextStop() {
        for (int i=0; i < allElev.length; i++){
            allElev[i] = new ElevetorForTest(i+1, 2,3,i);
        }
        BuildingForTest b = new BuildingForTest("kanGarimBekef", -10, 100, allElev);
        DataCalls d1 = new DataCalls(b);

        d1.getElev(0).addFirst(1);
        d1.getElev(0).addFirst(5);
        d1.removeNextStop(0);
        assertEquals(1, d1.nextStop(0));
        d1.removeNextStop(0);
        assertEquals("[]", ""+d1.getElev(0));

        d1.getElev(2).addFirst(6);
        d1.getElev(2).add(1, 8);
        d1.removeNextStop(2);
        assertEquals(8, d1.nextStop(2));


        d1.getElev(3).addFirst(11);
        d1.getElev(3).addFirst(12);
        d1.getElev(3).addFirst(14);
        d1.removeNextStop(3);
        assertEquals(12, d1.nextStop(3));
        d1.removeNextStop(3);
        assertEquals(11, d1.nextStop(3));

    }

    @org.junit.jupiter.api.Test
    void getSpecific() {
        for (int i=0; i < allElev.length; i++){
            allElev[i] = new ElevetorForTest(i+1, 2,3,i);
        }
        BuildingForTest b = new BuildingForTest("kanGarimBekef", -10, 100, allElev);
        DataCalls d1 = new DataCalls(b);
        d1.getElev(0).addFirst(10);
        d1.getElev(0).addFirst(12);
        d1.getElev(0).addFirst(9);
        d1.getElev(0).addFirst(100);
        d1.getElev(0).addFirst(15);
        d1.getElev(0).addFirst(-3);
        d1.getElev(0).addFirst(8);
        d1.getElev(0).addFirst(9);
        d1.getElev(0).addFirst(33);
        assertEquals(33, d1.getSpecific(0, 0));
        assertEquals(9, d1.getSpecific(0, 1));
        assertEquals(8, d1.getSpecific(0, 2));
        assertEquals(-3, d1.getSpecific(0, 3));
        assertEquals(15, d1.getSpecific(0, 4));
        assertEquals(100, d1.getSpecific(0, 5));
        assertEquals(9, d1.getSpecific(0, 6));
        assertEquals(12, d1.getSpecific(0, 7));
        assertEquals(10, d1.getSpecific(0, 8));
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        for (int i=0; i < allElev.length; i++){
            allElev[i] = new ElevetorForTest(i+1, 2,3,i);
        }
        BuildingForTest b = new BuildingForTest("kanGarimBekef", -10, 100, allElev);
        DataCalls d1 = new DataCalls(b);
        d1.getElev(0).addFirst(1);
        d1.removeNextStop(0);
        assertEquals(true, d1.isEmpty(0));
        assertEquals(true, d1.isEmpty(1));
        assertEquals(true, d1.isEmpty(2));
        assertEquals(true, d1.isEmpty(3));
        d1.getElev(0).addFirst(5);
        assertEquals(false, d1.isEmpty(0));
        d1.removeNextStop(0);
        assertEquals(true, d1.isEmpty(0));
    }

    @org.junit.jupiter.api.Test
    void noOfStops() {
        for (int i=0; i < allElev.length; i++){
            allElev[i] = new ElevetorForTest(i+1, 2,3,i);
        }
        BuildingForTest b = new BuildingForTest("kanGarimBekef", -10, 100, allElev);
        DataCalls d1 = new DataCalls(b);
        d1.getElev(0).addFirst(10);
        d1.getElev(0).addFirst(12);
        d1.getElev(0).addFirst(9);
        d1.getElev(0).addFirst(100);
        d1.getElev(0).addFirst(15);
        d1.getElev(0).addFirst(-3);
        d1.getElev(0).addFirst(8);
        d1.getElev(0).addFirst(9);
        d1.getElev(0).addFirst(33);
        assertEquals(9, d1.noOfStops(0));
        d1.getElev(0).removeFirst();
        assertEquals(8, d1.noOfStops(0));
        d1.getElev(0).removeFirst();
        assertEquals(7, d1.noOfStops(0));
        d1.getElev(0).removeFirst();
        assertEquals(6, d1.noOfStops(0));
        d1.getElev(0).removeFirst();
        assertEquals(5, d1.noOfStops(0));
        d1.getElev(0).removeFirst();
        assertEquals(4, d1.noOfStops(0));
        d1.getElev(0).removeFirst();
        assertEquals(3, d1.noOfStops(0));
        d1.getElev(0).removeFirst();
        assertEquals(2, d1.noOfStops(0));
        d1.getElev(0).removeFirst();
        assertEquals(1, d1.noOfStops(0));
        d1.getElev(0).removeFirst();
        assertEquals(0, d1.noOfStops(0));
    }

    @org.junit.jupiter.api.Test
    void getElev() {
        for (int i=0; i < allElev.length; i++){
            allElev[i] = new ElevetorForTest(i+1, 2,3,i);
        }
        BuildingForTest b = new BuildingForTest("kanGarimBekef", -10, 100, allElev);
        DataCalls d1 = new DataCalls(b);
        assertEquals(d1.getElev(0), d1.getElev(0));

    }
}