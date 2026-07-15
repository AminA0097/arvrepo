/**
 * Product Sections - اسلایدرهای محصولات + تایمر Amazing
 */

document.addEventListener('DOMContentLoaded', function() {

    const timerElement = document.querySelector('.ar-amazing-timer');

    if (timerElement) {
        const amazingExpireTime = timerElement.dataset.expire;

        console.log('Expire time raw:', amazingExpireTime); // دیباگ

        if (amazingExpireTime) {
            const expireTime = new Date(amazingExpireTime);
            console.log('Expire time parsed:', expireTime); // دیباگ

            // بررسی اینکه تاریخ معتبر هست یا نه
            if (isNaN(expireTime.getTime())) {
                console.error('Invalid date format:', amazingExpireTime);
                return;
            }

            function updateTimer() {
                const now = new Date();
                const diff = expireTime - now;

                if (diff <= 0) {
                    document.getElementById('amazing-hours').textContent = '00';
                    document.getElementById('amazing-minutes').textContent = '00';
                    document.getElementById('amazing-seconds').textContent = '00';
                    return;
                }

                const hours = Math.floor(diff / (1000 * 60 * 60));
                const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
                const seconds = Math.floor((diff % (1000 * 60)) / 1000);

                document.getElementById('amazing-hours').textContent = String(hours).padStart(2, '0');
                document.getElementById('amazing-minutes').textContent = String(minutes).padStart(2, '0');
                document.getElementById('amazing-seconds').textContent = String(seconds).padStart(2, '0');
            }

            updateTimer();
            setInterval(updateTimer, 1000);
        }
    }

    // ===== Product Swiper =====
    const productSwipers = document.querySelectorAll('[data-product-swiper]');

    productSwipers.forEach(swiperEl => {
        new Swiper(swiperEl, {
            slidesPerView: 'auto',
            spaceBetween: 16,
            freeMode: {
                enabled: true,
                momentum: true,
            },
            navigation: {
                nextEl: swiperEl.closest('.ar-product-swiper-wrap').querySelector('.ar-product-nav--next'),
                prevEl: swiperEl.closest('.ar-product-swiper-wrap').querySelector('.ar-product-nav--prev'),
            },
            breakpoints: {
                320: { spaceBetween: 8 },
                576: { spaceBetween: 12 },
                768: { spaceBetween: 14 },
                992: { spaceBetween: 16 }
            },
            watchSlidesProgress: true,
        });
    });

    // ===== Amazing Swiper =====
    const amazingSwiperEl = document.querySelector('[data-amazing-swiper]');

    if (amazingSwiperEl) {
        new Swiper(amazingSwiperEl, {
            slidesPerView: 'auto',
            spaceBetween: 12,
            freeMode: true,
            pagination: {
                el: '.ar-amazing-pagination',
                clickable: true,
                dynamicBullets: true,
            },
            breakpoints: {
                320: {
                    spaceBetween: 8,
                    slidesPerView: 1.5
                },
                480: {
                    spaceBetween: 10,
                    slidesPerView: 2
                },
                768: {
                    spaceBetween: 12,
                    slidesPerView: 3
                },
                1024: {
                    spaceBetween: 16,
                    slidesPerView: 4
                },
                1280: {
                    spaceBetween: 16,
                    slidesPerView: 5
                }
            },
            watchSlidesProgress: true,
        });
    }
});