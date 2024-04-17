const messageInput = document.getElementById('message');
const charCount = document.getElementById('charCount');

messageInput.addEventListener('input', function() {
    const maxLength = 2000;
    const currentLength = this.value.length;
    charCount.textContent = `${currentLength} / ${maxLength}`;
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

document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('dropshipping-form').addEventListener('submit', function(event) {
        event.preventDefault();
        setTimeout(function() {
            window.location.href = "/";
        }, 0);
    });
});

function formatPhone(input) {
    let phoneNumber = input.value.replace(/\D/g, '');

    if (phoneNumber.length === 0 || phoneNumber.charAt(0) !== '0') {
        phoneNumber = '0' + phoneNumber;
    }

    let formattedPhoneNumber = '';

    for (let i = 0; i < phoneNumber.length; i++) {
        if (i === 3 || i === 6 || i === 8 || i === 10) {
            formattedPhoneNumber += '-';
        }
        formattedPhoneNumber += phoneNumber.charAt(i);
    }

    if (formattedPhoneNumber.length <= 13) {
        input.value = formattedPhoneNumber;
    } else {
        input.value = formattedPhoneNumber.slice(0, 13);
    }

    if (phoneNumber.length === 0) {
        input.value = '';
    }
}

document.getElementById("phone").addEventListener("keydown", function(event) {
    if (event.key === "Backspace") {
        let phoneNumber = this.value.replace(/\D/g, '');

        if (phoneNumber.length > 1) {
            formatPhone(this);
        } else {
            this.value = '';
        }
    }
});

document.getElementById('firstName').addEventListener('input', function(event) {
    this.value = this.value.replace(/[^a-zA-Zа-яА-ЯґҐєЄіІїЇёЁ'\s]/g, '');
});