package br.com.ff.arch_beaver.common.utils.page.core;

import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.querydsl.core.QueryModifiers;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TotalElements {

    public <D, E>  long getTotalElements(BlazeJPAQuery<D> query, EntityPathBase<E> entityPathBase) {
        PathBuilder<E> pathBuilder = new PathBuilder<>(entityPathBase.getType(), entityPathBase.getMetadata());

        var countQuery = query.clone().select(pathBuilder.get("id"));

        countQuery.getMetadata().clearOrderBy();
        countQuery.getMetadata().setModifiers(QueryModifiers.EMPTY);

        return countQuery.fetchCount();
    }

}
