/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Party_v4;

import java.util.ArrayList;

/**
 *
 * @author lukas
 */

public abstract class Person {
    protected Location location;    // current location
    protected Location newLoc;      // new location

    /**
     * Initialise the person.
     * @param location 
     */
    public Person(Location location){
        this.location = location;
        
    }
    
    /**
     * Get the location of the person.
     * @return Location of the person.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Set the location of the person.
     * @param location 
     */
    public void setLocation(Location location) {
        this.location = location;
    }
    
    /**
     * Let the person act (abstract).
     * @param field 
     */
    public abstract void act(Field field);
}
