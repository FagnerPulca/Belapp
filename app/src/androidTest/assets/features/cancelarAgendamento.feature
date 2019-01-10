#language: pt
Funcionalidade: Cancelamento de Agendamento

  @cancelar-agendamento-feature
  Cenario: Desistir de um cancelamento.
    Dado que estou na tela de agendamentos
    Quando eu seleciono um agendamento
    E eu aperto em cancelar
    E eu não confirmo oagendamento
    Entao devo ver a mensagem, Agendamento não cancelado

  @cancelar-agendamento-feature
  Cenario: Cancelar um agendamento com sucesso.
    Dado que estou na tela de agendamentos
    Quando eu seleciono um agendamento
    E eu aperto em cancelar
    E eu confirmo o cancelamento
    Entao devo ver a mensagem, Agendamento cancelado com sucesso!

