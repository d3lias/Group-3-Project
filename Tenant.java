public class Tenant {
    private int tenantID;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    // constructor
    public Tenant(){}

    // constructor
    public Tenant(int tenantID, String firstName, String lastName, String phoneNumber, String email){
        this.tenantID = tenantID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // sets the tenantID
    public void setTenantID(int tenantID){
        this.tenantID = tenantID;
    }

    // returns the tenantID
    public String getTenantID(){
        String tenantIDString = String.valueOf(tenantID);
        return tenantIDString;
    }

    // sets the firstName
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    // returns the firstName
    public String getFirstName(){
        return firstName;
    }

    // sets the lastName
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    // returns the lastName
    public String getLastName(){
        return lastName;
    }

    // sets the phoneNumber
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    // returns the phoneNumber
    public String getPhoneNumber(){
        return phoneNumber;
    }

    // sets the email
    public void setEmail(String email){
        this.email = email;
    }

    // gets the email
    public String getEmail(){
        return email;
    }

    // returns the string representation of the tenants name
    @Override
    public String toString(){
        return getLastName() + ", " + getFirstName();
    }
}