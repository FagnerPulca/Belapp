#language: pt
Funcionalidade: cadastro

  @cadastroEmail-feature
  Cenario:Mostrar mensagem de email invalido ao criar novo usuário
  Dado: que estou no aplicativo Belapp
  Quando: eu preencho email
  E: todas outras informaçoes de usuario
  E: eu clico no botão enviar
  Entao: eu deveria ver messagem de erro
