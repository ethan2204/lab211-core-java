package tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import model.Customer;
import model.Order;

public class Inputter {

    private Scanner ndl;

    public Inputter() {
        this.ndl = new Scanner(System.in);
    }

    public String getString(String mess) {
        System.out.print(mess);
        return this.ndl.nextLine();
    }

    public int getInt(String mess) {
        int kq = 0;
        String tam = getString(mess);
        if (Acceptable.isValid(tam, Acceptable.INTEGER_VALID)) {
            kq = Integer.parseInt(tam);
        }
        return kq;
    }

    public double getDouble(String mess) {
        String tam = getString(mess);
        return Double.parseDouble(tam);
    }

    public String inputAndLoop(String mess, String pattern, boolean isLoop) {
        String result = "";
        boolean more = true;
        do {
            result = getString(mess);
            more = !Acceptable.isValid(result, pattern);
            if (more && (isLoop && result.length() > 0)) {
                System.out.println("Data is invalid!. Re-enter...");
            }
        } while (isLoop && more);
        return result.trim();
    }


    public Date getEventDate(String mess) {
        String dateSt = getString(mess);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            return sdf.parse(dateSt);
        } catch (Exception e) {
            System.out.println("Invalid format! Please use dd/MM/yyyy");
        }
        return getEventDate(mess);
    }

    public Order getOrderInfo(){
        Order o = new Order();
        
        o.setCustomerId(inputAndLoop("Enter Customer Id: ", Acceptable.CUS_ID_VALID, true));
        o.setProvince(inputAndLoop("Enter customer province: ", Acceptable.PROVINCE_VALID, true));
        o.setMenuId(inputAndLoop("Enter Menu Id: ", Acceptable.MENU_ID_VALID, true));
        o.setNumOfTables(getInt("Enter number of tables: "));
        o.setEventDate(getEventDate("Enter event date(dd/MM/yyyy): "));
        
        return o;
    }

    public Customer getCusInfo() {
        Customer c = new Customer();
        
        c.setId(inputAndLoop("Enter customer ID: ", Acceptable.CUS_ID_VALID, true));
        c.setName(inputAndLoop("Enter customer name: ", Acceptable.NAME_VALID, true));
        c.setPhone(inputAndLoop("Enter customer phone number: ", Acceptable.PHONE_VALID, true));
        c.setEmail(inputAndLoop("Enter customer email: ", Acceptable.EMAIL_VALID, true));
        
        return c;
    }

   

}
