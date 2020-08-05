package com.google.sps.data;

public class Address{
    private String flatOrHouseNoOrBuildingOrCompanyOrApartment;
    private String areaOrColonyOrStreetOrSectorOrVillage;
    private String landmark;
    private String townOrCity;
    private String state;
    private int pinCode;

    public Address(String flatOrHouseNoOrBuildingOrCompanyOrApartment, String areaOrColonyOrStreetOrSectorOrVillage, 
    String landmark, String townOrCity, String state, int pinCode){
        this.flatOrHouseNoOrBuildingOrCompanyOrApartment = flatOrHouseNoOrBuildingOrCompanyOrApartment;
        this.areaOrColonyOrStreetOrSectorOrVillage = areaOrColonyOrStreetOrSectorOrVillage;
        this.landmark = landmark;
        this.townOrCity = townOrCity;
        this.state = state;
        this.pinCode = pinCode;
    }

}