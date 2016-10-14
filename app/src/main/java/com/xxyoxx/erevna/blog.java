package com.xxyoxx.erevna;

/**
 * Created by Amiteshwar Sidhu on 03-Oct-16.
 */

public class blog {
    private String Name;
    private String Email;
    private String Image;
    private String Lat;
    private String Lon;


    public blog(){

    }

    public blog(String name, String email, String image,String lat, String lon) {
        Name = name;
        Email = email;
        Image = image;
        Lat = lat;
        Lon = lon;
    }

    public String getLat(){
        return Lat;
    }

    public  void setLat(String lat){
        Lat = lat;
    }

    public String getLon(){
        return  Lon;
    }

    public void setLon(String lon){
        Lon = lon;
    }


    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }


}
