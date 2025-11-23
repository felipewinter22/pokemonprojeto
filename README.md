# 🏥 Centro Pokémon — Aplicação Web com Pokédex e Centro de Cura

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Visual Paradigm](https://img.shields.io/badge/Visual_Paradigm-FF6F00?style=for-the-badge&logo=visual-paradigm&logoColor=white)

Um projeto Spring Boot com páginas estáticas e API REST para gerenciar treinadores, seus Pokémon e consultas médicas. Interface inspirada no anime, com Pokédex interativa e uma página inicial com “TV da Sala de Espera”.

## 🔗 Acesse localmente

- Home: http://localhost:8081/Pokemon.html
- Pokédex (anime): http://localhost:8081/pokedex-anime.html
- Pokédex (rota resumida): http://localhost:8081/pokedex
- Cadastro de Treinador: http://localhost:8081/cadastro.html
- Login: http://localhost:8081/login.html

Observação: a porta padrão é 8081. Você pode alterar com --server.port=8090 ao executar o .jar.

## ✨ O que já está funcionando

- Pokédex com busca por nome/ID, aleatório e filtro por tipo.
- Sprites com fallback automático entre múltiplas fontes quando um URL falha (inclui tratamento para erro 429 do GitHub).
- Erros visuais da Pokédex silenciados para uma experiência mais limpa.
- Cadastro de Treinador com escolha de iniciais e imagens estáticas locais para Bulbasaur, Charmander e Squirtle.
- Agendamento e listagem de consultas por treinador.

## 🗺️ Navegação e páginas

- Pokemon.html: landing com estatísticas, destaque do dia e TV.
- pokedex-anime.html / pokedex: interface da Pokédex.
- cadastro.html: cadastro de treinadores com seleção de inicial.
- login.html: autenticação do treinador.

Assets estáticos ficam em CentroPokemon/CentroPokemon/src/main/resources/static/:
- Imagens: /imagens/bulbasauro.png, /imagens/charmander.png, /imagens/squirtle.png, etc.
- Vídeos: /videos/EP 001 - Pokémon Eu Escolho Você!.mkv.

Nota de compatibilidade: alguns navegadores não reproduzem .mkv. Se necessário, adicione também um .mp4 como fallback em Pokemon.html.

## 🧰 Tecnologias

- Java 21, Spring Boot 3.3.x
- Spring Web, Spring Data JPA, Validation
- Banco de dados: PostgreSQL
- Maven Wrapper (mvnw / mvnw.cmd)

##   Como rodar

1) Com Maven Wrapper (Windows):

bash
./mvnw.cmd spring-boot:run


2) Com .jar:

bash
mvn -q package -DskipTests
java -jar CentroPokemon/CentroPokemon/target/CentroPokemon-0.0.1-SNAPSHOT.jar --server.port=8081


Configuração de banco no application.properties:


spring.datasource.url=jdbc:postgresql://localhost:5432/centro_pokemon
spring.datasource.username=<usuario>
spring.datasource.password=<senha>
spring.jpa.hibernate.ddl-auto=update


## 📡 API REST

Base: /CentroPokemon/api

- Treinadores (/treinadores)
  - POST /cadastrar — cadastra treinador (nome, usuário, email, senha, telefone opcional). Pode receber starterId, starterName, starterSpriteUrl.
  - POST /login — autentica por usuário ou e-mail.

- Pokémon do Treinador (/treinadores/{id}/pokemons)
  - POST / — cadastra Pokémon do treinador.
  - GET / — lista Pokémon do treinador.
  - DELETE /{pokemonId} — remove Pokémon do treinador.

- Consultas (/treinadores/{id}/consultas)
  - POST / — agenda consulta (tipo, dataHora, observações, pokemonId).
  - GET / — lista consultas do treinador.

- Pokédex (/pokemons)
  - GET /{nome} — busca por nome (EN).
  - GET /id/{id} — busca por ID.
  - GET /random — Pokémon aleatório.
  - GET /type/{type}/random — aleatório por tipo.

## 🖼️ Diagramas

- [Use Case](diagrams/Use%20Case%20Diagram1.png)
- [Sequence](diagrams/Sequence%20Diagram1.png)
- [Class](diagrams/Class%20Diagram1.png)
- [Activity](diagrams/Activity%20Diagram1.png)
- [Estados de saúde do Pokémon](diagrams/Estados%20de%20sa%C3%BAde%20do%20Pok%C3%A9mon.png)

Projeto visual original: [sources/CentroPokemon.vpp](sources/CentroPokemon.vpp)

## 🤝 Contribuições

- Abra uma issue com sugestão ou bug.
- Faça um fork e envie um Pull Request com uma explicação clara.

##   Notas finais

Este projeto foi feito com carinho para estudar e celebrar o universo Pokémon. Se algo não funcionar no seu ambiente, me avise — a ideia é que a experiência seja simples e divertida.