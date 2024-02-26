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
public class Engineer extends Guest{
    
    /**
     * Initialise the engineer.
     * @param location 
     */
    public Engineer(Location location){
        super(location);
    }
    
    /**
     * Let the scientist act.
     * @param field 
     */
    @Override
    public void act(Field field){
        // Use the act method of the superclass guest.
        super.act(field);
        
        // Get the current happiness and subtract 1 (otherwise the guest considers himself for calculating the happiness).
        crtHappiness = this.happy()-1;
        
        // Use the method of the superclass guest to move to the best location.
        super.moveToBestLocation(field);
    }
    
    @Override
    public int happy(){
        // Initialise a variable hap.        
        int hap = 0;
        
        // Increase the happiness if the neighbour is an artist, scientist or a host.        
        for(int i = 0; i < 9; i++){
            if(neighbours.get(i) instanceof Artist){
                ++hap;
            }
            else if(neighbours.get(i) instanceof Host){
                ++hap;
            }
            else if(neighbours.get(i) instanceof Scientist){
                ++hap;
            }
            else if(neighbours.get(i) instanceof Engineer){
                ++hap;
            }
            else{
                // nothing happens
            }
        }
        return hap;
    }
    
}
