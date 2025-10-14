package com.byteshield;

import com.byteshield.database.DatabaseConnection;
import com.byteshield.ui.QuizPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ByteShield - Cyber Hygiene Trainer
 * Main application class using Swing for the GUI with PostgreSQL database integration.
 * 
 * This application provides cybersecurity education through interactive quizzes
 * and learning materials to help users improve their digital security awareness.
 */
public class ByteShieldApp extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    public ByteShieldApp() {
        initializeGUI();
    }
    
    /**
     * Initialize the GUI components and layout
     */
    private void initializeGUI() {
        // Set frame properties
        setTitle("ByteShield - Cyber Hygiene Trainer");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);
        
        // Create main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        
        // Create welcome panel
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        welcomePanel.setBackground(new Color(248, 249, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Welcome title
        JLabel titleLabel = new JLabel("🛡️ ByteShield");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 10, 20);
        welcomePanel.add(titleLabel, gbc);
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Cyber Hygiene Trainer");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(52, 73, 94));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 20, 20, 20);
        welcomePanel.add(subtitleLabel, gbc);
        
        // Welcome message
        JLabel welcomeLabel = new JLabel("<html><div style='text-align: center;'>" +
            "Welcome to ByteShield!<br>" +
            "Your cybersecurity education companion.<br><br>" +
            "Learn about password security, phishing detection,<br>" +
            "privacy settings, and malware protection.</div></html>");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        welcomeLabel.setForeground(new Color(127, 140, 141));
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 20, 30, 20);
        welcomePanel.add(welcomeLabel, gbc);
        
        // Start button
        JButton startButton = new JButton("Get Started");
        startButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        startButton.setBackground(new Color(52, 152, 219));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.setPreferredSize(new Dimension(120, 35));
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButton.setBackground(new Color(41, 128, 185));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButton.setBackground(new Color(52, 152, 219));
            }
        });
        
        // Add action listener
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startQuiz();
            }
        });
        
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 20, 20, 20);
        welcomePanel.add(startButton, gbc);
        
        // Add welcome panel to main panel
        mainPanel.add(welcomePanel, BorderLayout.CENTER);
        
        // Create footer panel
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(248, 249, 250));
        JLabel footerLabel = new JLabel("Building Cybersecurity Awareness");
        footerLabel.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        footerLabel.setForeground(new Color(149, 165, 166));
        footerPanel.add(footerLabel);
        
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    /**
     * Start the quiz application
     */
    private void startQuiz() {
        // Test database connection first
        if (!DatabaseConnection.testConnection()) {
            JOptionPane.showMessageDialog(this,
                "❌ Cannot connect to database!\n\n" +
                "Please ensure PostgreSQL is running and the database is set up correctly.\n\n" +
                "Connection details:\n" + DatabaseConnection.getConnectionInfo(),
                "Database Connection Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create and show quiz window
        JFrame quizFrame = new JFrame("ByteShield - Cybersecurity Quiz");
        quizFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        quizFrame.setSize(800, 600);
        quizFrame.setLocationRelativeTo(this);
        
        QuizPanel quizPanel = new QuizPanel();
        quizFrame.add(quizPanel);
        
        quizFrame.setVisible(true);
        
        // Hide main window
        this.setVisible(false);
        
        // Show main window when quiz window is closed
        quizFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                ByteShieldApp.this.setVisible(true);
            }
        });
    }
    
    /**
     * Main method to launch the application
     */
    public static void main(String[] args) {
        // Create and show the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ByteShieldApp().setVisible(true);
            }
        });
    }
}