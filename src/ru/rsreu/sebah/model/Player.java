package ru.rsreu.sebah.model;

import ru.rsreu.sebah.view.View;

import static ru.rsreu.sebah.model.Barrel.RADIUS;

public class Player extends Entity {

    private Direction direction;

    public Player(Model modelGame, int x, int y) {
        super(modelGame,
                (double) x * View.WIDTH_SQUARE,
                (double) y * View.WIDTH_SQUARE + View.HEIGHT_TOP_PANEL);
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
        checkCollidesWithBarrels();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private boolean isCollideWalls() {
        int posMinY = (((int) (position.getX())) / View.WIDTH_SQUARE);
        int posMinX = (((int) (position.getY() - View.HEIGHT_TOP_PANEL)) / View.WIDTH_SQUARE);
        int posMaxY = (((int) (position.getX() + RADIUS)) / View.WIDTH_SQUARE);
        int posMaxX = (((int) (position.getY() - View.HEIGHT_TOP_PANEL + RADIUS)) / View.WIDTH_SQUARE);
        final boolean b = position.getX() < 0 || position.getY() < 0
                || posMaxY >= modelGame.getWidth()
                || posMaxX >= modelGame.getHeight();
        if (!b) {
            return !modelGame.getMap()[posMinX][posMinY] || !modelGame.getMap()[posMaxX][posMaxY]
                    || !modelGame.getMap()[posMinX][posMaxY] || !modelGame.getMap()[posMaxX][posMinY];
        }
        return true;
    }

    private void checkCollidesWithBarrels() {
        for (Entity barrel : modelGame.getBarrels()) {
            if (this != barrel && collides(barrel.getPosition(), getPosition())) {
                modelGame.stopAllEntities();
            }
        }
    }

    private boolean collides(Point c1, Point r1) {
        double closestX = clamp(c1.getX(), r1.getX(), r1.getX() + RADIUS);
        double closestY = clamp(c1.getY(), r1.getY(), r1.getY() + RADIUS);

        double distanceX = c1.getX() - closestX;
        double distanceY = c1.getY() - closestY;

        return Math.pow(distanceX, 2) + Math.pow(distanceY, 2) < Math.pow(RADIUS, 2);
    }

    private double clamp(double value, double min, double max) {
        double x = value;
        if (x < min) {
            x = min;
        } else if (x > max) {
            x = max;
        }
        return x;
    }
}
