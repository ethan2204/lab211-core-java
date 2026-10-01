package tools;

public interface Acceptable {

    public final String CUS_ID_VALID = "^[CGKcgk]\\d{4}$";
    public final String NAME_VALID = "^.{2,25}$";
    public final String PHONE_VALID = "^0\\d{9}$";
    public final String INTEGER_VALID = "^-?\\d+";
    public final String POSITVIE_INT_VALID = "^[1-9]\\d*";

    public final String DOUBLE_VALID = "^-?\\d+(\\.\\d+)?$";
    public final String POSITIVE_DOUBLE_VALID = "^\\d+(\\.\\d+)?$";
    public final String PROVINCE_VALID = "^.{2,25}$";
    public final String MENU_ID_VALID = "^[a-zA-Z]{2}\\d{3}$";
    public final String EMAIL_VALID = "^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$";

    public static boolean isValid(String data, String pattern) {
        return data.matches(pattern);
    }
}
