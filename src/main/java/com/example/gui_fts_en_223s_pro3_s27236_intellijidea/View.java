package com.example.gui_fts_en_223s_pro3_s27236_intellijidea;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.List;

class View {
    private BorderPane root;
    private HBox inputBox;
    private List<Button> inputButtons;
    private StringBuilder userInput;

    public View(BorderPane root) {
        this.root = root;
        initialize();
        userInput = new StringBuilder();
    }

    public void updateInputButtons(int length) {
        if (inputButtons.size() < length) {
            for (int i = inputButtons.size(); i < length; i++) {
                Button button = new Button();
                button.setMinSize(30, 30);
                button.setMaxSize(30, 30);
                button.setStyle("-fx-background-color: gray;");
                button.setFont(Font.font(16));
                inputButtons.add(button);
                inputBox.getChildren().add(button);
            }
        } else if (inputButtons.size() > length) {
            for (int i = inputButtons.size() - 1; i >= length; i--) {
                Button button = inputButtons.remove(i);
                inputBox.getChildren().remove(button);
            }
        }
    }

    public void setCharacterHighlighting(int currentIndex, String userInput) {
        for (int i = 0; i < inputButtons.size(); i++) {
            Button button = inputButtons.get(i);
            if (i < currentIndex) {
                button.setStyle("-fx-background-color: green;");
            } else if (i == currentIndex) {
                button.setStyle("-fx-background-color: yellow;");
            } else if (i < userInput.length() && button.getText().equals(String.valueOf(userInput.charAt(i)))) {
                button.setStyle("-fx-background-color: green;");
            } else if (i < userInput.length()) {
                button.setStyle("-fx-background-color: red;");
            } else {
                button.setStyle("-fx-background-color: gray;");
            }
        }
    }

    public String getUserInput() {
        return userInput.toString();
    }

    public void clearUserInput() {
        userInput.setLength(0);
    }

    private void initialize() {
        // Create the input box
        inputBox = new HBox();
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setSpacing(10);
        inputButtons = new ArrayList<>();

        // Add the input box to the root pane
        root.setCenter(inputBox);
    }
}
