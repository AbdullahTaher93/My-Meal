package com.apps.my_meal;



public class FoodData {
    private String itemname, itemdes;
    private String itemimage;

    public FoodData(String itemname, String itemdes, String itemimage) {
        this.itemname = itemname;
        this.itemdes = itemdes;
        this.itemimage = itemimage;
    }

    public String getItemname() {
        return itemname;
    }

    public String getItemdes() {
        return itemdes;
    }



    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public void setItemdes(String itemdes) {
        this.itemdes = itemdes;
    }

    public void setItemimage(String itemimage) {
        this.itemimage = itemimage;
    }

    public String getItemimage() {
        return itemimage;
    }
}
