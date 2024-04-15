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

document.addEventListener("DOMContentLoaded", function() {
    var products = document.querySelectorAll(".card");
    var productsHeading = document.getElementById("productsHeading");

    if (products.length === 0) {
        productsHeading.style.display = "none";
    } else {
        productsHeading.style.display = "flex";
    }
});