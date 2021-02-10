package ru.rsreu.sebah.view;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import ru.rsreu.sebah.controller.Controller;
import ru.rsreu.sebah.model.Barrel;
import ru.rsreu.sebah.model.Entity;
import ru.rsreu.sebah.model.Model;
import ru.rsreu.sebah.model.Player;

public class View implements Listener {
    public static final int WIDTH_WINDOW = 500;
    public static final int HEIGHT_WINDOW = 726;
    public static final int HEIGHT_TOP_PANEL = 26;
    public static final int WIDTH_SQUARE = 100;
    private final Controller controller;
    private final BorderPane root;
    private final FileChooser fileChooser = new FileChooser();
    private static final String OPEN_GAME_MENU_OPTION = "Open...";
    private static final String SAVE_GAME_MENU_OPTION = "Save...";
    private static final String EXIT_GAME_MENU_OPTION = "Exit";
    private static final String MENU_NAME = "Game";
    private static final double GRAY_INTENSITY = 0.8;
    private static final String WIN_MESSAGE = "You are win!";
    private static final String LOOSE_MESSAGE = "Unfortunately you are loose!";
    public static final int WIN_MESSAGE_WINDOW_X = 50;
    public static final int WIN_MESSAGE_WINDOW_Y = 200;
    public static final int WIN_MESSAGE_WINDOWS_WIDTH = 400;
    public static final int WIN_MESSAGE_WIDTH = 350;
    public static final int WIN_MESSAGE_WINDOWS_HEIGHT = 250;
    private static final int X_WON_MESSAGE = 70;
    private static final int Y_WON_MESSAGE = 210;
    private static final int FONT_SIZE = 40;

    public View(Controller controller, BorderPane root) {
        this.controller = controller;
        this.root = root;
    }

    @Override
    public void handle(Object object, EventType type) {
        if (type == EventType.INIT) {
            initGame((Model) object);
        }
        if (type == EventType.CREATE_ENTITY) {
            ((Entity) object).setObjectListener(createObjectListener(object));
        }
        if (type == EventType.WIN) {
            showMessage(WIN_MESSAGE);
        }
        if (type == EventType.LOOSE) {
            showMessage(LOOSE_MESSAGE);
        }
    }

    private ObjectListener createObjectListener(Object object) {
        ObjectListener objectListener;
        if (object.getClass().equals(Barrel.class)) {
            objectListener = createBarrelView((Barrel) object);
        } else {
            objectListener = createPlayerView((Player) object);
        }
        return objectListener;
    }

    private PlayerView createPlayerView(Player object) {
        return new PlayerView(root, object);
    }

    private BarrelView createBarrelView(Barrel object) {
        return new BarrelView(root, object);
    }

    private void initGame(Model model) {
        root.getChildren().clear();
        root.setPrefSize(WIDTH_WINDOW, HEIGHT_WINDOW + HEIGHT_TOP_PANEL);
        for (int i = 0; i < model.getHeight(); i++) {
            for (int j = 0; j < model.getWidth(); j++) {
                if (i == model.getFinalPoint().getY() && j == model.getFinalPoint().getX()) {
                    drawFinalPoint(i, j);
                }
                if (j != 0 && model.getMap()[i][j - 1] != model.getMap()[i][j]) {
                    drawLine(i, j, j, i + 1);
                }

                if (i != 0 && model.getMap()[i - 1][j] != model.getMap()[i][j]) {
                    drawLine(i, j, j + 1, i);
                }
            }
        }
        drawTopLine();
        createMenuBar(controller, root);

        root.setOnKeyPressed(a -> controller.addKey(a.getCode()));
        root.setOnKeyReleased(a -> controller.removeKey());
    }

    private void drawTopLine() {
        Platform.runLater(() -> root.getChildren()
                .add(new Line(0, HEIGHT_TOP_PANEL, WIDTH_WINDOW, HEIGHT_TOP_PANEL)));
    }

    private void drawLine(int y, int x, int x2, int i) {
        Platform.runLater(() -> root.getChildren()
                .add(new Line(x * WIDTH_SQUARE, y * WIDTH_SQUARE + HEIGHT_TOP_PANEL, x2 * WIDTH_SQUARE,
                        (i) * WIDTH_SQUARE + HEIGHT_TOP_PANEL)));
    }

    private void drawFinalPoint(int y, int x) {
        Platform.runLater(() -> {
            Paint fillSquare = new Color(0, 0, 0, 0.1);
            final Rectangle finalRectangle = new Rectangle();
            finalRectangle.setX(x * WIDTH_SQUARE);
            finalRectangle.setY(y * WIDTH_SQUARE + HEIGHT_TOP_PANEL);
            finalRectangle.setHeight(WIDTH_SQUARE);
            finalRectangle.setWidth(WIDTH_SQUARE);
            finalRectangle.setFill(fillSquare);
            root.getChildren().add(finalRectangle);
        });
    }

    private void createMenuBar(Controller controller, BorderPane root) {
        MenuBar menuBar = new MenuBar();
        Menu gameMenu = new Menu(MENU_NAME);
        Menu openMenu = new Menu(OPEN_GAME_MENU_OPTION);
        openMenu.setOnAction(actionEvent -> controller.openFile(fileChooser.showOpenDialog(root.getScene().getWindow())));
        Menu saveMenu = new Menu(SAVE_GAME_MENU_OPTION);
        saveMenu.setOnAction(actionEvent -> controller.saveFile(fileChooser.showSaveDialog(root.getScene().getWindow())));
        Menu closeMenu = new Menu(EXIT_GAME_MENU_OPTION);
        closeMenu.setOnAction(event -> controller.exit());
        gameMenu.getItems().addAll(openMenu, saveMenu, closeMenu);
        menuBar.getMenus().add(gameMenu);
        root.setTop(menuBar);
    }


    private void showMessage(String message) {
        Platform.runLater(() -> {
            drawRectangle();
            drawLabel(message);
        });
    }

    private void drawRectangle() {
        Paint fillRect = new Color(GRAY_INTENSITY, GRAY_INTENSITY, GRAY_INTENSITY, 1);
        Rectangle rectangle = new Rectangle();
        rectangle.setX(WIN_MESSAGE_WINDOW_X);
        rectangle.setY(WIN_MESSAGE_WINDOW_Y);
        rectangle.setWidth(WIN_MESSAGE_WINDOWS_WIDTH);
        rectangle.setHeight(WIN_MESSAGE_WINDOWS_HEIGHT);
        rectangle.setFill(fillRect);
        root.getChildren().add(rectangle);
    }

    private void drawLabel(String message) {
        final Label text = new Label(message);
        text.setWrapText(true);
        text.setMaxWidth(WIN_MESSAGE_WIDTH);
        text.setMinWidth(WIN_MESSAGE_WIDTH);
        text.setTranslateX(X_WON_MESSAGE);
        text.setTranslateY(Y_WON_MESSAGE);
        text.setFont(new Font(FONT_SIZE));
        FlowPane fp = new FlowPane();
        fp.getChildren().add(text);
        root.setCenter(fp);
    }
}
