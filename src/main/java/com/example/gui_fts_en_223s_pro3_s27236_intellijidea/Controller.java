package com.example.gui_fts_en_223s_pro3_s27236_intellijidea;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Stage stage; // Reference to the stage
    private String dictionaryDirectory; // Directory path for the dictionary files
    private List<String> dictionary; // Holds the loaded dictionary words

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDictionaryDirectory(String dictionaryDirectory) {
        this.dictionaryDirectory = dictionaryDirectory;
    }

    public List<String> getDictionary() {
        return dictionary;
    }

    public void setDictionary(List<String> dictionary) {
        this.dictionary = dictionary;
    }

    @FXML
    public void onLanguageMenuItemClick(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        String languageName = menuItem.getText();
        loadDictionary(languageName);
        System.out.println("Language selected: " + languageName);
    }

    private void loadDictionary(String languageName) {
        // Construct the file path based on the selected language name
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization code here
    }
}
