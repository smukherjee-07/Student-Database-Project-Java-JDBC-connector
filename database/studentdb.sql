CREATE DATABASE IF NOT EXISTS studentdb;
USE studentdb;

DROP TABLE IF EXISTS student;

CREATE TABLE student (
    reg_no VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    course VARCHAR(100) NOT NULL,
    semester INT NOT NULL,
    marks INT NOT NULL
);

INSERT INTO student (reg_no, name, course, semester, marks) VALUES
('25BAI10226', 'Soumallaya Mukherjee', 'CSE AIML', 3, 91),
('25BAI11075', 'Aryan Chirag', 'CSE AIML', 3, 85),
('25BAI10962', 'Achyut Roy', 'CSE AIML', 3, 88),
('25BAI11029', 'Anway Basu', 'CSE AIML', 3, 84),
('25BAI11327', 'Satyam Kumar Singh', 'CSE AIML', 3, 82),
('25BAC10266', 'Vaidant Udawat', 'ECE', 3, 80),
('25BCE10130', 'Subhradip Roy', 'CSE CORE', 3, 78),
('25BCE10635', 'Dhruv Shrivastav', 'CSE CORE', 3, 86);

SELECT * FROM student;