package br.com.ff.arch_beaver.common.utils.page.core;

import br.com.ff.arch_beaver.common.utils.page.filter.FilterTypeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Filter {

    @NotEmpty
    @NotNull
    private List<String> properties;
    @NotNull
    private List<String> values;
    @NotNull
    private FilterTypeEnum type;

    public String getSingleProperty() {
        var value = getSingle(properties, "propriedade");
        if (value.isBlank()) {
            throw new IllegalStateException("Uma de suas propriedades está nula ou vazia");
        }
        return value;
    }

    public List<String> getTwoProperties() {
        var value = getTwo(properties, "propriedade");

        var propertyOne = value.get(0);
        var propertyTwo = value.get(0);

        if (propertyOne.isBlank()) {
            throw new IllegalStateException("A primeira propriedade está nula ou vazia");
        }

        if (propertyTwo.isBlank()) {
            throw new IllegalStateException("A segunda propriedade está nula ou vazia");
        }

        return value;
    }

    public String getSingleValue() {
        return getSingle(values, "valor");
    }

    public List<Boolean> getValuesInBoolean() {
        try {
            return values.stream().map(it -> {
                if (it.isBlank()) {
                    throw new IllegalStateException();
                }
                return Boolean.valueOf(it);
            }).toList();
        } catch (Exception exception) {
            throw new IllegalStateException("Ao utilizar o filtro de seleção booleana, certifique-se de fornecer apenas valores booleanos");
        }
    }

    public Long getSingleValuesInLong() {
        try {
            return Long.valueOf(getSingle(values, "valor"));
        } catch (Exception exception) {
            throw new IllegalStateException("Ao utilizar o filtro de seleção simples, certifique-se de fornecer apenas valor numérico");
        }
    }

    public List<Long> getValuesInLong() {
        try {
            return values.stream().map(Long::valueOf).toList();
        } catch (Exception exception) {
            throw new IllegalStateException("Ao utilizar o filtro de seleção múltiplo, certifique-se de fornecer apenas valores numéricos");
        }
    }

    public List<String> getTwoValues(){
        return getTwo(values, "values");
    }

    private <T> T getSingle(List<T> list, String filterType) {
        if (list.size() > 1) {
            throw new IllegalStateException(String.format("Mais de um(a) %s informado no filtro.", filterType));
        }
        return list.get(0);
    }

    private <T> List<T> getTwo(List<T> list, String filterType) {
        if (list.size() != 2) {
            throw new IllegalStateException(String.format("Quantidade de %s diferente de duas informadas no filtro.", filterType));
        }
        return list;
    }

}
