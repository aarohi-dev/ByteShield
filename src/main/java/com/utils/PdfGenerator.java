package com.utils;

import com.models.Certificate;
import com.models.Quiz;
import com.models.UserStats;
import com.models.Question;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.io.font.constants.StandardFonts;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * PdfGenerator utility class for creating PDF documents including
 * certificates and quiz reports for the Cyber Hygiene Trainer application.
 */
public class PdfGenerator {
    
    /**
     * Generates a professional PDF certificate for completed quizzes.
     * 
     * @param certificate The certificate data containing user and quiz information
     * @return The file path where the certificate was saved
     * @throws RuntimeException if there's an error generating the PDF
     */
    public static String generateCertificate(Certificate certificate) {
        try {
            String fileName = "certificate_" + certificate.getCertificateId() + ".pdf";
            String filePath = System.getProperty("user.home") + "/Downloads/" + fileName;
            
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            // Create fonts
            PdfFont titleFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont headerFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont bodyFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            
            // Title
            Paragraph title = new Paragraph("CYBER HYGIENE TRAINING CERTIFICATE")
                .setFont(titleFont)
                .setFontSize(24)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(30);
            document.add(title);
            
            // Certificate content
            Paragraph certificateText = new Paragraph("This certifies that")
                .setFont(bodyFont)
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10);
            document.add(certificateText);
            
            Paragraph name = new Paragraph(certificate.getUserName())
                .setFont(headerFont)
                .setFontSize(20)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
            document.add(name);
            
            Paragraph completion = new Paragraph("has successfully completed the")
                .setFont(bodyFont)
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10);
            document.add(completion);
            
            Paragraph courseName = new Paragraph(certificate.getCourseName())
                .setFont(headerFont)
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
            document.add(courseName);
            
            // Results table
            Table resultsTable = new Table(2);
            resultsTable.setWidth(300);
            resultsTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
            resultsTable.setMarginBottom(30);
            
            resultsTable.addCell(new Cell().add(new Paragraph("Score:").setFont(headerFont)));
            resultsTable.addCell(new Cell().add(new Paragraph(certificate.getFormattedScore()).setFont(bodyFont)));
            
            resultsTable.addCell(new Cell().add(new Paragraph("Grade:").setFont(headerFont)));
            resultsTable.addCell(new Cell().add(new Paragraph(certificate.getGrade()).setFont(bodyFont)));
            
            resultsTable.addCell(new Cell().add(new Paragraph("Category:").setFont(headerFont)));
            resultsTable.addCell(new Cell().add(new Paragraph(certificate.getCategory()).setFont(bodyFont)));
            
            resultsTable.addCell(new Cell().add(new Paragraph("Date:").setFont(headerFont)));
            resultsTable.addCell(new Cell().add(new Paragraph(certificate.getFormattedDate()).setFont(bodyFont)));
            
            document.add(resultsTable);
            
            // Grade description
            Paragraph gradeDescription = new Paragraph(certificate.getGradeDescription())
                .setFont(bodyFont)
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(30);
            document.add(gradeDescription);
            
            // Footer
            Paragraph footer = new Paragraph("Cyber Hygiene Trainer - ByteShield")
                .setFont(bodyFont)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(50);
            document.add(footer);
            
            document.close();
            
            certificate.setCertificatePath(filePath);
            certificate.setGenerated(true);
            
            return filePath;
        } catch (IOException e) {
            throw new RuntimeException("Error generating certificate: " + e.getMessage(), e);
        }
    }
    
    /**
     * Generates a detailed PDF report for a completed quiz.
     * 
     * @param quiz The completed quiz data
     * @param userStats The user statistics (currently unused but available for future enhancements)
     * @return The file path where the report was saved
     * @throws RuntimeException if there's an error generating the PDF
     */
    public static String generateQuizReport(Quiz quiz, UserStats userStats) {
        try {
            String fileName = "quiz_report_" + quiz.getQuizId() + ".pdf";
            String filePath = System.getProperty("user.home") + "/Downloads/" + fileName;
            
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            // Create fonts
            PdfFont titleFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont headerFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont bodyFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            
            // Title
            Paragraph title = new Paragraph("QUIZ REPORT")
                .setFont(titleFont)
                .setFontSize(20)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
            document.add(title);
            
            // Quiz details
            Paragraph quizTitle = new Paragraph(quiz.getTitle())
                .setFont(headerFont)
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
            document.add(quizTitle);
            
            // Results table
            Table resultsTable = new Table(2);
            resultsTable.setWidth(400);
            resultsTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
            resultsTable.setMarginBottom(30);
            
            resultsTable.addCell(new Cell().add(new Paragraph("Category:").setFont(headerFont)));
            resultsTable.addCell(new Cell().add(new Paragraph(quiz.getCategory()).setFont(bodyFont)));
            
            resultsTable.addCell(new Cell().add(new Paragraph("Score:").setFont(headerFont)));
            resultsTable.addCell(new Cell().add(new Paragraph(quiz.getScore() + "/" + quiz.getTotalQuestions()).setFont(bodyFont)));
            
            resultsTable.addCell(new Cell().add(new Paragraph("Percentage:").setFont(headerFont)));
            resultsTable.addCell(new Cell().add(new Paragraph(String.format("%.1f%%", quiz.getPercentageScore())).setFont(bodyFont)));
            
            resultsTable.addCell(new Cell().add(new Paragraph("Duration:").setFont(headerFont)));
            resultsTable.addCell(new Cell().add(new Paragraph(quiz.getDurationInMinutes() + " minutes").setFont(bodyFont)));
            
            resultsTable.addCell(new Cell().add(new Paragraph("Date:").setFont(headerFont)));
            resultsTable.addCell(new Cell().add(new Paragraph(quiz.getEndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))).setFont(bodyFont)));
            
            document.add(resultsTable);
            
            // Incorrect answers section
            if (!quiz.getIncorrectAnswers().isEmpty()) {
                Paragraph incorrectTitle = new Paragraph("Areas for Improvement:")
                    .setFont(headerFont)
                    .setFontSize(14)
                    .setMarginTop(20)
                    .setMarginBottom(10);
                document.add(incorrectTitle);
                
                for (Question question : quiz.getIncorrectAnswers()) {
                    Paragraph questionText = new Paragraph("Q: " + question.getQuestion())
                        .setFont(bodyFont)
                        .setFontSize(12)
                        .setMarginBottom(5);
                    document.add(questionText);
                    
                    Paragraph explanation = new Paragraph("Explanation: " + question.getExplanation())
                        .setFont(bodyFont)
                        .setFontSize(10)
                        .setMarginBottom(15);
                    document.add(explanation);
                }
            }
            
            document.close();
            return filePath;
        } catch (IOException e) {
            throw new RuntimeException("Error generating quiz report: " + e.getMessage(), e);
        }
    }
}