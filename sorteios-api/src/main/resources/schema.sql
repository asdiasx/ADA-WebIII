CREATE TABLE sorteios
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_sorteio INTEGER,
    numero_concurso_proximo INTEGER,
    acumulado BOOLEAN,
    data_sorteio DATE,
    data_proximo_sorteio DATE,
    dezenas_sorteadas INTEGER ARRAY,
    premios CHARACTER VARYING(255),
    valor_acumulado_proximo_concurso NUMERIC(38,2)
);
