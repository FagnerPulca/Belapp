#language: pt

Funcionalidade: Avaliar estabelecimento

@avaliar-feature
  Cenario: Dar nota, deixar o comentário em branco e enviar estando logado
    Dado eu estou na tela de avaliações do Salão Beauty
    Quando eu clico em avaliações
    E dou uma nota
    E deixo comentario em branco
    E aperto no botão enviar
    Então vejo a mensagem de sucesso no envio

@avaliar-feature
  Cenario: Dar nota, adicionar comentário e enviar estando logado
   Dado eu estou na tela de avaliações do Salão Beauty
   Quando eu clico em avaliações
   E dou uma nota
   E faço um comentário
   E aperto no botão enviar
   Então vejo a mensagem de sucesso no envio

@avaliar-feature
  Cenario: Não dar nota, deixar comentário em branco  e enviar estando logado
    Dado eu estou na tela de avaliações do Salão Beauty
    Quando eu clico em avaliações
    E não dou nota
    E deixo comentario em branco
    E aperto no botão enviar
    Então vejo uma mensagem de falha no envio

@avaliar-feature
  Cenario: Não dar nota, adicionar cometário e enviar estando logado
    Dado eu estou na tela de avaliações do Salão Beauty
    Quando eu clico em avaliações
    E não dou nota
    E faço um comentário
    E aperto no botão enviar
    Então vejo uma mensagem de falha no envio

@avaliar-feature
  Cenario: Avaliar sem está logado
    Dado eu estou na tela de avaliações do Salão Beauty
    E não estou logado
    Quando eu clico em avaliações
    Então vejo um mensagem que preciso está logado