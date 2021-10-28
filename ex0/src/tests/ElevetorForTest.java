package tests;

import ex0.Elevator;

public class ElevetorForTest implements Elevator{
    double speed;
    int delayOpenClose;
    int delayStartStop;
    int switchState; //1 for UP 0 for LEVEL -1 for DOWN
    int currFloor;
    int ID;

    public ElevetorForTest(double speedPerFloor, int delayDoors, int delayEngine, int id){
        this.speed = speedPerFloor;
        this.delayOpenClose = delayDoors;
        this.delayStartStop = delayEngine;
        this.switchState = 0;
        this.currFloor = 0;
        this.ID = id;
    }

    @Override
    public int getMinFloor() {
        return Integer.MIN_VALUE;
    }

    @Override
    public int getMaxFloor() {
        return Integer.MAX_VALUE;
    }

    @Override
    public double getTimeForOpen() {
        return this.delayOpenClose;
    }

    @Override
    public double getTimeForClose() {
        return this.delayOpenClose;
    }

    @Override
    public int getState() {
        return this.switchState;
    }

    @Override
    public int getPos() {
        return this.currFloor;
    }

    @Override
    public boolean goTo(int floor) {
        boolean ans = false;
        if (this.currFloor < floor){
            this.switchState = 1;
            ans = true;
        }
        else if (this.currFloor > floor) {
            this.switchState = -1;
            ans = true;
        }
        return ans;
    }

    @Override
    public boolean stop(int floor) {
        return false;
    }

    @Override
    public double getSpeed() {
        return this.speed;
    }

    @Override
    public double getStartTime() {
        return this.delayStartStop;
    }

    @Override
    public double getStopTime() {
        return this.delayStartStop;
    }

    @Override
    public int getID() {
        return this.ID;
    }
}
