package com.inc.tim.dotoday.util;

import android.graphics.Color;

/**
 * Created by Timur on 02-Oct-17.
 */

public class CommonUtils {

    public static class ColorUtil {

        public static final Integer[] MATERIAL_COLORS = {
                Color.parseColor("#f44336"),    // Red
                Color.parseColor("#3F51B5"),    // Blue
                Color.parseColor("#FFC107"),    // Amber
                Color.parseColor("#9C27B0")     // Purple
        };

        public static final Integer[] MATERIAL_COLORS_LIGHT = {
                Color.parseColor("#ffcdd2"),    // Red
                Color.parseColor("#C5CAE9"),    // Blue
                Color.parseColor("#FFECB3"),    // Amber
                Color.parseColor("#D1C4E9")     // Purple
        };


        public static final Integer[] STATUSBAR_MATERIAL_COLORS = {
                Color.parseColor("#d32f2f"),
                Color.parseColor("#303F9F"),
                Color.parseColor("#FFA000"),
                Color.parseColor("#7B1FA2"),
        };

        public static final Integer[] MATERIAL_FOCUS = {
                Color.parseColor("#C5CAE9"),
                Color.parseColor("#7986CB"),
                Color.parseColor("#3F51B5"),
                Color.parseColor("#303F9F"),
                Color.parseColor("#1A237E"),
        };

        public static int getImportanceColor(int importance) {
            int result = 0;
            if (importance % 2 == 0) {
                result = MATERIAL_FOCUS[importance/2 - 1];
            } else {
                result = MATERIAL_FOCUS[importance / 2];
            }
            return result;
        }
    }

}
