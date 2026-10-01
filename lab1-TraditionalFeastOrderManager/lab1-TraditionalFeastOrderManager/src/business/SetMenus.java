package business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.SetMenu;

public class SetMenus extends ArrayList<SetMenu> {

    private String pathFile;

    public SetMenus() {
        super();
        this.pathFile = "src/data/FeastMenu.csv";
    }

public void readFromFile() {
        File f = new File(pathFile);
        try (
                 FileReader fr = new FileReader(f); 
                 BufferedReader br = new BufferedReader(fr)) {
            String temp = "";
            while ((temp = br.readLine()) != null) {
                SetMenu x = textToObject(temp);
                if( x != null){
                    this.add(x);
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SetMenus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SetMenus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private SetMenu textToObject(String temp) {
        SetMenu x = new SetMenu();
        String[] tam = temp.split(",");
        try {
            if (tam.length == 4) {

                x.setMenuId(tam[0]);
                x.setMenuName(tam[1]);
                x.setPrice(Double.parseDouble(tam[2]));
                x.setIngredients(tam[3]);
            }
        } catch (Exception e) {

            x = null;
        }
        return x;

    }
    
//hamf in ra menu function 4
    public void showAll() {
        if(this.isEmpty()){
            System.out.println("Nothing in menu");
            return;
        }
        for (SetMenu sm : this) {
            if(sm !=null){
                sm.showMenu();
            }
        }
    }
// hàm tìm MenuId
public SetMenu findMenuById(String id){
        for (SetMenu m : this) {
            if(m.getMenuId().equalsIgnoreCase(id)){
                return m;
            }
        }
        return null;
    }

    


    
}
