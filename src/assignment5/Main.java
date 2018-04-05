package assignment5;

import java.io.ByteArrayOutputStream;

import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

import assignment5.Critter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
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
	
	private int width=600;
	private int height=400;
	private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
	
    
    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }
	@Override
	public void start(Stage stage) throws Exception {
		// CONTROLLER
		stage.setTitle(" C R I T T E R S !");
		Pane controller = new Pane();
		controller.setMinSize(width, height);
		Text header = new Text("C R I T T E R S");
		header.setFont(Font.font("Ariel", FontWeight.BOLD, 24));
	    Button btn = new Button("s t e p");
	    BorderPane layout = new BorderPane();
	    // NOT MY CODE
	    VBox vbox = new VBox();
	    vbox.setPadding(new Insets(10));
	    vbox.setSpacing(8);

	    Text title = new Text("Data");
	    title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
	    vbox.getChildren().add(title);
	    vbox.getChildren().add(btn);
	    
	    // actually mine but whatever
	    controller.setStyle("-fx-border-color: green;");
	    layout.setTop(header);
	    layout.setLeft(vbox);
	    layout.setCenter(controller);
	    // END OF NOT MY CODE
	    
	    Button makeCritter = new Button("m a k e");
	    
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
	      

	    	  
	    	
	    
	    
        btn.setOnAction(new EventHandler<ActionEvent>() {     
            @Override 
            public void handle(ActionEvent e) {
                // Action for Button
            	Critter.worldTimeStep();
            	System.out.println("Hello Bug");
            }
        });
        
        Scene leftBorder = new Scene(layout);
        
        stage.setScene(leftBorder);
		stage.show();
	}

}
