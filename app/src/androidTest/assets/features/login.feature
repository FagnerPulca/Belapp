#language: pt
Funcionalidade: Login

  @login-feature
  Cenario: Login Efetuado com sucesso
    Dado que estou na tela de login
    Quando eu preencho o campo email flp.d@hotmail.com
    E eu preencho o campo senha 1234567
    E aperto o botão login
    Entao devo ver a mensagem, Login efetuado com sucesso!

