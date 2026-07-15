document.addEventListener("DOMContentLoaded", () => {
    // Initialize Lucide icons if available
    if (typeof lucide !== 'undefined') {
        lucide.createIcons();
    }

    initMobileMenu();
    initUserDropdown();
    initCloseMenusOnEscape();
    initCartCount();
});

/**
 * Mobile menu toggle
 */
function initMobileMenu() {
    const toggleBtn = document.querySelector("[data-mobile-menu-toggle]");
    const mobileMenu = document.querySelector("[data-mobile-menu]");

    if (!toggleBtn || !mobileMenu) return;

    toggleBtn.addEventListener("click", (event) => {
        event.stopPropagation();

        toggleBtn.classList.toggle("is-active");
        mobileMenu.classList.toggle("is-open");
    });

    document.addEventListener("click", (event) => {
        const clickedInsideMenu = mobileMenu.contains(event.target);
        const clickedToggle = toggleBtn.contains(event.target);

        if (!clickedInsideMenu && !clickedToggle) {
            toggleBtn.classList.remove("is-active");
            mobileMenu.classList.remove("is-open");
        }
    });
}

/**
 * User dropdown toggle
 */
function initUserDropdown() {
    const toggleBtn = document.querySelector("[data-user-menu-toggle]");
    const dropdown = document.querySelector("[data-user-menu]");

    if (!toggleBtn || !dropdown) return;

    toggleBtn.addEventListener("click", (event) => {
        event.stopPropagation();
        dropdown.classList.toggle("is-open");
    });

    document.addEventListener("click", (event) => {
        const clickedInsideDropdown = dropdown.contains(event.target);
        const clickedToggle = toggleBtn.contains(event.target);

        if (!clickedInsideDropdown && !clickedToggle) {
            dropdown.classList.remove("is-open");
        }
    });
}

/**
 * Close open menus with Escape
 */
function initCloseMenusOnEscape() {
    document.addEventListener("keydown", (event) => {
        if (event.key !== "Escape") return;

        const mobileBtn = document.querySelector("[data-mobile-menu-toggle]");
        const mobileMenu = document.querySelector("[data-mobile-menu]");
        const userDropdown = document.querySelector("[data-user-menu]");

        if (mobileBtn) {
            mobileBtn.classList.remove("is-active");
        }

        if (mobileMenu) {
            mobileMenu.classList.remove("is-open");
        }

        if (userDropdown) {
            userDropdown.classList.remove("is-open");
        }
    });
}

/**
 * Update cart count
 */
function initCartCount() {
    const cartCountElements = document.querySelectorAll('.cart-count');
    if (!cartCountElements.length) return;

    // Check if we have a cart count from server
    const firstElement = cartCountElements[0];
    const currentCount = parseInt(firstElement.textContent) || 0;

    // If count is 0, we can try to fetch from API
    if (currentCount === 0) {
        fetchCartCount();
    }

    // Set up event listener for cart updates (optional)
    document.addEventListener('cartUpdated', (event) => {
        updateCartCountDisplay(event.detail.count);
    });
}

/**
 * Fetch cart count from API
 */
function fetchCartCount() {
    fetch('/api/cart/count')
        .then(response => {
            if (!response.ok) throw new Error('Network response was not ok');
            return response.json();
        })
        .then(data => {
            updateCartCountDisplay(data.count || 0);
        })
        .catch(error => {
            console.log('Cart count fetch failed:', error);
            // Keep existing count or show 0
        });
}

/**
 * Update cart count display across all elements
 */
function updateCartCountDisplay(count) {
    document.querySelectorAll('.cart-count').forEach(el => {
        el.textContent = count;
    });
}

/**
 * Utility function to add to cart and update count
 */
function addToCart(productId, quantity = 1) {
    fetch('/api/cart/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ productId, quantity })
    })
        .then(response => {
            if (!response.ok) throw new Error('Failed to add to cart');
            return response.json();
        })
        .then(data => {
            // Update cart count
            updateCartCountDisplay(data.cartCount);
            // Dispatch event for other components
            document.dispatchEvent(new CustomEvent('cartUpdated', {
                detail: { count: data.cartCount }
            }));
        })
        .catch(error => {
            console.error('Error adding to cart:', error);
        });
}