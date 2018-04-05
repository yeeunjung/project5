package assignment5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import assignment5.Critter;
import assignment5.InvalidCritterException;
import assignment5.Params;
import javafx.scene.canvas.GraphicsContext;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public abstract class Critter {
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR,
		FLOWER
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
	
	protected final void walk(int direction) {
		int origX = x_coord;
		int origY = y_coord;
		energy -= Params.walk_energy_cost;
		if(!moved) {
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
			if (x_coord >= Params.world_width)	{
				x_coord = 0;
			} else if (x_coord <= -1)	{
				x_coord = Params.world_width-1;
			}
			
			if (y_coord >= Params.world_height)	{
				y_coord = 0;
			} else if (y_coord <= -1)	{
				y_coord = Params.world_height-1;
			}
			
			if(fighting) {
				int count = 0;
				for(Critter crit : population) {
					if(crit.x_coord==x_coord && crit.y_coord==y_coord) {
						count++;
					}
				}
				if(count<1) {
					x_coord = origX;
					y_coord = origY;
				}
			}
			moved = true;
		}
	}
	
	protected final void run(int direction) {
		int origX = x_coord;
		int origY = y_coord;
		energy -= Params.run_energy_cost;
		if(!moved) {
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
			
			if (x_coord == Params.world_width)	{
				x_coord = 0;
			} else if (x_coord == Params.world_width+1){
				x_coord = 1;
			} else if (x_coord == -1)	{
				x_coord = Params.world_width-1;
			} else if (x_coord == -2)	{
				x_coord = Params.world_width-2;
			}
			
			if (y_coord == Params.world_height)	{
				y_coord = 0;
			} else if (y_coord == Params.world_height+1) {
				y_coord = 1;
			} else if (y_coord == -1)	{
				y_coord = Params.world_height-1;
			} else if (y_coord == -2) {
				y_coord = Params.world_height-2;
			}
			
			if(fighting) {
				int count = 0;
				for(Critter crit : population) {
					if(crit.x_coord==x_coord && crit.y_coord==y_coord) {
						count++;
					}
				}
				if(count<1) {
					x_coord = origX;
					y_coord = origY;
				}
			}
			moved = true;
		}
	}
	
	protected final void reproduce(Critter offspring, int direction) {
		if (energy < Params.min_reproduce_energy) {
			return;
		}
		//offspring energy automatically rounded down because it's an int
		offspring.energy = energy/2;
		//to round up the parent's energy
		double parentEnergy = energy/2;
		if (parentEnergy%1 != 0) {
			parentEnergy -= parentEnergy%1;
			parentEnergy++;
		}
		energy = (int) parentEnergy;
		
		//an offspring doesn't move...yet
		offspring.moved = false;
		//or fight
		offspring.fighting = false;
		
		//to determine the offspring's position
		switch(direction)	{
			case 0:
				offspring.x_coord = x_coord+1;
				offspring.y_coord = y_coord;
				break;
			case 1:
				offspring.x_coord = x_coord+1;
				offspring.y_coord = y_coord-1;
				break;
			case 2:
				offspring.x_coord = x_coord;
				offspring.y_coord = y_coord-1;
				break;
			case 3:
				offspring.x_coord = x_coord-1;
				offspring.y_coord = y_coord-1;
				break;
			case 4:
				offspring.x_coord = x_coord-1;
				offspring.y_coord = y_coord;
				break;
			case 5:
				offspring.x_coord = x_coord-1;
				offspring.y_coord = y_coord+1;
				break;
			case 6:
				offspring.x_coord = x_coord;
				offspring.y_coord = y_coord+1;
				break;
			case 7:
				offspring.x_coord = x_coord+1;
				offspring.y_coord = y_coord+1;
				break;
		}
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * This functions process the encounters between different Critters
	 */
	private static void doEncounters() {
		// Must first figure out who is in the same spots
		Map<String, ArrayList<Integer>> encountered = new HashMap<String, ArrayList<Integer>>();
		String coord="";
		for(int idx1=0; idx1<population.size()-1; idx1++) {
			for(int idx2=idx1+1; idx2<population.size(); idx2++) {
				if(population.get(idx1).x_coord==population.get(idx2).x_coord && population.get(idx1).y_coord==population.get(idx2).y_coord) {
					ArrayList<Integer> popID = new ArrayList<Integer>();
					coord = String.valueOf(population.get(idx1).x_coord) + "," + String.valueOf(population.get(idx1).y_coord);
					if(encountered.containsKey(coord)) {
						// Key exists within the map
						if(!encountered.get(coord).contains(idx1)) {
							encountered.get(coord).add(idx1);
						}
						if(!encountered.get(coord).contains(idx2)) {
							encountered.get(coord).add(idx2);
						}
					} else {
						// Else create new key
						popID.add(idx1);
						popID.add(idx2);
						encountered.put(coord,popID);
					}
				}
			}
		}
	
		// Time to fight
		for (Map.Entry<String, ArrayList<Integer>> duel : encountered.entrySet()) {
			// This means that we are talking for each key,value entry
			for(int idx=0; idx<duel.getValue().size()-1; idx++) {
				// We are now fighting~ must update boolean fighting
				population.get(duel.getValue().get(idx)).fighting = true;
				population.get(duel.getValue().get(idx+1)).fighting = true;				
				int winner=0;
				int loser=0;
				boolean p1Fight = population.get(duel.getValue().get(idx)).fight(population.get(duel.getValue().get(idx+1)).toString());
				boolean p2Fight = population.get(duel.getValue().get(idx+1)).fight(population.get(duel.getValue().get(idx)).toString());
				// Must also account for movement
				if(population.get(duel.getValue().get(idx)).energy>0 && population.get(duel.getValue().get(idx+1)).energy>0 && Critter.samePosition(population.get(duel.getValue().get(idx)), population.get(duel.getValue().get(idx+1)))) {
					int p1Roll;
					int p2Roll;
					// If one does not want to fight, the fighter auto wins
					
					if(p1Fight!=p2Fight) {
						if(p1Fight) {
							winner = idx;
							loser = idx+1;
						}
						else if(p2Fight) {
							winner = idx+1;
							loser = idx;
						}
					} else {
					
						if(p1Fight) {
							p1Roll = Critter.getRandomInt(population.get(duel.getValue().get(idx)).energy+1);
						} else {
							p1Roll = 0;
						}
						if(p2Fight) {
							p2Roll = Critter.getRandomInt(population.get(duel.getValue().get(idx+1)).energy+1);
						} else {
							p2Roll = 0;
						}
						// Now we check who is winner
						if(p1Roll > p2Roll) {
							winner = idx;
							loser = idx+1;
						} else if(p1Roll <= p2Roll) {
							winner = idx+1;
							loser = idx;
						} 	
					}			
					
					population.get(duel.getValue().get(winner)).energy += population.get(duel.getValue().get(loser)).energy/2;
					population.get(duel.getValue().get(loser)).energy = 0;
					
					// The fighting has concluded. Mark as such.
					population.get(duel.getValue().get(idx)).fighting = false;
					population.get(duel.getValue().get(idx+1)).fighting = false;
					
					//System.out.println(population.get(duel.getValue().get(winner)).toString() + winner + " has won");
				} 
			}
		}
		return;
	}
	public static void worldTimeStep() {
		// Increment timestep
		
		// Update timesteps
		for(Critter c : population) {
			c.doTimeStep();
		}
		
		// Do the fights. doEncounters()
		Critter.doEncounters();
		
		// UpdateRestEnergy
		for(Critter c : population) {
			c.energy = c.energy-Params.rest_energy_cost;
		}
		
		// Generate algae genAlgae()
		for(int count=0; count<Params.refresh_algae_count; count++) {
			try {
				Critter.makeCritter("Algae");
			} catch(InvalidCritterException | NoClassDefFoundError e) {
        		System.out.println("fuck u");
        	}
		}
		
		// Move babies to general population
		for(Critter baby : babies) {
			population.add(baby);
		}
		babies.clear();
		
		// Cull the dead!
		for(int idx=population.size()-1; idx>=0; idx--) {
			if(population.get(idx).energy<=0) {
				population.remove(idx);
			}
		}
		
		// Now that the time is over, everyone resets if they have moved or not
		for(Critter crit : population) {
			crit.moved = false;
			crit.fighting = false;
		}		
	}
	
	public static void displayWorld(Canvas world) {
		// Create a pane for the Critters
		System.out.println("HELLO");
		GraphicsContext gc = ((Canvas) world).getGraphicsContext2D();
		gc.clearRect(0, 0, Params.world_width*Main.MULTIPLIER, Params.world_height*Main.MULTIPLIER);
		for(Critter crit : population) {
			//gc.strokePolyline(crit.viewShape(), crit.viewOutlineColor(), crit.viewFillColor());
			gc.setFill(crit.viewFillColor());
	        gc.setStroke(crit.viewOutlineColor());
	        
	        if(crit.viewShape()==CritterShape.CIRCLE) {
	        	gc.fillOval(crit.x_coord*Main.MULTIPLIER, crit.y_coord*Main.MULTIPLIER, Main.MULTIPLIER, Main.MULTIPLIER);
		        gc.strokeOval(crit.x_coord*Main.MULTIPLIER, crit.y_coord*Main.MULTIPLIER, Main.MULTIPLIER, Main.MULTIPLIER);
	        } else if(crit.viewShape()==CritterShape.SQUARE) {
	        	gc.fillRect(crit.x_coord*Main.MULTIPLIER, crit.y_coord*Main.MULTIPLIER, Main.MULTIPLIER, Main.MULTIPLIER);
		        gc.strokeRect(crit.x_coord*Main.MULTIPLIER, crit.y_coord*Main.MULTIPLIER, Main.MULTIPLIER, Main.MULTIPLIER);
	        } else if(crit.viewShape()==CritterShape.TRIANGLE) {
	        	//Polygon triangle = new Polygon();
	        	double[] xPoints = { crit.x_coord*Main.MULTIPLIER, (crit.x_coord+0.5)*Main.MULTIPLIER, (crit.x_coord+1)*Main.MULTIPLIER };
	        	double[] yPoints = { (crit.y_coord+1)*Main.MULTIPLIER, crit.y_coord*Main.MULTIPLIER, (crit.y_coord+1)*Main.MULTIPLIER };
	        	gc.strokePolygon(xPoints, yPoints, 3);
	        	gc.fillPolygon(xPoints, yPoints, 3);
	        } else if(crit.viewShape()==CritterShape.DIAMOND) {
	        	//Polygon diamond = new Polygon();
	        	double[] xPoints = { crit.x_coord*Main.MULTIPLIER, (crit.x_coord+0.5)*Main.MULTIPLIER, (crit.x_coord+1)*Main.MULTIPLIER, (crit.x_coord+0.5)*Main.MULTIPLIER };
	        	double[] yPoints = { (crit.y_coord+0.5)*Main.MULTIPLIER, crit.y_coord*Main.MULTIPLIER, (crit.y_coord+0.5)*Main.MULTIPLIER,(crit.y_coord+1)*Main.MULTIPLIER };
	        	gc.strokePolygon(xPoints, yPoints, 4);
	        	gc.fillPolygon(xPoints, yPoints, 4);
	        } else if(crit.viewShape()==CritterShape.STAR) {
	        	double[] xPoints = { (crit.x_coord+0.5)*Main.MULTIPLIER, (crit.x_coord+0.618)*Main.MULTIPLIER,
	        			 			(crit.x_coord+1)*Main.MULTIPLIER, (crit.x_coord+0.691)*Main.MULTIPLIER,
	        			 			 (crit.x_coord+0.809)*Main.MULTIPLIER, (crit.x_coord+0.5)*Main.MULTIPLIER,
	        			 			 (crit.x_coord+0.191)*Main.MULTIPLIER, (crit.x_coord+0.309)*Main.MULTIPLIER,
	        			 			 (crit.x_coord)*Main.MULTIPLIER, (crit.x_coord+0.382)*Main.MULTIPLIER,
	        			 			};
	        	double[] yPoints = { (crit.y_coord)*Main.MULTIPLIER, (crit.y_coord+0.363)*Main.MULTIPLIER,
	        						(crit.y_coord+0.363)*Main.MULTIPLIER, (crit.y_coord+0.588)*Main.MULTIPLIER,
	        						 (crit.y_coord+1)*Main.MULTIPLIER, (crit.y_coord+0.726)*Main.MULTIPLIER,
	        						 (crit.y_coord+1)*Main.MULTIPLIER, (crit.y_coord+0.588)*Main.MULTIPLIER,
	        						 (crit.y_coord+0.363)*Main.MULTIPLIER, (crit.y_coord+0.363)*Main.MULTIPLIER,
	        						};
	        	gc.strokePolygon(xPoints, yPoints, 10);
	        	gc.fillPolygon(xPoints, yPoints, 10);
	        } else if(crit.viewShape()==CritterShape.FLOWER) {
	        	double[] xPoints = { (crit.x_coord+0.5)*Main.MULTIPLIER, (crit.x_coord+0.5)*Main.MULTIPLIER,
			 			(crit.x_coord+1)*Main.MULTIPLIER, (crit.x_coord+0.5)*Main.MULTIPLIER,
			 			 (crit.x_coord+0.809)*Main.MULTIPLIER, (crit.x_coord+0.5)*Main.MULTIPLIER,
			 			 (crit.x_coord+0.191)*Main.MULTIPLIER, (crit.x_coord+0.5)*Main.MULTIPLIER,
			 			 (crit.x_coord)*Main.MULTIPLIER, (crit.x_coord+0.5)*Main.MULTIPLIER,
			 			};
	        	double[] yPoints = { (crit.y_coord)*Main.MULTIPLIER, (crit.y_coord+0.5)*Main.MULTIPLIER,
						(crit.y_coord+0.363)*Main.MULTIPLIER, (crit.y_coord+0.5)*Main.MULTIPLIER,
						 (crit.y_coord+1)*Main.MULTIPLIER, (crit.y_coord+0.5)*Main.MULTIPLIER,
						 (crit.y_coord+1)*Main.MULTIPLIER, (crit.y_coord+0.5)*Main.MULTIPLIER,
						 (crit.y_coord+0.363)*Main.MULTIPLIER, (crit.y_coord+0.5)*Main.MULTIPLIER,
						};
	        	gc.strokePolygon(xPoints, yPoints, 10);
	        	gc.fillPolygon(xPoints, yPoints, 10);
	        }
	        
		}
	} 
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
		List<Critter> result = new java.util.ArrayList<Critter>();
		for (Critter c: population) {
			if (c.getClass().getSimpleName().equals(critter_class_name)) {
				result.add(c);
			}
		}
		return result;
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
