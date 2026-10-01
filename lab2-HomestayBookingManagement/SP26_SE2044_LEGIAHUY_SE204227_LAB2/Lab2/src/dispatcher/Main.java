package dispatcher;

import business.BookingManager;
import business.HomestayManager;
import business.TourManager;
import model.Booking;
import model.Homestay;
import model.Tour;
import tool.Inputter;

public class Main {

    public static void main(String[] args) {
        Inputter input = new Inputter();
        HomestayManager hm = new HomestayManager();
        hm.readFromFile();
        TourManager tm = new TourManager(hm);
        tm.readFromFile();
        BookingManager bm = new BookingManager(tm);
        bm.readFromFile();
        int choice = 0;
        do {
            choice = input.getInt(
                    "|----------------------------------------------------------|\n"
                    + "1. Add a new Tour.\n"
                    + "2. Update a Tour by ID.\n"
                    + "3. List Tours with departure dates earlier than today.\n"
                    + "4. Show total Booking amount for future Tours.\n"
                    + "5. Add a new Booking.\n"
                    + "6. Remove a Booking by bookingID.\n"
                    + "7. Update a Booking by bookingID.\n"
                    + "8. Search Booking by fullName or partial name.\n"
                    + "9. Statistics of total tourists who booked homestays.\n"
                    + "Other. Quit program.\n"
                    + "|----------------------------------------------------------|\n"
            );
            switch (choice) {
                case 1:
                    Tour t = input.getTourInfo();
                    tm.addTour(t);
                    break;
                case 2:     
                    String newTourId = input.getString("Enter tour id to update: ");
                    tm.updateTour(input, newTourId);
                    break;
                case 3:
                    tm.listEarlierTour();
                    break;
                case 4:
                    tm.listTourBookingAmount();
                    break;
                case 5:
                    Booking b = input.getBookingInfo();
                    bm.addBooking(b);
                    break;
                case 6:
                    String id = input.getString("Enter id to remove:");
                    bm.removeBooking(id);
                    break;
                case 7:
                    String newBookingId = input.getString("Enter booking id to update: ");
                    bm.updateBooking(input, newBookingId);
                    break;
                case 8:
                    String keyWord = input.getString("Enter full name or partial name: ");
                    bm.searchByName(keyWord);
                    break;
                case 9:
                    bm.statisticBooking(hm);
                    break;
                default:
                    tm.saveToFile();
                    bm.saveToFile();
                    System.out.println("Quit program");
            }
        } while (choice > 0 && choice < 10);
    }
}
