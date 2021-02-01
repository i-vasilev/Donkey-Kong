package ru.rsreu.sebah.model;

import ru.rsreu.sebah.view.EventType;
import ru.rsreu.sebah.view.Listener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Model implements Serializable {
    private static final transient String MAP_FILENAME = "./res/map.txt";
    private static final transient int START_POSITION_Y = 0;
    public static final transient int START_POSITION_X = 0;

    private boolean[][] map;
    private transient Listener GameListener;
    private final List<Entity> entities;
    private final Entity player;
    private int height;
    private int width;
    private Point finalPoints;
    private List<PointDirection> pointsForBarrels = new ArrayList<>();
    public static final transient Object thread = new Object();
    private boolean pause;


    private void loadMap() throws IOException {
        final FileReader fileReader = new FileReader(MAP_FILENAME);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            if (line != null) {
                String[] splittedLine = line.split(" ");
                int i = 0;
                int j;
                height = Integer.parseInt(splittedLine[0]);
                width = Integer.parseInt(splittedLine[1]);
                map = new boolean[height][width];
                line = reader.readLine();
                splittedLine = line.split(" ");
                finalPoints = new Point(Integer.parseInt(splittedLine[0]),
                        Integer.parseInt(splittedLine[1]));
                line = reader.readLine();
                while (i < height) {
                    j = 0;
                    splittedLine = line.split(" ");
                    for (String sign : splittedLine) {
                        map[i][j] = sign.equals("1");
                        j++;
                    }
                    i++;
                    line = reader.readLine();
                }
                while (line != null) {
                    splittedLine = line.split(" ");

                    pointsForBarrels.add(new PointDirection(Integer.parseInt(splittedLine[0]),
                            Integer.parseInt(splittedLine[1]), splittedLine[2].charAt(0)));
                    line = reader.readLine();
                }
            }
        }
    }

    private Direction getDirectionByName(String name) {
        if (name.equals("r")) {
            return Direction.RIGHT;
        }
        if (name.equals("l")) {
            return Direction.LEFT;
        }
        if (name.equals("d")) {
            return Direction.DOWN;
        }
        return null;
    }

    public Model() throws IOException {
        entities = new ArrayList<>();
        entities.add(new Barrel(this, 30, 30));
        player = new Player(this,
                START_POSITION_X,
                START_POSITION_Y
        );
        loadMap();
    }

    public boolean[][] getMap() {
        return map;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Point getFinalPoint() {
        return finalPoints;
    }

    public void setGameListener(Listener gameListener) {
        this.GameListener = gameListener;
        for (Entity p :
                entities) {
            p.setGameListener(gameListener);
        }
    }


    public Object getPlayer() {
        return player;
    }

    public void initialize() {
        GameListener.handle(this, EventType.INIT);
        entities.forEach(Entity::initialize);
    }

    public void start() {
        for (Entity p : entities) {
            p.start();
        }
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void pause() {
        synchronized (thread) {
            if (pause) {
                thread.notifyAll();
            } else {
                GameListener.handle(this, EventType.PAUSE);
            }
            pause = !pause;
        }
    }

    public boolean isPause() {
        return pause;
    }

    public void stopAllEntities() {
        for (Entity p : entities) {
            p.setStopped(true);
        }


    }

    public void stopAllEntities(Entity WinE) {
        stopAllEntities();
        GameListener.handle(WinE, EventType.WIN);
    }

    public List<PointDirection> getPointsForBarrels() {
        return pointsForBarrels;
    }

    public char getNextDirection(Point point) {
        for (PointDirection pointD :
                pointsForBarrels) {
            if (point.equals(pointD)) {
                return pointD.getDirection();
            }
        }
        return '/';
    }


}
