# Projeto Final Web III

## API reativas

#### Versão reativa do projeto final de WebII
(https://github.com/asdiasx/Projeto-Final-Web-II)

Trata-se de um sistema de registro de jodos da Loteria (Quina).
Para fins de simplificação (devido ao tempo) foram abstraidos alguns conceitos da versão original, como Autenticação/Autorização e alguns pontos de validação. Sendo o foco demonstrar a possibilidade de migração de uma arquitetura monolítica para microserviços reativos.

O sistema permite o registro de jogos, apuração de resultados (através de consulta de API da CEF) e calculo de premiação. Mantendo o histórico dos registros persistidos em BD (in-memory H2 e R2DBC).

### Rotas:
Foi incluido o arquivo Insomnia_teste_web3.json com rotas de testes para as apis.
A porta do gateway é a 8080 e os caminhos são:
/api/v1/apostas  
/api/v1/sorteios  
/api/v1/resultados  

### 1- API de Apostas

- Responsabilidade:
  - Registrar jogos efetuados (apostas)
- Endpoints:
  - CRUD de apostas
    - avisa API de Sorteio (auto - POST num soteio)
- Tabela: APOSTA

### 2- API de Sorteios \*

- Responsabilidade:
  - Registrar sorteios de apostas
- Endpoints:
  - POST registra sorteio para atualizar
    - client para acessar a CEF (auto)
  - GET dados do Sorteio por num do Sorteio (item)
- Tabela: SORTEIO
\* Para o funcionamento do cliente que acessa o site da CEF, pode ser necessario adicionar a chave cripptografica do servidor sa CEF na pasta de seguranca da JDK. Procedimento aqui: https://intellij-support.jetbrains.com/hc/en-us/community/posts/115000094584-IDEA-Ultimate-2016-3-4-throwing-unable-to-find-valid-certification-path-to-requested-target-when-trying-to-refresh-gradle?page=1#community_comment_115000405564

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

### Débitos técnicos devido ao prazo:

\* automatizar criacao de registros no db de resultados quando aposta e sorteio forem criados.  
\* falta implementar validação  
\* mudar id de Long para UUID
