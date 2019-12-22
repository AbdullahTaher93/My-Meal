package com.apps.my_meal;

public class categories_Type {


        private String  cat_name,URL;
        public categories_Type() {
        }

        public categories_Type(String cat_name, String URL) {
            this.cat_name = cat_name;
            this.URL = URL;
        }

        public String getCat_name() {
            return cat_name;
        }

        public void setCat_name(String cat_name) {
            this.cat_name = cat_name;
        }

        public String getURL() {
            return URL;
        }

        public void setURL(String URL) {
            this.URL = URL;
        }
    }



