package com.example.vesithacks;

public class UserData {
    String email,role;
    boolean superAdmin;
    public  UserData(){}
    public UserData(String email,String role,boolean superAdmin){
        this.email=email;
        this.role=role;
        this.superAdmin=superAdmin;
    }

    public String getEmail(){return email;}
    public String getRole(){return  role;}
    public boolean getSuperAdmin (){return superAdmin;}
}

