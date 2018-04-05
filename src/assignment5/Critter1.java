package assignment5;
/* CRITTERS Critter1.java
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

import java.util.ArrayList;
import java.util.List;

//This critter can walk in any direction and never chooses to fight. This critter's also very lucky, and
//each turn it wins a little bit of money from a lottery ticket (up to $100). runStats prints the total
//money won as well as the individual amounts won each time step.

public class Critter1 extends Critter{
	
	private static int totalMoneyWon = 0;
	private static ArrayList<Integer> tickets = new ArrayList<Integer>();
	
	
	@Override
	/**
	 * This method updates a Critter1 during one step.
	 * The critter walks in any random direction.
	 * The critter scratches off a lottery ticket and wins an amount of money from $0-$100.
	 * The money won gets added to the total money won by all Critter1s and the ticket amount gets recorded into the tickets arraylist.
	 */
	public void doTimeStep()
	{
		int dir = Critter.getRandomInt(8);
		walk(dir);
		int lottoMoney = Critter.getRandomInt(101);
		tickets.add(lottoMoney);
		totalMoneyWon += lottoMoney;
	}
	
	@Override
	/**
	 * This method determines whether a Critter1 will fight or not.
	 * Critter1s never fight, or even run from a fight because they are little weenies.
	 * @param opponent is the other critter to be fought
	 * @return true if the critter wants to fight, false if it doesn't want to fight
	 */
	public boolean fight(String opponent) {
		//This critter's a goddang pacifist
		return false;
		
	}
	
	/**
	 * This method returns the ASCII character representation of a Critter1.
	 */
	public String toString() {
		return("1");
	}
	
	/**
	 * This method prints the total number of Critter1s in the world, as well as the total amount of money they have collectively
	 * won on lottery tickets, as well as the record of their tickets used each turn and how much money was won from each ticket.
	 * @param critters list of Critter1s that exist
	 */
	public static void runStats(java.util.List<Critter> critters)	{
		System.out.println("Total Critter5s: " + critters.size());
		System.out.println("Total lottery money won: $" + totalMoneyWon);
		for(int i=1; i<tickets.size()+1; i++)	{
			System.out.println("Lottery ticket " + i + ": $" + tickets.get(i-1) + " won.");
		}
	}
	
	@Override
	public CritterShape viewShape() { return CritterShape.FLOWER; }
	
	@Override
	public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.HOTPINK; }
}