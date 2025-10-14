package com.byteshield.dao;

import com.byteshield.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * QuizDAO (Data Access Object) class for handling quiz question database operations.
 * This class provides methods to interact with the quiz_questions table.
 */
public class QuizDAO {
    
    private static final String SELECT_ALL_QUESTIONS = 
        "SELECT id, question, option_a, option_b, option_c, option_d, " +
        "correct_option, category, difficulty, explanation " +
        "FROM quiz_questions ORDER BY id";
    
    private static final String SELECT_QUESTIONS_BY_CATEGORY = 
        "SELECT id, question, option_a, option_b, option_c, option_d, " +
        "correct_option, category, difficulty, explanation " +
        "FROM quiz_questions WHERE category = ? ORDER BY id";
    
    private static final String SELECT_QUESTIONS_BY_DIFFICULTY = 
        "SELECT id, question, option_a, option_b, option_c, option_d, " +
        "correct_option, category, difficulty, explanation " +
        "FROM quiz_questions WHERE difficulty = ? ORDER BY id";
    
    private static final String COUNT_QUESTIONS = 
        "SELECT COUNT(*) FROM quiz_questions";
    
    private static final String INSERT_QUESTION = 
        "INSERT INTO quiz_questions (question, option_a, option_b, option_c, option_d, " +
        "correct_option, category, difficulty, explanation) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    /**
     * Retrieves all quiz questions from the database.
     * 
     * @return List of QuizQuestion objects
     * @throws SQLException if database operation fails
     */
    public List<QuizQuestion> getAllQuestions() throws SQLException {
        List<QuizQuestion> questions = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUESTIONS);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                QuizQuestion question = mapResultSetToQuizQuestion(resultSet);
                questions.add(question);
            }
            
            System.out.println("✅ Retrieved " + questions.size() + " questions from database");
        }
        
        return questions;
    }
    
    /**
     * Retrieves quiz questions by category.
     * 
     * @param category The category to filter by
     * @return List of QuizQuestion objects in the specified category
     * @throws SQLException if database operation fails
     */
    public List<QuizQuestion> getQuestionsByCategory(String category) throws SQLException {
        List<QuizQuestion> questions = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_QUESTIONS_BY_CATEGORY)) {
            
            statement.setString(1, category);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    QuizQuestion question = mapResultSetToQuizQuestion(resultSet);
                    questions.add(question);
                }
            }
            
            System.out.println("✅ Retrieved " + questions.size() + " questions for category: " + category);
        }
        
        return questions;
    }
    
    /**
     * Retrieves quiz questions by difficulty level.
     * 
     * @param difficulty The difficulty level to filter by
     * @return List of QuizQuestion objects with the specified difficulty
     * @throws SQLException if database operation fails
     */
    public List<QuizQuestion> getQuestionsByDifficulty(String difficulty) throws SQLException {
        List<QuizQuestion> questions = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_QUESTIONS_BY_DIFFICULTY)) {
            
            statement.setString(1, difficulty);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    QuizQuestion question = mapResultSetToQuizQuestion(resultSet);
                    questions.add(question);
                }
            }
            
            System.out.println("✅ Retrieved " + questions.size() + " questions for difficulty: " + difficulty);
        }
        
        return questions;
    }
    
    /**
     * Gets the total number of questions in the database.
     * 
     * @return The count of questions
     * @throws SQLException if database operation fails
     */
    public int getQuestionCount() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_QUESTIONS);
             ResultSet resultSet = statement.executeQuery()) {
            
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                System.out.println("📊 Total questions in database: " + count);
                return count;
            }
        }
        
        return 0;
    }
    
    /**
     * Inserts a new quiz question into the database.
     * 
     * @param question The QuizQuestion object to insert
     * @return The generated ID of the inserted question
     * @throws SQLException if database operation fails
     */
    public int insertQuestion(QuizQuestion question) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_QUESTION, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, question.getQuestion());
            statement.setString(2, question.getOptionA());
            statement.setString(3, question.getOptionB());
            statement.setString(4, question.getOptionC());
            statement.setString(5, question.getOptionD());
            statement.setString(6, String.valueOf(question.getCorrectOption()));
            statement.setString(7, question.getCategory());
            statement.setString(8, question.getDifficulty());
            statement.setString(9, question.getExplanation());
            
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newId = generatedKeys.getInt(1);
                        System.out.println("✅ Inserted new question with ID: " + newId);
                        return newId;
                    }
                }
            }
        }
        
        throw new SQLException("Failed to insert question");
    }
    
    /**
     * Checks if the database has any questions and inserts sample data if empty.
     * 
     * @throws SQLException if database operation fails
     */
    public void ensureSampleData() throws SQLException {
        int count = getQuestionCount();
        
        if (count == 0) {
            System.out.println("📝 Database is empty, inserting sample questions...");
            insertSampleQuestions();
        } else {
            System.out.println("✅ Database already contains " + count + " questions");
        }
    }
    
    /**
     * Inserts sample quiz questions into the database.
     * 
     * @throws SQLException if database operation fails
     */
    private void insertSampleQuestions() throws SQLException {
        List<QuizQuestion> sampleQuestions = createSampleQuestions();
        
        for (QuizQuestion question : sampleQuestions) {
            insertQuestion(question);
        }
        
        System.out.println("✅ Inserted " + sampleQuestions.size() + " sample questions");
    }
    
    /**
     * Creates a list of sample quiz questions.
     * 
     * @return List of sample QuizQuestion objects
     */
    private List<QuizQuestion> createSampleQuestions() {
        List<QuizQuestion> questions = new ArrayList<>();
        
        questions.add(new QuizQuestion(0, 
            "What is the most secure way to create a password?",
            "Use your pet's name and birth year",
            "Use a combination of uppercase, lowercase, numbers, and special characters",
            "Use the same password for all accounts",
            "Write it down on a sticky note",
            'B', "Password Security", "Easy",
            "Strong passwords should include a mix of character types and be unique for each account."));
        
        questions.add(new QuizQuestion(0,
            "Which of the following is a sign of a phishing email?",
            "Professional formatting and correct spelling",
            "Urgent requests for personal information",
            "Clear sender identification",
            "Relevant content to your interests",
            'B', "Phishing Detection", "Medium",
            "Phishing emails often create urgency and request sensitive information to trick users."));
        
        questions.add(new QuizQuestion(0,
            "What should you do if you receive a suspicious email?",
            "Click on all links to verify authenticity",
            "Reply with your personal information",
            "Delete the email and report it as spam",
            "Forward it to all your contacts",
            'C', "Phishing Detection", "Easy",
            "Suspicious emails should be deleted and reported to avoid potential security threats."));
        
        return questions;
    }
    
    /**
     * Maps a ResultSet row to a QuizQuestion object.
     * 
     * @param resultSet The ResultSet containing the question data
     * @return QuizQuestion object
     * @throws SQLException if database operation fails
     */
    private QuizQuestion mapResultSetToQuizQuestion(ResultSet resultSet) throws SQLException {
        QuizQuestion question = new QuizQuestion();
        question.setId(resultSet.getInt("id"));
        question.setQuestion(resultSet.getString("question"));
        question.setOptionA(resultSet.getString("option_a"));
        question.setOptionB(resultSet.getString("option_b"));
        question.setOptionC(resultSet.getString("option_c"));
        question.setOptionD(resultSet.getString("option_d"));
        question.setCorrectOption(resultSet.getString("correct_option").charAt(0));
        question.setCategory(resultSet.getString("category"));
        question.setDifficulty(resultSet.getString("difficulty"));
        question.setExplanation(resultSet.getString("explanation"));
        
        return question;
    }
}
