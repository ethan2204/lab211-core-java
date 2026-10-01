    
package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Booking implements Serializable{
   
    private String bookingId;
    private String fullName;
    private String tourId;
    private Date bookingDate;
    private String phone;

    public Booking() {
    }

    public Booking(String bookingId, String fullName, String tourId, Date bookingDate, String phone) {
        this.bookingId = bookingId;
        this.fullName = fullName;
        this.tourId = tourId;
        this.bookingDate = bookingDate;
        this.phone = phone;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formatBookingDate = sdf.format(bookingDate);
        return String.format("%s,%s,%s,%s,%s", bookingId,fullName,tourId,formatBookingDate,phone);
    }
    
    
}
