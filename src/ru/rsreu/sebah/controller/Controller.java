package ru.rsreu.sebah.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.scene.input.KeyCode;
import ru.rsreu.sebah.model.Direction;
import ru.rsreu.sebah.model.Model;
import ru.rsreu.sebah.view.Listener;

public class Controller {
    private Model model;
    private Listener listener;

    public Controller(Model model) {
        this.model = model;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
        model.setGameListener(listener);
    }

    public void addKey(KeyCode keyCode) {
        if (keyCode == KeyCode.ESCAPE) {
            model.pause();
        }
        if (keyCode == KeyCode.RIGHT) {
            model.getPlayer();
        }
        if (keyCode == KeyCode.UP) {
            model.getPlayer();
        }
        if (keyCode == KeyCode.LEFT) {
            model.getPlayer();
        }
        if (keyCode == KeyCode.DOWN) {
            model.getPlayer();
        }
    }

    public void removeKey(KeyCode keyCode) {
        if (keyCode == KeyCode.RIGHT) {
            model.getPlayer();
        }
        if (keyCode == KeyCode.UP) {
            model.getPlayer();
        }
        if (keyCode == KeyCode.LEFT) {
            model.getPlayer();
        }
        if (keyCode == KeyCode.DOWN) {
            model.getPlayer();
        }
    }
    
    public void exit() {
        System.exit(0);
    }
    	
    public void openFile(File file) {
        model.stopAllEntities();
        model = (Model) openObjectFromFile(file.getAbsolutePath());
        if (model != null) {
            model.setGameListener(listener);
            model.initialize();
            model.start();
        }
    }
    private Object openObjectFromFile(String path) {
        try (FileInputStream fis = new FileInputStream(path)) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (Exception e) {
            return null;
        }
    }
    void saveObjectToFile(Object objForSaving, String path) {
        try (FileOutputStream outputStream = new FileOutputStream(path)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(objForSaving);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public void saveFile(File file) {
        saveObjectToFile(model, file.getAbsolutePath());
    }
}
