package assignment5;
/* CRITTERS Critter3.java
 * EE422C Project 5 submission by
 * Allegra Thomas
 * at35737
 * 15510
 * Yeeun Jung
 * yj3897
 * 15510
 * Slip days used: <0>
 * Spring 2018
 */
/*
 * This critter is a shy Critter at a party. It always moves in a diagonal direction (3).
 * This Critter only fights back when truly threatened. 
 * Otherwise, it will always run away or walk away, depending on the energy needed.
 * The stats for this Critter will reflect what it did in a fight, or essentially an encounter
 * with another Critter. This Critter will refuse eye contact (walk away), smash face into a corner (run away), or
 * be trapped by other people and feel stressed (fight).
 */
import java.util.*;

import assignment5.Critter.CritterShape;

public class Critter3 extends Critter {
	private static int numTimesStressed = 0;
	private static int numTimesEyeContact = 0;
	private static int numTimesSmashed = 0;
	
	/**
	 * This function commits an action in time step
	 * for critters with type Critter3. Will always walk.
	 */
	@Override
	public void doTimeStep() {
		walk(3);
	}
	
	/**
	 * This function is for when the critter encounters another critter.
	 * Updates what actions correspond to the actions taken in fight.
	 * @param opponent String representation of the critter encountered
	 * @return boolean if the critter is willing to fight or not.
	 */
	@Override
	public boolean fight(String opponent) {
		int option = Critter.getRandomInt(10);
		if (option>6) { 
			run(3);
			numTimesSmashed++;
			return false;
		} else if(option<=6 && option>3) {
			walk(3);
			numTimesEyeContact++;
			return false;
		} else {
			numTimesStressed++;
			return true; 
		}
	}
	
	/**
	 * This function returns the String representation of
	 * the critter.
	 * @return String representation is returned.
	 */
	public String toString() {
		return "3";
	}
	
	/**
	 * This method gives the stats for a class. The stats for this Critter shows 
	 * how all of them are successful at avoiding people.
	 * @param critters3 - gives a list of all the critters that are the Critter3 type
	 */
	public static void runStats(java.util.List<Critter> critters3) {
		System.out.println("This Critter hates parties. Let's review how " + critters3.size() + " Critters cope with being in a party situation.");
		System.out.println("These Critters refused eye contact " + numTimesEyeContact + " times.");
		System.out.println("These Critters smashed their faces into a corner " + numTimesSmashed + " times.");
		System.out.println("These Critters were cornered by other Critters " + numTimesStressed + " times.");
		if((numTimesStressed + numTimesSmashed+numTimesEyeContact)==0) {
			numTimesStressed = 1; // picked arbitrary -> will be zero.
		}
		double percentageSuccess = (double)(numTimesSmashed+numTimesEyeContact)/(numTimesStressed + numTimesSmashed+numTimesEyeContact)*100;
		System.out.println("Engagement was successfully avoided " + percentageSuccess + "% of the time.");
	}
	
	@Override
	public CritterShape viewShape() { return CritterShape.DIAMOND; }
	
	@Override
	public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.VIOLET; }

}