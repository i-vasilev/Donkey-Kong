package ru.rsreu.sebah.model;

public class PointDirection extends Point{
    private final char direction;

    public PointDirection(double x, double y, char direction) {
        super(x, y);
        this.direction = direction;
    }

    public char getDirection() {
        return direction;
    }
}
