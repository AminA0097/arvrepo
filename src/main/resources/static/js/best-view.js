const API_MOST_VIEWS_URL = '/api/product/by-filter';
let mostViewsSwiperInstance = null;

function getMostViewsWrapper() {
    return document.querySelector('#mostviewsSwiper .swiper-wrapper');
}

function createMostViewsSkeleton(count = 6) {
    return Array.from({ length: count }, () => `
        <div class="swiper-slide">
            <div class="ar-mostviews-product-card">
                <div class="ar-mostviews-image-wrapper">
                    <div class="ar-mostviews-skeleton-image"></div>
                </div>
                <div class="ar-mostviews-skeleton-title"></div>
                <div class="ar-mostviews-skeleton-price"></div>
                <div class="ar-mostviews-skeleton-meta">
                    <div class="ar-mostviews-skeleton-meta-item"></div>
                    <div class="ar-mostviews-skeleton-meta-item"></div>
                    <div class="ar-mostviews-skeleton-meta-item"></div>
                </div>
            </div>
        </div>
    `).join('');
}

function formatPrice(value) {
    return Number(value || 0).toLocaleString('fa-IR');
}

function getImageUrl(imgUrl) {
    if (!imgUrl) return '/images/no-image.png';
    return `/api/category/image/${encodeURIComponent(imgUrl)}`;
}

function renderMostViewsError(message) {
    const wrapper = getMostViewsWrapper();
    if (!wrapper) return;

    wrapper.innerHTML = `
        <div class="swiper-slide" style="width: 100%;">
            <div class="ar-mostviews-product-card" style="padding: 24px; text-align: center;">
                <p style="margin: 0; color: #ef4444; font-family: 'Shabnam', system-ui, sans-serif;">${message}</p>
            </div>
        </div>
    `;
}

function createMostViewsCard(product, index) {
    const rank = product.rank ?? (index + 1);
    const title = product.title || product.name || 'بدون عنوان';
    const price = Number(product.price || 0);
    const discount = Number(product.discount || 0);
    const finalPrice = discount > 0
        ? Math.round(price - (price * discount / 100))
        : price;
    const views = Number(product.views || 0);

    // Determine rank badge class
    let rankClass = '';
    if (rank === 1) rankClass = 'gold';
    else if (rank === 2) rankClass = 'silver';
    else if (rank === 3) rankClass = 'bronze';

    return `
        <div class="swiper-slide">
            <a href="/shop/product/${product.id}" class="ar-mostviews-product-card">
                <div class="ar-mostviews-image-wrapper">
                    <div class="ar-mostviews-image">
                        <img 
                            src="${getImageUrl(product.imgUrl)}" 
                            alt="${title}" 
                            loading="lazy"
                            onerror="this.onerror=null;this.src='/images/no-image.png';"
                        >
                    </div>
                    <span class="ar-mostviews-rank-badge ${rankClass}">
                        ${rank}
                    </span>
                </div>

                <h3 class="ar-mostviews-product-title">${title}</h3>

                <div class="ar-mostviews-product-price">
                    ${formatPrice(finalPrice)}
                    <span class="ar-mostviews-product-price-currency">تومان</span>
                </div>

                <div class="ar-mostviews-product-meta">
                    <span class="ar-mostviews-meta-item views-highlight">
                        <i class="fa-regular fa-eye icon"></i>
                        <span class="value">${formatPrice(views)}</span>
                    </span>
                    <span class="ar-mostviews-meta-item">
                        <i class="fa-regular fa-heart icon"></i>
                        <span class="value">product.stock || 0)}
                        </span>
                    </span>
                    ${discount > 0 ? `
                        <span class="ar-mostviews-meta-item discount">
                            <i class="fa-solid fa-tag icon"></i>
                            <span class="value">${discount}%</span>
                        </span>
                    ` : ''}
                </div>
            </a>
        </div>
    `;
}

function initMostViewsSwiper() {
    const container = document.querySelector('#mostviewsSwiper');
    if (!container) return;

    if (typeof Swiper === 'undefined') {
        console.warn('Swiper library not loaded');
        return;
    }

    if (mostViewsSwiperInstance) {
        mostViewsSwiperInstance.destroy(true, true);
        mostViewsSwiperInstance = null;
    }

    mostViewsSwiperInstance = new Swiper('#mostviewsSwiper', {
        slidesPerView: 'auto',
        spaceBetween: 16,
        centeredSlides: false,
        loop: false,
        observer: true,
        observeParents: true,
        preloadImages: false,
        lazy: true,
        pagination: {
            el: '.ar-mostviews-pagination',
            clickable: true,
            dynamicBullets: true,
        },
        breakpoints: {
            320: {
                slidesPerView: 1.2,
                spaceBetween: 12
            },
            480: {
                slidesPerView: 2.2,
                spaceBetween: 12
            },
            768: {
                slidesPerView: 3.2,
                spaceBetween: 16
            },
            1024: {
                slidesPerView: 4.5,
                spaceBetween: 16
            },
            1280: {
                slidesPerView: 5.5,
                spaceBetween: 16
            },
        }
    });
}

async function fetchMostViews() {
    const wrapper = getMostViewsWrapper();
    if (!wrapper) {
        console.warn('mostviews wrapper not found');
        return;
    }

    wrapper.innerHTML = createMostViewsSkeleton(6);
    initMostViewsSwiper();

    try {
        const response = await fetch(API_MOST_VIEWS_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                filter: '',
                sortBy: 'e.rank',
                sortDir: 'desc',
                pageNo: 1,
                pageSize: 10
            })
        });

        if (!response.ok) {
            throw new Error(`HTTP ${response.status}`);
        }

        const result = await response.json();
        console.log('Most Views API Result:', result);

        if (!result.success) {
            throw new Error(result.message || 'درخواست ناموفق بود');
        }

        const products = result?.data?.data ?? result?.data ?? [];

        if (!Array.isArray(products)) {
            throw new Error('products array پیدا نشد');
        }

        if (products.length === 0) {
            renderMostViewsError('محصولی یافت نشد');
            initMostViewsSwiper();
            return;
        }

        wrapper.innerHTML = products
            .map((product, index) => createMostViewsCard(product, index))
            .join('');

        initMostViewsSwiper();

    } catch (error) {
        console.error('Fetch error:', error);
        renderMostViewsError('خطا در دریافت پربازدیدترین‌ها');
        initMostViewsSwiper();
    }
}

// Auto-initialize
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', fetchMostViews);
} else {
    fetchMostViews();
}