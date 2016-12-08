package com.piotrek;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Piotrek on 2016-10-15.
 */
public class DateSpan {
    private final LocalDate beginDate;
    private final LocalDate endDate;

    private DateSpan(LocalDate beginDate, LocalDate endDate) throws InvalidArgumentException {
        if(endDate.isBefore(beginDate))
            throw new InvalidArgumentException(new String[0]);

        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public static DateSpan between(LocalDate beginDate, LocalDate endDate) throws InvalidArgumentException {
        return new DateSpan(beginDate, endDate);
    }

    public LocalDate[] getDates() {
//        List<Integer> range = IntStream.rangeClosed(start, end)
//                .boxed().collect(Collectors.toList());
        long daysWithBeginCount = ChronoUnit.DAYS.between(beginDate, endDate) + 1;
        List<LocalDate> dates = new ArrayList<>();
        for (int i =0; i < daysWithBeginCount; i++){
            dates.add(beginDate.plusDays(i));
        }
        LocalDate[] datesArray = new LocalDate[dates.size()];
        return dates.toArray(datesArray);
    }
}
