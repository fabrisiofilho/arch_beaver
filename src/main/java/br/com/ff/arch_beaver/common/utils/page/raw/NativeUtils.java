package br.com.ff.arch_beaver.common.utils.page.raw;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NativeUtils {

    public static String idsToString(List<String> longIds) {
        if (Objects.isNull(longIds) || longIds.isEmpty()) {
            return null;
        }

        return String.join(", ", longIds);
    }

    public static Long[] idsToLong(List<String> longIds) {
        if (Objects.isNull(longIds) || longIds.isEmpty()) {
            return null;
        }

        return longIds.stream()
            .map(Long::valueOf)
            .toArray(Long[]::new);
    }

    public static String[] idsToArrayString(List<String> longIds) {
        if (Objects.isNull(longIds) || longIds.isEmpty()) {
            return null;
        }

        return longIds.stream()
            .map(String::valueOf)
            .toArray(String[]::new);
    }

    public static Long idToLong(String longId) {
        if (Objects.isNull(longId)) {
            return null;
        }

        return Long.valueOf(longId);
    }


}
