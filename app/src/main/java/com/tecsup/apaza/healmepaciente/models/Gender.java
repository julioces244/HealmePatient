package com.tecsup.apaza.healmepaciente.models;

public class Gender {

    private Integer id;
    private String name;
    private String abbreviated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviated() {
        return abbreviated;
    }

    public void setAbbreviated(String abbreviated) {
        this.abbreviated = abbreviated;
    }

    @Override
    public String toString() {
        return "Gender{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", abbreviated='" + abbreviated + '\'' +
                '}';
    }
}
