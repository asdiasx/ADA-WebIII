# Projeto Final Web III

## API reativas

### 1- API de Apostas

- Responsabilidade:
  - Registrar jogos efetuados (apostas)
- Endpoints:
  - CRUD de apostas
    - avisa API de Sorteio (auto - POST num soteio)
- Tabela: APOSTA

### 2- API de Sorteios

- Responsabilidade:
  - Registrar sorteios de apostas
- Endpoints:
  - POST registra sorteio para atualizar
    - client para acessar a CEF (auto)
  - GET dados do Sorteio por num do Sorteio (item)
- Tabela: SORTEIO

### 3- API de Resultados

- Responsabilidade:
  - Associa Aposta a Sorteios e fornece resultados
- Endpoints:
  - GET resultado de aposta
    - consulta DB e responde
    - se nao tiver:
      - consulta Aposta
      - consulta Sorteio
      - registra resultado
      - responde com resultado
- Tabela: RESULTADO

### Débitos técnico devido ao prazo:

\* automatizar criacao de registros no db de resultados quando aposta e sorteio forem criados.  
\* falta implementar validação  
\* mudar id de Long para UUID
