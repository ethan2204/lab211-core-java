
package business;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Dev;

import model.Project;

public class ProjectManager extends ArrayList<Project>{
    private DevManager dm ;
    private String pathFile;
    private boolean saved;

    public ProjectManager() {
        super();
        this.saved = true;
        this.pathFile = "src/data/projects.txt";
    }

    public ProjectManager(DevManager dm) {
        this();
        this.dm = dm;
    }
    public boolean isSave(){
        return saved;
    }
    public void readFromFile(){
            FileInputStream fis = null;
        try {
            File f = new File(this.pathFile);
            if(!f.exists()){
                System.out.println("File does not exist");
                return;
            }

            fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String temp = "";
            while((temp = br.readLine())!= null){
                Project p = textToObject(temp);
                if(p!= null){
                    this.add(p);
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProjectManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProjectManager.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(DevManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Project textToObject(String temp) {
        Project p = new Project();
        String[] arr = temp.split(",");
        if(arr.length != 5){
            return null;
        }
        try {
            p.setProId(arr[0].trim());
            p.setDevId(arr[1].trim());
            p.setProName(arr[2].trim());
            p.setDurMonths(Integer.parseInt(arr[3].trim()));
            if(p.getDurMonths() <1){
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            p.setStartDate(sdf.parse(arr[4]));
        } catch (Exception e) {
            System.out.println(e);
            p = null;
        }
        return p;
    }
    public void showAll(){
        if(this.isEmpty()){
            System.out.println("Not found");
            return;
        }
        for (Project p : this) {
            if(p!=null){
                System.out.println(p);
            }
        }
    }
    public boolean addNew(Project p){
        if(findProById(p.getProId())!= null){
            System.out.println("Project existed");
            return false;
        }
        Dev d = dm.finDevById(p.getDevId());
        if(d == null){
            System.out.println("Developer ID does not exist");
            return false;
        }
        if(p.getDurMonths()<1){
            System.out.println("Duration must be longer than 1 months");
            return false;
        }
        this.add(p);
        System.out.println("Add project successfully");
        this.saved = false;
        return true;
    }

    public Project findProById(String proId) {
        for (Project p : this) {
            if(p.getProId().equals(proId)){
               return p;
            }
        }
        return null;
    }

    public void listProByDev() {
        for (Dev d : dm) {
            for (Project p : this) {
                if(p.getDevId().equals(d.getDevId())){
                    System.out.println(d.getDevId());
                    System.out.println(p.getProId()+"-"+p.getDurMonths()+"-"+new SimpleDateFormat("dd/MM/yyyy").format(p.getStartDate()));
                }
            }
        }
    }

    public void totalExpe(String devId) {
        int total = 0;
        boolean found = false;
        for (Project p: this) {
            if(p.getDevId().equals(devId)){
                total += p.getDurMonths();
                found = true;
            }
        }
        if(!found){
            System.out.println("Dev does not found!");
        }else{
            System.out.printf("Total experience: %d months\n",total);
        }

    }

    public void saveToFile(){
        File f = new File(this.pathFile);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try(BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8))){
            for (Project p : this) {
                String line = p.getProId()+","+
                        p.getDevId()+","+
                        p.getProName()+","+
                        p.getDurMonths()+","+
                        sdf.format(p.getStartDate());
                bw.write(line);
                bw.newLine();
            }
            this.saved = true;
        } catch (IOException ex) {
            Logger.getLogger(ProjectManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    
    
}
