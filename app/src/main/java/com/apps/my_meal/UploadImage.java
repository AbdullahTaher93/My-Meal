package com.apps.my_meal;


public class UploadImage {
    private String imgName;
    private String imgUrl;

    private String meal_name,meal_des,meal_type,USER_ID,Meal_ID;
    private int cocking_time,meal_calories,rating,likes;


    public UploadImage(String USER_ID,String Meal_ID,String imgUrl, String meal_name, String meal_des, String meal_type, int cocking_time, int meal_calories, int rating,int likes) {
        this.USER_ID=USER_ID;
        this.Meal_ID=Meal_ID;
        this.imgUrl = imgUrl;
        this.meal_name = meal_name;
        this.meal_des = meal_des;
        this.meal_type = meal_type;
        this.cocking_time = cocking_time;
        this.meal_calories = meal_calories;
        this.rating = rating;
        this.likes=likes;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public UploadImage() {
    }

    public String getMeal_ID() {
        return Meal_ID;
    }

    public void setMeal_ID(String meal_ID) {
        Meal_ID = meal_ID;
    }

    public UploadImage(String imgName, String imgUrl) {
        if (imgName.trim().equals(""))
        {
            imgName="no name";
        }
       this.imgName = imgName;
        this.imgUrl = imgUrl;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }

    public String getMeal_des() {
        return meal_des;
    }

    public void setMeal_des(String meal_des) {
        this.meal_des = meal_des;
    }

    public String getMeal_type() {
        return meal_type;
    }

    public void setMeal_type(String meal_type) {
        this.meal_type = meal_type;
    }

    public int getCocking_time() {
        return cocking_time;
    }

    public void setCocking_time(int cocking_time) {
        this.cocking_time = cocking_time;
    }

    public int getMeal_calories() {
        return meal_calories;
    }

    public void setMeal_calories(int meal_calories) {
        this.meal_calories = meal_calories;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
