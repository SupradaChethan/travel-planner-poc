// API Base URL
const API_BASE_URL = '/api/travel';

// Tab switching
document.querySelectorAll('.tab-btn').forEach(button => {
    button.addEventListener('click', () => {
        const tabName = button.getAttribute('data-tab');
        switchTab(tabName);
    });
});

function switchTab(tabName) {
    // Update buttons
    document.querySelectorAll('.tab-btn').forEach(btn => {
        btn.classList.remove('active');
    });
    document.querySelector(`[data-tab="${tabName}"]`).classList.add('active');

    // Update content
    document.querySelectorAll('.tab-content').forEach(content => {
        content.classList.remove('active');
    });
    document.getElementById(`${tabName}-tab`).classList.add('active');
}

// Travel Plan Form
document.getElementById('travel-plan-form').addEventListener('submit', async (e) => {
    e.preventDefault();

    const formData = {
        destination: document.getElementById('destination').value,
        numberOfDays: parseInt(document.getElementById('numberOfDays').value),
        interests: document.getElementById('interests').value || null,
        budget: document.getElementById('budget').value || null,
        travelStyle: document.getElementById('travelStyle').value || null
    };

    const submitBtn = e.target.querySelector('.btn-primary');

    try {
        setLoading(submitBtn, true);

        const response = await fetch(`${API_BASE_URL}/plan`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        });

        if (!response.ok) {
            throw new Error('Failed to generate travel plan');
        }

        const data = await response.json();
        displayPlanResult(data);
        showToast('Travel plan generated successfully!', 'success');

    } catch (error) {
        console.error('Error:', error);
        showToast('Failed to generate travel plan. Please try again.', 'error');
    } finally {
        setLoading(submitBtn, false);
    }
});

// Travel Tips Form
document.getElementById('travel-tips-form').addEventListener('submit', async (e) => {
    e.preventDefault();

    const destination = document.getElementById('tips-destination').value;
    const submitBtn = e.target.querySelector('.btn-primary');

    try {
        setLoading(submitBtn, true);

        const response = await fetch(`${API_BASE_URL}/tips/${encodeURIComponent(destination)}`);

        if (!response.ok) {
            throw new Error('Failed to get travel tips');
        }

        const data = await response.json();
        displayTipsResult(data);
        showToast('Travel tips loaded successfully!', 'success');

    } catch (error) {
        console.error('Error:', error);
        showToast('Failed to get travel tips. Please try again.', 'error');
    } finally {
        setLoading(submitBtn, false);
    }
});

// Suggestions Form
document.getElementById('suggest-form').addEventListener('submit', async (e) => {
    e.preventDefault();

    const preferences = document.getElementById('preferences').value;
    const submitBtn = e.target.querySelector('.btn-primary');

    try {
        setLoading(submitBtn, true);

        const response = await fetch(`${API_BASE_URL}/suggest`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ preferences })
        });

        if (!response.ok) {
            throw new Error('Failed to get suggestions');
        }

        const data = await response.json();
        displaySuggestResult(data);
        showToast('Destination suggestions loaded!', 'success');

    } catch (error) {
        console.error('Error:', error);
        showToast('Failed to get suggestions. Please try again.', 'error');
    } finally {
        setLoading(submitBtn, false);
    }
});

// Display Functions
function displayPlanResult(data) {
    const resultDiv = document.getElementById('plan-result');
    const contentDiv = document.getElementById('plan-content');

    let html = `
        <div class="plan-header">
            <h4>📍 ${data.destination}</h4>
            <p><strong>Duration:</strong> ${data.numberOfDays} ${data.numberOfDays === 1 ? 'day' : 'days'}</p>
        </div>
        <div class="plan-body">
            ${formatPlanContent(data.overview)}
        </div>
    `;

    contentDiv.innerHTML = html;
    resultDiv.style.display = 'block';
    resultDiv.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
}

function displayTipsResult(data) {
    const resultDiv = document.getElementById('tips-result');
    const contentDiv = document.getElementById('tips-content');

    let html = `
        <div class="tips-header">
            <h4>📍 ${data.destination}</h4>
        </div>
        <div class="tips-body">
            ${formatTipsContent(data.tips)}
        </div>
    `;

    contentDiv.innerHTML = html;
    resultDiv.style.display = 'block';
    resultDiv.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
}

function displaySuggestResult(data) {
    const resultDiv = document.getElementById('suggest-result');
    const contentDiv = document.getElementById('suggest-content');

    let html = `
        <div class="suggest-body">
            ${formatSuggestContent(data.suggestions)}
        </div>
    `;

    contentDiv.innerHTML = html;
    resultDiv.style.display = 'block';
    resultDiv.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
}

// Content Formatting Functions
function formatPlanContent(content) {
    // Convert line breaks and format the content nicely
    return content
        .split('\n')
        .map(line => {
            if (line.trim().startsWith('Day ') || line.trim().startsWith('## Day')) {
                return `<h4 style="color: var(--primary-color); margin-top: 1.5rem;">${line.trim()}</h4>`;
            } else if (line.trim().startsWith('###') || line.trim().startsWith('**')) {
                return `<h5 style="color: var(--text-primary); margin-top: 1rem;">${line.replace(/[#*]/g, '').trim()}</h5>`;
            } else if (line.trim().startsWith('-') || line.trim().startsWith('•')) {
                return `<li style="margin-left: 1.5rem;">${line.trim().substring(1).trim()}</li>`;
            } else if (line.trim()) {
                return `<p>${line.trim()}</p>`;
            }
            return '<br>';
        })
        .join('');
}

function formatTipsContent(content) {
    return content
        .split('\n')
        .map(line => {
            if (line.trim().match(/^\d+\./)) {
                return `<div style="margin: 1rem 0; padding: 1rem; background: var(--bg-color); border-radius: 8px;">
                    <strong style="color: var(--primary-color);">${line.trim()}</strong>
                </div>`;
            } else if (line.trim().startsWith('-')) {
                return `<li style="margin-left: 1.5rem;">${line.trim().substring(1).trim()}</li>`;
            } else if (line.trim()) {
                return `<p>${line.trim()}</p>`;
            }
            return '<br>';
        })
        .join('');
}

function formatSuggestContent(content) {
    return content
        .split('\n')
        .map(line => {
            if (line.trim().match(/^\d+\./)) {
                return `<div style="margin: 1rem 0; padding: 1.25rem; background: linear-gradient(135deg, #667eea20 0%, #764ba220 100%); border-radius: 12px; border-left: 4px solid var(--primary-color);">
                    <strong style="color: var(--primary-color); font-size: 1.1rem;">${line.trim()}</strong>
                </div>`;
            } else if (line.trim().startsWith('-')) {
                return `<li style="margin-left: 1.5rem; margin-bottom: 0.5rem;">${line.trim().substring(1).trim()}</li>`;
            } else if (line.trim()) {
                return `<p style="margin-bottom: 0.75rem;">${line.trim()}</p>`;
            }
            return '<br>';
        })
        .join('');
}

// Close Result Functions
function closePlanResult() {
    document.getElementById('plan-result').style.display = 'none';
}

function closeTipsResult() {
    document.getElementById('tips-result').style.display = 'none';
}

function closeSuggestResult() {
    document.getElementById('suggest-result').style.display = 'none';
}

// Utility Functions
function setLoading(button, isLoading) {
    if (isLoading) {
        button.classList.add('loading');
        button.disabled = true;
    } else {
        button.classList.remove('loading');
        button.disabled = false;
    }
}

function showToast(message, type = 'info') {
    const toast = document.getElementById('toast');
    toast.textContent = message;
    toast.className = `toast ${type}`;
    toast.classList.add('show');

    setTimeout(() => {
        toast.classList.remove('show');
    }, 3000);
}

// Auto-hide results when switching tabs
document.querySelectorAll('.tab-btn').forEach(button => {
    button.addEventListener('click', () => {
        closePlanResult();
        closeTipsResult();
        closeSuggestResult();
    });
});

// Initialize - Check API health
async function checkHealth() {
    try {
        const response = await fetch(`${API_BASE_URL}/health`);
        if (response.ok) {
            console.log('✅ API is connected and ready');
        }
    } catch (error) {
        console.warn('⚠️ API connection issue:', error);
        showToast('Warning: Could not connect to API. Please ensure the server is running.', 'error');
    }
}

// Check health on load
checkHealth();
