// Função para carregar componentes HTML
async function loadComponent(elementId, componentPath) {
    try {
        const response = await fetch(componentPath);
        const html = await response.text();
        document.getElementById(elementId).innerHTML = html;
    } catch (error) {
        console.error(`Erro ao carregar o componente ${componentPath}:`, error);
    }
}

// Carrega os componentes quando o documento estiver pronto
document.addEventListener('DOMContentLoaded', () => {
    loadComponent('header-container', '/src/pages/fragments/header.html');
    loadComponent('footer-container', '/src/pages/fragments/footer.html');
}); 