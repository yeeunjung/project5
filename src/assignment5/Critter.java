package assignment5;

import java.util.List;

import assignment4.Critter;
import assignment4.InvalidCritterException;
import assignment4.Params;

public abstract class Critter {
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 
	
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	/**
	 * This function returns true or false regarding if two Critters are in the same exact 
	 * position on the board/world. [custom function]
	 * @param a is the first Critter
	 * @param b is the second Critter
	 * @return boolean - if they are in the same position or not
	 */
	private static boolean samePosition(Critter a, Critter b) {
		return (a.x_coord==b.x_coord && a.y_coord==b.y_coord);
	}
	
	protected final String look(int direction, boolean steps) {
		energy -= Params.look_energy_cost;
		int x_coord = this.x_coord;
		int y_coord = this.y_coord;
		// if steps==false, move 1
		if(steps) {
			switch(direction)	{
			case 0:
				x_coord++;
				break;
			case 1:
				x_coord++;
				y_coord--;
				break;
			case 2:
				y_coord--;
				break;
			case 3:
				y_coord--;
				x_coord--;
				break;
			case 4:
				x_coord--;
				break;
			case 5:
				x_coord--;
				y_coord++;
				break;
			case 6:
				y_coord++;
				break;
			case 7:
				x_coord++;
				y_coord++;
				break;
			}
		} else {
		// else if steps==true, move 2
			switch(direction)	{
			case 0:
				x_coord+=2;
				break;
			case 1:
				x_coord+=2;
				y_coord-=2;
				break;
			case 2:
				y_coord-=2;
				break;
			case 3:
				y_coord-=2;
				x_coord-=2;
				break;
			case 4:
				x_coord-=2;
				break;
			case 5:
				x_coord-=2;
				y_coord+=2;
				break;
			case 6:
				y_coord+=2;
				break;
			case 7:
				x_coord+=2;
				y_coord+=2;
				break;
			}
		}
		
		// Now check if there exists a critter in that position
		for(Critter crit : population) {
			if(x_coord==crit.x_coord && y_coord==crit.y_coord) {
				return crit.toString();
			}
		}
		return "";
	}
	
	/* rest is unchanged from Project 4 */
	
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	// Our own variable for if the Critter moved at all
	private boolean moved;
	// Or if it is fighting
	private boolean fighting;
	
	protected final void walk(int direction) {}
	
	protected final void run(int direction) {}
	
	protected final void reproduce(Critter offspring, int direction) {}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	
	public static void worldTimeStep() {}
	
	public static void displayWorld(Object pane) {} 
	/* Alternate displayWorld, where you use Main.<pane> to reach into your
	   display component.
	   // public static void displayWorld() {}
	*/
	
	/* create and initialize a Critter subclass
	 * critter_class_name must be the name of a concrete subclass of Critter, if not
	 * an InvalidCritterException must be thrown
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try {
			Class newCritter = Class.forName("assignment5." + critter_class_name);
			Critter Buddy = (Critter) newCritter.newInstance();
			population.add(Buddy);
			Buddy.x_coord = getRandomInt(Params.world_width);
			Buddy.y_coord = getRandomInt(Params.world_height);
			Buddy.energy = Params.start_energy;
			Buddy.moved = false;
			Buddy.fighting = false;
		}
		catch(ClassNotFoundException | InstantiationException | IllegalAccessException exception)	{
			throw new InvalidCritterException(critter_class_name);
		}
	}
	
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		return null;
	}
	
	public static void runStats(List<Critter> critters) {}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure thath the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctup update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}
	
	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		for(int idx=population.size()-1; idx>=0; idx--){
			population.remove(idx);
		}
	}
	
	
}
