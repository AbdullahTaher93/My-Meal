package com.apps.my_meal;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Categories extends AppCompatActivity {


    RecyclerView recyclerView;
    List <categories_Type> categories;
    categories_Type cate;
    DatabaseReference databaseReference;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        databaseReference= FirebaseDatabase.getInstance().getReference("Categories");
        recyclerView=findViewById(R.id.recylecatogires);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(Categories.this,3);
        recyclerView.setLayoutManager(gridLayoutManager);



        categories=new ArrayList<>();



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





    }






}


