package oficina;

public class Servico {
    private int id;
    private String descricao;
    private double valor;

    public Servico(int id, String descricao, double valor) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
    }

    public int getId() { return id; }
    public String getDescricao() { return descricao; }
    public double getValor() { return valor; }

    public String toLinha() {
        return id + ";" + Dados.limpar(descricao) + ";" + valor;
    }

    public static Servico fromLinha(String linha) {
        String[] p = linha.split(";", -1);
        return new Servico(Integer.parseInt(p[0]), p[1], Double.parseDouble(p[2]));
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + descricao + " | Valor: R$ "
                + String.format("%.2f", valor);
    }
}
