document.getElementById('showPasswordCheckbox').addEventListener('change', function() {
    let passwordField = document.getElementById('password');

    if (this.checked) {
        passwordField.type = 'text';
    } else {
        passwordField.type = 'password';
    }
});

document.getElementById('generatePasswordButton').addEventListener('click', function() {
    let passwordLength = 18;
    let password = generatePassword(passwordLength);

    document.getElementById('password').value = password;
    document.querySelector('button[type="submit"]').disabled = false;
});

function generatePassword(length) {
    let charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+{}[]<>?";
    let password = "";

    for (let i = 0; i < length; i++) {
        let randomIndex = Math.floor(Math.random() * charset.length);
        password += charset[randomIndex];
    }

    return password;
}

document.addEventListener('DOMContentLoaded', function() {
    const passwordInput = document.getElementById('password');
    const strengthMeter = document.getElementById('strength-bar');
    const strengthText = document.getElementById('strength-text');
    const submitButton = document.querySelector('button[type="submit"]');

    passwordInput.addEventListener('input', function() {
        const password = passwordInput.value;
        if (password === '') {
            clearStrengthMeter();
            submitButton.disabled = false;
        } else {
            const strength = calculatePasswordStrength(password);
            updateStrengthMeter(strength);
            if (strength < 4) {
                submitButton.disabled = true;
            } else {
                submitButton.disabled = false;
            }
        }
    });

    document.getElementById('showPasswordCheckbox').addEventListener('change', function() {
        let passwordField = passwordInput;

        if (this.checked) {
            passwordField.type = 'text';
        } else {
            passwordField.type = 'password';
        }
    });

    document.getElementById('generatePasswordButton').addEventListener('click', function() {
        let passwordLength = 18;
        let password = generatePassword(passwordLength);
        passwordInput.value = password;
        const strength = calculatePasswordStrength(password);
        updateStrengthMeter(strength);
    });

    function generatePassword(length) {
        let charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+{}[]<>?";
        let password = "";

        for (let i = 0; i < length; i++) {
            let randomIndex = Math.floor(Math.random() * charset.length);
            password += charset[randomIndex];
        }

        return password;
    }

    function calculatePasswordStrength(password) {
        const hasNumber = /\d/.test(password);
        const hasLowerCase = /[a-z]/.test(password);
        const hasUpperCase = /[A-Z]/.test(password);
        const hasSpecialChars = /[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]/.test(password);
        const isLengthValid = password.length >= 8;
        const isLengthExcellent = password.length >= 16;

        let score = 0;
        if (hasNumber) score += 1;
        if (hasLowerCase) score += 1;
        if (hasUpperCase) score += 1;
        if (hasSpecialChars) score += 1;
        if (isLengthValid) score += 1;
        if (isLengthExcellent) score += 1;

        return score;
    }

    function updateStrengthMeter(score) {
        let colorClass = '';
        let widthPercentage = 0;

        if (score < 4) {
            colorClass = 'red';
            widthPercentage = 33.33;
        } else if (score < 6) {
            colorClass = 'yellow';
            widthPercentage = 66.66;
        } else {
            colorClass = 'green';
            widthPercentage = 100;
        }

        strengthMeter.style.width = widthPercentage + '%';
        strengthMeter.className = colorClass;
    }

    function clearStrengthMeter() {
        strengthMeter.style.width = '0';
        strengthMeter.className = '';
        strengthText.textContent = '';
    }
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

document.getElementById('lastName').addEventListener('input', function(event) {
    this.value = this.value.replace(/[^a-zA-Zа-яА-ЯґҐєЄіІїЇёЁ'\s]/g, '');
});