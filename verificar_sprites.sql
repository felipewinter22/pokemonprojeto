-- Script para verificar os sprites dos Pokémon cadastrados
-- Execute este script no PostgreSQL para ver os dados

-- 1. Ver todos os Pokémon cadastrados com seus sprites
SELECT 
    id,
    pokeapi_id,
    nome_pt,
    nome_en,
    sprite_url,
    treinador_id
FROM pokemons
ORDER BY id DESC
LIMIT 20;

-- 2. Ver apenas os Pokémon que NÃO têm sprite (problema!)
SELECT 
    id,
    pokeapi_id,
    nome_pt,
    nome_en,
    sprite_url,
    treinador_id
FROM pokemons
WHERE sprite_url IS NULL OR sprite_url = ''
ORDER BY id DESC;

-- 3. Ver os Pokémon de um treinador específico (substitua X pelo ID do treinador)
-- SELECT 
--     id,
--     pokeapi_id,
--     nome_pt,
--     nome_en,
--     sprite_url
-- FROM pokemons
-- WHERE treinador_id = X
-- ORDER BY id DESC;

-- 4. Contar quantos Pokémon têm sprite vs não têm
SELECT 
    CASE 
        WHEN sprite_url IS NULL OR sprite_url = '' THEN 'Sem sprite'
        ELSE 'Com sprite'
    END as status,
    COUNT(*) as quantidade
FROM pokemons
GROUP BY status;
