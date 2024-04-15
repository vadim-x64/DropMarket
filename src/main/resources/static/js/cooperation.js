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


document.addEventListener("DOMContentLoaded", function() {
    const messageInput = document.getElementById("message");
    const charCount = document.getElementById("charCount");

    messageInput.addEventListener("input", function() {
        const count = messageInput.value.length;
        charCount.textContent = count + " / 2000";
    });
});


document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('dropshipping-form').addEventListener('submit', function(event) {
        // Стоп події за замовчуванням (у цьому випадку - відправку форми)
        event.preventDefault();

        // Чекаємо 3 секунди перед переадресацією
        setTimeout(function() {
            window.location.href = "/"; // Перенаправлення на головну сторінку
        }, 3000); // 3000 мілісекунд = 3 секунди
    });
});
