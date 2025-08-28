# Early Heart Disease Detection System

A full-stack web application designed to predict the risk of early-onset heart disease based on user-provided health metrics. The system uses a machine learning model served via a Python Flask API, a robust backend built with Spring Boot, and a user-friendly frontend.

## ✨ Features

-   **User Authentication**: Secure user registration and login system.
-   **Password Reset**: OTP-based password reset functionality using Gmail.
-   **ML-Powered Predictions**: Users can input health data to receive a real-time prediction of their heart disease risk.
-   **Prediction History**: A dashboard for users to view their past prediction results.
-   **Model Bake-off**: The ML server automatically trains multiple models (Logistic Regression, Random Forest, SVM) and uses the most accurate one for predictions.

## 🛠️ Tech Stack

-   **Frontend**: HTML, Tailwind CSS, JavaScript
-   **Backend**: Java, Spring Boot, Spring Security, Spring Data JPA
-   **Machine Learning Server**: Python, Flask, Scikit-learn, Pandas
-   **Database**: MySQL

## 📂 Project Structure

The project is divided into three main parts:

-   `FRONTEND/`: Contains all the client-side files (`.html`, `.css`, `.js`).
-   `Backend/`: The complete Spring Boot application.
-   `ML Server/`: The Python Flask server that handles machine learning predictions.

## 🚀 Setup & Installation

### Prerequisites

-   Java 17 or later
-   Maven
-   Python 3.8 or later
-   MySQL Server
-   A Gmail account with an [App Password](https://support.google.com/accounts/answer/185833) enabled.

---

### 1. Backend Setup (Spring Boot)

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/Samved-Gupta/Early-Heart-Disease-Detection.git](https://github.com/Samved-Gupta/Early-Heart-Disease-Detection.git)
    cd "Early Heart Disease Detection/Backend"
    ```

2.  **Create MySQL Database:**
    ```sql
    CREATE DATABASE heart_disease_db;
    ```

3.  **Configure Application Properties:**
    Navigate to `src/main/resources/` and open the `application.properties` file. Update the following properties with your credentials.

    ```properties
    # 🚨 SECURITY NOTICE: Do NOT commit this file with your real passwords!
    # Use environment variables or a secrets management tool in production.

    # MySQL Database Connection
    spring.datasource.url=jdbc:mysql://localhost:3306/heart_disease_db
    spring.datasource.username=your_mysql_username
    spring.datasource.password=your_mysql_password

    # Gmail Configuration for OTP
    spring.mail.username=your-email@gmail.com
    spring.mail.password=your-16-character-app-password
    ```

4.  **Run the Backend Server:**
    Open the project in your IDE (e.g., IntelliJ IDEA) and run the `Application.java` file. The server will start on `http://localhost:8080`.

---

### 2. ML Server Setup (Python Flask)

1.  **Navigate to the ML Server directory:**
    ```bash
    # From the project root
    cd "ML Server"
    ```

2.  **Create a virtual environment and install dependencies:**
    ```bash
    # Create the virtual environment
    python -m venv venv

    # Activate it (Windows)
    venv\Scripts\activate

    # Activate it (macOS/Linux)
    # source venv/bin/activate

    # Install packages
    pip install -r requirements.txt
    ```

3.  **Train the ML Model:**
    Run the training script once to generate the `best_model.pkl` file.
    ```bash
    python train_model.py
    ```

4.  **Run the ML Server:**
    Use Waitress to run the server.
    ```bash
    waitress-serve --host 127.0.0.1 --port=5000 app:app
    ```
    The ML server will be available at `http://localhost:5000`.

---

### 3. Frontend Setup

1.  **Navigate to the Frontend directory:**
    ```bash
    # From the project root
    cd "Frontend"
    ```
2.  **Open the HTML files:**
    Open the `login.html` file in your web browser to start the application.