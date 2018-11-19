#language: pt
Funcionalidade: cadastro

  @cadastroSucesso-feature
  Cenario:Mostrar mensagem de sucesso ao criar novo usuário
  Dado: que estou no aplicativo Belapp
  Quando: eu preencher informações do usuário
  E: eu clico no botão enviar
  Entao: eu deveria ver messagem de suceeso
