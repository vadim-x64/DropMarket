document.getElementById('scrollIcon').addEventListener('click', function () {
    const container = document.querySelector('.cat');
    container.scrollIntoView({behavior: 'smooth'});
});


document.addEventListener('DOMContentLoaded', function () {
    const showMoreButton = document.getElementById('showMoreButton');
    const cardsContainer = document.querySelector('.cat');
    const cards = document.querySelectorAll('.card');
    const cardsToShow = 9;
    let visibleCards = cardsToShow;

    showMoreButton.addEventListener('click', function () {
        for (let i = visibleCards; i < visibleCards + cardsToShow && i < cards.length; i++) {
            cards[i].classList.remove('d-none');
        }

        visibleCards += cardsToShow;

        // Перевіряємо, чи всі карточки вже відображені
        if (visibleCards >= cards.length) {
            showMoreButton.style.display = 'none';
        }
    });

    // Функція для відображення карточок товарів одну за одною
    function showCardsSequentially() {
        cards.forEach(function (card, index) {
            if (index < visibleCards) {
                card.classList.remove('d-none');
            } else {
                card.classList.add('d-none');
            }
        });
    }

    // Приховує всі карточки, крім перших cardsToShow
    showCardsSequentially();

    // Перевіряє, чи потрібно відображати кнопку "Показати ще"
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
    // Перевірка, чи натиснута клавіша Enter
    if (event.key === 'Enter') {
        // Отримання блока, до якого потрібно прокрутити
        const featuretteBlock = document.querySelector('.cat');
        // Плавний перехід до блока
        featuretteBlock.scrollIntoView({behavior: 'smooth'});
    }
});


// Оголошуємо глобальну змінну для username
let username = "";

// Функція для генерації випадкового кольору
function getRandomColor() {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}

document.addEventListener('DOMContentLoaded', function () {
    // Отримуємо значення username з метаданих сторінки
    username = document.querySelector('meta[name="username"]').getAttribute('content');

    const userInitial = document.getElementById('userInitial');

    // Перевірка, чи вже був збережений колір для цього користувача
    let userColor = localStorage.getItem(username + '_color');

    // Генерація нового кольору, якщо він ще не збережений
    if (!userColor) {
        userColor = getRandomColor();
        // Збереження кольору для поточного користувача
        localStorage.setItem(username + '_color', userColor);
    }

    // Встановлення кольору та першої букви для віконця акаунту
    if (username) {
        const firstLetter = username.charAt(0).toUpperCase();
        userInitial.innerText = firstLetter;
        userInitial.style.backgroundColor = userColor;
    }
});

document.getElementById('userInitial').addEventListener('click', function () {
    window.location.href = "/profile";
});