#language: pt
Funcionalidade: Login Facebook

    @loginFacebook-feature
        Cenario: Mostrar mensagem de usuario logado ao logar pelo facebook
        Dado Eu estou na tela de login
        Quando Quero logar pelo facebook
        E Eu clico no botão de Conectar com o facebook
        Entao Eu devo ver mensagem de confirmação

