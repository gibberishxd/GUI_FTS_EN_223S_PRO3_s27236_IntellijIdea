package com.example.gui_fts_en_223s_pro3_s27236_intellijidea;

import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Controller {

    private Stage stage;
    private String dictionaryDirectory;
    private List<String> dictionary;
    private View view;
    private String currentWord;
    private int currentIndex;

    public Controller(Stage stage, String dictionaryDirectory, View view) {
        this.stage = stage;
        this.dictionaryDirectory = dictionaryDirectory;
        this.view = view;
        loadDictionary();
        displayRandomWord();
    }

    public void handleKeyPressed(String key) {
        if (currentWord == null) {
            return;
        }

        String userInput = view.getUserInput();

        if (userInput.length() < currentWord.length()) {
            char currentChar = currentWord.charAt(userInput.length());
            if (key.equals(String.valueOf(currentChar))) {
                view.setCharacterHighlighting(userInput.length(), userInput);
            } else {
                view.setCharacterHighlighting(userInput.length(), userInput);
            }
        } else {
            view.setCharacterHighlighting(userInput.length(), userInput);
        }
    }

    public void handleSpaceKeyPressed() {
        if (currentWord == null) {
            return;
        }

        String userInput = view.getUserInput();

        if (userInput.equals(currentWord)) {
            // Word completed, display a new word
            view.clearUserInput();
            displayRandomWord();
        }
    }

    private void loadDictionary() {
        dictionary = new ArrayList<>();
        File directory = new File(dictionaryDirectory);
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                dictionary.add(line);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void displayRandomWord() {
        if (dictionary.isEmpty()) {
            return;
        }

        Random random = new Random();
        int index = random.nextInt(dictionary.size());
        currentWord = dictionary.get(index);
        currentIndex = 0;
        view.updateInputButtons(currentWord.length());
    }
}