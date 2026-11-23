package oficina;

public class Veiculo {
    private int id;
    private String marca;
    private String modelo;
    private int ano;
    private String placa;
    private int clienteId;

    public Veiculo(int id, String marca, String modelo, int ano, String placa, int clienteId) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.placa = placa;
        this.clienteId = clienteId;
    }

    public int getId() { return id; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public int getAno() { return ano; }
    public String getPlaca() { return placa; }
    public int getClienteId() { return clienteId; }

    public String toLinha() {
        return id + ";" + Dados.limpar(marca) + ";" + Dados.limpar(modelo) + ";"
                + ano + ";" + Dados.limpar(placa) + ";" + clienteId;
    }

    public static Veiculo fromLinha(String linha) {
        String[] p = linha.split(";", -1);
        return new Veiculo(Integer.parseInt(p[0]), p[1], p[2],
                Integer.parseInt(p[3]), p[4], Integer.parseInt(p[5]));
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + marca + " " + modelo + " (" + ano + ")"
                + " | Placa: " + placa + " | Cliente ID: " + clienteId;
    }
}
