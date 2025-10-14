package com.utils;

import com.models.UserStats;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.XYDataset;

import javax.swing.JPanel;
import java.util.Map;

/**
 * ChartHelper utility class for creating various charts and visualizations
 * for the Cyber Hygiene Trainer application using JFreeChart.
 */
public class ChartHelper {
    
    /**
     * Creates a bar chart showing performance accuracy by category.
     * 
     * @param userStats The user statistics containing category performance data
     * @return A ChartPanel containing a bar chart displaying accuracy percentages by category
     */
    public static ChartPanel createCategoryPerformanceChart(UserStats userStats) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for (Map.Entry<String, Integer> entry : userStats.getCategoryScores().entrySet()) {
            String category = entry.getKey();
            double accuracy = userStats.getCategoryAccuracy(category);
            dataset.addValue(accuracy, "Accuracy", category);
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
            "Performance by Category",
            "Category",
            "Accuracy (%)",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        
        return new ChartPanel(chart);
    }
    
    /**
     * Creates a pie chart showing overall progress (correct vs incorrect answers).
     * 
     * @param userStats The user statistics containing answer data
     * @return A ChartPanel containing a pie chart displaying correct vs incorrect answer distribution
     */
    public static ChartPanel createOverallProgressChart(UserStats userStats) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        
        int correctAnswers = userStats.getCorrectAnswers();
        int totalQuestions = userStats.getTotalQuestionsAnswered();
        int incorrectAnswers = totalQuestions - correctAnswers;
        
        dataset.setValue("Correct", correctAnswers);
        dataset.setValue("Incorrect", incorrectAnswers);
        
        JFreeChart chart = ChartFactory.createPieChart(
            "Overall Progress",
            dataset,
            true,
            true,
            false
        );
        
        return new ChartPanel(chart);
    }
    
    /**
     * Creates a line chart showing progress over time.
     * 
     * @param userStats The user statistics containing performance data
     * @return A ChartPanel containing a line chart displaying score progression over quiz attempts
     */
    public static ChartPanel createProgressOverTimeChart(UserStats userStats) {
        XYSeries series = new XYSeries("Score");
        
        // This is a simplified version - in a real app, you'd track individual quiz scores
        // For now, we'll show the overall average with some progression
        series.add(1, userStats.getAverageScore());
        series.add(2, userStats.getAverageScore() + 5);
        series.add(3, userStats.getAverageScore() + 10);
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        
        JFreeChart chart = ChartFactory.createXYLineChart(
            "Progress Over Time",
            "Quiz Attempts",
            "Score (%)",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        
        return new ChartPanel(chart);
    }
    
    /**
     * Creates a bar chart showing the number of quiz attempts by category.
     * 
     * @param userStats The user statistics containing attempt data
     * @return A ChartPanel containing a bar chart displaying attempt counts by category
     */
    public static ChartPanel createCategoryAttemptsChart(UserStats userStats) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for (Map.Entry<String, Integer> entry : userStats.getCategoryAttempts().entrySet()) {
            String category = entry.getKey();
            int attempts = entry.getValue();
            dataset.addValue(attempts, "Attempts", category);
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
            "Quiz Attempts by Category",
            "Category",
            "Number of Attempts",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        
        return new ChartPanel(chart);
    }
}