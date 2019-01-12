#language: pt
 Funcionalidade: Busca
   Fazer uma busca preenchendo as informações.

  @busca-feature
  Cenario: Colocar somente cidade
    Dado Eu estou na tela de busca
    Quando Eu coloco a cidade e não o estabelecimento
    E Eu clico em buscar
    Entao Eu devo ver os salões retornados

  @busca-feature
  Cenario: Colocar somente estabelecimento
    Dado Eu estou na tela de busca
    Quando Eu coloco o estabelecimento e não a cidade
    E Eu clico em buscar
    Entao Eu devo ver os salões retornados

  @busca-feature
  Cenario: Colocar cidade e o estabelecimento
    Dado Eu estou na tela de busca
    Quando Eu coloco o estabelecimento e a cidade
    E Eu clico em buscar
    Entao Eu devo ver os salões retornados

  @busca-feature
  Cenario: Colocar nenhhum campo
    Dado Eu estou na tela de busca
    Quando Eu clico em buscar
    Entao Devo ver uma mensagem dizendo para digitar algum dado

  @busca-feature
  Cenario: Definir um preço máximo
    Dado Eu estou na tela de busca
    Quando Eu defino um preço
    E Eu clico em buscar
    Entao  Eu devo ver os salões retornados

  @busca-feature
  Cenario: Procurar um serviço
    Dado Eu estou na tela de busca
    Quando Eu digito um serviço
    E Eu clico em buscar
    Entao  Eu devo ver os salões retornados