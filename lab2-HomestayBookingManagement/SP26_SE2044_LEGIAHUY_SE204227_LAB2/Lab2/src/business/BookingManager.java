
package business;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.runtime.FindProperty;
import model.Booking;
import model.Homestay;
import model.Tour;
import tool.Acceptable;
import tool.Inputter;


public class BookingManager extends ArrayList<Booking>{
    private boolean saved;
    private String pathFile;
    private TourManager tm;

    public BookingManager() {
        super();
        this.saved = true;
        this.pathFile = "src/data/Bookings.txt";
    }

    public BookingManager(TourManager tm) {
        this();
        this.tm = tm;
    }
    
    
  
    
    
//    update booking
    public void updateBooking(Inputter input,String id){
        Booking oldBooking = findBookingById(id);
        if(oldBooking == null){
            System.out.println("Booking does not exist");
            return;
        }
        System.out.println("Updating"+ id);
        //fullname
        String name = input.inputAndLoop("Enter new full name(Enter to skip): ", Acceptable.NAME_VALID, false);
        if(!name.isEmpty()){
            oldBooking.setFullName(name);
        }
        //Tour id
        String tourId = input.inputAndLoop("Enter new tour id(Enter to skip): ", Acceptable.TOUR_ID_VALID, false);
        if(!tourId.isEmpty()){
//                kiểm tra có tồn tại không
            if(tm.findTourById(tourId) == null){
                System.out.println("Tour does not exist");
            }else{
                Tour oldTour = tm.findTourById(oldBooking.getTourId());
                if (oldTour != null) {
                    oldTour.setBooking(false);
                }
                tm.findTourById(tourId).setBooking(true);
                oldBooking.setTourId(tourId);
            }
        }
//        booking date
        String dateSt = input.getString("Enter new booking date (dd/MM/yyyy): ");
        if(!dateSt.isEmpty()){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false);
                oldBooking.setBookingDate(sdf.parse(dateSt));
            } catch (ParseException ex) {
                System.out.println("Invalid date");
            }
        }
//        phone
        String phone = input.inputAndLoop("Enter new phone(Enter to skip): ", Acceptable.PHONE_VALID, false);
        if(!phone.isEmpty()){
            oldBooking.setPhone(phone);
        }
        this.saved = false;
        System.out.println("Update successfully");
    }
//    remove
    
//    list by fullname or patrial name
    
    public void searchByName(String key){
        if(key.isEmpty()){
            System.out.println("Name cannot be empty!");
            return;
        }
        boolean found = false;
        System.out.println("|------|--------------------|------|------------|------------|");
        System.out.println("|ID    |FULLNAME            |TOURID|BOOKING DATE|PHONE NUMBER|");
        for (Booking b : this) {
            if(b.getFullName().toLowerCase().contains(key.toLowerCase())){
                System.out.printf("|%6s|%-20s|%6s|%10s  |%10s  |\n",
                                    b.getBookingId(),b.getFullName(),b.getTourId(),
                                    new SimpleDateFormat("dd/MM/yyyy").format(b.getBookingDate())
                                    ,b.getPhone());
                found = true;
            }
        }
        if(!found){
            System.out.println(" No booking found");
        }
    }
//    text to object
    public Booking textToObject(String temp){
        Booking b = new Booking();
        String[] arr = temp.split(",");
        if(arr.length != 5){
            return null;
        }
        try {
            b.setBookingId(arr[0]);
            b.setFullName(arr[1]);
            b.setTourId(arr[2]);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            b.setBookingDate(sdf.parse(arr[3]));
            b.setPhone(arr[4]);
            
            
        }catch(Exception e){
            System.out.println(e);
            b = null;
        }
        
        return b;
    }
    
//    readfromfile;
    public void readFromFile(){
            FileInputStream fis = null;
        try {
            File f = new File(this.pathFile);
            if(!f.exists()){
                System.out.println("File does not exist");
                return;
            }
            fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String temp="";
                    while((temp = br.readLine()) != null){
                        Booking b = textToObject(temp);
                        if(b != null){
                            this.add(b);
                        }
                    }
                    br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BookingManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BookingManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    savetoFile
     public void saveToFile(){
    File f = new File(this.pathFile);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    try (BufferedWriter bw = new BufferedWriter(
            new OutputStreamWriter(
                    new FileOutputStream(f), StandardCharsets.UTF_8))) {

        for (Booking b : this) {
            String line = b.getBookingId() + "," +
                    b.getFullName()+ "," +
                    b.getTourId() + "," +
                    sdf.format(b.getBookingDate())+ "," +
                    b.getPhone();
            bw.write(line);
            bw.newLine();
        }

        this.saved = true;

    } catch (IOException ex) {
        ex.printStackTrace();
    }
}
    public void showAll() {
        if (this.isEmpty()) {
            System.out.println("Cannot read data in Bookings.txt.Please check it!");
            return;
        }
        for (Booking m : this) {
            if (m != null) {
                System.out.println(m);
            }
        }
    }


   public boolean  removeBooking(String id){
       Booking b = findBookingById(id);
       if(b == null){
           System.out.println("not founnd");
           return false;
       }
       Tour t = tm.findTourById(b.getTourId());
       if( t != null ){
           t.setBooking(false);
       }
       
       this.remove(b);
       this.saved = false;
       return true;
   }

    public void statisticBooking(HomestayManager hm) {
        if(hm.isEmpty()){
            System.out.println("No home availble");
            return;
        }
        for (Homestay h : hm) {
            int total = 0;
            for (Booking b : this) {
                    Tour t = tm.findTourById(b.getTourId());
                    if(t!=null && (t.getHomeId().equals(h.getHomeId()))){
                        total += t.getNumberOfTourist();
               }
            }
            System.out.printf("|%s|%d|\n",h.getHomeName(),total);
        }
    }

    public boolean addBooking(Booking b) {
            if(findBookingById(b.getBookingId()) != null){
                System.out.println("Booking existed");
                return false;
            }
            Tour t = tm.findTourById(b.getTourId());
            if(t == null){
                System.out.println("No tour found");
                return false;
            }
            if(b.getBookingDate().before(new Date())){
                System.out.println("Invalid");
            }
            if(b.getBookingDate().after(t.getDepartureDate())){
                System.out.println("booking date must before departure date");
                return false;
            }
            return true;
            
    }
    public Booking findBookingById(String id){
        for (Booking b : this) {
            if(b.getBookingId().equals(id)){
                return b;
            }
        }
        return null;
    }
}
