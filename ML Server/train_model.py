import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
import joblib
import numpy as np

from sklearn.linear_model import LogisticRegression
from sklearn.ensemble import RandomForestClassifier
from sklearn.svm import SVC

def train_and_save_best_model():
    """
    Loads data from the UCI repository, cleans it, preprocesses it, trains multiple models,
    compares their accuracy, and saves the best performing model to a file.
    """
    try:
        url = "https://archive.ics.uci.edu/ml/machine-learning-databases/heart-disease/processed.cleveland.data"
        
        column_names = ['age', 'sex', 'cp', 'trestbps', 'chol', 'fbs', 'restecg', 'thalach', 
                        'exang', 'oldpeak', 'slope', 'ca', 'thal', 'target']
        
        df = pd.read_csv(url, header=None, names=column_names, na_values='?')
        
        print("Dataset loaded successfully.")

        df.dropna(inplace=True)
        print("Missing values handled.")

        df['target'] = np.where(df['target'] > 0, 1, 0)

    except Exception as e:
        print(f"Could not download or process dataset. Error: {e}")
        return

    df = pd.get_dummies(df, columns=['cp', 'thal'], drop_first=True)

    X = df.drop('target', axis=1)
    y = df['target']
    
    joblib.dump(X.columns, 'model_columns.pkl')
    print("Model columns saved to 'model_columns.pkl'")

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

    # --- Model Training and Comparison ---
    
    # Define the models to be trained
    models = {
        "Logistic Regression": LogisticRegression(max_iter=2000),
        "Random Forest": RandomForestClassifier(random_state=42),
        "SVM": SVC(probability=True, random_state=42) # probability=True is needed for predict_proba
    }

    best_model = None
    best_accuracy = 0.0

    # Loop through each model, train it, and evaluate its accuracy
    for name, model in models.items():
        # Train the model
        model.fit(X_train, y_train)
        
        # Make predictions on the test data
        y_pred = model.predict(X_test)
        
        # Calculate accuracy
        accuracy = accuracy_score(y_test, y_pred)
        
        print(f"Model: {name}, Accuracy: {accuracy:.4f}")
        
        # Check if this model is the best one so far
        if accuracy > best_accuracy:
            best_accuracy = accuracy
            best_model = model
            print(f"-> New best model found: {name}")

    # --- Save the Best Model ---
    if best_model:
        print(f"\nSaving the best model ({best_model.__class__.__name__}) with an accuracy of {best_accuracy:.4f}")
        joblib.dump(best_model, 'heart_disease_model.pkl')
        print("Best model saved as 'heart_disease_model.pkl'")
    else:
        print("Could not determine the best model. No model was saved.")

if __name__ == '__main__':
    train_and_save_best_model()
