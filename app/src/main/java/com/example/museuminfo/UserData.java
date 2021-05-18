package com.example.museuminfo;

public class UserData {
    private String NickName;
    private String Name;
    private String ID;
    private String Email;
    private String Phone;

    public UserData(){}
    public UserData(String niname,String name,String id,String email,String phone){
        NickName=niname;
        Name=name;
        ID=id;
        Email=email;
        Phone=phone;
    }
    public String getusernickname(){
        return NickName;
    }
    public void setusernickname(String nickna){
        NickName=nickna;
    }

    public String getusername(){
        return Name;
    }
    public void setusername(String na){
        Name=na;
    }
    public String getuserid(){
        return ID;
    }
    public void setuserid(String id){
        ID=id;
    }

    public String getuserphone(){
        return Phone;
    }
    public void setuserphone(String phone){
        Phone=phone;
    }

    public String getuseremail(){
        return Email;
    }
    public void setuseremail(String email){
        Email=email;
    }
}
