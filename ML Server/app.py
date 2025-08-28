from flask import Flask, request, jsonify
import joblib
import pandas as pd

app = Flask(__name__)

# --- Model and Columns Loading ---
try:
    model = joblib.load('heart_disease_model.pkl')
    model_columns = joblib.load('model_columns.pkl')
    print("Model and columns loaded successfully.")
except FileNotFoundError:
    print("Error: Model or column file not found. Please run train_model.py first.")
    exit()
except Exception as e:
    print(f"An error occurred while loading files: {e}")
    exit()


@app.route('/')
def home():
    return "ML Prediction Server is running."

@app.route('/predict', methods=['POST'])
def predict():
    """
    Receives health data, preprocesses it to match the model's training
    format, and returns a prediction.
    """
    try:
        data = request.get_json(force=True)
        print("Received data:", data)

        # Convert incoming json to a pandas DataFrame
        input_df = pd.DataFrame([data])

        input_df = pd.get_dummies(input_df, columns=['cp', 'thal'])

        final_df = input_df.reindex(columns=model_columns, fill_value=0)

        # --- Make Prediction ---
        prediction = model.predict(final_df)
        probability = model.predict_proba(final_df)

        prediction_result = int(prediction[0])
        probability_result = float(probability[0][prediction_result])

        response = {
            'prediction': prediction_result,
            'probability': probability_result
        }

        print("Prediction response:", response)
        return jsonify(response)

    except Exception as e:
        print(f"Error during prediction: {e}")
        return jsonify({'error': 'An error occurred during prediction.'}), 500
