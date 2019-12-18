package com.apps.my_meal;

public class infomeals {
    String mealname,ID;

    public infomeals(String ID, String mealname) {
        this.mealname = mealname;
        this.ID = ID;
    }

    public String getMealname() {
        return mealname;
    }

    public void setMealname(String mealname) {
        this.mealname = mealname;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }
}
