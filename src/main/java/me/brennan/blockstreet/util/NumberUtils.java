package me.brennan.blockstreet.util;

/**
 * made for BlockStreet
 *
 * @author Brennan
 * @since 8/9/2020
 **/
public class NumberUtils {

    public static boolean isNumber (String string) {
        try { Integer.parseInt(string); } catch (NumberFormatException e) { return false; }
        return true;
    }

}
