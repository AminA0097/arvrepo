// swiper-init.js
document.addEventListener('DOMContentLoaded', () => {
    // ===== Amazing Swiper =====
    document.querySelectorAll('[data-amazing-swiper]').forEach(swiperEl => {
        new Swiper(swiperEl, {
            slidesPerView: 'auto',
            spaceBetween: 12,
            navigation: {
                nextEl: swiperEl.closest('.ar-amazing-products-wrap')
                    ?.querySelector('.ar-product-nav--next'),
                prevEl: swiperEl.closest('.ar-amazing-products-wrap')
                    ?.querySelector('.ar-product-nav--prev'),
            },
            breakpoints: {
                320: { spaceBetween: 8 },
                576: { spaceBetween: 12 },
                768: { spaceBetween: 16 }
            }
        });
    });

    // ===== Product Swiper (برای پرفروش‌ها و سایر محصولات) =====
    document.querySelectorAll('[data-product-swiper]').forEach(swiperEl => {
        const container = swiperEl.closest('.ar-product-section-wrap');

        new Swiper(swiperEl, {
            slidesPerView: 1.2,
            spaceBetween: 16,
            navigation: {
                nextEl: container?.querySelector('.ar-product-nav--next'),
                prevEl: container?.querySelector('.ar-product-nav--prev'),
            },
            breakpoints: {
                576: {
                    slidesPerView: 2.2
                },
                768: {
                    slidesPerView: 3.2
                },
                992: {
                    slidesPerView: 4.2
                }
            }
        });
    });
});