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
    public static final transient Object LOCK = new Object();
    public static final String DELIMITER = " ";

    private boolean[][] map;
    private transient Listener gameListener;
    private final List<Entity> entities;
    private final Player player;
    private int height;
    private int width;
    private Point finalPoint;
    private Point startPoint;
    private final List<PointDirection> pointsForBarrels = new ArrayList<>();
    private boolean pause;


    private void loadMap() throws IOException {
        final FileReader fileReader = new FileReader(MAP_FILENAME);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line = reader.readLine();
            if (line != null) {
                String[] splittedLine = getSplittedLine(line);
                height = Integer.parseInt(splittedLine[0]);
                width = Integer.parseInt(splittedLine[1]);
                finalPoint = parsePoint(reader.readLine());
                startPoint = parsePoint(reader.readLine());
                parseMap(reader);
                parsePointsForBarrels(reader);
            }
        }
    }

    private String[] getSplittedLine(String line) {
        return line.split(DELIMITER);
    }

    private void parsePointsForBarrels(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        while (line != null) {
            String[] splittedLine = getSplittedLine(line);
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

    private void parseMap(BufferedReader reader) throws IOException {
        map = new boolean[height][width];
        int i = 0;
        while (i < height) {
            String line = reader.readLine();
            int j = 0;
            String[] splittedLine = getSplittedLine(line);
            for (String sign : splittedLine) {
                map[i][j] = sign.equals("1");
                j++;
            }
            i++;
        }
    }

    private Point parsePoint(String line) {
        final String[] splittedLine = getSplittedLine(line);
        return new Point(Integer.parseInt(splittedLine[0]),
                Integer.parseInt(splittedLine[1]));
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
        entities.add(
                new Barrel(
                        this,
                        3
                )
        );
        player = new Player(this,
                (int) startPoint.getX(),
                (int) startPoint.getY()
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
        return finalPoint;
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

    public List<Entity> getBarrels() {
        return entities;
    }

    public void pause() {
        synchronized (LOCK) {
            if (pause) {
                LOCK.notifyAll();
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
            p.interrupt();
        }
        player.interrupt();
    }

    public void winGame() {
        stopAllEntities();
        gameListener.handle(this, EventType.WIN);
    }

    public void looseGame() {
        stopAllEntities();
        gameListener.handle(this, EventType.LOOSE);
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
