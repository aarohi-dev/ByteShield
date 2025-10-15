package com.byteshield.ui;

import com.byteshield.dao.QuizDAO;
import com.byteshield.dao.QuizQuestion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

/**
 * QuizPanel class provides the main quiz interface for the ByteShield application.
 * This panel displays quiz questions and handles user interactions.
 */
public class QuizPanel extends JPanel {
    
    private QuizDAO quizDAO;
    private List<QuizQuestion> questions;
    private int currentQuestionIndex;
    private int score;
    private int totalQuestions;
    
    // UI Components
    private JLabel questionLabel;
    private JRadioButton optionA, optionB, optionC, optionD;
    private ButtonGroup optionGroup;
    private JButton nextButton, submitButton, backButton;
    private JLabel scoreLabel, questionNumberLabel;
    private JTextArea explanationArea;
    private JPanel questionPanel, buttonPanel, scorePanel;
    
    public QuizPanel() {
        this.quizDAO = new QuizDAO();
        this.currentQuestionIndex = 0;
        this.score = 0;
        this.totalQuestions = 0;
        
        initializeComponents();
        setupLayout();
        loadQuestions();
    }
    
    /**
     * Initialize all UI components
     */
    private void initializeComponents() {
        // Question display
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        questionLabel.setVerticalAlignment(SwingConstants.TOP);
        questionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Radio buttons for options
        optionA = new JRadioButton();
        optionB = new JRadioButton();
        optionC = new JRadioButton();
        optionD = new JRadioButton();
        
        optionGroup = new ButtonGroup();
        optionGroup.add(optionA);
        optionGroup.add(optionB);
        optionGroup.add(optionC);
        optionGroup.add(optionD);
        
        // Buttons
        nextButton = new JButton("Next Question");
        nextButton.setPreferredSize(new Dimension(120, 30));
        nextButton.addActionListener(new NextButtonListener());
        
        submitButton = new JButton("Submit Quiz");
        submitButton.setPreferredSize(new Dimension(120, 30));
        submitButton.addActionListener(new SubmitButtonListener());
        
        backButton = new JButton("Previous");
        backButton.setPreferredSize(new Dimension(120, 30));
        backButton.addActionListener(new BackButtonListener());
        
        // Score and question number labels
        scoreLabel = new JLabel("Score: 0/0");
        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        questionNumberLabel = new JLabel("Question 1 of 0");
        questionNumberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Explanation area
        explanationArea = new JTextArea(3, 40);
        explanationArea.setEditable(false);
        explanationArea.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        explanationArea.setBackground(getBackground());
        explanationArea.setBorder(BorderFactory.createTitledBorder("Explanation"));
        explanationArea.setVisible(false);
    }
    
    /**
     * Setup the layout of the panel
     */
    private void setupLayout() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Score panel at the top
        scorePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        scorePanel.add(scoreLabel);
        scorePanel.add(Box.createHorizontalStrut(20));
        scorePanel.add(questionNumberLabel);
        add(scorePanel, BorderLayout.NORTH);
        
        // Question panel in the center
        questionPanel = new JPanel(new BorderLayout());
        questionPanel.setBorder(BorderFactory.createTitledBorder("Quiz Question"));
        
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        optionsPanel.add(optionA);
        optionsPanel.add(optionB);
        optionsPanel.add(optionC);
        optionsPanel.add(optionD);
        
        questionPanel.add(questionLabel, BorderLayout.NORTH);
        questionPanel.add(optionsPanel, BorderLayout.CENTER);
        questionPanel.add(explanationArea, BorderLayout.SOUTH);
        
        add(questionPanel, BorderLayout.CENTER);
        
        // Button panel at the bottom
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(backButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(nextButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(submitButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Load questions from the database
     */
    private void loadQuestions() {
        try {
            // Ensure sample data exists
            quizDAO.ensureSampleData();
            
            // Load all questions
            questions = quizDAO.getAllQuestions();
            totalQuestions = questions.size();
            
            if (questions.isEmpty()) {
                showErrorMessage("No questions found in the database!");
                return;
            }
            
            System.out.println(" Loaded " + questions.size() + " questions from database");
            displayCurrentQuestion();
            updateQuizUI();
            
        } catch (SQLException e) {
            showErrorMessage("Failed to load questions from database: " + e.getMessage());
            System.err.println("Database error: " + e.getMessage());
        }
    }
    
    /**
     * Display the current question
     */
    private void displayCurrentQuestion() {
        if (questions == null || questions.isEmpty() || currentQuestionIndex >= questions.size()) {
            return;
        }
        
        QuizQuestion question = questions.get(currentQuestionIndex);
        
        // Update question text
        questionLabel.setText("<html><div style='width: 500px;'>" + question.getQuestion() + "</div></html>");
        
        // Update options
        optionA.setText("A) " + question.getOptionA());
        optionB.setText("B) " + question.getOptionB());
        optionC.setText("C) " + question.getOptionC());
        optionD.setText("D) " + question.getOptionD());
        
        // Clear selection
        optionGroup.clearSelection();
        
        // Hide explanation initially
        explanationArea.setVisible(false);
        
        // Update question number
        questionNumberLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + totalQuestions);
    }
    
    /**
     * Update UI elements based on current state
     */
    private void updateQuizUI() {
        // Update score
        scoreLabel.setText("Score: " + score + "/" + totalQuestions);
        
        // Update button states
        backButton.setEnabled(currentQuestionIndex > 0);
        
        if (currentQuestionIndex < totalQuestions - 1) {
            nextButton.setVisible(true);
            submitButton.setVisible(false);
        } else {
            nextButton.setVisible(false);
            submitButton.setVisible(true);
        }
    }
    
    /**
     * Check the current answer and update score
     */
    private void checkAnswer() {
        if (questions == null || currentQuestionIndex >= questions.size()) {
            return;
        }
        
        QuizQuestion question = questions.get(currentQuestionIndex);
        char selectedAnswer = getSelectedAnswer();
        
        if (selectedAnswer != '\0') {
            boolean isCorrect = question.isCorrectAnswer(selectedAnswer);
            
            if (isCorrect) {
                score++;
                System.out.println("Correct answer!");
            } else {
                System.out.println("Incorrect answer. Correct answer: " + question.getCorrectOption());
            }
            
            // Show explanation
            explanationArea.setText(question.getExplanation());
            explanationArea.setVisible(true);
            
            // Disable options after answering
            optionA.setEnabled(false);
            optionB.setEnabled(false);
            optionC.setEnabled(false);
            optionD.setEnabled(false);
        }
    }
    
    /**
     * Get the currently selected answer
     */
    private char getSelectedAnswer() {
        if (optionA.isSelected()) return 'A';
        if (optionB.isSelected()) return 'B';
        if (optionC.isSelected()) return 'C';
        if (optionD.isSelected()) return 'D';
        return '\0';
    }
    
    /**
     * Show final results
     */
    private void showResults() {
        double percentage = (double) score / totalQuestions * 100;
        String grade = getGrade(percentage);
        
        String message = String.format(
            "Quiz Complete!\n\n" +
            "Score: %d/%d (%.1f%%)\n" +
            "Grade: %s\n\n" +
            "Thank you for using ByteShield!",
            score, totalQuestions, percentage, grade
        );
        
        JOptionPane.showMessageDialog(this, message, "Quiz Results", JOptionPane.INFORMATION_MESSAGE);
        
        // Reset quiz
        resetQuiz();
    }
    
    /**
     * Get grade based on percentage
     */
    private String getGrade(double percentage) {
        if (percentage >= 90) return "A+";
        if (percentage >= 80) return "A";
        if (percentage >= 70) return "B";
        if (percentage >= 60) return "C";
        if (percentage >= 50) return "D";
        return "F";
    }
    
    /**
     * Reset the quiz to start over
     */
    private void resetQuiz() {
        currentQuestionIndex = 0;
        score = 0;
        
        // Re-enable options
        optionA.setEnabled(true);
        optionB.setEnabled(true);
        optionC.setEnabled(true);
        optionD.setEnabled(true);
        
        displayCurrentQuestion();
        updateUI();
    }
    
    /**
     * Show error message
     */
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    // Action Listeners
    private class NextButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            checkAnswer();
            currentQuestionIndex++;
            displayCurrentQuestion();
            updateQuizUI();
        }
    }
    
    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            checkAnswer();
            showResults();
        }
    }
    
    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                displayCurrentQuestion();
                updateQuizUI();
            }
        }
    }
}
