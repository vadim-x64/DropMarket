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

const messageInput = document.getElementById('description');
const charCount = document.getElementById('charCount');

messageInput.addEventListener('input', function() {
    const maxLength = 2000;
    const currentLength = this.value.length;
    charCount.textContent = `${currentLength} / ${maxLength}`;
});

function showConfirmation() {
    let modal = document.getElementById("confirmationModal");
    modal.style.display = "block";
}

function hideConfirmation() {
    let modal = document.getElementById("confirmationModal");
    modal.style.display = "none";
}

function deleteProduct() {
    document.getElementById("deleteForm").submit();
}