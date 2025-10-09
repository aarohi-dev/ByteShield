package com.controllers;

import com.QuizManager;
import com.Main;
import com.models.UserStats;
import com.utils.ChartHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class HomeController {
    
    @FXML
    private VBox mainContainer;
    
    @FXML
    private Label welcomeLabel;
    
    @FXML
    private TextField userNameField;
    
    @FXML
    private ComboBox<String> categoryComboBox;
    
    @FXML
    private ComboBox<String> difficultyComboBox;
    
    @FXML
    private Button startQuizButton;
    
    @FXML
    private Button viewStatsButton;
    
    @FXML
    private Button viewTipsButton;
    
    @FXML
    private Button phishingSimulationButton;
    
    @FXML
    private VBox statsContainer;
    
    @FXML
    private Label totalQuizzesLabel;
    
    @FXML
    private Label averageScoreLabel;
    
    @FXML
    private Label currentStreakLabel;
    
    private QuizManager quizManager;
    private UserStats userStats;
    
    @FXML
    private void initialize() {
        quizManager = QuizManager.getInstance();
        userStats = quizManager.getCurrentUserStats();
        
        // Initialize category combo box
        List<String> categories = quizManager.getAvailableCategories();
        categoryComboBox.getItems().addAll(categories);
        if (!categories.isEmpty()) {
            categoryComboBox.setValue(categories.get(0));
        }
        
        // Initialize difficulty combo box
        difficultyComboBox.getItems().addAll("All", "Easy", "Medium", "Hard");
        difficultyComboBox.setValue("All");
        
        // Update welcome message
        updateWelcomeMessage();
        
        // Load and display stats
        loadUserStats();
    }
    
    @FXML
    private void onStartQuiz() {
        String category = categoryComboBox.getValue();
        String difficulty = difficultyComboBox.getValue();
        String userName = userNameField.getText().trim();
        
        if (category == null || category.isEmpty()) {
            showAlert("Please select a category");
            return;
        }
        
        if (!userName.isEmpty()) {
            userStats.setUserName(userName);
        }
        
        try {
            // Start the quiz
            quizManager.startQuiz(category, difficulty);
            
            // Load quiz screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/quiz.fxml"));
            Scene quizScene = new Scene(loader.load(), 1000, 700);
            quizScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            
            Stage stage = (Stage) startQuizButton.getScene().getWindow();
            stage.setScene(quizScene);
        } catch (IOException e) {
            showAlert("Error loading quiz screen: " + e.getMessage());
        }
    }
    
    @FXML
    private void onViewStats() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/result.fxml"));
            Scene statsScene = new Scene(loader.load(), 1000, 700);
            statsScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            
            Stage stage = (Stage) viewStatsButton.getScene().getWindow();
            stage.setScene(statsScene);
        } catch (IOException e) {
            showAlert("Error loading stats screen: " + e.getMessage());
        }
    }
    
    @FXML
    private void onViewTips() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/tips.fxml"));
            Scene tipsScene = new Scene(loader.load(), 1000, 700);
            tipsScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            
            Stage stage = (Stage) viewTipsButton.getScene().getWindow();
            stage.setScene(tipsScene);
        } catch (IOException e) {
            showAlert("Error loading tips screen: " + e.getMessage());
        }
    }
    
    @FXML
    private void onPhishingSimulation() {
        // This would open a phishing simulation mini-game
        showAlert("Phishing simulation feature coming soon!");
    }
    
    @FXML
    private void onCategoryChanged() {
        String category = categoryComboBox.getValue();
        if (category != null) {
            String description = quizManager.getCategoryDescription(category);
            // You could display the description in a label or tooltip
        }
    }
    
    private void updateWelcomeMessage() {
        String userName = userStats.getUserName();
        if (userName != null && !userName.isEmpty()) {
            welcomeLabel.setText("Welcome back, " + userName + "!");
        } else {
            welcomeLabel.setText("Welcome to Cyber Hygiene Trainer!");
        }
    }
    
    private void loadUserStats() {
        totalQuizzesLabel.setText("Total Quizzes: " + userStats.getTotalQuizzesTaken());
        averageScoreLabel.setText("Average Score: " + String.format("%.1f%%", userStats.getAverageScore()));
        currentStreakLabel.setText("Current Streak: " + userStats.getCurrentStreak());
        
        // Add charts if there's data
        if (userStats.getTotalQuizzesTaken() > 0) {
            addStatsCharts();
        }
    }
    
    private void addStatsCharts() {
        try {
            // Add performance chart
            BarChart<String, Number> performanceChart = ChartHelper.createCategoryPerformanceChart(userStats);
            performanceChart.setPrefHeight(200);
            statsContainer.getChildren().add(performanceChart);
            
            // Add progress chart
            PieChart progressChart = ChartHelper.createOverallProgressChart(userStats);
            progressChart.setPrefHeight(200);
            statsContainer.getChildren().add(progressChart);
        } catch (Exception e) {
            System.err.println("Error creating charts: " + e.getMessage());
        }
    }
    
    private void showAlert(String message) {
        // Simple alert implementation - in a real app you'd use Alert dialog
        System.out.println("Alert: " + message);
    }
}
