package ex0.algo;
import ex0.simulator.Elevator_A;
import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.net.StandardSocketOptions;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class represents the simplest algorithm for elevator allocation - it uses a trivial concept of Shabat-Elevator (ignoring all the calls).
 * It simply stops on any floor on the way up and then stops on any floor on the way down.
 */
public class myElevatorAlgo implements ElevatorAlgo {
    public static final int UP=1, DOWN=-1;
    private int _direction;
    private Building _building;
    private ArrayList<LinkedList<Integer>> dataCalls;
    public myElevatorAlgo(Building b) {
        _building = b;
        dataCalls = new ArrayList<LinkedList<Integer>>(b.numberOfElevetors());
        for(int i=0; i < b.numberOfElevetors(); i++){
            this.dataCalls.add(new LinkedList<Integer>());
        }
        _direction = UP;
    }



//    private HashSet<Integer> upEle = new HashSet<Integer>();
//    private HashSet<Integer> downEle = new HashSet<Integer>();
//    private HashSet<Integer> levelEle = new HashSet<Integer>();
//
//    private void initialize(HashSet<Integer> levelEle){
//        for (int i=0; i < this._building.numberOfElevetors(); i++){
//            levelEle.add(i);
//        }
//    }

    @Override
    public Building getBuilding() {
        return _building;
    }

    @Override
    public String algoName() {
        return "B-Team algo";
    }

    @Override

    /**
     *     split to 2 options:
     *     1- mission type is for UP
     *     2- mission type is for DOWN
     *     get from the helper functions the ID number of elevetor which is optimal
     * @param:
     * from CallForElevator:
     * @origin - src Floor call
     * @destiny - dest Floor call
     * @typeMove - 1 for up, -1 for down
     * @optimal - ID elevator choosen to return
     */

    public int allocateAnElevator(CallForElevator c) {
//        int ind = rand(0,_building.numberOfElevetors()); // Random allocation of an elevator - you know better than that!
//        return ind;
        int origin, typeMove;
        int[] optimal = new int[2];
        int destiny; //tribute ur destiny => Maple Destiny 4ever, PandaEyes my love <3
        origin = c.getSrc();
        destiny = c.getDest();
        typeMove = c.getType();
        if (typeMove == 1){
            optimal = allocateAnElevatorHelperViaUpMove(origin, destiny, c);
        }
        else {
            optimal = allocateAnElevatorHelperViaDownMove(origin, destiny, c);
        }
        addOnlyIfNoDuplicates(optimal[1], c.getDest(), this.dataCalls.get(optimal[2]));
        addOnlyIfNoDuplicates(optimal[0], c.getSrc(), this.dataCalls.get(optimal[2]));
        return optimal[2];
    }

    /**
     *the "Brain" of the algorithm.
     *hold 3 steps to prioritize via the helper main functions:
     *1- elevators in the same "type" of mission
     *2- elevators in "LEVEL" state
     *3- all the elevatros
     *if we find at ea step even 1 elevetor relevant, func return ID of an elevator from that category
     *
     * @param origin - src Floor call
     * @param destiny - dest Floor call
     * @param c - CallForElevator the currect call which we trying to work on
     * @elevToAlloc - ID elevator choosen to return
     * @return optimal elevetor ID for this task.
     */
    private int[] allocateAnElevatorHelperViaUpMove(int origin, int destiny, CallForElevator c) {
        int elevToAlloc = -1;
        int floorDestIdxToAdd;
        int[] whereToAddAtDataCalls = new int[2];
        int[] outputArr = new int[3];
        double timeVal = Double.MAX_VALUE; //time needed to end task for choosen elevator
        double timeTemp;
        int numOfElevators = this._building.numberOfElevetors();
        Elevator elev;
        //first case - check all elevetors which in UP mission, taking the optimal if exists
        for (int i=0; i < numOfElevators; i++){
            elev = this._building.getElevetor(i);

            //elev.getState() == 0 ||
            if (elev.getState() == 0 || elev.getState() == c.getType() && elev.getPos() < origin){
                whereToAddAtDataCalls = checkWhereToaddAtDataCalls(c, i);
                floorDestIdxToAdd = whereToAddAtDataCalls[1];
                timeTemp = timeToEndMission(elev, origin, destiny, floorDestIdxToAdd, 1);
                if (timeVal > timeTemp){
                    elevToAlloc = i;
                    timeVal = timeTemp;
                    outputArr[0] = whereToAddAtDataCalls[0];
                    outputArr[1] = whereToAddAtDataCalls[1];
                    outputArr[2] = i;
                }
            }
        }
        if (elevToAlloc != -1){
            return outputArr;
        }
//        for (int i=0; i < numOfElevators; i++){
//            elev = this._building.getElevetor(i);
//            if (elev.getState() == 0){
//                whereToAddAtDataCalls = checkWhereToaddAtDataCalls(c, i);
//                floorDestIdxToAdd = whereToAddAtDataCalls[1];
//                timeTemp = timeToEndMission(elev, origin, destiny, floorDestIdxToAdd, 1);
//                if (timeVal > timeTemp){
//                    elevToAlloc = i;
//                    timeVal = timeTemp;
//                    outputArr[0] = whereToAddAtDataCalls[0];
//                    outputArr[1] = whereToAddAtDataCalls[1];
//                    outputArr[2] = i;
//                }
//            }
//        }
//        if (elevToAlloc != -1){
//            return outputArr;
//       }
        for (int i=0; i < numOfElevators; i++){
            elev = this._building.getElevetor(i);
            whereToAddAtDataCalls = checkWhereToaddAtDataCalls(c, i);
            floorDestIdxToAdd = whereToAddAtDataCalls[1];
            timeTemp = timeToEndMission(elev, origin, destiny, floorDestIdxToAdd, 1);
            if (timeVal > timeTemp){
                elevToAlloc = i;
                timeVal = timeTemp;
                outputArr[0] = whereToAddAtDataCalls[0];
                outputArr[1] = whereToAddAtDataCalls[1];
                outputArr[2] = i;
            }
        }
        return outputArr;
    }
    /**
     *the "Brain" of the algorithm.
     *hold 3 steps to prioritize via the helper main functions:
     *1- elevators in the same "type" of mission
     *2- elevators in "LEVEL" state
     *3- all the elevatros
     *if we find at ea step even 1 elevetor relevant, func return ID of an elevator from that category
     *
     * @param origin - src Floor call
     * @param destiny - dest Floor call
     * @param c - CallForElevator the currect call which we trying to work on
     * @elevToAlloc - ID elevator choosen to return
     * @return optimal elevetor ID for this task.
     */
    private int[] allocateAnElevatorHelperViaDownMove(int origin, int destiny, CallForElevator c) {
        int elevToAlloc = -1;
        int floorDestIdxToAdd;
        int[] whereToAddAtDataCalls = new int[2];
        int[] outputArr = new int[3];
        double timeVal = Double.MAX_VALUE; //time needed to end task for choosen elevator
        double timeTemp;
        int numOfElevators = this._building.numberOfElevetors();
        Elevator elev;
        //first case - check all elevetors which in DOWN mission, taking the optimal if exists
        for (int i=0; i < numOfElevators; i++){
            elev = this._building.getElevetor(i);
            if (elev.getState() == 0 || elev.getState() == c.getType() && elev.getPos() > origin){
                whereToAddAtDataCalls = checkWhereToaddAtDataCalls(c, i);
                floorDestIdxToAdd = whereToAddAtDataCalls[1];
                timeTemp = timeToEndMission(elev, origin, destiny, floorDestIdxToAdd, -1);
                if (timeVal > timeTemp){
                    elevToAlloc = i;
                    timeVal = timeTemp;
                    outputArr[0] = whereToAddAtDataCalls[0];
                    outputArr[1] = whereToAddAtDataCalls[1];
                    outputArr[2] = i;
                }
            }
        }
        if (elevToAlloc != -1){
            return outputArr;
        }
//        for (int i=0; i < numOfElevators; i++){
//            elev = this._building.getElevetor(i);
//            if (elev.getState() == 0){
//                whereToAddAtDataCalls = checkWhereToaddAtDataCalls(c, i);
//                floorDestIdxToAdd = whereToAddAtDataCalls[1];
//                timeTemp = timeToEndMission(elev, origin, destiny, floorDestIdxToAdd, -1);
//                if (timeVal > timeTemp){
//                    elevToAlloc = i;
//                    timeVal = timeTemp;
//                    outputArr[0] = whereToAddAtDataCalls[0];
//                    outputArr[1] = whereToAddAtDataCalls[1];
//                    outputArr[2] = i;
//                }
//            }
//        }
//        if (elevToAlloc != -1){
//            return outputArr;
//        }
        for (int i=0; i < numOfElevators; i++){
            elev = this._building.getElevetor(i);
            whereToAddAtDataCalls = checkWhereToaddAtDataCalls(c, i);
            floorDestIdxToAdd = whereToAddAtDataCalls[1];
            timeTemp = timeToEndMission(elev, origin, destiny, floorDestIdxToAdd, -1);
            if (timeVal > timeTemp){
                elevToAlloc = i;
                timeVal = timeTemp;
                outputArr[0] = whereToAddAtDataCalls[0];
                outputArr[1] = whereToAddAtDataCalls[1];
                outputArr[2] = i;
            }
        }
        return outputArr;
    }

    /**
     * cal approximate wait and movement time - togheter == the output time
     * @param elevator - choosen elevator to calculate it time to end the task
     * @param origin - src floor of the task
     * @param destiny - dest floor of the task
//   * @param howManyStops - amount of stops depend on the index where we gonna add the "dest floor" from the call
     * @param mission - task direction: UP = 1, DOWN = -1
     * @return time to End the Task for the elevator via the options writened before
     */
    private double timeToEndMission(Elevator elevator, int origin, int destiny, int whereToAddfloorDest, int mission) {
        int amountOfStops = 0;
        double approximateMoveTime, approximateWaitTime;
        //approximateMoveTime is all the movement between floors (sigma of all nodes from curr position
        //to the "how many stops" index
        approximateMoveTime = sumOfFloors(elevator, destiny, whereToAddfloorDest) * (1 / elevator.getSpeed());
        amountOfStops = whereToAddfloorDest;
        //approximateWaitTime is all parameters that gonna delay the movement in summery, multiply num of stops
        approximateWaitTime = amountOfStops * (elevator.getTimeForClose() + elevator.getTimeForOpen() + elevator.getStopTime() + elevator.getStartTime());
        return approximateMoveTime + approximateWaitTime;
    }

    private int sumOfFloors(Elevator elevator, int destiny, int tillWhereToCount) {
        int sum = 0;
        int temp = elevator.getPos();
        int id = elevator.getID();
        if (this.dataCalls.get(id).size() == 0){
            sum += Math.abs(temp - destiny);
            return sum;
        }
        else {
            for(int i=0; i < tillWhereToCount && i < this.dataCalls.get(id).size(); i++){
                sum += Math.abs(temp - this.dataCalls.get(id).get(i));
                temp = this.dataCalls.get(id).get(i);
            }
        }
        sum += Math.abs(temp - destiny);
        return sum;
    }

    /**
     * add the task of an choosen elevator at the currect place in the dataCalls structre
     * @param c - task of elevetor
     * @param elev - choosen elevator as optimal which will execute the mission
     */
    private int[] checkWhereToaddAtDataCalls(CallForElevator c, int elev) {
        Elevator e = this._building.getElevetor(elev);
        int i,j;
        //pointer to LinkedList<Integer> that holds all the calls for elev as arrenged list
        LinkedList<Integer> eList = this.dataCalls.get(elev);
        //1st case elevetor dataCalls list is empty so - add instantly.
        if (eList.isEmpty()) {
            i = 0;
            j = 1;
            int[] output = {i, j};
            return output;
        }
        int tempSrc = e.getPos();
        int tempDest = eList.getFirst();
        int pointer = 0;

        if (eList.size() == 1) {
            if (e.getState() == c.getType() && isBetween(tempSrc, tempDest, c.getSrc())) {
                i = 0;
                if (isBetween(tempSrc, tempDest, c.getDest())) {
                    j = 1;
                }
                else {
                    j = 2;
                }
            }
            else {
                i = 1;
                j = 2;
            }
            int[] output = {i, j};
            return output;
        }
        i = 0;
            i = gimmeCurrIdxToPushSrc(c.getSrc(), c.getType(), e.getPos(), eList);
        j = gimmeCurrIdxToPushDest(i, c.getDest(), c.getType(), e.getPos(), eList);
//            addOnlyIfNoDuplicates(j, c.getDest(), eList);
//            addOnlyIfNoDuplicates(i, c.getSrc(), eList);
        int[] output = {i, j};
        return output;
    }

    private int gimmeCurrIdxToPushSrc(int srcFloor, int mission, int elevPos, LinkedList<Integer> eList){
        int tempSrc = elevPos;
        int tempDest = eList.getFirst();
        int idxStart = 0;
        if (idxStart == 0){
            if (isBetween(tempSrc, tempDest, srcFloor) && missionDirection(tempSrc, tempDest) == mission){
                return idxStart;
            }
        }
        tempSrc = eList.get(idxStart);
        tempDest = eList.get(idxStart+1);
        while (idxStart < eList.size() &&
                !(missionDirection(tempSrc, tempDest) == mission && isBetween(tempSrc, tempDest, srcFloor))){
            tempSrc = tempDest;
            if (idxStart+1 == eList.size()){
                return idxStart+1;
            }
            tempDest = eList.get(idxStart+1);
            idxStart++;
        }
        return idxStart;
    }

    private int gimmeCurrIdxToPushDest(int idxStart, int destFloor, int mission, int elevPos, LinkedList<Integer> eList){
        int tempSrc = elevPos;
        int tempDest = eList.getFirst();
        if (idxStart == eList.size()){
            return idxStart;
        }
        if (idxStart == 0){
            if (isBetween(tempSrc, tempDest, destFloor) && missionDirection(tempSrc, tempDest) == mission){
                return idxStart;
            }
        }
        if (idxStart+1 == eList.size()){
            if (missionDirection(destFloor, eList.get(idxStart)) == mission){
                return idxStart;
            }
            else {
                return idxStart+1;
            }
        }
        else {
            tempSrc = eList.get(idxStart);
            tempDest = eList.get(idxStart+1);
        }
        idxStart++;
        if (mission == 1){
            while (idxStart <= eList.size() &&
                    missionDirection(tempSrc, tempDest) == 1 && tempSrc < destFloor && !(destFloor <= tempDest)){
                idxStart++;
                tempSrc = tempDest;
                if (eList.size() == idxStart){
                    break;
                }
                tempDest = eList.get(idxStart);
            }
            return idxStart;
        }
        else {
            while (idxStart <= eList.size() &&
                    missionDirection(tempSrc, tempDest) == -1 && tempSrc > destFloor && !(destFloor > tempDest)){
                idxStart++;
                tempSrc = tempDest;
                if (eList.size() == idxStart){
                    break;
                }
                tempDest = eList.get(idxStart);
            }
            return idxStart;
        }


    }

    /**
     * add a num to linkedList<Integer> ONLY if not a duplicate
     * @param idx where to add
     * @param floorNum - value to add
     * @param eList - LinkedList<Integer>
     * @return TRUE if added, FALSE if not added
     */
    private boolean addOnlyIfNoDuplicates(int idx, int floorNum, LinkedList<Integer> eList) {
        if (eList.size() == 0){
            eList.addFirst(floorNum);
            return true;
        }
        if (idx == 0){
            if (floorNum == eList.getFirst()){
                return false;
            }
            else {
                eList.addFirst(floorNum);
                return true;
            }
        }
        else if (eList.size() <= idx) {
            if (floorNum == eList.getLast()) {
                return false;
            } else {
                eList.addLast(floorNum);
                return true;
            }
        }
        else {
            if (eList.get(idx) == floorNum || eList.get(idx-1) == floorNum) {
                return false;
            } else {
                eList.add(idx, floorNum);
                return true;
            }
        }
    }

    /**
     * get 2 floors and returns if the mission is UP or DOWN
     * @param srcFloor
     * @param destFloor
     * @return 1 for UP, -1 for DOWN
     */
    private int missionDirection(int srcFloor, int destFloor){
        if (srcFloor < destFloor) {
            return 1;
        }
        else {
            return -1;
        }
    }

    /**
     * get 2 engages floors and 1 floor to check if its between them
     * @param srcFloor
     * @param destFloor
     * @param potInsert
     * @return boolean..
     */
    private boolean isBetween(int srcFloor, int destFloor, int potInsert){
        if (srcFloor >= potInsert && potInsert > destFloor){
            return true;
        }
        return (srcFloor <= potInsert && potInsert < destFloor);
    }



    @Override
    public void cmdElevator(int elev) {
        Elevator e = this._building.getElevetor(elev);

        //Ori's code
        //list is empty
        if(this.dataCalls.get(elev).isEmpty())
            return;

        //start operating
        if(!this.dataCalls.get(elev).isEmpty() && e.getState()==0)
            e.goTo(this.dataCalls.get(elev).getFirst());

        //stop
        if(this.dataCalls.get(elev).getFirst()==e.getPos()){
            e.stop(e.getPos());
            this.dataCalls.get(elev).removeFirst();
        }





/*
         //Amir's code
        if (this.dataCalls.get(elev).size() == 0){
            return;
        }
        if (this.dataCalls.get(elev).getFirst() == e.getPos()){
            e.goTo(this.dataCalls.get(elev).getFirst());
            e.stop(this.dataCalls.get(elev).getFirst());
            this.dataCalls.get(elev).removeFirst();
        }
        if (e.getState() == 0 && this.dataCalls.get(elev).size() > 0){
            e.goTo(this.dataCalls.get(elev).getFirst());
            return;
        }
        else if (this.dataCalls.get(elev).size() > 0 && e.getState() == 1){
            if (e.getPos() < this.dataCalls.get(elev).getFirst()){
                e.goTo(this.dataCalls.get(elev).getFirst());
                e.stop(this.dataCalls.get(elev).getFirst());
                return;
            }
            removeIrrelevantTaskUP(e, elev);

        }
        else if (this.dataCalls.get(elev).size() > 0 && e.getState() == -1){
            if (e.getPos() > this.dataCalls.get(elev).getFirst()){
                e.goTo(this.dataCalls.get(elev).getFirst());
                e.stop(this.dataCalls.get(elev).getFirst());
                return;
            }
            removeIrrelevantTaskDOWN(e, elev);

        }
*/

        return;
//        */
//        Elevator curr = this.getBuilding().getElevetor(elev);
//        if(curr.getState() == Elevator.LEVEL) {
//            int dir = this.getDirection();
//            int pos = curr.getPos();
//            boolean up2down = false;
//            if(dir == UP) {
//                if(pos<curr.getMaxFloor()) {
//                    curr.goTo(pos+1);
//                }
//                else {
//                    _direction = DOWN;
//                    curr.goTo(pos-1);
//                    up2down = true;
//                }
//            }
//            if(dir == DOWN && !up2down) {
//                if(pos>curr.getMinFloor()) {
//                    curr.goTo(pos-1);
//                }
//                else {
//                    _direction = UP;
//                    curr.goTo(pos+1);
//                }
//            }
//        }
    }

    private void removeIrrelevantTaskUP(Elevator e, int id) {
        while (this.dataCalls.get(id).size() > 0 && e.getPos() > this.dataCalls.get(id).getFirst()){
            this.dataCalls.get(id).removeFirst();
        }
    }

    private void removeIrrelevantTaskDOWN(Elevator e, int id) {
        while (this.dataCalls.get(id).size() > 0 && e.getPos() < this.dataCalls.get(id).getFirst()){
            this.dataCalls.get(id).removeFirst();
        }
    }


    public int getDirection() {return this._direction;}


    /**
     * return a random in (min,max)
     * @param min
     * @param max
     * @return
     */
    private static int rand(int min, int max) {
        if(max<min) {throw new RuntimeException("ERR: wrong values for range max should be >= min");}
        int ans = min;
        double dx = max-min;
        double r = Math.random()*dx;
        ans = ans + (int)(r);
        return ans;
    }
}
