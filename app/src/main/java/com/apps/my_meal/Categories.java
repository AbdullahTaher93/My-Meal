package com.apps.my_meal;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Categories extends AppCompatActivity {


    RecyclerView recyclerView;
    ViewPager viewPager;
    List <categories_Type> categories;
    List <UploadImage> uploadImages;

    UploadImage nUploadImage;
    categories_Type cate;
    DatabaseReference databaseReference;
    Query databaseReferenceMeals;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        databaseReference= FirebaseDatabase.getInstance().getReference("Categories");
        databaseReferenceMeals= FirebaseDatabase.getInstance().getReference("uploads").orderByChild("meal_type").equalTo("Lamb");


        recyclerView=findViewById(R.id.recylecatogires);
        viewPager =findViewById(R.id.viewpagerheader);



        GridLayoutManager gridLayoutManager=new GridLayoutManager(Categories.this,3);
        recyclerView.setLayoutManager(gridLayoutManager);




        categories=new ArrayList<>();
        uploadImages=new ArrayList<>();



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot post:dataSnapshot.getChildren() ){
                    categories_Type categories_type=post.getValue(categories_Type.class);
                    categories.add(categories_type);

                }
                catrgories_adpter  catrgories_adpter=new catrgories_adpter(Categories.this,categories);
                recyclerView.setAdapter(catrgories_adpter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReferenceMeals.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot post:dataSnapshot.getChildren()){


                        nUploadImage = post.getValue(UploadImage.class);
                        Log.d("MEALS Name PAGAR", "onBindViewHolder: " + nUploadImage.getMeal_name());
                        uploadImages.add(nUploadImage);


                }

                AdpterHeaderCategories adpterHeaderCategories=new AdpterHeaderCategories(Categories.this,uploadImages);
                viewPager.setAdapter(adpterHeaderCategories);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }






}


