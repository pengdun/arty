package com.kymjs.arty.utils;

import android.content.res.Resources;
import android.text.format.Time;

import com.kymjs.arty.R;
import com.kymjs.common.App;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * provide string based on current time.
 */
public class StringByTime {

    private enum TimeRange {
        MORNING(6, 10),
        BEFORENOON(10, 12),
        NOON(12, 14),
        AFTERNOON(14, 18),
        NIGHT(18, 21),
        EVENING(21, 24),
        MIDNIGHT(0, 6),
        DEFAULT(-1, -1);
        private int startHour, endHour;

        private static TimeRange[] timeRanges;

        static {
            timeRanges = new TimeRange[8];
            timeRanges[0] = MORNING;
            timeRanges[1] = BEFORENOON;
            timeRanges[2] = NOON;
            timeRanges[3] = AFTERNOON;
            timeRanges[4] = NIGHT;
            timeRanges[5] = EVENING;
            timeRanges[6] = MIDNIGHT;
            timeRanges[7] = DEFAULT;
        }

        TimeRange(int startHour, int endHour) {
            this.startHour = startHour;
            this.endHour = endHour;
        }

        public static TimeRange getTypeByIndex(int index) {
            return timeRanges[index];
        }

        public boolean contains(int currentHour) {
            return startHour <= currentHour && currentHour < endHour;
        }

        public static TimeRange getType(int currentHour) {
            if (MORNING.contains(currentHour)) {
                return MORNING;
            } else if (BEFORENOON.contains(currentHour)) {
                return BEFORENOON;
            } else if (NOON.contains(currentHour)) {
                return NOON;
            } else if (AFTERNOON.contains(currentHour)) {
                return AFTERNOON;
            } else if (NIGHT.contains(currentHour)) {
                return NIGHT;
            } else if (EVENING.contains(currentHour)) {
                return EVENING;
            } else if (MIDNIGHT.contains(currentHour)) {
                return MIDNIGHT;
            }
            return DEFAULT;
        }
    }

    private static Map<TimeRange, String> editContentHintDataSet;
    private static Map<TimeRange, String> editTitleHintDataSet;

    static {
        Resources resources = App.INSTANCE.getResources();

        editContentHintDataSet = new HashMap<>();
        String[] editContentHintArray = resources.getStringArray(R.array.edit_content_hint_array);

        editTitleHintDataSet = new HashMap<>();
        String[] editTitleHintArray = resources.getStringArray(R.array.edit_title_hint_array);

        for (int i = 0; i < 8; i++) {
            editContentHintDataSet.put(TimeRange.getTypeByIndex(i), editContentHintArray[i]);
            editTitleHintDataSet.put(TimeRange.getTypeByIndex(i), editTitleHintArray[i]);
        }
    }

    private static String getStringFromDataset(Map<TimeRange, String> dataSet) {
        Time time = new Time();
        time.set(new Date().getTime());
        return dataSet.get(TimeRange.getType(time.hour));
    }

    public static String getEditContentHintByNow() {
        return getStringFromDataset(editContentHintDataSet);
    }

    public static String getEditTitleHintByNow() {
        return getStringFromDataset(editTitleHintDataSet);
    }

}
