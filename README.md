
# 🚗 Oficina Garagem

Sistema de gerenciamento de uma oficina mecânica, desenvolvido em **Java**, com menu no terminal e persistência em arquivos CSV.

### 1️⃣ Requisitos

- **JDK:** versão 17 ou superior.
- **Terminal:** para execução do sistema.

### 2️⃣ Como Executar

Na pasta raiz do projeto, execute:

```bash
javac -encoding UTF-8 -d out src/main/java/oficina/*.java
java -cp out oficina.Main
```

Os arquivos de dados são criados automaticamente na pasta `dados/` após os primeiros cadastros. 

⚠️ **Importante:** não apague essa pasta caso queira manter os registros.

### 3️⃣ Funcionalidades

- Cadastro e listagem de clientes.
- Cadastro e listagem de veículos vinculados a clientes.
- Cadastro e listagem de serviços.
- Abertura e listagem de ordens de serviço.
- Atualização do status das ordens.

### 4️⃣ Estrutura do Projeto

- **`Cliente`:** dados do cliente.
- **`Veiculo`:** dados do veículo e vínculo com seu proprietário.
- **`Servico`:** catálogo de serviços e valores.
- **`OrdemServico`:** relaciona cliente, veículo e serviço.
- **`Dados`:** leitura e escrita dos arquivos.
- **`Main`:** menu e fluxo de interação.
- A documentação HTML está em `docs/index.html`.

### 5️⃣ Imagem do diagrama
<img width="593" height="588" alt="diagrama" src="https://github.com/user-attachments/assets/f6bd2539-2209-4458-a5ab-d09c43ba0b8b" />


