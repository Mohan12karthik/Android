package com.example.admin.doc;

public class doctor_details {

     private  int id,image;
     private String name,spec,hospital,fee,city;


    public doctor_details(int id, int image, String name, String spec, String hospital, String fee,String city) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.spec = spec;
        this.hospital = hospital;
        this.fee = fee;
        this.city=city;
    }

    public int getId() {
        return id;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getSpec() {
        return spec;
    }

    public String getHospital() {
        return hospital;
    }

    public String getFee() {
        return fee;
    }

    public String getCity() {
        return city;
    }
}
