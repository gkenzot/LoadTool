# LoadTool - Gerenciador de Empréstimo de Ferramentas
- [Repositório do Projeto](https://github.com/gkenzot/LoadTool)
- [Wireframe Figma](https://www.figma.com/design/8E8o32GkBkxFFQhujOp8yz/Untitled?node-id=0-1&t=Ylkg0LgCwWD3K84E-1)

## Status do Projeto
🚧 Em desenvolvimento (Versão Alpha)
Atualmente o projeto está todo em LoadTool-backend, porém no futuro irá migrar para pastas separadas

## Tecnologias Aplicadas

### Backend
- Java 17
- Spring Boot 3.4.3
- Spring Data JPA
- Hibernate
- MySQL (Banco de dados em produção)
- H2 (Banco de dados para testes)
- Lombok
- Spring Validation
- SpringDoc OpenAPI 2.8.5 (Documentação de APIs)

### Frontend
- Thymeleaf (Integração com Spring)
- HTML5
- CSS3
- Bootstrap
- JavaScript

### Ferramentas
- Maven (Gerenciamento de dependências)
- Spring Boot DevTools

## Time de Desenvolvimento
- Gustavo Kenzo Takeda (Desenvolvedor Full Stack)
- [Linkedin](https://www.linkedin.com/in/gkenzot/)

## Objetivo do Software
O LoadTool é um sistema interno de gerenciamento de empréstimos de ferramentas para lojas, com foco atual em:
- Controle centralizado pelo balconista/admin
- Cadastro básico de usuários, ferramentas e categorias
- Operações diárias de empréstimo em ambiente controlado
- Interface web responsiva

## Funcionalidades Atuais

### 🔧 Módulo Administrativo (Admin)
- **CRUD Completo** de:
  - Usuários (apenas admin)
  - Ferramentas
  - Categorias
- Acesso total ao sistema via interface web
- Validação de dados no backend

### 👤 Módulo Básico (Usuário Comum/Vendedor)
- Visualização de ferramentas disponíveis
- Interface responsiva com Bootstrap

### 📝 Sistema de Empréstimos
- Registro de empréstimos por diária
- Dados registrados:
  - Ferramenta emprestada
  - Usuário beneficiado
  - Data do empréstimo
  - Observações

### 📊 API Documentada
- Documentação automática com SpringDoc OpenAPI

## 🔮 Futuras Funcionalidades

### Melhorias Planejadas
- **Autenticação e Autorização**:
  - Roles: Admin, Vendedor, Usuário
  - Sistema de login seguro
  
- **Recursos Visuais**:
  - Upload de fotos para ferramentas
  - Dashboard administrativo

- **Expansão de Empréstimos**:
  - Histórico completo
  - Relatórios PDF
  - Busca avançada

- **Melhorias no Frontend**:
  - Interatividade com JavaScript
  - Temas personalizáveis

## Configuração do Ambiente

### Requisitos
- Java 17+
- MySQL 8+
- Maven 3.6+

### Banco de Dados
```properties
# Configuração típica no application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/loadtool
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update