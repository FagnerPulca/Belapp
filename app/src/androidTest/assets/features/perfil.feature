#language: pt
Funcionalidade: Alteraçao de dados do Perfil

  @alteracao-perfil-feature
  Cenario: TA_01: Atualizar dados do perfil com telefone em branco
    Dado que estou na tela Meu Perfil
    Quando eu preencho todos os campos obrigatórios, exeto o telefone
    E eu aperto o botão salvar
    Entao devo ver uma mensagem que informe que campo telefone nao pode ser deixado em branco

  @alteracao-perfil-feature
  Cenario: TA_01: Atualizar dados do perfil com nome em branco
    Dado que estou na tela Meu Perfil
    Quando eu preencho todos os campos obrigatórios, exeto o campo nome
    E eu aperto o botão salvar
    Entao devo ver uma mensagem que informe que campo nome nao pode ser deixado em branco

  @alteracao-perfil-feature
  Cenario: TA_01: Atualizar dados do perfil com e-mail em branco
    Dado que estou na tela Meu Perfil
    Quando eu preencho todos os campos obrigatórios, exeto o e-mail
    E eu aperto o botão salvar
    Entao devo ver uma mensagem que informe que campo e-mail nao pode ser deixado em branco

  @alteracao-perfil-feature
  Cenario: TA_04: Atualizar dados do perfil com e-mail inválido
    Dado que estou na tela Meu Perfil
    Quando eu preencho todos os campos obrigatórios e informo um e-mail inválido
    E eu aperto o botão salvar
    Entao devo ver uma mensagem que informe que o e-mail informado é inválido

  @alteracao-perfil-feature
  Cenario: TA_05: Atualizar dados do perfil com telefone inválido
    Dado que estou na tela Meu Perfil
    Quando eu preencho todos os campos obrigatórios e informo um telefone inválido
    E eu aperto o botão salvar
    Entao devo ver uma mensagem que informe que o telefone informado é inválido


  @alteracao-perfil-feature
  Cenario: TA_03: Atualizar dados do perfil com e-mail pertencente a outro cadastro
    Dado que estou na tela Meu Perfil
    Quando eu preencho todos os campos obrigatórios e informo um e-mail em uso por outro cadastro
    E eu aperto o botão salvar
    Entao devo ver uma mensagem que informe que o e-mail já está em uso

  @alteracao-perfil-feature
  Cenario: TA_06: Atualizar dados do perfil com dados válidos
    Dado que estou na tela Meu Perfil
    Quando eu preencho todos os campos obrigatórios corretamente
    E eu aperto o botão salvar
    Entao devo ver uma mensagem de sucesso da operacao




