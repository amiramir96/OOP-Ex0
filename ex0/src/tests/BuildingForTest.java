package tests;

import ex0.Building;
import ex0.Elevator;

public class BuildingForTest implements Building {
    String name = "testBuildingONLY";
    int min;
    int max;
    Elevator[] myBuildingElevetors;

    public BuildingForTest(String s, int minFloor, int maxFloor, Elevator[] arrOfOBJ){
        this.name = s;
        this.min = minFloor;
        this.max = maxFloor;
        this.myBuildingElevetors = new Elevator[arrOfOBJ.length];
        for(int i=0; i < arrOfOBJ.length; i++){
            this.myBuildingElevetors[i] = arrOfOBJ[i];
        }
    }

    @Override
    public String getBuildingName() {
        return name;
    }

    @Override
    public int minFloor() {
        return min;
    }

    @Override
    public int maxFloor() {
        return max;
    }

    @Override
    public int numberOfElevetors() {
        return this.myBuildingElevetors.length;
    }

    @Override
    public Elevator getElevetor(int i) {
        return this.myBuildingElevetors[i];
    }
}
