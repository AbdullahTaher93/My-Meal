package com.apps.my_meal;

import android.widget.ImageView;
import android.widget.TextView;

public class FoodData {
    private String itemname, itemdes;
    private int itemimage;

    public FoodData(String itemname, String itemdes, int itemimage) {
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

    public void setItemimage(int itemimage) {
        this.itemimage = itemimage;
    }

    public int getItemimage() {
        return itemimage;
    }
}
