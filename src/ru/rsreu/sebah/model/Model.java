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
    private transient Listener gameListener;
    private final List<Entity> entities;
    private final Player player;
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
                    pointsForBarrels.add(
                            new PointDirection(
                                    Integer.parseInt(splittedLine[0]),
                                    Integer.parseInt(splittedLine[1]),
                                    getDirectionByName(splittedLine[2].charAt(0))
                            )
                    );
                    line = reader.readLine();
                }
            }
        }
    }

    private Direction getDirectionByName(char name) {
        if (name == 'r') {
            return Direction.RIGHT;
        }
        if (name == 'l') {
            return Direction.LEFT;
        }
        if (name == 'd') {
            return Direction.DOWN;
        }
        return null;
    }

    public Model() throws IOException {
        loadMap();
        entities = new ArrayList<>();
        entities.add(
                new Barrel(
                        this,
                        0
                )
        );
        player = new Player(this,
                START_POSITION_X,
                START_POSITION_Y
        );
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
        this.gameListener = gameListener;
        for (Entity p :
                entities) {
            p.setGameListener(gameListener);
        }
        player.setGameListener(gameListener);
    }


    public Player getPlayer() {
        return player;
    }

    public void initialize() {
        gameListener.handle(this, EventType.INIT);
        entities.forEach(Entity::initialize);
        player.initialize();
    }

    public void start() {
        for (Entity p : entities) {
            p.start();
        }
        player.start();
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void pause() {
        synchronized (thread) {
            if (pause) {
                thread.notifyAll();
            } else {
                gameListener.handle(this, EventType.PAUSE);
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
        gameListener.handle(WinE, EventType.WIN);
    }

    public List<PointDirection> getPointsForBarrels() {
        return pointsForBarrels;
    }

    public int getNextDirection(Point point) {
        for (int i = 0; i < pointsForBarrels.size(); i++) {
            if (point.equals(pointsForBarrels.get(i))) {
                return i;
            }
        }
        return -1;
    }


}
