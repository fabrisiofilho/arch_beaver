package br.com.ff.arch_beaver.common.utils.page.filter;

import br.com.ff.arch_beaver.common.error.exception.general.BusinessException;
import br.com.ff.arch_beaver.common.utils.page.core.Filter;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum FilterTypeEnum {

    SEARCH(new SearchStrategy()),
    FILTER_CUSTOM(null);

    private final FilterFactory filterFactory;

    public <T> void apply(BlazeJPAQuery<T> query, Filter filter) {
        if (Objects.isNull(filterFactory)) {
            throw new BusinessException("Filtro não informado.");
        }

        filterFactory.apply(query, filter);
    }

    public <T> void apply(BlazeJPAQuery<T> query, Filter filter, FilterFactory filterFactory) {
        if (Objects.isNull(filterFactory)) {
            throw new BusinessException("Filtro custom não informado.");
        }

        filterFactory.apply(query, filter);
    }

}
