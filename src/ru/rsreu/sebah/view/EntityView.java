package ru.rsreu.sebah.view;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import ru.rsreu.sebah.model.Entity;

public abstract class EntityView<E extends Entity, R extends Shape> implements ObjectListener<E> {

    protected R shape;

    protected EntityView(Pane root) {
        Platform.runLater(() -> root.getChildren().add(shape));
    }

    @Override
    public void handle(E object, ObjectEventType type) {
        if (type == ObjectEventType.UPDATE) {
            Platform.runLater(() -> {
                shape.setTranslateX(object.getPosition().getX());
                shape.setTranslateY(object.getPosition().getY());
            });
        }
    }
}
