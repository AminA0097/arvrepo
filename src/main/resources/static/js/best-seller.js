const API_PRO_URL = '/api/product/by-filter';
let swiperInstance = null;

function getWrapper() {
    return document.querySelector('#bestsellerSwiper .swiper-wrapper');
}

function createSkeleton(count = 6) {
    return Array.from({ length: count }, () => `
        <div class="swiper-slide">
            <div class="ar-bestseller-product-card">
                <div class="ar-bestseller-image-wrapper">
                    <div class="ar-bestseller-skeleton-image"></div>
                </div>
                <div class="ar-bestseller-skeleton-title"></div>
                <div class="ar-bestseller-skeleton-price"></div>
                <div class="ar-bestseller-skeleton-meta">
                    <div class="ar-bestseller-skeleton-meta-item"></div>
                    <div class="ar-bestseller-skeleton-meta-item"></div>
                    <div class="ar-bestseller-skeleton-meta-item"></div>
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

function renderError(message) {
    const wrapper = getWrapper();
    if (!wrapper) return;

    wrapper.innerHTML = `
        <div class="swiper-slide" style="width: 100%;">
            <div class="ar-bestseller-product-card" style="padding: 24px; text-align: center;">
                <p style="margin: 0; color: #ef4444; font-family: 'Shabnam', system-ui, sans-serif;">${message}</p>
            </div>
        </div>
    `;
}

function createProductCard(product, index) {
    const stockClass = "ar-best-stock"
    const rank = product.rank ?? (index + 1);
    const title = product.title || product.name || 'بدون عنوان';
    const price = Number(product.price || 0);
    const discount = Number(product.discount || 0);
    const finalPrice = discount > 0
        ? Math.round(price - (price * discount / 100))
        : price;

    // Determine rank badge class
    let rankClass = '';
    if (rank === 1) rankClass = 'gold';
    else if (rank === 2) rankClass = 'silver';
    else if (rank === 3) rankClass = 'bronze';

    return `
        <div class="swiper-slide">
            <a href="/shop/product/${product.id}" class="ar-bestseller-product-card">
                <div class="ar-bestseller-image-wrapper">
                    <div class="ar-bestseller-image">
                        <img 
                            src="${getImageUrl(product.imgUrl)}" 
                            alt="${title}" 
                            loading="lazy"
                            onerror="this.onerror=null;this.src='/images/no-image.png';"
                        >
                    </div>
                   
                    ${discount > 0 ? `
                    <span class="ar-bestseller-rank-badge">
                            <span class="value">${discount}%</span>
                        </span>
                    ` : ''}
                </div>

                <h3 class="ar-bestseller-product-title">${title}</h3>

                <div class="ar-bestseller-product-price">
                    ${formatPrice(finalPrice)}
                    <span class="ar-bestseller-product-price-currency">تومان</span>
                </div>

                <div class="ar-product-meta">
                    <span class="ar-bestseller-meta-item">
                        <i class="fa-solid fa-box icon"></i>
                        <span class="${stockClass}">${product.stock}
                        <span>عدد</span>
                        </span>
                    </span>

                    <span class="ar-bestseller-meta-item">
                        <i class="fa-regular fa-eye icon"></i>
                        <span class="value">${Number(product.views) || 0}</span>
                    </span>

                    <span class="ar-bestseller-meta-item ar-product-rank">
                        <i class="fa-solid fa-star star"></i>
                        <span class="value">${Number(product.rank) || 0}</span>
                    </span>
                </div>
            </a>
        </div>
    `;
}

function initSwiper() {
    const container = document.querySelector('#bestsellerSwiper');
    if (!container) return;

    if (typeof Swiper === 'undefined') {
        console.warn('Swiper library not loaded');
        return;
    }

    if (swiperInstance) {
        swiperInstance.destroy(true, true);
        swiperInstance = null;
    }

    swiperInstance = new Swiper('#bestsellerSwiper', {
        slidesPerView: 'auto',
        spaceBetween: 16,
        centeredSlides: false,
        loop: false,
        observer: true,
        observeParents: true,
        preloadImages: false,
        lazy: true,
        pagination: {
            el: '.ar-bestseller-pagination',
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

async function fetchBestSellers() {
    const wrapper = getWrapper();
    if (!wrapper) {
        console.warn('bestseller wrapper not found');
        return;
    }

    wrapper.innerHTML = createSkeleton(6);
    initSwiper();

    try {
        const response = await fetch(API_PRO_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                filter: '@@e.views;gt;30@@',
                sortBy: '',
                sortDir: '',
                pageNo: 1,
                pageSize: 10
            })
        });

        if (!response.ok) {
            throw new Error(`HTTP ${response.status}`);
        }

        const result = await response.json();
        console.log('API Result:', result);

        if (!result.success) {
            throw new Error(result.message || 'درخواست ناموفق بود');
        }

        const products = result?.data?.data ?? result?.data ?? [];

        if (!Array.isArray(products)) {
            throw new Error('products array پیدا نشد');
        }

        if (products.length === 0) {
            renderError('محصولی یافت نشد');
            initSwiper();
            return;
        }

        wrapper.innerHTML = products
            .map((product, index) => createProductCard(product, index))
            .join('');

        initSwiper();

    } catch (error) {
        console.error('Fetch error:', error);
        renderError('خطا در دریافت پرفروش‌ترین‌ها');
        initSwiper();
    }
}

// Auto-initialize
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', fetchBestSellers);
} else {
    fetchBestSellers();
}