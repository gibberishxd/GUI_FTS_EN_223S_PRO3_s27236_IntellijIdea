package com.example.gui_fts_en_223s_pro3_s27236_intellijidea;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.List;

public class View {

    private static final String CHARACTER_DEFAULT_STYLE = "-fx-fill: black;";
    private static final String CHARACTER_CORRECT_STYLE = "-fx-fill: green;";
    private static final String CHARACTER_INCORRECT_STYLE = "-fx-fill: red;";
    private static final String CHARACTER_UNKNOWN_STYLE = "-fx-fill: orange;";
    private static final String CHARACTER_EMPTY_STYLE = "-fx-fill: gray;";
    private BorderPane root;
    private MenuBar menuBar;
    private Controller controller;
    private int time;
    private TextFlow wordTextFlow;
    private StringBuilder typedText = new StringBuilder();

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
                characterText.setStyle(getCharacterStyle(word.charAt(j), i == 0 && j == 0));
                wordTextFlow.getChildren().add(characterText);
            }

            if ((i + 1) % wordsPerLine == 0) {
                wordTextFlow.getChildren().add(new Text("\n"));
            } else {
                wordTextFlow.getChildren().add(new Text(" "));
            }
        }
    }

    private String getCharacterStyle(char ch, boolean isLastCharacter) {
        String input = typedText.toString();

        if (isLastCharacter) {
            return CHARACTER_DEFAULT_STYLE;
        } else if (!input.isEmpty() && ch == input.charAt(input.length() - 1)) {
            return CHARACTER_CORRECT_STYLE;
        } else if (!input.isEmpty() && ch != input.charAt(input.length() - 1)) {
            return CHARACTER_INCORRECT_STYLE;
        } else if (input.isEmpty() && !Character.isWhitespace(ch)) {
            return CHARACTER_EMPTY_STYLE;
        } else {
            return CHARACTER_UNKNOWN_STYLE;
        }
    }



    private void initialize() {
        // Create menu bar
        menuBar = new MenuBar();
        Menu languageMenu = new Menu("Language");
        Menu timeMenu = new Menu("Test Time");

        wordTextFlow = new TextFlow();
        wordTextFlow.setStyle("-fx-font-size: 15px;");
        wordTextFlow.setTextAlignment(TextAlignment.CENTER);

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
        VBox vbox = new VBox(wordTextFlow);
        root.setCenter(vbox);
        root.setTop(menuBar);
        root.setBottom(footerLabel);

        // Add event handlers for user input
        root.setOnKeyPressed(this::handleKeyPressed);
        root.setOnKeyTyped(this::handleKeyTyped);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private void setTime(int seconds) {
        time = seconds;
    }

    private void handleKeyPressed(KeyEvent event) {
        KeyCode keyCode = event.getCode();

        if (keyCode == KeyCode.BACK_SPACE) {
            if (typedText.length() > 0) {
                typedText.deleteCharAt(typedText.length() - 1);
            }
        } else if (keyCode == KeyCode.ENTER) {
            handleInput();
        } else if (keyCode == KeyCode.SPACE) {
            handleInput();
            typedText.setLength(0); // Clear the typed text for the next word
        } else {
            return; // Ignore other key presses
        }

        updateWordTextFlow();
        event.consume();
    }


    private void handleKeyTyped(KeyEvent event) {
        String inputText = event.getCharacter();

        if (!inputText.isEmpty()) {
            typedText.append(inputText);
            handleInput();
            updateWordTextFlow();
        }

        event.consume();
    }


    private void handleInput() {
        String inputText = typedText.toString();
        // Process the user input, compare with the expected word, etc.

        // Get the expected word from the controller
        String expectedWord = controller.getExpectedWord();

        if (inputText.equals(expectedWord)) {
            // User input matches the expected word
            // Perform actions for correct input
            controller.handleCorrectInput();
        } else {
            // User input does not match the expected word
            // Perform actions for incorrect input
            controller.handleIncorrectInput();
        }

        // Clear the typed text for the next word
        typedText.setLength(0);
    }

    private void updateWordTextFlow() {
        String input = typedText.toString();
        wordTextFlow.getChildren().clear(); // Clear the existing content

        int wordsPerLine = 10; // Change this to the desired number of words per line
        int characterCount = 0;
        int inputLength = input.length();
        int remainingWordsIndex = controller.getCurrentWordIndex() + 1;

        for (int i = 0; i < inputLength; i++) {
            char ch = input.charAt(i);
            Text characterText = new Text(String.valueOf(ch));
            characterText.setStyle(getCharacterStyle(ch, i == inputLength - 1));
            wordTextFlow.getChildren().add(characterText);
            characterCount++;

            if (characterCount % wordsPerLine == 0) {
                wordTextFlow.getChildren().add(new Text("\n"));
            } else {
                wordTextFlow.getChildren().add(new Text(" "));
            }
        }

        List<String> remainingWords = new ArrayList<>();
        if (remainingWordsIndex < controller.getDictionary().size()) {
            remainingWords = controller.getDictionary().subList(remainingWordsIndex, controller.getDictionary().size());
        }

        for (String word : remainingWords) {
            for (int i = 0; i < word.length(); i++) {
                Text characterText = new Text(String.valueOf(word.charAt(i)));
                characterText.setStyle(CHARACTER_UNKNOWN_STYLE);
                wordTextFlow.getChildren().add(characterText);
                characterCount++;

                if (characterCount % wordsPerLine == 0) {
                    wordTextFlow.getChildren().add(new Text("\n"));
                } else {
                    wordTextFlow.getChildren().add(new Text(" "));
                }
            }
        }
    }



    public void addLanguageMenuItem(String languageName, MenuItem menuItem) {
        Menu languageMenu = (Menu) menuBar.getMenus().get(0);
        languageMenu.getItems().add(menuItem);
    }
}
