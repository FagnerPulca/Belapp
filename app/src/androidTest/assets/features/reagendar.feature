#language: pt
Funcionalidade: Reagendar atendimento

@reagendamento-feature
  Cenario: TA_01: Remarcação com data/hora inválidos.
    Dado que estou na tela Minha Agenda
    Quando eu seleciono o agendamento
    E eu aperto em reagendar
    E eu informo a data invalida
    Entao devo ver a mensagem na tela, O estabelecimento estará fechado na data selecionada

@reagendamento-feature
  Cenario: TA_02: Remarcação com data/hora válidos.
    Dado que estou na tela Minha Agenda
    Quando eu seleciono o agendamento
    E eu aperto em reagendar
    E eu informo a data valida
    E eu seleciono uma hora válida
    E eu aperto o botão agendar
    Entao devo ver a mensagem na tela, Agendamento realizado com sucesso!

@reagendamento-feature
  Cenario: TA_03: Remarcação com outro serviço no mesmo estabelecimento.
    Dado que estou na tela Minha Agenda
    Quando eu seleciono o agendamento
    Quando eu aperto em reagendar e apertar em alterar serviço
    E eu seleciono o serviço e o profissional
    E eu aperto o botão agendar
    Entao devo ver a mensagem na tela, Agendamento realizado com sucesso!

