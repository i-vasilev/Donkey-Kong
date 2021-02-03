package ru.rsreu.sebah.model;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import ru.rsreu.sebah.view.*;


public abstract class Entity extends Thread {
    protected Point position;
    protected final Model modelGame;
    private transient Listener gameListener;
    private int countIterationsAfterCollide = 0;
    private boolean isStopped = false;
    private transient ObjectListener objectListener;

    public Entity(Model modelGame, double x, double y) {
        this.position = new Point(x, y);
        this.modelGame = modelGame;
        setDaemon(true);
    }

    public void initialize() {
        gameListener.handle(this, EventType.CREATE_ENTITY);
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            if (modelGame.isPause()) {
                try {
                    synchronized (Model.thread) {
                        Model.thread.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (Model.thread) {
                move();
            }
            objectListener.handle(this, ObjectEventType.UPDATE);
            try {
                sleep(10);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    protected abstract void move();

    public Shape getPolygon() {
        return (Shape) objectListener;
    }

    public void setStopped(boolean stopped) {
        isStopped = stopped;
    }

    protected void moveRight() {
        position.setX(position.getX() + 1);
    }

    protected void moveLeft() {
        position.setX(position.getX() - 1);
    }

    protected void moveDown() {
        position.setY(position.getY() + 1);
    }

    protected void moveUp() {
        position.setY(position.getY() - 1);
    }

    public Point getPosition() {
        return position;
    }

    public Polygon getRectangle() {
        return (Polygon) objectListener;
    }

    public void setGameListener(Listener gameListener) {
        this.gameListener = gameListener;
    }

    public void setObjectListener(ObjectListener objectListener) {
        this.objectListener = objectListener;
    }
}
