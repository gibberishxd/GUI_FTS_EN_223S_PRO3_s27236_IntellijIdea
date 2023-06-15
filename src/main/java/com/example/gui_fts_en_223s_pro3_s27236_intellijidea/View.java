package com.example.gui_fts_en_223s_pro3_s27236_intellijidea;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.util.List;

public class View {

    private BorderPane root;
    private MenuBar menuBar;
    private Controller controller;
    private int time;

    private TextFlow wordTextFlow;
    private StringBuilder inputBuffer;

    public View(BorderPane root) {
        this.root = root;
        initialize();
    }

    public void setWords(List<String> words) {
        wordTextFlow.getChildren().clear(); // Clear the existing content

        int wordsPerLine = 10; // Change this to the desired number of words per line
        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);

            for (int j = 0; j < word.length(); j++) {
                Text characterText = new Text(String.valueOf(word.charAt(j)));
                characterText.setFill(getCharacterColor(word.charAt(j), i == 0 && j == 0));
                wordTextFlow.getChildren().add(characterText);
            }

            if ((i + 1) % wordsPerLine == 0) {
                wordTextFlow.getChildren().add(new Text("\n"));
            } else {
                wordTextFlow.getChildren().add(new Text(" "));
            }
        }
    }


    private Color getCharacterColor(char ch, boolean isFirstCharacter) {
        if (isFirstCharacter) {
            return Color.BLACK;
        } else if (inputBuffer.length() >= 1 && ch == inputBuffer.charAt(0)) {
            return Color.GREEN;
        } else if (inputBuffer.length() >= 1 && ch != inputBuffer.charAt(0)) {
            return Color.RED;
        } else if (inputBuffer.length() < 1 && !Character.isWhitespace(ch)) {
            return Color.GRAY;
        } else {
            return Color.ORANGE;
        }
    }

    private void initialize() {
        // Create menu bar
        menuBar = new MenuBar();
        Menu languageMenu = new Menu("Language");
        Menu timeMenu = new Menu("Test Time");

        wordTextFlow = new TextFlow();
        wordTextFlow.setStyle("-fx-font-size: 15px;");
        wordTextFlow.setLineSpacing(10);
        wordTextFlow.setPrefWidth(800);
        wordTextFlow.setPrefHeight(200);
        wordTextFlow.setTextAlignment(TextAlignment.CENTER);

        VBox vbox = new VBox(wordTextFlow);
        vbox.setAlignment(Pos.CENTER);

        inputBuffer = new StringBuilder();

        // Add menu items to menu bar
        menuBar.getMenus().addAll(languageMenu, timeMenu);

        MenuItem time15MenuItem = new MenuItem("15 seconds");
        MenuItem time20MenuItem = new MenuItem("20 seconds");
        MenuItem time45MenuItem = new MenuItem("45 seconds");
        MenuItem time60MenuItem = new MenuItem("60 seconds");
        MenuItem time90MenuItem = new MenuItem("90 seconds");
        MenuItem time120MenuItem = new MenuItem("120 seconds");
        MenuItem time300MenuItem = new MenuItem("300 seconds");
        timeMenu.getItems().addAll(time15MenuItem, time20MenuItem, time45MenuItem, time60MenuItem,
                time90MenuItem, time120MenuItem, time300MenuItem);

        time15MenuItem.setOnAction(e -> setTime(15));
        time20MenuItem.setOnAction(e -> setTime(20));
        time45MenuItem.setOnAction(e -> setTime(45));
        time60MenuItem.setOnAction(e -> setTime(60));
        time90MenuItem.setOnAction(e -> setTime(90));
        time120MenuItem.setOnAction(e -> setTime(120));
        time300MenuItem.setOnAction(e -> setTime(300));

        // Create footer
        Label footerLabel = new Label("Keyboard Shortcuts: Tab+Enter = Restart Test, " +
                "Ctrl+Shift+P = Pause, Esc = End Test");
        footerLabel.setAlignment(Pos.CENTER);

        // Set the word text flow in the center of the root
        root.setCenter(vbox);
        root.setTop(menuBar);
        root.setBottom(footerLabel);

        // Add event handlers for user input
        root.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPressed);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private void setTime(int seconds) {
        time = seconds;
    }

    private void handleKeyPressed(KeyEvent event) {
        KeyCode keyCode = event.getCode();

        if (keyCode == KeyCode.BACK_SPACE && inputBuffer.length() > 0) {
            inputBuffer.deleteCharAt(inputBuffer.length() - 1);
        } else if (keyCode.isLetterKey()) {
            inputBuffer.append(event.getText().charAt(0));
        } else {
            return; // Ignore other key presses
        }

        updateWordTextFlow();
        event.consume();
    }

    private void updateWordTextFlow() {
        String input = inputBuffer.toString();
        wordTextFlow.getChildren().clear(); // Clear the existing content

        int wordsPerLine = 10; // Change this to the desired number of words per line
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            Text characterText = new Text(String.valueOf(ch));
            characterText.setFill(getCharacterColor(ch, i == 0));
            wordTextFlow.getChildren().add(characterText);
        }

        if (input.length() > 0 && Character.isWhitespace(input.charAt(input.length() - 1))) {
            wordTextFlow.getChildren().add(new Text(" "));
        }

        for (int i = input.length(); i < wordTextFlow.getChildren().size(); i++) {
            Text characterText = (Text) wordTextFlow.getChildren().get(i);
            characterText.setFill(getCharacterColor(characterText.getText().charAt(0), false));
        }
    }

    public void addLanguageMenuItem(String languageName, MenuItem menuItem) {
        Menu languageMenu = (Menu) menuBar.getMenus().get(0);
        languageMenu.getItems().add(menuItem);
    }
}
