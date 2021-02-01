package ru.rsreu.sebah.view;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import ru.rsreu.sebah.model.Barrel;
import ru.rsreu.sebah.model.Entity;

public abstract class ObjectView<R extends Shape> implements ObjectListener {

    protected R shape;

    public ObjectView(Pane root) {
        Platform.runLater(() -> root.getChildren().add(shape));
    }

    @Override
    public void handle(Object object, ObjectEventType type) {
        if (object instanceof Entity) {
            Barrel car = (Barrel) object;
            if (type == ObjectEventType.UPDATE) {
                Platform.runLater(() -> {
                    shape.setTranslateX(car.getPosition().getX());
                    shape.setTranslateY(car.getPosition().getY());
                });
            }
        }
    }
}
    
    
