package demo.java.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class LocalDateDemo {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
        System.out.println("localDate=" + localDate);
        System.out.println("localDate=" + localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println("localDate=" + localDate.getYear() + String.format("-%02d", localDate.getMonthValue()) + String.format("-%02d", localDate.getDayOfMonth()));
        System.out.println("localDate.getDayOfWeek=" + localDate.getDayOfWeek());
        System.out.println("localDate.getDayOfWeek=" + localDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US));
        System.out.println("localDate.getDayOfWeek=" + localDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.CHINA));
        System.out.println("localDate.getDayOfYear=" + localDate.getDayOfYear());
        System.out.println("localDate.lengthOfMonth=" + localDate.lengthOfMonth());
        System.out.println("localDate.lengthOfYear=" + localDate.lengthOfYear());
        System.out.println();

        System.out.println("weekOfWeekBasedYear=" + localDate.get(WeekFields.ISO.weekOfWeekBasedYear()));
        WeekFields localWeekFields = WeekFields.of(Locale.getDefault());
        int localWeekOfYear = localDate.get(localWeekFields.weekOfYear());
        System.out.println("localWeekOfYear=" + localWeekOfYear);
        WeekFields usWeekFields = WeekFields.of(Locale.US);
        int usWeekOfYear = localDate.get(usWeekFields.weekOfYear());
        System.out.println("usWeekOfYear=" + usWeekOfYear);
        System.out.println();

        localDate = localDate.plusDays(1);
//        localDate = localDate.plusWeeks(1);
//        localDate = localDate.plusMonths(1);
//        localDate = localDate.plusYears(1);
        System.out.println("localDate + 1=" + localDate);
        localDate = localDate.minusDays(1);
        System.out.println("localDate - 1=" + localDate);
        System.out.println();

        LocalDate epochDay = LocalDate.of(1970, 1, 1);
        System.out.println(epochDay.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        System.out.println("epochDay.getDayOfWeek=" + epochDay.getDayOfWeek());
        System.out.println();

        LocalDate testDay = LocalDate.of(2024, 7, 29);
        LocalDate previousMonday = testDay.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        LocalDate previousOrSameMonday = testDay.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        System.out.println("previousMonday=" + previousMonday + ", previousOrSameMonday=" + previousOrSameMonday);
        LocalDate nextSunday = testDay.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        LocalDate nextOrSameSunday = testDay.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        System.out.println("nextSunday=" + nextSunday + ", nextOrSameSunday=" + nextOrSameSunday);
        System.out.println();

        // 获取本周的第一天（周一）、最后一天（周日）
        LocalDate mondayOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sundayOfWeek = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        System.out.println("mondayOfWeek=" + mondayOfWeek + ", sundayOfWeek=" + sundayOfWeek);
        LocalDate mondayOfLastWeek = mondayOfWeek.minusDays(7);
        LocalDate sundayOfLastWeek = sundayOfWeek.minusDays(7);
        System.out.println("mondayOfLastWeek=" + mondayOfLastWeek + ", sundayOfLastWeek=" + sundayOfLastWeek);
        LocalDate mondayOfNextWeek = LocalDate.now().plusDays(7).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sundayOfNextWeek = LocalDate.now().plusDays(7).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        System.out.println("mondayOfNextWeek=" + mondayOfNextWeek + ", sundayOfNextWeek=" + sundayOfNextWeek);
        System.out.println();

        // 获取本月第一天、最后一天
        LocalDate firstDayOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        System.out.println("firstDayOfMonth=" + firstDayOfMonth + ", lastDayOfMonth=" + lastDayOfMonth);
        // 获取上个月第一天、最后一天
        LocalDate firstDayOfLastMonth = LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOfLastMonth = LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        System.out.println("firstDayOfLastMonth=" + firstDayOfLastMonth + ", lastDayOfLastMonth=" + lastDayOfLastMonth);
        // 获取下个月第一天、最后一天
        LocalDate firstDayOfNextMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
        LocalDate lastDayOfNextMonth = LocalDate.now().plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        System.out.println("firstDayOfNextMonth=" + firstDayOfNextMonth + ", lastDayOfNextMonth=" + lastDayOfNextMonth);
        System.out.println();

        // 获取今年第一天、最后一天
        LocalDate firstDayOfYear = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
        LocalDate lastDayOfYear = LocalDate.now().with(TemporalAdjusters.lastDayOfYear());
        System.out.println("firstDayOfYear=" + firstDayOfYear + ", lastDayOfYear=" + lastDayOfYear);
        // 获取去年第一天、最后一天
        LocalDate firstDayOfLastYear = LocalDate.now().minusYears(1).with(TemporalAdjusters.firstDayOfYear());
        LocalDate lastDayOfLastYear = LocalDate.now().minusYears(1).with(TemporalAdjusters.lastDayOfYear());
        System.out.println("firstDayOfLastYear=" + firstDayOfLastYear + ", lastDayOfLastYear=" + lastDayOfLastYear);
        // 获取明年第一天、最后一天
        LocalDate firstDayOfNextYear = LocalDate.now().with(TemporalAdjusters.firstDayOfNextYear());
        LocalDate lastDayOfNextYear = LocalDate.now().plusYears(1).with(TemporalAdjusters.lastDayOfYear());
        System.out.println("firstDayOfNextYear=" + firstDayOfNextYear + ", lastDayOfNextYear=" + lastDayOfNextYear);
    }
}