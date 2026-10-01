
package model;

import java.io.Serializable;


public class Dev implements Serializable{
    private String devId;
    private String devName;
    private String proLang;
    private int salary;

    public Dev() {
    }

    public Dev(String devId, String devName, String proLang, int salary) {
        this.devId = devId;
        this.devName = devName;
        this.proLang = proLang;
        this.salary = salary;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getProLang() {
        return proLang;
    }

    public void setProLang(String proLang) {
        this.proLang = proLang;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%d", devId,devName,proLang,salary);
    }
    
    
    
}
