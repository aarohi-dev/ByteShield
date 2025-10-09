package com.models;

import java.util.List;

public class Question {
    private String id;
    private String question;
    private List<String> options;
    private int correctAnswer;
    private String explanation;
    private String category;
    private String difficulty;
    private String imagePath;

    public Question() {}

    public Question(String id, String question, List<String> options, int correctAnswer, 
                   String explanation, String category, String difficulty) {
        this.id = id;
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
        this.category = category;
        this.difficulty = difficulty;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }

    public int getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(int correctAnswer) { this.correctAnswer = correctAnswer; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public boolean isCorrect(int selectedAnswer) {
        return selectedAnswer == correctAnswer;
    }
}
