package br.com.ff.arch_beaver.common.utils.page.core;

import br.com.ff.arch_beaver.common.error.exception.general.BusinessException;
import br.com.ff.arch_beaver.common.utils.page.filter.FilterFactory;
import br.com.ff.arch_beaver.common.utils.page.filter.FilterTypeEnum;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pagination {

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

    public <T, E> Page<T> applyWithGroupBy(BlazeJPAQuery<T> query, EntityPathBase<E> entityPathBase) {
        query.groupBy(Expressions.datePath(Long.class, "id"));
        return apply(query, entityPathBase, new HashMap<>());
    }

    public <T, E> Page<T> apply(BlazeJPAQuery<T> query, EntityPathBase<E> entityPathBase) {
        return apply(query, entityPathBase, new HashMap<>());
    }

    public <T, E> Page<T> apply(BlazeJPAQuery<T> query, EntityPathBase<E> entityPathBase, Map<String, FilterFactory> keyPropertyAndCustomFilters) {

        query.offset((long) page * size).limit(size);
        addOrderSpecifier(query);
        addFilterSpecifier(query, keyPropertyAndCustomFilters);

        List<T> resultList = query.fetch();

        return new PageImpl<>(resultList, PageRequest.of(page, size), new TotalElements().getTotalElements(query, entityPathBase));

    }

    private <T> void addOrderSpecifier(BlazeJPAQuery<T> query) {
        for (Sort sort : sorts) {
            OrderSpecifier<?> orderBy = new OrderSpecifier<>(sort.getDirectionInOrder(),
                    Expressions.stringPath(sort.getProperty()));
            query.orderBy(orderBy);
        }
        query.orderBy(Expressions.datePath(Long.class, "id").asc());
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
