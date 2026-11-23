package oficina;

public class FormatadorCelular {

    private FormatadorCelular() {
    }

    public static String formatarCelular(String numero) {

        numero = numero.replaceAll("\\D", "");

        if (numero.length() != 11) {
            throw new IllegalArgumentException(
                    "O número deve ter 11 dígitos, incluindo o DDD."
            );
        }

        return numero.replaceFirst(
                "(\\d{2})(\\d{5})(\\d{4})",
                "($1) $2-$3"
        );
    }
}