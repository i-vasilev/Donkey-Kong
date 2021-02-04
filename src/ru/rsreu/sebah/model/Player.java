package ru.rsreu.sebah.model;

import ru.rsreu.sebah.view.View;

import static ru.rsreu.sebah.model.Barrel.RADIUS;

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
        if (isCollideWalls()) {
            if (direction == Direction.RIGHT) {
                moveLeft();
            } else if (direction == Direction.LEFT) {
                moveRight();
            } else if (direction == Direction.DOWN) {
                moveUp();
            } else if (direction == Direction.UP) {
                moveDown();
            }
        }
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private boolean isCollideWalls() {
        int posMinY = (((int) (position.getX())) / View.WIDTH_SQUARE);
        int posMinX = (((int) (position.getY())) / View.WIDTH_SQUARE);
        int posMaxY = (((int) (position.getX() + RADIUS)) / View.WIDTH_SQUARE);
        int posMaxX = (((int) (position.getY() + RADIUS)) / View.WIDTH_SQUARE);
        final boolean b = position.getX() < 0 || position.getY() < 0
                || posMaxY >= modelGame.getWidth()
                || posMaxX >= modelGame.getHeight();
        if (!b) {
            return !modelGame.getMap()[posMinX][posMinY] || !modelGame.getMap()[posMaxX][posMaxY]
                    || !modelGame.getMap()[posMinX][posMaxY] || !modelGame.getMap()[posMaxX][posMinY];
        }
        return true;
    }

}
