package com.inc.tim.dotoday.util;

import android.graphics.Color;

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
                Color.parseColor("#ffcdd2"),
                Color.parseColor("#e57373"),
                Color.parseColor("#f44336"),
                Color.parseColor("#d32f2f"),
                Color.parseColor("#b71c1c"),
        };

        public static final Integer[] MATERIAL_GOAL = {
                Color.parseColor("#C5CAE9"),
                Color.parseColor("#7986CB"),
                Color.parseColor("#3F51B5"),
                Color.parseColor("#303F9F"),
                Color.parseColor("#1A237E"),
        };

        public static final Integer[] MATERIAL_DELEGATE = {
                Color.parseColor("#FFECB3"),
                Color.parseColor("#FFD54F"),
                Color.parseColor("#FFC107"),
                Color.parseColor("#FFA000"),
                Color.parseColor("#FF6F00"),
        };

        public static final Integer[] MATERIAL_THROW = {
                Color.parseColor("#E1BEE7"),
                Color.parseColor("#BA68C8"),
                Color.parseColor("#9C27B0"),
                Color.parseColor("#7B1FA2"),
                Color.parseColor("#4A148C"),
        };

        public static int getImportanceColor(int importance, int category) {
            int index = 0;
            int resultColor = 0;
            if (importance % 2 == 0) {
                index = importance / 2 - 1;
            } else {
                index = importance / 2;
            }
            switch (category) {
                case 0:
                    resultColor = MATERIAL_FOCUS[index];
                    break;
                case 1:
                    resultColor = MATERIAL_GOAL[index];
                    break;
                case 2:
                    resultColor = MATERIAL_DELEGATE[index];
                    break;
                case 3:
                    resultColor = MATERIAL_THROW[index];
                    break;
            }
            return resultColor;
        }
    }

}
