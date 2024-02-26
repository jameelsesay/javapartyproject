/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Party_v4;

/**
 *
 * @author lukas
 */
public class Host extends Person {
    
    /**
     * Initialise the host.
     * @param location 
     */
    public Host(Location location){
        super(location);
    }
    
    /**
     * Let the Host act on the field.
     * @param field
     */
    @Override
    public void act(Field field){
        // Look for a free adjacent location around the current location.
        newLoc = field.freeAdjacentLocation(location);
        
        // Only if there is a free adjacent location, place the host onto this location and clear the old location.
        if (newLoc != null){
            field.place(this, newLoc);
            field.clearLocation(location);
            location = newLoc;
        }
    }   
}
