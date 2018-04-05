package assignment5;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import assignment5.InvalidCritterException;
import assignment5.Critter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Button;

import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;


public class Main extends Application{
	private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
	public static final int MULTIPLIER = 4;
    
    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }
	@Override
	public void start(Stage stage) throws Exception {
		//**** CREATING VARIABLES*****************
		//**** ALL BUTTONS ***********************
	    Button step = new Button("STEP");
	    Button babymaker = new Button("MAKE");
        Button runBtn = new Button("RUN");
        Button stopBtn = new Button("STOP");
	    Button setSeed = new Button("SET SEED");
	    Button quitBtn = new Button("QUIT");
	    Button statsBtn = new Button("RUN STATS");
	    
	    babymaker.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-font-family: \"Courier New\";");
	    runBtn.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-font-family: \"Courier New\";");
	    stopBtn.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-font-family: \"Courier New\";");
        step.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-font-family: \"Courier New\";");
        statsBtn.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-font-family: \"Courier New\";");
        //**** SETS UP WORLD *********************	   
		stage.setTitle(" C R I T T E R S !");
		Text header = new Text("C R I T T E R S");
		header.setFont(Font.font("Courier New", FontWeight.BOLD, 24));
	    BorderPane layout = new BorderPane();	//Whole game Layout
	    layout.setPadding(new Insets(20));
	    Canvas worldCanvas = new Canvas(Params.world_width*MULTIPLIER, Params.world_height*MULTIPLIER);	// Canvas for Critters
		BorderPane controller = new BorderPane();	// Game window layout
		controller.setMinSize(Params.world_width, Params.world_height);	
	    controller.setStyle("-fx-border-color: green;");
	    controller.setCenter(worldCanvas);
	    layout.setTop(header);
	    layout.setCenter(controller);
	    
		//**** PROMPT BOXES **********************
	    Text critterTypeLabel = new Text("Critter Type:");
        ComboBox<String> makeCritterDropdown = new ComboBox<>();
        TextField makeInputBox = new TextField(); 
        makeInputBox.setPromptText("Enter # of Critters to make");

	    TextField seed = new TextField ();
	    seed.setPromptText("Enter new seed");


	    // Vbox help credit: https://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
	    //**** CONTROLS LAYOUT *******************
	    VBox controls = new VBox();
	    controls.setPadding(new Insets(20));
	    controls.setSpacing(8);
	    Text title = new Text("CONTROLS");
	    title.setFont(Font.font("Courier New", FontWeight.BOLD, 14));
	    layout.setLeft(controls);
	    
	    // RETRIEVING CLASSES - for the make and runStats functions
	    File srcFile = new File("//Users//Allegra//Documents//GitHub//project5//src//assignment5");    
	    ArrayList<String> classes = new ArrayList<String>();
	    for (String crit : srcFile.list()) {
	    	crit = crit.substring(0, crit.lastIndexOf("."));
	    	try {
	    		if(Critter.class.isAssignableFrom((Class.forName("assignment5."+crit)))){
	    			classes.add(crit);
	    		}
	    	} catch (ClassNotFoundException e) {
	    		// ignore
	    	}
	    }
	    
	    // ***** SETTING UP STATS ****************
	    VBox stats = new VBox();
	    TextArea statsText = new TextArea();
	    statsText.setText("Select a Critter from the menu to run its stats.");
	    //statsText.setDisable(true);
	    stats.getChildren().add(statsText);
	    
	    ComboBox<String> runStatsDropdown = new ComboBox<String>();
        runStatsDropdown.getItems().addAll(classes);
        runStatsDropdown.getItems().add("All Critters");
        stats.getChildren().add(runStatsDropdown);
        stats.getChildren().add(statsBtn);
	    
	    layout.setRight(stats);
	    
	    controls.getChildren().add(title);
	    controls.getChildren().add(step);
	    controls.getChildren().add(babymaker);
        controls.getChildren().add(makeInputBox);
        controls.getChildren().add(critterTypeLabel);   
        controls.getChildren().add(makeCritterDropdown);
	    controls.getChildren().add(seed);
	    controls.getChildren().add(setSeed);
	    controls.getChildren().add(runBtn);
	    controls.getChildren().add(quitBtn);
	    controls.getChildren().add(stopBtn);
	    
	    // ### RUN STATS BUTTON FUNCTION ###
	    statsBtn.setOnAction(new EventHandler<ActionEvent>()	{
            @Override
            public void handle(ActionEvent e) {
            		runStatsFunc(runStatsDropdown, statsText);
            }
	    });
	    //********SLIDER FOR STEPS*************
        HBox stepSliderFullLabel = new HBox();
        VBox stepSlider = new VBox();
        stepSlider.setPadding(new Insets(5));
        Slider numSteps = new Slider(0,1000,10);
        numSteps.setShowTickMarks(true);
        numSteps.setShowTickLabels(true);
        Label numStepsLbl = new Label(Integer.toString((int)numSteps.getValue()));
        numStepsLbl.setFont(Font.font("Courier New", 10));
        numSteps.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    numStepsLbl.setText(String.format("%1.0f", new_val));
            }
        });
        Text numStepsTxt = new Text("NUMBER OF STEPS : ");
		numStepsTxt.setFont(Font.font("Courier New", 10));
		stepSliderFullLabel.getChildren().add(numStepsTxt);
		stepSliderFullLabel.getChildren().add(numStepsLbl);
		stepSlider.getChildren().add(stepSliderFullLabel);
        stepSlider.getChildren().add(numSteps);
        controls.getChildren().add(numSteps);
        
        //********END STEP SLIDER****************
        
	    // ### STEP FUNCTION ###
	    step.setOnAction(new EventHandler<ActionEvent>()	{
            @Override 
            public void handle(ActionEvent e) {
                // Action for Button
	            	for(int i=0; i<numSteps.getValue(); i++)	{
	            		Critter.worldTimeStep();
	            	}
	            	Critter.displayWorld(worldCanvas);
	            	runStatsFunc(runStatsDropdown, statsText);
            }
	    });

	    	    
	    // ### SET SEEDS FUNCTION ###
	    setSeed.setOnAction(new EventHandler<ActionEvent>()	{
            @Override 
            public void handle(ActionEvent e) {
            		try	{
		            	if (seed != null)	{
		            		int newSeed = Integer.parseInt(seed.getText());
		            		Critter.setSeed(newSeed);
		            	}
            		} catch(NumberFormatException exception) {
            			new Alert(Alert.AlertType.ERROR, "Please enter a valid seed integer.").showAndWait();
            		}

            }
	    });	  
	    
	    makeCritterDropdown.getItems().addAll(classes);

	    // ### MAKE FUNCTION ###
        babymaker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String selection = makeCritterDropdown.getValue();
                    if (selection != null) {
                        String s = makeInputBox.getText();
                        if (s != null) {
                            int n = Integer.parseInt(s);
                            for (int i = 0; i < n; i++) {
                                Critter.makeCritter(selection);
                            }
                            Critter.displayWorld(worldCanvas);
                            statsText.setText(Critter.runStats());
                        }
                    }
                } catch (InvalidCritterException e) {
                    // ignore
                } catch (NumberFormatException e) {
                    // ignore
                }
            }
        
        });

        // ### QUIT ###
        // quits the whole program!! EXIT!!!!!!
	    quitBtn.setOnAction(new EventHandler<ActionEvent>()	{
            @Override 
            public void handle(ActionEvent e) {
            		System.exit(0);
            }
	    });
        
        // SLIDER CREATION
        // Makes the animation slider and formats it real pretty
        HBox animSliderFullLabel = new HBox();
        VBox animSlider = new VBox();
        animSlider.setPadding(new Insets(5));
        Slider animSpeed = new Slider(0,100,1);
        animSpeed.setShowTickMarks(true);
        animSpeed.setShowTickLabels(true);
        Label animSpeedLbl = new Label(Integer.toString((int)animSpeed.getValue()));
        animSpeedLbl.setFont(Font.font("Courier New", 10));
        animSpeed.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    animSpeedLbl.setText(String.format("%1.0f", new_val));
            }
        });
        Text animSpeedTxt = new Text("ANIMATION SPEED : ");
		animSpeedTxt.setFont(Font.font("Courier New", 10));
		animSliderFullLabel.getChildren().add(animSpeedTxt);
		animSliderFullLabel.getChildren().add(animSpeedLbl);
		animSlider.getChildren().add(animSliderFullLabel);
        animSlider.getChildren().add(animSpeed);
        controls.getChildren().add(animSlider);

        // ### RUN FUNCTION ###
        Timeline timeline = new Timeline(new KeyFrame(
    	        Duration.millis(500),
    	        ae -> animateTime(worldCanvas, runStatsDropdown, statsText, animSpeed.getValue())));
        runBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
            	animStart(timeline, worldCanvas, runBtn, step, babymaker);
            }
        });
        // ### STOP FUNCTION ###
        stopBtn.setOnAction(new EventHandler<ActionEvent>() {     
            @Override 
            public void handle(ActionEvent e) {
            	animStop(timeline, worldCanvas, runBtn, step, babymaker);
            }
        });        
        
        Scene placeLayout = new Scene(layout);
        stage.setScene(placeLayout);
		stage.show();
	}
	
	
	// FOR ANIMATIONS - PACKETS
	private static void animateTime(Canvas world, ComboBox<String> runStatsDropdown, TextArea statsText, double animSpeed) {
		for(int count=0; count<(int)animSpeed; count++) {
			Critter.worldTimeStep();			
		}
		Critter.displayWorld(world);
	    runStatsFunc(runStatsDropdown, statsText);
	}
	
	private static void animStart(Timeline timeline, Canvas worldCanvas, Button b1, Button b2, Button b3) {
		timeline.setCycleCount(Animation.INDEFINITE);
    	timeline.play();
    	b1.setDisable(true);
    	b2.setDisable(true);
    	b3.setDisable(true);
	}
	
	private static void animStop(Timeline timeline, Canvas worldCanvas, Button b1, Button b2, Button b3) {
		timeline.stop();
    	b1.setDisable(false);
    	b2.setDisable(false);
    	b3.setDisable(false);
	}
	
	private static void runStatsFunc(ComboBox<String> runStatsDropdown, TextArea statsText) {
		String statsCritter = runStatsDropdown.getValue();
    	if (statsCritter != null)	{
    		if (statsCritter == "All Critters") {
    			statsText.setText(Critter.runStats());
    		} else {
        		try {
        			//get the runStats method for the specific critter and call it
        			List<Critter> instances = Critter.getInstances(statsCritter);
        			Class StatCritter = Class.forName("assignment5." + statsCritter);
        			Method runStats = StatCritter.getMethod("runStats", List.class);
        		    statsText.setText((String) runStats.invoke(StatCritter, instances));
        		} catch (InvalidCritterException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | NoClassDefFoundError | IllegalArgumentException | InvocationTargetException exception) {
        			//run stats on all critters by default (if the particular critter doesn't have a runStats method
        			statsText.setText(Critter.runStats());
        		}
    		}
    	}
	}
}
