#language: pt
Funcionalidade: Recuperação de dados de acesso

  @recupera-senha-feature
  Cenario: TA_01: Usar um e-mail não cadastrado
    Dado que estou na tela Recupera Senha
    Quando eu forneço um e-mail inválido
    E eu clico no botão Solicitar
    Entao devo ver uma mensagem me informando que houve falha

  @recupera-senha-feature
  Cenario: TA_02: Usar um e-mail cadastrado
    Dado que estou na tela Recupera Senha
    Quando eu forneço um e-mail válido
    E eu clico no botão Solicitar
    Entao devo ver uma mensagem me informando que o e-mail de recuperação foi enviado

  @recupera-senha-feature
  Cenario: TA_03: Não preencher um e-mail
    Dado que estou na tela Recupera Senha
    Quando eu não forneço um e-mail
    E eu clico no botão Solicitar
    Entao devo ver uma mensagem me informando para digitar um e-mail
