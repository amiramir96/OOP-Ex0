package tests;

import ex0.algo.myElevatorAlgo;

import static org.junit.jupiter.api.Assertions.*;

class myElevatorAlgoTest {
    CallForElevetorTest c1 = new CallForElevetorTest(1, 1, 10);
    CallForElevetorTest c2 = new CallForElevetorTest(1, 2, 11);
    CallForElevetorTest c3 = new CallForElevetorTest(1, 33, 44);
    CallForElevetorTest c4 = new CallForElevetorTest(-1, 22, 11);
    CallForElevetorTest c5 = new CallForElevetorTest(-1, 10, 4);
    CallForElevetorTest c6 = new CallForElevetorTest(-1, 1, -7);
    CallForElevetorTest c7 = new CallForElevetorTest(-1, -5, -7);
    ElevetorForTest[] allElev = new ElevetorForTest[4];

    @org.junit.jupiter.api.Test
    void getBuilding() {
        for (int i=0; i < allElev.length; i++){
            allElev[i] = new ElevetorForTest(i+1, 2,3,i);
        }
        BuildingForTest b = new BuildingForTest("kanGarimBekef", -10, 100, allElev);
        myElevatorAlgo check = new myElevatorAlgo(b);
        assertEquals(b, check.getBuilding());
    }

    @org.junit.jupiter.api.Test
    void algoName() {
        for (int i=0; i < allElev.length; i++){
            allElev[i] = new ElevetorForTest(i+1, 2,3,i);
        }
        BuildingForTest b = new BuildingForTest("kanGarimBekef", -10, 100, allElev);
        myElevatorAlgo check = new myElevatorAlgo(b);
        assertEquals("B-Team algo", check.algoName());
    }

    /**
     * for deeper tests for the allocate phase - look at:
     * https://github.com/amiramir96/OOP-Ex0/tree/main/tests
     */
    @org.junit.jupiter.api.Test
    void allocateAnElevator() {
        for (int i=0; i < allElev.length; i++){
            allElev[i] = new ElevetorForTest(i+1, 2,3,i);
        }
        BuildingForTest b = new BuildingForTest("kanGarimBekef", -10, 100, allElev);
        myElevatorAlgo check = new myElevatorAlgo(b);
        int elev = check.allocateAnElevator(c1);
        assertEquals(3, elev);
        elev = check.allocateAnElevator(c2);
        assertEquals(2, elev);
        elev = check.allocateAnElevator(c3);
        assertEquals(3, elev);
        elev = check.allocateAnElevator(c4);
        assertEquals(1, elev);
        //duplicate the same call - check if proccess of - ADD IF NO DUPLICATES WORKS
        elev = check.allocateAnElevator(c5);
        assertEquals(0, elev);
        elev = check.allocateAnElevator(c5);
        assertEquals(0, elev);
        elev = check.allocateAnElevator(c5);
        assertEquals(0, elev);

        for (int i=0; i < allElev.length; i++){
            allElev[i] = new ElevetorForTest((i+1)*2, 2,3,i);
        }
        myElevatorAlgo check2 = new myElevatorAlgo(b);
        elev = check2.allocateAnElevator(c6);
        assertEquals(3, elev);
        elev = check2.allocateAnElevator(c2);
        assertEquals(2, elev);
        elev = check2.allocateAnElevator(c3);
        assertEquals(1, elev);
        elev = check2.allocateAnElevator(c4);
        assertEquals(2, elev);
        //duplicate the same call - check if proccess of - ADD IF NO DUPLICATES WORKS
        elev = check2.allocateAnElevator(c5);
        assertEquals(0, elev);
        elev = check2.allocateAnElevator(c5);
        assertEquals(0, elev);
    }

    @org.junit.jupiter.api.Test
    void cmdElevator() {
        for (int i=0; i < allElev.length; i++){
            allElev[i] = new ElevetorForTest(i+1, 2,3,i);
        }
        BuildingForTest b = new BuildingForTest("kanGarimBekef", -10, 100, allElev);
        myElevatorAlgo check = new myElevatorAlgo(b);
        int elev = check.allocateAnElevator(c1);
        check.cmdElevator(elev);
        assertEquals(1, b.getElevetor(elev).getState()); //go to UP direction
        elev = check.allocateAnElevator(c2);
        check.cmdElevator(elev);
        assertEquals(1, b.getElevetor(elev).getState()); //go to DOWN direction
        elev = check.allocateAnElevator(c3);
        check.cmdElevator(elev);
        assertEquals(1, b.getElevetor(elev).getState());
        elev = check.allocateAnElevator(c4);
        check.cmdElevator(elev);
        assertEquals(1, b.getElevetor(elev).getState());
        elev = check.allocateAnElevator(c7);
        check.cmdElevator(elev);
        assertEquals(-1, b.getElevetor(elev).getState());
    }
}