
package tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Dev;
import model.Project;


public class Inputter {
    private Scanner input;

    public Inputter() {
        this.input = new Scanner(System.in);
    }
    
    public String getString(String mess){
        System.out.print(mess);
        return this.input.nextLine();
    }
    public int getInt(String mess){
        int result = 0;
        String temp  = getString(mess);
        if(Acceptable.isValid(temp, Acceptable.INTEGER_VALID)){
            result = Integer.parseInt(temp);
        }
        return result;
    }
    public String inputAndLoop(String mess, String pattern, boolean isLoop){
        String result = "";
        boolean more = true;
        do{
            result = getString(mess);
            more = !Acceptable.isValid(result, pattern);
            if(more && isLoop && (result.length() > 0)){
                System.out.println("Re-enter! Data invalid");
            }
        }while (more && isLoop);
        return result.trim();
    }
    public Date getDate(String mess){
            String dateSt = getString(mess);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            return sdf.parse(dateSt);
        } catch (ParseException ex) {
            System.out.println("Wrong format");
        }
        return getDate(mess);
    }
    public Dev getDevInfo(){
        Dev d = new Dev();
        
        d.setDevId(inputAndLoop("Enter dev id: ", Acceptable.DEV_VALID, true));
        d.setDevName(inputAndLoop("Enter dev name: ",Acceptable.NAME_VALID, true));
        d.setProLang(inputAndLoop("Enter dev's program languages: ", Acceptable.LANGUAGE_VALID, true));
        d.setSalary(getInt("Enter dev's salary: "));
        
        return d;
    }
    public Project getProInfo(){
        Project p = new Project();
        p.setProId(inputAndLoop("Enter project id: ", Acceptable.PRO_VALID, true));
        p.setDevId(inputAndLoop("Enter dev id: ", Acceptable.DEV_VALID, true));
        p.setProName(inputAndLoop("Enter project name: ", Acceptable.PRO_NAME_VALID, true));
        p.setDurMonths(getInt("Enter duration months: "));
        p.setStartDate(getDate("Enter start date(dd/MM/yyyy): "));
        
        return p;
    }
}
