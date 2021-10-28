package tests;

import ex0.CallForElevator;

public class CallForElevetorTest implements CallForElevator {
    int mission; //1 for UP -1 for DOWN
    int src; //source floor
    int dest; //dest floor
    int myElevetorHandeler;

    public CallForElevetorTest(int type, int src, int dest){
        this.mission = type;
        this.src = src;
        this.dest = dest;
    }

    @Override
    public int getState() {
        return 0;
    }

    @Override
    public double getTime(int state) {
        return 0;
    }

    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public int getType() {
        return this.mission;
    }

    @Override
    public int allocatedTo() {
        return this.myElevetorHandeler;
    }
}
