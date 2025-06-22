package br.com.ff.arch_beaver.common.utils.page.config;

import br.com.ff.arch_beaver.common.utils.page.core.Pagination;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import org.springframework.data.domain.Page;

public abstract class AbstractQueryExecutor<T> implements QueryHandler<T> {

    public Page<T> pageable(Pagination pagination, PaginationParams paginationParams) {
        BlazeJPAQuery<T> query = createQuery(paginationParams);
        return paginationConfigurationFetch(pagination, query);
    }

    protected abstract Page<T> paginationConfigurationFetch(Pagination pagination, BlazeJPAQuery<T> query);

}

