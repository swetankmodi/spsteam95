package com.google.sps.data;

public final class User{

  private final String name;
  private final String email;
  private final String phone;
  private final float rating;

  public User(String name, String email, String phone, float rating){
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.rating = rating;
  }

}

