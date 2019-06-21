package com.tecsup.apaza.healmepaciente.Class;

import java.util.Date;
import java.util.List;

public class Doctor {

    private Integer id;
    private Integer user_id;
    private Date work_experience;
    private String digital_signature;
    private Integer certificated;
    private Integer office_worker;
    private Integer isOnline;
    private Double valoration;
    private User user;
    private List<Speciality> specialities;
    private Office office;

    public List<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<Speciality> specialities) {
        this.specialities = specialities;
    }

    public Double getValoration() {
        return valoration;
    }

    public void setValoration(Double valoration) {
        this.valoration = valoration;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Date getWork_experience() {
        return work_experience;
    }

    public void setWork_experience(Date work_experience) {
        this.work_experience = work_experience;
    }

    public String getDigital_signature() {
        return digital_signature;
    }

    public void setDigital_signature(String digital_signature) {
        this.digital_signature = digital_signature;
    }

    public Integer getCertificated() {
        return certificated;
    }

    public void setCertificated(Integer certificated) {
        this.certificated = certificated;
    }

    public Integer getOffice_worker() {
        return office_worker;
    }

    public void setOffice_worker(Integer office_worker) {
        this.office_worker = office_worker;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }
}
