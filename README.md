# Rest com dois datasources usando Spring

![Travis Build](https://img.shields.io/travis/suderio/springing.svg)
![License](https://img.shields.io/github/license/mashape/apistatus.svg)

## Objetivo

Criar um exemplo o mais simples possível de um microserviço usando Spring que acessa dois datasources diferentes.

## Execução e Funcionamento

O servidor irá subir localmente (se executado o jar) ou em um container docker (se a imagem gerada for usada). O path /account retorna os resultados de /account1 e /account2, que estão em duas instâncias H2 distintas em memória.

Para executar o jar: `java -jar rest-two-datasources-0.1.0.jar`

Para executar a imagem docker: `docker run -p 8080:8080 spring/rest-two-datasources`

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

`mvn install` irá criar o jar com todas as dependências e fará o build da imagem docker.

Obs.: Para abrir no eclipse (ou outra IDE) é preciso instalar o [lombok](https://projectlombok.org)

## Documentação

Relatórios e documentação do projeto [aqui](https://suderio.github.io/springing)
