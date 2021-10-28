README for TESTERS ONLY
/**
* the methods:
* checkWhereToaddAtDataCalls
* gimmeCurrIdxToPushSrc
* gimmeCurrIdxToPushDest
* allocateAnElevator
* allocateAnElevatorHelper
* cmdElevator
* is getting checken in ea of the tests below since without them the whole system would not work at all
* still, the methods of allocates have an special check which hold an OVERLOAD with alot of tasks
* also, the "complicated case" inner algo which described at the algo description is go checked especially 
*/

description of each test:
~name patterns of TEST is: <functionName><Direction>_<stageNUM>
~for example stage1 means - set Ex0_main file on "int stage = 1"

bdikatShfiut_stage1 - one mission, stage 1, check that basic call, allocation, moving, stoping is works

//is between methods shall make adding stable via return the main functions a boolean val
//if the obj is between two floors on the route
checkIsBetweenFunctions_UP_stage1 - chain of UP mission
checkIsBetweenFunctions_DOWN_stage1 - chain of down mission

addOnlyIfNoDuplicates_UP_stage1 - have to merge between tasks which gonna be in the same idx at the datacalls structre which represent the route of the elevetor
addOnlyIfNoDuplicates_DOWN_stage1 - same with down tasks
addOnlyIfNoDuplicates_ChangeDirection_stage1 - same with both above, look if route of 0->2, 2->4, 4->3, 3->1 is merged to route of 0-2-4-3-1

//inner function - "how many stops" is checken here directly
timeToEndMission_UP_stage4 - check if after alot of stop, the most fast elevetor isnt get choosed anymore (since the delay times)
timeToEndMission_DOWN_stage4 - same for down chain
timeToEndMission_ChangeDirection_stage4 - same like both above but with chains of UP and DOWN - whole four elev shall be engage to be busy


// test directly the methods: since they all togheter puzzeling the "allocate phase"
// checkWhereToaddAtDataCalls
// gimmeCurrIdxToPushSrc
// gimmeCurrIdxToPushDest
// allocateAnElevatorHelper
// inTheDirection
// missionDiretion 
allocateAnElevetor_UPchain_stage4 - no uncompleted, use all the elevetors, push currectly the sub floor of a route
allocateAnElevetor_DOWNchain_stage4 - no uncompleted, use all the elevetors, push currectly the sub floor of a route
allocateAnElevator_bothDirections_stage6 - goal is less than 60 seconds per task, no uncompleted
allocateAnElevetor_ComplicatedCase_stage8 - create a dilema to the system, shall take the fastest elevetor to task (45 to 91) which is 5,7,9 in state idle, or take a slghtly worst in time terms another elevetor - 3 which is already engaged and already got to stand on high floor
allocateAnElevetor_summaryPhase_stage8 - goal is 0 uncompleted at stage 8 which cutted off the last 200 seconds of the origin stage and 150 avg mission time (building is 110 floors!!!)
