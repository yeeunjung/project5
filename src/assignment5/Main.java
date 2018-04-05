package assignment5;

import java.io.ByteArrayOutputStream;

import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

import assignment5.InvalidCritterException;
import assignment5.Critter;
import javafx.application.Application;
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
import javafx.scene.control.Button;

import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;


public class Main extends Application{
	private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
	
    
    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }
	@Override
	public void start(Stage stage) throws Exception {
		// CONTROLLER
		stage.setTitle(" C R I T T E R S !");
		BorderPane controller = new BorderPane();
		controller.setMinSize(Params.world_width+4, Params.world_height+4);
		Text header = new Text("C R I T T E R S");
		header.setFont(Font.font("Courier New", FontWeight.BOLD, 24));
	    Button btn = new Button("STEP");
	    Button step100 = new Button("STEP 100 TIMES");
	    Button step1000 = new Button("STEP 1000 TIMES");
        Button makeButton = new Button("Make Critter(s)");
	    Text critterTypeLabel = new Text("Critter Type:");
        ComboBox<String> makeCritterDropdown = new ComboBox<>();
        TextField makeInputBox = new TextField(); 
        makeInputBox.setPromptText("Enter # of Critters to make");
	    Button babymaker = new Button("MAKE");
	    Button setSeed = new Button("SET SEED");
        Button runBtn = new Button("RUN");
	    Button quitBtn = new Button("QUIT");
	    TextField seed = new TextField ();
	    seed.setPromptText("Enter new seed");
	    btn.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-font-family: \"Courier New\";");
	    step100.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-font-family: \"Courier New\";");
	    step1000.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-font-family: \"Courier New\";");
	    babymaker.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-font-family: \"Courier New\";");

	    BorderPane layout = new BorderPane();
	    layout.setPadding(new Insets(20));
	    Canvas worldCanvas = new Canvas(Params.world_width*2, Params.world_height*2);
	    
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

	    // Category in column 2, row 1
	    Text category = new Text("..");
	    category.setFont(Font.font("Arial", FontWeight.BOLD, 10));
	    grid.add(category, 0, 0); 
	    //Critter.makeCritter("Algae");
	    
	    grid.setGridLinesVisible(true);
	    //
	    //layout.setRight(grid);
	    //controller.setCenter(grid);
	    /*
	     * 
	     * testing shit
	     */
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
        babymaker.setOnAction(new EventHandler<ActionEvent>() {     
            @Override 
            public void handle(ActionEvent e) {
            	try {
    				Critter.makeCritter("Craig");
    			//in case the user provided an invalid critter name
    			} catch(InvalidCritterException | NoClassDefFoundError e1) {
    				System.out.println("error processing: " + "Craig");
    			}
            	Critter.displayWorld(worldCanvas);
        	    statsText.setText(Critter.runStats());
            }
        });

        
        boolean running = true;
        runBtn.setOnAction(new EventHandler<ActionEvent>() {     
            @Override 
            public void handle(ActionEvent e) {
            		Critter.worldTimeStep();
                Critter.displayWorld(worldCanvas);
        	    		statsText.setText(Critter.runStats());
            }
        });
        
        Scene leftBorder = new Scene(layout);
        

        stage.setScene(leftBorder);
		stage.show();
	}

}
