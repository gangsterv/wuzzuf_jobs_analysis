# Wuzzuf Job Analysis using Java (Maven Tablesaw and Spring boot)

## Description
This project serves a pipeline to read and clean the Wuzzuf Jobs CSV file found on Kaggle.com. It uses Tablesaw to read the CSV file as a table and perform various cleaning and prepartion functions to it. The goal is to analyze and show information about the CSV file.

## Manual
The project is based on a RESTful API web service made using Spring Boot. You can run the web service on your local machine by running WebserviceApplication class. It has several endpoints that perform different actions. Those include:

• /display : Read Data and display the head of it

• /description : Show summary and structure of the CSV file

• /jobs_company : Show top 10 jobs by company

• /show_company : Show top 10 jobs by company as pie chart

• /jobs_title : Show top 10 jobs by title

• /show_title : Show top 10 jobs by title as bar chart

• /jobs_area : Show top 10 jobs by location

• /show_area : Show top 10 jobs by location as bar chart

• /skills : Show skills frequency, ordered by most frequent skills

• /factorize : Show dataframe with factorization for YearsExp column

## Made by

Alaa Abdo

Eid Gamal

Omar Hammad

Group 1
