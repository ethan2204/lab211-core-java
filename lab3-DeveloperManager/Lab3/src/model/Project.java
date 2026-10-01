
package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Project implements Serializable {
    private String proId;
    private String proName;
    private String devId;
    private int durMonths;
    private Date startDate;

    public Project() {
    }

    public Project(String proId, String proName,String devId ,int durMonths, Date startDate) {
        this.proId = proId;
        this.proName = proName;
        this.devId = devId;
        this.durMonths = durMonths;
        this.startDate = startDate;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }
    
    public int getDurMonths() {
        return durMonths;
    }

    public void setDurMonths(int durMonths) {
        this.durMonths = durMonths;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formatDate = sdf.format(startDate);
        return String.format("%s,%s,%s,%d,%s", this.proId,this.devId,this.proName,this.durMonths,formatDate);
    }
    
    
}
