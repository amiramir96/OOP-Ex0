package ex0.algo;

import ex0.Building;

import java.util.ArrayList;
import java.util.LinkedList;

public class DataCalls {
    public ArrayList<LinkedList<Integer>> data;

    /**
     * Build array of lists to store next stops and access them quickly
     * @param b the building of the elevators
     */
    public DataCalls(Building b){
        data = new ArrayList<LinkedList<Integer>>();
        for(int i=0; i < b.numberOfElevetors(); i++){
            data.add(new LinkedList<Integer>());
        }
    }

    /**
     * get next stop for specific elevator
     * @param e elevator ID
     * @return next stop fot elevator e
     */
    public int nextStop(int e){
        return data.get(e).getFirst();
    }

    /**
     * remove next stop (usually after finishing mission)
     * @param e elevator ID
     */
    public void removeNextStop(int e){
        data.get(e).removeFirst();
        return;
    }

    /**
     * get specific stop
     * @param e elevator ID
     * @param i stop index
     * @return stop location
     */
    public int getSpecific(int e, int i){
        return data.get(e).get(i);
    }

    /**
     * check for existence of next stop
     * @param e elevator ID
     * @return is there a next stop for e
     */
    public boolean isEmpty(int e){
        return data.get(e).isEmpty();
    }

    /**
     * how many stops does the elevator has
     * @param e elevator ID
     * @return number of stops in the list
     */
    public int noOfStops (int e){
        return data.get(e).size();
    }

    /**
     * get stops for specific elevator
     * @param e elevator ID
     * @return stops list of e
     */
    public LinkedList<Integer> getElev(int e){
        return data.get(e);
    }

}
