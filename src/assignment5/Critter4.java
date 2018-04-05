package assignment5;

/* CRITTERS Critter4.java
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
 * This critter is a mario-type character. This creature only walks straight left to right or vice versalike
 * a 2-D platform character would. It reverses its directions when meeting an enemy.
 * Stats: The critter collects coins randomly on its journey. The critter will attempt to buy things in stats.
 * If the critter is in debt, then it will not attempt to fight (basically it gets beat up by the
 * loan sharks.)
 */
import java.util.*;

import assignment5.Critter.CritterShape;

public class Critter4 extends Critter {
	private static int coins = 0;
	private int dir = 0;

	/**
	 * This is what the Critter does during the time step.
	 * This critter randomly earns coins to use.
	 */
	@Override
	public void doTimeStep() {
		coins += Critter.getRandomInt(101);
		walk(dir);
	}

	/**
	 * This is the function for when the Critter enounters another
	 * Critter to fight. It will attempt to run away unless it is debt.
	 * Then it must let itself be beaten.
	 * @param opponent String is the opponent
	 * @return boolean if Critter will fight or not
	 */
	@Override
	public boolean fight(String opponent) {
		if (dir == 0) {
			dir = 4;
		} else {
			dir = 0;
		}
		if (coins >= 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * This function returns the String represetnation of a Critter.
	 * @return String of the Critter
	 */
	public String toString() {
		return "4";
	}
	
	/**
	 * This function returns how much a family of these Critters have earned 
	 * and also let's them spend the money.
	 * @param critter4s is the list of all the Critter4 objects that exist
	 */
	public static void runStats(java.util.List<Critter> critter4s) {
		System.out.println("The Critter family of " + critter4s.size() + " has " + coins + " coins to spend.");
		int choice = Critter.getRandomInt(5);
		String chosen="";
		switch(choice) {
			case 0:
				chosen = "choco milk";
				coins -= 200*critter4s.size();
				break;
			case 1:
				chosen = "strawberry cake";
				coins -= 1000*critter4s.size();
				break;
			case 2: 
				chosen = "melon pan";
				coins -= 800*critter4s.size();
				break;
			case 3:
				chosen = "milk bread";
				coins -= 1500*critter4s.size();
				break;
			case 4:
				chosen = "apple";
				coins -= 50*critter4s.size();
				break;
		}
		System.out.println("The Critters choose the " + chosen + ".");
		coins -= 500;
		if(coins<0) {
			System.out.println("The family now has a debt of " + coins + " coins to the sharks.");
		} else {
			System.out.println("The family now has " + coins + " coins left.");
		}
	}

	@Override
	public CritterShape viewShape() { return CritterShape.STAR; }
	
	@Override
	public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.GOLDENROD; }

}