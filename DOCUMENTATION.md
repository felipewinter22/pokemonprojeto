# 📖 Documentação Técnica - Centro Pokémon

## 📋 Índice
1. [Arquitetura](#arquitetura)
2. [Backend](#backend)
3. [Frontend](#frontend)
4. [Animações](#animações)
5. [API](#api)
6. [Banco de Dados](#banco-de-dados)

---

## 🏗️ Arquitetura

### Stack Tecnológico
- **Backend**: Java 21 + Spring Boot 3.3
- **Frontend**: HTML5 + CSS3 + JavaScript (Vanilla)
- **Banco de Dados**: PostgreSQL 12+
- **API Externa**: PokeAPI v2
- **Build**: Maven 3.8+

### Padrão Arquitetural
- **MVC**: Model-View-Controller
- **REST**: RESTful API
- **SPA-like**: Single Page Application behavior com navegação por âncoras

---

## 🔧 Backend

### Estrutura de Pacotes
```
com.centropokemon/
├── controller/          # Controladores REST
├── model/              # Entidades JPA
├── repository/         # Repositórios Spring Data
├── service/           # Lógica de negócio
└── config/            # Configurações
```

### Entidades Principais

#### Treinador
```java
@Entity
public class Treinador {
    @Id @GeneratedValue
    private Long id;
    private String nome;
    private String usuario;
    private String email;
    private String senha;
    private String telefone;
    
    @OneToMany(mappedBy = "treinador")
    private List<Pokemon> pokemons;
    
    @OneToMany(mappedBy = "treinador")
    private List<Consulta> consultas;
}
```

#### Pokemon
```java
@Entity
public class Pokemon {
    @Id @GeneratedValue
    private Long id;
    private Integer pokeApiId;
    private String nomePt;
    private String nomeEn;
    private String spriteUrl;
    private Integer vidaAtual;
    private Integer vidaMaxima;
    private Integer nivel;
    
    @ManyToOne
    private Treinador treinador;
}
```

#### Consulta
```java
@Entity
public class Consulta {
    @Id @GeneratedValue
    private Long id;
    
    @ManyToOne
    private Treinador treinador;
    
    @ManyToOne
    private Pokemon pokemon;
    
    @Enumerated(EnumType.STRING)
    private TipoConsulta tipo;
    
    private LocalDateTime dataHora;
    private String observacoes;
}
```

### Controllers

#### PokedexController
```java
@RestController
@RequestMapping("/api/pokemons")
public class PokedexController {
    // GET /{nomeOuId} - Busca Pokémon
    // GET /random - Pokémon aleatório
    // GET /type/{tipo}/random - Aleatório por tipo
}
```

#### CentroController
```java
@RestController
@RequestMapping("/api/treinadores/{id}/pokemons")
public class CentroController {
    // POST / - Cadastra Pokémon
    // GET / - Lista Pokémon
    // POST /{pokemonId}/curar - Cura Pokémon
    // DELETE /{pokemonId} - Remove Pokémon
}
```

---

## 🎨 Frontend

### Estrutura de Arquivos
```
static/
├── css/
│   ├── novo-style.css (800+ linhas)
│   ├── pokedex-anime.css (1200+ linhas)
│   └── centro-cura.css (900+ linhas)
├── js/
│   ├── pokedex-anime.js (750+ linhas)
│   ├── animacoes.js
│   └── auth-header.js
├── imagens/
└── videos/
```

### CSS - Variáveis Globais
```css
:root {
  /* Cores Pokédex */
  --pokedex-red: #FF0000;
  --pokedex-dark-red: #CC0000;
  --pokedex-screen-bg: #88C070;
  --pokedex-screen-border: #306850;
  
  /* Cores Tipos */
  --type-fire: #F08030;
  --type-water: #6890F0;
  --type-grass: #78C850;
  /* ... 18 tipos */
}
```

### JavaScript - Arquitetura

#### Padrão Module (IIFE)
```javascript
const PokedexAnime = (() => {
    // Estado privado
    const state = {
        currentPokemon: null,
        viewedPokemon: new Set(),
        caughtPokemon: new Set()
    };
    
    // API pública
    return {
        init,
        loadPokemon,
        showNotification
    };
})();
```

#### Fluxo de Dados
```
User Action → Event Handler → API Call → Update State → Update UI
```

---

## ✨ Animações

### Timing Functions
```css
/* Bounce natural */
cubic-bezier(0.34, 1.56, 0.64, 1)

/* Ease out suave */
cubic-bezier(0.4, 0, 0.2, 1)
```

### Animações Principais

#### 1. Splash Screen (2s)
```css
@keyframes splashPokeballBounce {
    0%, 100% { transform: translateY(0) rotate(0deg); }
    25% { transform: translateY(-20px) rotate(90deg); }
    50% { transform: translateY(0) rotate(180deg); }
    75% { transform: translateY(-20px) rotate(270deg); }
}
```

#### 2. Loading Pokédex (1s)
```css
@keyframes pokeballBounce {
    0% { transform: translateY(0) rotate(0deg); opacity: 1; }
    50% { transform: translateY(0) rotate(180deg); opacity: 1; }
    90% { opacity: 1; }
    100% { transform: translateY(0) rotate(360deg); opacity: 0; }
}
```

#### 3. Partículas de Cura
```javascript
function createHealingParticles(card) {
    for (let i = 0; i < 15; i++) {
        const particle = document.createElement('div');
        particle.className = 'heal-particle';
        particle.style.left = `${Math.random() * 100}%`;
        particle.style.animationDelay = `${Math.random() * 0.5}s`;
        card.appendChild(particle);
    }
}
```

```css
@keyframes particleRise {
    0% { bottom: 0; opacity: 1; transform: translateY(0) scale(1); }
    50% { opacity: 1; transform: translateY(-50px) scale(1.2); }
    100% { bottom: 100%; opacity: 0; transform: translateY(-100px) scale(0.5); }
}
```

#### 4. Barra de HP
```css
.hp-bar-fill {
    transition: width 1.5s cubic-bezier(0.34, 1.56, 0.64, 1);
}
```

```javascript
// Fade out → Update → Fade in
hpText.style.opacity = '0';
await new Promise(resolve => setTimeout(resolve, 300));
hpText.textContent = `${vidaAtual}/${vidaMaxima} HP`;
hpText.style.opacity = '1';
hpFill.style.width = `${hpPercent}%`;
```

---

## 🔌 API

### Endpoints Completos

#### Treinadores
```http
POST /api/treinadores/cadastrar
Content-Type: application/json

{
  "nome": "Ash Ketchum",
  "usuario": "ash",
  "email": "ash@pokemon.com",
  "senha": "pikachu123",
  "telefone": "47999999999",
  "starterId": 25,
  "starterName": "Pikachu",
  "starterSpriteUrl": "/imagens/pikachu.png"
}

Response: 201 Created
{
  "id": 1,
  "nome": "Ash Ketchum",
  "usuario": "ash",
  "email": "ash@pokemon.com"
}
```

```http
POST /api/treinadores/login
Content-Type: application/json

{
  "usuarioOuEmail": "ash",
  "senha": "pikachu123"
}

Response: 200 OK
{
  "id": 1,
  "nome": "Ash Ketchum",
  "usuario": "ash"
}
```

#### Pokémon
```http
POST /api/treinadores/1/pokemons
Content-Type: application/json

{
  "pokeApiId": 25,
  "nomePt": "Pikachu",
  "nomeEn": "Pikachu",
  "spriteUrl": "https://...",
  "vidaAtual": 100,
  "vidaMaxima": 100,
  "nivel": 5,
  "habilidades": ["Static"],
  "tipos": ["electric"]
}

Response: 201 Created
```

```http
POST /api/treinadores/1/pokemons/1/curar

Response: 200 OK
{
  "id": 1,
  "vidaAtual": 100,
  "vidaMaxima": 100
}
```

#### Pokédex
```http
GET /api/pokemons/pikachu

Response: 200 OK
{
  "pokeApiId": 25,
  "nomePt": "Pikachu",
  "nomeEn": "Pikachu",
  "tipos": [{"nomePt": "Elétrico", "nomeEn": "electric"}],
  "altura": 0.4,
  "peso": 6.0,
  "habilidades": ["Static", "Lightning Rod"],
  "stats": {"hp": 35, "attack": 55, ...},
  "descricoes": [{"descricaoPt": "...", "descricaoEn": "..."}],
  "spriteUrl": "https://..."
}
```

---

## 💾 Banco de Dados

### Schema
```sql
CREATE TABLE treinador (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    usuario VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    telefone VARCHAR(20)
);

CREATE TABLE pokemon (
    id BIGSERIAL PRIMARY KEY,
    poke_api_id INTEGER,
    nome_pt VARCHAR(100),
    nome_en VARCHAR(100),
    sprite_url TEXT,
    vida_atual INTEGER,
    vida_maxima INTEGER,
    nivel INTEGER,
    treinador_id BIGINT REFERENCES treinador(id)
);

CREATE TABLE consulta (
    id BIGSERIAL PRIMARY KEY,
    treinador_id BIGINT REFERENCES treinador(id),
    pokemon_id BIGINT REFERENCES pokemon(id),
    tipo VARCHAR(50),
    data_hora TIMESTAMP,
    observacoes TEXT
);
```

### Índices
```sql
CREATE INDEX idx_pokemon_treinador ON pokemon(treinador_id);
CREATE INDEX idx_consulta_treinador ON consulta(treinador_id);
CREATE INDEX idx_consulta_data ON consulta(data_hora);
```

---

## 🎯 Performance

### Otimizações Implementadas
1. **LocalStorage Cache**: Estatísticas e pesquisas
2. **Atualização Individual**: Apenas o Pokémon curado é atualizado
3. **Lazy Loading**: Sprites carregados sob demanda
4. **Fallback System**: Múltiplas fontes para imagens
5. **Debounce**: Em inputs de busca
6. **CSS Animations**: Hardware-accelerated (transform, opacity)

### Métricas
- **First Paint**: < 1s
- **Interactive**: < 2s
- **Loading Animation**: 1s
- **Cura Animation**: 2-3s
- **Bundle Size**: ~100KB (sem minificação)

---

## 🔒 Segurança

### Implementado
- Validação de entrada no backend
- Sanitização de dados
- Prepared statements (JPA)
- CORS configurado
- Senhas em texto plano (⚠️ **TODO**: Hash com BCrypt)

### TODO
- [ ] Implementar BCrypt para senhas
- [ ] JWT para autenticação
- [ ] Rate limiting
- [ ] HTTPS obrigatório
- [ ] Validação de CSRF

---

## 📱 Responsividade

### Breakpoints
```css
/* Mobile */
@media (max-width: 480px) { ... }

/* Tablet */
@media (max-width: 768px) { ... }

/* Desktop Small */
@media (max-width: 1024px) { ... }

/* Desktop Large */
@media (max-width: 1200px) { ... }
```

### Adaptações
- Grid: 3 colunas → 1 coluna
- Fonte: Redução de 10-20%
- Padding: Redução de 20-30%
- Touch targets: Mínimo 44x44px

---

## 🧪 Testes

### TODO
- [ ] Testes unitários (JUnit)
- [ ] Testes de integração (Spring Test)
- [ ] Testes E2E (Selenium)
- [ ] Testes de performance (JMeter)

---

## 📦 Deploy

### Produção
```bash
# Build
mvn clean package -DskipTests

# Run
java -jar target/CentroPokemon-0.0.1-SNAPSHOT.jar \
  --spring.profiles.active=prod \
  --server.port=8080
```

### Docker (TODO)
```dockerfile
FROM openjdk:21-jdk-slim
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

---

**Última atualização**: 24/11/2025
