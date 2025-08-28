const API_BASE_URL = 'http://localhost:8080';

document.addEventListener('DOMContentLoaded', () => {
    const userCredentials = sessionStorage.getItem('userCredentials');
    const currentPage = window.location.pathname.split('/').pop();

    // If user is not logged in and trying to access a protected page
    if (!userCredentials && (currentPage === 'predict.html' || currentPage === 'dashboard.html')) {
        window.location.href = './login.html';
        return;
    }

    // Logout functionality
    const logoutBtn = document.getElementById('logout-btn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', () => {
            sessionStorage.removeItem('userCredentials');
            window.location.href = './login.html';
        });
    }
});

