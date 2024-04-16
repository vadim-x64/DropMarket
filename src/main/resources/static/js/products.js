document.getElementById('userInitial').addEventListener('click', function () {
    window.location.href = "/profile";
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