document.addEventListener('DOMContentLoaded', function() {
    let scrollIcon = document.getElementById('scrollIcon');
    let catSection = document.querySelector('.cat');
    scrollIcon.addEventListener('click', function(event) {
        event.preventDefault();
        let catPosition = catSection.offsetTop;
        window.scrollTo({
            top: catPosition,
            behavior: 'smooth'
        });
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const showMoreButton = document.getElementById('showMoreButton');
    const cards = document.querySelectorAll('.card');
    const cardsToShow = 9;
    let visibleCards = cardsToShow;

    showMoreButton.addEventListener('click', function () {
        for (let i = visibleCards; i < visibleCards + cardsToShow && i < cards.length; i++) {
            cards[i].classList.remove('d-none');
        }
        visibleCards += cardsToShow;

        if (visibleCards >= cards.length) {
            showMoreButton.style.display = 'none';
        }
    });

    function showCardsSequentially() {
        cards.forEach(function (card, index) {
            if (index < visibleCards) {
                card.classList.remove('d-none');
            } else {
                card.classList.add('d-none');
            }
        });
    }
    showCardsSequentially();

    if (cards.length <= cardsToShow) {
        showMoreButton.style.display = 'none';
    }
});

document.getElementById('searchInput').addEventListener('input', function () {
    const searchQuery = this.value.trim().toLowerCase();
    const cards = document.querySelectorAll('.card');
    const showMoreButton = document.getElementById('showMoreButton');
    const notFoundMessage = document.getElementById('notFoundMessage');
    let foundItems = false;

    cards.forEach(function (card) {
        const cardTitle = card.querySelector('.card-title').textContent.trim().toLowerCase();
        if (cardTitle.includes(searchQuery)) {
            card.style.display = 'block';
            foundItems = true;
        } else {
            card.style.display = 'none';
            showMoreButton.style.display = 'none';
        }
    });

    if (!foundItems) {
        notFoundMessage.style.display = 'block';
    } else {
        notFoundMessage.style.display = 'none';
    }
});

document.getElementById('searchInput').addEventListener('keypress', function (event) {
    if (event.key === 'Enter') {
        const featuretteBlock = document.querySelector('.cat');
        featuretteBlock.scrollIntoView({behavior: 'smooth'});
    }
});

let username = "";

function getRandomColor() {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}

document.addEventListener('DOMContentLoaded', function () {
    username = document.querySelector('meta[name="username"]').getAttribute('content');

    const userInitial = document.getElementById('userInitial');
    let userColor = localStorage.getItem(username + '_color');

    if (!userColor) {
        userColor = getRandomColor();
        localStorage.setItem(username + '_color', userColor);
    }

    if (username) {
        const firstLetter = username.charAt(0).toUpperCase();
        userInitial.innerText = firstLetter;
        userInitial.style.backgroundColor = userColor;
    }
});

document.getElementById('userInitial').addEventListener('click', function () {
    window.location.href = "/profile";
});