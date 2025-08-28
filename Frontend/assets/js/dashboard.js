document.addEventListener('DOMContentLoaded', () => {
    const historyContainer = document.getElementById('history-container');
    const userCredentials = sessionStorage.getItem('userCredentials');

    async function fetchHistory() {
        if (!userCredentials) { return; }

        historyContainer.innerHTML = `<p class="text-gray-600">Loading history...</p>`;

        try {
            const response = await fetch(`${API_BASE_URL}/api/predict/history`, {
                method: 'GET',
                headers: { 'Authorization': `Basic ${userCredentials}` }
            });

            if (!response.ok) { throw new Error(`HTTP error! status: ${response.status}`); }

            const historyData = await response.json();
            renderHistoryTable(historyData);

        } catch (error) {
            console.error("Failed to fetch history:", error);
            historyContainer.innerHTML = `<p class="text-red-500">Failed to load prediction history.</p>`;
        }
    }

    function renderHistoryTable(data) {
        if (data.length === 0) {
            historyContainer.innerHTML = `<p class="text-gray-600">No prediction history found.</p>`;
            return;
        }
        
        data.sort((a, b) => new Date(b.timestamp) - new Date(a.timestamp));

        let tableHtml = `
            <table class="min-w-full bg-white rounded-lg">
                <thead class="bg-gray-100">
                    <tr>
                        <th class="py-3 px-6 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Date & Time</th>
                        <th class="py-3 px-6 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Result</th>
                        <th class="py-3 px-6 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Probability</th>
                    </tr>
                </thead>
                <tbody class="text-gray-700">`;

        data.forEach(prediction => {
            const date = new Date(prediction.timestamp).toLocaleString();
            const resultText = prediction.result === 1 
                ? '<span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-red-100 text-red-800">High Risk</span>'
                : '<span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">Low Risk</span>';
            const probabilityPercent = (prediction.probability * 100).toFixed(2);

            tableHtml += `
                <tr>
                    <td class="py-4 px-6 border-b">${date}</td>
                    <td class="py-4 px-6 border-b">${resultText}</td>
                    <td class="py-4 px-6 border-b">${probabilityPercent}%</td>
                </tr>`;
        });

        tableHtml += `</tbody></table>`;
        historyContainer.innerHTML = tableHtml;
    }
    
    fetchHistory();
});
