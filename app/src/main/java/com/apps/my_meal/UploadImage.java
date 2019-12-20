package com.apps.my_meal;

import android.net.Uri;

public class UploadImage {
    private String imgName;
    private String imgUrl;

    public UploadImage() {
    }

    public UploadImage(String imgName, String imgUrl) {
        if (imgName.trim().equals(""))
        {
            imgName="no name";
        }
       this.imgName = imgName;
        this.imgUrl = imgUrl;
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
}
