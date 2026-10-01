
package business;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Dev;
import model.Project;
import tool.Acceptable;
import tool.Inputter;


public class DevManager extends ArrayList<Dev>{
    private String pathFile;
    private boolean saved;

    public DevManager() {
        super();
        this.saved = true;
        this.pathFile = "src/data/developers.txt";

    }
    public boolean isSave(){
        return saved;
    }
    
    public void showAll(){
        if(this.isEmpty()){
            System.out.println("No developers found!");
            return;
        }
        System.out.println("|------|-------------|-------------------|------|");
        System.out.println("|Id    |Name         |Languages          |Salary|");
        for (Dev d : this) {
            if(d != null){
                System.out.printf("|%s|%-13s|%-19s|%6d|\n",d.getDevId(),d.getDevName(),d.getProLang(),d.getSalary());
            }
        }
    }

    public boolean addNew(Dev d) {
        if(finDevById(d.getDevId()) != null){
            System.out.println("Developer existed!");
            return false;
        }
    this.add(d);
        System.out.println("Add successfully!");
    this.saved = false;
    return true;
    }

    public Dev finDevById(String id) {
        for (Dev d : this) {
            if(d.getDevId().equals(id)){
                return d;
            }
        }
            return null;
    }

    public void searchDevById(String id) {
        boolean found = false;
        for (Dev d : this) {
            if(d.getDevId().equals(id)){
                System.out.println(d);
                found = true;
            }
        }
        if(!found){
            System.out.println("Dev ID does not exist!");
        }
        
    }

    public void updateSalary(Inputter input,String idUpdate) {
        boolean found = false;
        for (Dev d : this) {
            if(d.getDevId().equals(idUpdate)){
                String newSalary = input.getString("Enter new salary:");
                if(!newSalary.isEmpty() && Integer.parseInt(newSalary) >= 1000){
                    d.setSalary(Integer.parseInt(newSalary));
                    System.out.println("Update successfully");
                }else{
                    System.out.println("Salary must be greater than 1000$");
                }
                found = true;
            }
        }
        if(!found){
            System.out.println("Dev ID does not exist");
        }
        this.saved = false;
    }

    public void listDevByLang(String lang) {
        if(this.isEmpty()){
            System.out.println("No developer found");
            return;
        }
        boolean found = false;
        for (Dev d : this) {
            if(d.getProLang().contains(lang)){
                System.out.println(d);
                found = true;
            }
        }
        if(!found){
            System.out.println("Nobody use this language!");
        }
    }

    public boolean removeDevById(String removeId, ProjectManager pm) {
        Dev d = finDevById(removeId);
        if(d == null){
            System.out.println("Developer ID does not exist!");
            return false;
        }
        for (Project p : pm) {
            if(removeId.equals(p.getDevId())){
                System.out.println("Cannot delete: Developer is assigned to projects.");
                return false;
            }
        }
        this.remove(d);
        System.out.println("Developer removed successfully!");
        this.saved = false;
        return true;
    }

    public void listByAscSalary() {
        Collections.sort(this,(d1,d2) -> d2.getSalary() - d1.getSalary());
        for (Dev d : this) {
            System.out.println(d);
        }
    }

     public void saveToFile(){
        File f = new File(this.pathFile);
        try(BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(f),StandardCharsets.UTF_8))){
            for (Dev d : this) {
                String line = d.getDevId()+","+
                        d.getDevName()+","+
                        d.getProLang()+","+
                        d.getSalary();
                bw.write(line);
                bw.newLine();
            }
            this.saved = true;
        
    }   catch (IOException ex) {
            Logger.getLogger(DevManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void readFromFile() {
        try {
            FileInputStream fis = null;
            File f = new File(pathFile);
            if(!f.exists()){
                System.out.println("File does not exist");
                return;
            }
            fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String temp ="";
            while((temp = br.readLine())!= null){
                Dev d = textToObject(temp);
                if(d!=null){
                    this.add(d);
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DevManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DevManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Dev textToObject(String temp) {
        Dev d = new Dev();
        String[] arr = temp.split(",");
        try{
            d.setDevId(arr[0].trim());
            d.setDevName(arr[1].trim());
            d.setSalary(Integer.parseInt(arr[arr.length - 1]));
            StringBuilder sb = new StringBuilder();
            for(int i = 2; i < arr.length - 1; i++){
                if(i > 2){
                    sb.append(",");
                }
                sb.append(arr[i].trim());
            }
            d.setProLang(sb.toString());
        }catch (Exception e){
            d = null;
        }
        return d;
    }
}
