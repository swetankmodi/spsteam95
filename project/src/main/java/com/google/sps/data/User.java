package com.google.sps.data;
import java.util.ArrayList;
import java.util.List;

public class User{
    private final long id;
    private String name;
    private final String email;
    private long phone;
    private Address address;
    private double rating;
    private List<Long> createdTasks;
    private List<Long> completedTasks;

    public User(long id, String name, String email, long phone, Address address){
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.rating = 5.0;
        this.createdTasks = new ArrayList<>();
        this.completedTasks = new ArrayList<>();
    }

    long getId(){
        return this.id;
    }
    void setName(String name){
        this.name = name;
    }
    String getName(){
        return this.name;
    }
    String getEmail(){
        return this.email;
    }
    void setPhoneNo(long phone){
        this.phone = phone;
    }
    long getPhone(){
        return this.phone;
    }
    void setAddress(Address address){
        this.address = address;
    }
    Address getAddress(){
        return this.address;
    }
    void setRating(double rating){
        this.rating = rating;
    }
    double getRating(){
        return this.rating;
    }
    void addCreatedTask(long id){
        this.createdTasks.add(id);
    }
    List<Long> getCreatedTasks(){
        return this.createdTasks;
    }
    void addCompletedTask(long id){
        this.completedTasks.add(id);
    }
    List<Long> getCompletedTasks(){
        return this.completedTasks;
    }
}
