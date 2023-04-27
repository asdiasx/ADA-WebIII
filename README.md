# Projeto Final Web III

## API reativas

#### Versão reativa do projeto final de WebII

Trata-se de um sistema de registro de jodos da Loteria (Quina).
Para fins de simplificação (devido ao tempo) foram abstraidos alguns conceitos da versão original, como Autenticação/Autorização e alguns pontos de validação. Sendo o foco demonstrar a possibilidade de migração de uma arquitetura monolítica para microserviços reativos.

O sistema permite o registro de jogos, apuração de resultados (através de consulta de API da CEF) e calculo de premiação. Mantendo o histórico dos registros persistidos em BD (in-memory H2 e R2DBC).

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

\* falta implementar validação  
\* mudar id de Long para UUID
