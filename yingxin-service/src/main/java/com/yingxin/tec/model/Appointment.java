package com.yingxin.tec.model;

import java.math.BigInteger;
import java.util.Date;

public class Appointment {

    private BigInteger id;

    private String username;

    private String phone;

    private Date appointmentdate;

    private String city;

    private String district;

    private String address;

    private String description;

    private String ip;

    private Integer status;

    private Date updatedat;

    private Date createdat;

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setAppointmentdate(Date appointmentdate) {
        this.appointmentdate = appointmentdate;
    }

    public Date getAppointmentdate() {
        return appointmentdate;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrict() {
        return district;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setUpdatedat(Date updatedat) {
        this.updatedat = updatedat;
    }

    public Date getUpdatedat() {
        return updatedat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    public Date getCreatedat() {
        return createdat;
    }

}
