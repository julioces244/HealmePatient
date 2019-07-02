package com.tecsup.apaza.healmepaciente.models;

import java.util.Date;
import java.util.List;

public class User {
    private Integer id;
    private Integer gender_id;
    private String document_type;
    private String identity_document;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private String phone;
    private Date birth_date;
    private String user_type;
    private Gender gender;
    private String base_url;

    private List<Images> images;

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGender_id() {
        return gender_id;
    }

    public void setGender_id(Integer gender_id) {
        this.gender_id = gender_id;
    }

    public String getDocument_type() {
        return document_type;
    }

    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }

    public String getIdentity_document() {
        return identity_document;
    }

    public void setIdentity_document(String identity_document) {
        this.identity_document = identity_document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getBase_url() {
        return base_url;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", gender_id=" + gender_id +
                ", document_type='" + document_type + '\'' +
                ", identity_document='" + identity_document + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", birth_date=" + birth_date +
                ", user_type='" + user_type + '\'' +
                ", gender=" + gender +
                ", base_url='" + base_url + '\'' +
                ", images=" + images +
                '}';
    }
}


