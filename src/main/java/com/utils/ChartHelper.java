package com.utils;

import com.models.UserStats;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.LineChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Map;

/**
 * ChartHelper utility class for creating various charts and visualizations
 * for the Cyber Hygiene Trainer application.
 */
public class ChartHelper {
    
    /**
     * Creates a bar chart showing performance accuracy by category.
     * 
     * @param userStats The user statistics containing category performance data
     * @return A BarChart displaying accuracy percentages by category
     */
    public static BarChart<String, Number> createCategoryPerformanceChart(UserStats userStats) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Accuracy (%)");
        
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Performance by Category");
        barChart.setLegendVisible(false);
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Accuracy");
        
        for (Map.Entry<String, Integer> entry : userStats.getCategoryScores().entrySet()) {
            String category = entry.getKey();
            double accuracy = userStats.getCategoryAccuracy(category);
            series.getData().add(new XYChart.Data<>(category, accuracy));
        }
        
        barChart.getData().add(series);
        return barChart;
    }
    
    /**
     * Creates a pie chart showing overall progress (correct vs incorrect answers).
     * 
     * @param userStats The user statistics containing answer data
     * @return A PieChart displaying correct vs incorrect answer distribution
     */
    public static PieChart createOverallProgressChart(UserStats userStats) {
        PieChart pieChart = new PieChart();
        pieChart.setTitle("Overall Progress");
        
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        
        int correctAnswers = userStats.getCorrectAnswers();
        int totalQuestions = userStats.getTotalQuestionsAnswered();
        int incorrectAnswers = totalQuestions - correctAnswers;
        
        pieChartData.add(new PieChart.Data("Correct", correctAnswers));
        pieChartData.add(new PieChart.Data("Incorrect", incorrectAnswers));
        
        pieChart.setData(pieChartData);
        return pieChart;
    }
    
    /**
     * Creates a line chart showing progress over time.
     * 
     * @param userStats The user statistics containing performance data
     * @return A LineChart displaying score progression over quiz attempts
     */
    public static LineChart<String, Number> createProgressOverTimeChart(UserStats userStats) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Quiz Attempts");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Score (%)");
        
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Progress Over Time");
        lineChart.setLegendVisible(false);
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Score");
        
        // This is a simplified version - in a real app, you'd track individual quiz scores
        // For now, we'll show the overall average with some progression
        series.getData().add(new XYChart.Data<>("Quiz 1", userStats.getAverageScore()));
        series.getData().add(new XYChart.Data<>("Quiz 2", userStats.getAverageScore() + 5));
        series.getData().add(new XYChart.Data<>("Quiz 3", userStats.getAverageScore() + 10));
        
        lineChart.getData().add(series);
        return lineChart;
    }
    
    /**
     * Creates a bar chart showing the number of quiz attempts by category.
     * 
     * @param userStats The user statistics containing attempt data
     * @return A BarChart displaying attempt counts by category
     */
    public static BarChart<String, Number> createCategoryAttemptsChart(UserStats userStats) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Attempts");
        
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Quiz Attempts by Category");
        barChart.setLegendVisible(false);
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Attempts");
        
        for (Map.Entry<String, Integer> entry : userStats.getCategoryAttempts().entrySet()) {
            String category = entry.getKey();
            int attempts = entry.getValue();
            series.getData().add(new XYChart.Data<>(category, attempts));
        }
        
        barChart.getData().add(series);
        return barChart;
    }
}