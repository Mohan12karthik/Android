package com.example.admin.doc;

public class medicine_details {

    int id,img;
    String medicinename,manufacturer,medinfo,price;

    public medicine_details(int id, int img, String medicinename, String manufacturer, String medinfo, String price) {
        this.id = id;
        this.img = img;
        this.medicinename = medicinename;
        this.manufacturer = manufacturer;
        this.medinfo = medinfo;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getImg() {
        return img;
    }

    public String getMedicinename() {
        return medicinename;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getMedinfo() {
        return medinfo;
    }

    public String getPrice() {
        return price;
    }
}
