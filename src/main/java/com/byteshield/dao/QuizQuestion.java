package com.byteshield.dao;

/**
 * QuizQuestion model class representing a quiz question from the database.
 * This class maps to the quiz_questions table in PostgreSQL.
 */
public class QuizQuestion {
    
    private int id;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private char correctOption;
    private String category;
    private String difficulty;
    private String explanation;
    
    // Default constructor
    public QuizQuestion() {}
    
    // Constructor with all fields
    public QuizQuestion(int id, String question, String optionA, String optionB, 
                       String optionC, String optionD, char correctOption, 
                       String category, String difficulty, String explanation) {
        this.id = id;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctOption = correctOption;
        this.category = category;
        this.difficulty = difficulty;
        this.explanation = explanation;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getQuestion() {
        return question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    
    public String getOptionA() {
        return optionA;
    }
    
    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }
    
    public String getOptionB() {
        return optionB;
    }
    
    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }
    
    public String getOptionC() {
        return optionC;
    }
    
    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }
    
    public String getOptionD() {
        return optionD;
    }
    
    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }
    
    public char getCorrectOption() {
        return correctOption;
    }
    
    public void setCorrectOption(char correctOption) {
        this.correctOption = correctOption;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    
    public String getExplanation() {
        return explanation;
    }
    
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    
    /**
     * Checks if the provided answer is correct.
     * 
     * @param answer The answer to check (A, B, C, or D)
     * @return true if the answer is correct, false otherwise
     */
    public boolean isCorrectAnswer(char answer) {
        return Character.toUpperCase(answer) == Character.toUpperCase(correctOption);
    }
    
    /**
     * Gets the correct option text.
     * 
     * @return The text of the correct option
     */
    public String getCorrectOptionText() {
        return switch (Character.toUpperCase(correctOption)) {
            case 'A' -> optionA;
            case 'B' -> optionB;
            case 'C' -> optionC;
            case 'D' -> optionD;
            default -> "Unknown";
        };
    }
    
    /**
     * Gets the option text by letter.
     * 
     * @param option The option letter (A, B, C, or D)
     * @return The text of the specified option
     */
    public String getOptionText(char option) {
        switch (Character.toUpperCase(option)) {
            case 'A': return optionA;
            case 'B': return optionB;
            case 'C': return optionC;
            case 'D': return optionD;
            default: return "Invalid option";
        }
    }
    
    @Override
    public String toString() {
        return String.format("QuizQuestion{id=%d, question='%s', category='%s', difficulty='%s'}", 
                           id, question, category, difficulty);
    }
}
