const PRODUCT_API = {
    DETAIL: '/api/product/by-id/',
    GALLERY: '/api/product/gallery/',
    IMAGE: '/api/category/image/',
    VARIANTS:'/api/variants/by-id'
};

const productState = {
    product: null,
    productId: null,
    gallery: []
};

function formatPrice(value) {
    return new Intl.NumberFormat('fa-IR').format(value || 0);
}

function calculateFinalPrice(price, discount) {
    if (!discount || discount <= 0) return price;
    return Math.round(price - (price * discount / 100));
}

async function loadProduct(id) {
    try {
        const response = await fetch(`${PRODUCT_API.DETAIL}${id}`);
        const result = await response.json();

        console.log(result);

        if (result.success && result.data) {
            productState.product = result.data;

            setBreadcrumb();
            updateProductPrice();
            updateStock();
            await loadGallery();
            updatePro()
        } else {
            showToast("error", result.message || "دریافت اطلاعات محصول ناموفق بود");
        }
    } catch (e) {
        console.error('Error loading product:', e);
        showToast("error", "خطا در دریافت اطلاعات محصول");
    }
}
function getImageUrl(imageId) {
    return `${PRODUCT_API.IMAGE}${imageId}`;
}
async function loadGallery() {
    try {
        const response = await fetch(`${PRODUCT_API.GALLERY}${productState.productId}`);
        const result = await response.json();

        const images = [];

        if (productState.product?.imgUrl) {
            images.push(productState.product.imgUrl);
        }

        if (result.success && Array.isArray(result.data)) {
            images.push(...result.data);
        }

        productState.gallery = [...new Set(images)];
        renderGallery();
    } catch (e) {
        console.error('Error loading gallery:', e);

        if (productState.product?.imgUrl) {
            productState.gallery = [productState.product.imgUrl];
            renderGallery();
        } else {
            showToast("error", "خطا در دریافت گالری محصول");
        }
    }
}

function renderGallery() {
    const mainImage = document.getElementById('mainProductImage');
    const thumbnailContainer = document.getElementById('thumbnailContainer');
    const product = productState.product;

    if (!mainImage || !thumbnailContainer || !productState.gallery.length) return;

    mainImage.src = getImageUrl(productState.gallery[0]);
    mainImage.alt = product?.title || 'تصویر محصول';

    thumbnailContainer.innerHTML = '';

    productState.gallery.forEach((imageId, index) => {
        const imageUrl = getImageUrl(imageId);

        thumbnailContainer.insertAdjacentHTML('beforeend', `
            <button type="button"
                    class="thumbnail-btn h-16 w-16 shrink-0 overflow-hidden rounded-xl border-2 transition ${
            index === 0 ? 'border-leather-500' : 'border-leather-200'
        }"
                    data-image-url="${imageUrl}"
                    data-index="${index}">
                <img src="${imageUrl}"
                     alt="${product?.title || 'تصویر محصول'}"
                     class="h-full w-full object-cover">
            </button>
        `);
    });

    bindThumbnailEvents();
}

function bindThumbnailEvents() {
    const mainImage = document.getElementById('mainProductImage');
    const buttons = document.querySelectorAll('.thumbnail-btn');

    buttons.forEach((button) => {
        button.addEventListener('click', function () {
            const imageUrl = this.dataset.imageUrl;

            mainImage.src = imageUrl;

            buttons.forEach(btn => {
                btn.classList.remove('border-leather-500');
                btn.classList.add('border-leather-200');
            });

            this.classList.remove('border-leather-200');
            this.classList.add('border-leather-500');
        });
    });
}

function updateActiveThumbnail(activeIndex) {
    document.querySelectorAll('.thumbnail-btn').forEach((btn, index) => {
        if (index === activeIndex) {
            btn.classList.remove('border-leather-200');
            btn.classList.add('border-leather-500');
        } else {
            btn.classList.remove('border-leather-500');
            btn.classList.add('border-leather-200');
        }
    });
}
function updateStock() {
    const stockEl = document.getElementById("stockCount");
    if (stockEl && productState.product) {
        stockEl.textContent = productState.product.stock ?? 0;
    }
}
function updatePro(){
    var pro = productState.product

    const proTitle = document.getElementById("title");
    const proDesc = document.getElementById("desc");
    const proRating = document.getElementById("rating");

    proTitle.textContent = pro.title
    proDesc.textContent = pro.desc
    proRating.textContent = pro.rank


}
function updateProductPrice() {
    const product = productState.product;
    if (!product) return;

    const oldPriceContainer = document.getElementById("oldPriceContainer");
    const oldPriceEl = document.getElementById("oldPrice");
    const finalPriceEl = document.getElementById("finalPrice");
    const discountBadgeEl = document.getElementById("discountBadge");

    const price = product.price || 0;
    const discount = product.discount || 0;
    const finalPrice = calculateFinalPrice(price, discount);

    if (finalPriceEl) {
        finalPriceEl.textContent = formatPrice(finalPrice);
    }

    if (discount > 0) {
        if (oldPriceEl) {
            oldPriceEl.textContent = formatPrice(price);
        }
        if (discountBadgeEl) {
            discountBadgeEl.textContent = `${discount}%`;
            discountBadgeEl.classList.remove('hidden');
        }
        if (oldPriceContainer) {
            oldPriceContainer.classList.remove('hidden');
        }
    } else {
        if (discountBadgeEl) {
            discountBadgeEl.classList.add('hidden');
        }
        if (oldPriceContainer) {
            oldPriceContainer.classList.add('hidden');
        }
    }
}

function setBreadcrumb() {
    const categoryEl = document.getElementById('breadcrumb-category');
    const titleEl = document.getElementById('breadcrumb-title');
    const product = productState.product;

    if (!product) return;

    if (categoryEl) {
        categoryEl.textContent = product.categoryTitle || 'بدون دسته';
    }

    if (titleEl) {
        titleEl.textContent = product.title || 'بدون عنوان';
    }
}
document.addEventListener('DOMContentLoaded', function () {
    const productEl = document.querySelector('[data-product-id]');
    const productId = productEl?.dataset.productId;

    if (productId) {
        productState.productId = productId;
        loadProduct(productId);
    } else {
        console.error('Product ID not found');
    }
});