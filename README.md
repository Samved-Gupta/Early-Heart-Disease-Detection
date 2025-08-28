Early Heart Disease Detection System
This is a full-stack web application designed to predict the risk of early-onset heart disease based on user-provided health metrics. The system uses a machine learning model served via a Python Flask API, a robust backend built with Spring Boot, and a user-friendly frontend created with HTML, CSS, and JavaScript.

Features
User Authentication: Secure user registration and login system.

Password Reset: OTP-based password reset functionality using Gmail.

ML-Powered Predictions: Users can input their health data to receive a real-time prediction of their heart disease risk.

Prediction History: A dashboard for users to view their past prediction results.

Model Bake-off: The ML server automatically trains multiple models (Logistic Regression, Random Forest, SVM) and uses the most accurate one for predictions.

Tech Stack
Frontend: HTML, Tailwind CSS, JavaScript

Backend: Java, Spring Boot, Spring Security, Spring Data JPA

Machine Learning Server: Python, Flask, Scikit-learn, Pandas

Database: MySQL

Project Structure
The project is divided into three main parts:

FRONTEND/: Contains all the client-side files (.html, .css, .js).

Backend/: The complete Spring Boot application.

ML Server/: The Python Flask server that handles machine learning predictions.

Setup & Installation
Prerequisites
Java 17 or later

Maven

Python 3.8 or later

MySQL Server

A Gmail account with an App Password enabled.

1. Backend Setup (Spring Boot)
Clone the repository:

git clone https://github.com/Samved-Gupta/Early-Heart-Disease-Detection.git
cd "Early Heart Disease Detection/Backend"

Create MySQL Database:

Open your MySQL client and create a new database.

CREATE DATABASE heart_disease_db;

Configure Application Properties:

Navigate to src/main/resources/ and open the application.properties file.

Update the following properties with your credentials.

# 🚨 SECURITY NOTICE: Do NOT commit this file with your real passwords!
# Use environment variables or a secrets management tool in production.

# MySQL Database Connection
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password

# Gmail Configuration for OTP
spring.mail.username=your-email@gmail.com
spring.mail.password=your-16-character-app-password

Run the Backend Server:

Open the project in IntelliJ IDEA or your preferred IDE.

Run the Application.java file. The server will start on http://localhost:8080.

2. ML Server Setup (Python Flask)
Navigate to the ML Server directory:

cd "../ML Server"

Create a virtual environment and install dependencies:

python -m venv venv
# On Windows
venv\Scripts\activate
# On macOS/Linux
# source venv/bin/activate

pip install -r requirements.txt

Train the ML Model:

Run the training script once to generate the model file.

python train_model.py

Run the ML Server:

Use Waitress to run the server in production mode.

waitress-serve --host 127.0.0.1 --port=5000 app:app

The ML server will be available at http://localhost:5000.

3. Frontend Setup
Open the HTML files:

Navigate to the FRONTEND/ directory.

Open the login.html file in your web browser to start the application.