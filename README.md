# OOP Ex0
 Our project refers to elevator problem, to create an algorithm which allocates and commands "smart" elevator system, and optimize overall time of calls.  
 ~definition overall time := time that takes since the client requested a ride until he reached his destination.
 
 # the main problem
 The old generation of elevator systems is not designed to be sufficient to a high and complex building, nor to handle more than 2 elevators at the same time.  
 Nowadays, high towers is towering to dozens and even more than a hundred floors and contains 6, 10, and even more elevators in the building.  
 During the planning phase we faced with several main challenge:  
 1- STARVATION - an occasion in which a client is waiting "forever". In another words, the client have to call again to an elevator because his call had been deleted/ will never  be answered. => unlike small buildings, where starvation usually takes a few minutes, at towers, starvation could take  15, 20 minutes and even half an hour.   
 2- Prioritize calls in a consistent way but to avoid employing the elevators as "shabat elevators" at OVERLOAD times, which means to stop at every floor up to the top of the tower and then move back down, stopping at each floor again.  
 3- The algorithm have to be balanced and robust as much as possible since we don't know the details of the building (number of elevators, their details, building height) and the building we will work on can be any realistic case.  
 4- The programme shall run in a linear time, dependant on num of elevators, calls, and floors, or at least polynomial time with a "small" power, BUT avoid from exponential time running.
 
 # Ways to overcome the problems
 // each solution corresponds to the same numbered problem above.  
 1- In a chain of calls to an exact elevator - an opposite direction mission to the given chain, could not over Take beside a case: the elevetor direction and the call type is the same, and also the source floor of the call wont make the elevator to turn its direction to take it.  
 This way, we can balance the "starvation" scenario, to take in the worst case a road between the elevetor to a peak of the building and than move back to the call (which is bad, but its can happen only if there are dozens of tasks in less than one minute)  
 2 & 3- Will handle this problem by our prioritization system and complicated call system, both will be described below.  
 4- The algorithm calculate ONLY ONCE terms for engaging elevators for every input call, after that the algorithm will allocate an elevator to that call, and from that moment, the call will be represented along the route of the give elevator until the call will be done.
 
 # prioritization system:
 every call will move through several stages:
1- get the input of the call.

2- check if there is elevator which respond to the next terms:  
     2.a - the elevator is in idle state or in a state that equal to the type of the call.  
     2.b - if the elevator is not in idle state, check if the call source floor wont make the elevator to change its direction.

3- from all the elevators that respond to 2.a and 2.b, mark the elevator whose time is the best to complete the task in the earliest time.

4- check from the given elevators above, if there is an elevator that respond to the terms of Complicated Call, if yes - mark it in place.

5- if we didnt find an optimal elevator yet, choose the elevator with the earliest time to complete the task.

6- allocate the optimal elevator to the task. 
 
 # complicated case
 for a given building with n - floors and m - elevators and average speed (of all the elevators in the building) - s
 if exists an elevator E which is not optimal considering times, BUT, it's close enough to the optimal elevator time up to a parameter - name it epsilon_time,
 and E is also far enough from the optimal elevator route (amount of floors which have to be crossed till the source floor) up to a parameter - name it delta_floor
 then, E is defined as elevator which answer to the definition of complicated case.
 
(a reminder - if an elevator is marked as complicated case, its shall replace the optimal elevator!)
 
the thinking about this mechanic came from merging some army experience with the Monty Hall problem.

// https://www.youtube.com/watch?v=iBdjqtR2iK4

if we lose a small amount of time till the point of an epsilon parameter, but we gain much more smaller route the a given elevator, we shall take it because of the thinking that:

### We dont look on the present time as the END, we shall always improve our position for the next step!
 
 
# Little description for the implementation of the algorithm
 The algorithm code is composed by two classes:
 1) MyElevatorAlgorithm which impliments ElevatorAlgo interface is the brain for all the Allocation phase, the cmd - command elevator function is also there.
 The allocation system and adding stops is the most complex (and the main) challenge in the implementation, its works according to the next steps:
    a- For all the elevators which stand the criterions - check where we shall add the source and dest floors inside the route of the give elevator (linkedlist of integers).
    
    b- For each elevator from ~a, calculate an accurate "timeToEndMission" and choose the elevator which holds the optimal time.
    
    c- Check from all the elevators in ~a, if there is an elevator which can be suitable to "complicated case" and replace it with the optimal if exist.
    
    d- Now the allocateAnElevatorHelper function return to the origin allocateAnElevator function an array with 3 ints-> idx to add src, idx to add dest, ID num of elevator
    then, add the items in the accurate place as needed.
    
 The adding system is simple from the moment there is index of where we shall add the items in the linked list, the "addOnlyIfNoDuplicates" do this task and ensure there wont be pair of floors with the same value that are linked.
 
 DataCalls class which built by arrayList of LinkedList and each of inner linked list at index i represent a route that the elevator i shall move through 
 
 command system - rely on the cmdElevator function, simple function which ensure that the computer will command to an elevator to move only if the elevator is in idle state so its will use goTo command. and if its move elevator so its shall use stop command. BUT use stop command only while the system didnt sent stop command on the same floor and task before (which make a double "stop" command.. waste of command xD)
 
 
 # Navigation in the git Project
ex0 -> src -> ex0 -> myElevatorAlgo - the main algorithm, the brain.
that class needs to be downloaded with the DataCalls.java file as well, the DataCalls class provides the data structre which hold all of the elevators routes (one linked list of integers per elevators, each node represent a station floor which the elev will stop at.
also there is UML diagram that helps to understand the connections between objects - have to be downloaded to the computer.
HTML files can be downloaded as well - its the java doc of MyElevatorAlgorithm && DataCalls classes.
https://github.com/amiramir96/OOP-Ex0/tree/main/ex0/src/ex0/algo
 
ex0 -> src -> tests - the basic JUNIT files which provides basic checks to ensure that the classes works.
https://github.com/amiramir96/OOP-Ex0/tree/main/ex0/src/tests

logs csv and results - hold the best results for simulation stages written by our teachers to OOP course in Ariel University (credit to proffesor Boaz Ben Moshe and doctor Elizabeth Itzkovich)  
summary xlsx file hold the end line of every stage simulation, without the logs or any other extra details
https://github.com/amiramir96/OOP-Ex0/tree/main/logs%20csv%20and%20results

tests - new simulation scenarioes which tests the challenge cases of the code in any relevant function that take role in the allocate, adding and commanding phases:
there is dedicated readme file focused only for this tests files, their results log is there as well!
https://github.com/amiramir96/OOP-Ex0/tree/main/tests
 
Ex0 part 1.pdf - our assigment file which added with the code to the teachers in the university, if you know hebrew you should read it!

# credits and special thanks
To this below articales and videoes which gave us alot of material to proccess and work with to create our own algorithm.
https://www.youtube.com/watch?v=siqiJAJWUVg&list=PLK8IOvtbwVssQ59IEL73S2ucQdTLiSZC6&index=2
https://www.sciencedirect.com/science/article/pii/S2405896316302671
https://www.researchgate.net/publication/220590321_Optimization_of_Group_Elevator_Scheduling_With_Advance_Information
http://co-at-work.zib.de/berlin2009/downloads/2009-10-01/2009-10-01-1100-BH-Online-Optimization.pdf

Special thanks to Ofri Tavor, our classmate student, who guided us to create our own advanced tests.

 
