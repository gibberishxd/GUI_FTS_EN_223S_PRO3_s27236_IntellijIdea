package com.example.gui_fts_en_223s_pro3_s27236_intellijidea;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller {

    private Stage stage; // Reference to the stage
    private String dictionaryDirectory; // Directory path for the dictionary files
    private List<String> dictionary; // Holds the loaded dictionary words
    private View view;
    private int currentWordIndex = 0;

    public Controller(Stage stage, String dictionaryDirectory, View view) {
        this.stage = stage;
        this.dictionaryDirectory = dictionaryDirectory;
        this.view = view;
        this.view.setController(this);
        stage.setTitle("Typing Test");
        stage.setResizable(false);
        populateLanguageMenu();
    }

    public List<String> getDictionary() {
        return dictionary;
    }

    public int getCurrentWordIndex() {
        return currentWordIndex;
    }

    private void populateLanguageMenu() {
        File dictionaryDirectory = new File(this.dictionaryDirectory);
        File[] languageFiles = dictionaryDirectory.listFiles();

        if (languageFiles != null) {
            for (File languageFile : languageFiles) {
                String fileName = languageFile.getName();
                String languageName = fileName.substring(0, fileName.lastIndexOf('.'));
                MenuItem languageMenuItem = new MenuItem(languageName);
                languageMenuItem.setOnAction(this::onLanguageMenuItemClick);
                view.addLanguageMenuItem(languageName, languageMenuItem);
            }
        }
    }

    public String getExpectedWord() {
        if (dictionary != null && !dictionary.isEmpty()) {
            if (currentWordIndex < dictionary.size()) {
                return dictionary.get(currentWordIndex);
            }
        }
        return ""; // Return an empty string if there are no more words in the dictionary
    }

    public void handleCorrectInput() {
        currentWordIndex++;
        displayNextWord();
    }

    public void handleIncorrectInput() {
        // Perform actions for incorrect input
    }

    private void displayNextWord() {
        if (dictionary != null && !dictionary.isEmpty()) {
            List<String> nextWord = new ArrayList<>();
            if (currentWordIndex < dictionary.size()) {
                nextWord.add(dictionary.get(currentWordIndex));
            }
            view.setWords(nextWord); // Call the method in the View class to update the word display
        }
    }

    public void onLanguageMenuItemClick(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        String languageName = menuItem.getText();
        loadDictionary(languageName);
        System.out.println("Language selected: " + languageName);
        displayRandomWords(); // Call the method to display a random word
    }


    private void loadDictionary(String languageName) {
        String filePath = dictionaryDirectory + "/" + languageName + ".txt";

        try {
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            dictionary = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                dictionary.add(line);
            }

            reader.close();

            // Test: Print the loaded words
            System.out.println("Dictionary for " + languageName + ":");
            for (String word : dictionary) {
                System.out.println(word);
            }
        } catch (IOException e) {
            System.out.println("Error loading dictionary: " + e.getMessage());
        }
    }

    private void displayRandomWords() {
        if (dictionary != null && !dictionary.isEmpty()) {
            Random random = new Random();
            int numWords = 30; // Change this to the desired number of words to display
            List<String> randomWords = new ArrayList<>();

            for (int i = 0; i < numWords; i++) {
                int index = random.nextInt(dictionary.size());
                String word = dictionary.get(index);
                randomWords.add(word);
            }

            view.setWords(randomWords); // Call the method in the View class to update the words
        }
    }
}