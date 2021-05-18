package com.example.museuminfo;

import org.w3c.dom.Text;

//museum类，用来实例化
public class Museum  {
    private int musemID;
    private String museumName;
    private String openingTime;
    private String address;
    private String telephone;
    private Text intro;


    public Museum(){}

    public Museum(int id,String mu,String open,String add,String tel,Text intr){
        musemID=id;
        museumName=mu;
        openingTime=open;
        address=add;
        telephone=tel;
        intro=intr;
    }
    public int getMuseumID(){return musemID;}
    public void setMuseumID(int id){this.musemID=id;}

    public String getMuseumName() {
        return museumName;
    }
    public void setMuseumName(String museumName) {
        this.museumName = museumName;
    }

    public String getOpeningTime(){
        return openingTime;
    }
    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getAddress(){
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone(){
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Text getIntro(){return intro;}
    public void setIntro(Text intro) {
        this.intro = intro;
    }

}

