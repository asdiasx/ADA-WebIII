CREATE TABLE apostas
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_sorteio INTEGER,
    dezenas INTEGER ARRAY,
    data_jogo DATE
);