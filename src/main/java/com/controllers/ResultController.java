package com.controllers;

import com.QuizManager;
import com.models.Quiz;
import com.models.Question;
import com.models.Certificate;
import com.models.UserStats;
import com.utils.ChartHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class ResultController {
    
    @FXML
    private Label scoreLabel;
    
    @FXML
    private Label percentageLabel;
    
    @FXML
    private Label gradeLabel;
    
    @FXML
    private Label categoryLabel;
    
    @FXML
    private Label durationLabel;
    
    @FXML
    private Button homeButton;
    
    @FXML
    private Button newQuizButton;
    
    @FXML
    private Button generateCertificateButton;
    
    @FXML
    private Button generateReportButton;
    
    @FXML
    private VBox incorrectAnswersContainer;
    
    @FXML
    private VBox statsContainer;
    
    @FXML
    private Label totalQuizzesLabel;
    
    @FXML
    private Label averageScoreLabel;
    
    @FXML
    private Label currentStreakLabel;
    
    private QuizManager quizManager;
    private Quiz completedQuiz;
    private UserStats userStats;
    
    public void setCompletedQuiz(Quiz quiz) {
        this.completedQuiz = quiz;
        displayResults();
    }
    
    @FXML
    private void initialize() {
        quizManager = QuizManager.getInstance();
        userStats = quizManager.getCurrentUserStats();
        
        // Load overall stats
        loadUserStats();
    }
    
    private void displayResults() {
        if (completedQuiz == null) {
            showAlert("No quiz results to display.");
            return;
        }
        
        // Display quiz results
        scoreLabel.setText("Score: " + completedQuiz.getScore() + "/" + completedQuiz.getTotalQuestions());
        percentageLabel.setText("Percentage: " + String.format("%.1f%%", completedQuiz.getPercentageScore()));
        categoryLabel.setText("Category: " + completedQuiz.getCategory());
        durationLabel.setText("Duration: " + completedQuiz.getDurationInMinutes() + " minutes");
        
        // Determine grade
        double percentage = completedQuiz.getPercentageScore();
        String grade;
        if (percentage >= 90) grade = "A+";
        else if (percentage >= 80) grade = "A";
        else if (percentage >= 70) grade = "B+";
        else if (percentage >= 60) grade = "B";
        else if (percentage >= 50) grade = "C+";
        else if (percentage >= 40) grade = "C";
        else grade = "F";
        
        gradeLabel.setText("Grade: " + grade);
        
        // Display incorrect answers
        displayIncorrectAnswers();
        
        // Show certificate button if passing grade
        generateCertificateButton.setVisible(percentage >= 60);
    }
    
    private void displayIncorrectAnswers() {
        List<Question> incorrectAnswers = completedQuiz.getIncorrectAnswers();
        
        if (incorrectAnswers.isEmpty()) {
            Label perfectLabel = new Label("🎉 Perfect! You got all questions correct!");
            perfectLabel.getStyleClass().add("perfect-score");
            incorrectAnswersContainer.getChildren().add(perfectLabel);
        } else {
            Label titleLabel = new Label("Areas for Improvement:");
            titleLabel.getStyleClass().add("section-title");
            incorrectAnswersContainer.getChildren().add(titleLabel);
            
            for (Question question : incorrectAnswers) {
                VBox questionBox = new VBox();
                questionBox.getStyleClass().add("incorrect-question");
                
                Label questionLabel = new Label("Q: " + question.getQuestion());
                questionLabel.getStyleClass().add("question-text");
                questionLabel.setWrapText(true);
                
                Label explanationLabel = new Label("Explanation: " + question.getExplanation());
                explanationLabel.getStyleClass().add("explanation-text");
                explanationLabel.setWrapText(true);
                
                questionBox.getChildren().addAll(questionLabel, explanationLabel);
                incorrectAnswersContainer.getChildren().add(questionBox);
            }
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
    private void onNewQuiz() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/home.fxml"));
            Scene homeScene = new Scene(loader.load(), 1000, 700);
            homeScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            
            Stage stage = (Stage) newQuizButton.getScene().getWindow();
            stage.setScene(homeScene);
        } catch (IOException e) {
            showAlert("Error loading home screen: " + e.getMessage());
        }
    }
    
    @FXML
    private void onGenerateCertificate() {
        if (completedQuiz != null && completedQuiz.getPercentageScore() >= 60) {
            try {
                Certificate certificate = quizManager.generateCertificate(completedQuiz);
                if (certificate != null) {
                    showAlert("Certificate generated successfully! Check your Downloads folder.");
                } else {
                    showAlert("Error generating certificate.");
                }
            } catch (Exception e) {
                showAlert("Error generating certificate: " + e.getMessage());
            }
        } else {
            showAlert("You need a passing grade (60% or higher) to generate a certificate.");
        }
    }
    
    @FXML
    private void onGenerateReport() {
        if (completedQuiz != null) {
            try {
                String reportPath = quizManager.generateQuizReport(completedQuiz);
                if (reportPath != null) {
                    showAlert("Quiz report generated successfully! Check your Downloads folder.");
                } else {
                    showAlert("Error generating quiz report.");
                }
            } catch (Exception e) {
                showAlert("Error generating quiz report: " + e.getMessage());
            }
        } else {
            showAlert("No quiz data available to generate report.");
        }
    }
    
    private void showAlert(String message) {
        System.out.println("Alert: " + message);
    }
}
