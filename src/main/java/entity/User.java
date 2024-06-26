package entity;

import java.util.List;
import java.util.ArrayList;
public class User {
    private int id;
    private String name;
    private String phoneNumber;
    private String address;
    private String city;
    private String street;
    private String email;
    private String password;
    boolean hasServiceWindow;
    private List<String> service;
    private List<Reserve> reservations;
    public User() {
        this.reservations = new ArrayList<>();
    }

    public List<String> getService() {
        return service;
    }
    public void setService(List<String> service) {
        if(service.isEmpty()){
            this.service = service;
        }else{
            setHasServiceWindow(true);
        }
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setHasServiceWindow(boolean hasServiceWindow) {
        this.hasServiceWindow = hasServiceWindow;
    }

    public List<Reserve> getReservations() {
        return reservations;
    }

    public void addReservation(Reserve reservation) {
        this.reservations.add(reservation);
    }


    public boolean hasReservedService(int serviceId) {
        if (reservations != null) {
            for (Reserve reservation : reservations) {
                if (reservation.getServiceId() == serviceId) {
                    return true;
                }
            }
        }
        return false;
    }

}
