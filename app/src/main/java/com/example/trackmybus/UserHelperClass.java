package com.example.trackmybus;

public class UserHelperClass {
    String name;
    String email;
    String mobno;
    String password;
    public UserHelperClass() {

    }
    public UserHelperClass(String name,String email,String mobno,String password)
    {
        this.name = name;
        this.email = email;
        this.mobno = mobno;
        this.password = password;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobno() {
        return mobno;
    }

    public void setMobno(String mobno) {
        this.mobno = mobno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
