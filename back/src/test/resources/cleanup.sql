-- Delete all rows from all tables
DELETE FROM PARTICIPATE;
DELETE FROM SESSIONS;
DELETE FROM TEACHERS;
DELETE FROM USERS;
-- Add more tables as needed

-- Reset the auto-incrementing primary key value
ALTER TABLE PARTICIPATE AUTO_INCREMENT = 1;
ALTER TABLE SESSIONS AUTO_INCREMENT = 1;
ALTER TABLE TEACHERS AUTO_INCREMENT = 1;
ALTER TABLE USERS AUTO_INCREMENT = 1;
-- Add more tables as needed