package br.com.ff.arch_beaver.common.utils.value;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.Normalizer;
import java.util.Objects;

/**
 * Classe utilitária para limpar strings removendo caracteres indesejados.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Cleaning {

    /**
     * Remove todos os caracteres não numéricos do texto fornecido.
     *
     * @param text o texto de entrada a ser limpo
     * @return uma string contendo apenas dígitos, ou null se o texto de entrada for nulo ou vazio
     *
     * <p>Exemplo:</p>
     * <pre>
     * clearCharactersAndLetters("a1b2c3") => "123"
     * </pre>
     */
    public static String removeNonNumericCharacters(String text) {
        if (Objects.isNull(text) || text.isBlank()) {
            return null;
        }
        return text.replaceAll("\\D", "");
    }

    /**
     * Remove caracteres específicos ('-', '(', '*') do texto fornecido.
     *
     * @param text o texto de entrada a ser limpo
     * @return uma string com os caracteres especificados removidos, ou null se o texto de entrada for nulo ou vazio
     *
     * <p>Exemplo:</p>
     * <pre>
     * clearCharacters("12-34*(56)") => "123456"
     * </pre>
     */
    public static String removeSpecificSymbols(String text) {
        if (Objects.isNull(text) || text.isBlank()) {
            return null;
        }
        return text.replaceAll("[-(*]", "");
    }

    /**
     * Remove todos os caracteres que não são letras, números ou espaços do texto fornecido.
     *
     * @param text o texto de entrada a ser limpo
     * @return uma string contendo apenas letras, números e espaços, ou null se o texto de entrada for nulo ou vazio
     *
     * <p>Exemplo:</p>
     * <pre>
     * clearAllCharacters("Hello@123! #World") => "Hello123 World"
     * </pre>
     */
    public static String removeSpecialCharacters(String text) {
        if (Objects.isNull(text) || text.isBlank()) {
            return null;
        }
        return text.replaceAll("[^a-zA-Z0-9\\s]", "");
    }

    /**
     * Remove todos os acentos do texto fornecido
     *
     * @param text o texto de entrada a ser limpo
     * @return uma string contendo o texto sem acentos,
     *
     * <p>Exemplo:</p>
     * <pre>
     * removeAccents("São José") => "Sao Jose"
     * </pre>
     */
    public static String removeAccents(String text) {
        if (Objects.isNull(text) || text.isBlank()) {
            return null;
        }

        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("[^\\p{ASCII}]", "");

    }

}
