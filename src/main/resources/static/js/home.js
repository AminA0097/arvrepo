const categories = [
    {
        id: 1,
        faName: "کفش",
        image: "https://picsum.photos/200?1"
    },
    {
        id: 2,
        faName: "کیف",
        image: "https://picsum.photos/200?2"
    },
    {
        id: 3,
        faName: "اکسسوری",
        image: "https://picsum.photos/200?3"
    },
    {
        id: 4,
        faName: "ساعت",
        image: "https://picsum.photos/200?4"
    },
    {
        id: 5,
        faName: "لباس",
        image: "https://picsum.photos/200?5"
    },
    {
        id: 6,
        faName: "شلوار",
        image: "https://picsum.photos/200?6"
    },
    {
        id: 7,
        faName: "کاپشن",
        image: "https://picsum.photos/200?7"
    },
    {
        id: 8,
        faName: "عینک",
        image: "https://picsum.photos/200?8"
    }
];

document.addEventListener("DOMContentLoaded", () => {

    renderCategories();

    new Swiper(".categorySwiper", {

        slidesPerView: 2.2,

        spaceBetween: 15,

        grabCursor: true,

        freeMode: true,

        breakpoints: {

            480: {
                slidesPerView: 3.2
            },

            768: {
                slidesPerView: 4.2
            },

            992: {
                slidesPerView: 5.2
            },

            1200: {
                slidesPerView: 7
            }
        }
    });
});

function renderCategories() {

    const wrapper =
        document.getElementById("categoryWrapper");

    let html = "";

    categories.forEach(category => {

        html += `
        
            <div class="swiper-slide">

                <a href="#" class="category-card">

                    <div class="category-image">
                        <img src="${category.image}"
                             alt="${category.faName}">
                    </div>

                    <div class="category-name">
                        ${category.faName}
                    </div>

                </a>

            </div>
        `;
    });

    wrapper.innerHTML = html;
}