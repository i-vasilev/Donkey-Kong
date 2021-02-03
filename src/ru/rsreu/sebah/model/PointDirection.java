package ru.rsreu.sebah.model;

public class PointDirection extends Point{
    private final Direction direction;

    public PointDirection(double x, double y, Direction direction) {
        super(x, y);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
