package dispatcher;

import business.Customers;
import business.Orders;
import business.SetMenus;
import java.util.Scanner;
import model.Customer;
import model.Order;
import tools.Inputter;

public class Main {

    public static void main(String[] args) {
        Inputter ndl = new Inputter();
        int choice = 0;
        Customers dsKh = new Customers();
        dsKh.readFromFile();
        SetMenus menus = new SetMenus();
        menus.readFromFile();
        Orders dsOrd = new Orders(menus, dsKh);
        dsOrd.readFromFile();

        do {
            choice = ndl.getInt(
                    "|----------------------------------------------------------|\n"
                    + "1. Register customers.\n"
                    + "2. Update customer infomation.\n"
                    + "3. Search for customer information by name.\n"
                    + "4. Display feast menus.\n"
                    + "5. Place a feast order.\n"
                    + "6. Update order information.\n"
                    + "7. Save data to file.\n"
                    + "8. Display Customer or Order lists.\n"
                    + "->Others quit.\n"
                    + "|----------------------------------------------------------|\n"
            );
            switch (choice) {
                case 1:
                    Customer c = ndl.getCusInfo();
                    dsKh.addNew(c);
                    break;
                case 2:
                    String cusUpdate = ndl.getString("Enter customer ID to update: ");
                    dsKh.updateCusByID(cusUpdate,ndl);
                    break;
                case 3:
                    String name = ndl.getString("Enter name to find: ");
                    dsKh.searchCusByName(name);
                    break;
                case 4:
                    menus.showAll();
                    break;
                case 5: 
                    Order o = ndl.getOrderInfo();
                    dsOrd.placeOrder(o);
                    break;
                case 6:
                    dsOrd.showAll();
                    String oId = ndl.getString("Enter Order Id: ");
                    dsOrd.updateOrd(oId,ndl);
                    break;
                case 7: 
                    //***
                    dsKh.saveToFile();
                    dsOrd.saveToFile();
                    System.out.println("Saved Successfully!");
                    break;
                
                case 8: 
                    int chon = 0;

                    do {
                        chon = ndl.getInt("Enter list you want to check:\n"
                                + "1.Customer List\n"
                                + "2.Order List\n"
                                + "3.Exit!\n");

                        switch (chon) {
                            case 1: {
                                dsKh.readFromFile();
                                if (dsKh.isEmpty()) {
                                    System.out.println("No data in system.");
                                } else {
                                    dsKh.showAll();
                                }
                                break;
                            }
                            case 2: {
                                dsOrd.readFromFile();
                                if (dsOrd.isEmpty()) {
                                    System.out.println("No data in system.");
                                }
                                dsOrd.showAll();
                                break;
                            }
                            default: {
                                System.out.println("");
                            }
                        }
                    } while (chon < 3 && chon > 0);
                
                default: 
                    System.out.println("");
                
            }
        } while (choice >= 1 && choice <= 8);
    }

    
}
