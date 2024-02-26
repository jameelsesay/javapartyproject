/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Party_v4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;


/**
 *
 * @author lukas
 */
public abstract class Guest extends Person {
    
    int crtHappiness = 0;   // current happiness of a guest
    int newHappiness = 0;   // new happiness of a guest 
    int maxHappiness = 0;   // maximum happiness of a guest
    ArrayList<Person> neighbours; // all persons in adjacent fields
    HashMap<Integer, Location> possiblePos = new HashMap<Integer, Location>(); // happiness level and the first location where this happiness level is found
        
    /**
     * Initialise the guest.
     * @param location 
     */
    public Guest(Location location){
        super(location);
    }
    
    /**
     * Let the guest act on the field.
     * @param field 
     */
    @Override
    public void act(Field field){
        // Create a new ArrayList.
        neighbours = new ArrayList<Person>();
        
        // Store all persons in the adjact fields in the ArrayList.
        Iterator<Location> loc = field.adjacentLocations(location);
        while(loc.hasNext()){
            neighbours.add(field.getObjectAt(loc.next()));
        }
    }
    
    /**
     * Let the guest move to the best location.
     * @param field 
     */
    public void moveToBestLocation(Field field){
        // Clear the HashMap with possible positions.
        possiblePos.clear();
        
        // Remove the guest from the current location.
        field.clearLocation(location);
        
        // Iterate through all adjacent locations within the "manhattan" radius N.
        Iterator<Location> loc = field.adjacentLocations(location, ModelConstants.N);
        while(loc.hasNext()){
            Location locnxt = loc.next();   // locnxt is the next element in the list of locations
            
            // Only if the location is free:
            if(field.getObjectAt(locnxt) == null){
                
                // Store the neighbours around locnxt in an ArrayList.
                neighbours = new ArrayList<Person>();
                Iterator<Location> loc2 = field.adjacentLocations(locnxt);
                while(loc2.hasNext()){
                    neighbours.add(field.getObjectAt(loc2.next()));
                }
                
                // Get the possible happiness at locnxt.
                newHappiness = happy();
                
                // Store the happiness level together with the first location to find this happiness level in a HashMap.
                possiblePos.put(newHappiness, locnxt);
            }
        }
        
        // Get the highest newHappiness in the HashMap.
        maxHappiness = Collections.max(possiblePos.keySet());
        
        // Let the guest move if the maximum happiness is higher than the current happiness.
        if (maxHappiness > crtHappiness){
            
            // Get the location from the HashMap. 
            newLoc = possiblePos.get(maxHappiness);
            
            // Place this guest at the new location.
            field.place(this, newLoc);
            location = newLoc;
        }
        
        // In case the maximum possible happiness is not higher than the current happiness the guest is placed at the old location.
        else{
            field.place(this, location);
        }
    }
    
    /**
     * Get the happiness of the guest (abstract).
     * @return 
     */
    public abstract int happy();
    
}
