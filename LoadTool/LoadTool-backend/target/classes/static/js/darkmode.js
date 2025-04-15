document.addEventListener('DOMContentLoaded', function () {
  const themeLink = document.getElementById('themeStylesheet');
  const darkModeSwitch = document.getElementById('darkModeSwitch');

  // Funções para cookie
  function setCookie(name, value, days) {
    const date = new Date();
    date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000)); // 30 dias
    document.cookie = `${name}=${value};expires=${date.toUTCString()};path=/`;
  }

  function getCookie(name) {
    const cookies = document.cookie.split(';');
    for (let cookie of cookies) {
      cookie = cookie.trim();
      if (cookie.startsWith(name + '=')) {
        return cookie.substring(name.length + 1);
      }
    }
    return null;
  }

  // Função para aplicar o tema
  function applyTheme(darkMode) {
    themeLink.href = darkMode ? '/css/styles-darkmode.css' : '/css/styles.css';
    darkModeSwitch.checked = darkMode;
    setCookie('darkMode', darkMode, 30);
  }

  // Ao carregar a página
  const darkModeCookie = getCookie('darkMode');
  const darkModeEnabled = darkModeCookie === 'true';
  applyTheme(darkModeEnabled);

  // Ao mudar o checkbox
  darkModeSwitch.addEventListener('change', function () {
    applyTheme(this.checked);
  });
});
