/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package createdatabasewithrandominfo;

/**
 *
 * @author blj0011
 */
public class DatabaseData {
    private Address address;
    private String companyName;
    private String boyName;
    private String girlName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;

    public DatabaseData() {
        this.address = new Address();
        this.companyName = "";
        this.boyName = "";
        this.girlName = "";
        this.lastName = "";
        this.phoneNumber = "";
        this.emailAddress = "";
    }

    
    public DatabaseData(Address address, String companyName, String boyName, String girlName, String lastName, String phoneNumber, String emailAddress) {
        this.address = address;
        this.companyName = companyName;
        this.boyName = boyName;
        this.girlName = girlName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }
    
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBoyName() {
        return boyName;
    }

    public void setBoyName(String boyName) {
        this.boyName = boyName;
    }

    public String getGirlName() {
        return girlName;
    }

    public void setGirlName(String girlName) {
        this.girlName = girlName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    
}
