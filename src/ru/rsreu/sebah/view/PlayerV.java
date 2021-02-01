package ru.rsreu.sebah.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ru.rsreu.sebah.model.Entity;
import ru.rsreu.sebah.model.Model;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public abstract class PlayerV extends Rectangle implements ObjectListener {
	
	public static final double P_WIDTH = 10;
    public static final double P_HEIGHT = 20;
    private final Pane root;
    private final Text mario;
    List<PlayerV> players = new ArrayList<>();
    Text collisionText = new Text();
    boolean collision = false;
    
    	
    public PlayerV(Pane root, Entity object) {
        this.root = root;
     
        final Random random = new Random();
        Paint color = new Color(random.nextDouble(), random.nextDouble(), random.nextDouble(), 1);
        Font font = new Font(30.0);this.
        setFill(color);
        mario = TextBuilder.getBuilder()
                .setX((object.getPosition().getX() - Model.START_POSITION_X) * 10)
            .setY(45)
            .setFill(color)
            .setFont(font)
            .build();
   
    Platform.runLater(() -> root.getChildren().add(this));

    Platform.runLater(() -> root.getChildren().add(mario));




    	     
    }




	private static class TextBuilder {
	        private static Text text;

	        public static TextBuilder getBuilder() {
	            final TextBuilder textBuilder = new TextBuilder();
	            text = new Text();
	            return textBuilder;
	        }

	        public TextBuilder setX(double x) {
	            text.setX(x);
	            return this;
	        }

	        public TextBuilder setY(double y) {
	            text.setY(y);
	            return this;
	        }

	        public  TextBuilder setFill(Paint paint) {
	            text.setFill(paint);
	            return this;
	        }


	        public TextBuilder setFont(Font font) {
	            text.setFont(font);
	            return this;
	        }

	        public Text build() {
	            return text;
	        }
	    }
	
	}
    
    
