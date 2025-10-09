package com.controllers;

import com.utils.JSONLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TipsController {
    
    @FXML
    private VBox tipsContainer;
    
    @FXML
    private ScrollPane scrollPane;
    
    @FXML
    private Button homeButton;
    
    @FXML
    private Button phishingSimulationButton;
    
    @FXML
    private void initialize() {
        loadTips();
    }
    
    private void loadTips() {
        try {
            List<Map<String, Object>> tipsData = JSONLoader.loadTips();
            
            for (Map<String, Object> categoryData : tipsData) {
                String category = (String) categoryData.get("category");
                List<Map<String, Object>> tips = (List<Map<String, Object>>) categoryData.get("tips");
                
                // Create category header
                Label categoryLabel = new Label(category);
                categoryLabel.getStyleClass().add("category-header");
                tipsContainer.getChildren().add(categoryLabel);
                
                // Add tips for this category
                for (Map<String, Object> tipData : tips) {
                    VBox tipBox = new VBox();
                    tipBox.getStyleClass().add("tip-box");
                    
                    String icon = (String) tipData.get("icon");
                    String title = (String) tipData.get("title");
                    String description = (String) tipData.get("description");
                    
                    Label tipTitle = new Label(icon + " " + title);
                    tipTitle.getStyleClass().add("tip-title");
                    
                    Label tipDescription = new Label(description);
                    tipDescription.getStyleClass().add("tip-description");
                    tipDescription.setWrapText(true);
                    
                    tipBox.getChildren().addAll(tipTitle, tipDescription);
                    tipsContainer.getChildren().add(tipBox);
                }
                
                // Add spacing between categories
                Label spacer = new Label("");
                spacer.setPrefHeight(20);
                tipsContainer.getChildren().add(spacer);
            }
        } catch (Exception e) {
            Label errorLabel = new Label("Error loading tips: " + e.getMessage());
            errorLabel.getStyleClass().add("error-message");
            tipsContainer.getChildren().add(errorLabel);
        }
    }
    
    @FXML
    private void onHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/home.fxml"));
            Scene homeScene = new Scene(loader.load(), 1000, 700);
            homeScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            
            Stage stage = (Stage) homeButton.getScene().getWindow();
            stage.setScene(homeScene);
        } catch (IOException e) {
            showAlert("Error loading home screen: " + e.getMessage());
        }
    }
    
    @FXML
    private void onPhishingSimulation() {
        // This would open a phishing simulation mini-game
        showAlert("Phishing simulation feature coming soon!");
    }
    
    private void showAlert(String message) {
        System.out.println("Alert: " + message);
    }
}
