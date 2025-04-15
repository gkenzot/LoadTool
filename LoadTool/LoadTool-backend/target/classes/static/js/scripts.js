// src/main/resources/static/js/scripts-importados.js

// Função para importar scripts
function importScript(src) {
  var script = document.createElement('script');
  script.src = src;
  script.type = 'text/javascript';
  document.head.appendChild(script);
}

// Função para importar folhas de estilo
function importStyle(href, integrity, crossorigin) {
  var link = document.createElement('link');
  link.rel = 'stylesheet';
  link.href = href;
  if (integrity) link.integrity = integrity;
  if (crossorigin) link.crossOrigin = crossorigin;
  document.head.appendChild(link);
}

// Importar Bootstrap CSS
importStyle(
  'https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css',
  'sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN',
  'anonymous'
);

// jQuery
importScript('https://code.jquery.com/jquery-3.7.1.min.js');

// Popper.js
importScript('https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js');

// Bootstrap JS
//importScript('https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js');

// Importe os outros scripts usando import
//import './scripts/placeholder.js';
//import './scripts/darkmode.js';
//import './scripts/menuitem.js';