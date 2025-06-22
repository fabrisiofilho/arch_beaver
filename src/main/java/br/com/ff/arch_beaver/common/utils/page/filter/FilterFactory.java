package br.com.ff.arch_beaver.common.utils.page.filter;

import br.com.ff.arch_beaver.common.utils.page.core.Filter;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;

public interface FilterFactory {

    <T> void apply(BlazeJPAQuery<T> query, Filter filter);

}
