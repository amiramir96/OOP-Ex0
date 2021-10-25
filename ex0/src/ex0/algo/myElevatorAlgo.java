package ex0.algo;
import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;
import java.util.ArrayList;
import java.util.LinkedList;


public class myElevatorAlgo implements ElevatorAlgo {
    private Building _building;
    private ArrayList<LinkedList<Integer>> dataCalls; //dataStructre to hold the route of floors that every elevetor shall do
    /**
     * constructor
     * @param b - building
     */
    public myElevatorAlgo(Building b) {
        _building = b;
        dataCalls = new ArrayList<LinkedList<Integer>>(b.numberOfElevetors());
        for(int i=0; i < b.numberOfElevetors(); i++){
            this.dataCalls.add(new LinkedList<Integer>());
        }
    }

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
     * @typeMove - 1 for up, -1 for down
     * @optimal - ID elevator choosen to return
     * //tribute ur destiny => Maple Destiny 4ever, PandaEyes my love <3
     * @return - ID of elevetor which shall will allocate the call
     */
    public int allocateAnElevator(CallForElevator c) {
        int[] optimal = new int[2];
        if (c.getType() == 1){
            optimal = allocateAnElevatorHelperViaUpMove(c);
        }
        else {
            optimal = allocateAnElevatorHelperViaDownMove(c);
        }
        addOnlyIfNoDuplicates(optimal[1], c.getDest(), this.dataCalls.get(optimal[2]));
        addOnlyIfNoDuplicates(optimal[0], c.getSrc(), this.dataCalls.get(optimal[2]));
        return optimal[2];
    }

    /**
     *the "Brain" of the algorithm.
     *hold 2 steps to prioritize via the helper main functions:
     *1- elevators in the same "type" of mission OR elevetors in LEVEL state
     *2- all the elevators
     *if we find at ea step even 1 elevetor relevant, func return ID of an elevator from that category
     * @param c - CallForElevator the currect call which we trying to work on
     * @elevToAlloc - ID elevator choosen to return
     * @return optimal elevetor ID for this task.
     */
    private int[] allocateAnElevatorHelperViaUpMove(CallForElevator c) {
        boolean foundElev = false; //way to know if to exit from func between the stages (1 and 2)
        int floorDestIdxToAdd;
        int src = c.getSrc(), dest = c.getDest();
        int[] whereToAddAtDataCalls = new int[2];
        int[] outputArr = new int[3];
        double timeVal = Double.MAX_VALUE; //time needed to end task for choosen elevator
        double timeTemp;
        int numOfElevators = this._building.numberOfElevetors();
        Elevator tempElev;
        //first case - check all elevetors which in UP mission OR LEVEL mission, taking the optimal if exists
        for (int i=0; i < numOfElevators; i++){
            tempElev = this._building.getElevetor(i);
            //mission is up so "good" case is only when elevetor position is BELOW the source target
            if (tempElev.getState() == 0 || tempElev.getState() == c.getType() && tempElev.getPos() < src){
                whereToAddAtDataCalls = checkWhereToaddAtDataCalls(c, i);
                floorDestIdxToAdd = whereToAddAtDataCalls[1];
                timeTemp = timeToEndMission(tempElev, src, dest, floorDestIdxToAdd, 1);
                if (timeVal > timeTemp){
                    foundElev = true;
                    timeVal = timeTemp;
                    outputArr[0] = whereToAddAtDataCalls[0];
                    outputArr[1] = whereToAddAtDataCalls[1];
                    outputArr[2] = i;
                }
            }
        }
        if (foundElev){
            return outputArr;
        }
        //2nd stage - check ALL elevators and takes the optimal
        for (int i=0; i < numOfElevators; i++){
            tempElev = this._building.getElevetor(i);
            whereToAddAtDataCalls = checkWhereToaddAtDataCalls(c, i);
            floorDestIdxToAdd = whereToAddAtDataCalls[1];
            timeTemp = timeToEndMission(tempElev, src, dest, floorDestIdxToAdd, 1);
            if (timeVal > timeTemp){
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
     *1- elevators in the same "type" of mission OR elevetors in LEVEL state
     *2- all the elevatros
     *if we find at ea step even 1 elevetor relevant, func return ID of an elevator from that category
     * @param c - CallForElevator the currect call which we trying to work on
     * @elevToAlloc - ID elevator choosen to return
     * @return optimal elevetor ID for this task.
     */
    private int[] allocateAnElevatorHelperViaDownMove(CallForElevator c) {
        boolean foundElev = false; //way to know if to exit from func between the stages (1 and 2)
        int floorDestIdxToAdd;
        int src = c.getSrc(), dest = c.getDest();
        int[] whereToAddAtDataCalls = new int[2];
        int[] outputArr = new int[3];
        double timeVal = Double.MAX_VALUE; //time needed to end task for choosen elevator
        double timeTemp;
        int numOfElevators = this._building.numberOfElevetors();
        Elevator tempElev;
        //first case - check all elevetors which in DOWN mission OR LEVEL mission, taking the optimal if exists
        for (int i=0; i < numOfElevators; i++){
            tempElev = this._building.getElevetor(i);
            //mission is DOWN so "good" case is only when elevetor position is ABOVE the source target
            if (tempElev.getState() == 0 || tempElev.getState() == c.getType() && tempElev.getPos() > src){
                whereToAddAtDataCalls = checkWhereToaddAtDataCalls(c, i);
                floorDestIdxToAdd = whereToAddAtDataCalls[1];
                timeTemp = timeToEndMission(tempElev, src, dest, floorDestIdxToAdd, -1);
                if (timeVal > timeTemp){
                    foundElev = true;
                    timeVal = timeTemp;
                    outputArr[0] = whereToAddAtDataCalls[0];
                    outputArr[1] = whereToAddAtDataCalls[1];
                    outputArr[2] = i;
                }
            }
        }
        if (foundElev){
            return outputArr;
        }
        //2nd stage - check ALL elevators and takes the optimal
        for (int i=0; i < numOfElevators; i++){
            tempElev = this._building.getElevetor(i);
            whereToAddAtDataCalls = checkWhereToaddAtDataCalls(c, i);
            floorDestIdxToAdd = whereToAddAtDataCalls[1];
            timeTemp = timeToEndMission(tempElev, src, dest, floorDestIdxToAdd, -1);
            if (timeVal > timeTemp){
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
     * @param src - src floor of the task
     * @param dest - dest floor of the task
//   * @param howManyStops - amount of stops depend on the index where we gonna add the "dest floor" from the call
     * @param mission - task direction: UP = 1, DOWN = -1
     * @return time to End the Task for the elevator via the options writened before
     */
    private double timeToEndMission(Elevator elevator, int src, int dest, int whereToAddfloorDest, int mission) {
        int amountOfStops = 0;
        double approximateMoveTime, approximateWaitTime;
        //approximateMoveTime is all the movement between floors (sigma of all nodes from curr position
        //to the "how many stops" index
        approximateMoveTime = sumOfFloors(elevator, dest, whereToAddfloorDest) * (1 / elevator.getSpeed());
        //approximateWaitTime is all parameters that gonna delay the movement in summery, multiply num of stops
        //whereToAddfloorDest == how many stops along the road
        approximateWaitTime = whereToAddfloorDest * (elevator.getTimeForClose() + elevator.getTimeForOpen() + elevator.getStopTime() + elevator.getStartTime());
        return approximateMoveTime + approximateWaitTime;
    }

    /**
     * summerize absolute number of floors that the elevertor gonna go through
     * example: if the elevetor position is 1 and the dest floor is 10, but there is few missions before this tasks
     *             which is: going to floor 7 than to floor 3
     *             the sumOf floors returns: 1->7->3->10 => 6+4+7 = 17
     * @param elevator - relevant elevetor
     * @param dest
     * @param tillWhereToCount - idx - where we shall add the destFloor of the task => means thats the position which we shall count till..
     * @return
     */
    private int sumOfFloors(Elevator elevator, int dest, int tillWhereToCount) {
        int sum = 0;
        int temp = elevator.getPos();
        int id = elevator.getID();
        if (this.dataCalls.get(id).size() == 0){
            sum += Math.abs(temp - dest);
            return sum;
        }
        else {
            for(int i=0; i < tillWhereToCount && i < this.dataCalls.get(id).size(); i++){
                sum += Math.abs(temp - this.dataCalls.get(id).get(i));
                temp = this.dataCalls.get(id).get(i);
            }
        }
        sum += Math.abs(temp - dest);
        return sum;
    }
    /**
     * checking : IF we will decide on the curr elevetor to accomplish the task
     * => what is the indexes that we shall add at the src and dest floors of the Call
     * @param c - task of elevetor
     * @param elev - choosen elevator as optimal which will execute the mission
     */
    private int[] checkWhereToaddAtDataCalls(CallForElevator c, int elev) {
        Elevator e = this._building.getElevetor(elev);
        int i,j; //indexes, i for src, j for dest

        //pointer to LinkedList<Integer> that holds all the calls for elev as arrenged list
        LinkedList<Integer> eList = this.dataCalls.get(elev);
        //1st case elevetor dataCalls list is empty so - add instantly.
        if (eList.isEmpty()) {
            i = 0;
            j = 1;
            int[] output = {i, j};
            return output;
        }
        //initialize vars to hold the 2 floors that gonna be chacked
        int tempSrc = e.getPos();
        int tempDest = eList.getFirst();
        //2nd - other specific case - the size of list that hold the "route" that the elev shall move through hold only 1 floor inside
        if (eList.size() == 1) {

            if (e.getState() == c.getType() && isBetween(tempSrc, tempDest, c.getSrc())) {
                i = 0; //add first the src
                if (isBetween(tempSrc, tempDest, c.getDest())) {
                    j = 1; //add after that the dest => route is; src - listItem - dest
                }
                else {
                    j = 2; //route gonna be: src - dest - listItem
                }
            }
            else {//route will be : listItem - src - dest
                i = 1;
                j = 2;
            }
            int[] output = {i, j};
            return output;
        }
        //3rd - the common case => list is at least size 2
        i = gimmeCurrIdxToPushSrc(c.getSrc(), c.getType(), e.getPos(), eList);
        j = gimmeCurrIdxToPushDest(i, c.getDest(), c.getType(), e.getPos(), eList);
        int[] output = {i, j};
        return output;
    }

    /**
     * the func returns idx for eList that represent => if we gonna add the task to this elevetor, where shall we add it?
     * @param srcFloor of the callForElevator
     * @param mission of the callForElevator
     * @param elevPos curr floor of the relevant elevator
     * @param eList the LinkedList of the ROUTE of the curr elevator
     * @return int - idx for eList
     */
    private int gimmeCurrIdxToPushSrc(int srcFloor, int mission, int elevPos, LinkedList<Integer> eList){
        //var intialize to start checks
        int tempSrc = elevPos;
        int tempDest = eList.getFirst();
        int idxStart = 0; //start of the route
        //case which we shall addFirst to eList (at the start)
        if (isBetween(tempSrc, tempDest, srcFloor) && missionDirection(tempSrc, tempDest) == mission){
                return idxStart;
        }
        //intialize to run over eList
        tempSrc = eList.get(idxStart);
        tempDest = eList.get(idxStart+1);
        /**
         * the logic terms!!!
         * we want to stand on the terms:
         * 1- do not get idxOutOfBound exception
         * 2- elev state and call type is same
         * 3- the item is between two nodes so its the BEST place xD (being asakura Yoh the shaman king nakama is the best place!!!)
         */
        //keep run as long as even one of the 3 terms is not achived
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

    /**
     * the func returns idx for eList that represent => if we gonna add the task to this elevetor, where shall we add it?
     * @param idxStart where shall we start our check, ATTENTION!!! any previous value is BEFORE the src floor, and now we look for place for the Dest which is for sure AFTER
     * @param destFloor of the callForElevator
     * @param mission of the callForElevator
     * @param elevPos curr floor of the relevant elevator
     * @param eList the LinkedList of the ROUTE of the curr elevator
     * @return int - idx for eList
     */
    private int gimmeCurrIdxToPushDest(int idxStart, int destFloor, int mission, int elevPos, LinkedList<Integer> eList){
        //intialize vars
        int tempSrc = elevPos;
        int tempDest = eList.getFirst();
        //check edges of the curr ROUTE
        //shall add at start?
        if (idxStart == 0){
            if (isBetween(tempSrc, tempDest, destFloor) && missionDirection(tempSrc, tempDest) == mission){
                return idxStart;
            }
        }
        //shall we add at end?
        if (idxStart == eList.size()){
            return idxStart;
        }
        else if (idxStart+1 == eList.size()){
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
        idxStart++; //indexologia
        /**
         * the logic terms!!! BUT WITH TWIST since we shall split between mission types!
         * we want to stand on the terms:
         * for UP mission:
         * 1- do not get idxOutOfBound exception
         * 2- elev state and call type is same
         * 3- the item shall be HIGHER than the tempSrc var (this way we never give to DOWN task to split a chain of UP tasks)
         * 4- the item shall be LOWER or SAME the tempDest var (this way we never Cross a higher floor)
         * for DOWN mission is the same terms but 3 and 4 is opposite in the meanings of higher/lower
         */
        //keep run as long as even one of the 3 terms is not achived
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
        else { //DOWN task
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
     * duplicate means => after added if the value at idx-1 OR the value at idx+1 is SAME (ONLY one of them is enough)
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
        //isolate case which idx is zero, easier to handle this way
        if (idx == 0){
            if (floorNum == eList.getFirst()){
                return false;
            }
            else {
                eList.addFirst(floorNum);
                return true;
            }
        }
        //shall add the item at Last of the list, only check if the last item is same
        else if (eList.size() <= idx) {
            if (floorNum == eList.getLast()) {
                return false;
            } else {
                eList.addLast(floorNum);
                return true;
            }
        }
        //all regular cases
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
     * little catch - between is only if X >= n > Y
     * without >= on the right side (Y) -> work better for running on the whole list!
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

    /**
     * use goto command while the elev is at LEVEL state
     * use stop command while the elev is on the move
     * goto and stop cmmands ALWAYS getting into used on the FIRST item at dataCall.get(elev) linked list
     * @param elev the current Elevator index on which the operation is performs.
     */
    @Override
    public void cmdElevator(int elev) {
        Elevator e = this._building.getElevetor(elev);
        //list is empty
        if (this.dataCalls.get(elev).isEmpty()){
            return;
        }
        //if elev got to the first floor in the list -> we shall remove it
        //also solve unique situtaion - elev starts the simulation in this floor so we shall tell her to open doors
        else if (this.dataCalls.get(elev).getFirst() == e.getPos()){
            e.stop(this.dataCalls.get(elev).getFirst());
            this.dataCalls.get(elev).removeFirst();
            //check now if list isEmpty so we shall return;
            if (this.dataCalls.get(elev).isEmpty()){
                return;
            }
        }
        //for LEVEL state - use goto command
        if (e.getState() == 0){
            e.goTo(this.dataCalls.get(elev).getFirst());
        }
        //while UP or DOWN state - use stop command
        else {
            e.stop(this.dataCalls.get(elev).getFirst());
        }
        return;
    }
}
