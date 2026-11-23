package oficina;

import java.time.LocalDate;

public class OrdemServico {
    private int id;
    private int clienteId;
    private int veiculoId;
    private int servicoId;
    private LocalDate data;
    private String status;

    public OrdemServico(int id, int clienteId, int veiculoId, int servicoId,
                        LocalDate data, String status) {
        this.id = id;
        this.clienteId = clienteId;
        this.veiculoId = veiculoId;
        this.servicoId = servicoId;
        this.data = data;
        this.status = status;
    }

    public int getId() { return id; }
    public int getClienteId() { return clienteId; }
    public int getVeiculoId() { return veiculoId; }
    public int getServicoId() { return servicoId; }
    public LocalDate getData() { return data; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String toLinha() {
        return id + ";" + clienteId + ";" + veiculoId + ";" + servicoId + ";"
                + data + ";" + Dados.limpar(status);
    }

    public static OrdemServico fromLinha(String linha) {
        String[] p = linha.split(";", -1);
        return new OrdemServico(Integer.parseInt(p[0]), Integer.parseInt(p[1]),
                Integer.parseInt(p[2]), Integer.parseInt(p[3]),
                LocalDate.parse(p[4]), p[5]);
    }

    @Override
    public String toString() {
        return "OS: " + id + " | Cliente ID: " + clienteId + " | Veículo ID: "
                + veiculoId + " | Serviço ID: " + servicoId + " | Data: "
                + data + " | Status: " + status;
    }
}
