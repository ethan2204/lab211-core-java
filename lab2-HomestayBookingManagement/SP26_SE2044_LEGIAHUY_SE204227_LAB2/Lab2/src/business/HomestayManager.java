package business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Homestay;

public class HomestayManager extends ArrayList<Homestay> {

    private String pathFile;

    public HomestayManager() {
        super();
        this.pathFile = "src/data/Homestays.txt";
    }

    public void readFromFile() {
        FileInputStream fis = null;
        try {
            File f = new File(this.pathFile);
            if (!f.exists()) {
                System.out.println("File does not exist");
                return;
            }
            fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_16);
            BufferedReader br = new BufferedReader(isr);
            String temp = "";
            while ((temp = br.readLine()) != null) {
                Homestay h = textToObject(temp);
                if (h != null) {
                    this.add(h);
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HomestayManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HomestayManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(HomestayManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private Homestay textToObject(String temp) {
        Homestay h = new Homestay();
        String[] arr = temp.split("-");
        if(arr.length < 5){
            return null;
        }
        try{
        h.setHomeId(arr[0].trim());
        h.setHomeName(arr[1].trim());
        h.setRoomNumber(Integer.parseInt(arr[2]));
        h.setMaximumCapcity(Integer.parseInt(arr[arr.length - 1]));
        StringBuilder sb = new StringBuilder();
        for (int i = 3; i < (arr.length - 1); i++){
            if(i>3){
                sb.append("-");
            }
            sb.append(arr[i].trim());
        }
        h.setAddress(sb.toString());
        return h;
        }catch(Exception e){
            return null;
        }
    }
    public void showAll() {
        if (this.isEmpty()) {
            System.out.println("Cannot read data in Homestays.txt.Please check it!");
            return;
        }
        for (Homestay m : this) {
            if (m != null) {
                System.out.println(m);
            }
        }
}
//   findById
    public Homestay findHomeById(String id){
        for (Homestay h : this) {
            if(h.getHomeId().trim().equalsIgnoreCase(id.trim()))
                return h;
        }
        return null;
    }
    

}
