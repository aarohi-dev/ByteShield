package com.controllers;

import com.QuizManager;
import com.models.Quiz;
import com.models.Question;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class QuizController {
    
    @FXML
    private Label questionLabel;
    
    @FXML
    private Label questionNumberLabel;
    
    @FXML
    private VBox optionsContainer;
    
    @FXML
    private Button nextButton;
    
    @FXML
    private Button previousButton;
    
    @FXML
    private Button submitButton;
    
    @FXML
    private Button homeButton;
    
    @FXML
    private Label categoryLabel;
    
    @FXML
    private Label difficultyLabel;
    
    private QuizManager quizManager;
    private Quiz currentQuiz;
    private ToggleGroup answerGroup;
    private RadioButton[] optionButtons;
    
    @FXML
    private void initialize() {
        quizManager = QuizManager.getInstance();
        currentQuiz = quizManager.getCurrentQuiz();
        
        if (currentQuiz == null) {
            showAlert("No active quiz found. Please start a quiz from the home screen.");
            return;
        }
        
        answerGroup = new ToggleGroup();
        
        // Set quiz info
        categoryLabel.setText("Category: " + currentQuiz.getCategory());
        difficultyLabel.setText("Difficulty: " + currentQuiz.getDifficulty());
        
        // Display first question
        displayCurrentQuestion();
    }
    
    @FXML
    private void onNext() {
        saveCurrentAnswer();
        quizManager.nextQuestion();
        displayCurrentQuestion();
    }
    
    @FXML
    private void onPrevious() {
        saveCurrentAnswer();
        quizManager.previousQuestion();
        displayCurrentQuestion();
    }
    
    @FXML
    private void onSubmit() {
        saveCurrentAnswer();
        
        // Complete the quiz
        Quiz completedQuiz = quizManager.completeQuiz();
        
        try {
            // Load results screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/result.fxml"));
            Scene resultScene = new Scene(loader.load(), 1000, 700);
            resultScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            
            // Pass the completed quiz to the result controller
            ResultController resultController = loader.getController();
            resultController.setCompletedQuiz(completedQuiz);
            
            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.setScene(resultScene);
        } catch (IOException e) {
            showAlert("Error loading results screen: " + e.getMessage());
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
    
    private void displayCurrentQuestion() {
        Question question = currentQuiz.getCurrentQuestion();
        if (question == null) {
            showAlert("No more questions available.");
            return;
        }
        
        // Update question number and text
        int currentIndex = currentQuiz.getCurrentQuestionIndex();
        int totalQuestions = currentQuiz.getTotalQuestions();
        questionNumberLabel.setText("Question " + (currentIndex + 1) + " of " + totalQuestions);
        questionLabel.setText(question.getQuestion());
        
        // Clear previous options
        optionsContainer.getChildren().clear();
        
        // Create radio buttons for options
        optionButtons = new RadioButton[question.getOptions().size()];
        for (int i = 0; i < question.getOptions().size(); i++) {
            RadioButton radioButton = new RadioButton(question.getOptions().get(i));
            radioButton.setToggleGroup(answerGroup);
            radioButton.setWrapText(true);
            optionButtons[i] = radioButton;
            optionsContainer.getChildren().add(radioButton);
        }
        
        // Check if user already answered this question
        if (currentIndex < currentQuiz.getUserAnswers().size()) {
            Integer userAnswer = currentQuiz.getUserAnswers().get(currentIndex);
            if (userAnswer != null && userAnswer >= 0 && userAnswer < optionButtons.length) {
                optionButtons[userAnswer].setSelected(true);
            }
        }
        
        // Update button states
        updateButtonStates();
    }
    
    private void saveCurrentAnswer() {
        if (answerGroup.getSelectedToggle() != null) {
            RadioButton selectedButton = (RadioButton) answerGroup.getSelectedToggle();
            int selectedIndex = -1;
            
            for (int i = 0; i < optionButtons.length; i++) {
                if (optionButtons[i] == selectedButton) {
                    selectedIndex = i;
                    break;
                }
            }
            
            if (selectedIndex >= 0) {
                currentQuiz.answerQuestion(selectedIndex);
            }
        }
    }
    
    private void updateButtonStates() {
        int currentIndex = currentQuiz.getCurrentQuestionIndex();
        int totalQuestions = currentQuiz.getTotalQuestions();
        
        // Previous button
        previousButton.setDisable(!currentQuiz.hasPreviousQuestion());
        
        // Next button
        if (currentIndex == totalQuestions - 1) {
            nextButton.setVisible(false);
            submitButton.setVisible(true);
        } else {
            nextButton.setVisible(true);
            submitButton.setVisible(false);
        }
    }
    
    private void showAlert(String message) {
        System.out.println("Alert: " + message);
    }
}
