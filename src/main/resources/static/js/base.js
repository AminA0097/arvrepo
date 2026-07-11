function showToast(type, message) {
    const container = document.getElementById("toastContainer");

    if (!container) {
        console.error("toastContainer not found");
        return;
    }

    const normalizedType = normalizeToastType(type);
    const toast = document.createElement("div");

    toast.className = `app-toast app-toast--${normalizedType}`;

    toast.innerHTML = `
        <span class="app-toast__icon">${getToastIcon(normalizedType)}</span>
        <span class="app-toast__message">${escapeHtml(message || "خطایی رخ داده است")}</span>
    `;

    container.appendChild(toast);

    setTimeout(() => {
        toast.remove();
    }, 3000);
}

function normalizeToastType(type) {
    const value = String(type || "").toLowerCase();

    if (value === "success") return "success";
    if (value === "warn" || value === "warning") return "warn";
    if (value === "failed" || value === "faild" || value === "error" || value === "danger") return "failed";

    return "failed";
}

function getToastIcon(type) {
    if (type === "success") return "✓";
    if (type === "warn") return "!";
    return "×";
}

function escapeHtml(value) {
    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}

function initApp() {
    // amazing
    if (typeof initAmazing === 'function') initAmazing();

    // events - مثل amazing
    if (typeof initEvents === 'function') initEvents();

    if (typeof fetchBestSellers === 'function') fetchBestSellers();
}

if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initApp);
} else {
    initApp();
}
function setValue(id,value){
    document.getElementById(id).value = value;
}