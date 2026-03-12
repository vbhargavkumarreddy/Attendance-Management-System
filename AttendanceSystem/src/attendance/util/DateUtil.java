package attendance.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static String today() {
        return new SimpleDateFormat(DATE_FORMAT).format(new Date());
    }

    public static boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static String formatDisplay(String date) {
        // yyyy-MM-dd -> dd/MM/yyyy
        try {
            SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat out = new SimpleDateFormat("dd/MM/yyyy");
            return out.format(in.parse(date));
        } catch (ParseException e) {
            return date;
        }
    }
}
