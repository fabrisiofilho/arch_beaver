package br.com.ff.arch_beaver.common.utils.page.filter;

import br.com.ff.arch_beaver.common.utils.page.core.Filter;
import br.com.ff.arch_beaver.common.utils.value.Cleaning;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

import java.util.Objects;

public class SearchStrategy implements FilterFactory {

    private static final String TRANSALTE_ACCENT = "translate(lower(%s), 'áàãâéèêíìîóòõôúùûç', 'aaaaeeeiiioooouuuc')";

    @Override
    public <T> void apply(BlazeJPAQuery<T> query, Filter filter) {
        if (Objects.nonNull(filter.getSingleValue()) && !filter.getSingleValue().isEmpty()) {
            BooleanExpression predicate = null;
            String valueFilter = filter.getSingleValue().toLowerCase().trim();

            for (String property : filter.getProperties()) {
                BooleanExpression condition = Expressions.stringPath(String.format(TRANSALTE_ACCENT, property))
                    .like("%" + Cleaning.removeAccents(valueFilter) + "%")
                    .or(Expressions.stringPath(String.format(TRANSALTE_ACCENT, property))
                    .like("%" + Cleaning.removeSpecialCharacters(valueFilter) + "%"));

                if (predicate == null) {
                    predicate = condition;
                } else {
                    predicate = predicate.or(condition);
                }
            }

            query.where(predicate);
        }
    }

}
