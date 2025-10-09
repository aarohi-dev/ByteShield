package com.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.models.Question;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class JSONLoader {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Question> loadQuestions(String category) {
        try {
            InputStream inputStream = JSONLoader.class.getClassLoader()
                .getResourceAsStream("data/questions.json");
            
            if (inputStream == null) {
                throw new RuntimeException("Could not find questions.json file");
            }

            Map<String, Object> data = objectMapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {});
            List<Map<String, Object>> categories = (List<Map<String, Object>>) data.get("categories");
            
            List<Question> questions = new ArrayList<>();
            
            for (Map<String, Object> categoryData : categories) {
                String categoryName = (String) categoryData.get("name");
                if (categoryName.equals(category)) {
                    List<Map<String, Object>> questionList = (List<Map<String, Object>>) categoryData.get("questions");
                    
                    for (Map<String, Object> questionData : questionList) {
                        Question question = new Question();
                        question.setId((String) questionData.get("id"));
                        question.setQuestion((String) questionData.get("question"));
                        question.setOptions((List<String>) questionData.get("options"));
                        question.setCorrectAnswer((Integer) questionData.get("correctAnswer"));
                        question.setExplanation((String) questionData.get("explanation"));
                        question.setCategory((String) questionData.get("category"));
                        question.setDifficulty((String) questionData.get("difficulty"));
                        
                        questions.add(question);
                    }
                    break;
                }
            }
            
            return questions;
        } catch (IOException e) {
            throw new RuntimeException("Error loading questions: " + e.getMessage(), e);
        }
    }

    public static List<String> getAvailableCategories() {
        try {
            InputStream inputStream = JSONLoader.class.getClassLoader()
                .getResourceAsStream("data/questions.json");
            
            if (inputStream == null) {
                throw new RuntimeException("Could not find questions.json file");
            }

            Map<String, Object> data = objectMapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {});
            List<Map<String, Object>> categories = (List<Map<String, Object>>) data.get("categories");
            
            List<String> categoryNames = new ArrayList<>();
            for (Map<String, Object> categoryData : categories) {
                categoryNames.add((String) categoryData.get("name"));
            }
            
            return categoryNames;
        } catch (IOException e) {
            throw new RuntimeException("Error loading categories: " + e.getMessage(), e);
        }
    }

    public static String getCategoryDescription(String category) {
        try {
            InputStream inputStream = JSONLoader.class.getClassLoader()
                .getResourceAsStream("data/questions.json");
            
            if (inputStream == null) {
                throw new RuntimeException("Could not find questions.json file");
            }

            Map<String, Object> data = objectMapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {});
            List<Map<String, Object>> categories = (List<Map<String, Object>>) data.get("categories");
            
            for (Map<String, Object> categoryData : categories) {
                String categoryName = (String) categoryData.get("name");
                if (categoryName.equals(category)) {
                    return (String) categoryData.get("description");
                }
            }
            
            return "";
        } catch (IOException e) {
            throw new RuntimeException("Error loading category description: " + e.getMessage(), e);
        }
    }

    public static List<Map<String, Object>> loadPhishingScenarios() {
        try {
            InputStream inputStream = JSONLoader.class.getClassLoader()
                .getResourceAsStream("data/phishing_examples.json");
            
            if (inputStream == null) {
                throw new RuntimeException("Could not find phishing_examples.json file");
            }

            Map<String, Object> data = objectMapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {});
            return (List<Map<String, Object>>) data.get("phishingScenarios");
        } catch (IOException e) {
            throw new RuntimeException("Error loading phishing scenarios: " + e.getMessage(), e);
        }
    }

    public static List<Map<String, Object>> loadTips() {
        try {
            InputStream inputStream = JSONLoader.class.getClassLoader()
                .getResourceAsStream("data/tips.json");
            
            if (inputStream == null) {
                throw new RuntimeException("Could not find tips.json file");
            }

            Map<String, Object> data = objectMapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {});
            return (List<Map<String, Object>>) data.get("cybersecurityTips");
        } catch (IOException e) {
            throw new RuntimeException("Error loading tips: " + e.getMessage(), e);
        }
    }
}
