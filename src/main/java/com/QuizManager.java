package com;

import com.models.Quiz;
import com.models.Question;
import com.models.UserStats;
import com.models.Certificate;
import com.utils.JSONLoader;
import com.utils.PdfGenerator;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class QuizManager {
    private static QuizManager instance;
    private UserStats currentUserStats;
    private Quiz currentQuiz;
    
    private QuizManager() {
        this.currentUserStats = new UserStats("default_user", "User");
    }
    
    public static QuizManager getInstance() {
        if (instance == null) {
            instance = new QuizManager();
        }
        return instance;
    }
    
    public List<String> getAvailableCategories() {
        return JSONLoader.getAvailableCategories();
    }
    
    public String getCategoryDescription(String category) {
        return JSONLoader.getCategoryDescription(category);
    }
    
    public Quiz startQuiz(String category, String difficulty) {
        List<Question> questions = JSONLoader.loadQuestions(category);
        
        // Filter questions by difficulty if specified
        if (!difficulty.equals("All")) {
            questions = questions.stream()
                .filter(q -> q.getDifficulty().equals(difficulty))
                .collect(java.util.stream.Collectors.toList());
        }
        
        // Shuffle questions and take up to 10
        Random random = new Random();
        java.util.Collections.shuffle(questions, random);
        if (questions.size() > 10) {
            questions = questions.subList(0, 10);
        }
        
        String quizId = UUID.randomUUID().toString();
        String title = category + " Quiz";
        
        currentQuiz = new Quiz(quizId, title, category, difficulty, questions);
        currentQuiz.setUserId(currentUserStats.getUserId());
        currentQuiz.startQuiz();
        
        return currentQuiz;
    }
    
    public void answerQuestion(int answerIndex) {
        if (currentQuiz != null) {
            currentQuiz.answerQuestion(answerIndex);
        }
    }
    
    public void nextQuestion() {
        if (currentQuiz != null) {
            currentQuiz.nextQuestion();
        }
    }
    
    public void previousQuestion() {
        if (currentQuiz != null) {
            currentQuiz.previousQuestion();
        }
    }
    
    public Quiz completeQuiz() {
        if (currentQuiz != null) {
            currentQuiz.completeQuiz();
            
            // Update user stats
            currentUserStats.updateStats(
                currentQuiz.getCategory(),
                currentQuiz.getScore(),
                currentQuiz.getTotalQuestions()
            );
            
            return currentQuiz;
        }
        return null;
    }
    
    public Quiz getCurrentQuiz() {
        return currentQuiz;
    }
    
    public UserStats getCurrentUserStats() {
        return currentUserStats;
    }
    
    public void setCurrentUserStats(UserStats userStats) {
        this.currentUserStats = userStats;
    }
    
    public Certificate generateCertificate(Quiz quiz) {
        if (quiz == null || !quiz.isCompleted()) {
            return null;
        }
        
        String certificateId = UUID.randomUUID().toString();
        Certificate certificate = new Certificate(
            certificateId,
            currentUserStats.getUserId(),
            currentUserStats.getUserName(),
            quiz.getTitle(),
            LocalDate.now(),
            quiz.getPercentageScore(),
            quiz.getCategory()
        );
        
        // Generate PDF certificate
        try {
            String filePath = PdfGenerator.generateCertificate(certificate);
            System.out.println("Certificate generated: " + filePath);
        } catch (Exception e) {
            System.err.println("Error generating certificate: " + e.getMessage());
        }
        
        return certificate;
    }
    
    public String generateQuizReport(Quiz quiz) {
        if (quiz == null || !quiz.isCompleted()) {
            return null;
        }
        
        try {
            return PdfGenerator.generateQuizReport(quiz, currentUserStats);
        } catch (Exception e) {
            System.err.println("Error generating quiz report: " + e.getMessage());
            return null;
        }
    }
    
    public List<Question> getQuestionsForCategory(String category) {
        return JSONLoader.loadQuestions(category);
    }
    
    public List<Question> getQuestionsForCategoryAndDifficulty(String category, String difficulty) {
        List<Question> questions = JSONLoader.loadQuestions(category);
        
        if (!difficulty.equals("All")) {
            questions = questions.stream()
                .filter(q -> q.getDifficulty().equals(difficulty))
                .collect(java.util.stream.Collectors.toList());
        }
        
        return questions;
    }
    
    public void resetCurrentQuiz() {
        currentQuiz = null;
    }
}
