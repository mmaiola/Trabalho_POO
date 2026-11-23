
package oficina;

public class Cliente {

    private int id;
    private String nome;
    private String telefone;

    public Cliente(int id, String nome, String telefone) {
        this.id = id;
        this.nome = nome;
        this.telefone = FormatadorCelular.formatarCelular(telefone);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String toLinha() {
        return id + ";" + Dados.limpar(nome) + ";" + Dados.limpar(telefone);
    }

    public static Cliente fromLinha(String linha) {
        String[] p = linha.split(";", -1);
        return new Cliente(Integer.parseInt(p[0]), p[1], p[2]);
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Nome: " + nome + " | Telefone: " + telefone;
    }
}
