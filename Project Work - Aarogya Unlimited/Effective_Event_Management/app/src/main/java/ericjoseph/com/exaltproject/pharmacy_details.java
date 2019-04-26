package ericjoseph.com.exaltproject;

public class pharmacy_details {

    private String Pharmname,address,phone,logo,email,id;

    public pharmacy_details(String id, String logo, String pharmname, String address, String phone, String email) {
        this.id = id;
        this.logo = logo;
        this.Pharmname = pharmname;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getLogo() {
        return logo;
    }

    public String getPharmname() {
        return Pharmname;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
