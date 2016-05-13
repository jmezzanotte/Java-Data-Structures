

/**
 * Customer class
 * @author JohnMezzanotte
 * Contact: jmezzan1@my.smccd.edu
 * Customer.java
 * CIS 256 JAVA
 * Date: 5-12-2016
 * @Notes I chose to represent phone number as a string. I did this for 
 * a few reasons. Since we won't be doing any computations with the phone 
 * number, I could represent it as a string. I was also having problems 
 * with integers being to large for processing. Using strings allowed me to 
 * avoid this. I can also use string methods to help me validate input which 
 * was an advantage for me in our driver application.
 */
public class Customer {
    
    private String name; 
    private String address;
    private String phoneNumber;
    
    public Customer( String name, String address, String phoneNumber ){
        this.name = name; 
        this.address = address; 
        this.phoneNumber = phoneNumber;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName( String name ){
        this.name = name;
    }
    
    public String getAddress(){
        return address;
    }
    
    public void setAddress( String address ){
        this.address = address;
    }
    
    public String getPhoneNumber(){
        return phoneNumber;
    }
    
    public void setPhoneNumber( String phoneNumber ){
        this.phoneNumber = phoneNumber;
    }
    
    
    @Override
    public String toString(){
        return String.format("Name:%s\nAddress:%s\nPhone Number: %s\n", 
                name, address, phoneNumber );
    }
    
}
