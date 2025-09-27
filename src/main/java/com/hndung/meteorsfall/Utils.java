package com.hndung.meteorsfall;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.md_5.bungee.api.ChatColor;
public class Utils {
    public static final Pattern HEX_PATTERN = Pattern.compile("&#(\\w{5}[0-9a-fA-F])");
    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf('&') + "[0-9A-FK-OR]");

    public static String color(String textToTranslate) {
        if (textToTranslate == null) {
            return "NULL_TEXT";
        }
        Matcher matcher = HEX_PATTERN.matcher(textToTranslate);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, ChatColor.of((String)("#" + matcher.group(1))).toString());
        }
        return ChatColor.translateAlternateColorCodes((char)'&', (String)matcher.appendTail(buffer).toString());
    }

    public static String stripColor(String input) {
        return input == null ? null : STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }
}
