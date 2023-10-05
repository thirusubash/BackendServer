package com.gksvp.web.util;
public class MaskUtil {

    /**
     * Masks a string to show only a certain number of characters at the beginning and end.
     *
     * @param str             The string to be masked.
     * @param startUnmasked   The number of characters to remain unmasked at the beginning.
     * @param endUnmasked     The number of characters to remain unmasked at the end.
     * @param maskChar        The masking character.
     * @return                The masked string.
     */
    public static String maskString(String str, int startUnmasked, int endUnmasked, char maskChar) {
        if (str == null) {
            return null;
        }

        if (str.length() <= startUnmasked + endUnmasked) {
            return str;  // Return the string as is if it's too short to be masked
        }

        // Add starting unmasked part

        return str.substring(0, startUnmasked) +

                // Mask the middle part
                String.valueOf(maskChar).repeat(Math.max(0, str.length() - endUnmasked - startUnmasked)) +

                // Add ending unmasked part
                str.substring(str.length() - endUnmasked);
    }

//    public static void main(String[] args) {
//        String accountNumber = "123456789012";
//        System.out.println(maskString(accountNumber, 3, 4, '*'));  // Expected output: ********9012
//        System.out.println(maskString(accountNumber, 3, 2, '#'));  // Expected output: 12######9012
//    }
}
