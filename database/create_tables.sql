-- ByteShield Database Schema
-- PostgreSQL database setup for cybersecurity quiz application

-- Create database (run this as superuser)
-- CREATE DATABASE byteshield_db;

-- Connect to the database
-- \c byteshield_db;

-- Create quiz_questions table
CREATE TABLE IF NOT EXISTS quiz_questions (
    id SERIAL PRIMARY KEY,
    question TEXT NOT NULL,
    option_a TEXT NOT NULL,
    option_b TEXT NOT NULL,
    option_c TEXT NOT NULL,
    option_d TEXT NOT NULL,
    correct_option CHAR(1) NOT NULL CHECK (correct_option IN ('A', 'B', 'C', 'D')),
    category VARCHAR(50) DEFAULT 'General',
    difficulty VARCHAR(20) DEFAULT 'Medium' CHECK (difficulty IN ('Easy', 'Medium', 'Hard')),
    explanation TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample cybersecurity quiz questions
INSERT INTO quiz_questions (question, option_a, option_b, option_c, option_d, correct_option, category, difficulty, explanation) VALUES
(
    'What is the most secure way to create a password?',
    'Use your pet''s name and birth year',
    'Use a combination of uppercase, lowercase, numbers, and special characters',
    'Use the same password for all accounts',
    'Write it down on a sticky note',
    'B',
    'Password Security',
    'Easy',
    'Strong passwords should include a mix of character types and be unique for each account.'
),
(
    'Which of the following is a sign of a phishing email?',
    'Professional formatting and correct spelling',
    'Urgent requests for personal information',
    'Clear sender identification',
    'Relevant content to your interests',
    'B',
    'Phishing Detection',
    'Medium',
    'Phishing emails often create urgency and request sensitive information to trick users.'
),
(
    'What should you do if you receive a suspicious email?',
    'Click on all links to verify authenticity',
    'Reply with your personal information',
    'Delete the email and report it as spam',
    'Forward it to all your contacts',
    'C',
    'Phishing Detection',
    'Easy',
    'Suspicious emails should be deleted and reported to avoid potential security threats.'
),
(
    'Which is the safest way to browse the internet?',
    'Use public Wi-Fi without VPN',
    'Click on all pop-up advertisements',
    'Keep your browser and security software updated',
    'Disable your firewall for faster browsing',
    'C',
    'General Security',
    'Easy',
    'Keeping software updated helps protect against known vulnerabilities and security threats.'
),
(
    'What is two-factor authentication (2FA)?',
    'Using two different passwords',
    'A security method requiring two forms of verification',
    'Having two email accounts',
    'Using two different browsers',
    'B',
    'Authentication',
    'Medium',
    '2FA adds an extra layer of security by requiring something you know (password) and something you have (phone, token).'
),
(
    'Which of the following is NOT a good practice for software security?',
    'Regularly updating software and applications',
    'Installing software only from trusted sources',
    'Disabling automatic security updates',
    'Using antivirus software',
    'C',
    'Software Security',
    'Medium',
    'Automatic security updates should be enabled to ensure you receive the latest security patches.'
),
(
    'What should you do if your computer is infected with malware?',
    'Continue using it normally',
    'Disconnect from the internet and run antivirus software',
    'Share files with other computers',
    'Ignore the warning messages',
    'B',
    'Malware Protection',
    'Medium',
    'Disconnecting from the internet prevents malware from spreading and allows for safe removal.'
),
(
    'Which of the following is the most secure way to store sensitive documents?',
    'On your desktop for easy access',
    'In an encrypted folder with strong password protection',
    'In a shared network folder',
    'As email attachments',
    'B',
    'Data Protection',
    'Medium',
    'Encrypted storage with strong passwords provides the best protection for sensitive documents.'
);

-- Create an index on category for faster queries
CREATE INDEX IF NOT EXISTS idx_quiz_questions_category ON quiz_questions(category);

-- Create an index on difficulty for filtering
CREATE INDEX IF NOT EXISTS idx_quiz_questions_difficulty ON quiz_questions(difficulty);

-- Display table information
SELECT 'Quiz questions table created successfully!' as status;
SELECT COUNT(*) as total_questions FROM quiz_questions;
