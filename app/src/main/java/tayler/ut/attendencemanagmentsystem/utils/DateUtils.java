package tayler.ut.attendencemanagmentsystem.utils;

import android.text.TextUtils;
 

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Months;
import org.joda.time.Period;
import org.joda.time.YearMonth;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateUtils   {



    private static String TAG = "DateUtils";


    public static String getCurrentDateForStudent() {
        String currentMonthYear = "";

        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MMM-yyyy");
            currentMonthYear = dtf.print(localDateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return currentMonthYear;
    }


    public static String getMonthFromDate(String strDate) {
        String convertedDate = "";

        try {
            DateTime dt = DateTime.parse(strDate, DateTimeFormat.forPattern("dd-MM-yyyy").withLocale(Locale.ENGLISH));
            convertedDate = dt.toString("MMM");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return convertedDate;
    }

    public static String getConvertedDateForExpenseChart(String strDate) {
        String convertedDate = "";

        try {
            DateTime dt = DateTime.parse(strDate, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.ENGLISH));
            convertedDate = dt.toString("yyyy-MM-dd");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return convertedDate;
    }

    public static String getConvertedDateForMFNFO(String strDate) {
        String convertedDate = "";

        try {
            DateTime dt = DateTime.parse(strDate, DateTimeFormat.forPattern("yyyy/MM/dd").withLocale(Locale.ENGLISH));
            convertedDate = dt.toString("dd/MMM/yyyy");
        } catch (Exception ex) {
            ex.printStackTrace(); 
        }

        return convertedDate;
    }

    public static String getConvertedDateForMFNFOLumsumUser(String strDate) {

        String convertedDate = "";

        try {
            DateTime dt = DateTime.parse(strDate, DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.ENGLISH));
            convertedDate = dt.toString("yyyy/MM/dd");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return convertedDate;
    }

    public static String getConvertedLFOrderBook(String strDate) {
        String convertedDate = "";

        try {
            DateTime dt = DateTime.parse(strDate, DateTimeFormat.forPattern("MM/dd/yyyy hh:mm:ss aa").withLocale(Locale.ENGLISH));
            convertedDate = dt.toString("dd/MM/yyyy hh:mm:ss aa");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return convertedDate;
    }

    public static String getConvertedLFOrderBookNewOnlyDate(String strDate) {
        String convertedDate = "";

        try {
            DateTime dt = DateTime.parse(strDate, DateTimeFormat.forPattern("MM/dd/yyyy hh:mm:ss aa").withLocale(Locale.ENGLISH));
            convertedDate = dt.toString("dd MMM yyyy");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return convertedDate;
    }

    public static String getConvertedLFOrderBookNewOnlyTime(String strDate) {
        String convertedDate = "";

        try {
            DateTime dt = DateTime.parse(strDate, DateTimeFormat.forPattern("MM/dd/yyyy hh:mm:ss aa").withLocale(Locale.ENGLISH));
            convertedDate = dt.toString("hh:mm:ss aa");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return convertedDate;
    }

    public static String getConvertedDateForNAV(String strDate) {
        String convertedDate = "";

        try {
            DateTime dt = DateTime.parse(strDate, DateTimeFormat.forPattern("dd/MMMM/yyyy").withLocale(Locale.ENGLISH));
            convertedDate = dt.toString("ddMMMyyyy");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return convertedDate;
    }

    public static String getConvertedDate(String strDate) {
        String convertedDate = "";

        try {
            DateTime dt = DateTime.parse(strDate, DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.ENGLISH));
            convertedDate = dt.toString("dd/MMM/yyyy");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return convertedDate;
    }

    public static String getTimeStampForBeewise() {
        String currentMonthYear = "";

        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            currentMonthYear = dtf.print(localDateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return currentMonthYear;
    }


    public static String getDayFromDate(String strDate) {
        String convertedDate = "";

        try {
            DateTime dt = DateTime.parse(strDate, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.ENGLISH));
            convertedDate = dt.toString("dd");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return convertedDate;
    }


    public static LocalDateTime getJodaDateTimeFromString(String date) {
        LocalDateTime localDateTime;
        try {
            localDateTime = LocalDateTime.parse(date, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (IllegalArgumentException e) {
            localDateTime = LocalDateTime.parse(date, DateTimeFormat.forPattern("YYYY-MM-dd"));
        }

        return localDateTime;
    }

    public static String getTimeStampForSocket() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String timeStamp = localDateTime.getHourOfDay() + ":" + localDateTime.getMinuteOfHour() + ":" + localDateTime.getSecondOfMinute();
        return timeStamp;
    }

    public static int getCurrentJodaDay() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.getDayOfMonth();
    }

    public static String getCurrentJodaMonthFirstDate() {
        DateTime dateTime = new DateTime().dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        return dtf.print(dateTime);
    }

    public static String getCurrentJodaYearFirstDate() {
        DateTime dateTime = new DateTime().dayOfYear().withMinimumValue().withTimeAtStartOfDay();
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        return dtf.print(dateTime);
    }

    public static String getCurrentJodaMonthCurrentDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        return dtf.print(localDateTime);
    }

    public static int getCurrentJodaYear() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.getYear();
    }

    public static String getPreviousDateYears(int years) {
        LocalDate localDate = new DateTime().minusYears(years).toLocalDate();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd-MM-yyyy");
        return localDate.toString(fmt);
    }

    /**
     * @param date  string date e.g."28-05-1991"
     * @param years no.of years to add in date.
     * @return
     */
    public static String getNextDateYears(String date, int years) {
        DateTime dateTime = DateTimeFormat.forPattern("dd-MM-yyyy").parseDateTime(date);
        LocalDate localDate = dateTime.plusYears(years).toLocalDate();
        return localDate.toString();
    }


    public static String getOneMonthPreviousDate() {
        LocalDate localDate = new DateTime().minusMonths(1).toLocalDate();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd MMM");
        return localDate.toString(fmt);
    }


    public static String getOneMonthPrevious25Date() {
        DateTime dateTime25th = new DateTime().minusMonths(1).withDayOfMonth(25).withTimeAtStartOfDay();
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        return dtf.print(dateTime25th);
    }

    public static String getCurrentMonth24Date() {
        DateTime dateTime24th = endOfHour(endOfDay(new DateTime().withDayOfMonth(24)));
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        return dtf.print(dateTime24th);
    }


    public static String getCurrentJodaDate() {
        LocalDate localDate = new LocalDate();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd MMM");
        String str = localDate.toString(fmt);
        return str;
    }

    public static String getCurrentJodaMonthString() {
        LocalDate localDate = new LocalDate();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM yyyy");
        String str = localDate.toString(fmt);
        return str;
    }

    public static String getOneMonthPreviousDateForBeeWise() {
        LocalDate localDate = new DateTime().minusMonths(1).toLocalDate();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        return localDate.toString(fmt);
    }

    public static String getTwoMonthPreviousDateForBeeWise() {
        LocalDate localDate = new DateTime().minusMonths(2).toLocalDate();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        return localDate.toString(fmt);
    }

    public static String getThreeMonthPreviousDateForBeeWise() {
        LocalDate localDate = new DateTime().minusMonths(3).toLocalDate();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        return localDate.toString(fmt);
    }

    public static String getDaysPreviousDateForBeeWise(int days) {
        LocalDate localDate = new DateTime().minusDays(days).toLocalDate();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        return localDate.toString(fmt);
    }

    public static String getMonthsPreviousDateForBeeWise(int months) {
        LocalDate localDate = new DateTime().minusMonths(months).toLocalDate();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        return localDate.toString(fmt);
    }

    public static long getTimeBefore(int days) {
        return new DateTime().minusDays(days).getMillis();
    }

    public static String getCurrentJodaDateForBeeWise() {
        LocalDate localDate = new LocalDate();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        String str = localDate.toString(fmt);
        return str;
    }

    public static String getCurrentJodaDateWithoutYear() {
        LocalDate localDate = new LocalDate();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("MM-dd");
        String str = localDate.toString(fmt);
        return str;
    }

    public static String getCurrentDate() {
        DateTime dateTime25th = new DateTime(System.currentTimeMillis() + 60000);
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
        return dtf.print(dateTime25th);
    }

    public static Date getCurrentDateTimeInDateFormat() {
        return new Date(System.currentTimeMillis());
    }

    public static long getTime(String date) {
        DateTime dateTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(date);
        return dateTime.getMillis();
    }



public static String getConvertedDateWithTime(String strDate) {

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.ROOT);

        String currentMonth = "";
        Date date;
        try {
        date = originalFormat.parse(strDate);
        currentMonth = targetFormat.format(date);
        } catch (ParseException ex) {

        }
        return currentMonth;
        }

public static String getConvertedDateForPortfolio(String strDate) {

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ROOT);

        String currentMonth = "";
        Date date;
        try {
        date = originalFormat.parse(strDate);
        currentMonth = targetFormat.format(date);
        } catch (ParseException ex) {

        }
        return currentMonth;
        }

public static int getMonthNumberFromMonthname(String monthName) {
        Date date = null;
        int monthNumber = -1;
        try {
        date = new SimpleDateFormat("MMM", Locale.ROOT).parse(monthName);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        monthNumber = cal.get(Calendar.MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return monthNumber + 1;
    }

    public static String getDateInYYMMDDFromDDMMYYYY(String strDate) {

        String oldFormat = "dd-MM-yyyy";
        String newFormat = "yyyy-MM-dd";

        String currenrtDay = "";
        Date date;
        String formatedDate = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat, Locale.ROOT);
        Date myDate = null;
        try {
            myDate = dateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat(newFormat, Locale.ROOT);
        formatedDate = timeFormat.format(myDate);
        return formatedDate;
    }

    public static String getMonthYearFromDate(String strDate) {

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
        SimpleDateFormat targetFormat = new SimpleDateFormat("MMMM yyyy", Locale.ROOT);

        String currenrtDay = "";
        Date date;
        try {
            date = originalFormat.parse(strDate);
            currenrtDay = targetFormat.format(date);
        } catch (ParseException ex) {

        }
        return currenrtDay;
    }



    public static String getPastWeekDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MMM/yyyy", Locale.ROOT);
        calendar.add(Calendar.DATE, -7);
        String currentTime = formatter1.format(calendar.getTime());
        return currentTime;
    }

    public static String showDate(boolean isTomorrow, String format) {
        String date;
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        DateFormat dateFormat = new SimpleDateFormat(format, Locale.ROOT);

        if (isTomorrow) {
            date = dateFormat.format(tomorrow);
        } else {
            date = dateFormat.format(today);
        }

        return date;
    }

    public static boolean compareTimeForQsip() {
        String currentTime = "";

        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormat.forPattern("HH:mm");
            currentTime = dtf.print(localDateTime);
            String datadb = "14:00";

            if (currentTime.compareTo(datadb) >= 0) {
                return true;
            }

        } catch (Exception e) {
            if (e != null)
                e.printStackTrace();
        }

        return false;
    }

    public static void getFutureDate(Date currentDate, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, days);

        Date futureDate = cal.getTime();
    }

    public static boolean compareDates(String d1, String d2) {
        boolean tag = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);
            Date date1 = sdf.parse(d1);
            Date date2 = sdf.parse(d2);

            if (date1.equals(date2) || date1.after(date2)) {
                tag = true;
            }

        } catch (ParseException ex) {
            ex.printStackTrace();

        }
        return tag;
    }

    public static long getCurrentMonthsFirstDateInMilliSeconds() {
        Calendar c = Calendar.getInstance();   // this takes current date
        c.set(Calendar.DAY_OF_MONTH, 1);
        long time = c.getTimeInMillis();
        return time;
    }



    public static String getNextDateForGoal() {
        LocalDate localDate = new DateTime().plusDays(1).toLocalDate();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        return localDate.toString(fmt);
    }

    public static String getDateInYYYY_mm_ddFrom_mmm_yyyy(String inputDate) {
        if (!TextUtils.isEmpty(inputDate)) {
            String reqDate = "";
            try {
                DateFormat inputFormat = new SimpleDateFormat("MMM yyyy", Locale.ROOT);
                DateFormat outputFormat = new SimpleDateFormat("yyyy-MM", Locale.ROOT);
                //   String inputDateStr="2013-06-24";
                Date date = null;
                date = inputFormat.parse(inputDate);

                reqDate = outputFormat.format(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return reqDate + "-28";
        } else {
            return "";
        }

    }

    public static DateTime endOfYear(DateTime dateTime) {
        return endOfDay(dateTime).withMonthOfYear(12).withDayOfMonth(31);
    }

    public static DateTime beginningOfYear(DateTime dateTime) {
        return beginningOfMonth(dateTime).withMonthOfYear(1);
    }

    public static DateTime endOfMonth(DateTime dateTime) {
        return endOfDay(dateTime).withDayOfMonth(dateTime.dayOfMonth().getMaximumValue());
    }

    public static DateTime beginningOfMonth(DateTime dateTime) {
        return beginningOfday(dateTime).withDayOfMonth(1);
    }

    public static DateTime endOfDay(DateTime dateTime) {
        return endOfHour(dateTime).withHourOfDay(23);
    }

    public static DateTime beginningOfday(DateTime dateTime) {
        return beginningOfHour(dateTime).withHourOfDay(0);
    }

    public static DateTime beginningOfHour(DateTime dateTime) {
        return dateTime.withMillisOfSecond(0).withSecondOfMinute(0).withMinuteOfHour(0);
    }

    public static DateTime endOfHour(DateTime dateTime) {
        return dateTime.withMillisOfSecond(999).withSecondOfMinute(59).withMinuteOfHour(59);
    }

    public static String getCurrentDateYYYYMMDD() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);
        String currentTime = formatter1.format(calendar.getTime());
        return currentTime;
    }

    public static String getMonthNumberFromMMM_YYYY(String inputDate) {
        DateFormat inputFormat = new SimpleDateFormat("MMM yyyy", Locale.ROOT);
        SimpleDateFormat targetFormat = new SimpleDateFormat("MM", Locale.ROOT);

        String currentMonth = "";
        Date date;
        try {
            date = inputFormat.parse(inputDate);
            currentMonth = targetFormat.format(date);
        } catch (ParseException ex) {
            ex.getMessage();
        }
        return currentMonth;
    }

    public static String getYearFromMMM_YYYY(String inputDate) {
        DateFormat inputFormat = new SimpleDateFormat("MMM yyyy", Locale.ROOT);
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy", Locale.ROOT);

        String currentMonth = "";
        Date date;
        try {
            date = inputFormat.parse(inputDate);
            currentMonth = targetFormat.format(date);
        } catch (ParseException ex) {

        }
        return currentMonth;
    }

    public static int getCurrentJodaMonth() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.getMonthOfYear();
    }

    public static String getDateMMMYYYYFormatFromYYYYMMDD(String dateInYYYYMMDD) {
        if (!TextUtils.isEmpty(dateInYYYYMMDD)) {
            try {
                DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
                DateTime jodatime = dtf.parseDateTime(dateInYYYYMMDD);
                DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MMM yyyy");

                return dtfOut.print(jodatime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }


    public static String getMMMYYYYfromYYYYMMDD(String date) {

        String convertedDate = "";

        try {
            DateTime dt = DateTime.parse(date, DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.ENGLISH));
            convertedDate = dt.toString("MMM yyyy");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return convertedDate;
    }



    public static int differenceBetween2DatesinYears(String dobdate, String targetDate) {
        int yearDiffinYears = 0;
        if (!TextUtils.isEmpty(dobdate) && !TextUtils.isEmpty(targetDate)) {
            DateTime dateTimeTargetDate = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime(targetDate);
            DateTime dateTimeDob = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime(dobdate);

            long longTargetDateInMillis = dateTimeTargetDate.getMillis();
            long longDobInMillis = dateTimeDob.getMillis();
            Period period = new Period(longDobInMillis, longTargetDateInMillis);

            yearDiffinYears = period.getYears();
        }
        return yearDiffinYears;
    }


    public static String convertDDMMYYYY_to_YYYYMMDD(String inputDate) {
        String convertedDate = "";
        if (!TextUtils.isEmpty(inputDate)) {
            try {
                DateTime dt = DateTime.parse(inputDate, DateTimeFormat.forPattern("dd-MM-yyyy").withLocale(Locale.ENGLISH));
                convertedDate = dt.toString("yyyy-MM-dd");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return convertedDate;
        }
        return convertedDate;
    }

    public static String convertYYYYMMDD_to_DDMMYYYY(String inputDate) {
        String convertedDate = "";
        if (!TextUtils.isEmpty(inputDate)) {
            try {
                DateTime dt = DateTime.parse(inputDate, DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.ENGLISH));
                convertedDate = dt.toString("dd-MM-yyyy");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return convertedDate;
        }
        return convertedDate;
    }

    public static String getPreviousStartDateForBeewise(int months) {
        DateTime dateTime = new DateTime().minusMonths(months);

        DateTime start = DateUtils.beginningOfMonth(dateTime);

        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        return dtf.print(start);
    }

    public static String getOneMonthPreviousStartDate() {
        DateTime dateTime = new DateTime().minusMonths(1);

        DateTime start = DateUtils.beginningOfMonth(dateTime);

        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        return dtf.print(start);
    }

    public static String getOneMonthPreviousEndDate() {
        DateTime dateTime = new DateTime().minusMonths(1);

        DateTime end = DateUtils.endOfMonth(dateTime);

        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        return dtf.print(end);
    }

    public static String getDateTimeForMFOrderDetail(String foramttedDate) {
        if (!TextUtils.isEmpty(foramttedDate)) {
            String convertedDate = "";
            try {
                DateTime dt = DateTime.parse(foramttedDate, DateTimeFormat.forPattern("ddMMMyyyy HH:mm").withLocale(Locale.ENGLISH));
                convertedDate = dt.toString("ddMMMyyyy HH:mm a");
            } catch (Exception ex) {
                ex.printStackTrace();

            }

            return convertedDate;
        }

        return "";
    }

    public static String getCurrentDateInddMMyyyy() {
        String currentMonthYear = "";

        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
            currentMonthYear = dtf.print(localDateTime);
        } catch (Exception e) {
            if (e != null)
                e.printStackTrace();
        }

        return currentMonthYear;
    }


    public static String getCurrentDateInyyyyMMdd() {
        String currentMonthYear = "";

        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
            currentMonthYear = dtf.print(localDateTime);
        } catch (Exception e) {
            if (e != null)
                e.printStackTrace();
        }

        return currentMonthYear;
    }
    public static int getMonthBefore(int noOfMonths) {
        return new DateTime().minusMonths(noOfMonths).getMonthOfYear();
    }

    public static int getYearBefore(int noOfMonths) {
        return new DateTime().minusMonths(noOfMonths).getYear();
    }

    public static String getDateForHealthInsurance(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("d/M/yyyy");
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.YEAR, year);
        String dateformatted = date.format(cal.getTime());
        return dateformatted;
    }




    public static String convertDateInYYYY_mm_ddToddMMMYYYY(String inputDate) {
        String convertedDate = "";
        if (!TextUtils.isEmpty(inputDate)) {
            try {
                DateTime dt = DateTime.parse(inputDate, DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.ENGLISH));
                convertedDate = dt.toString("dd-MMM-yyyy");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return convertedDate;
        }
        return convertedDate;
    }

    public static String getNextMonthDateWithGivenDay(int day) {
        DateTime dateTime25th = new DateTime().plusMonths(1).withDayOfMonth(day).withTimeAtStartOfDay();
        DateTimeFormatter dtf = DateTimeFormat.forPattern("ddMMMyyyy");
        return dtf.print(dateTime25th);
    }

    public static String getCurrentMonthDateWithGivenDay(int day) {
        DateTime dateTime25th = new DateTime().withDayOfMonth(day).withTimeAtStartOfDay();
        DateTimeFormatter dtf = DateTimeFormat.forPattern("ddMMMyyyy");
        return dtf.print(dateTime25th);
    }
}
