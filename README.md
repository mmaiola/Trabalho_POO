# Oficina Garagem

Sistema de gerenciamento de uma oficina mecânica, desenvolvido em Java com menu no terminal e persistência em arquivos CSV.

## Requisitos
- JDK 17 ou superior
- Terminal

## Como executar
Na pasta raiz do projeto:

```bash
javac -encoding UTF-8 -d out src/main/java/oficina/*.java
java -cp out oficina.Main
```

Os arquivos de dados são criados automaticamente na pasta `dados/` após os primeiros cadastros. Não apague essa pasta se quiser manter os registros.

## Funcionalidades
- Cadastro e listagem de clientes
- Cadastro e listagem de veículos vinculados a clientes
- Cadastro e listagem de serviços
- Abertura e listagem de ordens de serviço
- Atualização do status das ordens

## Estrutura
- `Cliente`: dados do cliente.
- `Veiculo`: dados do veículo e vínculo com seu proprietário.
- `Servico`: catálogo de serviços e valores.
- `OrdemServico`: relaciona cliente, veículo e serviço.
- `Dados`: leitura e escrita dos arquivos.
- `Main`: menu e fluxo de interação.

A documentação HTML está em `docs/index.html`.
