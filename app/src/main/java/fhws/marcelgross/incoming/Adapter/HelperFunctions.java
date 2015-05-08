package fhws.marcelgross.incoming.Adapter;

import java.util.Calendar;

/**
 * Created by Marcel on 08.05.2015.
 */
public class HelperFunctions {

    public static int[] dateStringToIntArray(String date){
        String[] stringArray = date.split("\\.");
        int[] dateArray = new int[stringArray.length];
        for (int i = 0; i < dateArray.length; i++){
            dateArray[i] = Integer.parseInt(stringArray[i]);
        }

        return dateArray;
    }
    public static int[] timeStringToIntArray(String time) {
        String[] stringArray = time.split(":");
        int[] timeArray = new int[stringArray.length];
        for (int i = 0; i < timeArray.length; i++) {
            timeArray[i] = Integer.parseInt(stringArray[i]);
        }
        return timeArray;
    }

    public static long[] getStartAndEndTime(String date, String time){
        int[] timeArray = timeStringToIntArray(time);
        int[] dateArray = dateStringToIntArray(date);
        long[] startAndEndTime = new long[2];

        for (int i = 0; i < startAndEndTime.length; i++){
            Calendar c = Calendar.getInstance();
            // year, month, day, hourOfDay, minute
            if (i == 1)
                timeArray[0] += 1;
            c.set(dateArray[2], dateArray[1], dateArray[0], timeArray[0], timeArray[1]);
            startAndEndTime[i] = c.getTimeInMillis();
        }

        return startAndEndTime;
    }
}
