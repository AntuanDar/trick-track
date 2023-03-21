package ru.spb.taranenkoant.trick.track.client.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by taranenko on 21.03.2023
 * description:
 */
public class StringUtils {

    private static boolean isCreepyChar(char c) {
        if (c < 128) {
            return false;
        } else if (c >= 1040 && c <= 1103) {
            return false;
        } else {
            return c != 1025 && c != 1105;
        }
    }

    public static String lpad(String str, char pad, int length) {
        int strLength = str.length();

        for(int i = 0; i < length - strLength; ++i) {
            str = pad + str;
        }

        return str;
    }

    public static String rpad(String str, char pad, int length) {
        int strLength = str.length();

        for(int i = 0; i < length - strLength; ++i) {
            str = str + pad;
        }

        return str;
    }

    public static int getCharCount(String str, char ch) {
        int count = 0;

        for(int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == ch) {
                ++count;
            }
        }

        return count;
    }

    public static String toString(Object value) {
        return value != null ? value.toString() : "";
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return !isNullOrEmpty(str);
    }

    public static boolean areEqual(String s1, String s2) {
        return areEqual(s1, s2, true);
    }

    public static boolean areEqual(String s1, String s2, boolean caseSensitive) {
        if (isNullOrEmpty(s1)) {
            return isNullOrEmpty(s2);
        } else {
            return caseSensitive ? s1.equals(s2) : s1.equalsIgnoreCase(s2);
        }
    }

    public static String[] wrapText(String text, int lineSize) {
        List<String> lines = new ArrayList();
        List<Integer> lineBreaks = new ArrayList();

        int i;
        for(i = 0; i < text.length(); i += lineSize) {
            int linebreakIndex;
            for(linebreakIndex = i; linebreakIndex >= 0 && text.charAt(linebreakIndex) != ' '; --linebreakIndex) {
            }

            if (linebreakIndex > 0) {
                lineBreaks.add(linebreakIndex);
                i = linebreakIndex;
            }
        }

        StringBuilder line = new StringBuilder();

        for(i = 0; i < text.length(); ++i) {
            line.append(text.charAt(i));
            if (lineBreaks.contains(i)) {
                lines.add(line.toString().trim());
                line = new StringBuilder();
            }
        }

        if (line.length() > 0) {
            lines.add(line.toString().trim());
        }

        return (String[])lines.toArray(new String[lines.size()]);
    }

    public static boolean endsWith(CharSequence text, CharSequence suffix) {
        return endsWith(text, suffix, true);
    }

    public static boolean endsWith(CharSequence text, CharSequence suffix, boolean caseSensitive) {
        if (suffix.length() > text.length()) {
            return false;
        } else {
            int textIndex = text.length() - suffix.length();
            int suffixIndex = 0;

            char c1;
            char c2;
            do {
                if (textIndex >= text.length()) {
                    return true;
                }

                c1 = text.charAt(textIndex++);
                c2 = suffix.charAt(suffixIndex++);
                if (caseSensitive) {
                    c1 = Character.toUpperCase(c1);
                    c2 = Character.toUpperCase(c2);
                }
            } while(c1 == c2);

            return false;
        }
    }

    public static boolean startsWith(CharSequence text, CharSequence prefix) {
        return startsWith(text, prefix, true);
    }

    public static boolean startsWith(CharSequence text, CharSequence prefix, boolean caseSensitive) {
        if (prefix.length() > text.length()) {
            return false;
        } else {
            for(int i = 0; i < prefix.length(); ++i) {
                char c1 = text.charAt(i);
                char c2 = prefix.charAt(i);
                if (caseSensitive) {
                    c1 = Character.toUpperCase(c1);
                    c2 = Character.toUpperCase(c2);
                }

                if (c1 != c2) {
                    return false;
                }
            }

            return true;
        }
    }

    public static String[] split(CharSequence text, char delimiter) {
        List<String> result = new ArrayList();
        StringBuilder part = new StringBuilder();

        for(int i = 0; i < text.length(); ++i) {
            if (text.charAt(i) == delimiter) {
                result.add(part.toString());
                part = new StringBuilder();
            } else {
                part.append(text.charAt(i));
            }
        }

        if (part.length() > 0) {
            result.add(part.toString());
        }

        return (String[])result.toArray(new String[result.size()]);
    }

    public static boolean isInt(String value) {
        return value.matches("-?[0-9]+");
    }

    public static boolean isPositiveInt(String value) {
        return value.matches("[0-9]+");
    }

    public static boolean isDouble(String value) {
        return value.matches("-?[0-9]+\\.") || value.matches("-?\\.[0-9]+") || value.matches("-?[0-9]+\\.[0-9]+");
    }

    public static boolean isPositiveDouble(String value) {
        return value.matches("[0-9]+\\.") || value.matches("\\.[0-9]+") || value.matches("[0-9]+\\.[0-9]+");
    }

    public static String capitalize(String text) {
        StringBuilder sb = new StringBuilder(text);

        for(int i = 0; i < sb.length(); ++i) {
            if (i == 0 || sb.charAt(i - 1) == ' ' && sb.charAt(i) != ' ') {
                sb.setCharAt(i, Character.toUpperCase(sb.charAt(i)));
            }
        }

        return sb.toString();
    }

    public static boolean equals(CharSequence s1, CharSequence s2) {
        if (s1 == null && s2 == null) {
            return true;
        } else if (s1 == null ^ s2 == null) {
            return false;
        } else if (s1.length() != s2.length()) {
            return false;
        } else {
            for(int i = 0; i < s1.length(); ++i) {
                if (s1.charAt(i) != s2.charAt(i)) {
                    return false;
                }
            }

            return true;
        }
    }
}
