
package dispatcher;

import business.DevManager;
import business.ProjectManager;
import model.Dev;
import model.Project;
import tool.Inputter;

public class Main {

   
    public static void main(String[] args) {
        Inputter input = new Inputter();
        DevManager dm = new DevManager();
        dm.readFromFile();
        ProjectManager pm = new ProjectManager(dm);
        pm.readFromFile();
        int choice = 0;
        do{
            choice = input.getInt(
                    "|----------------------------------------------------------|\n"
                    + "1. List all Developers\n" 
                    + "2. Add a new Developer\n" 
                    + "3. Search for a Developer by ID\n"
                    + "4. Update a Developer's salary by ID\n" 
                    + "5. List all Developers by Language\n" 
                    + "6. Add a new Project\n" 
                    + "7. List all Projects by Developer (Grouped)\n"
                    + "8. Calculate Total Experience by Dev ID\n" 
                    + "9. Remove a Developer by ID\n"
                    + "10. Sort Developers by Salary\n" 
                    + "11. Save data to files\n" 
                    + "12. Quit program\n" 
                    + "|----------------------------------------------------------|\n"
            );
            switch(choice){
                case 1:
                    dm.showAll();
                    break;
                case 2:
                    Dev d = input.getDevInfo();
                    dm.addNew(d);
                    break;
                case 3:
                    String id = input.getString("Enter dev ID to search: ");
                    dm.searchDevById(id);
                    break;
                case 4:
                    String idUpdate = input.getString("Enter dev ID to update salary: ");
                    dm.updateSalary(input,idUpdate);
                    break;
                case 5:
                    String lang = input.getString("Enter languages of developer: ");
                    dm.listDevByLang(lang);
                    break;
                case 6:
                    Project p = input.getProInfo();
                    pm.addNew(p);
                    break;
                case 7:
                    pm.listProByDev();
                    break;
                case 8:
                    String devId = input.getString("Enter dev id to calculate: ");
                    pm.totalExpe(devId);
                    break;
                case 9:
                    String removeId = input.getString("Enter developer ID to remove: ");
                    dm.removeDevById(removeId,pm);
                    break;
                case 10:
                    dm.listByAscSalary();
                    break;
                case 11:
                    dm.saveToFile();
                    pm.saveToFile();
                    System.out.println("Save data successfully!");
                    break;
                case 12:
                    if(!dm.isSave() || !pm.isSave()){
                        System.out.println("Do youwant to save the changes before exiting? (Y/N)");
                        String chon = input.getString("");
                        if(chon.equals("Y")){
                            dm.saveToFile();
                            pm.saveToFile();
                            System.out.println("Data saved successfully");
                        }
                    }
                    System.out.println("Program exited");
                    return;
            }
        }while(choice >0 && choice < 13);
    }
}
