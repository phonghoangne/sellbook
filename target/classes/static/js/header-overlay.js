const search = document.querySelector('.search');
    const headerSearchOverlay = document.querySelector('.overlay__header-search');
    const navbar = document.querySelector('.container__header-overlay');

    search.addEventListener('click', () => {
        headerSearchOverlay.classList.add('active');
        console.log(search);
    })

    headerSearchOverlay.addEventListener('click', () => {
        headerSearchOverlay.classList.remove('active');
    })

    navbar.addEventListener('click', (e) => {
        e.stopPropagation();
    })

const scrollTop = document.querySelector(".scroll-top");
window.addEventListener("scroll", (event) => {
    let scroll = this.scrollY;
    if (scroll > 0) {
        scrollTop.style.display = "block";
    } else {
        scrollTop.style.display = "none";
    }

    console.log(scroll);
});

scrollTop.addEventListener("click", (event) => {
    window.scrollTo({
        top: 0,
        behavior: "smooth",
    });
});

