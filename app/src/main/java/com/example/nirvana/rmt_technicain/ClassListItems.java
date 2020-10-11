package com.example.nirvana.rmt_technicain;

public class ClassListItems {
    public int img; //Image URL
    public String name; //Name
    public String lastname1; //Name
    public String phone; //Name
    public String landmark; //Name
    public String tel; //Name

    public  int idOrder;

    Double lati;
    Double longti;






    public ClassListItems(int img,String name, String lastname1, String phone, String landmark, String tel,Double lati,Double longti,int idOrder)
    {

        this.img = img;
        this.name = name;
        this.lastname1 =lastname1;
        this.phone=phone;
        this.landmark=landmark;
        this.tel=tel;
        this.lati=lati;
        this.longti=longti;
        this.idOrder =idOrder;
    }

    public Double getLati() {
        return lati;
    }
    public Double getLongti() {
        return longti;
    }


    public int getImg() {
        return img;
    }
    public int getIdOrder() {
        return idOrder;
    }
    public String getName() {
        return name;
    }
    public String getLastName() {
        return lastname1;
    }
    public String getPhone() {
        return phone;
    }
    public String getLandmark() {
        return landmark;
    }
    public String getTel() {
        return tel;
    }
}
