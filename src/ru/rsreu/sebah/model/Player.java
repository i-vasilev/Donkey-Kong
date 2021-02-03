package ru.rsreu.sebah.model;

public class Player extends Entity {

    private Direction direction;

    public Player(Model modelGame, int x, int y) {
        super(modelGame, x, y);
    }

    @Override
    protected void move() {
        if (direction == Direction.RIGHT) {
            moveRight();
        } else if (direction == Direction.LEFT) {
            moveLeft();
        } else if (direction == Direction.DOWN) {
            moveDown();
        } else if (direction == Direction.UP) {
            moveUp();
        }
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
