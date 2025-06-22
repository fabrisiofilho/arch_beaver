package br.com.ff.arch_beaver.common.utils.date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class DateUtil {

    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null)
            return null;

        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
