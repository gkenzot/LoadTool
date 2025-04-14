// Função para importar scripts dinamicamente
function importScript(scriptPath) {
  return new Promise((resolve, reject) => {
      const script = document.createElement('script');
      script.src = scriptPath;
      script.type = 'text/javascript';
      
      script.onload = () => resolve();
      script.onerror = () => reject(new Error(`Falha ao carregar o script: ${scriptPath}`));
      
      document.head.appendChild(script);
  });
}

// Lista de scripts para importar (atualize esta lista conforme necessário)
const scriptsToImport = [
  '/scripts/components.js',
  '/scripts/darkmode.js',
  // Adicione aqui outros scripts que precisam ser importados
];

// Função para importar todos os scripts em sequência
async function loadAllScripts() {
  try {
      for (const scriptPath of scriptsToImport) {
          await importScript(scriptPath);
          console.log(`Script carregado com sucesso: ${scriptPath}`);
      }
      console.log('Todos os scripts foram carregados!');
  } catch (error) {
      console.error('Erro ao carregar scripts:', error);
  }
}

// Carrega todos os scripts quando o documento estiver pronto
document.addEventListener('DOMContentLoaded', loadAllScripts); 