package com.example.gui_fts_en_223s_pro3_s27236_intellijidea;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

import java.util.List;

public class View {

    private BorderPane root;
    private MenuBar menuBar;
    private Controller controller;

    private Label wordLabel;

    public View(BorderPane root) {
        this.root = root;
        initialize();
    }

    public void setWords(List<String> words) {
        String wordsText = String.join(" ", words);
        wordLabel.setText(wordsText);
    }

    private void initialize() {
        // Create menu bar
        menuBar = new MenuBar();
        Menu languageMenu = new Menu("Language");
        Menu timeMenu = new Menu("Test Time");

        // Add menu items to menu bar
        menuBar.getMenus().addAll(languageMenu, timeMenu);

        // Create footer
        Label footerLabel = new Label("Keyboard Shortcuts: Tab+Enter = Restart Test, " +
                "Ctrl+Shift+P = Pause, Esc = End Test");
        footerLabel.setAlignment(Pos.CENTER);

        // Set the word label in the center of the root
        root.setCenter(wordLabel);
        root.setTop(menuBar);
        root.setBottom(footerLabel);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void addLanguageMenuItem(String languageName, MenuItem menuItem) {
        Menu languageMenu = (Menu) menuBar.getMenus().get(0);
        languageMenu.getItems().add(menuItem);
    }
}