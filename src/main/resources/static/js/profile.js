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
    let products = document.querySelectorAll(".card");
    let productsHeading = document.getElementById("productsHeading");

    if (products.length === 0) {
        productsHeading.style.display = "none";
    } else {
        productsHeading.style.display = "flex";
    }
});

document.getElementById('firstName').addEventListener('input', function(event) {
    this.value = this.value.replace(/[^a-zA-Zа-яА-ЯґҐєЄіІїЇёЁ'\s]/g, '');
});

document.getElementById('lastName').addEventListener('input', function(event) {
    this.value = this.value.replace(/[^a-zA-Zа-яА-ЯґҐєЄіІїЇёЁ'\s]/g, '');
});


function confirmDelete(event) {
    event.preventDefault(); // Prevent default form submission
    if (confirm("Ви впевнені, що хочете видалити цей товар?")) {
        event.target.closest('form').submit(); // Submit the form associated with the clicked delete button
    }
}

function confirmDeleteAccount() {
    event.preventDefault(); // Prevent default form submission
    if (confirm("Ви впевнені, що хочете видалити акаунт? Цю дію неможливо скасувати!")) {
        event.target.closest('form').submit(); // Submit the form associated with the clicked delete button
    }
}