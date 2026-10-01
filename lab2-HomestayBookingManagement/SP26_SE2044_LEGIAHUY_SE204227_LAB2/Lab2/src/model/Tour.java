
package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Tour implements Serializable{
    private String tourId;
    private String tourName;
    private String time;
    private int price;
    private String homeId;
    private Date departureDate;
    private Date endDate;
    private int numberOfTourist;
    private boolean booking;

    public Tour() {
    }

    public Tour(String tourId, String tourName, String time, int price, String homeId, Date departureDate, Date endDate, int numberOfTourist, boolean booking) {
        this.tourId = tourId;
        this.tourName = tourName;
        this.time = time;
        this.price = price;
        this.homeId = homeId;
        this.departureDate = departureDate;
        this.endDate = endDate;
        this.numberOfTourist = numberOfTourist;
        this.booking = booking;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String id) {
        this.tourId = id;
    }

    public String getName() {
        return tourName;
    }

    public void setName(String name) {
        this.tourName = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getNumberOfTourist() {
        return numberOfTourist;
    }

    public void setNumberOfTourist(int numberOfTourist) {
        this.numberOfTourist = numberOfTourist;
    }

    public boolean isBooking() {
        return booking;
    }

    public void setBooking(boolean booking) {
        this.booking = booking;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formatEndDate = sdf.format(endDate);
        String formatDepartureDate = sdf.format(departureDate);
        String s = String.format("%b",booking).toUpperCase();
        return String.format("%s,%s,%s,%d,%s,%s,%s,%d,%s",tourId,tourName,time,price,homeId,formatDepartureDate,formatEndDate,numberOfTourist,s);
    }
    
    
    
    
    
}
