package br.com.ff.arch_beaver.common.utils.page.config;

import br.com.ff.arch_beaver.common.error.exception.general.BusinessException;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class PaginationParams {

    public static PaginationParams build() {
        return new PaginationParams();
    }

    private final Map<String, ParamValue> filters = new HashMap<>();

    /**
     * Método para adicionar filtros de forma fluente.
     * Verifica se a chave já existe no mapa, e se existir, lança uma exceção para evitar duplicação.
     *
     * @param key    A chave do filtro.
     * @param aClass A classe do valor para casting adequado.
     * @param value  O valor do filtro.
     * @return a própria instância do Params para fluência.
     * @throws BusinessException caso a chave já exista.
     */
    public PaginationParams add(String key, Class<?> aClass, Object value) {
        if (value == null) {
            return this;
        }

        // Validação para evitar chaves duplicadas
        if (filters.containsKey(key)) {
            throw new BusinessException("A chave '" + key + "' já foi adicionada aos filtros.");
        }

        filters.put(key, ParamValue.builder().value(value).aClass(aClass).build());
        return this;
    }

    /**
     * Método para recuperar o valor do filtro dado uma chave e fazer o casting
     * para o tipo correto com base na classe fornecida.
     *
     * @param key A chave do filtro.
     * @param <T> O tipo para o qual o valor deve ser convertido.
     * @return O valor do filtro já com o tipo correto ou null se a chave não existir.
     * @throws ClassCastException Se o valor não puder ser convertido para o tipo esperado.
     */
    public <T> T get(String key) {
        ParamValue paramValue = filters.get(key);
        if (paramValue == null) {
            throw new BusinessException(String.format("O valor para o parâmetro '%s' não foi encontrado. Verifique se a chave está correta ou se o filtro foi devidamente configurado.", key));
        }

        // Realiza o casting de acordo com o tipo armazenado na ParamValue
        return (T) paramValue.getValue();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ParamValue {
        private Class<?> aClass;
        private Object value;
    }
}

