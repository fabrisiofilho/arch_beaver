package br.com.ff.arch_beaver.common.utils.page.core;

import br.com.ff.arch_beaver.common.error.exception.general.BusinessException;
import br.com.ff.arch_beaver.common.utils.page.filter.FilterFactory;
import br.com.ff.arch_beaver.common.utils.page.filter.FilterTypeEnum;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Listing {

    @NotNull
    @Builder.Default
    private List<Sort> sorts = new ArrayList<>();
    @NotNull
    @Builder.Default
    private List<Filter> filters = new ArrayList<>();

    public <T> List<T> apply(BlazeJPAQuery<T> query) {
        return apply(query, new HashMap<>());
    }

    public <T> List<T> apply(BlazeJPAQuery<T> query, Map<String, FilterFactory> keyPropertyAndCustomFilters) {
        addOrderSpecifier(query);
        addFilterSpecifier(query, keyPropertyAndCustomFilters);

        return query.fetch();
    }

    private <T> void addOrderSpecifier(BlazeJPAQuery<T> query) {
        for (Sort sort : sorts) {
            OrderSpecifier<?> orderBy = new OrderSpecifier<>(sort.getDirectionInOrder(),
                    Expressions.stringPath(sort.getProperty()));
            query.orderBy(orderBy);
        }
    }

    private <T> void addFilterSpecifier(BlazeJPAQuery<T> query, Map<String, FilterFactory> keyPropertyAndCustomFilters) {
        for (Filter filter : filters) {
            FilterTypeEnum filterType = filter.getType();
            if (FilterTypeEnum.FILTER_CUSTOM.equals(filterType)) {
                applyCustomFilters(filterType, query, filter, keyPropertyAndCustomFilters);
            } else {
                filterType.apply(query, filter);
            }
        }
    }

    private <T> void applyCustomFilters(FilterTypeEnum filterType, BlazeJPAQuery<T> query, Filter filter, Map<String, FilterFactory> keyPropertyAndCustomFilters) {
        if (keyPropertyAndCustomFilters.isEmpty()) {
            throw new BusinessException("Não foi informado nenhum filtro customizado para está busca.");
        }

        String property = filter.getSingleProperty();
        FilterFactory filterFactory = keyPropertyAndCustomFilters.get(property);

        filterType.apply(query, filter, filterFactory);
    }

}
