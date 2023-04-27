CREATE TABLE resultados
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    aposta_id    BIGINT,
    numero_sorteio   INTEGER,
    pontuacao    INTEGER,
    valor_premio NUMERIC(38, 2)
);
