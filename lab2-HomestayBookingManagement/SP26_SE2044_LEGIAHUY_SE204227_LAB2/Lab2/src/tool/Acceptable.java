
package tool;


public interface Acceptable {
    public final String TOUR_ID_VALID = "^[T]\\d{5}$";
    public final String HOME_ID_VALID = "^HS\\d{4}$";
    public final String NAME_VALID = "^.{2,25}$";
    public final String POSITIVE_INT_VALID = "^[1-9]\\d*$";
    public final String DATE_VALID = "^[\\d{2}/\\d{2}/\\d{4}$";
    public final String BOOKING_ID_VALID = "^[B]\\d{5}$";
    public final String PHONE_VALID = "^0\\d{9}$";
    public final String INTEGER_VALID = "^-?\\d+";
    public final String TIME_VALID = "^([1-9]\\d* day(s)? [1-9]\\d* night(s)?)$";
    
    public final String BOOLEAN_VALID = "^(?i)(true|false)$";
    
    public static boolean isValid(String data, String pattern){
        return data.matches(pattern);
    }
 
}
