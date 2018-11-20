#language: pt
Funcionalidade: Busca
  Fazer uma busca preenchendo as informações.

  @busca-feature
  Cenario: Colocar somente cidade
    Dado Eu estou na tela de busca
    Quando Eu coloco a cidade e não o serviço
    E Eu clico em buscar
    Entao Eu devo ver os salões retornados

  @busca-feature
  Cenario: Colocar somente serviço
    Dado Eu estou na tela de busca
    Quando Eu coloco o serviço e não a cidade
    E Eu clico em buscar
    Entao Eu devo ver os salões retornados

  @busca-feature
  Cenario: Colocar cidade e servico
    Dado Eu estou na tela de busca
    Quando Eu coloco o serviço e a cidade
    E Eu clico em buscar
    Entao Eu devo ver os salões retornados

  @busca-feature
  Cenario: Colocar nenhhum campo
    Dado Eu estou na tela de busca
    Quando Eu clico em buscar
    Entao Devo ver uma mensagem dizendo para digitar algum dado