#language: pt
Funcionalidade: cadastro

  @cadastroCampos-feature
  Cenario:Mostrar mensagem de campos em branco  ao criar novo usuário
  Dado: que estou no aplicativo Belapp
  Quando:  preencho informaçoes de usuario deixando alguma em branco
  E: eu clico no botão enviar
  Entao: eu deveria ver messagem de  que não posso deixar campos em branco
