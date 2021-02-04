package ru.rsreu.sebah.view;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import ru.rsreu.sebah.model.Barrel;
import ru.rsreu.sebah.model.Point;

public class BarrelView extends EntityView<Barrel, Circle> {
    public BarrelView(Pane root, Barrel object) {
        super(root);
        final Point position = object.getPosition();
        shape = new Circle(position.getX() / 2, position.getY() / 2, Barrel.RADIUS);
    }

    @Override
    public void handle(Barrel object, ObjectEventType type) {
        Platform.runLater(() -> {
            shape.setCenterX(object.getPosition().getX());
            shape.setCenterY(object.getPosition().getY());
        });
    }
}
