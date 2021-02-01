package ru.rsreu.sebah.view;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import ru.rsreu.sebah.model.Barrel;
import ru.rsreu.sebah.model.Point;
import ru.rsreu.sebah.model.Entity;

public class BarrelView extends ObjectView<Circle> {
    public BarrelView(Pane root, Entity object) {
        super(root);
        final Point position = object.getPosition();
        shape = new Circle(position.getX(), position.getY(), Barrel.RADIUS);
    }
}
