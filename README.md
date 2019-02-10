# Rest com dois datasources usando Spring

![Travis Build](https://img.shields.io/travis/suderio/springing.svg)
![License](https://img.shields.io/github/license/mashape/apistatus.svg)

## Objetivo

Criar um exemplo o mais simples possível para mostrar que não é complicado criar um microserviço usando Spring que acessa dois datasources diferentes.

## Estrutura

Cada datasource está em um package (isto é importante). As entidades de exemlo (`FirstAccount` e `SecondAccount`) implementam uma interface `Account` apenas para conveniência do `AccountRepository`, que consulta os dois repositórios.

rest.db1
- FirstAccount (@Entity)
- FirstAccountRepository (@RepositoryRestResource)
- FirstDsConfig (@Configuration)

rest.db2
- SecondAccount (@Entity)
- SecondAccountRepository (@RepositoryRestResource)
- SecondDsConfig (@Configuration)

As classes anotadas com @Configuration são responsáveis pela configuração dos datasources (e entityManagers, etc.) Essas configurações são lidas do arquivo application.properties, mas podem estar em variáveis de ambiente ou em arquivos separados por ambiente.

## Construção

Obs.: Para abrir no eclipse é preciso instalar o [lombok](https://projectlombok.org)

## Documentação

Relatórios e documentação do projeto [aqui](https://suderio.github.io/springing)