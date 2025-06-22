package br.com.ff.arch_beaver.common.utils.page.raw;


import br.com.ff.arch_beaver.common.error.exception.general.BusinessException;
import br.com.ff.arch_beaver.common.utils.page.core.Filter;
import br.com.ff.arch_beaver.common.utils.page.core.Sort;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NativePagination {

    @NotNull
    @Builder.Default
    private Integer page = 0;
    @NotNull
    @Builder.Default
    private Integer size = 10;
    @NotNull
    @Builder.Default
    private List<Sort> sorts = new ArrayList<>();
    @NotNull
    @Builder.Default
    private List<Filter> filters = new ArrayList<>();

    public <T> T getParametersByFilter(NativeFilter<T> nativeFilter) {
        if (filters.isEmpty() || Objects.isNull(nativeFilter)) {
            return null;
        }

        Filter filter = findFilterByProperty(filters, nativeFilter.getProperty());

        if (Objects.isNull(filter)) {
            return null;
        }

        if(!filter.getType().equals(nativeFilter.getType())) {
            throw new BusinessException(
                String.format("Filtro informado não é suportado por esta propriedade. Deve utilizar %s", nativeFilter.getType())
            );
        }

        if (filter.getValues().isEmpty()) {
            return null;
        }

        return nativeFilter.getHandler().handle(filter);
    }

    public Filter findFilterByProperty(List<Filter> filters, String property) {
        for (Filter filter : filters) {
            if (filter.getProperties().contains(property)) {
                return filter;
            }
        }
        return null;
    }

}
