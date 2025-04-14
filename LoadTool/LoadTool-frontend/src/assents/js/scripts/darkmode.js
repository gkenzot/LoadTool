function toggleDarkMode() {
  const linkElement = document.querySelector('link[href="../styles/styles.css"]');
  const toggleButton = document.getElementById('toggleButton');
  const darkModeImg = toggleButton.querySelector('img');
  const logoImg = document.querySelector('img.logo__escudo');

  let darkModeEnabled = linkElement.href.includes('../styles/styles-darkmode.css');

  // Função para criar ou atualizar um cookie
  function setCookie(name, value, days) {
    const expires = new Date();
    expires.setTime(expires.getTime() + (days * 24 * 60 * 60 * 1000));
    document.cookie = name + '=' + value + ';expires=' + expires.toUTCString();
  }

  // Função para ler um cookie
  function getCookie(name) {
    const cookies = document.cookie.split(';');
    for (let i = 0; i < cookies.length; i++) {
      const cookie = cookies[i].trim();
      if (cookie.startsWith(name + '=')) {
        return cookie.substring(name.length + 1);
      }
    }
    return null;
  }

  // Verificar se há um cookie de Dark Mode e definir o estado inicial
  const darkModeCookie = getCookie('darkMode');
  if (darkModeCookie !== null) {
    darkModeEnabled = darkModeCookie === 'true';
  }

  // Atualizar o estilo da folha de estilos
  function updateStyle() {
    if (darkModeEnabled) {
      linkElement.href = '../styles/styles-darkmode.css';
    } else {
      linkElement.href = '../styles/styles.css';
    }
  }
}