package oficina;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Dados {
    private static final Path PASTA = Paths.get("dados");

    private static Path arquivo(String nome) throws IOException {
        Files.createDirectories(PASTA);
        return PASTA.resolve(nome);
    }

    public static String limpar(String texto) {
        return texto.replace(";", ",").replace("\n", " ").replace("\r", " ");
    }

    public static <T> List<T> carregar(String nome, Function<String, T> conversor) {
        List<T> itens = new ArrayList<>();
        try {
            Path caminho = arquivo(nome);
            if (!Files.exists(caminho)) return itens;
            for (String linha : Files.readAllLines(caminho, StandardCharsets.UTF_8)) {
                if (!linha.isBlank()) {
                    try {
                        itens.add(conversor.apply(linha));
                    } catch (RuntimeException e) {
                        System.out.println("Aviso: uma linha inválida foi ignorada em " + nome);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler " + nome + ": " + e.getMessage());
        }
        return itens;
    }

    public static <T> void salvar(String nome, List<T> itens, Function<T, String> conversor) {
        try {
            List<String> linhas = itens.stream().map(conversor).toList();
            Files.write(arquivo(nome), linhas, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.out.println("Erro ao salvar " + nome + ": " + e.getMessage());
        }
    }
}
