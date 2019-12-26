package com.apps.my_meal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
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

    EditText SearchMeals;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        databaseReference= FirebaseDatabase.getInstance().getReference("Categories");
        databaseReferenceMeals= FirebaseDatabase.getInstance().getReference("uploads").orderByChild("meal_type").equalTo("Lamb");


        recyclerView=findViewById(R.id.recylecatogires);
        viewPager =findViewById(R.id.viewpagerheader);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.logos);




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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_of_users,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        if(item.getItemId()==R.id.My_Posts){
            Intent intent=new Intent(Categories.this,My_Posts.class);
            startActivity(intent);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }




}


