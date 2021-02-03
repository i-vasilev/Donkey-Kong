package ru.rsreu.sebah.view;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import ru.rsreu.sebah.model.Barrel;
import ru.rsreu.sebah.model.Player;
import ru.rsreu.sebah.model.Point;

public class PlayerView extends EntityView<Player, Rectangle> {
    public PlayerView(Pane root, Player object) {
        super(root);
        final Point position = object.getPosition();
        shape = new Rectangle();
        shape.setX(position.getX());
        shape.setY(position.getY());
        shape.setWidth(Barrel.RADIUS);
        shape.setHeight(Barrel.RADIUS);
    }
}
