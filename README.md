# OOP Ex0
 our project refers to elevator problem, to create an algorithm which provides allocate and commands to an "smart" elevator system, and optimize overall time of calls 
 ~definition overall time - time that takes since the client input his destination floor (from another given floor)
 
 # the main problem
 the old generation of elevator systems is not designed to be sufficient to an high and complex building, neighter to handle with more than 2 elevators in the same time
 in the present, high towers is towering to dozens and even more than a hundred floors and contains 6, 10, and even more elevators in the building
 along the planning phase we had to face with the next several main challenge:
 1- STARVATION - an occasion which client is waiting "forever" or in another words, the client have to call again to an elevator because his call had been deleted/will never pop up in the priority of elevator. => in diffrent from small buildings where starvation meaning is few little mins, at towers, starvation could express in 15, 20 and even half hour.
 2- prioritize calls in a consistent way but to avoid from employ the elevators as "shabat elevators" along the OVERLOAD times which means to stop at every floor till the top of the tower and than move back down, again, stopping at each floor over and over again.
 3- the algorithm have to be balanced and robustic as much as possible since we dont know the details of the building (num of elevators, their details, building floors) and the input building can be any realistic case.
 4- the programme shall run in a linear time, dependant on num of elevators, calls, and floors, or at least polynomial time with a "small" power, BUT avoid from exponential time running.
 
 # ways to contest with the problems
 // nums represent - an answer to the i problem which represented above.
 1- in a chain of calls to an exact elevator - an opposite direction mission to the given chain, could not over Take beside a case: the elevetor direction and the call type is the same, and also the source floor of the call wont make the elevator to turn its direction to take it.
 this way, we can balance the "starvation" occure, to take in the worst case a road between the elevetor to a peak of the building and than move back to the call (which is bad, but its can happen only if there is dozens of tasks in less than one minute)
 2 & 3- will handle this problem by our prioritization system and complicated call system, both will be described below.
 4- the algorithm calculate ONLY ONCE terms for engaging elevators for every input call, after that the algo will allocate a given elevator to that call, and from that moment, the call will be represented along the route of the give elevator till the call will be done.
 
 # prioritization system:
 every call will move through several stages:
1- get the input of the call

2- check if there is elevator which respond to the next terms:
     2.a - the elevator is in idle state or in a state that equal to the type of the call
     2.b - if the elevator is not in idle state, check if the call source floor wont make the elevator to change its direction

3- from all the elevators that respond to 2.a and 2.b, mark the elevator which his time is the best to complete the task in the earliest time

4- check from the given elevators above, if there is an elevator that respond to the terms of Complicated Call, if yes - mark it in place.

5- if we didnt find an optimal elevator yet, choose the elevator with the earliest time to complete the task

6- allocate the task to the optimal elevator
 
 # complicated case
 for a given building with n - floors and m - elevators and average speed (of all the elevators in the building) - s
 if there is exist an elevator E which is not optimal via times, BUT, its close enough to the optimal elevator time till to the point of a parameter - name it epsilon_time
 and E is also far enough from the optimal elevator route (amount of floors which have to be crossed till the source floor) till to the point of a parameter - name it delta_floor
 then, E is defined as elevator which answer to the definition of complicated case.
 
(a reminder - if a elevator is marked as complicated case, its shall replace the optimal elevator!)
 
the thinking about this mechanic came from merging some army experience with the Monty Hall problem.

// https://www.youtube.com/watch?v=iBdjqtR2iK4

if we lose a small amount of time till the point of an epsilon parameter, but we gain much more smaller route the a given elevator, we shall take it because of the thinking that:

we dont look on the present time as the END, we shall always improve our position for the next step!
 
 
# little description for the implimention code for the algorithm
 the algorithm code is composed by two classes:
 myElevatorAlgorithm which impliments ElevatorAlgo interface is the brain for all the Allocation phase but also the cmd - command elevator function is exist there
 the allocation system  and adding is the most complex (and the main) challenge in the implemention, its works via the next steps:
    a- for all the elevators which stand the criterions - check where we shall add the source and dest floors inside the route of the give elevator (linkedlist of integers)
    
    b- for ea elevator from ~a, calculate an accurate "timeToEndMission" and choose the elevator which holds the optimal time
    
    c- check from all the elevators from ~a, if there is elevator which can be suitable to "complicated case" and replace it with the optimal if exist
    
    d- now the allocateAnElevatorHelper function return to the origin allocateAnElevator function an array with 3 ints-> idx to add src, idx to add dest, ID num of elevator
    then, add the items in the accurate place as needed.
    
 the addind system is simple from the moment there is index of where we shall add the items in the linked list, the "addOnlyIfNoDuplicates" do this task and ensure there wont be pair of floors with the same value that are linked.
 
 DataCalls class which built by arrayList of LinkedList and each of inner linked list at index i represent a route that the elevator i shall move through 
 
 command system - rely on the cmdElevator function, simple function which ensure that the computer will command to an elevator to move only if the elevator is in idle state so its will use goTo command. and if its move elevator so its shall use stop command. BUT use stop command only while the system didnt sent stop command on the same floor and task before (which make a double "stop" command.. waste of command xD)
 
 
 # navigation in the git Project
ex0 -> src -> ex0 -> myElevatorAlgo - the main algorithm, the brain.
that class shall be downloaded with the DataCalls.java file as well, the DataCalls class provides the data structre which hold all of the elevators routes (one linked list of integers per elevators, ea node represent a station floor which the elev shall be move through (and stop there).
also there is uml diagram that helps to understand the connections between objects - have to be download to ur pc.
html files shall downloaded as well - its the java doc of myElevatorAlgorithm && DataCalls classes.
https://github.com/amiramir96/OOP-Ex0/tree/main/ex0/src/ex0/algo
 
ex0 -> src -> tests - the basic JUNIT files which provides u basic checks to ensure that the classes works.
https://github.com/amiramir96/OOP-Ex0/tree/main/ex0/src/tests

logs csv and results - hold the best results for simulation stages of our teachers to OOP course in Ariel University (credit to proffesor boaz ben mosh and doctor elizabeth itzkovich)
summary xlsx file hold the end line of every stage simulation, without the logs or any other extra details
https://github.com/amiramir96/OOP-Ex0/tree/main/logs%20csv%20and%20results

tests - new simulation scenarioes which tests the challenge cases of the code in any relevant function that take role in the allocate, adding and commanding phases:
there is dedicated readme file focused only for this tests files, their results log is there as well!
https://github.com/amiramir96/OOP-Ex0/tree/main/tests
 
Ex0 part 1.pdf - our assigment file which added with the code to the teachers in the university, if u know hebrew u shall read it!

# credits and special thanks
to this below articales and videoes which gave us alot of material to proccess and work with to create our own algorithm.
https://www.youtube.com/watch?v=siqiJAJWUVg&list=PLK8IOvtbwVssQ59IEL73S2ucQdTLiSZC6&index=2
https://www.sciencedirect.com/science/article/pii/S2405896316302671
https://www.researchgate.net/publication/220590321_Optimization_of_Group_Elevator_Scheduling_With_Advance_Information
http://co-at-work.zib.de/berlin2009/downloads/2009-10-01/2009-10-01-1100-BH-Online-Optimization.pdf

special thanks to Ofri Tavor, our class mate student, which guided us to create our own advanced tests.

 
