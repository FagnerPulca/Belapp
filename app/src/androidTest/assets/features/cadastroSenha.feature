#language: pt
Funcionalidade: cadastro

  @cadastroSenha-feature
  Cenario:Mostrar mensagem de senha invalida ao criar novo usuário
  Dado: que estou no aplicativo Belapp
  Quando: eu preencho a  senha
  E: todas outras informaçoes de usuario
  E: eu clico no botão enviar
  Entao: eu deveria ver messagem de erro
