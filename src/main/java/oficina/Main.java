
package oficina;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static final List<Cliente> clientes =
            Dados.carregar("clientes.csv", Cliente::fromLinha);
    private static final List<Veiculo> veiculos =
            Dados.carregar("veiculos.csv", Veiculo::fromLinha);
    private static final List<Servico> servicos =
            Dados.carregar("servicos.csv", Servico::fromLinha);
    private static final List<OrdemServico> ordens =
            Dados.carregar("ordens.csv", OrdemServico::fromLinha);

    public static void main(String[] args) {
        int opcao;

        do {
            System.out.println("\n========== OFICINA GARAGEM ==========");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Listar clientes");
            System.out.println("3 - Cadastrar veículo");
            System.out.println("4 - Listar veículos");
            System.out.println("5 - Cadastrar serviço");
            System.out.println("6 - Listar serviços");
            System.out.println("7 - Abrir ordem de serviço");
            System.out.println("8 - Listar ordens de serviço");
            System.out.println("9 - Atualizar status da ordem");
            System.out.println("0 - Sair");

            opcao = inteiro("Escolha: ");

            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> listar(clientes);
                case 3 -> cadastrarVeiculo();
                case 4 -> listar(veiculos);
                case 5 -> cadastrarServico();
                case 6 -> listar(servicos);
                case 7 -> abrirOrdem();
                case 8 -> listar(ordens);
                case 9 -> atualizarStatus();
                case 0 -> System.out.println("Sistema encerrado. Os dados foram mantidos.");
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        sc.close();
    }

    private static void cadastrarCliente() {
        int id = proximoId(clientes.stream().mapToInt(Cliente::getId).toArray());

        System.out.print("Nome: ");
        String nome = sc.nextLine().trim();

        if (nome.isBlank()) {
            System.out.println("O nome não pode ficar vazio.");
            return;
        }

        String telefone;

        while (true) {
            System.out.print("Telefone: ");
            telefone = sc.nextLine().trim();

            try {
                telefone = FormatadorCelular.formatarCelular(telefone);
                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
                System.out.print("Deseja tentar novamente? (S/N): ");

                String resposta = sc.nextLine().trim();

                if (!resposta.equalsIgnoreCase("S")) {
                    System.out.println("Cadastro cancelado.");
                    return;
                }
            }
        }

        clientes.add(new Cliente(id, nome, telefone));
        Dados.salvar("clientes.csv", clientes, Cliente::toLinha);

        System.out.println("Cliente cadastrado com ID " + id + ".");
    }

    private static void cadastrarVeiculo() {
        if (clientes.isEmpty()) {
            System.out.println("Cadastre um cliente antes de cadastrar um veículo.");
            return;
        }

        listar(clientes);

        int clienteId = inteiro("ID do proprietário: ");

        if (buscarCliente(clienteId) == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        int id = proximoId(veiculos.stream().mapToInt(Veiculo::getId).toArray());

        System.out.print("Marca: ");
        String marca = sc.nextLine().trim();

        System.out.print("Modelo: ");
        String modelo = sc.nextLine().trim();

        int ano = inteiro("Ano: ");

        System.out.print("Placa: ");
        String placa = sc.nextLine().trim().toUpperCase();

        if (marca.isBlank() || modelo.isBlank() || placa.isBlank() || ano < 1900 || ano > 2100) {
            System.out.println("Dados do veículo inválidos.");
            return;
        }

        veiculos.add(new Veiculo(id, marca, modelo, ano, placa, clienteId));
        Dados.salvar("veiculos.csv", veiculos, Veiculo::toLinha);

        System.out.println("Veículo cadastrado com ID " + id + ".");
    }

    private static void cadastrarServico() {
        int id = proximoId(servicos.stream().mapToInt(Servico::getId).toArray());

        System.out.print("Descrição do serviço: ");
        String descricao = sc.nextLine().trim();

        double valor = decimal("Valor (ex.: 150,50): ");

        if (descricao.isBlank() || valor < 0) {
            System.out.println("Dados do serviço inválidos.");
            return;
        }

        servicos.add(new Servico(id, descricao, valor));
        Dados.salvar("servicos.csv", servicos, Servico::toLinha);

        System.out.println("Serviço cadastrado com ID " + id + ".");
    }

    private static void abrirOrdem() {
        if (clientes.isEmpty() || veiculos.isEmpty() || servicos.isEmpty()) {
            System.out.println("Cadastre pelo menos um cliente, veículo e serviço primeiro.");
            return;
        }

        listar(clientes);

        int clienteId = inteiro("ID do cliente: ");

        if (buscarCliente(clienteId) == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        List<Veiculo> doCliente = veiculos.stream()
                .filter(v -> v.getClienteId() == clienteId).toList();

        if (doCliente.isEmpty()) {
            System.out.println("Esse cliente ainda não possui veículos cadastrados.");
            return;
        }

        System.out.println("\nVeículos do cliente:");
        listar(doCliente);

        int veiculoId = inteiro("ID do veículo: ");

        Veiculo veiculo = buscarVeiculo(veiculoId);

        if (veiculo == null || veiculo.getClienteId() != clienteId) {
            System.out.println("Veículo inválido para esse cliente.");
            return;
        }

        listar(servicos);

        int servicoId = inteiro("ID do serviço: ");

        if (buscarServico(servicoId) == null) {
            System.out.println("Serviço não encontrado.");
            return;
        }

        int id = proximoId(ordens.stream().mapToInt(OrdemServico::getId).toArray());

        ordens.add(new OrdemServico(id, clienteId, veiculoId, servicoId,
                LocalDate.now(), "ABERTA"));

        Dados.salvar("ordens.csv", ordens, OrdemServico::toLinha);

        System.out.println("Ordem de serviço " + id + " aberta com sucesso.");
    }

    private static void atualizarStatus() {
        if (ordens.isEmpty()) {
            System.out.println("Não há ordens cadastradas.");
            return;
        }

        listar(ordens);

        int id = inteiro("ID da ordem: ");

        OrdemServico ordem = ordens.stream()
                .filter(o -> o.getId() == id)
                .findFirst()
                .orElse(null);

        if (ordem == null) {
            System.out.println("Ordem não encontrada.");
            return;
        }

        System.out.println("1 - ABERTA | 2 - EM_ANDAMENTO | 3 - CONCLUIDA | 4 - CANCELADA");

        int escolha = inteiro("Novo status: ");

        String status = switch (escolha) {
            case 1 -> "ABERTA";
            case 2 -> "EM_ANDAMENTO";
            case 3 -> "CONCLUIDA";
            case 4 -> "CANCELADA";
            default -> null;
        };

        if (status == null) {
            System.out.println("Status inválido.");
            return;
        }

        ordem.setStatus(status);
        Dados.salvar("ordens.csv", ordens, OrdemServico::toLinha);

        System.out.println("Status atualizado.");
    }

    private static Cliente buscarCliente(int id) {
        return clientes.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private static Veiculo buscarVeiculo(int id) {
        return veiculos.stream()
                .filter(v -> v.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private static Servico buscarServico(int id) {
        return servicos.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private static int proximoId(int[] ids) {
        int maior = 0;

        for (int id : ids) {
            if (id > maior) maior = id;
        }

        return maior + 1;
    }

    private static int inteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);

            try {
                return Integer.parseInt(sc.nextLine().trim());

            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    private static double decimal(String mensagem) {
        while (true) {
            System.out.print(mensagem);

            try {
                return Double.parseDouble(sc.nextLine().trim().replace(",", "."));

            } catch (NumberFormatException e) {
                System.out.println("Digite um valor válido.");
            }
        }
    }

    private static void listar(List<?> itens) {
        if (itens.isEmpty()) {
            System.out.println("Nenhum registro encontrado.");
            return;
        }

        itens.forEach(System.out::println);
    }
}
