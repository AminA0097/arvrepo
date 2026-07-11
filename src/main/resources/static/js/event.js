const STORIES = {
    API: {
        PAGE_SIZE: 50,
        PAGE_NO: 0,
        ENDPOINT: '/api/event/get-all',
    },
    UI: {
        MODAL_TIMEOUT: 10000,
        SKELETON_COUNT: 8,
    }
};

const SDOM = {
    get loading() { return document.getElementById('storiesLoading'); },
    get error() { return document.getElementById('storiesError'); },
    get empty() { return document.getElementById('storiesEmpty'); },
    get content() { return document.getElementById('storiesContent'); },
    get list() { return document.getElementById('storiesList'); },
    get modal() { return document.getElementById('storyModal'); },
    get modalImage() { return document.getElementById('modalImage'); },
    get modalTitle() { return document.getElementById('modalTitle'); },
    get modalDesc() { return document.getElementById('modalDescription'); },
    get modalTime() { return document.getElementById('modalTime'); },
    get modalLocation() { return document.getElementById('modalLocation'); },
    get modalProgress() { return document.getElementById('modalProgress'); },
    get modalContent() { return document.getElementById('modalContent'); },
    get modalBackdrop() { return document.querySelector('.ar-modal-backdrop'); },
    get actionBtn() { return document.querySelector('.ar-modal-action-btn'); },
    get imageWrapper() { return document.querySelector('.ar-modal-image-wrapper'); },
    get retryBtn() { return document.querySelector('#storiesError .ar-btn'); },
};

let eventsData = [];
let viewedEvents = new Set();
let storyTimer = null;
let remainingTime = STORIES.UI.MODAL_TIMEOUT;
let startTime = 0;
let currentEventId = null;
let isInitialized = false;
let isLoading = false;

function markStoryAsViewed(eventId) {
    if (eventId) viewedEvents.add(eventId);
}

function isStoryViewed(eventId) {
    return viewedEvents.has(eventId);
}

function clearViewedCache() {
    viewedEvents.clear();
}

function getViewedCount() {
    return viewedEvents.size;
}

async function loadEvents() {
    if (isLoading) return;
    isLoading = true;

    try {
        showState('loading');
        const url = `${STORIES.API.ENDPOINT}?page=${STORIES.API.PAGE_NO}&size=${STORIES.API.PAGE_SIZE}`;
        const res = await fetch(url);

        if (!res.ok) throw new Error(`HTTP ${res.status}`);

        const json = await res.json();
        if (!json || !json.success) throw new Error(json?.message || 'Error');

        const events = json.data?.data || [];
        if (events.length === 0) {
            eventsData = [];
            showState('empty');
            return;
        }

        eventsData = events;
        renderEvents(eventsData);
        showState('content');
    } catch (error) {
        console.error(error);
        if (eventsData.length > 0) {
            renderEvents(eventsData);
            showState('content');
            showToast('warning', 'مشکل در ارتباط - نمایش نسخه قبلی');
        } else {
            showState('error');
        }
    } finally {
        isLoading = false;
    }
}

function renderEvents(events) {
    const list = SDOM.list;
    if (!list) return;

    list.innerHTML = '';
    if (!events || events.length === 0) {
        showState('empty');
        return;
    }

    events.forEach((event, index) => {
        const eventId = event.id;
        const viewed = isStoryViewed(eventId);
        const isActive = event.inGrid === true && !viewed;
        const imageUrl = `/api/category/image/${event.docId}`;

        const item = document.createElement('div');
        item.className = `ar-story-item ${isActive ? 'ar-story-item--active' : ''} ${viewed ? 'ar-story-item--viewed' : ''}`;
        item.dataset.id = eventId;
        item.dataset.title = event.title || 'بدون عنوان';
        item.dataset.image = imageUrl;
        item.dataset.description = event.description || '';
        item.dataset.date = event.date || event.time || '';
        item.dataset.location = event.location || event.venue || '';
        item.style.animationDelay = `${(index % 10) * 0.05}s`;

        item.innerHTML = `
            <div class="ar-story-ring">
                <div class="ar-story-avatar">
                    <img src="${imageUrl}" alt="${event.title || 'رویداد'}" loading="lazy" onerror="this.style.display='none'">
                </div>
                ${isActive ? '<span class="ar-story-badge">جدید</span>' : ''}
                ${viewed ? '<span class="ar-story-check"><i class="fa-solid fa-check"></i></span>' : ''}
            </div>
            <span class="ar-story-label">${event.title || 'رویداد'}</span>
        `;

        item.addEventListener('click', () => openStoryModal(item));
        list.appendChild(item);
    });
}

function showState(state) {
    const states = ['loading', 'error', 'empty', 'content'];
    states.forEach(s => {
        const el = document.getElementById(`stories${s.charAt(0).toUpperCase() + s.slice(1)}`);
        if (el) {
            el.style.display = s === state ? (s === 'content' ? 'block' : 'flex') : 'none';
        }
    });
}

function openStoryModal(element) {
    const modal = SDOM.modal;
    const image = SDOM.modalImage;
    const title = SDOM.modalTitle;
    const desc = SDOM.modalDesc;
    const timeEl = SDOM.modalTime;
    const locationEl = SDOM.modalLocation;
    const progress = SDOM.modalProgress;

    if (!modal || !image || !title || !desc || !timeEl || !locationEl || !progress) return;

    const eventId = element.dataset.id;
    currentEventId = eventId;

    const event = eventsData.find(e => String(e.id) === String(eventId)) || {};
    const imgSrc = element.dataset.image;

    image.src = imgSrc;
    image.alt = element.dataset.title || 'رویداد';
    title.textContent = element.dataset.title || 'بدون عنوان';
    desc.textContent = element.dataset.description || '';

    const date = element.dataset.date || event.date || event.time || '';
    const location = element.dataset.location || event.location || event.venue || '';

    timeEl.textContent = date || '--';
    locationEl.textContent = location || '--';

    const metaItems = document.querySelectorAll('.ar-modal-meta-item');
    if (metaItems.length >= 2) {
        metaItems[0].style.display = date ? 'flex' : 'none';
        metaItems[1].style.display = location ? 'flex' : 'none';
    }

    if (!isStoryViewed(eventId)) {
        markStoryAsViewed(eventId);
        element.classList.remove('ar-story-item--active');
        element.classList.add('ar-story-item--viewed');

        const badge = element.querySelector('.ar-story-badge');
        if (badge) badge.remove();

        const ring = element.querySelector('.ar-story-ring');
        if (ring && !ring.querySelector('.ar-story-check')) {
            const check = document.createElement('span');
            check.className = 'ar-story-check';
            check.innerHTML = '<i class="fa-solid fa-check"></i>';
            ring.appendChild(check);
        }
    }

    modal.classList.add('active');
    document.body.style.overflow = 'hidden';

    remainingTime = STORIES.UI.MODAL_TIMEOUT;
    progress.style.width = '0%';
    startTimer();
}

function closeStoryModal() {
    const modal = SDOM.modal;
    if (!modal) return;

    modal.classList.remove('active');
    document.body.style.overflow = 'auto';
    clearInterval(storyTimer);
    storyTimer = null;
    currentEventId = null;
}

function startTimer() {
    clearInterval(storyTimer);
    startTime = Date.now();
    const progress = SDOM.modalProgress;
    if (!progress) return;

    const timeout = remainingTime || STORIES.UI.MODAL_TIMEOUT;

    storyTimer = setInterval(() => {
        const elapsed = Date.now() - startTime;
        const totalDuration = STORIES.UI.MODAL_TIMEOUT;
        const percent = Math.min(((totalDuration - timeout + elapsed) / totalDuration) * 100, 100);
        progress.style.width = `${percent}%`;

        if (elapsed >= timeout) {
            clearInterval(storyTimer);
            storyTimer = null;
            remainingTime = STORIES.UI.MODAL_TIMEOUT;
            closeStoryModal();
        }
    }, 50);
}

function pauseTimer() {
    if (storyTimer) {
        clearInterval(storyTimer);
        storyTimer = null;
        const elapsed = Date.now() - startTime;
        remainingTime = Math.max(remainingTime - elapsed, 0);
    }
}

function resumeTimer() {
    if (remainingTime > 0 && !storyTimer) {
        startTimer();
    }
}

function setupEventListeners() {
    const modalContent = SDOM.modalContent;
    if (modalContent) {
        modalContent.addEventListener('mouseenter', pauseTimer);
        modalContent.addEventListener('mouseleave', resumeTimer);
        modalContent.addEventListener('touchstart', pauseTimer, { passive: true });
        modalContent.addEventListener('touchend', resumeTimer, { passive: true });
    }

    const backdrop = SDOM.modalBackdrop;
    if (backdrop) {
        backdrop.addEventListener('click', closeStoryModal);
    }

    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape') closeStoryModal();
    });

    const actionBtn = SDOM.actionBtn;
    if (actionBtn) {
        actionBtn.addEventListener('click', function (e) {
            e.stopPropagation();
            const title = SDOM.modalTitle?.textContent || '';
            showToast('success', `ثبت‌نام در "${title}" با موفقیت انجام شد`);
            closeStoryModal();
        });
    }

    const imageWrapper = SDOM.imageWrapper;
    if (imageWrapper) {
        imageWrapper.addEventListener('click', function (e) {
            if (e.target.tagName === 'IMG') {
                closeStoryModal();
            }
        });
    }

    const retryBtn = SDOM.retryBtn;
    if (retryBtn) {
        retryBtn.addEventListener('click', () => loadEvents());
    }
}

// تابع initEvents که از base.js صدا زده میشه
async function initEvents() {
    if (isInitialized) return;
    isInitialized = true;
    setupEventListeners();
    await loadEvents();
}

// صادر کردن توابع برای استفاده در جای دیگه
window.loadEvents = loadEvents;
window.openStoryModal = openStoryModal;
window.closeStoryModal = closeStoryModal;
window.clearViewedCache = clearViewedCache;
window.getViewedCount = getViewedCount;
window.initEvents = initEvents;