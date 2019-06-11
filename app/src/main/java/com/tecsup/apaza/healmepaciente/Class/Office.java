package com.tecsup.apaza.healmepaciente.Class;

public class Office {
    private Integer id;
    private Integer doctor_id;
    private String Adress;
    private String latitude;
    private String longitude;
    private String geo_coordinate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(Integer doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getGeo_coordinate() {
        return geo_coordinate;
    }

    public void setGeo_coordinate(String geo_coordinate) {
        this.geo_coordinate = geo_coordinate;
    }
}
