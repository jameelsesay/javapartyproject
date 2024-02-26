/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Party_v4;

import java.awt.Color;
import java.util.Random; // unnecessary because of next line?
import java.util.*;

import java.util.concurrent.TimeUnit; // delete later! For timer between steps
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author lukas
 */
public class Simulator {

    private static Person[][] person;   // all the persons on the field
    private static Field room;          // party room 
    private static SimulatorView view;  // graphical visualisation of the simulation
    int currentStep = 0;                // current step of the simulation
    private Field crtField;             // current field
   
    public static void main(String[] args){
        // Call constructor for the simulation.
        Simulator sim = new Simulator(ModelConstants.FIELD_DEPTH, ModelConstants.FIELD_WIDTH);
        
        // Populate the room.
        sim.populate(room);
        
        // Show the status.
        sim.status(view, room);
        
        // Simulate the party.
        sim.simulate(ModelConstants.SIMULATION_LENGTH, room);
    }
       
    /**
     * Initialise the simulator.
     * @param depth
     * @param width 
     */
    public Simulator(int depth, int width){
        // Check that the parameters received are over 0 (otherwise initialise with default numbers).
        if(depth <= 0){
            depth = ModelConstants.DEFAULT_DEPTH;
        }
        if(width <= 0){
            width = ModelConstants.DEFAULT_WIDTH;
        }
        
        // Initialise RandomGenerator.
        RandomGenerator.initialiseWithSeed(ModelConstants.SEED);
        
        // Create a new array that stores all the persons.
        person = new Person[depth][width];
        
        // Create a new field.
        room = new Field(depth, width);
        
        // Create a new graphical visualisation of the state of the field.
        view = new SimulatorView(depth, width);
    }
    
    /**
     * Populate the field.
     * @param field 
     */
    public void populate(Field field){
        
        // Get field dimensions.
        int depth = field.getDepth();  // Is it possible to use the variables instead?
        int width = field.getWidth();
        
        // Clear field.
        field.clear();
        
        // Create random numbers.
        Random rand = RandomGenerator.getRandom();
        
        // Assign a type of person or a blank space to each place in the grid according to the probabilites.
        for(int i = 0; i < depth; i++){
            for(int j = 0; j < width; j++){
                double p = rand.nextDouble();
                if(p <= ModelConstants.PROB_HOST){
                    person[i][j] = new Host(new Location(i, j));
                } 
                else if(p <= ModelConstants.PROB_HOST + ModelConstants.PROB_ARTIST){
                    person[i][j] = new Artist(new Location(i, j));
                }
                else if(p <= ModelConstants.PROB_HOST + ModelConstants.PROB_ARTIST + ModelConstants.PROB_SCIENTIST){
                    person[i][j] = new Scientist(new Location(i, j));
                }
                else if(p <= ModelConstants.PROB_HOST + ModelConstants.PROB_ARTIST + ModelConstants.PROB_SCIENTIST + ModelConstants.PROB_ENGINEER){
                    person[i][j] = new Engineer(new Location(i, j));
                } 
                else {
                    // blank space
                }
                
                // Place the persons on the field.
                field.place(person[i][j], i, j);
            }
        }
    }
    
    /**
     * Show the status of the party as a graphical visualisation.
     * @param view
     * @param field 
     */
    public void status(SimulatorView view, Field field){
        // Set colors for each type of person.
        view.setColor(Host.class, Color.black);         // Host = black
        view.setColor(Artist.class, Color.red);         // Artist = red
        view.setColor(Scientist.class, Color.orange);   // Scientist = orange
        view.setColor(Engineer.class, Color.blue);      // Engineer = blue
        
        // Show status of the current step.
        view.showStatus(currentStep, field);
    }
    
    /**
     * Simulate a number of steps.
     * @param steps
     * @param field 
     */
    public void simulate(int steps, Field field){
        // Initialise the current field.
        crtField = field;
        
        // For each step:
        for (int i = 0; i < steps; i++){
            // Increase the step counter.
            currentStep++;
            
            // 1 second delay between each step.
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Simulator.class.getName()).log(Level.SEVERE, null, ex);
            }
                        
            // Simulate one step.
            simulateOneStep(crtField);
            
            // Update the array "person".
            for (int p = 0; p < ModelConstants.DEFAULT_DEPTH; p++){
                for(int q = 0; q < ModelConstants.DEFAULT_WIDTH; q++){
                   person[p][q] = crtField.getObjectAt(p, q);
                }
            }      
        }
    }
    
    /**
     * Simulate one step.
     * @param field 
     */
    public void simulateOneStep(Field field){
        // Let every person on the field act.
        for (int i = 0; i < ModelConstants.DEFAULT_DEPTH; i++){
            for(int j = 0; j < ModelConstants.DEFAULT_WIDTH; j++){
                
                // exclude blank spaces
                if(person[i][j] != null){   
                    person[i][j].act(field);
                }
            }          
        }
        
        // Show the status of the party as a graphical visualisation.
        status(view, field);
    }
}