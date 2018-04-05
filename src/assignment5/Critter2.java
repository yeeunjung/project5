package assignment5;
/* CRITTERS Main.java
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

import assignment5.Critter.CritterShape;

//This critter can only run in directions 0, 1, and 2 and reproduces every turn.
//Moving diagonally makes this critter aggressive and ready to fight.
//Moving horizontally or vertically makes this critter clumsy, so each time it moves in such a way it falls and gets a new bruise.
//

public class Critter2 extends Critter{
	
	private int dir = Critter.getRandomInt(3);
	private static int bruises = 0;
	
	@Override
	/**
	 * This method updates a Critter2 during one step.
	 * The critter runs in either the 0, 1, or 2 direction.
	 * If the critter runs horizontally or vertically, it falls and gets a bruise.
	 * Then the critter reproduces.
	 */
	public void doTimeStep()
	{
		run(dir);
		if(dir==0 || dir==2) {
			bruises++;
		}
		Critter2 offspring = new Critter2();
		reproduce(offspring, Critter.getRandomInt(8));
	}
	
	@Override
	/**
	 * This method determines whether a Critter2 will fight or not.
	 * If the critter moved in a diagonal direction, it will fight, otherwise it won't.
	 * @param opponent is the other critter to be fought
	 * @return true if the critter wants to fight, false if it doesn't want to fight
	 */
	public boolean fight(String opponent) {
		//if the critter moved diagonally, it's FIGHT NIGHT
		if (dir == 1)	{
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method returns String representation of Critter
	 * @return String representation
	 */
	public String toString() {
		return("2");
	}
	
	/**
	 * This method gives the total critters and the amount of bruises they have.
	 * @param critters list of critters with bruises
	 */
	public static void runStats(java.util.List<Critter> critters)	{
		System.out.println("Total Critter2s: " + critters.size());
		System.out.println("Total bruises: " + bruises);
	}
	
	@Override
	public CritterShape viewShape() { return CritterShape.TRIANGLE; }
	
	@Override
	public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.FIREBRICK; }
}
