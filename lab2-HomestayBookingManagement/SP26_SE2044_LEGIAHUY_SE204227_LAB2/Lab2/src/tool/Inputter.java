
package tool;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import model.Booking;
import model.Tour;

public class Inputter {
    
    private Scanner input;

    public Inputter() {
        this.input = new Scanner(System.in);
    }
    
    public String getString(String mess){
        System.out.println(mess);
        return this.input.nextLine();
    }
    
    public int getInt(String mess){
        int result = 0;
        String temp = getString(mess);
        if(Acceptable.isValid(temp, Acceptable.INTEGER_VALID)){
            result = Integer.parseInt(temp);
        }
        return result;
    }
    public String inputAndLoop(String mess, String pattern, boolean isLoop){
        String result = "";
        boolean more = true;
        do {
            result = getString(mess);
            more = !Acceptable.isValid(result, pattern);
            if(more && (isLoop && result.length() > 0)){
                System.out.println("Data invalid. Re-enter!");
            }
        } while (more && isLoop);
        return result.trim();
    }
    
    public Date getDate(String mess){
        String dateSt = getString(mess);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            return sdf.parse(dateSt);
        } catch (Exception e) {
            System.out.println("Invalid format! Please use dd/MM/yyyy");
        }
        return getDate(mess);
    }

    public Tour getTourInfo(){
    Tour t = new Tour();
         t.setTourId(inputAndLoop("Enter tour id: ", Acceptable.TOUR_ID_VALID, true));
         t.setName(inputAndLoop("Enter tour name: ", Acceptable.NAME_VALID, true));
         t.setTime(inputAndLoop("Enter time: ", Acceptable.TIME_VALID, true));
         t.setPrice(getInt("Enter price: "));
         t.setHomeId(inputAndLoop("Enter home id: ", Acceptable.HOME_ID_VALID, true));
         t.setDepartureDate(getDate("Enter departure date(dd/MM/yyyy): "));
         t.setEndDate(getDate("Enter end date(dd/MM/yyyy): "));
         t.setNumberOfTourist(getInt("Enter number of tourist: "));
         return t;
    }
    public Booking getBookingInfo(){
       Booking b = new Booking();
       
       b.setBookingId(inputAndLoop("Enter booking Id: ", Acceptable.BOOKING_ID_VALID, true));
       b.setFullName(inputAndLoop("Enter full name: ", Acceptable.NAME_VALID, true));
       b.setTourId(inputAndLoop("Enter tour Id: ", Acceptable.TOUR_ID_VALID, true));
       b.setBookingDate(getDate("Enter booking date (dd/MM/yyyy): "));
       b.setPhone(inputAndLoop("Enter phone number: ", Acceptable.PHONE_VALID, true));
       return b;
    }
}
