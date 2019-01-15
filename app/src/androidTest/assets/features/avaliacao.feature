#language: pt

Funcionalidade: Visualizar avaliações do estabelecimento

@avaliacao-feature
  Cenario: Estabelecimento com avalição
    Dado eu estou na tela do salão beauty
    Quando eu clico no botão avaliações
    Entao eu vejo os clientes que avaliaram

@avaliacao-feature
  Cenario: Estabelecimento sem avaliação
    Dado eu estou na tela do Imperador Darth Vader
    Quando eu clico no botão avaliações
    Então não há avaliações para ver