package com.apps.my_meal;

import android.util.Log;
import android.view.ViewDebug;

import java.util.Comparator;

public class Sorting {
    public Sorting() {
    }

    public static final Comparator<UploadImage> ASC_by_Title=new Comparator<UploadImage>() {
        @Override
        public int compare(UploadImage meal1, UploadImage meal2) {
            return meal1.getMeal_name().compareTo(meal2.getMeal_name());
        }
    };

    public static final Comparator<UploadImage> DEC_by_Title=new Comparator<UploadImage>() {
        @Override
        public int compare(UploadImage meal1, UploadImage meal2) {

            return meal2.getMeal_name().compareTo(meal1.getMeal_name());
        }
    };

    public static final Comparator<UploadImage> Less_Cocking_Time=new Comparator<UploadImage>() {
        @Override
        public int compare(UploadImage meal1, UploadImage meal2) {
            Integer x=new Integer(meal1.getCocking_time());
            Integer y=new Integer(meal2.getCocking_time());
            return x.compareTo(y);
        }
    };

    public static final Comparator<UploadImage> Less_Calories=new Comparator<UploadImage>() {
        @Override
        public int compare(UploadImage meal1, UploadImage meal2) {
            Integer x=new Integer(meal1.getMeal_calories());
            Integer y=new Integer(meal2.getMeal_calories());
            return x.compareTo(y);


        }
    };

    public static final Comparator<UploadImage> High_Rating=new Comparator<UploadImage>() {
        @Override
        public int compare(UploadImage meal1, UploadImage meal2) {
            Integer x=new Integer(meal1.getRating());
            Integer y=new Integer(meal2.getRating());
            return x.compareTo(y);
        }
    };

}
