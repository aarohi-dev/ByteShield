package com.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private String quizId;
    private String title;
    private String category;
    private String difficulty;
    private List<Question> questions;
    private int currentQuestionIndex;
    private List<Integer> userAnswers;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isCompleted;
    private int score;
    private String userId;

    public Quiz() {
        this.questions = new ArrayList<>();
        this.userAnswers = new ArrayList<>();
        this.currentQuestionIndex = 0;
        this.isCompleted = false;
        this.score = 0;
    }

    public Quiz(String quizId, String title, String category, String difficulty, List<Question> questions) {
        this();
        this.quizId = quizId;
        this.title = title;
        this.category = category;
        this.difficulty = difficulty;
        this.questions = questions;
    }

    // Getters and Setters
    public String getQuizId() { return quizId; }
    public void setQuizId(String quizId) { this.quizId = quizId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }

    public int getCurrentQuestionIndex() { return currentQuestionIndex; }
    public void setCurrentQuestionIndex(int currentQuestionIndex) { this.currentQuestionIndex = currentQuestionIndex; }

    public List<Integer> getUserAnswers() { return userAnswers; }
    public void setUserAnswers(List<Integer> userAnswers) { this.userAnswers = userAnswers; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    // Utility methods
    public Question getCurrentQuestion() {
        if (currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex);
        }
        return null;
    }

    public boolean hasNextQuestion() {
        return currentQuestionIndex < questions.size() - 1;
    }

    public boolean hasPreviousQuestion() {
        return currentQuestionIndex > 0;
    }

    public void nextQuestion() {
        if (hasNextQuestion()) {
            currentQuestionIndex++;
        }
    }

    public void previousQuestion() {
        if (hasPreviousQuestion()) {
            currentQuestionIndex--;
        }
    }

    public void answerQuestion(int answerIndex) {
        if (currentQuestionIndex < userAnswers.size()) {
            userAnswers.set(currentQuestionIndex, answerIndex);
        } else {
            userAnswers.add(answerIndex);
        }
    }

    public void startQuiz() {
        this.startTime = LocalDateTime.now();
        this.currentQuestionIndex = 0;
        this.userAnswers.clear();
    }

    public void completeQuiz() {
        this.endTime = LocalDateTime.now();
        this.isCompleted = true;
        calculateScore();
    }

    private void calculateScore() {
        score = 0;
        for (int i = 0; i < questions.size() && i < userAnswers.size(); i++) {
            if (questions.get(i).isCorrect(userAnswers.get(i))) {
                score++;
            }
        }
    }

    public double getPercentageScore() {
        if (questions.isEmpty()) return 0.0;
        return (double) score / questions.size() * 100;
    }

    public long getDurationInMinutes() {
        if (startTime == null || endTime == null) return 0;
        return java.time.Duration.between(startTime, endTime).toMinutes();
    }

    public int getTotalQuestions() {
        return questions.size();
    }

    public List<Question> getIncorrectAnswers() {
        List<Question> incorrect = new ArrayList<>();
        for (int i = 0; i < questions.size() && i < userAnswers.size(); i++) {
            if (!questions.get(i).isCorrect(userAnswers.get(i))) {
                incorrect.add(questions.get(i));
            }
        }
        return incorrect;
    }
}
