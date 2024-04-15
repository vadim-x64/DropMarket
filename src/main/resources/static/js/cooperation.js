// JavaScript для відправки форми і відображення повідомлення про успіх
document.getElementById("dropshipping-form").addEventListener("submit", function(event) {
    event.preventDefault(); // Зупиняємо стандартну відправку форми
    var formData = new FormData(this);

    // Тут можна додати код для відправки даних форми на сервер,
    // наприклад, використовуючи AJAX або fetch API

    // Приклад відображення повідомлення про успішну відправку
    alert("Your cooperation request has been successfully submitted!");

    // Очищаємо поля форми після відправки
    this.reset();
});