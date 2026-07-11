const API_URL = "/api/amazing/get-all";
const PAGE_NO = 0;
const PAGE_SIZE = 30;

let amazingSwiper = null;
let amazingTimer = null;
let fetchController = null;

document.addEventListener("DOMContentLoaded", initAmazing);

async function initAmazing() {
    const container = document.querySelector("#amazing-swiper .swiper-wrapper");
    const timerEl = document.querySelector("#amazing-timer");

    if (!container || !timerEl) {
        console.warn("Amazing section elements not found");
        return;
    }

    renderSkeleton(container);

    try {
        await refreshAmazing(container, timerEl);
    } catch (e) {
        console.error("Init amazing failed:", e);
        hideAmazingSection();
    }
}

function renderSkeleton(container) {
    const skeletonWrapper = document.createElement("div");
    skeletonWrapper.className = "ar-skeleton-wrapper";

    const skeletonCards = Array.from({ length: 6 }, () => {
        const card = document.createElement("div");
        card.className = "ar-skeleton-card";
        card.innerHTML = `
            <div class="ar-skeleton-image"></div>
            <div class="ar-skeleton-title"></div>
            <div class="ar-skeleton-title" style="width:80%;"></div>
            <div class="ar-skeleton-price"></div>
            <div class="ar-skeleton-meta">
                <div class="ar-skeleton-meta-item"></div>
                <div class="ar-skeleton-meta-item"></div>
                <div class="ar-skeleton-meta-item"></div>
            </div>
        `;
        return card;
    });

    skeletonCards.forEach(card => skeletonWrapper.appendChild(card));

    container.innerHTML = "";
    container.appendChild(skeletonWrapper);
}

async function refreshAmazing(container, timerEl) {
    const data = await fetchAmazingProducts();

    console.log("Amazing API data:", data);

    if (!data || !Array.isArray(data.productList) || data.productList.length === 0) {
        console.warn("No amazing products received");
        renderEmptyState(container);
        return;
    }

    renderAmazingProducts(container, data.productList);

    initializeSwiper();

    startTimer(data.duration, timerEl, () => {
        hideAmazingSection();
    });
}

/* =========================================================
   API
========================================================= */
async function fetchAmazingProducts() {
    try {
        if (fetchController) {
            fetchController.abort();
        }

        fetchController = new AbortController();

        const response = await fetch(
            `${API_URL}?page=${PAGE_NO}&size=${PAGE_SIZE}`,
            {
                signal: fetchController.signal,
                headers: {
                    Accept: "application/json"
                }
            }
        );

        if (!response.ok) {
            throw new Error(`HTTP ${response.status}`);
        }

        const json = await response.json();

        console.log("Raw amazing response:", json);

        if (!json?.success) {
            throw new Error(json?.message || "API Error");
        }

        return {
            productList: json.data?.productList || [],
            duration: json.data?.duration
        };

    } catch (error) {
        if (error.name === "AbortError") {
            return null;
        }

        console.error("Fetch amazing products failed:", error);
        showToast?.("warning", "خطا در دریافت اطلاعات شگفت‌انگیز");
        return null;
    }
}

/* =========================================================
   TIMER
========================================================= */
function startTimer(duration, element, onExpire) {
    stopTimer();

    const deadline = parseDurationToDeadline(duration);

    console.log("Amazing duration:", duration);
    console.log("Amazing deadline:", deadline);

    if (!deadline || Number.isNaN(deadline.getTime())) {
        console.warn("Invalid amazing duration:", duration);

        element.innerHTML = `
            <span class="text-leather-400 text-sm" style="font-family: 'Shabnam', system-ui, sans-serif;">
                زمان نامعتبر
            </span>
        `;

        return;
    }

    const render = () => {
        const remaining = getRemainingTime(deadline);

        if (remaining.expired) {
            element.innerHTML = `
                <span class="text-leather-400 text-sm" style="font-family: 'Shabnam', system-ui, sans-serif;">
                    تمام شد
                </span>
            `;

            stopTimer();
            onExpire?.();
            return;
        }

        element.innerHTML = `
            ${timerUnit(remaining.hours, "ساعت")}
            <span class="ar-amazing-timer-separator">:</span>
            ${timerUnit(remaining.minutes, "دقیقه")}
            <span class="ar-amazing-timer-separator">:</span>
            ${timerUnit(remaining.seconds, "ثانیه")}
        `;
    };

    render();
    amazingTimer = setInterval(render, 1000);
}

function stopTimer() {
    if (!amazingTimer) return;

    clearInterval(amazingTimer);
    amazingTimer = null;
}

function parseDurationToDeadline(duration) {
    if (!duration) return null;

    if (typeof duration === "number") {
        if (duration > 1_000_000_000_000) {
            return new Date(duration);
        }

        if (duration > 1_000_000_000) {
            return new Date(duration * 1000);
        }

        return new Date(Date.now() + duration * 1000);
    }

    if (typeof duration === "string") {
        const trimmed = duration.trim();

        if (/^\d+$/.test(trimmed)) {
            return parseDurationToDeadline(Number(trimmed));
        }

        const date = new Date(trimmed);
        if (!Number.isNaN(date.getTime())) {
            return date;
        }
    }

    return null;
}

function getRemainingTime(deadline) {
    const diff = deadline.getTime() - Date.now();

    if (diff <= 0) {
        return {
            hours: 0,
            minutes: 0,
            seconds: 0,
            expired: true
        };
    }

    return {
        hours: Math.floor(diff / 3600000),
        minutes: Math.floor((diff % 3600000) / 60000),
        seconds: Math.floor((diff % 60000) / 1000),
        expired: false
    };
}

function timerUnit(value, label) {
    return `
        <div class="ar-amazing-timer-unit">
            <span class="ar-amazing-timer-val">
                ${String(value).padStart(2, "0")}
            </span>
            <span class="ar-amazing-timer-label">
                ${label}
            </span>
        </div>
    `;
}

/* =========================================================
   RENDER
========================================================= */
function renderAmazingProducts(container, products) {
    container.innerHTML = products
        .map(createProductSlide)
        .join("");
}

function renderEmptyState(container) {
    container.innerHTML = `
        <div class="ar-amazing-empty">
            <div class="ar-amazing-empty-icon">
                <i class="fa-regular fa-circle-xmark"></i>
            </div>
            <p>محصولی موجود نیست</p>
        </div>
    `;
}

function createProductSlide(product) {
    const title = escapeHtml(product.name || product.title || "بدون عنوان");

    const discount = Number(product.discount) || 0;
    const hasDiscount = discount > 0;

    const price = Number(product.price) || 0;
    const finalPrice = hasDiscount
        ? price * (1 - discount / 100)
        : price;

    const imageUrl = product.imgUrl
        ? `/api/category/image/${encodeURIComponent(product.imgUrl)}`
        : "/assets/images/no-image.webp";

    const stock = Number(product.stock) || 0;

    let stockClass = "ar-product-stock";
    let stockLabel = `${stock} عدد`;

    if (stock === 0) {
        stockClass += " out";
        stockLabel = "ناموجود";
    } else if (stock < 5) {
        stockClass += " low";
        stockLabel = `${stock} عدد باقی`;
    }

    return `
        <div class="swiper-slide">
            <a href="/product/${encodeURIComponent(product.id)}" class="ar-amazing-product-card" aria-label="${title}">
                <div class="ar-product-image-wrapper">
                    <div class="ar-product-image">
                        <img
                            src="${imageUrl}"
                            alt="${title}"
                            loading="lazy"
                            decoding="async"
                            referrerpolicy="no-referrer"
                            onerror="this.onerror=null;this.src='/assets/images/no-image.webp';"
                        >
                    </div>

                    ${hasDiscount ? `
                        <span class="ar-product-discount-badge">
                            ${Math.round(discount)}%
                        </span>
                    ` : ""}
                </div>

                <h3 class="ar-amazing-product-title">
                    ${title}
                </h3>

                <div class="ar-product-prices">
                    ${hasDiscount ? `
                        <span class="ar-product-price-old">
                            ${formatPrice(price)}
                        </span>
                    ` : ""}

                    <span class="ar-amazing-product-price">
                        ${formatPrice(finalPrice)}
                        <span class="ar-amazing-product-price-currency">تومان</span>
                    </span>
                </div>

                <div class="ar-product-meta">
                    <span class="ar-product-meta-item">
                        <i class="fa-solid fa-box icon"></i>
                        <span class="value ${stockClass}">${stockLabel}</span>
                    </span>

                    <span class="ar-product-meta-item">
                        <i class="fa-regular fa-eye icon"></i>
                        <span class="value">${Number(product.views) || 0}</span>
                    </span>

                    <span class="ar-product-meta-item ar-product-rank">
                        <i class="fa-solid fa-star star"></i>
                        <span class="value">${Number(product.rank) || 0}</span>
                    </span>
                </div>
            </a>
        </div>
    `;
}

/* =========================================================
   SWIPER
========================================================= */
function initializeSwiper() {
    if (typeof Swiper === "undefined") {
        console.error("Swiper is not loaded");
        return;
    }

    if (amazingSwiper) {
        amazingSwiper.destroy(true, true);
        amazingSwiper = null;
    }

    amazingSwiper = new Swiper("#amazing-swiper", {
        slidesPerView: "auto",
        spaceBetween: 16,
        freeMode: true,
        observer: true,
        observeParents: true,
        preloadImages: false,
        grabCursor: true,
        simulateTouch: true,
        touchRatio: 1,
        touchAngle: 45,
        resistance: true,
        resistanceRatio: 0.85,
        mousewheel: {
            forceToAxis: true,
            sensitivity: 1,
            releaseOnEdges: true,
        },
        pagination: {
            el: ".ar-amazing-pagination",
            clickable: true,
            dynamicBullets: true,
            dynamicMainBullets: 5,
            renderBullet: function(index, className) {
                return `<span class="${className}"></span>`;
            }
        },
        breakpoints: {
            320: {
                spaceBetween: 12
            },
            768: {
                spaceBetween: 16
            },
            1280: {
                spaceBetween: 20
            }
        }
    });
}

/* =========================================================
   HELPERS
========================================================= */
function formatPrice(value) {
    return new Intl.NumberFormat("fa-IR").format(
        Math.round(Number(value) || 0)
    );
}

function escapeHtml(value) {
    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}

function hideAmazingSection() {
    stopTimer();

    if (amazingSwiper) {
        amazingSwiper.destroy(true, true);
        amazingSwiper = null;
    }

    const section = document.querySelector(".ar-amazing-section");
    if (section) {
        section.remove();
    }
}