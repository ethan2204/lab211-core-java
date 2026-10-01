package business;

import com.sun.org.apache.bcel.internal.generic.ISHR;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import model.Homestay;
import model.Tour;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tool.Acceptable;
import tool.Inputter;

public class TourManager extends ArrayList<Tour> {
    private HomestayManager hm;
    private boolean saved;
    private String pathFile;

    public TourManager() {
        super();
        this.saved = true;
        this.pathFile = "src/data/Tours.txt";
        
    }  
    public TourManager(HomestayManager hm){
        this();
        this.hm = hm;
    }

    public void readFromFile() {

        FileInputStream fis = null;
        try {
            File f = new File(this.pathFile);
            if (!f.exists()) {
                System.out.println("File does not exist!");
                return;
            }
            fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String temp = "";
            while ((temp = br.readLine()) != null) {
                Tour t = textToObject(temp);
                if (t != null) {

                    this.add(t);
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TourManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TourManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public Tour textToObject(String temp) {
        Tour t = new Tour();
        String[] tam = temp.split(",");
        if (tam.length != 9) {

            return null;
        }
        try {

            t.setTourId(tam[0]);
            t.setName(tam[1]);
            t.setTime(tam[2]);
            t.setPrice(Integer.parseInt(tam[3]));
            t.setHomeId(tam[4]);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);

            t.setDepartureDate(sdf.parse(tam[5]));
            t.setEndDate(sdf.parse(tam[6]));
            t.setNumberOfTourist(Integer.parseInt(tam[7]));
            t.setBooking(Boolean.parseBoolean(tam[8]));

        } catch (Exception e) {
            System.out.println(e);
            t = null;
        }
        return t;
    }
//    save to file
    public void saveToFile(){
        File f = new File(this.pathFile);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try(BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(f),StandardCharsets.UTF_8))){
            for (Tour t : this) {
                String line = t.getTourId()+","+
                        t.getName()+","+
                        t.getTime()+","+
                        t.getPrice()+","+
                        t.getHomeId()+","+
                        sdf.format(t.getDepartureDate())+","+
                        sdf.format(t.getEndDate())+","+
                        t.getNumberOfTourist()+","+
                        String.format("%b",t.isBooking());
                bw.write(line);
                bw.newLine();
            }
            this.saved = true;
        } catch (IOException ex) {
            Logger.getLogger(TourManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    

    public void showAll() {
        if (this.isEmpty()) {
            System.out.println("Cannot read data in Tours.txt.Please check it!");
            return;
        }
        for (Tour m : this) {
            if (m != null) {
                System.out.println(m);
            }
        }
    }
    
//  add tour

 public boolean addTour(Tour t) {
        if(findTourById(t.getTourId()) != null){
            System.out.println("Tour id is already exist!");
            System.out.println("Failure message!");
            return false;
        }
        String[] arr = t.getTime().split("\\s+");
        int days = Integer.parseInt(arr[0]);
        int nights = Integer.parseInt(arr[2]);
        if(days != nights + 1){
            System.out.println("Time is invalid!");
            System.out.println("Failure message!");
            return false;
        }
        Homestay h = hm.findHomeById(t.getHomeId()) ;
        if( h == null){
            System.out.println("Home id does not exist!");
            System.out.println("Failure message!");
            return false;
        }
        if(!t.getDepartureDate().before(t.getEndDate()) || !t.getDepartureDate().after(new Date())){
            System.out.println("Departure date must be before end date");
            System.out.println("Failure message!");
            return false;
        }
        for (Tour oldTour : this) {
         if(oldTour.getHomeId().equals(t.getHomeId())){
         if(!(t.getDepartureDate().after(oldTour.getEndDate())||t.getEndDate().before(oldTour.getDepartureDate()))){
             System.out.println("Tour schedule is overlap");
             System.out.println("Failure massage!");
             return false;
         }
         }
     }
        if(t.getNumberOfTourist()<0 ){
            System.out.println("Number of tourist must be greater than 0");
            return false;
        }
        
        if(t.getNumberOfTourist()> h.getMaximumcapcity()){
            System.out.println("Number of tourist was greater than maximum capacity!");
            System.out.println("Failure message!");
            return false;
        }
        
        t.setBooking(false);

        this.add(t);
        this.saved = false;
        System.out.println("Add tour successfully!");
        return true;
    }
   
//    update
public void updateTour(Inputter input, String id){
    Tour oldTour = findTourById(id);
    if(oldTour == null){
        System.out.println("Tour does not exist!");
        return;
    }
    
    String newName = input.inputAndLoop("Enter new name(Enter to skip): ", Acceptable.NAME_VALID, false);
    if(!newName.isEmpty()){
        oldTour.setName(newName);
    }
    String newTime = input.inputAndLoop("Enter new time(Enter to skip): ", Acceptable.TIME_VALID, false);
        if(!newTime.isEmpty()){
            String[] arr = newTime.split("\\s+");
            int days = Integer.parseInt(arr[0]);
            int nights = Integer.parseInt(arr[2]);
            if(days != nights + 1){
                System.out.println("Invalid. Re-enter");
            } else{
                oldTour.setTime(newTime);
            }
        }
    String newHomeId = input.inputAndLoop("Enter new home id(Enter to skip): ", Acceptable.HOME_ID_VALID, false);
    if(!newHomeId.isEmpty()){
        Homestay h = hm.findHomeById(newHomeId);
        if(h == null){
        System.out.println("Home id does not exist!");
        } else {
          oldTour.setHomeId(newHomeId);
        }
    }
    String priceSt = input.getString("Enter new price(Enter to skip): ");
    if(!priceSt.isEmpty()){
        int newPrice = Integer.parseInt(priceSt);
        oldTour.setPrice(newPrice);
    }
    String dpDateSt = input.getString("Enter new departure date(Enter to skip): ");
    if(!dpDateSt.isEmpty()){
        try{
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        Date newDepDate = sdf.parse(dpDateSt);
        oldTour.setDepartureDate(newDepDate);
        }catch(Exception e){
            System.out.println("Invalid format");
        }
    }
    String endDateSt = input.getString("Enter new end date(Enter to skip): ");
    if(!endDateSt.isEmpty()){
        try{
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        Date newEndDate = sdf.parse(endDateSt);
        
        oldTour.setEndDate(newEndDate);
        }catch(Exception e){
            System.out.println("Invalid format");
        }
    }
    String numSt = input.getString("Enter new number of tourist(Enter to skip): ");
    if(!numSt.isEmpty()){
        Homestay h = hm.findHomeById(oldTour.getHomeId());
        int newNumOfTrs = Integer.parseInt(numSt);
        if(newNumOfTrs > h.getMaximumcapcity() ){
            System.out.println("Number of tourist was greater than maximum capacity");
        }else{
            oldTour.setNumberOfTourist(newNumOfTrs);
        }
    }
    for (Tour t : this) {
        if(t == oldTour) continue;
        if(t.getHomeId().equals(oldTour.getHomeId())){
            if(!(t.getDepartureDate().after(oldTour.getEndDate())|| t.getEndDate().before(oldTour.getDepartureDate()))){
                System.out.println("Tour schedule is overlap");
                return;
            }
        }
    }
    
    saved = false;
    System.out.println("Update successfully");      
}
public void listEarlierTour(){
    Date currentDate = new Date();
    for (Tour t : this) {
        if(t.getDepartureDate().before(currentDate)){
            System.out.println(t);
        }
    }
}

public void listTourBookingAmount(){
        List<Tour> list = new ArrayList<>();
        Date today = new Date();
        for (Tour t : this) {
            if(t.getDepartureDate().after(today)){
                list.add(t);
            }
        }
        if(list.isEmpty()){
            System.out.println("No Tour future found");
            return;
        }
        list.sort((t1,t2)->{
            double total1 = t1.getPrice() * t1.getNumberOfTourist();
            double total2 = t2.getPrice() * t2.getNumberOfTourist();
            return Double.compare(total2, total1);
        });
        for (Tour t : list) {
            int total = t.getPrice() * t.getNumberOfTourist();
            System.out.printf("|%s|%s|%d|\n",t.getTourId(),t.getName(),total);
        }
    }

 
    public Tour findTourById(String id){
        for (Tour t : this) {
            if(t.getTourId().equals(id)){
                return t;
            }
        }
        return null;
    }
   

}
