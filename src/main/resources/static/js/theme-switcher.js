class ThemeSwitcher {
    constructor() {
        this.themeToggle = document.getElementById('themeToggle');
        this.currentTheme = localStorage.getItem('theme') || 'light';

        if (this.themeToggle) {
            this.init();
        }
    }

    init() {
        this.applyTheme(this.currentTheme);
        this.themeToggle.addEventListener('click', () => {
            this.toggleTheme();
        });
        this.updateButtonText();
    }

    toggleTheme() {
        this.currentTheme = this.currentTheme === 'light' ? 'dark' : 'light';
        this.applyTheme(this.currentTheme);
        this.updateButtonText();
        this.saveTheme();
    }

    applyTheme(theme) {
        document.documentElement.setAttribute('data-theme', theme);
    }

    updateButtonText() {
        const icon = this.themeToggle.querySelector('i');
        const text = this.themeToggle.querySelector('span');

        if (this.currentTheme === 'dark') {
            icon.className = 'bi bi-sun';
            text.textContent = ' Светлая тема';
            this.themeToggle.classList.remove('btn-outline-light');
            this.themeToggle.classList.add('btn-outline-warning');
        } else {
            icon.className = 'bi bi-moon';
            text.textContent = ' Тёмная тема';
            this.themeToggle.classList.remove('btn-outline-warning');
            this.themeToggle.classList.add('btn-outline-light');
        }
    }

    saveTheme() {
        localStorage.setItem('theme', this.currentTheme);
    }
}

// Запускаем когда DOM загружен
document.addEventListener('DOMContentLoaded', () => {
    new ThemeSwitcher();
});