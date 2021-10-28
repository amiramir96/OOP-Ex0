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

we never look only on the present as the END, we shall always improve our position for the next step!
 
 # description
 the algorithm code is composed by two classes:
 1- myElevatorAlgorithm which impliments ElevatorAlgo interface 
 2- DataCalls class which built by arrayList of LinkedList and each of inner linked list at index i represent a route that the elevator i shall move through 
 
 
