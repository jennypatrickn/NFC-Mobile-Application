package com.njara.bounty.helpers;

/**
 * Created by njara on 16/02/2018.
 */


public class StringExtension  {

    public static String upperCaseAllFirst(String value) {

        char[] array = value.toCharArray();

        array[0] = Character.toUpperCase(array[0]);

        for (int i = 1; i < array.length; i++) {
            if (Character.isWhitespace(array[i - 1])) {
                array[i] = Character.toUpperCase(array[i]);
            }
        }


        return new String(array);
    }

}
