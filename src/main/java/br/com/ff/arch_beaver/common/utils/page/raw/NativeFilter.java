package br.com.ff.arch_beaver.common.utils.page.raw;

import br.com.ff.arch_beaver.common.utils.page.core.Filter;
import br.com.ff.arch_beaver.common.utils.page.filter.FilterTypeEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NativeFilter<T> {

    private String property;
    private FilterTypeEnum type;
    private ValueHandler<T> handler;
    private Class<?> tClass;

    @FunctionalInterface
    public interface ValueHandler<T> {
        T handle(Filter filter);
    }

}
