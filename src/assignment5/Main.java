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

public class Main extends Application{
	private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
	public static final int MULTIPLIER = 3;
    
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
	    Button babymaker = new Button("MAKE");
	    btn.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-font-family: \"Courier New\";");
	    babymaker.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-font-family: \"Courier New\";");

	    BorderPane layout = new BorderPane();
	    layout.setPadding(new Insets(20));
	    Canvas worldCanvas = new Canvas(Params.world_width*MULTIPLIER, Params.world_height*MULTIPLIER);
	    
	    // NOT MY CODE
	    // Vbox credit for help: https://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
	    HBox vbox = new HBox();
	    vbox.setPadding(new Insets(20));
	    vbox.setSpacing(8);

	    Text title = new Text("DATA");
	    title.setFont(Font.font("Courier New", FontWeight.BOLD, 14));
	    vbox.getChildren().add(title);
	    vbox.getChildren().add(btn);
	    vbox.getChildren().add(babymaker);
	    
	    
	    // actually mine but whatever
	    controller.setStyle("-fx-border-color: green;");
	    controller.setCenter(worldCanvas);
	    layout.setTop(header);
	    layout.setLeft(vbox);
	    layout.setCenter(controller);
	    // END OF NOT MY CODE
	    

	    
	    makeCritter.setOnAction(new EventHandler<ActionEvent>()	{
            @Override 
            public void handle(ActionEvent e) {
                // Action for Button
            	Critter.worldTimeStep();
            	System.out.println("Hello Bug");
            }
	    });
	    

	    	  

	    
	        
	        Button makeButton = new Button("Make Critter");
	        ComboBox<String> makeCritterDropdown = new ComboBox<>();
	        //String[] classes = this.getClasses();
	        String[] classes = {"Critter1", "Critter2", "Critter3"};
	        makeCritterDropdown.getItems().addAll(classes);

	        TextField makeInputBox = new TextField("1"); // default to 1 critter
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
	                        }
	                    }
	                } catch (InvalidCritterException e) {
	                    // ignore
	                } catch (NumberFormatException e) {
	                    // ignore
	                }
	            }
	        });

	        vbox.getChildren().add(makeButton);
	        vbox.getChildren().add(makeInputBox);
	        vbox.getChildren().add(makeCritterDropdown);
	      

	    	  
	    	
	    
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
            }
        });
        babymaker.setOnAction(new EventHandler<ActionEvent>() {     
            @Override 
            public void handle(ActionEvent e) {
            	try {
    				Critter.makeCritter("Craig");
    				Critter.makeCritter("Critter1");
    				Critter.makeCritter("Critter2");
    				Critter.makeCritter("Critter3");
    				Critter.makeCritter("Critter4");
    			//in case the user provided an invalid critter name
    			} catch(InvalidCritterException | NoClassDefFoundError e1) {
    				System.out.println("error processing: " + "Craig");
    			}
            	Critter.displayWorld(worldCanvas);
            }
        });
        Button runBtn = new Button("RUN");
        vbox.getChildren().add(runBtn);
        boolean running = true;
        runBtn.setOnAction(new EventHandler<ActionEvent>() {     
            @Override 
            public void handle(ActionEvent e) {
            	Critter.worldTimeStep();
                Critter.displayWorld(worldCanvas);
            }
        });
        
        Scene leftBorder = new Scene(layout);
        

        stage.setScene(leftBorder);
		stage.show();
	}

}
