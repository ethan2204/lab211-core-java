package business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customer;
import model.Order;
import sun.net.www.content.audio.x_aiff;
import tools.Acceptable;
import tools.Inputter;

public class Customers extends HashMap<String, Customer> {

    private boolean saved; // lưu đường dẫn để ánh xạ vào tập
    private String pathFile; // biến trạng thái đã lưu hay chưa lưu
    private final String TABLE_HEADER = "|-----|---------------|----------|-------------------------|\n"
            + "|ID   |FUll NAME      |PHONE     |EMAIL                    |\n"
            + "|-----|---------------|----------|-------------------------|";
    private final String TABLE_FOOTER = "|-----|---------------|----------|-------------------------|\n";
// cóntructor

    public Customers() {
        super();
        this.saved = true;
        this.pathFile = "src/data/customers.dat";
    }

    public boolean isSaved() {
        return saved;
    }

    //thêm khách hàng
   
    public void showAll(){
        showAll(this.values());
    }
    public void showAll(Collection<Customer> l){
        for (Customer c : l) {
            System.out.println(c);
        }
    }

    

    //fucntion 3 search by name
    

// đọc file
    
    public void readFromFile() {
        try {
            FileInputStream fis = null;
            File f = new File(pathFile);
            if (!f.exists()) {
                System.out.println("File does not exist");
                return;
            }
            fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            while (fis.available() > 0) {
                Customer c = (Customer) ois.readObject();
                this.addNew(c);
            }
            ois.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

//     lưu vào file

    public void saveToFile() {
        try {
            if(this.saved){
                return;
            }
            FileOutputStream fos = null;
            File f = new File(this.pathFile);
            fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Customer c : this.values()) {
                oos.writeObject(c);
            }
            oos.close();
            fos.close();
            this.saved = true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    

    public void addNew(Customer c) {
        this.putIfAbsent(c.getId(), c);
        this.saved = false;
    }

   
    public void searchCusByName(String name) {
        boolean found = false;
        for (Customer c: this.values()) {
            if(c.getName().toLowerCase().contains(name.toLowerCase())){
                System.out.println(c);
                found = true;
            }
        }
        if(!found){
            System.out.println("Does not found customer");
        }
    }

    public void updateCusByID(String id,Inputter ndl) {
        Customer c = findCusById(id);
        if(c == null){
            System.out.println("Customer id does not exists");
            return;
        }
        String name = ndl.inputAndLoop("Enter new name: ", Acceptable.NAME_VALID, false);
        if(!name.isEmpty()){
            c.setName(name);
        }
        String phone = ndl.inputAndLoop("Enter new phone: ", Acceptable.PHONE_VALID, false);
        if(!phone.isEmpty()){
            c.setPhone(phone);
        }
        String email = ndl.inputAndLoop("Enter new email: ", Acceptable.EMAIL_VALID, false);
        if(!email.isEmpty()){
            c.setEmail(email);
        }
        this.saved = false;
        System.out.println("Update successfully");
    }

    public Customer findCusById(String id) {
        for (Customer c : this.values()) {
            if(c.getId().equals(id)){
                return c;
            }
        }
        return null;
    }

    
   
   

}
