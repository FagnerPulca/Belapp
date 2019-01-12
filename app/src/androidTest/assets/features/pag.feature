#language: pt
Funcionalidade: Favoritar

  @pag-feature
  Cenario:TA_01: Adicionar na lista dos favoritos
    Dado que eu estou na tela de detalhes do salao
    E eu clico em favoritar
    Entao Eu devo ver uma animacao indicando que foi favoritado

  @pag-feature
  Cenario:TA_02: Retirar salao na lista dos favoritos
    Dado que eu estou na tela de detalhes do salao
    E eu clico em favoritar
    Entao Eu devo ver uma animacao indicando que foi desfavoritado