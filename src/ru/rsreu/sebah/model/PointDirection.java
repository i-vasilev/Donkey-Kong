package ru.rsreu.sebah.model;

import java.io.Serializable;

public class PointDirection extends Point implements Serializable {
    private final Direction direction;

    public PointDirection(double x, double y, Direction direction) {
        super(x, y);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
