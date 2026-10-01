
package model;

import java.io.Serializable;


public class Homestay implements Serializable{
    
    private String homeId;
    private String homeName;
    private int roomNumber;
    private String address;
    private int maximumcapcity;

    public Homestay() {
    }

    public Homestay(String homeId, String homeName, int roomNumber, String address, int maximumcapcity) {
        this.homeId = homeId;
        this.homeName = homeName;
        this.roomNumber = roomNumber;
        this.address = address;
        this.maximumcapcity = maximumcapcity;
    }

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMaximumcapcity() {
        return maximumcapcity;
    }

    public void setMaximumCapcity(int maximumcapcity) {
        this.maximumcapcity = maximumcapcity;
    }

    @Override
    public String toString() {
        return String.format("%s-%s-%d-%s-%d\n", homeId,homeName,roomNumber,address,maximumcapcity);
    }
    
    
}
