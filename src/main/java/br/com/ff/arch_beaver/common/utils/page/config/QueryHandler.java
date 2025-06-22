package br.com.ff.arch_beaver.common.utils.page.config;

import com.blazebit.persistence.querydsl.BlazeJPAQuery;

@FunctionalInterface
public interface QueryHandler<T> {

    /**
     * Método responsável por criar e retornar a query.
     *
     * @return BlazeJPAQuery<T> A query que será usada para paginação ou exportação.
     */
    BlazeJPAQuery<T> createQuery(PaginationParams paginationParams);

}

