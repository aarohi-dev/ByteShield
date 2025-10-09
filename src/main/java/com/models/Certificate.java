package com.models;

import java.time.LocalDate;

public class Certificate {
    private String certificateId;
    private String userId;
    private String userName;
    private String courseName;
    private LocalDate completionDate;
    private double score;
    private String grade;
    private String category;
    private String certificatePath;
    private boolean isGenerated;

    public Certificate() {
        this.isGenerated = false;
    }

    public Certificate(String certificateId, String userId, String userName, String courseName, 
                      LocalDate completionDate, double score, String category) {
        this();
        this.certificateId = certificateId;
        this.userId = userId;
        this.userName = userName;
        this.courseName = courseName;
        this.completionDate = completionDate;
        this.score = score;
        this.category = category;
        this.grade = calculateGrade(score);
    }

    // Getters and Setters
    public String getCertificateId() { return certificateId; }
    public void setCertificateId(String certificateId) { this.certificateId = certificateId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public LocalDate getCompletionDate() { return completionDate; }
    public void setCompletionDate(LocalDate completionDate) { this.completionDate = completionDate; }

    public double getScore() { return score; }
    public void setScore(double score) { 
        this.score = score; 
        this.grade = calculateGrade(score);
    }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getCertificatePath() { return certificatePath; }
    public void setCertificatePath(String certificatePath) { this.certificatePath = certificatePath; }

    public boolean isGenerated() { return isGenerated; }
    public void setGenerated(boolean generated) { isGenerated = generated; }

    // Utility methods
    private String calculateGrade(double score) {
        if (score >= 90) return "A+";
        else if (score >= 80) return "A";
        else if (score >= 70) return "B+";
        else if (score >= 60) return "B";
        else if (score >= 50) return "C+";
        else if (score >= 40) return "C";
        else return "F";
    }

    public String getGradeDescription() {
        switch (grade) {
            case "A+": return "Excellent - Outstanding performance!";
            case "A": return "Very Good - Great job!";
            case "B+": return "Good - Well done!";
            case "B": return "Satisfactory - Good effort!";
            case "C+": return "Fair - Keep practicing!";
            case "C": return "Pass - Room for improvement";
            default: return "Needs Improvement - Try again!";
        }
    }

    public boolean isPassingGrade() {
        return score >= 60;
    }

    public String getFormattedScore() {
        return String.format("%.1f%%", score);
    }

    public String getFormattedDate() {
        return completionDate.toString();
    }
}
