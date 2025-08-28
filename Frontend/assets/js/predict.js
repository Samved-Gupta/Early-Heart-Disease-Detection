document.addEventListener('DOMContentLoaded', () => {
    const predictionForm = document.getElementById('prediction-form');
    const resultSection = document.getElementById('result-section');

    predictionForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const userCredentials = sessionStorage.getItem('userCredentials');
        if (!userCredentials) {
            alert('Authentication error. Please log in again.');
            window.location.href = './login.html';
            return;
        }

        const formData = new FormData(predictionForm);
        const data = Object.fromEntries(formData.entries());
        for (const key in data) { data[key] = parseFloat(data[key]); }

        resultSection.innerHTML = `<div class="text-gray-600">Analyzing data...</div>`;

        try {
            const response = await fetch(`${API_BASE_URL}/api/predict`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Basic ${userCredentials}`
                },
                body: JSON.stringify(data)
            });

            if (!response.ok) { throw new Error(`HTTP error! status: ${response.status}`); }

            const result = await response.json();
            displayResult(result);

        } catch (error) {
            console.error('Error fetching prediction:', error);
            displayError('Failed to get prediction. Please try again.');
        }
    });

    function displayResult(result) {
        const hasDisease = result.prediction === 1;
        const probabilityPercent = (result.probability * 100).toFixed(2);
        const cardClass = hasDisease ? 'bg-red-100 border-red-500 text-red-800' : 'bg-green-100 border-green-500 text-green-800';
        const title = hasDisease ? 'High Risk of Heart Disease Detected' : 'Low Risk of Heart Disease Detected';
        resultSection.innerHTML = `
            <div class="max-w-md mx-auto p-6 rounded-xl border ${cardClass}">
                <h2 class="text-2xl font-bold mb-2">${title}</h2>
                <p class="text-lg">Probability: <span class="font-semibold">${probabilityPercent}%</span></p>
            </div>`;
    }
    
    function displayError(message) {
         resultSection.innerHTML = `
            <div class="max-w-md mx-auto p-6 rounded-xl border bg-yellow-100 border-yellow-500 text-yellow-800">
                <h2 class="text-2xl font-bold mb-2">Error</h2>
                <p>${message}</p>
            </div>`;
    }
});
