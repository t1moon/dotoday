package com.inc.tim.dotoday.util;

import android.graphics.Color;

/**
 * Created by Timur on 02-Oct-17.
 */

public class CommonUtils {

    public static class ColorUtil {
        final static int COLOR_START = Color.parseColor("#4CAF50");    // material green
        final static int COLOR_END = Color.parseColor("#f44336");      // material red

        public static float interpolate(final float a, final float b,
                                  final float proportion) {
            return (a + ((b - a) * proportion));
        }

        public static int interpolateColor(final int a, final int b,
                                     final float proportion) {
            final float[] hsva = new float[3];
            final float[] hsvb = new float[3];
            Color.colorToHSV(a, hsva);
            Color.colorToHSV(b, hsvb);
            for (int i = 0; i < 3; i++) {
                hsvb[i] = interpolate(hsva[i], hsvb[i], proportion);
            }
            return Color.HSVToColor(hsvb);
        }

        public static int getImportanceColor(int importance) {
            return interpolateColor(COLOR_START, COLOR_END,
                    importance / 100f);
        }
    }
}
