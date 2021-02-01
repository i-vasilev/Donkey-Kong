package ru.rsreu.sebah.model;

import ru.rsreu.sebah.view.View;

public class Barrel extends Entity {
    public static final int RADIUS = 30;
    public Direction direction;

    public Barrel(Model modelGame, int x, int y) {
        super(modelGame, x, y);
    }

    @Override
    protected void move() {
        int posMinX = (int) (position.getX() / View.WIDTH_SQUARE);
        int posMinY = (int) (position.getY() / View.WIDTH_SQUARE);
        int posMaxX = (int) ((position.getX() + RADIUS * 2) / View.WIDTH_SQUARE);
        int posMaxY = (int) ((position.getY() + RADIUS * 2) / View.WIDTH_SQUARE);
        Point pos = new Point(posMinX, posMinY);
        Point posMax = new Point(posMaxX, posMaxY);
        if (pos.equals(posMax)) {
            final char nextDirection = modelGame.getNextDirection(pos);
            if (nextDirection != '/') {
                direction = nextDirection;
            }
        }
        if (direction == Direction.RIGHT) {
            moveRight();
        }
        if (direction == Direction.LEFT) {
            moveLeft();
        }
        if (direction == Direction.DOWN) {
            moveDown();
        }
    }

    private void moveRight() {
        position.setX(position.getX() + 1);
    }

    private void moveLeft() {
        position.setX(position.getX() - 1);
    }

    private void moveDown() {
        position.setY(position.getY() + 1);
    }
}
