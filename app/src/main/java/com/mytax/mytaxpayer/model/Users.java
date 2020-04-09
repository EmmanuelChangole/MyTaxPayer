package com.mytax.mytaxpayer.model;

public class Users
{
    private String userId, name,phoneNumber,nationalID,businessType,businessName,businessLocation,kraPin;
    public Users(){}

    public Users(String userId,String name, String phoneNumber, String nationalID, String businessType, String businessName, String businessLocation, String kraPin) {
        this.userId=userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.nationalID = nationalID;
        this.businessType = businessType;
        this.businessName = businessName;
        this.businessLocation = businessLocation;
        this.kraPin = kraPin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessLocation() {
        return businessLocation;
    }

    public void setBusinessLocation(String businessLocation) {
        this.businessLocation = businessLocation;
    }

    public String getKraPin() {
        return kraPin;
    }

    public void setKraPin(String kraPin) {
        this.kraPin = kraPin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", nationalID='" + nationalID + '\'' +
                ", businessType='" + businessType + '\'' +
                ", businessName='" + businessName + '\'' +
                ", businessLocation='" + businessLocation + '\'' +
                ", kraPin='" + kraPin + '\'' +
                '}';
    }
}
