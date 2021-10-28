package ex0.algo;

import ex0.Building;

import java.util.ArrayList;
import java.util.LinkedList;

public class DataCalls {
    private ArrayList<LinkedList<Integer>> data;

    /**
     * Build array of lists to store next stops and access them quickly
     * @param b the building of the elevators
     */
    public DataCalls(Building b){
        for(int i=0; i < b.numberOfElevetors(); i++){
            data.add(new LinkedList<Integer>());
        }
    }

    /**
     * get next stop for specific elevator
     * @param e elevator ID
     * @return next stop fot elevator e
     */
    public int getNext(int e){
        return data.get(e).getFirst();
    }

    /**
     * remove next stop (usually after finishing mission)
     * @param e elevator ID
     */
    public void removeNext(int e){
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
     * get stops for specific elevator
     * @param e elevator ID
     * @return stops list of e
     */
    public LinkedList<Integer> getElev(int e){
        return data.get(e);
    }

}
