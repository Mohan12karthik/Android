package ericjoseph.com.exaltproject;

public class doctor_details {

    private String image, id, name, spec, hospital, fee, city;


    public doctor_details(String id, String image, String name, String spec, String hospital, String fee, String city) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.spec = spec;
        this.hospital = hospital;
        this.fee = fee;
        this.city=city;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
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
