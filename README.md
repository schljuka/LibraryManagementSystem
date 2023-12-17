  SHOW DATABASES;
 
 CREATE DATABASE library_ms;

USE library_ms;



// create table users

CREATE TABLE users (
id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
name varchar(50),
password varchar(50),
email varchar(100),
contact varchar(20)
);


// create tabele book_details

CREATE TABLE book_details(
book_id int PRIMARY KEY AUTO_INCREMENT NOT NULL,
book_name varchar(250),
author varchar(250),
quantity INT
);


// create table tabele students__details

CREATE TABLE student_details(
student_id int PRIMARY KEY AUTO_INCREMENT NOT NULL,
name varchar(100),
department varchar(100),
programme varchar(100)
);




// create tabele issue_book

CREATE TABLE issue_book_details(
id int PRIMARY KEY AUTO_INCREMENT NOT NULL,
book_id int,
book_name varchar(150),
student_id int,
student_name varchar(50),
issue_date date,
due_date date,
status varchar(20),
return_date date
);
