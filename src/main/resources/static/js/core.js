

const originalFetch = window.fetch;
window.fetch = async function (...args) {
    showLoader();

    try {
        const response = await originalFetch(...args);
        return response;
    } catch (error) {
        throw error;
    } finally {
        hideLoader();
    }
};
function showConfirm(msg) {
    return window.confirm(msg);
}
function showLoader() {
    const loader = document.getElementById("globalLoader");
    if (loader) loader.style.display = "flex";
}

function hideLoader() {
    const loader = document.getElementById("globalLoader");
    if (loader) loader.style.display = "none";
}
function showToast(message, type = 'success') {

    const container = document.getElementById("toastContainer");

    if (!container) {
        console.warn("toastContainer not found");
        return;
    }

    const toast = document.createElement("div");
    toast.className = `toast toast-${type}`;
    toast.innerText = message;

    container.appendChild(toast);

    setTimeout(() => toast.remove(), 10000);
}
function handleFilePreview(e) {

    const preview = document.getElementById("imagePreview");

    if (!preview) return;

    const file = e.target.files[0];

    if (!file) return;

    const reader = new FileReader();

    reader.onload = (event) => {
        preview.innerHTML =
            `<img src="${event.target.result}"
                  alt="Preview"
                  style="max-height:200px;border-radius:10px;">`;
    };

    reader.readAsDataURL(file);
}
function initBaseUi() {

    document.body.insertAdjacentHTML(
        "beforeend",
        `
        <div id="globalLoader" class="loader-overlay">
            <div class="loader-content">
                <div class="loader-spinner"></div>
                <div class="loader-text">Loading...</div>
            </div>
        </div>

        <div id="toastContainer" class="toast-container"></div>
        `
    );
}

document.addEventListener("DOMContentLoaded", initBaseUi);