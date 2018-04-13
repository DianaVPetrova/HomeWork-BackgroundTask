package diana.softuni.bg.andoriddesignpatternshomework;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    
    private static final String DATA_PATTERN = "dd-MMM-yyyy HH-mm";

    public  static  String getTimeOfData(Integer dateTime){

        String dateAsText = new SimpleDateFormat(DATA_PATTERN)
                .format(new Date(dateTime * 1000L));
        
        return dateAsText;
    }

    public static boolean isTodayOrTomorrow(Integer dateTime) {
        Date d = new Date(dateTime * 1000L);
        boolean isInRange = (DateUtils.isToday(d.getTime() - DateUtils.DAY_IN_MILLIS) || DateUtils.isToday(d.getTime())) ? true : false;
        return isInRange;
    }

}
