package uk.gov.dvla.osl.commons.validation;

import java.util.regex.Pattern;

public class AddressValidator {

    public static boolean isValidLineFormat(String addressLineToValidate){
        String lengthRegex = "^(.{1,30})$";
        if (!Pattern.matches(lengthRegex, addressLineToValidate)) return false;

        String acceptableCharRegex = "^(([a-zA-Z0-9\\s,.\\-&'/])*(\\([^()]*[a-zA-Z0-9\\s,.\\-&'/]*\\))*)*$";
        return Pattern.matches(acceptableCharRegex, addressLineToValidate);
    }

    public static boolean isValidPostTownFormat(String addressTownToValidate) {
        String lengthRegex = "^(.{1,30})$";
        if (!Pattern.matches(lengthRegex, addressTownToValidate)) return false;

        String acceptableCharRegex = "^(([a-zA-Z\\s,.\\-&'/])*(\\([^()]*[a-zA-Z\\s,.\\-&'/]*\\))*)*$";
        return Pattern.matches(acceptableCharRegex, addressTownToValidate);
    }

    /**
     * check if  post code is in a valid postcode format
     * @param postcodeToValidate  postcode to validate its format
     * @return true if and only if the postcode format matches the regex, otherwise false is returned.
     */
    public static boolean isValidUKPostcodeFormat(String postcodeToValidate){
        boolean match;
        if(!isValidLength(postcodeToValidate)){
            match=false;
        }else {
            String inwardPostcode = postcodeToValidate.substring(postcodeToValidate.length() - 3, postcodeToValidate.length());
            if (isInwardPostcodeValidFormat(inwardPostcode)) {
                String outwardPostcode = postcodeToValidate.substring(0, postcodeToValidate.length() - 3);
                if (isValidSpaceSeparator(outwardPostcode)) {

                    switch (outwardPostcode.trim().length()) {
                        case 2:
                            String AN_Regex = "^([A-PR-UWYZa-pr-uwyz][0-9])$";
                            match = Pattern.matches(AN_Regex, outwardPostcode.trim());
                            break;
                        case 3:
                            //match ANN, AAN and ANA business rule
                            String third_char_length_regex = "^(([A-PR-UWYZa-pr-uwyz][A-HK-Ya-hk-y][0-9])|([A-PR-UWYZa-pr-uwyz][0-9][A-HJKPR-UWa-hjkpr-uw])|([A-PR-UWYZa-pr-uwyz][0-9]{2}))$";
                            match = Pattern.matches(third_char_length_regex, outwardPostcode.trim());
                            break;

                        case 4:
                            //match AANA and AANN business rule
                            String regex = "^([A-PR-UWYZa-pr-uwyz][A-HK-Ya-hk-y][0-9][A-Za-z0-9])$";
                            match = Pattern.matches(regex, outwardPostcode.trim());
                            break;
                        default:
                            match = false;
                    }
                } else {
                    match = false;
                }
            } else {
                match = false;
            }
        }
        return match;
    }
    private static boolean isInwardPostcodeValidFormat(String inwardPostcode){
        String inwardRegexPattern = "^([0-9][ABD-HJLNP-UW-Zabd-hjlnp-uw-z]{2})$";
        return Pattern.matches(inwardRegexPattern, inwardPostcode);

    }
    private static boolean isValidSpaceSeparator(String outwardPostcode){
        String spaceRegexPattern = "^([A-Za-z0-9][A-Za-z0-9][A-Za-z0-9]?[A-Za-z0-9]? {0,1})$";
        return Pattern.matches(spaceRegexPattern, outwardPostcode);
    }
    private static boolean isValidLength(String postcode){
        String lengthRegexPattern = "^(.{5,8})$";
        return Pattern.matches(lengthRegexPattern, postcode.trim().replaceAll(" ", ""));
    }
}
