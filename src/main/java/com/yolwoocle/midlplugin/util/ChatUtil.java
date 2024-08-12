package com.yolwoocle.midlplugin.util;

import org.bukkit.Color;

import java.util.Arrays;

public class ChatUtil {
    public static String colorToString(Color color) {
        return Arrays.stream(Integer.toHexString(color.asRGB()).split("")).reduce("§x", (a, b) -> a + "§" + b);
    }

    public static Color parseColor(String string) {
        if (string == null || string.length() != 7 || string.charAt(0) != '#') {
            throw new IllegalArgumentException("Invalid color format");
        }

        return Color.fromRGB(Integer.parseInt(string.substring(1), 16));
    }
}
