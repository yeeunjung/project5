package assignment5;

import java.io.ByteArrayOutputStream;

import java.io.PrintStream;
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
	public static final int MULTIPLIER = 3;
    
    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }
	@Override
	public void start(Stage stage) throws Exception {
		//**** CREATING VARIABLES*****************
		stage.setTitle(" C R I T T E R S !");
		BorderPane controller = new BorderPane();
		controller.setMinSize(Params.world_width+4, Params.world_height+4);
		//**** ALL BUTTONS ***********************
	    Button btn = new Button("STEP");
	    Button step100 = new Button("STEP 100 TIMES");
	    Button step1000 = new Button("STEP 1000 TIMES");
        Button makeButton = new Button("Make Critter(s)");
	    Text critterTypeLabel = new Text("Critter Type:");
        ComboBox<String> makeCritterDropdown = new ComboBox<>();
        TextField makeInputBox = new TextField(); 
        makeInputBox.setPromptText("Enter # of Critters to make");
	    Button babymaker = new Button("MAKE");
        Button runBtn = new Button("RUN");
        Button stopBtn = new Button("STOP");
        btn.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-font-family: \"Courier New\";");

	    Button setSeed = new Button("SET SEED");
	    Button quitBtn = new Button("QUIT");
	    TextField seed = new TextField ();
	    seed.setPromptText("Enter new seed");
	    btn.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-font-family: \"Courier New\";");
	    step100.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-font-family: \"Courier New\";");
	    step1000.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-font-family: \"Courier New\";");
	    babymaker.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-font-family: \"Courier New\";");
	    runBtn.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-font-family: \"Courier New\";");
	    stopBtn.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-font-family: \"Courier New\";");

        
		Text header = new Text("C R I T T E R S");
		header.setFont(Font.font("Courier New", FontWeight.BOLD, 24));
	    BorderPane layout = new BorderPane();
	    layout.setPadding(new Insets(20));
	    Canvas worldCanvas = new Canvas(Params.world_width*MULTIPLIER, Params.world_height*MULTIPLIER);
	    
	    // NOT MY CODE
	    // Vbox credit for help: https://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
	    VBox vbox = new VBox();
	    vbox.setPadding(new Insets(20));
	    vbox.setSpacing(8);
	    
	    VBox stats = new VBox();
	    TextArea statsText = new TextArea();
	    statsText.setText(Critter.runStats());
	    //statsText.setDisable(true);
	    stats.getChildren().add(statsText);
	    //List<Critter> crittersUgh = new List<Critter>();
	    
	    //statsText.setText(Critter.runStats());
	    Text title = new Text("DATA");
	    title.setFont(Font.font("Courier New", FontWeight.BOLD, 14));
	    vbox.getChildren().add(title);
	    vbox.getChildren().add(btn);
	    vbox.getChildren().add(babymaker);

	    vbox.getChildren().add(step100);
	    vbox.getChildren().add(step1000);
        vbox.getChildren().add(makeInputBox);
        vbox.getChildren().add(critterTypeLabel);
        vbox.getChildren().add(makeCritterDropdown);
        vbox.getChildren().add(makeButton);
	    vbox.getChildren().add(seed);
	    vbox.getChildren().add(setSeed);
	    vbox.getChildren().add(runBtn);
	    vbox.getChildren().add(quitBtn);
	    
	    
	    // actually mine but whatever
	    controller.setStyle("-fx-border-color: green;");
	    controller.setCenter(worldCanvas);
	    layout.setTop(header);
	    layout.setLeft(vbox);
	    layout.setCenter(controller);

	    layout.setRight(stats);
	    // END OF NOT MY CODE
	    


	    step100.setOnAction(new EventHandler<ActionEvent>()	{
            @Override 
            public void handle(ActionEvent e) {
                // Action for Button
	            	for(int i=0; i<100; i++)	{
	            		Critter.worldTimeStep();
	            	}
	            	Critter.displayWorld(worldCanvas);
	        	    statsText.setText(Critter.runStats());
            }
	    });
	    
	    step1000.setOnAction(new EventHandler<ActionEvent>()	{
            @Override 
            public void handle(ActionEvent e) {
                // Action for Button
	            	for(int i=0; i<1000; i++)	{
	            		Critter.worldTimeStep();
	            	}
	            	Critter.displayWorld(worldCanvas);
	        	    statsText.setText(Critter.runStats());
            }
	    });
	    
	    
	    
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
	    
	  
	        //String[] classes = this.getClasses();
	        String[] classes = {"Craig"};
	        makeCritterDropdown.getItems().addAll(classes);


	        makeButton.setOnAction(new EventHandler<ActionEvent>() {
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

	        
	    quitBtn.setOnAction(new EventHandler<ActionEvent>()	{
            @Override 
            public void handle(ActionEvent e) {
            		System.exit(0);
            }
	    });
	     

	    	  
	    	
	    
	    //
	    GridPane grid = new GridPane();
	    grid.setHgap(10);
	    grid.setVgap(10);
	    grid.setPadding(new Insets(0, 10, 0, 10));

	    Critter.makeCritter("Algae");
	    
        btn.setOnAction(new EventHandler<ActionEvent>() {     
            @Override 
            public void handle(ActionEvent e) {
                // Action for Button
            	Critter.worldTimeStep();
            	Critter.displayWorld(worldCanvas);
        	    statsText.setText(Critter.runStats());
            }
        });
        // SLIDER START
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
        vbox.getChildren().add(animSlider);
        // SLIDER END
        

        vbox.getChildren().add(runBtn);
        Timeline timeline = new Timeline(new KeyFrame(
    	        Duration.millis(500),
    	        ae -> animateTime(worldCanvas, animSpeed.getValue())));
        runBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
            	animStart(timeline, worldCanvas, runBtn, btn, babymaker);
            }
        });
        //String[] classes = this.getClasses()
        makeCritterDropdown.getItems().addAll(classes);

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
                                Critter.displayWorld(worldCanvas);
                            }
                        }
                    }
                } catch (InvalidCritterException e) {
                    // ignore
                } catch (NumberFormatException e) {
                    // ignore
                }
            }
        });
        vbox.getChildren().add(makeInputBox);
        vbox.getChildren().add(makeCritterDropdown);

               vbox.getChildren().add(stopBtn);
        stopBtn.setOnAction(new EventHandler<ActionEvent>() {     
            @Override 
            public void handle(ActionEvent e) {
            	animStop(timeline, worldCanvas, runBtn, btn, babymaker);
            }
        });
        Scene leftBorder = new Scene(layout);
        

        stage.setScene(leftBorder);
		stage.show();
	}
	
	// FOR ANIMATIONS - PACKETS
	public static void animateTime(Canvas world, double animSpeed) {
		for(int count=0; count<(int)animSpeed; count++) {
			Critter.worldTimeStep();			
		}
		Critter.displayWorld(world);
	}
	
	public static void animStart(Timeline timeline, Canvas worldCanvas, Button b1, Button b2, Button b3) {
		timeline.setCycleCount(Animation.INDEFINITE);
    	timeline.play();
    	b1.setDisable(true);
    	b2.setDisable(true);
    	b3.setDisable(true);
	}
	
	public static void animStop(Timeline timeline, Canvas worldCanvas, Button b1, Button b2, Button b3) {
		timeline.stop();
    	b1.setDisable(false);
    	b2.setDisable(false);
    	b3.setDisable(false);
	}
}
