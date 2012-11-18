package springWeb.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FullDateTimeFormator {
    public static final String DATE_TIME_TEMPLATE = "yyyy-MM-dd hh:mm:ss";

    static public String format(Date currentDate) {
        return new SimpleDateFormat(DATE_TIME_TEMPLATE).format(currentDate);
    }
}
