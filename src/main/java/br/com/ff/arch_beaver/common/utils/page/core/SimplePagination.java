package br.com.ff.arch_beaver.common.utils.page.core;

import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.querydsl.core.types.dsl.EntityPathBase;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimplePagination {

    @NotNull
    private Integer page;
    @NotNull
    private Integer size;

    public <T, E> Page<T> apply(BlazeJPAQuery<T> query, EntityPathBase<E> entityPathBase) {
        query.offset((long) page * size).limit(size);
        List<T> resultList = query.fetch();

        return new PageImpl<>(resultList, PageRequest.of(page, size),
                new TotalElements().getTotalElements(query, entityPathBase));
    }

}
