package tool;
public interface Acceptable {
    
    public final String INTEGER_VALID = "^-?\\d+$" ;
    public final String DEV_VALID = "^DEV\\d{3}$";
    public final String NAME_VALID = "^[A-Za-z]+(\\s[A-Za-z]+)+$";
    public final String LANGUAGE_VALID = "^[A-Za-z+#]+(,\\s*[A-Za-z+#]+)*$";
    public final String DATE_VALID = "^\\d{2}/\\d{2}/\\d{4}$";
    public final String PRO_VALID = "^PROJ\\d{2}$";
    public final String PRO_NAME_VALID = "^[A-Za-z]+([ -][A-Za-z]+)*$";
    public static boolean isValid(String data, String pattern){
        return data.matches(pattern);
    }
}
