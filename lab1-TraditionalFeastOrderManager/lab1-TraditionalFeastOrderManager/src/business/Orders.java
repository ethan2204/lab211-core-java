package business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customer;
import model.Order;
import model.SetMenu;
import tools.Acceptable;
import tools.Inputter;

public class Orders extends HashSet<Order> {        
    private SetMenus sm;
    private Customers dsKh;
    private String pathFile;
    private boolean saved;

    public Orders() {
        super();
        this.saved = true;
        this.pathFile = "src/data/feast_order_service.dat";
    }

    public Orders(SetMenus sm, Customers dsKh) {
        this();
        this.sm = sm;
        this.dsKh = dsKh;
    }
    

    public boolean isSaved() {
        return saved;
    }
    
    public boolean checkIsDupplicate(Order newOrd) {
        for (Order o : this) {
            if (o.getOrderCode().equalsIgnoreCase(newOrd.getOrderCode())) {
                return true;
            }
        }
        return false;
    }
  
    public Order searchById(String id) {
        for (Order o : this) {
            if(o.getOrderCode().equals(id)){
                return o;
            }
        }
        return null;
    }
    
    public void readFromFile() {
        try {
            FileInputStream fis = null;
            File f = new File(this.pathFile);
            if(!f.exists()){
                System.out.println("Customer.dat file not found!.");
                return;
            }
            fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            while(fis.available()>0){
                Order o = (Order) ois.readObject();
                if( o != null){
                    this.add(o);
                }
            }
            ois.close();
            this.saved = true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    public void saveToFile() {
        try {
            if(this.saved){
                return;
            }
            FileOutputStream fos = null;
            File f = new File(this.pathFile);
            fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Order o : this) {
                oos.writeObject(o);
            }
            oos.close();
            fos.close();
            this.saved = true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public boolean  placeOrder(Order o) {
        if(dsKh.findCusById(o.getCustomerId()) == null){
            System.out.println("Cusstomer ID does not exist");
            return false;
        }
        if(sm.findMenuById(o.getMenuId())== null){
            System.out.println("Menu does not exist");
            return false;
        }
        if(o.getNumOfTables() < 1){
            System.out.println("Table mus be > 0");
            return false;
        }
        if(o.getEventDate().before(new Date())){
            System.out.println("event date must be in the future");
            return false;
        }
        this.add(o);
        this.saved = false;
        System.out.println("Placed successfully");
        return true;
    }

    public void showAll(Collection<Order> l) {
        for (Order c : l) {
            System.out.println(c);
        }
    }
    public void showAll(){
        showAll(this);
    }

    public void updateOrd(String oId, Inputter ndl) {
        Order o = searchById(oId);
        if(o == null){
            System.out.println("Order does not exist");
            return;
        }
        this.remove(o);
        String menu = ndl.getString("Enter new menu: ");
        if(!menu.isEmpty()){
            if(sm.findMenuById(menu) == null){
                System.out.println("Menu does not exist");
            }else{
                o.setMenuId(menu);
            }
        }
        String table = ndl.getString("Enter new table: ");
        if(!table.isEmpty()){
            o.setNumOfTables(Integer.parseInt(table));
        }
        String date = ndl.getString("Enter new date: ");
        if(!date.isEmpty()){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false);
                Date newDate = sdf.parse(date);
                if(newDate.before(new Date())){
                    System.out.println("must be in the future");
                }else{
                    o.setEventDate(newDate);
                }
            } catch (ParseException ex) {
                Logger.getLogger(Orders.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.add(o);
        this.saved = false;
        System.out.println("update success");
    }
    
}



