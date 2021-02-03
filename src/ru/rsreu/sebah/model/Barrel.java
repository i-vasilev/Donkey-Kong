package ru.rsreu.sebah.model;

import ru.rsreu.sebah.view.View;

import java.util.List;

public class Barrel extends Entity {
    public static final int RADIUS = 30;
    private int step;

    public Barrel(Model modelGame, int step) {
        super(modelGame,
                modelGame.getPointsForBarrels().get(step).getX() * View.WIDTH_SQUARE + RADIUS,
                modelGame.getPointsForBarrels().get(step).getY() * View.WIDTH_SQUARE + View.HEIGHT_TOP_PANEL + RADIUS
        );
        this.step = step;
    }

    @Override
    protected void move() {
        int posMinX = (int) ((position.getX() - RADIUS) / View.WIDTH_SQUARE);
        int posMinY = (int) ((position.getY() - View.HEIGHT_TOP_PANEL - RADIUS) / View.WIDTH_SQUARE);
        int posMaxX = (int) ((position.getX() + RADIUS) / View.WIDTH_SQUARE);
        int posMaxY = (int) ((position.getY() - View.HEIGHT_TOP_PANEL + RADIUS) / View.WIDTH_SQUARE);
        Point pos = new Point(posMinX, posMinY);
        Point posMax = new Point(posMaxX, posMaxY);
        final List<PointDirection> pointsForBarrels = modelGame.getPointsForBarrels();
        if (pos.equals(posMax)) {
            final int n = modelGame.getNextDirection(pos);
            if (n == pointsForBarrels.size() - 1) {
                step = 0;
                position.setX(pointsForBarrels.get(step).getX() + RADIUS);
                position.setY(pointsForBarrels.get(step).getY() + View.HEIGHT_TOP_PANEL + RADIUS);
            } else if (n != -1) {
                step = n;
            }
        }
        Direction direction = pointsForBarrels.get(step).getDirection();
        if (direction == Direction.RIGHT) {
            moveRight();
        } else if (direction == Direction.LEFT) {
            moveLeft();
        } else if (direction == Direction.DOWN) {
            moveDown();
        }
    }
}
