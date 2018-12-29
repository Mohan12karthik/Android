package com.example.admin.doc;

public class pharmacy_details {

    private  int id,logo;
    private String Pharmname,address,phone;

    public pharmacy_details(int id, int logo, String pharmname, String address, String phone) {
        this.id = id;
        this.logo = logo;
        Pharmname = pharmname;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public int getLogo() {
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
}
