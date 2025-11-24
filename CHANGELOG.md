# 📝 Changelog - Centro Pokémon

Todas as mudanças notáveis neste projeto serão documentadas neste arquivo.

## [1.1.0] - 2025-11-24

### ✨ Adicionado
- **Pokédex Interativa Completa**
  - Layout de 3 colunas (Coleção | Pokédex | Sidebar)
  - Busca por nome, ID ou tipo
  - Navegação anterior/próximo
  - Pokémon aleatório e aleatório por tipo
  - 18 filtros de tipo clicáveis
  - Sidebar esquerda com coleção capturada (clicável)
  - Sidebar direita com mais pesquisados (clicável)
  - Missões diárias com progresso
  - Estatísticas de visualizações

- **Animações Premium**
  - Splash screen "Iniciando Aventura Pokémon" (2s)
  - Loading com 3 Pokébolas girando (1s)
  - Entrada 3D da Pokédex (rotateY)
  - Hover effects em todos cards
  - Transições cubic-bezier para movimento natural
  - Partículas de cura (15 partículas verdes)
  - Barra de HP com transição suave (1.5s)
  - Fade in/out do texto de HP

- **Centro de Cura Completo**
  - Layout 2 colunas otimizado
  - Formulário de agendamento com preview
  - Consultas agendadas com cards grandes
  - Cura rápida com animações
  - Atualização individual de Pokémon (sem reload)
  - Notificação "+100 HP"
  - Badge "✓ Curado" quando HP = 100%

- **Sistema de Autenticação**
  - Página de login com fundo azul temático
  - Página de cadastro com preview em tempo real
  - Escolha de Pokémon inicial (Bulbasaur, Charmander, Squirtle)
  - Persistência em LocalStorage

### 🎨 Melhorado
- **Design Visual**
  - Cores oficiais Pokémon
  - Fonte Press Start 2P (estilo retrô)
  - Bordas pretas 3px em todos cards
  - Sombras e profundidade
  - Gradientes azuis temáticos

- **Performance**
  - Atualização individual de Pokémon
  - Cache em LocalStorage
  - Lazy loading de sprites
  - Sistema de fallback para imagens

- **Responsividade**
  - Mobile-first design
  - Breakpoints: 480px, 768px, 1024px, 1200px
  - Grid adaptativo (3 → 1 coluna)
  - Touch-friendly (min-height 44px)

### 🐛 Corrigido
- Animações estranhas no cadastro
- Bordas inconsistentes no login
- Piscada ao final das animações de cura
- Recarregamento desnecessário de todos Pokémon
- Scroll automático agressivo

### 📚 Documentação
- README.md atualizado com todas funcionalidades
- CHANGELOG.md criado
- Comentários JSDoc em JavaScript
- Comentários detalhados em HTML/CSS

## [1.0.0] - 2025-11-23

### ✨ Inicial
- Estrutura básica Spring Boot
- API REST para treinadores e Pokémon
- Integração com PokeAPI v2
- Banco de dados PostgreSQL
- Páginas HTML estáticas
- Sistema de consultas

---

**Formato baseado em [Keep a Changelog](https://keepachangelog.com/)**
