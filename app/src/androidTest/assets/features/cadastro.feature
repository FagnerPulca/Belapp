#language: pt
Funcionalidade: Cadastro de dados do Perfil

  @cadastro-feature
  Cenario: TA_01: Cadastrar dados do perfil com telefone em branco
    Dado que estou na tela de Cadastro
    Quando eu preencho todos os campos obrigatórios, exeto o telefone
    E eu aperto o botão salvar
    Entao devo ver uma mensagem que informe que campo telefone nao pode ser deixado em branco


@cadastro-feature
 Cenario: TA_02: Cadastrar dados do perfil com nome em branco
    Dado que estou na tela de Cadastro
    Quando eu preencho todos os campos obrigatórios, exeto o nome
    E eu aperto o botão salvar
    Entao devo ver uma mensagem que informe que campo nome nao pode ser deixado em branco

 @cadastro-feature
  Cenario: TA_03: Cadastrar dados do perfil com e-mail em branco
    Dado que estou na tela de Cadastro
    Quando eu preencho todos os campos obrigatórios, exeto o Email
    E eu aperto o botão salvar
    Entao devo ver uma mensagem que informe que campo e-mail nao pode ser deixado em branco

 @cadastro-feature
  Cenario: TA_04: Cadastrar dados do perfil com senha em branco
    Dado que estou na tela de Cadastro
    Quando eu preencho todos os campos obrigatórios, exeto o senha
    E eu aperto o botão salvar
    Entao devo ver uma mensagem que informe que campo senha nao pode ser deixado em branco

  @cadastro-feature
  Cenario: TA_05: Cadastrar dados do perfil com dados válidos
     Dado que estou na tela de Cadastro
     Quando eu preencho todos os campos obrigatórios corretamente
     E eu aperto o botão salvar
     Entao devo ver uma mensagem de sucesso da operacao






