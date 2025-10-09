package com.models;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class UserStats {
    private String userId;
    private String userName;
    private int totalQuizzesTaken;
    private int totalQuestionsAnswered;
    private int correctAnswers;
    private double averageScore;
    private Map<String, Integer> categoryScores;
    private Map<String, Integer> categoryAttempts;
    private LocalDateTime lastQuizDate;
    private int currentStreak;
    private int longestStreak;
    private boolean hasCompletedAllCategories;

    public UserStats() {
        this.categoryScores = new HashMap<>();
        this.categoryAttempts = new HashMap<>();
        this.lastQuizDate = LocalDateTime.now();
    }

    public UserStats(String userId, String userName) {
        this();
        this.userId = userId;
        this.userName = userName;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public int getTotalQuizzesTaken() { return totalQuizzesTaken; }
    public void setTotalQuizzesTaken(int totalQuizzesTaken) { this.totalQuizzesTaken = totalQuizzesTaken; }

    public int getTotalQuestionsAnswered() { return totalQuestionsAnswered; }
    public void setTotalQuestionsAnswered(int totalQuestionsAnswered) { this.totalQuestionsAnswered = totalQuestionsAnswered; }

    public int getCorrectAnswers() { return correctAnswers; }
    public void setCorrectAnswers(int correctAnswers) { this.correctAnswers = correctAnswers; }

    public double getAverageScore() { return averageScore; }
    public void setAverageScore(double averageScore) { this.averageScore = averageScore; }

    public Map<String, Integer> getCategoryScores() { return categoryScores; }
    public void setCategoryScores(Map<String, Integer> categoryScores) { this.categoryScores = categoryScores; }

    public Map<String, Integer> getCategoryAttempts() { return categoryAttempts; }
    public void setCategoryAttempts(Map<String, Integer> categoryAttempts) { this.categoryAttempts = categoryAttempts; }

    public LocalDateTime getLastQuizDate() { return lastQuizDate; }
    public void setLastQuizDate(LocalDateTime lastQuizDate) { this.lastQuizDate = lastQuizDate; }

    public int getCurrentStreak() { return currentStreak; }
    public void setCurrentStreak(int currentStreak) { this.currentStreak = currentStreak; }

    public int getLongestStreak() { return longestStreak; }
    public void setLongestStreak(int longestStreak) { this.longestStreak = longestStreak; }

    public boolean isHasCompletedAllCategories() { return hasCompletedAllCategories; }
    public void setHasCompletedAllCategories(boolean hasCompletedAllCategories) { this.hasCompletedAllCategories = hasCompletedAllCategories; }

    // Utility methods
    public double getOverallAccuracy() {
        if (totalQuestionsAnswered == 0) return 0.0;
        return (double) correctAnswers / totalQuestionsAnswered * 100;
    }

    public void updateStats(String category, int score, int totalQuestions) {
        totalQuizzesTaken++;
        totalQuestionsAnswered += totalQuestions;
        correctAnswers += score;
        averageScore = (double) correctAnswers / totalQuestionsAnswered * 100;
        
        categoryScores.put(category, categoryScores.getOrDefault(category, 0) + score);
        categoryAttempts.put(category, categoryAttempts.getOrDefault(category, 0) + 1);
        
        lastQuizDate = LocalDateTime.now();
    }

    public int getCategoryScore(String category) {
        return categoryScores.getOrDefault(category, 0);
    }

    public int getCategoryAttempts(String category) {
        return categoryAttempts.getOrDefault(category, 0);
    }

    public double getCategoryAccuracy(String category) {
        int attempts = getCategoryAttempts(category);
        if (attempts == 0) return 0.0;
        return (double) getCategoryScore(category) / attempts * 100;
    }
}
