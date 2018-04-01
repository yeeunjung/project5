package assignment5;

import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class Main extends Application{

	public static void main(String[] args) {
		// launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
        stage.setTitle("Java FX Demo Program");
        //Group root = new Group();
        FlowPane fp = new FlowPane();
        
        Canvas canvas = new Canvas(300, 250);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        Text t = new Text("Hello World");
        t.setFill(Color.PURPLE);
        Font f = new Font(80);
        t.setFont(f);
       
        fp.getChildren().add(t);
        fp.getChildren().add(canvas);

        Scene scene = new Scene(fp);
        stage.setScene(scene);
        stage.show();
	}

}
