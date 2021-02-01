package ru.rsreu.sebah.view;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import ru.rsreu.sebah.controller.Controller;
import ru.rsreu.sebah.model.Barrel;
import ru.rsreu.sebah.model.Entity;
import ru.rsreu.sebah.model.Model;

;


public class View implements Listener {
    public static final int WIDTH_WINDOW = 500;
    public static final int HEIGHT_WINDOW = 700;
    public static final int HEIGHT_TOP_PANEL = 0;
    public static final int WIDTH_SQUARE = 100;

//    public static final int WIN_MESSAGE_WINDOW_X = 150;
//    public static final int WIN_MESSAGE_WINDOW_Y = 200;
//    public static final int WIN_MESSAGE_WINDOWS_WIDTH = 200;
//    public static final int WIN_MESSAGE_WINDOWS_HEIGHT = 100;
//    private static final int X_WON_MESSAGE = 200;
//    private static final int Y_WON_MESSAGE = 300;
//    private static final int FONT_SIZE = 40;
//    private static final double GRAY_INTENSITY = 0.8;
//    private static final String WIN_MESSAGE = " you won!";

    private final Controller controller;
    private final BorderPane root;
    private final FileChooser fileChooser = new FileChooser();
    private static final String OPEN_GAME_MENU_OPTION = "Open...";
    private static final String SAVE_GAME_MENU_OPTION = "Save...";
    private static final String EXIT_GAME_MENU_OPTION = "Exit";
    private static final String MENU_NAME = "Game";


    public View(Controller controller, BorderPane root) {
        this.controller = controller;
        this.root = root;
    }


    @Override
    public void handle(Object object, EventType type) {
        if (type == EventType.INIT) {
            root.getChildren().clear();
            initGame((Model) object);
            root.setOnKeyPressed(a -> controller.addKey(a.getCode()));
            root.setOnKeyReleased(a -> controller.removeKey(a.getCode()));
            MenuBar menuBar = createMenuBar(controller, root);
            root.setTop(menuBar);
        }
        if (type == EventType.CREATE_ENTITY) {
            ObjectListener objectListener = null;
            if (object.getClass().equals(Barrel.class)) {
                objectListener = createBarrelView((Entity) object);
            }
            ((Entity) object).setObjectListener(objectListener);
        }

    }

    private ObjectListener createBarrelView(Entity object) {
        return new BarrelView(root, object);
    }

    private void initGame(Model model) {
        root.setPrefSize(WIDTH_WINDOW, HEIGHT_WINDOW + HEIGHT_TOP_PANEL);
        for (int i = 0; i < model.getHeight(); i++) {
            for (int j = 0; j < model.getWidth(); j++) {
                final int y = i;
                final int x = j;
                if (y == model.getFinalPoint().getY() && x == model.getFinalPoint().getX()) {
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
                if (x != 0 && model.getMap()[y][x - 1] != model.getMap()[y][x]) {
                    Platform.runLater(() -> root.getChildren()
                            .add(new Line(x * WIDTH_SQUARE, y * WIDTH_SQUARE + HEIGHT_TOP_PANEL, x * WIDTH_SQUARE,
                                    (y + 1) * WIDTH_SQUARE + HEIGHT_TOP_PANEL)));
                }

                if (y != 0 && model.getMap()[y - 1][x] != model.getMap()[y][x]) {
                    Platform.runLater(() -> root.getChildren()
                            .add(new Line(x * WIDTH_SQUARE, y * WIDTH_SQUARE + HEIGHT_TOP_PANEL,
                                    (x + 1) * WIDTH_SQUARE, y * WIDTH_SQUARE + HEIGHT_TOP_PANEL)));
                }
            }
        }
        Platform.runLater(() -> root.getChildren()
                .add(new Line(0, HEIGHT_TOP_PANEL, WIDTH_WINDOW, HEIGHT_TOP_PANEL)));
    }

    private MenuBar createMenuBar(Controller controller, BorderPane root) {
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
        return menuBar;
    }

}
